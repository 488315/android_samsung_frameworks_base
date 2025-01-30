package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.ConstraintLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class VirtualLayout extends HelperWidget {
    public int mPaddingTop = 0;
    public int mPaddingBottom = 0;
    public int mPaddingStart = 0;
    public int mPaddingEnd = 0;
    public int mResolvedPaddingLeft = 0;
    public int mResolvedPaddingRight = 0;
    public boolean mNeedsCallFromSolver = false;
    public int mMeasuredWidth = 0;
    public int mMeasuredHeight = 0;
    public final BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
    public BasicMeasure.Measurer mMeasurer = null;

    public void measure(int i, int i2, int i3, int i4) {
    }

    @Override // androidx.constraintlayout.core.widgets.HelperWidget, androidx.constraintlayout.core.widgets.Helper
    public final void updateConstraints() {
        for (int i = 0; i < this.mWidgetsCount; i++) {
            ConstraintWidget constraintWidget = this.mWidgets[i];
            if (constraintWidget != null) {
                constraintWidget.mInVirtualLayout = true;
            }
        }
    }

    public final void measure(ConstraintWidget constraintWidget, ConstraintWidget.DimensionBehaviour dimensionBehaviour, int i, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, int i2) {
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
        ((ConstraintLayout.Measurer) measurer).measure(constraintWidget, measure);
        constraintWidget.setWidth(measure.measuredWidth);
        constraintWidget.setHeight(measure.measuredHeight);
        constraintWidget.hasBaseline = measure.measuredHasBaseline;
        int i3 = measure.measuredBaseline;
        constraintWidget.mBaselineDistance = i3;
        constraintWidget.hasBaseline = i3 > 0;
    }
}
