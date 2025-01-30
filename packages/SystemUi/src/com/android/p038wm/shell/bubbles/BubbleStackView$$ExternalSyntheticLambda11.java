package com.android.p038wm.shell.bubbles;

import com.android.p038wm.shell.animation.PhysicsAnimator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda11 implements PhysicsAnimator.UpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda11(BubbleStackView bubbleStackView, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleStackView;
    }

    @Override // com.android.wm.shell.animation.PhysicsAnimator.UpdateListener
    public final void onAnimationUpdateForProperty(Object obj) {
        int i = this.$r8$classId;
        BubbleStackView bubbleStackView = this.f$0;
        switch (i) {
            case 0:
                bubbleStackView.mExpandedViewContainer.setAnimationMatrix(bubbleStackView.mExpandedViewContainerMatrix);
                break;
            case 1:
                bubbleStackView.mExpandedViewContainer.setAnimationMatrix(bubbleStackView.mExpandedViewContainerMatrix);
                break;
            case 2:
                bubbleStackView.mExpandedViewContainer.setAnimationMatrix(bubbleStackView.mExpandedViewContainerMatrix);
                break;
            default:
                bubbleStackView.mExpandedViewContainer.setAnimationMatrix(bubbleStackView.mExpandedViewContainerMatrix);
                break;
        }
    }
}
