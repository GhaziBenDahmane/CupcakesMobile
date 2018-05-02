package gui;

import Entity.Event;
import Entity.Participant;
import Service.EventService;
import Service.ParticipantService;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ParticipantForm extends SideMenuBaseForm {

    EventService es = new EventService();
    private int id;
    Style style = UIManager.getInstance().getComponentStyle("Label");
    protected final AutoCompleteTextField search;
    ParticipantService ps = new ParticipantService();
    Style s = UIManager.getInstance().getComponentStyle("Title");

    public ParticipantForm(Resources res, Event event) {
        super(BoxLayout.y());
        removeAll();
        Toolbar tb = getToolbar();
        tb.setTitle("Participant");
        this.id = 1;
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        final DefaultListModel<String> options = new DefaultListModel<>();
        ArrayList<Participant> participants = ps.listParticipant(id);
        ArrayList<Participant> users = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();

        search = new AutoCompleteTextField(options) {
            @Override
            protected boolean filter(String text) {
                if (text.length() == 0) {
                    return false;
                }
                ArrayList<Participant> l = ps.searchParticipant(text);

                if (l == null || l.isEmpty()) {
                    return false;
                }
                removeAll();
                add(new Label("Add friends"));
                for (Participant s : l) {
                    FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_ADD, "Label", 3);
                    Label add = new Label(arrowDown);
                    add.setAlignment(RIGHT);
                    add.addPointerPressedListener((evt) -> {
                       ps.addParticipant(event,s.getId());
                    });
                    MultiButton a = addButton(s.getUser_id(), 0xd997f1, true);
                    Container box = GridLayout.encloseIn(2,
                            a,
                            add
                    );
                    Style boxStyle = box.getUnselectedStyle();
                    boxStyle.setBgTransparency(255);
                    boxStyle.setBgColor(0xeeeeee);
                    boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
                    boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
                    boxStyle.setMargin(1, 1, 1, 1);
                    boxStyle.setPadding(1, 1, 3, 1);

                    Container layers = LayeredLayout.encloseIn(box);
                    add(layers);
                    
                }
                add(new Label("Particiapant List"));
                for (Participant e : participants) {
                    add(createContainer(e));
                }
                show();
                l = null;
                return false;
            }

        };
        search.setHint("Find user to add", FontImage.createMaterial(FontImage.MATERIAL_SEARCH, style));
        search.setMinimumElementsShownInPopup(0);
        add(new Label("Particiapant List"));
        for (Participant e : participants) {
            add(createContainer(e));
            id = e.getId();
        }
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                search,
                GridLayout.encloseIn(2)
        );
        tb.setTitleComponent(titleCmp);
        setupSideMenu(res);
    }

    @Override

    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
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

    public Container createContainer(Participant e) {

        Label title = new Label(e.getUser_id(), "Container");
        title.getAllStyles().setAlignment(Component.LEFT);

        Container box = BoxLayout.encloseY(
                title,
                GridLayout.encloseIn(1));
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

    private void addButtonBottom(String text, int color, boolean first) {
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(), first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }

    private MultiButton addButton(String text, int color, boolean first) {
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(), first));
        finishLandingPage.setIconUIID("Container");
        return finishLandingPage;
    }

    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if (first) {
            y = height / 12 + 1;
        }

        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }
}
