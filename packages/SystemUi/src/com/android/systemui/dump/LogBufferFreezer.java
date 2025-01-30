package com.android.systemui.dump;

import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
