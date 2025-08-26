package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class VerticalWidgetRun extends WidgetRun {
    public final DependencyNode baseline;
    public BaselineDimensionDependency mBaselineDimension;

    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType;

        static {
            int[] iArr = new int[WidgetRun.RunType.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType = iArr;
            try {
                iArr[WidgetRun.RunType.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

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
    public final void apply() {
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        ConstraintWidget constraintWidget3;
        ConstraintWidget constraintWidget4;
        ConstraintWidget constraintWidget5 = this.mWidget;
        boolean z = constraintWidget5.measured;
        DimensionDependency dimensionDependency = this.mDimension;
        if (z) {
            dimensionDependency.resolve(constraintWidget5.getHeight());
        }
        boolean z2 = dimensionDependency.resolved;
        DependencyNode dependencyNode = this.end;
        DependencyNode dependencyNode2 = this.start;
        if (!z2) {
            ConstraintWidget constraintWidget6 = this.mWidget;
            this.mDimensionBehavior = constraintWidget6.mListDimensionBehaviors[1];
            if (constraintWidget6.mHasBaseline) {
                this.mBaselineDimension = new BaselineDimensionDependency(this);
            }
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.mDimensionBehavior;
            if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (constraintWidget4 = this.mWidget.mParent) != null && constraintWidget4.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED) {
                    int height = (constraintWidget4.getHeight() - this.mWidget.mTop.getMargin()) - this.mWidget.mBottom.getMargin();
                    WidgetRun.addTarget(dependencyNode2, constraintWidget4.mVerticalRun.start, this.mWidget.mTop.getMargin());
                    WidgetRun.addTarget(dependencyNode, constraintWidget4.mVerticalRun.end, -this.mWidget.mBottom.getMargin());
                    dimensionDependency.resolve(height);
                    return;
                }
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
                    dimensionDependency.resolve(this.mWidget.getHeight());
                }
            }
        } else if (this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (constraintWidget2 = (constraintWidget = this.mWidget).mParent) != null && constraintWidget2.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED) {
            WidgetRun.addTarget(dependencyNode2, constraintWidget2.mVerticalRun.start, constraintWidget.mTop.getMargin());
            WidgetRun.addTarget(dependencyNode, constraintWidget2.mVerticalRun.end, -this.mWidget.mBottom.getMargin());
            return;
        }
        boolean z3 = dimensionDependency.resolved;
        DependencyNode dependencyNode3 = this.baseline;
        if (z3) {
            ConstraintWidget constraintWidget7 = this.mWidget;
            if (constraintWidget7.measured) {
                ConstraintAnchor[] constraintAnchorArr = constraintWidget7.mListAnchors;
                ConstraintAnchor constraintAnchor = constraintAnchorArr[2];
                ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
                if (constraintAnchor2 != null && constraintAnchorArr[3].mTarget != null) {
                    if (constraintWidget7.isInVerticalChain()) {
                        dependencyNode2.mMargin = this.mWidget.mListAnchors[2].getMargin();
                        dependencyNode.mMargin = -this.mWidget.mListAnchors[3].getMargin();
                    } else {
                        DependencyNode target = WidgetRun.getTarget(this.mWidget.mListAnchors[2]);
                        if (target != null) {
                            WidgetRun.addTarget(dependencyNode2, target, this.mWidget.mListAnchors[2].getMargin());
                        }
                        DependencyNode target2 = WidgetRun.getTarget(this.mWidget.mListAnchors[3]);
                        if (target2 != null) {
                            WidgetRun.addTarget(dependencyNode, target2, -this.mWidget.mListAnchors[3].getMargin());
                        }
                        dependencyNode2.delegateToWidgetRun = true;
                        dependencyNode.delegateToWidgetRun = true;
                    }
                    ConstraintWidget constraintWidget8 = this.mWidget;
                    if (constraintWidget8.mHasBaseline) {
                        WidgetRun.addTarget(dependencyNode3, dependencyNode2, constraintWidget8.mBaselineDistance);
                        return;
                    }
                    return;
                }
                if (constraintAnchor2 != null) {
                    DependencyNode target3 = WidgetRun.getTarget(constraintAnchor);
                    if (target3 != null) {
                        WidgetRun.addTarget(dependencyNode2, target3, this.mWidget.mListAnchors[2].getMargin());
                        WidgetRun.addTarget(dependencyNode, dependencyNode2, dimensionDependency.value);
                        ConstraintWidget constraintWidget9 = this.mWidget;
                        if (constraintWidget9.mHasBaseline) {
                            WidgetRun.addTarget(dependencyNode3, dependencyNode2, constraintWidget9.mBaselineDistance);
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
                        WidgetRun.addTarget(dependencyNode, target4, -this.mWidget.mListAnchors[3].getMargin());
                        WidgetRun.addTarget(dependencyNode2, dependencyNode, -dimensionDependency.value);
                    }
                    ConstraintWidget constraintWidget10 = this.mWidget;
                    if (constraintWidget10.mHasBaseline) {
                        WidgetRun.addTarget(dependencyNode3, dependencyNode2, constraintWidget10.mBaselineDistance);
                        return;
                    }
                    return;
                }
                ConstraintAnchor constraintAnchor4 = constraintAnchorArr[4];
                if (constraintAnchor4.mTarget != null) {
                    DependencyNode target5 = WidgetRun.getTarget(constraintAnchor4);
                    if (target5 != null) {
                        WidgetRun.addTarget(dependencyNode3, target5, 0);
                        WidgetRun.addTarget(dependencyNode2, dependencyNode3, -this.mWidget.mBaselineDistance);
                        WidgetRun.addTarget(dependencyNode, dependencyNode2, dimensionDependency.value);
                        return;
                    }
                    return;
                }
                if ((constraintWidget7 instanceof Helper) || constraintWidget7.mParent == null || constraintWidget7.getAnchor(ConstraintAnchor.Type.CENTER).mTarget != null) {
                    return;
                }
                ConstraintWidget constraintWidget11 = this.mWidget;
                WidgetRun.addTarget(dependencyNode2, constraintWidget11.mParent.mVerticalRun.start, constraintWidget11.getY());
                WidgetRun.addTarget(dependencyNode, dependencyNode2, dimensionDependency.value);
                ConstraintWidget constraintWidget12 = this.mWidget;
                if (constraintWidget12.mHasBaseline) {
                    WidgetRun.addTarget(dependencyNode3, dependencyNode2, constraintWidget12.mBaselineDistance);
                    return;
                }
                return;
            }
        }
        if (z3 || this.mDimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            dimensionDependency.addDependency(this);
        } else {
            ConstraintWidget constraintWidget13 = this.mWidget;
            int i = constraintWidget13.mMatchConstraintDefaultHeight;
            if (i == 2) {
                ConstraintWidget constraintWidget14 = constraintWidget13.mParent;
                if (constraintWidget14 != null) {
                    DimensionDependency dimensionDependency2 = constraintWidget14.mVerticalRun.mDimension;
                    ((ArrayList) dimensionDependency.mTargets).add(dimensionDependency2);
                    ((ArrayList) dimensionDependency2.mDependencies).add(dimensionDependency);
                    dimensionDependency.delegateToWidgetRun = true;
                    ((ArrayList) dimensionDependency.mDependencies).add(dependencyNode2);
                    ((ArrayList) dimensionDependency.mDependencies).add(dependencyNode);
                }
            } else if (i == 3 && !constraintWidget13.isInVerticalChain()) {
                ConstraintWidget constraintWidget15 = this.mWidget;
                if (constraintWidget15.mMatchConstraintDefaultWidth != 3) {
                    DimensionDependency dimensionDependency3 = constraintWidget15.mHorizontalRun.mDimension;
                    ((ArrayList) dimensionDependency.mTargets).add(dimensionDependency3);
                    ((ArrayList) dimensionDependency3.mDependencies).add(dimensionDependency);
                    dimensionDependency.delegateToWidgetRun = true;
                    ((ArrayList) dimensionDependency.mDependencies).add(dependencyNode2);
                    ((ArrayList) dimensionDependency.mDependencies).add(dependencyNode);
                }
            }
        }
        ConstraintWidget constraintWidget16 = this.mWidget;
        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget16.mListAnchors;
        ConstraintAnchor constraintAnchor5 = constraintAnchorArr2[2];
        ConstraintAnchor constraintAnchor6 = constraintAnchor5.mTarget;
        if (constraintAnchor6 != null && constraintAnchorArr2[3].mTarget != null) {
            if (constraintWidget16.isInVerticalChain()) {
                dependencyNode2.mMargin = this.mWidget.mListAnchors[2].getMargin();
                dependencyNode.mMargin = -this.mWidget.mListAnchors[3].getMargin();
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
            if (this.mWidget.mHasBaseline) {
                addTarget(dependencyNode3, dependencyNode2, 1, this.mBaselineDimension);
            }
        } else if (constraintAnchor6 != null) {
            DependencyNode target8 = WidgetRun.getTarget(constraintAnchor5);
            if (target8 != null) {
                WidgetRun.addTarget(dependencyNode2, target8, this.mWidget.mListAnchors[2].getMargin());
                addTarget(dependencyNode, dependencyNode2, 1, dimensionDependency);
                if (this.mWidget.mHasBaseline) {
                    addTarget(dependencyNode3, dependencyNode2, 1, this.mBaselineDimension);
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.mDimensionBehavior;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour2 == dimensionBehaviour3) {
                    ConstraintWidget constraintWidget17 = this.mWidget;
                    if (constraintWidget17.mDimensionRatio > 0.0f) {
                        HorizontalWidgetRun horizontalWidgetRun = constraintWidget17.mHorizontalRun;
                        if (horizontalWidgetRun.mDimensionBehavior == dimensionBehaviour3) {
                            ((ArrayList) horizontalWidgetRun.mDimension.mDependencies).add(dimensionDependency);
                            ((ArrayList) dimensionDependency.mTargets).add(this.mWidget.mHorizontalRun.mDimension);
                            dimensionDependency.updateDelegate = this;
                        }
                    }
                }
            }
        } else {
            ConstraintAnchor constraintAnchor7 = constraintAnchorArr2[3];
            if (constraintAnchor7.mTarget != null) {
                DependencyNode target9 = WidgetRun.getTarget(constraintAnchor7);
                if (target9 != null) {
                    WidgetRun.addTarget(dependencyNode, target9, -this.mWidget.mListAnchors[3].getMargin());
                    addTarget(dependencyNode2, dependencyNode, -1, dimensionDependency);
                    if (this.mWidget.mHasBaseline) {
                        addTarget(dependencyNode3, dependencyNode2, 1, this.mBaselineDimension);
                    }
                }
            } else {
                ConstraintAnchor constraintAnchor8 = constraintAnchorArr2[4];
                if (constraintAnchor8.mTarget != null) {
                    DependencyNode target10 = WidgetRun.getTarget(constraintAnchor8);
                    if (target10 != null) {
                        WidgetRun.addTarget(dependencyNode3, target10, 0);
                        addTarget(dependencyNode2, dependencyNode3, -1, this.mBaselineDimension);
                        addTarget(dependencyNode, dependencyNode2, 1, dimensionDependency);
                    }
                } else if (!(constraintWidget16 instanceof Helper) && (constraintWidget3 = constraintWidget16.mParent) != null) {
                    WidgetRun.addTarget(dependencyNode2, constraintWidget3.mVerticalRun.start, constraintWidget16.getY());
                    addTarget(dependencyNode, dependencyNode2, 1, dimensionDependency);
                    if (this.mWidget.mHasBaseline) {
                        addTarget(dependencyNode3, dependencyNode2, 1, this.mBaselineDimension);
                    }
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = this.mDimensionBehavior;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    if (dimensionBehaviour4 == dimensionBehaviour5) {
                        ConstraintWidget constraintWidget18 = this.mWidget;
                        if (constraintWidget18.mDimensionRatio > 0.0f) {
                            HorizontalWidgetRun horizontalWidgetRun2 = constraintWidget18.mHorizontalRun;
                            if (horizontalWidgetRun2.mDimensionBehavior == dimensionBehaviour5) {
                                ((ArrayList) horizontalWidgetRun2.mDimension.mDependencies).add(dimensionDependency);
                                ((ArrayList) dimensionDependency.mTargets).add(this.mWidget.mHorizontalRun.mDimension);
                                dimensionDependency.updateDelegate = this;
                            }
                        }
                    }
                }
            }
        }
        if (((ArrayList) dimensionDependency.mTargets).size() == 0) {
            dimensionDependency.readyToSolve = true;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.mWidget.mY = dependencyNode.value;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void clear() {
        this.mRunGroup = null;
        this.start.clear();
        this.end.clear();
        this.baseline.clear();
        this.mDimension.clear();
        this.mResolved = false;
    }

    public final void reset() {
        this.mResolved = false;
        DependencyNode dependencyNode = this.start;
        dependencyNode.clear();
        dependencyNode.resolved = false;
        DependencyNode dependencyNode2 = this.end;
        dependencyNode2.clear();
        dependencyNode2.resolved = false;
        DependencyNode dependencyNode3 = this.baseline;
        dependencyNode3.clear();
        dependencyNode3.resolved = false;
        this.mDimension.resolved = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final boolean supportsWrapComputation() {
        return this.mDimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.mWidget.mMatchConstraintDefaultHeight == 0;
    }

    public final String toString() {
        return "VerticalRun " + this.mWidget.mDebugName;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public final void update(Dependency dependency) {
        float f;
        float f2;
        float f3;
        int i;
        if (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()] == 3) {
            ConstraintWidget constraintWidget = this.mWidget;
            updateRunCenter(constraintWidget.mTop, constraintWidget.mBottom, 1);
            return;
        }
        DimensionDependency dimensionDependency = this.mDimension;
        if (dimensionDependency.readyToSolve && !dimensionDependency.resolved && this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget2 = this.mWidget;
            int i2 = constraintWidget2.mMatchConstraintDefaultHeight;
            if (i2 == 2) {
                ConstraintWidget constraintWidget3 = constraintWidget2.mParent;
                if (constraintWidget3 != null) {
                    if (constraintWidget3.mVerticalRun.mDimension.resolved) {
                        dimensionDependency.resolve((int) ((r1.value * constraintWidget2.mMatchConstraintPercentHeight) + 0.5f));
                    }
                }
            } else if (i2 == 3) {
                DimensionDependency dimensionDependency2 = constraintWidget2.mHorizontalRun.mDimension;
                if (dimensionDependency2.resolved) {
                    int i3 = constraintWidget2.mDimensionRatioSide;
                    if (i3 == -1) {
                        f = dimensionDependency2.value;
                        f2 = constraintWidget2.mDimensionRatio;
                    } else if (i3 == 0) {
                        f3 = dimensionDependency2.value * constraintWidget2.mDimensionRatio;
                        i = (int) (f3 + 0.5f);
                        dimensionDependency.resolve(i);
                    } else if (i3 != 1) {
                        i = 0;
                        dimensionDependency.resolve(i);
                    } else {
                        f = dimensionDependency2.value;
                        f2 = constraintWidget2.mDimensionRatio;
                    }
                    f3 = f / f2;
                    i = (int) (f3 + 0.5f);
                    dimensionDependency.resolve(i);
                }
            }
        }
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.readyToSolve) {
            DependencyNode dependencyNode2 = this.end;
            if (dependencyNode2.readyToSolve) {
                if (dependencyNode.resolved && dependencyNode2.resolved && dimensionDependency.resolved) {
                    return;
                }
                if (!dimensionDependency.resolved && this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    ConstraintWidget constraintWidget4 = this.mWidget;
                    if (constraintWidget4.mMatchConstraintDefaultWidth == 0 && !constraintWidget4.isInVerticalChain()) {
                        DependencyNode dependencyNode3 = (DependencyNode) ((ArrayList) dependencyNode.mTargets).get(0);
                        DependencyNode dependencyNode4 = (DependencyNode) ((ArrayList) dependencyNode2.mTargets).get(0);
                        int i4 = dependencyNode3.value + dependencyNode.mMargin;
                        int i5 = dependencyNode4.value + dependencyNode2.mMargin;
                        dependencyNode.resolve(i4);
                        dependencyNode2.resolve(i5);
                        dimensionDependency.resolve(i5 - i4);
                        return;
                    }
                }
                if (!dimensionDependency.resolved && this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && ((ArrayList) dependencyNode.mTargets).size() > 0 && ((ArrayList) dependencyNode2.mTargets).size() > 0) {
                    DependencyNode dependencyNode5 = (DependencyNode) ((ArrayList) dependencyNode.mTargets).get(0);
                    int i6 = (((DependencyNode) ((ArrayList) dependencyNode2.mTargets).get(0)).value + dependencyNode2.mMargin) - (dependencyNode5.value + dependencyNode.mMargin);
                    int i7 = dimensionDependency.wrapValue;
                    if (i6 < i7) {
                        dimensionDependency.resolve(i6);
                    } else {
                        dimensionDependency.resolve(i7);
                    }
                }
                if (dimensionDependency.resolved && ((ArrayList) dependencyNode.mTargets).size() > 0 && ((ArrayList) dependencyNode2.mTargets).size() > 0) {
                    DependencyNode dependencyNode6 = (DependencyNode) ((ArrayList) dependencyNode.mTargets).get(0);
                    DependencyNode dependencyNode7 = (DependencyNode) ((ArrayList) dependencyNode2.mTargets).get(0);
                    int i8 = dependencyNode6.value;
                    int i9 = dependencyNode.mMargin + i8;
                    int i10 = dependencyNode7.value;
                    int i11 = dependencyNode2.mMargin + i10;
                    float f4 = this.mWidget.mVerticalBiasPercent;
                    if (dependencyNode6 == dependencyNode7) {
                        f4 = 0.5f;
                    } else {
                        i8 = i9;
                        i10 = i11;
                    }
                    dependencyNode.resolve((int) ((((i10 - i8) - dimensionDependency.value) * f4) + i8 + 0.5f));
                    dependencyNode2.resolve(dependencyNode.value + dimensionDependency.value);
                }
            }
        }
    }
}
