package com.android.wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PhysicsAnimationLayout$PhysicsAnimationController$$ExternalSyntheticLambda1 {
    public final /* synthetic */ PhysicsAnimationLayout.PhysicsAnimationController f$0;
    public final /* synthetic */ Set f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ PhysicsAnimationLayout$PhysicsAnimationController$$ExternalSyntheticLambda1(PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController, Set set, List list) {
        this.f$0 = physicsAnimationController;
        this.f$1 = set;
        this.f$2 = list;
    }

    public final void startAll(Runnable[] runnableArr) {
        Set set = this.f$1;
        List list = this.f$2;
        PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController = this.f$0;
        physicsAnimationController.getClass();
        PhysicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0 physicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0 = new PhysicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0(runnableArr, 1);
        if (physicsAnimationController.mLayout.getChildCount() == 0) {
            physicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0.run();
            return;
        }
        DynamicAnimation.ViewProperty[] viewPropertyArr = (DynamicAnimation.ViewProperty[]) ((HashSet) set).toArray(new DynamicAnimation.ViewProperty[0]);
        PhysicsAnimationLayout$PhysicsAnimationController$$ExternalSyntheticLambda0 physicsAnimationLayout$PhysicsAnimationController$$ExternalSyntheticLambda0 = new PhysicsAnimationLayout$PhysicsAnimationController$$ExternalSyntheticLambda0(physicsAnimationController, viewPropertyArr, physicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0);
        for (DynamicAnimation.ViewProperty viewProperty : viewPropertyArr) {
            physicsAnimationController.mLayout.mEndActionForProperty.put(viewProperty, physicsAnimationLayout$PhysicsAnimationController$$ExternalSyntheticLambda0);
        }
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((PhysicsAnimationLayout.PhysicsPropertyAnimator) obj).start(new Runnable[0]);
        }
    }
}
