package com.android.systemui.util;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WindowUtils {
    private static final String TAG = "WindowUtils";

    private WindowUtils() {
    }

    public static boolean isDesktopDualModeMonitorDisplay(Context context) {
        return isSamsungDexMode(context) && !isDesktopStandaloneMode(context) && ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getDisplayId() == 2;
    }

    public static boolean isDesktopStandaloneMode(Context context) {
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
        if (semDesktopModeManager != null) {
            return semDesktopModeManager.getDesktopModeState().getDisplayType() == 101;
        }
        Log.d(TAG, "isDesktopStandaloneMode : desktopModeManager is null");
        return false;
    }

    public static boolean isLcdMdnieSupported() {
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_MDNIE_HW");
    }

    public static boolean isSamsungDexMode(Context context) {
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP") && 1 == context.getResources().getConfiguration().semDesktopModeEnabled;
    }
}
