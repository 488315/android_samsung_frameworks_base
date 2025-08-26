package com.android.wm.shell.bubbles;

import com.android.wm.shell.shared.animation.PhysicsAnimator;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda30 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleStackView f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda30(BubbleStackView bubbleStackView, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleStackView;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubbleStackView bubbleStackView = this.f$0;
                boolean z = this.f$1;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                bubbleStackView.updatePointerPosition(false);
                bubbleStackView.mIsExpansionAnimating = false;
                bubbleStackView.updateExpandedView();
                bubbleStackView.requestUpdate();
                bubbleStackView.mExpandedViewContainer.setVisibility(0);
                bubbleStackView.mExpandedViewAnimationController.animateForImeVisibilityChange(z);
                break;
            default:
                BubbleStackView bubbleStackView2 = this.f$0;
                boolean z2 = this.f$1;
                BubbleOverflow bubbleOverflow = bubbleStackView2.mBubbleOverflow;
                int i = z2 ? 0 : 8;
                BadgedImageView badgedImageView = bubbleOverflow.overflowBtn;
                if (badgedImageView != null) {
                    badgedImageView.setVisibility(i);
                    break;
                }
                break;
        }
    }
}
