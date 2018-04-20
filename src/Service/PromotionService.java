/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Promotion;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arshavin
 */
public class PromotionService {

    public Promotion SelectAllProducts(int id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/WebService/Product/ListPromotion.php/?id=" + id);
        Promotion promotion = new Promotion();

        con.addResponseListener((NetworkEvent evt) -> {
            //listTasks = getListTask(new String(con.getResponseData()));
            JSONParser jsonp = new JSONParser();
            System.out.println("Response" + new CharArrayReader(new String(con.getResponseData()).toCharArray()));
            try {
                Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                System.out.println(tasks);
                //System.out.println(tasks);
                List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                System.out.println("list" + list);
                for (Map<String, Object> obj : list) {
                    float id1 = Float.parseFloat(obj.get("id").toString());
                    promotion.setId_promotion((int) id1);
                    promotion.setDiscount(Double.parseDouble(obj.get("discount").toString()));
                }
            }catch (IOException ex) {
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return promotion;
    }

}
