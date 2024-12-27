package com.android.systemui.statusbar.events;

import androidx.core.animation.Animator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
