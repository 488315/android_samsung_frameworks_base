package com.sec.android.secsetupwizardlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BottomScrollView extends ScrollView {
    public BottomScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    public final void onScrollChanged(int i, int i2, int i3, int i4) {
        if (isAttachedToWindow()) {
            getChildAt(getChildCount() - 1).getBottom();
            getHeight();
            getScrollY();
        }
        super.onScrollChanged(i, i2, i3, i4);
    }

    public BottomScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BottomScrollView(Context context) {
        super(context);
    }
}
