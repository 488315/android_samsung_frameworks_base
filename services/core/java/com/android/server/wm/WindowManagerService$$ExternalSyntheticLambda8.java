package com.android.server.wm;

import android.app.PendingIntent;
import android.content.Intent;

public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda8
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WindowManagerService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda8(
            WindowManagerService windowManagerService, Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = windowManagerService;
        this.f$1 = obj;
        this.f$2 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WindowManagerService windowManagerService = this.f$0;
                windowManagerService.mExt.mPolicyExt.setPendingIntentAfterUnlock(
                        (PendingIntent) this.f$1, (Intent) this.f$2);
                return;
            default:
                WindowManagerService windowManagerService2 = this.f$0;
                InputTarget inputTarget = (InputTarget) this.f$1;
                InputTarget inputTarget2 = (InputTarget) this.f$2;
                WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService2.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (inputTarget != windowManagerService2.mFocusedInputTarget) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        if (inputTarget != null && inputTarget != inputTarget2) {
                            inputTarget.handleTapOutsideFocusOutsideSelf();
                        }
                        inputTarget2.handleTapOutsideFocusInsideSelf();
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
        }
    }
}
