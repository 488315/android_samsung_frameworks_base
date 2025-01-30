package com.android.systemui.shared.system;

import android.os.SystemProperties;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QuickStepContract {
    public static final boolean ALLOW_BACK_GESTURE_IN_SHADE = SystemProperties.getBoolean("persist.wm.debug.shade_allow_back_gesture", true);
    public static boolean SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN = false;

    public static boolean isAssistantGestureDisabled(long j) {
        if ((131072 & j) != 0) {
            j &= -3;
        }
        if ((3083 & j) != 0) {
            return true;
        }
        return (4 & j) != 0 && (j & 64) == 0;
    }

    public static boolean isBackGestureDisabled(long j) {
        if ((8 & j) != 0 || (32768 & j) != 0 || (33554432 & j) != 0) {
            return false;
        }
        if ((131072 & j) != 0 || SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN) {
            j &= -3;
        }
        return (j & (!ALLOW_BACK_GESTURE_IN_SHADE ? 34363932678L : 34363932674L)) != 0;
    }

    public static boolean isGesturalMode(int i) {
        return i == 2 || i == 3;
    }
}
