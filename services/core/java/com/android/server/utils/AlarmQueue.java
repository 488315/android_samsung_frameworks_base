package com.android.server.utils;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Pair;

import java.util.PriorityQueue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AlarmQueue implements AlarmManager.OnAlarmListener {
    public final AlarmPriorityQueue mAlarmPriorityQueue;
    public final String mAlarmTag;
    public final Context mContext;
    public final String mDumpTitle;
    public final boolean mExactAlarm;
    public final Handler mHandler;
    public final Injector mInjector;
    public long mLastFireTimeElapsed;
    public final Object mLock;
    public long mMinTimeBetweenAlarmsMs;
    public final AnonymousClass1 mScheduleAlarmRunnable;
    public long mTriggerTimeElapsed;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class AlarmPriorityQueue extends PriorityQueue {
        public static final AlarmQueue$AlarmPriorityQueue$$ExternalSyntheticLambda0
                sTimeComparator = new AlarmQueue$AlarmPriorityQueue$$ExternalSyntheticLambda0();

        public AlarmPriorityQueue() {
            super(1, sTimeComparator);
        }

        public final boolean removeKey(Object obj) {
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {}

    public AlarmQueue(Context context, Looper looper, String str, String str2, boolean z, long j) {
        this(context, looper, str, str2, z, j, new Injector());
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.utils.AlarmQueue$1] */
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
                    public final void run() {
                        AlarmQueue.this.mHandler.removeCallbacks(this);
                        AlarmManager alarmManager =
                                (AlarmManager)
                                        AlarmQueue.this.mContext.getSystemService(
                                                AlarmManager.class);
                        if (alarmManager == null) {
                            AlarmQueue.this.mHandler.postDelayed(this, 30000L);
                            return;
                        }
                        synchronized (AlarmQueue.this.mLock) {
                            try {
                                AlarmQueue alarmQueue = AlarmQueue.this;
                                long j2 = alarmQueue.mTriggerTimeElapsed;
                                if (j2 == -1) {
                                    return;
                                }
                                long j3 = alarmQueue.mMinTimeBetweenAlarmsMs;
                                if (alarmQueue.mExactAlarm) {
                                    alarmManager.setExact(
                                            3,
                                            j2,
                                            alarmQueue.mAlarmTag,
                                            alarmQueue,
                                            alarmQueue.mHandler);
                                } else {
                                    alarmManager.setWindow(
                                            3,
                                            j2,
                                            j3 / 2,
                                            alarmQueue.mAlarmTag,
                                            alarmQueue,
                                            alarmQueue.mHandler);
                                }
                            } catch (Throwable th) {
                                throw th;
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

    public final void addAlarm(long j, Object obj) {
        synchronized (this.mLock) {
            boolean removeKey = this.mAlarmPriorityQueue.removeKey(obj);
            this.mAlarmPriorityQueue.offer(new Pair(obj, Long.valueOf(j)));
            long j2 = this.mTriggerTimeElapsed;
            if (j2 == -1 || removeKey || j < j2) {
                setNextAlarmLocked(this.mLastFireTimeElapsed + this.mMinTimeBetweenAlarmsMs);
            }
        }
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.print(this.mDumpTitle);
                indentingPrintWriter.println(" alarms:");
                indentingPrintWriter.increaseIndent();
                if (this.mAlarmPriorityQueue.size() == 0) {
                    indentingPrintWriter.println("NOT WAITING");
                } else {
                    AlarmPriorityQueue alarmPriorityQueue = this.mAlarmPriorityQueue;
                    Pair[] pairArr =
                            (Pair[])
                                    alarmPriorityQueue.toArray(new Pair[alarmPriorityQueue.size()]);
                    for (int i = 0; i < pairArr.length; i++) {
                        indentingPrintWriter.print(pairArr[i].first);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.print(pairArr[i].second);
                        indentingPrintWriter.println();
                    }
                }
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract boolean isForUser(int i, Object obj);

    @Override // android.app.AlarmManager.OnAlarmListener
    public final void onAlarm() {
        ArraySet arraySet = new ArraySet();
        synchronized (this.mLock) {
            try {
                this.mInjector.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                this.mLastFireTimeElapsed = elapsedRealtime;
                while (this.mAlarmPriorityQueue.size() > 0) {
                    Pair pair = (Pair) this.mAlarmPriorityQueue.peek();
                    if (((Long) pair.second).longValue() > elapsedRealtime) {
                        break;
                    }
                    arraySet.add(pair.first);
                    this.mAlarmPriorityQueue.remove(pair);
                }
                setNextAlarmLocked(elapsedRealtime + this.mMinTimeBetweenAlarmsMs);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (arraySet.size() > 0) {
            processExpiredAlarms(arraySet);
        }
    }

    public abstract void processExpiredAlarms(ArraySet arraySet);

    public final void removeAlarmForKey(Object obj) {
        synchronized (this.mLock) {
            if (this.mAlarmPriorityQueue.removeKey(obj)) {
                setNextAlarmLocked(this.mLastFireTimeElapsed + this.mMinTimeBetweenAlarmsMs);
            }
        }
    }

    public final void removeAlarmsForUserId(int i) {
        synchronized (this.mLock) {
            try {
                AlarmPriorityQueue alarmPriorityQueue = this.mAlarmPriorityQueue;
                Pair[] pairArr =
                        (Pair[]) alarmPriorityQueue.toArray(new Pair[alarmPriorityQueue.size()]);
                boolean z = false;
                for (int length = pairArr.length - 1; length >= 0; length--) {
                    if (isForUser(i, pairArr[length].first)) {
                        this.mAlarmPriorityQueue.remove(pairArr[length]);
                        z = true;
                    }
                }
                if (z) {
                    setNextAlarmLocked(this.mLastFireTimeElapsed + this.mMinTimeBetweenAlarmsMs);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeAllAlarms() {
        synchronized (this.mLock) {
            this.mAlarmPriorityQueue.clear();
            setNextAlarmLocked(0L);
        }
    }

    public final void setMinTimeBetweenAlarmsMs(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("min time between alarms must be non-negative");
        }
        synchronized (this.mLock) {
            this.mMinTimeBetweenAlarmsMs = j;
        }
    }

    public final void setNextAlarmLocked(long j) {
        if (this.mAlarmPriorityQueue.size() == 0) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.utils.AlarmQueue$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AlarmQueue alarmQueue = AlarmQueue.this;
                            AlarmManager alarmManager =
                                    (AlarmManager)
                                            alarmQueue.mContext.getSystemService(
                                                    AlarmManager.class);
                            if (alarmManager != null) {
                                alarmManager.cancel(alarmQueue);
                            }
                        }
                    });
            this.mTriggerTimeElapsed = -1L;
            return;
        }
        long max =
                Math.max(j, ((Long) ((Pair) this.mAlarmPriorityQueue.peek()).second).longValue());
        long min = Math.min(60000L, this.mMinTimeBetweenAlarmsMs);
        long j2 = this.mTriggerTimeElapsed;
        if (j2 == -1 || max < j2 - min || j2 < max) {
            this.mTriggerTimeElapsed = max;
            this.mHandler.post(this.mScheduleAlarmRunnable);
        }
    }
}
