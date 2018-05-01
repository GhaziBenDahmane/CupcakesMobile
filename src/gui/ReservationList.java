/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Reservation;
import Service.ControleSaisie;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import Service.ReservationService;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.InteractionDialog;
import com.codename1.components.MultiButton;
import static com.codename1.io.rest.Rest.delete;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
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
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 */
public final class ReservationList {

    private Form form;
    private MultiButton mb;
    private Container container;

    private Resources theme_1;
    private int id;
    Style s = UIManager.getInstance().getComponentStyle("Button");
    Style style = UIManager.getInstance().getComponentStyle("Label");
    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, s);
    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);
    Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
    Font mediumPlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_ITALIC, Font.SIZE_MEDIUM);
    Font largePlainMonospaceFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);
    Font smallUnderlineMonospaceFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_UNDERLINED, Font.SIZE_SMALL);
    ArrayList<Reservation> reservations;
    Dialog dlg1 = new Dialog("Error");
    ReservationService rs = new ReservationService();
    final DefaultListModel<String> options = new DefaultListModel<>();

    public ReservationList()  {
        form = new Form("Reservation List", new BoxLayout(BoxLayout.Y_AXIS));
        theme_1 = UIManager.initFirstTheme("/theme_1");
        reservations = rs.listReservation();

        final Form f = new Form("Reservations");
        final ArrayList<Reservation> reservation = reservations;
        ArrayList<Integer> ids = new ArrayList<>();

        Form hi = new Form("Search", BoxLayout.y());
        // form.add(new InfiniteProgress());
        Toolbar.setGlobalToolbar(true);
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...
            Display.getInstance().callSerially(() -> {
                form.removeAll();
                for (Reservation c : reservations) {

                    MultiButton m = new MultiButton();
                    Date oldstring = c.getDateReservation();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String curDate = dateFormat.format(oldstring);
                    System.out.println("cc" + curDate);
                    m.setTextLine1("Date: " + curDate.toString());
                    m.setTextLine2("Number of persons: " + Integer.toString(c.getNbPerson()));
                    m.setTextLine3("Number of table: " + Integer.toString(c.getNbTable()));
                  
                        FontImage i = FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, "Label", 3);
                    Label delete = new Label(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, style));
                    
                    delete.addPointerPressedListener((evt) -> {
                        rs.delReservation(c);
                        new ReservationList();
                    });
                    
                    m.setEmblem(i);
                    form.add(m);
                }

                form.revalidate();
            });
        });

        form.getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : form.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                form.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : form.getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    String line3 = mb.getTextLine3();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                            || line2 != null && line2.toLowerCase().indexOf(text) > -1
                            || line3 != null && line3.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                form.getContentPane().animateLayout(150);
            }
        }, 4);

        form.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_ADD, style), e -> {
            InteractionDialog dlg = new InteractionDialog("");
            dlg.setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
            Button ok = new Button("OK");

            Button cancel = new Button("Cancel");

            Label nbPersonLabel = new Label("Number of Person", "Container");
            nbPersonLabel.getAllStyles().setAlignment(Component.CENTER);

            Label dateReservationLabel = new Label("Reservation Date", "Container");
            dateReservationLabel.getAllStyles().setAlignment(Component.CENTER);

            Label nbTableLabel = new Label("Number of table", "Container");
            nbTableLabel.getAllStyles().setAlignment(Component.CENTER);

            TextField nbPerson = new TextField("", "Number of Person", 20, TextArea.ANY);
            Picker sDate = new Picker();
            sDate.setType(Display.PICKER_TYPE_DATE);
            TextField nbTable = new TextField("", "Number of table", 20, TextArea.ANY);
            ControleSaisie s = new ControleSaisie();

            ok.addActionListener((evt) -> {

                if (!s.isNumberTable(nbTable.getText())) {

                    Label title = dlg1.getTitleComponent();
                    dlg1.setLayout(BoxLayout.y());
                    Label blueLabel = new Label();
                    dlg1.add(blueLabel);
                    TextArea ta = new TextArea("You taped a wrong number of table (We only have 15 Tables) ...");
                    ta.setEditable(false);
                    ta.setUIID("DialogBody");
                    ta.getAllStyles().setFgColor(0);
                    dlg1.add(ta);
                    Label grayLabel = new Label();
                    dlg1.add(grayLabel);
                    Button ok1 = new Button(new Command("OK"));
                    dlg1.add(ok1);
                    dlg1.showDialog();
                    dlg1.dispose();
                   

                } else if (!s.isNumberPerson(nbPerson.getText())) {

                    Label title = dlg1.getTitleComponent();
                    dlg1.setLayout(BoxLayout.y());
                    Label blueLabel = new Label();
                    dlg1.add(blueLabel);
                    TextArea ta = new TextArea("You taped a wrong number of persons (You can only have 4 persons in one table) ...");
                    ta.setEditable(false);
                    ta.setUIID("DialogBody");
                    ta.getAllStyles().setFgColor(0);
                    dlg1.add(ta);
                    Label grayLabel = new Label();
                    dlg1.add(grayLabel);
                    Button ok1 = new Button(new Command("OK"));
                    dlg1.add(ok1);
                    dlg1.showDialog();
                    dlg1.dispose();
                  

                } 
              
              else if (!s.isValidDate(sDate.getDate())) {
                    
                    Label title = dlg1.getTitleComponent();
                    dlg1.setLayout(BoxLayout.y());
                    Label blueLabel = new Label();
                    dlg1.add(blueLabel);
                    TextArea ta = new TextArea("You taped a wrong date) ...");
                    ta.setEditable(false);
                    ta.setUIID("DialogBody");
                    ta.getAllStyles().setFgColor(0);
                    dlg1.add(ta);
                    Label grayLabel = new Label();
                    dlg1.add(grayLabel);
                    Button ok1 = new Button(new Command("OK"));
                    dlg1.add(ok1);
                    dlg1.showDialog();
                    dlg1.dispose();
               
               }
               
              else if (s.isValidDate2(sDate.getDate())) {
                    
                    Label title = dlg1.getTitleComponent();
                    dlg1.setLayout(BoxLayout.y());
                    Label blueLabel = new Label();
                    dlg1.add(blueLabel);
                    TextArea ta = new TextArea("You can't reserve a table before more than one month ) ...");
                    ta.setEditable(false);
                    ta.setUIID("DialogBody");
                    ta.getAllStyles().setFgColor(0);
                    dlg1.add(ta);
                    Label grayLabel = new Label();
                    dlg1.add(grayLabel);
                    Button ok1 = new Button(new Command("OK"));
                    dlg1.add(ok1);
                    dlg1.showDialog();
                    dlg1.dispose();
               
               }
           
                
                else {
                    rs.addReservation(new Reservation(id + 1,
                            Integer.parseInt(nbPerson.getText()),
                            Integer.parseInt(nbTable.getText()),
                            sDate.getDate()
                    )
                    );

                    dlg.dispose();
                }
                new ReservationList();

            });

            setDesign(nbPerson.getAllStyles());
            setDesign(sDate.getAllStyles());
            setDesign(nbTable.getAllStyles());

            Container box = BoxLayout.encloseY(
                    nbPersonLabel,
                    nbPerson,
                    dateReservationLabel,
                    sDate,
                    nbTableLabel,
                    nbTable,
                    GridLayout.encloseIn(2, cancel, ok));

            Container layers = LayeredLayout.encloseIn(box);

            Button close = new Button("Close");
            cancel.addActionListener((ee) -> dlg.dispose());

            dlg.addComponent(BorderLayout.CENTER, layers);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.getAllStyles().setBgColor(0xffffff);
            dlg.show(0, 0, 0, 0);
        });
        form.show();
    }

    public Container createContainer(Reservation e) {

        Label delete = new Label(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, style));

        delete.addPointerPressedListener((evt) -> {
            rs.delReservation(e);
            new ReservationList();
        });

        Label nbTable = new Label("Number of table = " + e.getNbTable(), "Container");
        nbTable.getAllStyles().setAlignment(Component.LEFT);

        String date = e.getDateReservation().toString();
        Label sdate = new Label("Date = " + date, "Container");
        sdate.getAllStyles().setAlignment(Component.LEFT);

        Label nbPerson = new Label("Number Of Person = " + e.getNbPerson(), "Container");
        nbPerson.getAllStyles().setAlignment(Component.LEFT);

        Container box = BoxLayout.encloseY(
                nbTable,
                sdate,
                nbPerson,
                GridLayout.encloseIn(2, delete));
        Style boxStyle = box.getUnselectedStyle();
        boxStyle.setBgTransparency(255);
        boxStyle.setBgColor(0xeeeeee);
        boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setMargin(1, 1, 1, 1);
        boxStyle.setPadding(1, 1, 1, 1);

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
                color(0xBBBAB9).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        s.setMarginUnit(Style.UNIT_TYPE_DIPS);
        s.setMargin(Component.BOTTOM, 3);
    }


}
