/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arshavin
 */
public class CurrencyConvertService {

    public static Double currency=1.00;

    public Double ConvertPrice() {

        if (Storage.getInstance().readObject("currency") == "TND") {
            
        } else {
            ConnectionRequest cnx = new ConnectionRequest() {
                @Override
                protected void readResponse(InputStream input) throws IOException {
                    
                    InputStreamReader reader = new InputStreamReader(input);
                    JSONParser parser = new JSONParser();
                    Map<String, Object> response;
                    response = parser.parseJSON(reader);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) response.get("quotes");
                    System.out.println(list);
                    for (Map<String, Object> obj : list) {
                        System.out.println("midpoint: " + obj.get("midpoint"));
                        currency = Double.parseDouble(obj.get("midpoint").toString());
                    }
                }
            };
            cnx.setPost(false);
            cnx.setHttpMethod("GET");
            cnx.setUrl("https://web-services.oanda.com/rates/api/v2/rates/spot.json?api_"
                    + "key=mtAApSB7ndk9S9nYzfeeapHS&base=TND&quote=" + Storage.getInstance().readObject("currency"));

            NetworkManager.getInstance().addToQueue(cnx);

        }
        return currency;
    }
    

}
