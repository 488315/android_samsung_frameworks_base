package com.android.server.am;

import android.content.Context;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.util.TimeUtils;

import com.android.internal.app.ProcessMap;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BaseAppStateTimeSlotEventsTracker extends BaseAppStateEventsTracker {
    public final H mHandler;
    public final ArrayMap mTmpPkgs;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class BaseAppStateTimeSlotEventsPolicy
            extends BaseAppStateEventsTracker.BaseAppStateEventsPolicy {
        public final int mDefaultNumOfEventsThreshold;
        public final ProcessMap mExcessiveEventPkgs;
        public final String mKeyNumOfEventsThreshold;
        public final Object mLock;
        public volatile int mNumOfEventsThreshold;
        public long mTimeSlotSize;

        public BaseAppStateTimeSlotEventsPolicy(
                BaseAppStateTracker.Injector injector,
                BaseAppStateTimeSlotEventsTracker baseAppStateTimeSlotEventsTracker,
                String str,
                String str2,
                String str3) {
            super(
                    injector,
                    baseAppStateTimeSlotEventsTracker,
                    str,
                    true,
                    str2,
                    BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
            this.mExcessiveEventPkgs = new ProcessMap();
            this.mTimeSlotSize = 900000L;
            this.mKeyNumOfEventsThreshold = str3;
            this.mDefaultNumOfEventsThreshold = 10000;
            this.mLock = baseAppStateTimeSlotEventsTracker.mLock;
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy,
                  // com.android.server.am.BaseAppStatePolicy
        public void dump(PrintWriter printWriter, String str) {
            super.dump(printWriter, str);
            if (this.mTrackerEnabled) {
                printWriter.print(str);
                printWriter.print(this.mKeyNumOfEventsThreshold);
                printWriter.print('=');
                printWriter.println(this.mNumOfEventsThreshold);
            }
            printWriter.print(str);
            printWriter.print("event_time_slot_size=");
            printWriter.println(this.mTimeSlotSize);
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0023 A[Catch: all -> 0x001e, DONT_GENERATE, TryCatch #0 {all -> 0x001e, blocks: (B:4:0x0003, B:6:0x000f, B:11:0x0023, B:15:0x0027, B:17:0x0029), top: B:3:0x0003 }] */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0025  */
        @Override // com.android.server.am.BaseAppStatePolicy
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int getProposedRestrictionLevel(int r3, int r4, java.lang.String r5) {
            /*
                r2 = this;
                java.lang.Object r0 = r2.mLock
                monitor-enter(r0)
                com.android.internal.app.ProcessMap r1 = r2.mExcessiveEventPkgs     // Catch: java.lang.Throwable -> L1e
                java.lang.Object r3 = r1.get(r5, r3)     // Catch: java.lang.Throwable -> L1e
                r5 = 30
                r1 = 40
                if (r3 == 0) goto L20
                com.android.server.am.BaseAppStateTracker r2 = r2.mTracker     // Catch: java.lang.Throwable -> L1e
                com.android.server.am.BaseAppStateTimeSlotEventsTracker r2 = (com.android.server.am.BaseAppStateTimeSlotEventsTracker) r2     // Catch: java.lang.Throwable -> L1e
                com.android.server.am.AppRestrictionController r2 = r2.mAppRestrictionController     // Catch: java.lang.Throwable -> L1e
                com.android.server.am.AppRestrictionController$ConstantsObserver r2 = r2.mConstantsObserver     // Catch: java.lang.Throwable -> L1e
                boolean r2 = r2.mBgAutoRestrictAbusiveApps     // Catch: java.lang.Throwable -> L1e
                if (r2 != 0) goto L1c
                goto L20
            L1c:
                r2 = r1
                goto L21
            L1e:
                r2 = move-exception
                goto L2c
            L20:
                r2 = r5
            L21:
                if (r4 <= r1) goto L25
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L1e
                return r2
            L25:
                if (r4 != r1) goto L29
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L1e
                return r5
            L29:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L1e
                r2 = 0
                return r2
            L2c:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L1e
                throw r2
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy.getProposedRestrictionLevel(int,"
                        + " int, java.lang.String):int");
        }

        public final void onExcessiveEvents(int i, String str, long j) {
            boolean z;
            synchronized (this.mLock) {
                try {
                    if (((Long) this.mExcessiveEventPkgs.get(str, i)) == null) {
                        this.mExcessiveEventPkgs.put(str, i, Long.valueOf(j));
                        z = true;
                    } else {
                        z = false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z) {
                ((BaseAppStateTimeSlotEventsTracker) this.mTracker)
                        .mAppRestrictionController.refreshAppRestrictionLevelForUid(
                                i,
                                FrameworkStatsLog
                                        .APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM,
                                2,
                                true);
            }
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public final void onMaxTrackingDurationChanged() {
            final BaseAppStateTimeSlotEventsTracker baseAppStateTimeSlotEventsTracker =
                    (BaseAppStateTimeSlotEventsTracker) this.mTracker;
            AppRestrictionController.BgHandler bgHandler =
                    baseAppStateTimeSlotEventsTracker.mBgHandler;
            Objects.requireNonNull(baseAppStateTimeSlotEventsTracker);
            bgHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.BaseAppStateTimeSlotEventsTracker$BaseAppStateTimeSlotEventsPolicy$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BaseAppStateTimeSlotEventsTracker baseAppStateTimeSlotEventsTracker2 =
                                    BaseAppStateTimeSlotEventsTracker.this;
                            baseAppStateTimeSlotEventsTracker2.getClass();
                            baseAppStateTimeSlotEventsTracker2.trim(
                                    Math.max(
                                            0L,
                                            SystemClock.elapsedRealtime()
                                                    - ((BaseAppStateTimeSlotEventsTracker
                                                                            .BaseAppStateTimeSlotEventsPolicy)
                                                                    baseAppStateTimeSlotEventsTracker2
                                                                            .mInjector
                                                                            .mAppStatePolicy)
                                                            .mMaxTrackingDuration));
                        }
                    });
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy,
                  // com.android.server.am.BaseAppStatePolicy
        public final void onPropertiesChanged(String str) {
            if (this.mKeyNumOfEventsThreshold.equals(str)) {
                updateNumOfEventsThreshold();
            } else {
                super.onPropertiesChanged(str);
            }
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy,
                  // com.android.server.am.BaseAppStatePolicy
        public final void onSystemReady() {
            super.onSystemReady();
            updateNumOfEventsThreshold();
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void onTrackerEnabled(boolean z) {
            BaseAppStateTimeSlotEventsTracker baseAppStateTimeSlotEventsTracker =
                    (BaseAppStateTimeSlotEventsTracker) this.mTracker;
            if (z) {
                baseAppStateTimeSlotEventsTracker.getClass();
                return;
            }
            synchronized (baseAppStateTimeSlotEventsTracker.mLock) {
                baseAppStateTimeSlotEventsTracker.mPkgEvents.clear();
            }
        }

        public void setTimeSlotSize(long j) {
            this.mTimeSlotSize = j;
        }

        public final void updateNumOfEventsThreshold() {
            int i =
                    DeviceConfig.getInt(
                            "activity_manager",
                            this.mKeyNumOfEventsThreshold,
                            this.mDefaultNumOfEventsThreshold);
            if (i != this.mNumOfEventsThreshold) {
                this.mNumOfEventsThreshold = i;
                BaseAppStateTimeSlotEventsTracker baseAppStateTimeSlotEventsTracker =
                        (BaseAppStateTimeSlotEventsTracker) this.mTracker;
                baseAppStateTimeSlotEventsTracker.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                synchronized (baseAppStateTimeSlotEventsTracker.mLock) {
                    try {
                        SparseArray sparseArray = baseAppStateTimeSlotEventsTracker.mPkgEvents.mMap;
                        for (int size = sparseArray.size() - 1; size >= 0; size--) {
                            ArrayMap arrayMap = (ArrayMap) sparseArray.valueAt(size);
                            for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                                SimpleAppStateTimeslotEvents simpleAppStateTimeslotEvents =
                                        (SimpleAppStateTimeslotEvents) arrayMap.valueAt(size2);
                                int totalEventsSince =
                                        simpleAppStateTimeslotEvents.getTotalEventsSince(
                                                simpleAppStateTimeslotEvents.getEarliest(0L),
                                                elapsedRealtime);
                                if (totalEventsSince >= i) {
                                    baseAppStateTimeSlotEventsTracker.mTmpPkgs.put(
                                            simpleAppStateTimeslotEvents,
                                            Integer.valueOf(totalEventsSince));
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                for (int size3 = baseAppStateTimeSlotEventsTracker.mTmpPkgs.size() - 1;
                        size3 >= 0;
                        size3--) {
                    SimpleAppStateTimeslotEvents simpleAppStateTimeslotEvents2 =
                            (SimpleAppStateTimeslotEvents)
                                    baseAppStateTimeSlotEventsTracker.mTmpPkgs.keyAt(size3);
                    BaseAppStateTimeSlotEventsPolicy baseAppStateTimeSlotEventsPolicy =
                            (BaseAppStateTimeSlotEventsPolicy)
                                    baseAppStateTimeSlotEventsTracker.mInjector.mAppStatePolicy;
                    String str = simpleAppStateTimeslotEvents2.mPackageName;
                    int i2 = simpleAppStateTimeslotEvents2.mUid;
                    ((Integer) baseAppStateTimeSlotEventsTracker.mTmpPkgs.valueAt(size3))
                            .getClass();
                    baseAppStateTimeSlotEventsPolicy.onExcessiveEvents(i2, str, elapsedRealtime);
                }
                baseAppStateTimeSlotEventsTracker.mTmpPkgs.clear();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public final BaseAppStateTimeSlotEventsTracker mTracker;

        public H(BaseAppStateTimeSlotEventsTracker baseAppStateTimeSlotEventsTracker) {
            super(baseAppStateTimeSlotEventsTracker.mBgHandler.getLooper());
            this.mTracker = baseAppStateTimeSlotEventsTracker;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean contains;
            int backgroundRestrictionExemptionReason;
            boolean z;
            if (message.what != 0) {
                return;
            }
            BaseAppStateTimeSlotEventsTracker baseAppStateTimeSlotEventsTracker = this.mTracker;
            String str = (String) message.obj;
            int i = message.arg1;
            BaseAppStateTimeSlotEventsPolicy baseAppStateTimeSlotEventsPolicy =
                    (BaseAppStateTimeSlotEventsPolicy)
                            baseAppStateTimeSlotEventsTracker.mInjector.mAppStatePolicy;
            BaseAppStateTimeSlotEventsTracker baseAppStateTimeSlotEventsTracker2 =
                    (BaseAppStateTimeSlotEventsTracker) baseAppStateTimeSlotEventsPolicy.mTracker;
            synchronized (baseAppStateTimeSlotEventsTracker2.mLock) {
                contains = baseAppStateTimeSlotEventsTracker2.mTopUids.contains(Integer.valueOf(i));
            }
            if (contains) {
                backgroundRestrictionExemptionReason = 12;
            } else if (((BaseAppStateTimeSlotEventsTracker)
                            baseAppStateTimeSlotEventsPolicy.mTracker)
                    .mAppRestrictionController.hasForegroundServices(i, str)) {
                backgroundRestrictionExemptionReason = 14;
            } else {
                backgroundRestrictionExemptionReason =
                        baseAppStateTimeSlotEventsPolicy.mTracker.mAppRestrictionController
                                .getBackgroundRestrictionExemptionReason(i);
                if (backgroundRestrictionExemptionReason == -1) {
                    backgroundRestrictionExemptionReason = -1;
                }
            }
            if (backgroundRestrictionExemptionReason != -1) {
                return;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            synchronized (baseAppStateTimeSlotEventsTracker.mLock) {
                try {
                    SimpleAppStateTimeslotEvents simpleAppStateTimeslotEvents =
                            (SimpleAppStateTimeslotEvents)
                                    baseAppStateTimeSlotEventsTracker.mPkgEvents.get(i, str);
                    if (simpleAppStateTimeslotEvents == null) {
                        simpleAppStateTimeslotEvents =
                                (SimpleAppStateTimeslotEvents)
                                        baseAppStateTimeSlotEventsTracker.createAppStateEvents(
                                                i, str);
                        baseAppStateTimeSlotEventsTracker.mPkgEvents.put(
                                str, i, simpleAppStateTimeslotEvents);
                    }
                    simpleAppStateTimeslotEvents.addEvent(elapsedRealtime);
                    z =
                            simpleAppStateTimeslotEvents.getTotalEventsSince(
                                            simpleAppStateTimeslotEvents.getEarliest(0L),
                                            elapsedRealtime)
                                    >= ((BaseAppStateTimeSlotEventsPolicy)
                                                    baseAppStateTimeSlotEventsTracker
                                                            .mInjector
                                                            .mAppStatePolicy)
                                            .mNumOfEventsThreshold;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z) {
                ((BaseAppStateTimeSlotEventsPolicy)
                                baseAppStateTimeSlotEventsTracker.mInjector.mAppStatePolicy)
                        .onExcessiveEvents(i, str, elapsedRealtime);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SimpleAppStateTimeslotEvents extends BaseAppStateTimeSlotEvents {
        @Override // com.android.server.am.BaseAppStateEvents
        public final String formatEventSummary(int i, long j) {
            LinkedList linkedList = this.mEvents[0];
            if (linkedList == null || linkedList.size() == 0) {
                return "(none)";
            }
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            getTotalEventsSince(getEarliest(0L), j), "total=", ", latest=");
            long[] jArr = this.mCurSlotStartTime;
            m.append(getTotalEventsSince(jArr[0], j));
            m.append("(slot=");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                    m, TimeUtils.formatTime(jArr[0], j), ")");
        }

        @Override // com.android.server.am.BaseAppStateEvents
        public final String formatEventTypeLabel(int i) {
            return "";
        }
    }

    public BaseAppStateTimeSlotEventsTracker(
            Context context, AppRestrictionController appRestrictionController) {
        super(context, appRestrictionController);
        this.mTmpPkgs = new ArrayMap();
        this.mHandler = new H(this);
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onUserInteractionStarted(String str, int i) {
        BaseAppStateTimeSlotEventsPolicy baseAppStateTimeSlotEventsPolicy =
                (BaseAppStateTimeSlotEventsPolicy) this.mInjector.mAppStatePolicy;
        synchronized (baseAppStateTimeSlotEventsPolicy.mLock) {
            baseAppStateTimeSlotEventsPolicy.mExcessiveEventPkgs.remove(str, i);
        }
        ((BaseAppStateTimeSlotEventsTracker) baseAppStateTimeSlotEventsPolicy.mTracker)
                .mAppRestrictionController.refreshAppRestrictionLevelForUid(
                        i,
                        FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE,
                        3,
                        true);
    }
}
