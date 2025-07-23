package com.android.wm.shell.bubbles;

import android.view.View;
import com.android.wm.shell.shared.animation.PhysicsAnimator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda6 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda6(BubbleStackView bubbleStackView, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleStackView;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        BubbleStackView bubbleStackView = this.f$0;
        switch (i) {
            case 0:
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                if (bubbleStackView.isManageEduVisible()) {
                    bubbleStackView.mManageEduView.hide();
                } else if (bubbleStackView.isStackEduVisible()) {
                    bubbleStackView.mStackEduView.hide(false);
                } else {
                    BubbleData bubbleData = bubbleStackView.mBubbleData;
                    if (bubbleData.mExpanded) {
                        bubbleData.setExpanded(false);
                    }
                }
                bubbleStackView.mIsDraggingStack = false;
                bubbleStackView.mMagnetizedObject = null;
                break;
            case 1:
                BubbleData bubbleData2 = bubbleStackView.mBubbleData;
                bubbleData2.mShowingOverflow = true;
                bubbleData2.setSelectedBubbleInternal(bubbleStackView.mBubbleOverflow);
                bubbleData2.dispatchPendingChanges();
                bubbleStackView.mBubbleData.setExpanded(true);
                break;
            default:
                BubbleStackView.m3215$r8$lambda$H8d4Ep0eUhn88VAHYBVbgvu58M(bubbleStackView);
                break;
        }
    }
}
