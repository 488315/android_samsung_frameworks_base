package com.android.systemui.keyguard;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SafeUIKeyguardViewMediator$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SafeUIKeyguardViewMediator f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ SafeUIKeyguardViewMediator$$ExternalSyntheticLambda5(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = safeUIKeyguardViewMediator;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = this.f$0;
                boolean z = this.f$1;
                if (!safeUIKeyguardViewMediator.mPM.isInteractive() && !safeUIKeyguardViewMediator.mPendingLock) {
                    android.util.Log.e("SafeUIKeyguardViewMediator", "exitKeyguardAndFinishSurfaceBehindRemoteAnimation#postAfterTraversal: mPM.isInteractive()=" + safeUIKeyguardViewMediator.mPM.isInteractive() + " mPendingLock=" + safeUIKeyguardViewMediator.mPendingLock + ". One of these being false means we re-locked the device during unlock. Do not proceed to finish keyguard exit and unlock.");
                    safeUIKeyguardViewMediator.doKeyguardLocked(null);
                    safeUIKeyguardViewMediator.finishSurfaceBehindRemoteAnimation(true);
                    safeUIKeyguardViewMediator.setShowingLocked(true, true);
                    break;
                } else {
                    safeUIKeyguardViewMediator.onKeyguardExitFinished();
                    ((KeyguardStateControllerImpl) safeUIKeyguardViewMediator.mKeyguardStateController).getClass();
                    if (z) {
                        android.util.Log.d("SafeUIKeyguardViewMediator", "onKeyguardExitRemoteAnimationFinished#hideKeyguardViewAfterRemoteAnimation");
                        ((KeyguardUnlockAnimationController) safeUIKeyguardViewMediator.mKeyguardUnlockAnimationControllerLazy.get()).hideKeyguardViewAfterRemoteAnimation();
                    } else {
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("skip hideKeyguardViewAfterRemoteAnimation dismissFromSwipe=false wasShowing=", "SafeUIKeyguardViewMediator", z);
                    }
                    safeUIKeyguardViewMediator.finishSurfaceBehindRemoteAnimation(false);
                    safeUIKeyguardViewMediator.mUpdateMonitor.mHandler.sendEmptyMessage(346);
                    break;
                }
                break;
            default:
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator2 = this.f$0;
                boolean z2 = this.f$1;
                for (int size = safeUIKeyguardViewMediator2.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
                    IKeyguardStateCallback iKeyguardStateCallback = (IKeyguardStateCallback) safeUIKeyguardViewMediator2.mKeyguardStateCallbacks.get(size);
                    try {
                        iKeyguardStateCallback.onShowingStateChanged(z2, safeUIKeyguardViewMediator2.mSelectedUserInteractor.getSelectedUserId());
                    } catch (RemoteException e) {
                        Slog.w("SafeUIKeyguardViewMediator", "Failed to call onShowingStateChanged", e);
                        if (e instanceof DeadObjectException) {
                            safeUIKeyguardViewMediator2.mKeyguardStateCallbacks.remove(iKeyguardStateCallback);
                        }
                    }
                }
                break;
        }
    }
}
