/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Participant;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author haffe
 */
public class ParticipantService {

    public void addParticipant() {

    }

    public void delParticipant() {

    }

    public ArrayList<Participant> listParticipant(int id_event) {

        ArrayList<Participant> listParticipants = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("http://127.0.0.1:8000/participants/list/" + id_event);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                try {
                    Map<String, Object> participants = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(participants);

                    List<Map<String, Object>> list = (List<Map<String, Object>>) participants.get("root");
                    for (Map<String, Object> obj : list) {

                        Participant participant = new Participant();
                        participant.setId(1);
                        participant.setUser_id(obj.get("username").toString());
                        participant.setEvent_id("");
                        participant.setDate(toDate("11-02-2012"));
                        listParticipants.add(participant);
                    }
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listParticipants;
    }
    
    
    
     public ArrayList<Participant> searchParticipant(String key) {

        ArrayList<Participant> listParticipants = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("http://127.0.0.1:8000/participants/listAll/" + key);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                try {
                    Map<String, Object> participants = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(participants);

                    List<Map<String, Object>> list = (List<Map<String, Object>>) participants.get("root");
                    for (Map<String, Object> obj : list) {

                        Participant participant = new Participant();
                   float id = Float.parseFloat(obj.get("id").toString());
                        
                        participant.setId((int) id);
                        participant.setUser_id(obj.get("username").toString());
                        listParticipants.add(participant);
                    }
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listParticipants;
    }

    public Date toDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.parse(date);
        } catch (ParseException ex) {
        }
        return null;
    }
}
