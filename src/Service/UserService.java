/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.User;
import Utils.Utils;
import com.codename1.capture.Capture;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.rest.Rest;
import com.codename1.ui.Image;
import com.mycompany.myapp.MyApplication;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ding
 */
public class UserService {

    //public final static String API_URL = "http://localhost/PiWeb/web/app_dev.php";
    public final static String API_PATH = MyApplication.API_URL + "/api/";

    public static boolean login(String username, String password) {
        String loginUrl = MyApplication.API_URL + "/oauth/v2/token";
        String responseData = Rest.post(loginUrl)
                .queryParam("grant_type", "password")
                .queryParam("client_id", "1_3bcbxd9e24g0gk4swg0kwgcwg4o8k8g4g888kwc44gcc0gwwk4")
                .queryParam("client_secret", "4ok2x70rlfokc8g0wws8c8kwcokw80k44sg48goc0ok4w0so0k")
                .queryParam("username", username)
                .queryParam("password", password)
                .getAsString()
                .getResponseData();
        boolean loggedIn = !responseData.contains("error");
        if (loggedIn) {
            MyApplication.currentUser = get(username);
            try {
                downloadPhoto();
            } catch (Exception e) {

            }
            return true;
        } else {
            return false;
        }

    }

    public static boolean register(String username, String emailAd, String phoneNumber, String pass1) {
        String url = API_PATH + "users";
        try {
            Map responseData = Rest.post(url)
                    .queryParam("username", username)
                    .queryParam("password", pass1)
                    .queryParam("email", emailAd)
                    .queryParam("phone", phoneNumber)
                    .getAsJsonMap()
                    .getResponseData();
            MyApplication.currentUser = mapToUser(responseData);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static void update(User u) {
        String url = API_PATH + "user";
        Map responseData = Rest.put(url)
                .queryParam("id", "" + u.getId())
                .queryParam("username", u.getUsername())
                .queryParam("phone", u.getPhone())
                .queryParam("photo", u.getPhotoprofil())
                .queryParam("email", u.getEmail())
                .getAsJsonMap()
                .getResponseData();
    }

    public static void changePassword(User u, String password) {
        String url = API_PATH + "user";
        Map responseData = Rest.put(url)
                .queryParam("id", "" + u.getId())
                .queryParam("password", password)
                .getAsJsonMap()
                .getResponseData();
    }

    public static void changeEmail(User u, String email) {
        String url = API_PATH + "user";
        Map responseData = Rest.put(url)
                .queryParam("id", "" + u.getId())
                .queryParam("email", email)
                .getAsJsonMap()
                .getResponseData();
    }

    public static void changeUserName(User u, String username) {
        String url = API_PATH + "user";
        Map responseData = Rest.put(url)
                .queryParam("id", "" + u.getId())
                .queryParam("username", username)
                .getAsJsonMap()
                .getResponseData();
    }

    public static void changePhone(User u, String phone) {
        String url = API_PATH + "user";
        Map responseData = Rest.put(url)
                .queryParam("id", "" + u.getId())
                .queryParam("phone", phone)
                .getAsJsonMap()
                .getResponseData();
        System.out.println(responseData);
    }

    public static User get(String username) {
        String url = API_PATH + "users/" + username;
        Map responseData = Rest.get(url)
                .getAsJsonMap()
                .getResponseData();
        return mapToUser(responseData);
    }

    public static void changePicture() {
        String fileName = Capture.capturePhoto();
        try {
            if (fileName != null) {
                InputStream openInputStream = FileSystemStorage.getInstance().openInputStream(fileName);
                byte[] bytesFromInputStream = Utils.getBytesFromInputStream(openInputStream);
                String url = Utils.uploadProfilePicture(bytesFromInputStream);
                System.out.println(url);
                MyApplication.currentUser.setPhotoprofil(url);
                update(MyApplication.currentUser);
                MyApplication.userPicture = Image.createImage(bytesFromInputStream, 0, bytesFromInputStream.length);

            }
        } catch (Exception e) {

        }

    }

    public static void downloadPhoto() {
        if (!MyApplication.currentUser.getPhotoprofil().isEmpty()) {
            byte[] responseData = Rest.get(MyApplication.currentUser.getPhotoprofil())
                    .getAsBytes()
                    .getResponseData();
            MyApplication.userPicture = Image.createImage(responseData, 0, responseData.length);
        } else {
            MyApplication.userPicture = null;
        }

    }

    public static User mapToUser(Map responseData) {
        User u = new User();
        System.out.println(responseData);
        u.setId(((Double) responseData.get("id")).intValue());
        u.setUsername((String) responseData.get("username"));
        u.setEmail((String) responseData.get("email"));
        u.setEnabled(true);
        u.setPassword((String) responseData.get("password"));
        u.setPhone((String) responseData.get("phone"));

        u.setPhotoprofil(responseData.containsKey("profile_picture")
                ? (String) responseData.get("profile_picture") : "");
        return u;

    }

    public static List<User> getAll() {
        List u = new ArrayList<>();
        String url = API_PATH + "users";
        Map responseData = Rest.get(url)
                .getAsJsonMap()
                .getResponseData();
        List l = (ArrayList) responseData.get("root");
        for (Object x : l) {
            u.add(mapToUser((Map) x));
        }
        System.out.println(u);
        return u;
    }
}
