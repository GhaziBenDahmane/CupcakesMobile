/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Promotion;
import Service.PromotionService;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author haffe
 */
public class PromotionGUI {

    public PromotionGUI() {
        PromotionService as = new PromotionService();
        Promotion e = as.SelectOnePromotion();
        System.out.println("" + e.toString());
        show(e);
    }

    private Resources theme;

    Style style = UIManager.getInstance().getComponentStyle("Label");
    private static final String PATH = "http://localhost/picture/";
    Style s = UIManager.getInstance().getComponentStyle("Button");

    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);

    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);

    public Container createContainer(Promotion e) {
        Label icon = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE, style));
        icon.getAllStyles().setAlignment(Component.LEFT);

        Label more = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE_HORIZ, style));
        more.getAllStyles().setAlignment(Component.RIGHT);
        more.addPointerPressedListener((evt) -> {
            Form list = new Form("Promotion List");
            PromotionService ps = new PromotionService();
            for (Promotion p : ps.SelectAllPromotion()) {

                list.add(createContainer(p));

            }
            list.show();
        });

        Label discount = new Label("" + e.getDiscount() * 100 + "%", "Container");
        discount.getAllStyles().setAlignment(Component.CENTER);
        discount.getAllStyles().setUnderline(true);

        Label endDate = new Label("" + e.getEnding_date(), "Container");
        endDate.getAllStyles().setAlignment(Component.RIGHT);
        Label startDate = new Label("" + e.getStarting_date(), "Container");
        startDate.getAllStyles().setAlignment(Component.RIGHT);

        System.out.println("bhhdhdhvhdvhdvhd" + e.getImage());
        URLImage i = URLImage.createToStorage(placeholder, e.getImage(),
                PATH + e.getImage());
        Image image = (Image) i;

        icon.setIcon(image);

        Container box = TableLayout.encloseIn(2,
                icon,
                discount,
                new Label("Starting date"),
                startDate,
                new Label("Ending Date "),
                endDate,
                more);
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

    public void show(Promotion e) {
        Container c = this.createContainer(e);
        Form f = new Form("Promotion");
        f.add(c);
        f.show();
    }
}
