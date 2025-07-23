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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface HomeStatusBarComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
