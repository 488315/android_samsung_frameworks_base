package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;

/* loaded from: classes.dex */
public class VirtualLayout extends HelperWidget {
    private int mPaddingTop = 0;
    private int mPaddingBottom = 0;
    private int mPaddingStart = 0;
    private int mPaddingEnd = 0;
    private int mResolvedPaddingLeft = 0;
    private int mResolvedPaddingRight = 0;
    private boolean mNeedsCallFromSolver = false;
    private int mMeasuredWidth = 0;
    private int mMeasuredHeight = 0;
    protected BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
    BasicMeasure.Measurer mMeasurer = null;

    public final void applyRtl(boolean z) {
        int i = this.mPaddingStart;
        if (i > 0 || this.mPaddingEnd > 0) {
            if (z) {
                this.mResolvedPaddingLeft = this.mPaddingEnd;
                this.mResolvedPaddingRight = i;
            } else {
                this.mResolvedPaddingLeft = i;
                this.mResolvedPaddingRight = this.mPaddingEnd;
            }
        }
    }

    public final int getMeasuredHeight() {
        return this.mMeasuredHeight;
    }

    public final int getMeasuredWidth() {
        return this.mMeasuredWidth;
    }

    public final int getPaddingBottom() {
        return this.mPaddingBottom;
    }

    public final int getPaddingLeft() {
        return this.mResolvedPaddingLeft;
    }

    public final int getPaddingRight() {
        return this.mResolvedPaddingRight;
    }

    public final int getPaddingTop() {
        return this.mPaddingTop;
    }

    public void measure(int i, int i2, int i3, int i4) {
    }

    public final boolean needSolverPass() {
        return this.mNeedsCallFromSolver;
    }

    protected final void needsCallbackFromSolver(boolean z) {
        this.mNeedsCallFromSolver = z;
    }

    public final void setMeasure(int i, int i2) {
        this.mMeasuredWidth = i;
        this.mMeasuredHeight = i2;
    }

    public final void setPadding(int i) {
        this.mPaddingTop = i;
        this.mPaddingBottom = i;
        this.mPaddingStart = i;
        this.mPaddingEnd = i;
    }

    public final void setPaddingBottom(int i) {
        this.mPaddingBottom = i;
    }

    public final void setPaddingEnd(int i) {
        this.mPaddingEnd = i;
    }

    public final void setPaddingLeft(int i) {
        this.mResolvedPaddingLeft = i;
    }

    public final void setPaddingRight(int i) {
        this.mResolvedPaddingRight = i;
    }

    public final void setPaddingStart(int i) {
        this.mPaddingStart = i;
        this.mResolvedPaddingLeft = i;
        this.mResolvedPaddingRight = i;
    }

    public final void setPaddingTop(int i) {
        this.mPaddingTop = i;
    }

    @Override // androidx.constraintlayout.core.widgets.HelperWidget, androidx.constraintlayout.core.widgets.Helper
    public final void updateConstraints() {
        for (int i = 0; i < this.mWidgetsCount; i++) {
            ConstraintWidget constraintWidget = this.mWidgets[i];
            if (constraintWidget != null) {
                constraintWidget.setInVirtualLayout();
            }
        }
    }

    protected final void measure(ConstraintWidget constraintWidget, ConstraintWidget.DimensionBehaviour dimensionBehaviour, int i, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, int i2) {
        BasicMeasure.Measurer measurer;
        ConstraintWidget constraintWidget2;
        while (true) {
            measurer = this.mMeasurer;
            if (measurer != null || (constraintWidget2 = this.mParent) == null) {
                break;
            } else {
                this.mMeasurer = ((ConstraintWidgetContainer) constraintWidget2).mMeasurer;
            }
        }
        BasicMeasure.Measure measure = this.mMeasure;
        measure.horizontalBehavior = dimensionBehaviour;
        measure.verticalBehavior = dimensionBehaviour2;
        measure.horizontalDimension = i;
        measure.verticalDimension = i2;
        measurer.measure(constraintWidget, measure);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        constraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
    }
}
