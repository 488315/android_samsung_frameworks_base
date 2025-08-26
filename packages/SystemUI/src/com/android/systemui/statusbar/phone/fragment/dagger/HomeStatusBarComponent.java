package com.android.systemui.statusbar.phone.fragment.dagger;

import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.data.repository.StatusBarConfigurationController;
import com.android.systemui.statusbar.layout.StatusBarBoundsProvider;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import java.util.Set;

/* loaded from: classes3.dex */
public interface HomeStatusBarComponent {

    public interface Factory {
        HomeStatusBarComponent create(PhoneStatusBarView phoneStatusBarView, StatusBarConfigurationController statusBarConfigurationController, StatusBarWindowController statusBarWindowController, DarkIconDispatcher darkIconDispatcher);
    }

    StatusBarBoundsProvider getBoundsProvider();

    DarkIconDispatcher getDarkIconDispatcher();

    HeadsUpAppearanceController getHeadsUpAppearanceController();

    PhoneStatusBarTransitions getPhoneStatusBarTransitions();

    PhoneStatusBarViewController getPhoneStatusBarViewController();

    Set getStartables();
}
