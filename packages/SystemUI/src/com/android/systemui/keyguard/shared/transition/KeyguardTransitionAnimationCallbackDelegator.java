package com.android.systemui.keyguard.shared.transition;

import com.android.systemui.keyguard.shared.model.KeyguardState;

/* loaded from: classes2.dex */
public final class KeyguardTransitionAnimationCallbackDelegator implements KeyguardTransitionAnimationCallback {
    public KeyguardTransitionAnimationCallback delegate;

    @Override // com.android.systemui.keyguard.shared.transition.KeyguardTransitionAnimationCallback
    public final void onAnimationCanceled(KeyguardState keyguardState, KeyguardState keyguardState2) {
        KeyguardTransitionAnimationCallback keyguardTransitionAnimationCallback = this.delegate;
        if (keyguardTransitionAnimationCallback != null) {
            keyguardTransitionAnimationCallback.onAnimationCanceled(keyguardState, keyguardState2);
        }
    }

    @Override // com.android.systemui.keyguard.shared.transition.KeyguardTransitionAnimationCallback
    public final void onAnimationEnded(KeyguardState keyguardState, KeyguardState keyguardState2) {
        KeyguardTransitionAnimationCallback keyguardTransitionAnimationCallback = this.delegate;
        if (keyguardTransitionAnimationCallback != null) {
            keyguardTransitionAnimationCallback.onAnimationEnded(keyguardState, keyguardState2);
        }
    }

    @Override // com.android.systemui.keyguard.shared.transition.KeyguardTransitionAnimationCallback
    public final void onAnimationStarted(KeyguardState keyguardState, KeyguardState keyguardState2) {
        KeyguardTransitionAnimationCallback keyguardTransitionAnimationCallback = this.delegate;
        if (keyguardTransitionAnimationCallback != null) {
            keyguardTransitionAnimationCallback.onAnimationStarted(keyguardState, keyguardState2);
        }
    }
}
