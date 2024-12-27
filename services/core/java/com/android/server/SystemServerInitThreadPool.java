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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SystemServerInitThreadPool implements Dumpable {
    public static final boolean IS_DEBUGGABLE = Build.IS_DEBUGGABLE;
    public static final Object LOCK = new Object();
    public static SystemServerInitThreadPool sInstance;
    public final List mPendingTasks = new ArrayList();
    public final ExecutorService mService;
    public boolean mShutDown;
    public final int mSize;

    public SystemServerInitThreadPool() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        this.mSize = availableProcessors;
        BootReceiver$$ExternalSyntheticOutline0.m(
                availableProcessors,
                "Creating instance with ",
                " threads",
                "SystemServerInitThreadPool");
        this.mService =
                ConcurrentUtils.newFixedThreadPool(
                        availableProcessors, "system-server-init-thread", -2);
    }

    public static void dumpStackTraces() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(Process.myPid()));
        StackTracesDumpHelper.dumpStackTraces(
                arrayList,
                null,
                null,
                CompletableFuture.completedFuture(Watchdog.getInterestingNativePids()),
                null,
                null,
                null,
                null,
                null,
                new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                null,
                null);
    }

    public static Future submit(final String str, final Runnable runnable) {
        final SystemServerInitThreadPool systemServerInitThreadPool;
        synchronized (LOCK) {
            Preconditions.checkState(
                    sInstance != null,
                    "Cannot get SystemServerInitThreadPool - it has been shut down");
            systemServerInitThreadPool = sInstance;
        }
        synchronized (systemServerInitThreadPool.mPendingTasks) {
            Preconditions.checkState(
                    !systemServerInitThreadPool.mShutDown,
                    "SystemServerInitThreadPool already shut down");
            ((ArrayList) systemServerInitThreadPool.mPendingTasks).add(str);
        }
        return systemServerInitThreadPool.mService.submit(
                new Runnable() { // from class:
                                 // com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SystemServerInitThreadPool systemServerInitThreadPool2 =
                                SystemServerInitThreadPool.this;
                        String str2 = str;
                        Runnable runnable2 = runnable;
                        systemServerInitThreadPool2.getClass();
                        TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
                        newAsyncLog.traceBegin("InitThreadPoolExec:" + str2);
                        boolean z = SystemServerInitThreadPool.IS_DEBUGGABLE;
                        if (z) {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    "Started executing ", str2, "SystemServerInitThreadPool");
                        }
                        try {
                            runnable2.run();
                            synchronized (systemServerInitThreadPool2.mPendingTasks) {
                                ((ArrayList) systemServerInitThreadPool2.mPendingTasks)
                                        .remove(str2);
                            }
                            if (z) {
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        "Finished executing ", str2, "SystemServerInitThreadPool");
                            }
                            newAsyncLog.traceEnd();
                        } catch (RuntimeException e) {
                            Slog.e(
                                    "SystemServerInitThreadPool",
                                    "Failure in " + str2 + ": " + e,
                                    e);
                            newAsyncLog.traceEnd();
                            throw e;
                        }
                    }
                });
    }

    @Override // android.util.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        synchronized (LOCK) {
            printWriter.printf("has instance: %b\n", Boolean.valueOf(sInstance != null));
        }
        printWriter.printf("number of threads: %d\n", Integer.valueOf(this.mSize));
        printWriter.printf("service: %s\n", this.mService);
        synchronized (this.mPendingTasks) {
            try {
                printWriter.printf("is shutdown: %b\n", Boolean.valueOf(this.mShutDown));
                int size = ((ArrayList) this.mPendingTasks).size();
                if (size == 0) {
                    printWriter.println("no pending tasks");
                } else {
                    printWriter.printf(
                            "%d pending tasks: %s\n", Integer.valueOf(size), this.mPendingTasks);
                }
            } finally {
            }
        }
    }

    @Override // android.util.Dumpable
    public final String getDumpableName() {
        return "SystemServerInitThreadPool";
    }
}
