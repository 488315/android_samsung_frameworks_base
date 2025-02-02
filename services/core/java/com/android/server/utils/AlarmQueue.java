package com.android.server.utils;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public abstract class AlarmQueue implements AlarmManager.OnAlarmListener {
  public static final String TAG = AlarmQueue.class.getSimpleName();
  public final AlarmPriorityQueue mAlarmPriorityQueue;
  public final String mAlarmTag;
  public final Context mContext;
  public final String mDumpTitle;
  public final boolean mExactAlarm;
  public final Handler mHandler;
  public final Injector mInjector;
  public final Object mLock;
  public long mMinTimeBetweenAlarmsMs;
  public final Runnable mScheduleAlarmRunnable;
  public long mTriggerTimeElapsed;

  public abstract boolean isForUser(Object obj, int i);

  public abstract void processExpiredAlarms(ArraySet arraySet);

  class AlarmPriorityQueue extends PriorityQueue {
    public static final Comparator sTimeComparator =
        new Comparator() { // from class:
                           // com.android.server.utils.AlarmQueue$AlarmPriorityQueue$$ExternalSyntheticLambda0
          @Override // java.util.Comparator
          public final int compare(Object obj, Object obj2) {
            int lambda$static$0;
            lambda$static$0 =
                AlarmQueue.AlarmPriorityQueue.lambda$static$0((Pair) obj, (Pair) obj2);
            return lambda$static$0;
          }
        };

    public static /* synthetic */ int lambda$static$0(Pair pair, Pair pair2) {
      return Long.compare(((Long) pair.second).longValue(), ((Long) pair2.second).longValue());
    }

    public AlarmPriorityQueue() {
      super(1, sTimeComparator);
    }

    public boolean removeKey(Object obj) {
      Pair[] pairArr = (Pair[]) toArray(new Pair[size()]);
      boolean z = false;
      for (int length = pairArr.length - 1; length >= 0; length--) {
        if (obj.equals(pairArr[length].first)) {
          remove(pairArr[length]);
          z = true;
        }
      }
      return z;
    }
  }

  class Injector {
    public long getElapsedRealtime() {
      return SystemClock.elapsedRealtime();
    }
  }

  public AlarmQueue(Context context, Looper looper, String str, String str2, boolean z, long j) {
    this(context, looper, str, str2, z, j, new Injector());
  }

  public AlarmQueue(
      Context context,
      Looper looper,
      String str,
      String str2,
      boolean z,
      long j,
      Injector injector) {
    this.mScheduleAlarmRunnable =
        new Runnable() { // from class: com.android.server.utils.AlarmQueue.1
          @Override // java.lang.Runnable
          public void run() {
            AlarmQueue.this.mHandler.removeCallbacks(this);
            AlarmManager alarmManager =
                (AlarmManager) AlarmQueue.this.mContext.getSystemService(AlarmManager.class);
            if (alarmManager == null) {
              AlarmQueue.this.mHandler.postDelayed(this, 30000L);
              return;
            }
            synchronized (AlarmQueue.this.mLock) {
              if (AlarmQueue.this.mTriggerTimeElapsed == -1) {
                return;
              }
              long j2 = AlarmQueue.this.mTriggerTimeElapsed;
              long j3 = AlarmQueue.this.mMinTimeBetweenAlarmsMs;
              if (AlarmQueue.this.mExactAlarm) {
                String str3 = AlarmQueue.this.mAlarmTag;
                AlarmQueue alarmQueue = AlarmQueue.this;
                alarmManager.setExact(3, j2, str3, alarmQueue, alarmQueue.mHandler);
              } else {
                String str4 = AlarmQueue.this.mAlarmTag;
                AlarmQueue alarmQueue2 = AlarmQueue.this;
                alarmManager.setWindow(3, j2, j3 / 2, str4, alarmQueue2, alarmQueue2.mHandler);
              }
            }
          }
        };
    this.mLock = new Object();
    this.mAlarmPriorityQueue = new AlarmPriorityQueue();
    this.mTriggerTimeElapsed = -1L;
    this.mContext = context;
    this.mAlarmTag = str;
    this.mDumpTitle = str2.trim();
    this.mExactAlarm = z;
    this.mHandler = new Handler(looper);
    this.mInjector = injector;
    if (j < 0) {
      throw new IllegalArgumentException("min time between alarms must be non-negative");
    }
    this.mMinTimeBetweenAlarmsMs = j;
  }

  public void addAlarm(Object obj, long j) {
    synchronized (this.mLock) {
      boolean removeKey = this.mAlarmPriorityQueue.removeKey(obj);
      this.mAlarmPriorityQueue.offer(new Pair(obj, Long.valueOf(j)));
      long j2 = this.mTriggerTimeElapsed;
      if (j2 == -1 || removeKey || j < j2) {
        setNextAlarmLocked();
      }
    }
  }

  public long getMinTimeBetweenAlarmsMs() {
    long j;
    synchronized (this.mLock) {
      j = this.mMinTimeBetweenAlarmsMs;
    }
    return j;
  }

  public void removeAlarmForKey(Object obj) {
    synchronized (this.mLock) {
      if (this.mAlarmPriorityQueue.removeKey(obj)) {
        setNextAlarmLocked();
      }
    }
  }

  public void removeAlarmsForUserId(int i) {
    synchronized (this.mLock) {
      AlarmPriorityQueue alarmPriorityQueue = this.mAlarmPriorityQueue;
      Pair[] pairArr = (Pair[]) alarmPriorityQueue.toArray(new Pair[alarmPriorityQueue.size()]);
      boolean z = false;
      for (int length = pairArr.length - 1; length >= 0; length--) {
        if (isForUser(pairArr[length].first, i)) {
          this.mAlarmPriorityQueue.remove(pairArr[length]);
          z = true;
        }
      }
      if (z) {
        setNextAlarmLocked();
      }
    }
  }

  public void removeAllAlarms() {
    synchronized (this.mLock) {
      this.mAlarmPriorityQueue.clear();
      setNextAlarmLocked(0L);
    }
  }

  public void removeAlarmsIf(Predicate predicate) {
    synchronized (this.mLock) {
      AlarmPriorityQueue alarmPriorityQueue = this.mAlarmPriorityQueue;
      Pair[] pairArr = (Pair[]) alarmPriorityQueue.toArray(new Pair[alarmPriorityQueue.size()]);
      boolean z = false;
      for (int length = pairArr.length - 1; length >= 0; length--) {
        if (predicate.test(pairArr[length].first)) {
          this.mAlarmPriorityQueue.remove(pairArr[length]);
          z = true;
        }
      }
      if (z) {
        setNextAlarmLocked();
      }
    }
  }

  public void setMinTimeBetweenAlarmsMs(long j) {
    if (j < 0) {
      throw new IllegalArgumentException("min time between alarms must be non-negative");
    }
    synchronized (this.mLock) {
      this.mMinTimeBetweenAlarmsMs = j;
    }
  }

  public final void setNextAlarmLocked() {
    setNextAlarmLocked(this.mInjector.getElapsedRealtime());
  }

  public final void setNextAlarmLocked(long j) {
    if (this.mAlarmPriorityQueue.size() == 0) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.utils.AlarmQueue$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              AlarmQueue.this.lambda$setNextAlarmLocked$0();
            }
          });
      this.mTriggerTimeElapsed = -1L;
      return;
    }
    long max = Math.max(j, ((Long) ((Pair) this.mAlarmPriorityQueue.peek()).second).longValue());
    long min = Math.min(60000L, this.mMinTimeBetweenAlarmsMs);
    long j2 = this.mTriggerTimeElapsed;
    if (j2 == -1 || max < j2 - min || j2 < max) {
      this.mTriggerTimeElapsed = max;
      this.mHandler.post(this.mScheduleAlarmRunnable);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setNextAlarmLocked$0() {
    AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
    if (alarmManager != null) {
      alarmManager.cancel(this);
    }
  }

  @Override // android.app.AlarmManager.OnAlarmListener
  public void onAlarm() {
    ArraySet arraySet = new ArraySet();
    synchronized (this.mLock) {
      long elapsedRealtime = this.mInjector.getElapsedRealtime();
      while (this.mAlarmPriorityQueue.size() > 0) {
        Pair pair = (Pair) this.mAlarmPriorityQueue.peek();
        if (((Long) pair.second).longValue() > elapsedRealtime) {
          break;
        }
        arraySet.add(pair.first);
        this.mAlarmPriorityQueue.remove(pair);
      }
      setNextAlarmLocked(elapsedRealtime + this.mMinTimeBetweenAlarmsMs);
    }
    if (arraySet.size() > 0) {
      processExpiredAlarms(arraySet);
    }
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      indentingPrintWriter.print(this.mDumpTitle);
      indentingPrintWriter.println(" alarms:");
      indentingPrintWriter.increaseIndent();
      if (this.mAlarmPriorityQueue.size() == 0) {
        indentingPrintWriter.println("NOT WAITING");
      } else {
        AlarmPriorityQueue alarmPriorityQueue = this.mAlarmPriorityQueue;
        Pair[] pairArr = (Pair[]) alarmPriorityQueue.toArray(new Pair[alarmPriorityQueue.size()]);
        for (int i = 0; i < pairArr.length; i++) {
          indentingPrintWriter.print(pairArr[i].first);
          indentingPrintWriter.print(": ");
          indentingPrintWriter.print(pairArr[i].second);
          indentingPrintWriter.println();
        }
      }
      indentingPrintWriter.decreaseIndent();
    }
  }
}
