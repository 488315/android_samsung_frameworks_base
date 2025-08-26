package com.android.wm.shell.shared.bubbles;

import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;

/* loaded from: classes3.dex */
public final class BaseBubblePinController$addEndAction$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ Runnable $runnable;

    public BaseBubblePinController$addEndAction$1(Runnable runnable) {
        this.$runnable = runnable;
    }

    @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        this.$runnable.run();
    }
}
