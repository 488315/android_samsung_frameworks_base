package com.android.wm.shell.bubbles;

import java.util.Comparator;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda1(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        String str = this.f$0;
        Bubble bubble = (Bubble) obj;
        switch (i) {
            case 0:
                Comparator comparator = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                break;
            case 1:
                Comparator comparator2 = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                break;
            case 2:
                Comparator comparator3 = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                break;
            default:
                Comparator comparator4 = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                break;
        }
        return bubble.mKey.equals(str);
    }
}
