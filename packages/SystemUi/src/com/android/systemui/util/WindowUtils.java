package com.android.systemui.util;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WindowUtils {
    private WindowUtils() {
    }

    public static boolean isDesktopDualModeMonitorDisplay(Context context) {
        boolean z;
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP") && 1 == context.getResources().getConfiguration().semDesktopModeEnabled) {
            SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
            if (semDesktopModeManager == null) {
                Log.d("WindowUtils", "isDesktopStandaloneMode : desktopModeManager is null");
            } else if (semDesktopModeManager.getDesktopModeState().getDisplayType() == 101) {
                z = true;
                return z && defaultDisplay.getDisplayId() == 2;
            }
            z = false;
            if (z) {
            }
        }
        return false;
    }
}
