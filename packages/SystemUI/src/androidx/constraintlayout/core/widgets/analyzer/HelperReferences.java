package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class HelperReferences extends WidgetRun {
    public HelperReferences(ConstraintWidget constraintWidget) {
        super(constraintWidget);
    }

    public final void addDependency$1(DependencyNode dependencyNode) {
        DependencyNode dependencyNode2 = this.start;
        ((ArrayList) dependencyNode2.mDependencies).add(dependencyNode);
        ((ArrayList) dependencyNode.mTargets).add(dependencyNode2);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void apply() {
        ConstraintWidget constraintWidget = this.mWidget;
        if (constraintWidget instanceof Barrier) {
            DependencyNode dependencyNode = this.start;
            dependencyNode.delegateToWidgetRun = true;
            Barrier barrier = (Barrier) constraintWidget;
            int i = barrier.mBarrierType;
            boolean z = barrier.mAllowsGoneWidget;
            int i2 = 0;
            if (i == 0) {
                dependencyNode.mType = DependencyNode.Type.LEFT;
                while (i2 < barrier.mWidgetsCount) {
                    ConstraintWidget constraintWidget2 = barrier.mWidgets[i2];
                    if (z || constraintWidget2.mVisibility != 8) {
                        DependencyNode dependencyNode2 = constraintWidget2.mHorizontalRun.start;
                        ((ArrayList) dependencyNode2.mDependencies).add(dependencyNode);
                        ((ArrayList) dependencyNode.mTargets).add(dependencyNode2);
                    }
                    i2++;
                }
                addDependency$1(this.mWidget.mHorizontalRun.start);
                addDependency$1(this.mWidget.mHorizontalRun.end);
                return;
            }
            if (i == 1) {
                dependencyNode.mType = DependencyNode.Type.RIGHT;
                while (i2 < barrier.mWidgetsCount) {
                    ConstraintWidget constraintWidget3 = barrier.mWidgets[i2];
                    if (z || constraintWidget3.mVisibility != 8) {
                        DependencyNode dependencyNode3 = constraintWidget3.mHorizontalRun.end;
                        ((ArrayList) dependencyNode3.mDependencies).add(dependencyNode);
                        ((ArrayList) dependencyNode.mTargets).add(dependencyNode3);
                    }
                    i2++;
                }
                addDependency$1(this.mWidget.mHorizontalRun.start);
                addDependency$1(this.mWidget.mHorizontalRun.end);
                return;
            }
            if (i == 2) {
                dependencyNode.mType = DependencyNode.Type.TOP;
                while (i2 < barrier.mWidgetsCount) {
                    ConstraintWidget constraintWidget4 = barrier.mWidgets[i2];
                    if (z || constraintWidget4.mVisibility != 8) {
                        DependencyNode dependencyNode4 = constraintWidget4.mVerticalRun.start;
                        ((ArrayList) dependencyNode4.mDependencies).add(dependencyNode);
                        ((ArrayList) dependencyNode.mTargets).add(dependencyNode4);
                    }
                    i2++;
                }
                addDependency$1(this.mWidget.mVerticalRun.start);
                addDependency$1(this.mWidget.mVerticalRun.end);
                return;
            }
            if (i != 3) {
                return;
            }
            dependencyNode.mType = DependencyNode.Type.BOTTOM;
            while (i2 < barrier.mWidgetsCount) {
                ConstraintWidget constraintWidget5 = barrier.mWidgets[i2];
                if (z || constraintWidget5.mVisibility != 8) {
                    DependencyNode dependencyNode5 = constraintWidget5.mVerticalRun.end;
                    ((ArrayList) dependencyNode5.mDependencies).add(dependencyNode);
                    ((ArrayList) dependencyNode.mTargets).add(dependencyNode5);
                }
                i2++;
            }
            addDependency$1(this.mWidget.mVerticalRun.start);
            addDependency$1(this.mWidget.mVerticalRun.end);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        ConstraintWidget constraintWidget = this.mWidget;
        if (constraintWidget instanceof Barrier) {
            int i = ((Barrier) constraintWidget).mBarrierType;
            DependencyNode dependencyNode = this.start;
            if (i == 0 || i == 1) {
                constraintWidget.mX = dependencyNode.value;
            } else {
                constraintWidget.mY = dependencyNode.value;
            }
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void clear() {
        this.mRunGroup = null;
        this.start.clear();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final boolean supportsWrapComputation() {
        return false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public final void update(Dependency dependency) {
        Barrier barrier = (Barrier) this.mWidget;
        int i = barrier.mBarrierType;
        DependencyNode dependencyNode = this.start;
        ArrayList arrayList = (ArrayList) dependencyNode.mTargets;
        int size = arrayList.size();
        int i2 = 0;
        int i3 = 0;
        int i4 = -1;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            int i5 = ((DependencyNode) obj).value;
            if (i4 == -1 || i5 < i4) {
                i4 = i5;
            }
            if (i2 < i5) {
                i2 = i5;
            }
        }
        if (i == 0 || i == 2) {
            dependencyNode.resolve(i4 + barrier.mMargin);
        } else {
            dependencyNode.resolve(i2 + barrier.mMargin);
        }
    }
}
