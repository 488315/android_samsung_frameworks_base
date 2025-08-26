package com.android.internal.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.flags.Flags;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class MessagingLinearLayout extends ViewGroup {
    private static final boolean TRACE_ONMEASURE = Build.isDebuggable();
    private int mMaxDisplayedLines;
    private int mSpacing;

    public interface MessagingChild {
        public static final int MEASURED_NORMAL = 0;
        public static final int MEASURED_SHORTENED = 1;
        public static final int MEASURED_TOO_SMALL = 2;

        int getConsumedLines();

        default int getExtraSpacing() {
            return 0;
        }

        int getMeasuredType();

        default boolean hasDifferentHeightWhenFirst() {
            return false;
        }

        void hideAnimated();

        boolean isHidingAnimated();

        void recycle();

        default void setIsFirstInLayout(boolean z) {
        }

        void setMaxDisplayedLines(int i);
    }

    public MessagingLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxDisplayedLines = Integer.MAX_VALUE;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MessagingLinearLayout, 0, 0);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            if (typedArrayObtainStyledAttributes.getIndex(i) == 0) {
                this.mSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(i, 0);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        View view;
        View view2;
        LayoutParams layoutParams;
        int i3;
        MessagingChild messagingChild;
        int i4;
        int i5;
        int measuredHeight;
        if (TRACE_ONMEASURE) {
            Trace.beginSection("MessagingLinearLayout#onMeasure");
            trackMeasureSpecs(i, i2);
        }
        int size = View.MeasureSpec.getSize(i2);
        if (View.MeasureSpec.getMode(i2) == 0) {
            size = Integer.MAX_VALUE;
        }
        int i6 = size;
        int i7 = this.mPaddingLeft + this.mPaddingRight;
        int childCount = getChildCount();
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            ((LayoutParams) childAt.getLayoutParams()).hide = true;
            if (Flags.messagingChildRequestLayout()) {
                childAt.requestLayout();
            }
            if (childAt instanceof MessagingChild) {
                ((MessagingChild) childAt).setIsFirstInLayout(true);
            }
        }
        int iMax = i7;
        int i9 = this.mPaddingTop + this.mPaddingBottom;
        int consumedLines = this.mMaxDisplayedLines;
        int i10 = 0;
        int i11 = 0;
        int consumedLines2 = 0;
        boolean z = true;
        View view3 = null;
        MessagingChild messagingChild2 = null;
        for (int i12 = childCount - 1; i12 >= 0 && i9 < i6; i12--) {
            if (getChildAt(i12).getVisibility() != 8) {
                View childAt2 = getChildAt(i12);
                LayoutParams layoutParams2 = (LayoutParams) getChildAt(i12).getLayoutParams();
                int extraSpacing = this.mSpacing;
                if (childAt2 instanceof MessagingChild) {
                    if (messagingChild2 == null || !messagingChild2.hasDifferentHeightWhenFirst()) {
                        view = view3;
                        view2 = childAt2;
                        layoutParams = layoutParams2;
                        i5 = extraSpacing;
                        measuredHeight = 0;
                    } else {
                        messagingChild2.setIsFirstInLayout(false);
                        view2 = childAt2;
                        layoutParams = layoutParams2;
                        i5 = extraSpacing;
                        measureChildWithMargins(view3, i, 0, i2, i10 - i11);
                        view = view3;
                        measuredHeight = view.getMeasuredHeight() - i11;
                        consumedLines -= messagingChild2.getConsumedLines() - consumedLines2;
                    }
                    MessagingChild messagingChild3 = (MessagingChild) view2;
                    messagingChild3.setMaxDisplayedLines(Math.max(0, consumedLines));
                    extraSpacing = i5 + messagingChild3.getExtraSpacing();
                    messagingChild = messagingChild3;
                    i3 = consumedLines;
                    i4 = measuredHeight;
                } else {
                    view = view3;
                    view2 = childAt2;
                    layoutParams = layoutParams2;
                    i3 = consumedLines;
                    messagingChild = null;
                    i4 = 0;
                }
                int i13 = z ? 0 : extraSpacing;
                View view4 = view2;
                measureChildWithMargins(view4, i, 0, i2, ((i9 - this.mPaddingTop) - this.mPaddingBottom) + i13);
                int measuredHeight2 = view4.getMeasuredHeight();
                int iMax2 = Math.max(i9, i9 + measuredHeight2 + layoutParams.topMargin + layoutParams.bottomMargin + i13 + i4);
                int measuredType = messagingChild != null ? messagingChild.getMeasuredType() : 0;
                boolean z2 = measuredType == 2 && !z;
                boolean z3 = measuredType == 1 || (measuredType == 2 && z);
                if (iMax2 <= i6 && !z2) {
                    if (messagingChild != null) {
                        consumedLines2 = messagingChild.getConsumedLines();
                        i3 -= consumedLines2;
                        view = view4;
                        i11 = measuredHeight2;
                        messagingChild2 = messagingChild;
                    } else {
                        i9 = i10;
                    }
                    iMax = Math.max(iMax, view4.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin + this.mPaddingLeft + this.mPaddingRight);
                    layoutParams.hide = false;
                    if (z3 || i3 <= 0) {
                        i9 = iMax2;
                        break;
                    }
                    z = false;
                    i10 = i9;
                    view3 = view;
                    consumedLines = i3;
                    i9 = iMax2;
                } else if (messagingChild2 != null && messagingChild2.hasDifferentHeightWhenFirst()) {
                    messagingChild2.setIsFirstInLayout(true);
                    measureChildWithMargins(view, i, 0, i2, i10 - i11);
                }
            }
        }
        setMeasuredDimension(resolveSize(Math.max(getSuggestedMinimumWidth(), iMax), i), Math.max(getSuggestedMinimumHeight(), i9));
        if (TRACE_ONMEASURE) {
            Trace.endSection();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        int i5;
        int i6;
        int i7 = this.mPaddingLeft;
        int i8 = (i3 - i) - this.mPaddingRight;
        int layoutDirection = getLayoutDirection();
        int childCount = getChildCount();
        int i9 = this.mPaddingTop;
        boolean zIsShown = isShown();
        int i10 = 1;
        boolean z2 = true;
        int i11 = 0;
        while (i11 < childCount) {
            View childAt = getChildAt(i11);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                MessagingChild messagingChild = (MessagingChild) childAt;
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (layoutDirection == i10) {
                    i5 = (i8 - measuredWidth) - layoutParams.rightMargin;
                } else {
                    i5 = i7 + layoutParams.leftMargin;
                }
                int i12 = i5;
                if (layoutParams.hide) {
                    if (zIsShown && layoutParams.visibleBefore) {
                        childAt.layout(i12, i9, measuredWidth + i12, layoutParams.lastVisibleHeight + i9);
                        messagingChild.hideAnimated();
                    }
                    layoutParams.visibleBefore = false;
                } else {
                    i6 = 1;
                    layoutParams.visibleBefore = true;
                    layoutParams.lastVisibleHeight = measuredHeight;
                    if (!z2) {
                        i9 += this.mSpacing;
                    }
                    int i13 = i9 + layoutParams.topMargin;
                    childAt.layout(i12, i13, measuredWidth + i12, i13 + measuredHeight);
                    i9 = i13 + measuredHeight + layoutParams.bottomMargin;
                    z2 = false;
                    i11++;
                    i10 = i6;
                }
            }
            i6 = 1;
            i11++;
            i10 = i6;
        }
    }

    private void trackMeasureSpecs(int i, int i2) {
        if (TRACE_ONMEASURE) {
            int size = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            int size2 = View.MeasureSpec.getSize(i2);
            int mode2 = View.MeasureSpec.getMode(i2);
            Trace.setCounter("MessagingLinearLayout#onMeasure_widthMeasureSpecSize", size);
            Trace.setCounter("MessagingLinearLayout#onMeasure_widthMeasureSpecMode", mode);
            Trace.setCounter("MessagingLinearLayout#onMeasure_heightMeasureSpecSize", size2);
            Trace.setCounter("MessagingLinearLayout#onMeasure_heightMeasureSpecMode", mode2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        if (!((LayoutParams) view.getLayoutParams()).hide || ((MessagingChild) view).isHidingAnimated()) {
            return super.drawChild(canvas, view, j);
        }
        return true;
    }

    public void setSpacing(int i) {
        if (this.mSpacing != i) {
            this.mSpacing = i;
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.mContext, attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutParams layoutParams2 = new LayoutParams(layoutParams.width, layoutParams.height);
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            layoutParams2.copyMarginsFrom((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return layoutParams2;
    }

    public static boolean isGone(View view) {
        if (view.getVisibility() == 8) {
            return true;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        return (layoutParams instanceof LayoutParams) && ((LayoutParams) layoutParams).hide;
    }

    @RemotableViewMethod
    public void setMaxDisplayedLines(int i) {
        this.mMaxDisplayedLines = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.internal.widget.MessagingLinearLayout] */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r1v4, types: [android.view.View] */
    public IMessagingLayout getMessagingLayout() {
        do {
            Object parent = this.getParent();
            if (!(parent instanceof View)) {
                return null;
            }
            this = (View) parent;
        } while (!(this instanceof IMessagingLayout));
        return (IMessagingLayout) this;
    }

    @Override // android.view.View
    public int getBaseline() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (!isGone(childAt)) {
                int baseline = childAt.getBaseline();
                if (baseline == -1) {
                    return -1;
                }
                return ((ViewGroup.MarginLayoutParams) childAt.getLayoutParams()).topMargin + baseline;
            }
        }
        return super.getBaseline();
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public boolean hide;
        public int lastVisibleHeight;
        public boolean visibleBefore;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.hide = false;
            this.visibleBefore = false;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.hide = false;
            this.visibleBefore = false;
        }
    }
}
