package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintWidget;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Optimizer {
    public static final boolean[] flags = new boolean[3];

    public static void checkMatchParent(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ConstraintWidget constraintWidget) {
        constraintWidget.mHorizontalResolution = -1;
        constraintWidget.mVerticalResolution = -1;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidgetContainer.mListDimensionBehaviors[0];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (dimensionBehaviour != dimensionBehaviour2 && constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
            int i = constraintAnchor.mMargin;
            int width = constraintWidgetContainer.getWidth();
            ConstraintAnchor constraintAnchor2 = constraintWidget.mRight;
            int i2 = width - constraintAnchor2.mMargin;
            constraintAnchor.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor);
            constraintAnchor2.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor2);
            linearSystem.addEquality(constraintAnchor.mSolverVariable, i);
            linearSystem.addEquality(constraintAnchor2.mSolverVariable, i2);
            constraintWidget.mHorizontalResolution = 2;
            constraintWidget.f15mX = i;
            int i3 = i2 - i;
            constraintWidget.mWidth = i3;
            int i4 = constraintWidget.mMinWidth;
            if (i3 < i4) {
                constraintWidget.mWidth = i4;
            }
        }
        if (constraintWidgetContainer.mListDimensionBehaviors[1] == dimensionBehaviour2 || constraintWidget.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            return;
        }
        ConstraintAnchor constraintAnchor3 = constraintWidget.mTop;
        int i5 = constraintAnchor3.mMargin;
        int height = constraintWidgetContainer.getHeight();
        ConstraintAnchor constraintAnchor4 = constraintWidget.mBottom;
        int i6 = height - constraintAnchor4.mMargin;
        constraintAnchor3.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor3);
        constraintAnchor4.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor4);
        linearSystem.addEquality(constraintAnchor3.mSolverVariable, i5);
        linearSystem.addEquality(constraintAnchor4.mSolverVariable, i6);
        if (constraintWidget.mBaselineDistance > 0 || constraintWidget.mVisibility == 8) {
            ConstraintAnchor constraintAnchor5 = constraintWidget.mBaseline;
            constraintAnchor5.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor5);
            linearSystem.addEquality(constraintAnchor5.mSolverVariable, constraintWidget.mBaselineDistance + i5);
        }
        constraintWidget.mVerticalResolution = 2;
        constraintWidget.f16mY = i5;
        int i7 = i6 - i5;
        constraintWidget.mHeight = i7;
        int i8 = constraintWidget.mMinHeight;
        if (i7 < i8) {
            constraintWidget.mHeight = i8;
        }
    }

    public static final boolean enabled(int i, int i2) {
        return (i & i2) == i2;
    }
}
