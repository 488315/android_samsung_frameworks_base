package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class ExecutorImpl implements DelayableExecutor {
    private static final int MSG_EXECUTE_RUNNABLE = 0;
    private final Handler mHandler;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class ExecutionToken implements Runnable {
        public final Runnable runnable;

        public /* synthetic */ ExecutionToken(ExecutorImpl executorImpl, Runnable runnable, int i) {
            this(runnable);
        }

        @Override // java.lang.Runnable
        public void run() {
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
                boolean onHandleMessage;
                onHandleMessage = ExecutorImpl.this.onHandleMessage(message);
                return onHandleMessage;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onHandleMessage(Message message) {
        if (message.what == 0) {
            ((ExecutionToken) message.obj).runnable.run();
            return true;
        }
        throw new IllegalStateException("Unrecognized message: " + message.what);
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (this.mHandler.post(runnable)) {
            return;
        }
        throw new RejectedExecutionException(this.mHandler + " is shutting down");
    }

    @Override // com.android.systemui.util.concurrency.DelayableExecutor
    public Runnable executeAtTime(Runnable runnable, long j, TimeUnit timeUnit) {
        ExecutionToken executionToken = new ExecutionToken(this, runnable, 0);
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(0, executionToken), timeUnit.toMillis(j));
        return executionToken;
    }

    @Override // com.android.systemui.util.concurrency.DelayableExecutor
    public Runnable executeDelayed(Runnable runnable, long j, TimeUnit timeUnit) {
        ExecutionToken executionToken = new ExecutionToken(this, runnable, 0);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, executionToken), timeUnit.toMillis(j));
        return executionToken;
    }
}
