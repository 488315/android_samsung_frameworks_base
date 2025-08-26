package com.android.wm.shell.bubbles.animation;

import com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda5;

/* loaded from: classes3.dex */
public final /* synthetic */ class ExpandedAnimationController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ExpandedAnimationController f$0;

    public /* synthetic */ ExpandedAnimationController$$ExternalSyntheticLambda1(ExpandedAnimationController expandedAnimationController, int i) {
        this.$r8$classId = i;
        this.f$0 = expandedAnimationController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ExpandedAnimationController expandedAnimationController = this.f$0;
        switch (i) {
            case 0:
                expandedAnimationController.mAnimatingExpand = false;
                Runnable runnable = expandedAnimationController.mAfterExpand;
                if (runnable != null) {
                    runnable.run();
                }
                expandedAnimationController.mAfterExpand = null;
                expandedAnimationController.updateBubblePositions();
                break;
            case 1:
                expandedAnimationController.mAnimatingCollapse = false;
                BubbleStackView$$ExternalSyntheticLambda5 bubbleStackView$$ExternalSyntheticLambda5 = expandedAnimationController.mAfterCollapse;
                if (bubbleStackView$$ExternalSyntheticLambda5 != null) {
                    bubbleStackView$$ExternalSyntheticLambda5.run();
                }
                expandedAnimationController.mAfterCollapse = null;
                expandedAnimationController.mFadeBubblesDuringCollapse = false;
                break;
            default:
                expandedAnimationController.getClass();
                break;
        }
    }
}
