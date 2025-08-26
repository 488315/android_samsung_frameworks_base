package com.android.wm.shell.bubbles;

import android.graphics.Point;
import android.service.notification.NotificationListenerService;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleTransitions;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda13 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda13(Object obj, Object obj2, int i, Object obj3) {
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
                    if (bubbleController.canLaunchInTaskView(bubbleController.mContext, bubbleEntry)) {
                        bubbleController.updateBubble(bubbleEntry, true, ((Boolean) ((HashMap) userBubbleData.mKeyToShownInShadeMap).get(bubbleEntry.mSbn.getKey())).booleanValue());
                    }
                }
                break;
            case 1:
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) this.f$0;
                BubbleController.this.onRankingUpdated((NotificationListenerService.RankingMap) this.f$1, (HashMap) this.f$2);
                break;
            default:
                BubbleController.IBubblesImpl iBubblesImpl = (BubbleController.IBubblesImpl) this.f$0;
                String str = (String) this.f$1;
                Point point = (Point) this.f$2;
                BubbleController bubbleController2 = iBubblesImpl.mController;
                Bubble bubbleInStackWithKey = bubbleController2.mBubbleData.getBubbleInStackWithKey(str);
                BubbleTransitions bubbleTransitions = bubbleController2.mBubbleTransitions;
                bubbleTransitions.getClass();
                new BubbleTransitions.DraggedBubbleIconToFullscreen(bubbleTransitions, bubbleInStackWithKey, point);
                break;
        }
    }
}
