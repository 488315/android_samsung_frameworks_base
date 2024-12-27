package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
