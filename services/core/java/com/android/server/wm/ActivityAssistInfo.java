package com.android.server.wm;

import android.content.ComponentName;
import android.os.IBinder;

/* loaded from: classes3.dex */
public class ActivityAssistInfo {
    public final IBinder mActivityToken;
    public final IBinder mAssistToken;
    public final ComponentName mComponentName;
    public final int mTaskId;
    public final int mUserId;

    public ActivityAssistInfo(ActivityRecord activityRecord) {
        this.mActivityToken = activityRecord.token;
        this.mAssistToken = activityRecord.assistToken;
        this.mTaskId = activityRecord.getTask().mTaskId;
        this.mComponentName = activityRecord.mActivityComponent;
        this.mUserId = activityRecord.mUserId;
    }

    public IBinder getActivityToken() {
        return this.mActivityToken;
    }

    public IBinder getAssistToken() {
        return this.mAssistToken;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public int getUserId() {
        return this.mUserId;
    }
}
