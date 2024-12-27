package com.android.server.wm;

public final /* synthetic */ class MultiTaskingController$$ExternalSyntheticLambda14
        implements Runnable {
    public final /* synthetic */ MultiTaskingController f$0;
    public final /* synthetic */ Task f$1;
    public final /* synthetic */ WindowProcessController f$2;

    public /* synthetic */ MultiTaskingController$$ExternalSyntheticLambda14(
            MultiTaskingController multiTaskingController,
            Task task,
            WindowProcessController windowProcessController) {
        this.f$0 = multiTaskingController;
        this.f$1 = task;
        this.f$2 = windowProcessController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MultiTaskingController multiTaskingController = this.f$0;
        Task task = this.f$1;
        WindowProcessController windowProcessController = this.f$2;
        WindowManagerGlobalLock windowManagerGlobalLock = multiTaskingController.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                multiTaskingController.mAtm.mTaskSupervisor.removeTask(
                        task, false, false, "embedded-package-changed");
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        multiTaskingController.mAtm.mAmInternal.killProcess(
                windowProcessController.mName,
                windowProcessController.mUid,
                "embedded-package-changed");
    }
}
