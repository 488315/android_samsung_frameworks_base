package com.android.systemui.dump;

import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LogBufferFreezer {
    public final DumpManager dumpManager;
    public final DelayableExecutor executor;
    public final long freezeDuration;
    public Runnable pendingToken;

    public LogBufferFreezer(DumpManager dumpManager, DelayableExecutor delayableExecutor, long j) {
        this.dumpManager = dumpManager;
        this.executor = delayableExecutor;
        this.freezeDuration = j;
    }

    public LogBufferFreezer(DumpManager dumpManager, DelayableExecutor delayableExecutor) {
        this(dumpManager, delayableExecutor, TimeUnit.MINUTES.toMillis(5L));
    }
}
