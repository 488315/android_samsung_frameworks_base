package androidx.constraintlayout.core.widgets.analyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DependencyNode implements Dependency {
    public int margin;
    public final WidgetRun run;
    public int value;
    public WidgetRun updateDelegate = null;
    public boolean delegateToWidgetRun = false;
    public boolean readyToSolve = false;
    public Type type = Type.UNKNOWN;
    public int marginFactor = 1;
    public DimensionDependency marginDependency = null;
    public boolean resolved = false;
    public final List dependencies = new ArrayList();
    public final List targets = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.run = widgetRun;
    }

    public final void addDependency(Dependency dependency) {
        ((ArrayList) this.dependencies).add(dependency);
        if (this.resolved) {
            dependency.update(dependency);
        }
    }

    public final void clear() {
        ((ArrayList) this.targets).clear();
        ((ArrayList) this.dependencies).clear();
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
        Iterator it = ((ArrayList) this.dependencies).iterator();
        while (it.hasNext()) {
            Dependency dependency = (Dependency) it.next();
            dependency.update(dependency);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.run.widget.mDebugName);
        sb.append(":");
        sb.append(this.type);
        sb.append("(");
        sb.append(this.resolved ? Integer.valueOf(this.value) : "unresolved");
        sb.append(") <t=");
        sb.append(((ArrayList) this.targets).size());
        sb.append(":d=");
        sb.append(((ArrayList) this.dependencies).size());
        sb.append(">");
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.Dependency
    public final void update(Dependency dependency) {
        ArrayList arrayList = (ArrayList) this.targets;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (!((DependencyNode) it.next()).resolved) {
                return;
            }
        }
        this.readyToSolve = true;
        WidgetRun widgetRun = this.updateDelegate;
        if (widgetRun != null) {
            widgetRun.update(this);
        }
        if (this.delegateToWidgetRun) {
            this.run.update(this);
            return;
        }
        Iterator it2 = arrayList.iterator();
        DependencyNode dependencyNode = null;
        int i = 0;
        while (it2.hasNext()) {
            DependencyNode dependencyNode2 = (DependencyNode) it2.next();
            if (!(dependencyNode2 instanceof DimensionDependency)) {
                i++;
                dependencyNode = dependencyNode2;
            }
        }
        if (dependencyNode != null && i == 1 && dependencyNode.resolved) {
            DimensionDependency dimensionDependency = this.marginDependency;
            if (dimensionDependency != null) {
                if (!dimensionDependency.resolved) {
                    return;
                } else {
                    this.margin = this.marginFactor * dimensionDependency.value;
                }
            }
            resolve(dependencyNode.value + this.margin);
        }
        WidgetRun widgetRun2 = this.updateDelegate;
        if (widgetRun2 != null) {
            widgetRun2.update(this);
        }
    }
}
