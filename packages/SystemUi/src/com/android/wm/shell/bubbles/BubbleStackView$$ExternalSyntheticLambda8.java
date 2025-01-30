package com.android.wm.shell.bubbles;

import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda8 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda8(BubbleStackView bubbleStackView, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleStackView;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                BubbleStackView bubbleStackView = this.f$0;
                BubbleData bubbleData = bubbleStackView.mBubbleData;
                bubbleData.mShowingOverflow = true;
                bubbleData.setSelectedBubble(bubbleStackView.mBubbleOverflow);
                bubbleStackView.mBubbleData.setExpanded(true);
                bubbleStackView.mBubbleSALogger.sendEventCDLog("QPNE0100", "app", "overflow bubble");
                break;
            case 1:
                BubbleStackView bubbleStackView2 = this.f$0;
                if (!bubbleStackView2.isManageEduVisible()) {
                    if (!bubbleStackView2.isStackEduVisible()) {
                        BubbleData bubbleData2 = bubbleStackView2.mBubbleData;
                        if (bubbleData2.mExpanded) {
                            bubbleData2.setExpanded(false);
                            break;
                        }
                    } else {
                        bubbleStackView2.mStackEduView.hide(false);
                        break;
                    }
                } else {
                    bubbleStackView2.mManageEduView.hide();
                    break;
                }
                break;
            default:
                BubbleStackView.$r8$lambda$Cn_cFsmoCiAxFL8cmUmWnqiwXJk(this.f$0);
                break;
        }
    }
}
