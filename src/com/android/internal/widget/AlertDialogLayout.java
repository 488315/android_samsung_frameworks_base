package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
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
        int i3;
        int i4;
        int i5;
        int i6;
        int childCount = getChildCount();
        View view = null;
        View view2 = null;
        View view3 = null;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
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
                    case R.id.topPanel /* 16909992 */:
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
            i3 = combineMeasuredStates(0, view.getMeasuredState());
        } else {
            i3 = 0;
        }
        if (view2 != null) {
            view2.measure(i, 0);
            i4 = resolveMinimumHeight(view2);
            i5 = view2.getMeasuredHeight() - i4;
            paddingTop += i4;
            i3 = combineMeasuredStates(i3, view2.getMeasuredState());
        } else {
            i4 = 0;
            i5 = 0;
        }
        if (view3 != null) {
            view3.measure(i, mode == 0 ? 0 : View.MeasureSpec.makeMeasureSpec(Math.max(0, size - paddingTop), mode));
            i6 = view3.getMeasuredHeight();
            paddingTop += i6;
            i3 = combineMeasuredStates(i3, view3.getMeasuredState());
        } else {
            i6 = 0;
        }
        int i8 = size - paddingTop;
        if (view2 != null) {
            int i9 = paddingTop - i4;
            int min = Math.min(i8, i5);
            if (min > 0) {
                i8 -= min;
                i4 += min;
            }
            view2.measure(i, View.MeasureSpec.makeMeasureSpec(i4, 1073741824));
            paddingTop = i9 + view2.getMeasuredHeight();
            i3 = combineMeasuredStates(i3, view2.getMeasuredState());
        }
        if (view3 != null && i8 > 0) {
            view3.measure(i, View.MeasureSpec.makeMeasureSpec(i6 + i8, mode));
            paddingTop = (paddingTop - i6) + view3.getMeasuredHeight();
            i3 = combineMeasuredStates(i3, view3.getMeasuredState());
        }
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            View childAt2 = getChildAt(i11);
            if (childAt2.getVisibility() != 8) {
                i10 = Math.max(i10, childAt2.getMeasuredWidth());
            }
        }
        setMeasuredDimension(resolveSizeAndState(i10 + getPaddingLeft() + getPaddingRight(), i, i3), resolveSizeAndState(paddingTop, i2, 0));
        if (mode2 == 1073741824) {
            return true;
        }
        forceUniformWidth(childCount, i2);
        return true;
    }

    private void forceUniformWidth(int i, int i2) {
        AlertDialogLayout alertDialogLayout;
        int i3;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
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
                    alertDialogLayout.measureChildWithMargins(childAt, makeMeasureSpec, 0, i3, 0);
                    layoutParams.height = i5;
                    i4++;
                    this = alertDialogLayout;
                    i2 = i3;
                }
            }
            alertDialogLayout = this;
            i3 = i2;
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

    /* JADX WARN: Removed duplicated region for block: B:25:0x0093  */
    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onLayout(boolean r11, int r12, int r13, int r14, int r15) {
        /*
            r10 = this;
            int r11 = r10.mPaddingLeft
            int r14 = r14 - r12
            int r12 = r10.mPaddingRight
            int r12 = r14 - r12
            int r14 = r14 - r11
            int r0 = r10.mPaddingRight
            int r14 = r14 - r0
            int r0 = r10.getMeasuredHeight()
            int r1 = r10.getChildCount()
            int r2 = r10.getGravity()
            r3 = r2 & 112(0x70, float:1.57E-43)
            r4 = 8388615(0x800007, float:1.1754953E-38)
            r2 = r2 & r4
            r4 = 16
            if (r3 == r4) goto L2f
            r4 = 80
            if (r3 == r4) goto L28
            int r13 = r10.mPaddingTop
            goto L37
        L28:
            int r3 = r10.mPaddingTop
            int r3 = r3 + r15
            int r3 = r3 - r13
            int r13 = r3 - r0
            goto L37
        L2f:
            int r3 = r10.mPaddingTop
            int r15 = r15 - r13
            int r15 = r15 - r0
            int r15 = r15 / 2
            int r13 = r3 + r15
        L37:
            android.graphics.drawable.Drawable r15 = r10.getDividerDrawable()
            r0 = 0
            if (r15 != 0) goto L40
            r15 = r0
            goto L44
        L40:
            int r15 = r15.getIntrinsicHeight()
        L44:
            if (r0 >= r1) goto La7
            android.view.View r4 = r10.getChildAt(r0)
            if (r4 == 0) goto La2
            int r3 = r4.getVisibility()
            r5 = 8
            if (r3 == r5) goto La2
            int r7 = r4.getMeasuredWidth()
            int r8 = r4.getMeasuredHeight()
            android.view.ViewGroup$LayoutParams r3 = r4.getLayoutParams()
            r9 = r3
            android.widget.LinearLayout$LayoutParams r9 = (android.widget.LinearLayout.LayoutParams) r9
            int r3 = r9.gravity
            if (r3 >= 0) goto L68
            r3 = r2
        L68:
            int r5 = r10.getLayoutDirection()
            int r3 = android.view.Gravity.getAbsoluteGravity(r3, r5)
            r3 = r3 & 7
            r5 = 1
            if (r3 == r5) goto L81
            r5 = 5
            if (r3 == r5) goto L7c
            int r3 = r9.leftMargin
            int r3 = r3 + r11
            goto L8c
        L7c:
            int r3 = r12 - r7
            int r5 = r9.rightMargin
            goto L8b
        L81:
            int r3 = r14 - r7
            int r3 = r3 / 2
            int r3 = r3 + r11
            int r5 = r9.leftMargin
            int r3 = r3 + r5
            int r5 = r9.rightMargin
        L8b:
            int r3 = r3 - r5
        L8c:
            r5 = r3
            boolean r3 = r10.hasDividerBeforeChildAt(r0)
            if (r3 == 0) goto L94
            int r13 = r13 + r15
        L94:
            int r3 = r9.topMargin
            int r6 = r13 + r3
            r3 = r10
            r3.setChildFrame(r4, r5, r6, r7, r8)
            int r10 = r9.bottomMargin
            int r8 = r8 + r10
            int r6 = r6 + r8
            r13 = r6
            goto La3
        La2:
            r3 = r10
        La3:
            int r0 = r0 + 1
            r10 = r3
            goto L44
        La7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.AlertDialogLayout.onLayout(boolean, int, int, int, int):void");
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i3 + i, i4 + i2);
    }
}
