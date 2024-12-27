package com.android.server.wm;

import android.app.ActivityOptions;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class DexRestartAppInfo {
    public final ActivityOptions mOptions;
    public PendingActivityLaunch mPal;
    public final int mPreferredDisplayId;
    public final String mProcessName;
    public final Task mReusedTask;
    public final Task mTargetTask;
    public final int mUid;

    public DexRestartAppInfo(
            String str, int i, Task task, Task task2, ActivityOptions activityOptions, int i2) {
        this.mProcessName = str;
        this.mUid = i;
        this.mReusedTask = task;
        this.mTargetTask = task2;
        this.mOptions = activityOptions == null ? ActivityOptions.makeBasic() : activityOptions;
        this.mPreferredDisplayId = i2;
    }

    public abstract void startResult(int i, ActivityTaskManagerService activityTaskManagerService);
}
