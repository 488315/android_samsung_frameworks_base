package com.android.server.wm;

public final /* synthetic */ class PinnedTaskController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ PinnedTaskController f$0;

    public /* synthetic */ PinnedTaskController$$ExternalSyntheticLambda0(
            PinnedTaskController pinnedTaskController) {
        this.f$0 = pinnedTaskController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PinnedTaskController pinnedTaskController = this.f$0;
        WindowManagerGlobalLock windowManagerGlobalLock = pinnedTaskController.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (pinnedTaskController.mDeferOrientationChanging) {
                    pinnedTaskController.continueOrientationChange();
                    pinnedTaskController.mService.mWindowPlacerLocked.requestTraversal();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }
}
