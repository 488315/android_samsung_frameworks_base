package com.android.systemui.statusbar.phone;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.ScrimAlpha;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ScrimController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScrimController f$0;

    public /* synthetic */ ScrimController$$ExternalSyntheticLambda1(ScrimController scrimController, int i) {
        this.$r8$classId = i;
        this.f$0 = scrimController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        ScrimController scrimController = this.f$0;
        switch (i) {
            case 0:
                TransitionStep transitionStep = (TransitionStep) obj;
                boolean z = ScrimController.DEBUG;
                scrimController.getClass();
                float f = ScrimState.KEYGUARD.mBehindAlpha;
                float f2 = transitionStep.value;
                KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
                KeyguardState keyguardState2 = transitionStep.to;
                if (keyguardState2 == keyguardState) {
                    scrimController.mBehindAlpha = f * f2;
                } else if (keyguardState2 == KeyguardState.GLANCEABLE_HUB) {
                    scrimController.mBehindAlpha = (1.0f - f2) * f;
                }
                scrimController.mScrimBehind.setViewAlpha(scrimController.mBehindAlpha);
                break;
            case 1:
                ScrimAlpha scrimAlpha = (ScrimAlpha) obj;
                boolean z2 = ScrimController.DEBUG;
                scrimController.getClass();
                float f3 = scrimAlpha.frontAlpha;
                scrimController.mInFrontAlpha = f3;
                scrimController.mScrimInFront.setViewAlpha(f3);
                float f4 = scrimAlpha.notificationsAlpha;
                scrimController.mNotificationsAlpha = f4;
                scrimController.mNotificationsScrim.setViewAlpha(f4);
                float f5 = scrimAlpha.behindAlpha;
                scrimController.mBehindAlpha = f5;
                scrimController.mScrimBehind.setViewAlpha(f5);
                break;
            case 2:
                boolean z3 = ScrimController.DEBUG;
                scrimController.getClass();
                Float f6 = (Float) obj;
                f6.getClass();
                scrimController.mScrimStateListener.accept(scrimController.mState, f6, scrimController.mColors);
                break;
            default:
                boolean z4 = ScrimController.DEBUG;
                scrimController.getClass();
                TransitionState transitionState = ((TransitionStep) obj).transitionState;
                scrimController.mIsBouncerToGoneTransitionRunning = transitionState == TransitionState.RUNNING;
                if (transitionState == TransitionState.STARTED) {
                    scrimController.mExpansionAffectsAlpha = false;
                    scrimController.legacyTransitionTo(ScrimState.UNLOCKED);
                }
                if (transitionState == TransitionState.FINISHED || transitionState == TransitionState.CANCELED) {
                    scrimController.mExpansionAffectsAlpha = true;
                    if (((KeyguardStateControllerImpl) scrimController.mKeyguardStateController).mKeyguardFadingAway) {
                        scrimController.mStatusBarKeyguardViewManager.onKeyguardFadedAway();
                    }
                    scrimController.dispatchScrimsVisible();
                    scrimController.dispatchBackScrimState(scrimController.mScrimBehind.mViewAlpha);
                    break;
                }
                break;
        }
    }
}
