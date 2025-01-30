package com.android.systemui.util.concurrency;

import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface DelayableExecutor extends Executor {
    default ExecutorImpl.ExecutionToken executeDelayed(long j, Runnable runnable) {
        return ((ExecutorImpl) this).executeDelayed(runnable, j, TimeUnit.MILLISECONDS);
    }
}
