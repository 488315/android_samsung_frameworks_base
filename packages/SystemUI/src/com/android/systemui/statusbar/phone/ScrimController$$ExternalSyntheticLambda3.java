package com.android.systemui.statusbar.phone;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.ScrimAlpha;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class ScrimController$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScrimController f$0;

    public /* synthetic */ ScrimController$$ExternalSyntheticLambda3(ScrimController scrimController, int i) {
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
                boolean booleanValue = ((Boolean) obj).booleanValue();
                scrimController.mWallpaperSupportsAmbientMode = booleanValue;
                for (ScrimState scrimState : ScrimState.values()) {
                    scrimState.mWallpaperSupportsAmbientMode = booleanValue;
                }
                break;
            case 2:
                scrimController.getClass();
                Float f3 = (Float) obj;
                f3.getClass();
                scrimController.mScrimStateListener.accept(scrimController.mState, f3, scrimController.mColors);
                break;
            case 3:
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
                        scrimController.mStatusBarKeyguardViewManager.onKeyguardFadedAway$1();
                    }
                    scrimController.dispatchScrimsVisible();
                    break;
                }
                break;
            default:
                ScrimAlpha scrimAlpha = (ScrimAlpha) obj;
                scrimController.getClass();
                float f4 = scrimAlpha.frontAlpha;
                scrimController.mInFrontAlpha = f4;
                scrimController.mScrimInFront.setViewAlpha(f4);
                float f5 = scrimAlpha.notificationsAlpha;
                scrimController.mNotificationsAlpha = f5;
                scrimController.mNotificationsScrim.setViewAlpha(f5);
                float f6 = scrimAlpha.behindAlpha;
                scrimController.mBehindAlpha = f6;
                scrimController.mScrimBehind.setViewAlpha(f6);
                break;
        }
    }
}
