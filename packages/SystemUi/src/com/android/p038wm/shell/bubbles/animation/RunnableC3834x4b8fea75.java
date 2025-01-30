package com.android.p038wm.shell.bubbles.animation;

import android.graphics.PointF;
import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.p038wm.shell.bubbles.animation.PhysicsAnimationLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC3834x4b8fea75 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RunnableC3834x4b8fea75(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 0;
        switch (this.$r8$classId) {
            case 0:
                Runnable[] runnableArr = (Runnable[]) this.f$0;
                int length = runnableArr.length;
                while (i < length) {
                    runnableArr[i].run();
                    i++;
                }
                break;
            case 1:
                Runnable[] runnableArr2 = (Runnable[]) this.f$0;
                int length2 = runnableArr2.length;
                while (i < length2) {
                    runnableArr2[i].run();
                    i++;
                }
                break;
            default:
                PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator = (PhysicsAnimationLayout.PhysicsPropertyAnimator) this.f$0;
                physicsPropertyAnimator.getClass();
                DynamicAnimation.C01841 c01841 = DynamicAnimation.TRANSLATION_X;
                PointF pointF = physicsPropertyAnimator.mCurrentPointOnPath;
                float f = pointF.x;
                View view = physicsPropertyAnimator.mView;
                physicsPropertyAnimator.updateValueForChild(c01841, view, f);
                physicsPropertyAnimator.updateValueForChild(DynamicAnimation.TRANSLATION_Y, view, pointF.y);
                break;
        }
    }
}
