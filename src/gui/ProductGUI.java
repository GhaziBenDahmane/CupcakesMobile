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
import com.codename1.ui.layouts.BorderLayout;
import Entity.Product;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.List;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Arshavin
 */
public class ProductGUI {

    private Label type, name, price, description;
    private ImageViewer imageView;

    private Form form;
    private MultiButton mb;
    private Container container;
    private Image image, ii;

    private static final String PATH = "http://localhost/picture/";
    Style s = UIManager.getInstance().getComponentStyle("Button");
    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);
    Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
    Font mediumPlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_MEDIUM);
    Font largePlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);
    Font smallUnderlineMonospaceFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_UNDERLINED, Font.SIZE_SMALL);

    public ProductGUI() {
        List list = new List(createGenericListCellRendererModelData());
        list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(), createGenericRendererContainer()));
        form = new Form("ProductList", new BorderLayout());
        form.add(BorderLayout.CENTER, list);
    }

    private Container createGenericRendererContainer() {
        name = new Label();
        name.setName("name");

        type = new Label();
        type.setName("type");

        description = new Label();
        description.setName("description");

        price = new Label();
        price.setName("price");

        //imageView = new ImageViewer();
        //imageView.setName("image");
        mb = new MultiButton();
        mb.setIconName("image");
        mb.setIcon(placeholder);

        //imageView.setImage(image);
        Container container1 = new Container(BoxLayout.y());

        container1.add(name);
        container1.add(type);
        container1.add(description);
        container1.add(price);

        container = new Container(BoxLayout.x());
        container.add(mb);
        container.add(container1);
        container.setUIID("ListRenderer");

        name.getAllStyles().setFgColor(0xf7786b);
        type.getAllStyles().setFont(largePlainMonospaceFont);
        type.getAllStyles().setFont(mediumPlainMonospaceFont);
        type.getAllStyles().setFgColor(0x000000);
        description.getAllStyles().setFont(smallPlainSystemFont);
        description.getAllStyles().setFgColor(0xeaece5);
        price.getAllStyles().setFont(smallUnderlineMonospaceFont);
        price.getAllStyles().setFgColor(0xb2b2b2);
        container.getSelectedStyle().setBgColor(0xffffffff);

        return container;
    }

    private Object[] createGenericListCellRendererModelData() {
        ArrayList<Product> products = new ArrayList<>();
        ProductService ps = new ProductService();
        products = ps.SelectAllProducts();
        Map<String, Object>[] data = new HashMap[products.size()];

        for (int i = 0; i < products.size(); i++) {
            data[i] = new HashMap<>();

            URLImage im = URLImage.createToStorage(placeholder, products.get(i).getImage(),
                    PATH + products.get(i).getImage());
            image = (Image) im;
            data[i].put("name", products.get(i).getName());
            data[i].put("type", products.get(i).getType());
            data[i].put("description", products.get(i).getDescription());
            data[i].put("price", products.get(i).getPrice());
            data[i].put("image", image);

        }

        return data;
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

}
