package gui;

import Entity.Claim;
import Service.ClaimService;
import Utils.Utils;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.InteractionDialog;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import java.util.List;

public class ClaimForm extends SideMenuBaseForm {

    private int id;
    Style style = UIManager.getInstance().getComponentStyle("Label");

    public ClaimForm(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Claim");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        List<Claim> claims = ClaimService.getByUser();
        final DefaultListModel<String> options = new DefaultListModel<>();
        final Form f = new Form("Claims");

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                GridLayout.encloseIn(2)
        );

        Label l;
        if (claims.isEmpty()) {
            l = new Label("No Claims found", "TodayTitle");

        } else {
            l = new Label("Claims", "TodayTitle");

        }
        l.getStyle().setAlignment(CENTER);
        add(l);

        for (Claim e : claims) {

            add(createContainer(e, res));
            id = e.getId();
        }

        getToolbar().addCommandToRightBar("", FontImage.createMaterial(FontImage.MATERIAL_ADD, style), e -> {
            InteractionDialog dlg = new InteractionDialog("");
            dlg.setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
            Button ok = new Button("OK");
            Button cancel = new Button("Cancel");

            String[] characters = {"Technical Problem", "Commercial Problem"};

            Picker p = new Picker();
            p.setStrings(characters);

            Label typeLabel = new Label("Type", "Container");
            typeLabel.getAllStyles().setAlignment(Component.CENTER);

            Label descLabel = new Label("Description", "Container");
            descLabel.getAllStyles().setAlignment(Component.CENTER);
            TextArea description = new TextArea("", 5, 20, TextArea.ANY);
            ok.addActionListener((evt) -> {
                Claim c = new Claim();
                c.setClient(MyApplication.currentUser);
                c.setDescription(description.getText());
                c.setType(p.getSelectedString());
                ClaimService.add(c);
                dlg.dispose();
                new ClaimForm(UIManager.initFirstTheme("/theme_1")).show();

            });

            setDesign(p.getAllStyles());
            setDesign(description.getAllStyles());

            Container box = BoxLayout.encloseY(
                    typeLabel,
                    p,
                    descLabel,
                    description,
                    GridLayout.encloseIn(2, cancel, ok)
            );

            Container layers = LayeredLayout.encloseIn(box);
            layers.setScrollableY(true);
            Button close = new Button("Close");
            cancel.addActionListener((ee) -> dlg.dispose());

            dlg.addComponent(BorderLayout.CENTER, layers);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.getAllStyles().setBgColor(0xffffff);
            dlg.show(0, 0, 0, 0);
        });

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        //fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        //tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        tb.setTitleComponent(titleCmp);

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

    public Container createContainer(Claim e, Resources res) {

        Label delete = new Label(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE_OUTLINE, style));

        delete.addPointerPressedListener((evt) -> {
            Utils.showConfirm(x -> {
                ClaimService.delete(e);
                new ClaimForm(UIManager.initFirstTheme("/theme_1")).show();

            });
        });
        Label type = new Label("" + e.getType());
        Label description = new Label("" + e.getDescription());
        String date = e.getPostedOn().toString();
        Label sdate = new Label(" " + date);
        if (!e.isAnswered()) {
            Label answer = new Label("" + e.getAnswer());
            Container box = TableLayout.encloseIn(2,
                    new Label("Type"),
                    type,
                    new Label("Description"),
                    description,
                    new Label("Posted On"),
                    sdate, delete);
            Style boxStyle = box.getUnselectedStyle();
            boxStyle.setBgTransparency(255);
            boxStyle.setBgColor(0xff0000);
            boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
            boxStyle.setMargin(1, 1, 1, 1);
            boxStyle.setPadding(1, 1, 1, 1);

            Container layers = LayeredLayout.encloseIn(box);
            return layers;
        } else {
            Label answer = new Label("" + e.getAnswer());

            Container box = TableLayout.encloseIn(2,
                    new Label("Type"),
                    type,
                    new Label("Description"),
                    description,
                    new Label("Posted On"),
                    sdate,
                    new Label("Answer"),
                    answer, delete);
            Style boxStyle = box.getUnselectedStyle();
            boxStyle.setBgTransparency(255);
            boxStyle.setBgColor(0xbeeeef);
            boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
            boxStyle.setMargin(1, 1, 1, 1);
            boxStyle.setPadding(1, 1, 1, 1);
            Container layers = LayeredLayout.encloseIn(box);
            return layers;

        }

    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

    public static void setDesign(Style s) {
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        s.setBorder(Border.createLineBorder(1));
        s.setMarginUnit(Style.UNIT_TYPE_DIPS);
        s.setMargin(Component.BOTTOM, 3);
    }
}
