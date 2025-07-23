package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class HorizontalWidgetRun extends WidgetRun {
    public static final int[] sTempDimensions = new int[2];

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun$1, reason: invalid class name */
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

    public HorizontalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.mType = DependencyNode.Type.LEFT;
        this.end.mType = DependencyNode.Type.RIGHT;
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
        ConstraintWidget constraintWidget5 = this.mWidget;
        boolean z = constraintWidget5.measured;
        DimensionDependency dimensionDependency = this.mDimension;
        if (z) {
            dimensionDependency.resolve(constraintWidget5.getWidth());
        }
        boolean z2 = dimensionDependency.resolved;
        DependencyNode dependencyNode = this.end;
        DependencyNode dependencyNode2 = this.start;
        if (z2) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = this.mDimensionBehavior;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
            if (dimensionBehaviour3 == dimensionBehaviour4 && (constraintWidget2 = (constraintWidget = this.mWidget).mParent) != null && ((dimensionBehaviour = constraintWidget2.mListDimensionBehaviors[0]) == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour == dimensionBehaviour4)) {
                WidgetRun.addTarget(dependencyNode2, constraintWidget2.mHorizontalRun.start, constraintWidget.mLeft.getMargin());
                WidgetRun.addTarget(dependencyNode, constraintWidget2.mHorizontalRun.end, -this.mWidget.mRight.getMargin());
                return;
            }
        } else {
            ConstraintWidget constraintWidget6 = this.mWidget;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = constraintWidget6.mListDimensionBehaviors[0];
            this.mDimensionBehavior = dimensionBehaviour5;
            if (dimensionBehaviour5 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour6 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
                if (dimensionBehaviour5 == dimensionBehaviour6 && (constraintWidget4 = constraintWidget6.mParent) != null && ((dimensionBehaviour2 = constraintWidget4.mListDimensionBehaviors[0]) == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour2 == dimensionBehaviour6)) {
                    int width = (constraintWidget4.getWidth() - this.mWidget.mLeft.getMargin()) - this.mWidget.mRight.getMargin();
                    WidgetRun.addTarget(dependencyNode2, constraintWidget4.mHorizontalRun.start, this.mWidget.mLeft.getMargin());
                    WidgetRun.addTarget(dependencyNode, constraintWidget4.mHorizontalRun.end, -this.mWidget.mRight.getMargin());
                    dimensionDependency.resolve(width);
                    return;
                }
                if (dimensionBehaviour5 == ConstraintWidget.DimensionBehaviour.FIXED) {
                    dimensionDependency.resolve(constraintWidget6.getWidth());
                }
            }
        }
        if (dimensionDependency.resolved) {
            ConstraintWidget constraintWidget7 = this.mWidget;
            if (constraintWidget7.measured) {
                ConstraintAnchor[] constraintAnchorArr = constraintWidget7.mListAnchors;
                ConstraintAnchor constraintAnchor = constraintAnchorArr[0];
                ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
                if (constraintAnchor2 != null && constraintAnchorArr[1].mTarget != null) {
                    if (constraintWidget7.isInHorizontalChain()) {
                        dependencyNode2.mMargin = this.mWidget.mListAnchors[0].getMargin();
                        dependencyNode.mMargin = -this.mWidget.mListAnchors[1].getMargin();
                        return;
                    }
                    DependencyNode target = WidgetRun.getTarget(this.mWidget.mListAnchors[0]);
                    if (target != null) {
                        WidgetRun.addTarget(dependencyNode2, target, this.mWidget.mListAnchors[0].getMargin());
                    }
                    DependencyNode target2 = WidgetRun.getTarget(this.mWidget.mListAnchors[1]);
                    if (target2 != null) {
                        WidgetRun.addTarget(dependencyNode, target2, -this.mWidget.mListAnchors[1].getMargin());
                    }
                    dependencyNode2.delegateToWidgetRun = true;
                    dependencyNode.delegateToWidgetRun = true;
                    return;
                }
                if (constraintAnchor2 != null) {
                    DependencyNode target3 = WidgetRun.getTarget(constraintAnchor);
                    if (target3 != null) {
                        WidgetRun.addTarget(dependencyNode2, target3, this.mWidget.mListAnchors[0].getMargin());
                        WidgetRun.addTarget(dependencyNode, dependencyNode2, dimensionDependency.value);
                        return;
                    }
                    return;
                }
                ConstraintAnchor constraintAnchor3 = constraintAnchorArr[1];
                if (constraintAnchor3.mTarget != null) {
                    DependencyNode target4 = WidgetRun.getTarget(constraintAnchor3);
                    if (target4 != null) {
                        WidgetRun.addTarget(dependencyNode, target4, -this.mWidget.mListAnchors[1].getMargin());
                        WidgetRun.addTarget(dependencyNode2, dependencyNode, -dimensionDependency.value);
                        return;
                    }
                    return;
                }
                if ((constraintWidget7 instanceof Helper) || constraintWidget7.mParent == null || constraintWidget7.getAnchor(ConstraintAnchor.Type.CENTER).mTarget != null) {
                    return;
                }
                ConstraintWidget constraintWidget8 = this.mWidget;
                WidgetRun.addTarget(dependencyNode2, constraintWidget8.mParent.mHorizontalRun.start, constraintWidget8.getX());
                WidgetRun.addTarget(dependencyNode, dependencyNode2, dimensionDependency.value);
                return;
            }
        }
        if (this.mDimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget9 = this.mWidget;
            int i = constraintWidget9.mMatchConstraintDefaultWidth;
            if (i == 2) {
                ConstraintWidget constraintWidget10 = constraintWidget9.mParent;
                if (constraintWidget10 != null) {
                    DimensionDependency dimensionDependency2 = constraintWidget10.mVerticalRun.mDimension;
                    ((ArrayList) dimensionDependency.mTargets).add(dimensionDependency2);
                    ((ArrayList) dimensionDependency2.mDependencies).add(dimensionDependency);
                    dimensionDependency.delegateToWidgetRun = true;
                    ((ArrayList) dimensionDependency.mDependencies).add(dependencyNode2);
                    ((ArrayList) dimensionDependency.mDependencies).add(dependencyNode);
                }
            } else if (i == 3) {
                if (constraintWidget9.mMatchConstraintDefaultHeight == 3) {
                    dependencyNode2.updateDelegate = this;
                    dependencyNode.updateDelegate = this;
                    VerticalWidgetRun verticalWidgetRun = constraintWidget9.mVerticalRun;
                    verticalWidgetRun.start.updateDelegate = this;
                    verticalWidgetRun.end.updateDelegate = this;
                    dimensionDependency.updateDelegate = this;
                    if (constraintWidget9.isInVerticalChain()) {
                        ((ArrayList) dimensionDependency.mTargets).add(this.mWidget.mVerticalRun.mDimension);
                        ((ArrayList) this.mWidget.mVerticalRun.mDimension.mDependencies).add(dimensionDependency);
                        VerticalWidgetRun verticalWidgetRun2 = this.mWidget.mVerticalRun;
                        verticalWidgetRun2.mDimension.updateDelegate = this;
                        ((ArrayList) dimensionDependency.mTargets).add(verticalWidgetRun2.start);
                        ((ArrayList) dimensionDependency.mTargets).add(this.mWidget.mVerticalRun.end);
                        ((ArrayList) this.mWidget.mVerticalRun.start.mDependencies).add(dimensionDependency);
                        ((ArrayList) this.mWidget.mVerticalRun.end.mDependencies).add(dimensionDependency);
                    } else if (this.mWidget.isInHorizontalChain()) {
                        ((ArrayList) this.mWidget.mVerticalRun.mDimension.mTargets).add(dimensionDependency);
                        ((ArrayList) dimensionDependency.mDependencies).add(this.mWidget.mVerticalRun.mDimension);
                    } else {
                        ((ArrayList) this.mWidget.mVerticalRun.mDimension.mTargets).add(dimensionDependency);
                    }
                } else {
                    DimensionDependency dimensionDependency3 = constraintWidget9.mVerticalRun.mDimension;
                    ((ArrayList) dimensionDependency.mTargets).add(dimensionDependency3);
                    ((ArrayList) dimensionDependency3.mDependencies).add(dimensionDependency);
                    ((ArrayList) this.mWidget.mVerticalRun.start.mDependencies).add(dimensionDependency);
                    ((ArrayList) this.mWidget.mVerticalRun.end.mDependencies).add(dimensionDependency);
                    dimensionDependency.delegateToWidgetRun = true;
                    ((ArrayList) dimensionDependency.mDependencies).add(dependencyNode2);
                    ((ArrayList) dimensionDependency.mDependencies).add(dependencyNode);
                    ((ArrayList) dependencyNode2.mTargets).add(dimensionDependency);
                    ((ArrayList) dependencyNode.mTargets).add(dimensionDependency);
                }
            }
        }
        ConstraintWidget constraintWidget11 = this.mWidget;
        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget11.mListAnchors;
        ConstraintAnchor constraintAnchor4 = constraintAnchorArr2[0];
        ConstraintAnchor constraintAnchor5 = constraintAnchor4.mTarget;
        if (constraintAnchor5 != null && constraintAnchorArr2[1].mTarget != null) {
            if (constraintWidget11.isInHorizontalChain()) {
                dependencyNode2.mMargin = this.mWidget.mListAnchors[0].getMargin();
                dependencyNode.mMargin = -this.mWidget.mListAnchors[1].getMargin();
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
                WidgetRun.addTarget(dependencyNode2, target7, this.mWidget.mListAnchors[0].getMargin());
                addTarget(dependencyNode, dependencyNode2, 1, dimensionDependency);
                return;
            }
            return;
        }
        ConstraintAnchor constraintAnchor6 = constraintAnchorArr2[1];
        if (constraintAnchor6.mTarget != null) {
            DependencyNode target8 = WidgetRun.getTarget(constraintAnchor6);
            if (target8 != null) {
                WidgetRun.addTarget(dependencyNode, target8, -this.mWidget.mListAnchors[1].getMargin());
                addTarget(dependencyNode2, dependencyNode, -1, dimensionDependency);
                return;
            }
            return;
        }
        if ((constraintWidget11 instanceof Helper) || (constraintWidget3 = constraintWidget11.mParent) == null) {
            return;
        }
        WidgetRun.addTarget(dependencyNode2, constraintWidget3.mHorizontalRun.start, constraintWidget11.getX());
        addTarget(dependencyNode, dependencyNode2, 1, dimensionDependency);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.mWidget.mX = dependencyNode.value;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void clear() {
        this.mRunGroup = null;
        this.start.clear();
        this.end.clear();
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
        this.mDimension.resolved = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final boolean supportsWrapComputation() {
        return this.mDimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.mWidget.mMatchConstraintDefaultWidth == 0;
    }

    public final String toString() {
        return "HorizontalRun " + this.mWidget.mDebugName;
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x0259, code lost:
    
        if (r15 != 1) goto L124;
     */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update(androidx.constraintlayout.core.widgets.analyzer.Dependency r23) {
        /*
            Method dump skipped, instructions count: 946
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun.update(androidx.constraintlayout.core.widgets.analyzer.Dependency):void");
    }
}
