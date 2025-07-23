package android.filterfw.core;

import android.filterfw.core.GraphRunner;
import android.os.AsyncTask;
import android.util.Log;

/* loaded from: classes.dex */
public class AsyncRunner extends GraphRunner {
    private static final String TAG = "AsyncRunner";
    private boolean isProcessing;
    private GraphRunner.OnRunnerDoneListener mDoneListener;
    private Exception mException;
    private boolean mLogVerbose;
    private AsyncRunnerTask mRunTask;
    private SyncRunner mRunner;
    private Class mSchedulerClass;

    private class RunnerResult {
        public Exception exception;
        public int status;

        private RunnerResult(AsyncRunner asyncRunner) {
            this.status = 0;
        }
    }

    private class AsyncRunnerTask extends AsyncTask<SyncRunner, Void, RunnerResult> {
        private static final String TAG = "AsyncRunnerTask";

        private AsyncRunnerTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public RunnerResult doInBackground(SyncRunner... syncRunnerArr) {
            RunnerResult runnerResult = new RunnerResult();
            try {
            } catch (Exception e) {
                runnerResult.exception = e;
                runnerResult.status = 6;
            }
            if (syncRunnerArr.length > 1) {
                throw new RuntimeException("More than one runner received!");
            }
            syncRunnerArr[0].assertReadyToStep();
            if (AsyncRunner.this.mLogVerbose) {
                Log.v(TAG, "Starting background graph processing.");
            }
            AsyncRunner.this.activateGlContext();
            if (AsyncRunner.this.mLogVerbose) {
                Log.v(TAG, "Preparing filter graph for processing.");
            }
            syncRunnerArr[0].beginProcessing();
            if (AsyncRunner.this.mLogVerbose) {
                Log.v(TAG, "Running graph.");
            }
            runnerResult.status = 1;
            while (!isCancelled() && runnerResult.status == 1) {
                if (!syncRunnerArr[0].performStep()) {
                    runnerResult.status = syncRunnerArr[0].determinePostRunState();
                    if (runnerResult.status == 3) {
                        syncRunnerArr[0].waitUntilWake();
                        runnerResult.status = 1;
                    }
                }
            }
            if (isCancelled()) {
                runnerResult.status = 5;
            }
            try {
                AsyncRunner.this.deactivateGlContext();
            } catch (Exception e2) {
                runnerResult.exception = e2;
                runnerResult.status = 6;
            }
            if (AsyncRunner.this.mLogVerbose) {
                Log.v(TAG, "Done with background graph processing.");
            }
            return runnerResult;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onCancelled(RunnerResult runnerResult) {
            onPostExecute(runnerResult);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(RunnerResult runnerResult) {
            if (AsyncRunner.this.mLogVerbose) {
                Log.v(TAG, "Starting post-execute.");
            }
            AsyncRunner.this.setRunning(false);
            if (runnerResult == null) {
                runnerResult = new RunnerResult();
                runnerResult.status = 5;
            }
            AsyncRunner.this.setException(runnerResult.exception);
            if (runnerResult.status == 5 || runnerResult.status == 6) {
                if (AsyncRunner.this.mLogVerbose) {
                    Log.v(TAG, "Closing filters.");
                }
                try {
                    AsyncRunner.this.mRunner.close();
                } catch (Exception e) {
                    runnerResult.status = 6;
                    AsyncRunner.this.setException(e);
                }
            }
            if (AsyncRunner.this.mDoneListener != null) {
                if (AsyncRunner.this.mLogVerbose) {
                    Log.v(TAG, "Calling graph done callback.");
                }
                AsyncRunner.this.mDoneListener.onRunnerDone(runnerResult.status);
            }
            if (AsyncRunner.this.mLogVerbose) {
                Log.v(TAG, "Completed post-execute.");
            }
        }
    }

    public AsyncRunner(FilterContext filterContext, Class cls) {
        super(filterContext);
        this.mSchedulerClass = cls;
        this.mLogVerbose = Log.isLoggable(TAG, 2);
    }

    public AsyncRunner(FilterContext filterContext) {
        super(filterContext);
        this.mSchedulerClass = SimpleScheduler.class;
        this.mLogVerbose = Log.isLoggable(TAG, 2);
    }

    @Override // android.filterfw.core.GraphRunner
    public void setDoneCallback(GraphRunner.OnRunnerDoneListener onRunnerDoneListener) {
        this.mDoneListener = onRunnerDoneListener;
    }

    public synchronized void setGraph(FilterGraph filterGraph) {
        if (isRunning()) {
            throw new RuntimeException("Graph is already running!");
        }
        this.mRunner = new SyncRunner(this.mFilterContext, filterGraph, this.mSchedulerClass);
    }

    @Override // android.filterfw.core.GraphRunner
    public FilterGraph getGraph() {
        SyncRunner syncRunner = this.mRunner;
        if (syncRunner != null) {
            return syncRunner.getGraph();
        }
        return null;
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized void run() {
        if (this.mLogVerbose) {
            Log.v(TAG, "Running graph.");
        }
        setException(null);
        if (isRunning()) {
            throw new RuntimeException("Graph is already running!");
        }
        if (this.mRunner == null) {
            throw new RuntimeException("Cannot run before a graph is set!");
        }
        this.mRunTask = new AsyncRunnerTask();
        setRunning(true);
        this.mRunTask.execute(this.mRunner);
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized void stop() {
        AsyncRunnerTask asyncRunnerTask = this.mRunTask;
        if (asyncRunnerTask != null && !asyncRunnerTask.isCancelled()) {
            if (this.mLogVerbose) {
                Log.v(TAG, "Stopping graph.");
            }
            this.mRunTask.cancel(false);
        }
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized void close() {
        if (isRunning()) {
            throw new RuntimeException("Cannot close graph while it is running!");
        }
        if (this.mLogVerbose) {
            Log.v(TAG, "Closing filters.");
        }
        this.mRunner.close();
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized boolean isRunning() {
        return this.isProcessing;
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized Exception getError() {
        return this.mException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void setRunning(boolean z) {
        this.isProcessing = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void setException(Exception exc) {
        this.mException = exc;
    }
}
