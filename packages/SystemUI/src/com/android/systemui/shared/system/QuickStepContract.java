package com.android.systemui.shared.system;

import com.android.systemui.navigationbar.BasicRuneWrapper;
import com.android.systemui.shared.Flags;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QuickStepContract {
    public static final boolean ALLOW_BACK_GESTURE_IN_SHADE;
    public static boolean SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN = false;

    static {
        Flags.FEATURE_FLAGS.getClass();
        ALLOW_BACK_GESTURE_IN_SHADE = BasicRuneWrapper.NAVBAR_GESTURE;
    }

    public static boolean isAssistantGestureDisabled(long j) {
        if ((131072 & j) != 0) {
            j &= -3;
        }
        if ((3083 & j) != 0) {
            return true;
        }
        return (4 & j) != 0 && (j & 64) == 0;
    }

    public static boolean isBackGestureDisabled(long j, boolean z) {
        if ((8 & j) != 0 || (32768 & j) != 0 || (33554432 & j) != 0) {
            return false;
        }
        if ((131072 & j) != 0 || SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN) {
            j &= -3;
        }
        long j2 = !z ? 137443147778L : 137443147776L;
        if (!ALLOW_BACK_GESTURE_IN_SHADE) {
            j2 |= 4;
        }
        return (j & j2) != 0;
    }

    public static boolean isGesturalMode(int i) {
        return i == 2 || i == 3;
    }
}
