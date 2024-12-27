package com.android.systemui.navigationbar.util;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NavigationModeUtil {
    public static final NavigationModeUtil INSTANCE = new NavigationModeUtil();
    public static float[] sideInsetScaleArray = new float[0];
    public static float[] bottomInsetScaleArray = new float[0];

    private NavigationModeUtil() {
    }

    public static final String getGestureOverlayPackageName(Context context) {
        int i = Settings.Global.getInt(context.getContentResolver(), "navigation_bar_gesture_detail_type", 1);
        boolean z = Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_NAVIGATIONBAR_GESTURE_HINT, 1) != 0;
        return i == 0 ? z ? "com.samsung.internal.systemui.navbar.sec_gestural" : "com.samsung.internal.systemui.navbar.sec_gestural_no_hint" : z ? QuickStepContract.NAV_BAR_MODE_GESTURAL_OVERLAY : "com.samsung.internal.systemui.navbar.gestural_no_hint";
    }

    public static final String getOverlayPackage(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) == 0 ? QuickStepContract.NAV_BAR_MODE_3BUTTON_OVERLAY : getGestureOverlayPackageName(context);
    }

    public static final boolean isBottomGesture(int i) {
        return i == 3;
    }
}
