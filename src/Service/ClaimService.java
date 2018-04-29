/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Claim;
import com.codename1.io.rest.Rest;
import java.util.Map;

/**
 *
 * @author ding
 */
public class ClaimService {

    public final static String API_URL = "http://localhost:8000/api/";

    public static void add(Claim claim) {
        Map responseData = Rest.post(API_URL + "claims")
                .queryParam("description", "can't")
                .queryParam("type", "problem")
                .queryParam("user", "ghazi")
                .getAsJsonMap()
                .getResponseData();

    }

    public static void getByUser() {
        /*Map result = (Map) Rest.post(API_URL + "claim")
                .queryParam("description", claim.getDescription())
                .queryParam("type", claim.getType())
                .queryParam("user", claim.getClient().getUsername())
                .getAsJsonMap()
                .getResponseData()
                .get("data");*/
        String result = Rest.post(API_URL + "claims")
                .queryParam("description", "can't")
                .queryParam("type", "problem")
                .queryParam("user", "ghazi")
                .getAsString()
                .getResponseData();
        System.out.println(result);
    }

    public static void delete(Claim claim) {
        /*Map result = (Map) Rest.post(API_URL + "claim")
                .queryParam("description", claim.getDescription())
                .queryParam("type", claim.getType())
                .queryParam("user", claim.getClient().getUsername())
                .getAsJsonMap()
                .getResponseData()
                .get("data");*/
        String result = Rest.post(API_URL + "claims")
                .queryParam("description", "can't")
                .queryParam("type", "problem")
                .queryParam("user", "ghazi")
                .getAsString()
                .getResponseData();
        System.out.println(result);
    }

    public static void addClaim(Claim claim) {
        /*Map result = (Map) Rest.post(API_URL + "claim")
                .queryParam("description", claim.getDescription())
                .queryParam("type", claim.getType())
                .queryParam("user", claim.getClient().getUsername())
                .getAsJsonMap()
                .getResponseData()
                .get("data");*/
        String result = Rest.post(API_URL + "claims")
                .queryParam("description", "can't")
                .queryParam("type", "problem")
                .queryParam("user", "ghazi")
                .getAsString()
                .getResponseData();
        System.out.println(result);
    }

    public static Claim mapToClaim(Map<String, Object> e) {
        Claim m = new Claim();
        m.setId(Integer.parseInt((String) e.get("id")));
        m.setDescription((String) e.get("description"));
        m.setType((String) e.get("type"));

        m.setAnswer((String) e.get("answer"));
        m.setAnswered((boolean) e.get("answered"));

        //m.setPostedOn(e.get("posted_on"));
        m.setAnsweredBy(UserService.mapToUser((Map) e.get("answeredBy")));
        m.setClient(UserService.mapToUser((Map) e.get("client")));

        return m;

    }
    /*
    public Claim get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void update(Claim a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteId(int a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Claim fromMap(Map a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Claim> getAll() {
        String result = Rest.post(API_URL + "claims")
                .queryParam("description", "can't")
                .queryParam("type", "problem")
                .queryParam("user", "ghazi")
                .getAsString()
                .getResponseData();
        System.out.println(result);
        return null;
    }*/
}
