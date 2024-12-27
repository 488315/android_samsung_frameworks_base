package com.android.systemui.animation;

import com.android.systemui.animation.ActivityTransitionAnimator;

public final class ActivityTransitionAnimator$Runner$dispose$1 implements Runnable {
    public final /* synthetic */ ActivityTransitionAnimator.Runner this$0;

    public ActivityTransitionAnimator$Runner$dispose$1(ActivityTransitionAnimator.Runner runner) {
        this.this$0 = runner;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.delegate = null;
    }
}
