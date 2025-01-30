package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ExecutorImpl implements DelayableExecutor {
    public final Handler mHandler;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ExecutionToken implements Runnable {
        public final Runnable runnable;

        public /* synthetic */ ExecutionToken(ExecutorImpl executorImpl, Runnable runnable, int i) {
            this(runnable);
        }

        @Override // java.lang.Runnable
        public final void run() {
            ExecutorImpl.this.mHandler.removeCallbacksAndMessages(this);
        }

        private ExecutionToken(Runnable runnable) {
            this.runnable = runnable;
        }
    }

    public ExecutorImpl(Looper looper) {
        this.mHandler = new Handler(looper, new Handler.Callback() { // from class: com.android.systemui.util.concurrency.ExecutorImpl$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                ExecutorImpl.this.getClass();
                if (message.what == 0) {
                    ((ExecutorImpl.ExecutionToken) message.obj).runnable.run();
                    return true;
                }
                throw new IllegalStateException("Unrecognized message: " + message.what);
            }
        });
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        if (this.mHandler.post(runnable)) {
            return;
        }
        throw new RejectedExecutionException(this.mHandler + " is shutting down");
    }

    public final ExecutionToken executeDelayed(Runnable runnable, long j, TimeUnit timeUnit) {
        ExecutionToken executionToken = new ExecutionToken(this, runnable, 0);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, executionToken), timeUnit.toMillis(j));
        return executionToken;
    }
}
