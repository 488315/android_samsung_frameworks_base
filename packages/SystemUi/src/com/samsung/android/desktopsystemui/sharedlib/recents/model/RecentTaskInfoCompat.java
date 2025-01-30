package com.samsung.android.desktopsystemui.sharedlib.recents.model;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RecentTaskInfoCompat {
    private ActivityManager.RecentTaskInfo mInfo;

    public RecentTaskInfoCompat(ActivityManager.RecentTaskInfo recentTaskInfo) {
        this.mInfo = recentTaskInfo;
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

    public ArrayList<Integer> getPairedTaskIds() {
        return new ArrayList<>();
    }

    public ComponentName getSourceComponent() {
        ActivityManager.RecentTaskInfo recentTaskInfo = this.mInfo;
        ComponentName componentName = recentTaskInfo.origActivity;
        return componentName != null ? componentName : recentTaskInfo.realActivity;
    }

    public boolean getSupportsSplitScreenMultiWindow() {
        return false;
    }

    public ActivityManager.TaskDescription getTaskDescription() {
        return this.mInfo.taskDescription;
    }

    public int getTaskId() {
        return this.mInfo.taskId;
    }

    public ComponentName getTopActivity() {
        return this.mInfo.topActivity;
    }

    public int getUserId() {
        return this.mInfo.userId;
    }

    public int getWindowingMode() {
        return this.mInfo.configuration.windowConfiguration.getWindowingMode();
    }

    public int isDexCompatEnabled() {
        return this.mInfo.configuration.dexCompatEnabled;
    }

    public boolean isPairTask() {
        return false;
    }

    public boolean isRunning() {
        return this.mInfo.isRunning;
    }
}
