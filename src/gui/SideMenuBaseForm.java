package gui;

import Service.ClaimService;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;

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
        Image profilePic = res.getImage("user-picture.jpg");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("  Jennifer Wilson", profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");

        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Change picture", FontImage.MATERIAL_DASHBOARD, e -> ClaimService.getByUser());
        getToolbar().addMaterialCommandToSideMenu("  Profile", FontImage.MATERIAL_DASHBOARD, e -> new ProfileForm(res).show());

        getToolbar().addMaterialCommandToSideMenu("  Actuality", FontImage.MATERIAL_DASHBOARD, e -> new ActualityForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Training", FontImage.MATERIAL_TRENDING_UP, e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Product", FontImage.MATERIAL_ACCESS_TIME, e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Promotion", FontImage.MATERIAL_SETTINGS, e -> new PromotionForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Claim", FontImage.MATERIAL_EXIT_TO_APP, e -> new RegisterForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Event", FontImage.MATERIAL_EXIT_TO_APP, e -> new RegisterForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Pastries", FontImage.MATERIAL_EXIT_TO_APP, e -> new RegisterForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Reservation", FontImage.MATERIAL_EXIT_TO_APP, e -> new ReservationForm(res).show());
    }

    protected abstract void showOtherForm(Resources res);
}
