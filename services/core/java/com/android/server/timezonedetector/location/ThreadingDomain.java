package com.android.server.timezonedetector.location;

import com.android.internal.util.Preconditions;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public abstract class ThreadingDomain {
  public final Object mLockObject = new Object();

  public abstract Thread getThread();

  public abstract void post(Runnable runnable);

  public abstract Object postAndWait(Callable callable, long j);

  public abstract void postDelayed(Runnable runnable, Object obj, long j);

  public abstract void removeQueuedRunnables(Object obj);

  public Object getLockObject() {
    return this.mLockObject;
  }

  public void assertCurrentThread() {
    Preconditions.checkState(Thread.currentThread() == getThread());
  }

  public void assertNotCurrentThread() {
    Preconditions.checkState(Thread.currentThread() != getThread());
  }

  public final void postAndWait(final Runnable runnable, long j) {
    try {
      postAndWait(
          new Callable() { // from class:
                           // com.android.server.timezonedetector.location.ThreadingDomain$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
              Object lambda$postAndWait$0;
              lambda$postAndWait$0 = ThreadingDomain.lambda$postAndWait$0(runnable);
              return lambda$postAndWait$0;
            }
          },
          j);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static /* synthetic */ Object lambda$postAndWait$0(Runnable runnable) {
    runnable.run();
    return null;
  }

  public SingleRunnableQueue createSingleRunnableQueue() {
    return new SingleRunnableQueue();
  }

  public final class SingleRunnableQueue {
    public long mDelayMillis;
    public boolean mIsQueued;

    public SingleRunnableQueue() {}

    public void runDelayed(final Runnable runnable, long j) {
      cancel();
      this.mIsQueued = true;
      this.mDelayMillis = j;
      ThreadingDomain.this.postDelayed(
          new Runnable() { // from class:
                           // com.android.server.timezonedetector.location.ThreadingDomain$SingleRunnableQueue$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              ThreadingDomain.SingleRunnableQueue.this.lambda$runDelayed$0(runnable);
            }
          },
          this,
          j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runDelayed$0(Runnable runnable) {
      this.mIsQueued = false;
      this.mDelayMillis = -2L;
      runnable.run();
    }

    public boolean hasQueued() {
      ThreadingDomain.this.assertCurrentThread();
      return this.mIsQueued;
    }

    public long getQueuedDelayMillis() {
      ThreadingDomain.this.assertCurrentThread();
      if (!this.mIsQueued) {
        throw new IllegalStateException("No item queued");
      }
      return this.mDelayMillis;
    }

    public void cancel() {
      ThreadingDomain.this.assertCurrentThread();
      if (this.mIsQueued) {
        ThreadingDomain.this.removeQueuedRunnables(this);
      }
      this.mIsQueued = false;
      this.mDelayMillis = -1L;
    }
  }
}
