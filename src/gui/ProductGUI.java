/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Service.ProductService;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import Entity.Product;
import Entity.Rating;
import Service.RatingService;
import Service.ScanCodeService;
import com.codename1.components.MultiButton;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Slider;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.util.MathUtil;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Arshavin
 */
public final class ProductGUI {

    private Form form;
    private MultiButton mb;
    private Button  max_min_price;
    private Container container;
    private Image image;
    private Double price;
    private Resources theme;
    protected final AutoCompleteTextField search;

    private static final String PATH = "http://localhost/picture/";

    Style s = UIManager.getInstance().getComponentStyle("Button");
    Style style = UIManager.getInstance().getComponentStyle("Label");
    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);
    Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
    Font mediumPlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_MEDIUM);
    Font largePlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);
    Font smallUnderlineMonospaceFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_UNDERLINED, Font.SIZE_SMALL);

    public ProductGUI() throws IOException {
        form = new Form("Products");
        theme = UIManager.initFirstTheme("/theme");

        ArrayList<Product> products = new ArrayList<>();
        ProductService ps = new ProductService();
        products = ps.SelectAllProducts();
        final DefaultListModel<String> options = new DefaultListModel<>();
        final Form f = new Form("products");
        final ArrayList<Product> pr = products;
        ArrayList<Integer> ids = new ArrayList<>();

        max_min_price = new Button();
        max_min_price.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ACCOUNT_BALANCE_WALLET, style));

        search = new AutoCompleteTextField(options) {
            @Override
            protected boolean filter(String text) {
                if (text.length() == 0) {
                    return false;
                }
                ArrayList<Product> l = ps.findProducts(text);

                if (l == null || l.isEmpty()) {
                    return false;
                }

                options.removeAll();
                for (Product s : l) {
                    options.addItem(s.getName());
                    ids.add(s.getId());
                }

                return true;
            }

        };

        search.setHint("Name Product", FontImage.createMaterial(FontImage.MATERIAL_SEARCH, style));
        search.addListListener((ActionListener) (ActionEvent evt) -> {
            System.out.println(search.getText());
            int i = ids.get(options.getSelectedIndex());
            for (Product p1 : pr) {
                if (p1.getId() == i) {
                    f.add(createRankWidget(p1));
                }
            }
            f.show();
        });

        search.setMinimumElementsShownInPopup(4);
        f.getToolbar().addCommandToRightBar("", FontImage.createMaterial(FontImage.MATERIAL_BACKSPACE, style), e -> {
            form.show();
            f.removeAll();
            search.clear();
            search.setHint("Name Product", FontImage.createMaterial(FontImage.MATERIAL_SEARCH, style));

        });
        form.getToolbar().addCommandToRightBar("", theme.getImage("code.png"), e -> {
          ScanCodeService scs= new ScanCodeService();
          Product product = new Product();
          product =scs.ScanBarCode();
          f.add(createRankWidget(product));
          f.show();
        });
        Container co = new Container(BoxLayout.x());
        
        co.add(search);

        form.add(co);

        for (Product product : products) {
            form.add(createRankWidget(product));
        }

    }

    public SwipeableContainer createRankWidget(Product p) {

        mb = new MultiButton();
        mb.setUIID("Button");
        Button n = new Button();
        mb.setPropertyValue("name", n);
        mb.setUIIDLine1("Title");
        mb.setUIIDLine2("Label");
        mb.setUIIDLine3("Badge");
        mb.setUIIDLine4("TouchCommand");

        mb.setTextLine1(p.getName());
        mb.setTextLine2(p.getType());
        mb.setTextLine3(p.getDescription());
        price = p.getPrice() - (p.getPrice() * p.getPromotion().getDiscount());
        mb.setTextLine4(price.toString() + " DT");
        URLImage i = URLImage.createToStorage(placeholder, p.getImage(),
                PATH + p.getImage());
        image = (Image) i;

        mb.setIcon(image);

        return new SwipeableContainer(FlowLayout.encloseCenterMiddle(createStarRankSlider(p)),
                mb);
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    private Container createStarRankSlider(Product p) {
        Container c = new Container(BoxLayout.y());
        Label rate = new Label("Rate");

        Slider starRank = new Slider();
        starRank.setEditable(true);
        starRank.setMinValue(1);
        starRank.setMaxValue(6);
        starRank.setRenderValueOnTop(true);
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style st = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, st).toImage();
        st.setOpacity(100);
        st.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, st).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        RatingService rs = new RatingService();
        Rating rating = rs.SelectRatingByProduct(p.getId());
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        //starRank.setProgress(rating.getRate().intValue());
        starRank.addActionListener((ActionListener) (ActionEvent evt) -> {
            System.out.println(starRank.getProgress());
            Rating newRating = new Rating(starRank.getProgress());
            newRating.setProducts(p);
            rs.addStars(newRating);
            Rating r = rs.SelectRatingByProduct(p.getId());
            int d = MathUtil.round(r.getRate().floatValue() * 100);
            Integer l = (Integer) d;
            Double q = l.doubleValue() / 100;
            System.out.println(q);
            rate.setText("Rate : " + q + "/5.0" + " (" + r.getVotes().toString() + " votes)");
        });
        int d = MathUtil.round(rating.getRate().floatValue() * 100);
        Integer l = (Integer) d;
        Double q = l.doubleValue() / 100;
        rate.setText("Rate : " + q + "/5.0" + " (" + rating.getVotes().toString() + " votes)");
        rate.getAllStyles().setFont(mediumPlainMonospaceFont);

        c.add(starRank);
        c.add(rate);

        return c;
    }

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

}
