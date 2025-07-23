package com.android.systemui.animation;

import com.android.systemui.animation.ActivityTransitionAnimator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ActivityTransitionAnimator$Runner$dispose$1 implements Runnable {
    public final /* synthetic */ ActivityTransitionAnimator.Runner this$0;

    public ActivityTransitionAnimator$Runner$dispose$1(ActivityTransitionAnimator.Runner runner) {
        this.this$0 = runner;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ActivityTransitionAnimator.Runner runner = this.this$0;
        runner.delegate = null;
        runner.controller = null;
    }
}
