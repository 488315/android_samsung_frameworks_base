package com.android.internal.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ViewAnimator;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class DialogViewAnimator extends ViewAnimator {
    private final ArrayList<View> mMatchParentChildren;

    public DialogViewAnimator(Context context) {
        super(context);
        this.mMatchParentChildren = new ArrayList<>(1);
    }

    public DialogViewAnimator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMatchParentChildren = new ArrayList<>(1);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int childMeasureSpec;
        int childMeasureSpec2;
        int i3;
        boolean z = (View.MeasureSpec.getMode(i) == 1073741824 && View.MeasureSpec.getMode(i2) == 1073741824) ? false : true;
        int childCount = getChildCount();
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (getMeasureAllChildren() || childAt.getVisibility() != 8) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                boolean z2 = layoutParams.width == -1;
                boolean z3 = layoutParams.height == -1;
                if (z && (z2 || z3)) {
                    this.mMatchParentChildren.add(childAt);
                }
                measureChildWithMargins(childAt, i, 0, i2, 0);
                if (!z || z2) {
                    i3 = 0;
                } else {
                    int max = Math.max(i4, childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                    i3 = childAt.getMeasuredWidthAndState() & (-16777216);
                    i4 = max;
                }
                if (z && !z3) {
                    int max2 = Math.max(i5, childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                    i3 |= (childAt.getMeasuredHeightAndState() >> 16) & (-256);
                    i5 = max2;
                }
                i6 = combineMeasuredStates(i6, i3);
            }
        }
        int paddingLeft = i4 + getPaddingLeft() + getPaddingRight();
        int max3 = Math.max(i5 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight());
        int max4 = Math.max(paddingLeft, getSuggestedMinimumWidth());
        Drawable foreground = getForeground();
        if (foreground != null) {
            max3 = Math.max(max3, foreground.getMinimumHeight());
            max4 = Math.max(max4, foreground.getMinimumWidth());
        }
        setMeasuredDimension(resolveSizeAndState(max4, i, i6), resolveSizeAndState(max3, i2, i6 << 16));
        int size = this.mMatchParentChildren.size();
        for (int i8 = 0; i8 < size; i8++) {
            View view = this.mMatchParentChildren.get(i8);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (marginLayoutParams.width == -1) {
                childMeasureSpec = View.MeasureSpec.makeMeasureSpec((((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin, 1073741824);
            } else {
                childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width);
            }
            if (marginLayoutParams.height == -1) {
                childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec((((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin, 1073741824);
            } else {
                childMeasureSpec2 = getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
            }
            view.measure(childMeasureSpec, childMeasureSpec2);
        }
        this.mMatchParentChildren.clear();
    }
}
