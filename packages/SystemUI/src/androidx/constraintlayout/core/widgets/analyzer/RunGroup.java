package androidx.constraintlayout.core.widgets.analyzer;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class RunGroup {
    public final WidgetRun mFirstRun;
    public final ArrayList mRuns = new ArrayList();

    public RunGroup(WidgetRun widgetRun, int i) {
        this.mFirstRun = null;
        this.mFirstRun = widgetRun;
    }

    public static long traverseEnd(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.mRun;
        if (widgetRun instanceof HelperReferences) {
            return j;
        }
        int size = ((ArrayList) dependencyNode.mDependencies).size();
        long jMin = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = (Dependency) ((ArrayList) dependencyNode.mDependencies).get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.mRun != widgetRun) {
                    jMin = Math.min(jMin, traverseEnd(dependencyNode2, dependencyNode2.mMargin + j));
                }
            }
        }
        if (dependencyNode != widgetRun.end) {
            return jMin;
        }
        long wrapDimension = widgetRun.getWrapDimension();
        long j2 = j - wrapDimension;
        return Math.min(Math.min(jMin, traverseEnd(widgetRun.start, j2)), j2 - r8.mMargin);
    }

    public static long traverseStart(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.mRun;
        if (widgetRun instanceof HelperReferences) {
            return j;
        }
        int size = ((ArrayList) dependencyNode.mDependencies).size();
        long jMax = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = (Dependency) ((ArrayList) dependencyNode.mDependencies).get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.mRun != widgetRun) {
                    jMax = Math.max(jMax, traverseStart(dependencyNode2, dependencyNode2.mMargin + j));
                }
            }
        }
        if (dependencyNode != widgetRun.start) {
            return jMax;
        }
        long wrapDimension = widgetRun.getWrapDimension();
        long j2 = j + wrapDimension;
        return Math.max(Math.max(jMax, traverseStart(widgetRun.end, j2)), j2 - r8.mMargin);
    }
}
