package com.mycompany.myapp;

import Entity.User;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import gui.LoginForm;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
public class MyApplication {

    public static User currentUser = null;
    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme_1");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);
        Toolbar.setCenteredDefault(false);
        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        new LoginForm(theme).show();
        //  new EventForm(theme).show();
        /*           try {
            FavouriteService.db.delete("Cupcake");
        } catch (IOException ex) {
        }
        Storage.getInstance().writeObject("currency", "TND");
        FavouriteService fs = new FavouriteService();
       
        fs.createSQLiteDB();
        Promotion p = new Promotion();
        p.setDiscount(0.1);
        p.setId_promotion(1);
        fs.insertPromotionInSQLiteDB(p);
        fs.closeDB();

        ProductGUI pp;
        try {
            pp = new ProductGUI();
            pp.getForm().show();
        } catch (IOException ex) {
            System.out.println("log" + ex.getMessage());
        }*/

    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }

    public void destroy() {
    }

}
