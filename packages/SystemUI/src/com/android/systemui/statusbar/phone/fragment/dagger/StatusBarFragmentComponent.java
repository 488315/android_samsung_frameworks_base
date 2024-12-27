package com.android.systemui.statusbar.phone.fragment.dagger;

import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.StatusBarBoundsProvider;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface StatusBarFragmentComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        StatusBarFragmentComponent create(PhoneStatusBarView phoneStatusBarView);
    }

    StatusBarBoundsProvider getBoundsProvider();

    HeadsUpAppearanceController getHeadsUpAppearanceController();

    PhoneStatusBarTransitions getPhoneStatusBarTransitions();

    PhoneStatusBarView getPhoneStatusBarView();

    PhoneStatusBarViewController getPhoneStatusBarViewController();

    Set getStartables();

    void init();
}
