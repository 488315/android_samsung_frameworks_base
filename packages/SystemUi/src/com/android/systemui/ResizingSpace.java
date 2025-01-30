package com.android.systemui;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ResizingSpace extends View {
    public final int mHeight;
    public final int mWidth;

    public ResizingSpace(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (getVisibility() == 0) {
            setVisibility(4);
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewGroup_Layout);
        this.mWidth = obtainStyledAttributes.getResourceId(0, 0);
        this.mHeight = obtainStyledAttributes.getResourceId(1, 0);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z;
        int dimensionPixelOffset;
        int dimensionPixelOffset2;
        super.onConfigurationChanged(configuration);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        boolean z2 = true;
        if (this.mWidth <= 0 || (dimensionPixelOffset2 = getContext().getResources().getDimensionPixelOffset(this.mWidth)) == layoutParams.width) {
            z = false;
        } else {
            layoutParams.width = dimensionPixelOffset2;
            z = true;
        }
        if (this.mHeight <= 0 || (dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(this.mHeight)) == layoutParams.height) {
            z2 = z;
        } else {
            layoutParams.height = dimensionPixelOffset;
        }
        if (z2) {
            setLayoutParams(layoutParams);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int suggestedMinimumWidth = getSuggestedMinimumWidth();
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode == Integer.MIN_VALUE) {
            suggestedMinimumWidth = Math.min(suggestedMinimumWidth, size);
        } else if (mode == 1073741824) {
            suggestedMinimumWidth = size;
        }
        int suggestedMinimumHeight = getSuggestedMinimumHeight();
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode2 == Integer.MIN_VALUE) {
            suggestedMinimumHeight = Math.min(suggestedMinimumHeight, size2);
        } else if (mode2 == 1073741824) {
            suggestedMinimumHeight = size2;
        }
        setMeasuredDimension(suggestedMinimumWidth, suggestedMinimumHeight);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
    }
}
