package com.android.wm.shell.bubbles;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda6(int i, int i2, Object obj) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BubbleData bubbleData = (BubbleData) this.f$0;
                int i = this.f$1;
                bubbleData.getClass();
                bubbleData.dismissBubbleWithKey(i, ((Bubble) obj).mKey);
                break;
            case 1:
                BubbleData bubbleData2 = (BubbleData) this.f$0;
                int i2 = this.f$1;
                bubbleData2.getClass();
                bubbleData2.dismissBubbleWithKey(i2, ((Bubble) obj).mKey);
                break;
            default:
                ArrayList arrayList = (ArrayList) this.f$0;
                Bubble bubble = (Bubble) obj;
                if (arrayList.size() < this.f$1) {
                    arrayList.add(bubble);
                    break;
                }
                break;
        }
    }
}
