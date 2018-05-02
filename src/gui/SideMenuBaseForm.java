package gui;

import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;

public abstract class SideMenuBaseForm extends Form {

    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(Resources res) {
        Image profilePic;
        if (MyApplication.userPicture != null) {
            profilePic = MyApplication.userPicture;

        } else {
            profilePic = res.getImage("user-picture.jpg");

        }
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("  " + MyApplication.currentUser.getUsername(), profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());
        profilePicLabel.addPointerPressedListener((evt) -> {
            new ProfileForm(res).show();
        });
        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");

        getToolbar().addComponentToSideMenu(sidemenuTop);

        getToolbar().addMaterialCommandToSideMenu("  Actualities", FontImage.MATERIAL_DASHBOARD, e -> new ActualityForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Training", FontImage.MATERIAL_TRENDING_UP, e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Products", FontImage.MATERIAL_PRESENT_TO_ALL, e -> {
            try {
                new ProductGUI().show();
            } catch (IOException ex) {
            }
        });
                getToolbar().addMaterialCommandToSideMenu("  Favourites", FontImage.MATERIAL_FAVORITE, e -> new FavouriteGUI().show());

        getToolbar().addMaterialCommandToSideMenu("  Cart", FontImage.MATERIAL_ADD_SHOPPING_CART, e -> new CartGUI().show());
        getToolbar().addMaterialCommandToSideMenu("  Promotions", FontImage.MATERIAL_SETTINGS, e -> new PromotionForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Claims", FontImage.MATERIAL_EXIT_TO_APP, e -> new ClaimForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Event", FontImage.MATERIAL_EXIT_TO_APP, e -> new EventForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Pastries", FontImage.MATERIAL_EXIT_TO_APP, e -> { new PastryForm(res).show();});
        getToolbar().addMaterialCommandToSideMenu("  Reservations", FontImage.MATERIAL_EXIT_TO_APP, e -> new ReservationGUI(res).show());

        getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_UNDO, e -> {
            MyApplication.currentUser = null;
            new LoginForm(res).show();
        });
    }

    protected abstract void showOtherForm(Resources res);

}
