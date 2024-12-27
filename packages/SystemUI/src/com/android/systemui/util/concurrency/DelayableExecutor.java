package com.android.systemui.util.concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface DelayableExecutor extends Executor {
    default Runnable executeAtTime(Runnable runnable, long j) {
        return executeAtTime(runnable, j, TimeUnit.MILLISECONDS);
    }

    Runnable executeAtTime(Runnable runnable, long j, TimeUnit timeUnit);

    default Runnable executeDelayed(Runnable runnable, long j) {
        return executeDelayed(runnable, j, TimeUnit.MILLISECONDS);
    }

    Runnable executeDelayed(Runnable runnable, long j, TimeUnit timeUnit);
}
