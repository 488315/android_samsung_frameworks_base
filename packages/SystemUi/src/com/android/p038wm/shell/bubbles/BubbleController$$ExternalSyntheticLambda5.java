package com.android.p038wm.shell.bubbles;

import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.p038wm.shell.bubbles.SecHideInformationMirroringController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda5 implements Bubbles.BubbleMetadataFlagListener, Bubbles.PendingIntentCanceledListener, SecHideInformationMirroringController.HideInformationMirroringCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleController f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda5(BubbleController bubbleController, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleController;
    }

    public final void onBubbleMetadataFlagChanged(Bubble bubble) {
        int i = this.$r8$classId;
        BubbleController bubbleController = this.f$0;
        switch (i) {
            case 0:
                bubbleController.onBubbleMetadataFlagChanged(bubble);
                break;
            default:
                bubbleController.onBubbleMetadataFlagChanged(bubble);
                break;
        }
    }
}
