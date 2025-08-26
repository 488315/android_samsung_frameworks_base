package com.android.wm.shell.bubbles.bar;

import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubbleViewProvider;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedViewDragController;
import com.android.wm.shell.bubbles.bar.BubbleEducationViewController;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleBarLayerView$$ExternalSyntheticLambda0 implements BubbleEducationViewController.Listener, BubbleBarExpandedViewDragController.DragListener {
    public final /* synthetic */ BubbleBarLayerView f$0;

    public /* synthetic */ BubbleBarLayerView$$ExternalSyntheticLambda0(BubbleBarLayerView bubbleBarLayerView) {
        this.f$0 = bubbleBarLayerView;
    }

    public void onReleased(boolean z) {
        BubbleBarLayerView bubbleBarLayerView = this.f$0;
        if (!z) {
            int i = BubbleBarLayerView.$r8$clinit;
            bubbleBarLayerView.getClass();
            return;
        }
        BubbleViewProvider bubbleViewProvider = bubbleBarLayerView.mExpandedBubble;
        if (bubbleViewProvider != null) {
            BubbleController bubbleController = bubbleBarLayerView.mBubbleController;
            bubbleController.mBubbleData.dismissBubbleWithKey(1, bubbleViewProvider.getKey());
            BubbleLogger.Event event = BubbleLogger.Event.BUBBLE_BAR_BUBBLE_DISMISSED_DRAG_EXP_VIEW;
            BubbleViewProvider bubbleViewProvider2 = bubbleBarLayerView.mExpandedBubble;
            if (bubbleViewProvider2 == null || !(bubbleViewProvider2 instanceof Bubble)) {
                return;
            }
            bubbleBarLayerView.mBubbleLogger.log((Bubble) bubbleViewProvider2, event);
        }
    }
}
