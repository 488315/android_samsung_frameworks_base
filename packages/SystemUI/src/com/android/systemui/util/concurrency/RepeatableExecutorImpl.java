package com.android.systemui.util.concurrency;

import com.android.systemui.util.concurrency.RepeatableExecutorImpl;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
class RepeatableExecutorImpl implements RepeatableExecutor {
    private final DelayableExecutor mExecutor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class ExecutionToken implements Runnable {
        private Runnable mCancel;
        private final Runnable mCommand;
        private final long mDelay;
        private final Object mLock = new Object();
        private final TimeUnit mUnit;

        public ExecutionToken(Runnable runnable, long j, TimeUnit timeUnit) {
            this.mCommand = runnable;
            this.mDelay = j;
            this.mUnit = timeUnit;
        }

        public void cancel() {
            synchronized (this.mLock) {
                try {
                    Runnable runnable = this.mCancel;
                    if (runnable != null) {
                        runnable.run();
                        this.mCancel = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mCommand.run();
            synchronized (this.mLock) {
                try {
                    if (this.mCancel != null) {
                        this.mCancel = RepeatableExecutorImpl.this.mExecutor.executeDelayed(this, this.mDelay, this.mUnit);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void start(long j, TimeUnit timeUnit) {
            synchronized (this.mLock) {
                this.mCancel = RepeatableExecutorImpl.this.mExecutor.executeDelayed(this, j, timeUnit);
            }
        }
    }

    public RepeatableExecutorImpl(DelayableExecutor delayableExecutor) {
        this.mExecutor = delayableExecutor;
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        this.mExecutor.execute(runnable);
    }

    @Override // com.android.systemui.util.concurrency.RepeatableExecutor
    public Runnable executeRepeatedly(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        final ExecutionToken executionToken = new ExecutionToken(runnable, j2, timeUnit);
        executionToken.start(j, timeUnit);
        return new Runnable() { // from class: com.android.systemui.util.concurrency.RepeatableExecutorImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RepeatableExecutorImpl.ExecutionToken.this.cancel();
            }
        };
    }
}
