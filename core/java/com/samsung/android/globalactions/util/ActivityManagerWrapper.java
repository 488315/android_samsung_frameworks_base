package com.samsung.android.globalactions.util;

import android.app.ActivityManager;
import android.content.Context;

public class ActivityManagerWrapper {
    private final ActivityManager mActivityManager;

    public ActivityManagerWrapper(Context context) {
        this.mActivityManager = (ActivityManager) context.getSystemService("activity");
    }

    public boolean isInLockTaskMode() {
        return (this.mActivityManager == null || this.mActivityManager.getLockTaskModeState() == 0)
                ? false
                : true;
    }
}
