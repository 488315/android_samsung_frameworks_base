package com.android.systemui.statusbar.events;

import androidx.core.animation.Animator;

public interface SystemStatusAnimationCallback {
    default Animator onSystemEventAnimationBegin(boolean z, boolean z2) {
        return null;
    }

    default Animator onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3) {
        return null;
    }

    default void onHidePersistentDot(boolean z) {
    }

    default void onSystemStatusAnimationTransitionToPersistentDot(String str) {
    }
}
