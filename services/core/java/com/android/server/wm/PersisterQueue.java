package com.android.server.wm;

import android.os.Process;
import android.os.SystemClock;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PersisterQueue {
    public static final PersisterQueue$$ExternalSyntheticLambda0 EMPTY_ITEM =
            new PersisterQueue$$ExternalSyntheticLambda0();
    public final long mInterWriteDelayMs;
    public final LazyTaskWriterThread mLazyTaskWriterThread;
    public final ArrayList mListeners;
    public long mNextWriteTime;
    public final long mPreTaskDelayMs;
    public final ArrayList mWriteQueue;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LazyTaskWriterThread extends Thread {
        public LazyTaskWriterThread() {
            super("LazyTaskWriterThread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            boolean isEmpty;
            Process.setThreadPriority(10);
            while (true) {
                try {
                    synchronized (PersisterQueue.this) {
                        isEmpty = PersisterQueue.this.mWriteQueue.isEmpty();
                    }
                    for (int size = PersisterQueue.this.mListeners.size() - 1; size >= 0; size--) {
                        ((TaskPersister) ((Listener) PersisterQueue.this.mListeners.get(size)))
                                .onPreProcessItem(isEmpty);
                    }
                    PersisterQueue.m1066$$Nest$mprocessNextItem(PersisterQueue.this);
                } catch (InterruptedException unused) {
                    Slog.e(
                            "PersisterQueue",
                            "Persister thread is exiting. Should never happen in prod, butit's OK"
                                + " in tests.");
                    return;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Listener {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface WriteQueueItem {
        default boolean matches(WriteQueueItem writeQueueItem) {
            return false;
        }

        void process();

        default void updateFrom(WriteQueueItem writeQueueItem) {}
    }

    /* renamed from: -$$Nest$mprocessNextItem, reason: not valid java name */
    public static void m1066$$Nest$mprocessNextItem(PersisterQueue persisterQueue) {
        WriteQueueItem writeQueueItem;
        synchronized (persisterQueue) {
            try {
                if (persisterQueue.mNextWriteTime != -1) {
                    persisterQueue.mNextWriteTime =
                            SystemClock.uptimeMillis() + persisterQueue.mInterWriteDelayMs;
                }
                while (persisterQueue.mWriteQueue.isEmpty()) {
                    if (persisterQueue.mNextWriteTime != 0) {
                        persisterQueue.mNextWriteTime = 0L;
                        persisterQueue.notify();
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }
                    persisterQueue.wait();
                }
                writeQueueItem = (WriteQueueItem) persisterQueue.mWriteQueue.remove(0);
                long uptimeMillis = SystemClock.uptimeMillis();
                while (true) {
                    long j = persisterQueue.mNextWriteTime;
                    if (uptimeMillis < j) {
                        persisterQueue.wait(j - uptimeMillis);
                        uptimeMillis = SystemClock.uptimeMillis();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        writeQueueItem.process();
    }

    public PersisterQueue() {
        this(500L, 3000L);
    }

    public PersisterQueue(long j, long j2) {
        this.mWriteQueue = new ArrayList();
        this.mListeners = new ArrayList();
        this.mNextWriteTime = 0L;
        if (j < 0 || j2 < 0) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "Both inter-write delay and pre-task delay need tobe non-negative."
                                + " inter-write delay: ",
                            j,
                            "ms pre-task delay: ");
            m.append(j2);
            throw new IllegalArgumentException(m.toString());
        }
        this.mInterWriteDelayMs = j;
        this.mPreTaskDelayMs = j2;
        this.mLazyTaskWriterThread = new LazyTaskWriterThread();
    }

    public final synchronized void addItem(WriteQueueItem writeQueueItem, boolean z) {
        try {
            this.mWriteQueue.add(writeQueueItem);
            if (!z && this.mWriteQueue.size() <= 6) {
                if (this.mNextWriteTime == 0) {
                    this.mNextWriteTime = SystemClock.uptimeMillis() + this.mPreTaskDelayMs;
                }
                notify();
            }
            this.mNextWriteTime = -1L;
            notify();
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized WriteQueueItem findLastItem(Predicate predicate, Class cls) {
        for (int size = this.mWriteQueue.size() - 1; size >= 0; size--) {
            WriteQueueItem writeQueueItem = (WriteQueueItem) this.mWriteQueue.get(size);
            if (cls.isInstance(writeQueueItem)) {
                WriteQueueItem writeQueueItem2 = (WriteQueueItem) cls.cast(writeQueueItem);
                if (predicate.test(writeQueueItem2)) {
                    return writeQueueItem2;
                }
            }
        }
        return null;
    }

    public final synchronized void removeItems(Predicate predicate, Class cls) {
        for (int size = this.mWriteQueue.size() - 1; size >= 0; size--) {
            WriteQueueItem writeQueueItem = (WriteQueueItem) this.mWriteQueue.get(size);
            if (cls.isInstance(writeQueueItem)
                    && predicate.test((WriteQueueItem) cls.cast(writeQueueItem))) {
                this.mWriteQueue.remove(size);
            }
        }
    }

    public boolean removeListener(Listener listener) {
        return this.mListeners.remove(listener);
    }

    public void stopPersisting() throws InterruptedException {
        if (this.mLazyTaskWriterThread.isAlive()) {
            synchronized (this) {
                this.mLazyTaskWriterThread.interrupt();
            }
            this.mLazyTaskWriterThread.join();
        }
    }

    public final synchronized void updateLastOrAddItem(final WriteQueueItem writeQueueItem) {
        try {
            WriteQueueItem findLastItem =
                    findLastItem(
                            new Predicate() { // from class:
                                              // com.android.server.wm.PersisterQueue$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return PersisterQueue.WriteQueueItem.this.matches(
                                            (PersisterQueue.WriteQueueItem) obj);
                                }
                            },
                            writeQueueItem.getClass());
            if (findLastItem == null) {
                addItem(writeQueueItem, false);
            } else {
                findLastItem.updateFrom(writeQueueItem);
            }
            yieldIfQueueTooDeep();
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void yieldIfQueueTooDeep() {
        boolean z;
        synchronized (this) {
            z = this.mNextWriteTime == -1;
        }
        if (z) {
            Thread.yield();
        }
    }
}
