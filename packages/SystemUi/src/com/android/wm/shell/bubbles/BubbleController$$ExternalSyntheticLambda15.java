package com.android.wm.shell.bubbles;

import android.util.SparseArray;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda1;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda15 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda15(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubbleController bubbleController = (BubbleController) this.f$0;
                Bubble bubble = (Bubble) this.f$1;
                bubbleController.getClass();
                bubbleController.removeBubble(bubble.mKey, 10);
                break;
            case 1:
                ((Consumer) this.f$0).accept((Boolean) this.f$1);
                break;
            case 2:
                BubbleController.this.mBubbleSALogger = (BubblesManager$$ExternalSyntheticLambda1) this.f$1;
                break;
            case 3:
                BubbleController.this.mSysuiProxy = (BubblesManager.C37394) this.f$1;
                break;
            case 4:
                BubbleController.this.mCurrentProfiles = (SparseArray) this.f$1;
                break;
            case 5:
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) this.f$0;
                Bubble bubble2 = (Bubble) this.f$1;
                BubbleController bubbleController2 = BubbleController.this;
                if (bubble2 != null) {
                    BubbleData bubbleData = bubbleController2.mBubbleData;
                    String str = bubble2.mKey;
                    if (!bubbleData.hasBubbleInStackWithKey(str)) {
                        if (bubbleData.hasOverflowBubbleWithKey(str)) {
                            bubbleController2.promoteBubbleFromOverflow(bubble2);
                            break;
                        }
                    } else {
                        bubbleData.setSelectedBubble(bubble2);
                        bubbleData.setExpanded(true);
                        break;
                    }
                } else {
                    bubbleController2.getClass();
                    break;
                }
                break;
            case 6:
                BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) this.f$0;
                BubbleController.this.setExpandListener((Bubbles.BubbleExpandListener) this.f$1);
                break;
            default:
                BubbleController.IBubblesImpl iBubblesImpl = (BubbleController.IBubblesImpl) this.f$0;
                iBubblesImpl.mListener.register((IBubblesListener) this.f$1);
                break;
        }
    }
}
