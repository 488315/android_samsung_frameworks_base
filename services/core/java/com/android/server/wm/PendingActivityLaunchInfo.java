package com.android.server.wm;

import android.util.Slog;

public final class PendingActivityLaunchInfo extends DexRestartAppInfo {
    @Override // com.android.server.wm.DexRestartAppInfo
    public final void startResult(int i, ActivityTaskManagerService activityTaskManagerService) {
        if (this.mPal == null) {
            return;
        }
        this.mOptions.setLaunchDisplayId(i);
        PendingActivityLaunch pendingActivityLaunch = this.mPal;
        int i2 = pendingActivityLaunch.r.launchMode;
        boolean z = i2 == 3 || i2 == 2;
        ActivityStartController activityStartController =
                activityTaskManagerService.mActivityStartController;
        try {
            activityStartController
                    .obtainStarter(null, "pendingActivityLaunch-for-dex")
                    .startResolvedActivity(
                            pendingActivityLaunch.r,
                            pendingActivityLaunch.sourceRecord,
                            pendingActivityLaunch.startFlags,
                            this.mOptions,
                            z ? null : this.mTargetTask,
                            pendingActivityLaunch.intentGrants);
        } catch (Exception e) {
            Slog.e(
                    "ActivityTaskManager",
                    "Exception during pending activity launch for dex. pal="
                            + pendingActivityLaunch,
                    e);
        }
    }

    public final String toString() {
        return "START_PENDING_ACTIVITY_LAUNCH_TYPE, pal : "
                + this.mPal
                + ", processName : "
                + this.mProcessName
                + ", uid : "
                + this.mUid;
    }
}
