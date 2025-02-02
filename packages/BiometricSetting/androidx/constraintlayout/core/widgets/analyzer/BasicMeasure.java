package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.Optimizer;
import androidx.constraintlayout.core.widgets.VirtualLayout;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class BasicMeasure {
    private ConstraintWidgetContainer mConstraintWidgetContainer;
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList<>();
    private Measure mMeasure = new Measure();

    public static class Measure {
        public ConstraintWidget.DimensionBehaviour horizontalBehavior;
        public int horizontalDimension;
        public int measureStrategy;
        public int measuredBaseline;
        public boolean measuredHasBaseline;
        public int measuredHeight;
        public boolean measuredNeedsSolverPass;
        public int measuredWidth;
        public ConstraintWidget.DimensionBehaviour verticalBehavior;
        public int verticalDimension;
    }

    public interface Measurer {
        void didMeasures();

        void measure(ConstraintWidget constraintWidget, Measure measure);
    }

    public BasicMeasure(ConstraintWidgetContainer constraintWidgetContainer) {
        this.mConstraintWidgetContainer = constraintWidgetContainer;
    }

    private boolean measure(int i, ConstraintWidget constraintWidget, Measurer measurer) {
        Measure measure = this.mMeasure;
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
        measure.horizontalBehavior = dimensionBehaviourArr[0];
        measure.verticalBehavior = dimensionBehaviourArr[1];
        measure.horizontalDimension = constraintWidget.getWidth();
        this.mMeasure.verticalDimension = constraintWidget.getHeight();
        Measure measure2 = this.mMeasure;
        measure2.measuredNeedsSolverPass = false;
        measure2.measureStrategy = i;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = measure2.horizontalBehavior;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean z = dimensionBehaviour == dimensionBehaviour2;
        boolean z2 = measure2.verticalBehavior == dimensionBehaviour2;
        boolean z3 = z && constraintWidget.mDimensionRatio > 0.0f;
        boolean z4 = z2 && constraintWidget.mDimensionRatio > 0.0f;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.FIXED;
        if (z3 && constraintWidget.mResolvedMatchConstraintDefault[0] == 4) {
            measure2.horizontalBehavior = dimensionBehaviour3;
        }
        if (z4 && constraintWidget.mResolvedMatchConstraintDefault[1] == 4) {
            measure2.verticalBehavior = dimensionBehaviour3;
        }
        measurer.measure(constraintWidget, measure2);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        constraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
        Measure measure3 = this.mMeasure;
        measure3.measureStrategy = 0;
        return measure3.measuredNeedsSolverPass;
    }

    private void solveLinearSystem(ConstraintWidgetContainer constraintWidgetContainer, int i, int i2, int i3) {
        constraintWidgetContainer.getClass();
        int minWidth = constraintWidgetContainer.getMinWidth();
        int minHeight = constraintWidgetContainer.getMinHeight();
        constraintWidgetContainer.setMinWidth(0);
        constraintWidgetContainer.setMinHeight(0);
        constraintWidgetContainer.setWidth(i2);
        constraintWidgetContainer.setHeight(i3);
        constraintWidgetContainer.setMinWidth(minWidth);
        constraintWidgetContainer.setMinHeight(minHeight);
        this.mConstraintWidgetContainer.setPass(i);
        this.mConstraintWidgetContainer.layout();
    }

    /* JADX WARN: Removed duplicated region for block: B:127:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01be A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void solverMeasure(ConstraintWidgetContainer constraintWidgetContainer, int i, int i2, int i3, int i4, int i5) {
        boolean z;
        int i6;
        ConstraintWidgetContainer constraintWidgetContainer2;
        int i7;
        ConstraintAnchor.Type type;
        int i8;
        ConstraintAnchor.Type type2;
        ArrayList<ConstraintWidget> arrayList;
        int i9;
        Measurer measurer;
        int i10;
        boolean z2;
        boolean z3;
        int i11;
        boolean z4;
        HorizontalWidgetRun horizontalWidgetRun;
        VerticalWidgetRun verticalWidgetRun;
        int i12;
        int i13;
        boolean z5;
        Measurer measurer2 = constraintWidgetContainer.getMeasurer();
        int size = constraintWidgetContainer.mChildren.size();
        int width = constraintWidgetContainer.getWidth();
        int height = constraintWidgetContainer.getHeight();
        boolean enabled = Optimizer.enabled(i, 128);
        boolean z6 = enabled || Optimizer.enabled(i, 64);
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (z6) {
            for (int i14 = 0; i14 < size; i14++) {
                ConstraintWidget constraintWidget = constraintWidgetContainer.mChildren.get(i14);
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
                boolean z7 = (dimensionBehaviourArr[0] == dimensionBehaviour) && (dimensionBehaviourArr[1] == dimensionBehaviour) && constraintWidget.mDimensionRatio > 0.0f;
                if ((constraintWidget.isInHorizontalChain() && z7) || ((constraintWidget.isInVerticalChain() && z7) || (constraintWidget instanceof VirtualLayout) || constraintWidget.isInHorizontalChain() || constraintWidget.isInVerticalChain())) {
                    z6 = false;
                    break;
                }
            }
        }
        boolean z8 = z6 & ((i2 == 1073741824 && i4 == 1073741824) || enabled);
        if (z8) {
            int min = Math.min(constraintWidgetContainer.getMaxWidth(), i3);
            int min2 = Math.min(constraintWidgetContainer.getMaxHeight(), i5);
            if (i2 == 1073741824 && constraintWidgetContainer.getWidth() != min) {
                constraintWidgetContainer.setWidth(min);
                constraintWidgetContainer.mDependencyGraph.invalidateGraph();
            }
            if (i4 == 1073741824 && constraintWidgetContainer.getHeight() != min2) {
                constraintWidgetContainer.setHeight(min2);
                constraintWidgetContainer.mDependencyGraph.invalidateGraph();
            }
            if (i2 == 1073741824 && i4 == 1073741824) {
                z = constraintWidgetContainer.mDependencyGraph.directMeasure(enabled);
                i6 = 2;
            } else {
                constraintWidgetContainer.mDependencyGraph.directMeasureSetup();
                if (i2 == 1073741824) {
                    i12 = 1;
                    i13 = 1;
                    z5 = constraintWidgetContainer.mDependencyGraph.directMeasureWithOrientation(0, enabled) & true;
                } else {
                    i12 = 1;
                    i13 = 0;
                    z5 = true;
                }
                if (i4 == 1073741824) {
                    z = constraintWidgetContainer.mDependencyGraph.directMeasureWithOrientation(i12, enabled) & z5;
                    i6 = i13 + 1;
                } else {
                    z = z5;
                    i6 = i13;
                }
            }
            if (z) {
                constraintWidgetContainer.updateFromRuns(i2 == 1073741824, i4 == 1073741824);
            }
        } else {
            z = false;
            i6 = 0;
        }
        if (z && i6 == 2) {
            return;
        }
        int optimizationLevel = constraintWidgetContainer.getOptimizationLevel();
        if (size > 0) {
            int size2 = constraintWidgetContainer.mChildren.size();
            boolean optimizeFor = constraintWidgetContainer.optimizeFor(64);
            Measurer measurer3 = constraintWidgetContainer.getMeasurer();
            int i15 = 0;
            while (i15 < size2) {
                ConstraintWidget constraintWidget2 = constraintWidgetContainer.mChildren.get(i15);
                if ((constraintWidget2 instanceof Guideline) || (constraintWidget2 instanceof Barrier) || constraintWidget2.isInVirtualLayout() || (optimizeFor && (horizontalWidgetRun = constraintWidget2.mHorizontalRun) != null && (verticalWidgetRun = constraintWidget2.mVerticalRun) != null && horizontalWidgetRun.mDimension.resolved && verticalWidgetRun.mDimension.resolved)) {
                    i11 = size2;
                    z4 = optimizeFor;
                } else {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget2.getDimensionBehaviour(0);
                    i11 = size2;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = constraintWidget2.getDimensionBehaviour(1);
                    z4 = optimizeFor;
                    boolean z9 = dimensionBehaviour2 == dimensionBehaviour && constraintWidget2.mMatchConstraintDefaultWidth != 1 && dimensionBehaviour3 == dimensionBehaviour && constraintWidget2.mMatchConstraintDefaultHeight != 1;
                    if (!z9 && constraintWidgetContainer.optimizeFor(1) && !(constraintWidget2 instanceof VirtualLayout)) {
                        if (dimensionBehaviour2 == dimensionBehaviour && constraintWidget2.mMatchConstraintDefaultWidth == 0 && dimensionBehaviour3 != dimensionBehaviour && !constraintWidget2.isInHorizontalChain()) {
                            z9 = true;
                        }
                        if (dimensionBehaviour3 == dimensionBehaviour && constraintWidget2.mMatchConstraintDefaultHeight == 0 && dimensionBehaviour2 != dimensionBehaviour && !constraintWidget2.isInHorizontalChain()) {
                            z9 = true;
                        }
                        if (dimensionBehaviour2 == dimensionBehaviour || dimensionBehaviour3 == dimensionBehaviour) {
                            if (constraintWidget2.mDimensionRatio > 0.0f) {
                                z9 = true;
                            }
                            if (z9) {
                                measure(0, constraintWidget2, measurer3);
                            }
                        }
                    }
                    if (z9) {
                    }
                }
                i15++;
                size2 = i11;
                optimizeFor = z4;
            }
            measurer3.didMeasures();
        }
        updateHierarchy(constraintWidgetContainer);
        ArrayList<ConstraintWidget> arrayList2 = this.mVariableDimensionsWidgets;
        int size3 = arrayList2.size();
        if (size > 0) {
            solveLinearSystem(constraintWidgetContainer, 0, width, height);
        }
        if (size3 > 0) {
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidgetContainer.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = dimensionBehaviourArr2[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            boolean z10 = dimensionBehaviour4 == dimensionBehaviour5;
            boolean z11 = dimensionBehaviourArr2[1] == dimensionBehaviour5;
            int max = Math.max(constraintWidgetContainer.getWidth(), this.mConstraintWidgetContainer.getMinWidth());
            int max2 = Math.max(constraintWidgetContainer.getHeight(), this.mConstraintWidgetContainer.getMinHeight());
            int i16 = 0;
            boolean z12 = false;
            while (true) {
                type = ConstraintAnchor.Type.BOTTOM;
                i8 = optimizationLevel;
                type2 = ConstraintAnchor.Type.RIGHT;
                if (i16 >= size3) {
                    break;
                }
                ConstraintWidget constraintWidget3 = arrayList2.get(i16);
                int i17 = width;
                if (constraintWidget3 instanceof VirtualLayout) {
                    int width2 = constraintWidget3.getWidth();
                    i10 = height;
                    int height2 = constraintWidget3.getHeight();
                    z2 = z8;
                    boolean measure = z12 | measure(1, constraintWidget3, measurer2);
                    int width3 = constraintWidget3.getWidth();
                    int height3 = constraintWidget3.getHeight();
                    if (width3 != width2) {
                        constraintWidget3.setWidth(width3);
                        if (z10 && constraintWidget3.getRight() > max) {
                            max = Math.max(max, constraintWidget3.getAnchor(type2).getMargin() + constraintWidget3.getRight());
                        }
                        z3 = true;
                    } else {
                        z3 = measure;
                    }
                    if (height3 != height2) {
                        constraintWidget3.setHeight(height3);
                        if (z11 && constraintWidget3.getBottom() > max2) {
                            max2 = Math.max(max2, constraintWidget3.getAnchor(type).getMargin() + constraintWidget3.getBottom());
                        }
                        z3 = true;
                    }
                    z12 = ((VirtualLayout) constraintWidget3).needSolverPass() | z3;
                } else {
                    z2 = z8;
                    i10 = height;
                }
                i16++;
                optimizationLevel = i8;
                width = i17;
                height = i10;
                z8 = z2;
            }
            boolean z13 = z8;
            int i18 = width;
            int i19 = height;
            int i20 = 0;
            int i21 = 2;
            while (i20 < i21) {
                int i22 = 0;
                while (i22 < size3) {
                    ConstraintWidget constraintWidget4 = arrayList2.get(i22);
                    if (((constraintWidget4 instanceof Helper) && !(constraintWidget4 instanceof VirtualLayout)) || (constraintWidget4 instanceof Guideline) || constraintWidget4.getVisibility() == 8 || ((z13 && constraintWidget4.mHorizontalRun.mDimension.resolved && constraintWidget4.mVerticalRun.mDimension.resolved) || (constraintWidget4 instanceof VirtualLayout))) {
                        arrayList = arrayList2;
                        measurer = measurer2;
                        i9 = size3;
                    } else {
                        int width4 = constraintWidget4.getWidth();
                        int height4 = constraintWidget4.getHeight();
                        arrayList = arrayList2;
                        int baselineDistance = constraintWidget4.getBaselineDistance();
                        i9 = size3;
                        boolean measure2 = measure(i20 == 1 ? 2 : 1, constraintWidget4, measurer2) | z12;
                        int width5 = constraintWidget4.getWidth();
                        measurer = measurer2;
                        int height5 = constraintWidget4.getHeight();
                        if (width5 != width4) {
                            constraintWidget4.setWidth(width5);
                            if (z10 && constraintWidget4.getRight() > max) {
                                max = Math.max(max, constraintWidget4.getAnchor(type2).getMargin() + constraintWidget4.getRight());
                            }
                            measure2 = true;
                        }
                        if (height5 != height4) {
                            constraintWidget4.setHeight(height5);
                            if (z11 && constraintWidget4.getBottom() > max2) {
                                max2 = Math.max(max2, constraintWidget4.getAnchor(type).getMargin() + constraintWidget4.getBottom());
                            }
                            measure2 = true;
                        }
                        z12 = (!constraintWidget4.hasBaseline() || baselineDistance == constraintWidget4.getBaselineDistance()) ? measure2 : true;
                    }
                    i22++;
                    size3 = i9;
                    arrayList2 = arrayList;
                    measurer2 = measurer;
                }
                ArrayList<ConstraintWidget> arrayList3 = arrayList2;
                Measurer measurer4 = measurer2;
                int i23 = size3;
                if (!z12) {
                    break;
                }
                i20++;
                solveLinearSystem(constraintWidgetContainer, i20, i18, i19);
                size3 = i23;
                arrayList2 = arrayList3;
                measurer2 = measurer4;
                i21 = 2;
                z12 = false;
            }
            constraintWidgetContainer2 = constraintWidgetContainer;
            i7 = i8;
        } else {
            constraintWidgetContainer2 = constraintWidgetContainer;
            i7 = optimizationLevel;
        }
        constraintWidgetContainer2.setOptimizationLevel(i7);
    }

    public final void updateHierarchy(ConstraintWidgetContainer constraintWidgetContainer) {
        ArrayList<ConstraintWidget> arrayList = this.mVariableDimensionsWidgets;
        arrayList.clear();
        int size = constraintWidgetContainer.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = constraintWidgetContainer.mChildren.get(i);
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
            if (dimensionBehaviour == dimensionBehaviour2 || dimensionBehaviourArr[1] == dimensionBehaviour2) {
                arrayList.add(constraintWidget);
            }
        }
        constraintWidgetContainer.mDependencyGraph.invalidateGraph();
    }
}
