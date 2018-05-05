/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Product;
import Entity.Promotion;
import com.codename1.components.ToastBar;
import com.codename1.ext.codescan.CodeScanner;
import com.codename1.ext.codescan.ScanResult;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arshavin
 */
public class ScanCodeService {

    public static String code ="";

    public void ScanBarCode() {

        if (!CodeScanner.isSupported()) {
            ToastBar.showErrorMessage("CodeScanner not supported on this platform");

        } 

            CodeScanner.getInstance().scanBarCode(new ScanResult() {

                @Override
                public void scanCompleted(String contents, String formatName, byte[] rawBytes) {
                    //barCode.setText("Bar: " + contents);
                     code = contents;
                   

                }

                @Override
                public void scanCanceled() {
                    Dialog.show("ok", "le", "ok", null);
                }

                @Override
                public void scanError(int errorCode, String message) {
                    System.out.println("err " + message);
                    Dialog.show("ok", message, "ok", null);
                }

            });
       
    }

    public Product findProducts(long barcode) {

        Product product = new Product();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://192.168.0.100:9999/WebService/Product/FindProductByBarCode.php?barcode=" + barcode);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
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
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return product;

    }

}
