package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ActivityTaskSupervisor$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityTaskSupervisor f$0;

    public /* synthetic */ ActivityTaskSupervisor$$ExternalSyntheticLambda0(
            ActivityTaskSupervisor activityTaskSupervisor, int i) {
        this.$r8$classId = i;
        this.f$0 = activityTaskSupervisor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ActivityTaskSupervisor activityTaskSupervisor = this.f$0;
        switch (i) {
            case 0:
                activityTaskSupervisor.mService.mAmInternal.trimApplications();
                return;
            default:
                WindowManagerGlobalLock windowManagerGlobalLock =
                        activityTaskSupervisor.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        activityTaskSupervisor
                                .scheduleProcessStoppingAndFinishingActivitiesIfNeeded();
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
        }
    }
}
