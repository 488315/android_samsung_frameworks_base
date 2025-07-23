package com.android.systemui.controls.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsCard extends RelativeLayout {
    public ControlsCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i);
        getLayoutParams().height = getWidth();
    }

    public ControlsCard(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ControlsCard(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
