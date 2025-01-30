package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.util.Slog;

/* compiled from: DexRestartAppInfo.java */
/* loaded from: classes3.dex */
public final class PendingActivityLaunchInfo extends DexRestartAppInfo {
    public PendingActivityLaunchInfo(PendingActivityLaunch pendingActivityLaunch, String str, int i, ApplicationInfo applicationInfo, Task task, Task task2, ActivityOptions activityOptions, int i2) {
        super(str, i, applicationInfo, task, task2, activityOptions, i2);
        this.mPal = pendingActivityLaunch;
    }

    @Override // com.android.server.wm.DexRestartAppInfo
    public CharSequence getAppName(Context context, ActivityTaskSupervisor activityTaskSupervisor) {
        ActivityRecord activityRecord;
        ActivityInfo activityInfo;
        PendingActivityLaunch pendingActivityLaunch = this.mPal;
        return (pendingActivityLaunch == null || (activityRecord = pendingActivityLaunch.f1747r) == null || (activityInfo = activityRecord.info) == null) ? "" : activityInfo.loadLabel(context.getPackageManager());
    }

    @Override // com.android.server.wm.DexRestartAppInfo
    public void startResult(ActivityTaskManagerService activityTaskManagerService, int i) {
        if (this.mPal == null) {
            return;
        }
        this.mOptions.setLaunchDisplayId(i);
        if (DexRestartAppInfo.SAFE_DEBUG) {
            Slog.d("DexController", "startResult: windowingMode=" + this.mOptions.getLaunchWindowingMode() + " targetDisplayId=" + i);
        }
        int i2 = this.mPal.f1747r.launchMode;
        activityTaskManagerService.getActivityStartController().doPendingActivityLaunches(this.mPal, i2 == 3 || i2 == 2 ? null : this.mTargetTask, this.mOptions);
    }

    public String toString() {
        return "START_PENDING_ACTIVITY_LAUNCH_TYPE, pal : " + this.mPal + ", processName : " + this.mProcessName + ", uid : " + this.mUid;
    }
}
