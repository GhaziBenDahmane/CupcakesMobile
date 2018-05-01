/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.codename1.io.rest.Rest;
import com.codename1.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 *
 * @author ding
 */
public class Util {

    public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }

    public static String uploadProfilePicture(String image) {
        Map result = (Map) Rest.post("https://api.imgur.com/3/upload")
                .header("Authorization", "Client-ID 34c16e93b018fc5")
                .queryParam("image", image)
                .queryParam("type", "base64")
                .getAsJsonMap()
                .getResponseData()
                .get("data");
        return (String) result.get("link");
    }

    public static String uploadProfilePicture(byte[] image) {
        String imageb64 = Base64.encode(image);
        return uploadProfilePicture(imageb64);
    }

}
