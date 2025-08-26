package com.samsung.android.sdk.scs.base.tasks;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class TaskExecutors {
    public static final MainExecutor MAIN_THREAD = new MainExecutor();

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
