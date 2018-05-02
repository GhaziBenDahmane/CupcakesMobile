/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Formation;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
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

/**
 *
 * @author Omar
 */
public class ServiceFormation {

    public ArrayList<Formation> getList2() {
        ArrayList<Formation> listAvis = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();

        con.setUrl(MyApplication.API_URL + "/api/Formation/all");

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp = new JSONParser();

                try {
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(tasks);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                    for (Map<String, Object> obj : list) {

                        Formation task = new Formation();

                        float id = Float.parseFloat(obj.get("id").toString());
                        task.setId((int) id);
                        task.setNom(obj.get("nom").toString());
                        task.setStatus(obj.get("status").toString());
                        task.setVideo(obj.get("video").toString());
                        task.setStart_date_formation(toDate(obj.get("startDate").toString()));
                        task.setEnd_date_formation(toDate((String) obj.get("endDate").toString()));

                        listAvis.add(task);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listAvis;
    }

    public Date toDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date);
        } catch (ParseException ex) {
        }
        return null;
    }

    public void Supprimer(int id) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = MyApplication.API_URL + "/api/Formation/Supprimer/" + id;
        con.setUrl(Url);
        con.removeResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

            Dialog.show("Succes", "Avis Supprimer avec Succes", "ok", null);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
}
