package com.android.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class ButtonBarLayout extends LinearLayout {
    private static final String IS_DIVIDER = "isDivider";
    private static final int PEEK_BUTTON_DP = 16;
    private boolean mAllowStacking;
    private boolean mIsDeviceDefault;
    private int mLastWidthSize;
    private int mMinimumHeight;

    public ButtonBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastWidthSize = -1;
        this.mMinimumHeight = 0;
        this.mIsDeviceDefault = false;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, false);
        if (typedValue.data != 0) {
            this.mIsDeviceDefault = true;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ButtonBarLayout);
        this.mAllowStacking = obtainStyledAttributes.getBoolean(0, true);
        obtainStyledAttributes.recycle();
    }

    public void setAllowStacking(boolean z) {
        if (this.mAllowStacking != z) {
            this.mAllowStacking = z;
            if (!z && getOrientation() == 1) {
                setStacked(false);
            }
            requestLayout();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        boolean z;
        int paddingBottom;
        int size = View.MeasureSpec.getSize(i);
        int i4 = 0;
        if (this.mAllowStacking) {
            if (size > this.mLastWidthSize && isStacked()) {
                setStacked(false);
                if (this.mIsDeviceDefault) {
                    setDividerVisible(getNextVisibleChildIndex(0));
                }
            }
            this.mLastWidthSize = size;
        }
        if (isStacked() || View.MeasureSpec.getMode(i) != 1073741824) {
            i3 = i;
            z = false;
        } else {
            i3 = View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
            z = true;
        }
        super.onMeasure(i3, i2);
        if (this.mAllowStacking && !isStacked() && (getMeasuredWidthAndState() & (-16777216)) == 16777216) {
            setStacked(true);
            if (this.mIsDeviceDefault) {
                setDividerInvisible(0);
                setGravity(17);
            }
            z = true;
        }
        if (z) {
            super.onMeasure(i, i2);
        }
        int nextVisibleChildIndex = getNextVisibleChildIndex(0);
        if (nextVisibleChildIndex >= 0) {
            View childAt = getChildAt(nextVisibleChildIndex);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            int paddingTop = getPaddingTop() + childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (isStacked()) {
                if (this.mIsDeviceDefault) {
                    paddingBottom = getPaddingBottom();
                } else {
                    i4 = getNextVisibleChildIndex(nextVisibleChildIndex + 1) >= 0 ? (int) (paddingTop + getChildAt(r6).getPaddingTop() + (getResources().getDisplayMetrics().density * 16.0f)) : paddingTop;
                }
            } else {
                paddingBottom = getPaddingBottom();
            }
            i4 = paddingTop + paddingBottom;
        }
        if (getMinimumHeight() != i4) {
            setMinimumHeight(i4);
        }
    }

    private int getNextVisibleChildIndex(int i) {
        int childCount = getChildCount();
        while (i < childCount) {
            if (getChildAt(i).getVisibility() == 0 && (!this.mIsDeviceDefault || (getChildAt(i) instanceof Button))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override // android.view.View
    public int getMinimumHeight() {
        return Math.max(this.mMinimumHeight, super.getMinimumHeight());
    }

    private void setStacked(boolean z) {
        setOrientation(z ? 1 : 0);
        setGravity(z ? Gravity.END : 80);
        if (this.mIsDeviceDefault) {
            return;
        }
        View findViewById = findViewById(R.id.spacer);
        if (findViewById != null) {
            findViewById.setVisibility(z ? 8 : 4);
        }
        for (int childCount = getChildCount() - 2; childCount >= 0; childCount--) {
            bringChildToFront(getChildAt(childCount));
        }
    }

    private boolean isStacked() {
        return getOrientation() == 1;
    }

    private void setDividerInvisible(int i) {
        int childCount = getChildCount();
        while (i < childCount) {
            if (IS_DIVIDER.equals(getChildAt(i).getTag())) {
                getChildAt(i).setVisibility(8);
            }
            i++;
        }
    }

    private void setDividerVisible(int i) {
        int i2;
        int childCount = getChildCount();
        while (i < childCount) {
            if (IS_DIVIDER.equals(getChildAt(i).getTag()) && (i2 = i + 1) < childCount && (getChildAt(i2) instanceof Button) && getChildAt(i2).getVisibility() == 0) {
                getChildAt(i).setVisibility(0);
            }
            i++;
        }
    }
}
