package com.mycompany.myapp;

import Service.CurrencyConvertService;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.ui.Display;
import com.codename1.ui.Toolbar;
import com.codename1.util.EasyThread;
import gui.CurrencySettingsGUI;
import java.io.IOException;
import gui.ProductGUI;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
public class MyApplication {

    private Form current;
    private Resources theme;
    public static Double k;

    public Resources getTheme() {
        return theme;
    }

    public void setTheme(Resources theme) {
        this.theme = theme;
    }

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if (err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        Storage.getInstance().writeObject("currency", "dinar");

        /*  ProductGUI p;
         try {
         p = new ProductGUI();
         p.getForm().show();
         } catch (IOException ex) {
         System.out.println("log" + ex.getMessage());
         }*/
        CurrencySettingsGUI ccs = new CurrencySettingsGUI();
        ccs.getForm().show();

    }

    public void stop() {
        current = getCurrentForm();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }

}