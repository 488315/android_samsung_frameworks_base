package com.android.systemui.process;

import android.app.ActivityManager;
import android.os.Process;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ProcessWrapper {
    public final ActivityManager mActivityManager;

    public ProcessWrapper(ActivityManager activityManager) {
        this.mActivityManager = activityManager;
    }

    public static boolean isSystemUser() {
        return Process.myUserHandle().isSystem();
    }
}
