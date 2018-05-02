package gui;

import Service.UserService;
import Utils.Utils;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.util.regex.RE;
import com.mycompany.myapp.MyApplication;

public class ProfileForm extends SideMenuBaseForm {

    public static String newUserName;
    public static String newEmail;
    public static String newPhone;

    public ProfileForm(Resources res) {
        super(BoxLayout.y());
        RE intMatcher = new RE("\\d+");
        RE r = new RE("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");

        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic;
        if (MyApplication.userPicture != null) {
            profilePic = MyApplication.userPicture;

        } else {
            profilePic = res.getImage("user-picture.jpg");

        }
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());
        profilePicLabel.addPointerPressedListener((evt) -> {
            UserService.changePicture();
        });
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label(MyApplication.currentUser.getUsername(), "Title"),
                                new Label(MyApplication.currentUser.getEmail(), "SubTitle")
                        )
                ).add(BorderLayout.WEST, profilePicLabel)
        );

        tb.setTitleComponent(titleCmp);

        add(new Label("Today", "TodayTitle"));

        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_EDIT, "Label", 3);

        MultiButton username = new MultiButton("Name:    " + MyApplication.currentUser.getUsername());
        username.setEmblem(arrowDown);
        username.setUIID("Container");
        username.setUIIDLine1("TodayEntry");
        username.setIcon(createCircleLine(0xd997f1, username.getPreferredH(), true));
        username.setIconUIID("Container");
        username.addActionListener(x -> {
            String ret = Utils.requestChange(TextField.ANY, MyApplication.currentUser.getUsername());
            if (ret == null) {
            } else if (ret.isEmpty() || ret.trim().length() < 2) {
                Utils.showDialog("Invalid Username");
            } else {
                MyApplication.currentUser.setUsername(ret);
                UserService.changeUserName(MyApplication.currentUser, ret);
                new ProfileForm(UIManager.initFirstTheme("/theme_1")).show();
            }
        });
        add(FlowLayout.encloseIn(username));

        MultiButton email = new MultiButton("Email:    " + MyApplication.currentUser.getEmail());
        email.setEmblem(arrowDown);
        email.setUIID("Container");
        email.setUIIDLine1("TodayEntry");
        email.setIcon(createCircleLine(0x5ae29d, email.getPreferredH(), false));
        email.setIconUIID("Container");
        email.addActionListener(x -> {
            String ret = Utils.requestChange(TextField.EMAILADDR, MyApplication.currentUser.getEmail());
            if (ret == null) {
            } else if (ret.isEmpty() || !r.match(ret)) {
                Utils.showDialog("Invalid Email");
            } else {
                MyApplication.currentUser.setEmail(ret);
                UserService.changeEmail(MyApplication.currentUser, ret);
                new ProfileForm(UIManager.initFirstTheme("/theme_1")).show();

            }
        });
        add(FlowLayout.encloseIn(email));

        MultiButton phone = new MultiButton("Phone:    " + MyApplication.currentUser.getPhone());
        phone.setEmblem(arrowDown);
        phone.setUIID("Container");
        phone.setUIIDLine1("TodayEntry");
        phone.setIcon(createCircleLine(0xffc06f, phone.getPreferredH(), false));
        phone.setIconUIID("Container");
        phone.addActionListener(x -> {
            String ret = Utils.requestChange(TextField.NUMERIC, MyApplication.currentUser.getPhone() != null ? "" + MyApplication.currentUser.getPhone() : "");
            if (ret == null) {
            } else if (ret.isEmpty() || ret.length() != 8 || !intMatcher.match(ret)) {
                Utils.showDialog("Invalid phone number");
            } else {
                MyApplication.currentUser.setPhone(ret);
                UserService.changePhone(MyApplication.currentUser, ret);
                new ProfileForm(UIManager.initFirstTheme("/theme_1")).show();

            }

        });
        add(FlowLayout.encloseIn(phone));

        MultiButton photo = new MultiButton("Change Picture");
        photo.setEmblem(arrowDown);
        photo.setUIID("Container");
        photo.setUIIDLine1("TodayEntry");
        photo.setIcon(createCircleLine(0xd997f1, photo.getPreferredH(), false));
        photo.setIconUIID("Container");
        photo.addActionListener(x -> {
            if (UserService.changePicture()) {
                new ProfileForm(UIManager.initFirstTheme("/theme_1")).show();

            }
        });
        add(FlowLayout.encloseIn(photo));

        MultiButton password = new MultiButton("Change Password");
        password.setEmblem(arrowDown);
        password.setUIID("Container");
        password.setUIIDLine1("TodayEntry");
        password.setIcon(createCircleLine(0xd997f1, photo.getPreferredH(), false));
        password.setIconUIID("Container");
        password.addActionListener(x -> {
            String ret = Utils.requestChangePassword();
            if (ret == null) {
            } else if (ret.isEmpty()) {
                Utils.showDialog("Invalid Password");
            } else {
                UserService.changePassword(MyApplication.currentUser, ret);
            }

        });

        add(FlowLayout.encloseIn(password));

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

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}
