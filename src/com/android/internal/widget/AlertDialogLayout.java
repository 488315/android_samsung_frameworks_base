package com.android.internal.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class AlertDialogLayout extends LinearLayout {
    public AlertDialogLayout(Context context) {
        super(context);
    }

    public AlertDialogLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AlertDialogLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AlertDialogLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (tryOnMeasure(i, i2)) {
            return;
        }
        super.onMeasure(i, i2);
    }

    private boolean tryOnMeasure(int i, int i2) {
        int iCombineMeasuredStates;
        int iResolveMinimumHeight;
        int measuredHeight;
        int measuredHeight2;
        int childCount = getChildCount();
        View view = null;
        View view2 = null;
        View view3 = null;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                switch (childAt.getId()) {
                    case R.id.buttonPanel /* 16908897 */:
                        view2 = childAt;
                        break;
                    case R.id.contentPanel /* 16908960 */:
                    case R.id.customPanel /* 16909006 */:
                        if (view3 != null) {
                            return false;
                        }
                        view3 = childAt;
                        break;
                    case R.id.topPanel /* 16909993 */:
                        view = childAt;
                        break;
                    default:
                        return false;
                }
            }
        }
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (view != null) {
            view.measure(i, 0);
            paddingTop += view.getMeasuredHeight();
            iCombineMeasuredStates = combineMeasuredStates(0, view.getMeasuredState());
        } else {
            iCombineMeasuredStates = 0;
        }
        if (view2 != null) {
            view2.measure(i, 0);
            iResolveMinimumHeight = resolveMinimumHeight(view2);
            measuredHeight = view2.getMeasuredHeight() - iResolveMinimumHeight;
            paddingTop += iResolveMinimumHeight;
            iCombineMeasuredStates = combineMeasuredStates(iCombineMeasuredStates, view2.getMeasuredState());
        } else {
            iResolveMinimumHeight = 0;
            measuredHeight = 0;
        }
        if (view3 != null) {
            view3.measure(i, mode == 0 ? 0 : View.MeasureSpec.makeMeasureSpec(Math.max(0, size - paddingTop), mode));
            measuredHeight2 = view3.getMeasuredHeight();
            paddingTop += measuredHeight2;
            iCombineMeasuredStates = combineMeasuredStates(iCombineMeasuredStates, view3.getMeasuredState());
        } else {
            measuredHeight2 = 0;
        }
        int i4 = size - paddingTop;
        if (view2 != null) {
            int i5 = paddingTop - iResolveMinimumHeight;
            int iMin = Math.min(i4, measuredHeight);
            if (iMin > 0) {
                i4 -= iMin;
                iResolveMinimumHeight += iMin;
            }
            view2.measure(i, View.MeasureSpec.makeMeasureSpec(iResolveMinimumHeight, 1073741824));
            paddingTop = i5 + view2.getMeasuredHeight();
            iCombineMeasuredStates = combineMeasuredStates(iCombineMeasuredStates, view2.getMeasuredState());
        }
        if (view3 != null && i4 > 0) {
            view3.measure(i, View.MeasureSpec.makeMeasureSpec(measuredHeight2 + i4, mode));
            paddingTop = (paddingTop - measuredHeight2) + view3.getMeasuredHeight();
            iCombineMeasuredStates = combineMeasuredStates(iCombineMeasuredStates, view3.getMeasuredState());
        }
        int iMax = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt2 = getChildAt(i6);
            if (childAt2.getVisibility() != 8) {
                iMax = Math.max(iMax, childAt2.getMeasuredWidth());
            }
        }
        setMeasuredDimension(resolveSizeAndState(iMax + getPaddingLeft() + getPaddingRight(), i, iCombineMeasuredStates), resolveSizeAndState(paddingTop, i2, 0));
        if (mode2 == 1073741824) {
            return true;
        }
        forceUniformWidth(childCount, i2);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void forceUniformWidth(int i, int i2) {
        AlertDialogLayout alertDialogLayout;
        int i3;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        int i4 = 0;
        while (i4 < i) {
            View childAt = this.getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i5 = layoutParams.height;
                    layoutParams.height = childAt.getMeasuredHeight();
                    alertDialogLayout = this;
                    i3 = i2;
                    alertDialogLayout.measureChildWithMargins(childAt, iMakeMeasureSpec, 0, i3, 0);
                    layoutParams.height = i5;
                } else {
                    alertDialogLayout = this;
                    i3 = i2;
                }
            }
            i4++;
            this = alertDialogLayout;
            i2 = i3;
        }
    }

    private int resolveMinimumHeight(View view) {
        int minimumHeight = view.getMinimumHeight();
        if (minimumHeight > 0) {
            return minimumHeight;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() == 1) {
                return resolveMinimumHeight(viewGroup.getChildAt(0));
            }
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0093  */
    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        int i5;
        AlertDialogLayout alertDialogLayout;
        int i6;
        int i7;
        int i8;
        int i9 = this.mPaddingLeft;
        int i10 = i3 - i;
        int i11 = i10 - this.mPaddingRight;
        int i12 = (i10 - i9) - this.mPaddingRight;
        int measuredHeight = getMeasuredHeight();
        int childCount = getChildCount();
        int gravity = getGravity();
        int i13 = gravity & 112;
        int i14 = gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i13 == 16) {
            i5 = this.mPaddingTop + (((i4 - i2) - measuredHeight) / 2);
        } else if (i13 == 80) {
            i5 = ((this.mPaddingTop + i4) - i2) - measuredHeight;
        } else {
            i5 = this.mPaddingTop;
        }
        Drawable dividerDrawable = getDividerDrawable();
        int i15 = 0;
        int intrinsicHeight = dividerDrawable == null ? 0 : dividerDrawable.getIntrinsicHeight();
        while (i15 < childCount) {
            View childAt = this.getChildAt(i15);
            if (childAt == null || childAt.getVisibility() == 8) {
                alertDialogLayout = this;
            } else {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight2 = childAt.getMeasuredHeight();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                int i16 = layoutParams.gravity;
                if (i16 < 0) {
                    i16 = i14;
                }
                int absoluteGravity = Gravity.getAbsoluteGravity(i16, this.getLayoutDirection()) & 7;
                if (absoluteGravity == 1) {
                    i6 = ((i12 - measuredWidth) / 2) + i9 + layoutParams.leftMargin;
                    i7 = layoutParams.rightMargin;
                } else if (absoluteGravity == 5) {
                    i6 = i11 - measuredWidth;
                    i7 = layoutParams.rightMargin;
                } else {
                    i8 = layoutParams.leftMargin + i9;
                    int i17 = i8;
                    if (this.hasDividerBeforeChildAt(i15)) {
                        i5 += intrinsicHeight;
                    }
                    int i18 = i5 + layoutParams.topMargin;
                    alertDialogLayout = this;
                    alertDialogLayout.setChildFrame(childAt, i17, i18, measuredWidth, measuredHeight2);
                    i5 = i18 + measuredHeight2 + layoutParams.bottomMargin;
                }
                i8 = i6 - i7;
                int i172 = i8;
                if (this.hasDividerBeforeChildAt(i15)) {
                }
                int i182 = i5 + layoutParams.topMargin;
                alertDialogLayout = this;
                alertDialogLayout.setChildFrame(childAt, i172, i182, measuredWidth, measuredHeight2);
                i5 = i182 + measuredHeight2 + layoutParams.bottomMargin;
            }
            i15++;
            this = alertDialogLayout;
        }
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        view.layout(i, i2, i3 + i, i4 + i2);
    }
}
