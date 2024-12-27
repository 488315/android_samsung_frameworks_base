package com.android.server.wm;

import java.util.concurrent.CompletableFuture;

public final /* synthetic */ class TaskPositioningController$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TaskPositioningController$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((CompletableFuture) obj).complete(null);
                return;
            case 1:
                ((CompletableFuture) obj).complete(null);
                return;
            default:
                TaskPositioningController taskPositioningController =
                        (TaskPositioningController) obj;
                WindowManagerGlobalLock windowManagerGlobalLock =
                        taskPositioningController.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        taskPositioningController.cleanUpTaskPositioner();
                        taskPositioningController.mPositioningDisplay = null;
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
