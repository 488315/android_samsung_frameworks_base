package com.android.p038wm.shell.bubbles;

import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.p038wm.shell.bubbles.BubbleData;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleController.BubblesImpl f$0;
    public final /* synthetic */ BubbleEntry f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda2(BubbleController.BubblesImpl bubblesImpl, BubbleEntry bubbleEntry, int i) {
        this.$r8$classId = i;
        this.f$0 = bubblesImpl;
        this.f$1 = bubbleEntry;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubbleController.BubblesImpl bubblesImpl = this.f$0;
                BubbleEntry bubbleEntry = this.f$1;
                bubblesImpl.getClass();
                boolean z = BubbleController.BUBBLE_BAR_ENABLED;
                BubbleController bubbleController = BubbleController.this;
                if (bubbleController.isSummaryOfBubbles(bubbleEntry)) {
                    String groupKey = bubbleEntry.mSbn.getGroupKey();
                    BubbleData bubbleData = bubbleController.mBubbleData;
                    bubbleData.mSuppressedGroupKeys.remove(groupKey);
                    BubbleData.Update update = bubbleData.mStateChange;
                    update.suppressedSummaryChanged = true;
                    update.suppressedSummaryGroup = groupKey;
                    bubbleData.dispatchPendingChanges();
                    ArrayList bubblesInGroup = bubbleController.getBubblesInGroup(groupKey);
                    for (int i = 0; i < bubblesInGroup.size(); i++) {
                        bubbleController.removeBubble(((Bubble) bubblesInGroup.get(i)).mKey, 9);
                    }
                    break;
                } else {
                    bubbleController.removeBubble(bubbleEntry.getKey(), 5);
                    break;
                }
            case 1:
                BubbleController.BubblesImpl bubblesImpl2 = this.f$0;
                BubbleEntry bubbleEntry2 = this.f$1;
                BubbleController bubbleController2 = BubbleController.this;
                if (BubbleController.canLaunchInTaskView(bubbleController2.mContext, bubbleEntry2)) {
                    bubbleController2.updateBubble(bubbleEntry2);
                }
                String groupKey2 = bubbleEntry2.mSbn.getGroupKey();
                if (bubbleController2.isSummaryOfBubbles(bubbleEntry2)) {
                    BubbleData bubbleData2 = bubbleController2.mBubbleData;
                    if (bubbleData2.isSummarySuppressed(groupKey2)) {
                        bubbleData2.mSuppressedGroupKeys.remove(groupKey2);
                        BubbleData.Update update2 = bubbleData2.mStateChange;
                        update2.suppressedSummaryChanged = true;
                        update2.suppressedSummaryGroup = groupKey2;
                        bubbleData2.dispatchPendingChanges();
                        break;
                    }
                }
                break;
            default:
                BubbleController.BubblesImpl bubblesImpl3 = this.f$0;
                BubbleController.this.expandStackAndSelectBubble(this.f$1);
                break;
        }
    }
}
