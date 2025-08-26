package com.android.systemui.navigationbar.util;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.util.SettingsHelper;

/* loaded from: classes2.dex */
public final class NavigationModeUtil {
    public static final NavigationModeUtil INSTANCE = new NavigationModeUtil();
    public static float[] sideInsetScaleArray = new float[0];
    public static float[] bottomInsetScaleArray = new float[0];

    private NavigationModeUtil() {
    }

    public static final String getGestureOverlayPackageName(Context context) {
        int i = Settings.Global.getInt(context.getContentResolver(), "navigation_bar_gesture_detail_type", 1);
        boolean z = Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_NAVIGATIONBAR_GESTURE_HINT, 1) != 0;
        return i == 0 ? z ? "com.samsung.internal.systemui.navbar.sec_gestural" : "com.samsung.internal.systemui.navbar.sec_gestural_no_hint" : z ? "com.android.internal.systemui.navbar.gestural" : "com.samsung.internal.systemui.navbar.gestural_no_hint";
    }

    public static final String getOverlayPackage(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) == 0 ? "com.android.internal.systemui.navbar.threebutton" : getGestureOverlayPackageName(context);
    }

    public static final boolean isBottomGesture(int i) {
        return i == 3;
    }
}
