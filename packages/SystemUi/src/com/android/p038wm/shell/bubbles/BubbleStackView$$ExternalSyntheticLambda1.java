package com.android.p038wm.shell.bubbles;

import android.graphics.PointF;
import com.android.p038wm.shell.bubbles.BadgedImageView;
import com.android.p038wm.shell.bubbles.Bubble;
import com.android.p038wm.shell.bubbles.animation.StackAnimationController;
import com.android.systemui.R;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleStackView f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda1(BubbleStackView bubbleStackView, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleStackView;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubbleStackView bubbleStackView = this.f$0;
                Bubble bubble = (Bubble) this.f$1;
                bubbleStackView.mAfterFlyoutHidden = null;
                BubbleViewProvider bubbleViewProvider = bubbleStackView.mBubbleToExpandAfterFlyoutCollapse;
                if (bubbleViewProvider != null) {
                    bubbleStackView.mBubbleData.setSelectedBubble(bubbleViewProvider);
                    bubbleStackView.mBubbleData.setExpanded(true);
                    bubbleStackView.mBubbleToExpandAfterFlyoutCollapse = null;
                }
                BadgedImageView badgedImageView = bubble.mIconView;
                if (badgedImageView != null) {
                    badgedImageView.removeDotSuppressionFlag(BadgedImageView.SuppressionFlag.FLYOUT_VISIBLE);
                }
                bubbleStackView.updateTemporarilyInvisibleAnimation(false);
                break;
            case 1:
                BubbleStackView bubbleStackView2 = this.f$0;
                Bubble bubble2 = (Bubble) this.f$1;
                if (!bubbleStackView2.mIsExpanded && bubble2.mIconView != null) {
                    BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda3 = new BubbleStackView$$ExternalSyntheticLambda3(bubbleStackView2, 4);
                    if (bubbleStackView2.mFlyout.getVisibility() == 0) {
                        BubbleFlyoutView bubbleFlyoutView = bubbleStackView2.mFlyout;
                        Bubble.FlyoutMessage flyoutMessage = bubble2.mFlyoutMessage;
                        PointF pointF = bubbleStackView2.mStackAnimationController.mStackPosition;
                        boolean showDot = true ^ bubble2.showDot();
                        float[] dotCenter = bubble2.mIconView.getDotCenter();
                        bubbleFlyoutView.mOnHide = bubbleStackView2.mAfterFlyoutHidden;
                        bubbleFlyoutView.mDotCenter = dotCenter;
                        bubbleFlyoutView.fade(false, pointF, showDot, new BubbleFlyoutView$$ExternalSyntheticLambda0(bubbleFlyoutView, flyoutMessage, pointF, showDot));
                    } else {
                        bubbleStackView2.mFlyout.setVisibility(4);
                        BubbleFlyoutView bubbleFlyoutView2 = bubbleStackView2.mFlyout;
                        Bubble.FlyoutMessage flyoutMessage2 = bubble2.mFlyoutMessage;
                        StackAnimationController stackAnimationController = bubbleStackView2.mStackAnimationController;
                        PointF pointF2 = stackAnimationController.mStackPosition;
                        boolean isStackOnLeftSide = stackAnimationController.isStackOnLeftSide();
                        BadgedImageView badgedImageView2 = bubble2.mIconView;
                        int i = badgedImageView2.mDotColor;
                        BubbleStackView$$ExternalSyntheticLambda1 bubbleStackView$$ExternalSyntheticLambda1 = bubbleStackView2.mAfterFlyoutHidden;
                        float[] dotCenter2 = badgedImageView2.getDotCenter();
                        boolean z = !bubble2.showDot();
                        bubbleFlyoutView2.mBubbleSize = bubbleFlyoutView2.mPositioner.mBubbleSize;
                        float dimensionPixelOffset = bubbleFlyoutView2.getResources().getDimensionPixelOffset(R.dimen.sec_noti_bubble_badge_dot_size);
                        bubbleFlyoutView2.mOriginalDotSize = dimensionPixelOffset;
                        float f = (dimensionPixelOffset * 1.0f) / 2.0f;
                        bubbleFlyoutView2.mNewDotRadius = f;
                        bubbleFlyoutView2.mNewDotSize = f * 2.0f;
                        bubbleFlyoutView2.updateFlyoutMessage(flyoutMessage2);
                        bubbleFlyoutView2.mArrowPointingLeft = isStackOnLeftSide;
                        bubbleFlyoutView2.mDotColor = i;
                        bubbleFlyoutView2.mOnHide = bubbleStackView$$ExternalSyntheticLambda1;
                        bubbleFlyoutView2.mDotCenter = dotCenter2;
                        bubbleFlyoutView2.setCollapsePercent(1.0f);
                        bubbleFlyoutView2.post(new BubbleFlyoutView$$ExternalSyntheticLambda0(bubbleFlyoutView2, pointF2, z, bubbleStackView$$ExternalSyntheticLambda3));
                    }
                    bubbleStackView2.mFlyout.bringToFront();
                    break;
                }
                break;
            default:
                BubbleStackView bubbleStackView3 = this.f$0;
                List list = (List) this.f$1;
                bubbleStackView3.getClass();
                for (int i2 = 0; i2 < list.size(); i2++) {
                    bubbleStackView3.mBubbleContainer.reorderView(((Bubble) list.get(i2)).mIconView, i2);
                }
                break;
        }
    }
}
