package com.android.systemui.navigationbar.util;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.BasicRune;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavigationModeUtil {
    public static final NavigationModeUtil INSTANCE = new NavigationModeUtil();
    public static float[] sideInsetScaleArray = new float[0];
    public static float[] bottomInsetScaleArray = new float[0];

    private NavigationModeUtil() {
    }

    public static final String getGestureOverlayPackageName(Context context) {
        int i = Settings.Global.getInt(context.getContentResolver(), "navigation_bar_gesture_detail_type", !BasicRune.supportSamsungGesturalModeAsDefault() ? 1 : 0);
        boolean z = Settings.Global.getInt(context.getContentResolver(), "navigation_bar_gesture_hint", 1) != 0;
        return i == 0 ? z ? "com.samsung.internal.systemui.navbar.sec_gestural" : "com.samsung.internal.systemui.navbar.sec_gestural_no_hint" : z ? QuickStepContract.NAV_BAR_MODE_GESTURAL_OVERLAY : "com.samsung.internal.systemui.navbar.gestural_no_hint";
    }

    public static final boolean isBottomGesture(int i) {
        return i == 3;
    }
}
