package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Guideline;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class GuidelineReference extends WidgetRun {
    public GuidelineReference(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        constraintWidget.mHorizontalRun.clear();
        constraintWidget.mVerticalRun.clear();
        this.orientation = ((Guideline) constraintWidget).mOrientation;
    }

    public final void addDependency(DependencyNode dependencyNode) {
        DependencyNode dependencyNode2 = this.start;
        ((ArrayList) dependencyNode2.mDependencies).add(dependencyNode);
        ((ArrayList) dependencyNode.mTargets).add(dependencyNode2);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void apply() {
        ConstraintWidget constraintWidget = this.mWidget;
        Guideline guideline = (Guideline) constraintWidget;
        int i = guideline.mRelativeBegin;
        int i2 = guideline.mRelativeEnd;
        int i3 = guideline.mOrientation;
        DependencyNode dependencyNode = this.start;
        if (i3 == 1) {
            if (i != -1) {
                ((ArrayList) dependencyNode.mTargets).add(constraintWidget.mParent.mHorizontalRun.start);
                ((ArrayList) this.mWidget.mParent.mHorizontalRun.start.mDependencies).add(dependencyNode);
                dependencyNode.mMargin = i;
            } else if (i2 != -1) {
                ((ArrayList) dependencyNode.mTargets).add(constraintWidget.mParent.mHorizontalRun.end);
                ((ArrayList) this.mWidget.mParent.mHorizontalRun.end.mDependencies).add(dependencyNode);
                dependencyNode.mMargin = -i2;
            } else {
                dependencyNode.delegateToWidgetRun = true;
                ((ArrayList) dependencyNode.mTargets).add(constraintWidget.mParent.mHorizontalRun.end);
                ((ArrayList) this.mWidget.mParent.mHorizontalRun.end.mDependencies).add(dependencyNode);
            }
            addDependency(this.mWidget.mHorizontalRun.start);
            addDependency(this.mWidget.mHorizontalRun.end);
            return;
        }
        if (i != -1) {
            ((ArrayList) dependencyNode.mTargets).add(constraintWidget.mParent.mVerticalRun.start);
            ((ArrayList) this.mWidget.mParent.mVerticalRun.start.mDependencies).add(dependencyNode);
            dependencyNode.mMargin = i;
        } else if (i2 != -1) {
            ((ArrayList) dependencyNode.mTargets).add(constraintWidget.mParent.mVerticalRun.end);
            ((ArrayList) this.mWidget.mParent.mVerticalRun.end.mDependencies).add(dependencyNode);
            dependencyNode.mMargin = -i2;
        } else {
            dependencyNode.delegateToWidgetRun = true;
            ((ArrayList) dependencyNode.mTargets).add(constraintWidget.mParent.mVerticalRun.end);
            ((ArrayList) this.mWidget.mParent.mVerticalRun.end.mDependencies).add(dependencyNode);
        }
        addDependency(this.mWidget.mVerticalRun.start);
        addDependency(this.mWidget.mVerticalRun.end);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        ConstraintWidget constraintWidget = this.mWidget;
        int i = ((Guideline) constraintWidget).mOrientation;
        DependencyNode dependencyNode = this.start;
        if (i == 1) {
            constraintWidget.mX = dependencyNode.value;
        } else {
            constraintWidget.mY = dependencyNode.value;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void clear() {
        this.start.clear();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final boolean supportsWrapComputation() {
        return false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public final void update(Dependency dependency) {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.readyToSolve && !dependencyNode.resolved) {
            dependencyNode.resolve((int) ((((DependencyNode) ((ArrayList) dependencyNode.mTargets).get(0)).value * ((Guideline) this.mWidget).mRelativePercent) + 0.5f));
        }
    }
}
