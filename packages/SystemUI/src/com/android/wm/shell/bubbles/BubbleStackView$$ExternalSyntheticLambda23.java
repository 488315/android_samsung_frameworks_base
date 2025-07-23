package com.android.wm.shell.bubbles;

import com.android.wm.shell.shared.animation.PhysicsAnimator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda23 implements PhysicsAnimator.UpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda23(BubbleStackView bubbleStackView, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleStackView;
    }

    @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.UpdateListener
    public final void onAnimationUpdateForProperty(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BubbleStackView bubbleStackView = this.f$0;
                bubbleStackView.mExpandedViewContainer.setAnimationMatrix(bubbleStackView.mExpandedViewContainerMatrix);
                break;
            case 1:
                BubbleStackView bubbleStackView2 = this.f$0;
                bubbleStackView2.mExpandedViewContainer.setAnimationMatrix(bubbleStackView2.mExpandedViewContainerMatrix);
                break;
            default:
                BubbleStackView bubbleStackView3 = this.f$0;
                bubbleStackView3.mExpandedViewContainer.setAnimationMatrix(bubbleStackView3.mExpandedViewContainerMatrix);
                break;
        }
    }
}
