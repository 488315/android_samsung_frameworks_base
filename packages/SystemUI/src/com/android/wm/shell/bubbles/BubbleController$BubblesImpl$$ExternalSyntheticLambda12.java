package com.android.wm.shell.bubbles;

import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda1;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleData;
import java.util.List;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda12 implements Supplier {
    public final /* synthetic */ BubbleController.BubblesImpl f$0;
    public final /* synthetic */ BubbleEntry f$1;
    public final /* synthetic */ List f$2;
    public final /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda11 f$3;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda12(BubbleController.BubblesImpl bubblesImpl, BubbleEntry bubbleEntry, List list, BubbleController$BubblesImpl$$ExternalSyntheticLambda11 bubbleController$BubblesImpl$$ExternalSyntheticLambda11) {
        this.f$0 = bubblesImpl;
        this.f$1 = bubbleEntry;
        this.f$2 = list;
        this.f$3 = bubbleController$BubblesImpl$$ExternalSyntheticLambda11;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        BubbleController.BubblesImpl bubblesImpl = this.f$0;
        BubbleEntry bubbleEntry = this.f$1;
        List list = this.f$2;
        BubbleController$BubblesImpl$$ExternalSyntheticLambda11 bubbleController$BubblesImpl$$ExternalSyntheticLambda11 = this.f$3;
        BubbleController bubbleController = BubbleController.this;
        boolean zIsSummaryOfBubbles = bubbleController.isSummaryOfBubbles(bubbleEntry);
        boolean z = true;
        BubbleData bubbleData = bubbleController.mBubbleData;
        if (!zIsSummaryOfBubbles) {
            Bubble bubbleInStackWithKey = bubbleData.getBubbleInStackWithKey(bubbleEntry.mSbn.getKey());
            if (bubbleInStackWithKey == null || !bubbleEntry.isBubble()) {
                bubbleInStackWithKey = bubbleData.getOverflowBubbleWithKey(bubbleEntry.mSbn.getKey());
            }
            if (bubbleInStackWithKey != null) {
                bubbleInStackWithKey.setSuppressNotification(true);
                bubbleInStackWithKey.setShowDot(false);
                BubblesManager.AnonymousClass5 anonymousClass5 = bubbleController.mSysuiProxy;
                anonymousClass5.val$sysuiMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda1(anonymousClass5, "BubbleController.handleDismissalInterception", 3));
            }
            return Boolean.valueOf(z);
        }
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                BubbleEntry bubbleEntry2 = (BubbleEntry) list.get(i);
                if (bubbleData.hasAnyBubbleWithKey(bubbleEntry2.mSbn.getKey())) {
                    Bubble anyBubbleWithKey = bubbleData.getAnyBubbleWithKey(bubbleEntry2.mSbn.getKey());
                    if (anyBubbleWithKey != null) {
                        anyBubbleWithKey.setSuppressNotification(true);
                        anyBubbleWithKey.setShowDot(false);
                    }
                } else {
                    bubbleController$BubblesImpl$$ExternalSyntheticLambda11.accept(i);
                }
            }
        }
        bubbleController$BubblesImpl$$ExternalSyntheticLambda11.accept(-1);
        String groupKey = bubbleEntry.mSbn.getGroupKey();
        bubbleData.mSuppressedGroupKeys.put(groupKey, bubbleEntry.mSbn.getKey());
        BubbleData.Update update = bubbleData.mStateChange;
        update.suppressedSummaryChanged = true;
        update.suppressedSummaryGroup = groupKey;
        bubbleData.dispatchPendingChanges();
        z = false;
        return Boolean.valueOf(z);
    }
}
