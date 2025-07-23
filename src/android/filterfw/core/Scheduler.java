package android.filterfw.core;

/* loaded from: classes.dex */
public abstract class Scheduler {
    private FilterGraph mGraph;

    boolean finished() {
        return true;
    }

    abstract void reset();

    abstract Filter scheduleNextNode();

    Scheduler(FilterGraph filterGraph) {
        this.mGraph = filterGraph;
    }

    FilterGraph getGraph() {
        return this.mGraph;
    }
}
