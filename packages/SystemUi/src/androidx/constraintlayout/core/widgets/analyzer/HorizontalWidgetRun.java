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
public final class HorizontalWidgetRun extends WidgetRun {
    public static final int[] tempDimensions = new int[2];

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun$1 */
    public abstract /* synthetic */ class AbstractC01191 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType */
        public static final /* synthetic */ int[] f20x56910102;

        static {
            int[] iArr = new int[WidgetRun.RunType.values().length];
            f20x56910102 = iArr;
            try {
                iArr[WidgetRun.RunType.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f20x56910102[WidgetRun.RunType.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f20x56910102[WidgetRun.RunType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public HorizontalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.type = DependencyNode.Type.LEFT;
        this.end.type = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }

    public static void computeInsetRatio(int[] iArr, int i, int i2, int i3, int i4, float f, int i5) {
        int i6 = i2 - i;
        int i7 = i4 - i3;
        if (i5 != -1) {
            if (i5 == 0) {
                iArr[0] = (int) ((i7 * f) + 0.5f);
                iArr[1] = i7;
                return;
            } else {
                if (i5 != 1) {
                    return;
                }
                iArr[0] = i6;
                iArr[1] = (int) ((i6 * f) + 0.5f);
                return;
            }
        }
        int i8 = (int) ((i7 * f) + 0.5f);
        int i9 = (int) ((i6 / f) + 0.5f);
        if (i8 <= i6) {
            iArr[0] = i8;
            iArr[1] = i7;
        } else if (i9 <= i7) {
            iArr[0] = i6;
            iArr[1] = i9;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void apply() {
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        ConstraintWidget constraintWidget3;
        ConstraintWidget constraintWidget4;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        ConstraintWidget constraintWidget5 = this.widget;
        boolean z = constraintWidget5.measured;
        DimensionDependency dimensionDependency = this.dimension;
        if (z) {
            dimensionDependency.resolve(constraintWidget5.getWidth());
        }
        boolean z2 = dimensionDependency.resolved;
        DependencyNode dependencyNode = this.end;
        DependencyNode dependencyNode2 = this.start;
        if (z2) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = this.dimensionBehavior;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
            if (dimensionBehaviour3 == dimensionBehaviour4 && (constraintWidget2 = (constraintWidget = this.widget).mParent) != null && ((dimensionBehaviour = constraintWidget2.mListDimensionBehaviors[0]) == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour == dimensionBehaviour4)) {
                WidgetRun.addTarget(dependencyNode2, constraintWidget2.horizontalRun.start, constraintWidget.mLeft.getMargin());
                WidgetRun.addTarget(dependencyNode, constraintWidget2.horizontalRun.end, -this.widget.mRight.getMargin());
                return;
            }
        } else {
            ConstraintWidget constraintWidget6 = this.widget;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = constraintWidget6.mListDimensionBehaviors[0];
            this.dimensionBehavior = dimensionBehaviour5;
            if (dimensionBehaviour5 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
                if (dimensionBehaviour5 == dimensionBehaviour6 && (constraintWidget4 = constraintWidget6.mParent) != null && ((dimensionBehaviour2 = constraintWidget4.mListDimensionBehaviors[0]) == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour2 == dimensionBehaviour6)) {
                    int width = (constraintWidget4.getWidth() - this.widget.mLeft.getMargin()) - this.widget.mRight.getMargin();
                    WidgetRun.addTarget(dependencyNode2, constraintWidget4.horizontalRun.start, this.widget.mLeft.getMargin());
                    WidgetRun.addTarget(dependencyNode, constraintWidget4.horizontalRun.end, -this.widget.mRight.getMargin());
                    dimensionDependency.resolve(width);
                    return;
                }
                if (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.FIXED) {
                    dimensionDependency.resolve(constraintWidget6.getWidth());
                }
            }
        }
        if (dimensionDependency.resolved) {
            ConstraintWidget constraintWidget7 = this.widget;
            if (constraintWidget7.measured) {
                ConstraintAnchor[] constraintAnchorArr = constraintWidget7.mListAnchors;
                ConstraintAnchor constraintAnchor = constraintAnchorArr[0];
                ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
                if (constraintAnchor2 != null && constraintAnchorArr[1].mTarget != null) {
                    if (constraintWidget7.isInHorizontalChain()) {
                        dependencyNode2.margin = this.widget.mListAnchors[0].getMargin();
                        dependencyNode.margin = -this.widget.mListAnchors[1].getMargin();
                        return;
                    }
                    DependencyNode target = WidgetRun.getTarget(this.widget.mListAnchors[0]);
                    if (target != null) {
                        WidgetRun.addTarget(dependencyNode2, target, this.widget.mListAnchors[0].getMargin());
                    }
                    DependencyNode target2 = WidgetRun.getTarget(this.widget.mListAnchors[1]);
                    if (target2 != null) {
                        WidgetRun.addTarget(dependencyNode, target2, -this.widget.mListAnchors[1].getMargin());
                    }
                    dependencyNode2.delegateToWidgetRun = true;
                    dependencyNode.delegateToWidgetRun = true;
                    return;
                }
                if (constraintAnchor2 != null) {
                    DependencyNode target3 = WidgetRun.getTarget(constraintAnchor);
                    if (target3 != null) {
                        WidgetRun.addTarget(dependencyNode2, target3, this.widget.mListAnchors[0].getMargin());
                        WidgetRun.addTarget(dependencyNode, dependencyNode2, dimensionDependency.value);
                        return;
                    }
                    return;
                }
                ConstraintAnchor constraintAnchor3 = constraintAnchorArr[1];
                if (constraintAnchor3.mTarget != null) {
                    DependencyNode target4 = WidgetRun.getTarget(constraintAnchor3);
                    if (target4 != null) {
                        WidgetRun.addTarget(dependencyNode, target4, -this.widget.mListAnchors[1].getMargin());
                        WidgetRun.addTarget(dependencyNode2, dependencyNode, -dimensionDependency.value);
                        return;
                    }
                    return;
                }
                if ((constraintWidget7 instanceof Helper) || constraintWidget7.mParent == null || constraintWidget7.getAnchor(ConstraintAnchor.Type.CENTER).mTarget != null) {
                    return;
                }
                ConstraintWidget constraintWidget8 = this.widget;
                WidgetRun.addTarget(dependencyNode2, constraintWidget8.mParent.horizontalRun.start, constraintWidget8.getX());
                WidgetRun.addTarget(dependencyNode, dependencyNode2, dimensionDependency.value);
                return;
            }
        }
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget9 = this.widget;
            int i = constraintWidget9.mMatchConstraintDefaultWidth;
            List list = dimensionDependency.dependencies;
            List list2 = dimensionDependency.targets;
            if (i == 2) {
                ConstraintWidget constraintWidget10 = constraintWidget9.mParent;
                if (constraintWidget10 != null) {
                    DimensionDependency dimensionDependency2 = constraintWidget10.verticalRun.dimension;
                    ((ArrayList) list2).add(dimensionDependency2);
                    ((ArrayList) dimensionDependency2.dependencies).add(dimensionDependency);
                    dimensionDependency.delegateToWidgetRun = true;
                    ((ArrayList) list).add(dependencyNode2);
                    ((ArrayList) list).add(dependencyNode);
                }
            } else if (i == 3) {
                if (constraintWidget9.mMatchConstraintDefaultHeight == 3) {
                    dependencyNode2.updateDelegate = this;
                    dependencyNode.updateDelegate = this;
                    VerticalWidgetRun verticalWidgetRun = constraintWidget9.verticalRun;
                    verticalWidgetRun.start.updateDelegate = this;
                    verticalWidgetRun.end.updateDelegate = this;
                    dimensionDependency.updateDelegate = this;
                    if (constraintWidget9.isInVerticalChain()) {
                        ((ArrayList) list2).add(this.widget.verticalRun.dimension);
                        ((ArrayList) this.widget.verticalRun.dimension.dependencies).add(dimensionDependency);
                        VerticalWidgetRun verticalWidgetRun2 = this.widget.verticalRun;
                        verticalWidgetRun2.dimension.updateDelegate = this;
                        ((ArrayList) list2).add(verticalWidgetRun2.start);
                        ((ArrayList) list2).add(this.widget.verticalRun.end);
                        ((ArrayList) this.widget.verticalRun.start.dependencies).add(dimensionDependency);
                        ((ArrayList) this.widget.verticalRun.end.dependencies).add(dimensionDependency);
                    } else if (this.widget.isInHorizontalChain()) {
                        ((ArrayList) this.widget.verticalRun.dimension.targets).add(dimensionDependency);
                        ((ArrayList) list).add(this.widget.verticalRun.dimension);
                    } else {
                        ((ArrayList) this.widget.verticalRun.dimension.targets).add(dimensionDependency);
                    }
                } else {
                    DimensionDependency dimensionDependency3 = constraintWidget9.verticalRun.dimension;
                    ((ArrayList) list2).add(dimensionDependency3);
                    ((ArrayList) dimensionDependency3.dependencies).add(dimensionDependency);
                    ((ArrayList) this.widget.verticalRun.start.dependencies).add(dimensionDependency);
                    ((ArrayList) this.widget.verticalRun.end.dependencies).add(dimensionDependency);
                    dimensionDependency.delegateToWidgetRun = true;
                    ((ArrayList) list).add(dependencyNode2);
                    ((ArrayList) list).add(dependencyNode);
                    ((ArrayList) dependencyNode2.targets).add(dimensionDependency);
                    ((ArrayList) dependencyNode.targets).add(dimensionDependency);
                }
            }
        }
        ConstraintWidget constraintWidget11 = this.widget;
        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget11.mListAnchors;
        ConstraintAnchor constraintAnchor4 = constraintAnchorArr2[0];
        ConstraintAnchor constraintAnchor5 = constraintAnchor4.mTarget;
        if (constraintAnchor5 != null && constraintAnchorArr2[1].mTarget != null) {
            if (constraintWidget11.isInHorizontalChain()) {
                dependencyNode2.margin = this.widget.mListAnchors[0].getMargin();
                dependencyNode.margin = -this.widget.mListAnchors[1].getMargin();
                return;
            }
            DependencyNode target5 = WidgetRun.getTarget(this.widget.mListAnchors[0]);
            DependencyNode target6 = WidgetRun.getTarget(this.widget.mListAnchors[1]);
            if (target5 != null) {
                target5.addDependency(this);
            }
            if (target6 != null) {
                target6.addDependency(this);
            }
            this.mRunType = WidgetRun.RunType.CENTER;
            return;
        }
        if (constraintAnchor5 != null) {
            DependencyNode target7 = WidgetRun.getTarget(constraintAnchor4);
            if (target7 != null) {
                WidgetRun.addTarget(dependencyNode2, target7, this.widget.mListAnchors[0].getMargin());
                addTarget(dependencyNode, dependencyNode2, 1, dimensionDependency);
                return;
            }
            return;
        }
        ConstraintAnchor constraintAnchor6 = constraintAnchorArr2[1];
        if (constraintAnchor6.mTarget != null) {
            DependencyNode target8 = WidgetRun.getTarget(constraintAnchor6);
            if (target8 != null) {
                WidgetRun.addTarget(dependencyNode, target8, -this.widget.mListAnchors[1].getMargin());
                addTarget(dependencyNode2, dependencyNode, -1, dimensionDependency);
                return;
            }
            return;
        }
        if ((constraintWidget11 instanceof Helper) || (constraintWidget3 = constraintWidget11.mParent) == null) {
            return;
        }
        WidgetRun.addTarget(dependencyNode2, constraintWidget3.horizontalRun.start, constraintWidget11.getX());
        addTarget(dependencyNode, dependencyNode2, 1, dimensionDependency);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.widget.f15mX = dependencyNode.value;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
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
        this.dimension.resolved = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final boolean supportsWrapComputation() {
        return this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultWidth == 0;
    }

    public final String toString() {
        return "HorizontalRun " + this.widget.mDebugName;
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x0261, code lost:
    
        if (r15 != 1) goto L128;
     */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(Dependency dependency) {
        float f;
        float f2;
        float f3;
        int i;
        if (AbstractC01191.f20x56910102[this.mRunType.ordinal()] == 3) {
            ConstraintWidget constraintWidget = this.widget;
            updateRunCenter(constraintWidget.mLeft, constraintWidget.mRight, 0);
            return;
        }
        DimensionDependency dimensionDependency = this.dimension;
        boolean z = dimensionDependency.resolved;
        DependencyNode dependencyNode = this.start;
        DependencyNode dependencyNode2 = this.end;
        if (!z && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget2 = this.widget;
            int i2 = constraintWidget2.mMatchConstraintDefaultWidth;
            if (i2 == 2) {
                ConstraintWidget constraintWidget3 = constraintWidget2.mParent;
                if (constraintWidget3 != null) {
                    if (constraintWidget3.horizontalRun.dimension.resolved) {
                        dimensionDependency.resolve((int) ((r3.value * constraintWidget2.mMatchConstraintPercentWidth) + 0.5f));
                    }
                }
            } else if (i2 == 3) {
                int i3 = constraintWidget2.mMatchConstraintDefaultHeight;
                if (i3 == 0 || i3 == 3) {
                    VerticalWidgetRun verticalWidgetRun = constraintWidget2.verticalRun;
                    DependencyNode dependencyNode3 = verticalWidgetRun.start;
                    DependencyNode dependencyNode4 = verticalWidgetRun.end;
                    boolean z2 = constraintWidget2.mLeft.mTarget != null;
                    boolean z3 = constraintWidget2.mTop.mTarget != null;
                    boolean z4 = constraintWidget2.mRight.mTarget != null;
                    boolean z5 = constraintWidget2.mBottom.mTarget != null;
                    int i4 = constraintWidget2.mDimensionRatioSide;
                    if (z2 && z3 && z4 && z5) {
                        float f4 = constraintWidget2.mDimensionRatio;
                        boolean z6 = dependencyNode3.resolved;
                        int[] iArr = tempDimensions;
                        if (z6 && dependencyNode4.resolved) {
                            if (dependencyNode.readyToSolve && dependencyNode2.readyToSolve) {
                                computeInsetRatio(iArr, ((DependencyNode) ((ArrayList) dependencyNode.targets).get(0)).value + dependencyNode.margin, ((DependencyNode) ((ArrayList) dependencyNode2.targets).get(0)).value - dependencyNode2.margin, dependencyNode3.value + dependencyNode3.margin, dependencyNode4.value - dependencyNode4.margin, f4, i4);
                                dimensionDependency.resolve(iArr[0]);
                                this.widget.verticalRun.dimension.resolve(iArr[1]);
                                return;
                            }
                            return;
                        }
                        boolean z7 = dependencyNode.resolved;
                        List list = dependencyNode3.targets;
                        if (z7 && dependencyNode2.resolved) {
                            if (!dependencyNode3.readyToSolve || !dependencyNode4.readyToSolve) {
                                return;
                            }
                            computeInsetRatio(iArr, dependencyNode.value + dependencyNode.margin, dependencyNode2.value - dependencyNode2.margin, ((DependencyNode) ((ArrayList) list).get(0)).value + dependencyNode3.margin, ((DependencyNode) ((ArrayList) dependencyNode4.targets).get(0)).value - dependencyNode4.margin, f4, i4);
                            dimensionDependency.resolve(iArr[0]);
                            this.widget.verticalRun.dimension.resolve(iArr[1]);
                        }
                        if (!dependencyNode.readyToSolve || !dependencyNode2.readyToSolve || !dependencyNode3.readyToSolve || !dependencyNode4.readyToSolve) {
                            return;
                        }
                        computeInsetRatio(iArr, ((DependencyNode) ((ArrayList) dependencyNode.targets).get(0)).value + dependencyNode.margin, ((DependencyNode) ((ArrayList) dependencyNode2.targets).get(0)).value - dependencyNode2.margin, ((DependencyNode) ((ArrayList) list).get(0)).value + dependencyNode3.margin, ((DependencyNode) ((ArrayList) dependencyNode4.targets).get(0)).value - dependencyNode4.margin, f4, i4);
                        dimensionDependency.resolve(iArr[0]);
                        this.widget.verticalRun.dimension.resolve(iArr[1]);
                    } else if (z2 && z4) {
                        if (!dependencyNode.readyToSolve || !dependencyNode2.readyToSolve) {
                            return;
                        }
                        float f5 = constraintWidget2.mDimensionRatio;
                        int i5 = ((DependencyNode) ((ArrayList) dependencyNode.targets).get(0)).value + dependencyNode.margin;
                        int i6 = ((DependencyNode) ((ArrayList) dependencyNode2.targets).get(0)).value - dependencyNode2.margin;
                        if (i4 == -1 || i4 == 0) {
                            int limitedDimension = getLimitedDimension(i6 - i5, 0);
                            int i7 = (int) ((limitedDimension * f5) + 0.5f);
                            int limitedDimension2 = getLimitedDimension(i7, 1);
                            if (i7 != limitedDimension2) {
                                limitedDimension = (int) ((limitedDimension2 / f5) + 0.5f);
                            }
                            dimensionDependency.resolve(limitedDimension);
                            this.widget.verticalRun.dimension.resolve(limitedDimension2);
                        } else if (i4 == 1) {
                            int limitedDimension3 = getLimitedDimension(i6 - i5, 0);
                            int i8 = (int) ((limitedDimension3 / f5) + 0.5f);
                            int limitedDimension4 = getLimitedDimension(i8, 1);
                            if (i8 != limitedDimension4) {
                                limitedDimension3 = (int) ((limitedDimension4 * f5) + 0.5f);
                            }
                            dimensionDependency.resolve(limitedDimension3);
                            this.widget.verticalRun.dimension.resolve(limitedDimension4);
                        }
                    } else if (z3 && z5) {
                        if (!dependencyNode3.readyToSolve || !dependencyNode4.readyToSolve) {
                            return;
                        }
                        float f6 = constraintWidget2.mDimensionRatio;
                        int i9 = ((DependencyNode) ((ArrayList) dependencyNode3.targets).get(0)).value + dependencyNode3.margin;
                        int i10 = ((DependencyNode) ((ArrayList) dependencyNode4.targets).get(0)).value - dependencyNode4.margin;
                        if (i4 != -1) {
                            if (i4 == 0) {
                                int limitedDimension5 = getLimitedDimension(i10 - i9, 1);
                                int i11 = (int) ((limitedDimension5 * f6) + 0.5f);
                                int limitedDimension6 = getLimitedDimension(i11, 0);
                                if (i11 != limitedDimension6) {
                                    limitedDimension5 = (int) ((limitedDimension6 / f6) + 0.5f);
                                }
                                dimensionDependency.resolve(limitedDimension6);
                                this.widget.verticalRun.dimension.resolve(limitedDimension5);
                            }
                        }
                        int limitedDimension7 = getLimitedDimension(i10 - i9, 1);
                        int i12 = (int) ((limitedDimension7 / f6) + 0.5f);
                        int limitedDimension8 = getLimitedDimension(i12, 0);
                        if (i12 != limitedDimension8) {
                            limitedDimension7 = (int) ((limitedDimension8 * f6) + 0.5f);
                        }
                        dimensionDependency.resolve(limitedDimension8);
                        this.widget.verticalRun.dimension.resolve(limitedDimension7);
                    }
                } else {
                    int i13 = constraintWidget2.mDimensionRatioSide;
                    if (i13 == -1) {
                        f = constraintWidget2.verticalRun.dimension.value;
                        f2 = constraintWidget2.mDimensionRatio;
                    } else if (i13 == 0) {
                        f3 = constraintWidget2.verticalRun.dimension.value / constraintWidget2.mDimensionRatio;
                        i = (int) (f3 + 0.5f);
                        dimensionDependency.resolve(i);
                    } else if (i13 != 1) {
                        i = 0;
                        dimensionDependency.resolve(i);
                    } else {
                        f = constraintWidget2.verticalRun.dimension.value;
                        f2 = constraintWidget2.mDimensionRatio;
                    }
                    f3 = f * f2;
                    i = (int) (f3 + 0.5f);
                    dimensionDependency.resolve(i);
                }
            }
        }
        if (dependencyNode.readyToSolve && dependencyNode2.readyToSolve) {
            if (dependencyNode.resolved && dependencyNode2.resolved && dimensionDependency.resolved) {
                return;
            }
            boolean z8 = dimensionDependency.resolved;
            List list2 = dependencyNode.targets;
            List list3 = dependencyNode2.targets;
            if (!z8 && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                ConstraintWidget constraintWidget4 = this.widget;
                if (constraintWidget4.mMatchConstraintDefaultWidth == 0 && !constraintWidget4.isInHorizontalChain()) {
                    DependencyNode dependencyNode5 = (DependencyNode) ((ArrayList) list2).get(0);
                    DependencyNode dependencyNode6 = (DependencyNode) ((ArrayList) list3).get(0);
                    int i14 = dependencyNode5.value + dependencyNode.margin;
                    int i15 = dependencyNode6.value + dependencyNode2.margin;
                    dependencyNode.resolve(i14);
                    dependencyNode2.resolve(i15);
                    dimensionDependency.resolve(i15 - i14);
                    return;
                }
            }
            if (!dimensionDependency.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && ((ArrayList) list2).size() > 0 && ((ArrayList) list3).size() > 0) {
                int min = Math.min((((DependencyNode) ((ArrayList) list3).get(0)).value + dependencyNode2.margin) - (((DependencyNode) ((ArrayList) list2).get(0)).value + dependencyNode.margin), dimensionDependency.wrapValue);
                ConstraintWidget constraintWidget5 = this.widget;
                int i16 = constraintWidget5.mMatchConstraintMaxWidth;
                int max = Math.max(constraintWidget5.mMatchConstraintMinWidth, min);
                if (i16 > 0) {
                    max = Math.min(i16, max);
                }
                dimensionDependency.resolve(max);
            }
            if (dimensionDependency.resolved) {
                DependencyNode dependencyNode7 = (DependencyNode) ((ArrayList) list2).get(0);
                DependencyNode dependencyNode8 = (DependencyNode) ((ArrayList) list3).get(0);
                int i17 = dependencyNode7.value;
                int i18 = dependencyNode.margin + i17;
                int i19 = dependencyNode8.value;
                int i20 = dependencyNode2.margin + i19;
                float f7 = this.widget.mHorizontalBiasPercent;
                if (dependencyNode7 == dependencyNode8) {
                    f7 = 0.5f;
                } else {
                    i17 = i18;
                    i19 = i20;
                }
                dependencyNode.resolve((int) ((((i19 - i17) - dimensionDependency.value) * f7) + i17 + 0.5f));
                dependencyNode2.resolve(dependencyNode.value + dimensionDependency.value);
            }
        }
    }
}
