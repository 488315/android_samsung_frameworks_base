package com.android.systemui.statusbar.policy;

import android.view.View;
import java.util.Comparator;

public final /* synthetic */ class SmartReplyView$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        View view = (View) obj;
        View view2 = (View) obj2;
        int i = SmartReplyView.MEASURE_SPEC_ANY_LENGTH;
        return ((view2.getMeasuredWidth() - view2.getPaddingLeft()) - view2.getPaddingRight()) - ((view.getMeasuredWidth() - view.getPaddingLeft()) - view.getPaddingRight());
    }
}
