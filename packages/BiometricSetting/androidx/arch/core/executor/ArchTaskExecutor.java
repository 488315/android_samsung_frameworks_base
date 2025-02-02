package androidx.arch.core.executor;

import android.os.Looper;

/* loaded from: classes.dex */
public final class ArchTaskExecutor extends TaskExecutor {
    private static volatile ArchTaskExecutor sInstance;
    private final DefaultTaskExecutor mDefaultTaskExecutor;
    private DefaultTaskExecutor mDelegate;

    private ArchTaskExecutor() {
        DefaultTaskExecutor defaultTaskExecutor = new DefaultTaskExecutor();
        this.mDefaultTaskExecutor = defaultTaskExecutor;
        this.mDelegate = defaultTaskExecutor;
    }

    public static ArchTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (ArchTaskExecutor.class) {
            if (sInstance == null) {
                sInstance = new ArchTaskExecutor();
            }
        }
        return sInstance;
    }

    public final boolean isMainThread() {
        this.mDelegate.getClass();
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
