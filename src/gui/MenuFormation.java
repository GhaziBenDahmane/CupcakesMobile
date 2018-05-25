package gui;

import Entity.Formation;
import Service.FormationService;
import com.codename1.components.MultiButton;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.text.SimpleDateFormat;

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
        tb.setTitle("Trainings");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        getStyle().setBgColor(0xE8DCB5);
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                GridLayout.encloseIn(2)
        );

        FormationService ps = new FormationService();
        for (Formation f : ps.getList2()) {
            MultiButton m = new MultiButton();
            m.setTextLine1("");
            m.setTextLine2(f.getNom() + " training");
            m.setTextLine3(new SimpleDateFormat("yyyy-MM-dd").format(f.getStart_date_formation()) + " ~ "
                    + new SimpleDateFormat("yyyy-MM-dd").format(f.getEnd_date_formation()));
            add(m);
            m.addActionListener(
                    l -> {
                        showConfirm(p -> {
                            FormationService.Supprimer(f.getId());
                        });
                    }
            );
        }

        tb.setTitleComponent(titleCmp);
        setupSideMenu(res);

    }

    public static void showConfirm(ActionListener successCallBack) {

        Dialog dlg = new Dialog("");
        Style dlgStyle = dlg.getDialogStyle();
        dlgStyle.setBorder(Border.createEmpty());
        dlgStyle.setBgTransparency(255);
        dlgStyle.setBgColor(0xffffff);

        dlg.setLayout(BoxLayout.y());
        Label blueLabel = new Label();
        blueLabel.setShowEvenIfBlank(true);
        blueLabel.getUnselectedStyle().setBgColor(0xff);
        blueLabel.getUnselectedStyle().setPadding(1, 1, 1, 1);
        blueLabel.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_PIXELS);
        dlg.add(blueLabel);
        TextArea ta = new TextArea("Are you sure you want to remove this training?");
        ta.setEditable(false);
        ta.setUIID("DialogBody");
        ta.getAllStyles().setFgColor(0);
        dlg.add(ta);

        Label grayLabel = new Label();
        grayLabel.setShowEvenIfBlank(true);
        grayLabel.getUnselectedStyle().setBgColor(0xcccccc);
        grayLabel.getUnselectedStyle().setPadding(1, 1, 1, 1);
        grayLabel.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_PIXELS);
        dlg.add(grayLabel);

        Button ok = new Button(new Command("Confirm"));
        Button cancel = new Button(new Command("Cancel"));
        cancel.addActionListener(l -> {
            dlg.dispose();
        });

        ok.addActionListener(successCallBack);
        dlg.add(GridLayout.encloseIn(2, cancel, ok));

        dlg.showDialog();

    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}
