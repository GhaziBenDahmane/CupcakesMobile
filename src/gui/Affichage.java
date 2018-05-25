/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Formation;
import Service.FormationService;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

/**
 *
 * @author sana
 */
public class Affichage extends SideMenuBaseForm {

    SpanLabel lb;

    public Affichage(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Formation");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                GridLayout.encloseIn(2)
        );
        lb = new SpanLabel("");
        add(lb);
        FormationService SP = new FormationService();
        ArrayList<Formation> lis = SP.getList2();
        lb.setText(lis.toString());
        getToolbar().addCommandToRightBar("back", null, (ev) -> {

            new MenuFormation(UIManager.initFirstTheme("/theme_1")).show();
        });
        tb.setTitleComponent(titleCmp);

        setupSideMenu(res);
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

}
