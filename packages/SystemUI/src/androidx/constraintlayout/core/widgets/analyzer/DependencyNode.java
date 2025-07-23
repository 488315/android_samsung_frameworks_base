package androidx.constraintlayout.core.widgets.analyzer;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DependencyNode implements Dependency {
    public int mMargin;
    public final WidgetRun mRun;
    public int value;
    public WidgetRun updateDelegate = null;
    public boolean delegateToWidgetRun = false;
    public boolean readyToSolve = false;
    public Type mType = Type.UNKNOWN;
    public int mMarginFactor = 1;
    public DimensionDependency mMarginDependency = null;
    public boolean resolved = false;
    public final List mDependencies = new ArrayList();
    public final List mTargets = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum Type {
        UNKNOWN,
        HORIZONTAL_DIMENSION,
        VERTICAL_DIMENSION,
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        BASELINE
    }

    public DependencyNode(WidgetRun widgetRun) {
        this.mRun = widgetRun;
    }

    public final void addDependency(WidgetRun widgetRun) {
        ((ArrayList) this.mDependencies).add(widgetRun);
        if (this.resolved) {
            widgetRun.update(widgetRun);
        }
    }

    public final void clear() {
        ((ArrayList) this.mTargets).clear();
        ((ArrayList) this.mDependencies).clear();
        this.resolved = false;
        this.value = 0;
        this.readyToSolve = false;
        this.delegateToWidgetRun = false;
    }

    public void resolve(int i) {
        if (this.resolved) {
            return;
        }
        this.resolved = true;
        this.value = i;
        ArrayList arrayList = (ArrayList) this.mDependencies;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            Dependency dependency = (Dependency) obj;
            dependency.update(dependency);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mRun.mWidget.mDebugName);
        sb.append(":");
        sb.append(this.mType);
        sb.append("(");
        sb.append(this.resolved ? Integer.valueOf(this.value) : "unresolved");
        sb.append(") <t=");
        sb.append(((ArrayList) this.mTargets).size());
        sb.append(":d=");
        sb.append(((ArrayList) this.mDependencies).size());
        sb.append(">");
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.Dependency
    public final void update(Dependency dependency) {
        ArrayList arrayList = (ArrayList) this.mTargets;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            if (!((DependencyNode) obj).resolved) {
                return;
            }
        }
        this.readyToSolve = true;
        WidgetRun widgetRun = this.updateDelegate;
        if (widgetRun != null) {
            widgetRun.update(this);
        }
        if (this.delegateToWidgetRun) {
            this.mRun.update(this);
            return;
        }
        ArrayList arrayList2 = (ArrayList) this.mTargets;
        int size2 = arrayList2.size();
        DependencyNode dependencyNode = null;
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList2.get(i3);
            i3++;
            DependencyNode dependencyNode2 = (DependencyNode) obj2;
            if (!(dependencyNode2 instanceof DimensionDependency)) {
                i++;
                dependencyNode = dependencyNode2;
            }
        }
        if (dependencyNode != null && i == 1 && dependencyNode.resolved) {
            DimensionDependency dimensionDependency = this.mMarginDependency;
            if (dimensionDependency != null) {
                if (!dimensionDependency.resolved) {
                    return;
                } else {
                    this.mMargin = this.mMarginFactor * dimensionDependency.value;
                }
            }
            resolve(dependencyNode.value + this.mMargin);
        }
        WidgetRun widgetRun2 = this.updateDelegate;
        if (widgetRun2 != null) {
            widgetRun2.update(this);
        }
    }
}
