package com.android.systemui.animation;

import com.android.systemui.animation.ActivityTransitionAnimator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
