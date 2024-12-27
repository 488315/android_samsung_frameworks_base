package com.android.server.wm;

import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardController$$ExternalSyntheticLambda0 implements Runnable {
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
                WindowManagerGlobalLock windowManagerGlobalLock = keyguardController.mWindowManager.mGlobalLock;
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
