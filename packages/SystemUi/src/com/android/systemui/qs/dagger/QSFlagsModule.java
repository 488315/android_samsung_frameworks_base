package com.android.systemui.qs.dagger;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import android.os.SystemProperties;
import android.util.Log;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface QSFlagsModule {
    static boolean isPMLiteEnabled(FeatureFlags featureFlags, GlobalSettings globalSettings) {
        return ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.POWER_MENU_LITE) && globalSettings.getInt("sysui_pm_lite", 1) != 0;
    }

    static boolean isReduceBrightColorsAvailable(Context context) {
        boolean z;
        if (!ColorDisplayManager.isReduceBrightColorsAvailable(context) || !SystemProperties.getBoolean("ro.surface_flinger.protected_contents", false)) {
            return false;
        }
        String str = SystemProperties.get("ro.product.vendor.device");
        if (str == null) {
            Log.d("ExtraDim", "Most of all devices supports extra dim. But failed to get device name.");
        } else {
            HashSet hashSet = new HashSet();
            hashSet.add("beyond");
            hashSet.add("d1");
            hashSet.add("d2");
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                if (str.startsWith((String) it.next())) {
                    z = false;
                    break;
                }
            }
        }
        z = true;
        return z;
    }
}
