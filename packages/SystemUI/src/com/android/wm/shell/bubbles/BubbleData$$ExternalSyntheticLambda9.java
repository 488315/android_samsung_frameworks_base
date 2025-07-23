package com.android.wm.shell.bubbles;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda9(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                BubbleData bubbleData = (BubbleData) obj2;
                Comparator comparator = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                bubbleData.getClass();
                bubbleData.doRemove(2, ((Bubble) obj).mKey);
                break;
            case 1:
                BubbleData bubbleData2 = (BubbleData) obj2;
                Comparator comparator2 = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                bubbleData2.getClass();
                bubbleData2.dismissBubbleWithKey(13, ((Bubble) obj).mKey);
                break;
            case 2:
                BubbleData bubbleData3 = (BubbleData) obj2;
                Comparator comparator3 = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                bubbleData3.getClass();
                bubbleData3.dismissBubbleWithKey(12, ((Bubble) obj).mKey);
                break;
            default:
                ((List) obj2).add((Bubble) obj);
                break;
        }
    }
}
