package com.google.android.setupdesign.view;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class WrapTextView extends AppCompatTextView {
    public WrapTextView(Context context) {
        super(context);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int wrapMeasure = wrapMeasure(i);
        if (wrapMeasure != i) {
            super.onMeasure(wrapMeasure, i2);
        }
    }

    public int wrapMeasure(int i) {
        Layout layout;
        int lineCount;
        if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE && (lineCount = (layout = getLayout()).getLineCount()) > 1) {
            float f = 0.0f;
            for (int i2 = 0; i2 < lineCount; i2++) {
                f = Math.max(f, layout.getLineWidth(i2));
            }
            int totalPaddingRight = getTotalPaddingRight() + getTotalPaddingLeft() + ((int) Math.ceil(f));
            if (totalPaddingRight < getMeasuredWidth()) {
                return View.MeasureSpec.makeMeasureSpec(totalPaddingRight, Integer.MIN_VALUE);
            }
        }
        return i;
    }

    public WrapTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WrapTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
