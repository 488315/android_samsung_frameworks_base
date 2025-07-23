package com.android.systemui.screenshot.sep;

import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TaskbarUtils {
    public static final boolean FEATURE_SUPPORT_TASKBAR;
    public static final String TAG;

    static {
        new TaskbarUtils();
        TAG = "Screenshot";
        FEATURE_SUPPORT_TASKBAR = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LAUNCHER_SUPPORT_TASKBAR");
    }

    private TaskbarUtils() {
    }
}
