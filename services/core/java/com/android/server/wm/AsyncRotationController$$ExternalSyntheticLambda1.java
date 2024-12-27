package com.android.server.wm;

import android.util.Slog;

public final /* synthetic */ class AsyncRotationController$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ AsyncRotationController f$0;

    public /* synthetic */ AsyncRotationController$$ExternalSyntheticLambda1(
            AsyncRotationController asyncRotationController) {
        this.f$0 = asyncRotationController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        AsyncRotationController asyncRotationController = this.f$0;
        WindowManagerGlobalLock windowManagerGlobalLock =
                asyncRotationController.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (asyncRotationController.mIsStartTransactionCommitted) {
                    str = "unfinished windows " + asyncRotationController.mTargetWindowTokens;
                } else {
                    str =
                            !asyncRotationController.mIsStartTransactionPrepared
                                    ? "setupStartTransaction is not called"
                                    : "start transaction is not committed";
                }
                Slog.i("AsyncRotation_WindowManager", "Async rotation timeout: " + str);
                if (!asyncRotationController.mIsStartTransactionCommitted
                        && asyncRotationController.mIsStartTransactionPrepared) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                asyncRotationController.mDisplayContent.finishAsyncRotationIfPossible();
                asyncRotationController.mService.mWindowPlacerLocked.performSurfacePlacement(false);
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }
}
