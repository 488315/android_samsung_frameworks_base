package com.android.server;

import android.os.Build;
import android.os.Process;
import android.util.Dumpable;
import android.util.Slog;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.Preconditions;
import com.android.server.am.StackTracesDumpHelper;
import com.android.server.utils.TimingsTraceAndSlog;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class SystemServerInitThreadPool implements Dumpable {
    public static SystemServerInitThreadPool sInstance;
    public final List mPendingTasks = new ArrayList();
    public final ExecutorService mService;
    public boolean mShutDown;
    public final int mSize;
    public static final String TAG = SystemServerInitThreadPool.class.getSimpleName();
    public static final boolean IS_DEBUGGABLE = Build.IS_DEBUGGABLE;
    public static final Object LOCK = new Object();

    public SystemServerInitThreadPool() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        this.mSize = availableProcessors;
        Slog.i(TAG, "Creating instance with " + availableProcessors + " threads");
        this.mService = ConcurrentUtils.newFixedThreadPool(availableProcessors, "system-server-init-thread", -2);
    }

    public static Future submit(Runnable runnable, String str) {
        SystemServerInitThreadPool systemServerInitThreadPool;
        Objects.requireNonNull(str, "description cannot be null");
        synchronized (LOCK) {
            Preconditions.checkState(sInstance != null, "Cannot get " + TAG + " - it has been shut down");
            systemServerInitThreadPool = sInstance;
        }
        return systemServerInitThreadPool.submitTask(runnable, str);
    }

    public final Future submitTask(final Runnable runnable, final String str) {
        synchronized (this.mPendingTasks) {
            Preconditions.checkState(!this.mShutDown, TAG + " already shut down");
            this.mPendingTasks.add(str);
        }
        return this.mService.submit(new Runnable() { // from class: com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SystemServerInitThreadPool.this.lambda$submitTask$0(str, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submitTask$0(String str, Runnable runnable) {
        TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin("InitThreadPoolExec:" + str);
        boolean z = IS_DEBUGGABLE;
        if (z) {
            Slog.d(TAG, "Started executing " + str);
        }
        try {
            runnable.run();
            synchronized (this.mPendingTasks) {
                this.mPendingTasks.remove(str);
            }
            if (z) {
                Slog.d(TAG, "Finished executing " + str);
            }
            newAsyncLog.traceEnd();
        } catch (RuntimeException e) {
            Slog.e(TAG, "Failure in " + str + ": " + e, e);
            newAsyncLog.traceEnd();
            throw e;
        }
    }

    public static SystemServerInitThreadPool start() {
        SystemServerInitThreadPool systemServerInitThreadPool;
        synchronized (LOCK) {
            Preconditions.checkState(sInstance == null, TAG + " already started");
            systemServerInitThreadPool = new SystemServerInitThreadPool();
            sInstance = systemServerInitThreadPool;
        }
        return systemServerInitThreadPool;
    }

    public static void shutdown() {
        SystemServerInitThreadPool systemServerInitThreadPool;
        String str = TAG;
        Slog.d(str, "Shutdown requested");
        synchronized (LOCK) {
            TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
            timingsTraceAndSlog.traceBegin("WaitInitThreadPoolShutdown");
            SystemServerInitThreadPool systemServerInitThreadPool2 = sInstance;
            if (systemServerInitThreadPool2 == null) {
                timingsTraceAndSlog.traceEnd();
                Slog.wtf(str, "Already shutdown", new Exception());
                return;
            }
            synchronized (systemServerInitThreadPool2.mPendingTasks) {
                systemServerInitThreadPool = sInstance;
                systemServerInitThreadPool.mShutDown = true;
            }
            systemServerInitThreadPool.mService.shutdown();
            try {
                boolean awaitTermination = sInstance.mService.awaitTermination(20000L, TimeUnit.MILLISECONDS);
                if (!awaitTermination) {
                    dumpStackTraces();
                }
                List<Runnable> shutdownNow = sInstance.mService.shutdownNow();
                if (!awaitTermination) {
                    ArrayList arrayList = new ArrayList();
                    synchronized (sInstance.mPendingTasks) {
                        arrayList.addAll(sInstance.mPendingTasks);
                    }
                    timingsTraceAndSlog.traceEnd();
                    throw new IllegalStateException("Cannot shutdown. Unstarted tasks " + shutdownNow + " Unfinished tasks " + arrayList);
                }
                sInstance = null;
                Slog.d(str, "Shutdown successful");
                timingsTraceAndSlog.traceEnd();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                dumpStackTraces();
                timingsTraceAndSlog.traceEnd();
                throw new IllegalStateException(TAG + " init interrupted");
            }
        }
    }

    public static void dumpStackTraces() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(Process.myPid()));
        StackTracesDumpHelper.dumpStackTraces(arrayList, null, null, CompletableFuture.completedFuture(Watchdog.getInterestingNativePids()), null, null, null, new SystemServerInitThreadPool$$ExternalSyntheticLambda1(), null);
    }

    @Override // android.util.Dumpable
    public String getDumpableName() {
        return SystemServerInitThreadPool.class.getSimpleName();
    }

    @Override // android.util.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        synchronized (LOCK) {
            Object[] objArr = new Object[1];
            objArr[0] = Boolean.valueOf(sInstance != null);
            printWriter.printf("has instance: %b\n", objArr);
        }
        printWriter.printf("number of threads: %d\n", Integer.valueOf(this.mSize));
        printWriter.printf("service: %s\n", this.mService);
        synchronized (this.mPendingTasks) {
            printWriter.printf("is shutdown: %b\n", Boolean.valueOf(this.mShutDown));
            int size = this.mPendingTasks.size();
            if (size == 0) {
                printWriter.println("no pending tasks");
            } else {
                printWriter.printf("%d pending tasks: %s\n", Integer.valueOf(size), this.mPendingTasks);
            }
        }
    }
}
