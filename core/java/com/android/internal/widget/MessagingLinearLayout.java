package com.android.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.internal.C4337R;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class MessagingLinearLayout extends ViewGroup {
    private int mMaxDisplayedLines;
    private int mSpacing;

    public MessagingLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMaxDisplayedLines = Integer.MAX_VALUE;
        TypedArray a = context.obtainStyledAttributes(attrs, C4337R.styleable.MessagingLinearLayout, 0, 0);
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case 0:
                    this.mSpacing = a.getDimensionPixelSize(i, 0);
                    break;
            }
        }
        a.recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        boolean z;
        LayoutParams layoutParams;
        View view;
        int i4;
        int i5;
        boolean z2;
        int i6;
        int size = View.MeasureSpec.getSize(i2);
        switch (View.MeasureSpec.getMode(i2)) {
            case 0:
                i3 = Integer.MAX_VALUE;
                break;
            default:
                i3 = size;
                break;
        }
        int i7 = this.mPaddingLeft + this.mPaddingRight;
        int childCount = getChildCount();
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            ((LayoutParams) childAt.getLayoutParams()).hide = true;
            if (childAt instanceof MessagingChild) {
                ((MessagingChild) childAt).setIsFirstInLayout(true);
            }
        }
        MessagingChild messagingChild = null;
        View view2 = null;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = childCount - 1;
        int i13 = i7;
        int i14 = this.mPaddingTop + this.mPaddingBottom;
        char c = 1;
        int i15 = this.mMaxDisplayedLines;
        while (i12 >= 0 && i14 < i3) {
            if (getChildAt(i12).getVisibility() == 8) {
                i4 = i12;
            } else {
                View childAt2 = getChildAt(i12);
                LayoutParams layoutParams2 = (LayoutParams) getChildAt(i12).getLayoutParams();
                MessagingChild messagingChild2 = null;
                int i16 = this.mSpacing;
                int i17 = 0;
                if (!(childAt2 instanceof MessagingChild)) {
                    z = 0;
                    layoutParams = layoutParams2;
                    view = childAt2;
                    i4 = i12;
                } else {
                    if (messagingChild == null || !messagingChild.hasDifferentHeightWhenFirst()) {
                        z2 = false;
                        i6 = i16;
                        layoutParams = layoutParams2;
                        view = childAt2;
                        i4 = i12;
                    } else {
                        messagingChild.setIsFirstInLayout(false);
                        z2 = false;
                        i6 = i16;
                        layoutParams = layoutParams2;
                        view = childAt2;
                        i4 = i12;
                        measureChildWithMargins(view2, i, 0, i2, i10 - i9);
                        int measuredHeight = view2.getMeasuredHeight() - i9;
                        i15 -= messagingChild.getConsumedLines() - i11;
                        i17 = measuredHeight;
                    }
                    MessagingChild messagingChild3 = (MessagingChild) view;
                    messagingChild3.setMaxDisplayedLines(Math.max(z2 ? 1 : 0, i15));
                    i16 = i6 + messagingChild3.getExtraSpacing();
                    messagingChild2 = messagingChild3;
                    z = z2;
                }
                int i18 = c != 0 ? z : i16;
                measureChildWithMargins(view, i, 0, i2, ((i14 - this.mPaddingTop) - this.mPaddingBottom) + i18);
                int measuredHeight2 = view.getMeasuredHeight();
                LayoutParams layoutParams3 = layoutParams;
                int max = Math.max(i14, i14 + measuredHeight2 + layoutParams3.topMargin + layoutParams3.bottomMargin + i18 + i17);
                if (messagingChild2 == null) {
                    i5 = 0;
                } else {
                    i5 = messagingChild2.getMeasuredType();
                }
                boolean z3 = (i5 == 2 && c == 0) ? true : z;
                boolean z4 = (i5 == 1 || (i5 == 2 && c != 0)) ? true : z;
                if ((max > i3 || z3) ? z : true) {
                    if (messagingChild2 != null) {
                        i11 = messagingChild2.getConsumedLines();
                        i15 -= i11;
                        messagingChild = messagingChild2;
                        view2 = view;
                        i9 = measuredHeight2;
                        i10 = i14;
                    }
                    i14 = max;
                    i13 = Math.max(i13, view.getMeasuredWidth() + layoutParams3.leftMargin + layoutParams3.rightMargin + this.mPaddingLeft + this.mPaddingRight);
                    layoutParams3.hide = z;
                    if (!z4 && i15 > 0) {
                        c = 0;
                    }
                } else if (messagingChild != null && messagingChild.hasDifferentHeightWhenFirst()) {
                    messagingChild.setIsFirstInLayout(true);
                    measureChildWithMargins(view2, i, 0, i2, i10 - i9);
                }
                setMeasuredDimension(resolveSize(Math.max(getSuggestedMinimumWidth(), i13), i), Math.max(getSuggestedMinimumHeight(), i14));
            }
            i12 = i4 - 1;
            c = c;
        }
        setMeasuredDimension(resolveSize(Math.max(getSuggestedMinimumWidth(), i13), i), Math.max(getSuggestedMinimumHeight(), i14));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childLeft;
        int paddingLeft;
        int width;
        int paddingLeft2 = this.mPaddingLeft;
        int width2 = right - left;
        int childRight = width2 - this.mPaddingRight;
        int layoutDirection = getLayoutDirection();
        int count = getChildCount();
        int childTop = this.mPaddingTop;
        boolean first = true;
        boolean shown = isShown();
        int i = 0;
        while (i < count) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 8) {
                paddingLeft = paddingLeft2;
                width = width2;
            } else {
                LayoutParams lp = (LayoutParams) childAt.getLayoutParams();
                MessagingChild messagingChild = (MessagingChild) childAt;
                int childWidth = childAt.getMeasuredWidth();
                int childHeight = childAt.getMeasuredHeight();
                if (layoutDirection == 1) {
                    childLeft = (childRight - childWidth) - lp.rightMargin;
                } else {
                    childLeft = paddingLeft2 + lp.leftMargin;
                }
                paddingLeft = paddingLeft2;
                if (lp.hide) {
                    if (!shown || !lp.visibleBefore) {
                        width = width2;
                    } else {
                        width = width2;
                        childAt.layout(childLeft, childTop, childLeft + childWidth, lp.lastVisibleHeight + childTop);
                        messagingChild.hideAnimated();
                    }
                    lp.visibleBefore = false;
                } else {
                    width = width2;
                    lp.visibleBefore = true;
                    lp.lastVisibleHeight = childHeight;
                    if (!first) {
                        childTop += this.mSpacing;
                    }
                    int childTop2 = childTop + lp.topMargin;
                    childAt.layout(childLeft, childTop2, childLeft + childWidth, childTop2 + childHeight);
                    childTop = childTop2 + lp.bottomMargin + childHeight;
                    first = false;
                }
            }
            i++;
            paddingLeft2 = paddingLeft;
            width2 = width;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long drawingTime) {
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        if (lp.hide) {
            MessagingChild messagingChild = (MessagingChild) view;
            if (!messagingChild.isHidingAnimated()) {
                return true;
            }
        }
        return super.drawChild(canvas, view, drawingTime);
    }

    public void setSpacing(int spacing) {
        if (this.mSpacing != spacing) {
            this.mSpacing = spacing;
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(this.mContext, attrs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        LayoutParams copy = new LayoutParams(lp.width, lp.height);
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            copy.copyMarginsFrom((ViewGroup.MarginLayoutParams) lp);
        }
        return copy;
    }

    public static boolean isGone(View view) {
        if (view.getVisibility() == 8) {
            return true;
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        return (lp instanceof LayoutParams) && ((LayoutParams) lp).hide;
    }

    @RemotableViewMethod
    public void setMaxDisplayedLines(int numberLines) {
        this.mMaxDisplayedLines = numberLines;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IMessagingLayout getMessagingLayout() {
        View view = this;
        do {
            Object parent = view.getParent();
            if (parent instanceof View) {
                view = (View) parent;
            } else {
                return null;
            }
        } while (!(view instanceof IMessagingLayout));
        return (IMessagingLayout) view;
    }

    @Override // android.view.View
    public int getBaseline() {
        int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (!isGone(child)) {
                int childBaseline = child.getBaseline();
                if (childBaseline == -1) {
                    return -1;
                }
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                return lp.topMargin + childBaseline;
            }
        }
        int i2 = super.getBaseline();
        return i2;
    }

    public interface MessagingChild {
        public static final int MEASURED_NORMAL = 0;
        public static final int MEASURED_SHORTENED = 1;
        public static final int MEASURED_TOO_SMALL = 2;

        int getConsumedLines();

        int getMeasuredType();

        void hideAnimated();

        boolean isHidingAnimated();

        void recycle();

        void setMaxDisplayedLines(int i);

        default void setIsFirstInLayout(boolean first) {
        }

        default boolean hasDifferentHeightWhenFirst() {
            return false;
        }

        default int getExtraSpacing() {
            return 0;
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public boolean hide;
        public int lastVisibleHeight;
        public boolean visibleBefore;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.hide = false;
            this.visibleBefore = false;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.hide = false;
            this.visibleBefore = false;
        }
    }
}
