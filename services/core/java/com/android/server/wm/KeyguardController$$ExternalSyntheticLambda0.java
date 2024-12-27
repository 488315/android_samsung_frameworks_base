package com.android.server.wm;

import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;

public final /* synthetic */ class KeyguardController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardController$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                KeyguardController keyguardController = (KeyguardController) obj;
                WindowManagerGlobalLock windowManagerGlobalLock =
                        keyguardController.mWindowManager.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        keyguardController.updateDeferTransitionForAod(false);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
            default:
                ((PhoneWindowManager) ((WindowManagerPolicy) obj)).applyKeyguardOcclusionChange();
                return;
        }
    }
}
