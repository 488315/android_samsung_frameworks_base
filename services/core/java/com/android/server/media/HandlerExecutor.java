package com.android.server.media;

import android.os.Handler;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public final class HandlerExecutor implements Executor {
    public final Handler mHandler;

    public HandlerExecutor(Handler handler) {
        Objects.requireNonNull(handler);
        this.mHandler = handler;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        if (this.mHandler.post(runnable)) {
            return;
        }
        throw new RejectedExecutionException(this.mHandler + " is shutting down");
    }
}
