package com.android.wm.shell.common;

import android.os.Handler;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public class HandlerExecutor implements ShellExecutor {
    public final Handler mHandler;

    public HandlerExecutor(Handler handler) {
        this(handler, 0, 0);
    }

    public final void assertCurrentThread() {
        if (this.mHandler.getLooper().isCurrentThread()) {
            return;
        }
        throw new IllegalStateException("must be called on " + this.mHandler);
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        if (this.mHandler.getLooper().isCurrentThread()) {
            runnable.run();
        } else {
            if (this.mHandler.post(runnable)) {
                return;
            }
            throw new RuntimeException(this.mHandler + " is probably exiting");
        }
    }

    public final void executeDelayed(Runnable runnable, long j) {
        if (this.mHandler.postDelayed(runnable, j)) {
            return;
        }
        throw new RuntimeException(this.mHandler + " is probably exiting");
    }

    public final void removeCallbacks(Runnable runnable) {
        this.mHandler.removeCallbacks(runnable);
    }

    public HandlerExecutor(Handler handler, int i, int i2) {
        this.mHandler = handler;
    }

    public void replaceSetThreadPriorityFn(BiConsumer<Integer, Integer> biConsumer) {
    }
}
