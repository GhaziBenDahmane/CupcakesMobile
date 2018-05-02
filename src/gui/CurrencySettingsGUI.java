/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Service.CurrencyConvertService;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.FocusListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.io.IOException;

/**
 *
 * @author Arshavin
 */
public class CurrencySettingsGUI {

    private final CheckBox dinar, euro, dollar, pound;
    private final Label label;
    private final Button save;
    private Form form;
    private final Container container;
    private static String def = "TND";
    private final Style style = UIManager.getInstance().getComponentStyle("Label");

    public CurrencySettingsGUI() {

        Label test = new Label();
        form = new Form("Settings");
        container = new Container(BoxLayout.y());
        save = new Button("Save");
        label = new Label("Select your currency :");
        dinar = new CheckBox("Dinar");
        euro = new CheckBox("Euro");
        dollar = new CheckBox("Dollar");
        pound = new CheckBox("Pound");
        dinar.setSelected(true);
        euro.setSelected(false);
        dollar.setSelected(false);
        pound.setSelected(false);
        clearCheckBoxs();

        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Storage.getInstance().writeObject("currency", saveChoice());
                System.out.println(Storage.getInstance().readObject("currency"));
                Dialog ip = new InfiniteProgress().showInifiniteBlocking();
                ip.setTimeout(10000);

                CurrencyConvertService ccs = new CurrencyConvertService();
                
                Storage.getInstance().writeObject("price", ccs.ConvertPrice());
                ip.dispose();
               // test.setText(Storage.getInstance().readObject("price").toString());

            }
        });
        form.getToolbar().addCommandToRightBar("", FontImage.createMaterial(FontImage.MATERIAL_BACKSPACE, style), e -> {
            try {
                ProductGUI p = new ProductGUI();
                p.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        });

        container.add(label).add(dinar).add(euro).add(dollar).add(pound).add(save).add(test);
        form.add(container);

    }

    public final void clearCheckBoxs() {

        dinar.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(Component cmp) {
                euro.setSelected(false);
                dollar.setSelected(false);
                pound.setSelected(false);
            }

            @Override
            public void focusLost(Component cmp) {
            }
        });
        euro.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(Component cmp) {
                dinar.setSelected(false);
                dollar.setSelected(false);
                pound.setSelected(false);
            }

            @Override
            public void focusLost(Component cmp) {
            }
        });
        dollar.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(Component cmp) {
                dinar.setSelected(false);
                euro.setSelected(false);
                pound.setSelected(false);
            }

            @Override
            public void focusLost(Component cmp) {
            }
        });
        pound.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(Component cmp) {
                dinar.setSelected(false);
                dollar.setSelected(false);
                euro.setSelected(false);
            }

            @Override
            public void focusLost(Component cmp) {
            }
        });

    }

    public String saveChoice() {
        if (dinar.isSelected()) {
            def = "TND";
        } else if (euro.isSelected()) {
            def = "EUR";
        } else if (dollar.isSelected()) {
            def = "USD";
        } else {
            def = "GBP";
        }

        return def;

    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

}
