package com.android.systemui.animation;

import kotlin.jvm.functions.Function0;

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
