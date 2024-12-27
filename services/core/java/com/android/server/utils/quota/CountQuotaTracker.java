package com.android.server.utils.quota;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.LongArrayQueue;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.SystemServiceManager;

import java.util.function.Consumer;

public final class CountQuotaTracker extends QuotaTracker {
    public static final String ALARM_TAG_CLEANUP =
            XmlUtils$$ExternalSyntheticOutline0.m("*", "CountQuotaTracker", ".cleanup*");
    public final ArrayMap mCategoryCountWindowSizesMs;
    public final CountQuotaTracker$$ExternalSyntheticLambda2 mCreateExecutionStats;
    public final CountQuotaTracker$$ExternalSyntheticLambda2 mCreateLongArrayQueue;
    public final DeleteEventTimesFunctor mDeleteOldEventTimesFunctor;
    public final EarliestEventTimeFunctor mEarliestEventTimeFunctor;
    public final CountQuotaTracker$$ExternalSyntheticLambda0 mEventCleanupAlarmListener;
    public final UptcMap mEventTimes;
    public final UptcMap mExecutionStatsCache;
    public final CqtHandler mHandler;
    public final ArrayMap mMaxCategoryCounts;
    public long mMaxPeriodMs;
    public long mNextCleanupTimeElapsed;

    public final class CqtHandler extends Handler {
        public CqtHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            synchronized (CountQuotaTracker.this.mLock) {
                if (message.what == 1) {
                    CountQuotaTracker.this.deleteObsoleteEventsLocked();
                    CountQuotaTracker.this.maybeScheduleCleanupAlarmLocked();
                }
            }
        }
    }

    public final class DeleteEventTimesFunctor implements Consumer {
        public long mMaxPeriodMs;

        public DeleteEventTimesFunctor() {}

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            LongArrayQueue longArrayQueue = (LongArrayQueue) obj;
            if (longArrayQueue != null) {
                while (longArrayQueue.size() > 0) {
                    long peekFirst = longArrayQueue.peekFirst();
                    CountQuotaTracker.this.mInjector.getClass();
                    if (peekFirst > SystemClock.elapsedRealtime() - this.mMaxPeriodMs) {
                        return;
                    } else {
                        longArrayQueue.removeFirst();
                    }
                }
            }
        }
    }

    public final class EarliestEventTimeFunctor implements Consumer {
        public long earliestTimeElapsed;

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            LongArrayQueue longArrayQueue = (LongArrayQueue) obj;
            if (longArrayQueue == null || longArrayQueue.size() <= 0) {
                return;
            }
            this.earliestTimeElapsed = Math.min(this.earliestTimeElapsed, longArrayQueue.get(0));
        }
    }

    class ExecutionStats {
        public int countInWindow;
        public int countLimit;
        public long expirationTimeElapsed;
        public long inQuotaTimeElapsed;
        public long windowSizeMs;

        public final boolean equals(Object obj) {
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

        public final int hashCode() {
            return Long.hashCode(this.inQuotaTimeElapsed)
                    + ((((((Long.hashCode(this.windowSizeMs)
                                                                    + (Long.hashCode(
                                                                                    this
                                                                                            .expirationTimeElapsed)
                                                                            * 31))
                                                            * 31)
                                                    + this.countLimit)
                                            * 31)
                                    + this.countInWindow)
                            * 31);
        }

        public final String toString() {
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
    }

    public CountQuotaTracker(Context context, Categorizer categorizer) {
        this(context, categorizer, new QuotaTracker.Injector());
    }

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
                        CountQuotaTracker.this.mHandler.obtainMessage(1).sendToTarget();
                    }
                };
        this.mCategoryCountWindowSizesMs = new ArrayMap();
        this.mMaxCategoryCounts = new ArrayMap();
        this.mMaxPeriodMs = 0L;
        EarliestEventTimeFunctor earliestEventTimeFunctor = new EarliestEventTimeFunctor();
        earliestEventTimeFunctor.earliestTimeElapsed = Long.MAX_VALUE;
        this.mEarliestEventTimeFunctor = earliestEventTimeFunctor;
        this.mDeleteOldEventTimesFunctor = new DeleteEventTimesFunctor();
        this.mCreateLongArrayQueue = new CountQuotaTracker$$ExternalSyntheticLambda2(1);
        this.mCreateExecutionStats = new CountQuotaTracker$$ExternalSyntheticLambda2(0);
        this.mHandler = new CqtHandler(context.getMainLooper());
    }

    public void deleteObsoleteEventsLocked() {
        this.mEventTimes.mData.forEach(
                new UptcMap$$ExternalSyntheticLambda0(this.mDeleteOldEventTimesFunctor));
    }

    @Override // com.android.server.utils.quota.QuotaTracker
    public final void dump(final IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("CountQuotaTracker");
        indentingPrintWriter.println(":");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                super.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Instantaneous events:");
                indentingPrintWriter.increaseIndent();
                final int i = 1;
                this.mEventTimes.forEach(
                        new UptcMap
                                .UptcDataConsumer() { // from class:
                                                      // com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda7
                            @Override // com.android.server.utils.quota.UptcMap.UptcDataConsumer
                            public final void accept(int i2, Object obj, String str, String str2) {
                                int i3 = i;
                                IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                                switch (i3) {
                                    case 0:
                                        CountQuotaTracker.ExecutionStats executionStats =
                                                (CountQuotaTracker.ExecutionStats) obj;
                                        if (executionStats != null) {
                                            indentingPrintWriter2.print(Uptc.string(i2, str, str2));
                                            indentingPrintWriter2.println(":");
                                            indentingPrintWriter2.increaseIndent();
                                            indentingPrintWriter2.println(executionStats);
                                            indentingPrintWriter2.decreaseIndent();
                                            break;
                                        }
                                        break;
                                    default:
                                        LongArrayQueue longArrayQueue = (LongArrayQueue) obj;
                                        if (longArrayQueue.size() > 0) {
                                            indentingPrintWriter2.print(Uptc.string(i2, str, str2));
                                            indentingPrintWriter2.println(":");
                                            indentingPrintWriter2.increaseIndent();
                                            indentingPrintWriter2.print(longArrayQueue.get(0));
                                            for (int i4 = 1; i4 < longArrayQueue.size(); i4++) {
                                                indentingPrintWriter2.print(", ");
                                                indentingPrintWriter2.print(longArrayQueue.get(i4));
                                            }
                                            indentingPrintWriter2.decreaseIndent();
                                            indentingPrintWriter2.println();
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Cached execution stats:");
                indentingPrintWriter.increaseIndent();
                final int i2 = 0;
                this.mExecutionStatsCache.forEach(
                        new UptcMap
                                .UptcDataConsumer() { // from class:
                                                      // com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda7
                            @Override // com.android.server.utils.quota.UptcMap.UptcDataConsumer
                            public final void accept(int i22, Object obj, String str, String str2) {
                                int i3 = i2;
                                IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                                switch (i3) {
                                    case 0:
                                        CountQuotaTracker.ExecutionStats executionStats =
                                                (CountQuotaTracker.ExecutionStats) obj;
                                        if (executionStats != null) {
                                            indentingPrintWriter2.print(
                                                    Uptc.string(i22, str, str2));
                                            indentingPrintWriter2.println(":");
                                            indentingPrintWriter2.increaseIndent();
                                            indentingPrintWriter2.println(executionStats);
                                            indentingPrintWriter2.decreaseIndent();
                                            break;
                                        }
                                        break;
                                    default:
                                        LongArrayQueue longArrayQueue = (LongArrayQueue) obj;
                                        if (longArrayQueue.size() > 0) {
                                            indentingPrintWriter2.print(
                                                    Uptc.string(i22, str, str2));
                                            indentingPrintWriter2.println(":");
                                            indentingPrintWriter2.increaseIndent();
                                            indentingPrintWriter2.print(longArrayQueue.get(0));
                                            for (int i4 = 1; i4 < longArrayQueue.size(); i4++) {
                                                indentingPrintWriter2.print(", ");
                                                indentingPrintWriter2.print(longArrayQueue.get(i4));
                                            }
                                            indentingPrintWriter2.decreaseIndent();
                                            indentingPrintWriter2.println();
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Limits:");
                indentingPrintWriter.increaseIndent();
                int size = this.mCategoryCountWindowSizesMs.size();
                for (int i3 = 0; i3 < size; i3++) {
                    Category category = (Category) this.mCategoryCountWindowSizesMs.keyAt(i3);
                    indentingPrintWriter.print(category);
                    indentingPrintWriter.print(": ");
                    indentingPrintWriter.print(this.mMaxCategoryCounts.get(category));
                    indentingPrintWriter.print(" events in ");
                    indentingPrintWriter.println(
                            TimeUtils.formatDuration(
                                    ((Long) this.mCategoryCountWindowSizesMs.get(category))
                                            .longValue()));
                }
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final void dump(ProtoOutputStream protoOutputStream) {
        int i;
        long start = protoOutputStream.start(1146756268054L);
        synchronized (this.mLock) {
            long j = 1146756268033L;
            try {
                long start2 = protoOutputStream.start(1146756268033L);
                synchronized (this.mLock) {
                    protoOutputStream.write(1133871366145L, this.mIsEnabled);
                    i = 0;
                    protoOutputStream.write(1133871366146L, false);
                    this.mInjector.getClass();
                    protoOutputStream.write(1112396529667L, SystemClock.elapsedRealtime());
                }
                protoOutputStream.end(start2);
                while (i < this.mCategoryCountWindowSizesMs.size()) {
                    Category category = (Category) this.mCategoryCountWindowSizesMs.keyAt(i);
                    long start3 = protoOutputStream.start(2246267895810L);
                    category.getClass();
                    long start4 = protoOutputStream.start(j);
                    protoOutputStream.write(1138166333441L, category.mName);
                    protoOutputStream.end(start4);
                    protoOutputStream.write(
                            1120986464258L,
                            ((Integer) this.mMaxCategoryCounts.get(category)).intValue());
                    protoOutputStream.write(
                            1112396529667L,
                            ((Long) this.mCategoryCountWindowSizesMs.get(category)).longValue());
                    protoOutputStream.end(start3);
                    i++;
                    j = 1146756268033L;
                }
                this.mExecutionStatsCache.forEach(
                        new CountQuotaTracker$$ExternalSyntheticLambda5(
                                this, protoOutputStream, 0));
                protoOutputStream.end(start);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public LongArrayQueue getEvents(int i, String str, String str2) {
        return (LongArrayQueue) this.mEventTimes.get(i, str, str2);
    }

    public ExecutionStats getExecutionStatsLocked(int i, String str, String str2) {
        return getExecutionStatsLocked(i, str, str2, true);
    }

    public final ExecutionStats getExecutionStatsLocked(int i, String str, String str2, boolean z) {
        Object apply;
        CountQuotaTracker$$ExternalSyntheticLambda2 countQuotaTracker$$ExternalSyntheticLambda2 =
                this.mCreateExecutionStats;
        UptcMap uptcMap = this.mExecutionStatsCache;
        ArrayMap arrayMap = (ArrayMap) uptcMap.mData.get(i, str);
        if (arrayMap == null || !arrayMap.containsKey(str2)) {
            apply = countQuotaTracker$$ExternalSyntheticLambda2.apply(null);
            ArrayMap arrayMap2 = (ArrayMap) uptcMap.mData.get(i, str);
            if (arrayMap2 == null) {
                arrayMap2 = new ArrayMap();
                uptcMap.mData.add(i, str, arrayMap2);
            }
            arrayMap2.put(str2, apply);
        } else {
            apply = arrayMap.get(str2);
        }
        ExecutionStats executionStats = (ExecutionStats) apply;
        if (z) {
            Category category = this.mCategorizer.getCategory(str2);
            long longValue =
                    ((Long) this.mCategoryCountWindowSizesMs.getOrDefault(category, Long.MAX_VALUE))
                            .longValue();
            int intValue =
                    ((Integer) this.mMaxCategoryCounts.getOrDefault(category, Integer.MAX_VALUE))
                            .intValue();
            long j = executionStats.expirationTimeElapsed;
            this.mInjector.getClass();
            if (j <= SystemClock.elapsedRealtime()
                    || executionStats.windowSizeMs != longValue
                    || executionStats.countLimit != intValue) {
                executionStats.windowSizeMs = longValue;
                executionStats.countLimit = intValue;
                updateExecutionStatsLocked(i, str, str2, executionStats);
            }
        }
        return executionStats;
    }

    public void maybeScheduleCleanupAlarmLocked() {
        long j = this.mNextCleanupTimeElapsed;
        this.mInjector.getClass();
        if (j > SystemClock.elapsedRealtime()) {
            return;
        }
        EarliestEventTimeFunctor earliestEventTimeFunctor = this.mEarliestEventTimeFunctor;
        earliestEventTimeFunctor.earliestTimeElapsed = Long.MAX_VALUE;
        this.mEventTimes.mData.forEach(
                new UptcMap$$ExternalSyntheticLambda0(earliestEventTimeFunctor));
        long j2 = earliestEventTimeFunctor.earliestTimeElapsed;
        if (j2 == Long.MAX_VALUE) {
            return;
        }
        final long j3 = j2 + this.mMaxPeriodMs;
        if (j3 - this.mNextCleanupTimeElapsed <= 600000) {
            j3 += 600000;
        }
        this.mNextCleanupTimeElapsed = j3;
        Handler handler = FgThread.getHandler();
        final CountQuotaTracker$$ExternalSyntheticLambda0
                countQuotaTracker$$ExternalSyntheticLambda0 = this.mEventCleanupAlarmListener;
        handler.post(
                new Runnable() { // from class:
                                 // com.android.server.utils.quota.QuotaTracker$$ExternalSyntheticLambda1
                    public final /* synthetic */ int f$1;
                    public final /* synthetic */ String f$3;

                    {
                        String str = CountQuotaTracker.ALARM_TAG_CLEANUP;
                        this.f$1 = 3;
                        this.f$3 = str;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        QuotaTracker quotaTracker = this;
                        int i = this.f$1;
                        long j4 = j3;
                        String str = this.f$3;
                        AlarmManager.OnAlarmListener onAlarmListener =
                                countQuotaTracker$$ExternalSyntheticLambda0;
                        quotaTracker.mInjector.getClass();
                        if (((SystemServiceManager)
                                        LocalServices.getService(SystemServiceManager.class))
                                .isBootCompleted()) {
                            quotaTracker.mAlarmManager.set(
                                    i,
                                    j4,
                                    str,
                                    onAlarmListener,
                                    ((CountQuotaTracker) quotaTracker).mHandler);
                        } else {
                            Slog.w(
                                    "QuotaTracker",
                                    "Alarm not scheduled because boot isn't completed");
                        }
                    }
                });
    }

    public final void maybeUpdateStatusForUptcLocked(int i, String str, String str2) {
        ExecutionStats executionStatsLocked = getExecutionStatsLocked(i, str, str2, false);
        boolean z = true;
        boolean z2 = executionStatsLocked.countInWindow < executionStatsLocked.countLimit;
        if (this.mIsEnabled
                && !((Boolean) this.mFreeQuota.getOrDefault(i, str, Boolean.FALSE))
                        .booleanValue()) {
            ExecutionStats executionStatsLocked2 = getExecutionStatsLocked(i, str, str2, true);
            z = executionStatsLocked2.countInWindow < executionStatsLocked2.countLimit;
        }
        if (z) {
            this.mInQuotaAlarmQueue.removeAlarmForKey(new Uptc(i, str, str2));
        } else {
            maybeScheduleStartAlarmLocked(i, str, str2);
        }
        if (z2 != z) {
            BackgroundThread.getHandler()
                    .post(new QuotaTracker$$ExternalSyntheticLambda0(this, i, str, str2));
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean noteEvent(int r11, java.lang.String r12, java.lang.String r13) {
        /*
            r10 = this;
            java.lang.Object r0 = r10.mLock
            monitor-enter(r0)
            boolean r1 = r10.mIsEnabled     // Catch: java.lang.Throwable -> L8e
            r2 = 1
            if (r1 == 0) goto Ldc
            android.util.SparseArrayMap r1 = r10.mFreeQuota     // Catch: java.lang.Throwable -> L8e
            java.lang.Boolean r3 = java.lang.Boolean.FALSE     // Catch: java.lang.Throwable -> L8e
            java.lang.Object r1 = r1.getOrDefault(r11, r12, r3)     // Catch: java.lang.Throwable -> L8e
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch: java.lang.Throwable -> L8e
            boolean r1 = r1.booleanValue()     // Catch: java.lang.Throwable -> L8e
            if (r1 == 0) goto L1a
            goto Ldc
        L1a:
            com.android.server.utils.quota.QuotaTracker$Injector r1 = r10.mInjector     // Catch: java.lang.Throwable -> L8e
            r1.getClass()     // Catch: java.lang.Throwable -> L8e
            long r3 = android.os.SystemClock.elapsedRealtime()     // Catch: java.lang.Throwable -> L8e
            com.android.server.utils.quota.UptcMap r1 = r10.mEventTimes     // Catch: java.lang.Throwable -> L8e
            com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda2 r5 = r10.mCreateLongArrayQueue     // Catch: java.lang.Throwable -> L8e
            android.util.SparseArrayMap r6 = r1.mData     // Catch: java.lang.Throwable -> L8e
            java.lang.Object r6 = r6.get(r11, r12)     // Catch: java.lang.Throwable -> L8e
            android.util.ArrayMap r6 = (android.util.ArrayMap) r6     // Catch: java.lang.Throwable -> L8e
            if (r6 == 0) goto L3d
            boolean r7 = r6.containsKey(r13)     // Catch: java.lang.Throwable -> L8e
            if (r7 != 0) goto L38
            goto L3d
        L38:
            java.lang.Object r1 = r6.get(r13)     // Catch: java.lang.Throwable -> L8e
            goto L5a
        L3d:
            r6 = 0
            java.lang.Object r5 = r5.apply(r6)     // Catch: java.lang.Throwable -> L8e
            android.util.SparseArrayMap r6 = r1.mData     // Catch: java.lang.Throwable -> L8e
            java.lang.Object r6 = r6.get(r11, r12)     // Catch: java.lang.Throwable -> L8e
            android.util.ArrayMap r6 = (android.util.ArrayMap) r6     // Catch: java.lang.Throwable -> L8e
            if (r6 != 0) goto L56
            android.util.ArrayMap r6 = new android.util.ArrayMap     // Catch: java.lang.Throwable -> L8e
            r6.<init>()     // Catch: java.lang.Throwable -> L8e
            android.util.SparseArrayMap r1 = r1.mData     // Catch: java.lang.Throwable -> L8e
            r1.add(r11, r12, r6)     // Catch: java.lang.Throwable -> L8e
        L56:
            r6.put(r13, r5)     // Catch: java.lang.Throwable -> L8e
            r1 = r5
        L5a:
            android.util.LongArrayQueue r1 = (android.util.LongArrayQueue) r1     // Catch: java.lang.Throwable -> L8e
            r1.addLast(r3)     // Catch: java.lang.Throwable -> L8e
            com.android.server.utils.quota.CountQuotaTracker$ExecutionStats r5 = r10.getExecutionStatsLocked(r11, r12, r13)     // Catch: java.lang.Throwable -> L8e
            int r6 = r5.countInWindow     // Catch: java.lang.Throwable -> L8e
            int r6 = r6 + r2
            r5.countInWindow = r6     // Catch: java.lang.Throwable -> L8e
            long r6 = r5.expirationTimeElapsed     // Catch: java.lang.Throwable -> L8e
            long r8 = r5.windowSizeMs     // Catch: java.lang.Throwable -> L8e
            long r8 = r8 + r3
            long r6 = java.lang.Math.min(r6, r8)     // Catch: java.lang.Throwable -> L8e
            r5.expirationTimeElapsed = r6     // Catch: java.lang.Throwable -> L8e
            int r6 = r5.countInWindow     // Catch: java.lang.Throwable -> L8e
            int r7 = r5.countLimit     // Catch: java.lang.Throwable -> L8e
            if (r6 != r7) goto La6
            long r6 = r5.windowSizeMs     // Catch: java.lang.Throwable -> L8e
            long r3 = r3 - r6
        L7c:
            int r6 = r1.size()     // Catch: java.lang.Throwable -> L8e
            if (r6 <= 0) goto L90
            long r6 = r1.peekFirst()     // Catch: java.lang.Throwable -> L8e
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 >= 0) goto L90
            r1.removeFirst()     // Catch: java.lang.Throwable -> L8e
            goto L7c
        L8e:
            r10 = move-exception
            goto Lde
        L90:
            long r3 = r1.peekFirst()     // Catch: java.lang.Throwable -> L8e
            long r6 = r5.windowSizeMs     // Catch: java.lang.Throwable -> L8e
            long r3 = r3 + r6
            r5.inQuotaTimeElapsed = r3     // Catch: java.lang.Throwable -> L8e
            android.os.Handler r1 = com.android.internal.os.BackgroundThread.getHandler()     // Catch: java.lang.Throwable -> L8e
            com.android.server.utils.quota.QuotaTracker$$ExternalSyntheticLambda0 r3 = new com.android.server.utils.quota.QuotaTracker$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L8e
            r3.<init>(r10, r11, r12, r13)     // Catch: java.lang.Throwable -> L8e
            r1.post(r3)     // Catch: java.lang.Throwable -> L8e
            goto Lcf
        La6:
            r1 = 9
            if (r7 <= r1) goto Lcf
            int r7 = r7 * 4
            int r7 = r7 / 5
            if (r6 != r7) goto Lcf
            java.lang.String r1 = "CountQuotaTracker"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8e
            r3.<init>()     // Catch: java.lang.Throwable -> L8e
            java.lang.String r11 = com.android.server.utils.quota.Uptc.string(r11, r12, r13)     // Catch: java.lang.Throwable -> L8e
            r3.append(r11)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r11 = " has reached 80% of it's count limit of "
            r3.append(r11)     // Catch: java.lang.Throwable -> L8e
            int r11 = r5.countLimit     // Catch: java.lang.Throwable -> L8e
            r3.append(r11)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r11 = r3.toString()     // Catch: java.lang.Throwable -> L8e
            android.util.Slog.w(r1, r11)     // Catch: java.lang.Throwable -> L8e
        Lcf:
            r10.maybeScheduleCleanupAlarmLocked()     // Catch: java.lang.Throwable -> L8e
            int r10 = r5.countInWindow     // Catch: java.lang.Throwable -> L8e
            int r11 = r5.countLimit     // Catch: java.lang.Throwable -> L8e
            if (r10 >= r11) goto Ld9
            goto Lda
        Ld9:
            r2 = 0
        Lda:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L8e
            return r2
        Ldc:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L8e
            return r2
        Lde:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L8e
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.utils.quota.CountQuotaTracker.noteEvent(int,"
                    + " java.lang.String, java.lang.String):boolean");
    }

    public final void setCountLimit(Category category, int i, long j) {
        if (i >= 0) {
            long j2 = 0;
            if (j >= 0) {
                synchronized (this.mLock) {
                    try {
                        Integer num =
                                (Integer) this.mMaxCategoryCounts.put(category, Integer.valueOf(i));
                        long max = Math.max(20000L, Math.min(j, 2592000000L));
                        Long l =
                                (Long)
                                        this.mCategoryCountWindowSizesMs.put(
                                                category, Long.valueOf(max));
                        if (num == null
                                || l == null
                                || num.intValue() != i
                                || l.longValue() != max) {
                            DeleteEventTimesFunctor deleteEventTimesFunctor =
                                    this.mDeleteOldEventTimesFunctor;
                            for (int size =
                                            CountQuotaTracker.this.mCategoryCountWindowSizesMs
                                                            .size()
                                                    - 1;
                                    size >= 0;
                                    size--) {
                                j2 =
                                        Long.max(
                                                j2,
                                                ((Long)
                                                                CountQuotaTracker.this
                                                                        .mCategoryCountWindowSizesMs
                                                                        .valueAt(size))
                                                        .longValue());
                            }
                            deleteEventTimesFunctor.mMaxPeriodMs = j2;
                            this.mMaxPeriodMs = this.mDeleteOldEventTimesFunctor.mMaxPeriodMs;
                            this.mInjector.getClass();
                            final long elapsedRealtime = SystemClock.elapsedRealtime();
                            this.mExecutionStatsCache.mData.forEach(
                                    new UptcMap$$ExternalSyntheticLambda0(
                                            new Consumer() { // from class:
                                                             // com.android.server.utils.quota.CountQuotaTracker$$ExternalSyntheticLambda3
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    long j3 = elapsedRealtime;
                                                    CountQuotaTracker.ExecutionStats
                                                            executionStats =
                                                                    (CountQuotaTracker
                                                                                    .ExecutionStats)
                                                                            obj;
                                                    if (executionStats != null) {
                                                        executionStats.expirationTimeElapsed = j3;
                                                    }
                                                }
                                            }));
                            BackgroundThread.getHandler()
                                    .post(new QuotaTracker$$ExternalSyntheticLambda0(this));
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Limit and window size must be nonnegative.");
    }

    public void updateExecutionStatsLocked(
            int i, String str, String str2, ExecutionStats executionStats) {
        executionStats.countInWindow = 0;
        if (executionStats.countLimit == 0) {
            executionStats.inQuotaTimeElapsed = Long.MAX_VALUE;
        } else {
            executionStats.inQuotaTimeElapsed = 0L;
        }
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
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
                        Math.max(
                                executionStats.inQuotaTimeElapsed,
                                j3 + executionStats.windowSizeMs);
            }
        }
        executionStats.expirationTimeElapsed = elapsedRealtime + j;
    }
}
