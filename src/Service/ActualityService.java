/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Actuality;
import Entity.Promotion;
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
public class ActualityService {

    public Actuality SelectOneActuality() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1:8000/bloglist");
        Actuality a = new Actuality();
        con.addResponseListener((NetworkEvent evt) -> {
            //listTasks = getListTask(new String(con.getResponseData()));
            JSONParser jsonp = new JSONParser();
            System.out.println("Response" + new CharArrayReader(new String(con.getResponseData()).toCharArray()));
            try {
                Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                System.out.println(tasks);
                //System.out.println(tasks);
                List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                for (Map<String, Object> obj : list) {
//                    float id = Float.parseFloat(obj.get("photo").toString());
//                    promotion.setId_promotion((int) id1);
                    a.setContent(obj.get("content").toString());
                    a.setDate(toDate(obj.get("date").toString()));
                    a.setTitle(obj.get("title").toString());
                    a.setImage(obj.get("photo").toString());
                }
            } catch (IOException ex) {
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return a;
    }

    public Date toDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.parse(date);
        } catch (ParseException ex) {
        }
        return null;
    }

    public ArrayList<Actuality> SelectAllActuality() {

        ArrayList<Actuality> listPromotion = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("http://127.0.0.1:8000/bloglist");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                try {
                    Map<String, Object> promotions = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));

                    List<Map<String, Object>> list = (List<Map<String, Object>>) promotions.get("root");
                    for (Map<String, Object> obj : list) {
Actuality a = new Actuality();
                        a.setContent(obj.get("content").toString());
                    a.setDate(toDate(obj.get("date").toString()));
                    a.setTitle(obj.get("title").toString());
                    a.setImage(obj.get("photo").toString());

                        listPromotion.add(a);
                    }
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listPromotion;
    }
}
