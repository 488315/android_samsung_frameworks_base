package com.android.systemui.statusbar.events;

import androidx.core.animation.Animator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SystemStatusAnimationCallback {
    default Animator onPrepareSystemEventAnimation(boolean z) {
        return null;
    }

    default SpringAnimatorSet onSystemEventAnimationBegin(boolean z, boolean z2) {
        return null;
    }

    default SpringAnimatorSet onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
        return null;
    }

    default void onHidePersistentDot(boolean z) {
    }

    default void onSystemStatusAnimationTransitionToPersistentDot(String str) {
    }
}
