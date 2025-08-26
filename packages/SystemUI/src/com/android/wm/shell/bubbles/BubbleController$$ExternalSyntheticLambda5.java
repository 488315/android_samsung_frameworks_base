package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.Bubbles;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda5 implements Bubbles.BubbleMetadataFlagListener, Bubbles.PendingIntentCanceledListener, Bubbles.BubbleExpandListener {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda5(Object obj) {
        this.f$0 = obj;
    }

    @Override // com.android.wm.shell.bubbles.Bubbles.BubbleExpandListener
    public void onBubbleExpandChanged(String str, boolean z) {
        Bubbles.BubbleExpandListener bubbleExpandListener = (Bubbles.BubbleExpandListener) this.f$0;
        if (bubbleExpandListener != null) {
            bubbleExpandListener.onBubbleExpandChanged(str, z);
        }
    }

    @Override // com.android.wm.shell.bubbles.Bubbles.BubbleMetadataFlagListener
    public void onBubbleMetadataFlagChanged(Bubble bubble) {
        ((BubbleController) this.f$0).onBubbleMetadataFlagChanged(bubble);
    }
}
