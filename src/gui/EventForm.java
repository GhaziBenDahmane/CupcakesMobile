package gui;

import Entity.Event;
import Service.ControleSaisie;
import Service.EventService;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.InteractionDialog;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventForm extends SideMenuBaseForm {

    EventService es = new EventService();
    private int id;
    Style style = UIManager.getInstance().getComponentStyle("Label");
    private Resources theme;
    ControleSaisie cs = new ControleSaisie();

    public EventForm(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitle("Event");
        theme = res;

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        ArrayList<Event> events = es.listEvent();
        
        
        Container titleCmp = BoxLayout.encloseY(
                BoxLayout.encloseY(
                        new Label("Event List", "Title")
                ),
                FlowLayout.encloseIn(menuButton)
        );

        for (Event e : events) {

            add(createContainer(e));
            id = e.getId();
        }

        getToolbar().addCommandToRightBar("", FontImage.createMaterial(FontImage.MATERIAL_ADD, style), e -> {
            InteractionDialog dlg = new InteractionDialog("");
            dlg.setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
            Button ok = new Button("OK");

            Button cancel = new Button("Cancel");

            Label titleLabel = new Label("Title", "Container");
            titleLabel.getAllStyles().setAlignment(Component.CENTER);

            Label nbPersonLabel = new Label("Number of Person", "Container");
            nbPersonLabel.getAllStyles().setAlignment(Component.CENTER);

            Label sDateLabel = new Label("Starting Date", "Container");
            nbPersonLabel.getAllStyles().setAlignment(Component.CENTER);

            Label nbTableLabel = new Label("Number of table", "Container");
            nbTableLabel.getAllStyles().setAlignment(Component.CENTER);

            TextField title = new TextField("", "Title", 20, TextArea.ANY);
            TextField nbPerson = new TextField("", "Number of Person", 20, TextArea.ANY);

            Picker sDate = new Picker();
            sDate.setType(Display.PICKER_TYPE_DATE);
            TextField nbTable = new TextField("", "Number of table", 20, TextArea.ANY);
            ok.addActionListener((evt) -> {

                if (title.getText().equals("") || nbPerson.getText().equals("") || nbTable.getText().equals("")) {

                    showDialog("Please enter the following input");

                } else if (!cs.isNumber(nbPerson.getText())) {

                    showDialog("Number of person must be between 1 and 99");
                } else if (!cs.isNumber(nbTable.getText())) {
                    showDialog("Number of table must be between 1 and 99");
 
                } else if (!cs.isValidDate(sDate.getDate())) {
                    showDialog("Please enter correct date");

                } else {
                    es.addEvent(new Event(id + 1,
                            title.getText(),
                            Integer.parseInt(nbPerson.getText()),
                            sDate.getDate(),
                            sDate.getDate(),
                            Integer.parseInt(nbTable.getText()),
                            Integer.parseInt(nbTable.getText()),
                            "Pending",
                            0.0)
                    );
                    dlg.dispose();
                }
            });

            setDesign(title.getAllStyles());
            setDesign(nbPerson.getAllStyles());
            setDesign(sDate.getAllStyles());
            setDesign(nbTable.getAllStyles());

            Container box = BoxLayout.encloseY(
                    titleLabel,
                    title,
                    nbPersonLabel,
                    nbPerson,
                    sDateLabel,
                    sDate,
                    nbTableLabel,
                    nbTable,
                    GridLayout.encloseIn(2, cancel, ok));

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

        tb.setTitleComponent(titleCmp);

        setupSideMenu(theme);
    }

    public Container createContainer(Event e) {

        Label delete = new Label(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE_OUTLINE, style));

        delete.addPointerPressedListener((evt) -> {
            
           
            
            showConfirm((ex) -> {
                 es.delEvent(e);
            new EventForm(theme).show();
            });
            
        });

        Label participants = new Label(FontImage.createMaterial(FontImage.MATERIAL_GROUP_ADD, style));
        participants.getAllStyles().setAlignment(Component.RIGHT);
        participants.addPointerPressedListener((evt) -> {
            new ParticipantForm(theme, e).show();

        });
        participants.getStyle().setAlignment(RIGHT);
        Label title = new Label("" + e.getTitle());
        Label status = new Label("" + e.getStatus());

        String date = new SimpleDateFormat("yyyy-MM-dd").format(e.getEndDate());
        Label sdate = new Label(" " + date);

        Label nbPerson = new Label("" + e.getNbPerson());
        Container box = TableLayout.encloseIn(2,
                new Label("Title"),
                title,
                new Label("Status"),
                status,
                new Label("Starting Date"),
                sdate,
                new Label("Number of Places"),
                nbPerson,
                GridLayout.encloseIn(2, delete, participants));
        Style boxStyle = box.getUnselectedStyle();
        boxStyle.setBgTransparency(255);
        boxStyle.setBgColor(0xC3BEBE);
        boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setMargin(1, 1, 1, 1);
        boxStyle.setPadding(1, 1, 1, 1);

        Container layers = LayeredLayout.encloseIn(box);
        return layers;
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

    public final void setDesign(Style s) {
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        s.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xD0D0D0).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        s.setMarginUnit(Style.UNIT_TYPE_DIPS);
        s.setMargin(Component.BOTTOM, 3);
    }

    public void showDialog(String msg) {

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
        TextArea ta = new TextArea(msg);
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

        Button ok = new Button(new Command("OK"));

        dlg.add(ok);
        dlg.showDialog();

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
        TextArea ta = new TextArea("Are you sure?");
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
        dlg.add(ok);
        dlg.add(cancel);
        dlg.showDialog();

    }
}
