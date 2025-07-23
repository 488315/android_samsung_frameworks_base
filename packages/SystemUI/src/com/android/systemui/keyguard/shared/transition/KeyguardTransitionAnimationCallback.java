package com.android.systemui.keyguard.shared.transition;

import com.android.systemui.keyguard.shared.model.KeyguardState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface KeyguardTransitionAnimationCallback {
    void onAnimationCanceled(KeyguardState keyguardState, KeyguardState keyguardState2);

    void onAnimationEnded(KeyguardState keyguardState, KeyguardState keyguardState2);

    void onAnimationStarted(KeyguardState keyguardState, KeyguardState keyguardState2);
}
