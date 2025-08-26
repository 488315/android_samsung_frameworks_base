package com.android.systemui.process;

import android.app.ActivityManager;
import android.os.Process;

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
