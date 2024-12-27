package com.android.internal.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.flags.Flags;

import java.util.ArrayList;
import java.util.List;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class NotificationOptimizedLinearLayout extends LinearLayout {
    private static final boolean DEBUG_LAYOUT = false;
    private static final String TAG = "NotifOptimizedLinearLayout";
    private static final boolean TRACE_ONMEASURE = Build.isDebuggable();
    private boolean mShouldUseOptimizedLayout;

    public NotificationOptimizedLinearLayout(Context context) {
        super(context);
        this.mShouldUseOptimizedLayout = false;
    }

    public NotificationOptimizedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mShouldUseOptimizedLayout = false;
    }

    public NotificationOptimizedLinearLayout(
            Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mShouldUseOptimizedLayout = false;
    }

    public NotificationOptimizedLinearLayout(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mShouldUseOptimizedLayout = false;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View weightedChildView = getSingleWeightedChild();
        this.mShouldUseOptimizedLayout =
                isUseOptimizedLinearLayoutFlagEnabled()
                        && weightedChildView != null
                        && isOptimizationPossible(widthMeasureSpec, heightMeasureSpec);
        if (this.mShouldUseOptimizedLayout) {
            onMeasureOptimized(weightedChildView, widthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private boolean isUseOptimizedLinearLayoutFlagEnabled() {
        boolean enabled = Flags.notifLinearlayoutOptimized();
        if (!enabled) {
            logSkipOptimizedOnMeasure("enableNotifLinearlayoutOptimized flag is off.");
        }
        return enabled;
    }

    private boolean isOptimizationPossible(int widthMeasureSpec, int heightMeasureSpec) {
        boolean hasWeightSum = getWeightSum() > 0.0f;
        if (hasWeightSum) {
            logSkipOptimizedOnMeasure("Has weightSum.");
            return false;
        }
        if (requiresMatchParentRemeasureForVerticalLinearLayout(widthMeasureSpec)) {
            logSkipOptimizedOnMeasure(
                    "Vertical LinearLayout requires children width MATCH_PARENT remeasure ");
            return false;
        }
        boolean isHorizontal = getOrientation() == 0;
        if (isHorizontal && View.MeasureSpec.getMode(widthMeasureSpec) != 1073741824) {
            logSkipOptimizedOnMeasure("Horizontal LinearLayout's width should be measured EXACTLY");
            return false;
        }
        if (requiresBaselineAlignmentForHorizontalLinearLayout()) {
            logSkipOptimizedOnMeasure("Need to apply baseline.");
            return false;
        }
        if (!requiresNegativeMarginHandlingForHorizontalLinearLayout()) {
            return true;
        }
        logSkipOptimizedOnMeasure("Need to handle negative margins.");
        return false;
    }

    private boolean requiresNegativeMarginHandlingForHorizontalLinearLayout() {
        if (getOrientation() == 1) {
            return false;
        }
        List<View> activeChildren = getActiveChildren();
        for (int i = 0; i < activeChildren.size(); i++) {
            View child = activeChildren.get(i);
            ViewGroup.MarginLayoutParams lp =
                    (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            if (lp.leftMargin < 0 || lp.rightMargin < 0) {
                return true;
            }
        }
        return false;
    }

    private boolean requiresMatchParentRemeasureForVerticalLinearLayout(int widthMeasureSpec) {
        if (getOrientation() == 0) {
            return false;
        }
        boolean nonExactWidth = View.MeasureSpec.getMode(widthMeasureSpec) != 1073741824;
        List<View> activeChildren = getActiveChildren();
        for (int i = 0; i < activeChildren.size(); i++) {
            View child = activeChildren.get(i);
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            if (nonExactWidth && lp.width == -1) {
                return true;
            }
        }
        return false;
    }

    private boolean requiresBaselineAlignmentForHorizontalLinearLayout() {
        if (getOrientation() == 1 || !isBaselineAligned()) {
            return false;
        }
        List<View> activeChildren = getActiveChildren();
        int minorGravity = getGravity() & 112;
        for (int i = 0; i < activeChildren.size(); i++) {
            View child = activeChildren.get(i);
            if (child.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                int childBaseline = -1;
                if (lp.height != -1) {
                    childBaseline = child.getBaseline();
                }
                if (childBaseline != -1) {
                    int gravity = lp.gravity;
                    if (gravity < 0) {
                        gravity = minorGravity;
                    }
                    int result = gravity & 112;
                    if (result == 48 || result == 80) {
                        return true;
                    }
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    private View getSingleWeightedChild() {
        boolean isVertical = getOrientation() == 1;
        List<View> activeChildren = getActiveChildren();
        View singleWeightedChild = null;
        for (int i = 0; i < activeChildren.size(); i++) {
            View child = activeChildren.get(i);
            if (child.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                if ((!isVertical && lp.width == -1) || (isVertical && lp.height == -1)) {
                    logSkipOptimizedOnMeasure(
                            "There is a match parent child in the related orientation.");
                    return null;
                }
                if (lp.weight == 0.0f) {
                    continue;
                } else if (singleWeightedChild == null) {
                    singleWeightedChild = child;
                } else {
                    logSkipOptimizedOnMeasure("There is more than one weighted child.");
                    return null;
                }
            }
        }
        if (singleWeightedChild == null) {
            logSkipOptimizedOnMeasure("There is no weighted child in this layout.");
            return singleWeightedChild;
        }
        LinearLayout.LayoutParams lp2 =
                (LinearLayout.LayoutParams) singleWeightedChild.getLayoutParams();
        boolean isHeightWrapContentOrZero = lp2.height == -2 || lp2.height == 0;
        boolean isWidthWrapContentOrZero = lp2.width == -2 || lp2.width == 0;
        if ((isVertical && !isHeightWrapContentOrZero)
                || (!isVertical && !isWidthWrapContentOrZero)) {
            logSkipOptimizedOnMeasure(
                    "Single weighted child should be either WRAP_CONTENT or 0 in the related"
                        + " orientation");
            return null;
        }
        return singleWeightedChild;
    }

    private void onMeasureOptimized(
            View weightedChildView, int widthMeasureSpec, int heightMeasureSpec) {
        try {
            if (TRACE_ONMEASURE) {
                Trace.beginSection("NotifOptimizedLinearLayout#onMeasure");
            }
            if (getOrientation() == 0) {
                ViewGroup.LayoutParams lp = weightedChildView.getLayoutParams();
                int childWidth = lp.width;
                boolean isBaselineAligned = isBaselineAligned();
                lp.width = 0;
                setBaselineAligned(false);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                lp.width = childWidth;
                setBaselineAligned(isBaselineAligned);
            } else {
                measureVerticalOptimized(weightedChildView, widthMeasureSpec, heightMeasureSpec);
            }
        } finally {
            if (TRACE_ONMEASURE) {
                trackShouldUseOptimizedLayout();
                Trace.endSection();
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mShouldUseOptimizedLayout) {
            onLayoutOptimized(changed, l, t, r, b);
        } else {
            super.onLayout(changed, l, t, r, b);
        }
    }

    private void onLayoutOptimized(boolean changed, int l, int t, int r, int b) {
        if (getOrientation() == 0) {
            super.onLayout(changed, l, t, r, b);
        } else {
            layoutVerticalOptimized(l, t, r, b);
        }
    }

    /* JADX WARN: Incorrect condition in loop: B:3:0x001a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void measureVerticalOptimized(android.view.View r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 263
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.widget.NotificationOptimizedLinearLayout.measureVerticalOptimized(android.view.View,"
                    + " int, int):void");
    }

    private List<View> getActiveChildren() {
        int childCount = getChildCount();
        List<View> activeChildren = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child != null && child.getVisibility() != 8) {
                activeChildren.add(child);
            }
        }
        return activeChildren;
    }

    private void layoutVerticalOptimized(int left, int top, int right, int bottom) {
        int childTop;
        int mTotalLength;
        int width;
        int childLeft;
        NotificationOptimizedLinearLayout notificationOptimizedLinearLayout = this;
        int paddingLeft = notificationOptimizedLinearLayout.mPaddingLeft;
        int mTotalLength2 = getMeasuredHeight();
        int width2 = right - left;
        int childRight = width2 - notificationOptimizedLinearLayout.mPaddingRight;
        int childSpace = (width2 - paddingLeft) - notificationOptimizedLinearLayout.mPaddingRight;
        int count = getChildCount();
        int majorGravity = getGravity() & 112;
        int minorGravity = getGravity() & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        switch (majorGravity) {
            case 16:
                int childTop2 = notificationOptimizedLinearLayout.mPaddingTop;
                childTop = childTop2 + (((bottom - top) - mTotalLength2) / 2);
                break;
            case 80:
                int childTop3 = notificationOptimizedLinearLayout.mPaddingTop;
                childTop = ((childTop3 + bottom) - top) - mTotalLength2;
                break;
            default:
                childTop = notificationOptimizedLinearLayout.mPaddingTop;
                break;
        }
        int dividerHeight = getDividerHeight();
        int i = 0;
        while (i < count) {
            View child = notificationOptimizedLinearLayout.getChildAt(i);
            if (child == null || child.getVisibility() == 8) {
                mTotalLength = mTotalLength2;
                width = width2;
            } else {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                mTotalLength = mTotalLength2;
                int gravity = lp.gravity;
                if (gravity < 0) {
                    gravity = minorGravity;
                }
                width = width2;
                int layoutDirection = getLayoutDirection();
                int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
                switch (absoluteGravity & 7) {
                    case 1:
                        int layoutDirection2 = lp.leftMargin;
                        childLeft =
                                ((((childSpace - childWidth) / 2) + paddingLeft) + layoutDirection2)
                                        - lp.rightMargin;
                        break;
                    case 5:
                        int layoutDirection3 = childRight - childWidth;
                        int childLeft2 = layoutDirection3 - lp.rightMargin;
                        childLeft = childLeft2;
                        break;
                    default:
                        childLeft = lp.leftMargin + paddingLeft;
                        break;
                }
                if (notificationOptimizedLinearLayout.hasDividerBeforeChildAt(i)) {
                    childTop += dividerHeight;
                }
                int childTop4 = childTop + lp.topMargin;
                child.layout(childLeft, childTop4, childLeft + childWidth, childTop4 + childHeight);
                childTop = childTop4 + lp.bottomMargin + childHeight;
            }
            i++;
            notificationOptimizedLinearLayout = this;
            mTotalLength2 = mTotalLength;
            width2 = width;
        }
    }

    private int getDividerHeight() {
        Drawable dividerDrawable = getDividerDrawable();
        if (dividerDrawable == null) {
            return 0;
        }
        return dividerDrawable.getIntrinsicHeight();
    }

    private void trackShouldUseOptimizedLayout() {
        if (TRACE_ONMEASURE) {
            Trace.setCounter(
                    "NotifOptimizedLinearLayout#shouldUseOptimizedLayout",
                    this.mShouldUseOptimizedLayout ? 1L : 0L);
        }
    }

    private void logSkipOptimizedOnMeasure(String reason) {}
}
