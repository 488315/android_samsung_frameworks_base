package com.android.p038wm.shell.bubbles;

import android.service.notification.NotificationListenerService;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.systemui.wmshell.BubblesManager;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda11(Object obj, Object obj2, int i, Object obj3) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubbleController bubbleController = (BubbleController) this.f$0;
                List<BubbleEntry> list = (List) this.f$1;
                BubbleController.UserBubbleData userBubbleData = (BubbleController.UserBubbleData) this.f$2;
                bubbleController.getClass();
                for (BubbleEntry bubbleEntry : list) {
                    if (BubbleController.canLaunchInTaskView(bubbleController.mContext, bubbleEntry)) {
                        bubbleController.updateBubble(bubbleEntry, true, ((Boolean) ((HashMap) userBubbleData.mKeyToShownInShadeMap).get(bubbleEntry.getKey())).booleanValue());
                    }
                }
                break;
            case 1:
                BubbleController bubbleController2 = (BubbleController) this.f$0;
                BubbleEntry bubbleEntry2 = (BubbleEntry) this.f$1;
                Bubble bubble = (Bubble) this.f$2;
                bubbleController2.getClass();
                if (bubbleEntry2 != null && bubbleController2.getBubblesInGroup(bubbleEntry2.mSbn.getGroupKey()).isEmpty()) {
                    BubblesManager.C37394 c37394 = bubbleController2.mSysuiProxy;
                    String str = bubble.mKey;
                    c37394.getClass();
                    break;
                }
                break;
            default:
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) this.f$0;
                BubbleController.this.onRankingUpdated((NotificationListenerService.RankingMap) this.f$1, (HashMap) this.f$2);
                break;
        }
    }
}
