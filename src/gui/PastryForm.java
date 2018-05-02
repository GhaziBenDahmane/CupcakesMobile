package gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;

public class PastryForm extends SideMenuBaseForm {
    private static final String HTML_API_KEY = "AIzaSyDajgw0nmPpqAVnPhHsyMx6qNKTG7dyk1s";

    public PastryForm(Resources res) {
         super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Event");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
            Form hi = new Form("Pastry");
        hi.setLayout(new BorderLayout());
        MapContainer cnt = new MapContainer(HTML_API_KEY);
        

        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(3));
        cnt.zoom(new Coord(36.8367566, 10.2316940), 13);
        cnt.setCameraPosition(new Coord(36.8367566, 10.2316940));
        
        cnt.addMarker(
                EncodedImage.createFromImage(markerImg, false),
                new Coord(36.862499, 10.195556),
                "Hi marker",
                "Optional long description",
                evt -> {
                    ToastBar.showMessage("You clicked the marker", FontImage.MATERIAL_PLACE);
                }
        );
        
        
        Container root = LayeredLayout.encloseIn(
                BorderLayout.center(cnt),
                BorderLayout.south(
                        FlowLayout.encloseBottom()
                )
        );
        hi.getToolbar().addMaterialCommandToSideMenu("  Actuality", FontImage.MATERIAL_DASHBOARD, e -> new ActualityForm(res).show());
        hi.getToolbar().addMaterialCommandToSideMenu("  Training", FontImage.MATERIAL_TRENDING_UP, e -> showOtherForm(res));
        hi.getToolbar().addMaterialCommandToSideMenu("  Product", FontImage.MATERIAL_ACCESS_TIME, e -> showOtherForm(res));
        hi.getToolbar().addMaterialCommandToSideMenu("  Promotion", FontImage.MATERIAL_SETTINGS, e -> new PromotionForm(res).show());
        hi.getToolbar().addMaterialCommandToSideMenu("  Claim", FontImage.MATERIAL_EXIT_TO_APP, e -> new RegisterForm(res).show());
        hi.getToolbar().addMaterialCommandToSideMenu("  Event", FontImage.MATERIAL_EXIT_TO_APP, e -> new EventForm(res).show());
        hi.getToolbar().addMaterialCommandToSideMenu("  Reservation", FontImage.MATERIAL_EXIT_TO_APP, e -> new RegisterForm(res).show());
        hi.add(BorderLayout.CENTER, root);
        hi.show();
    }

  

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}
