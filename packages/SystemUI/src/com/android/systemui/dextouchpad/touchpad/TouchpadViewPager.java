package com.android.systemui.dextouchpad.touchpad;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TouchpadViewPager extends ViewPager {
    public final Resources mResources;

    public TouchpadViewPager(Context context) {
        super(context);
        this.mResources = context.getResources();
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final void onMeasure(int i, int i2) {
        int min = Math.min((int) ((this.mResources.getConfiguration().screenHeightDp - 88) * this.mResources.getDisplayMetrics().density), this.mResources.getDimensionPixelSize(R.dimen.touchpad_small_popup_height));
        int size = View.MeasureSpec.getSize(i2);
        if (min > 0 && min < size) {
            i2 = View.MeasureSpec.makeMeasureSpec(min, View.MeasureSpec.getMode(i2));
        }
        super.onMeasure(i, i2);
    }

    public TouchpadViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mResources = context.getResources();
    }
}
