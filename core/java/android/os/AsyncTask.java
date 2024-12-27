package android.os;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Deprecated
public abstract class AsyncTask<Params, Progress, Result> {
    private static final int BACKUP_POOL_SIZE = 5;
    private static final int CORE_POOL_SIZE = 1;
    private static final int KEEP_ALIVE_SECONDS = 3;
    private static final String LOG_TAG = "AsyncTask";
    private static final int MAXIMUM_POOL_SIZE = 20;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;

    @Deprecated public static final Executor SERIAL_EXECUTOR;

    @Deprecated public static final Executor THREAD_POOL_EXECUTOR;
    private static ThreadPoolExecutor sBackupExecutor;
    private static LinkedBlockingQueue<Runnable> sBackupExecutorQueue;
    private static volatile Executor sDefaultExecutor;
    private static InternalHandler sHandler;
    private final AtomicBoolean mCancelled;
    private final FutureTask<Result> mFuture;
    private final Handler mHandler;
    private volatile Status mStatus;
    private final AtomicBoolean mTaskInvoked;
    private final WorkerRunnable<Params, Result> mWorker;
    private static final ThreadFactory sThreadFactory =
            new ThreadFactory() { // from class: android.os.AsyncTask.1
                private final AtomicInteger mCount = new AtomicInteger(1);

                @Override // java.util.concurrent.ThreadFactory
                public Thread newThread(Runnable r) {
                    return new Thread(r, "AsyncTask #" + this.mCount.getAndIncrement());
                }
            };
    private static final RejectedExecutionHandler sRunOnSerialPolicy =
            new RejectedExecutionHandler() { // from class: android.os.AsyncTask.2
                @Override // java.util.concurrent.RejectedExecutionHandler
                public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                    Log.w(AsyncTask.LOG_TAG, "Exceeded ThreadPoolExecutor pool size");
                    synchronized (this) {
                        if (AsyncTask.sBackupExecutor == null) {
                            AsyncTask.sBackupExecutorQueue = new LinkedBlockingQueue();
                            AsyncTask.sBackupExecutor =
                                    new ThreadPoolExecutor(
                                            5,
                                            5,
                                            3L,
                                            TimeUnit.SECONDS,
                                            AsyncTask.sBackupExecutorQueue,
                                            AsyncTask.sThreadFactory);
                            AsyncTask.sBackupExecutor.allowCoreThreadTimeOut(true);
                        }
                    }
                    AsyncTask.sBackupExecutor.execute(r);
                }
            };

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    protected abstract Result doInBackground(Params... paramsArr);

    static {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(
                        1, 20, 3L, TimeUnit.SECONDS, new SynchronousQueue(), sThreadFactory);
        threadPoolExecutor.setRejectedExecutionHandler(sRunOnSerialPolicy);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
        SERIAL_EXECUTOR = new SerialExecutor();
        sDefaultExecutor = SERIAL_EXECUTOR;
    }

    private static class SerialExecutor implements Executor {
        Runnable mActive;
        final ArrayDeque<Runnable> mTasks;

        private SerialExecutor() {
            this.mTasks = new ArrayDeque<>();
        }

        @Override // java.util.concurrent.Executor
        public synchronized void execute(final Runnable r) {
            this.mTasks.offer(
                    new Runnable() { // from class: android.os.AsyncTask.SerialExecutor.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                r.run();
                            } finally {
                                SerialExecutor.this.scheduleNext();
                            }
                        }
                    });
            if (this.mActive == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            Runnable poll = this.mTasks.poll();
            this.mActive = poll;
            if (poll != null) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(this.mActive);
            }
        }
    }

    private static Handler getMainHandler() {
        InternalHandler internalHandler;
        synchronized (AsyncTask.class) {
            if (sHandler == null) {
                sHandler = new InternalHandler(Looper.getMainLooper());
            }
            internalHandler = sHandler;
        }
        return internalHandler;
    }

    private Handler getHandler() {
        return this.mHandler;
    }

    public static void setDefaultExecutor(Executor exec) {
        sDefaultExecutor = exec;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AsyncTask() {
        this((Looper) null);
    }

    public AsyncTask(Handler handler) {
        this(handler != null ? handler.getLooper() : null);
    }

    public AsyncTask(Looper callbackLooper) {
        Handler mainHandler;
        this.mStatus = Status.PENDING;
        this.mCancelled = new AtomicBoolean();
        this.mTaskInvoked = new AtomicBoolean();
        if (callbackLooper == null || callbackLooper == Looper.getMainLooper()) {
            mainHandler = getMainHandler();
        } else {
            mainHandler = new Handler(callbackLooper);
        }
        this.mHandler = mainHandler;
        this.mWorker =
                new WorkerRunnable<Params, Result>() { // from class: android.os.AsyncTask.3
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.concurrent.Callable
                    public Result call() throws Exception {
                        AsyncTask.this.mTaskInvoked.set(true);
                        Result result = null;
                        try {
                            Process.setThreadPriority(10);
                            result = AsyncTask.this.doInBackground(this.mParams);
                            Binder.flushPendingCommands();
                            return result;
                        } finally {
                        }
                    }
                };
        this.mFuture =
                new FutureTask<Result>(this.mWorker) { // from class: android.os.AsyncTask.4
                    @Override // java.util.concurrent.FutureTask
                    protected void done() {
                        try {
                            AsyncTask.this.postResultIfNotInvoked(get());
                        } catch (InterruptedException e) {
                            Log.w(AsyncTask.LOG_TAG, e);
                        } catch (CancellationException e2) {
                            AsyncTask.this.postResultIfNotInvoked(null);
                        } catch (ExecutionException e3) {
                            throw new RuntimeException(
                                    "An error occurred while executing doInBackground()",
                                    e3.getCause());
                        }
                    }
                };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postResultIfNotInvoked(Result result) {
        boolean wasTaskInvoked = this.mTaskInvoked.get();
        if (!wasTaskInvoked) {
            postResult(result);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Result postResult(Result result) {
        Message message = getHandler().obtainMessage(1, new AsyncTaskResult(this, result));
        message.sendToTarget();
        return result;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    protected void onPreExecute() {}

    protected void onPostExecute(Result result) {}

    protected void onProgressUpdate(Progress... values) {}

    protected void onCancelled(Result result) {
        onCancelled();
    }

    protected void onCancelled() {}

    public final boolean isCancelled() {
        return this.mCancelled.get();
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        this.mCancelled.set(true);
        return this.mFuture.cancel(mayInterruptIfRunning);
    }

    public final Result get() throws InterruptedException, ExecutionException {
        return this.mFuture.get();
    }

    public final Result get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return this.mFuture.get(timeout, unit);
    }

    public final AsyncTask<Params, Progress, Result> execute(Params... params) {
        return executeOnExecutor(sDefaultExecutor, params);
    }

    public final AsyncTask<Params, Progress, Result> executeOnExecutor(
            Executor exec, Params... params) {
        if (this.mStatus != Status.PENDING) {
            switch (this.mStatus.ordinal()) {
                case 1:
                    throw new IllegalStateException(
                            "Cannot execute task: the task is already running.");
                case 2:
                    throw new IllegalStateException(
                            "Cannot execute task: the task has already been executed (a task can be"
                                + " executed only once)");
            }
        }
        this.mStatus = Status.RUNNING;
        onPreExecute();
        this.mWorker.mParams = params;
        exec.execute(this.mFuture);
        return this;
    }

    public static void execute(Runnable runnable) {
        sDefaultExecutor.execute(runnable);
    }

    protected final void publishProgress(Progress... values) {
        if (!isCancelled()) {
            getHandler().obtainMessage(2, new AsyncTaskResult(this, values)).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
        this.mStatus = Status.FINISHED;
    }

    private static class InternalHandler extends Handler {
        public InternalHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            AsyncTaskResult<?> result = (AsyncTaskResult) msg.obj;
            switch (msg.what) {
                case 1:
                    result.mTask.finish(result.mData[0]);
                    break;
                case 2:
                    result.mTask.onProgressUpdate(result.mData);
                    break;
            }
        }
    }

    private abstract static class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;

        private WorkerRunnable() {}
    }

    private static class AsyncTaskResult<Data> {
        final Data[] mData;
        final AsyncTask mTask;

        AsyncTaskResult(AsyncTask task, Data... data) {
            this.mTask = task;
            this.mData = data;
        }
    }
}
