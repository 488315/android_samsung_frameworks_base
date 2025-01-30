package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R$styleable;
import androidx.constraintlayout.widget.VirtualLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Flow extends VirtualLayout {
    public androidx.constraintlayout.core.widgets.Flow mFlow;

    public Flow(Context context) {
        super(context);
    }

    @Override // androidx.constraintlayout.widget.VirtualLayout, androidx.constraintlayout.widget.ConstraintHelper
    public final void init(AttributeSet attributeSet) {
        super.init(attributeSet);
        this.mFlow = new androidx.constraintlayout.core.widgets.Flow();
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 0) {
                    this.mFlow.mOrientation = obtainStyledAttributes.getInt(index, 0);
                } else if (index == 1) {
                    androidx.constraintlayout.core.widgets.Flow flow = this.mFlow;
                    int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    flow.mPaddingTop = dimensionPixelSize;
                    flow.mPaddingBottom = dimensionPixelSize;
                    flow.mPaddingStart = dimensionPixelSize;
                    flow.mPaddingEnd = dimensionPixelSize;
                } else if (index == 18) {
                    androidx.constraintlayout.core.widgets.Flow flow2 = this.mFlow;
                    int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    flow2.mPaddingStart = dimensionPixelSize2;
                    flow2.mResolvedPaddingLeft = dimensionPixelSize2;
                    flow2.mResolvedPaddingRight = dimensionPixelSize2;
                } else if (index == 19) {
                    this.mFlow.mPaddingEnd = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == 2) {
                    this.mFlow.mResolvedPaddingLeft = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == 3) {
                    this.mFlow.mPaddingTop = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == 4) {
                    this.mFlow.mResolvedPaddingRight = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == 5) {
                    this.mFlow.mPaddingBottom = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == 54) {
                    this.mFlow.mWrapMode = obtainStyledAttributes.getInt(index, 0);
                } else if (index == 44) {
                    this.mFlow.mHorizontalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == 53) {
                    this.mFlow.mVerticalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == 38) {
                    this.mFlow.mFirstHorizontalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == 46) {
                    this.mFlow.mLastHorizontalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == 40) {
                    this.mFlow.mFirstVerticalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == 48) {
                    this.mFlow.mLastVerticalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == 42) {
                    this.mFlow.mHorizontalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == 37) {
                    this.mFlow.mFirstHorizontalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == 45) {
                    this.mFlow.mLastHorizontalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == 39) {
                    this.mFlow.mFirstVerticalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == 47) {
                    this.mFlow.mLastVerticalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == 51) {
                    this.mFlow.mVerticalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == 41) {
                    this.mFlow.mHorizontalAlign = obtainStyledAttributes.getInt(index, 2);
                } else if (index == 50) {
                    this.mFlow.mVerticalAlign = obtainStyledAttributes.getInt(index, 2);
                } else if (index == 43) {
                    this.mFlow.mHorizontalGap = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == 52) {
                    this.mFlow.mVerticalGap = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == 49) {
                    this.mFlow.mMaxElementsWrap = obtainStyledAttributes.getInt(index, -1);
                }
            }
            obtainStyledAttributes.recycle();
        }
        this.mHelperWidget = this.mFlow;
        validateParams();
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public final void loadParameters(ConstraintSet.Constraint constraint, HelperWidget helperWidget, Constraints.LayoutParams layoutParams, SparseArray sparseArray) {
        super.loadParameters(constraint, helperWidget, layoutParams, sparseArray);
        if (helperWidget instanceof androidx.constraintlayout.core.widgets.Flow) {
            androidx.constraintlayout.core.widgets.Flow flow = (androidx.constraintlayout.core.widgets.Flow) helperWidget;
            int i = layoutParams.orientation;
            if (i != -1) {
                flow.mOrientation = i;
            }
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    public final void onMeasure(int i, int i2) {
        onMeasure(this.mFlow, i, i2);
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public final void resolveRtl(ConstraintWidget constraintWidget, boolean z) {
        androidx.constraintlayout.core.widgets.Flow flow = this.mFlow;
        int i = flow.mPaddingStart;
        if (i > 0 || flow.mPaddingEnd > 0) {
            if (z) {
                flow.mResolvedPaddingLeft = flow.mPaddingEnd;
                flow.mResolvedPaddingRight = i;
            } else {
                flow.mResolvedPaddingLeft = i;
                flow.mResolvedPaddingRight = flow.mPaddingEnd;
            }
        }
    }

    public Flow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.constraintlayout.widget.VirtualLayout
    public final void onMeasure(androidx.constraintlayout.core.widgets.VirtualLayout virtualLayout, int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (virtualLayout == null) {
            setMeasuredDimension(0, 0);
        } else {
            virtualLayout.measure(mode, size, mode2, size2);
            setMeasuredDimension(virtualLayout.mMeasuredWidth, virtualLayout.mMeasuredHeight);
        }
    }

    public Flow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
