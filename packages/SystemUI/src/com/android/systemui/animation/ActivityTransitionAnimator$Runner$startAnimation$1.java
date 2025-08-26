package com.android.systemui.animation;

import com.android.systemui.animation.ActivityTransitionAnimator;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class ActivityTransitionAnimator$Runner$startAnimation$1 implements Runnable {
    public final /* synthetic */ ActivityTransitionAnimator.AnimationDelegate $delegate;
    public final /* synthetic */ Function1 $performAnimation;

    public ActivityTransitionAnimator$Runner$startAnimation$1(Function1 function1, ActivityTransitionAnimator.AnimationDelegate animationDelegate) {
        this.$performAnimation = function1;
        this.$delegate = animationDelegate;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.$performAnimation.mo781invoke(this.$delegate);
    }
}
