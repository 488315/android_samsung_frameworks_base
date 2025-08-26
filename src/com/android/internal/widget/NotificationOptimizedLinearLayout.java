package com.android.internal.widget;

import android.content.Context;
import android.content.res.Resources;
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
/* loaded from: classes6.dex */
public class NotificationOptimizedLinearLayout extends LinearLayout {
    private static final boolean DEBUG_LAYOUT = false;
    private static final String TAG = "NotifOptimizedLinearLayout";
    private static final boolean TRACE_ONMEASURE = Build.isDebuggable();
    private boolean mShouldUseOptimizedLayout;

    private void logSkipOptimizedOnMeasure(String str) {
    }

    public NotificationOptimizedLinearLayout(Context context) {
        super(context);
        this.mShouldUseOptimizedLayout = false;
    }

    public NotificationOptimizedLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShouldUseOptimizedLayout = false;
    }

    public NotificationOptimizedLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShouldUseOptimizedLayout = false;
    }

    public NotificationOptimizedLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mShouldUseOptimizedLayout = false;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        View singleWeightedChild = getSingleWeightedChild();
        boolean z = isUseOptimizedLinearLayoutFlagEnabled() && singleWeightedChild != null && isOptimizationPossible(i, i2);
        this.mShouldUseOptimizedLayout = z;
        if (z) {
            onMeasureOptimized(singleWeightedChild, i, i2);
        } else {
            super.onMeasure(i, i2);
        }
    }

    private boolean isUseOptimizedLinearLayoutFlagEnabled() {
        boolean zNotifLinearlayoutOptimized = Flags.notifLinearlayoutOptimized();
        if (!zNotifLinearlayoutOptimized) {
            logSkipOptimizedOnMeasure("enableNotifLinearlayoutOptimized flag is off.");
        }
        return zNotifLinearlayoutOptimized;
    }

    private boolean isOptimizationPossible(int i, int i2) {
        if (getWeightSum() > 0.0f) {
            logSkipOptimizedOnMeasure("Has weightSum.");
            return false;
        }
        if (requiresMatchParentRemeasureForVerticalLinearLayout(i)) {
            logSkipOptimizedOnMeasure("Vertical LinearLayout requires children width MATCH_PARENT remeasure ");
            return false;
        }
        if (getOrientation() == 0 && View.MeasureSpec.getMode(i) != 1073741824) {
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
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) activeChildren.get(i).getLayoutParams();
            if (marginLayoutParams.leftMargin < 0 || marginLayoutParams.rightMargin < 0) {
                return true;
            }
        }
        return false;
    }

    private boolean requiresMatchParentRemeasureForVerticalLinearLayout(int i) {
        if (getOrientation() == 0) {
            return false;
        }
        boolean z = View.MeasureSpec.getMode(i) != 1073741824;
        List<View> activeChildren = getActiveChildren();
        for (int i2 = 0; i2 < activeChildren.size(); i2++) {
            ViewGroup.LayoutParams layoutParams = activeChildren.get(i2).getLayoutParams();
            if (z && layoutParams.width == -1) {
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
        int gravity = getGravity() & 112;
        for (int i = 0; i < activeChildren.size(); i++) {
            View view = activeChildren.get(i);
            if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                if ((layoutParams.height != -1 ? view.getBaseline() : -1) != -1) {
                    int i2 = layoutParams.gravity;
                    if (i2 < 0) {
                        i2 = gravity;
                    }
                    int i3 = i2 & 112;
                    if (i3 == 48 || i3 == 80) {
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
        boolean z = getOrientation() == 1;
        List<View> activeChildren = getActiveChildren();
        View view = null;
        for (int i = 0; i < activeChildren.size(); i++) {
            View view2 = activeChildren.get(i);
            if (view2.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view2.getLayoutParams();
                if ((!z && layoutParams.width == -1) || (z && layoutParams.height == -1)) {
                    logSkipOptimizedOnMeasure("There is a match parent child in the related orientation.");
                    return null;
                }
                if (layoutParams.weight == 0.0f) {
                    continue;
                } else {
                    if (view != null) {
                        logSkipOptimizedOnMeasure("There is more than one weighted child.");
                        return null;
                    }
                    view = view2;
                }
            }
        }
        if (view == null) {
            logSkipOptimizedOnMeasure("There is no weighted child in this layout.");
            return view;
        }
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view.getLayoutParams();
        boolean z2 = layoutParams2.height == -2 || layoutParams2.height == 0;
        boolean z3 = layoutParams2.width == -2 || layoutParams2.width == 0;
        if ((!z || z2) && (z || z3)) {
            return view;
        }
        logSkipOptimizedOnMeasure("Single weighted child should be either WRAP_CONTENT or 0 in the related orientation");
        return null;
    }

    private void onMeasureOptimized(View view, int i, int i2) {
        try {
            boolean z = TRACE_ONMEASURE;
            if (z) {
                Trace.beginSection("NotifOptimizedLinearLayout#onMeasure");
            }
            if (getOrientation() == 0) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                int i3 = layoutParams.width;
                boolean zIsBaselineAligned = isBaselineAligned();
                layoutParams.width = 0;
                setBaselineAligned(false);
                super.onMeasure(i, i2);
                layoutParams.width = i3;
                setBaselineAligned(zIsBaselineAligned);
            } else {
                measureVerticalOptimized(view, i, i2);
            }
            if (z) {
                trackShouldUseOptimizedLayout();
                Trace.endSection();
            }
        } catch (Throwable th) {
            if (TRACE_ONMEASURE) {
                trackShouldUseOptimizedLayout();
                Trace.endSection();
            }
            throw th;
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mShouldUseOptimizedLayout) {
            onLayoutOptimized(z, i, i2, i3, i4);
        } else {
            super.onLayout(z, i, i2, i3, i4);
        }
    }

    private void onLayoutOptimized(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        if (getOrientation() == 0) {
            super.onLayout(z, i, i2, i3, i4);
        } else {
            layoutVerticalOptimized(i, i2, i3, i4);
        }
    }

    private void measureVerticalOptimized(View view, int i, int i2) {
        int iMakeMeasureSpec;
        int size = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        int iMax = 0;
        int iMax2 = 0;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            View childAt = getChildAt(i3);
            if (childAt != null && childAt.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                if (childAt == view) {
                    if (marginLayoutParams.height == 0 && mode == 1073741824) {
                        iMax = Math.max(iMax, marginLayoutParams.topMargin + iMax + marginLayoutParams.bottomMargin);
                    }
                } else {
                    measureChildWithMargins(childAt, i, 0, i2, 0);
                    iMax = Math.max(iMax, childAt.getMeasuredHeight() + iMax + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin);
                    iMax2 = Math.max(iMax2, childAt.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
                }
            }
        }
        int i4 = iMax + this.mPaddingTop + this.mPaddingBottom;
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i5 = mode == 1073741824 ? 1073741824 : Integer.MIN_VALUE;
        if (marginLayoutParams2.height == 0 && mode == 1073741824) {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, size - i4), i5);
        } else {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, size - ((marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin) + i4)), i5);
        }
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight + marginLayoutParams2.leftMargin + marginLayoutParams2.rightMargin, marginLayoutParams2.width), iMakeMeasureSpec);
        setMeasuredDimension(resolveSizeAndState(Math.max(Math.max(iMax2, view.getMeasuredWidth() + marginLayoutParams2.leftMargin + marginLayoutParams2.rightMargin) + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, 0), resolveSizeAndState(Math.max(Math.max(i4, view.getMeasuredHeight() + i4 + marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin), getSuggestedMinimumHeight()), i2, 0));
    }

    private List<View> getActiveChildren() {
        int childCount = getChildCount();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null && childAt.getVisibility() != 8) {
                arrayList.add(childAt);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void layoutVerticalOptimized(int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9 = this.mPaddingLeft;
        int measuredHeight = getMeasuredHeight();
        int i10 = i3 - i;
        int i11 = i10 - this.mPaddingRight;
        int i12 = (i10 - i9) - this.mPaddingRight;
        int childCount = getChildCount();
        int gravity = getGravity() & 112;
        int gravity2 = getGravity() & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (gravity == 16) {
            i5 = this.mPaddingTop + (((i4 - i2) - measuredHeight) / 2);
        } else if (gravity == 80) {
            i5 = ((this.mPaddingTop + i4) - i2) - measuredHeight;
        } else {
            i5 = this.mPaddingTop;
        }
        int dividerHeight = getDividerHeight();
        for (int i13 = 0; i13 < childCount; i13++) {
            View childAt = getChildAt(i13);
            if (childAt != null && childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight2 = childAt.getMeasuredHeight();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                int i14 = layoutParams.gravity;
                if (i14 < 0) {
                    i14 = gravity2;
                }
                int absoluteGravity = Gravity.getAbsoluteGravity(i14, getLayoutDirection()) & 7;
                if (absoluteGravity == 1) {
                    i6 = ((i12 - measuredWidth) / 2) + i9 + layoutParams.leftMargin;
                    i7 = layoutParams.rightMargin;
                } else if (absoluteGravity == 5) {
                    i6 = i11 - measuredWidth;
                    i7 = layoutParams.rightMargin;
                } else {
                    i8 = layoutParams.leftMargin + i9;
                    if (hasDividerBeforeChildAt(i13)) {
                        i5 += dividerHeight;
                    }
                    int i15 = i5 + layoutParams.topMargin;
                    childAt.layout(i8, i15, measuredWidth + i8, i15 + measuredHeight2);
                    i5 = i15 + measuredHeight2 + layoutParams.bottomMargin;
                }
                i8 = i6 - i7;
                if (hasDividerBeforeChildAt(i13)) {
                }
                int i152 = i5 + layoutParams.topMargin;
                childAt.layout(i8, i152, measuredWidth + i8, i152 + measuredHeight2);
                i5 = i152 + measuredHeight2 + layoutParams.bottomMargin;
            }
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
            Trace.setCounter("NotifOptimizedLinearLayout#shouldUseOptimizedLayout", this.mShouldUseOptimizedLayout ? 1L : 0L);
        }
    }
}
