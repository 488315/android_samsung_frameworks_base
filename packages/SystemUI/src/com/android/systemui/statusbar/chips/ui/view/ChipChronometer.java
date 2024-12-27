package com.android.systemui.statusbar.chips.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ChipChronometer extends Chronometer {
    public int minimumTextWidth;
    public boolean shouldHideText;

    public ChipChronometer(Context context) {
        this(context, null, 0, 6, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        if (this.shouldHideText) {
            setMeasuredDimension(0, 0);
            return;
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(0, 0), i2);
        int measuredWidth = getMeasuredWidth();
        if (measuredWidth > Chronometer.resolveSize(measuredWidth, i)) {
            this.shouldHideText = true;
            setVisibility(8);
            setMeasuredDimension(0, 0);
        } else {
            int i3 = this.minimumTextWidth;
            if (measuredWidth < i3) {
                measuredWidth = i3;
            }
            this.minimumTextWidth = measuredWidth;
            setMeasuredDimension(measuredWidth, View.MeasureSpec.getSize(i2));
        }
    }

    @Override // android.widget.Chronometer
    public final void setBase(long j) {
        this.minimumTextWidth = 0;
        this.shouldHideText = false;
        setVisibility(0);
        super.setBase(j);
    }

    public ChipChronometer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ ChipChronometer(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public ChipChronometer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
