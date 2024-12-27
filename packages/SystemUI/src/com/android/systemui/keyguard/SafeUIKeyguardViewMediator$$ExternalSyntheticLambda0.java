package com.android.systemui.keyguard;

import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SafeUIKeyguardViewMediator$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SafeUIKeyguardViewMediator f$0;

    public /* synthetic */ SafeUIKeyguardViewMediator$$ExternalSyntheticLambda0(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, int i) {
        this.$r8$classId = i;
        this.f$0 = safeUIKeyguardViewMediator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = this.f$0;
        switch (i) {
            case 0:
                safeUIKeyguardViewMediator.mPM.userActivity(safeUIKeyguardViewMediator.mSystemClock.uptimeMillis(), false);
                break;
            case 1:
                ((KeyguardUnlockAnimationController) safeUIKeyguardViewMediator.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(false);
                safeUIKeyguardViewMediator.mInteractionJankMonitor.end(29);
                break;
            default:
                safeUIKeyguardViewMediator.getClass();
                android.util.Log.e("SafeUIKeyguardViewMediator", "mHideAnimationFinishedRunnable#run");
                safeUIKeyguardViewMediator.mHideAnimationRunning = false;
                safeUIKeyguardViewMediator.tryKeyguardDone();
                break;
        }
    }

    public /* synthetic */ SafeUIKeyguardViewMediator$$ExternalSyntheticLambda0(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, RemoteAnimationTarget[] remoteAnimationTargetArr) {
        this.$r8$classId = 1;
        this.f$0 = safeUIKeyguardViewMediator;
    }
}
