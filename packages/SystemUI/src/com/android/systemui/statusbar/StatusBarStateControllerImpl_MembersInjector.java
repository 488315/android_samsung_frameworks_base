package com.android.systemui.statusbar;

import com.android.systemui.util.SettingsHelper;
import javax.inject.Provider;

public final class StatusBarStateControllerImpl_MembersInjector {
    public StatusBarStateControllerImpl_MembersInjector(Provider provider, Provider provider2) {
    }

    public static void injectMSettingHelper(StatusBarStateControllerImpl statusBarStateControllerImpl, SettingsHelper settingsHelper) {
        statusBarStateControllerImpl.mSettingHelper = settingsHelper;
    }
}
