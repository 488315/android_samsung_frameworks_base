package com.android.systemui.qs.dagger;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.util.settings.GlobalSettings;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface QSFlagsModule {
    static boolean isPMLiteEnabled(FeatureFlags featureFlags, GlobalSettings globalSettings) {
        return ((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.POWER_MENU_LITE) && globalSettings.getInt("sysui_pm_lite", 1) != 0;
    }
}
