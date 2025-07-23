package com.android.systemui.statusbar;

import com.android.systemui.util.SettingsHelper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarStateControllerImpl_MembersInjector {
    public StatusBarStateControllerImpl_MembersInjector(Provider provider, Provider provider2) {
    }

    public static void injectMSettingHelper(StatusBarStateControllerImpl statusBarStateControllerImpl, SettingsHelper settingsHelper) {
        statusBarStateControllerImpl.mSettingHelper = settingsHelper;
    }
}
