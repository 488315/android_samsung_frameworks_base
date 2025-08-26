package com.android.systemui.statusbar.chips.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* loaded from: classes3.dex */
public final class SecondaryOngoingActivityChip extends FrameLayout {
    public SecondaryOngoingActivityChip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (getMeasuredWidth() < getMinimumWidth()) {
            setVisibility(8);
            setMeasuredDimension(0, 0);
        }
    }
}
