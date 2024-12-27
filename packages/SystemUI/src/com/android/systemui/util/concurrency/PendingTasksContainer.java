package com.android.systemui.util.concurrency;

import android.os.Trace;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class PendingTasksContainer {
    public static final int $stable = 8;
    private volatile AtomicInteger pendingTasksCount = new AtomicInteger(0);
    private volatile AtomicReference<Runnable> completionCallback = new AtomicReference<>();

    public final int getPendingCount() {
        return this.pendingTasksCount.get();
    }

    public final void onTasksComplete(Runnable runnable) {
        Runnable andSet;
        this.completionCallback.set(runnable);
        if (this.pendingTasksCount.get() != 0 || (andSet = this.completionCallback.getAndSet(null)) == null) {
            return;
        }
        andSet.run();
    }

    public final Runnable registerTask(final String str) {
        this.pendingTasksCount.incrementAndGet();
        Trace.beginAsyncSection("PendingTasksContainer#".concat(str), 0);
        return new Runnable() { // from class: com.android.systemui.util.concurrency.PendingTasksContainer$registerTask$1
            @Override // java.lang.Runnable
            public final void run() {
                AtomicInteger atomicInteger;
                AtomicReference atomicReference;
                Trace.endAsyncSection("PendingTasksContainer#" + str, 0);
                atomicInteger = this.pendingTasksCount;
                if (atomicInteger.decrementAndGet() == 0) {
                    atomicReference = this.completionCallback;
                    Runnable runnable = (Runnable) atomicReference.getAndSet(null);
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }
        };
    }

    public final void reset() {
        this.completionCallback = new AtomicReference<>();
        this.pendingTasksCount = new AtomicInteger(0);
    }
}
