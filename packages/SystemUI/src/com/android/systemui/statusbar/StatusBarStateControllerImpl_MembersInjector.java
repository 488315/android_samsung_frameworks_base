package com.android.systemui.statusbar;

import com.android.systemui.util.SettingsHelper;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarStateControllerImpl_MembersInjector {
    public StatusBarStateControllerImpl_MembersInjector(Provider provider, Provider provider2) {
    }

    public static void injectMSettingHelper(StatusBarStateControllerImpl statusBarStateControllerImpl, SettingsHelper settingsHelper) {
        statusBarStateControllerImpl.mSettingHelper = settingsHelper;
    }
}
