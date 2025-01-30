package com.android.server.wm;

import android.os.Process;
import android.os.SystemClock;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class PersisterQueue {
  public static final WriteQueueItem EMPTY_ITEM =
      new WriteQueueItem() { // from class:
                             // com.android.server.wm.PersisterQueue$$ExternalSyntheticLambda0
        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void process() {
          PersisterQueue.lambda$static$0();
        }
      };
  public final long mInterWriteDelayMs;
  public final LazyTaskWriterThread mLazyTaskWriterThread;
  public final ArrayList mListeners;
  public long mNextWriteTime;
  public final long mPreTaskDelayMs;
  public final ArrayList mWriteQueue;

  public interface Listener {
    void onPreProcessItem(boolean z);
  }

  public interface WriteQueueItem {
    default boolean matches(WriteQueueItem writeQueueItem) {
      return false;
    }

    void process();

    default void updateFrom(WriteQueueItem writeQueueItem) {}
  }

  public static /* synthetic */ void lambda$static$0() {}

  public PersisterQueue() {
    this(500L, 3000L);
  }

  public PersisterQueue(long j, long j2) {
    this.mWriteQueue = new ArrayList();
    this.mListeners = new ArrayList();
    this.mNextWriteTime = 0L;
    if (j < 0 || j2 < 0) {
      throw new IllegalArgumentException(
          "Both inter-write delay and pre-task delay need tobe non-negative. inter-write delay: "
              + j
              + "ms pre-task delay: "
              + j2);
    }
    this.mInterWriteDelayMs = j;
    this.mPreTaskDelayMs = j2;
    this.mLazyTaskWriterThread = new LazyTaskWriterThread("LazyTaskWriterThread");
  }

  public synchronized void startPersisting() {
    if (!this.mLazyTaskWriterThread.isAlive()) {
      this.mLazyTaskWriterThread.start();
    }
  }

  public void stopPersisting() {
    if (this.mLazyTaskWriterThread.isAlive()) {
      synchronized (this) {
        this.mLazyTaskWriterThread.interrupt();
      }
      this.mLazyTaskWriterThread.join();
    }
  }

  public synchronized void addItem(WriteQueueItem writeQueueItem, boolean z) {
    this.mWriteQueue.add(writeQueueItem);
    if (!z && this.mWriteQueue.size() <= 6) {
      if (this.mNextWriteTime == 0) {
        this.mNextWriteTime = SystemClock.uptimeMillis() + this.mPreTaskDelayMs;
      }
      notify();
    }
    this.mNextWriteTime = -1L;
    notify();
  }

  public synchronized WriteQueueItem findLastItem(Predicate predicate, Class cls) {
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

  public synchronized void updateLastOrAddItem(final WriteQueueItem writeQueueItem, boolean z) {
    Objects.requireNonNull(writeQueueItem);
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
      addItem(writeQueueItem, z);
    } else {
      findLastItem.updateFrom(writeQueueItem);
    }
    yieldIfQueueTooDeep();
  }

  public synchronized void removeItems(Predicate predicate, Class cls) {
    for (int size = this.mWriteQueue.size() - 1; size >= 0; size--) {
      WriteQueueItem writeQueueItem = (WriteQueueItem) this.mWriteQueue.get(size);
      if (cls.isInstance(writeQueueItem)
          && predicate.test((WriteQueueItem) cls.cast(writeQueueItem))) {
        this.mWriteQueue.remove(size);
      }
    }
  }

  public synchronized void flush() {
    this.mNextWriteTime = -1L;
    notifyAll();
    do {
      try {
        wait();
      } catch (InterruptedException unused) {
      }
    } while (this.mNextWriteTime == -1);
  }

  public void yieldIfQueueTooDeep() {
    boolean z;
    synchronized (this) {
      z = this.mNextWriteTime == -1;
    }
    if (z) {
      Thread.yield();
    }
  }

  public void addListener(Listener listener) {
    this.mListeners.add(listener);
  }

  public boolean removeListener(Listener listener) {
    return this.mListeners.remove(listener);
  }

  public final void processNextItem() {
    WriteQueueItem writeQueueItem;
    synchronized (this) {
      if (this.mNextWriteTime != -1) {
        this.mNextWriteTime = SystemClock.uptimeMillis() + this.mInterWriteDelayMs;
      }
      while (this.mWriteQueue.isEmpty()) {
        if (this.mNextWriteTime != 0) {
          this.mNextWriteTime = 0L;
          notify();
        }
        if (Thread.currentThread().isInterrupted()) {
          throw new InterruptedException();
        }
        wait();
      }
      writeQueueItem = (WriteQueueItem) this.mWriteQueue.remove(0);
      long uptimeMillis = SystemClock.uptimeMillis();
      while (true) {
        long j = this.mNextWriteTime;
        if (uptimeMillis < j) {
          wait(j - uptimeMillis);
          uptimeMillis = SystemClock.uptimeMillis();
        }
      }
    }
    writeQueueItem.process();
  }

  public class LazyTaskWriterThread extends Thread {
    public LazyTaskWriterThread(String str) {
      super(str);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      boolean isEmpty;
      Process.setThreadPriority(10);
      while (true) {
        try {
          synchronized (PersisterQueue.this) {
            isEmpty = PersisterQueue.this.mWriteQueue.isEmpty();
          }
          for (int size = PersisterQueue.this.mListeners.size() - 1; size >= 0; size--) {
            ((Listener) PersisterQueue.this.mListeners.get(size)).onPreProcessItem(isEmpty);
          }
          PersisterQueue.this.processNextItem();
        } catch (InterruptedException unused) {
          Slog.e(
              "PersisterQueue",
              "Persister thread is exiting. Should never happen in prod, butit's OK in tests.");
          return;
        }
      }
    }
  }
}
