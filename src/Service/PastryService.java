/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Event;
import Entity.Pastry;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
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
    
    public ArrayList<Pastry> listPastry()
    {
        ArrayList<Pastry> listEvents = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("http://127.0.0.1:8000/pastry/list");
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
                        float nbTable = Float.parseFloat(obj.get("nbTable").toString());

                        p.setId((int) id);
                        p.setAdress(obj.get("adress").toString());
                        p.setNbTable((int)nbTable);
            

                        listEvents.add(p);
                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listEvents;
    }
    
   
}
