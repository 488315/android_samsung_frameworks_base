package com.android.systemui.statusbar;

import com.android.systemui.util.SettingsHelper;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class StatusBarStateControllerImpl_MembersInjector {
    public StatusBarStateControllerImpl_MembersInjector(Provider provider, Provider provider2) {
    }

    public static void injectMSettingHelper(StatusBarStateControllerImpl statusBarStateControllerImpl, SettingsHelper settingsHelper) {
        statusBarStateControllerImpl.mSettingHelper = settingsHelper;
    }
}
