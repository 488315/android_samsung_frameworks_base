package com.android.wm.shell.bubbles;

import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.Collections;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleController.BubblesImpl f$0;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda5(BubbleController.BubblesImpl bubblesImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = bubblesImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BubbleController.BubblesImpl bubblesImpl = this.f$0;
        switch (i) {
            case 0:
                BubbleController bubbleController = BubbleController.this;
                BubbleData bubbleData = bubbleController.mBubbleData;
                for (Bubble bubble : Collections.unmodifiableList(bubbleData.mBubbles)) {
                    bubbleData.dismissBubbleWithKey(4, bubble.mKey);
                    bubbleController.setIsBubble(bubble, false);
                }
                break;
            case 1:
                BubbleController bubbleController2 = BubbleController.this;
                if (bubbleController2.hasBubbles() && ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 4892874825176268090L, 0, null);
                }
                for (Bubble bubble2 : Collections.unmodifiableList(bubbleController2.mBubbleData.mBubbles)) {
                    bubble2.setShowDot(bubble2.showInShade());
                }
                break;
            case 2:
                BubbleController.this.collapseStack();
                break;
            default:
                BubbleController.this.mBubbleData.dismissAll(2);
                break;
        }
    }
}
