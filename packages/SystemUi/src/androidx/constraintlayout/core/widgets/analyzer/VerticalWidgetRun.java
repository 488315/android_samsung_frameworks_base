package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class VerticalWidgetRun extends WidgetRun {
    public final DependencyNode baseline;
    public BaselineDimensionDependency baselineDimension;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun$1 */
    public abstract /* synthetic */ class AbstractC01201 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType */
        public static final /* synthetic */ int[] f21x56910102;

        static {
            int[] iArr = new int[WidgetRun.RunType.values().length];
            f21x56910102 = iArr;
            try {
                iArr[WidgetRun.RunType.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f21x56910102[WidgetRun.RunType.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f21x56910102[WidgetRun.RunType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public VerticalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        DependencyNode dependencyNode = new DependencyNode(this);
        this.baseline = dependencyNode;
        this.baselineDimension = null;
        this.start.type = DependencyNode.Type.TOP;
        this.end.type = DependencyNode.Type.BOTTOM;
        dependencyNode.type = DependencyNode.Type.BASELINE;
        this.orientation = 1;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void apply() {
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        ConstraintWidget constraintWidget3;
        ConstraintWidget constraintWidget4;
        ConstraintWidget constraintWidget5 = this.widget;
        boolean z = constraintWidget5.measured;
        DimensionDependency dimensionDependency = this.dimension;
        if (z) {
            dimensionDependency.resolve(constraintWidget5.getHeight());
        }
        boolean z2 = dimensionDependency.resolved;
        DependencyNode dependencyNode = this.end;
        DependencyNode dependencyNode2 = this.start;
        if (!z2) {
            ConstraintWidget constraintWidget6 = this.widget;
            this.dimensionBehavior = constraintWidget6.mListDimensionBehaviors[1];
            if (constraintWidget6.hasBaseline) {
                this.baselineDimension = new BaselineDimensionDependency(this);
            }
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.dimensionBehavior;
            if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (constraintWidget4 = this.widget.mParent) != null && constraintWidget4.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED) {
                    int height = (constraintWidget4.getHeight() - this.widget.mTop.getMargin()) - this.widget.mBottom.getMargin();
                    WidgetRun.addTarget(dependencyNode2, constraintWidget4.verticalRun.start, this.widget.mTop.getMargin());
                    WidgetRun.addTarget(dependencyNode, constraintWidget4.verticalRun.end, -this.widget.mBottom.getMargin());
                    dimensionDependency.resolve(height);
                    return;
                }
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
                    dimensionDependency.resolve(this.widget.getHeight());
                }
            }
        } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (constraintWidget2 = (constraintWidget = this.widget).mParent) != null && constraintWidget2.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED) {
            WidgetRun.addTarget(dependencyNode2, constraintWidget2.verticalRun.start, constraintWidget.mTop.getMargin());
            WidgetRun.addTarget(dependencyNode, constraintWidget2.verticalRun.end, -this.widget.mBottom.getMargin());
            return;
        }
        boolean z3 = dimensionDependency.resolved;
        DependencyNode dependencyNode3 = this.baseline;
        if (z3) {
            ConstraintWidget constraintWidget7 = this.widget;
            if (constraintWidget7.measured) {
                ConstraintAnchor[] constraintAnchorArr = constraintWidget7.mListAnchors;
                ConstraintAnchor constraintAnchor = constraintAnchorArr[2];
                ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
                if (constraintAnchor2 != null && constraintAnchorArr[3].mTarget != null) {
                    if (constraintWidget7.isInVerticalChain()) {
                        dependencyNode2.margin = this.widget.mListAnchors[2].getMargin();
                        dependencyNode.margin = -this.widget.mListAnchors[3].getMargin();
                    } else {
                        DependencyNode target = WidgetRun.getTarget(this.widget.mListAnchors[2]);
                        if (target != null) {
                            WidgetRun.addTarget(dependencyNode2, target, this.widget.mListAnchors[2].getMargin());
                        }
                        DependencyNode target2 = WidgetRun.getTarget(this.widget.mListAnchors[3]);
                        if (target2 != null) {
                            WidgetRun.addTarget(dependencyNode, target2, -this.widget.mListAnchors[3].getMargin());
                        }
                        dependencyNode2.delegateToWidgetRun = true;
                        dependencyNode.delegateToWidgetRun = true;
                    }
                    ConstraintWidget constraintWidget8 = this.widget;
                    if (constraintWidget8.hasBaseline) {
                        WidgetRun.addTarget(dependencyNode3, dependencyNode2, constraintWidget8.mBaselineDistance);
                        return;
                    }
                    return;
                }
                if (constraintAnchor2 != null) {
                    DependencyNode target3 = WidgetRun.getTarget(constraintAnchor);
                    if (target3 != null) {
                        WidgetRun.addTarget(dependencyNode2, target3, this.widget.mListAnchors[2].getMargin());
                        WidgetRun.addTarget(dependencyNode, dependencyNode2, dimensionDependency.value);
                        ConstraintWidget constraintWidget9 = this.widget;
                        if (constraintWidget9.hasBaseline) {
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
                        WidgetRun.addTarget(dependencyNode, target4, -this.widget.mListAnchors[3].getMargin());
                        WidgetRun.addTarget(dependencyNode2, dependencyNode, -dimensionDependency.value);
                    }
                    ConstraintWidget constraintWidget10 = this.widget;
                    if (constraintWidget10.hasBaseline) {
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
                        WidgetRun.addTarget(dependencyNode2, dependencyNode3, -this.widget.mBaselineDistance);
                        WidgetRun.addTarget(dependencyNode, dependencyNode2, dimensionDependency.value);
                        return;
                    }
                    return;
                }
                if ((constraintWidget7 instanceof Helper) || constraintWidget7.mParent == null || constraintWidget7.getAnchor(ConstraintAnchor.Type.CENTER).mTarget != null) {
                    return;
                }
                ConstraintWidget constraintWidget11 = this.widget;
                WidgetRun.addTarget(dependencyNode2, constraintWidget11.mParent.verticalRun.start, constraintWidget11.getY());
                WidgetRun.addTarget(dependencyNode, dependencyNode2, dimensionDependency.value);
                ConstraintWidget constraintWidget12 = this.widget;
                if (constraintWidget12.hasBaseline) {
                    WidgetRun.addTarget(dependencyNode3, dependencyNode2, constraintWidget12.mBaselineDistance);
                    return;
                }
                return;
            }
        }
        List list = dimensionDependency.targets;
        if (z3 || this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            dimensionDependency.addDependency(this);
        } else {
            ConstraintWidget constraintWidget13 = this.widget;
            int i = constraintWidget13.mMatchConstraintDefaultHeight;
            List list2 = dimensionDependency.dependencies;
            if (i == 2) {
                ConstraintWidget constraintWidget14 = constraintWidget13.mParent;
                if (constraintWidget14 != null) {
                    DimensionDependency dimensionDependency2 = constraintWidget14.verticalRun.dimension;
                    ((ArrayList) list).add(dimensionDependency2);
                    ((ArrayList) dimensionDependency2.dependencies).add(dimensionDependency);
                    dimensionDependency.delegateToWidgetRun = true;
                    ((ArrayList) list2).add(dependencyNode2);
                    ((ArrayList) list2).add(dependencyNode);
                }
            } else if (i == 3 && !constraintWidget13.isInVerticalChain()) {
                ConstraintWidget constraintWidget15 = this.widget;
                if (constraintWidget15.mMatchConstraintDefaultWidth != 3) {
                    DimensionDependency dimensionDependency3 = constraintWidget15.horizontalRun.dimension;
                    ((ArrayList) list).add(dimensionDependency3);
                    ((ArrayList) dimensionDependency3.dependencies).add(dimensionDependency);
                    dimensionDependency.delegateToWidgetRun = true;
                    ((ArrayList) list2).add(dependencyNode2);
                    ((ArrayList) list2).add(dependencyNode);
                }
            }
        }
        ConstraintWidget constraintWidget16 = this.widget;
        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget16.mListAnchors;
        ConstraintAnchor constraintAnchor5 = constraintAnchorArr2[2];
        ConstraintAnchor constraintAnchor6 = constraintAnchor5.mTarget;
        if (constraintAnchor6 != null && constraintAnchorArr2[3].mTarget != null) {
            if (constraintWidget16.isInVerticalChain()) {
                dependencyNode2.margin = this.widget.mListAnchors[2].getMargin();
                dependencyNode.margin = -this.widget.mListAnchors[3].getMargin();
            } else {
                DependencyNode target6 = WidgetRun.getTarget(this.widget.mListAnchors[2]);
                DependencyNode target7 = WidgetRun.getTarget(this.widget.mListAnchors[3]);
                if (target6 != null) {
                    target6.addDependency(this);
                }
                if (target7 != null) {
                    target7.addDependency(this);
                }
                this.mRunType = WidgetRun.RunType.CENTER;
            }
            if (this.widget.hasBaseline) {
                addTarget(dependencyNode3, dependencyNode2, 1, this.baselineDimension);
            }
        } else if (constraintAnchor6 != null) {
            DependencyNode target8 = WidgetRun.getTarget(constraintAnchor5);
            if (target8 != null) {
                WidgetRun.addTarget(dependencyNode2, target8, this.widget.mListAnchors[2].getMargin());
                addTarget(dependencyNode, dependencyNode2, 1, dimensionDependency);
                if (this.widget.hasBaseline) {
                    addTarget(dependencyNode3, dependencyNode2, 1, this.baselineDimension);
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.dimensionBehavior;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour2 == dimensionBehaviour3) {
                    ConstraintWidget constraintWidget17 = this.widget;
                    if (constraintWidget17.mDimensionRatio > 0.0f) {
                        HorizontalWidgetRun horizontalWidgetRun = constraintWidget17.horizontalRun;
                        if (horizontalWidgetRun.dimensionBehavior == dimensionBehaviour3) {
                            ((ArrayList) horizontalWidgetRun.dimension.dependencies).add(dimensionDependency);
                            ((ArrayList) list).add(this.widget.horizontalRun.dimension);
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
                    WidgetRun.addTarget(dependencyNode, target9, -this.widget.mListAnchors[3].getMargin());
                    addTarget(dependencyNode2, dependencyNode, -1, dimensionDependency);
                    if (this.widget.hasBaseline) {
                        addTarget(dependencyNode3, dependencyNode2, 1, this.baselineDimension);
                    }
                }
            } else {
                ConstraintAnchor constraintAnchor8 = constraintAnchorArr2[4];
                if (constraintAnchor8.mTarget != null) {
                    DependencyNode target10 = WidgetRun.getTarget(constraintAnchor8);
                    if (target10 != null) {
                        WidgetRun.addTarget(dependencyNode3, target10, 0);
                        addTarget(dependencyNode2, dependencyNode3, -1, this.baselineDimension);
                        addTarget(dependencyNode, dependencyNode2, 1, dimensionDependency);
                    }
                } else if (!(constraintWidget16 instanceof Helper) && (constraintWidget3 = constraintWidget16.mParent) != null) {
                    WidgetRun.addTarget(dependencyNode2, constraintWidget3.verticalRun.start, constraintWidget16.getY());
                    addTarget(dependencyNode, dependencyNode2, 1, dimensionDependency);
                    if (this.widget.hasBaseline) {
                        addTarget(dependencyNode3, dependencyNode2, 1, this.baselineDimension);
                    }
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = this.dimensionBehavior;
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                    if (dimensionBehaviour4 == dimensionBehaviour5) {
                        ConstraintWidget constraintWidget18 = this.widget;
                        if (constraintWidget18.mDimensionRatio > 0.0f) {
                            HorizontalWidgetRun horizontalWidgetRun2 = constraintWidget18.horizontalRun;
                            if (horizontalWidgetRun2.dimensionBehavior == dimensionBehaviour5) {
                                ((ArrayList) horizontalWidgetRun2.dimension.dependencies).add(dimensionDependency);
                                ((ArrayList) list).add(this.widget.horizontalRun.dimension);
                                dimensionDependency.updateDelegate = this;
                            }
                        }
                    }
                }
            }
        }
        if (((ArrayList) list).size() == 0) {
            dimensionDependency.readyToSolve = true;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.widget.f16mY = dependencyNode.value;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
        this.baseline.clear();
        this.dimension.clear();
        this.resolved = false;
    }

    public final void reset() {
        this.resolved = false;
        DependencyNode dependencyNode = this.start;
        dependencyNode.clear();
        dependencyNode.resolved = false;
        DependencyNode dependencyNode2 = this.end;
        dependencyNode2.clear();
        dependencyNode2.resolved = false;
        DependencyNode dependencyNode3 = this.baseline;
        dependencyNode3.clear();
        dependencyNode3.resolved = false;
        this.dimension.resolved = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final boolean supportsWrapComputation() {
        return this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultHeight == 0;
    }

    public final String toString() {
        return "VerticalRun " + this.widget.mDebugName;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public final void update(Dependency dependency) {
        float f;
        float f2;
        float f3;
        int i;
        if (AbstractC01201.f21x56910102[this.mRunType.ordinal()] == 3) {
            ConstraintWidget constraintWidget = this.widget;
            updateRunCenter(constraintWidget.mTop, constraintWidget.mBottom, 1);
            return;
        }
        DimensionDependency dimensionDependency = this.dimension;
        if (dimensionDependency.readyToSolve && !dimensionDependency.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget2 = this.widget;
            int i2 = constraintWidget2.mMatchConstraintDefaultHeight;
            if (i2 == 2) {
                ConstraintWidget constraintWidget3 = constraintWidget2.mParent;
                if (constraintWidget3 != null) {
                    if (constraintWidget3.verticalRun.dimension.resolved) {
                        dimensionDependency.resolve((int) ((r1.value * constraintWidget2.mMatchConstraintPercentHeight) + 0.5f));
                    }
                }
            } else if (i2 == 3) {
                DimensionDependency dimensionDependency2 = constraintWidget2.horizontalRun.dimension;
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
                boolean z = dimensionDependency.resolved;
                List list = dependencyNode.targets;
                List list2 = dependencyNode2.targets;
                if (!z && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    ConstraintWidget constraintWidget4 = this.widget;
                    if (constraintWidget4.mMatchConstraintDefaultWidth == 0 && !constraintWidget4.isInVerticalChain()) {
                        DependencyNode dependencyNode3 = (DependencyNode) ((ArrayList) list).get(0);
                        DependencyNode dependencyNode4 = (DependencyNode) ((ArrayList) list2).get(0);
                        int i4 = dependencyNode3.value + dependencyNode.margin;
                        int i5 = dependencyNode4.value + dependencyNode2.margin;
                        dependencyNode.resolve(i4);
                        dependencyNode2.resolve(i5);
                        dimensionDependency.resolve(i5 - i4);
                        return;
                    }
                }
                if (!dimensionDependency.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && ((ArrayList) list).size() > 0 && ((ArrayList) list2).size() > 0) {
                    DependencyNode dependencyNode5 = (DependencyNode) ((ArrayList) list).get(0);
                    int i6 = (((DependencyNode) ((ArrayList) list2).get(0)).value + dependencyNode2.margin) - (dependencyNode5.value + dependencyNode.margin);
                    int i7 = dimensionDependency.wrapValue;
                    if (i6 < i7) {
                        dimensionDependency.resolve(i6);
                    } else {
                        dimensionDependency.resolve(i7);
                    }
                }
                if (dimensionDependency.resolved && ((ArrayList) list).size() > 0 && ((ArrayList) list2).size() > 0) {
                    DependencyNode dependencyNode6 = (DependencyNode) ((ArrayList) list).get(0);
                    DependencyNode dependencyNode7 = (DependencyNode) ((ArrayList) list2).get(0);
                    int i8 = dependencyNode6.value;
                    int i9 = dependencyNode.margin + i8;
                    int i10 = dependencyNode7.value;
                    int i11 = dependencyNode2.margin + i10;
                    float f4 = this.widget.mVerticalBiasPercent;
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
