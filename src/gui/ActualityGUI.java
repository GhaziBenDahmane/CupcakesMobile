/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Actuality;
import Service.ActualityService;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

/**
 *
 * @author haffe
 */
public class ActualityGUI extends SideMenuBaseForm {

    private Button valider;
    private Resources theme;
    private com.codename1.ui.TextArea gui_Text_Area_1 = new com.codename1.ui.TextArea();
    Style s = UIManager.getInstance().getComponentStyle("Button");
    Style style = UIManager.getInstance().getComponentStyle("Label");
    private static final String PATH = "http://localhost/picture/blog/";
    ActualityService as = new ActualityService();
    Actuality e = as.SelectOneActuality();
    ArrayList<Actuality> actualities = as.SelectAllActuality();

    public ActualityGUI(Resources res) {
    

        super(BoxLayout.y());


        Toolbar tb = getToolbar();

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                GridLayout.encloseIn(2)
        );
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        tb.setTitleComponent(titleCmp);

        ActualityService ps = new ActualityService();
        for (Actuality p : ps.SelectAllActuality()) {

            add(createContainer(p));

        }

        setupSideMenu(res);
    }

    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);

    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);

     

    public Container createContainer(Actuality e) {
        Label icon = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE, style));
        icon.getAllStyles().setAlignment(Component.LEFT);

        Label more = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE_HORIZ, style));
        more.getAllStyles().setAlignment(Component.LEFT);
        more.addPointerPressedListener((evt) -> {
            removeAll();

            Toolbar.setGlobalToolbar(true);
            Display.getInstance().scheduleBackgroundTask(() -> {
                // this will take a while...
                Display.getInstance().callSerially(() -> {
                    removeAll();
                    for (Actuality c : actualities) {

                        MultiButton m = new MultiButton();

                        m.setTextLine1("Title : " + c.getTitle());
                        // m.setTextLine2("Image: " + c.getImage());
                        m.setTextLine3("Content: " + c.getContent());
                        m.setTextLine4("date: " + c.getDate());

                        add(m);
                    }

                    revalidate();
                });
            });

            getToolbar().addSearchCommand(x -> {
                String text = (String) x.getSource();
                if (text == null || text.length() == 0) {
                    // clear search
                    for (Component cmp : getContentPane()) {
                        cmp.setHidden(false);
                        cmp.setVisible(true);
                    }
                    getContentPane().animateLayout(150);
                } else {
                    text = text.toLowerCase();
                    for (Component cmp : getContentPane()) {
                        MultiButton mb = (MultiButton) cmp;
                        String line1 = mb.getTextLine1();
                        //String line2 = mb.getTextLine2();
                        String line3 = mb.getTextLine3();
                        boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                                //|| line2 != null && line2.toLowerCase().indexOf(text) > -1
                                || line3 != null && line3.toLowerCase().indexOf(text) > -1;
                        mb.setHidden(!show);
                        mb.setVisible(show);
                    }
                    getContentPane().animateLayout(150);
                }
            }, 4);
        });

        Label title = new Label("" + e.getTitle(), "Container");
        title.getAllStyles().setAlignment(Component.CENTER);
        title.getAllStyles().setUnderline(true);

        Label endDate = new Label("" + e.getContent(), "Container");
        endDate.getAllStyles().setAlignment(Component.RIGHT);

        TextArea content = new com.codename1.ui.TextArea();
        content.setText(e.getContent());
        content.setUIID("SlightlySmallerFontLabelLeft");
        content.setName("Text_Area_1");
        if (e.getImage() != null) {
            System.out.println("i=" + e.getImage() + PATH);
            URLImage i = URLImage.createToStorage(placeholder, e.getImage(),
                    PATH + e.getImage());
            Image image = (Image) i;

            icon.setIcon(image);
        }

        Label Delete = new Label(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, style));
        Delete.addPointerPressedListener((b) -> {
            System.out.println(e.getId());
            as.delAct(e.getId());
        });

        Container box = BoxLayout.encloseY(
                icon,
                title,
                content,
                GridLayout.encloseIn(2, more, Delete));

        Style boxStyle = box.getUnselectedStyle();
        boxStyle.setBgTransparency(255);
        boxStyle.setBgColor(0xeeeeee);
        boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setMargin(1, 1, 1, 1);
        boxStyle.setPadding(1, 1, 1, 1);
        Container layers = LayeredLayout.encloseIn(box);
        return layers;
    }

    public void show(Actuality e) {
        Container c = this.createContainer(e);
        Form f = new Form("Actuality");
        f.add(c);

        f.show();
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}
