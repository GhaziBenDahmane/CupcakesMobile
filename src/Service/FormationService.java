package Service;

import Entity.Formation;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Rest;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FormationService {

    public ArrayList<Formation> getList2() {
        ArrayList<Formation> u = new ArrayList<>();

        Map result = Rest.get(MyApplication.API_URL + "/api/formations").getAsJsonMap().getResponseData();
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

    public static Date toDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date);
        } catch (ParseException ex) {
        }
        return null;
    }

    public static Formation mapToClaim(Map<String, Object> e) {

        Formation m = new Formation();

        float id = Float.parseFloat(e.get("id").toString());
        m.setId((int) id);
        m.setNom(e.get("nom").toString());
        m.setStatus(e.get("status").toString());
        m.setVideo(e.get("video").toString());
        m.setStart_date_formation(toDate(e.get("start_date_formation").toString()));
        m.setEnd_date_formation(toDate((String) e.get("end_date_formation").toString()));
        return m;

    }

    public static void Supprimer(int id) {
        Rest.delete(MyApplication.API_URL + "/api/formations/" + id)
                .getAsString()
                .getResponseData();
    }
}
