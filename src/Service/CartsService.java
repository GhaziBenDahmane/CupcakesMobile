/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Cart;
import Entity.Product;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arshavin
 */
public class CartsService {
        private boolean isCartAdded= false;
        

    public ArrayList<Cart> SelectCartOfUser(int id_user) {
          ArrayList<Cart> listCarts = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://192.168.0.100:9999/WebService/Product/ListOrder.php?id="+id_user);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                
                try {
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                    try
                    {
                    for (Map<String, Object> obj : list) {
                        Cart cart = new Cart();
                        
                       float id = Float.parseFloat(obj.get("id").toString());

                        cart.setId_cart((int) id);
                        ProductService ps = new ProductService();
                        Product product = ps.findProductById(Integer.parseInt(obj.get("id_product").toString()));
                        cart.setProduct(product);
                        listCarts.add(cart);

                    }}catch(NullPointerException ex)
                    {
                        Dialog.show("Information", "Your cart is empty ", "OK", null);
                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listCarts;
    }

    public void addProductInCart(Cart cart) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://192.168.0.100:9999/WebService/Product/AddToCart.php?product=" + cart.getProduct().getId() + "&user="+ MyApplication.currentUser.getId();

        con.setUrl(Url);


        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        if(con.isReadResponseForErrors())
        {
            setIsCartAdded(true);
        }
        
    }
    
     public void deleteFromCart(int id) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://192.168.0.100:9999/WebService/Product/DeleteFromCart.php?id="+id ;

        con.setUrl(Url);

        System.out.println(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
     
      public void deleteAllProductsFromCart(int id_user) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://192.168.0.100:9999/WebService/Product/EmptyCart.php?id="+id_user ;

        con.setUrl(Url);

        System.out.println(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public boolean isIsCartAdded() {
        return isCartAdded;
    }

    public void setIsCartAdded(boolean isCartAdded) {
        this.isCartAdded = isCartAdded;
    }
    
    
    

}
