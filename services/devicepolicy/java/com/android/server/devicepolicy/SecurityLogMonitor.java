package com.android.server.devicepolicy;

import android.app.admin.SecurityLog;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.util.Slog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes2.dex */
public class SecurityLogMonitor implements Runnable {
  public static final long BROADCAST_RETRY_INTERVAL_MS;
  static final int BUFFER_ENTRIES_NOTIFICATION_LEVEL = 1024;
  public static final long FORCE_FETCH_THROTTLE_NS;
  public static final long OVERLAP_NS;
  public static final long POLLING_INTERVAL_MS;
  public static final long RATE_LIMIT_INTERVAL_MS = TimeUnit.HOURS.toMillis(2);
  public boolean mAllowedToRetrieve;
  public boolean mCriticalLevelLogged;
  public int mEnabledUser;
  public final Semaphore mForceSemaphore;
  public long mId;
  public long mLastEventNanos;
  public final ArrayList mLastEvents;
  public long mLastForceNanos;
  public final Lock mLock;
  public Thread mMonitorThread;
  public long mNextAllowedRetrievalTimeMillis;
  public boolean mPaused;
  public ArrayList mPendingLogs;
  public final DevicePolicyManagerService mService;

  public SecurityLogMonitor(DevicePolicyManagerService devicePolicyManagerService) {
    this(devicePolicyManagerService, 0L);
  }

  public SecurityLogMonitor(DevicePolicyManagerService devicePolicyManagerService, long j) {
    this.mLock = new ReentrantLock();
    this.mMonitorThread = null;
    this.mPendingLogs = new ArrayList();
    this.mAllowedToRetrieve = false;
    this.mCriticalLevelLogged = false;
    this.mLastEvents = new ArrayList();
    this.mLastEventNanos = -1L;
    this.mNextAllowedRetrievalTimeMillis = -1L;
    this.mPaused = false;
    this.mForceSemaphore = new Semaphore(0);
    this.mLastForceNanos = 0L;
    this.mService = devicePolicyManagerService;
    this.mId = j;
    this.mLastForceNanos = System.nanoTime();
  }

  static {
    TimeUnit timeUnit = TimeUnit.MINUTES;
    BROADCAST_RETRY_INTERVAL_MS = timeUnit.toMillis(30L);
    POLLING_INTERVAL_MS = timeUnit.toMillis(1L);
    TimeUnit timeUnit2 = TimeUnit.SECONDS;
    OVERLAP_NS = timeUnit2.toNanos(3L);
    FORCE_FETCH_THROTTLE_NS = timeUnit2.toNanos(10L);
  }

  public void start(int i) {
    Slog.i("SecurityLogMonitor", "Starting security logging for user " + i);
    this.mEnabledUser = i;
    SecurityLog.writeEvent(210011, new Object[0]);
    this.mLock.lock();
    try {
      if (this.mMonitorThread == null) {
        this.mPendingLogs = new ArrayList();
        this.mCriticalLevelLogged = false;
        this.mId = 0L;
        this.mAllowedToRetrieve = false;
        this.mNextAllowedRetrievalTimeMillis = -1L;
        this.mPaused = false;
        Thread thread = new Thread(this);
        this.mMonitorThread = thread;
        thread.start();
      }
    } finally {
      this.mLock.unlock();
    }
  }

  public void stop() {
    Slog.i("SecurityLogMonitor", "Stopping security logging.");
    SecurityLog.writeEvent(210012, new Object[0]);
    this.mLock.lock();
    try {
      Thread thread = this.mMonitorThread;
      if (thread != null) {
        thread.interrupt();
        try {
          this.mMonitorThread.join(TimeUnit.SECONDS.toMillis(5L));
        } catch (InterruptedException e) {
          Log.e("SecurityLogMonitor", "Interrupted while waiting for thread to stop", e);
        }
        this.mPendingLogs = new ArrayList();
        this.mId = 0L;
        this.mAllowedToRetrieve = false;
        this.mNextAllowedRetrievalTimeMillis = -1L;
        this.mPaused = false;
        this.mMonitorThread = null;
      }
    } finally {
      this.mLock.unlock();
    }
  }

  public void pause() {
    Slog.i("SecurityLogMonitor", "Paused.");
    this.mLock.lock();
    this.mPaused = true;
    this.mAllowedToRetrieve = false;
    this.mLock.unlock();
  }

