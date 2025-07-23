package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.R$styleable;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ButtonBarLayout extends LinearLayout {
    public final boolean mAllowStacking;
    public final int mButtonBarBottomMargin;
    public int mLastWidthSize;
    public boolean mStacked;

    public ButtonBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastWidthSize = -1;
        int[] iArr = R$styleable.ButtonBarLayout;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, 0, 0);
        boolean z = obtainStyledAttributes.getBoolean(0, true);
        this.mAllowStacking = z;
        obtainStyledAttributes.recycle();
        if (getOrientation() == 1) {
            setStacked(z);
        }
        this.mButtonBarBottomMargin = (int) getResources().getDimension(R.dimen.sesl_dialog_button_bar_margin_bottom);
    }

    public final int getNextVisibleChildIndex(int i) {
        int childCount = getChildCount();
        while (i < childCount) {
            if (getChildAt(i).getVisibility() == 0 && (getChildAt(i) instanceof Button)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        boolean z;
        int i4;
        int size = View.MeasureSpec.getSize(i);
        int i5 = 0;
        if (this.mAllowStacking) {
            if (size > this.mLastWidthSize && this.mStacked) {
                setStacked(false);
                int childCount = getChildCount();
                for (int nextVisibleChildIndex = getNextVisibleChildIndex(0); nextVisibleChildIndex < childCount; nextVisibleChildIndex++) {
                    if (!(getChildAt(nextVisibleChildIndex) instanceof Button) && (i4 = nextVisibleChildIndex + 1) < childCount && (getChildAt(i4) instanceof Button) && getChildAt(i4).getVisibility() == 0) {
                        getChildAt(nextVisibleChildIndex).setVisibility(0);
                    }
                }
            }
            this.mLastWidthSize = size;
        }
        if (this.mStacked || View.MeasureSpec.getMode(i) != 1073741824) {
            i3 = i;
            z = false;
        } else {
            i3 = View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
            z = true;
        }
        super.onMeasure(i3, i2);
        if (this.mAllowStacking && !this.mStacked) {
            boolean z2 = (getMeasuredWidthAndState() & (-16777216)) == 16777216;
            if (z2) {
                setStacked(true);
                int childCount2 = getChildCount();
                for (int i6 = 0; i6 < childCount2; i6++) {
                    if (!(getChildAt(i6) instanceof Button)) {
                        getChildAt(i6).setVisibility(8);
                    }
                }
                setGravity(17);
                z = true;
            }
            if (z2) {
                int childCount3 = getChildCount();
                for (int i7 = 0; i7 < childCount3; i7++) {
                    View childAt = getChildAt(i7);
                    if (childAt instanceof Button) {
                        ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                            layoutParams.width = -1;
                            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                            if (i7 < childCount3 - 1) {
                                marginLayoutParams.setMargins(0, 0, 0, this.mButtonBarBottomMargin);
                            }
                            childAt.setLayoutParams(marginLayoutParams);
                        }
                    }
                }
            } else {
                int childCount4 = getChildCount();
                for (int i8 = 0; i8 < childCount4; i8++) {
                    View childAt2 = getChildAt(i8);
                    if (childAt2 instanceof Button) {
                        ViewGroup.LayoutParams layoutParams2 = childAt2.getLayoutParams();
                        if (layoutParams2 instanceof ViewGroup.MarginLayoutParams) {
                            layoutParams2.width = -2;
                            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams2;
                            if (i8 < childCount4 - 1) {
                                marginLayoutParams2.setMargins(0, 0, 0, 0);
                            }
                            childAt2.setLayoutParams(marginLayoutParams2);
                        }
                    }
                }
            }
        }
        if (z) {
            super.onMeasure(i, i2);
        }
        int nextVisibleChildIndex2 = getNextVisibleChildIndex(0);
        if (nextVisibleChildIndex2 >= 0) {
            View childAt3 = getChildAt(nextVisibleChildIndex2);
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) childAt3.getLayoutParams();
            i5 = childAt3.getMeasuredHeight() + getPaddingTop() + layoutParams3.topMargin + layoutParams3.bottomMargin;
            if (this.mStacked) {
                int nextVisibleChildIndex3 = getNextVisibleChildIndex(nextVisibleChildIndex2 + 1);
                if (nextVisibleChildIndex3 >= 0) {
                    i5 = getChildAt(nextVisibleChildIndex3).getPaddingTop() + ((int) (getResources().getDisplayMetrics().density * 16.0f)) + i5;
                }
            } else {
                i5 += getPaddingBottom();
            }
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (getMinimumHeight() != i5) {
            setMinimumHeight(i5);
            if (i2 == 0 || z) {
                super.onMeasure(i, i2);
            }
        }
    }

    public final void setStacked(boolean z) {
        if (this.mStacked != z) {
            if (!z || this.mAllowStacking) {
                this.mStacked = z;
                setOrientation(z ? 1 : 0);
                setGravity(z ? 8388613 : 80);
            }
        }
    }
}
