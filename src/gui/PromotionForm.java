package gui;

import Entity.Actuality;
import Entity.Promotion;
import Service.ActualityService;
import Service.PromotionService;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

public class PromotionForm extends SideMenuBaseForm {

    private Resources theme;

    Style style = UIManager.getInstance().getComponentStyle("Label");
    private static final String PATH = "http://localhost/picture/";
    Style s = UIManager.getInstance().getComponentStyle("Button");
    PromotionService ps = new PromotionService();
    Promotion e = ps.SelectOnePromotion();
    ArrayList<Promotion> promotions = ps.SelectAllPromotion();

    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);

    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);

    public PromotionForm(Resources res) {
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

        PromotionService ps = new PromotionService();
        for (Promotion p : ps.SelectAllPromotion()) {

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

    public Container createContainer(Promotion e) {
        Label icon = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE, style));
        icon.getAllStyles().setAlignment(Component.LEFT);

        Label more = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE_HORIZ, style));
        more.getAllStyles().setAlignment(Component.RIGHT);
        more.addPointerPressedListener((evt) -> {
            removeAll();
            Toolbar.setGlobalToolbar(true);
            Display.getInstance().scheduleBackgroundTask(() -> {
                // this will take a while...
                Display.getInstance().callSerially(() -> {
                    removeAll();
                    for (Promotion c : promotions) {

                        MultiButton m = new MultiButton();

                        m.setTextLine1("discount : " + c.getDiscount());
                        // m.setTextLine2("Image: " + c.getImage());
                        // m.setTextLine3("Content: " + c.getContent());
                        // m.setTextLine4("date: " + c.getDate());

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
}
