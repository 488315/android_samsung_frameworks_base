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
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class LinearLayoutCompat extends ViewGroup {
    public boolean mBaselineAligned;
    public final int mBaselineAlignedChildIndex;
    public int mBaselineChildTop;
    public Drawable mDivider;
    public int mDividerHeight;
    public final int mDividerPadding;
    public int mDividerWidth;
    public int mGravity;
    public int[] mMaxAscent;
    public int[] mMaxDescent;
    public int mOrientation;
    public final int mShowDividers;
    public int mTotalLength;
    public boolean mUseLargestChild;
    public final float mWeightSum;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LayoutParams extends LinearLayout.LayoutParams {
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

    final void drawHorizontalDivider(int i, Canvas canvas) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i);
        this.mDivider.draw(canvas);
    }

    final void drawVerticalDivider(int i, Canvas canvas) {
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
        int i3 = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (i = this.mGravity & 112) != 48) {
            if (i == 16) {
                i3 = AbsActionBarView$$ExternalSyntheticOutline0.m8m(((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom(), this.mTotalLength, 2, i3);
            } else if (i == 80) {
                i3 = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
            }
        }
        return i3 + ((LinearLayout.LayoutParams) ((LayoutParams) childAt.getLayoutParams())).topMargin + baseline;
    }

    public final boolean hasDividerBeforeChildAt(int i) {
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
                    drawHorizontalDivider((childAt.getTop() - ((LinearLayout.LayoutParams) ((LayoutParams) childAt.getLayoutParams())).topMargin) - this.mDividerHeight, canvas);
                }
                i2++;
            }
            if (hasDividerBeforeChildAt(childCount)) {
                View childAt2 = getChildAt(childCount - 1);
                drawHorizontalDivider(childAt2 == null ? (getHeight() - getPaddingBottom()) - this.mDividerHeight : childAt2.getBottom() + ((LinearLayout.LayoutParams) ((LayoutParams) childAt2.getLayoutParams())).bottomMargin, canvas);
                return;
            }
            return;
        }
        int childCount2 = getChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        while (i2 < childCount2) {
            View childAt3 = getChildAt(i2);
            if (childAt3 != null && childAt3.getVisibility() != 8 && hasDividerBeforeChildAt(i2)) {
                LayoutParams layoutParams = (LayoutParams) childAt3.getLayoutParams();
                drawVerticalDivider(isLayoutRtl ? childAt3.getRight() + ((LinearLayout.LayoutParams) layoutParams).rightMargin : (childAt3.getLeft() - ((LinearLayout.LayoutParams) layoutParams).leftMargin) - this.mDividerWidth, canvas);
            }
            i2++;
        }
        if (hasDividerBeforeChildAt(childCount2)) {
            View childAt4 = getChildAt(childCount2 - 1);
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
            drawVerticalDivider(right, canvas);
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

    /* JADX WARN: Removed duplicated region for block: B:25:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x018e  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        boolean z2;
        int m8m;
        int m8m2;
        int i13;
        int i14;
        int i15 = 8;
        int i16 = 5;
        int i17 = 1;
        if (this.mOrientation == 1) {
            int paddingLeft = getPaddingLeft();
            int i18 = i3 - i;
            int paddingRight = i18 - getPaddingRight();
            int paddingRight2 = (i18 - paddingLeft) - getPaddingRight();
            int childCount = getChildCount();
            int i19 = this.mGravity;
            int i20 = i19 & 112;
            int i21 = 8388615 & i19;
            int paddingTop = i20 != 16 ? i20 != 80 ? getPaddingTop() : ((getPaddingTop() + i4) - i2) - this.mTotalLength : AbsActionBarView$$ExternalSyntheticOutline0.m8m(i4 - i2, this.mTotalLength, 2, getPaddingTop());
            int i22 = 0;
            while (i22 < childCount) {
                View childAt = getChildAt(i22);
                if (childAt == null) {
                    paddingTop += 0;
                } else if (childAt.getVisibility() != i15) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    int i23 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                    if (i23 < 0) {
                        i23 = i21;
                    }
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    int absoluteGravity = Gravity.getAbsoluteGravity(i23, ViewCompat.Api17Impl.getLayoutDirection(this)) & 7;
                    if (absoluteGravity == 1) {
                        m8m2 = AbsActionBarView$$ExternalSyntheticOutline0.m8m(paddingRight2, measuredWidth, 2, paddingLeft) + ((LinearLayout.LayoutParams) layoutParams).leftMargin;
                        i13 = ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                    } else if (absoluteGravity != i16) {
                        i14 = ((LinearLayout.LayoutParams) layoutParams).leftMargin + paddingLeft;
                        if (hasDividerBeforeChildAt(i22)) {
                            paddingTop += this.mDividerHeight;
                        }
                        int i24 = paddingTop + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                        int i25 = i24 + 0;
                        childAt.layout(i14, i25, measuredWidth + i14, measuredHeight + i25);
                        i22 += 0;
                        paddingTop = measuredHeight + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + 0 + i24;
                    } else {
                        m8m2 = paddingRight - measuredWidth;
                        i13 = ((LinearLayout.LayoutParams) layoutParams).rightMargin;
                    }
                    i14 = m8m2 - i13;
                    if (hasDividerBeforeChildAt(i22)) {
                    }
                    int i242 = paddingTop + ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    int i252 = i242 + 0;
                    childAt.layout(i14, i252, measuredWidth + i14, measuredHeight + i252);
                    i22 += 0;
                    paddingTop = measuredHeight + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + 0 + i242;
                }
                i22++;
                i15 = 8;
                i16 = 5;
            }
            return;
        }
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop2 = getPaddingTop();
        int i26 = i4 - i2;
        int paddingBottom = i26 - getPaddingBottom();
        int paddingBottom2 = (i26 - paddingTop2) - getPaddingBottom();
        int childCount2 = getChildCount();
        int i27 = this.mGravity;
        int i28 = 8388615 & i27;
        int i29 = i27 & 112;
        boolean z3 = this.mBaselineAligned;
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        int absoluteGravity2 = Gravity.getAbsoluteGravity(i28, ViewCompat.Api17Impl.getLayoutDirection(this));
        int paddingLeft2 = absoluteGravity2 != 1 ? absoluteGravity2 != 5 ? getPaddingLeft() : ((getPaddingLeft() + i3) - i) - this.mTotalLength : AbsActionBarView$$ExternalSyntheticOutline0.m8m(i3 - i, this.mTotalLength, 2, getPaddingLeft());
        if (isLayoutRtl) {
            i5 = childCount2 - 1;
            i17 = -1;
        } else {
            i5 = 0;
        }
        int i30 = 0;
        while (i30 < childCount2) {
            int i31 = (i17 * i30) + i5;
            View childAt2 = getChildAt(i31);
            if (childAt2 == null) {
                paddingLeft2 += 0;
                i6 = i5;
            } else {
                i6 = i5;
                if (childAt2.getVisibility() != 8) {
                    int measuredWidth2 = childAt2.getMeasuredWidth();
                    int measuredHeight2 = childAt2.getMeasuredHeight();
                    i7 = childCount2;
                    LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                    if (z3) {
                        i8 = i29;
                        i9 = i17;
                        if (((LinearLayout.LayoutParams) layoutParams2).height != -1) {
                            i10 = childAt2.getBaseline();
                            i11 = ((LinearLayout.LayoutParams) layoutParams2).gravity;
                            if (i11 < 0) {
                                i11 = i8;
                            }
                            i12 = i11 & 112;
                            z2 = z3;
                            if (i12 != 16) {
                                m8m = (AbsActionBarView$$ExternalSyntheticOutline0.m8m(paddingBottom2, measuredHeight2, 2, paddingTop2) + ((LinearLayout.LayoutParams) layoutParams2).topMargin) - ((LinearLayout.LayoutParams) layoutParams2).bottomMargin;
                            } else if (i12 == 48) {
                                int i32 = ((LinearLayout.LayoutParams) layoutParams2).topMargin + paddingTop2;
                                m8m = i10 != -1 ? (iArr[1] - i10) + i32 : i32;
                            } else if (i12 != 80) {
                                m8m = paddingTop2;
                            } else {
                                m8m = (paddingBottom - measuredHeight2) - ((LinearLayout.LayoutParams) layoutParams2).bottomMargin;
                                if (i10 != -1) {
                                    m8m -= iArr2[2] - (childAt2.getMeasuredHeight() - i10);
                                }
                            }
                            if (hasDividerBeforeChildAt(i31)) {
                                paddingLeft2 += this.mDividerWidth;
                            }
                            int i33 = paddingLeft2 + ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                            int i34 = i33 + 0;
                            childAt2.layout(i34, m8m, measuredWidth2 + i34, measuredHeight2 + m8m);
                            i30 += 0;
                            paddingLeft2 = measuredWidth2 + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + 0 + i33;
                            i30++;
                            i5 = i6;
                            i17 = i9;
                            childCount2 = i7;
                            i29 = i8;
                            z3 = z2;
                        }
                    } else {
                        i8 = i29;
                        i9 = i17;
                    }
                    i10 = -1;
                    i11 = ((LinearLayout.LayoutParams) layoutParams2).gravity;
                    if (i11 < 0) {
                    }
                    i12 = i11 & 112;
                    z2 = z3;
                    if (i12 != 16) {
                    }
                    if (hasDividerBeforeChildAt(i31)) {
                    }
                    int i332 = paddingLeft2 + ((LinearLayout.LayoutParams) layoutParams2).leftMargin;
                    int i342 = i332 + 0;
                    childAt2.layout(i342, m8m, measuredWidth2 + i342, measuredHeight2 + m8m);
                    i30 += 0;
                    paddingLeft2 = measuredWidth2 + ((LinearLayout.LayoutParams) layoutParams2).rightMargin + 0 + i332;
                    i30++;
                    i5 = i6;
                    i17 = i9;
                    childCount2 = i7;
                    i29 = i8;
                    z3 = z2;
                }
            }
            i7 = childCount2;
            i8 = i29;
            i9 = i17;
            z2 = z3;
            i30++;
            i5 = i6;
            i17 = i9;
            childCount2 = i7;
            i29 = i8;
            z3 = z2;
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
    public void onMeasure(int i, int i2) {
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
        int i41 = this.mOrientation;
        int i42 = -2;
        int i43 = VideoPlayer.MEDIA_ERROR_SYSTEM;
        int i44 = 8;
        int i45 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
        float f4 = 0.0f;
        int i46 = 0;
        boolean z10 = true;
        if (i41 == 1) {
            this.mTotalLength = 0;
            int childCount = getChildCount();
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int i47 = this.mBaselineAlignedChildIndex;
            boolean z11 = this.mUseLargestChild;
            boolean z12 = true;
            int i48 = 0;
            int i49 = 0;
            int i50 = 0;
            int i51 = 0;
            int i52 = 0;
            int i53 = 0;
            boolean z13 = false;
            boolean z14 = false;
            float f5 = 0.0f;
            while (i48 < childCount) {
                View childAt = getChildAt(i48);
                if (childAt == null) {
                    this.mTotalLength += i46;
                } else if (childAt.getVisibility() == i44) {
                    i48 += 0;
                } else {
                    if (hasDividerBeforeChildAt(i48)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                    LayoutParams layoutParams3 = (LayoutParams) childAt.getLayoutParams();
                    float f6 = ((LinearLayout.LayoutParams) layoutParams3).weight;
                    f5 += f6;
                    if (mode2 == i45 && ((LinearLayout.LayoutParams) layoutParams3).height == 0 && f6 > f4) {
                        int i54 = this.mTotalLength;
                        this.mTotalLength = Math.max(i54, ((LinearLayout.LayoutParams) layoutParams3).topMargin + i54 + ((LinearLayout.LayoutParams) layoutParams3).bottomMargin);
                        i36 = i47;
                        i37 = mode2;
                        i38 = mode;
                        i39 = childCount;
                        layoutParams2 = layoutParams3;
                        z8 = true;
                        z7 = true;
                    } else {
                        if (((LinearLayout.LayoutParams) layoutParams3).height != 0 || f6 <= f4) {
                            i35 = i43;
                        } else {
                            ((LinearLayout.LayoutParams) layoutParams3).height = i42;
                            i35 = 0;
                        }
                        int i55 = f5 == f4 ? this.mTotalLength : 0;
                        i36 = i47;
                        i37 = mode2;
                        i38 = mode;
                        i39 = childCount;
                        z7 = true;
                        layoutParams2 = layoutParams3;
                        measureChildWithMargins(childAt, i, 0, i2, i55);
                        if (i35 != i43) {
                            ((LinearLayout.LayoutParams) layoutParams2).height = i35;
                        }
                        int measuredHeight = childAt.getMeasuredHeight();
                        int i56 = this.mTotalLength;
                        this.mTotalLength = Math.max(i56, i56 + measuredHeight + ((LinearLayout.LayoutParams) layoutParams2).topMargin + ((LinearLayout.LayoutParams) layoutParams2).bottomMargin + 0);
                        if (z11) {
                            i51 = Math.max(measuredHeight, i51);
                        }
                        z8 = z13;
                    }
                    if (i36 >= 0 && i36 == i48 + 1) {
                        this.mBaselineChildTop = this.mTotalLength;
                    }
                    if (i48 < i36 && ((LinearLayout.LayoutParams) layoutParams2).weight > 0.0f) {
                        throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    }
                    i40 = i38;
                    if (i40 == 1073741824 || ((LinearLayout.LayoutParams) layoutParams2).width != -1) {
                        z9 = false;
                    } else {
                        z9 = z7;
                        z14 = z9;
                    }
                    int i57 = ((LinearLayout.LayoutParams) layoutParams2).leftMargin + ((LinearLayout.LayoutParams) layoutParams2).rightMargin;
                    int measuredWidth = childAt.getMeasuredWidth() + i57;
                    int max3 = Math.max(i53, measuredWidth);
                    combineMeasuredStates = View.combineMeasuredStates(i50, childAt.getMeasuredState());
                    boolean z15 = (z12 && ((LinearLayout.LayoutParams) layoutParams2).width == -1) ? z7 : false;
                    if (((LinearLayout.LayoutParams) layoutParams2).weight > 0.0f) {
                        if (!z9) {
                            i57 = measuredWidth;
                        }
                        i52 = Math.max(i52, i57);
                    } else {
                        int i58 = i52;
                        if (!z9) {
                            i57 = measuredWidth;
                        }
                        i49 = Math.max(i49, i57);
                        i52 = i58;
                    }
                    i48 += 0;
                    i53 = max3;
                    z13 = z8;
                    z12 = z15;
                    i48++;
                    mode = i40;
                    i47 = i36;
                    i50 = combineMeasuredStates;
                    z10 = z7;
                    mode2 = i37;
                    childCount = i39;
                    i46 = 0;
                    i42 = -2;
                    i43 = VideoPlayer.MEDIA_ERROR_SYSTEM;
                    i44 = 8;
                    i45 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                    f4 = 0.0f;
                }
                i36 = i47;
                i37 = mode2;
                i40 = mode;
                i39 = childCount;
                combineMeasuredStates = i50;
                z7 = true;
                i48++;
                mode = i40;
                i47 = i36;
                i50 = combineMeasuredStates;
                z10 = z7;
                mode2 = i37;
                childCount = i39;
                i46 = 0;
                i42 = -2;
                i43 = VideoPlayer.MEDIA_ERROR_SYSTEM;
                i44 = 8;
                i45 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                f4 = 0.0f;
            }
            int i59 = mode2;
            int i60 = mode;
            int i61 = childCount;
            boolean z16 = z10;
            int i62 = i49;
            int i63 = i50;
            int i64 = i51;
            int i65 = i52;
            int i66 = i53;
            if (this.mTotalLength > 0 && hasDividerBeforeChildAt(i61)) {
                this.mTotalLength += this.mDividerHeight;
            }
            int i67 = i59;
            if (z11 && (i67 == Integer.MIN_VALUE || i67 == 0)) {
                int i68 = 0;
                this.mTotalLength = 0;
                int i69 = 0;
                while (i69 < i61) {
                    View childAt2 = getChildAt(i69);
                    if (childAt2 == null) {
                        this.mTotalLength += i68;
                    } else if (childAt2.getVisibility() == 8) {
                        i69 += 0;
                    } else {
                        LayoutParams layoutParams4 = (LayoutParams) childAt2.getLayoutParams();
                        int i70 = this.mTotalLength;
                        this.mTotalLength = Math.max(i70, i70 + i64 + ((LinearLayout.LayoutParams) layoutParams4).topMargin + ((LinearLayout.LayoutParams) layoutParams4).bottomMargin + 0);
                    }
                    i69++;
                    i68 = 0;
                }
            }
            int paddingBottom = getPaddingBottom() + getPaddingTop() + this.mTotalLength;
            this.mTotalLength = paddingBottom;
            int resolveSizeAndState = View.resolveSizeAndState(Math.max(paddingBottom, getSuggestedMinimumHeight()), i2, 0);
            int i71 = (16777215 & resolveSizeAndState) - this.mTotalLength;
            if (z13 || (i71 != 0 && f5 > 0.0f)) {
                float f7 = this.mWeightSum;
                if (f7 > 0.0f) {
                    f5 = f7;
                }
                this.mTotalLength = 0;
                int i72 = 0;
                while (i72 < i61) {
                    View childAt3 = getChildAt(i72);
                    if (childAt3.getVisibility() == 8) {
                        i31 = i67;
                    } else {
                        LayoutParams layoutParams5 = (LayoutParams) childAt3.getLayoutParams();
                        float f8 = ((LinearLayout.LayoutParams) layoutParams5).weight;
                        if (f8 > 0.0f) {
                            int i73 = (int) ((i71 * f8) / f5);
                            f5 -= f8;
                            int i74 = i71 - i73;
                            int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + ((LinearLayout.LayoutParams) layoutParams5).leftMargin + ((LinearLayout.LayoutParams) layoutParams5).rightMargin, ((LinearLayout.LayoutParams) layoutParams5).width);
                            if (((LinearLayout.LayoutParams) layoutParams5).height == 0) {
                                i34 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                                if (i67 == 1073741824) {
                                    if (i73 <= 0) {
                                        i73 = 0;
                                    }
                                    childAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(i73, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                                    i63 = View.combineMeasuredStates(i63, childAt3.getMeasuredState() & (-256));
                                    i71 = i74;
                                }
                            } else {
                                i34 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                            }
                            int measuredHeight2 = childAt3.getMeasuredHeight() + i73;
                            if (measuredHeight2 < 0) {
                                measuredHeight2 = 0;
                            }
                            childAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight2, i34));
                            i63 = View.combineMeasuredStates(i63, childAt3.getMeasuredState() & (-256));
                            i71 = i74;
                        }
                        int i75 = ((LinearLayout.LayoutParams) layoutParams5).leftMargin + ((LinearLayout.LayoutParams) layoutParams5).rightMargin;
                        int measuredWidth2 = childAt3.getMeasuredWidth() + i75;
                        int max4 = Math.max(i66, measuredWidth2);
                        i31 = i67;
                        if (i60 != 1073741824) {
                            i32 = max4;
                            i33 = -1;
                            if (((LinearLayout.LayoutParams) layoutParams5).width == -1) {
                                z6 = z16;
                                if (!z6) {
                                    i75 = measuredWidth2;
                                }
                                int max5 = Math.max(i62, i75);
                                boolean z17 = (z12 || ((LinearLayout.LayoutParams) layoutParams5).width != i33) ? false : z16;
                                int i76 = this.mTotalLength;
                                this.mTotalLength = Math.max(i76, childAt3.getMeasuredHeight() + i76 + ((LinearLayout.LayoutParams) layoutParams5).topMargin + ((LinearLayout.LayoutParams) layoutParams5).bottomMargin + 0);
                                z12 = z17;
                                i66 = i32;
                                i62 = max5;
                            }
                        } else {
                            i32 = max4;
                            i33 = -1;
                        }
                        z6 = false;
                        if (!z6) {
                        }
                        int max52 = Math.max(i62, i75);
                        if (z12) {
                        }
                        int i762 = this.mTotalLength;
                        this.mTotalLength = Math.max(i762, childAt3.getMeasuredHeight() + i762 + ((LinearLayout.LayoutParams) layoutParams5).topMargin + ((LinearLayout.LayoutParams) layoutParams5).bottomMargin + 0);
                        z12 = z17;
                        i66 = i32;
                        i62 = max52;
                    }
                    i72++;
                    i67 = i31;
                }
                this.mTotalLength = getPaddingBottom() + getPaddingTop() + this.mTotalLength;
            } else {
                i62 = Math.max(i62, i65);
                if (z11 && i67 != 1073741824) {
                    for (int i77 = 0; i77 < i61; i77++) {
                        View childAt4 = getChildAt(i77);
                        if (childAt4 != null && childAt4.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) childAt4.getLayoutParams())).weight > 0.0f) {
                            childAt4.measure(View.MeasureSpec.makeMeasureSpec(childAt4.getMeasuredWidth(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(i64, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                        }
                    }
                }
            }
            int i78 = i66;
            if (z12 || i60 == 1073741824) {
                i62 = i78;
            }
            setMeasuredDimension(View.resolveSizeAndState(Math.max(getPaddingRight() + getPaddingLeft() + i62, getSuggestedMinimumWidth()), i, i63), resolveSizeAndState);
            if (z14) {
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                for (int i79 = 0; i79 < i61; i79++) {
                    View childAt5 = getChildAt(i79);
                    if (childAt5.getVisibility() != 8) {
                        LayoutParams layoutParams6 = (LayoutParams) childAt5.getLayoutParams();
                        if (((LinearLayout.LayoutParams) layoutParams6).width == -1) {
                            int i80 = ((LinearLayout.LayoutParams) layoutParams6).height;
                            ((LinearLayout.LayoutParams) layoutParams6).height = childAt5.getMeasuredHeight();
                            measureChildWithMargins(childAt5, makeMeasureSpec, 0, i2, 0);
                            ((LinearLayout.LayoutParams) layoutParams6).height = i80;
                        }
                    }
                }
                return;
            }
            return;
        }
        this.mTotalLength = 0;
        int childCount2 = getChildCount();
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
        int i81 = 0;
        float f9 = 0.0f;
        int i82 = 0;
        int i83 = 0;
        int i84 = 0;
        int i85 = 0;
        boolean z22 = false;
        boolean z23 = false;
        int i86 = 0;
        while (i83 < childCount2) {
            View childAt6 = getChildAt(i83);
            if (childAt6 == null) {
                this.mTotalLength += 0;
                i19 = i81;
                i20 = i82;
            } else {
                i19 = i81;
                i20 = i82;
                if (childAt6.getVisibility() == 8) {
                    i83 += 0;
                } else {
                    if (hasDividerBeforeChildAt(i83)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    LayoutParams layoutParams7 = (LayoutParams) childAt6.getLayoutParams();
                    float f10 = ((LinearLayout.LayoutParams) layoutParams7).weight;
                    float f11 = f9 + f10;
                    if (mode3 == 1073741824 && ((LinearLayout.LayoutParams) layoutParams7).width == 0 && f10 > 0.0f) {
                        if (z20) {
                            i30 = i83;
                            this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams7).leftMargin + ((LinearLayout.LayoutParams) layoutParams7).rightMargin + this.mTotalLength;
                        } else {
                            i30 = i83;
                            int i87 = this.mTotalLength;
                            this.mTotalLength = Math.max(i87, ((LinearLayout.LayoutParams) layoutParams7).leftMargin + i87 + ((LinearLayout.LayoutParams) layoutParams7).rightMargin);
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
                            i26 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
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
                            int combineMeasuredStates2 = View.combineMeasuredStates(i85, childAt6.getMeasuredState());
                            if (z3 || (baseline2 = childAt6.getBaseline()) == -1) {
                                i28 = i27;
                            } else {
                                int i88 = ((LinearLayout.LayoutParams) layoutParams).gravity;
                                if (i88 < 0) {
                                    i88 = this.mGravity;
                                }
                                int i89 = (((i88 & 112) >> 4) & (-2)) >> 1;
                                i28 = i27;
                                iArr[i89] = Math.max(iArr[i89], baseline2);
                                iArr2[i89] = Math.max(iArr2[i89], measuredHeight3 - baseline2);
                            }
                            int max6 = Math.max(i86, measuredHeight3);
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
                            i86 = max6;
                            i81 = max2;
                            i85 = combineMeasuredStates2;
                            z22 = z4;
                            i83 = i25 + 0;
                            z21 = z24;
                            i82 = i29;
                            f9 = f11;
                            i83++;
                            z19 = z2;
                            z18 = z3;
                        }
                    } else {
                        int i90 = i83;
                        if (((LinearLayout.LayoutParams) layoutParams7).width == 0) {
                            f3 = 0.0f;
                            if (f10 > 0.0f) {
                                ((LinearLayout.LayoutParams) layoutParams7).width = -2;
                                i21 = 0;
                                i22 = i19;
                                i23 = i20;
                                i24 = i21;
                                i25 = i90;
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
                                    int i91 = this.mTotalLength;
                                    this.mTotalLength = Math.max(i91, i91 + measuredWidth3 + ((LinearLayout.LayoutParams) layoutParams).leftMargin + ((LinearLayout.LayoutParams) layoutParams).rightMargin + 0);
                                } else {
                                    this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams).leftMargin + measuredWidth3 + ((LinearLayout.LayoutParams) layoutParams).rightMargin + 0 + this.mTotalLength;
                                }
                                if (z2) {
                                    i84 = Math.max(measuredWidth3, i84);
                                }
                            }
                        } else {
                            f3 = 0.0f;
                        }
                        i21 = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        i22 = i19;
                        i23 = i20;
                        i24 = i21;
                        i25 = i90;
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
                    i26 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                    if (mode4 == i26) {
                    }
                    z5 = false;
                    i27 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                    int measuredHeight32 = childAt6.getMeasuredHeight() + i27;
                    int combineMeasuredStates22 = View.combineMeasuredStates(i85, childAt6.getMeasuredState());
                    if (z3) {
                    }
                    i28 = i27;
                    int max62 = Math.max(i86, measuredHeight32);
                    if (z21) {
                    }
                    if (((LinearLayout.LayoutParams) layoutParams).weight <= 0.0f) {
                    }
                    i86 = max62;
                    i81 = max2;
                    i85 = combineMeasuredStates22;
                    z22 = z4;
                    i83 = i25 + 0;
                    z21 = z24;
                    i82 = i29;
                    f9 = f11;
                    i83++;
                    z19 = z2;
                    z18 = z3;
                }
            }
            z3 = z18;
            i81 = i19;
            i82 = i20;
            z2 = z19;
            i83++;
            z19 = z2;
            z18 = z3;
        }
        boolean z25 = z19;
        boolean z26 = z18;
        int i92 = i81;
        int i93 = i86;
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(childCount2)) {
            this.mTotalLength += this.mDividerWidth;
        }
        int i94 = iArr[1];
        int i95 = i85;
        if (i94 == -1 && iArr[0] == -1 && iArr[2] == -1) {
            c = 3;
            if (iArr[3] == -1) {
                max = i93;
                i3 = mode4;
                if (z25 && (mode3 == Integer.MIN_VALUE || mode3 == 0)) {
                    int i96 = 0;
                    this.mTotalLength = 0;
                    i18 = 0;
                    while (i18 < childCount2) {
                        View childAt7 = getChildAt(i18);
                        if (childAt7 == null) {
                            this.mTotalLength += i96;
                        } else if (childAt7.getVisibility() == 8) {
                            i18 += 0;
                        } else {
                            LayoutParams layoutParams8 = (LayoutParams) childAt7.getLayoutParams();
                            if (z20) {
                                this.mTotalLength = ((LinearLayout.LayoutParams) layoutParams8).leftMargin + i84 + ((LinearLayout.LayoutParams) layoutParams8).rightMargin + 0 + this.mTotalLength;
                            } else {
                                int i97 = this.mTotalLength;
                                this.mTotalLength = Math.max(i97, i97 + i84 + ((LinearLayout.LayoutParams) layoutParams8).leftMargin + ((LinearLayout.LayoutParams) layoutParams8).rightMargin + 0);
                            }
                        }
                        i18++;
                        i96 = 0;
                    }
                }
                int paddingRight = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
                this.mTotalLength = paddingRight;
                int resolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingRight, getSuggestedMinimumWidth()), i, 0);
                int i98 = (16777215 & resolveSizeAndState2) - this.mTotalLength;
                if (!z22 || (i98 != 0 && f9 > 0.0f)) {
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
                    i4 = i95;
                    int i99 = -1;
                    i5 = 0;
                    while (i5 < childCount2) {
                        View childAt8 = getChildAt(i5);
                        if (childAt8 == null || childAt8.getVisibility() == 8) {
                            i11 = i98;
                            i12 = mode3;
                            i13 = i3;
                        } else {
                            LayoutParams layoutParams9 = (LayoutParams) childAt8.getLayoutParams();
                            float f12 = ((LinearLayout.LayoutParams) layoutParams9).weight;
                            if (f12 > 0.0f) {
                                int i100 = (int) ((i98 * f12) / f9);
                                float f13 = f9 - f12;
                                int i101 = i98 - i100;
                                int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i2, getPaddingBottom() + getPaddingTop() + ((LinearLayout.LayoutParams) layoutParams9).topMargin + ((LinearLayout.LayoutParams) layoutParams9).bottomMargin, ((LinearLayout.LayoutParams) layoutParams9).height);
                                if (((LinearLayout.LayoutParams) layoutParams9).width == 0) {
                                    i17 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                                    if (mode3 == 1073741824) {
                                        if (i100 <= 0) {
                                            i100 = 0;
                                        }
                                        childAt8.measure(View.MeasureSpec.makeMeasureSpec(i100, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), childMeasureSpec2);
                                        i4 = View.combineMeasuredStates(i4, childAt8.getMeasuredState() & EmergencyPhoneWidget.BG_COLOR);
                                        f9 = f13;
                                        i14 = i101;
                                    }
                                } else {
                                    i17 = VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
                                }
                                int measuredWidth4 = childAt8.getMeasuredWidth() + i100;
                                if (measuredWidth4 < 0) {
                                    measuredWidth4 = 0;
                                }
                                childAt8.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth4, i17), childMeasureSpec2);
                                i4 = View.combineMeasuredStates(i4, childAt8.getMeasuredState() & EmergencyPhoneWidget.BG_COLOR);
                                f9 = f13;
                                i14 = i101;
                            } else {
                                i14 = i98;
                            }
                            if (z20) {
                                f2 = f9;
                                this.mTotalLength = childAt8.getMeasuredWidth() + ((LinearLayout.LayoutParams) layoutParams9).leftMargin + ((LinearLayout.LayoutParams) layoutParams9).rightMargin + 0 + this.mTotalLength;
                                i15 = i14;
                            } else {
                                f2 = f9;
                                int i102 = this.mTotalLength;
                                i15 = i14;
                                this.mTotalLength = Math.max(i102, childAt8.getMeasuredWidth() + i102 + ((LinearLayout.LayoutParams) layoutParams9).leftMargin + ((LinearLayout.LayoutParams) layoutParams9).rightMargin + 0);
                            }
                            i13 = i3;
                            boolean z27 = i13 != 1073741824 && ((LinearLayout.LayoutParams) layoutParams9).height == -1;
                            i12 = mode3;
                            int i103 = ((LinearLayout.LayoutParams) layoutParams9).topMargin + ((LinearLayout.LayoutParams) layoutParams9).bottomMargin;
                            int measuredHeight4 = childAt8.getMeasuredHeight() + i103;
                            i99 = Math.max(i99, measuredHeight4);
                            if (!z27) {
                                i103 = measuredHeight4;
                            }
                            int max7 = Math.max(i92, i103);
                            if (z21) {
                                i16 = -1;
                                if (((LinearLayout.LayoutParams) layoutParams9).height == -1) {
                                    z = true;
                                    if (!z26 && (baseline = childAt8.getBaseline()) != i16) {
                                        int i104 = ((LinearLayout.LayoutParams) layoutParams9).gravity;
                                        if (i104 < 0) {
                                            i104 = this.mGravity;
                                        }
                                        int i105 = (((i104 & 112) >> 4) & (-2)) >> 1;
                                        iArr[i105] = Math.max(iArr[i105], baseline);
                                        iArr2[i105] = Math.max(iArr2[i105], measuredHeight4 - baseline);
                                    }
                                    z21 = z;
                                    i11 = i15;
                                    i92 = max7;
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
                            i92 = max7;
                            f9 = f2;
                        }
                        i5++;
                        i98 = i11;
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
                            max = i99;
                            i9 = 0;
                            i10 = i92;
                        }
                    } else {
                        c2 = 3;
                    }
                    i9 = 0;
                    max = Math.max(i99, Math.max(iArr2[c2], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c2], Math.max(iArr[0], Math.max(i8, iArr[2]))));
                    i10 = i92;
                } else {
                    i10 = Math.max(i92, i82);
                    if (z25 && mode3 != 1073741824) {
                        for (int i106 = 0; i106 < childCount2; i106++) {
                            View childAt9 = getChildAt(i106);
                            if (childAt9 != null && childAt9.getVisibility() != 8 && ((LinearLayout.LayoutParams) ((LayoutParams) childAt9.getLayoutParams())).weight > 0.0f) {
                                childAt9.measure(View.MeasureSpec.makeMeasureSpec(i84, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(childAt9.getMeasuredHeight(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                            }
                        }
                    }
                    i6 = i2;
                    i4 = i95;
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
                int makeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                while (i9 < childCount2) {
                    View childAt10 = getChildAt(i9);
                    if (childAt10.getVisibility() != 8) {
                        LayoutParams layoutParams10 = (LayoutParams) childAt10.getLayoutParams();
                        if (((LinearLayout.LayoutParams) layoutParams10).height == -1) {
                            int i107 = ((LinearLayout.LayoutParams) layoutParams10).width;
                            ((LinearLayout.LayoutParams) layoutParams10).width = childAt10.getMeasuredWidth();
                            measureChildWithMargins(childAt10, i, 0, makeMeasureSpec3, 0);
                            ((LinearLayout.LayoutParams) layoutParams10).width = i107;
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
        max = Math.max(i93, Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c], Math.max(iArr[0], Math.max(i94, iArr[2]))));
        if (z25) {
            int i962 = 0;
            this.mTotalLength = 0;
            i18 = 0;
            while (i18 < childCount2) {
            }
        }
        int paddingRight2 = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
        this.mTotalLength = paddingRight2;
        int resolveSizeAndState22 = View.resolveSizeAndState(Math.max(paddingRight2, getSuggestedMinimumWidth()), i, 0);
        int i982 = (16777215 & resolveSizeAndState22) - this.mTotalLength;
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
        i4 = i95;
        int i992 = -1;
        i5 = 0;
        while (i5 < childCount2) {
        }
        i6 = i2;
        i7 = i3;
        this.mTotalLength = getPaddingRight() + getPaddingLeft() + this.mTotalLength;
        i8 = iArr[1];
        if (i8 != -1) {
        }
        c2 = 3;
        i9 = 0;
        max = Math.max(i992, Math.max(iArr2[c2], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[c2], Math.max(iArr[0], Math.max(i8, iArr[2]))));
        i10 = i92;
        if (!z21) {
        }
        i10 = max;
        setMeasuredDimension(resolveSizeAndState22 | ((-16777216) & i4), View.resolveSizeAndState(Math.max(getPaddingBottom() + getPaddingTop() + i10, getSuggestedMinimumHeight()), i6, i4 << 16));
        if (z23) {
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
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArray, i, 0);
        int i2 = obtainStyledAttributes.getInt(1, -1);
        if (i2 >= 0 && this.mOrientation != i2) {
            this.mOrientation = i2;
            requestLayout();
        }
        int i3 = obtainStyledAttributes.getInt(0, -1);
        if (i3 >= 0 && this.mGravity != i3) {
            i3 = (8388615 & i3) == 0 ? i3 | 8388611 : i3;
            this.mGravity = (i3 & 112) == 0 ? i3 | 48 : i3;
            requestLayout();
        }
        boolean z = obtainStyledAttributes.getBoolean(2, true);
        if (!z) {
            this.mBaselineAligned = z;
        }
        this.mWeightSum = obtainStyledAttributes.mWrapped.getFloat(4, -1.0f);
        this.mBaselineAlignedChildIndex = obtainStyledAttributes.getInt(3, -1);
        this.mUseLargestChild = obtainStyledAttributes.getBoolean(7, false);
        Drawable drawable = obtainStyledAttributes.getDrawable(5);
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
        this.mShowDividers = obtainStyledAttributes.getInt(8, 0);
        this.mDividerPadding = obtainStyledAttributes.getDimensionPixelSize(6, 0);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }
}
