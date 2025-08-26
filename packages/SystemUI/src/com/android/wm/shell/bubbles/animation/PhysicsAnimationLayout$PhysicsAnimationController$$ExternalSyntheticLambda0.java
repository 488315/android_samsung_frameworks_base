package com.android.wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;

/* loaded from: classes3.dex */
public final /* synthetic */ class PhysicsAnimationLayout$PhysicsAnimationController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ PhysicsAnimationLayout.PhysicsAnimationController f$0;
    public final /* synthetic */ DynamicAnimation.ViewProperty[] f$1;
    public final /* synthetic */ Runnable f$2;

    public /* synthetic */ PhysicsAnimationLayout$PhysicsAnimationController$$ExternalSyntheticLambda0(PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController, DynamicAnimation.ViewProperty[] viewPropertyArr, Runnable runnable) {
        this.f$0 = physicsAnimationController;
        this.f$1 = viewPropertyArr;
        this.f$2 = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController = this.f$0;
        DynamicAnimation.ViewProperty[] viewPropertyArr = this.f$1;
        Runnable runnable = this.f$2;
        PhysicsAnimationLayout physicsAnimationLayout = physicsAnimationController.mLayout;
        for (int i = 0; i < physicsAnimationLayout.getChildCount(); i++) {
            if (PhysicsAnimationLayout.arePropertiesAnimatingOnView(physicsAnimationLayout.getChildAt(i), viewPropertyArr)) {
                return;
            }
        }
        runnable.run();
        for (DynamicAnimation.ViewProperty viewProperty : viewPropertyArr) {
            physicsAnimationController.mLayout.mEndActionForProperty.remove(viewProperty);
        }
    }
}
