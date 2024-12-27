package com.android.systemui.qs.dagger;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.util.settings.GlobalSettings;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface QSFlagsModule {
    static boolean isPMLiteEnabled(FeatureFlags featureFlags, GlobalSettings globalSettings) {
        return ((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.POWER_MENU_LITE) && globalSettings.getInt("sysui_pm_lite", 1) != 0;
    }

    static boolean isReduceBrightColorsAvailable(Context context) {
        return ColorDisplayManager.isReduceBrightColorsAvailable(context);
    }
}
