package com.android.systemui.statusbar.events;

import androidx.core.animation.Animator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface SystemStatusAnimationCallback {
    Animator onSystemEventAnimationBegin(boolean z);

    Animator onSystemEventAnimationFinish(boolean z, boolean z2);

    default void onHidePersistentDot(boolean z) {
    }

    default void onSystemStatusAnimationTransitionToPersistentDot(String str) {
    }
}
