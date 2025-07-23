package com.android.systemui.dextouchpad.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class MaxLargeButton extends Button {
    public MaxLargeButton(Context context) {
        super(context);
        setTextSizeInternal(context);
    }

    public final void setTextSizeInternal(Context context) {
        float f = context.getResources().getConfiguration().fontScale;
        float textSize = getTextSize() / context.getResources().getDisplayMetrics().scaledDensity;
        if (f > 1.2f) {
            f = 1.2f;
        }
        setTextSize(1, textSize * f);
    }

    public MaxLargeButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setTextSizeInternal(context);
    }

    public MaxLargeButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setTextSizeInternal(context);
    }
}
