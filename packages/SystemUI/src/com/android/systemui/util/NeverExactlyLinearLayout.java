package com.android.systemui.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import kotlin.Triple;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NeverExactlyLinearLayout extends LinearLayout {
    public static final int $stable = 0;

    public NeverExactlyLinearLayout(Context context) {
        this(context, null, 0, 6, null);
    }

    private final Triple<Boolean, Integer, Integer> getNonExactlyMeasureSpec(int i) {
        boolean z = View.MeasureSpec.getMode(i) == 1073741824;
        int size = View.MeasureSpec.getSize(i);
        if (z) {
            i = View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
        }
        return new Triple<>(Boolean.valueOf(z), Integer.valueOf(i), Integer.valueOf(size));
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        Triple<Boolean, Integer, Integer> nonExactlyMeasureSpec = getNonExactlyMeasureSpec(i);
        boolean zBooleanValue = ((Boolean) nonExactlyMeasureSpec.component1()).booleanValue();
        int iIntValue = ((Number) nonExactlyMeasureSpec.component2()).intValue();
        int iIntValue2 = ((Number) nonExactlyMeasureSpec.component3()).intValue();
        Triple<Boolean, Integer, Integer> nonExactlyMeasureSpec2 = getNonExactlyMeasureSpec(i2);
        boolean zBooleanValue2 = ((Boolean) nonExactlyMeasureSpec2.component1()).booleanValue();
        int iIntValue3 = ((Number) nonExactlyMeasureSpec2.component2()).intValue();
        int iIntValue4 = ((Number) nonExactlyMeasureSpec2.component3()).intValue();
        super.onMeasure(iIntValue, iIntValue3);
        if (zBooleanValue || zBooleanValue2) {
            if (!zBooleanValue) {
                iIntValue2 = getMeasuredWidth();
            }
            if (!zBooleanValue2) {
                iIntValue4 = getMeasuredHeight();
            }
            setMeasuredDimension(iIntValue2, iIntValue4);
        }
    }

    public NeverExactlyLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ NeverExactlyLinearLayout(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public NeverExactlyLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
