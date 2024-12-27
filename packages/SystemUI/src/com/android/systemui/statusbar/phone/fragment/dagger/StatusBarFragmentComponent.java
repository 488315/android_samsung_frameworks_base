package com.android.systemui.statusbar.phone.fragment.dagger;

import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.StatusBarBoundsProvider;
import java.util.Set;

public interface StatusBarFragmentComponent {

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
