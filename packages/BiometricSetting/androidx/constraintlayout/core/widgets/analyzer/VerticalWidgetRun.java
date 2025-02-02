package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class VerticalWidgetRun extends WidgetRun {
    public DependencyNode baseline;
    BaselineDimensionDependency mBaselineDimension;

    public VerticalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        DependencyNode dependencyNode = new DependencyNode(this);
        this.baseline = dependencyNode;
        this.mBaselineDimension = null;
        this.start.mType = DependencyNode.Type.TOP;
        this.end.mType = DependencyNode.Type.BOTTOM;
        dependencyNode.mType = DependencyNode.Type.BASELINE;
        this.orientation = 1;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    final void apply() {
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        ConstraintWidget constraintWidget3;
        ConstraintWidget constraintWidget4;
        ConstraintWidget constraintWidget5 = this.mWidget;
        if (constraintWidget5.measured) {
            this.mDimension.resolve(constraintWidget5.getHeight());
        }
        boolean z = this.mDimension.resolved;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (!z) {
            ConstraintWidget constraintWidget6 = this.mWidget;
            this.mDimensionBehavior = constraintWidget6.mListDimensionBehaviors[1];
            if (constraintWidget6.hasBaseline()) {
                this.mBaselineDimension = new BaselineDimensionDependency(this);
            }
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = this.mDimensionBehavior;
            if (dimensionBehaviour4 != dimensionBehaviour3) {
                if (dimensionBehaviour4 == dimensionBehaviour && (constraintWidget4 = this.mWidget.mParent) != null && constraintWidget4.mListDimensionBehaviors[1] == dimensionBehaviour2) {
                    int height = (constraintWidget4.getHeight() - this.mWidget.mTop.getMargin()) - this.mWidget.mBottom.getMargin();
                    WidgetRun.addTarget(this.start, constraintWidget4.mVerticalRun.start, this.mWidget.mTop.getMargin());
                    WidgetRun.addTarget(this.end, constraintWidget4.mVerticalRun.end, -this.mWidget.mBottom.getMargin());
                    this.mDimension.resolve(height);
                    return;
                }
                if (dimensionBehaviour4 == dimensionBehaviour2) {
                    this.mDimension.resolve(this.mWidget.getHeight());
                }
            }
        } else if (this.mDimensionBehavior == dimensionBehaviour && (constraintWidget2 = (constraintWidget = this.mWidget).mParent) != null && constraintWidget2.mListDimensionBehaviors[1] == dimensionBehaviour2) {
            WidgetRun.addTarget(this.start, constraintWidget2.mVerticalRun.start, constraintWidget.mTop.getMargin());
            WidgetRun.addTarget(this.end, constraintWidget2.mVerticalRun.end, -this.mWidget.mBottom.getMargin());
            return;
        }
        DimensionDependency dimensionDependency = this.mDimension;
        boolean z2 = dimensionDependency.resolved;
        if (z2) {
            ConstraintWidget constraintWidget7 = this.mWidget;
            if (constraintWidget7.measured) {
                ConstraintAnchor[] constraintAnchorArr = constraintWidget7.mListAnchors;
                ConstraintAnchor constraintAnchor = constraintAnchorArr[2];
                ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
                if (constraintAnchor2 != null && constraintAnchorArr[3].mTarget != null) {
                    if (constraintWidget7.isInVerticalChain()) {
                        this.start.mMargin = this.mWidget.mListAnchors[2].getMargin();
                        this.end.mMargin = -this.mWidget.mListAnchors[3].getMargin();
                    } else {
                        DependencyNode target = WidgetRun.getTarget(this.mWidget.mListAnchors[2]);
                        if (target != null) {
                            WidgetRun.addTarget(this.start, target, this.mWidget.mListAnchors[2].getMargin());
                        }
                        DependencyNode target2 = WidgetRun.getTarget(this.mWidget.mListAnchors[3]);
                        if (target2 != null) {
                            WidgetRun.addTarget(this.end, target2, -this.mWidget.mListAnchors[3].getMargin());
                        }
                        this.start.delegateToWidgetRun = true;
                        this.end.delegateToWidgetRun = true;
                    }
                    if (this.mWidget.hasBaseline()) {
                        WidgetRun.addTarget(this.baseline, this.start, this.mWidget.getBaselineDistance());
                        return;
                    }
                    return;
                }
                if (constraintAnchor2 != null) {
                    DependencyNode target3 = WidgetRun.getTarget(constraintAnchor);
                    if (target3 != null) {
                        WidgetRun.addTarget(this.start, target3, this.mWidget.mListAnchors[2].getMargin());
                        WidgetRun.addTarget(this.end, this.start, this.mDimension.value);
                        if (this.mWidget.hasBaseline()) {
                            WidgetRun.addTarget(this.baseline, this.start, this.mWidget.getBaselineDistance());
                            return;
                        }
                        return;
                    }
                    return;
                }
                ConstraintAnchor constraintAnchor3 = constraintAnchorArr[3];
                if (constraintAnchor3.mTarget != null) {
                    DependencyNode target4 = WidgetRun.getTarget(constraintAnchor3);
                    if (target4 != null) {
                        WidgetRun.addTarget(this.end, target4, -this.mWidget.mListAnchors[3].getMargin());
                        WidgetRun.addTarget(this.start, this.end, -this.mDimension.value);
                    }
                    if (this.mWidget.hasBaseline()) {
                        WidgetRun.addTarget(this.baseline, this.start, this.mWidget.getBaselineDistance());
                        return;
                    }
                    return;
                }
                ConstraintAnchor constraintAnchor4 = constraintAnchorArr[4];
                if (constraintAnchor4.mTarget != null) {
                    DependencyNode target5 = WidgetRun.getTarget(constraintAnchor4);
                    if (target5 != null) {
                        WidgetRun.addTarget(this.baseline, target5, 0);
                        WidgetRun.addTarget(this.start, this.baseline, -this.mWidget.getBaselineDistance());
                        WidgetRun.addTarget(this.end, this.start, this.mDimension.value);
                        return;
                    }
                    return;
                }
                if ((constraintWidget7 instanceof Helper) || constraintWidget7.mParent == null || constraintWidget7.getAnchor(ConstraintAnchor.Type.CENTER).mTarget != null) {
                    return;
                }
                ConstraintWidget constraintWidget8 = this.mWidget;
                WidgetRun.addTarget(this.start, constraintWidget8.mParent.mVerticalRun.start, constraintWidget8.getY());
                WidgetRun.addTarget(this.end, this.start, this.mDimension.value);
                if (this.mWidget.hasBaseline()) {
                    WidgetRun.addTarget(this.baseline, this.start, this.mWidget.getBaselineDistance());
                    return;
                }
                return;
            }
        }
        if (z2 || this.mDimensionBehavior != dimensionBehaviour3) {
            dimensionDependency.addDependency(this);
        } else {
            ConstraintWidget constraintWidget9 = this.mWidget;
            int i = constraintWidget9.mMatchConstraintDefaultHeight;
            if (i == 2) {
                ConstraintWidget constraintWidget10 = constraintWidget9.mParent;
                if (constraintWidget10 != null) {
                    DimensionDependency dimensionDependency2 = constraintWidget10.mVerticalRun.mDimension;
                    ((ArrayList) dimensionDependency.mTargets).add(dimensionDependency2);
                    ((ArrayList) dimensionDependency2.mDependencies).add(this.mDimension);
                    DimensionDependency dimensionDependency3 = this.mDimension;
                    dimensionDependency3.delegateToWidgetRun = true;
                    ((ArrayList) dimensionDependency3.mDependencies).add(this.start);
                    ((ArrayList) this.mDimension.mDependencies).add(this.end);
                }
            } else if (i == 3 && !constraintWidget9.isInVerticalChain()) {
                ConstraintWidget constraintWidget11 = this.mWidget;
                if (constraintWidget11.mMatchConstraintDefaultWidth != 3) {
                    DimensionDependency dimensionDependency4 = constraintWidget11.mHorizontalRun.mDimension;
                    ((ArrayList) this.mDimension.mTargets).add(dimensionDependency4);
                    ((ArrayList) dimensionDependency4.mDependencies).add(this.mDimension);
                    DimensionDependency dimensionDependency5 = this.mDimension;
                    dimensionDependency5.delegateToWidgetRun = true;
                    ((ArrayList) dimensionDependency5.mDependencies).add(this.start);
                    ((ArrayList) this.mDimension.mDependencies).add(this.end);
                }
            }
        }
        ConstraintWidget constraintWidget12 = this.mWidget;
        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget12.mListAnchors;
        ConstraintAnchor constraintAnchor5 = constraintAnchorArr2[2];
        ConstraintAnchor constraintAnchor6 = constraintAnchor5.mTarget;
        if (constraintAnchor6 != null && constraintAnchorArr2[3].mTarget != null) {
            if (constraintWidget12.isInVerticalChain()) {
                this.start.mMargin = this.mWidget.mListAnchors[2].getMargin();
                this.end.mMargin = -this.mWidget.mListAnchors[3].getMargin();
            } else {
                DependencyNode target6 = WidgetRun.getTarget(this.mWidget.mListAnchors[2]);
                DependencyNode target7 = WidgetRun.getTarget(this.mWidget.mListAnchors[3]);
                if (target6 != null) {
                    target6.addDependency(this);
                }
                if (target7 != null) {
                    target7.addDependency(this);
                }
                this.mRunType = WidgetRun.RunType.CENTER;
            }
            if (this.mWidget.hasBaseline()) {
                addTarget(this.baseline, this.start, 1, this.mBaselineDimension);
            }
        } else if (constraintAnchor6 != null) {
            DependencyNode target8 = WidgetRun.getTarget(constraintAnchor5);
            if (target8 != null) {
                WidgetRun.addTarget(this.start, target8, this.mWidget.mListAnchors[2].getMargin());
                addTarget(this.end, this.start, 1, this.mDimension);
                if (this.mWidget.hasBaseline()) {
                    addTarget(this.baseline, this.start, 1, this.mBaselineDimension);
                }
                if (this.mDimensionBehavior == dimensionBehaviour3) {
                    ConstraintWidget constraintWidget13 = this.mWidget;
                    if (constraintWidget13.mDimensionRatio > 0.0f) {
                        HorizontalWidgetRun horizontalWidgetRun = constraintWidget13.mHorizontalRun;
                        if (horizontalWidgetRun.mDimensionBehavior == dimensionBehaviour3) {
                            ((ArrayList) horizontalWidgetRun.mDimension.mDependencies).add(this.mDimension);
                            ((ArrayList) this.mDimension.mTargets).add(this.mWidget.mHorizontalRun.mDimension);
                            this.mDimension.updateDelegate = this;
                        }
                    }
                }
            }
        } else {
            ConstraintAnchor constraintAnchor7 = constraintAnchorArr2[3];
            if (constraintAnchor7.mTarget != null) {
                DependencyNode target9 = WidgetRun.getTarget(constraintAnchor7);
                if (target9 != null) {
                    WidgetRun.addTarget(this.end, target9, -this.mWidget.mListAnchors[3].getMargin());
                    addTarget(this.start, this.end, -1, this.mDimension);
                    if (this.mWidget.hasBaseline()) {
                        addTarget(this.baseline, this.start, 1, this.mBaselineDimension);
                    }
                }
            } else {
                ConstraintAnchor constraintAnchor8 = constraintAnchorArr2[4];
                if (constraintAnchor8.mTarget != null) {
                    DependencyNode target10 = WidgetRun.getTarget(constraintAnchor8);
                    if (target10 != null) {
                        WidgetRun.addTarget(this.baseline, target10, 0);
                        addTarget(this.start, this.baseline, -1, this.mBaselineDimension);
                        addTarget(this.end, this.start, 1, this.mDimension);
                    }
                } else if (!(constraintWidget12 instanceof Helper) && (constraintWidget3 = constraintWidget12.mParent) != null) {
                    WidgetRun.addTarget(this.start, constraintWidget3.mVerticalRun.start, constraintWidget12.getY());
                    addTarget(this.end, this.start, 1, this.mDimension);
                    if (this.mWidget.hasBaseline()) {
                        addTarget(this.baseline, this.start, 1, this.mBaselineDimension);
                    }
                    if (this.mDimensionBehavior == dimensionBehaviour3) {
                        ConstraintWidget constraintWidget14 = this.mWidget;
                        if (constraintWidget14.mDimensionRatio > 0.0f) {
                            HorizontalWidgetRun horizontalWidgetRun2 = constraintWidget14.mHorizontalRun;
                            if (horizontalWidgetRun2.mDimensionBehavior == dimensionBehaviour3) {
                                ((ArrayList) horizontalWidgetRun2.mDimension.mDependencies).add(this.mDimension);
                                ((ArrayList) this.mDimension.mTargets).add(this.mWidget.mHorizontalRun.mDimension);
                                this.mDimension.updateDelegate = this;
                            }
                        }
                    }
                }
            }
        }
        if (((ArrayList) this.mDimension.mTargets).size() == 0) {
            this.mDimension.readyToSolve = true;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.mWidget.setY(dependencyNode.value);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    final void clear() {
        this.mRunGroup = null;
        this.start.clear();
        this.end.clear();
        this.baseline.clear();
        this.mDimension.clear();
        this.mResolved = false;
    }

    final void reset() {
        this.mResolved = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.baseline.clear();
        this.baseline.resolved = false;
        this.mDimension.resolved = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    final boolean supportsWrapComputation() {
        return this.mDimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.mWidget.mMatchConstraintDefaultHeight == 0;
    }

    public final String toString() {
        return "VerticalRun " + this.mWidget.getDebugName();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public final void update(Dependency dependency) {
        float f;
        float f2;
        float f3;
        int i;
        if (this.mRunType.ordinal() == 3) {
            ConstraintWidget constraintWidget = this.mWidget;
            updateRunCenter(constraintWidget.mTop, constraintWidget.mBottom, 1);
            return;
        }
        DimensionDependency dimensionDependency = this.mDimension;
        boolean z = dimensionDependency.readyToSolve;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (z && !dimensionDependency.resolved && this.mDimensionBehavior == dimensionBehaviour) {
            ConstraintWidget constraintWidget2 = this.mWidget;
            int i2 = constraintWidget2.mMatchConstraintDefaultHeight;
            if (i2 == 2) {
                ConstraintWidget constraintWidget3 = constraintWidget2.mParent;
                if (constraintWidget3 != null) {
                    if (constraintWidget3.mVerticalRun.mDimension.resolved) {
                        dimensionDependency.resolve((int) ((r1.value * constraintWidget2.mMatchConstraintPercentHeight) + 0.5f));
                    }
                }
            } else if (i2 == 3 && constraintWidget2.mHorizontalRun.mDimension.resolved) {
                int dimensionRatioSide = constraintWidget2.getDimensionRatioSide();
                if (dimensionRatioSide == -1) {
                    ConstraintWidget constraintWidget4 = this.mWidget;
                    f = constraintWidget4.mHorizontalRun.mDimension.value;
                    f2 = constraintWidget4.mDimensionRatio;
                } else if (dimensionRatioSide == 0) {
                    f3 = r9.mHorizontalRun.mDimension.value * this.mWidget.mDimensionRatio;
                    i = (int) (f3 + 0.5f);
                    this.mDimension.resolve(i);
                } else if (dimensionRatioSide != 1) {
                    i = 0;
                    this.mDimension.resolve(i);
                } else {
                    ConstraintWidget constraintWidget5 = this.mWidget;
                    f = constraintWidget5.mHorizontalRun.mDimension.value;
                    f2 = constraintWidget5.mDimensionRatio;
                }
                f3 = f / f2;
                i = (int) (f3 + 0.5f);
                this.mDimension.resolve(i);
            }
        }
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.readyToSolve) {
            DependencyNode dependencyNode2 = this.end;
            if (dependencyNode2.readyToSolve) {
                if (dependencyNode.resolved && dependencyNode2.resolved && this.mDimension.resolved) {
                    return;
                }
                if (!this.mDimension.resolved && this.mDimensionBehavior == dimensionBehaviour) {
                    ConstraintWidget constraintWidget6 = this.mWidget;
                    if (constraintWidget6.mMatchConstraintDefaultWidth == 0 && !constraintWidget6.isInVerticalChain()) {
                        DependencyNode dependencyNode3 = (DependencyNode) ((ArrayList) this.start.mTargets).get(0);
                        DependencyNode dependencyNode4 = (DependencyNode) ((ArrayList) this.end.mTargets).get(0);
                        int i3 = dependencyNode3.value;
                        DependencyNode dependencyNode5 = this.start;
                        int i4 = i3 + dependencyNode5.mMargin;
                        int i5 = dependencyNode4.value + this.end.mMargin;
                        dependencyNode5.resolve(i4);
                        this.end.resolve(i5);
                        this.mDimension.resolve(i5 - i4);
                        return;
                    }
                }
                if (!this.mDimension.resolved && this.mDimensionBehavior == dimensionBehaviour && this.matchConstraintsType == 1 && ((ArrayList) this.start.mTargets).size() > 0 && ((ArrayList) this.end.mTargets).size() > 0) {
                    DependencyNode dependencyNode6 = (DependencyNode) ((ArrayList) this.start.mTargets).get(0);
                    int i6 = (((DependencyNode) ((ArrayList) this.end.mTargets).get(0)).value + this.end.mMargin) - (dependencyNode6.value + this.start.mMargin);
                    DimensionDependency dimensionDependency2 = this.mDimension;
                    int i7 = dimensionDependency2.wrapValue;
                    if (i6 < i7) {
                        dimensionDependency2.resolve(i6);
                    } else {
                        dimensionDependency2.resolve(i7);
                    }
                }
                if (this.mDimension.resolved && ((ArrayList) this.start.mTargets).size() > 0 && ((ArrayList) this.end.mTargets).size() > 0) {
                    DependencyNode dependencyNode7 = (DependencyNode) ((ArrayList) this.start.mTargets).get(0);
                    DependencyNode dependencyNode8 = (DependencyNode) ((ArrayList) this.end.mTargets).get(0);
                    int i8 = dependencyNode7.value + this.start.mMargin;
                    int i9 = dependencyNode8.value + this.end.mMargin;
                    float verticalBiasPercent = this.mWidget.getVerticalBiasPercent();
                    if (dependencyNode7 == dependencyNode8) {
                        i8 = dependencyNode7.value;
                        i9 = dependencyNode8.value;
                        verticalBiasPercent = 0.5f;
                    }
                    this.start.resolve((int) ((((i9 - i8) - this.mDimension.value) * verticalBiasPercent) + i8 + 0.5f));
                    this.end.resolve(this.start.value + this.mDimension.value);
                }
            }
        }
    }
}
