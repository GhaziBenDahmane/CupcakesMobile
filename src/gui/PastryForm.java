/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Service.LocalPastryDB;
import Service.PastryService;
import com.codename1.googlemaps.MapContainer;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author haffe
 */
public class PastryForm extends SideMenuBaseForm {

    private static final String HTML_API_KEY = "AIzaSyDajgw0nmPpqAVnPhHsyMx6qNKTG7dyk1s";
    private Form current;

    public PastryForm(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        Container titleCmp = BoxLayout.encloseY(
                BoxLayout.encloseY(
                        new Label("Pastries", "Title")
                ),
                FlowLayout.encloseIn(menuButton)
        );
        tb.setTitleComponent(titleCmp);

        start(res);
        setupSideMenu(res);

    }

    public void start(Resources res) {
        if (current != null) {
            current.show();
            return;
        }

        MapContainer cnt = new MapContainer(HTML_API_KEY);
        cnt.zoom(new Coord(36.8367566, 10.2316940), 13);
        cnt.setCameraPosition(new Coord(36.8367566, 10.2316940));

        PastryService ps = new PastryService();

        try {
            System.out.println("IN TRY");
            ArrayList<Entity.Pastry> ls = ps.listPastry();
            LocalPastryDB ldb = new LocalPastryDB();
            for (Entity.Pastry l : ls) {
                ps.gelLocationPastry(l.getAdress());
                double alt = ps.getL().get(0);
                double lon = ps.getL().get(1);
                ldb.createSQLiteDB();
                ldb.insertInSQLiteDB(l, alt, lon);
                System.out.println("pastry =" + l.toString() + "ALT= " + alt + "LON " + lon);
                cnt.setCameraPosition(new Coord(36.8367566, 10.2316940));
                cnt.addMarker(EncodedImage.create("/maps-pin.png"),
                        new Coord(alt, lon), "Hi marker",
                        "Optional long description",
                        new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        Dialog.show("Marker Clicked!", "This pastry has " + l.getNbTable() + " table", "OK", null);
                    }
                });
            }

        } catch (Exception e) {

            System.out.println("EXE" + e);
            System.out.println("IN CATCH");

            LocalPastryDB ldb = new LocalPastryDB();
            ArrayList<Entity.Pastry> ls = ldb.SelectFromSQLiteDB();
            for (Entity.Pastry l : ls) {
                try {
                    System.out.println("pastry =" + l.toString() + "ALT= " + l.getLat() + "LON " + l.getLon());

                    cnt.setCameraPosition(new Coord(36.8367566, 10.2316940));
                    cnt.addMarker(EncodedImage.create("/maps-pin.png"),
                            new Coord(l.getLat(), l.getLon()), "Hi marker",
                            "Optional long description",
                            new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            Dialog.show("Marker Clicked!", "This pastry has " + l.getNbTable() + " table", "OK", null);
                        }
                    });
                } catch (IOException ex) {
                    System.out.println("Image Not Found");

                }
            }

        }

        add(cnt);

    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }

    @Override
    protected void showOtherForm(Resources res) {
    }

}
