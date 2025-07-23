package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController.AnonymousClass2;
import com.android.wm.shell.bubbles.BubbleViewInfoTask;
import com.android.wm.shell.onehanded.OneHandedController;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda9(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((ArrayList) ((OneHandedController) obj).mDisplayAreaOrganizer.mTransitionCallbacks).add(((BubbleController) this.f$0).new AnonymousClass2());
                break;
            case 1:
                final BubbleController bubbleController = (BubbleController) this.f$0;
                final Bubble bubble = (Bubble) obj;
                bubbleController.getClass();
                if (!bubbleController.mBubbleData.hasAnyBubbleWithKey(bubble.mKey)) {
                    bubble.inflate(new BubbleViewInfoTask.Callback() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda22
                        @Override // com.android.wm.shell.bubbles.BubbleViewInfoTask.Callback
                        public final void onBubbleViewsReady(Bubble bubble2) {
                            BubbleController.this.mBubbleData.overflowBubble(15, bubble);
                        }
                    }, bubbleController.mContext, bubbleController.mExpandedViewManager, bubbleController.mBubbleTaskViewFactory, bubbleController.mBubblePositioner, bubbleController.mStackView, null, bubbleController.mBubbleIconFactory, bubbleController.mBubbleBadgeIconFactory, true);
                    break;
                }
                break;
            default:
                int i = BubbleController.IBubblesImpl.$r8$clinit;
                ((BubbleController) obj).getClass();
                break;
        }
    }
}
