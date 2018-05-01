/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.codename1.io.rest.Rest;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
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
        dlg.add(ok);
        dlg.add(cancel);
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
}
