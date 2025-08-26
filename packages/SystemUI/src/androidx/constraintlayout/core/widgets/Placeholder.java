package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;

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
        int width = this.mResolvedPaddingLeft + this.mResolvedPaddingRight;
        int height = this.mPaddingTop + this.mPaddingBottom;
        if (this.mWidgetsCount > 0) {
            width += this.mWidgets[0].getWidth();
            height += this.mWidgets[0].getHeight();
        }
        int iMax = Math.max(this.mMinWidth, width);
        int iMax2 = Math.max(this.mMinHeight, height);
        if (i != 1073741824) {
            i2 = i == Integer.MIN_VALUE ? Math.min(iMax, i2) : i == 0 ? iMax : 0;
        }
        if (i3 != 1073741824) {
            i4 = i3 == Integer.MIN_VALUE ? Math.min(iMax2, i4) : i3 == 0 ? iMax2 : 0;
        }
        this.mMeasuredWidth = i2;
        this.mMeasuredHeight = i4;
        setWidth(i2);
        setHeight(i4);
        this.mNeedsCallFromSolver = this.mWidgetsCount > 0;
    }
}
