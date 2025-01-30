package com.android.wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ExpandedAnimationController$$ExternalSyntheticLambda2 implements PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator {
    @Override // com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator
    public final void configureAnimationForChildAtIndex(int i, PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator) {
        physicsPropertyAnimator.property(DynamicAnimation.SCALE_X, 1.0f, new Runnable[0]);
        physicsPropertyAnimator.property(DynamicAnimation.SCALE_Y, 1.0f, new Runnable[0]);
        physicsPropertyAnimator.property(DynamicAnimation.ALPHA, 1.0f, new Runnable[0]);
    }
}
