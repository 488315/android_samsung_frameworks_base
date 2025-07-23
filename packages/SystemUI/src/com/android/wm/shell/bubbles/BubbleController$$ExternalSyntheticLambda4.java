package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.BubbleController;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda4(BubbleController bubbleController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleController;
        this.f$1 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BubbleController bubbleController = this.f$0;
                bubbleController.mMainExecutor.execute(new BubbleController$$ExternalSyntheticLambda13(bubbleController, (List) obj, 0, (BubbleController.UserBubbleData) this.f$1));
                break;
            default:
                BubbleController bubbleController2 = this.f$0;
                bubbleController2.getClass();
                bubbleController2.mMainExecutor.execute(new BubbleController$$ExternalSyntheticLambda14(1, (BubbleStackView$$ExternalSyntheticLambda29) this.f$1, (Boolean) obj));
                break;
        }
    }
}
