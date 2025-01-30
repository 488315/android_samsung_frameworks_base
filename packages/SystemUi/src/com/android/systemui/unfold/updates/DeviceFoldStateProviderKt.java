package com.android.systemui.unfold.updates;

import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class DeviceFoldStateProviderKt {
    public static final boolean DEBUG = Log.isLoggable("DeviceFoldProvider", 3);

    public static final String name(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "UNKNOWN" : "FINISH_CLOSED" : "FINISH_FULL_OPEN" : "FINISH_HALF_OPEN" : "START_CLOSING" : "START_OPENING";
    }

    public static /* synthetic */ void getFULLY_OPEN_THRESHOLD_DEGREES$annotations() {
    }

    public static /* synthetic */ void getHINGE_ANGLE_CHANGE_THRESHOLD_DEGREES$annotations() {
    }

    public static /* synthetic */ void getSTART_CLOSING_ON_APPS_THRESHOLD_DEGREES$annotations() {
    }
}
