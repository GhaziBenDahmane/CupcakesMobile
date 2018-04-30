/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.rest.Rest;
import com.codename1.ui.Dialog;
import com.codename1.util.Base64;
import com.codename1.util.StringUtil;
import com.mycompany.myapp.MyApplication;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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

    public static void downloadUserImage() {
        ConnectionRequest r = new ConnectionRequest();
        String imageUrl = MyApplication.currentUser.getPhotoprofil();
        List<String> tokenize = StringUtil.tokenize(imageUrl, "/");
        String imageName = tokenize.get(tokenize.size() - 1);
        System.out.println("imageName " + imageName);
        r.setUrl(imageUrl);
        ToastBar.showInfoMessage(FileSystemStorage.getInstance().getAppHomePath() + imageName);
        r.downloadImageToStorage(imageName, e -> {
            Dialog.show(FileSystemStorage.getInstance().getAppHomePath() + imageName, e.toString(), "OK", null);
        });
    }
}
