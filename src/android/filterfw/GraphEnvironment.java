package android.filterfw;

import android.content.Context;
import android.filterfw.core.AsyncRunner;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GraphRunner;
import android.filterfw.core.RoundRobinScheduler;
import android.filterfw.core.SyncRunner;
import android.filterfw.io.GraphIOException;
import android.filterfw.io.GraphReader;
import android.filterfw.io.TextGraphReader;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class GraphEnvironment extends MffEnvironment {
    public static final int MODE_ASYNCHRONOUS = 1;
    public static final int MODE_SYNCHRONOUS = 2;
    private GraphReader mGraphReader;
    private ArrayList<GraphHandle> mGraphs;

    private class GraphHandle {
        private AsyncRunner mAsyncRunner;
        private FilterGraph mGraph;
        private SyncRunner mSyncRunner;

        public GraphHandle(GraphEnvironment graphEnvironment, FilterGraph filterGraph) {
            this.mGraph = filterGraph;
        }

        public FilterGraph getGraph() {
            return this.mGraph;
        }

        public AsyncRunner getAsyncRunner(FilterContext filterContext) {
            if (this.mAsyncRunner == null) {
                AsyncRunner asyncRunner = new AsyncRunner(filterContext, RoundRobinScheduler.class);
                this.mAsyncRunner = asyncRunner;
                asyncRunner.setGraph(this.mGraph);
            }
            return this.mAsyncRunner;
        }

        public GraphRunner getSyncRunner(FilterContext filterContext) {
            if (this.mSyncRunner == null) {
                this.mSyncRunner = new SyncRunner(filterContext, this.mGraph, RoundRobinScheduler.class);
            }
            return this.mSyncRunner;
        }
    }

    public GraphEnvironment() {
        super(null);
        this.mGraphs = new ArrayList<>();
    }

    public GraphEnvironment(FrameManager frameManager, GraphReader graphReader) {
        super(frameManager);
        this.mGraphs = new ArrayList<>();
        this.mGraphReader = graphReader;
    }

    public GraphReader getGraphReader() {
        if (this.mGraphReader == null) {
            this.mGraphReader = new TextGraphReader();
        }
        return this.mGraphReader;
    }

    public void addReferences(Object... objArr) {
        getGraphReader().addReferencesByKeysAndValues(objArr);
    }

    public int loadGraph(Context context, int i) {
        try {
            return addGraph(getGraphReader().readGraphResource(context, i));
        } catch (GraphIOException e) {
            throw new RuntimeException("Could not read graph: " + e.getMessage());
        }
    }

    public int addGraph(FilterGraph filterGraph) {
        this.mGraphs.add(new GraphHandle(this, filterGraph));
        return this.mGraphs.size() - 1;
    }

    public FilterGraph getGraph(int i) {
        if (i < 0 || i >= this.mGraphs.size()) {
            throw new IllegalArgumentException("Invalid graph ID " + i + " specified in runGraph()!");
        }
        return this.mGraphs.get(i).getGraph();
    }

    public GraphRunner getRunner(int i, int i2) {
        if (i2 == 1) {
            return this.mGraphs.get(i).getAsyncRunner(getContext());
        }
        if (i2 == 2) {
            return this.mGraphs.get(i).getSyncRunner(getContext());
        }
        throw new RuntimeException("Invalid execution mode " + i2 + " specified in getRunner()!");
    }
}
