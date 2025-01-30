package androidx.appcompat.app;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppLocalesStorageHelper$SerialExecutor implements Executor {
    public Runnable mActive;
    public final Executor mExecutor;
    public final Object mLock = new Object();
    public final Queue mTasks = new ArrayDeque();

    public AppLocalesStorageHelper$SerialExecutor(Executor executor) {
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

    public final void scheduleNext() {
        synchronized (this.mLock) {
            Runnable runnable = (Runnable) ((ArrayDeque) this.mTasks).poll();
            this.mActive = runnable;
            if (runnable != null) {
                this.mExecutor.execute(runnable);
            }
        }
    }
}
