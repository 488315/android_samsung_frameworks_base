package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class WidgetRun implements Dependency {
    public ConstraintWidget.DimensionBehaviour mDimensionBehavior;
    public RunGroup mRunGroup;
    public ConstraintWidget mWidget;
    public int matchConstraintsType;
    public final DimensionDependency mDimension = new DimensionDependency(this);
    public int orientation = 0;
    public boolean mResolved = false;
    public final DependencyNode start = new DependencyNode(this);
    public final DependencyNode end = new DependencyNode(this);
    public RunType mRunType = RunType.NONE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.WidgetRun$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BASELINE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BOTTOM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum RunType {
        NONE,
        START,
        END,
        CENTER
    }

    public WidgetRun(ConstraintWidget constraintWidget) {
        this.mWidget = constraintWidget;
    }

    public static void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i) {
        ((ArrayList) dependencyNode.mTargets).add(dependencyNode2);
        dependencyNode.mMargin = i;
        ((ArrayList) dependencyNode2.mDependencies).add(dependencyNode);
    }

    public static DependencyNode getTarget(ConstraintAnchor constraintAnchor) {
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        int i = AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[constraintAnchor2.mType.ordinal()];
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        if (i == 1) {
            return constraintWidget.mHorizontalRun.start;
        }
        if (i == 2) {
            return constraintWidget.mHorizontalRun.end;
        }
        if (i == 3) {
            return constraintWidget.mVerticalRun.start;
        }
        if (i == 4) {
            return constraintWidget.mVerticalRun.baseline;
        }
        if (i != 5) {
            return null;
        }
        return constraintWidget.mVerticalRun.end;
    }

    public abstract void apply();

    public abstract void applyToWidget();

    public abstract void clear();

    public final int getLimitedDimension(int i, int i2) {
        if (i2 == 0) {
            ConstraintWidget constraintWidget = this.mWidget;
            int i3 = constraintWidget.mMatchConstraintMaxWidth;
            int max = Math.max(constraintWidget.mMatchConstraintMinWidth, i);
            if (i3 > 0) {
                max = Math.min(i3, i);
            }
            if (max != i) {
                return max;
            }
        } else {
            ConstraintWidget constraintWidget2 = this.mWidget;
            int i4 = constraintWidget2.mMatchConstraintMaxHeight;
            int max2 = Math.max(constraintWidget2.mMatchConstraintMinHeight, i);
            if (i4 > 0) {
                max2 = Math.min(i4, i);
            }
            if (max2 != i) {
                return max2;
            }
        }
        return i;
    }

    public long getWrapDimension() {
        if (this.mDimension.resolved) {
            return r2.value;
        }
        return 0L;
    }

    public abstract boolean supportsWrapComputation();

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0053, code lost:
    
        if (r10.matchConstraintsType == 3) goto L51;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateRunCenter(androidx.constraintlayout.core.widgets.ConstraintAnchor r13, androidx.constraintlayout.core.widgets.ConstraintAnchor r14, int r15) {
        /*
            Method dump skipped, instructions count: 236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.analyzer.WidgetRun.updateRunCenter(androidx.constraintlayout.core.widgets.ConstraintAnchor, androidx.constraintlayout.core.widgets.ConstraintAnchor, int):void");
    }

    public final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i, DimensionDependency dimensionDependency) {
        ((ArrayList) dependencyNode.mTargets).add(dependencyNode2);
        ((ArrayList) dependencyNode.mTargets).add(this.mDimension);
        dependencyNode.mMarginFactor = i;
        dependencyNode.mMarginDependency = dimensionDependency;
        ((ArrayList) dependencyNode2.mDependencies).add(dependencyNode);
        ((ArrayList) dimensionDependency.mDependencies).add(dependencyNode);
    }

    public static DependencyNode getTarget(ConstraintAnchor constraintAnchor, int i) {
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        WidgetRun widgetRun = i == 0 ? constraintWidget.mHorizontalRun : constraintWidget.mVerticalRun;
        int i2 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[constraintAnchor2.mType.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 != 5) {
                        return null;
                    }
                }
            }
            return widgetRun.end;
        }
        return widgetRun.start;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
    }
}
