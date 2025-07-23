package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Placeholder extends VirtualLayout {
    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public final void addToSolver(LinearSystem linearSystem, boolean z) {
        super.addToSolver(linearSystem, z);
        if (this.mWidgetsCount > 0) {
            ConstraintWidget constraintWidget = this.mWidgets[0];
            constraintWidget.resetAnchors();
            constraintWidget.mVerticalBiasPercent = 0.5f;
            constraintWidget.mHorizontalBiasPercent = 0.5f;
            ConstraintAnchor.Type type = ConstraintAnchor.Type.LEFT;
            constraintWidget.connect(type, this, type, 0);
            ConstraintAnchor.Type type2 = ConstraintAnchor.Type.RIGHT;
            constraintWidget.connect(type2, this, type2, 0);
            ConstraintAnchor.Type type3 = ConstraintAnchor.Type.TOP;
            constraintWidget.connect(type3, this, type3, 0);
            ConstraintAnchor.Type type4 = ConstraintAnchor.Type.BOTTOM;
            constraintWidget.connect(type4, this, type4, 0);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.VirtualLayout
    public final void measure(int i, int i2, int i3, int i4) {
        int i5 = this.mResolvedPaddingLeft + this.mResolvedPaddingRight;
        int i6 = this.mPaddingTop + this.mPaddingBottom;
        if (this.mWidgetsCount > 0) {
            i5 += this.mWidgets[0].getWidth();
            i6 += this.mWidgets[0].getHeight();
        }
        int max = Math.max(this.mMinWidth, i5);
        int max2 = Math.max(this.mMinHeight, i6);
        if (i != 1073741824) {
            i2 = i == Integer.MIN_VALUE ? Math.min(max, i2) : i == 0 ? max : 0;
        }
        if (i3 != 1073741824) {
            i4 = i3 == Integer.MIN_VALUE ? Math.min(max2, i4) : i3 == 0 ? max2 : 0;
        }
        this.mMeasuredWidth = i2;
        this.mMeasuredHeight = i4;
        setWidth(i2);
        setHeight(i4);
        this.mNeedsCallFromSolver = this.mWidgetsCount > 0;
    }
}
