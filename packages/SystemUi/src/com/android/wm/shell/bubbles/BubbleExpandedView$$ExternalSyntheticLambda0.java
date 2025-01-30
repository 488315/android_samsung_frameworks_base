package com.android.wm.shell.bubbles;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleExpandedView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleExpandedView$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((BubbleExpandedView) this.f$0).mOverflowView.show();
                break;
            default:
                BubbleExpandedView bubbleExpandedView = BubbleExpandedView.this;
                bubbleExpandedView.mController.removeBubble(bubbleExpandedView.mBubble.mKey, 3);
                break;
        }
    }
}
