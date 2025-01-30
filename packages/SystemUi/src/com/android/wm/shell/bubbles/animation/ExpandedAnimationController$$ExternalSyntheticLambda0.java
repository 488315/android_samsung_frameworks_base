package com.android.wm.shell.bubbles.animation;

import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ExpandedAnimationController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ExpandedAnimationController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ExpandedAnimationController expandedAnimationController = (ExpandedAnimationController) this.f$0;
                expandedAnimationController.mAnimatingExpand = false;
                Runnable runnable = expandedAnimationController.mAfterExpand;
                if (runnable != null) {
                    runnable.run();
                }
                expandedAnimationController.mAfterExpand = null;
                expandedAnimationController.updateBubblePositions();
                break;
            case 1:
                ExpandedAnimationController expandedAnimationController2 = (ExpandedAnimationController) this.f$0;
                expandedAnimationController2.mAnimatingCollapse = false;
                Runnable runnable2 = expandedAnimationController2.mAfterCollapse;
                if (runnable2 != null) {
                    runnable2.run();
                }
                expandedAnimationController2.mAfterCollapse = null;
                break;
            case 2:
                ((ExpandedAnimationController) this.f$0).mLeadBubbleEndAction = null;
                break;
            default:
                ((View) this.f$0).setTranslationZ(0.0f);
                break;
        }
    }
}
