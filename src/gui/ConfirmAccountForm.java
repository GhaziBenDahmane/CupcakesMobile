package gui;

import Service.UserService;
import Utils.Utils;
import com.codename1.io.rest.Rest;
import com.codename1.sms.intercept.SMSInterceptor;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.util.Base64;
import com.mycompany.myapp.MyApplication;
import java.util.Map;
import java.util.Random;

public class ConfirmAccountForm extends Form {

    public ConfirmAccountForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Enter the received code!", "WelcomeBlue")
        );
        getTitleArea().setUIID("Container");

        TextField number = new TextField("", "number", 20, TextField.NUMERIC);

        number.getAllStyles().setMargin(LEFT, 0);

        Label numberIcon = new Label("", "TextField");

        numberIcon.getAllStyles().setMargin(RIGHT, 0);

        FontImage.setMaterialIcon(numberIcon, FontImage.MATERIAL_PHONE, 3);

        Button confirmButton = new Button("Confirm");
        confirmButton.setUIID("LoginButton");

        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }

        Container by = BoxLayout.encloseY(
                welcome,
                spaceLabel,
                BorderLayout.center(number).
                add(BorderLayout.WEST, numberIcon),
                confirmButton
        );
        add(BorderLayout.CENTER, by);
        Command back = new Command("Back") {
            public void actionPerformed(ActionEvent ev) {
                // notice that when showing a previous form it is best to use showBack() so the
                // transition runs in reverse

            }
        };
        this.setBackCommand(back);
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);

        Random r = new Random();
        String val = "" + r.nextInt(10000);
        String accountSID = "AC1c0bd1ebca8e2ecd2ea5ddc919e950a0";
        String authToken = "65063fd6f5f0b228ff625955eb0fb4ca";
        String fromPhone = "+19283230909";
        while (val.length() < 4) {
            val = "0" + val;
        }
        UserService.confirmation = val;
        Map result = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
                queryParam("To", "+216" + MyApplication.currentUser.getPhone()).
                queryParam("From", fromPhone).
                queryParam("Body", val).
                header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
                getAsJsonMap().getResponseData();
        confirmButton.addActionListener((ActionEvent e) -> {

            if (number.getText() == null ? UserService.confirmation == null : number.getText().equals(UserService.confirmation)) {
                Toolbar.setGlobalToolbar(false);
                new WalkthruForm(theme).show();
                Toolbar.setGlobalToolbar(true);
            } else {
                Utils.showDialog("Invalid Code!");
            }

        });
        SMSInterceptor.grabNextSMS(e -> {
            System.out.println(UserService.confirmation);
            number.setText(UserService.confirmation);

        });

    }
}
