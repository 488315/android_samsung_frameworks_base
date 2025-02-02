package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class WidgetRun implements Dependency {
    protected ConstraintWidget.DimensionBehaviour mDimensionBehavior;
    RunGroup mRunGroup;
    ConstraintWidget mWidget;
    public int matchConstraintsType;
    DimensionDependency mDimension = new DimensionDependency(this);
    public int orientation = 0;
    boolean mResolved = false;
    public DependencyNode start = new DependencyNode(this);
    public DependencyNode end = new DependencyNode(this);
    protected RunType mRunType = RunType.NONE;

    enum RunType {
        NONE,
        /* JADX INFO: Fake field, exist only in values array */
        EF1,
        /* JADX INFO: Fake field, exist only in values array */
        EF2,
        CENTER;

        RunType() {
        }
    }

    public WidgetRun(ConstraintWidget constraintWidget) {
        this.mWidget = constraintWidget;
    }

    protected static void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i) {
        ((ArrayList) dependencyNode.mTargets).add(dependencyNode2);
        dependencyNode.mMargin = i;
        ((ArrayList) dependencyNode2.mDependencies).add(dependencyNode);
    }

    protected static DependencyNode getTarget(ConstraintAnchor constraintAnchor) {
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        int ordinal = constraintAnchor2.mType.ordinal();
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        if (ordinal == 1) {
            return constraintWidget.mHorizontalRun.start;
        }
        if (ordinal == 2) {
            return constraintWidget.mVerticalRun.start;
        }
        if (ordinal == 3) {
            return constraintWidget.mHorizontalRun.end;
        }
        if (ordinal == 4) {
            return constraintWidget.mVerticalRun.end;
        }
        if (ordinal != 5) {
            return null;
        }
        return constraintWidget.mVerticalRun.baseline;
    }

    abstract void apply();

    abstract void applyToWidget();

    abstract void clear();

    protected final int getLimitedDimension(int i, int i2) {
        int max;
        if (i2 == 0) {
            ConstraintWidget constraintWidget = this.mWidget;
            int i3 = constraintWidget.mMatchConstraintMaxWidth;
            max = Math.max(constraintWidget.mMatchConstraintMinWidth, i);
            if (i3 > 0) {
                max = Math.min(i3, i);
            }
            if (max == i) {
                return i;
            }
        } else {
            ConstraintWidget constraintWidget2 = this.mWidget;
            int i4 = constraintWidget2.mMatchConstraintMaxHeight;
            max = Math.max(constraintWidget2.mMatchConstraintMinHeight, i);
            if (i4 > 0) {
                max = Math.min(i4, i);
            }
            if (max == i) {
                return i;
            }
        }
        return max;
    }

    public long getWrapDimension() {
        if (this.mDimension.resolved) {
            return r2.value;
        }
        return 0L;
    }

    public final boolean isResolved() {
        return this.mResolved;
    }

    abstract boolean supportsWrapComputation();

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0053, code lost:
    
        if (r10.matchConstraintsType == 3) goto L51;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final void updateRunCenter(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i) {
        DependencyNode target = getTarget(constraintAnchor);
        DependencyNode target2 = getTarget(constraintAnchor2);
        if (target.resolved && target2.resolved) {
            int margin = constraintAnchor.getMargin() + target.value;
            int margin2 = target2.value - constraintAnchor2.getMargin();
            int i2 = margin2 - margin;
            DimensionDependency dimensionDependency = this.mDimension;
            if (!dimensionDependency.resolved) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.mDimensionBehavior;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour == dimensionBehaviour2) {
                    int i3 = this.matchConstraintsType;
                    if (i3 == 0) {
                        dimensionDependency.resolve(getLimitedDimension(i2, i));
                    } else if (i3 == 1) {
                        this.mDimension.resolve(Math.min(getLimitedDimension(dimensionDependency.wrapValue, i), i2));
                    } else if (i3 == 2) {
                        ConstraintWidget constraintWidget = this.mWidget;
                        ConstraintWidget constraintWidget2 = constraintWidget.mParent;
                        if (constraintWidget2 != null) {
                            if ((i == 0 ? constraintWidget2.mHorizontalRun : constraintWidget2.mVerticalRun).mDimension.resolved) {
                                dimensionDependency.resolve(getLimitedDimension((int) ((r6.value * (i == 0 ? constraintWidget.mMatchConstraintPercentWidth : constraintWidget.mMatchConstraintPercentHeight)) + 0.5f), i));
                            }
                        }
                    } else if (i3 == 3) {
                        ConstraintWidget constraintWidget3 = this.mWidget;
                        WidgetRun widgetRun = constraintWidget3.mHorizontalRun;
                        if (widgetRun.mDimensionBehavior == dimensionBehaviour2 && widgetRun.matchConstraintsType == 3) {
                            VerticalWidgetRun verticalWidgetRun = constraintWidget3.mVerticalRun;
                            if (verticalWidgetRun.mDimensionBehavior == dimensionBehaviour2) {
                            }
                        }
                        if (i == 0) {
                            widgetRun = constraintWidget3.mVerticalRun;
                        }
                        if (widgetRun.mDimension.resolved) {
                            float f = constraintWidget3.mDimensionRatio;
                            dimensionDependency.resolve(i == 1 ? (int) ((r6.value / f) + 0.5f) : (int) ((f * r6.value) + 0.5f));
                        }
                    }
                }
            }
            DimensionDependency dimensionDependency2 = this.mDimension;
            if (dimensionDependency2.resolved) {
                if (dimensionDependency2.value == i2) {
                    this.start.resolve(margin);
                    this.end.resolve(margin2);
                    return;
                }
                float horizontalBiasPercent = i == 0 ? this.mWidget.getHorizontalBiasPercent() : this.mWidget.getVerticalBiasPercent();
                if (target == target2) {
                    margin = target.value;
                    margin2 = target2.value;
                    horizontalBiasPercent = 0.5f;
                }
                this.start.resolve((int) ((((margin2 - margin) - this.mDimension.value) * horizontalBiasPercent) + margin + 0.5f));
                this.end.resolve(this.start.value + this.mDimension.value);
            }
        }
    }

    protected final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i, DimensionDependency dimensionDependency) {
        ((ArrayList) dependencyNode.mTargets).add(dependencyNode2);
        ((ArrayList) dependencyNode.mTargets).add(this.mDimension);
        dependencyNode.mMarginFactor = i;
        dependencyNode.mMarginDependency = dimensionDependency;
        ((ArrayList) dependencyNode2.mDependencies).add(dependencyNode);
        ((ArrayList) dimensionDependency.mDependencies).add(dependencyNode);
    }

    protected static DependencyNode getTarget(ConstraintAnchor constraintAnchor, int i) {
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        WidgetRun widgetRun = i == 0 ? constraintWidget.mHorizontalRun : constraintWidget.mVerticalRun;
        int ordinal = constraintAnchor2.mType.ordinal();
        if (ordinal == 1 || ordinal == 2) {
            return widgetRun.start;
        }
        if (ordinal == 3 || ordinal == 4) {
            return widgetRun.end;
        }
        return null;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
    }
}
