package com.android.wm.shell.shared.bubbles;

import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
