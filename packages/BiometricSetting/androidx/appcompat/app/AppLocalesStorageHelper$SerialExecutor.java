package androidx.appcompat.app;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
final class AppLocalesStorageHelper$SerialExecutor implements Executor {
    Runnable mActive;
    final Executor mExecutor;
    private final Object mLock = new Object();
    final Queue<Runnable> mTasks = new ArrayDeque();

    AppLocalesStorageHelper$SerialExecutor(Executor executor) {
        this.mExecutor = executor;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(final Runnable runnable) {
        synchronized (this.mLock) {
            ((ArrayDeque) this.mTasks).add(new Runnable() { // from class: androidx.appcompat.app.AppLocalesStorageHelper$SerialExecutor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppLocalesStorageHelper$SerialExecutor appLocalesStorageHelper$SerialExecutor = AppLocalesStorageHelper$SerialExecutor.this;
                    Runnable runnable2 = runnable;
                    appLocalesStorageHelper$SerialExecutor.getClass();
                    try {
                        runnable2.run();
                    } finally {
                        appLocalesStorageHelper$SerialExecutor.scheduleNext();
                    }
                }
            });
            if (this.mActive == null) {
                scheduleNext();
            }
        }
    }

    protected final void scheduleNext() {
        synchronized (this.mLock) {
            Runnable runnable = (Runnable) ((ArrayDeque) this.mTasks).poll();
            this.mActive = runnable;
            if (runnable != null) {
                this.mExecutor.execute(runnable);
            }
        }
    }
}
