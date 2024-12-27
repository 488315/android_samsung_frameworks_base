package com.android.server.utils.quota;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Looper;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArrayMap;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.utils.AlarmQueue;

public abstract class QuotaTracker {
    public static final String ALARM_TAG_QUOTA_CHECK =
            XmlUtils$$ExternalSyntheticOutline0.m("*", "QuotaTracker", ".quota_check*");
    static final long MAX_WINDOW_SIZE_MS = 2592000000L;
    static final long MIN_WINDOW_SIZE_MS = 20000;
    public final AlarmManager mAlarmManager;
    public final AnonymousClass1 mBroadcastReceiver;
    public final Categorizer mCategorizer;
    public final InQuotaAlarmQueue mInQuotaAlarmQueue;
    public final Injector mInjector;
    public final Object mLock = new Object();
    public final ArraySet mQuotaChangeListeners = new ArraySet();
    public final SparseArrayMap mFreeQuota = new SparseArrayMap();
    public boolean mIsEnabled = true;

    public final class InQuotaAlarmQueue extends AlarmQueue {
        public InQuotaAlarmQueue(Context context, Looper looper) {
            super(context, looper, QuotaTracker.ALARM_TAG_QUOTA_CHECK, "In quota", false, 0L);
        }

        @Override // com.android.server.utils.AlarmQueue
        public final boolean isForUser(int i, Object obj) {
            return i == ((Uptc) obj).userId;
        }

        @Override // com.android.server.utils.AlarmQueue
        public final void processExpiredAlarms(ArraySet arraySet) {
            for (int i = 0; i < arraySet.size(); i++) {
                final Uptc uptc = (Uptc) arraySet.valueAt(i);
                ((CountQuotaTracker) QuotaTracker.this)
                        .mHandler.post(
                                new Runnable() { // from class:
                                                 // com.android.server.utils.quota.QuotaTracker$InQuotaAlarmQueue$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        QuotaTracker.InQuotaAlarmQueue inQuotaAlarmQueue =
                                                QuotaTracker.InQuotaAlarmQueue.this;
                                        Uptc uptc2 = uptc;
                                        QuotaTracker quotaTracker = QuotaTracker.this;
                                        int i2 = uptc2.userId;
                                        String str = uptc2.packageName;
                                        String str2 = uptc2.tag;
                                        CountQuotaTracker countQuotaTracker =
                                                (CountQuotaTracker) quotaTracker;
                                        synchronized (countQuotaTracker.mLock) {
                                            countQuotaTracker.maybeUpdateStatusForUptcLocked(
                                                    i2, str, str2);
                                        }
                                    }
                                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public class Injector {}

    public QuotaTracker(Context context, Categorizer categorizer, Injector injector) {
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.utils.quota.QuotaTracker.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (intent == null
                                || intent.getBooleanExtra(
                                        "android.intent.extra.REPLACING", false)) {
                            return;
                        }
                        String action = intent.getAction();
                        if (action == null) {
                            String str = QuotaTracker.ALARM_TAG_QUOTA_CHECK;
                            Slog.e("QuotaTracker", "Received intent with null action");
                            return;
                        }
                        if (action.equals("android.intent.action.USER_REMOVED")) {
                            int intExtra =
                                    intent.getIntExtra("android.intent.extra.user_handle", 0);
                            synchronized (QuotaTracker.this.mLock) {
                                QuotaTracker quotaTracker = QuotaTracker.this;
                                quotaTracker.mInQuotaAlarmQueue.removeAlarmsForUserId(intExtra);
                                quotaTracker.mFreeQuota.delete(intExtra);
                                CountQuotaTracker countQuotaTracker =
                                        (CountQuotaTracker) quotaTracker;
                                countQuotaTracker.mEventTimes.mData.delete(intExtra);
                                countQuotaTracker.mExecutionStatsCache.mData.delete(intExtra);
                            }
                            return;
                        }
                        if (action.equals("android.intent.action.PACKAGE_FULLY_REMOVED")) {
                            int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                            synchronized (QuotaTracker.this.mLock) {
                                QuotaTracker quotaTracker2 = QuotaTracker.this;
                                int userId = UserHandle.getUserId(intExtra2);
                                Uri data = intent.getData();
                                quotaTracker2.onAppRemovedLocked(
                                        userId, data != null ? data.getSchemeSpecificPart() : null);
                            }
                        }
                    }
                };
        this.mCategorizer = categorizer;
        this.mInjector = injector;
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        this.mInQuotaAlarmQueue = new InQuotaAlarmQueue(context, FgThread.getHandler().getLooper());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme("package");
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(
                broadcastReceiver, userHandle, intentFilter, null, BackgroundThread.getHandler());
        context.registerReceiverAsUser(
                broadcastReceiver,
                userHandle,
                new IntentFilter("android.intent.action.USER_REMOVED"),
                null,
                BackgroundThread.getHandler());
    }

    public final void clear() {
        synchronized (this.mLock) {
            this.mInQuotaAlarmQueue.removeAllAlarms();
            this.mFreeQuota.clear();
            CountQuotaTracker countQuotaTracker = (CountQuotaTracker) this;
            countQuotaTracker.mExecutionStatsCache.mData.clear();
            countQuotaTracker.mEventTimes.mData.clear();
        }
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("QuotaTracker:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Is enabled: " + this.mIsEnabled);
                indentingPrintWriter.println("Is global quota free: false");
                StringBuilder sb = new StringBuilder("Current elapsed time: ");
                this.mInjector.getClass();
                sb.append(SystemClock.elapsedRealtime());
                indentingPrintWriter.println(sb.toString());
                indentingPrintWriter.println();
                indentingPrintWriter.println();
                this.mInQuotaAlarmQueue.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Per-app free quota:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mFreeQuota.numMaps(); i++) {
                    int keyAt = this.mFreeQuota.keyAt(i);
                    for (int i2 = 0; i2 < this.mFreeQuota.numElementsForKey(keyAt); i2++) {
                        String str = (String) this.mFreeQuota.keyAt(i, i2);
                        indentingPrintWriter.print(Uptc.string(keyAt, str, null));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mFreeQuota.get(keyAt, str));
                    }
                }
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final boolean isWithinQuota(int i, String str, String str2) {
        boolean z;
        synchronized (this.mLock) {
            CountQuotaTracker countQuotaTracker = (CountQuotaTracker) this;
            z = true;
            if (countQuotaTracker.mIsEnabled
                    && !((Boolean) countQuotaTracker.mFreeQuota.getOrDefault(i, str, Boolean.FALSE))
                            .booleanValue()) {
                CountQuotaTracker.ExecutionStats executionStatsLocked =
                        countQuotaTracker.getExecutionStatsLocked(i, str, str2);
                if (executionStatsLocked.countInWindow >= executionStatsLocked.countLimit) {
                    z = false;
                }
            }
        }
        return z;
    }

