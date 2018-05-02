/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Pastry;
import static Service.UserService.API_PATH;
import static Service.UserService.mapToUser;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Rest;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author haffe
 */
public class PastryService {

     private ArrayList<Double> l = new ArrayList<Double>();

    public ArrayList<Pastry> listPastry() {
        ArrayList<Pastry> listEvents = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("http://192.168.0.101:8000/pastrylist");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                try {
                    Map<String, Object> events = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(events);

                    List<Map<String, Object>> list = (List<Map<String, Object>>) events.get("root");
                    for (Map<String, Object> obj : list) {
                        Pastry p = new Pastry();
                        float id = Float.parseFloat(obj.get("id").toString());
                        float nbTable = Float.parseFloat(obj.get("nb_table").toString());

                        p.setId((int) id);
                        p.setAdress(obj.get("address").toString());
                        p.setNbTable((int) nbTable);

                        listEvents.add(p);
                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listEvents;
    }
    // http://maps.googleapis.com/maps/api/geocode/json?address=

    public void gelLocationPastry(String address) {
        ArrayList<Pastry> listEvents = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("https://nominatim.openstreetmap.org/search?q=" + address + "&format=json");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                try {
                    Map<String, Object> events = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(events.get("root"));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) events.get("root");
                    int i =0;
                    for (Map<String, Object> obj : list) {
                        if (i==0)
                        {
                        Double lat = Double.parseDouble(obj.get("lat").toString());
                        Double lon = Double.parseDouble(obj.get("lon").toString());
                        System.out.println("lat =  " + lat);
                        System.out.println("lat =  " + lon);
                        setLocation(lat, lon,l);
                    
                        i++;
                        }
                    
                    }

                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public void setLocation(Double f, Double f2, ArrayList<Double> k) {
       k.add(f);
       k.add(f2);
     
    }

    public ArrayList<Double> getL() {
        return l;
    }

    public void setL(ArrayList<Double> l) {
        this.l = l;
    }
    
   
}
