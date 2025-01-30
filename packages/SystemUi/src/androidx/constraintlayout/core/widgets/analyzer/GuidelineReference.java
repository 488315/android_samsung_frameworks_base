package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Guideline;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GuidelineReference extends WidgetRun {
    public GuidelineReference(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        constraintWidget.horizontalRun.clear();
        constraintWidget.verticalRun.clear();
        this.orientation = ((Guideline) constraintWidget).mOrientation;
    }

    public final void addDependency(DependencyNode dependencyNode) {
        DependencyNode dependencyNode2 = this.start;
        ((ArrayList) dependencyNode2.dependencies).add(dependencyNode);
        ((ArrayList) dependencyNode.targets).add(dependencyNode2);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void apply() {
        ConstraintWidget constraintWidget = this.widget;
        Guideline guideline = (Guideline) constraintWidget;
        int i = guideline.mRelativeBegin;
        int i2 = guideline.mRelativeEnd;
        int i3 = guideline.mOrientation;
        DependencyNode dependencyNode = this.start;
        if (i3 == 1) {
            if (i != -1) {
                ((ArrayList) dependencyNode.targets).add(constraintWidget.mParent.horizontalRun.start);
                ((ArrayList) this.widget.mParent.horizontalRun.start.dependencies).add(dependencyNode);
                dependencyNode.margin = i;
            } else if (i2 != -1) {
                ((ArrayList) dependencyNode.targets).add(constraintWidget.mParent.horizontalRun.end);
                ((ArrayList) this.widget.mParent.horizontalRun.end.dependencies).add(dependencyNode);
                dependencyNode.margin = -i2;
            } else {
                dependencyNode.delegateToWidgetRun = true;
                ((ArrayList) dependencyNode.targets).add(constraintWidget.mParent.horizontalRun.end);
                ((ArrayList) this.widget.mParent.horizontalRun.end.dependencies).add(dependencyNode);
            }
            addDependency(this.widget.horizontalRun.start);
            addDependency(this.widget.horizontalRun.end);
            return;
        }
        if (i != -1) {
            ((ArrayList) dependencyNode.targets).add(constraintWidget.mParent.verticalRun.start);
            ((ArrayList) this.widget.mParent.verticalRun.start.dependencies).add(dependencyNode);
            dependencyNode.margin = i;
        } else if (i2 != -1) {
            ((ArrayList) dependencyNode.targets).add(constraintWidget.mParent.verticalRun.end);
            ((ArrayList) this.widget.mParent.verticalRun.end.dependencies).add(dependencyNode);
            dependencyNode.margin = -i2;
        } else {
            dependencyNode.delegateToWidgetRun = true;
            ((ArrayList) dependencyNode.targets).add(constraintWidget.mParent.verticalRun.end);
            ((ArrayList) this.widget.mParent.verticalRun.end.dependencies).add(dependencyNode);
        }
        addDependency(this.widget.verticalRun.start);
        addDependency(this.widget.verticalRun.end);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public final void applyToWidget() {
        ConstraintWidget constraintWidget = this.widget;
        int i = ((Guideline) constraintWidget).mOrientation;
        DependencyNode dependencyNode = this.start;
        if (i == 1) {
            constraintWidget.f15mX = dependencyNode.value;
        } else {
            constraintWidget.f16mY = dependencyNode.value;
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
            dependencyNode.resolve((int) ((((DependencyNode) ((ArrayList) dependencyNode.targets).get(0)).value * ((Guideline) this.widget).mRelativePercent) + 0.5f));
        }
    }
}
