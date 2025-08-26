package com.android.systemui.keyguard.shared.transition;

import com.android.systemui.keyguard.shared.model.KeyguardState;

/* loaded from: classes2.dex */
public interface KeyguardTransitionAnimationCallback {
    void onAnimationCanceled(KeyguardState keyguardState, KeyguardState keyguardState2);

    void onAnimationEnded(KeyguardState keyguardState, KeyguardState keyguardState2);

    void onAnimationStarted(KeyguardState keyguardState, KeyguardState keyguardState2);
}
