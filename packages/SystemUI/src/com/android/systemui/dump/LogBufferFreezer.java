package com.android.systemui.dump;

import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.concurrent.TimeUnit;

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
