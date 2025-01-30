package com.google.android.setupdesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.google.android.setupdesign.R$styleable;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FillContentLayout extends FrameLayout {
    public int maxHeight;
    public int maxWidth;

    public FillContentLayout(Context context) {
        this(context, null);
    }

    public static int getMaxSizeMeasureSpec(int i, int i2, int i3) {
        int max = Math.max(0, i - i2);
        if (i3 >= 0) {
            return View.MeasureSpec.makeMeasureSpec(i3, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        }
        if (i3 == -1) {
            return View.MeasureSpec.makeMeasureSpec(max, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        }
        if (i3 == -2) {
            return View.MeasureSpec.makeMeasureSpec(max, VideoPlayer.MEDIA_ERROR_SYSTEM);
        }
        return 0;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        setMeasuredDimension(FrameLayout.getDefaultSize(getSuggestedMinimumWidth(), i), FrameLayout.getDefaultSize(getSuggestedMinimumHeight(), i2));
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
            childAt.measure(getMaxSizeMeasureSpec(Math.min(this.maxWidth, measuredWidth), getPaddingRight() + getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width), getMaxSizeMeasureSpec(Math.min(this.maxHeight, measuredHeight), getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height));
        }
    }

    public FillContentLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.sudFillContentLayoutStyle);
    }

    public FillContentLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (isInEditMode()) {
            return;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudFillContentLayout, i, 0);
        this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(1, -1);
        this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        obtainStyledAttributes.recycle();
    }
}
