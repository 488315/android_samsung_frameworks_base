package com.samsung.android.desktopsystemui.sharedlib.recents.model;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RunningTaskInfoCompat {
    private ActivityManager.RunningTaskInfo mInfo;

    public RunningTaskInfoCompat(ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.mInfo = runningTaskInfo;
    }

    public Intent getBaseIntent() {
        return this.mInfo.baseIntent;
    }

    public int getDisplayId() {
        return this.mInfo.displayId;
    }

    public long getLastActiveTime() {
        return this.mInfo.lastActiveTime;
    }

    public long getLastGainFocusTime() {
        return this.mInfo.lastGainFocusTime;
    }

    public ComponentName getSourceComponent() {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mInfo;
        ComponentName componentName = runningTaskInfo.origActivity;
        return componentName != null ? componentName : runningTaskInfo.realActivity;
    }

    public int getTaskId() {
        return this.mInfo.taskId;
    }

    public int getUserId() {
        return this.mInfo.userId;
    }

    public int getWindowingMode() {
        return this.mInfo.configuration.windowConfiguration.getWindowingMode();
    }

    public boolean isRunning() {
        return this.mInfo.isRunning;
    }
}
