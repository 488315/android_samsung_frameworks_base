package com.android.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class MaxHeightFrameLayout extends FrameLayout {
    private int mMaxHeight;

    public MaxHeightFrameLayout(Context context) {
        this(context, null);
    }

    public MaxHeightFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaxHeightFrameLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MaxHeightFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaxHeight = Integer.MAX_VALUE;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MaxHeightFrameLayout, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.MaxHeightFrameLayout, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        setMaxHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(0, Integer.MAX_VALUE));
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    public void setMaxHeight(int i) {
        this.mMaxHeight = i;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getSize(i2) > this.mMaxHeight) {
            i2 = View.MeasureSpec.makeMeasureSpec(this.mMaxHeight, View.MeasureSpec.getMode(i2));
        }
        super.onMeasure(i, i2);
    }
}
