package com.android.wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ExpandedAnimationController$$ExternalSyntheticLambda0 implements PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator {
    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator
    public final void configureAnimationForChildAtIndex(int i, PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator) {
        physicsPropertyAnimator.property(DynamicAnimation.SCALE_X, 1.0f, new Runnable[0]);
        physicsPropertyAnimator.property(DynamicAnimation.SCALE_Y, 1.0f, new Runnable[0]);
        physicsPropertyAnimator.property(DynamicAnimation.ALPHA, 1.0f, new Runnable[0]);
    }
}
