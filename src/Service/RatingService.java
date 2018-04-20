/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Product;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import Entity.Rating;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arshavin
 */
public class RatingService {
    
    public Rating SelectRatingByProduct(int id) {
        Rating rating = new Rating();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/WebService/Product/ListRating.php/?id="+id);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                System.out.println("Response" + new CharArrayReader(new String(con.getResponseData()).toCharArray()));

                try {
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(tasks);
                    
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                    System.out.println("list" + list);
                    for (Map<String, Object> obj : list) {
                        
 rating.setVotes(Integer.parseInt(obj.get("count").toString()));
                        
                        rating.setRate(Double.parseDouble(obj.get("rate").toString()));
                        

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return rating;
    }
    
    public void addStars(Rating rating) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/WebService/Product/AddRating.php/?id="+rating.getProducts().getId()+"&note="+ rating.getNote();
       
        con.setUrl(Url);

        System.out.println(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
//            if (str.trim().equalsIgnoreCase("OK")) {
//                f2.setTitle(tlogin.getText());
//             f2.show();
//            }
//            else{
//            Dialog.show("error", "login ou pwd invalid", "ok", null);
//            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    
}
