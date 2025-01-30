package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Direct {
    public static final BasicMeasure.Measure measure = new BasicMeasure.Measure();

    public static boolean canMeasure(ConstraintWidget constraintWidget) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = dimensionBehaviourArr[0];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = dimensionBehaviourArr[1];
        ConstraintWidget constraintWidget2 = constraintWidget.mParent;
        ConstraintWidgetContainer constraintWidgetContainer = constraintWidget2 != null ? (ConstraintWidgetContainer) constraintWidget2 : null;
        if (constraintWidgetContainer != null) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = constraintWidgetContainer.mListDimensionBehaviors[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        if (constraintWidgetContainer != null) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour7 = constraintWidgetContainer.mListDimensionBehaviors[1];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour8 = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        ConstraintWidget.DimensionBehaviour dimensionBehaviour9 = ConstraintWidget.DimensionBehaviour.FIXED;
        boolean z = dimensionBehaviour3 == dimensionBehaviour9 || constraintWidget.isResolvedHorizontally() || dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (dimensionBehaviour3 == (dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && constraintWidget.mMatchConstraintDefaultWidth == 0 && constraintWidget.mDimensionRatio == 0.0f && constraintWidget.hasDanglingDimension(0)) || (dimensionBehaviour3 == dimensionBehaviour2 && constraintWidget.mMatchConstraintDefaultWidth == 1 && constraintWidget.hasResolvedTargets(0, constraintWidget.getWidth()));
        boolean z2 = dimensionBehaviour4 == dimensionBehaviour9 || constraintWidget.isResolvedVertically() || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (dimensionBehaviour4 == (dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && constraintWidget.mMatchConstraintDefaultHeight == 0 && constraintWidget.mDimensionRatio == 0.0f && constraintWidget.hasDanglingDimension(1)) || (dimensionBehaviour4 == dimensionBehaviour && constraintWidget.mMatchConstraintDefaultHeight == 1 && constraintWidget.hasResolvedTargets(1, constraintWidget.getHeight()));
        if (constraintWidget.mDimensionRatio <= 0.0f || !(z || z2)) {
            return z && z2;
        }
        return true;
    }

    public static void horizontalSolvingPass(int i, ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer, boolean z) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        ConstraintAnchor constraintAnchor3;
        ConstraintAnchor constraintAnchor4;
        if (constraintWidget.horizontalSolvingPass) {
            return;
        }
        if (!(constraintWidget instanceof ConstraintWidgetContainer) && constraintWidget.isMeasureRequested() && canMeasure(constraintWidget)) {
            ConstraintWidgetContainer.measure(constraintWidget, measurer, new BasicMeasure.Measure());
        }
        ConstraintAnchor anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
        ConstraintAnchor anchor2 = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
        int finalValue = anchor.getFinalValue();
        int finalValue2 = anchor2.getFinalValue();
        HashSet hashSet = anchor.mDependents;
        char c = 0;
        if (hashSet != null && anchor.mHasFinalValue) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                ConstraintAnchor constraintAnchor5 = (ConstraintAnchor) it.next();
                ConstraintWidget constraintWidget2 = constraintAnchor5.mOwner;
                int i2 = i + 1;
                boolean canMeasure = canMeasure(constraintWidget2);
                if (constraintWidget2.isMeasureRequested() && canMeasure) {
                    ConstraintWidgetContainer.measure(constraintWidget2, measurer, new BasicMeasure.Measure());
                }
                ConstraintAnchor constraintAnchor6 = constraintWidget2.mLeft;
                ConstraintAnchor constraintAnchor7 = constraintWidget2.mRight;
                char c2 = ((constraintAnchor5 == constraintAnchor6 && (constraintAnchor4 = constraintAnchor7.mTarget) != null && constraintAnchor4.mHasFinalValue) || (constraintAnchor5 == constraintAnchor7 && (constraintAnchor3 = constraintAnchor6.mTarget) != null && constraintAnchor3.mHasFinalValue)) ? (char) 1 : c;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget2.mListDimensionBehaviors[c];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour != dimensionBehaviour2 || canMeasure) {
                    if (!constraintWidget2.isMeasureRequested()) {
                        if (constraintAnchor5 == constraintAnchor6 && constraintAnchor7.mTarget == null) {
                            int margin = constraintAnchor6.getMargin() + finalValue;
                            constraintWidget2.setFinalHorizontal(margin, constraintWidget2.getWidth() + margin);
                            horizontalSolvingPass(i2, constraintWidget2, measurer, z);
                        } else if (constraintAnchor5 == constraintAnchor7 && constraintAnchor6.mTarget == null) {
                            int margin2 = finalValue - constraintAnchor7.getMargin();
                            constraintWidget2.setFinalHorizontal(margin2 - constraintWidget2.getWidth(), margin2);
                            horizontalSolvingPass(i2, constraintWidget2, measurer, z);
                        } else if (c2 != 0 && !constraintWidget2.isInHorizontalChain()) {
                            solveHorizontalCenterConstraints(i2, constraintWidget2, measurer, z);
                        }
                    }
                } else if (dimensionBehaviour == dimensionBehaviour2 && constraintWidget2.mMatchConstraintMaxWidth >= 0 && constraintWidget2.mMatchConstraintMinWidth >= 0 && ((constraintWidget2.mVisibility == 8 || (constraintWidget2.mMatchConstraintDefaultWidth == 0 && constraintWidget2.mDimensionRatio == 0.0f)) && !constraintWidget2.isInHorizontalChain() && !constraintWidget2.mInVirtualLayout && c2 != 0 && !constraintWidget2.isInHorizontalChain())) {
                    solveHorizontalMatchConstraint(i2, constraintWidget, measurer, constraintWidget2, z);
                }
                c = 0;
            }
        }
        if (constraintWidget instanceof Guideline) {
            return;
        }
        HashSet hashSet2 = anchor2.mDependents;
        if (hashSet2 != null && anchor2.mHasFinalValue) {
            Iterator it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                ConstraintAnchor constraintAnchor8 = (ConstraintAnchor) it2.next();
                ConstraintWidget constraintWidget3 = constraintAnchor8.mOwner;
                int i3 = i + 1;
                boolean canMeasure2 = canMeasure(constraintWidget3);
                if (constraintWidget3.isMeasureRequested() && canMeasure2) {
                    ConstraintWidgetContainer.measure(constraintWidget3, measurer, new BasicMeasure.Measure());
                }
                ConstraintAnchor constraintAnchor9 = constraintWidget3.mLeft;
                ConstraintAnchor constraintAnchor10 = constraintWidget3.mRight;
                boolean z2 = (constraintAnchor8 == constraintAnchor9 && (constraintAnchor2 = constraintAnchor10.mTarget) != null && constraintAnchor2.mHasFinalValue) || (constraintAnchor8 == constraintAnchor10 && (constraintAnchor = constraintAnchor9.mTarget) != null && constraintAnchor.mHasFinalValue);
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = constraintWidget3.mListDimensionBehaviors[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour3 != dimensionBehaviour4 || canMeasure2) {
                    if (!constraintWidget3.isMeasureRequested()) {
                        if (constraintAnchor8 == constraintAnchor9 && constraintAnchor10.mTarget == null) {
                            int margin3 = constraintAnchor9.getMargin() + finalValue2;
                            constraintWidget3.setFinalHorizontal(margin3, constraintWidget3.getWidth() + margin3);
                            horizontalSolvingPass(i3, constraintWidget3, measurer, z);
                        } else if (constraintAnchor8 == constraintAnchor10 && constraintAnchor9.mTarget == null) {
                            int margin4 = finalValue2 - constraintAnchor10.getMargin();
                            constraintWidget3.setFinalHorizontal(margin4 - constraintWidget3.getWidth(), margin4);
                            horizontalSolvingPass(i3, constraintWidget3, measurer, z);
                        } else if (z2 && !constraintWidget3.isInHorizontalChain()) {
                            solveHorizontalCenterConstraints(i3, constraintWidget3, measurer, z);
                        }
                    }
                } else if (dimensionBehaviour3 == dimensionBehaviour4 && constraintWidget3.mMatchConstraintMaxWidth >= 0 && constraintWidget3.mMatchConstraintMinWidth >= 0) {
                    if (constraintWidget3.mVisibility != 8) {
                        if (constraintWidget3.mMatchConstraintDefaultWidth == 0) {
                            if (constraintWidget3.mDimensionRatio == 0.0f) {
                            }
                        }
                    }
                    if (!constraintWidget3.isInHorizontalChain() && !constraintWidget3.mInVirtualLayout && z2 && !constraintWidget3.isInHorizontalChain()) {
                        solveHorizontalMatchConstraint(i3, constraintWidget, measurer, constraintWidget3, z);
                    }
                }
            }
        }
        constraintWidget.horizontalSolvingPass = true;
    }

    public static void solveHorizontalCenterConstraints(int i, ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer, boolean z) {
        float f = constraintWidget.mHorizontalBiasPercent;
        ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
        int finalValue = constraintAnchor.mTarget.getFinalValue();
        ConstraintAnchor constraintAnchor2 = constraintWidget.mRight;
        int finalValue2 = constraintAnchor2.mTarget.getFinalValue();
        int margin = constraintAnchor.getMargin() + finalValue;
        int margin2 = finalValue2 - constraintAnchor2.getMargin();
        if (finalValue == finalValue2) {
            f = 0.5f;
        } else {
            finalValue = margin;
            finalValue2 = margin2;
        }
        int width = constraintWidget.getWidth();
        int i2 = (finalValue2 - finalValue) - width;
        if (finalValue > finalValue2) {
            i2 = (finalValue - finalValue2) - width;
        }
        int i3 = ((int) (i2 > 0 ? (f * i2) + 0.5f : f * i2)) + finalValue;
        int i4 = i3 + width;
        if (finalValue > finalValue2) {
            i4 = i3 - width;
        }
        constraintWidget.setFinalHorizontal(i3, i4);
        horizontalSolvingPass(i + 1, constraintWidget, measurer, z);
    }

    public static void solveHorizontalMatchConstraint(int i, ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer, ConstraintWidget constraintWidget2, boolean z) {
        float f = constraintWidget2.mHorizontalBiasPercent;
        ConstraintAnchor constraintAnchor = constraintWidget2.mLeft;
        int margin = constraintAnchor.getMargin() + constraintAnchor.mTarget.getFinalValue();
        ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
        int finalValue = constraintAnchor2.mTarget.getFinalValue() - constraintAnchor2.getMargin();
        if (finalValue >= margin) {
            int width = constraintWidget2.getWidth();
            if (constraintWidget2.mVisibility != 8) {
                int i2 = constraintWidget2.mMatchConstraintDefaultWidth;
                if (i2 == 2) {
                    width = (int) (constraintWidget2.mHorizontalBiasPercent * 0.5f * (constraintWidget instanceof ConstraintWidgetContainer ? constraintWidget.getWidth() : constraintWidget.mParent.getWidth()));
                } else if (i2 == 0) {
                    width = finalValue - margin;
                }
                width = Math.max(constraintWidget2.mMatchConstraintMinWidth, width);
                int i3 = constraintWidget2.mMatchConstraintMaxWidth;
                if (i3 > 0) {
                    width = Math.min(i3, width);
                }
            }
            int i4 = margin + ((int) ((f * ((finalValue - margin) - width)) + 0.5f));
            constraintWidget2.setFinalHorizontal(i4, width + i4);
            horizontalSolvingPass(i + 1, constraintWidget2, measurer, z);
        }
    }

    public static void solveVerticalCenterConstraints(int i, ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer) {
        float f = constraintWidget.mVerticalBiasPercent;
        ConstraintAnchor constraintAnchor = constraintWidget.mTop;
        int finalValue = constraintAnchor.mTarget.getFinalValue();
        ConstraintAnchor constraintAnchor2 = constraintWidget.mBottom;
        int finalValue2 = constraintAnchor2.mTarget.getFinalValue();
        int margin = constraintAnchor.getMargin() + finalValue;
        int margin2 = finalValue2 - constraintAnchor2.getMargin();
        if (finalValue == finalValue2) {
            f = 0.5f;
        } else {
            finalValue = margin;
            finalValue2 = margin2;
        }
        int height = constraintWidget.getHeight();
        int i2 = (finalValue2 - finalValue) - height;
        if (finalValue > finalValue2) {
            i2 = (finalValue - finalValue2) - height;
        }
        int i3 = (int) (i2 > 0 ? (f * i2) + 0.5f : f * i2);
        int i4 = finalValue + i3;
        int i5 = i4 + height;
        if (finalValue > finalValue2) {
            i4 = finalValue - i3;
            i5 = i4 - height;
        }
        constraintWidget.setFinalVertical(i4, i5);
        verticalSolvingPass(i + 1, constraintWidget, measurer);
    }

    public static void solveVerticalMatchConstraint(int i, ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer, ConstraintWidget constraintWidget2) {
        float f = constraintWidget2.mVerticalBiasPercent;
        ConstraintAnchor constraintAnchor = constraintWidget2.mTop;
        int margin = constraintAnchor.getMargin() + constraintAnchor.mTarget.getFinalValue();
        ConstraintAnchor constraintAnchor2 = constraintWidget2.mBottom;
        int finalValue = constraintAnchor2.mTarget.getFinalValue() - constraintAnchor2.getMargin();
        if (finalValue >= margin) {
            int height = constraintWidget2.getHeight();
            if (constraintWidget2.mVisibility != 8) {
                int i2 = constraintWidget2.mMatchConstraintDefaultHeight;
                if (i2 == 2) {
                    height = (int) (f * 0.5f * (constraintWidget instanceof ConstraintWidgetContainer ? constraintWidget.getHeight() : constraintWidget.mParent.getHeight()));
                } else if (i2 == 0) {
                    height = finalValue - margin;
                }
                height = Math.max(constraintWidget2.mMatchConstraintMinHeight, height);
                int i3 = constraintWidget2.mMatchConstraintMaxHeight;
                if (i3 > 0) {
                    height = Math.min(i3, height);
                }
            }
            int i4 = margin + ((int) ((f * ((finalValue - margin) - height)) + 0.5f));
            constraintWidget2.setFinalVertical(i4, height + i4);
            verticalSolvingPass(i + 1, constraintWidget2, measurer);
        }
    }

    public static void verticalSolvingPass(int i, ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        ConstraintAnchor constraintAnchor3;
        ConstraintAnchor constraintAnchor4;
        ConstraintAnchor constraintAnchor5;
        if (constraintWidget.verticalSolvingPass) {
            return;
        }
        if (!(constraintWidget instanceof ConstraintWidgetContainer) && constraintWidget.isMeasureRequested() && canMeasure(constraintWidget)) {
            ConstraintWidgetContainer.measure(constraintWidget, measurer, new BasicMeasure.Measure());
        }
        ConstraintAnchor anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.TOP);
        ConstraintAnchor anchor2 = constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM);
        int finalValue = anchor.getFinalValue();
        int finalValue2 = anchor2.getFinalValue();
        HashSet hashSet = anchor.mDependents;
        char c = 1;
        if (hashSet != null && anchor.mHasFinalValue) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                ConstraintAnchor constraintAnchor6 = (ConstraintAnchor) it.next();
                ConstraintWidget constraintWidget2 = constraintAnchor6.mOwner;
                int i2 = i + 1;
                boolean canMeasure = canMeasure(constraintWidget2);
                if (constraintWidget2.isMeasureRequested() && canMeasure) {
                    ConstraintWidgetContainer.measure(constraintWidget2, measurer, new BasicMeasure.Measure());
                }
                ConstraintAnchor constraintAnchor7 = constraintWidget2.mTop;
                ConstraintAnchor constraintAnchor8 = constraintWidget2.mBottom;
                char c2 = ((constraintAnchor6 == constraintAnchor7 && (constraintAnchor5 = constraintAnchor8.mTarget) != null && constraintAnchor5.mHasFinalValue) || (constraintAnchor6 == constraintAnchor8 && (constraintAnchor4 = constraintAnchor7.mTarget) != null && constraintAnchor4.mHasFinalValue)) ? c : (char) 0;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget2.mListDimensionBehaviors[c];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour != dimensionBehaviour2 || canMeasure) {
                    if (!constraintWidget2.isMeasureRequested()) {
                        if (constraintAnchor6 == constraintAnchor7 && constraintAnchor8.mTarget == null) {
                            int margin = constraintAnchor7.getMargin() + finalValue;
                            constraintWidget2.setFinalVertical(margin, constraintWidget2.getHeight() + margin);
                            verticalSolvingPass(i2, constraintWidget2, measurer);
                        } else if (constraintAnchor6 == constraintAnchor8 && constraintAnchor7.mTarget == null) {
                            int margin2 = finalValue - constraintAnchor8.getMargin();
                            constraintWidget2.setFinalVertical(margin2 - constraintWidget2.getHeight(), margin2);
                            verticalSolvingPass(i2, constraintWidget2, measurer);
                        } else if (c2 != 0 && !constraintWidget2.isInVerticalChain()) {
                            solveVerticalCenterConstraints(i2, constraintWidget2, measurer);
                        }
                    }
                } else if (dimensionBehaviour == dimensionBehaviour2 && constraintWidget2.mMatchConstraintMaxHeight >= 0 && constraintWidget2.mMatchConstraintMinHeight >= 0 && ((constraintWidget2.mVisibility == 8 || (constraintWidget2.mMatchConstraintDefaultHeight == 0 && constraintWidget2.mDimensionRatio == 0.0f)) && !constraintWidget2.isInVerticalChain() && !constraintWidget2.mInVirtualLayout && c2 != 0 && !constraintWidget2.isInVerticalChain())) {
                    solveVerticalMatchConstraint(i2, constraintWidget, measurer, constraintWidget2);
                }
                c = 1;
            }
        }
        if (constraintWidget instanceof Guideline) {
            return;
        }
        HashSet hashSet2 = anchor2.mDependents;
        if (hashSet2 != null && anchor2.mHasFinalValue) {
            Iterator it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                ConstraintAnchor constraintAnchor9 = (ConstraintAnchor) it2.next();
                ConstraintWidget constraintWidget3 = constraintAnchor9.mOwner;
                int i3 = i + 1;
                boolean canMeasure2 = canMeasure(constraintWidget3);
                if (constraintWidget3.isMeasureRequested() && canMeasure2) {
                    ConstraintWidgetContainer.measure(constraintWidget3, measurer, new BasicMeasure.Measure());
                }
                ConstraintAnchor constraintAnchor10 = constraintWidget3.mTop;
                ConstraintAnchor constraintAnchor11 = constraintWidget3.mBottom;
                boolean z = (constraintAnchor9 == constraintAnchor10 && (constraintAnchor3 = constraintAnchor11.mTarget) != null && constraintAnchor3.mHasFinalValue) || (constraintAnchor9 == constraintAnchor11 && (constraintAnchor2 = constraintAnchor10.mTarget) != null && constraintAnchor2.mHasFinalValue);
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = constraintWidget3.mListDimensionBehaviors[1];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour3 != dimensionBehaviour4 || canMeasure2) {
                    if (!constraintWidget3.isMeasureRequested()) {
                        if (constraintAnchor9 == constraintAnchor10 && constraintAnchor11.mTarget == null) {
                            int margin3 = constraintAnchor10.getMargin() + finalValue2;
                            constraintWidget3.setFinalVertical(margin3, constraintWidget3.getHeight() + margin3);
                            verticalSolvingPass(i3, constraintWidget3, measurer);
                        } else if (constraintAnchor9 == constraintAnchor11 && constraintAnchor10.mTarget == null) {
                            int margin4 = finalValue2 - constraintAnchor11.getMargin();
                            constraintWidget3.setFinalVertical(margin4 - constraintWidget3.getHeight(), margin4);
                            verticalSolvingPass(i3, constraintWidget3, measurer);
                        } else if (z && !constraintWidget3.isInVerticalChain()) {
                            solveVerticalCenterConstraints(i3, constraintWidget3, measurer);
                        }
                    }
                } else if (dimensionBehaviour3 == dimensionBehaviour4 && constraintWidget3.mMatchConstraintMaxHeight >= 0 && constraintWidget3.mMatchConstraintMinHeight >= 0) {
                    if (constraintWidget3.mVisibility != 8) {
                        if (constraintWidget3.mMatchConstraintDefaultHeight == 0) {
                            if (constraintWidget3.mDimensionRatio == 0.0f) {
                            }
                        }
                    }
                    if (!constraintWidget3.isInVerticalChain() && !constraintWidget3.mInVirtualLayout && z && !constraintWidget3.isInVerticalChain()) {
                        solveVerticalMatchConstraint(i3, constraintWidget, measurer, constraintWidget3);
                    }
                }
            }
        }
        ConstraintAnchor anchor3 = constraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE);
        if (anchor3.mDependents != null && anchor3.mHasFinalValue) {
            int finalValue3 = anchor3.getFinalValue();
            Iterator it3 = anchor3.mDependents.iterator();
            while (it3.hasNext()) {
                ConstraintAnchor constraintAnchor12 = (ConstraintAnchor) it3.next();
                ConstraintWidget constraintWidget4 = constraintAnchor12.mOwner;
                int i4 = i + 1;
                boolean canMeasure3 = canMeasure(constraintWidget4);
                if (constraintWidget4.isMeasureRequested() && canMeasure3) {
                    ConstraintWidgetContainer.measure(constraintWidget4, measurer, new BasicMeasure.Measure());
                }
                if (constraintWidget4.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure3) {
                    if (!constraintWidget4.isMeasureRequested() && constraintAnchor12 == (constraintAnchor = constraintWidget4.mBaseline)) {
                        int margin5 = constraintAnchor12.getMargin() + finalValue3;
                        if (constraintWidget4.hasBaseline) {
                            int i5 = margin5 - constraintWidget4.mBaselineDistance;
                            int i6 = constraintWidget4.mHeight + i5;
                            constraintWidget4.f16mY = i5;
                            constraintWidget4.mTop.setFinalValue(i5);
                            constraintWidget4.mBottom.setFinalValue(i6);
                            constraintAnchor.setFinalValue(margin5);
                            constraintWidget4.resolvedVertical = true;
                        }
                        verticalSolvingPass(i4, constraintWidget4, measurer);
                    }
                }
            }
        }
        constraintWidget.verticalSolvingPass = true;
    }
}
