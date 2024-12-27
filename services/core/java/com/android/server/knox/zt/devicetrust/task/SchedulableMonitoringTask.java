package com.android.server.knox.zt.devicetrust.task;

import android.os.Process;
import android.os.ThreadLocalWorkSource;
import android.util.Log;

import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;

import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public abstract class SchedulableMonitoringTask extends MonitoringTask {
    public static final long DEFAULT_INIT_DELAY_MS = 100;
    public static final long DEFAULT_PERIOD_MS = 10;
    public final ScheduledExecutorService mScheduler;

    public SchedulableMonitoringTask(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            IEndpointMonitorListener iEndpointMonitorListener,
            Predicate predicate,
            EndpointMonitorImpl.Injector injector) {
        super(i, i2, i3, i4, i5, iEndpointMonitorListener, predicate, injector);
        this.mScheduler = initScheduler();
    }

    public static /* synthetic */ void lambda$createThreadFactory$0(Runnable runnable) {
        ThreadLocalWorkSource.setUid(Process.myUid());
        runnable.run();
    }

    public final ThreadFactory createThreadFactory() {
        return new SchedulableMonitoringTask$$ExternalSyntheticLambda0(this);
    }

    public final ScheduledExecutorService initScheduler() {
        return Executors.newSingleThreadScheduledExecutor(
                new SchedulableMonitoringTask$$ExternalSyntheticLambda0(this));
    }

    public final Thread lambda$createThreadFactory$1(final Runnable runnable) {
        Thread thread =
                new Thread(
                        new Runnable() { // from class:
                                         // com.android.server.knox.zt.devicetrust.task.SchedulableMonitoringTask$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SchedulableMonitoringTask.lambda$createThreadFactory$0(runnable);
                            }
                        },
                        getTag());
        thread.setPriority(5);
        thread.setDaemon(true);
        onCreated();
        return thread;
    }

    public final void onCreated() {
        Log.i(getTag(), "Task created");
    }

    public void schedule() {
        this.mScheduler.scheduleAtFixedRate(this, 100L, 10L, TimeUnit.MILLISECONDS);
    }

    public final void terminate() {
        this.mScheduler.shutdownNow();
    }
}
