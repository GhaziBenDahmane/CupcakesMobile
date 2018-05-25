/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Claim;
import com.codename1.io.rest.Rest;
import com.codename1.util.StringUtil;
import com.mycompany.myapp.MyApplication;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ding
 */
public class ClaimService {

    public final static String API_PATH = MyApplication.API_URL + "/api/";

    public static void add(Claim claim) {
        Map responseData = Rest.post(API_PATH + "claims")
                .queryParam("description", claim.getDescription())
                .queryParam("type", claim.getType())
                .queryParam("user", MyApplication.currentUser.getUsername())
                .getAsJsonMap()
                .getResponseData();

    }

    public static List<Claim> getByUser() {
        List<Claim> u = new ArrayList<>();
        Map result = Rest.get(API_PATH + "users/" + MyApplication.currentUser.getUsername() + "/claims")
                .getAsJsonMap()
                .getResponseData();
        List l = (ArrayList) result.get("root");
        System.out.println(l);
        if (l == null) {
            return u;
        }
        for (Object x : l) {
            u.add(mapToClaim((Map) x));
        }
        System.out.println(u);
        return u;
    }

    public static void delete(Claim claim) {
        String result = Rest.delete(API_PATH + "claims/" + claim.getId())
                .getAsString()
                .getResponseData();
        System.out.println(result);
    }

    public static Claim mapToClaim(Map<String, Object> e) {

        Claim m = new Claim();
        m.setId(((Double) e.get("id")).intValue());
        m.setDescription((String) e.get("description"));
        m.setType((String) e.get("type"));
        m.setClient(UserService.mapToUser((Map) e.get("client")));

        if (!((String) e.get("answered")).equals("false")) {
            m.setAnswer((String) e.get("answer"));
            m.setAnswered(true);
            m.setAnsweredBy(UserService.mapToUser((Map) e.get("answered_by")));
        } else {
            m.setAnswered(false);

        }
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

            String date = StringUtil.tokenize(((String) e.get("posted_on")), "T").get(0);
            Date parse = formatter1.parse(date);
            m.setPostedOn(parse);

            return m;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
