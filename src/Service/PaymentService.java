/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.rest.Rest;
import com.codename1.ui.Dialog;
import com.mycompany.myapp.MyApplication;
import java.util.Map;

/**
 *
 * @author Arshavin
 */
public class PaymentService {

    //"5105105105105100"
    public void payemnt(String amount, String date, String cardNumber) {

        Dialog ip = new InfiniteProgress().showInifiniteBlocking();
        Map responseData = Rest.post("http://192.168.0.100:9999/Server/web/app_dev.php/payment")
                .queryParam("amount", amount)
                .queryParam("date", date)
                .queryParam("cartNumber", cardNumber)
                .getAsJsonMap()
                .getResponseData();
        ip.dispose();
        System.out.println(responseData);
        CartsService cs = new CartsService();
        cs.deleteAllProductsFromCart(MyApplication.currentUser.getId());
        Dialog.show("Succes", "Thank you to buy our products! Please Rate our Products ", "OK", null);

    }

}