  public void resume() {
    this.mLock.lock();
    try {
      if (!this.mPaused) {
        Log.d("SecurityLogMonitor", "Attempted to resume, but logging is not paused.");
        return;
      }
      this.mPaused = false;
      this.mAllowedToRetrieve = false;
      this.mLock.unlock();
      Slog.i("SecurityLogMonitor", "Resumed.");
      try {
        notifyDeviceOwnerOrProfileOwnerIfNeeded(false);
      } catch (InterruptedException e) {
        Log.w("SecurityLogMonitor", "Thread interrupted.", e);
      }
    } finally {
      this.mLock.unlock();
    }
  }

  public void discardLogs() {
    this.mLock.lock();
    this.mAllowedToRetrieve = false;
    this.mPendingLogs = new ArrayList();
    this.mCriticalLevelLogged = false;
    this.mLock.unlock();
    Slog.i("SecurityLogMonitor", "Discarded all logs.");
  }

  public List retrieveLogs() {
    this.mLock.lock();
    try {
      if (!this.mAllowedToRetrieve) {
        this.mLock.unlock();
        return null;
      }
      this.mAllowedToRetrieve = false;
      this.mNextAllowedRetrievalTimeMillis = SystemClock.elapsedRealtime() + RATE_LIMIT_INTERVAL_MS;
      ArrayList arrayList = this.mPendingLogs;
      this.mPendingLogs = new ArrayList();
      this.mCriticalLevelLogged = false;
      return arrayList;
    } finally {
      this.mLock.unlock();
    }
  }

  public final void getNextBatch(ArrayList arrayList) {
    if (this.mLastEventNanos < 0) {
      SecurityLog.readEvents(arrayList);
    } else {
      SecurityLog.readEventsSince(
          this.mLastEvents.isEmpty()
              ? this.mLastEventNanos
              : Math.max(0L, this.mLastEventNanos - OVERLAP_NS),
          arrayList);
    }
    int i = 0;
    while (true) {
      if (i >= arrayList.size() - 1) {
        break;
      }
      long timeNanos = ((SecurityLog.SecurityEvent) arrayList.get(i)).getTimeNanos();
      i++;
      if (timeNanos > ((SecurityLog.SecurityEvent) arrayList.get(i)).getTimeNanos()) {
        arrayList.sort(
            new Comparator() { // from class:
              // com.android.server.devicepolicy.SecurityLogMonitor$$ExternalSyntheticLambda0
              @Override // java.util.Comparator
              public final int compare(Object obj, Object obj2) {
                int lambda$getNextBatch$0;
                lambda$getNextBatch$0 =
                    SecurityLogMonitor.lambda$getNextBatch$0(
                        (SecurityLog.SecurityEvent) obj, (SecurityLog.SecurityEvent) obj2);
                return lambda$getNextBatch$0;
              }
            });
        break;
      }
    }
    SecurityLog.redactEvents(arrayList, this.mEnabledUser);
  }

  public static /* synthetic */ int lambda$getNextBatch$0(
      SecurityLog.SecurityEvent securityEvent, SecurityLog.SecurityEvent securityEvent2) {
    return Long.signum(securityEvent.getTimeNanos() - securityEvent2.getTimeNanos());
  }

  public final void saveLastEvents(ArrayList arrayList) {
    this.mLastEvents.clear();
    if (arrayList.isEmpty()) {
      return;
    }
    this.mLastEventNanos =
        ((SecurityLog.SecurityEvent) arrayList.get(arrayList.size() - 1)).getTimeNanos();
    int size = arrayList.size() - 2;
    while (size >= 0
        && this.mLastEventNanos - ((SecurityLog.SecurityEvent) arrayList.get(size)).getTimeNanos()
            < OVERLAP_NS) {
      size--;
    }
    this.mLastEvents.addAll(arrayList.subList(size + 1, arrayList.size()));
  }

