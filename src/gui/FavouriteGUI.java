/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Cart;
import Entity.Product;
import Entity.Rating;
import Service.CartsService;
import Service.FavouriteService;
import Service.RatingService;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Stroke;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.util.MathUtil;
import com.mycompany.myapp.MyApplication;
import static gui.ProductGUI.hasRated;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Arshavin
 */
public class FavouriteGUI extends SideMenuBaseForm {
    

    private MultiButton mb;
    private Container container;
    private Image image;
    private Double price;
    private final Resources theme;
    public static int id_cart = 0;
    
    private static final String PATH = "http://192.168.0.100:10000/picture/";
    
    Style s = UIManager.getInstance().getComponentStyle("Button");
    Style style = UIManager.getInstance().getComponentStyle("Label");
    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);
    Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
    Font mediumPlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_MEDIUM);
    Font largePlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);
    Font smallUnderlineMonospaceFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_UNDERLINED, Font.SIZE_SMALL);
    
    public FavouriteGUI() {
         super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Favourite");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        //form = new Form("Products");
        theme = UIManager.initFirstTheme("/theme_1");
        
        mb = new MultiButton();
        mb.setWidth(Display.getInstance().getDisplayWidth());
        ArrayList<Product> products = new ArrayList<>();
        FavouriteService fs = new FavouriteService();
        fs.createSQLiteDB();
        getToolbar().addCommandToRightBar("", FontImage.createMaterial(FontImage.MATERIAL_BACKSPACE, style), e -> {
            ProductGUI pp;
            try {
                pp = new ProductGUI();
                pp.show();
            } catch (IOException ex) {
                System.out.println("log" + ex.getMessage());
            }
            
        });
        products = fs.SelectProductFromSQLiteDB();
        for (Product product : products) {
            add(createFavouriteWidget(product));
        }
                setupSideMenu(theme);

    }
    
    public SwipeableContainer createFavouriteWidget(Product p) {
        mb = new MultiButton();
        mb.setUIID("Button");
        Button n = new Button();
        mb.setPropertyValue("name", n);
        mb.setUIIDLine1("Title");
        mb.setUIIDLine2("Label");
        mb.setUIIDLine3("Badge");
        mb.setUIIDLine4("TouchCommand");
        setDesign(mb.getAllStyles());
        
        mb.setTextLine1(p.getName());
        mb.setTextLine2(p.getType());
        mb.setTextLine3(p.getDescription());
        
        price = (p.getPrice() - (p.getPrice() * p.getPromotion().getDiscount()));
        mb.setTextLine4(price.toString() + " " + Storage.getInstance().readObject("currency"));
        
        URLImage i = URLImage.createToStorage(placeholder, p.getImage(),
                PATH + p.getImage());
        image = (Image) i;
        
        mb.setIcon(image);
        Container c1 = new Container(BoxLayout.y());
        Button addButton = new Button();
        addButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_ADD_SHOPPING_CART, style));
        Button removeFavourite = new Button();
        Button settings = new Button();
        removeFavourite.setIcon(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, style));
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, style));
        c1.addAll(addButton, removeFavourite, settings);
        
        settings.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent evt) {
                CurrencySettingsGUI ccs = new CurrencySettingsGUI();
                ccs.getForm().show();
            }
        });
        
        removeFavourite.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog ip = new InfiniteProgress().showInifiniteBlocking();
                removeAll();
                FavouriteService fs = new FavouriteService();
                fs.deleteProductFromFavouriteInSQLiteDB(p.getId());
                
                ArrayList<Product> products = fs.SelectProductFromSQLiteDB();
                if (!products.isEmpty()) {
                    for (Product pro : products) {
                        
                        add(createFavouriteWidget(pro));
                        
                    }
                    
                    add(container);
                } else {
                   add(new Label("Favourite list is empty !"));
                }
                //form.revalidate();

                ip.dispose();
            }
        });
        
        addButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent evt) {
                CartsService cs = new CartsService();
                Cart cart = new Cart();
                cart.setId_cart(id_cart);
                id_cart++;
                cart.setProduct(p);
                
                ArrayList<Cart> carts = new ArrayList<>();
                carts = cs.SelectCartOfUser(MyApplication.currentUser.getId());
                
                if (isProductInCart(carts, p)) {
                    cs.addProductInCart(cart);
                    
                    if (cs.isIsCartAdded()) {
                        Dialog.show("Succes", "The product is added in your cart.", "OK", null);
                    } else {
                        Dialog.show("Error", "Adding product failed.", "OK", null);
                    }
                } else {
                    Dialog.show("Warning", "Product exist in your cart.", "OK", null);
                }
                
            }
        });
        
        return new SwipeableContainer(FlowLayout.encloseCenterMiddle(createStarRankSlider(p)),
                c1, mb);
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
            if (Storage.getInstance().readObject("rate") == "false") {
                rs.addStars(newRating, MyApplication.currentUser.getId());
                hasRated = true;
                Storage.getInstance().writeObject("rate", "true");
            } else {
                rs.UpdateStars(newRating, MyApplication.currentUser.getId());
            }
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
    
    private boolean isProductInCart(ArrayList<Cart> carts, Product product) {
        for (Cart cart : carts) {
            if (cart.getProduct().getId() == product.getId()) {
                return false;
            }
        }
        return true;
    }
    
    public void setDesign(Style s) {
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        s.setBorder(RoundBorder.create().
                rectangle(true).
                color(0x3f4996).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        s.setMarginUnit(Style.UNIT_TYPE_DIPS);
        s.setMargin(Component.BOTTOM, 3);
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
    
}
