package com.android.wm.shell.bubbles;

import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ BubbleController f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Bubble f$2;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda12(BubbleController bubbleController, boolean z, Bubble bubble) {
        this.f$0 = bubbleController;
        this.f$1 = z;
        this.f$2 = bubble;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        final BubbleController bubbleController = this.f$0;
        final boolean z = this.f$1;
        final Bubble bubble = this.f$2;
        final BubbleEntry bubbleEntry = (BubbleEntry) obj;
        bubbleController.getClass();
        bubbleController.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                BubbleController bubbleController2 = BubbleController.this;
                BubbleEntry bubbleEntry2 = bubbleEntry;
                boolean z2 = z;
                Bubble bubble2 = bubble;
                bubbleController2.getClass();
                if (bubbleEntry2 != null) {
                    bubbleController2.setIsBubble(bubbleEntry2, z2, bubble2.isEnabled(1));
                } else if (z2) {
                    Bubble orCreateBubble = bubbleController2.mBubbleData.getOrCreateBubble(null, bubble2);
                    bubbleController2.inflateAndAdd(orCreateBubble, orCreateBubble.isEnabled(1), !orCreateBubble.isEnabled(1));
                }
            }
        });
    }
}
