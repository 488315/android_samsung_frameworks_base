package com.android.p038wm.shell.common;

import android.os.Handler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HandlerExecutor implements ShellExecutor {
    public final Handler mHandler;

    public HandlerExecutor(Handler handler) {
        this.mHandler = handler;
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

    public final void executeDelayed(long j, Runnable runnable) {
        if (this.mHandler.postDelayed(runnable, j)) {
            return;
        }
        throw new RuntimeException(this.mHandler + " is probably exiting");
    }

    public final void removeCallbacks(Runnable runnable) {
        this.mHandler.removeCallbacks(runnable);
    }
}
