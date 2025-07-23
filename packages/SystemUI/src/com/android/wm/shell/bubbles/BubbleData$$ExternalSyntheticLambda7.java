package com.android.wm.shell.bubbles;

import android.content.LocusId;
import android.view.View;
import java.util.Comparator;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda7 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda7(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return !((Bubble) obj).equals(((BubbleData) obj2).mSelectedBubble);
            case 1:
                Comparator comparator = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                return ((LocusId) obj2).equals(((Bubble) obj).mLocusId);
            default:
                View view = (View) obj2;
                Comparator comparator2 = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                BadgedImageView badgedImageView = ((Bubble) obj).mIconView;
                return badgedImageView != null && badgedImageView.equals(view);
        }
    }
}
