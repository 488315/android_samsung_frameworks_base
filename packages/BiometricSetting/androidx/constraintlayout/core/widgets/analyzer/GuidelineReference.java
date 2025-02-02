package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Guideline;
import java.util.ArrayList;

/* loaded from: classes.dex */
final class GuidelineReference extends WidgetRun {
    GuidelineReference(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        constraintWidget.mHorizontalRun.clear();
        constraintWidget.mVerticalRun.clear();
        this.orientation = ((Guideline) constraintWidget).getOrientation();
    }

    private void addDependency(DependencyNode dependencyNode) {
        ((ArrayList) this.start.mDependencies).add(dependencyNode);
        ((ArrayList) dependencyNode.mTargets).add(this.start);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    final void apply() {
        Guideline guideline = (Guideline) this.mWidget;
        int relativeBegin = guideline.getRelativeBegin();
        int relativeEnd = guideline.getRelativeEnd();
        if (guideline.getOrientation() == 1) {
            if (relativeBegin != -1) {
                ((ArrayList) this.start.mTargets).add(this.mWidget.mParent.mHorizontalRun.start);
                ((ArrayList) this.mWidget.mParent.mHorizontalRun.start.mDependencies).add(this.start);
                this.start.mMargin = relativeBegin;
            } else if (relativeEnd != -1) {
                ((ArrayList) this.start.mTargets).add(this.mWidget.mParent.mHorizontalRun.end);
                ((ArrayList) this.mWidget.mParent.mHorizontalRun.end.mDependencies).add(this.start);
                this.start.mMargin = -relativeEnd;
            } else {
                DependencyNode dependencyNode = this.start;
                dependencyNode.delegateToWidgetRun = true;
                ((ArrayList) dependencyNode.mTargets).add(this.mWidget.mParent.mHorizontalRun.end);
                ((ArrayList) this.mWidget.mParent.mHorizontalRun.end.mDependencies).add(this.start);
            }
            addDependency(this.mWidget.mHorizontalRun.start);
            addDependency(this.mWidget.mHorizontalRun.end);
            return;
        }
        if (relativeBegin != -1) {
            ((ArrayList) this.start.mTargets).add(this.mWidget.mParent.mVerticalRun.start);
            ((ArrayList) this.mWidget.mParent.mVerticalRun.start.mDependencies).add(this.start);
            this.start.mMargin = relativeBegin;
        } else if (relativeEnd != -1) {
            ((ArrayList) this.start.mTargets).add(this.mWidget.mParent.mVerticalRun.end);
            ((ArrayList) this.mWidget.mParent.mVerticalRun.end.mDependencies).add(this.start);
            this.start.mMargin = -relativeEnd;
        } else {
            DependencyNode dependencyNode2 = this.start;
            dependencyNode2.delegateToWidgetRun = true;
            ((ArrayList) dependencyNode2.mTargets).add(this.mWidget.mParent.mVerticalRun.end);
            ((ArrayList) this.mWidget.mParent.mVerticalRun.end.mDependencies).add(this.start);
        }
        addDependency(this.mWidget.mVerticalRun.start);
        addDependency(this.mWidget.mVerticalRun.end);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        if (((Guideline) this.mWidget).getOrientation() == 1) {
            this.mWidget.setX(this.start.value);
        } else {
            this.mWidget.setY(this.start.value);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    final void clear() {
        this.start.clear();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    final boolean supportsWrapComputation() {
        return false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public final void update(Dependency dependency) {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.readyToSolve && !dependencyNode.resolved) {
            DependencyNode dependencyNode2 = (DependencyNode) ((ArrayList) dependencyNode.mTargets).get(0);
            this.start.resolve((int) ((((Guideline) this.mWidget).getRelativePercent() * dependencyNode2.value) + 0.5f));
        }
    }
}
