package gui;

import Entity.Actuality;
import Service.ActualityService;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;

public class ActualityForm {
 ActualityService as = new ActualityService();
 ArrayList<Actuality> actualities;
 private Container container;

   private Form form;
    
  
    public ActualityForm() {
        //super(BoxLayout.y());
        actualities = as.SelectAllActuality();
        form = new Form("Blog", new BoxLayout(BoxLayout.Y_AXIS));
         final ArrayList<Actuality> actualitie = actualities;
        
       // Toolbar tb = getToolbar();
        final Form f = new Form("Blog");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
      //  menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                GridLayout.encloseIn(2)
        );
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
       // tb.setTitleComponent(titleCmp);

        ActualityService as = new ActualityService();
        for (Actuality p : as.SelectAllActuality()) {

           // add(createContainer(p));

        }

        
         Toolbar.setGlobalToolbar(true);
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...
            Display.getInstance().callSerially(() -> {
                form.removeAll();
                for (Actuality c : actualities) {

                    MultiButton m = new MultiButton();
                    
                    
                    m.setTextLine1("Title : " + c.getTitle());
                   // m.setTextLine2("Image: " + c.getImage());
                    m.setTextLine3("Content: " + c.getContent());
                  
                  
                   
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
                    //String line2 = mb.getTextLine2();
                    String line3 = mb.getTextLine3();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                            //|| line2 != null && line2.toLowerCase().indexOf(text) > -1
                            || line3 != null && line3.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                form.getContentPane().animateLayout(150);
            }
        }, 4);

        form.show()
        ;
        //setupSideMenu(res);
    }

   

   

   
/*
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

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

    Style style = UIManager.getInstance().getComponentStyle("Label");
    private static final String PATH = "http://localhost/picture/blog/";
    Style s = UIManager.getInstance().getComponentStyle("Button");
    FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
    EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 4, p.getHeight() * 5), false);

    public Container createContainer(Actuality a) {
        Label icon = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE, style));
        icon.getAllStyles().setAlignment(Component.LEFT);
/*
        Label more = new Label(FontImage.createMaterial(FontImage.MATERIAL_MORE_HORIZ, style));
        more.getAllStyles().setAlignment(Component.LEFT);
        more.addPointerPressedListener((evt) -> {

            Form list = new Form("Actuality");
            ActualityService as = new ActualityService();
            for (Actuality p : as.SelectAllActuality()) {

                list.add(createContainer(p));

            }
            list.show();
        });
*//*
        Label title = new Label("" + a.getTitle() , "Container");
        title.getAllStyles().setAlignment(Component.CENTER);
        title.getAllStyles().setUnderline(true);

        Label endDate = new Label("" + a.getContent(), "Container");
        endDate.getAllStyles().setAlignment(Component.RIGHT);

        TextArea content = new com.codename1.ui.TextArea();
        content.setText(a.getContent());
        content.setUIID("SlightlySmallerFontLabelLeft");
        content.setName("Text_Area_1");

        System.out.println("i=" + a.getImage() + PATH);
        URLImage i = URLImage.createToStorage(placeholder, a.getImage(),
                PATH + a.getImage());
        Image image = (Image) i;

        icon.setIcon(image);
       Label Delete = new Label(FontImage.createMaterial(FontImage.MATERIAL_REMOVE_CIRCLE, style));

        Delete.addPointerPressedListener((evt) -> {
            
                as.delAct(a);
                new ActualityGUI();
            
            
        });
        
        
//        Button HidePost = new Button("Hide Post");
//     
//        HidePost.setUIID("HidePost");
//       HidePost.addActionListener((evt) -> {
//            as.delAct(a);
//            new ActualityGUI();
//        });
        
        Container box = BoxLayout.encloseY(
                
                icon,
                title,
                content,
                
                GridLayout.encloseIn(2,Delete));
       
        Style boxStyle = box.getUnselectedStyle();
        boxStyle.setBgTransparency(255);
        boxStyle.setBgColor(0xeeeeee);
        boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setMargin(1, 1, 1, 1);
        boxStyle.setPadding(1, 1, 1, 1);
        Container layers = LayeredLayout.encloseIn(box);
        return layers;
    }*/
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
