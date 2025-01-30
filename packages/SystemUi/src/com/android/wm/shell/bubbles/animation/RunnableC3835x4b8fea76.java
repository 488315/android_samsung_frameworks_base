package com.android.wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda1 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC3835x4b8fea76 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ RunnableC3835x4b8fea76(Object obj, Object obj2, int i, Object obj3) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        int i = 0;
        switch (this.$r8$classId) {
            case 0:
                PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator = (PhysicsAnimationLayout.PhysicsPropertyAnimator) this.f$0;
                SpringAnimation springAnimation = (SpringAnimation) this.f$1;
                SpringAnimation springAnimation2 = (SpringAnimation) this.f$2;
                physicsPropertyAnimator.getClass();
                if (!springAnimation.mRunning && !springAnimation2.mRunning) {
                    Runnable[] runnableArr = physicsPropertyAnimator.mPositionEndActions;
                    if (runnableArr != null) {
                        int length = runnableArr.length;
                        while (i < length) {
                            runnableArr[i].run();
                            i++;
                        }
                    }
                    physicsPropertyAnimator.mPositionEndActions = null;
                    break;
                }
                break;
            default:
                PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController = (PhysicsAnimationLayout.PhysicsAnimationController) this.f$0;
                DynamicAnimation.ViewProperty[] viewPropertyArr = (DynamicAnimation.ViewProperty[]) this.f$1;
                Runnable runnable = (Runnable) this.f$2;
                PhysicsAnimationLayout physicsAnimationLayout = physicsAnimationController.mLayout;
                int i2 = 0;
                while (true) {
                    if (i2 >= physicsAnimationLayout.getChildCount()) {
                        z = false;
                    } else if (PhysicsAnimationLayout.arePropertiesAnimatingOnView(physicsAnimationLayout.getChildAt(i2), viewPropertyArr)) {
                        z = true;
                    } else {
                        i2++;
                    }
                }
                if (!z) {
                    runnable.run();
                    int length2 = viewPropertyArr.length;
                    while (i < length2) {
                        physicsAnimationController.mLayout.mEndActionForProperty.remove(viewPropertyArr[i]);
                        i++;
                    }
                    break;
                }
                break;
        }
    }
}
