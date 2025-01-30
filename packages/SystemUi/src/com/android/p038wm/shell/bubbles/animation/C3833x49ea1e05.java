package com.android.p038wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.p038wm.shell.bubbles.animation.PhysicsAnimationLayout;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsAnimationController$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C3833x49ea1e05 {
    public final /* synthetic */ PhysicsAnimationLayout.PhysicsAnimationController f$0;
    public final /* synthetic */ Set f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ C3833x49ea1e05(PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController, Set set, List list) {
        this.f$0 = physicsAnimationController;
        this.f$1 = set;
        this.f$2 = list;
    }

    public final void startAll(Runnable[] runnableArr) {
        PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController = this.f$0;
        physicsAnimationController.getClass();
        RunnableC3834x4b8fea75 runnableC3834x4b8fea75 = new RunnableC3834x4b8fea75(runnableArr, 1);
        if (physicsAnimationController.mLayout.getChildCount() == 0) {
            runnableC3834x4b8fea75.run();
            return;
        }
        DynamicAnimation.ViewProperty[] viewPropertyArr = (DynamicAnimation.ViewProperty[]) this.f$1.toArray(new DynamicAnimation.ViewProperty[0]);
        RunnableC3835x4b8fea76 runnableC3835x4b8fea76 = new RunnableC3835x4b8fea76(physicsAnimationController, viewPropertyArr, 1, runnableC3834x4b8fea75);
        for (DynamicAnimation.ViewProperty viewProperty : viewPropertyArr) {
            physicsAnimationController.mLayout.mEndActionForProperty.put(viewProperty, runnableC3835x4b8fea76);
        }
        Iterator it = this.f$2.iterator();
        while (it.hasNext()) {
            ((PhysicsAnimationLayout.PhysicsPropertyAnimator) it.next()).start(new Runnable[0]);
        }
    }
}
