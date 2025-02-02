package androidx.appcompat.widget;

import android.content.Context;
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

/* loaded from: classes.dex */
public class LinearLayoutCompat extends ViewGroup {
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    public static class LayoutParams extends LinearLayout.LayoutParams {
    }

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    final void drawHorizontalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i);
        this.mDivider.draw(canvas);
    }

    final void drawVerticalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(i, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + i, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    @Override // android.view.View
    public int getBaseline() {
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
        int i3 = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (i = this.mGravity & 112) != 48) {
            if (i == 16) {
                i3 += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
            } else if (i == 80) {
                i3 = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
            }
        }
        return i3 + ((LinearLayout.LayoutParams) ((LayoutParams) childAt.getLayoutParams())).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    protected final boolean hasDividerBeforeChildAt(int i) {
        if (i == 0) {
            return (this.mShowDividers & 1) != 0;
        }
        if (i == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        }
        if ((this.mShowDividers & 2) == 0) {
            return false;
        }
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (getChildAt(i2).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    protected final void onDraw(Canvas canvas) {
        int right;
        int left;
        int i;
        if (this.mDivider == null) {
            return;
        }
        int i2 = 0;
        if (this.mOrientation == 1) {
            int virtualChildCount = getVirtualChildCount();
            while (i2 < virtualChildCount) {
                View childAt = getChildAt(i2);
                if (childAt != null && childAt.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                    drawHorizontalDivider(canvas, (childAt.getTop() - ((LinearLayout.LayoutParams) ((LayoutParams) childAt.getLayoutParams())).topMargin) - this.mDividerHeight);
                }
                i2++;
            }
            if (hasDividerBeforeChildAt(virtualChildCount)) {
                View childAt2 = getChildAt(virtualChildCount - 1);
                drawHorizontalDivider(canvas, childAt2 == null ? (getHeight() - getPaddingBottom()) - this.mDividerHeight : childAt2.getBottom() + ((LinearLayout.LayoutParams) ((LayoutParams) childAt2.getLayoutParams())).bottomMargin);
                return;
            }
            return;
        }
        int virtualChildCount2 = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        while (i2 < virtualChildCount2) {
            View childAt3 = getChildAt(i2);
            if (childAt3 != null && childAt3.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                LayoutParams layoutParams = (LayoutParams) childAt3.getLayoutParams();
                drawVerticalDivider(canvas, isLayoutRtl ? childAt3.getRight() + ((LinearLayout.LayoutParams) layoutParams).rightMargin : (childAt3.getLeft() - ((LinearLayout.LayoutParams) layoutParams).leftMargin) - this.mDividerWidth);
            }
            i2++;
        }
        if (hasDividerBeforeChildAt(virtualChildCount2)) {
            View childAt4 = getChildAt(virtualChildCount2 - 1);
            if (childAt4 != null) {
                LayoutParams layoutParams2 = (LayoutParams) childAt4.getLayoutParams();
                if (isLayoutRtl) {
                    left = childAt4.getLeft() - ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                    i = this.mDividerWidth;
                    right = left - i;
                } else {
                    right = childAt4.getRight() + ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                }
            } else if (isLayoutRtl) {
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

    /* JADX WARN: Removed duplicated region for block: B:25:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0195  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17 = 8;
        int i18 = 5;
        if (this.mOrientation == 1) {
            int paddingLeft = getPaddingLeft();
            int i19 = i3 - i;
            int paddingRight = i19 - getPaddingRight();
            int paddingRight2 = (i19 - paddingLeft) - getPaddingRight();
            int virtualChildCount = getVirtualChildCount();
            int i20 = this.mGravity;
            int i21 = i20 & 112;
            int i22 = 8388615 & i20;
            int paddingTop = i21 != 16 ? i21 != 80 ? getPaddingTop() : ((getPaddingTop() + i4) - i2) - this.mTotalLength : getPaddingTop() + (((i4 - i2) - this.mTotalLength) / 2);
            int i23 = 0;
            while (i23 < virtualChildCount) {
                View childAt = getChildAt(i23);
                if (childAt == null) {
                    paddingTop += 0;
                } else if (childAt.getVisibility() != i17) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    int i24 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                    if (i24 < 0) {
                        i24 = i22;
                    }
                    int absoluteGravity = Gravity.getAbsoluteGravity(i24, ViewCompat.getLayoutDirection(this)) & 7;
                    if (absoluteGravity == 1) {
                        i14 = ((paddingRight2 - measuredWidth) / 2) + paddingLeft + ((LinearLayout.LayoutParams) layoutParams).leftMargin;
                        i15 = ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                    } else if (absoluteGravity != i18) {
                        i16 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + paddingLeft;
                        if (hasDividerBeforeChildAt(i23)) {
                            paddingTop += this.mDividerHeight;
                        }
                        int i25 = paddingTop + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                        int i26 = i25 + 0;
                        childAt.layout(i16, i26, measuredWidth + i16, measuredHeight + i26);
                        i23 += 0;
                        paddingTop = measuredHeight + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + 0 + i25;
                    } else {
                        i14 = paddingRight - measuredWidth;
                        i15 = ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                    }
                    i16 = i14 - i15;
                    if (hasDividerBeforeChildAt(i23)) {
                    }
                    int i252 = paddingTop + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    int i262 = i252 + 0;
                    childAt.layout(i16, i262, measuredWidth + i16, measuredHeight + i262);
                    i23 += 0;
                    paddingTop = measuredHeight + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + 0 + i252;
                }
                i23++;
                i17 = 8;
                i18 = 5;
            }
            return;
        }
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop2 = getPaddingTop();
        int i27 = i4 - i2;
        int paddingBottom = i27 - getPaddingBottom();
        int paddingBottom2 = (i27 - paddingTop2) - getPaddingBottom();
        int virtualChildCount2 = getVirtualChildCount();
        int i28 = this.mGravity;
        int i29 = 8388615 & i28;
        int i30 = i28 & 112;
        boolean z2 = this.mBaselineAligned;
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        int absoluteGravity2 = Gravity.getAbsoluteGravity(i29, ViewCompat.getLayoutDirection(this));
        int paddingLeft2 = absoluteGravity2 != 1 ? absoluteGravity2 != 5 ? getPaddingLeft() : ((getPaddingLeft() + i3) - i) - this.mTotalLength : getPaddingLeft() + (((i3 - i) - this.mTotalLength) / 2);
        if (isLayoutRtl) {
            i6 = virtualChildCount2 - 1;
            i5 = -1;
        } else {
            i5 = 1;
            i6 = 0;
        }
        int i31 = paddingLeft2;
        int i32 = 0;
        while (i32 < virtualChildCount2) {
            int i33 = (i5 * i32) + i6;
            View childAt2 = getChildAt(i33);
            if (childAt2 == null) {
                i31 += 0;
            } else if (childAt2.getVisibility() != 8) {
                int measuredWidth2 = childAt2.getMeasuredWidth();
                int measuredHeight2 = childAt2.getMeasuredHeight();
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                if (z2) {
                    i7 = i6;
                    i8 = virtualChildCount2;
                    if (((LinearLayout.LayoutParams) layoutParams2).height != -1) {
                        i9 = childAt2.getBaseline();
                        i10 = ((LinearLayout.LayoutParams) layoutParams2).gravity;
                        if (i10 < 0) {
                            i10 = i30;
                        }
                        i11 = i10 & 112;
                        i12 = i30;
                        if (i11 != 16) {
                            i13 = ((((paddingBottom2 - measuredHeight2) / 2) + paddingTop2) + ((LinearLayout.LayoutParams) layoutParams2).topMargin) - ((LinearLayout.LayoutParams) layoutParams2).bottomMargin;
                        } else if (i11 == 48) {
                            i13 = ((LinearLayout.LayoutParams) layoutParams2).topMargin + paddingTop2;
                            if (i9 != -1) {
                                i13 = (iArr[1] - i9) + i13;
                            }
                        } else if (i11 != 80) {
                            i13 = paddingTop2;
                        } else {
                            i13 = (paddingBottom - measuredHeight2) - ((LinearLayout.LayoutParams) layoutParams2).bottomMargin;
                            if (i9 != -1) {
                                i13 -= iArr2[2] - (childAt2.getMeasuredHeight() - i9);
                            }
                        }
                        if (hasDividerBeforeChildAt(i33)) {
                            i31 += this.mDividerWidth;
                        }
                        int i34 = i31 + ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                        int i35 = i34 + 0;
                        childAt2.layout(i35, i13, measuredWidth2 + i35, measuredHeight2 + i13);
                        i31 = measuredWidth2 + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + 0 + i34;
                        i32 += 0;
                        i32++;
                        i6 = i7;
                        virtualChildCount2 = i8;
                        i30 = i12;
                    }
                } else {
                    i7 = i6;
                    i8 = virtualChildCount2;
                }
                i9 = -1;
                i10 = ((LinearLayout.LayoutParams) layoutParams2).gravity;
                if (i10 < 0) {
                }
                i11 = i10 & 112;
                i12 = i30;
                if (i11 != 16) {
                }
                if (hasDividerBeforeChildAt(i33)) {
                }
                int i342 = i31 + ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                int i352 = i342 + 0;
                childAt2.layout(i352, i13, measuredWidth2 + i352, measuredHeight2 + i13);
                i31 = measuredWidth2 + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + 0 + i342;
                i32 += 0;
                i32++;
                i6 = i7;
                virtualChildCount2 = i8;
                i30 = i12;
            }
            i7 = i6;
            i8 = virtualChildCount2;
            i12 = i30;
            i32++;
            i6 = i7;
            virtualChildCount2 = i8;
            i30 = i12;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:177:0x02f9  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x056b  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0579  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x049e  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x04cb  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x04d8  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x04fa  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x04e6  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x04d0  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x04a3  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x060f  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x08a4  */
    /* JADX WARN: Removed duplicated region for block: B:357:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:361:0x06cb  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x06e7  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int i, int i2) {
        char c;
        int i3;
        int max;
        float f;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        char c2;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        float f2;
        int i15;
        int i16;
        boolean z;
        int baseline;
        int i17;
        int i18;
        int i19;
        int i20;
        float f3;
        int i21;
        int i22;
        int i23;
        int i24;
        int i25;
        boolean z2;
        boolean z3;
        LayoutParams layoutParams;
        boolean z4;
        int i26;
        boolean z5;
        int i27;
        int i28;
        int i29;
        int max2;
        int baseline2;
        int i30;
        int i31;
        int i32;
        int i33;
        boolean z6;
        int i34;
        int i35;
        int i36;
        int i37;
        int i38;
        int i39;
        boolean z7;
        LayoutParams layoutParams2;
        boolean z8;
        int i40;
        boolean z9;
        int combineMeasuredStates;
        int i41 = -2;
        int i42 = Integer.MIN_VALUE;
        int i43 = 8;
        int i44 = 1073741824;
        float f4 = 0.0f;
        int i45 = 0;
        boolean z10 = true;
        if (this.mOrientation == 1) {
            this.mTotalLength = 0;
            int virtualChildCount = getVirtualChildCount();
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int i46 = this.mBaselineAlignedChildIndex;
            boolean z11 = this.mUseLargestChild;
            boolean z12 = true;
            int i47 = 0;
            int i48 = 0;
            int i49 = 0;
            int i50 = 0;
            int i51 = 0;
            int i52 = 0;
            boolean z13 = false;
            boolean z14 = false;
            float f5 = 0.0f;
            while (i47 < virtualChildCount) {
                View childAt = getChildAt(i47);
                if (childAt == null) {
                    this.mTotalLength += i45;
                } else if (childAt.getVisibility() == i43) {
                    i47 += 0;
                } else {
                    if (hasDividerBeforeChildAt(i47)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                    LayoutParams layoutParams3 = (LayoutParams) childAt.getLayoutParams();
                    float f6 = ((LinearLayout.LayoutParams) layoutParams3).weight;
                    f5 += f6;
                    if (mode2 == i44 && ((LinearLayout.LayoutParams) layoutParams3).height == 0 && f6 > f4) {
                        int i53 = this.mTotalLength;
                        this.mTotalLength = Math.max(i53, ((LinearLayout.LayoutParams) layoutParams3).topMargin + i53 + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin);
                        i36 = i46;
                        i37 = mode2;
                        i38 = mode;
                        i39 = virtualChildCount;
                        layoutParams2 = layoutParams3;
                        z8 = true;
                        z7 = true;
                    } else {
                        if (((LinearLayout.LayoutParams) layoutParams3).height != 0 || f6 <= f4) {
                            i35 = i42;
                        } else {
                            ((LinearLayout.LayoutParams) layoutParams3).height = i41;
                            i35 = 0;
                        }
                        int i54 = f5 == f4 ? this.mTotalLength : 0;
                        i36 = i46;
                        i37 = mode2;
                        i38 = mode;
                        i39 = virtualChildCount;
                        z7 = true;
                        layoutParams2 = layoutParams3;
                        measureChildWithMargins(childAt, i, 0, i2, i54);
                        if (i35 != i42) {
                            ((LinearLayout.LayoutParams) layoutParams2).height = i35;
                        }
                        int measuredHeight = childAt.getMeasuredHeight();
                        int i55 = this.mTotalLength;
                        this.mTotalLength = Math.max(i55, i55 + measuredHeight + ((LinearLayout.LayoutParams) layoutParams2).topMargin + ((LinearLayout.LayoutParams) layoutParams2).bottomMargin + 0);
                        if (z11) {
                            i50 = Math.max(measuredHeight, i50);
                        }
                        z8 = z13;
                    }
                    if (i36 >= 0 && i36 == i47 + 1) {
                        this.mBaselineChildTop = this.mTotalLength;
                    }
                    if (i47 < i36 && ((LinearLayout.LayoutParams) layoutParams2).weight > 0.0f) {
                        throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    }
                    i40 = i38;
                    if (i40 == 1073741824 || ((LinearLayout.LayoutParams) layoutParams2).width != -1) {
                        z9 = false;
                    } else {
                        z9 = z7;
                        z14 = z9;
                    }
                    int i56 = ((LinearLayout.LayoutParams) layoutParams2).leftMargin + ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                    int measuredWidth = childAt.getMeasuredWidth() + i56;
                    int max3 = Math.max(i52, measuredWidth);
                    combineMeasuredStates = View.combineMeasuredStates(i49, childAt.getMeasuredState());
                    boolean z15 = (z12 && ((LinearLayout.LayoutParams) layoutParams2).width == -1) ? z7 : false;
                    if (((LinearLayout.LayoutParams) layoutParams2).weight > 0.0f) {
                        if (!z9) {
                            i56 = measuredWidth;
                        }
                        i51 = Math.max(i51, i56);
                    } else {
                        int i57 = i51;
                        if (!z9) {
                            i56 = measuredWidth;
                        }
                        i48 = Math.max(i48, i56);
                        i51 = i57;
                    }
                    i47 += 0;
                    i52 = max3;
                    z13 = z8;
                    z12 = z15;
                    i47++;
                    mode = i40;
                    i46 = i36;
                    i49 = combineMeasuredStates;
                    z10 = z7;
                    mode2 = i37;
                    virtualChildCount = i39;
                    i45 = 0;
                    i41 = -2;
                    i42 = Integer.MIN_VALUE;
                    i43 = 8;
                    i44 = 1073741824;
                    f4 = 0.0f;
                }
                i36 = i46;
                i37 = mode2;
                i40 = mode;
                i39 = virtualChildCount;
                combineMeasuredStates = i49;
                z7 = true;
                i47++;
                mode = i40;
                i46 = i36;
                i49 = combineMeasuredStates;
                z10 = z7;
                mode2 = i37;
                virtualChildCount = i39;
                i45 = 0;
                i41 = -2;
                i42 = Integer.MIN_VALUE;
                i43 = 8;
                i44 = 1073741824;
                f4 = 0.0f;
            }
            int i58 = mode2;
            int i59 = mode;
            int i60 = virtualChildCount;
            boolean z16 = z10;
            int i61 = i48;
            int i62 = i49;
            int i63 = i50;
            int i64 = i51;
            int i65 = i52;
            if (this.mTotalLength > 0 && hasDividerBeforeChildAt(i60)) {
                this.mTotalLength += this.mDividerHeight;
            }
            int i66 = i58;
            if (z11 && (i66 == Integer.MIN_VALUE || i66 == 0)) {
                int i67 = 0;
                this.mTotalLength = 0;
                int i68 = 0;
                while (i68 < i60) {
                    View childAt2 = getChildAt(i68);
                    if (childAt2 == null) {
                        this.mTotalLength += i67;
                    } else if (childAt2.getVisibility() == 8) {
                        i68 += 0;
                    } else {
                        LayoutParams layoutParams4 = (LayoutParams) childAt2.getLayoutParams();
                        int i69 = this.mTotalLength;
                        this.mTotalLength = Math.max(i69, i69 + i63 + ((LinearLayout.LayoutParams) layoutParams4).topMargin + ((LinearLayout.LayoutParams) layoutParams4).bottomMargin + 0);
                    }
                    i68++;
                    i67 = 0;
                }
            }
            int paddingBottom = getPaddingBottom() + getPaddingTop() + this.mTotalLength;
            this.mTotalLength = paddingBottom;
            int resolveSizeAndState = View.resolveSizeAndState(Math.max(paddingBottom, getSuggestedMinimumHeight()), i2, 0);
            int i70 = (16777215 & resolveSizeAndState) - this.mTotalLength;
            if (z13 || (i70 != 0 && f5 > 0.0f)) {
                float f7 = this.mWeightSum;
                if (f7 > 0.0f) {
                    f5 = f7;
                }
                this.mTotalLength = 0;
                int i71 = 0;
                while (i71 < i60) {
                    View childAt3 = getChildAt(i71);
                    if (childAt3.getVisibility() == 8) {
                        i31 = i66;
                    } else {
                        LayoutParams layoutParams5 = (LayoutParams) childAt3.getLayoutParams();
                        float f8 = ((LinearLayout.LayoutParams) layoutParams5).weight;
                        if (f8 > 0.0f) {
                            int i72 = (int) ((i70 * f8) / f5);
                            f5 -= f8;
                            int i73 = i70 - i72;
                            int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + ((LinearLayout.LayoutParams) layoutParams5).leftMargin + ((LinearLayout.LayoutParams) layoutParams5).rightMargin, ((LinearLayout.LayoutParams) layoutParams5).width);
                            if (((LinearLayout.LayoutParams) layoutParams5).height == 0) {
                                i34 = 1073741824;
                                if (i66 == 1073741824) {
                                    if (i72 <= 0) {
                                        i72 = 0;
                                    }
                                    childAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(i72, 1073741824));
                                    i62 = View.combineMeasuredStates(i62, childAt3.getMeasuredState() & (-256));
                                    i70 = i73;
                                }
                            } else {
                                i34 = 1073741824;
                            }
                            int measuredHeight2 = childAt3.getMeasuredHeight() + i72;
                            if (measuredHeight2 < 0) {
                                measuredHeight2 = 0;
                            }
                            childAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight2, i34));
                            i62 = View.combineMeasuredStates(i62, childAt3.getMeasuredState() & (-256));
                            i70 = i73;
                        }
                        int i74 = ((LinearLayout.LayoutParams) layoutParams5).leftMargin + ((LinearLayout.LayoutParams) layoutParams5).rightMargin;
                        int measuredWidth2 = childAt3.getMeasuredWidth() + i74;
                        int max4 = Math.max(i65, measuredWidth2);
                        i31 = i66;
                        if (i59 != 1073741824) {
                            i32 = max4;
                            i33 = -1;
                            if (((LinearLayout.LayoutParams) layoutParams5).width == -1) {
                                z6 = z16;
                                if (!z6) {
                                    i74 = measuredWidth2;
                                }
                                int max5 = Math.max(i61, i74);
                                boolean z17 = (z12 || ((LinearLayout.LayoutParams) layoutParams5).width != i33) ? false : z16;
                                int i75 = this.mTotalLength;
                                this.mTotalLength = Math.max(i75, childAt3.getMeasuredHeight() + i75 + ((LinearLayout.LayoutParams) layoutParams5).topMargin + ((LinearLayout.LayoutParams) layoutParams5).bottomMargin + 0);
                                z12 = z17;
                                i65 = i32;
                                i61 = max5;
                            }
                        } else {
                            i32 = max4;
                            i33 = -1;
                        }
                        z6 = false;
                        if (!z6) {
                        }
                        int max52 = Math.max(i61, i74);
                        if (z12) {
                        }
                        int i752 = this.mTotalLength;
                        this.mTotalLength = Math.max(i752, childAt3.getMeasuredHeight() + i752 + ((LinearLayout.LayoutParams) layoutParams5).topMargin + ((LinearLayout.LayoutParams) layoutParams5).bottomMargin + 0);
                        z12 = z17;
                        i65 = i32;
                        i61 = max52;
                    }
                    i71++;
                    i66 = i31;
                }
                this.mTotalLength = getPaddingBottom() + getPaddingTop() + this.mTotalLength;
            } else {
                i61 = Math.max(i61, i64);
                if (z11 && i66 != 1073741824) {
                    for (int i76 = 0; i76 < i60; i76++) {
                        View childAt4 = getChildAt(i76);
                        if (childAt4 != null && childAt4.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) childAt4.getLayoutParams())).weight > 0.0f) {
                            childAt4.measure(View.MeasureSpec.makeMeasureSpec(childAt4.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i63, 1073741824));
                        }
                    }
                }
            }
            int i77 = i65;
            if (z12 || i59 == 1073741824) {
                i61 = i77;
            }
            setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + i61, getSuggestedMinimumWidth()), i, i62), resolveSizeAndState);
            if (z14) {
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
                for (int i78 = 0; i78 < i60; i78++) {
                    View childAt5 = getChildAt(i78);
                    if (childAt5.getVisibility() != 8) {
                        LayoutParams layoutParams6 = (LayoutParams) childAt5.getLayoutParams();
                        if (((LinearLayout.LayoutParams) layoutParams6).width == -1) {
                            int i79 = ((LinearLayout.LayoutParams) layoutParams6).height;
                            ((LinearLayout.LayoutParams) layoutParams6).height = childAt5.getMeasuredHeight();
                            measureChildWithMargins(childAt5, makeMeasureSpec, 0, i2, 0);
                            ((LinearLayout.LayoutParams) layoutParams6).height = i79;
                        }
                    }
                }
                return;
            }
            return;
        }
        this.mTotalLength = 0;
        int virtualChildCount2 = getVirtualChildCount();
        int mode3 = View.MeasureSpec.getMode(i);
        int mode4 = View.MeasureSpec.getMode(i2);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        iArr[3] = -1;
        iArr[2] = -1;
        iArr[1] = -1;
        iArr[0] = -1;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        boolean z18 = this.mBaselineAligned;
        boolean z19 = this.mUseLargestChild;
        boolean z20 = mode3 == 1073741824;
        boolean z21 = true;
        int i80 = 0;
        float f9 = 0.0f;
        int i81 = 0;
        int i82 = 0;
        int i83 = 0;
        int i84 = 0;
        boolean z22 = false;
        boolean z23 = false;
        int i85 = 0;
        while (i82 < virtualChildCount2) {
            View childAt6 = getChildAt(i82);
            if (childAt6 == null) {
                this.mTotalLength += 0;
                i19 = i80;
                i20 = i81;
            } else {
                i19 = i80;
                i20 = i81;
                if (childAt6.getVisibility() == 8) {
                    i82 += 0;
                } else {
                    if (hasDividerBeforeChildAt(i82)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    LayoutParams layoutParams7 = (LayoutParams) childAt6.getLayoutParams();
                    float f10 = ((LinearLayout.LayoutParams) layoutParams7).weight;
                    float f11 = f9 + f10;
                    if (mode3 == 1073741824 && ((LinearLayout.LayoutParams) layoutParams7).width == 0 && f10 > 0.0f) {
                        if (z20) {
                            i30 = i82;
                            this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams7).leftMargin + ((LinearLayout.LayoutParams) layoutParams7).rightMargin + this.mTotalLength;
                        } else {
                            i30 = i82;
                            int i86 = this.mTotalLength;
                            this.mTotalLength = Math.max(i86, ((LinearLayout.LayoutParams) layoutParams7).leftMargin + i86 + ((LinearLayout.LayoutParams) layoutParams7).rightMargin);
                        }
                        if (z18) {
                            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
                            childAt6.measure(makeMeasureSpec2, makeMeasureSpec2);
                            layoutParams = layoutParams7;
                            i22 = i19;
                            i23 = i20;
                            i25 = i30;
                            z2 = z19;
                            z3 = z18;
                        } else {
                            layoutParams = layoutParams7;
                            i22 = i19;
                            i23 = i20;
                            i25 = i30;
                            i26 = 1073741824;
                            z2 = z19;
                            z3 = z18;
                            z4 = true;
                            if (mode4 == i26 && ((LinearLayout.LayoutParams) layoutParams).height == -1) {
                                z5 = true;
                                z23 = true;
                            } else {
                                z5 = false;
                            }
                            i27 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                            int measuredHeight3 = childAt6.getMeasuredHeight() + i27;
                            int combineMeasuredStates2 = View.combineMeasuredStates(i84, childAt6.getMeasuredState());
                            if (z3 || (baseline2 = childAt6.getBaseline()) == -1) {
                                i28 = i27;
                            } else {
                                int i87 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                                if (i87 < 0) {
                                    i87 = this.mGravity;
                                }
                                int i88 = (((i87 & 112) >> 4) & (-2)) >> 1;
                                i28 = i27;
                                iArr[i88] = Math.max(iArr[i88], baseline2);
                                iArr2[i88] = Math.max(iArr2[i88], measuredHeight3 - baseline2);
                            }
                            int max6 = Math.max(i85, measuredHeight3);
                            boolean z24 = !z21 && ((LinearLayout.LayoutParams) layoutParams).height == -1;
                            if (((LinearLayout.LayoutParams) layoutParams).weight <= 0.0f) {
                                if (z5) {
                                    measuredHeight3 = i28;
                                }
                                i29 = Math.max(i23, measuredHeight3);
                                max2 = i22;
                            } else {
                                i29 = i23;
                                if (z5) {
                                    measuredHeight3 = i28;
                                }
                                max2 = Math.max(i22, measuredHeight3);
                            }
                            i85 = max6;
                            i80 = max2;
                            i84 = combineMeasuredStates2;
                            z22 = z4;
                            i82 = i25 + 0;
                            z21 = z24;
                            i81 = i29;
                            f9 = f11;
                            i82++;
                            z19 = z2;
                            z18 = z3;
                        }
                    } else {
                        int i89 = i82;
                        if (((LinearLayout.LayoutParams) layoutParams7).width == 0) {
                            f3 = 0.0f;
                            if (f10 > 0.0f) {
                                ((LinearLayout.LayoutParams) layoutParams7).width = -2;
                                i21 = 0;
                                i22 = i19;
                                i23 = i20;
                                i24 = i21;
                                i25 = i89;
                                z2 = z19;
                                z3 = z18;
                                measureChildWithMargins(childAt6, i, f11 != f3 ? this.mTotalLength : 0, i2, 0);
                                if (i24 == Integer.MIN_VALUE) {
                                    layoutParams = layoutParams7;
                                    ((LinearLayout.LayoutParams) layoutParams).width = i24;
                                } else {
                                    layoutParams = layoutParams7;
                                }
                                int measuredWidth3 = childAt6.getMeasuredWidth();
                                if (z20) {
                                    int i90 = this.mTotalLength;
                                    this.mTotalLength = Math.max(i90, i90 + measuredWidth3 + ((LinearLayout.LayoutParams) layoutParams).leftMargin + ((LinearLayout.LayoutParams) layoutParams).rightMargin + 0);
                                } else {
                                    this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams).leftMargin + measuredWidth3 + ((LinearLayout.LayoutParams) layoutParams).rightMargin + 0 + this.mTotalLength;
                                }
                                if (z2) {
                                    i83 = Math.max(measuredWidth3, i83);
                                }
                            }
                        } else {
                            f3 = 0.0f;
                        }
                        i21 = Integer.MIN_VALUE;
                        i22 = i19;
                        i23 = i20;
                        i24 = i21;
                        i25 = i89;
                        z2 = z19;
                        z3 = z18;
                        measureChildWithMargins(childAt6, i, f11 != f3 ? this.mTotalLength : 0, i2, 0);
                        if (i24 == Integer.MIN_VALUE) {
                        }
                        int measuredWidth32 = childAt6.getMeasuredWidth();
                        if (z20) {
                        }
                        if (z2) {
                        }
                    }
                    z4 = z22;
                    i26 = 1073741824;
                    if (mode4 == i26) {
                    }
                    z5 = false;
                    i27 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                    int measuredHeight32 = childAt6.getMeasuredHeight() + i27;
                    int combineMeasuredStates22 = View.combineMeasuredStates(i84, childAt6.getMeasuredState());
                    if (z3) {
                    }
                    i28 = i27;
                    int max62 = Math.max(i85, measuredHeight32);
                    if (z21) {
                    }
                    if (((LinearLayout.LayoutParams) layoutParams).weight <= 0.0f) {
                    }
                    i85 = max62;
                    i80 = max2;
                    i84 = combineMeasuredStates22;
                    z22 = z4;
                    i82 = i25 + 0;
                    z21 = z24;
                    i81 = i29;
                    f9 = f11;
                    i82++;
                    z19 = z2;
                    z18 = z3;
                }
            }
            z3 = z18;
            i80 = i19;
            i81 = i20;
            z2 = z19;
            i82++;
            z19 = z2;
            z18 = z3;
        }
        boolean z25 = z19;
        boolean z26 = z18;
        int i91 = i80;
        int i92 = i85;
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(virtualChildCount2)) {
            this.mTotalLength += this.mDividerWidth;
        }
        int i93 = iArr[1];
        int i94 = i84;
        if (i93 == -1 && iArr[0] == -1 && iArr[2] == -1) {
            c = 3;
            if (iArr[3] == -1) {
                max = i92;
                i3 = mode4;
                if (z25 && (mode3 == Integer.MIN_VALUE || mode3 == 0)) {
                    int i95 = 0;
                    this.mTotalLength = 0;
                    i18 = 0;
                    while (i18 < virtualChildCount2) {
                        View childAt7 = getChildAt(i18);
                        if (childAt7 == null) {
                            this.mTotalLength += i95;
                        } else if (childAt7.getVisibility() == 8) {
                            i18 += 0;
                        } else {
                            LayoutParams layoutParams8 = (LayoutParams) childAt7.getLayoutParams();
                            if (z20) {
                                this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams8).leftMargin + i83 + ((LinearLayout.LayoutParams) layoutParams8).rightMargin + 0 + this.mTotalLength;
                            } else {
                                int i96 = this.mTotalLength;
                                this.mTotalLength = Math.max(i96, i96 + i83 + ((LinearLayout.LayoutParams) layoutParams8).leftMargin + ((LinearLayout.LayoutParams) layoutParams8).rightMargin + 0);
                            }
                        }
                        i18++;
                        i95 = 0;
                    }
                }
                int paddingRight = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
                this.mTotalLength = paddingRight;
                int resolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingRight, getSuggestedMinimumWidth()), i, 0);
                int i97 = (16777215 & resolveSizeAndState2) - this.mTotalLength;
                if (!z22 || (i97 != 0 && f9 > 0.0f)) {
                    f = this.mWeightSum;
                    if (f > 0.0f) {
                        f9 = f;
                    }
                    iArr[3] = -1;
                    iArr[2] = -1;
                    iArr[1] = -1;
                    iArr[0] = -1;
                    iArr2[3] = -1;
                    iArr2[2] = -1;
                    iArr2[1] = -1;
                    iArr2[0] = -1;
                    this.mTotalLength = 0;
                    i4 = i94;
                    int i98 = -1;
                    i5 = 0;
                    while (i5 < virtualChildCount2) {
                        View childAt8 = getChildAt(i5);
                        if (childAt8 == null || childAt8.getVisibility() == 8) {
                            i11 = i97;
                            i12 = mode3;
                            i13 = i3;
                        } else {
                            LayoutParams layoutParams9 = (LayoutParams) childAt8.getLayoutParams();
                            float f12 = ((LinearLayout.LayoutParams) layoutParams9).weight;
                            if (f12 > 0.0f) {
                                int i99 = (int) ((i97 * f12) / f9);
                                float f13 = f9 - f12;
                                int i100 = i97 - i99;
                                int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i2, getPaddingBottom() + getPaddingTop() + ((LinearLayout.LayoutParams) layoutParams9).topMargin + ((LinearLayout.LayoutParams) layoutParams9).bottomMargin, ((LinearLayout.LayoutParams) layoutParams9).height);
                                if (((LinearLayout.LayoutParams) layoutParams9).width == 0) {
                                    i17 = 1073741824;
                                    if (mode3 == 1073741824) {
                                        if (i99 <= 0) {
                                            i99 = 0;
                                        }
                                        childAt8.measure(View.MeasureSpec.makeMeasureSpec(i99, 1073741824), childMeasureSpec2);
                                        i4 = View.combineMeasuredStates(i4, childAt8.getMeasuredState() & (-16777216));
                                        f9 = f13;
                                        i14 = i100;
                                    }
                                } else {
                                    i17 = 1073741824;
                                }
                                int measuredWidth4 = childAt8.getMeasuredWidth() + i99;
                                if (measuredWidth4 < 0) {
                                    measuredWidth4 = 0;
                                }
                                childAt8.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth4, i17), childMeasureSpec2);
                                i4 = View.combineMeasuredStates(i4, childAt8.getMeasuredState() & (-16777216));
                                f9 = f13;
                                i14 = i100;
                            } else {
                                i14 = i97;
                            }
                            if (z20) {
                                f2 = f9;
                                this.mTotalLength = childAt8.getMeasuredWidth() + ((LinearLayout.LayoutParams) layoutParams9).leftMargin + ((LinearLayout.LayoutParams) layoutParams9).rightMargin + 0 + this.mTotalLength;
                                i15 = i14;
                            } else {
                                f2 = f9;
                                int i101 = this.mTotalLength;
                                i15 = i14;
                                this.mTotalLength = Math.max(i101, childAt8.getMeasuredWidth() + i101 + ((LinearLayout.LayoutParams) layoutParams9).leftMargin + ((LinearLayout.LayoutParams) layoutParams9).rightMargin + 0);
                            }
                            i13 = i3;
                            boolean z27 = i13 != 1073741824 && ((LinearLayout.LayoutParams) layoutParams9).height == -1;
                            i12 = mode3;
                            int i102 = ((LinearLayout.LayoutParams) layoutParams9).topMargin + ((LinearLayout.LayoutParams) layoutParams9).bottomMargin;
                            int measuredHeight4 = childAt8.getMeasuredHeight() + i102;
                            i98 = Math.max(i98, measuredHeight4);
                            if (!z27) {
                                i102 = measuredHeight4;
                            }
                            int max7 = Math.max(i91, i102);
                            if (z21) {
                                i16 = -1;
                                if (((LinearLayout.LayoutParams) layoutParams9).height == -1) {
                                    z = true;
                                    if (!z26 && (baseline = childAt8.getBaseline()) != i16) {
                                        int i103 = ((LinearLayout.LayoutParams) layoutParams9).gravity;
                                        if (i103 < 0) {
                                            i103 = this.mGravity;
                                        }
                                        int i104 = (((i103 & 112) >> 4) & (-2)) >> 1;
                                        iArr[i104] = Math.max(iArr[i104], baseline);
                                        iArr2[i104] = Math.max(iArr2[i104], measuredHeight4 - baseline);
                                    }
                                    z21 = z;
                                    i11 = i15;
                                    i91 = max7;
                                    f9 = f2;
                                }
                            } else {
                                i16 = -1;
                            }
                            z = false;
                            if (!z26) {
                            }
                            z21 = z;
                            i11 = i15;
                            i91 = max7;
                            f9 = f2;
                        }
                        i5++;
                        i97 = i11;
                        i3 = i13;
                        mode3 = i12;
                    }
                    i6 = i2;
                    i7 = i3;
                    this.mTotalLength = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
                    i8 = iArr[1];
                    if (i8 != -1 && iArr[0] == -1 && iArr[2] == -1) {
                        c2 = 3;
                        if (iArr[3] == -1) {
                            max = i98;
                            i9 = 0;
                            i10 = i91;
                        }
                    } else {
                        c2 = 3;
                    }
                    i9 = 0;
                    max = Math.max(i98, Math.max(iArr2[c2], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c2], Math.max(iArr[0], Math.max(i8, iArr[2]))));
                    i10 = i91;
                } else {
                    i10 = Math.max(i91, i81);
                    if (z25 && mode3 != 1073741824) {
                        for (int i105 = 0; i105 < virtualChildCount2; i105++) {
                            View childAt9 = getChildAt(i105);
                            if (childAt9 != null && childAt9.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) childAt9.getLayoutParams())).weight > 0.0f) {
                                childAt9.measure(View.MeasureSpec.makeMeasureSpec(i83, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt9.getMeasuredHeight(), 1073741824));
                            }
                        }
                    }
                    i6 = i2;
                    i4 = i94;
                    i7 = i3;
                    i9 = 0;
                }
                if (!z21 || i7 == 1073741824) {
                    i10 = max;
                }
                setMeasuredDimension(resolveSizeAndState2 | ((-16777216) & i4), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + i10, getSuggestedMinimumHeight()), i6, i4 << 16));
                if (z23) {
                    return;
                }
                int makeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
                while (i9 < virtualChildCount2) {
                    View childAt10 = getChildAt(i9);
                    if (childAt10.getVisibility() != 8) {
                        LayoutParams layoutParams10 = (LayoutParams) childAt10.getLayoutParams();
                        if (((LinearLayout.LayoutParams) layoutParams10).height == -1) {
                            int i106 = ((LinearLayout.LayoutParams) layoutParams10).width;
                            ((LinearLayout.LayoutParams) layoutParams10).width = childAt10.getMeasuredWidth();
                            measureChildWithMargins(childAt10, i, 0, makeMeasureSpec3, 0);
                            ((LinearLayout.LayoutParams) layoutParams10).width = i106;
                        }
                    }
                    i9++;
                }
                return;
            }
        } else {
            c = 3;
        }
        i3 = mode4;
        max = Math.max(i92, Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c], Math.max(iArr[0], Math.max(i93, iArr[2]))));
        if (z25) {
            int i952 = 0;
            this.mTotalLength = 0;
            i18 = 0;
            while (i18 < virtualChildCount2) {
            }
        }
        int paddingRight2 = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
        this.mTotalLength = paddingRight2;
        int resolveSizeAndState22 = View.resolveSizeAndState(Math.max(paddingRight2, getSuggestedMinimumWidth()), i, 0);
        int i972 = (16777215 & resolveSizeAndState22) - this.mTotalLength;
        if (z22) {
        }
        f = this.mWeightSum;
        if (f > 0.0f) {
        }
        iArr[3] = -1;
        iArr[2] = -1;
        iArr[1] = -1;
        iArr[0] = -1;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        this.mTotalLength = 0;
        i4 = i94;
        int i982 = -1;
        i5 = 0;
        while (i5 < virtualChildCount2) {
        }
        i6 = i2;
        i7 = i3;
        this.mTotalLength = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
        i8 = iArr[1];
        if (i8 != -1) {
        }
        c2 = 3;
        i9 = 0;
        max = Math.max(i982, Math.max(iArr2[c2], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c2], Math.max(iArr[0], Math.max(i8, iArr[2]))));
        i10 = i91;
        if (!z21) {
        }
        i10 = max;
        setMeasuredDimension(resolveSizeAndState22 | ((-16777216) & i4), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + i10, getSuggestedMinimumHeight()), i6, i4 << 16));
        if (z23) {
        }
    }

    public void setBaselineAligned(boolean z) {
        this.mBaselineAligned = z;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i >= 0 && i < getChildCount()) {
            this.mBaselineAlignedChildIndex = i;
            return;
        }
        throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.mDivider) {
            return;
        }
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

    public void setDividerPadding(int i) {
        this.mDividerPadding = i;
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= 8388611;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.mGravity = i;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i) {
        int i2 = i & 8388615;
        int i3 = this.mGravity;
        if ((8388615 & i3) != i2) {
            this.mGravity = i2 | ((-8388616) & i3);
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.mUseLargestChild = z;
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            requestLayout();
        }
    }

    public void setShowDividers(int i) {
        if (i != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = i;
    }

    public void setVerticalGravity(int i) {
        int i2 = i & 112;
        int i3 = this.mGravity;
        if ((i3 & 112) != i2) {
            this.mGravity = i2 | (i3 & (-113));
            requestLayout();
        }
    }

    public void setWeightSum(float f) {
        this.mWeightSum = Math.max(0.0f, f);
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
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
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes.getWrappedTypeArray(), i, 0);
        int i2 = obtainStyledAttributes.getInt(1, -1);
        if (i2 >= 0) {
            setOrientation(i2);
        }
        int i3 = obtainStyledAttributes.getInt(0, -1);
        if (i3 >= 0) {
            setGravity(i3);
        }
        boolean z = obtainStyledAttributes.getBoolean(2, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.mWeightSum = obtainStyledAttributes.getFloat();
        this.mBaselineAlignedChildIndex = obtainStyledAttributes.getInt(3, -1);
        this.mUseLargestChild = obtainStyledAttributes.getBoolean(7, false);
        setDividerDrawable(obtainStyledAttributes.getDrawable(5));
        this.mShowDividers = obtainStyledAttributes.getInt(8, 0);
        this.mDividerPadding = obtainStyledAttributes.getDimensionPixelSize(6, 0);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }
}
