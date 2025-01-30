package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.log.LogLevel;
import java.io.Serializable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda18 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediator f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Serializable f$2;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda18(KeyguardViewMediator keyguardViewMediator, Object obj, Serializable serializable, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediator;
        this.f$1 = obj;
        this.f$2 = serializable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                Boolean bool = (Boolean) this.f$1;
                Boolean bool2 = (Boolean) this.f$2;
                keyguardViewMediator.getClass();
                keyguardViewMediator.setShowingLocked(bool.booleanValue(), bool2.booleanValue());
                return;
            default:
                final KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = (IRemoteAnimationFinishedCallback) this.f$1;
                RemoteAnimationTarget[] remoteAnimationTargetArr = (RemoteAnimationTarget[]) this.f$2;
                InteractionJankMonitor interactionJankMonitor = keyguardViewMediator2.mInteractionJankMonitor;
                if (iRemoteAnimationFinishedCallback == null) {
                    ((KeyguardUnlockAnimationController) keyguardViewMediator2.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(false);
                    return;
                }
                if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0) {
                    android.util.Slog.e("KeyguardViewMediator", "Keyguard exit without a corresponding app to show.");
                    KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.ERROR, "Keyguard exit without a corresponding app to show.", null);
                    try {
                        try {
                            iRemoteAnimationFinishedCallback.onAnimationFinished();
                        } catch (RemoteException unused) {
                            android.util.Slog.e("KeyguardViewMediator", "RemoteException");
                            KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.ERROR, "RemoteException", null);
                        }
                        return;
                    } finally {
                        interactionJankMonitor.end(29);
                    }
                }
                SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) keyguardViewMediator2.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
                RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr[0];
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(400L);
                ofFloat.setInterpolator(Interpolators.LINEAR);
                ofFloat.addUpdateListener(new KeyguardViewMediator$$ExternalSyntheticLambda21(remoteAnimationTarget, syncRtSurfaceTransactionApplier, 0));
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.15
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        try {
                            try {
                                iRemoteAnimationFinishedCallback.onAnimationFinished();
                            } catch (RemoteException unused2) {
                                android.util.Slog.e("KeyguardViewMediator", "RemoteException");
                                KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.ERROR, "RemoteException", null);
                            }
                            KeyguardViewMediator.this.mInteractionJankMonitor.cancel(29);
                        } catch (Throwable th) {
                            KeyguardViewMediator.this.mInteractionJankMonitor.cancel(29);
                            throw th;
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        try {
                            try {
                                iRemoteAnimationFinishedCallback.onAnimationFinished();
                            } catch (RemoteException unused2) {
                                android.util.Slog.e("KeyguardViewMediator", "RemoteException");
                                KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.ERROR, "RemoteException", null);
                            }
                            KeyguardViewMediator.this.mInteractionJankMonitor.end(29);
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
}
