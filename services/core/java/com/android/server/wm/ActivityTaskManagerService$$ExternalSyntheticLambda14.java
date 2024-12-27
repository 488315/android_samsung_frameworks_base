package com.android.server.wm;

import android.app.ActivityManagerInternal;

import com.android.server.am.AppStateBroadcaster;

public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda14
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ActivityTaskManagerService$$ExternalSyntheticLambda14(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ActivityRecord activityRecord;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ActivityTaskManagerService activityTaskManagerService =
                        (ActivityTaskManagerService) obj;
                WindowManagerGlobalLock windowManagerGlobalLock =
                        activityTaskManagerService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        Task topDisplayFocusedRootTask =
                                activityTaskManagerService.mRootWindowContainer
                                        .getTopDisplayFocusedRootTask();
                        if (topDisplayFocusedRootTask != null) {
                            activityRecord = topDisplayFocusedRootTask.topRunningActivityLocked();
                            if (activityRecord == null) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                        } else {
                            activityRecord = null;
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        if (activityRecord == null) {
                            return;
                        }
                        AppStateBroadcaster.sendApplicationFocusLoss(activityRecord.packageName);
                        return;
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            case 1:
                ((ActivityTaskManagerService) obj).mAmInternal.scheduleAppGcs();
                return;
            default:
                ((ActivityManagerInternal) obj).updateCpuStats();
                return;
        }
    }
}
