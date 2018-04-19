/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import Entity.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arshavin
 */
public class ProductService {
    
    public ArrayList<Product> SelectAllProducts() {
        ArrayList<Product> listProducts = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/WebService/Product/ListProduct.php");
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                System.out.println("Response" +new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                
                try {
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(tasks);
                    //System.out.println(tasks);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                    System.out.println("list"+list);
                    for (Map<String, Object> obj : list) {
                        Product product = new Product();
                        
                        
                        product.setName(obj.get("name").toString());
                        product.setType(obj.get("type").toString());
                        product.setDescription(obj.get("description").toString());
                        product.setPrice(Double.parseDouble(obj.get("price").toString()));
                        product.setImage(obj.get("photo1").toString());
                        listProducts.add(product);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listProducts;
    }
    
}
