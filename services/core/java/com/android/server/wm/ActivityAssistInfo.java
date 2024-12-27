package com.android.server.wm;

import android.content.ComponentName;
import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
