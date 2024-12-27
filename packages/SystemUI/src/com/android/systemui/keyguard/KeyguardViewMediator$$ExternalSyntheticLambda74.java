package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.RemoteException;
import android.util.Slog;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.Flags;

public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda74 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ KeyguardViewMediator f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda74(KeyguardViewMediator keyguardViewMediator, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, RemoteAnimationTarget[] remoteAnimationTargetArr) {
        this.f$0 = keyguardViewMediator;
        this.f$1 = iRemoteAnimationFinishedCallback;
        this.f$2 = remoteAnimationTargetArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                Boolean bool = (Boolean) this.f$1;
                Boolean bool2 = (Boolean) this.f$2;
                keyguardViewMediator.getClass();
                keyguardViewMediator.setShowingLocked$1(bool.booleanValue(), bool2.booleanValue());
                return;
            default:
                KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = (IRemoteAnimationFinishedCallback) this.f$1;
                RemoteAnimationTarget[] remoteAnimationTargetArr = (RemoteAnimationTarget[]) this.f$2;
                if (iRemoteAnimationFinishedCallback == null) {
                    ((KeyguardUnlockAnimationController) keyguardViewMediator2.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(false);
                    return;
                }
                keyguardViewMediator2.getClass();
                if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0) {
                    Slog.e("KeyguardViewMediator", "Keyguard exit without a corresponding app to show.");
                    try {
                        try {
                            KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                            Flags.keyguardWmStateRefactor();
                            iRemoteAnimationFinishedCallback.onAnimationFinished();
                        } catch (RemoteException unused) {
                            Slog.e("KeyguardViewMediator", "RemoteException");
                        }
                        return;
                    } finally {
                        keyguardViewMediator2.mInteractionJankMonitor.end(29);
                    }
                }
                SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) keyguardViewMediator2.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
                RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr[0];
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(400L);
                ofFloat.setInterpolator(Interpolators.LINEAR);
                ofFloat.addUpdateListener(new KeyguardViewMediator$$ExternalSyntheticLambda92(remoteAnimationTarget, syncRtSurfaceTransactionApplier, 0));
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.16
                    public final /* synthetic */ IRemoteAnimationFinishedCallback val$finishedCallback;

                    public AnonymousClass16(IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2) {
                        r2 = iRemoteAnimationFinishedCallback2;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        KeyguardViewMediator keyguardViewMediator3;
                        try {
                            try {
                                KeyguardWmStateRefactor keyguardWmStateRefactor2 = KeyguardWmStateRefactor.INSTANCE;
                                Flags.keyguardWmStateRefactor();
                                r2.onAnimationFinished();
                                keyguardViewMediator3 = KeyguardViewMediator.this;
                            } catch (RemoteException unused2) {
                                Slog.e("KeyguardViewMediator", "RemoteException");
                                keyguardViewMediator3 = KeyguardViewMediator.this;
                            }
                            this = keyguardViewMediator3.mInteractionJankMonitor;
                            this.cancel(29);
                        } catch (Throwable th) {
                            KeyguardViewMediator.this.mInteractionJankMonitor.cancel(29);
                            throw th;
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        KeyguardViewMediator keyguardViewMediator3;
                        try {
                            try {
                                KeyguardWmStateRefactor keyguardWmStateRefactor2 = KeyguardWmStateRefactor.INSTANCE;
                                Flags.keyguardWmStateRefactor();
                                r2.onAnimationFinished();
                                keyguardViewMediator3 = KeyguardViewMediator.this;
                            } catch (RemoteException unused2) {
                                Slog.e("KeyguardViewMediator", "RemoteException");
                                keyguardViewMediator3 = KeyguardViewMediator.this;
                            }
                            this = keyguardViewMediator3.mInteractionJankMonitor;
                            this.end(29);
                        } catch (Throwable th) {
                            KeyguardViewMediator.this.mInteractionJankMonitor.end(29);
                            throw th;
                        }
                    }
                });
                ofFloat.start();
                return;
        }
    }

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda74(KeyguardViewMediator keyguardViewMediator, Boolean bool, Boolean bool2) {
        this.f$0 = keyguardViewMediator;
        this.f$1 = bool;
        this.f$2 = bool2;
    }
}
