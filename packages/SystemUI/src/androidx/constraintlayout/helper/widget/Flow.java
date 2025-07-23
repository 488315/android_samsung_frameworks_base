package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.VirtualLayout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.ConstraintLayout_Layout_android_orientation) {
                    this.mFlow.mOrientation = obtainStyledAttributes.getInt(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_android_padding) {
                    androidx.constraintlayout.core.widgets.Flow flow = this.mFlow;
                    int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    flow.mPaddingTop = dimensionPixelSize;
                    flow.mPaddingBottom = dimensionPixelSize;
                    flow.mPaddingStart = dimensionPixelSize;
                    flow.mPaddingEnd = dimensionPixelSize;
                } else if (index == R.styleable.ConstraintLayout_Layout_android_paddingStart) {
                    androidx.constraintlayout.core.widgets.Flow flow2 = this.mFlow;
                    int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                    flow2.mPaddingStart = dimensionPixelSize2;
                    flow2.mResolvedPaddingLeft = dimensionPixelSize2;
                    flow2.mResolvedPaddingRight = dimensionPixelSize2;
                } else if (index == R.styleable.ConstraintLayout_Layout_android_paddingEnd) {
                    this.mFlow.mPaddingEnd = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_android_paddingLeft) {
                    this.mFlow.mResolvedPaddingLeft = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_android_paddingTop) {
                    this.mFlow.mPaddingTop = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_android_paddingRight) {
                    this.mFlow.mResolvedPaddingRight = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_android_paddingBottom) {
                    this.mFlow.mPaddingBottom = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_wrapMode) {
                    this.mFlow.mWrapMode = obtainStyledAttributes.getInt(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_horizontalStyle) {
                    this.mFlow.mHorizontalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_verticalStyle) {
                    this.mFlow.mVerticalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_firstHorizontalStyle) {
                    this.mFlow.mFirstHorizontalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_lastHorizontalStyle) {
                    this.mFlow.mLastHorizontalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_firstVerticalStyle) {
                    this.mFlow.mFirstVerticalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_lastVerticalStyle) {
                    this.mFlow.mLastVerticalStyle = obtainStyledAttributes.getInt(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_horizontalBias) {
                    this.mFlow.mHorizontalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_firstHorizontalBias) {
                    this.mFlow.mFirstHorizontalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_lastHorizontalBias) {
                    this.mFlow.mLastHorizontalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_firstVerticalBias) {
                    this.mFlow.mFirstVerticalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_lastVerticalBias) {
                    this.mFlow.mLastVerticalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_verticalBias) {
                    this.mFlow.mVerticalBias = obtainStyledAttributes.getFloat(index, 0.5f);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_horizontalAlign) {
                    this.mFlow.mHorizontalAlign = obtainStyledAttributes.getInt(index, 2);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_verticalAlign) {
                    this.mFlow.mVerticalAlign = obtainStyledAttributes.getInt(index, 2);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_horizontalGap) {
                    this.mFlow.mHorizontalGap = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_verticalGap) {
                    this.mFlow.mVerticalGap = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.ConstraintLayout_Layout_flow_maxElementsWrap) {
                    this.mFlow.mMaxElementsWrap = obtainStyledAttributes.getInt(index, -1);
                }
            }
            obtainStyledAttributes.recycle();
        }
        this.mHelperWidget = this.mFlow;
        validateParams();
    }

    @Override // androidx.constraintlayout.widget.ConstraintHelper
    public final void loadParameters(ConstraintSet.Constraint constraint, HelperWidget helperWidget, ConstraintLayout.LayoutParams layoutParams, SparseArray sparseArray) {
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
