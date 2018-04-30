package gui;

import Entity.Actuality;
import Service.ActualityService;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class ActualityForm extends SideMenuBaseForm {

    public ActualityForm(Resources res) {
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

        ActualityService as = new ActualityService();
        for (Actuality p : as.SelectAllActuality()) {

            add(createContainer(p));

        }

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

    Style style = UIManager.getInstance().getComponentStyle("Label");
    private static final String PATH = "http://localhost/picture/blog/";
    Style s = UIManager.getInstance().getComponentStyle("Button");
    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);

    public Container createContainer(Actuality e) {
        Label icon = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE, style));
        icon.getAllStyles().setAlignment(Component.LEFT);
/*
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
*/
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
                GridLayout.encloseIn(2));

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
}
