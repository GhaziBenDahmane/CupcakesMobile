/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Event;
import Entity.Reservation;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public class ReservationService {
    
    public void addReservation(Reservation e) {
        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("http://127.0.0.1:8000/reservation/add?dateReservation="+ toDateTime(e.getDateReservation())+ ""+
                "&nbPerson=" + e.getNbPerson() + "&nbTable=" + e.getNbTable());

        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public void delReservation(Reservation e) {

        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("http://127.0.0.1:8000/reservation/" + e.getId() + "/delete");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public ArrayList<Reservation> listReservation() {
        ArrayList<Reservation> listReservations = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setHttpMethod("GET");
        con.setPost(true);
        con.setUrl("http://127.0.0.1:8000/reservation/list");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                try {
                    Map<String, Object> reservations = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(reservations);

                    List<Map<String, Object>> list = (List<Map<String, Object>>) reservations.get("root");
                    for (Map<String, Object> obj : list) {
                        Reservation reservation = new Reservation();
                        float id = Float.parseFloat(obj.get("id").toString());
                        float nbPerson = Float.parseFloat(obj.get("nbPerson").toString());
                        float nbTable = Float.parseFloat(obj.get("nbTable").toString());
                       reservation.setId((int) id);
                       reservation.setDateReservation(toDate(obj.get("dateReservation").toString()));
                       reservation.setNbPerson((int) nbPerson );
                       reservation.setNbTable((int) nbTable);
                        
                        

                        listReservations.add(reservation);
                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listReservations;
    }

    public Date toDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.parse(date);
        } catch (ParseException ex) {
        }
        return null;
    }

    public ArrayList<Reservation> findProducts(String name) {
        if (name.length() > 0) {
            ArrayList<Reservation> listReservations = new ArrayList<>();
            ConnectionRequest con = new ConnectionRequest();
            con.setUrl("http://localhost/WebService/Product/FindProducts.php/?name" + name);

            con.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    //listTasks = getListTask(new String(con.getResponseData()));
                    JSONParser jsonp = new JSONParser();
                    System.out.println("Response" + new CharArrayReader(new String(con.getResponseData()).toCharArray()));

                    try {
                        Map<String, Object> reservations = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                        System.out.println(reservations);
                        //System.out.println(tasks);
                        List<Map<String, Object>> list = (List<Map<String, Object>>) reservations.get("root");
                        System.out.println("list" + list);
                        for (Map<String, Object> obj : list) {
                            Reservation reservation = new Reservation();
                            float id = Float.parseFloat(obj.get("id").toString());

                            reservation.setId((int) id);
                            reservation.setNbPerson((int) obj.get("nbPerson"));
                            reservation.setNbTable((int) obj.get("nbTable"));
                            reservation.setDateReservation(toDate(obj.get("dateReservation").toString()));
                            listReservations.add(reservation);

                        }
                    } catch (IOException ex) {
                    }
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(con);
            return listReservations;
        }

        return null;

    }

    private String toDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    
}