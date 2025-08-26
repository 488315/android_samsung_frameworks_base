package com.android.systemui.keyguard;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediator f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda1(KeyguardViewMediator keyguardViewMediator, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediator;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                boolean z = this.f$1;
                if (!keyguardViewMediator.mPM.isInteractive() && !keyguardViewMediator.mPendingLock) {
                    android.util.Log.e("KeyguardViewMediator", "exitKeyguardAndFinishSurfaceBehindRemoteAnimation#postAfterTraversal: mPM.isInteractive()=" + keyguardViewMediator.mPM.isInteractive() + " mPendingLock=" + keyguardViewMediator.mPendingLock + ". One of these being false means we re-locked the device during unlock. Do not proceed to finish keyguard exit and unlock.");
                    keyguardViewMediator.doKeyguardLocked$1(null);
                    keyguardViewMediator.finishSurfaceBehindRemoteAnimation(true);
                    keyguardViewMediator.setShowingLocked("exitKeyguardAndFinishSurfaceBehindRemoteAnimation - relocked", true, true);
                    break;
                } else {
                    keyguardViewMediator.onKeyguardExitFinished("exitKeyguardAndFinishSurfaceBehindRemoteAnimation");
                    ((KeyguardStateControllerImpl) keyguardViewMediator.mKeyguardStateController).getClass();
                    if (z) {
                        android.util.Log.d("KeyguardViewMediator", "onKeyguardExitRemoteAnimationFinished#hideKeyguardViewAfterRemoteAnimation");
                        ((KeyguardUnlockAnimationController) keyguardViewMediator.mKeyguardUnlockAnimationControllerLazy.get()).hideKeyguardViewAfterRemoteAnimation();
                    } else {
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("skip hideKeyguardViewAfterRemoteAnimation dismissFromSwipe=false wasShowing=", "KeyguardViewMediator", z);
                    }
                    keyguardViewMediator.finishSurfaceBehindRemoteAnimation(false);
                    keyguardViewMediator.mUpdateMonitor.mHandler.sendEmptyMessage(346);
                    break;
                }
                break;
            default:
                KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                boolean z2 = this.f$1;
                SelectedUserInteractor selectedUserInteractor = keyguardViewMediator2.mSelectedUserInteractor;
                for (int size = keyguardViewMediator2.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
                    IKeyguardStateCallback iKeyguardStateCallback = (IKeyguardStateCallback) keyguardViewMediator2.mKeyguardStateCallbacks.get(size);
                    try {
                        android.util.Log.d("KeyguardViewMediator", "notifyDefaultDisplayCallbacks: showing=" + z2 + " userId=" + selectedUserInteractor.getSelectedUserId());
                        iKeyguardStateCallback.onShowingStateChanged(z2, selectedUserInteractor.getSelectedUserId());
                    } catch (RemoteException e) {
                        Slog.w("KeyguardViewMediator", "Failed to call onShowingStateChanged", e);
                        if (e instanceof DeadObjectException) {
                            keyguardViewMediator2.mKeyguardStateCallbacks.remove(iKeyguardStateCallback);
                        }
                    }
                }
                break;
        }
    }
}
