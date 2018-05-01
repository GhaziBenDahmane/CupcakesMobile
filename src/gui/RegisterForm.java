package gui;

import Service.UserService;
import Utils.Utils;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.util.regex.RE;

public class RegisterForm extends Form {

    public RegisterForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Register", "WelcomeBlue")
        );

        getTitleArea().setUIID("Container");
        Image profilePic = theme.getImage("user-picture.jpg");
        Image mask = theme.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        profilePicLabel.setMask(mask.createMask());
        TextField login = new TextField("", "username", 20, TextField.INITIAL_CAPS_WORD);

        TextField email = new TextField("", "email", 20, TextField.EMAILADDR);

        TextField number = new TextField("", "number", 20, TextField.PHONENUMBER);

        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField password2 = new TextField("", "Password", 20, TextField.PASSWORD);

        login.getAllStyles().setMargin(LEFT, 0);
        email.getAllStyles().setMargin(LEFT, 0);
        number.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        password2.getAllStyles().setMargin(LEFT, 0);

        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        Label passwordIcon2 = new Label("", "TextField");
        Label numberIcon = new Label("", "TextField");
        Label mailIcon = new Label("", "TextField");

        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon2.getAllStyles().setMargin(RIGHT, 0);
        numberIcon.getAllStyles().setMargin(RIGHT, 0);
        mailIcon.getAllStyles().setMargin(RIGHT, 0);

        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(numberIcon, FontImage.MATERIAL_PHONE, 3);
        FontImage.setMaterialIcon(mailIcon, FontImage.MATERIAL_MAIL, 3);

        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon2, FontImage.MATERIAL_LOCK_OUTLINE, 3);

        Button createAccountButton = new Button("Register");
        createAccountButton.setUIID("LoginButton");
        createAccountButton.addActionListener((ActionEvent e) -> {
            RE r = new RE("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
            RE intMatcher = new RE("\\d+");
            String username = login.getText();
            String emailAd = email.getText();
            String phoneNumber = number.getText();
            String pass1 = password.getText();
            String pass2 = password2.getText();
            if (username.trim().isEmpty()) {
                Utils.showDialog("Invalid username");
            } else if (emailAd.isEmpty() || !r.match(emailAd)) {
                Utils.showDialog("Invalid email");
            } else if (phoneNumber.isEmpty() || phoneNumber.length() != 8 || !intMatcher.match(phoneNumber)) {
                Utils.showDialog("Invalid phone number");
            } else if (pass1.isEmpty() || !pass1.equals(pass2)) {
                Utils.showDialog("Passwords must match and not be empty");
            } else {
                boolean x = UserService.register(username, emailAd, phoneNumber, pass1);
                if (!x) {
                    Utils.showDialog("Username already in use");

                } else {
                    Toolbar.setGlobalToolbar(false);
                    new WalkthruForm(theme).show();
                    Toolbar.setGlobalToolbar(true);
                }
            }
        });

        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }

        Container by = BoxLayout.encloseY(
                welcome,
                spaceLabel,
                BorderLayout.center(login).
                add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(email).
                add(BorderLayout.WEST, mailIcon),
                BorderLayout.center(number).
                add(BorderLayout.WEST, numberIcon),
                BorderLayout.center(password2).
                add(BorderLayout.WEST, passwordIcon2),
                BorderLayout.center(password).
                add(BorderLayout.WEST, passwordIcon),
                createAccountButton
        );
        add(BorderLayout.CENTER, by);
        Command back = new Command("Back") {
            public void actionPerformed(ActionEvent ev) {
                // notice that when showing a previous form it is best to use showBack() so the
                // transition runs in reverse
                new LoginForm(theme).show();

            }
        };
        this.setBackCommand(back);
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
