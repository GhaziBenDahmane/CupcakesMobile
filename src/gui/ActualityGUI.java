/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Actuality;
import Entity.Promotion;
import Service.ActualityService;
import Service.PromotionService;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author haffe
 */
public class ActualityGUI {

    private Resources theme;
    private com.codename1.ui.TextArea gui_Text_Area_1 = new com.codename1.ui.TextArea();

    Style style = UIManager.getInstance().getComponentStyle("Label");
    private static final String PATH = "http://localhost/picture/blog/";
    Style s = UIManager.getInstance().getComponentStyle("Button");

    public ActualityGUI() {
        ActualityService as = new ActualityService();
        Actuality e = as.SelectOneActuality();
        show(e);
    }

    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);

    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);

    public Container createContainer(Actuality e) {
        Label icon = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE, style));
        icon.getAllStyles().setAlignment(Component.LEFT);

        Label more = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE_HORIZ, style));
        more.getAllStyles().setAlignment(Component.LEFT);
        more.addPointerPressedListener((evt) -> {
            
            Form list = new Form("Actuality");
            ActualityService as = new ActualityService();
            for (Actuality p : as.SelectAllActuality()) {

                list.add(createContainer(p));

            }
            list.show();
        });

        Label discount = new Label("" + e.getTitle() + "%", "Container");
        discount.getAllStyles().setAlignment(Component.CENTER);
        discount.getAllStyles().setUnderline(true);

        Label endDate = new Label("" + e.getContent(), "Container");
        endDate.getAllStyles().setAlignment(Component.RIGHT);

        TextArea content = new com.codename1.ui.TextArea();
        content.setText(e.getContent());
        content.setUIID("SlightlySmallerFontLabelLeft");
        content.setName("Text_Area_1");

        System.out.println("i=" + e.getImage() + PATH);
        URLImage i = URLImage.createToStorage(placeholder, e.getImage(),
                PATH + e.getImage());
        Image image = (Image) i;

        icon.setIcon(image);

        Container box = BoxLayout.encloseY(
                icon,
                content,
                GridLayout.encloseIn(2, more));

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
}
