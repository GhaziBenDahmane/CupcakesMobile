/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Participant;
import Service.ParticipantService;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

/**
 *
 * @author haffe
 */
public class ParticipantList {

    private Form form;
    private Container container;
    protected final AutoCompleteTextField search;
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
    ParticipantService ps = new ParticipantService();

    public ParticipantList(int id) {

        form = new Form("Particiapant List ", new BoxLayout(BoxLayout.Y_AXIS));
        theme = UIManager.initFirstTheme("/theme_1");

        final DefaultListModel<String> options = new DefaultListModel<>();
        final Form f = new Form("Participants");
        final ArrayList<Participant> participants = ps.listParticipant(id);
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

                options.removeAll();
                for (Participant s : l) {
                    options.addItem(s.getUser_id());
                    ids.add(s.getId());
                }
                int i = ids.get(options.getSelectedIndex());
                String s = options.getItemAt(i - 1);
                Participant p = new Participant();
                p.setUser_id(s);
                p.setId(i);
                participants.add(p);
                options.removeAll();
                for (Participant e : participants) {
                    form.add(createContainer(e));
                }
                form.show();
                l = null;
                return false;
            }

        };

        search.setHint("Find user to add", FontImage.createMaterial(FontImage.MATERIAL_SEARCH, style));
        search.setMinimumElementsShownInPopup(4);
        f.getToolbar().addCommandToRightBar("", FontImage.createMaterial(FontImage.MATERIAL_BACKSPACE, style), e -> {
            form.show();
            f.removeAll();
        });
        form.add(search);
        for (Participant e : participants) {
            form.add(createContainer(e));
            id = e.getId();
        }
        form.getToolbar().addCommandToLeftBar("", FontImage.createMaterial(FontImage.MATERIAL_ADD, style), e -> {
        });
        form.show();

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

}
