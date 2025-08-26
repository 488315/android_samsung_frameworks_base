package com.android.systemui.statusbar.events;

import androidx.core.animation.Animator;

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
