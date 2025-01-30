package com.android.systemui.util.concurrency;

import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RepeatableExecutorImpl implements RepeatableExecutor {
    public final DelayableExecutor mExecutor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ExecutionToken implements Runnable {
        public ExecutorImpl.ExecutionToken mCancel;
        public final Runnable mCommand;
        public final long mDelay;
        public final Object mLock = new Object();
        public final TimeUnit mUnit;

        public ExecutionToken(Runnable runnable, long j, TimeUnit timeUnit) {
            this.mCommand = runnable;
            this.mDelay = j;
            this.mUnit = timeUnit;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.mCommand.run();
            synchronized (this.mLock) {
                if (this.mCancel != null) {
                    this.mCancel = ((ExecutorImpl) RepeatableExecutorImpl.this.mExecutor).executeDelayed(this, this.mDelay, this.mUnit);
                }
            }
        }
    }

    public RepeatableExecutorImpl(DelayableExecutor delayableExecutor) {
        this.mExecutor = delayableExecutor;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        ((ExecutorImpl) this.mExecutor).execute(runnable);
    }
}
