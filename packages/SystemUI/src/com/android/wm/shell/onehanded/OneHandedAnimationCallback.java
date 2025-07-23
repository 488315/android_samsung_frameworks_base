package com.android.wm.shell.onehanded;

import com.android.wm.shell.onehanded.OneHandedAnimationController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface OneHandedAnimationCallback {
    void onOneHandedAnimationCancel(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator);

    default void onAnimationUpdate(float f) {
    }

    default void onOneHandedAnimationEnd(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator) {
    }

    default void onOneHandedAnimationStart(OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator) {
    }
}
