/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Event;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author haffe
 */
public class EventService {
    public void addEvent(Event e) {
        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("http://192.168.0.101:8000/event/add?title=" + e.getTitle() + "&sdate=" + toDateTime(e.getStartDate()) + ""
                + "&edate=" + toDateTime(e.getEndDate()) + "&nbPerson=" + e.getNbPerson() + "&nbTable=" + e.getNbTable()
                + "&band=" + e.getBand() + "&cost=" + e.getCost() + "&user="+MyApplication.currentUser.getUsername()+"&url=" + e.getId());

        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public void delEvent(Event e) {

        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("http://192.168.0.101:8000/event/" + e.getId() + "/delete");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public ArrayList<Event> listEvent() {
        ArrayList<Event> listEvents = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("http://192.168.0.101:8000/event/list");
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
                        Event event = new Event();
                        float id = Float.parseFloat(obj.get("id").toString());
                        float nbPerson = Float.parseFloat(obj.get("nbPerson").toString());

                        event.setId((int) id);
                        event.setTitle(obj.get("title").toString());
                        event.setStatus(obj.get("status").toString());
                        event.setStartDate(toDate(obj.get("startDate").toString()));
                        event.setEndDate(toDate((String) obj.get("endDate")));
                        event.setUser( obj.get("user").toString());
                        event.setNbPerson((int)nbPerson);

                        listEvents.add(event);
                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listEvents;
    }

    public Date toDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date);
        } catch (ParseException ex) {
        }
        return null;
    }

    

    private String toDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

}
