package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.appcompat.R$styleable;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class LinearLayoutCompat extends ViewGroup {
    public boolean mBaselineAligned;
    public final int mBaselineAlignedChildIndex;
    public int mBaselineChildTop;
    public final Drawable mDivider;
    public final int mDividerHeight;
    public final int mDividerPadding;
    public final int mDividerWidth;
    public int mGravity;
    public int[] mMaxAscent;
    public int[] mMaxDescent;
    public int mOrientation;
    public final int mShowDividers;
    public int mTotalLength;
    public final boolean mUseLargestChild;
    public final float mWeightSum;

    public class LayoutParams extends LinearLayout.LayoutParams {
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public final void drawHorizontalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i);
        this.mDivider.draw(canvas);
    }

    public final void drawVerticalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(i, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + i, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    @Override // android.view.View
    public final int getBaseline() {
        int i;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i2 = this.mBaselineAlignedChildIndex;
        if (childCount <= i2) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(i2);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int iM = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (i = this.mGravity & 112) != 48) {
            if (i == 16) {
                iM = AbsActionBarView$$ExternalSyntheticOutline0.m(((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom(), this.mTotalLength, 2, iM);
            } else if (i == 80) {
                iM = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
            }
        }
        return iM + ((LinearLayout.LayoutParams) ((LayoutParams) childAt.getLayoutParams())).topMargin + baseline;
    }

    public final boolean hasDividerBeforeChildAt(int i) {
        if (i == 0) {
            return (this.mShowDividers & 1) != 0;
        }
        if (i == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        }
        if ((this.mShowDividers & 2) != 0) {
            for (int i2 = i - 1; i2 >= 0; i2--) {
                if (getChildAt(i2).getVisibility() != 8) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int right;
        int left;
        int i;
        if (this.mDivider == null) {
            return;
        }
        int i2 = 0;
        if (this.mOrientation == 1) {
            int childCount = getChildCount();
            while (i2 < childCount) {
                View childAt = getChildAt(i2);
                if (childAt != null && childAt.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                    drawHorizontalDivider(canvas, (childAt.getTop() - ((LinearLayout.LayoutParams) ((LayoutParams) childAt.getLayoutParams())).topMargin) - this.mDividerHeight);
                }
                i2++;
            }
            if (hasDividerBeforeChildAt(childCount)) {
                View childAt2 = getChildAt(childCount - 1);
                drawHorizontalDivider(canvas, childAt2 == null ? (getHeight() - getPaddingBottom()) - this.mDividerHeight : childAt2.getBottom() + ((LinearLayout.LayoutParams) ((LayoutParams) childAt2.getLayoutParams())).bottomMargin);
                return;
            }
            return;
        }
        int childCount2 = getChildCount();
        boolean z = getLayoutDirection() == 1;
        while (i2 < childCount2) {
            View childAt3 = getChildAt(i2);
            if (childAt3 != null && childAt3.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                LayoutParams layoutParams = (LayoutParams) childAt3.getLayoutParams();
                drawVerticalDivider(canvas, z ? childAt3.getRight() + ((LinearLayout.LayoutParams) layoutParams).rightMargin : (childAt3.getLeft() - ((LinearLayout.LayoutParams) layoutParams).leftMargin) - this.mDividerWidth);
            }
            i2++;
        }
        if (hasDividerBeforeChildAt(childCount2)) {
            View childAt4 = getChildAt(childCount2 - 1);
            if (childAt4 != null) {
                LayoutParams layoutParams2 = (LayoutParams) childAt4.getLayoutParams();
                if (z) {
                    left = childAt4.getLeft() - ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                    i = this.mDividerWidth;
                    right = left - i;
                } else {
                    right = childAt4.getRight() + ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                }
            } else if (z) {
                right = getPaddingLeft();
            } else {
                left = getWidth() - getPaddingRight();
                i = this.mDividerWidth;
                right = left - i;
            }
            drawVerticalDivider(canvas, right);
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01ab  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int iM;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int iM2;
        int i13 = 8;
        if (this.mOrientation == 1) {
            int paddingLeft = getPaddingLeft();
            int i14 = i3 - i;
            int paddingRight = i14 - getPaddingRight();
            int paddingRight2 = (i14 - paddingLeft) - getPaddingRight();
            int childCount = getChildCount();
            int i15 = this.mGravity;
            int i16 = i15 & 112;
            int i17 = 8388615 & i15;
            int paddingTop = i16 != 16 ? i16 != 80 ? getPaddingTop() : ((getPaddingTop() + i4) - i2) - this.mTotalLength : AbsActionBarView$$ExternalSyntheticOutline0.m(i4 - i2, this.mTotalLength, 2, getPaddingTop());
            int i18 = 0;
            while (i18 < childCount) {
                View childAt = getChildAt(i18);
                if (childAt != null && childAt.getVisibility() != i13) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    int i19 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                    if (i19 < 0) {
                        i19 = i17;
                    }
                    int absoluteGravity = Gravity.getAbsoluteGravity(i19, getLayoutDirection()) & 7;
                    int iM3 = absoluteGravity != 1 ? absoluteGravity != 5 ? ((LinearLayout.LayoutParams) layoutParams).leftMargin + paddingLeft : (paddingRight - measuredWidth) - ((LinearLayout.LayoutParams) layoutParams).rightMargin : (AbsActionBarView$$ExternalSyntheticOutline0.m(paddingRight2, measuredWidth, 2, paddingLeft) + ((LinearLayout.LayoutParams) layoutParams).leftMargin) - ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                    if (hasDividerBeforeChildAt(i18)) {
                        paddingTop += this.mDividerHeight;
                    }
                    int i20 = paddingTop + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    childAt.layout(iM3, i20, measuredWidth + iM3, i20 + measuredHeight);
                    paddingTop = measuredHeight + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + i20;
                }
                i18++;
                i13 = 8;
            }
            return;
        }
        boolean z2 = getLayoutDirection() == 1;
        int paddingTop2 = getPaddingTop();
        int i21 = i4 - i2;
        int paddingBottom = i21 - getPaddingBottom();
        int paddingBottom2 = (i21 - paddingTop2) - getPaddingBottom();
        int childCount2 = getChildCount();
        int i22 = this.mGravity;
        int i23 = 8388615 & i22;
        int i24 = i22 & 112;
        boolean z3 = this.mBaselineAligned;
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        int absoluteGravity2 = Gravity.getAbsoluteGravity(i23, getLayoutDirection());
        if (absoluteGravity2 != 1) {
            iM = absoluteGravity2 != 5 ? getPaddingLeft() : ((getPaddingLeft() + i3) - i) - this.mTotalLength;
            i5 = 1;
        } else {
            i5 = 1;
            iM = AbsActionBarView$$ExternalSyntheticOutline0.m(i3 - i, this.mTotalLength, 2, getPaddingLeft());
        }
        if (z2) {
            i7 = childCount2 - 1;
            i6 = -1;
        } else {
            i6 = i5;
            i7 = 0;
        }
        int i25 = 0;
        while (i25 < childCount2) {
            int i26 = (i6 * i25) + i7;
            View childAt2 = getChildAt(i26);
            if (childAt2 == null) {
                i8 = i7;
            } else {
                i8 = i7;
                if (childAt2.getVisibility() != 8) {
                    int measuredWidth2 = childAt2.getMeasuredWidth();
                    int measuredHeight2 = childAt2.getMeasuredHeight();
                    int i27 = iM;
                    LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                    if (z3) {
                        i9 = i6;
                        int baseline = ((LinearLayout.LayoutParams) layoutParams2).height != -1 ? childAt2.getBaseline() : -1;
                        i10 = ((LinearLayout.LayoutParams) layoutParams2).gravity;
                        if (i10 < 0) {
                            i10 = i24;
                        }
                        i11 = i10 & 112;
                        i12 = i25;
                        if (i11 != 16) {
                            iM2 = (AbsActionBarView$$ExternalSyntheticOutline0.m(paddingBottom2, measuredHeight2, 2, paddingTop2) + ((LinearLayout.LayoutParams) layoutParams2).topMargin) - ((LinearLayout.LayoutParams) layoutParams2).bottomMargin;
                        } else if (i11 == 48) {
                            iM2 = ((LinearLayout.LayoutParams) layoutParams2).topMargin + paddingTop2;
                            if (baseline != -1) {
                                iM2 = (iArr[i5] - baseline) + iM2;
                            }
                        } else if (i11 != 80) {
                            iM2 = paddingTop2;
                        } else {
                            iM2 = (paddingBottom - measuredHeight2) - ((LinearLayout.LayoutParams) layoutParams2).bottomMargin;
                            if (baseline != -1) {
                                iM2 -= iArr2[2] - (childAt2.getMeasuredHeight() - baseline);
                            }
                        }
                        int i28 = (!hasDividerBeforeChildAt(i26) ? i27 + this.mDividerWidth : i27) + ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                        childAt2.layout(i28, iM2, i28 + measuredWidth2, measuredHeight2 + iM2);
                        iM = measuredWidth2 + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + i28;
                        i25 = i12 + 1;
                        i6 = i9;
                        i7 = i8;
                    } else {
                        i9 = i6;
                    }
                    i10 = ((LinearLayout.LayoutParams) layoutParams2).gravity;
                    if (i10 < 0) {
                    }
                    i11 = i10 & 112;
                    i12 = i25;
                    if (i11 != 16) {
                    }
                    int i282 = (!hasDividerBeforeChildAt(i26) ? i27 + this.mDividerWidth : i27) + ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                    childAt2.layout(i282, iM2, i282 + measuredWidth2, measuredHeight2 + iM2);
                    iM = measuredWidth2 + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + i282;
                    i25 = i12 + 1;
                    i6 = i9;
                    i7 = i8;
                }
            }
            i9 = i6;
            i12 = i25;
            i25 = i12 + 1;
            i6 = i9;
            i7 = i8;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:228:0x04e4  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x04f9  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0527  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0537  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x053e  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0548  */
    /* JADX WARN: Removed duplicated region for block: B:366:0x079d  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0148  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int iMax;
        int i6;
        int i7;
        int baseline;
        int i8;
        int i9;
        int[] iArr;
        int i10;
        int i11;
        boolean z;
        boolean z2;
        LayoutParams layoutParams;
        int i12;
        int[] iArr2;
        int i13;
        View view;
        int i14;
        boolean z3;
        boolean z4;
        int iMax2;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        boolean z5;
        int i23;
        int i24;
        int i25;
        View view2;
        boolean z6;
        LinearLayoutCompat linearLayoutCompat = this;
        int i26 = -2;
        int i27 = 1073741824;
        int i28 = 8;
        int iMax3 = 0;
        if (linearLayoutCompat.mOrientation == 1) {
            linearLayoutCompat.mTotalLength = 0;
            int childCount = linearLayoutCompat.getChildCount();
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int i29 = linearLayoutCompat.mBaselineAlignedChildIndex;
            boolean z7 = linearLayoutCompat.mUseLargestChild;
            int i30 = 0;
            int iMax4 = 0;
            int iMax5 = 0;
            boolean z8 = false;
            int i31 = 0;
            boolean z9 = false;
            boolean z10 = true;
            float f = 0.0f;
            int iMax6 = 0;
            while (i30 < childCount) {
                int i32 = mode;
                View childAt = linearLayoutCompat.getChildAt(i30);
                if (childAt == null) {
                    linearLayoutCompat.mTotalLength = linearLayoutCompat.mTotalLength;
                } else {
                    if (childAt.getVisibility() != i28) {
                        if (linearLayoutCompat.hasDividerBeforeChildAt(i30)) {
                            linearLayoutCompat.mTotalLength += linearLayoutCompat.mDividerHeight;
                        }
                        LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                        float f2 = ((LinearLayout.LayoutParams) layoutParams2).weight;
                        f += f2;
                        if (mode2 == i27 && ((LinearLayout.LayoutParams) layoutParams2).height == 0 && f2 > 0.0f) {
                            int i33 = linearLayoutCompat.mTotalLength;
                            linearLayoutCompat.mTotalLength = Math.max(i33, ((LinearLayout.LayoutParams) layoutParams2).topMargin + i33 + ((LinearLayout.LayoutParams) layoutParams2).bottomMargin);
                            view2 = childAt;
                            i22 = mode2;
                            i23 = i29;
                            z5 = z7;
                            i24 = i30;
                            z8 = true;
                            i25 = i32;
                        } else {
                            if (((LinearLayout.LayoutParams) layoutParams2).height != 0 || f2 <= 0.0f) {
                                i19 = Integer.MIN_VALUE;
                            } else {
                                ((LinearLayout.LayoutParams) layoutParams2).height = i26;
                                i19 = 0;
                            }
                            if (f == 0.0f) {
                                i20 = i30;
                                i21 = linearLayoutCompat.mTotalLength;
                            } else {
                                i20 = i30;
                                i21 = 0;
                            }
                            i22 = mode2;
                            z5 = z7;
                            i23 = i29;
                            i24 = i20;
                            i25 = i32;
                            linearLayoutCompat.measureChildWithMargins(childAt, i, 0, i2, i21);
                            if (i19 != Integer.MIN_VALUE) {
                                ((LinearLayout.LayoutParams) layoutParams2).height = i19;
                            }
                            int measuredHeight = childAt.getMeasuredHeight();
                            int i34 = linearLayoutCompat.mTotalLength;
                            view2 = childAt;
                            linearLayoutCompat.mTotalLength = Math.max(i34, i34 + measuredHeight + ((LinearLayout.LayoutParams) layoutParams2).topMargin + ((LinearLayout.LayoutParams) layoutParams2).bottomMargin);
                            if (z5) {
                                iMax6 = Math.max(measuredHeight, iMax6);
                            }
                        }
                        if (i23 >= 0 && i23 == i24 + 1) {
                            linearLayoutCompat.mBaselineChildTop = linearLayoutCompat.mTotalLength;
                        }
                        if (i24 < i23 && ((LinearLayout.LayoutParams) layoutParams2).weight > 0.0f) {
                            throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                        }
                        if (i25 == 1073741824 || ((LinearLayout.LayoutParams) layoutParams2).width != -1) {
                            z6 = false;
                        } else {
                            z6 = true;
                            z9 = true;
                        }
                        int i35 = ((LinearLayout.LayoutParams) layoutParams2).leftMargin + ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                        int measuredWidth = view2.getMeasuredWidth() + i35;
                        int iMax7 = Math.max(iMax3, measuredWidth);
                        int measuredState = view2.getMeasuredState();
                        boolean z11 = z6;
                        int iCombineMeasuredStates = View.combineMeasuredStates(i31, measuredState);
                        if (z10) {
                            i31 = iCombineMeasuredStates;
                            boolean z12 = ((LinearLayout.LayoutParams) layoutParams2).width == -1;
                            if (((LinearLayout.LayoutParams) layoutParams2).weight <= 0.0f) {
                                if (!z11) {
                                    i35 = measuredWidth;
                                }
                                iMax5 = Math.max(iMax5, i35);
                            } else {
                                if (!z11) {
                                    i35 = measuredWidth;
                                }
                                iMax4 = Math.max(iMax4, i35);
                            }
                            z10 = z12;
                            iMax3 = iMax7;
                        } else {
                            i31 = iCombineMeasuredStates;
                        }
                        if (((LinearLayout.LayoutParams) layoutParams2).weight <= 0.0f) {
                        }
                        z10 = z12;
                        iMax3 = iMax7;
                    }
                    i30 = i24 + 1;
                    i29 = i23;
                    mode = i25;
                    z7 = z5;
                    mode2 = i22;
                    i26 = -2;
                    i27 = 1073741824;
                    i28 = 8;
                }
                i22 = mode2;
                i23 = i29;
                z5 = z7;
                i24 = i30;
                i25 = i32;
                i30 = i24 + 1;
                i29 = i23;
                mode = i25;
                z7 = z5;
                mode2 = i22;
                i26 = -2;
                i27 = 1073741824;
                i28 = 8;
            }
            int i36 = mode;
            int i37 = mode2;
            boolean z13 = z7;
            int i38 = i31;
            int i39 = i2;
            if (linearLayoutCompat.mTotalLength > 0 && linearLayoutCompat.hasDividerBeforeChildAt(childCount)) {
                linearLayoutCompat.mTotalLength += linearLayoutCompat.mDividerHeight;
            }
            if (z13 && (i37 == Integer.MIN_VALUE || i37 == 0)) {
                linearLayoutCompat.mTotalLength = 0;
                for (int i40 = 0; i40 < childCount; i40++) {
                    View childAt2 = linearLayoutCompat.getChildAt(i40);
                    if (childAt2 == null) {
                        linearLayoutCompat.mTotalLength = linearLayoutCompat.mTotalLength;
                    } else if (childAt2.getVisibility() != 8) {
                        LayoutParams layoutParams3 = (LayoutParams) childAt2.getLayoutParams();
                        int i41 = linearLayoutCompat.mTotalLength;
                        linearLayoutCompat.mTotalLength = Math.max(i41, i41 + iMax6 + ((LinearLayout.LayoutParams) layoutParams3).topMargin + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin);
                    }
                }
            }
            int paddingBottom = linearLayoutCompat.getPaddingBottom() + linearLayoutCompat.getPaddingTop() + linearLayoutCompat.mTotalLength;
            linearLayoutCompat.mTotalLength = paddingBottom;
            int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingBottom, linearLayoutCompat.getSuggestedMinimumHeight()), i39, 0);
            int i42 = (iResolveSizeAndState & 16777215) - linearLayoutCompat.mTotalLength;
            if (z8 || (i42 != 0 && f > 0.0f)) {
                float f3 = linearLayoutCompat.mWeightSum;
                if (f3 > 0.0f) {
                    f = f3;
                }
                linearLayoutCompat.mTotalLength = 0;
                int iCombineMeasuredStates2 = i38;
                int i43 = 0;
                while (i43 < childCount) {
                    View childAt3 = linearLayoutCompat.getChildAt(i43);
                    if (childAt3.getVisibility() == 8) {
                        i16 = i43;
                    } else {
                        LayoutParams layoutParams4 = (LayoutParams) childAt3.getLayoutParams();
                        float f4 = ((LinearLayout.LayoutParams) layoutParams4).weight;
                        if (f4 > 0.0f) {
                            int i44 = (int) ((i42 * f4) / f);
                            f -= f4;
                            i42 -= i44;
                            i16 = i43;
                            int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, linearLayoutCompat.getPaddingRight() + linearLayoutCompat.getPaddingLeft() + ((LinearLayout.LayoutParams) layoutParams4).leftMargin + ((LinearLayout.LayoutParams) layoutParams4).rightMargin, ((LinearLayout.LayoutParams) layoutParams4).width);
                            if (((LinearLayout.LayoutParams) layoutParams4).height == 0) {
                                i18 = 1073741824;
                                if (i37 == 1073741824) {
                                    if (i44 <= 0) {
                                        i44 = 0;
                                    }
                                    childAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(i44, 1073741824));
                                }
                                iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, childAt3.getMeasuredState() & (-256));
                            } else {
                                i18 = 1073741824;
                            }
                            int measuredHeight2 = childAt3.getMeasuredHeight() + i44;
                            if (measuredHeight2 < 0) {
                                measuredHeight2 = 0;
                            }
                            childAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight2, i18));
                            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, childAt3.getMeasuredState() & (-256));
                        } else {
                            i16 = i43;
                        }
                        int i45 = ((LinearLayout.LayoutParams) layoutParams4).leftMargin + ((LinearLayout.LayoutParams) layoutParams4).rightMargin;
                        int measuredWidth2 = childAt3.getMeasuredWidth() + i45;
                        iMax3 = Math.max(iMax3, measuredWidth2);
                        if (i36 != 1073741824) {
                            i17 = -1;
                            if (((LinearLayout.LayoutParams) layoutParams4).width == -1) {
                                measuredWidth2 = i45;
                            }
                        } else {
                            i17 = -1;
                        }
                        iMax4 = Math.max(iMax4, measuredWidth2);
                        boolean z14 = z10 && ((LinearLayout.LayoutParams) layoutParams4).width == i17;
                        int i46 = linearLayoutCompat.mTotalLength;
                        linearLayoutCompat.mTotalLength = Math.max(i46, childAt3.getMeasuredHeight() + i46 + ((LinearLayout.LayoutParams) layoutParams4).topMargin + ((LinearLayout.LayoutParams) layoutParams4).bottomMargin);
                        z10 = z14;
                    }
                    i43 = i16 + 1;
                }
                linearLayoutCompat.mTotalLength = linearLayoutCompat.getPaddingBottom() + linearLayoutCompat.getPaddingTop() + linearLayoutCompat.mTotalLength;
                i38 = iCombineMeasuredStates2;
            } else {
                iMax4 = Math.max(iMax4, iMax5);
                if (z13 && i37 != 1073741824) {
                    for (int i47 = 0; i47 < childCount; i47++) {
                        View childAt4 = linearLayoutCompat.getChildAt(i47);
                        if (childAt4 != null && childAt4.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) childAt4.getLayoutParams())).weight > 0.0f) {
                            childAt4.measure(View.MeasureSpec.makeMeasureSpec(childAt4.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(iMax6, 1073741824));
                        }
                    }
                }
            }
            if (z10 || i36 == 1073741824) {
                iMax4 = iMax3;
            }
            linearLayoutCompat.setMeasuredDimension(View.resolveSizeAndState(Math.max(linearLayoutCompat.getPaddingRight() + linearLayoutCompat.getPaddingLeft() + iMax4, linearLayoutCompat.getSuggestedMinimumWidth()), i, i38), iResolveSizeAndState);
            if (z9) {
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(linearLayoutCompat.getMeasuredWidth(), 1073741824);
                int i48 = 0;
                while (i48 < childCount) {
                    View childAt5 = linearLayoutCompat.getChildAt(i48);
                    if (childAt5.getVisibility() != 8) {
                        LayoutParams layoutParams5 = (LayoutParams) childAt5.getLayoutParams();
                        if (((LinearLayout.LayoutParams) layoutParams5).width == -1) {
                            int i49 = ((LinearLayout.LayoutParams) layoutParams5).height;
                            ((LinearLayout.LayoutParams) layoutParams5).height = childAt5.getMeasuredHeight();
                            linearLayoutCompat.measureChildWithMargins(childAt5, iMakeMeasureSpec, 0, i39, 0);
                            ((LinearLayout.LayoutParams) layoutParams5).height = i49;
                        }
                    }
                    i48++;
                    i39 = i2;
                }
                return;
            }
            return;
        }
        int i50 = i;
        linearLayoutCompat.mTotalLength = 0;
        int childCount2 = linearLayoutCompat.getChildCount();
        int mode3 = View.MeasureSpec.getMode(i50);
        int mode4 = View.MeasureSpec.getMode(i2);
        if (linearLayoutCompat.mMaxAscent == null || linearLayoutCompat.mMaxDescent == null) {
            linearLayoutCompat.mMaxAscent = new int[4];
            linearLayoutCompat.mMaxDescent = new int[4];
        }
        int[] iArr3 = linearLayoutCompat.mMaxAscent;
        int[] iArr4 = linearLayoutCompat.mMaxDescent;
        iArr3[3] = -1;
        char c = 2;
        iArr3[2] = -1;
        iArr3[1] = -1;
        iArr3[0] = -1;
        iArr4[3] = -1;
        iArr4[2] = -1;
        iArr4[1] = -1;
        iArr4[0] = -1;
        boolean z15 = linearLayoutCompat.mBaselineAligned;
        boolean z16 = linearLayoutCompat.mUseLargestChild;
        boolean z17 = mode3 == 1073741824;
        float f5 = 0.0f;
        boolean z18 = true;
        int i51 = 0;
        int i52 = 0;
        int i53 = 0;
        int iMax8 = 0;
        int iMax9 = 0;
        int iCombineMeasuredStates3 = 0;
        boolean z19 = false;
        boolean z20 = false;
        while (i51 < childCount2) {
            char c2 = c;
            View childAt6 = linearLayoutCompat.getChildAt(i51);
            if (childAt6 == null) {
                linearLayoutCompat.mTotalLength = linearLayoutCompat.mTotalLength;
                i11 = i51;
                i15 = i53;
                iArr2 = iArr3;
                iArr = iArr4;
                z = z15;
                z2 = z16;
            } else {
                int i54 = i52;
                if (childAt6.getVisibility() == 8) {
                    i50 = i;
                    i11 = i51;
                    i15 = i53;
                    iArr = iArr4;
                    z = z15;
                    z2 = z16;
                    i52 = i54;
                    iArr2 = iArr3;
                } else {
                    if (linearLayoutCompat.hasDividerBeforeChildAt(i51)) {
                        linearLayoutCompat.mTotalLength += linearLayoutCompat.mDividerWidth;
                    }
                    LayoutParams layoutParams6 = (LayoutParams) childAt6.getLayoutParams();
                    float f6 = ((LinearLayout.LayoutParams) layoutParams6).weight;
                    f5 += f6;
                    int i55 = i51;
                    if (mode3 == 1073741824 && ((LinearLayout.LayoutParams) layoutParams6).width == 0 && f6 > 0.0f) {
                        if (z17) {
                            linearLayoutCompat.mTotalLength = ((LinearLayout.LayoutParams) layoutParams6).leftMargin + ((LinearLayout.LayoutParams) layoutParams6).rightMargin + linearLayoutCompat.mTotalLength;
                        } else {
                            int i56 = linearLayoutCompat.mTotalLength;
                            linearLayoutCompat.mTotalLength = Math.max(i56, ((LinearLayout.LayoutParams) layoutParams6).leftMargin + i56 + ((LinearLayout.LayoutParams) layoutParams6).rightMargin);
                        }
                        if (z15) {
                            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
                            childAt6.measure(iMakeMeasureSpec2, iMakeMeasureSpec2);
                            view = childAt6;
                            z = z15;
                            z2 = z16;
                            i12 = i54;
                            i11 = i55;
                            layoutParams = layoutParams6;
                            iArr2 = iArr3;
                            iArr = iArr4;
                            i50 = i;
                            i13 = i53;
                            i10 = iMax8;
                        } else {
                            view = childAt6;
                            z = z15;
                            z2 = z16;
                            z20 = true;
                            i12 = i54;
                            i11 = i55;
                            i14 = 1073741824;
                            layoutParams = layoutParams6;
                            iArr2 = iArr3;
                            iArr = iArr4;
                            i50 = i;
                            i13 = i53;
                            i10 = iMax8;
                            if (mode4 == i14 && ((LinearLayout.LayoutParams) layoutParams).height == -1) {
                                z3 = true;
                                z19 = true;
                            } else {
                                z3 = false;
                            }
                            int i57 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                            int measuredHeight3 = view.getMeasuredHeight() + i57;
                            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, view.getMeasuredState());
                            if (z) {
                                z4 = z3;
                            } else {
                                int baseline2 = view.getBaseline();
                                z4 = z3;
                                if (baseline2 != -1) {
                                    int i58 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                                    if (i58 < 0) {
                                        i58 = linearLayoutCompat.mGravity;
                                    }
                                    int i59 = (((i58 & 112) >> 4) & (-2)) >> 1;
                                    iArr2[i59] = Math.max(iArr2[i59], baseline2);
                                    iArr[i59] = Math.max(iArr[i59], measuredHeight3 - baseline2);
                                }
                            }
                            int iMax10 = Math.max(i12, measuredHeight3);
                            boolean z21 = !z18 && ((LinearLayout.LayoutParams) layoutParams).height == -1;
                            if (((LinearLayout.LayoutParams) layoutParams).weight <= 0.0f) {
                                if (!z4) {
                                    i57 = measuredHeight3;
                                }
                                iMax8 = Math.max(i10, i57);
                                iMax2 = i13;
                            } else {
                                if (!z4) {
                                    i57 = measuredHeight3;
                                }
                                iMax2 = Math.max(i13, i57);
                                iMax8 = i10;
                            }
                            int i60 = iMax2;
                            i52 = iMax10;
                            i15 = i60;
                            z18 = z21;
                        }
                    } else {
                        if (((LinearLayout.LayoutParams) layoutParams6).width != 0 || f6 <= 0.0f) {
                            i9 = Integer.MIN_VALUE;
                        } else {
                            ((LinearLayout.LayoutParams) layoutParams6).width = -2;
                            i9 = 0;
                        }
                        iArr = iArr4;
                        i10 = iMax8;
                        i11 = i55;
                        z = z15;
                        z2 = z16;
                        int i61 = i9;
                        layoutParams = layoutParams6;
                        i12 = i54;
                        i50 = i;
                        iArr2 = iArr3;
                        i13 = i53;
                        linearLayoutCompat.measureChildWithMargins(childAt6, i50, f5 == 0.0f ? linearLayoutCompat.mTotalLength : 0, i2, 0);
                        if (i61 != Integer.MIN_VALUE) {
                            ((LinearLayout.LayoutParams) layoutParams).width = i61;
                        }
                        int measuredWidth3 = childAt6.getMeasuredWidth();
                        if (z17) {
                            view = childAt6;
                            linearLayoutCompat.mTotalLength = ((LinearLayout.LayoutParams) layoutParams).leftMargin + measuredWidth3 + ((LinearLayout.LayoutParams) layoutParams).rightMargin + linearLayoutCompat.mTotalLength;
                        } else {
                            view = childAt6;
                            int i62 = linearLayoutCompat.mTotalLength;
                            linearLayoutCompat.mTotalLength = Math.max(i62, i62 + measuredWidth3 + ((LinearLayout.LayoutParams) layoutParams).leftMargin + ((LinearLayout.LayoutParams) layoutParams).rightMargin);
                        }
                        if (z2) {
                            iMax9 = Math.max(measuredWidth3, iMax9);
                        }
                    }
                    i14 = 1073741824;
                    if (mode4 == i14) {
                        z3 = false;
                        int i572 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                        int measuredHeight32 = view.getMeasuredHeight() + i572;
                        iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, view.getMeasuredState());
                        if (z) {
                        }
                        int iMax102 = Math.max(i12, measuredHeight32);
                        if (z18) {
                            if (((LinearLayout.LayoutParams) layoutParams).weight <= 0.0f) {
                            }
                            int i602 = iMax2;
                            i52 = iMax102;
                            i15 = i602;
                            z18 = z21;
                        }
                    }
                }
            }
            i53 = i15;
            i51 = i11 + 1;
            c = c2;
            iArr3 = iArr2;
            iArr4 = iArr;
            z15 = z;
            z16 = z2;
        }
        int[] iArr5 = iArr3;
        int[] iArr6 = iArr4;
        char c3 = c;
        boolean z22 = z15;
        boolean z23 = z16;
        int i63 = i52;
        int i64 = i53;
        int i65 = iMax8;
        if (linearLayoutCompat.mTotalLength > 0 && linearLayoutCompat.hasDividerBeforeChildAt(childCount2)) {
            linearLayoutCompat.mTotalLength += linearLayoutCompat.mDividerWidth;
        }
        int i66 = iArr5[1];
        int iMax11 = (i66 == -1 && iArr5[0] == -1 && iArr5[c3] == -1 && iArr5[3] == -1) ? i63 : Math.max(i63, Math.max(iArr6[3], Math.max(iArr6[0], Math.max(iArr6[1], iArr6[c3]))) + Math.max(iArr5[3], Math.max(iArr5[0], Math.max(i66, iArr5[c3]))));
        if (z23 && (mode3 == Integer.MIN_VALUE || mode3 == 0)) {
            linearLayoutCompat.mTotalLength = 0;
            for (int i67 = 0; i67 < childCount2; i67++) {
                View childAt7 = linearLayoutCompat.getChildAt(i67);
                if (childAt7 == null) {
                    linearLayoutCompat.mTotalLength = linearLayoutCompat.mTotalLength;
                } else if (childAt7.getVisibility() != 8) {
                    LayoutParams layoutParams7 = (LayoutParams) childAt7.getLayoutParams();
                    if (z17) {
                        linearLayoutCompat.mTotalLength = ((LinearLayout.LayoutParams) layoutParams7).leftMargin + iMax9 + ((LinearLayout.LayoutParams) layoutParams7).rightMargin + linearLayoutCompat.mTotalLength;
                    } else {
                        int i68 = linearLayoutCompat.mTotalLength;
                        linearLayoutCompat.mTotalLength = Math.max(i68, i68 + iMax9 + ((LinearLayout.LayoutParams) layoutParams7).leftMargin + ((LinearLayout.LayoutParams) layoutParams7).rightMargin);
                    }
                }
            }
        }
        int paddingRight = linearLayoutCompat.getPaddingRight() + linearLayoutCompat.getPaddingLeft() + linearLayoutCompat.mTotalLength;
        linearLayoutCompat.mTotalLength = paddingRight;
        int iResolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingRight, linearLayoutCompat.getSuggestedMinimumWidth()), i50, 0);
        int i69 = (iResolveSizeAndState2 & 16777215) - linearLayoutCompat.mTotalLength;
        if (z20 || (i69 != 0 && f5 > 0.0f)) {
            float f7 = linearLayoutCompat.mWeightSum;
            if (f7 > 0.0f) {
                f5 = f7;
            }
            iArr5[3] = -1;
            iArr5[c3] = -1;
            iArr5[1] = -1;
            iArr5[0] = -1;
            iArr6[3] = -1;
            iArr6[c3] = -1;
            iArr6[1] = -1;
            iArr6[0] = -1;
            linearLayoutCompat.mTotalLength = 0;
            iMax11 = -1;
            int i70 = 0;
            while (i70 < childCount2) {
                View childAt8 = linearLayoutCompat.getChildAt(i70);
                if (childAt8 == null || childAt8.getVisibility() == 8) {
                    i6 = iResolveSizeAndState2;
                } else {
                    LayoutParams layoutParams8 = (LayoutParams) childAt8.getLayoutParams();
                    float f8 = ((LinearLayout.LayoutParams) layoutParams8).weight;
                    if (f8 > 0.0f) {
                        int i71 = (int) ((i69 * f8) / f5);
                        f5 -= f8;
                        i69 -= i71;
                        i6 = iResolveSizeAndState2;
                        int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i2, linearLayoutCompat.getPaddingBottom() + linearLayoutCompat.getPaddingTop() + ((LinearLayout.LayoutParams) layoutParams8).topMargin + ((LinearLayout.LayoutParams) layoutParams8).bottomMargin, ((LinearLayout.LayoutParams) layoutParams8).height);
                        if (((LinearLayout.LayoutParams) layoutParams8).width == 0) {
                            i8 = 1073741824;
                            if (mode3 == 1073741824) {
                                if (i71 <= 0) {
                                    i71 = 0;
                                }
                                childAt8.measure(View.MeasureSpec.makeMeasureSpec(i71, 1073741824), childMeasureSpec2);
                            }
                            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, childAt8.getMeasuredState() & (-16777216));
                        } else {
                            i8 = 1073741824;
                        }
                        int measuredWidth4 = childAt8.getMeasuredWidth() + i71;
                        if (measuredWidth4 < 0) {
                            measuredWidth4 = 0;
                        }
                        childAt8.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth4, i8), childMeasureSpec2);
                        iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, childAt8.getMeasuredState() & (-16777216));
                    } else {
                        i6 = iResolveSizeAndState2;
                    }
                    if (z17) {
                        linearLayoutCompat.mTotalLength = childAt8.getMeasuredWidth() + ((LinearLayout.LayoutParams) layoutParams8).leftMargin + ((LinearLayout.LayoutParams) layoutParams8).rightMargin + linearLayoutCompat.mTotalLength;
                    } else {
                        int i72 = linearLayoutCompat.mTotalLength;
                        linearLayoutCompat.mTotalLength = Math.max(i72, childAt8.getMeasuredWidth() + i72 + ((LinearLayout.LayoutParams) layoutParams8).leftMargin + ((LinearLayout.LayoutParams) layoutParams8).rightMargin);
                    }
                    boolean z24 = mode4 != 1073741824 && ((LinearLayout.LayoutParams) layoutParams8).height == -1;
                    int i73 = ((LinearLayout.LayoutParams) layoutParams8).topMargin + ((LinearLayout.LayoutParams) layoutParams8).bottomMargin;
                    int measuredHeight4 = childAt8.getMeasuredHeight() + i73;
                    iMax11 = Math.max(iMax11, measuredHeight4);
                    if (!z24) {
                        i73 = measuredHeight4;
                    }
                    int iMax12 = Math.max(i64, i73);
                    if (z18) {
                        i7 = -1;
                        boolean z25 = ((LinearLayout.LayoutParams) layoutParams8).height == -1;
                        if (!z22 && (baseline = childAt8.getBaseline()) != i7) {
                            int i74 = ((LinearLayout.LayoutParams) layoutParams8).gravity;
                            if (i74 < 0) {
                                i74 = linearLayoutCompat.mGravity;
                            }
                            int i75 = (((i74 & 112) >> 4) & (-2)) >> 1;
                            iArr5[i75] = Math.max(iArr5[i75], baseline);
                            iArr6[i75] = Math.max(iArr6[i75], measuredHeight4 - baseline);
                        }
                        z18 = z25;
                        i64 = iMax12;
                    } else {
                        i7 = -1;
                    }
                    if (!z22) {
                        z18 = z25;
                        i64 = iMax12;
                    }
                }
                i70++;
                iResolveSizeAndState2 = i6;
            }
            i3 = iResolveSizeAndState2;
            i4 = -16777216;
            linearLayoutCompat.mTotalLength = linearLayoutCompat.getPaddingRight() + linearLayoutCompat.getPaddingLeft() + linearLayoutCompat.mTotalLength;
            int i76 = iArr5[1];
            if (i76 == -1 && iArr5[0] == -1 && iArr5[c3] == -1 && iArr5[3] == -1) {
                i5 = 0;
            } else {
                i5 = 0;
                iMax11 = Math.max(iMax11, Math.max(iArr6[3], Math.max(iArr6[0], Math.max(iArr6[1], iArr6[c3]))) + Math.max(iArr5[3], Math.max(iArr5[0], Math.max(i76, iArr5[c3]))));
            }
            iMax = i64;
        } else {
            iMax = Math.max(i64, i65);
            if (z23 && mode3 != 1073741824) {
                for (int i77 = 0; i77 < childCount2; i77++) {
                    View childAt9 = linearLayoutCompat.getChildAt(i77);
                    if (childAt9 != null && childAt9.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) childAt9.getLayoutParams())).weight > 0.0f) {
                        childAt9.measure(View.MeasureSpec.makeMeasureSpec(iMax9, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt9.getMeasuredHeight(), 1073741824));
                    }
                }
            }
            i3 = iResolveSizeAndState2;
            i4 = -16777216;
            i5 = 0;
        }
        if (!z18 && mode4 != 1073741824) {
            iMax11 = iMax;
        }
        linearLayoutCompat.setMeasuredDimension(i3 | (iCombineMeasuredStates3 & i4), View.resolveSizeAndState(Math.max(linearLayoutCompat.getPaddingBottom() + linearLayoutCompat.getPaddingTop() + iMax11, linearLayoutCompat.getSuggestedMinimumHeight()), i2, iCombineMeasuredStates3 << 16));
        if (z19) {
            int iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(linearLayoutCompat.getMeasuredHeight(), 1073741824);
            int i78 = i5;
            while (i78 < childCount2) {
                View childAt10 = linearLayoutCompat.getChildAt(i78);
                if (childAt10.getVisibility() != 8) {
                    LayoutParams layoutParams9 = (LayoutParams) childAt10.getLayoutParams();
                    if (((LinearLayout.LayoutParams) layoutParams9).height == -1) {
                        int i79 = ((LinearLayout.LayoutParams) layoutParams9).width;
                        ((LinearLayout.LayoutParams) layoutParams9).width = childAt10.getMeasuredWidth();
                        linearLayoutCompat.measureChildWithMargins(childAt10, i50, 0, iMakeMeasureSpec3, 0);
                        ((LinearLayout.LayoutParams) layoutParams9).width = i79;
                    }
                }
                i78++;
                linearLayoutCompat = this;
                i50 = i;
            }
        }
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        int i = this.mOrientation;
        if (i == 0) {
            return new LayoutParams(-2, -2);
        }
        if (i == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        int[] iArr = R$styleable.LinearLayoutCompat;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        TypedArray typedArray = tintTypedArrayObtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArray, i, 0);
        int i2 = tintTypedArrayObtainStyledAttributes.mWrapped.getInt(1, -1);
        if (i2 >= 0 && this.mOrientation != i2) {
            this.mOrientation = i2;
            requestLayout();
        }
        int i3 = tintTypedArrayObtainStyledAttributes.mWrapped.getInt(0, -1);
        if (i3 >= 0 && this.mGravity != i3) {
            i3 = (8388615 & i3) == 0 ? i3 | 8388611 : i3;
            this.mGravity = (i3 & 112) == 0 ? i3 | 48 : i3;
            requestLayout();
        }
        boolean z = tintTypedArrayObtainStyledAttributes.mWrapped.getBoolean(2, true);
        if (!z) {
            this.mBaselineAligned = z;
        }
        this.mWeightSum = tintTypedArrayObtainStyledAttributes.mWrapped.getFloat(4, -1.0f);
        this.mBaselineAlignedChildIndex = tintTypedArrayObtainStyledAttributes.mWrapped.getInt(3, -1);
        this.mUseLargestChild = tintTypedArrayObtainStyledAttributes.mWrapped.getBoolean(7, false);
        Drawable drawable = tintTypedArrayObtainStyledAttributes.getDrawable(5);
        if (drawable != this.mDivider) {
            this.mDivider = drawable;
            if (drawable != null) {
                this.mDividerWidth = drawable.getIntrinsicWidth();
                this.mDividerHeight = drawable.getIntrinsicHeight();
            } else {
                this.mDividerWidth = 0;
                this.mDividerHeight = 0;
            }
            setWillNotDraw(drawable == null);
            requestLayout();
        }
        this.mShowDividers = tintTypedArrayObtainStyledAttributes.mWrapped.getInt(8, 0);
        this.mDividerPadding = tintTypedArrayObtainStyledAttributes.mWrapped.getDimensionPixelSize(6, 0);
        tintTypedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }
}
