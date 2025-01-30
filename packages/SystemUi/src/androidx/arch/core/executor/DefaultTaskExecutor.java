package androidx.arch.core.executor;

import android.os.Handler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DefaultTaskExecutor extends TaskExecutor {
    public volatile Handler mMainHandler;
    public final Object mLock = new Object();
    public final ExecutorService mDiskIO = Executors.newFixedThreadPool(4, new ThreadFactory(this) { // from class: androidx.arch.core.executor.DefaultTaskExecutor.1
        public final AtomicInteger mThreadId = new AtomicInteger(0);

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("arch_disk_io_" + this.mThreadId.getAndIncrement());
            return thread;
        }
    });
}
