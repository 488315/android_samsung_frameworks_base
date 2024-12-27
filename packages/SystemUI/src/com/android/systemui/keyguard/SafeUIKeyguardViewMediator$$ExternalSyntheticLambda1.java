package com.android.systemui.keyguard;

import android.os.RemoteException;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Flags;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

public final /* synthetic */ class SafeUIKeyguardViewMediator$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SafeUIKeyguardViewMediator f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ SafeUIKeyguardViewMediator$$ExternalSyntheticLambda1(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, boolean z) {
        this.$r8$classId = 1;
        this.f$0 = safeUIKeyguardViewMediator;
        this.f$1 = z;
        this.f$2 = false;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = this.f$0;
                boolean z = this.f$1;
                boolean z2 = this.f$2;
                safeUIKeyguardViewMediator.getClass();
                StringBuilder sb = new StringBuilder("updateActivityLockScreenState(");
                sb.append(z);
                sb.append(", ");
                sb.append(z2);
                ExifInterface$$ExternalSyntheticOutline0.m(sb, ")", "SafeUIKeyguardViewMediator");
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                Flags.keyguardWmStateRefactor();
                try {
                    safeUIKeyguardViewMediator.mActivityTaskManagerService.setLockScreenShown(z, z2);
                    break;
                } catch (RemoteException unused) {
                    return;
                }
            default:
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator2 = this.f$0;
                boolean z3 = this.f$1;
                boolean z4 = this.f$2;
                if (!safeUIKeyguardViewMediator2.mPM.isInteractive() && !safeUIKeyguardViewMediator2.mPendingLock) {
                    android.util.Log.e("SafeUIKeyguardViewMediator", "exitKeyguardAndFinishSurfaceBehindRemoteAnimation#postAfterTraversal: mPM.isInteractive()=" + safeUIKeyguardViewMediator2.mPM.isInteractive() + " mPendingLock=" + safeUIKeyguardViewMediator2.mPendingLock + ". One of these being false means we re-locked the device during unlock. Do not proceed to finish keyguard exit and unlock.");
                    safeUIKeyguardViewMediator2.doKeyguardLocked(null);
                    safeUIKeyguardViewMediator2.finishSurfaceBehindRemoteAnimation(true);
                    safeUIKeyguardViewMediator2.setShowingLocked(true, true);
                    break;
                } else {
                    safeUIKeyguardViewMediator2.onKeyguardExitFinished();
                    KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) safeUIKeyguardViewMediator2.mKeyguardStateController;
                    if (keyguardStateControllerImpl.mDismissingFromTouch || z3) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "onKeyguardExitRemoteAnimationFinished#hideKeyguardViewAfterRemoteAnimation");
                        ((KeyguardUnlockAnimationController) safeUIKeyguardViewMediator2.mKeyguardUnlockAnimationControllerLazy.get()).hideKeyguardViewAfterRemoteAnimation();
                    } else {
                        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(new StringBuilder("skip hideKeyguardViewAfterRemoteAnimation dismissFromSwipe="), keyguardStateControllerImpl.mDismissingFromTouch, " wasShowing=", z3, "SafeUIKeyguardViewMediator");
                    }
                    safeUIKeyguardViewMediator2.finishSurfaceBehindRemoteAnimation(z4);
                    safeUIKeyguardViewMediator2.mUpdateMonitor.mHandler.sendEmptyMessage(346);
                    break;
                }
                break;
        }
    }

    public /* synthetic */ SafeUIKeyguardViewMediator$$ExternalSyntheticLambda1(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, boolean z, boolean z2) {
        this.$r8$classId = 0;
        this.f$0 = safeUIKeyguardViewMediator;
        this.f$1 = z;
        this.f$2 = z2;
    }
}
