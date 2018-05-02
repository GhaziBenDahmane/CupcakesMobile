package gui;

import Service.ServiceFormation;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

/**
 *
 * @author sana
 */
public class MenuFormation extends SideMenuBaseForm {

    Button btnaff, btnDelete;
    Button btn;
    Button Email;

    TextField idSupp;

    public MenuFormation(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Menu Formation");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        getStyle().setBgColor(0xE8DCB5);
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                GridLayout.encloseIn(2)
        );
        btnaff = new Button("Affichage");
        idSupp = new TextField();
        btn = new Button("send sms");
        btnDelete = new Button("Supprimer");
        Email = new Button("Email");

        add(btnaff);
        add(btn);
        add(idSupp);
        add(btnDelete);
        add(Email);
        btnaff.addActionListener((e) -> {
            new Affichage(UIManager.initFirstTheme("/theme_1")).show();
        });
        btnDelete.addActionListener((e) -> {
            ServiceFormation ser = new ServiceFormation();
            String a = idSupp.getText();
            int id = Integer.parseInt(a);
            if (id > 1 && id < 20) {
                ser.Supprimer(id);
                Dialog.show("Ok", "Succees", "Ok", null);
            } else {
                Dialog.show("Erreur", "Echec", "Ok", null);

            }

        });

        Email.addActionListener((e) -> {
            Message m = new Message("");
            Display.getInstance().sendMessage(new String[]{""}, "test", m);
        });
        tb.setTitleComponent(titleCmp);
        setupSideMenu(res);

    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}