  public final void mergeBatchLocked(ArrayList arrayList) {
    ArrayList arrayList2 = this.mPendingLogs;
    arrayList2.ensureCapacity(arrayList2.size() + arrayList.size());
    int i = 0;
    int i2 = 0;
    while (i < this.mLastEvents.size() && i2 < arrayList.size()) {
      SecurityLog.SecurityEvent securityEvent = (SecurityLog.SecurityEvent) arrayList.get(i2);
      long timeNanos = securityEvent.getTimeNanos();
      if (timeNanos > this.mLastEventNanos) {
        break;
      }
      SecurityLog.SecurityEvent securityEvent2 =
          (SecurityLog.SecurityEvent) this.mLastEvents.get(i);
      long timeNanos2 = securityEvent2.getTimeNanos();
      if (timeNanos2 > timeNanos) {
        assignLogId(securityEvent);
        this.mPendingLogs.add(securityEvent);
      } else if (timeNanos2 < timeNanos) {
        i++;
      } else {
        if (!securityEvent2.eventEquals(securityEvent)) {
          assignLogId(securityEvent);
          this.mPendingLogs.add(securityEvent);
        }
        i++;
      }
      i2++;
    }
    List subList = arrayList.subList(i2, arrayList.size());
    Iterator it = subList.iterator();
    while (it.hasNext()) {
      assignLogId((SecurityLog.SecurityEvent) it.next());
    }
    this.mPendingLogs.addAll(subList);
    checkCriticalLevel();
    if (this.mPendingLogs.size() > 10240) {
      this.mPendingLogs =
          new ArrayList(this.mPendingLogs.subList(r1.size() - 5120, this.mPendingLogs.size()));
      this.mCriticalLevelLogged = false;
      Slog.i("SecurityLogMonitor", "Pending logs buffer full. Discarding old logs.");
    }
  }

  public final void checkCriticalLevel() {
    if (SecurityLog.isLoggingEnabled()
        && this.mPendingLogs.size() >= 9216
        && !this.mCriticalLevelLogged) {
      this.mCriticalLevelLogged = true;
      SecurityLog.writeEvent(210015, new Object[0]);
    }
  }

  public final void assignLogId(SecurityLog.SecurityEvent securityEvent) {
    securityEvent.setId(this.mId);
    long j = this.mId;
    if (j == Long.MAX_VALUE) {
      Slog.i("SecurityLogMonitor", "Reached maximum id value; wrapping around.");
      this.mId = 0L;
    } else {
      this.mId = j + 1;
    }
  }

  @Override // java.lang.Runnable
  public void run() {
    Process.setThreadPriority(10);
    ArrayList arrayList = new ArrayList();
    while (!Thread.currentThread().isInterrupted()) {
      try {
        boolean tryAcquire =
            this.mForceSemaphore.tryAcquire(POLLING_INTERVAL_MS, TimeUnit.MILLISECONDS);
        getNextBatch(arrayList);
        this.mLock.lockInterruptibly();
        try {
          mergeBatchLocked(arrayList);
          this.mLock.unlock();
          saveLastEvents(arrayList);
          arrayList.clear();
          notifyDeviceOwnerOrProfileOwnerIfNeeded(tryAcquire);
        } catch (Throwable th) {
          this.mLock.unlock();
          throw th;
        }
      } catch (IOException e) {
        Log.e("SecurityLogMonitor", "Failed to read security log", e);
      } catch (InterruptedException e2) {
        Log.i("SecurityLogMonitor", "Thread interrupted, exiting.", e2);
      }
    }
    this.mLastEvents.clear();
    long j = this.mLastEventNanos;
    if (j != -1) {
      this.mLastEventNanos = j + 1;
    }
    Slog.i("SecurityLogMonitor", "MonitorThread exit.");
  }

  public final void notifyDeviceOwnerOrProfileOwnerIfNeeded(boolean z) {
    this.mLock.lockInterruptibly();
    try {
      if (this.mPaused) {
        return;
      }
      int size = this.mPendingLogs.size();
      boolean z2 = (size >= 1024 || (z && size > 0)) && !this.mAllowedToRetrieve;
      if (size > 0 && SystemClock.elapsedRealtime() >= this.mNextAllowedRetrievalTimeMillis) {
        z2 = true;
      }
      if (z2) {
        this.mAllowedToRetrieve = true;
        this.mNextAllowedRetrievalTimeMillis =
            SystemClock.elapsedRealtime() + BROADCAST_RETRY_INTERVAL_MS;
      }
      if (z2) {
        Slog.i("SecurityLogMonitor", "notify DO or PO");
        this.mService.sendDeviceOwnerOrProfileOwnerCommand(
            "android.app.action.SECURITY_LOGS_AVAILABLE", null, this.mEnabledUser);
      }
    } finally {
      this.mLock.unlock();
    }
  }

  public long forceLogs() {
    long nanoTime = System.nanoTime();
    synchronized (this.mForceSemaphore) {
      long j = (this.mLastForceNanos + FORCE_FETCH_THROTTLE_NS) - nanoTime;
      if (j > 0) {
        return TimeUnit.NANOSECONDS.toMillis(j) + 1;
      }
      this.mLastForceNanos = nanoTime;
      if (this.mForceSemaphore.availablePermits() == 0) {
        this.mForceSemaphore.release();
      }
      return 0L;
    }
  }
}
