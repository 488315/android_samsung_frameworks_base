package com.android.server.utils.quota;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.LongArrayQueue;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class CountQuotaTracker extends QuotaTracker {
  public static final String ALARM_TAG_CLEANUP =
      "*" + CountQuotaTracker.class.getSimpleName() + ".cleanup*";
  public static final String TAG = "CountQuotaTracker";
  public final ArrayMap mCategoryCountWindowSizesMs;
  public Function mCreateExecutionStats;
  public Function mCreateLongArrayQueue;
  public final DeleteEventTimesFunctor mDeleteOldEventTimesFunctor;
  public final EarliestEventTimeFunctor mEarliestEventTimeFunctor;
  public final AlarmManager.OnAlarmListener mEventCleanupAlarmListener;
  public final UptcMap mEventTimes;
  public final UptcMap mExecutionStatsCache;
  public final Handler mHandler;
  public final ArrayMap mMaxCategoryCounts;
  public long mMaxPeriodMs;
  public long mNextCleanupTimeElapsed;

  @Override // com.android.server.utils.quota.QuotaTracker
  public /* bridge */ /* synthetic */ void clear() {
    super.clear();
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public /* bridge */ /* synthetic */ void dump(IndentingPrintWriter indentingPrintWriter) {
    super.dump(indentingPrintWriter);
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public /* bridge */ /* synthetic */ boolean isWithinQuota(int i, String str, String str2) {
    return super.isWithinQuota(i, str, str2);
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public /* bridge */ /* synthetic */ void setEnabled(boolean z) {
    super.setEnabled(z);
  }

  class ExecutionStats {
    public int countInWindow;
    public int countLimit;
    public long expirationTimeElapsed;
    public long inQuotaTimeElapsed;
    public long windowSizeMs;

    public String toString() {
      return "expirationTime="
          + this.expirationTimeElapsed
          + ", windowSizeMs="
          + this.windowSizeMs
          + ", countLimit="
          + this.countLimit
          + ", countInWindow="
          + this.countInWindow
          + ", inQuotaTime="
          + this.inQuotaTimeElapsed;
    }

    public boolean equals(Object obj) {
      if (!(obj instanceof ExecutionStats)) {
        return false;
      }
      ExecutionStats executionStats = (ExecutionStats) obj;
      return this.expirationTimeElapsed == executionStats.expirationTimeElapsed
          && this.windowSizeMs == executionStats.windowSizeMs
          && this.countLimit == executionStats.countLimit
          && this.countInWindow == executionStats.countInWindow
          && this.inQuotaTimeElapsed == executionStats.inQuotaTimeElapsed;
    }

    public int hashCode() {
      return ((((((((0 + Long.hashCode(this.expirationTimeElapsed)) * 31)
                                  + Long.hashCode(this.windowSizeMs))
                              * 31)
                          + this.countLimit)
                      * 31)
                  + this.countInWindow)
              * 31)
          + Long.hashCode(this.inQuotaTimeElapsed);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    this.mHandler.obtainMessage(1).sendToTarget();
  }

  public CountQuotaTracker(Context context, Categorizer categorizer) {
    this(context, categorizer, new QuotaTracker.Injector());
  }

  /* JADX WARN: Multi-variable type inference failed */
  public CountQuotaTracker(
      Context context, Categorizer categorizer, QuotaTracker.Injector injector) {
    super(context, categorizer, injector);
    this.mEventTimes = new UptcMap();
    this.mExecutionStatsCache = new UptcMap();
    this.mNextCleanupTimeElapsed = 0L;
    this.mEventCleanupAlarmListener =
        new AlarmManager
            .OnAlarmListener() { // from class:
                                 // com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda0
          @Override // android.app.AlarmManager.OnAlarmListener
          public final void onAlarm() {
            CountQuotaTracker.this.lambda$new$0();
          }
        };
    this.mCategoryCountWindowSizesMs = new ArrayMap();
    this.mMaxCategoryCounts = new ArrayMap();
    this.mMaxPeriodMs = 0L;
    this.mEarliestEventTimeFunctor = new EarliestEventTimeFunctor();
    this.mDeleteOldEventTimesFunctor = new DeleteEventTimesFunctor();
    this.mCreateLongArrayQueue =
        new Function() { // from class:
                         // com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda1
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            LongArrayQueue lambda$new$4;
            lambda$new$4 = CountQuotaTracker.lambda$new$4((Void) obj);
            return lambda$new$4;
          }
        };
    this.mCreateExecutionStats =
        new Function() { // from class:
                         // com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda2
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            CountQuotaTracker.ExecutionStats lambda$new$5;
            lambda$new$5 = CountQuotaTracker.lambda$new$5((Void) obj);
            return lambda$new$5;
          }
        };
    this.mHandler = new CqtHandler(context.getMainLooper());
  }

  public boolean noteEvent(int i, String str, String str2) {
    synchronized (this.mLock) {
      if (isEnabledLocked() && !isQuotaFreeLocked(i, str)) {
        long elapsedRealtime = this.mInjector.getElapsedRealtime();
        LongArrayQueue longArrayQueue =
            (LongArrayQueue) this.mEventTimes.getOrCreate(i, str, str2, this.mCreateLongArrayQueue);
        longArrayQueue.addLast(elapsedRealtime);
        ExecutionStats executionStatsLocked = getExecutionStatsLocked(i, str, str2);
        executionStatsLocked.countInWindow++;
        executionStatsLocked.expirationTimeElapsed =
            Math.min(
                executionStatsLocked.expirationTimeElapsed,
                executionStatsLocked.windowSizeMs + elapsedRealtime);
        int i2 = executionStatsLocked.countInWindow;
        int i3 = executionStatsLocked.countLimit;
        if (i2 == i3) {
          long j = elapsedRealtime - executionStatsLocked.windowSizeMs;
          while (longArrayQueue.size() > 0 && longArrayQueue.peekFirst() < j) {
            longArrayQueue.removeFirst();
          }
          executionStatsLocked.inQuotaTimeElapsed =
              longArrayQueue.peekFirst() + executionStatsLocked.windowSizeMs;
          postQuotaStatusChanged(i, str, str2);
        } else if (i3 > 9 && i2 == (i3 * 4) / 5) {
          Slog.w(
              TAG,
              Uptc.string(i, str, str2)
                  + " has reached 80% of it's count limit of "
                  + executionStatsLocked.countLimit);
        }
        maybeScheduleCleanupAlarmLocked();
        return isWithinQuotaLocked(executionStatsLocked);
      }
      return true;
    }
  }

  public void setCountLimit(Category category, int i, long j) {
    if (i < 0 || j < 0) {
      throw new IllegalArgumentException("Limit and window size must be nonnegative.");
    }
    synchronized (this.mLock) {
      Integer num = (Integer) this.mMaxCategoryCounts.put(category, Integer.valueOf(i));
      long max = Math.max(20000L, Math.min(j, 2592000000L));
      Long l = (Long) this.mCategoryCountWindowSizesMs.put(category, Long.valueOf(max));
      if (num == null || l == null || num.intValue() != i || l.longValue() != max) {
        this.mDeleteOldEventTimesFunctor.updateMaxPeriod();
        this.mMaxPeriodMs = this.mDeleteOldEventTimesFunctor.mMaxPeriodMs;
        invalidateAllExecutionStatsLocked();
        scheduleQuotaCheck();
      }
    }
  }

  public int getLimit(Category category) {
    int intValue;
    synchronized (this.mLock) {
      Integer num = (Integer) this.mMaxCategoryCounts.get(category);
      if (num == null) {
        throw new IllegalArgumentException("Limit for " + category + " not defined");
      }
      intValue = num.intValue();
    }
    return intValue;
  }

  public long getWindowSizeMs(Category category) {
    long longValue;
    synchronized (this.mLock) {
      Long l = (Long) this.mCategoryCountWindowSizesMs.get(category);
      if (l == null) {
        throw new IllegalArgumentException("Limit for " + category + " not defined");
      }
      longValue = l.longValue();
    }
    return longValue;
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public void dropEverythingLocked() {
    this.mExecutionStatsCache.clear();
    this.mEventTimes.clear();
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public Handler getHandler() {
    return this.mHandler;
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public long getInQuotaTimeElapsedLocked(int i, String str, String str2) {
    return getExecutionStatsLocked(i, str, str2).inQuotaTimeElapsed;
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public void handleRemovedAppLocked(int i, String str) {
    if (str == null) {
      Slog.wtf(TAG, "Told app removed but given null package name.");
    } else {
      this.mEventTimes.delete(i, str);
      this.mExecutionStatsCache.delete(i, str);
    }
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public void handleRemovedUserLocked(int i) {
    this.mEventTimes.delete(i);
    this.mExecutionStatsCache.delete(i);
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public boolean isWithinQuotaLocked(int i, String str, String str2) {
    if (isEnabledLocked() && !isQuotaFreeLocked(i, str)) {
      return isWithinQuotaLocked(getExecutionStatsLocked(i, str, str2));
    }
    return true;
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public void maybeUpdateAllQuotaStatusLocked() {
    final UptcMap uptcMap = new UptcMap();
    this.mEventTimes.forEach(
        new UptcMap
            .UptcDataConsumer() { // from class:
                                  // com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda5
          @Override // com.android.server.utils.quota.UptcMap.UptcDataConsumer
          public final void accept(int i, String str, String str2, Object obj) {
            CountQuotaTracker.this.lambda$maybeUpdateAllQuotaStatusLocked$1(
                uptcMap, i, str, str2, (LongArrayQueue) obj);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$maybeUpdateAllQuotaStatusLocked$1(
      UptcMap uptcMap, int i, String str, String str2, LongArrayQueue longArrayQueue) {
    if (uptcMap.contains(i, str, str2)) {
      return;
    }
    maybeUpdateStatusForUptcLocked(i, str, str2);
    uptcMap.add(i, str, str2, Boolean.TRUE);
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public void maybeUpdateQuotaStatus(int i, String str, String str2) {
    synchronized (this.mLock) {
      maybeUpdateStatusForUptcLocked(i, str, str2);
    }
  }

  public final boolean isWithinQuotaLocked(ExecutionStats executionStats) {
    return isUnderCountQuotaLocked(executionStats);
  }

  public final boolean isUnderCountQuotaLocked(ExecutionStats executionStats) {
    return executionStats.countInWindow < executionStats.countLimit;
  }

  public ExecutionStats getExecutionStatsLocked(int i, String str, String str2) {
    return getExecutionStatsLocked(i, str, str2, true);
  }

  public final ExecutionStats getExecutionStatsLocked(int i, String str, String str2, boolean z) {
    ExecutionStats executionStats =
        (ExecutionStats)
            this.mExecutionStatsCache.getOrCreate(i, str, str2, this.mCreateExecutionStats);
    if (z) {
      Category category = this.mCategorizer.getCategory(i, str, str2);
      long longValue =
          ((Long) this.mCategoryCountWindowSizesMs.getOrDefault(category, Long.MAX_VALUE))
              .longValue();
      int intValue =
          ((Integer) this.mMaxCategoryCounts.getOrDefault(category, Integer.MAX_VALUE)).intValue();
      if (executionStats.expirationTimeElapsed <= this.mInjector.getElapsedRealtime()
          || executionStats.windowSizeMs != longValue
          || executionStats.countLimit != intValue) {
        executionStats.windowSizeMs = longValue;
        executionStats.countLimit = intValue;
        updateExecutionStatsLocked(i, str, str2, executionStats);
      }
    }
    return executionStats;
  }

  public void updateExecutionStatsLocked(
      int i, String str, String str2, ExecutionStats executionStats) {
    executionStats.countInWindow = 0;
    if (executionStats.countLimit == 0) {
      executionStats.inQuotaTimeElapsed = Long.MAX_VALUE;
    } else {
      executionStats.inQuotaTimeElapsed = 0L;
    }
    long elapsedRealtime = this.mInjector.getElapsedRealtime();
    executionStats.expirationTimeElapsed = this.mMaxPeriodMs + elapsedRealtime;
    LongArrayQueue longArrayQueue = (LongArrayQueue) this.mEventTimes.get(i, str, str2);
    if (longArrayQueue == null) {
      return;
    }
    long j = Long.MAX_VALUE - elapsedRealtime;
    long j2 = elapsedRealtime - executionStats.windowSizeMs;
    for (int size = longArrayQueue.size() - 1; size >= 0; size--) {
      long j3 = longArrayQueue.get(size);
      if (j3 < j2) {
        break;
      }
      executionStats.countInWindow++;
      j = Math.min(j, j3 - j2);
      if (executionStats.countInWindow >= executionStats.countLimit) {
        executionStats.inQuotaTimeElapsed =
            Math.max(executionStats.inQuotaTimeElapsed, j3 + executionStats.windowSizeMs);
      }
    }
    executionStats.expirationTimeElapsed = elapsedRealtime + j;
  }

  public final void invalidateAllExecutionStatsLocked() {
    final long elapsedRealtime = this.mInjector.getElapsedRealtime();
    this.mExecutionStatsCache.forEach(
        new Consumer() { // from class:
                         // com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda3
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            CountQuotaTracker.lambda$invalidateAllExecutionStatsLocked$2(
                elapsedRealtime, (CountQuotaTracker.ExecutionStats) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$invalidateAllExecutionStatsLocked$2(
      long j, ExecutionStats executionStats) {
    if (executionStats != null) {
      executionStats.expirationTimeElapsed = j;
    }
  }

  public final class EarliestEventTimeFunctor implements Consumer {
    public long earliestTimeElapsed;

    public EarliestEventTimeFunctor() {
      this.earliestTimeElapsed = Long.MAX_VALUE;
    }

    @Override // java.util.function.Consumer
    public void accept(LongArrayQueue longArrayQueue) {
      if (longArrayQueue == null || longArrayQueue.size() <= 0) {
        return;
      }
      this.earliestTimeElapsed = Math.min(this.earliestTimeElapsed, longArrayQueue.get(0));
    }

    public void reset() {
      this.earliestTimeElapsed = Long.MAX_VALUE;
    }
  }

  public void maybeScheduleCleanupAlarmLocked() {
    if (this.mNextCleanupTimeElapsed > this.mInjector.getElapsedRealtime()) {
      return;
    }
    this.mEarliestEventTimeFunctor.reset();
    this.mEventTimes.forEach(this.mEarliestEventTimeFunctor);
    long j = this.mEarliestEventTimeFunctor.earliestTimeElapsed;
    if (j == Long.MAX_VALUE) {
      return;
    }
    long j2 = j + this.mMaxPeriodMs;
    if (j2 - this.mNextCleanupTimeElapsed <= 600000) {
      j2 += 600000;
    }
    long j3 = j2;
    this.mNextCleanupTimeElapsed = j3;
    scheduleAlarm(3, j3, ALARM_TAG_CLEANUP, this.mEventCleanupAlarmListener);
  }

  public final boolean maybeUpdateStatusForUptcLocked(int i, String str, String str2) {
    boolean isWithinQuotaLocked = isWithinQuotaLocked(getExecutionStatsLocked(i, str, str2, false));
    boolean isWithinQuotaLocked2 =
        (!isEnabledLocked() || isQuotaFreeLocked(i, str))
            ? true
            : isWithinQuotaLocked(getExecutionStatsLocked(i, str, str2, true));
    if (!isWithinQuotaLocked2) {
      maybeScheduleStartAlarmLocked(i, str, str2);
    } else {
      cancelScheduledStartAlarmLocked(i, str, str2);
    }
    if (isWithinQuotaLocked == isWithinQuotaLocked2) {
      return false;
    }
    postQuotaStatusChanged(i, str, str2);
    return true;
  }

  public final class DeleteEventTimesFunctor implements Consumer {
    public long mMaxPeriodMs;

    public DeleteEventTimesFunctor() {}

    @Override // java.util.function.Consumer
    public void accept(LongArrayQueue longArrayQueue) {
      if (longArrayQueue != null) {
        while (longArrayQueue.size() > 0
            && longArrayQueue.peekFirst()
                <= CountQuotaTracker.this.mInjector.getElapsedRealtime() - this.mMaxPeriodMs) {
          longArrayQueue.removeFirst();
        }
      }
    }

    public final void updateMaxPeriod() {
      long j = 0;
      for (int size = CountQuotaTracker.this.mCategoryCountWindowSizesMs.size() - 1;
          size >= 0;
          size--) {
        j =
            Long.max(
                j,
                ((Long) CountQuotaTracker.this.mCategoryCountWindowSizesMs.valueAt(size))
                    .longValue());
      }
      this.mMaxPeriodMs = j;
    }
  }

  public void deleteObsoleteEventsLocked() {
    this.mEventTimes.forEach(this.mDeleteOldEventTimesFunctor);
  }

  public class CqtHandler extends Handler {
    public CqtHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      synchronized (CountQuotaTracker.this.mLock) {
        if (message.what == 1) {
          CountQuotaTracker.this.deleteObsoleteEventsLocked();
          CountQuotaTracker.this.maybeScheduleCleanupAlarmLocked();
        }
      }
    }
  }

  public static /* synthetic */ LongArrayQueue lambda$new$4(Void r0) {
    return new LongArrayQueue();
  }

  public static /* synthetic */ ExecutionStats lambda$new$5(Void r0) {
    return new ExecutionStats();
  }

  public LongArrayQueue getEvents(int i, String str, String str2) {
    return (LongArrayQueue) this.mEventTimes.get(i, str, str2);
  }

  @Override // com.android.server.utils.quota.QuotaTracker
  public void dump(final ProtoOutputStream protoOutputStream, long j) {
    long start = protoOutputStream.start(j);
    synchronized (this.mLock) {
      super.dump(protoOutputStream, 1146756268033L);
      for (int i = 0; i < this.mCategoryCountWindowSizesMs.size(); i++) {
        Category category = (Category) this.mCategoryCountWindowSizesMs.keyAt(i);
        long start2 = protoOutputStream.start(2246267895810L);
        category.dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(
            1120986464258L, ((Integer) this.mMaxCategoryCounts.get(category)).intValue());
        protoOutputStream.write(
            1112396529667L, ((Long) this.mCategoryCountWindowSizesMs.get(category)).longValue());
        protoOutputStream.end(start2);
      }
      this.mExecutionStatsCache.forEach(
          new UptcMap
              .UptcDataConsumer() { // from class:
                                    // com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda4
            @Override // com.android.server.utils.quota.UptcMap.UptcDataConsumer
            public final void accept(int i2, String str, String str2, Object obj) {
              CountQuotaTracker.this.lambda$dump$8(
                  protoOutputStream, i2, str, str2, (CountQuotaTracker.ExecutionStats) obj);
            }
          });
      protoOutputStream.end(start);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$dump$8(
      ProtoOutputStream protoOutputStream,
      int i,
      String str,
      String str2,
      ExecutionStats executionStats) {
    boolean isIndividualQuotaFreeLocked = isIndividualQuotaFreeLocked(i, str);
    long start = protoOutputStream.start(2246267895811L);
    new Uptc(i, str, str2).dumpDebug(protoOutputStream, 1146756268033L);
    protoOutputStream.write(1133871366146L, isIndividualQuotaFreeLocked);
    LongArrayQueue longArrayQueue = (LongArrayQueue) this.mEventTimes.get(i, str, str2);
    if (longArrayQueue != null) {
      for (int size = longArrayQueue.size() - 1; size >= 0; size--) {
        long start2 = protoOutputStream.start(2246267895811L);
        protoOutputStream.write(1112396529665L, longArrayQueue.get(size));
        protoOutputStream.end(start2);
      }
    }
    long start3 = protoOutputStream.start(2246267895812L);
    protoOutputStream.write(1112396529665L, executionStats.expirationTimeElapsed);
    protoOutputStream.write(1112396529666L, executionStats.windowSizeMs);
    protoOutputStream.write(1120986464259L, executionStats.countLimit);
    protoOutputStream.write(1120986464260L, executionStats.countInWindow);
    protoOutputStream.write(1112396529669L, executionStats.inQuotaTimeElapsed);
    protoOutputStream.end(start3);
    protoOutputStream.end(start);
  }
}
