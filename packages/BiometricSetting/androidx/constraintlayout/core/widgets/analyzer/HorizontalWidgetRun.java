package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class HorizontalWidgetRun extends WidgetRun {
    private static int[] sTempDimensions = new int[2];

    public HorizontalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.mType = DependencyNode.Type.LEFT;
        this.end.mType = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }

    private static void computeInsetRatio(int[] iArr, int i, int i2, int i3, int i4, float f, int i5) {
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
    final void apply() {
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        ConstraintWidget constraintWidget3;
        ConstraintWidget constraintWidget4;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        ConstraintWidget constraintWidget5 = this.mWidget;
        if (constraintWidget5.measured) {
            this.mDimension.resolve(constraintWidget5.getWidth());
        }
        DimensionDependency dimensionDependency = this.mDimension;
        boolean z = dimensionDependency.resolved;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.FIXED;
        if (!z) {
            ConstraintWidget constraintWidget6 = this.mWidget;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = constraintWidget6.mListDimensionBehaviors[0];
            this.mDimensionBehavior = dimensionBehaviour6;
            if (dimensionBehaviour6 != dimensionBehaviour3) {
                if (dimensionBehaviour6 == dimensionBehaviour4 && (constraintWidget4 = constraintWidget6.mParent) != null && ((dimensionBehaviour2 = constraintWidget4.mListDimensionBehaviors[0]) == dimensionBehaviour5 || dimensionBehaviour2 == dimensionBehaviour4)) {
                    int width = (constraintWidget4.getWidth() - this.mWidget.mLeft.getMargin()) - this.mWidget.mRight.getMargin();
                    WidgetRun.addTarget(this.start, constraintWidget4.mHorizontalRun.start, this.mWidget.mLeft.getMargin());
                    WidgetRun.addTarget(this.end, constraintWidget4.mHorizontalRun.end, -this.mWidget.mRight.getMargin());
                    this.mDimension.resolve(width);
                    return;
                }
                if (dimensionBehaviour6 == dimensionBehaviour5) {
                    dimensionDependency.resolve(constraintWidget6.getWidth());
                }
            }
        } else if (this.mDimensionBehavior == dimensionBehaviour4 && (constraintWidget2 = (constraintWidget = this.mWidget).mParent) != null && ((dimensionBehaviour = constraintWidget2.mListDimensionBehaviors[0]) == dimensionBehaviour5 || dimensionBehaviour == dimensionBehaviour4)) {
            WidgetRun.addTarget(this.start, constraintWidget2.mHorizontalRun.start, constraintWidget.mLeft.getMargin());
            WidgetRun.addTarget(this.end, constraintWidget2.mHorizontalRun.end, -this.mWidget.mRight.getMargin());
            return;
        }
        DimensionDependency dimensionDependency2 = this.mDimension;
        if (dimensionDependency2.resolved) {
            ConstraintWidget constraintWidget7 = this.mWidget;
            if (constraintWidget7.measured) {
                ConstraintAnchor[] constraintAnchorArr = constraintWidget7.mListAnchors;
                ConstraintAnchor constraintAnchor = constraintAnchorArr[0];
                ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
                if (constraintAnchor2 != null && constraintAnchorArr[1].mTarget != null) {
                    if (constraintWidget7.isInHorizontalChain()) {
                        this.start.mMargin = this.mWidget.mListAnchors[0].getMargin();
                        this.end.mMargin = -this.mWidget.mListAnchors[1].getMargin();
                        return;
                    }
                    DependencyNode target = WidgetRun.getTarget(this.mWidget.mListAnchors[0]);
                    if (target != null) {
                        WidgetRun.addTarget(this.start, target, this.mWidget.mListAnchors[0].getMargin());
                    }
                    DependencyNode target2 = WidgetRun.getTarget(this.mWidget.mListAnchors[1]);
                    if (target2 != null) {
                        WidgetRun.addTarget(this.end, target2, -this.mWidget.mListAnchors[1].getMargin());
                    }
                    this.start.delegateToWidgetRun = true;
                    this.end.delegateToWidgetRun = true;
                    return;
                }
                if (constraintAnchor2 != null) {
                    DependencyNode target3 = WidgetRun.getTarget(constraintAnchor);
                    if (target3 != null) {
                        WidgetRun.addTarget(this.start, target3, this.mWidget.mListAnchors[0].getMargin());
                        WidgetRun.addTarget(this.end, this.start, this.mDimension.value);
                        return;
                    }
                    return;
                }
                ConstraintAnchor constraintAnchor3 = constraintAnchorArr[1];
                if (constraintAnchor3.mTarget != null) {
                    DependencyNode target4 = WidgetRun.getTarget(constraintAnchor3);
                    if (target4 != null) {
                        WidgetRun.addTarget(this.end, target4, -this.mWidget.mListAnchors[1].getMargin());
                        WidgetRun.addTarget(this.start, this.end, -this.mDimension.value);
                        return;
                    }
                    return;
                }
                if ((constraintWidget7 instanceof Helper) || constraintWidget7.mParent == null || constraintWidget7.getAnchor(ConstraintAnchor.Type.CENTER).mTarget != null) {
                    return;
                }
                ConstraintWidget constraintWidget8 = this.mWidget;
                WidgetRun.addTarget(this.start, constraintWidget8.mParent.mHorizontalRun.start, constraintWidget8.getX());
                WidgetRun.addTarget(this.end, this.start, this.mDimension.value);
                return;
            }
        }
        if (this.mDimensionBehavior == dimensionBehaviour3) {
            ConstraintWidget constraintWidget9 = this.mWidget;
            int i = constraintWidget9.mMatchConstraintDefaultWidth;
            if (i == 2) {
                ConstraintWidget constraintWidget10 = constraintWidget9.mParent;
                if (constraintWidget10 != null) {
                    DimensionDependency dimensionDependency3 = constraintWidget10.mVerticalRun.mDimension;
                    ((ArrayList) dimensionDependency2.mTargets).add(dimensionDependency3);
                    ((ArrayList) dimensionDependency3.mDependencies).add(this.mDimension);
                    DimensionDependency dimensionDependency4 = this.mDimension;
                    dimensionDependency4.delegateToWidgetRun = true;
                    ((ArrayList) dimensionDependency4.mDependencies).add(this.start);
                    ((ArrayList) this.mDimension.mDependencies).add(this.end);
                }
            } else if (i == 3) {
                if (constraintWidget9.mMatchConstraintDefaultHeight == 3) {
                    this.start.updateDelegate = this;
                    this.end.updateDelegate = this;
                    VerticalWidgetRun verticalWidgetRun = constraintWidget9.mVerticalRun;
                    verticalWidgetRun.start.updateDelegate = this;
                    verticalWidgetRun.end.updateDelegate = this;
                    dimensionDependency2.updateDelegate = this;
                    if (constraintWidget9.isInVerticalChain()) {
                        ((ArrayList) this.mDimension.mTargets).add(this.mWidget.mVerticalRun.mDimension);
                        ((ArrayList) this.mWidget.mVerticalRun.mDimension.mDependencies).add(this.mDimension);
                        VerticalWidgetRun verticalWidgetRun2 = this.mWidget.mVerticalRun;
                        verticalWidgetRun2.mDimension.updateDelegate = this;
                        ((ArrayList) this.mDimension.mTargets).add(verticalWidgetRun2.start);
                        ((ArrayList) this.mDimension.mTargets).add(this.mWidget.mVerticalRun.end);
                        ((ArrayList) this.mWidget.mVerticalRun.start.mDependencies).add(this.mDimension);
                        ((ArrayList) this.mWidget.mVerticalRun.end.mDependencies).add(this.mDimension);
                    } else if (this.mWidget.isInHorizontalChain()) {
                        ((ArrayList) this.mWidget.mVerticalRun.mDimension.mTargets).add(this.mDimension);
                        ((ArrayList) this.mDimension.mDependencies).add(this.mWidget.mVerticalRun.mDimension);
                    } else {
                        ((ArrayList) this.mWidget.mVerticalRun.mDimension.mTargets).add(this.mDimension);
                    }
                } else {
                    DimensionDependency dimensionDependency5 = constraintWidget9.mVerticalRun.mDimension;
                    ((ArrayList) dimensionDependency2.mTargets).add(dimensionDependency5);
                    ((ArrayList) dimensionDependency5.mDependencies).add(this.mDimension);
                    ((ArrayList) this.mWidget.mVerticalRun.start.mDependencies).add(this.mDimension);
                    ((ArrayList) this.mWidget.mVerticalRun.end.mDependencies).add(this.mDimension);
                    DimensionDependency dimensionDependency6 = this.mDimension;
                    dimensionDependency6.delegateToWidgetRun = true;
                    ((ArrayList) dimensionDependency6.mDependencies).add(this.start);
                    ((ArrayList) this.mDimension.mDependencies).add(this.end);
                    ((ArrayList) this.start.mTargets).add(this.mDimension);
                    ((ArrayList) this.end.mTargets).add(this.mDimension);
                }
            }
        }
        ConstraintWidget constraintWidget11 = this.mWidget;
        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget11.mListAnchors;
        ConstraintAnchor constraintAnchor4 = constraintAnchorArr2[0];
        ConstraintAnchor constraintAnchor5 = constraintAnchor4.mTarget;
        if (constraintAnchor5 != null && constraintAnchorArr2[1].mTarget != null) {
            if (constraintWidget11.isInHorizontalChain()) {
                this.start.mMargin = this.mWidget.mListAnchors[0].getMargin();
                this.end.mMargin = -this.mWidget.mListAnchors[1].getMargin();
                return;
            }
            DependencyNode target5 = WidgetRun.getTarget(this.mWidget.mListAnchors[0]);
            DependencyNode target6 = WidgetRun.getTarget(this.mWidget.mListAnchors[1]);
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
                WidgetRun.addTarget(this.start, target7, this.mWidget.mListAnchors[0].getMargin());
                addTarget(this.end, this.start, 1, this.mDimension);
                return;
            }
            return;
        }
        ConstraintAnchor constraintAnchor6 = constraintAnchorArr2[1];
        if (constraintAnchor6.mTarget != null) {
            DependencyNode target8 = WidgetRun.getTarget(constraintAnchor6);
            if (target8 != null) {
                WidgetRun.addTarget(this.end, target8, -this.mWidget.mListAnchors[1].getMargin());
                addTarget(this.start, this.end, -1, this.mDimension);
                return;
            }
            return;
        }
        if ((constraintWidget11 instanceof Helper) || (constraintWidget3 = constraintWidget11.mParent) == null) {
            return;
        }
        WidgetRun.addTarget(this.start, constraintWidget3.mHorizontalRun.start, constraintWidget11.getX());
        addTarget(this.end, this.start, 1, this.mDimension);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.mWidget.setX(dependencyNode.value);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    final void clear() {
        this.mRunGroup = null;
        this.start.clear();
        this.end.clear();
        this.mDimension.clear();
        this.mResolved = false;
    }

    final void reset() {
        this.mResolved = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.mDimension.resolved = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    final boolean supportsWrapComputation() {
        return this.mDimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.mWidget.mMatchConstraintDefaultWidth == 0;
    }

    public final String toString() {
        return "HorizontalRun " + this.mWidget.getDebugName();
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x0297, code lost:
    
        if (r4 != 1) goto L128;
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
        if (this.mRunType.ordinal() == 3) {
            ConstraintWidget constraintWidget = this.mWidget;
            updateRunCenter(constraintWidget.mLeft, constraintWidget.mRight, 0);
            return;
        }
        DimensionDependency dimensionDependency = this.mDimension;
        boolean z = dimensionDependency.resolved;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (!z && this.mDimensionBehavior == dimensionBehaviour) {
            ConstraintWidget constraintWidget2 = this.mWidget;
            int i2 = constraintWidget2.mMatchConstraintDefaultWidth;
            if (i2 == 2) {
                ConstraintWidget constraintWidget3 = constraintWidget2.mParent;
                if (constraintWidget3 != null) {
                    if (constraintWidget3.mHorizontalRun.mDimension.resolved) {
                        dimensionDependency.resolve((int) ((r3.value * constraintWidget2.mMatchConstraintPercentWidth) + 0.5f));
                    }
                }
            } else if (i2 == 3) {
                int i3 = constraintWidget2.mMatchConstraintDefaultHeight;
                if (i3 == 0 || i3 == 3) {
                    VerticalWidgetRun verticalWidgetRun = constraintWidget2.mVerticalRun;
                    DependencyNode dependencyNode = verticalWidgetRun.start;
                    DependencyNode dependencyNode2 = verticalWidgetRun.end;
                    boolean z2 = constraintWidget2.mLeft.mTarget != null;
                    boolean z3 = constraintWidget2.mTop.mTarget != null;
                    boolean z4 = constraintWidget2.mRight.mTarget != null;
                    boolean z5 = constraintWidget2.mBottom.mTarget != null;
                    int dimensionRatioSide = constraintWidget2.getDimensionRatioSide();
                    if (z2 && z3 && z4 && z5) {
                        float f4 = this.mWidget.mDimensionRatio;
                        boolean z6 = dependencyNode.resolved;
                        int[] iArr = sTempDimensions;
                        if (z6 && dependencyNode2.resolved) {
                            DependencyNode dependencyNode3 = this.start;
                            if (dependencyNode3.readyToSolve && this.end.readyToSolve) {
                                computeInsetRatio(iArr, ((DependencyNode) ((ArrayList) dependencyNode3.mTargets).get(0)).value + this.start.mMargin, ((DependencyNode) ((ArrayList) this.end.mTargets).get(0)).value - this.end.mMargin, dependencyNode.value + dependencyNode.mMargin, dependencyNode2.value - dependencyNode2.mMargin, f4, dimensionRatioSide);
                                this.mDimension.resolve(iArr[0]);
                                this.mWidget.mVerticalRun.mDimension.resolve(iArr[1]);
                                return;
                            }
                            return;
                        }
                        DependencyNode dependencyNode4 = this.start;
                        if (dependencyNode4.resolved) {
                            DependencyNode dependencyNode5 = this.end;
                            if (dependencyNode5.resolved) {
                                if (!dependencyNode.readyToSolve || !dependencyNode2.readyToSolve) {
                                    return;
                                }
                                computeInsetRatio(iArr, dependencyNode4.value + dependencyNode4.mMargin, dependencyNode5.value - dependencyNode5.mMargin, ((DependencyNode) ((ArrayList) dependencyNode.mTargets).get(0)).value + dependencyNode.mMargin, ((DependencyNode) ((ArrayList) dependencyNode2.mTargets).get(0)).value - dependencyNode2.mMargin, f4, dimensionRatioSide);
                                this.mDimension.resolve(iArr[0]);
                                this.mWidget.mVerticalRun.mDimension.resolve(iArr[1]);
                            }
                        }
                        DependencyNode dependencyNode6 = this.start;
                        if (!dependencyNode6.readyToSolve || !this.end.readyToSolve || !dependencyNode.readyToSolve || !dependencyNode2.readyToSolve) {
                            return;
                        }
                        computeInsetRatio(iArr, ((DependencyNode) ((ArrayList) dependencyNode6.mTargets).get(0)).value + this.start.mMargin, ((DependencyNode) ((ArrayList) this.end.mTargets).get(0)).value - this.end.mMargin, ((DependencyNode) ((ArrayList) dependencyNode.mTargets).get(0)).value + dependencyNode.mMargin, ((DependencyNode) ((ArrayList) dependencyNode2.mTargets).get(0)).value - dependencyNode2.mMargin, f4, dimensionRatioSide);
                        this.mDimension.resolve(iArr[0]);
                        this.mWidget.mVerticalRun.mDimension.resolve(iArr[1]);
                    } else if (z2 && z4) {
                        DependencyNode dependencyNode7 = this.start;
                        if (!dependencyNode7.readyToSolve || !this.end.readyToSolve) {
                            return;
                        }
                        float f5 = this.mWidget.mDimensionRatio;
                        int i4 = ((DependencyNode) ((ArrayList) dependencyNode7.mTargets).get(0)).value + this.start.mMargin;
                        int i5 = ((DependencyNode) ((ArrayList) this.end.mTargets).get(0)).value - this.end.mMargin;
                        if (dimensionRatioSide == -1 || dimensionRatioSide == 0) {
                            int limitedDimension = getLimitedDimension(i5 - i4, 0);
                            int i6 = (int) ((limitedDimension * f5) + 0.5f);
                            int limitedDimension2 = getLimitedDimension(i6, 1);
                            if (i6 != limitedDimension2) {
                                limitedDimension = (int) ((limitedDimension2 / f5) + 0.5f);
                            }
                            this.mDimension.resolve(limitedDimension);
                            this.mWidget.mVerticalRun.mDimension.resolve(limitedDimension2);
                        } else if (dimensionRatioSide == 1) {
                            int limitedDimension3 = getLimitedDimension(i5 - i4, 0);
                            int i7 = (int) ((limitedDimension3 / f5) + 0.5f);
                            int limitedDimension4 = getLimitedDimension(i7, 1);
                            if (i7 != limitedDimension4) {
                                limitedDimension3 = (int) ((limitedDimension4 * f5) + 0.5f);
                            }
                            this.mDimension.resolve(limitedDimension3);
                            this.mWidget.mVerticalRun.mDimension.resolve(limitedDimension4);
                        }
                    } else if (z3 && z5) {
                        if (!dependencyNode.readyToSolve || !dependencyNode2.readyToSolve) {
                            return;
                        }
                        float f6 = this.mWidget.mDimensionRatio;
                        int i8 = ((DependencyNode) ((ArrayList) dependencyNode.mTargets).get(0)).value + dependencyNode.mMargin;
                        int i9 = ((DependencyNode) ((ArrayList) dependencyNode2.mTargets).get(0)).value - dependencyNode2.mMargin;
                        if (dimensionRatioSide != -1) {
                            if (dimensionRatioSide == 0) {
                                int limitedDimension5 = getLimitedDimension(i9 - i8, 1);
                                int i10 = (int) ((limitedDimension5 * f6) + 0.5f);
                                int limitedDimension6 = getLimitedDimension(i10, 0);
                                if (i10 != limitedDimension6) {
                                    limitedDimension5 = (int) ((limitedDimension6 / f6) + 0.5f);
                                }
                                this.mDimension.resolve(limitedDimension6);
                                this.mWidget.mVerticalRun.mDimension.resolve(limitedDimension5);
                            }
                        }
                        int limitedDimension7 = getLimitedDimension(i9 - i8, 1);
                        int i11 = (int) ((limitedDimension7 / f6) + 0.5f);
                        int limitedDimension8 = getLimitedDimension(i11, 0);
                        if (i11 != limitedDimension8) {
                            limitedDimension7 = (int) ((limitedDimension8 * f6) + 0.5f);
                        }
                        this.mDimension.resolve(limitedDimension8);
                        this.mWidget.mVerticalRun.mDimension.resolve(limitedDimension7);
                    }
                } else {
                    int dimensionRatioSide2 = constraintWidget2.getDimensionRatioSide();
                    if (dimensionRatioSide2 == -1) {
                        ConstraintWidget constraintWidget4 = this.mWidget;
                        f = constraintWidget4.mVerticalRun.mDimension.value;
                        f2 = constraintWidget4.mDimensionRatio;
                    } else if (dimensionRatioSide2 == 0) {
                        f3 = r1.mVerticalRun.mDimension.value / this.mWidget.mDimensionRatio;
                        i = (int) (f3 + 0.5f);
                        this.mDimension.resolve(i);
                    } else if (dimensionRatioSide2 != 1) {
                        i = 0;
                        this.mDimension.resolve(i);
                    } else {
                        ConstraintWidget constraintWidget5 = this.mWidget;
                        f = constraintWidget5.mVerticalRun.mDimension.value;
                        f2 = constraintWidget5.mDimensionRatio;
                    }
                    f3 = f * f2;
                    i = (int) (f3 + 0.5f);
                    this.mDimension.resolve(i);
                }
            }
        }
        DependencyNode dependencyNode8 = this.start;
        if (dependencyNode8.readyToSolve) {
            DependencyNode dependencyNode9 = this.end;
            if (dependencyNode9.readyToSolve) {
                if (dependencyNode8.resolved && dependencyNode9.resolved && this.mDimension.resolved) {
                    return;
                }
                if (!this.mDimension.resolved && this.mDimensionBehavior == dimensionBehaviour) {
                    ConstraintWidget constraintWidget6 = this.mWidget;
                    if (constraintWidget6.mMatchConstraintDefaultWidth == 0 && !constraintWidget6.isInHorizontalChain()) {
                        DependencyNode dependencyNode10 = (DependencyNode) ((ArrayList) this.start.mTargets).get(0);
                        DependencyNode dependencyNode11 = (DependencyNode) ((ArrayList) this.end.mTargets).get(0);
                        int i12 = dependencyNode10.value;
                        DependencyNode dependencyNode12 = this.start;
                        int i13 = i12 + dependencyNode12.mMargin;
                        int i14 = dependencyNode11.value + this.end.mMargin;
                        dependencyNode12.resolve(i13);
                        this.end.resolve(i14);
                        this.mDimension.resolve(i14 - i13);
                        return;
                    }
                }
                if (!this.mDimension.resolved && this.mDimensionBehavior == dimensionBehaviour && this.matchConstraintsType == 1 && ((ArrayList) this.start.mTargets).size() > 0 && ((ArrayList) this.end.mTargets).size() > 0) {
                    int min = Math.min((((DependencyNode) ((ArrayList) this.end.mTargets).get(0)).value + this.end.mMargin) - (((DependencyNode) ((ArrayList) this.start.mTargets).get(0)).value + this.start.mMargin), this.mDimension.wrapValue);
                    ConstraintWidget constraintWidget7 = this.mWidget;
                    int i15 = constraintWidget7.mMatchConstraintMaxWidth;
                    int max = Math.max(constraintWidget7.mMatchConstraintMinWidth, min);
                    if (i15 > 0) {
                        max = Math.min(i15, max);
                    }
                    this.mDimension.resolve(max);
                }
                if (this.mDimension.resolved) {
                    DependencyNode dependencyNode13 = (DependencyNode) ((ArrayList) this.start.mTargets).get(0);
                    DependencyNode dependencyNode14 = (DependencyNode) ((ArrayList) this.end.mTargets).get(0);
                    int i16 = dependencyNode13.value + this.start.mMargin;
                    int i17 = dependencyNode14.value + this.end.mMargin;
                    float horizontalBiasPercent = this.mWidget.getHorizontalBiasPercent();
                    if (dependencyNode13 == dependencyNode14) {
                        i16 = dependencyNode13.value;
                        i17 = dependencyNode14.value;
                        horizontalBiasPercent = 0.5f;
                    }
                    this.start.resolve((int) ((((i17 - i16) - this.mDimension.value) * horizontalBiasPercent) + i16 + 0.5f));
                    this.end.resolve(this.start.value + this.mDimension.value);
                }
            }
        }
    }
}
