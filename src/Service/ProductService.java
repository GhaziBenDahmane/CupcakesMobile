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
import Entity.Product;
import Entity.Promotion;
import com.codename1.components.ToastBar;
import com.codename1.io.Storage;
import com.codename1.ui.events.ActionListener;
import com.codename1.util.MathUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arshavin
 */
public class ProductService {

    private Double k;
    Product product = new Product();

    public ArrayList<Product> SelectAllProducts() {
        ArrayList<Product> listProducts = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();

        con.setUrl("http://192.168.0.100:9999/WebService/Product/ListProduct.php");

        con.addResponseListener((NetworkEvent evt) -> {
            //listTasks = getListTask(new String(con.getResponseData()));
            JSONParser jsonp = new JSONParser();

            try {
                Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                //System.out.println(tasks);
                List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                for (Map<String, Object> obj : list) {
                    Product product = new Product();

                    float id = Float.parseFloat(obj.get("id").toString());

                    product.setId((int) id);
                    product.setName(obj.get("name").toString());
                    product.setType(obj.get("type").toString());
                    product.setDescription(obj.get("description").toString());
                    CurrencyConvertService ccs = new CurrencyConvertService();
                    Storage.getInstance().writeObject("price", ccs.ConvertPrice());

                    try {
                        Double q;
                        if (Storage.getInstance().readObject("currency") != "TND") {
                            k = CurrencyConvertService.currency * Double.parseDouble(obj.get("price").toString());

                            int d = MathUtil.round(k.floatValue() * 10);
                            Integer l = (Integer) d;
                            q = l.doubleValue() / 10;
                        } else {
                            q = Double.parseDouble(obj.get("price").toString());
                        }
                        product.setPrice(q);
                    } catch (NullPointerException ex) {
                        ToastBar.showErrorMessage("Coverting is not finished yet", 2000);
                    }
                    product.setImage(obj.get("photo1").toString());
                    PromotionService ps = new PromotionService();

                    Promotion promotion = ps.SelectAllProducts(Integer.parseInt(obj.get("promotion_id").toString()));
                    product.setPromotion(promotion);
                    listProducts.add(product);

                }
            } catch (IOException ex) {
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listProducts;
    }

    public ArrayList<Product> findProducts(String name) {
        if (name.length() > 0) {
            ArrayList<Product> listProducts = new ArrayList<>();
            ConnectionRequest con = new ConnectionRequest();
            con.setUrl("http://192.168.0.100:9999/WebService/Product/FindProducts.php/?name" + name);

            con.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    //listTasks = getListTask(new String(con.getResponseData()));
                    JSONParser jsonp = new JSONParser();

                    try {
                        Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                        List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                        for (Map<String, Object> obj : list) {
                            Product product = new Product();

                            float id = Float.parseFloat(obj.get("id").toString());

                            product.setId((int) id);
                            product.setName(obj.get("name").toString());
                            product.setType(obj.get("type").toString());
                            product.setDescription(obj.get("description").toString());
                            product.setPrice(Double.parseDouble(obj.get("price").toString()));
                            product.setImage(obj.get("photo1").toString());
                            PromotionService ps = new PromotionService();

                            Promotion promotion = ps.SelectAllProducts(Integer.parseInt(obj.get("promotion_id").toString()));
                            product.setPromotion(promotion);
                            listProducts.add(product);

                        }
                    } catch (IOException ex) {
                    }
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(con);
            return listProducts;
        }
        return null;

    }

    public Product findProductById(int id) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://192.168.0.100:9999/WebService/Product/FindProductById.php?id=" + id);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();

                try {
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));

                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                    for (Map<String, Object> obj : list) {

                        float id = Float.parseFloat(obj.get("id").toString());

                        product.setId((int) id);
                        product.setName(obj.get("name").toString());
                        product.setType(obj.get("type").toString());
                        product.setDescription(obj.get("description").toString());
                        product.setPrice(Double.parseDouble(obj.get("price").toString()));
                        product.setImage(obj.get("photo1").toString());
                        PromotionService ps = new PromotionService();

                        Promotion promotion = ps.SelectAllProducts(Integer.parseInt(obj.get("promotion_id").toString()));
                        product.setPromotion(promotion);

                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return product;

    }

}
