package com.android.systemui.util.concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface RepeatableExecutor extends Executor {
    default Runnable executeRepeatedly(Runnable runnable, long j, long j2) {
        return executeRepeatedly(runnable, j, j2, TimeUnit.MILLISECONDS);
    }

    Runnable executeRepeatedly(Runnable runnable, long j, long j2, TimeUnit timeUnit);
}
