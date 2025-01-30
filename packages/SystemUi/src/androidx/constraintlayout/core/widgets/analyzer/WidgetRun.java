package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class WidgetRun implements Dependency {
    public ConstraintWidget.DimensionBehaviour dimensionBehavior;
    public int matchConstraintsType;
    public RunGroup runGroup;
    public ConstraintWidget widget;
    public final DimensionDependency dimension = new DimensionDependency(this);
    public int orientation = 0;
    public boolean resolved = false;
    public final DependencyNode start = new DependencyNode(this);
    public final DependencyNode end = new DependencyNode(this);
    public RunType mRunType = RunType.NONE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.WidgetRun$1 */
    public abstract /* synthetic */ class AbstractC01211 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type */
        public static final /* synthetic */ int[] f23x6930e354;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            f23x6930e354 = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f23x6930e354[ConstraintAnchor.Type.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f23x6930e354[ConstraintAnchor.Type.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f23x6930e354[ConstraintAnchor.Type.BASELINE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f23x6930e354[ConstraintAnchor.Type.BOTTOM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum RunType {
        NONE,
        START,
        END,
        CENTER
    }

    public WidgetRun(ConstraintWidget constraintWidget) {
        this.widget = constraintWidget;
    }

    public static void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i) {
        ((ArrayList) dependencyNode.targets).add(dependencyNode2);
        dependencyNode.margin = i;
        ((ArrayList) dependencyNode2.dependencies).add(dependencyNode);
    }

    public static DependencyNode getTarget(ConstraintAnchor constraintAnchor) {
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        int i = AbstractC01211.f23x6930e354[constraintAnchor2.mType.ordinal()];
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        if (i == 1) {
            return constraintWidget.horizontalRun.start;
        }
        if (i == 2) {
            return constraintWidget.horizontalRun.end;
        }
        if (i == 3) {
            return constraintWidget.verticalRun.start;
        }
        if (i == 4) {
            return constraintWidget.verticalRun.baseline;
        }
        if (i != 5) {
            return null;
        }
        return constraintWidget.verticalRun.end;
    }

    public abstract void apply();

    public abstract void applyToWidget();

    public abstract void clear();

    public final int getLimitedDimension(int i, int i2) {
        int max;
        if (i2 == 0) {
            ConstraintWidget constraintWidget = this.widget;
            int i3 = constraintWidget.mMatchConstraintMaxWidth;
            max = Math.max(constraintWidget.mMatchConstraintMinWidth, i);
            if (i3 > 0) {
                max = Math.min(i3, i);
            }
            if (max == i) {
                return i;
            }
        } else {
            ConstraintWidget constraintWidget2 = this.widget;
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
        if (this.dimension.resolved) {
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
    */
    public final void updateRunCenter(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i) {
        DependencyNode target = getTarget(constraintAnchor);
        DependencyNode target2 = getTarget(constraintAnchor2);
        if (target.resolved && target2.resolved) {
            int margin = constraintAnchor.getMargin() + target.value;
            int margin2 = target2.value - constraintAnchor2.getMargin();
            int i2 = margin2 - margin;
            DimensionDependency dimensionDependency = this.dimension;
            if (!dimensionDependency.resolved) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.dimensionBehavior;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour == dimensionBehaviour2) {
                    int i3 = this.matchConstraintsType;
                    if (i3 == 0) {
                        dimensionDependency.resolve(getLimitedDimension(i2, i));
                    } else if (i3 == 1) {
                        dimensionDependency.resolve(Math.min(getLimitedDimension(dimensionDependency.wrapValue, i), i2));
                    } else if (i3 == 2) {
                        ConstraintWidget constraintWidget = this.widget;
                        ConstraintWidget constraintWidget2 = constraintWidget.mParent;
                        if (constraintWidget2 != null) {
                            if ((i == 0 ? constraintWidget2.horizontalRun : constraintWidget2.verticalRun).dimension.resolved) {
                                dimensionDependency.resolve(getLimitedDimension((int) ((r6.value * (i == 0 ? constraintWidget.mMatchConstraintPercentWidth : constraintWidget.mMatchConstraintPercentHeight)) + 0.5f), i));
                            }
                        }
                    } else if (i3 == 3) {
                        ConstraintWidget constraintWidget3 = this.widget;
                        WidgetRun widgetRun = constraintWidget3.horizontalRun;
                        if (widgetRun.dimensionBehavior == dimensionBehaviour2 && widgetRun.matchConstraintsType == 3) {
                            VerticalWidgetRun verticalWidgetRun = constraintWidget3.verticalRun;
                            if (verticalWidgetRun.dimensionBehavior == dimensionBehaviour2) {
                            }
                        }
                        if (i == 0) {
                            widgetRun = constraintWidget3.verticalRun;
                        }
                        if (widgetRun.dimension.resolved) {
                            float f = constraintWidget3.mDimensionRatio;
                            dimensionDependency.resolve(i == 1 ? (int) ((r6.value / f) + 0.5f) : (int) ((f * r6.value) + 0.5f));
                        }
                    }
                }
            }
            if (dimensionDependency.resolved) {
                int i4 = dimensionDependency.value;
                DependencyNode dependencyNode = this.end;
                DependencyNode dependencyNode2 = this.start;
                if (i4 == i2) {
                    dependencyNode2.resolve(margin);
                    dependencyNode.resolve(margin2);
                    return;
                }
                ConstraintWidget constraintWidget4 = this.widget;
                float f2 = i == 0 ? constraintWidget4.mHorizontalBiasPercent : constraintWidget4.mVerticalBiasPercent;
                if (target == target2) {
                    margin = target.value;
                    margin2 = target2.value;
                    f2 = 0.5f;
                }
                dependencyNode2.resolve((int) ((((margin2 - margin) - i4) * f2) + margin + 0.5f));
                dependencyNode.resolve(dependencyNode2.value + dimensionDependency.value);
            }
        }
    }

    public final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i, DimensionDependency dimensionDependency) {
        ((ArrayList) dependencyNode.targets).add(dependencyNode2);
        ((ArrayList) dependencyNode.targets).add(this.dimension);
        dependencyNode.marginFactor = i;
        dependencyNode.marginDependency = dimensionDependency;
        ((ArrayList) dependencyNode2.dependencies).add(dependencyNode);
        ((ArrayList) dimensionDependency.dependencies).add(dependencyNode);
    }

    public static DependencyNode getTarget(ConstraintAnchor constraintAnchor, int i) {
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        WidgetRun widgetRun = i == 0 ? constraintWidget.horizontalRun : constraintWidget.verticalRun;
        int i2 = AbstractC01211.f23x6930e354[constraintAnchor2.mType.ordinal()];
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
