package com.android.server.wm;

import android.content.ComponentName;
import android.os.IBinder;

public final class ActivityAssistInfo {
    public final IBinder mActivityToken;
    public final IBinder mAssistToken;
    public final ComponentName mComponentName;
    public final int mTaskId;
    public final int mUserId;

    public ActivityAssistInfo(ActivityRecord activityRecord) {
        this.mActivityToken = activityRecord.token;
        this.mAssistToken = activityRecord.assistToken;
        this.mTaskId = activityRecord.task.mTaskId;
        this.mComponentName = activityRecord.mActivityComponent;
        this.mUserId = activityRecord.mUserId;
    }
}
