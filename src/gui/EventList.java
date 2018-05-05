/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Event;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import Service.EventService;
import com.codename1.components.InteractionDialog;
import com.codename1.components.MultiButton;

import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.TableLayout;
import java.util.ArrayList;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Arshavin
 */
public final class EventList {

    private Form form;
    private MultiButton mb;
    private Container container;
    private Resources theme;
    private int id;
    Style s = UIManager.getInstance().getComponentStyle("Button");
    Style style = UIManager.getInstance().getComponentStyle("Label");
    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, s);
    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);
    Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
    Font mediumPlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_MEDIUM);
    Font largePlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);
    Font smallUnderlineMonospaceFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_UNDERLINED, Font.SIZE_SMALL);
    EventService es = new EventService();
com.codename1.ui.util.Resources resourceObjectInstance = com.codename1.ui.util.Resources.getGlobalResources();
    public EventList()  {
        form = new Form("Event List",  new BoxLayout(BoxLayout.Y_AXIS));
        theme = UIManager.initFirstTheme("/theme_1");
        ArrayList<Event> events = es.listEvent();
        final DefaultListModel<String> options = new DefaultListModel<>();
        final Form f = new Form("Events");
        final ArrayList<Event> event = events;
        ArrayList<Integer> ids = new ArrayList<>();
        
        
        

      
       

       

        for (Event e : events) {

            form.add(createContainer(e));
            id = e.getId();
        }
        form.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_ADD, style), e -> {
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
                new EventList();

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
                form.getToolbar().addCommandToRightBar("", FontImage.createMaterial(FontImage.MATERIAL_HOME, style), e -> {
                
                 });
        form.show();
    }

    public Container createContainer(Event e) {

        Label delete = new Label(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE_OUTLINE, style));

        delete.addPointerPressedListener((evt) -> {
            new EventList();
        });

        Label participants = new Label(FontImage.createMaterial(FontImage.MATERIAL_GROUP_ADD, style));
        participants.getAllStyles().setAlignment(Component.RIGHT);
        participants.addPointerPressedListener((evt) -> {
            new ParticipantList(e.getId());
        });
        Label title = new Label("" + e.getTitle(), "Container");
        title.getAllStyles().setAlignment(Component.LEFT);
        Label status = new Label("" + e.getStatus(), "Container");
        status.getAllStyles().setAlignment(Component.LEFT);

        String date = e.getEndDate().toString();
        Label sdate = new Label(" " + date, "Container");
        sdate.getAllStyles().setAlignment(Component.LEFT);

        Label nbPerson = new Label("" + e.getNbPerson(), "Container");
        nbPerson.getAllStyles().setAlignment(Component.LEFT);

        Container box =  TableLayout.encloseIn(2,
                new Label("Title"),
                title,
                new Label("Status"),
                status,
                new Label("Starting Date"),
                sdate,
                new Label("Number of Person"),
                nbPerson,
                TableLayout.encloseIn(2, delete, participants));
        Style boxStyle = box.getUnselectedStyle();
        boxStyle.setBgTransparency(255);
        boxStyle.setBgColor(0xeeeeee);
        boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setMargin(1, 1, 1, 1);
        boxStyle.setPadding(1, 1, 3, 1);

        Container layers = LayeredLayout.encloseIn(box);
        return layers;
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

    public void setDesign(Style s) {
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        s.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        s.setMarginUnit(Style.UNIT_TYPE_DIPS);
        s.setMargin(Component.BOTTOM, 3);
    }
}
