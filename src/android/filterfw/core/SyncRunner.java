package android.filterfw.core;

import android.filterfw.core.GraphRunner;
import android.os.ConditionVariable;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class SyncRunner extends GraphRunner {
    private static final String TAG = "SyncRunner";
    private GraphRunner.OnRunnerDoneListener mDoneListener;
    private final boolean mLogVerbose;
    private Scheduler mScheduler;
    private StopWatchMap mTimer;
    private ConditionVariable mWakeCondition;
    private ScheduledThreadPoolExecutor mWakeExecutor;

    @Override // android.filterfw.core.GraphRunner
    public boolean isRunning() {
        return false;
    }

    public SyncRunner(FilterContext filterContext, FilterGraph filterGraph, Class cls) {
        super(filterContext);
        this.mScheduler = null;
        this.mDoneListener = null;
        this.mWakeExecutor = new ScheduledThreadPoolExecutor(1);
        this.mWakeCondition = new ConditionVariable();
        this.mTimer = null;
        boolean isLoggable = Log.isLoggable(TAG, 2);
        this.mLogVerbose = isLoggable;
        if (isLoggable) {
            Log.v(TAG, "Initializing SyncRunner");
        }
        if (Scheduler.class.isAssignableFrom(cls)) {
            try {
                this.mScheduler = (Scheduler) cls.getConstructor(FilterGraph.class).newInstance(filterGraph);
                this.mFilterContext = filterContext;
                this.mFilterContext.addGraph(filterGraph);
                this.mTimer = new StopWatchMap();
                if (isLoggable) {
                    Log.v(TAG, "Setting up filters");
                }
                filterGraph.setupFilters();
                return;
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot access Scheduler constructor!", e);
            } catch (InstantiationException e2) {
                throw new RuntimeException("Could not instantiate the Scheduler instance!", e2);
            } catch (NoSuchMethodException e3) {
                throw new RuntimeException("Scheduler does not have constructor <init>(FilterGraph)!", e3);
            } catch (InvocationTargetException e4) {
                throw new RuntimeException("Scheduler constructor threw an exception", e4);
            } catch (Exception e5) {
                throw new RuntimeException("Could not instantiate Scheduler", e5);
            }
        }
        throw new IllegalArgumentException("Class provided is not a Scheduler subclass!");
    }

    @Override // android.filterfw.core.GraphRunner
    public FilterGraph getGraph() {
        Scheduler scheduler = this.mScheduler;
        if (scheduler != null) {
            return scheduler.getGraph();
        }
        return null;
    }

    public int step() {
        assertReadyToStep();
        if (!getGraph().isReady()) {
            throw new RuntimeException("Trying to process graph that is not open!");
        }
        if (performStep()) {
            return 1;
        }
        return determinePostRunState();
    }

    public void beginProcessing() {
        this.mScheduler.reset();
        getGraph().beginProcessing();
    }

    @Override // android.filterfw.core.GraphRunner
    public void close() {
        if (this.mLogVerbose) {
            Log.v(TAG, "Closing graph.");
        }
        getGraph().closeFilters(this.mFilterContext);
        this.mScheduler.reset();
    }

    @Override // android.filterfw.core.GraphRunner
    public void run() {
        if (this.mLogVerbose) {
            Log.v(TAG, "Beginning run.");
        }
        assertReadyToStep();
        beginProcessing();
        boolean activateGlContext = activateGlContext();
        boolean z = true;
        while (z) {
            z = performStep();
        }
        if (activateGlContext) {
            deactivateGlContext();
        }
        if (this.mDoneListener != null) {
            if (this.mLogVerbose) {
                Log.v(TAG, "Calling completion listener.");
            }
            this.mDoneListener.onRunnerDone(determinePostRunState());
        }
        if (this.mLogVerbose) {
            Log.v(TAG, "Run complete");
        }
    }

    @Override // android.filterfw.core.GraphRunner
    public void setDoneCallback(GraphRunner.OnRunnerDoneListener onRunnerDoneListener) {
        this.mDoneListener = onRunnerDoneListener;
    }

    @Override // android.filterfw.core.GraphRunner
    public void stop() {
        throw new RuntimeException("SyncRunner does not support stopping a graph!");
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized Exception getError() {
        return null;
    }

    protected void waitUntilWake() {
        this.mWakeCondition.block();
    }

    protected void processFilterNode(Filter filter) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Processing filter node");
        }
        filter.performProcess(this.mFilterContext);
        if (filter.getStatus() == 6) {
            throw new RuntimeException("There was an error executing " + filter + "!");
        }
        if (filter.getStatus() == 4) {
            if (this.mLogVerbose) {
                Log.v(TAG, "Scheduling filter wakeup");
            }
            scheduleFilterWake(filter, filter.getSleepDelay());
        }
    }

    protected void scheduleFilterWake(final Filter filter, int i) {
        this.mWakeCondition.close();
        final ConditionVariable conditionVariable = this.mWakeCondition;
        this.mWakeExecutor.schedule(new Runnable(this) { // from class: android.filterfw.core.SyncRunner.1
            @Override // java.lang.Runnable
            public void run() {
                filter.unsetStatus(4);
                conditionVariable.open();
            }
        }, i, TimeUnit.MILLISECONDS);
    }

    protected int determinePostRunState() {
        for (Filter filter : this.mScheduler.getGraph().getFilters()) {
            if (filter.isOpen()) {
                return filter.getStatus() == 4 ? 3 : 4;
            }
        }
        return 2;
    }

    boolean performStep() {
        if (this.mLogVerbose) {
            Log.v(TAG, "Performing one step.");
        }
        Filter scheduleNextNode = this.mScheduler.scheduleNextNode();
        if (scheduleNextNode == null) {
            return false;
        }
        this.mTimer.start(scheduleNextNode.getName());
        processFilterNode(scheduleNextNode);
        this.mTimer.stop(scheduleNextNode.getName());
        return true;
    }

    void assertReadyToStep() {
        if (this.mScheduler == null) {
            throw new RuntimeException("Attempting to run schedule with no scheduler in place!");
        }
        if (getGraph() == null) {
            throw new RuntimeException("Calling step on scheduler with no graph in place!");
        }
    }
}