    public void maybeScheduleStartAlarmLocked(int i, String str, String str2) {
        if (this.mQuotaChangeListeners.size() == 0) {
            return;
        }
        Uptc.string(i, str, str2);
        boolean isWithinQuota = isWithinQuota(i, str, str2);
        InQuotaAlarmQueue inQuotaAlarmQueue = this.mInQuotaAlarmQueue;
        if (!isWithinQuota) {
            inQuotaAlarmQueue.addAlarm(
                    ((CountQuotaTracker) this)
                            .getExecutionStatsLocked(i, str, str2)
                            .inQuotaTimeElapsed,
                    new Uptc(i, str, str2));
            return;
        }
        inQuotaAlarmQueue.removeAlarmForKey(new Uptc(i, str, str2));
        CountQuotaTracker countQuotaTracker = (CountQuotaTracker) this;
        synchronized (countQuotaTracker.mLock) {
            countQuotaTracker.maybeUpdateStatusForUptcLocked(i, str, str2);
        }
    }

    public final void onAppRemovedLocked(int i, String str) {
        if (str == null) {
            Slog.wtf("QuotaTracker", "Told app removed but given null package name.");
            return;
        }
        InQuotaAlarmQueue inQuotaAlarmQueue = this.mInQuotaAlarmQueue;
        synchronized (inQuotaAlarmQueue.mLock) {
            try {
                AlarmQueue.AlarmPriorityQueue alarmPriorityQueue =
                        inQuotaAlarmQueue.mAlarmPriorityQueue;
                Pair[] pairArr =
                        (Pair[]) alarmPriorityQueue.toArray(new Pair[alarmPriorityQueue.size()]);
                boolean z = false;
                for (int length = pairArr.length - 1; length >= 0; length--) {
                    Uptc uptc = (Uptc) pairArr[length].first;
                    if (i == uptc.userId && str.equals(uptc.packageName)) {
                        inQuotaAlarmQueue.mAlarmPriorityQueue.remove(pairArr[length]);
                        z = true;
                    }
                }
                if (z) {
                    inQuotaAlarmQueue.setNextAlarmLocked(
                            inQuotaAlarmQueue.mLastFireTimeElapsed
                                    + inQuotaAlarmQueue.mMinTimeBetweenAlarmsMs);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mFreeQuota.delete(i, str);
        CountQuotaTracker countQuotaTracker = (CountQuotaTracker) this;
    }
}
