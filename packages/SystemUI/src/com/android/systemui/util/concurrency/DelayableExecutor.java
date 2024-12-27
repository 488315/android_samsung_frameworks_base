package com.android.systemui.util.concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

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
