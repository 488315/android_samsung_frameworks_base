package com.android.server.am;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.os.TimeoutRecord;
import com.android.server.wm.WindowProcessController;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class AnrHelper {
  public final ArrayList mAnrRecords;
  public final ExecutorService mAuxiliaryTaskExecutor;
  public final ExecutorService mEarlyDumpExecutor;
  public long mLastAnrTimeMs;
  public int mProcessingPid;
  public final AtomicBoolean mRunning;
  public final ActivityManagerService mService;
  public final Set mTempDumpedPids;
  public static final long EXPIRED_REPORT_TIME_MS = TimeUnit.SECONDS.toMillis(10);
  public static final long CONSECUTIVE_ANR_TIME_MS = TimeUnit.MINUTES.toMillis(2);
  public static final ThreadFactory sDefaultThreadFactory =
      new ThreadFactory() { // from class: com.android.server.am.AnrHelper$$ExternalSyntheticLambda1
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
          Thread lambda$static$0;
          lambda$static$0 = AnrHelper.lambda$static$0(runnable);
          return lambda$static$0;
        }
      };
  public static final ThreadFactory sMainProcessDumpThreadFactory =
      new ThreadFactory() { // from class: com.android.server.am.AnrHelper$$ExternalSyntheticLambda2
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
          Thread lambda$static$1;
          lambda$static$1 = AnrHelper.lambda$static$1(runnable);
          return lambda$static$1;
        }
      };

  public static /* synthetic */ Thread lambda$static$0(Runnable runnable) {
    return new Thread(runnable, "AnrAuxiliaryTaskExecutor");
  }

  public static /* synthetic */ Thread lambda$static$1(Runnable runnable) {
    return new Thread(runnable, "AnrMainProcessDumpThread");
  }

  public AnrHelper(ActivityManagerService activityManagerService) {
    this(
        activityManagerService,
        makeExpiringThreadPoolWithSize(1, sDefaultThreadFactory),
        makeExpiringThreadPoolWithSize(2, sMainProcessDumpThreadFactory));
  }

  public AnrHelper(
      ActivityManagerService activityManagerService,
      ExecutorService executorService,
      ExecutorService executorService2) {
    this.mAnrRecords = new ArrayList();
    this.mTempDumpedPids = Collections.synchronizedSet(new ArraySet());
    this.mRunning = new AtomicBoolean(false);
    this.mLastAnrTimeMs = 0L;
    this.mProcessingPid = -1;
    this.mService = activityManagerService;
    this.mAuxiliaryTaskExecutor = executorService;
    this.mEarlyDumpExecutor = executorService2;
  }

  public void appNotResponding(ProcessRecord processRecord, TimeoutRecord timeoutRecord) {
    appNotResponding(processRecord, null, null, null, null, false, timeoutRecord, false);
  }

  public void appNotResponding(
      final ProcessRecord processRecord,
      String str,
      final ApplicationInfo applicationInfo,
      String str2,
      WindowProcessController windowProcessController,
      boolean z,
      final TimeoutRecord timeoutRecord,
      boolean z2) {
    TimeoutRecord timeoutRecord2;
    try {
      timeoutRecord.mLatencyTracker.appNotRespondingStarted();
      final int i = processRecord.mPid;
      timeoutRecord.mLatencyTracker.waitingOnAnrRecordLockStarted();
      synchronized (this.mAnrRecords) {
        try {
          timeoutRecord.mLatencyTracker.waitingOnAnrRecordLockEnded();
          if (i == 0) {
            Slog.i("ActivityManager", "Skip zero pid ANR, process=" + processRecord.processName);
          } else if (this.mProcessingPid == i) {
            Slog.i(
                "ActivityManager", "Skip duplicated ANR, pid=" + i + " " + timeoutRecord.mReason);
          } else if (this.mTempDumpedPids.add(Integer.valueOf(i))) {
            for (int size = this.mAnrRecords.size() - 1; size >= 0; size--) {
              if (((AnrRecord) this.mAnrRecords.get(size)).mPid == i) {
                Slog.i(
                    "ActivityManager", "Skip queued ANR, pid=" + i + " " + timeoutRecord.mReason);
              }
            }
            timeoutRecord.mLatencyTracker.earlyDumpRequestSubmittedWithSize(
                this.mTempDumpedPids.size());
            Future submit =
                this.mEarlyDumpExecutor.submit(
                    new Callable() { // from class:
                                     // com.android.server.am.AnrHelper$$ExternalSyntheticLambda0
                      @Override // java.util.concurrent.Callable
                      public final Object call() {
                        File lambda$appNotResponding$2;
                        lambda$appNotResponding$2 =
                            AnrHelper.this.lambda$appNotResponding$2(i, timeoutRecord);
                        return lambda$appNotResponding$2;
                      }
                    });
            timeoutRecord.mLatencyTracker.anrRecordPlacingOnQueueWithSize(this.mAnrRecords.size());
            try {
              this.mAnrRecords.add(
                  new AnrRecord(
                      processRecord,
                      str,
                      applicationInfo,
                      str2,
                      windowProcessController,
                      z,
                      timeoutRecord,
                      z2,
                      submit));
              if (applicationInfo != null) {
                try {
                  if (processRecord.uid >= 10000) {
                    new Thread(
                        "AppAnrHistoryBroadcastThread") { // from class:
                                                          // com.android.server.am.AnrHelper.1
                      @Override // java.lang.Thread, java.lang.Runnable
                      public void run() {
                        Slog.w(
                            "ActivityManager",
                            "anr : " + applicationInfo.packageName + "," + applicationInfo.uid);
                        Intent intent = new Intent("com.sec.android.sdhms.action.CRASH_ANR");
                        intent.setPackage("com.sec.android.sdhms");
                        intent.putExtra("pkgName", applicationInfo.packageName);
                        intent.putExtra("userId", processRecord.userId);
                        intent.putExtra("uid", processRecord.uid);
                        intent.putExtra("type", "anr");
                        AnrHelper.this.mService.mContext.sendBroadcastAsUser(
                            intent, UserHandle.ALL, "com.samsung.permission.WRITE_SDHMS_DATA");
                      }
                    }.start();
                    startAnrConsumerIfNeeded();
                    timeoutRecord.mLatencyTracker.appNotRespondingEnded();
                    return;
                  }
                } catch (Throwable th) {
                  th = th;
                  timeoutRecord2 = timeoutRecord;
                  timeoutRecord2.mLatencyTracker.appNotRespondingEnded();
                  throw th;
                }
              }
              startAnrConsumerIfNeeded();
              timeoutRecord.mLatencyTracker.appNotRespondingEnded();
              return;
            } catch (Throwable th2) {
              th = th2;
              timeoutRecord2 = timeoutRecord;
              while (true) {
                try {
                  try {
                    throw th;
                  } catch (Throwable th3) {
                    th = th3;
                    timeoutRecord2.mLatencyTracker.appNotRespondingEnded();
                    throw th;
                  }
                } catch (Throwable th4) {
                  th = th4;
                }
              }
            }
          } else {
            Slog.i(
                "ActivityManager",
                "Skip ANR being predumped, pid=" + i + " " + timeoutRecord.mReason);
          }
          timeoutRecord.mLatencyTracker.appNotRespondingEnded();
        } catch (Throwable th5) {
          th = th5;
          timeoutRecord2 = timeoutRecord;
        }
      }
    } catch (Throwable th6) {
      th = th6;
      timeoutRecord2 = timeoutRecord;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ File lambda$appNotResponding$2(int i, TimeoutRecord timeoutRecord) {
    File dumpStackTracesTempFile =
        StackTracesDumpHelper.dumpStackTracesTempFile(i, timeoutRecord.mLatencyTracker);
    this.mTempDumpedPids.remove(Integer.valueOf(i));
    return dumpStackTracesTempFile;
  }

  public final void startAnrConsumerIfNeeded() {
    if (this.mRunning.compareAndSet(false, true)) {
      new AnrConsumerThread().start();
    }
  }

  public static ThreadPoolExecutor makeExpiringThreadPoolWithSize(
      int i, ThreadFactory threadFactory) {
    ThreadPoolExecutor threadPoolExecutor =
        new ThreadPoolExecutor(
            i, i, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue(), threadFactory);
    threadPoolExecutor.allowCoreThreadTimeOut(true);
    return threadPoolExecutor;
  }

  public class AnrConsumerThread extends Thread {
    public AnrConsumerThread() {
      super("AnrConsumer");
    }

    public final AnrRecord next() {
      synchronized (AnrHelper.this.mAnrRecords) {
        if (AnrHelper.this.mAnrRecords.isEmpty()) {
          return null;
        }
        AnrRecord anrRecord = (AnrRecord) AnrHelper.this.mAnrRecords.remove(0);
        AnrHelper.this.mProcessingPid = anrRecord.mPid;
        anrRecord.mTimeoutRecord.mLatencyTracker.anrRecordsQueueSizeWhenPopped(
            AnrHelper.this.mAnrRecords.size());
        return anrRecord;
      }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      while (true) {
        AnrRecord next = next();
        if (next == null) {
          break;
        }
        AnrHelper.this.scheduleBinderHeavyHitterAutoSamplerIfNecessary();
        int i = next.mApp.mPid;
        if (i != next.mPid) {
          Slog.i(
              "ActivityManager",
              "Skip ANR with mismatched pid=" + next.mPid + ", current pid=" + i);
        } else {
          long uptimeMillis = SystemClock.uptimeMillis();
          long j = uptimeMillis - next.mTimestamp;
          boolean z = j > AnrHelper.EXPIRED_REPORT_TIME_MS;
          next.appNotResponding(z);
          long uptimeMillis2 = SystemClock.uptimeMillis();
          StringBuilder sb = new StringBuilder();
          sb.append("Completed ANR of ");
          sb.append(next.mApp.processName);
          sb.append(" in ");
          sb.append(uptimeMillis2 - uptimeMillis);
          sb.append("ms, latency ");
          sb.append(j);
          sb.append(z ? "ms (expired, only dump ANR app)" : "ms");
          Slog.d("ActivityManager", sb.toString());
        }
      }
      AnrHelper.this.mRunning.set(false);
      synchronized (AnrHelper.this.mAnrRecords) {
        AnrHelper.this.mProcessingPid = -1;
        if (!AnrHelper.this.mAnrRecords.isEmpty()) {
          AnrHelper.this.startAnrConsumerIfNeeded();
        }
      }
    }
  }

  public final void scheduleBinderHeavyHitterAutoSamplerIfNecessary() {
    try {
      Trace.traceBegin(64L, "scheduleBinderHeavyHitterAutoSamplerIfNecessary()");
      long uptimeMillis = SystemClock.uptimeMillis();
      if (this.mLastAnrTimeMs + CONSECUTIVE_ANR_TIME_MS > uptimeMillis) {
        this.mService.scheduleBinderHeavyHitterAutoSampler();
      }
      this.mLastAnrTimeMs = uptimeMillis;
    } finally {
      Trace.traceEnd(64L);
    }
  }

  public class AnrRecord {
    public final boolean mAboveSystem;
    public final String mActivityShortComponentName;
    public final ProcessRecord mApp;
    public final ApplicationInfo mAppInfo;
    public final Future mFirstPidFilePromise;
    public final boolean mIsContinuousAnr;
    public final WindowProcessController mParentProcess;
    public final String mParentShortComponentName;
    public final int mPid;
    public final TimeoutRecord mTimeoutRecord;
    public final long mTimestamp = SystemClock.uptimeMillis();

    public AnrRecord(
        ProcessRecord processRecord,
        String str,
        ApplicationInfo applicationInfo,
        String str2,
        WindowProcessController windowProcessController,
        boolean z,
        TimeoutRecord timeoutRecord,
        boolean z2,
        Future future) {
      this.mApp = processRecord;
      this.mPid = processRecord.mPid;
      this.mActivityShortComponentName = str;
      this.mParentShortComponentName = str2;
      this.mTimeoutRecord = timeoutRecord;
      this.mAppInfo = applicationInfo;
      this.mParentProcess = windowProcessController;
      this.mAboveSystem = z;
      this.mIsContinuousAnr = z2;
      this.mFirstPidFilePromise = future;
    }

    public void appNotResponding(boolean z) {
      try {
        this.mTimeoutRecord.mLatencyTracker.anrProcessingStarted();
        this.mApp.mErrorState.appNotResponding(
            this.mActivityShortComponentName,
            this.mAppInfo,
            this.mParentShortComponentName,
            this.mParentProcess,
            this.mAboveSystem,
            this.mTimeoutRecord,
            AnrHelper.this.mAuxiliaryTaskExecutor,
            z,
            this.mIsContinuousAnr,
            this.mFirstPidFilePromise);
      } finally {
        this.mTimeoutRecord.mLatencyTracker.anrProcessingEnded();
      }
    }
  }
}
