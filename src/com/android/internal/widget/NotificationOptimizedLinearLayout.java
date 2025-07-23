package com.android.internal.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Trace;
import android.util.AttributeSet;
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
        boolean notifLinearlayoutOptimized = Flags.notifLinearlayoutOptimized();
        if (!notifLinearlayoutOptimized) {
            logSkipOptimizedOnMeasure("enableNotifLinearlayoutOptimized flag is off.");
        }
        return notifLinearlayoutOptimized;
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
                boolean isBaselineAligned = isBaselineAligned();
                layoutParams.width = 0;
                setBaselineAligned(false);
                super.onMeasure(i, i2);
                layoutParams.width = i3;
                setBaselineAligned(isBaselineAligned);
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

    private void onLayoutOptimized(boolean z, int i, int i2, int i3, int i4) {
        if (getOrientation() == 0) {
            super.onLayout(z, i, i2, i3, i4);
        } else {
            layoutVerticalOptimized(i, i2, i3, i4);
        }
    }

    private void measureVerticalOptimized(View view, int i, int i2) {
        int makeMeasureSpec;
        int size = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            if (childAt != null && childAt.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                if (childAt == view) {
                    if (marginLayoutParams.height == 0 && mode == 1073741824) {
                        i3 = Math.max(i3, marginLayoutParams.topMargin + i3 + marginLayoutParams.bottomMargin);
                    }
                } else {
                    measureChildWithMargins(childAt, i, 0, i2, 0);
                    i3 = Math.max(i3, childAt.getMeasuredHeight() + i3 + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin);
                    i4 = Math.max(i4, childAt.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
                }
            }
        }
        int i6 = i3 + this.mPaddingTop + this.mPaddingBottom;
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i7 = mode == 1073741824 ? 1073741824 : Integer.MIN_VALUE;
        if (marginLayoutParams2.height == 0 && mode == 1073741824) {
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, size - i6), i7);
        } else {
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, size - ((marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin) + i6)), i7);
        }
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight + marginLayoutParams2.leftMargin + marginLayoutParams2.rightMargin, marginLayoutParams2.width), makeMeasureSpec);
        setMeasuredDimension(resolveSizeAndState(Math.max(Math.max(i4, view.getMeasuredWidth() + marginLayoutParams2.leftMargin + marginLayoutParams2.rightMargin) + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, 0), resolveSizeAndState(Math.max(Math.max(i6, view.getMeasuredHeight() + i6 + marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin), getSuggestedMinimumHeight()), i2, 0));
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void layoutVerticalOptimized(int r11, int r12, int r13, int r14) {
        /*
            r10 = this;
            int r0 = r10.mPaddingLeft
            int r1 = r10.getMeasuredHeight()
            int r13 = r13 - r11
            int r11 = r10.mPaddingRight
            int r11 = r13 - r11
            int r13 = r13 - r0
            int r2 = r10.mPaddingRight
            int r13 = r13 - r2
            int r2 = r10.getChildCount()
            int r3 = r10.getGravity()
            r3 = r3 & 112(0x70, float:1.57E-43)
            int r4 = r10.getGravity()
            r5 = 8388615(0x800007, float:1.1754953E-38)
            r4 = r4 & r5
            r5 = 16
            if (r3 == r5) goto L33
            r5 = 80
            if (r3 == r5) goto L2c
            int r12 = r10.mPaddingTop
            goto L3b
        L2c:
            int r3 = r10.mPaddingTop
            int r3 = r3 + r14
            int r3 = r3 - r12
            int r12 = r3 - r1
            goto L3b
        L33:
            int r3 = r10.mPaddingTop
            int r14 = r14 - r12
            int r14 = r14 - r1
            int r14 = r14 / 2
            int r12 = r3 + r14
        L3b:
            int r14 = r10.getDividerHeight()
            r1 = 0
        L40:
            if (r1 >= r2) goto L9e
            android.view.View r3 = r10.getChildAt(r1)
            if (r3 == 0) goto L9b
            int r5 = r3.getVisibility()
            r6 = 8
            if (r5 == r6) goto L9b
            int r5 = r3.getMeasuredWidth()
            int r6 = r3.getMeasuredHeight()
            android.view.ViewGroup$LayoutParams r7 = r3.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r7 = (android.widget.LinearLayout.LayoutParams) r7
            int r8 = r7.gravity
            if (r8 >= 0) goto L63
            r8 = r4
        L63:
            int r9 = r10.getLayoutDirection()
            int r8 = android.view.Gravity.getAbsoluteGravity(r8, r9)
            r8 = r8 & 7
            r9 = 1
            if (r8 == r9) goto L7c
            r9 = 5
            if (r8 == r9) goto L77
            int r8 = r7.leftMargin
            int r8 = r8 + r0
            goto L87
        L77:
            int r8 = r11 - r5
            int r9 = r7.rightMargin
            goto L86
        L7c:
            int r8 = r13 - r5
            int r8 = r8 / 2
            int r8 = r8 + r0
            int r9 = r7.leftMargin
            int r8 = r8 + r9
            int r9 = r7.rightMargin
        L86:
            int r8 = r8 - r9
        L87:
            boolean r9 = r10.hasDividerBeforeChildAt(r1)
            if (r9 == 0) goto L8e
            int r12 = r12 + r14
        L8e:
            int r9 = r7.topMargin
            int r12 = r12 + r9
            int r5 = r5 + r8
            int r9 = r12 + r6
            r3.layout(r8, r12, r5, r9)
            int r3 = r7.bottomMargin
            int r6 = r6 + r3
            int r12 = r12 + r6
        L9b:
            int r1 = r1 + 1
            goto L40
        L9e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.NotificationOptimizedLinearLayout.layoutVerticalOptimized(int, int, int, int):void");
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
