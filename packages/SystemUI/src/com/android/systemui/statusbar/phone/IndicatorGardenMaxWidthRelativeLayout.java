package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class IndicatorGardenMaxWidthRelativeLayout extends RelativeLayout implements IndicatorGardenContainer {
    public int gardenMaxWidth;

    public IndicatorGardenMaxWidthRelativeLayout(Context context) {
        super(context);
        this.gardenMaxWidth = -1;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenContainer
    public final int getGardenWidth() {
        int paddingEnd = getPaddingEnd() + getPaddingStart();
        int measuredWidth = getMeasuredWidth();
        int width = getWidth();
        if (measuredWidth < width) {
            measuredWidth = width;
        }
        return measuredWidth + paddingEnd;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenContainer
    public final boolean isGardenVisible() {
        return getVisibility() == 0;
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int i3 = this.gardenMaxWidth;
        if (1 <= i3 && i3 < size) {
            i = View.MeasureSpec.makeMeasureSpec(i3, View.MeasureSpec.getMode(i));
        }
        super.onMeasure(i, i2);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenContainer
    public final void setGardenMaxWidth(int i) {
        if (this.gardenMaxWidth == i) {
            return;
        }
        this.gardenMaxWidth = i;
        requestLayout();
    }

    public IndicatorGardenMaxWidthRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.gardenMaxWidth = -1;
    }

    public IndicatorGardenMaxWidthRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.gardenMaxWidth = -1;
    }
}
