package com.android.systemui.statusbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;

public final class KeyboardShortcutKeysLayout extends ViewGroup {
    public final Context mContext;
    public int mLineHeight;

    public KeyboardShortcutKeysLayout(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        int applyDimension = (int) TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics());
        return new LayoutParams(applyDimension, applyDimension);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        int applyDimension = (int) TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics());
        return new LayoutParams(applyDimension, applyDimension, layoutParams);
    }

    public final boolean isRTL() {
        return MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.mContext) == 1;
    }

    public final void layoutChildrenOnRow(int i, int i2, int i3, int i4, int i5, int i6) {
        if (!isRTL()) {
            i4 = ((getPaddingLeft() + i3) - i4) + i6;
        }
        int i7 = i;
        while (i7 < i2) {
            View childAt = getChildAt(i7);
            int measuredWidth = childAt.getMeasuredWidth();
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (isRTL() && i7 == i) {
                i4 = (((i3 - i4) - getPaddingRight()) - measuredWidth) - layoutParams.mHorizontalSpacing;
            }
            childAt.layout(i4, i5, i4 + measuredWidth, childAt.getMeasuredHeight() + i5);
            if (isRTL()) {
                i4 -= (i7 < i2 + (-1) ? getChildAt(i7 + 1).getMeasuredWidth() : 0) + layoutParams.mHorizontalSpacing;
            } else {
                i4 = measuredWidth + layoutParams.mHorizontalSpacing + i4;
            }
            i7++;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int i5 = i3 - i;
        int paddingRight = isRTL() ? i5 - getPaddingRight() : getPaddingLeft();
        int paddingTop = getPaddingTop();
        int i6 = paddingRight;
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (!isRTL() ? i6 + measuredWidth > i5 : (i6 - getPaddingLeft()) - measuredWidth < 0) {
                    layoutChildrenOnRow(i7, i9, i5, i6, paddingTop, i8);
                    i6 = isRTL() ? i5 - getPaddingRight() : getPaddingLeft();
                    paddingTop += this.mLineHeight;
                    i7 = i9;
                }
                i6 = isRTL() ? (i6 - measuredWidth) - layoutParams.mHorizontalSpacing : i6 + measuredWidth + layoutParams.mHorizontalSpacing;
                i8 = layoutParams.mHorizontalSpacing;
            }
        }
        if (i7 < childCount) {
            layoutChildrenOnRow(i7, childCount, i5, i6, paddingTop, i8);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        int size = (View.MeasureSpec.getSize(i) - getPaddingLeft()) - getPaddingRight();
        int childCount = getChildCount();
        int size2 = (View.MeasureSpec.getSize(i2) - getPaddingTop()) - getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int makeMeasureSpec = View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE ? View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE) : View.MeasureSpec.makeMeasureSpec(0, 0);
        int i4 = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                childAt.measure(View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), makeMeasureSpec);
                int measuredWidth = childAt.getMeasuredWidth();
                i4 = Math.max(i4, childAt.getMeasuredHeight() + layoutParams.mVerticalSpacing);
                if (paddingLeft + measuredWidth > size) {
                    paddingLeft = getPaddingLeft();
                    paddingTop += i4;
                }
                paddingLeft = measuredWidth + layoutParams.mHorizontalSpacing + paddingLeft;
            }
        }
        this.mLineHeight = i4;
        if (View.MeasureSpec.getMode(i2) == 0) {
            size2 = paddingTop + i4;
        } else if (View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE && (i3 = paddingTop + i4) < size2) {
            size2 = i3;
        }
        setMeasuredDimension(size, size2);
    }

    public final class LayoutParams extends ViewGroup.LayoutParams {
        public final int mHorizontalSpacing;
        public final int mVerticalSpacing;

        public LayoutParams(int i, int i2, ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mHorizontalSpacing = i;
            this.mVerticalSpacing = i2;
        }

        public LayoutParams(int i, int i2) {
            super(0, 0);
            this.mHorizontalSpacing = i;
            this.mVerticalSpacing = i2;
        }
    }

    public KeyboardShortcutKeysLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }
}
