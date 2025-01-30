package androidx.constraintlayout.core.widgets.analyzer;

import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RunGroup {
    public final WidgetRun firstRun;
    public final ArrayList runs = new ArrayList();

    public RunGroup(WidgetRun widgetRun, int i) {
        this.firstRun = null;
        this.firstRun = widgetRun;
    }

    public static long traverseEnd(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.run;
        if (widgetRun instanceof HelperReferences) {
            return j;
        }
        ArrayList arrayList = (ArrayList) dependencyNode.dependencies;
        int size = arrayList.size();
        long j2 = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = (Dependency) arrayList.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.run != widgetRun) {
                    j2 = Math.min(j2, traverseEnd(dependencyNode2, dependencyNode2.margin + j));
                }
            }
        }
        if (dependencyNode != widgetRun.end) {
            return j2;
        }
        long wrapDimension = widgetRun.getWrapDimension();
        long j3 = j - wrapDimension;
        return Math.min(Math.min(j2, traverseEnd(widgetRun.start, j3)), j3 - r9.margin);
    }

    public static long traverseStart(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.run;
        if (widgetRun instanceof HelperReferences) {
            return j;
        }
        ArrayList arrayList = (ArrayList) dependencyNode.dependencies;
        int size = arrayList.size();
        long j2 = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = (Dependency) arrayList.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.run != widgetRun) {
                    j2 = Math.max(j2, traverseStart(dependencyNode2, dependencyNode2.margin + j));
                }
            }
        }
        if (dependencyNode != widgetRun.start) {
            return j2;
        }
        long wrapDimension = widgetRun.getWrapDimension();
        long j3 = j + wrapDimension;
        return Math.max(Math.max(j2, traverseStart(widgetRun.end, j3)), j3 - r9.margin);
    }
}
