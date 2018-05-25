/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Formation;
import Service.FormationService;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author asus
 */
public class FormationForm extends SideMenuBaseForm {

    private Resources theme;
    private Form f;
    Style style = UIManager.getInstance().getComponentStyle("Label");
    Style s = UIManager.getInstance().getComponentStyle("Button");
    Button supp = new Button("Supprimer");
    TextField idSupp;
    Button Email = new Button("Email");
    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);

    public FormationForm(Resources res) {
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

        FormationService ps = new FormationService();
        for (Formation F : ps.getList2()) {

            add(createContainer(F));

        }
        tb.setTitleComponent(titleCmp);

        setupSideMenu(res);
    }

    private void addButtonBottom(Image arrowDown, String text, int color, boolean first) {
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(), first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }

    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if (first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

    public Container createContainer(Formation e) {
        Label icon = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE, style));
        icon.getAllStyles().setAlignment(Component.LEFT);
        Label Nom = new Label("" + e.getNom(), "Container");
        Nom.getAllStyles().setAlignment(Component.RIGHT);
        Label endDate = new Label("" + e.getEnd_date_formation(), "Container");
        endDate.getAllStyles().setAlignment(Component.RIGHT);
        Label startDate = new Label("" + e.getStart_date_formation(), "Container");
        startDate.getAllStyles().setAlignment(Component.RIGHT);
        Container box = TableLayout.encloseIn(2,
                new Label("Nom"),
                Nom,
                new Label("Starting date"),
                startDate,
                new Label("Ending Date "),
                endDate
        );

        Style boxStyle = box.getUnselectedStyle();
        boxStyle.setBgTransparency(255);
        boxStyle.setBgColor(0xeeeeee);
        boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setMargin(1, 1, 1, 1);
        boxStyle.setPadding(1, 1, 1, 1);
        idSupp = new TextField();
        box.add(supp);
        box.add(idSupp);
        box.add(Email);

        supp.addActionListener((j) -> {
            FormationService ser = new FormationService();
            String a = idSupp.getText();
            int id = Integer.parseInt(a);
            if (id > 1 && id < 20) {
                ser.Supprimer(id);
                Dialog.show("Ok", "Succees", "Ok", null);
            } else {
                Dialog.show("Erreur", "Echec", "Ok", null);

            }

        });

        Email.addActionListener((n) -> {
            Message m = new Message("");
            Display.getInstance().sendMessage(new String[]{""}, "test", m);
        });
        Container layers = LayeredLayout.encloseIn(box);
        return layers;

    }
}
