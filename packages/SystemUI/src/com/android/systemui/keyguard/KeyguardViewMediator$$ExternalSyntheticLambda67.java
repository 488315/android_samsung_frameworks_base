package com.android.systemui.keyguard;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.Flags;
import com.android.systemui.Rune;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda67 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda67(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final int i = 1;
        final int i2 = 0;
        switch (this.$r8$classId) {
            case 0:
                KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this.f$0;
                Integer num = (Integer) this.f$1;
                keyguardViewMediator.getClass();
                int intValue = num.intValue();
                if (intValue == 0) {
                    return;
                }
                keyguardViewMediator.mHelper.playSound$2(intValue);
                return;
            case 1:
                ((KeyguardViewMediator) this.f$0).doKeyguardLocked((Bundle) this.f$1, false);
                return;
            default:
                KeyguardViewMediator.AnonymousClass13 anonymousClass13 = (KeyguardViewMediator.AnonymousClass13) this.f$0;
                KeyguardViewMediator.StartKeyguardExitAnimParams startKeyguardExitAnimParams = (KeyguardViewMediator.StartKeyguardExitAnimParams) this.f$1;
                final KeyguardViewMediator keyguardViewMediator2 = KeyguardViewMediator.this;
                long j = startKeyguardExitAnimParams.startTime;
                long j2 = startKeyguardExitAnimParams.fadeoutDuration;
                RemoteAnimationTarget[] remoteAnimationTargetArr = startKeyguardExitAnimParams.mApps;
                RemoteAnimationTarget[] remoteAnimationTargetArr2 = startKeyguardExitAnimParams.mWallpapers;
                RemoteAnimationTarget[] remoteAnimationTargetArr3 = startKeyguardExitAnimParams.mNonApps;
                final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = startKeyguardExitAnimParams.mFinishedCallback;
                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                keyguardViewMediator2.getClass();
                Trace.beginSection("KeyguardViewMediator#handleStartKeyguardExitAnimation");
                android.util.Log.d("KeyguardViewMediator", "handleStartKeyguardExitAnimation startTime=" + j + " fadeoutDuration=" + j2);
                synchronized (keyguardViewMediator2) {
                    if (keyguardViewMediator2.mHiding || keyguardViewMediator2.mSurfaceBehindRemoteAnimationRequested || ((KeyguardStateControllerImpl) keyguardViewMediator2.mKeyguardStateController).mFlingingToDismissKeyguardDuringSwipeGesture) {
                        keyguardViewMediator2.mHiding = false;
                        IRemoteAnimationRunner iRemoteAnimationRunner = keyguardViewMediator2.mKeyguardExitAnimationRunner;
                        keyguardViewMediator2.mKeyguardExitAnimationRunner = null;
                        LatencyTracker.getInstance(keyguardViewMediator2.mContext).onActionEnd(11);
                        if (iRemoteAnimationRunner != null && iRemoteAnimationFinishedCallback != null) {
                            IRemoteAnimationFinishedCallback anonymousClass15 = new IRemoteAnimationFinishedCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.15
                                public final /* synthetic */ IRemoteAnimationFinishedCallback val$finishedCallback;

                                public AnonymousClass15(final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2) {
                                    r2 = iRemoteAnimationFinishedCallback2;
                                }

                                public final IBinder asBinder() {
                                    return r2.asBinder();
                                }

                                public final void onAnimationFinished() {
                                    KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                                    Flags.keyguardWmStateRefactor();
                                    try {
                                        r2.onAnimationFinished();
                                    } catch (RemoteException e) {
                                        Slog.w("KeyguardViewMediator", "Failed to call onAnimationFinished", e);
                                    }
                                    KeyguardViewMediator keyguardViewMediator3 = KeyguardViewMediator.this;
                                    Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                                    keyguardViewMediator3.onKeyguardExitFinished$1();
                                    ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).hide(0L, 0L);
                                    KeyguardViewMediator.this.mInteractionJankMonitor.end(29);
                                }
                            };
                            try {
                                keyguardViewMediator2.mInteractionJankMonitor.begin(keyguardViewMediator2.createInteractionJankMonitorConf$1(29, "RunRemoteAnimation"));
                                iRemoteAnimationRunner.onAnimationStart(7, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, anonymousClass15);
                            } catch (RemoteException e) {
                                Slog.w("KeyguardViewMediator", "Failed to call onAnimationStart", e);
                            }
                        } else if (((StatusBarStateControllerImpl) keyguardViewMediator2.mStatusBarStateController).mLeaveOpenOnKeyguardHide || remoteAnimationTargetArr == null || remoteAnimationTargetArr.length <= 0) {
                            KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                            Flags.keyguardWmStateRefactor();
                            ((KeyguardViewController) keyguardViewMediator2.mKeyguardViewControllerLazy.get()).hide(j, j2);
                            keyguardViewMediator2.mContext.getMainExecutor().execute(new KeyguardViewMediator$$ExternalSyntheticLambda74(keyguardViewMediator2, iRemoteAnimationFinishedCallback2, remoteAnimationTargetArr));
                            keyguardViewMediator2.onKeyguardExitFinished$1();
                        } else {
                            KeyguardWmStateRefactor keyguardWmStateRefactor2 = KeyguardWmStateRefactor.INSTANCE;
                            Flags.keyguardWmStateRefactor();
                            keyguardViewMediator2.mSurfaceBehindRemoteAnimationFinishedCallback = iRemoteAnimationFinishedCallback2;
                            keyguardViewMediator2.mSurfaceBehindRemoteAnimationRunning = true;
                            RemoteAnimationTarget[] remoteAnimationTargetArr4 = android.service.dreams.Flags.dismissDreamOnKeyguardDismiss() ? (RemoteAnimationTarget[]) Arrays.stream(remoteAnimationTargetArr).filter(new Predicate() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda84
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    RemoteAnimationTarget remoteAnimationTarget = (RemoteAnimationTarget) obj;
                                    switch (i2) {
                                        case 0:
                                            if (remoteAnimationTarget.mode == 0) {
                                            }
                                            break;
                                        default:
                                            if (remoteAnimationTarget.mode == 0) {
                                            }
                                            break;
                                    }
                                    return false;
                                }
                            }).toArray(new IntFunction() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda85
                                @Override // java.util.function.IntFunction
                                public final Object apply(int i3) {
                                    switch (i2) {
                                        case 0:
                                            return new RemoteAnimationTarget[i3];
                                        default:
                                            return new RemoteAnimationTarget[i3];
                                    }
                                }
                            }) : remoteAnimationTargetArr;
                            ((KeyguardUnlockAnimationController) keyguardViewMediator2.mKeyguardUnlockAnimationControllerLazy.get()).notifyStartSurfaceBehindRemoteAnimation(remoteAnimationTargetArr4, remoteAnimationTargetArr2, j, keyguardViewMediator2.mSurfaceBehindRemoteAnimationRequested);
                        }
                        Trace.endSection();
                    } else {
                        if (iRemoteAnimationFinishedCallback2 != null) {
                            KeyguardWmStateRefactor keyguardWmStateRefactor3 = KeyguardWmStateRefactor.INSTANCE;
                            Flags.keyguardWmStateRefactor();
                            try {
                                iRemoteAnimationFinishedCallback2.onAnimationFinished();
                            } catch (RemoteException e2) {
                                Slog.w("KeyguardViewMediator", "Failed to call onAnimationFinished", e2);
                            }
                        }
                        keyguardViewMediator2.setShowingLocked$1(keyguardViewMediator2.mShowing, true);
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator2.mHelper;
                        Objects.requireNonNull(keyguardViewMediatorHelperImpl);
                        Rune.runIf((Runnable) new KeyguardViewMediator$$ExternalSyntheticLambda10(keyguardViewMediatorHelperImpl, 8), true);
                    }
                }
                KeyguardViewMediator.this.mFalsingCollector.onSuccessfulUnlock();
                return;
        }
    }
}
