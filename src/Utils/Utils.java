/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.codename1.io.rest.Rest;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ding
 */
public class Utils {

    static String newValue = null;

    public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }

    public static String uploadProfilePicture(String image) {
        Map result = (Map) Rest.post("https://api.imgur.com/3/upload")
                .header("Authorization", "Client-ID 34c16e93b018fc5")
                .queryParam("image", image)
                .queryParam("type", "base64")
                .getAsJsonMap()
                .getResponseData()
                .get("data");
        return (String) result.get("link");
    }

    public static String uploadProfilePicture(byte[] image) {
        String imageb64 = Base64.encode(image);
        return uploadProfilePicture(imageb64);
    }

    static public Map<String, Object> createListEntry(String name, String date) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("Line1", name);
        entry.put("Line2", date);
        return entry;
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
        dlg.add(GridLayout.encloseIn(2, cancel, ok));

        dlg.showDialog();

    }

    public static void showDialog(String msg) {

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

    public static String requestChange(int type, String defaultValue) {

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
        TextField popupBody = new TextField(defaultValue, "", 20, type);
        popupBody.setUIID("DialogBody");
        popupBody.getAllStyles().setFgColor(0);
        setDesign(popupBody.getAllStyles());

        dlg.add(popupBody);

        Label grayLabel = new Label();
        grayLabel.setShowEvenIfBlank(true);
        grayLabel.getUnselectedStyle().setBgColor(0xcccccc);
        grayLabel.getUnselectedStyle().setPadding(1, 1, 1, 1);
        grayLabel.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_PIXELS);
        dlg.add(grayLabel);

        Button ok = new Button(new Command("OK"));
        Button cancel = new Button(new Command("Cancel"));
        ok.addActionListener(x -> {
            newValue = popupBody.getText();
            dlg.dispose();

        });
        cancel.addActionListener(l -> {
            newValue = null;
            dlg.dispose();
        });
        dlg.add(GridLayout.encloseIn(2, cancel, ok));
        dlg.showDialog();
        return newValue;
    }

    public static String requestChangePassword() {
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
        TextField popupBody = new TextField("", "enter password", 20, TextField.PASSWORD);
        popupBody.setUIID("DialogBody");
        popupBody.getAllStyles().setFgColor(0);
        setDesign(popupBody.getAllStyles());
        TextField popupBody2 = new TextField("", "enter password", 20, TextField.PASSWORD);
        popupBody.setUIID("DialogBody");
        popupBody.getAllStyles().setFgColor(0);
        setDesign(popupBody.getAllStyles());
        setDesign(popupBody2.getAllStyles());

        dlg.add(popupBody);
        dlg.add(popupBody2);

        Label grayLabel = new Label();
        grayLabel.setShowEvenIfBlank(true);
        grayLabel.getUnselectedStyle().setBgColor(0xcccccc);
        grayLabel.getUnselectedStyle().setPadding(1, 1, 1, 1);
        grayLabel.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_PIXELS);
        dlg.add(grayLabel);

        Button ok = new Button(new Command("OK"));
        Button cancel = new Button(new Command("Cancel"));
        ok.addActionListener(x -> {
            if (popupBody.getText() == null ? popupBody2.getText() != null : !popupBody.getText().equals(popupBody2.getText())) {
                Utils.showDialog("Not the same password ");

            } else {

                newValue = popupBody.getText();
                dlg.dispose();
            }

        });
        cancel.addActionListener(l -> {
            newValue = null;
            dlg.dispose();
        });
        dlg.add(GridLayout.encloseIn(2, cancel, ok));
        dlg.showDialog();
        return newValue;
    }

    public static void setDesign(Style s) {
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        s.setBorder(Border.createLineBorder(1));
        s.setMarginUnit(Style.UNIT_TYPE_DIPS);
        s.setMargin(Component.BOTTOM, 3);
    }
}
