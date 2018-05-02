/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Cart;
import Service.CartsService;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.util.MathUtil;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Arshavin
 */
public final class CartGUI {

    private Form form;
    private final Container container;
    private MultiButton mb;
    private Image image;
    private static final String PATH = "http://192.168.0.100:9999/picture/";
    private Double price;
    private Integer ch;
    private final Label purchasePrice;
    public static Float t = new Float(0.0);
    private final CartsService cs = new CartsService();
    private ArrayList<Cart> carts = new ArrayList<>();
    boolean isRemoved = false;

    Style s = UIManager.getInstance().getComponentStyle("Button");
    Style style = UIManager.getInstance().getComponentStyle("Label");
    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);
    Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
    Font mediumPlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_MEDIUM);
    Font largePlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);
    Font smallUnderlineMonospaceFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_UNDERLINED, Font.SIZE_SMALL);

    public CartGUI() {
        form = new Form("Cart", BoxLayout.y());
        Label total = new Label("Total :");
        Label empty = new Label("Your cart is empty");
        Button purchase = new Button("Purchase");
        container = new Container(BoxLayout.x());
        purchasePrice = new Label();


        carts = cs.SelectCartOfUser(1);
        purchasePrice.getAllStyles().setFgColor(0x0000ff);

        //carts = cs.SelectCartOfUser();
        //purchasePrice.getAllStyles().setFgColor(Color.BLUE.getIntArgbPre());
        purchasePrice.setAutoSizeMode(true);

        form.getToolbar().addCommandToRightBar("", FontImage.createMaterial(FontImage.MATERIAL_BACKSPACE, style), e -> {
            ProductGUI pp;
            try {
                pp = new ProductGUI();
                pp.getForm().show();
            } catch (IOException ex) {
                System.out.println("log" + ex.getMessage());
            }

        });

        // purchasePrice.setText("0.0");
        container.add(total);
        container.add(purchasePrice);
        container.add(purchase);
        if (!carts.isEmpty()) {

            for (Cart c : carts) {

                form.add(createCartWidget(c));

            }
        } else {
            purchasePrice.setText("0.0");
            form.add(empty);
        }
        purchase.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                PayementGUI pgui = new PayementGUI(purchasePrice.getText());
                t=new Float(0);
                pgui.getVal().show();
            }
        });
        form.add(container);

    }

    public SwipeableContainer createCartWidget(Cart c) {

        mb = new MultiButton();
        mb.setUIID("Button");
        mb.setUIIDLine1("Title");
        mb.setUIIDLine2("Label");
        mb.setUIIDLine3("Badge");
        mb.setUIIDLine4("TouchCommand");
        mb.setWidth(Display.getInstance().getDisplayWidth());
        mb.setX(200);

        mb.setTextLine1(c.getProduct().getName());
        price = (c.getProduct().getPrice() - (c.getProduct().getPrice() * c.getProduct().getPromotion().getDiscount()));
        mb.setTextLine4(price.toString() + " " + Storage.getInstance().readObject("currency"));
        t = t + price.floatValue();

        Integer o = (Integer) (MathUtil.round(t * 100));
        t = o.floatValue() / 100;

        purchasePrice.setText(t.toString());

        URLImage i = URLImage.createToStorage(placeholder, c.getProduct().getImage(),
                PATH + c.getProduct().getImage());
        image = (Image) i;

        mb.setIcon(image);

        Container remove = new Container(BoxLayout.y());
        Button removeButton = new Button();
        Button favourite = new Button();
        Button settings = new Button();
        removeButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_SHOPPING_CART, style));
        favourite.setIcon(FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, style));
        settings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SETTINGS, style));

        remove.add(removeButton);
        remove.add(favourite);
        remove.add(settings);

        settings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                CurrencySettingsGUI ccs = new CurrencySettingsGUI();
                ccs.getForm().show();
            }
        });

        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                Dialog ip = new InfiniteProgress().showInifiniteBlocking();
                form.removeAll();
                cs.deleteFromCart(c.getId_cart());

                carts = cs.SelectCartOfUser(1);

                isRemoved = true;
                System.out.println(t);

                t = new Float(0);
                if (!carts.isEmpty()) {
                    for (Cart c : carts) {
                        purchasePrice.setText(t.toString());
                        form.add(createCartWidget(c));

                    }
                } else {
                    form.add(new Label("Your cart is empty"));
                    purchasePrice.setText("0.0");
                }
                form.add(container);
                isRemoved = false;
                //form.revalidate();

                ip.dispose();
            }

        });

        return new SwipeableContainer(FlowLayout.encloseCenterMiddle(createQuantityWidget(price)),
                remove, mb);
    }

    private Container createQuantityWidget(Double s) {
        Container c = new Container(BoxLayout.y());
        Label totalPrice = new Label();
        totalPrice.setText(s + " " + Storage.getInstance().readObject("currency"));

        Container child = new Container(BoxLayout.y());
        Button add = new Button("+");
        Button sous = new Button("-");
        Label quantity = new Label();

        totalPrice.setAutoSizeMode(true);
        quantity.setText("1");

        add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                ch = Integer.parseInt(quantity.getText()) + 1;
                quantity.setText(ch.toString());
                int d = MathUtil.round(s.floatValue() * ch * 100);
                Integer l = (Integer) d;
                Double q = l.doubleValue() / 100;
                totalPrice.setText(q.floatValue() + " " + Storage.getInstance().readObject("currency"));
                Float t = Float.parseFloat(purchasePrice.getText()) + s.floatValue();
                Integer o = (Integer) (MathUtil.round(t * 100));
                Float m = o.floatValue() / 100;
                purchasePrice.setText(m.toString());
            }
        });
        sous.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if (Integer.parseInt(quantity.getText()) > 1) {
                    ch = Integer.parseInt(quantity.getText()) - 1;
                    quantity.setText(ch.toString());
                    int d = MathUtil.round(s.floatValue() * ch * 100);
                    Integer l = (Integer) d;
                    Double q = l.doubleValue() / 100;
                    totalPrice.setText(q.floatValue() + " " + Storage.getInstance().readObject("currency"));
                    Float t = Float.parseFloat(purchasePrice.getText()) - s.floatValue();
                    Integer o = (Integer) (MathUtil.round(t * 100));
                    Float m = o.floatValue() / 100;
                    purchasePrice.setText(m.toString());

                }

            }
        });

        child.addAll(add, quantity, sous);

        totalPrice.setUIID("Button");

        c.add(child);
        c.add(totalPrice);
        return c;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

}
