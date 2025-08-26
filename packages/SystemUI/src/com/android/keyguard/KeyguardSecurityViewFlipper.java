package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.widget.FrameLayout;
import android.widget.ViewFlipper;
import com.android.systemui.res.R$styleable;

/* loaded from: classes.dex */
public class KeyguardSecurityViewFlipper extends ViewFlipper {
    public final Rect mTempRect;

    public class LayoutParams extends FrameLayout.LayoutParams {
        public final int maxHeight;
        public final int maxWidth;

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public final void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:maxWidth", this.maxWidth);
            viewHierarchyEncoder.addProperty("layout:maxHeight", this.maxHeight);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((FrameLayout.LayoutParams) layoutParams);
            this.maxWidth = layoutParams.maxWidth;
            this.maxHeight = layoutParams.maxHeight;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyguardSecurityViewFlipper_Layout, 0, 0);
            this.maxWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, 0);
            this.maxHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public KeyguardSecurityViewFlipper(Context context) {
        this(context, null);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public final KeyguardInputView getSecurityView() {
        View childAt = getChildAt(getDisplayedChild());
        if (childAt instanceof KeyguardInputView) {
            return (KeyguardInputView) childAt;
        }
        return null;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int iMin;
        int i3;
        int iMin2;
        int i4;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (mode != Integer.MIN_VALUE) {
            Log.w("KeyguardSecurityViewFlipper", "onMeasure: widthSpec " + View.MeasureSpec.toString(i) + " should be AT_MOST");
        }
        if (mode2 != Integer.MIN_VALUE) {
            Log.w("KeyguardSecurityViewFlipper", "onMeasure: heightSpec " + View.MeasureSpec.toString(i2) + " should be AT_MOST");
        }
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int childCount = getChildCount();
        int i5 = size;
        int i6 = size2;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() == 0) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int i8 = layoutParams.maxWidth;
                if (i8 > 0 && i8 < i5) {
                    i5 = i8;
                }
                int i9 = layoutParams.maxHeight;
                if (i9 > 0 && i9 < i6) {
                    i6 = i9;
                }
            }
        }
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int iMax = Math.max(0, i5 - paddingRight);
        int iMax2 = Math.max(0, i6 - paddingBottom);
        int iMax3 = mode == 1073741824 ? size : 0;
        int iMax4 = mode2 == 1073741824 ? size2 : 0;
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt2 = getChildAt(i10);
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
            int i11 = ((FrameLayout.LayoutParams) layoutParams2).width;
            if (i11 != -2) {
                iMin = i11 != -1 ? Math.min(iMax, i11) : iMax;
                i3 = 1073741824;
            } else {
                iMin = iMax;
                i3 = Integer.MIN_VALUE;
            }
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMin, i3);
            int i12 = ((FrameLayout.LayoutParams) layoutParams2).height;
            if (i12 != -2) {
                iMin2 = i12 != -1 ? Math.min(iMax2, i12) : iMax2;
                i4 = 1073741824;
            } else {
                iMin2 = iMax2;
                i4 = Integer.MIN_VALUE;
            }
            childAt2.measure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(iMin2, i4));
            iMax3 = Math.max(iMax3, Math.min(childAt2.getMeasuredWidth(), size - paddingRight));
            iMax4 = Math.max(iMax4, Math.min(childAt2.getMeasuredHeight(), size2 - paddingBottom));
        }
        setMeasuredDimension(iMax3 + paddingRight, iMax4 + paddingBottom);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
        this.mTempRect.set(0, 0, 0, 0);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                offsetRectIntoDescendantCoords(childAt, this.mTempRect);
                Rect rect = this.mTempRect;
                motionEvent.offsetLocation(rect.left, rect.top);
                zOnTouchEvent = childAt.dispatchTouchEvent(motionEvent) || zOnTouchEvent;
                Rect rect2 = this.mTempRect;
                motionEvent.offsetLocation(-rect2.left, -rect2.top);
            }
        }
        return zOnTouchEvent;
    }

    public KeyguardSecurityViewFlipper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempRect = new Rect();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }
}
