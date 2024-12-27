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

public class KeyguardSecurityViewFlipper extends ViewFlipper {
    public final Rect mTempRect;

    public final class LayoutParams extends FrameLayout.LayoutParams {
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
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyguardSecurityViewFlipper_Layout, 0, 0);
            this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(1, 0);
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(0, 0);
            obtainStyledAttributes.recycle();
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
        int i3;
        int i4;
        int i5;
        int i6;
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
        int i7 = size;
        int i8 = size2;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() == 0) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int i10 = layoutParams.maxWidth;
                if (i10 > 0 && i10 < i7) {
                    i7 = i10;
                }
                int i11 = layoutParams.maxHeight;
                if (i11 > 0 && i11 < i8) {
                    i8 = i11;
                }
            }
        }
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int max = Math.max(0, i7 - paddingRight);
        int max2 = Math.max(0, i8 - paddingBottom);
        int i12 = mode == 1073741824 ? size : 0;
        int i13 = mode2 == 1073741824 ? size2 : 0;
        for (int i14 = 0; i14 < childCount; i14++) {
            View childAt2 = getChildAt(i14);
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
            int i15 = ((FrameLayout.LayoutParams) layoutParams2).width;
            if (i15 != -2) {
                i3 = i15 != -1 ? Math.min(max, i15) : max;
                i4 = 1073741824;
            } else {
                i3 = max;
                i4 = Integer.MIN_VALUE;
            }
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i3, i4);
            int i16 = ((FrameLayout.LayoutParams) layoutParams2).height;
            if (i16 != -2) {
                i5 = i16 != -1 ? Math.min(max2, i16) : max2;
                i6 = 1073741824;
            } else {
                i5 = max2;
                i6 = Integer.MIN_VALUE;
            }
            childAt2.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(i5, i6));
            i12 = Math.max(i12, Math.min(childAt2.getMeasuredWidth(), size - paddingRight));
            i13 = Math.max(i13, Math.min(childAt2.getMeasuredHeight(), size2 - paddingBottom));
        }
        setMeasuredDimension(i12 + paddingRight, i13 + paddingBottom);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        this.mTempRect.set(0, 0, 0, 0);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                offsetRectIntoDescendantCoords(childAt, this.mTempRect);
                Rect rect = this.mTempRect;
                motionEvent.offsetLocation(rect.left, rect.top);
                onTouchEvent = childAt.dispatchTouchEvent(motionEvent) || onTouchEvent;
                Rect rect2 = this.mTempRect;
                motionEvent.offsetLocation(-rect2.left, -rect2.top);
            }
        }
        return onTouchEvent;
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
