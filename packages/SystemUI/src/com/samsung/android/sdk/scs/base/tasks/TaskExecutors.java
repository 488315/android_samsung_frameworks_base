package com.samsung.android.sdk.scs.base.tasks;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TaskExecutors {
    public static final MainExecutor MAIN_THREAD = new MainExecutor();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MainExecutor implements Executor {
        public final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

    static {
        new BasicExecutor();
    }

    private TaskExecutors() {
    }
}
