package com.android.systemui.util.concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface RepeatableExecutor extends Executor {
    default Runnable executeRepeatedly(Runnable runnable, long j, long j2) {
        return executeRepeatedly(runnable, j, j2, TimeUnit.MILLISECONDS);
    }

    Runnable executeRepeatedly(Runnable runnable, long j, long j2, TimeUnit timeUnit);
}
