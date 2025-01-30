package com.android.p038wm.shell.bubbles;

import com.android.p038wm.shell.bubbles.BubbleController;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleController$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BubbleController$3$$ExternalSyntheticLambda0(int i, int i2, Object obj) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        switch (this.$r8$classId) {
            case 0:
                BubbleController.C37913 c37913 = (BubbleController.C37913) this.f$0;
                int i2 = this.f$1;
                BubbleController bubbleController = BubbleController.this;
                BubbleStackView bubbleStackView = bubbleController.mStackView;
                if (bubbleStackView != null && bubbleStackView.getExpandedBubble() != null && bubbleController.isStackExpanded()) {
                    BubbleStackView bubbleStackView2 = bubbleController.mStackView;
                    if (!bubbleStackView2.mIsExpansionAnimating && !bubbleStackView2.mIsBubbleSwitchAnimating) {
                        i = bubbleStackView2.getExpandedBubble().getTaskId();
                        if (i == -1 && i != i2) {
                            bubbleController.mBubbleData.setExpanded(false);
                            break;
                        }
                    }
                }
                i = -1;
                if (i == -1) {
                }
                break;
            default:
                ((IntConsumer) this.f$0).accept(this.f$1);
                break;
        }
    }
}
