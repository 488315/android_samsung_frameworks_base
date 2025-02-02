package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;

/* loaded from: classes.dex */
final class RunGroup {
    WidgetRun mFirstRun;
    ArrayList<WidgetRun> mRuns = new ArrayList<>();

    RunGroup(WidgetRun widgetRun) {
        this.mFirstRun = null;
        this.mFirstRun = widgetRun;
    }

    private static long traverseEnd(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.mRun;
        if (widgetRun instanceof HelperReferences) {
            return j;
        }
        int size = ((ArrayList) dependencyNode.mDependencies).size();
        long j2 = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = (Dependency) ((ArrayList) dependencyNode.mDependencies).get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.mRun != widgetRun) {
                    j2 = Math.min(j2, traverseEnd(dependencyNode2, dependencyNode2.mMargin + j));
                }
            }
        }
        if (dependencyNode != widgetRun.end) {
            return j2;
        }
        long wrapDimension = j - widgetRun.getWrapDimension();
        return Math.min(Math.min(j2, traverseEnd(widgetRun.start, wrapDimension)), wrapDimension - widgetRun.start.mMargin);
    }

    private static long traverseStart(DependencyNode dependencyNode, long j) {
        WidgetRun widgetRun = dependencyNode.mRun;
        if (widgetRun instanceof HelperReferences) {
            return j;
        }
        int size = ((ArrayList) dependencyNode.mDependencies).size();
        long j2 = j;
        for (int i = 0; i < size; i++) {
            Dependency dependency = (Dependency) ((ArrayList) dependencyNode.mDependencies).get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency;
                if (dependencyNode2.mRun != widgetRun) {
                    j2 = Math.max(j2, traverseStart(dependencyNode2, dependencyNode2.mMargin + j));
                }
            }
        }
        if (dependencyNode != widgetRun.start) {
            return j2;
        }
        long wrapDimension = j + widgetRun.getWrapDimension();
        return Math.max(Math.max(j2, traverseStart(widgetRun.end, wrapDimension)), wrapDimension - widgetRun.end.mMargin);
    }

    public final long computeWrapSize(ConstraintWidgetContainer constraintWidgetContainer, int i) {
        long wrapDimension;
        int i2;
        WidgetRun widgetRun = this.mFirstRun;
        if (widgetRun instanceof ChainRun) {
            if (((ChainRun) widgetRun).orientation != i) {
                return 0L;
            }
        } else if (i == 0) {
            if (!(widgetRun instanceof HorizontalWidgetRun)) {
                return 0L;
            }
        } else if (!(widgetRun instanceof VerticalWidgetRun)) {
            return 0L;
        }
        DependencyNode dependencyNode = (i == 0 ? constraintWidgetContainer.mHorizontalRun : constraintWidgetContainer.mVerticalRun).start;
        DependencyNode dependencyNode2 = (i == 0 ? constraintWidgetContainer.mHorizontalRun : constraintWidgetContainer.mVerticalRun).end;
        boolean contains = ((ArrayList) widgetRun.start.mTargets).contains(dependencyNode);
        boolean contains2 = ((ArrayList) this.mFirstRun.end.mTargets).contains(dependencyNode2);
        long wrapDimension2 = this.mFirstRun.getWrapDimension();
        if (contains && contains2) {
            long traverseStart = traverseStart(this.mFirstRun.start, 0L);
            long traverseEnd = traverseEnd(this.mFirstRun.end, 0L);
            long j = traverseStart - wrapDimension2;
            WidgetRun widgetRun2 = this.mFirstRun;
            int i3 = widgetRun2.end.mMargin;
            if (j >= (-i3)) {
                j += i3;
            }
            long j2 = widgetRun2.start.mMargin;
            long j3 = ((-traverseEnd) - wrapDimension2) - j2;
            if (j3 >= j2) {
                j3 -= j2;
            }
            float biasPercent = widgetRun2.mWidget.getBiasPercent(i);
            float f = biasPercent > 0.0f ? (long) ((j / (1.0f - biasPercent)) + (j3 / biasPercent)) : 0L;
            long m3m = ((long) ((f * biasPercent) + 0.5f)) + wrapDimension2 + ((long) RunGroup$$ExternalSyntheticOutline0.m3m(1.0f, biasPercent, f, 0.5f));
            wrapDimension = r11.start.mMargin + m3m;
            i2 = this.mFirstRun.end.mMargin;
        } else {
            if (contains) {
                return Math.max(traverseStart(this.mFirstRun.start, r12.mMargin), this.mFirstRun.start.mMargin + wrapDimension2);
            }
            if (contains2) {
                return Math.max(-traverseEnd(this.mFirstRun.end, r12.mMargin), (-this.mFirstRun.end.mMargin) + wrapDimension2);
            }
            wrapDimension = this.mFirstRun.getWrapDimension() + r12.start.mMargin;
            i2 = this.mFirstRun.end.mMargin;
        }
        return wrapDimension - i2;
    }
}
