/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.WebBrowser;
import com.codename1.io.JSONParser;
import com.codename1.io.Storage;
import com.codename1.javascript.JavascriptContext;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Dialog;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arshavin
 */
public class CurrencyConvertService {

    public static Double currency;

    public Double ConvertPrice() {

        if (Storage.getInstance().readObject("currency") == "TND") {
            return 1.00;
        } else {

            final WebBrowser b = new WebBrowser() {

                @Override
                public void onLoad(String url) {
                    BrowserComponent c = (BrowserComponent) this.getInternal();
                    JavascriptContext ctx = new JavascriptContext(c);
                    String responseString = (String) ctx.get("document.body.textContent");
                    byte[] responseBytes = responseString.getBytes();
                    ByteArrayInputStream bais = new ByteArrayInputStream(responseBytes);
                    InputStreamReader reader = new InputStreamReader(bais);
                    JSONParser parser = new JSONParser();
                    Map<String, Object> response = null;
                    try {
                        response = parser.parseJSON(reader);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex.getMessage());
                    }
                    List<Map<String, Object>> list = (List<Map<String, Object>>) response.get("quotes");
                    for (Map<String, Object> obj : list) {
                        System.out.println("midpoint: " + obj.get("midpoint"));
                        currency = Double.parseDouble(obj.get("midpoint").toString());

                    }

                }

            };
            b.setURL("https://web-services.oanda.com/rates/api/v2/rates/spot.json?api_"
                    + "key=mtAApSB7ndk9S9nYzfeeapHS&base=TND&quote=" + Storage.getInstance().readObject("currency"));
            System.out.println("https://web-services.oanda.com/rates/api/v2/rates/spot.json?api_"
                    + "key=mtAApSB7ndk9S9nYzfeeapHS&base=TND&quote=" + Storage.getInstance().readObject("currency"));

            return currency;
        }

    }
}
