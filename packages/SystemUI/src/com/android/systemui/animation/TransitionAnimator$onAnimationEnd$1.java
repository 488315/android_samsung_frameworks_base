package com.android.systemui.animation;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TransitionAnimator$onAnimationEnd$1 implements Runnable {
    public final /* synthetic */ Function0 $onEnd;

    public TransitionAnimator$onAnimationEnd$1(Function0 function0) {
        this.$onEnd = function0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.$onEnd.invoke();
    }
}
