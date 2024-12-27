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

/* loaded from: classes5.dex */
public class ButtonBarLayout extends LinearLayout {
    private static final String IS_DIVIDER = "isDivider";
    private static final int PEEK_BUTTON_DP = 16;
    private boolean mAllowStacking;
    private boolean mIsDeviceDefault;
    private int mLastWidthSize;
    private int mMinimumHeight;

    public ButtonBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLastWidthSize = -1;
        this.mMinimumHeight = 0;
        this.mIsDeviceDefault = false;
        TypedValue themeValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, themeValue, false);
        if (themeValue.data != 0) {
            this.mIsDeviceDefault = true;
        }
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ButtonBarLayout);
        this.mAllowStacking = ta.getBoolean(0, true);
        ta.recycle();
    }

    public void setAllowStacking(boolean allowStacking) {
        if (this.mAllowStacking != allowStacking) {
            this.mAllowStacking = allowStacking;
            if (!this.mAllowStacking && getOrientation() == 1) {
                setStacked(false);
            }
            requestLayout();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int initialWidthMeasureSpec;
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (this.mAllowStacking) {
            if (widthSize > this.mLastWidthSize && isStacked()) {
                setStacked(false);
                if (this.mIsDeviceDefault) {
                    setDividerVisible(getNextVisibleChildIndex(0));
                }
            }
            this.mLastWidthSize = widthSize;
        }
        boolean needsRemeasure = false;
        if (!isStacked() && View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824) {
            initialWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, Integer.MIN_VALUE);
            needsRemeasure = true;
        } else {
            initialWidthMeasureSpec = widthMeasureSpec;
        }
        super.onMeasure(initialWidthMeasureSpec, heightMeasureSpec);
        if (this.mAllowStacking && !isStacked()) {
            int measuredWidth = getMeasuredWidthAndState();
            int measuredWidthState = (-16777216) & measuredWidth;
            if (measuredWidthState == 16777216) {
                setStacked(true);
                if (this.mIsDeviceDefault) {
                    setDividerInvisible(0);
                    setGravity(17);
                }
                needsRemeasure = true;
            }
        }
        if (needsRemeasure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        int minHeight = 0;
        int firstVisible = getNextVisibleChildIndex(0);
        if (firstVisible >= 0) {
            View firstButton = getChildAt(firstVisible);
            LinearLayout.LayoutParams firstParams = (LinearLayout.LayoutParams) firstButton.getLayoutParams();
            minHeight = 0 + getPaddingTop() + firstButton.getMeasuredHeight() + firstParams.topMargin + firstParams.bottomMargin;
            if (isStacked()) {
                if (this.mIsDeviceDefault) {
                    minHeight += getPaddingBottom();
                } else {
                    int secondVisible = getNextVisibleChildIndex(firstVisible + 1);
                    if (secondVisible >= 0) {
                        minHeight = (int) (minHeight + getChildAt(secondVisible).getPaddingTop() + (getResources().getDisplayMetrics().density * 16.0f));
                    }
                }
            } else {
                minHeight += getPaddingBottom();
            }
        }
        if (getMinimumHeight() != minHeight) {
            setMinimumHeight(minHeight);
        }
    }

    private int getNextVisibleChildIndex(int index) {
        int count = getChildCount();
        for (int i = index; i < count; i++) {
            if (getChildAt(i).getVisibility() == 0 && (!this.mIsDeviceDefault || (getChildAt(i) instanceof Button))) {
                return i;
            }
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

    private void setDividerInvisible(int index) {
        int count = getChildCount();
        for (int i = index; i < count; i++) {
            if (IS_DIVIDER.equals(getChildAt(i).getTag())) {
                getChildAt(i).setVisibility(8);
            }
        }
    }

    private void setDividerVisible(int index) {
        int count = getChildCount();
        for (int i = index; i < count; i++) {
            if (IS_DIVIDER.equals(getChildAt(i).getTag()) && i + 1 < count && (getChildAt(i + 1) instanceof Button) && getChildAt(i + 1).getVisibility() == 0) {
                getChildAt(i).setVisibility(0);
            }
        }
    }
}
