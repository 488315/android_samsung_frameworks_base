package com.android.server.job.controllers;

import android.app.job.JobInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.telephony.TelephonyManager;
import android.telephony.UiccSlotMapping;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.KeyValueListParser;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseArrayMap;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.SparseSetArray;
import android.util.TimeUtils;

import com.android.server.AppSchedulingModuleThread;
import com.android.server.DeviceIdleInternal;
import com.android.server.LocalServices;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;
import com.android.server.job.JobSchedulerService$Constants$$ExternalSyntheticOutline0;
import com.android.server.job.JobStore;
import com.android.server.utils.AlarmQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FlexibilityController extends StateController {
    public static final boolean DEBUG;
    static final int FLEXIBLE_CONSTRAINTS = 268435463;
    public int mAppliedConstraints;
    public long mDeadlineProximityLimitMs;
    public SparseLongArray mFallbackFlexibilityAdditionalScoreTimeFactors;
    public long mFallbackFlexibilityDeadlineMs;
    public SparseIntArray mFallbackFlexibilityDeadlineScores;
    public SparseLongArray mFallbackFlexibilityDeadlines;
    final FcConfig mFcConfig;
    final FlexibilityAlarmQueue mFlexibilityAlarmQueue;
    public boolean mFlexibilityEnabled;
    final FlexibilityTracker mFlexibilityTracker;
    public final FcHandler mHandler;
    public final SparseArrayMap mJobScoreTrackers;
    public final ArraySet mJobsToCheck;
    public final SparseLongArray mLastSeenConstraintTimesElapsed;
    public boolean mLocalOverride;
    public long mMaxRescheduledDeadline;
    public long mMinTimeBetweenFlexibilityAlarmsMs;
    public final ArraySet mPackagesToCheck;
    public SparseArray mPercentsToDropConstraints;
    final PrefetchController.PrefetchChangedListener mPrefetchChangedListener;
    final PrefetchController mPrefetchController;
    final SparseArrayMap mPrefetchLifeCycleStart;
    public long mRescheduledJobDeadline;
    int mSatisfiedFlexibleConstraints;
    public final SpecialAppTracker mSpecialAppTracker;
    public final int mSupportedFlexConstraints;
    public long mUnseenConstraintGracePeriodMs;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.job.controllers.FlexibilityController$1, reason: invalid class name */
    public final class AnonymousClass1 implements PrefetchController.PrefetchChangedListener {
        public AnonymousClass1() {}

        public final void onPrefetchCacheUpdated(
                ArraySet arraySet, int i, String str, long j, long j2, long j3) {
            synchronized (FlexibilityController.this.mLock) {
                try {
                    long launchTimeThresholdMs =
                            FlexibilityController.this.mPrefetchController
                                    .getLaunchTimeThresholdMs();
                    boolean z = true;
                    boolean z2 = j - launchTimeThresholdMs < j3;
                    if (j2 - launchTimeThresholdMs >= j3) {
                        z = false;
                    }
                    if (z != z2) {
                        SparseArrayMap sparseArrayMap =
                                FlexibilityController.this.mPrefetchLifeCycleStart;
                        sparseArrayMap.add(
                                i,
                                str,
                                Long.valueOf(
                                        Math.max(
                                                j3,
                                                ((Long) sparseArrayMap.getOrDefault(i, str, 0L))
                                                        .longValue())));
                    }
                    for (int i2 = 0; i2 < arraySet.size(); i2++) {
                        JobStatus jobStatus = (JobStatus) arraySet.valueAt(i2);
                        if (jobStatus.hasFlexibilityConstraint()) {
                            FlexibilityController.this.mFlexibilityTracker
                                    .calculateNumDroppedConstraints(jobStatus, j3);
                            FlexibilityController.this.mFlexibilityAlarmQueue
                                    .scheduleDropNumConstraintsAlarm(jobStatus, j3);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FcConfig {
        static final long DEFAULT_DEADLINE_PROXIMITY_LIMIT_MS = 900000;
        public static final SparseLongArray DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES;
        public static final SparseLongArray
                DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS;
        static final long DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_MS = 86400000;
        public static final SparseIntArray DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES;
        static final SparseArray DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS;
        static final long DEFAULT_UNSEEN_CONSTRAINT_GRACE_PERIOD_MS = 259200000;
        static final String KEY_APPLIED_CONSTRAINTS = "fc_applied_constraints";
        public boolean mShouldReevaluateConstraints = false;
        public int APPLIED_CONSTRAINTS = 0;
        public long DEADLINE_PROXIMITY_LIMIT_MS = DEFAULT_DEADLINE_PROXIMITY_LIMIT_MS;
        public long FALLBACK_FLEXIBILITY_DEADLINE_MS = 86400000;
        public long MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS = 60000;
        public final SparseArray PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS = new SparseArray();
        public long RESCHEDULED_JOB_DEADLINE_MS = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        public long MAX_RESCHEDULED_DEADLINE_MS = 86400000;
        public long UNSEEN_CONSTRAINT_GRACE_PERIOD_MS = DEFAULT_UNSEEN_CONSTRAINT_GRACE_PERIOD_MS;
        public final SparseLongArray FALLBACK_FLEXIBILITY_DEADLINES = new SparseLongArray();
        public final SparseIntArray FALLBACK_FLEXIBILITY_DEADLINE_SCORES = new SparseIntArray();
        public final SparseLongArray FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS =
                new SparseLongArray();

        static {
            SparseLongArray sparseLongArray = new SparseLongArray();
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES = sparseLongArray;
            SparseIntArray sparseIntArray = new SparseIntArray();
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES = sparseIntArray;
            SparseLongArray sparseLongArray2 = new SparseLongArray();
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS = sparseLongArray2;
            SparseArray sparseArray = new SparseArray();
            DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS = sparseArray;
            sparseLongArray.put(500, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
            sparseLongArray.put(400, 21600000L);
            sparseLongArray.put(300, 43200000L);
            sparseLongArray.put(200, 86400000L);
            sparseLongArray.put(100, 172800000L);
            sparseIntArray.put(500, 5);
            sparseIntArray.put(400, 4);
            sparseIntArray.put(300, 3);
            sparseIntArray.put(200, 2);
            sparseIntArray.put(100, 1);
            sparseLongArray2.put(500, 0L);
            sparseLongArray2.put(400, 180000L);
            sparseLongArray2.put(300, 120000L);
            sparseLongArray2.put(200, 60000L);
            sparseLongArray2.put(100, 60000L);
            sparseArray.put(500, new int[] {1, 2, 3, 4});
            sparseArray.put(400, new int[] {33, 50, 60, 75});
            sparseArray.put(300, new int[] {50, 60, 70, 80});
            sparseArray.put(200, new int[] {50, 60, 70, 80});
            sparseArray.put(100, new int[] {55, 65, 75, 85});
        }

        public FcConfig() {
            int i = 0;
            int i2 = 0;
            while (true) {
                SparseLongArray sparseLongArray = DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES;
                if (i2 >= sparseLongArray.size()) {
                    break;
                }
                this.FALLBACK_FLEXIBILITY_DEADLINES.put(
                        sparseLongArray.keyAt(i2), sparseLongArray.valueAt(i2));
                i2++;
            }
            int i3 = 0;
            while (true) {
                SparseIntArray sparseIntArray = DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES;
                if (i3 >= sparseIntArray.size()) {
                    break;
                }
                this.FALLBACK_FLEXIBILITY_DEADLINE_SCORES.put(
                        sparseIntArray.keyAt(i3), sparseIntArray.valueAt(i3));
                i3++;
            }
            int i4 = 0;
            while (true) {
                SparseLongArray sparseLongArray2 =
                        DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS;
                if (i4 >= sparseLongArray2.size()) {
                    break;
                }
                this.FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS.put(
                        sparseLongArray2.keyAt(i4), sparseLongArray2.valueAt(i4));
                i4++;
            }
            while (true) {
                SparseArray sparseArray = DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS;
                if (i >= sparseArray.size()) {
                    return;
                }
                this.PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS.put(
                        sparseArray.keyAt(i), (int[]) sparseArray.valueAt(i));
                i++;
            }
        }

        public static int[] parsePercentToDropString(String str) {
            if (str == null || str.isEmpty()) {
                return null;
            }
            String[] split = str.split("\\|");
            int bitCount = Integer.bitCount(FlexibilityController.FLEXIBLE_CONSTRAINTS);
            int[] iArr = new int[bitCount];
            if (bitCount != split.length) {
                return null;
            }
            int i = 0;
            int i2 = 0;
            while (i < split.length) {
                try {
                    int parseInt = Integer.parseInt(split[i]);
                    iArr[i] = parseInt;
                    if (parseInt < i2) {
                        Slog.wtf(
                                "JobScheduler.Flex",
                                "Percents to drop constraints were not in increasing order.");
                        return null;
                    }
                    if (parseInt > 100) {
                        Slog.e("JobScheduler.Flex", "Found % over 100");
                        return null;
                    }
                    i++;
                    i2 = parseInt;
                } catch (NumberFormatException e) {
                    Slog.e("JobScheduler.Flex", "Provided string was improperly formatted.", e);
                    return null;
                }
            }
            return iArr;
        }

        public static boolean parsePriorityToLongKeyValueString(
                String str, SparseLongArray sparseLongArray, SparseLongArray sparseLongArray2) {
            KeyValueListParser keyValueListParser = new KeyValueListParser(',');
            try {
                keyValueListParser.setString(str);
            } catch (IllegalArgumentException e) {
                Slog.wtf("JobScheduler.Flex", "Bad string given", e);
                keyValueListParser.setString((String) null);
            }
            long j = sparseLongArray.get(500);
            long j2 = sparseLongArray.get(400);
            long j3 = sparseLongArray.get(300);
            long j4 = sparseLongArray.get(200);
            long j5 = sparseLongArray.get(100);
            long j6 = keyValueListParser.getLong(String.valueOf(500), sparseLongArray2.get(500));
            long j7 = keyValueListParser.getLong(String.valueOf(400), sparseLongArray2.get(400));
            long j8 = keyValueListParser.getLong(String.valueOf(300), sparseLongArray2.get(300));
            long j9 = keyValueListParser.getLong(String.valueOf(200), sparseLongArray2.get(200));
            long j10 = keyValueListParser.getLong(String.valueOf(100), sparseLongArray2.get(100));
            sparseLongArray.put(500, j6);
            sparseLongArray.put(400, j7);
            sparseLongArray.put(300, j8);
            sparseLongArray.put(200, j9);
            sparseLongArray.put(100, j10);
            return (j == j6 && j2 == j7 && j3 == j8 && j4 == j9 && j5 == j10) ? false : true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FcHandler extends Handler {
        public FcHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            ArraySet arraySet;
            int i = message.what;
            if (i == 0) {
                removeMessages(0);
                synchronized (FlexibilityController.this.mLock) {
                    try {
                        FlexibilityController.this.mJobsToCheck.clear();
                        FlexibilityController.this.mPackagesToCheck.clear();
                        JobSchedulerService.sElapsedRealtimeClock.getClass();
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        ArraySet arraySet2 = new ArraySet();
                        int bitCount =
                                Integer.bitCount(
                                        FlexibilityController.this.mAppliedConstraints & 7);
                        for (int i2 = 0; i2 <= bitCount; i2++) {
                            FlexibilityTracker flexibilityTracker =
                                    FlexibilityController.this.mFlexibilityTracker;
                            if (i2 > flexibilityTracker.mTrackedJobs.size()) {
                                Slog.wtfStack(
                                        "JobScheduler.Flex",
                                        "Asked for a larger number of constraints than exists.");
                                arraySet = null;
                            } else {
                                arraySet = (ArraySet) flexibilityTracker.mTrackedJobs.get(i2);
                            }
                            if (arraySet != null) {
                                for (int i3 = 0; i3 < arraySet.size(); i3++) {
                                    JobStatus jobStatus = (JobStatus) arraySet.valueAt(i3);
                                    if (jobStatus.setConstraintSatisfied(
                                            2097152,
                                            elapsedRealtime,
                                            FlexibilityController.this.isFlexibilitySatisfiedLocked(
                                                    jobStatus))) {
                                        arraySet2.add(jobStatus);
                                    }
                                }
                            }
                        }
                        if (arraySet2.size() > 0) {
                            FlexibilityController.this.mStateChangedListener
                                    .onControllerStateChanged(arraySet2);
                        }
                    } finally {
                    }
                }
                return;
            }
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                synchronized (FlexibilityController.this.mLock) {
                    try {
                        JobSchedulerService.sElapsedRealtimeClock.getClass();
                        final long elapsedRealtime2 = SystemClock.elapsedRealtime();
                        final ArraySet arraySet3 = new ArraySet();
                        FlexibilityController.this.mService.mJobs.forEachJob(
                                new Predicate() { // from class:
                                                  // com.android.server.job.controllers.FlexibilityController$FcHandler$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        JobStatus jobStatus2 = (JobStatus) obj;
                                        FlexibilityController flexibilityController =
                                                FlexibilityController.this;
                                        return flexibilityController.mPackagesToCheck.contains(
                                                        jobStatus2.sourcePackageName)
                                                || flexibilityController.mPackagesToCheck.contains(
                                                        jobStatus2
                                                                .job
                                                                .getService()
                                                                .getPackageName());
                                    }
                                },
                                new Consumer() { // from class:
                                                 // com.android.server.job.controllers.FlexibilityController$FcHandler$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        FlexibilityController.FcHandler fcHandler =
                                                FlexibilityController.FcHandler.this;
                                        long j = elapsedRealtime2;
                                        ArraySet arraySet4 = arraySet3;
                                        JobStatus jobStatus2 = (JobStatus) obj;
                                        fcHandler.getClass();
                                        if (FlexibilityController.DEBUG) {
                                            Slog.d(
                                                    "JobScheduler.Flex",
                                                    "Checking on " + jobStatus2.toShortString());
                                        }
                                        if (jobStatus2.setConstraintSatisfied(
                                                2097152,
                                                j,
                                                FlexibilityController.this
                                                        .isFlexibilitySatisfiedLocked(
                                                                jobStatus2))) {
                                            arraySet4.add(jobStatus2);
                                        }
                                    }
                                });
                        FlexibilityController.this.mPackagesToCheck.clear();
                        if (arraySet3.size() > 0) {
                            FlexibilityController.this.mStateChangedListener
                                    .onControllerStateChanged(arraySet3);
                        }
                    } finally {
                    }
                }
                return;
            }
            synchronized (FlexibilityController.this.mLock) {
                try {
                    JobSchedulerService.sElapsedRealtimeClock.getClass();
                    long elapsedRealtime3 = SystemClock.elapsedRealtime();
                    ArraySet arraySet4 = new ArraySet();
                    for (int size = FlexibilityController.this.mJobsToCheck.size() - 1;
                            size >= 0;
                            size--) {
                        JobStatus jobStatus2 =
                                (JobStatus) FlexibilityController.this.mJobsToCheck.valueAt(size);
                        if (FlexibilityController.DEBUG) {
                            Slog.d(
                                    "JobScheduler.Flex",
                                    "Checking on " + jobStatus2.toShortString());
                        }
                        if (jobStatus2.setConstraintSatisfied(
                                2097152,
                                elapsedRealtime3,
                                FlexibilityController.this.isFlexibilitySatisfiedLocked(
                                        jobStatus2))) {
                            arraySet4.add(jobStatus2);
                        }
                    }
                    FlexibilityController.this.mJobsToCheck.clear();
                    if (arraySet4.size() > 0) {
                        FlexibilityController.this.mStateChangedListener.onControllerStateChanged(
                                arraySet4);
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class FlexibilityAlarmQueue extends AlarmQueue {
        public FlexibilityAlarmQueue(Context context, Looper looper) {
            super(
                    context,
                    looper,
                    "*job.flexibility_check*",
                    "Flexible Constraint Check",
                    true,
                    FlexibilityController.this.mMinTimeBetweenFlexibilityAlarmsMs);
        }

        @Override // com.android.server.utils.AlarmQueue
        public final boolean isForUser(int i, Object obj) {
            return ((JobStatus) obj).sourceUserId == i;
        }

        @Override // com.android.server.utils.AlarmQueue
        public final void processExpiredAlarms(ArraySet arraySet) {
            synchronized (FlexibilityController.this.mLock) {
                try {
                    ArraySet arraySet2 = new ArraySet();
                    JobSchedulerService.sElapsedRealtimeClock.getClass();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    for (int i = 0; i < arraySet.size(); i++) {
                        JobStatus jobStatus = (JobStatus) arraySet.valueAt(i);
                        if (FlexibilityController.DEBUG) {
                            Slog.d(
                                    "JobScheduler.Flex",
                                    "Alarm fired for " + jobStatus.toShortString());
                        }
                        FlexibilityController.this.mFlexibilityTracker
                                .calculateNumDroppedConstraints(jobStatus, elapsedRealtime);
                        if (jobStatus.getNumRequiredFlexibleConstraints() > 0) {
                            scheduleDropNumConstraintsAlarm(jobStatus, elapsedRealtime);
                        }
                        if (jobStatus.setConstraintSatisfied(
                                2097152,
                                elapsedRealtime,
                                FlexibilityController.this.isFlexibilitySatisfiedLocked(
                                        jobStatus))) {
                            arraySet2.add(jobStatus);
                        }
                    }
                    FlexibilityController.this.mStateChangedListener.onControllerStateChanged(
                            arraySet2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void scheduleDropNumConstraintsAlarm(JobStatus jobStatus, long j) {
            Object obj;
            long j2;
            Object obj2 = FlexibilityController.this.mLock;
            synchronized (obj2) {
                try {
                    try {
                        long lifeCycleBeginningElapsedLocked =
                                FlexibilityController.this.getLifeCycleBeginningElapsedLocked(
                                        jobStatus);
                        long lifeCycleEndElapsedLocked =
                                FlexibilityController.this.getLifeCycleEndElapsedLocked(
                                        jobStatus, j, lifeCycleBeginningElapsedLocked);
                        if (lifeCycleEndElapsedLocked <= lifeCycleBeginningElapsedLocked) {
                            Slog.wtf(
                                    "JobScheduler.Flex",
                                    "Got invalid latest when scheduling alarm. prefetch="
                                            + jobStatus.job.isPrefetch()
                                            + " periodic="
                                            + jobStatus.job.isPeriodic());
                            FlexibilityTracker flexibilityTracker =
                                    FlexibilityController.this.mFlexibilityTracker;
                            int i = jobStatus.mNumAppliedFlexibleConstraints;
                            flexibilityTracker.getClass();
                            if (i != jobStatus.mNumDroppedFlexibleConstraints) {
                                ((ArraySet)
                                                flexibilityTracker.mTrackedJobs.get(
                                                        jobStatus
                                                                .getNumRequiredFlexibleConstraints()))
                                        .remove(jobStatus);
                                jobStatus.mNumDroppedFlexibleConstraints =
                                        Math.max(
                                                0,
                                                Math.min(
                                                        jobStatus.mNumAppliedFlexibleConstraints,
                                                        i));
                                flexibilityTracker.add(jobStatus);
                            }
                            FlexibilityController.this.mJobsToCheck.add(jobStatus);
                            FlexibilityController.this.mHandler.sendEmptyMessage(1);
                            return;
                        }
                        long nextConstraintDropTimeElapsedLocked =
                                FlexibilityController.this.getNextConstraintDropTimeElapsedLocked(
                                        jobStatus,
                                        lifeCycleBeginningElapsedLocked,
                                        lifeCycleEndElapsedLocked);
                        boolean z = FlexibilityController.DEBUG;
                        if (z) {
                            StringBuilder sb =
                                    new StringBuilder("scheduleDropNumConstraintsAlarm: ");
                            sb.append(jobStatus.toShortString());
                            sb.append(" numApplied: ");
                            sb.append(jobStatus.mNumAppliedFlexibleConstraints);
                            sb.append(" numRequired: ");
                            sb.append(jobStatus.getNumRequiredFlexibleConstraints());
                            sb.append(" numSatisfied: ");
                            FlexibilityController flexibilityController =
                                    FlexibilityController.this;
                            sb.append(
                                    Integer.bitCount(
                                            flexibilityController
                                                            .getRelevantAppliedConstraintsLocked(
                                                                    jobStatus)
                                                    & flexibilityController
                                                            .mSatisfiedFlexibleConstraints));
                            sb.append(" curTime: ");
                            sb.append(j);
                            sb.append(" earliest: ");
                            obj = obj2;
                            sb.append(lifeCycleBeginningElapsedLocked);
                            sb.append(" latest: ");
                            j2 = lifeCycleEndElapsedLocked;
                            sb.append(j2);
                            sb.append(" nextTime: ");
                            sb.append(nextConstraintDropTimeElapsedLocked);
                            Slog.d("JobScheduler.Flex", sb.toString());
                        } else {
                            obj = obj2;
                            j2 = lifeCycleEndElapsedLocked;
                        }
                        long j3 = j2 - j;
                        long j4 = FlexibilityController.this.mDeadlineProximityLimitMs;
                        if (j3 < j4) {
                            if (z) {
                                Slog.d("JobScheduler.Flex", "deadline proximity met: " + jobStatus);
                            }
                            FlexibilityTracker flexibilityTracker2 =
                                    FlexibilityController.this.mFlexibilityTracker;
                            int i2 = jobStatus.mNumAppliedFlexibleConstraints;
                            flexibilityTracker2.getClass();
                            if (i2 != jobStatus.mNumDroppedFlexibleConstraints) {
                                ((ArraySet)
                                                flexibilityTracker2.mTrackedJobs.get(
                                                        jobStatus
                                                                .getNumRequiredFlexibleConstraints()))
                                        .remove(jobStatus);
                                jobStatus.mNumDroppedFlexibleConstraints =
                                        Math.max(
                                                0,
                                                Math.min(
                                                        jobStatus.mNumAppliedFlexibleConstraints,
                                                        i2));
                                flexibilityTracker2.add(jobStatus);
                            }
                            FlexibilityController.this.mJobsToCheck.add(jobStatus);
                            FlexibilityController.this.mHandler.sendEmptyMessage(1);
                            return;
                        }
                        if (nextConstraintDropTimeElapsedLocked == Long.MAX_VALUE) {
                            removeAlarmForKey(jobStatus);
                        } else {
                            if (j2 - nextConstraintDropTimeElapsedLocked > j4) {
                                addAlarm(nextConstraintDropTimeElapsedLocked, jobStatus);
                                return;
                            }
                            if (z) {
                                Slog.d("JobScheduler.Flex", "last alarm set: " + jobStatus);
                            }
                            addAlarm(
                                    j2 - FlexibilityController.this.mDeadlineProximityLimitMs,
                                    jobStatus);
                        }
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class FlexibilityTracker {
        public final ArrayList mTrackedJobs = new ArrayList();

        public FlexibilityTracker(int i) {
            for (int i2 = 0; i2 <= i; i2++) {
                this.mTrackedJobs.add(new ArraySet());
            }
        }

        public final void add(JobStatus jobStatus) {
            if (jobStatus.getNumRequiredFlexibleConstraints() < 0) {
                return;
            }
            ((ArraySet) this.mTrackedJobs.get(jobStatus.getNumRequiredFlexibleConstraints()))
                    .add(jobStatus);
        }

        public final void calculateNumDroppedConstraints(JobStatus jobStatus, long j) {
            FlexibilityController flexibilityController = FlexibilityController.this;
            int curPercentOfLifecycleLocked =
                    flexibilityController.getCurPercentOfLifecycleLocked(jobStatus, j);
            int i = jobStatus.mNumAppliedFlexibleConstraints;
            int[] percentsToDropConstraints =
                    flexibilityController.getPercentsToDropConstraints(
                            jobStatus.getEffectivePriority());
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                if (curPercentOfLifecycleLocked >= percentsToDropConstraints[i3]) {
                    i2++;
                }
            }
            if (i2 != jobStatus.mNumDroppedFlexibleConstraints) {
                ((ArraySet) this.mTrackedJobs.get(jobStatus.getNumRequiredFlexibleConstraints()))
                        .remove(jobStatus);
                jobStatus.mNumDroppedFlexibleConstraints =
                        Math.max(0, Math.min(jobStatus.mNumAppliedFlexibleConstraints, i2));
                add(jobStatus);
            }
        }

        public final void updateFlexibleConstraints(JobStatus jobStatus, long j) {
            int numRequiredFlexibleConstraints = jobStatus.getNumRequiredFlexibleConstraints();
            FlexibilityController flexibilityController = FlexibilityController.this;
            int bitCount =
                    Integer.bitCount(
                            flexibilityController.getRelevantAppliedConstraintsLocked(jobStatus));
            jobStatus.mNumAppliedFlexibleConstraints = bitCount;
            int[] percentsToDropConstraints =
                    flexibilityController.getPercentsToDropConstraints(
                            jobStatus.getEffectivePriority());
            int curPercentOfLifecycleLocked =
                    flexibilityController.getCurPercentOfLifecycleLocked(jobStatus, j);
            int i = 0;
            for (int i2 = 0; i2 < bitCount; i2++) {
                if (curPercentOfLifecycleLocked >= percentsToDropConstraints[i2]) {
                    i++;
                }
            }
            jobStatus.mNumDroppedFlexibleConstraints =
                    Math.max(0, Math.min(jobStatus.mNumAppliedFlexibleConstraints, i));
            if (numRequiredFlexibleConstraints == jobStatus.getNumRequiredFlexibleConstraints()) {
                return;
            }
            ((ArraySet) this.mTrackedJobs.get(numRequiredFlexibleConstraints)).remove(jobStatus);
            add(jobStatus);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class JobScoreTracker {
        public int mCachedScore;
        public long mCachedScoreExpirationTimeElapsed;
        public final JobScoreBucket[] mScoreBuckets = new JobScoreBucket[24];
        public int mScoreBucketIndex = 0;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class JobScoreBucket {
            public int score;
            public long startTimeElapsed;
        }

        public final void addScore(int i, long j) {
            int i2 = this.mScoreBucketIndex;
            JobScoreBucket[] jobScoreBucketArr = this.mScoreBuckets;
            JobScoreBucket jobScoreBucket = jobScoreBucketArr[i2];
            if (jobScoreBucket == null) {
                jobScoreBucket = new JobScoreBucket();
                jobScoreBucket.startTimeElapsed = j;
                jobScoreBucketArr[i2] = jobScoreBucket;
                this.mCachedScoreExpirationTimeElapsed =
                        Math.min(
                                this.mCachedScoreExpirationTimeElapsed,
                                j
                                        + BackupManagerConstants
                                                .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
            } else {
                long j2 = jobScoreBucket.startTimeElapsed;
                if (j2 < j - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) {
                    jobScoreBucket.score = 0;
                    jobScoreBucket.startTimeElapsed = j;
                    this.mCachedScoreExpirationTimeElapsed = j;
                } else if (j2 < j - ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                    this.mScoreBucketIndex = (i2 + 1) % 24;
                    addScore(i, j);
                    return;
                }
            }
            jobScoreBucket.score += i;
            this.mCachedScore += i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SpecialAppTracker {
        public DeviceIdleInternal mDeviceIdleInternal;
        public final boolean mHasFeatureTelephonySubscription;
        public TelephonyManager mTelephonyManager;
        public final Object mSatLock = new Object();
        public final SparseSetArray mSpecialApps = new SparseSetArray();
        public final SparseSetArray mCarrierPrivilegedApps = new SparseSetArray();
        public final SparseArray mCarrierPrivilegedCallbacks = new SparseArray();
        public final ArraySet mPowerAllowlistedApps = new ArraySet();
        public final AnonymousClass1 mBroadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.job.controllers.FlexibilityController.SpecialAppTracker.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        action.getClass();
                        if (action.equals("android.os.action.POWER_SAVE_WHITELIST_CHANGED")) {
                            SpecialAppTracker specialAppTracker = SpecialAppTracker.this;
                            FlexibilityController.this.mHandler.post(
                                    new FlexibilityController$$ExternalSyntheticLambda1(
                                            2, specialAppTracker));
                        } else if (action.equals(
                                "android.telephony.action.MULTI_SIM_CONFIG_CHANGED")) {
                            SpecialAppTracker.this.updateCarrierPrivilegedCallbackRegistration();
                        }
                    }
                };

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class LogicalIndexCarrierPrivilegesCallback
                implements TelephonyManager.CarrierPrivilegesCallback {
            public final int logicalIndex;

            public LogicalIndexCarrierPrivilegesCallback(int i) {
                this.logicalIndex = i;
            }

            public final void onCarrierPrivilegesChanged(Set set, Set set2) {
                ArraySet arraySet = new ArraySet();
                synchronized (SpecialAppTracker.this.mSatLock) {
                    try {
                        ArraySet arraySet2 =
                                SpecialAppTracker.this.mCarrierPrivilegedApps.get(
                                        this.logicalIndex);
                        if (arraySet2 != null) {
                            arraySet.addAll(arraySet2);
                            SpecialAppTracker.this.mCarrierPrivilegedApps.remove(this.logicalIndex);
                        }
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            String str = (String) it.next();
                            SpecialAppTracker.this.mCarrierPrivilegedApps.add(
                                    this.logicalIndex, str);
                            if (!arraySet.remove(str)) {
                                arraySet.add(str);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                SpecialAppTracker.this.updateSpecialAppSetUnlocked(arraySet);
            }
        }

        /* renamed from: -$$Nest$mstartTracking, reason: not valid java name */
        public static void m625$$Nest$mstartTracking(SpecialAppTracker specialAppTracker) {
            specialAppTracker.getClass();
            IntentFilter intentFilter =
                    new IntentFilter("android.os.action.POWER_SAVE_WHITELIST_CHANGED");
            if (specialAppTracker.mHasFeatureTelephonySubscription) {
                intentFilter.addAction("android.telephony.action.MULTI_SIM_CONFIG_CHANGED");
                specialAppTracker.updateCarrierPrivilegedCallbackRegistration();
            }
            FlexibilityController.this.mContext.registerReceiver(
                    specialAppTracker.mBroadcastReceiver, intentFilter);
            specialAppTracker.updatePowerAllowlistCache();
        }

        /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.job.controllers.FlexibilityController$SpecialAppTracker$1] */
        public SpecialAppTracker() {
            this.mHasFeatureTelephonySubscription =
                    FlexibilityController.this
                            .mContext
                            .getPackageManager()
                            .hasSystemFeature("android.hardware.telephony.subscription");
        }

        public final void updateCarrierPrivilegedCallbackRegistration() {
            TelephonyManager telephonyManager = this.mTelephonyManager;
            if (telephonyManager != null && this.mHasFeatureTelephonySubscription) {
                Collection simSlotMapping = telephonyManager.getSimSlotMapping();
                ArraySet arraySet = new ArraySet();
                synchronized (this.mSatLock) {
                    try {
                        IntArray intArray = new IntArray();
                        for (int size = this.mCarrierPrivilegedCallbacks.size() - 1;
                                size >= 0;
                                size--) {
                            intArray.add(this.mCarrierPrivilegedCallbacks.keyAt(size));
                        }
                        Iterator it = simSlotMapping.iterator();
                        while (it.hasNext()) {
                            int logicalSlotIndex =
                                    ((UiccSlotMapping) it.next()).getLogicalSlotIndex();
                            if (this.mCarrierPrivilegedCallbacks.contains(logicalSlotIndex)) {
                                int size2 = intArray.size() - 1;
                                while (true) {
                                    if (size2 < 0) {
                                        break;
                                    }
                                    if (intArray.get(size2) == logicalSlotIndex) {
                                        intArray.remove(size2);
                                        break;
                                    }
                                    size2--;
                                }
                            } else {
                                LogicalIndexCarrierPrivilegesCallback
                                        logicalIndexCarrierPrivilegesCallback =
                                                new LogicalIndexCarrierPrivilegesCallback(
                                                        logicalSlotIndex);
                                this.mCarrierPrivilegedCallbacks.put(
                                        logicalSlotIndex, logicalIndexCarrierPrivilegesCallback);
                                this.mTelephonyManager.registerCarrierPrivilegesCallback(
                                        logicalSlotIndex,
                                        AppSchedulingModuleThread.getExecutor(),
                                        logicalIndexCarrierPrivilegesCallback);
                            }
                        }
                        for (int size3 = intArray.size() - 1; size3 >= 0; size3--) {
                            int i = intArray.get(size3);
                            this.mTelephonyManager.unregisterCarrierPrivilegesCallback(
                                    (LogicalIndexCarrierPrivilegesCallback)
                                            this.mCarrierPrivilegedCallbacks.get(i));
                            this.mCarrierPrivilegedCallbacks.remove(i);
                            arraySet.addAll(this.mCarrierPrivilegedApps.get(i));
                            this.mCarrierPrivilegedApps.remove(i);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                updateSpecialAppSetUnlocked(arraySet);
            }
        }

        public final void updatePowerAllowlistCache() {
            DeviceIdleInternal deviceIdleInternal = this.mDeviceIdleInternal;
            if (deviceIdleInternal == null) {
                return;
            }
            String[] fullPowerWhitelistExceptIdle =
                    deviceIdleInternal.getFullPowerWhitelistExceptIdle();
            ArraySet arraySet = new ArraySet();
            synchronized (this.mSatLock) {
                try {
                    arraySet.addAll(this.mPowerAllowlistedApps);
                    this.mPowerAllowlistedApps.clear();
                    for (String str : fullPowerWhitelistExceptIdle) {
                        this.mPowerAllowlistedApps.add(str);
                        if (!arraySet.remove(str)) {
                            arraySet.add(str);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            updateSpecialAppSetUnlocked(arraySet);
        }

        public final void updateSpecialAppSetUnlocked(ArraySet arraySet) {
            if (Thread.holdsLock(this.mSatLock)) {
                throw new IllegalStateException("Must never hold local mSatLock");
            }
            if (arraySet.size() == 0) {
                return;
            }
            ArraySet arraySet2 = new ArraySet();
            synchronized (this.mSatLock) {
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    String str = (String) arraySet.valueAt(size);
                    synchronized (this.mSatLock) {
                        try {
                            if (!this.mPowerAllowlistedApps.contains(str)) {
                                for (int size2 = this.mCarrierPrivilegedApps.size() - 1;
                                        size2 >= 0;
                                        size2--) {
                                    SparseSetArray sparseSetArray = this.mCarrierPrivilegedApps;
                                    if (!sparseSetArray.contains(
                                            sparseSetArray.keyAt(size2), str)) {}
                                }
                                if (this.mSpecialApps.remove(-1, str)) {
                                    arraySet2.add(str);
                                }
                            }
                            if (this.mSpecialApps.add(-1, str)) {
                                arraySet2.add(str);
                            }
                        } finally {
                        }
                    }
                }
            }
            if (arraySet2.size() > 0) {
                synchronized (FlexibilityController.this.mLock) {
                    FlexibilityController.this.mPackagesToCheck.addAll(arraySet2);
                    FlexibilityController.this.mHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.Flex", 3);
    }

    public FlexibilityController(
            JobSchedulerService jobSchedulerService, PrefetchController prefetchController) {
        super(jobSchedulerService);
        this.mFallbackFlexibilityDeadlineMs =
                BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        this.mFallbackFlexibilityDeadlines = FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES;
        this.mFallbackFlexibilityDeadlineScores =
                FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES;
        this.mFallbackFlexibilityAdditionalScoreTimeFactors =
                FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS;
        this.mRescheduledJobDeadline = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        this.mMaxRescheduledDeadline =
                BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        this.mUnseenConstraintGracePeriodMs = 259200000L;
        this.mAppliedConstraints = 0;
        this.mMinTimeBetweenFlexibilityAlarmsMs = 60000L;
        this.mDeadlineProximityLimitMs = 900000L;
        this.mLastSeenConstraintTimesElapsed = new SparseLongArray();
        this.mPrefetchLifeCycleStart = new SparseArrayMap();
        this.mPrefetchChangedListener = new AnonymousClass1();
        this.mJobScoreTrackers = new SparseArrayMap();
        this.mJobsToCheck = new ArraySet();
        this.mPackagesToCheck = new ArraySet();
        this.mHandler = new FcHandler(AppSchedulingModuleThread.get().getLooper());
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")
                || this.mContext
                        .getPackageManager()
                        .hasSystemFeature("android.hardware.type.embedded")) {
            this.mSupportedFlexConstraints = 0;
        } else {
            this.mSupportedFlexConstraints = FLEXIBLE_CONSTRAINTS;
        }
        int i = this.mAppliedConstraints;
        int i2 = this.mSupportedFlexConstraints;
        this.mFlexibilityEnabled = (i & i2) != 0;
        this.mFlexibilityTracker = new FlexibilityTracker(Integer.bitCount(i2));
        this.mFcConfig = new FcConfig();
        this.mFlexibilityAlarmQueue =
                new FlexibilityAlarmQueue(
                        this.mContext, AppSchedulingModuleThread.get().getLooper());
        this.mPercentsToDropConstraints = FcConfig.DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS;
        this.mPrefetchController = prefetchController;
        SpecialAppTracker specialAppTracker = new SpecialAppTracker();
        this.mSpecialAppTracker = specialAppTracker;
        if (this.mFlexibilityEnabled) {
            SpecialAppTracker.m625$$Nest$mstartTracking(specialAppTracker);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpConstants(IndentingPrintWriter indentingPrintWriter) {
        FcConfig fcConfig = this.mFcConfig;
        SparseLongArray sparseLongArray = FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES;
        fcConfig.getClass();
        indentingPrintWriter.println();
        indentingPrintWriter.print("FlexibilityController");
        indentingPrintWriter.println(":");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print(
                "fc_applied_constraints", Integer.valueOf(fcConfig.APPLIED_CONSTRAINTS));
        indentingPrintWriter.print("(");
        int i = fcConfig.APPLIED_CONSTRAINTS;
        if (i != 0) {
            JobStatus.dumpConstraints(i, indentingPrintWriter);
        } else {
            indentingPrintWriter.print("nothing");
        }
        indentingPrintWriter.println(")");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(
                fcConfig.DEADLINE_PROXIMITY_LIMIT_MS,
                indentingPrintWriter,
                "fc_flexibility_deadline_proximity_limit_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(
                fcConfig.FALLBACK_FLEXIBILITY_DEADLINE_MS,
                indentingPrintWriter,
                "fc_fallback_flexibility_deadline_ms");
        indentingPrintWriter
                .print("fc_fallback_flexibility_deadlines", fcConfig.FALLBACK_FLEXIBILITY_DEADLINES)
                .println();
        indentingPrintWriter
                .print(
                        "fc_fallback_flexibility_deadline_scores",
                        fcConfig.FALLBACK_FLEXIBILITY_DEADLINE_SCORES)
                .println();
        indentingPrintWriter
                .print(
                        "fc_fallback_flexibility_deadline_additional_score_time_factors",
                        fcConfig.FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS)
                .println();
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(
                fcConfig.MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS,
                indentingPrintWriter,
                "fc_min_time_between_flexibility_alarms_ms");
        indentingPrintWriter
                .print(
                        "fc_percents_to_drop_flexible_constraints",
                        fcConfig.PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS)
                .println();
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(
                fcConfig.RESCHEDULED_JOB_DEADLINE_MS,
                indentingPrintWriter,
                "fc_rescheduled_job_deadline_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(
                fcConfig.MAX_RESCHEDULED_DEADLINE_MS,
                indentingPrintWriter,
                "fc_max_rescheduled_deadline_ms");
        indentingPrintWriter
                .print(
                        "fc_unseen_constraint_grace_period_ms",
                        Long.valueOf(fcConfig.UNSEEN_CONSTRAINT_GRACE_PERIOD_MS))
                .println();
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            final IndentingPrintWriter indentingPrintWriter,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        if (this.mLocalOverride) {
            indentingPrintWriter.println("Local override active");
        }
        indentingPrintWriter.print("Applied Flexible Constraints:");
        JobStatus.dumpConstraints(this.mAppliedConstraints, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.print("Satisfied Flexible Constraints:");
        JobStatus.dumpConstraints(this.mSatisfiedFlexibleConstraints, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.println();
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        indentingPrintWriter.println("Time since constraint combos last seen:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mLastSeenConstraintTimesElapsed.size(); i++) {
            int keyAt = this.mLastSeenConstraintTimesElapsed.keyAt(i);
            if (keyAt == this.mSatisfiedFlexibleConstraints) {
                indentingPrintWriter.print("0ms");
            } else {
                TimeUtils.formatDuration(
                        this.mLastSeenConstraintTimesElapsed.valueAt(i),
                        elapsedRealtime,
                        indentingPrintWriter);
            }
            indentingPrintWriter.print(":");
            if (keyAt != 0) {
                JobStatus.dumpConstraints(keyAt, indentingPrintWriter);
            } else {
                indentingPrintWriter.print(" none");
            }
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        SpecialAppTracker specialAppTracker = this.mSpecialAppTracker;
        specialAppTracker.getClass();
        indentingPrintWriter.println("Special apps:");
        indentingPrintWriter.increaseIndent();
        synchronized (specialAppTracker.mSatLock) {
            for (int i2 = 0; i2 < specialAppTracker.mSpecialApps.size(); i2++) {
                try {
                    indentingPrintWriter.print("User ");
                    indentingPrintWriter.print(specialAppTracker.mSpecialApps.keyAt(i2));
                    indentingPrintWriter.print(": ");
                    indentingPrintWriter.println(specialAppTracker.mSpecialApps.valuesAt(i2));
                } catch (Throwable th) {
                    throw th;
                }
            }
            indentingPrintWriter.println();
            indentingPrintWriter.println("Carrier privileged packages:");
            indentingPrintWriter.increaseIndent();
            for (int i3 = 0; i3 < specialAppTracker.mCarrierPrivilegedApps.size(); i3++) {
                indentingPrintWriter.print(specialAppTracker.mCarrierPrivilegedApps.keyAt(i3));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.println(specialAppTracker.mCarrierPrivilegedApps.valuesAt(i3));
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.print("Power allowlisted packages: ");
            indentingPrintWriter.println(specialAppTracker.mPowerAllowlistedApps);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        FlexibilityTracker flexibilityTracker = this.mFlexibilityTracker;
        for (int i4 = 0; i4 < flexibilityTracker.mTrackedJobs.size(); i4++) {
            ArraySet arraySet = (ArraySet) flexibilityTracker.mTrackedJobs.get(i4);
            for (int i5 = 0; i5 < arraySet.size(); i5++) {
                JobStatus jobStatus = (JobStatus) arraySet.valueAt(i5);
                if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                    jobStatus.printUniqueId(indentingPrintWriter);
                    indentingPrintWriter.print(" from ");
                    UserHandle.formatUid(indentingPrintWriter, jobStatus.sourceUid);
                    indentingPrintWriter.print("-> Num Required Constraints: ");
                    indentingPrintWriter.print(jobStatus.getNumRequiredFlexibleConstraints());
                    indentingPrintWriter.print(", lifecycle=[");
                    FlexibilityController flexibilityController = FlexibilityController.this;
                    long lifeCycleBeginningElapsedLocked =
                            flexibilityController.getLifeCycleBeginningElapsedLocked(jobStatus);
                    indentingPrintWriter.print(lifeCycleBeginningElapsedLocked);
                    indentingPrintWriter.print(", (");
                    indentingPrintWriter.print(
                            flexibilityController.getCurPercentOfLifecycleLocked(
                                    jobStatus, elapsedRealtime));
                    indentingPrintWriter.print("%), ");
                    indentingPrintWriter.print(
                            FlexibilityController.this.getLifeCycleEndElapsedLocked(
                                    jobStatus, elapsedRealtime, lifeCycleBeginningElapsedLocked));
                    indentingPrintWriter.print("]");
                    indentingPrintWriter.println();
                }
            }
        }
        indentingPrintWriter.println();
        indentingPrintWriter.println("Job scores:");
        indentingPrintWriter.increaseIndent();
        this.mJobScoreTrackers.forEach(
                new SparseArrayMap
                        .TriConsumer() { // from class:
                                         // com.android.server.job.controllers.FlexibilityController$$ExternalSyntheticLambda0
                    public final void accept(int i6, Object obj, Object obj2) {
                        IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                        long j = elapsedRealtime;
                        FlexibilityController.JobScoreTracker jobScoreTracker =
                                (FlexibilityController.JobScoreTracker) obj2;
                        indentingPrintWriter2.print(i6);
                        indentingPrintWriter2.print("/");
                        indentingPrintWriter2.print((String) obj);
                        indentingPrintWriter2.print(": ");
                        jobScoreTracker.getClass();
                        indentingPrintWriter2.print("{");
                        int i7 = 0;
                        boolean z = false;
                        while (true) {
                            FlexibilityController.JobScoreTracker.JobScoreBucket[]
                                    jobScoreBucketArr = jobScoreTracker.mScoreBuckets;
                            if (i7 >= jobScoreBucketArr.length) {
                                indentingPrintWriter2.print("}");
                                indentingPrintWriter2.println();
                                return;
                            }
                            FlexibilityController.JobScoreTracker.JobScoreBucket jobScoreBucket =
                                    jobScoreBucketArr[
                                            ((jobScoreTracker.mScoreBucketIndex + 1) + i7)
                                                    % jobScoreBucketArr.length];
                            if (jobScoreBucket != null && jobScoreBucket.startTimeElapsed != 0) {
                                if (z) {
                                    indentingPrintWriter2.print(", ");
                                }
                                TimeUtils.formatDuration(
                                        jobScoreBucket.startTimeElapsed, j, indentingPrintWriter2);
                                indentingPrintWriter2.print("=");
                                indentingPrintWriter2.print(jobScoreBucket.score);
                                z = true;
                            }
                            i7++;
                        }
                    }
                });
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mFlexibilityAlarmQueue.dump(indentingPrintWriter);
    }

    public int getCurPercentOfLifecycleLocked(JobStatus jobStatus, long j) {
        long lifeCycleBeginningElapsedLocked = getLifeCycleBeginningElapsedLocked(jobStatus);
        long lifeCycleEndElapsedLocked =
                getLifeCycleEndElapsedLocked(jobStatus, j, lifeCycleBeginningElapsedLocked);
        if (lifeCycleEndElapsedLocked == Long.MAX_VALUE || lifeCycleBeginningElapsedLocked >= j) {
            return 0;
        }
        if (j > lifeCycleEndElapsedLocked
                || lifeCycleEndElapsedLocked == lifeCycleBeginningElapsedLocked) {
            return 100;
        }
        return (int)
                (((j - lifeCycleBeginningElapsedLocked) * 100)
                        / (lifeCycleEndElapsedLocked - lifeCycleBeginningElapsedLocked));
    }

    public FcConfig getFcConfig() {
        return this.mFcConfig;
    }

    public long getLifeCycleBeginningElapsedLocked(JobStatus jobStatus) {
        long j = jobStatus.earliestRunTimeElapsedMillis;
        if (j == 0) {
            j = jobStatus.enqueueTime;
        }
        if (jobStatus.job.isPeriodic() && jobStatus.getNumPreviousAttempts() == 0) {
            j = ((jobStatus.latestRunTimeElapsedMillis - jobStatus.job.getFlexMillis()) + j) / 2;
        }
        if (!jobStatus.job.isPrefetch()) {
            return j;
        }
        PrefetchController prefetchController = this.mPrefetchController;
        prefetchController.getClass();
        long millis = JobSchedulerService.sSystemClock.millis();
        int i = jobStatus.sourceUserId;
        String str = jobStatus.sourcePackageName;
        long nextEstimatedLaunchTimeLocked =
                prefetchController.getNextEstimatedLaunchTimeLocked(i, str, millis);
        long longValue = ((Long) this.mPrefetchLifeCycleStart.getOrDefault(i, str, 0L)).longValue();
        if (nextEstimatedLaunchTimeLocked != Long.MAX_VALUE) {
            longValue =
                    Math.max(
                            longValue,
                            nextEstimatedLaunchTimeLocked
                                    - this.mPrefetchController.getLaunchTimeThresholdMs());
        }
        return Math.max(longValue, j);
    }

    public long getLifeCycleEndElapsedLocked(JobStatus jobStatus, long j, long j2) {
        boolean isPrefetch = jobStatus.job.isPrefetch();
        String str = jobStatus.sourcePackageName;
        long j3 = jobStatus.latestRunTimeElapsedMillis;
        if (!isPrefetch) {
            if (jobStatus.getNumPreviousAttempts() > 1) {
                return Math.min(
                                (long)
                                        Math.scalb(
                                                this.mRescheduledJobDeadline,
                                                jobStatus.getNumPreviousAttempts() - 2),
                                this.mMaxRescheduledDeadline)
                        + j2;
            }
            int effectivePriority = jobStatus.getEffectivePriority();
            int scoreLocked = getScoreLocked(jobStatus.sourceUid, str, j);
            long j4 = this.mFallbackFlexibilityDeadlineMs;
            long min =
                    Math.min(
                                    3 * j4,
                                    (this.mFallbackFlexibilityAdditionalScoreTimeFactors.get(
                                                            effectivePriority, 60000L)
                                                    * scoreLocked)
                                            + this.mFallbackFlexibilityDeadlines.get(
                                                    effectivePriority, j4))
                            + j2;
            return j3 == Long.MAX_VALUE ? min : Math.max(min, j3);
        }
        PrefetchController prefetchController = this.mPrefetchController;
        prefetchController.getClass();
        long nextEstimatedLaunchTimeLocked =
                prefetchController.getNextEstimatedLaunchTimeLocked(
                        jobStatus.sourceUserId, str, JobSchedulerService.sSystemClock.millis());
        JobSchedulerService.Constants constants = this.mConstants;
        if (j3 != Long.MAX_VALUE) {
            return Math.min(
                    nextEstimatedLaunchTimeLocked
                            - constants.PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS,
                    j3);
        }
        if (nextEstimatedLaunchTimeLocked != Long.MAX_VALUE) {
            return nextEstimatedLaunchTimeLocked
                    - constants.PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS;
        }
        return Long.MAX_VALUE;
    }

    public long getNextConstraintDropTimeElapsedLocked(JobStatus jobStatus) {
        long lifeCycleBeginningElapsedLocked = getLifeCycleBeginningElapsedLocked(jobStatus);
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        return getNextConstraintDropTimeElapsedLocked(
                jobStatus,
                lifeCycleBeginningElapsedLocked,
                getLifeCycleEndElapsedLocked(
                        jobStatus, SystemClock.elapsedRealtime(), lifeCycleBeginningElapsedLocked));
    }

    public final long getNextConstraintDropTimeElapsedLocked(JobStatus jobStatus, long j, long j2) {
        int i;
        int[] percentsToDropConstraints =
                getPercentsToDropConstraints(jobStatus.getEffectivePriority());
        if (j2 == Long.MAX_VALUE
                || (i = jobStatus.mNumDroppedFlexibleConstraints)
                        == percentsToDropConstraints.length) {
            return Long.MAX_VALUE;
        }
        return (((j2 - j) * percentsToDropConstraints[i]) / 100) + j;
    }

    public final int[] getPercentsToDropConstraints(int i) {
        int[] iArr = (int[]) this.mPercentsToDropConstraints.get(i);
        if (iArr != null) {
            return iArr;
        }
        Slog.wtf("JobScheduler.Flex", "No %-to-drop for priority " + JobInfo.getPriorityString(i));
        return new int[] {50, 60, 70, 80};
    }

    public int getRelevantAppliedConstraintsLocked(JobStatus jobStatus) {
        return this.mAppliedConstraints
                & ((jobStatus.mCanApplyTransportAffinities ? 268435456 : 0) | 7);
    }

    public int getScoreLocked(int i, String str, long j) {
        JobScoreTracker jobScoreTracker = (JobScoreTracker) this.mJobScoreTrackers.get(i, str);
        if (jobScoreTracker == null) {
            return 0;
        }
        if (j < jobScoreTracker.mCachedScoreExpirationTimeElapsed) {
            return jobScoreTracker.mCachedScore;
        }
        long j2 = j - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        long j3 = Long.MAX_VALUE;
        int i2 = 0;
        for (JobScoreTracker.JobScoreBucket jobScoreBucket : jobScoreTracker.mScoreBuckets) {
            if (jobScoreBucket != null) {
                long j4 = jobScoreBucket.startTimeElapsed;
                if (j4 >= j2) {
                    i2 += jobScoreBucket.score;
                    if (j3 > j4) {
                        j3 = j4;
                    }
                }
            }
        }
        jobScoreTracker.mCachedScore = i2;
        jobScoreTracker.mCachedScoreExpirationTimeElapsed =
                j3 + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        return i2;
    }

    public boolean hasEnoughSatisfiedConstraintsLocked(JobStatus jobStatus) {
        int bitCount =
                Integer.bitCount(
                        this.mSatisfiedFlexibleConstraints
                                & this.mAppliedConstraints
                                & ((jobStatus.mTransportAffinitiesSatisfied ? 268435456 : 0) | 7));
        if (bitCount >= jobStatus.getNumRequiredFlexibleConstraints()) {
            return true;
        }
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime < this.mUnseenConstraintGracePeriodMs) {
            return false;
        }
        int i = ~getRelevantAppliedConstraintsLocked(jobStatus);
        for (int size = this.mLastSeenConstraintTimesElapsed.size() - 1; size >= 0; size--) {
            int keyAt = this.mLastSeenConstraintTimesElapsed.keyAt(size);
            if ((keyAt & i) == 0) {
                boolean z =
                        elapsedRealtime - this.mLastSeenConstraintTimesElapsed.valueAt(size)
                                <= this.mUnseenConstraintGracePeriodMs;
                if (Integer.bitCount(keyAt) > bitCount && z) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isConstraintSatisfied(int i) {
        return (this.mSatisfiedFlexibleConstraints & i) != 0;
    }

    public final boolean isFlexibilitySatisfiedLocked(JobStatus jobStatus) {
        if (this.mFlexibilityEnabled
                && this.mService.getUidBias(jobStatus.sourceUid) != 40
                && (this.mService.getUidBias(jobStatus.sourceUid) < 30
                        || jobStatus.getEffectivePriority() < 300)) {
            if (jobStatus.getEffectivePriority() >= 300) {
                SpecialAppTracker specialAppTracker = this.mSpecialAppTracker;
                int i = jobStatus.sourceUserId;
                String str = jobStatus.sourcePackageName;
                synchronized (specialAppTracker.mSatLock) {
                    try {
                        if (!specialAppTracker.mSpecialApps.contains(-1, str)) {
                            if (specialAppTracker.mSpecialApps.contains(i, str)) {}
                        }
                    } finally {
                    }
                }
            }
            if (!hasEnoughSatisfiedConstraintsLocked(jobStatus)
                    && !this.mService.isCurrentlyRunningLocked(jobStatus)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.hasFlexibilityConstraint()) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (this.mSupportedFlexConstraints == 0) {
                jobStatus.setConstraintSatisfied(2097152, elapsedRealtime, true);
                return;
            }
            jobStatus.mNumAppliedFlexibleConstraints =
                    Integer.bitCount(getRelevantAppliedConstraintsLocked(jobStatus));
            jobStatus.setConstraintSatisfied(
                    2097152, elapsedRealtime, isFlexibilitySatisfiedLocked(jobStatus));
            this.mFlexibilityTracker.add(jobStatus);
            jobStatus.setTrackingController(128);
            this.mFlexibilityAlarmQueue.scheduleDropNumConstraintsAlarm(jobStatus, elapsedRealtime);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(128)) {
            this.mFlexibilityAlarmQueue.removeAlarmForKey(jobStatus);
            ((ArraySet)
                            this.mFlexibilityTracker.mTrackedJobs.get(
                                    jobStatus.getNumRequiredFlexibleConstraints()))
                    .remove(jobStatus);
        }
        this.mJobsToCheck.remove(jobStatus);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onAppRemovedLocked(int i, String str) {
        int userId = UserHandle.getUserId(i);
        this.mPrefetchLifeCycleStart.delete(userId, str);
        this.mJobScoreTrackers.delete(i, str);
        SpecialAppTracker specialAppTracker = this.mSpecialAppTracker;
        synchronized (specialAppTracker.mSatLock) {
            specialAppTracker.mSpecialApps.remove(userId, str);
        }
        for (int size = this.mJobsToCheck.size() - 1; size >= 0; size--) {
            JobStatus jobStatus = (JobStatus) this.mJobsToCheck.valueAt(size);
            if ((jobStatus.sourceUid == i && jobStatus.sourcePackageName.equals(str))
                    || (jobStatus.callingUid == i
                            && jobStatus.job.getService().getPackageName().equals(str))) {
                this.mJobsToCheck.removeAt(size);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onConstantsUpdatedLocked() {
        if (this.mFcConfig.mShouldReevaluateConstraints) {
            AppSchedulingModuleThread.getHandler()
                    .post(new FlexibilityController$$ExternalSyntheticLambda1(0, this));
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onSystemServicesReady() {
        SpecialAppTracker specialAppTracker = this.mSpecialAppTracker;
        specialAppTracker.getClass();
        specialAppTracker.mDeviceIdleInternal =
                (DeviceIdleInternal) LocalServices.getService(DeviceIdleInternal.class);
        specialAppTracker.mTelephonyManager =
                (TelephonyManager)
                        FlexibilityController.this.mContext.getSystemService(
                                TelephonyManager.class);
        synchronized (FlexibilityController.this.mLock) {
            try {
                FlexibilityController flexibilityController = FlexibilityController.this;
                if (flexibilityController.mFlexibilityEnabled) {
                    flexibilityController.mHandler.post(
                            new FlexibilityController$$ExternalSyntheticLambda1(
                                    1, specialAppTracker));
                    FlexibilityController.this.mHandler.post(
                            new FlexibilityController$$ExternalSyntheticLambda1(
                                    2, specialAppTracker));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onUidBiasChangedLocked(int i, int i2, int i3) {
        if (i2 >= 30 || i3 >= 30) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            JobSchedulerService jobSchedulerService = this.mService;
            JobStore.JobSet jobSet = jobSchedulerService.mJobs.mJobSet;
            jobSet.getClass();
            ArraySet arraySet = new ArraySet();
            jobSet.getJobsBySourceUid(i, arraySet);
            boolean z = false;
            for (int i4 = 0; i4 < arraySet.size(); i4++) {
                JobStatus jobStatus = (JobStatus) arraySet.valueAt(i4);
                if (jobStatus.hasFlexibilityConstraint()) {
                    jobStatus.setConstraintSatisfied(
                            2097152, elapsedRealtime, isFlexibilitySatisfiedLocked(jobStatus));
                    z |= jobStatus.job.isPrefetch();
                }
            }
            if (z && i2 == 40) {
                int userId = UserHandle.getUserId(i);
                ArraySet packagesForUidLocked = jobSchedulerService.getPackagesForUidLocked(i);
                if (packagesForUidLocked == null) {
                    return;
                }
                for (int i5 = 0; i5 < packagesForUidLocked.size(); i5++) {
                    String str = (String) packagesForUidLocked.valueAt(i5);
                    SparseArrayMap sparseArrayMap = this.mPrefetchLifeCycleStart;
                    sparseArrayMap.add(
                            userId,
                            str,
                            Long.valueOf(
                                    Math.max(
                                            ((Long) sparseArrayMap.getOrDefault(userId, str, 0L))
                                                    .longValue(),
                                            elapsedRealtime)));
                }
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onUserRemovedLocked(int i) {
        this.mPrefetchLifeCycleStart.delete(i);
        SpecialAppTracker specialAppTracker = this.mSpecialAppTracker;
        synchronized (specialAppTracker.mSatLock) {
            specialAppTracker.mSpecialApps.remove(i);
        }
        for (int numMaps = this.mJobScoreTrackers.numMaps() - 1; numMaps >= 0; numMaps--) {
            if (UserHandle.getUserId(this.mJobScoreTrackers.keyAt(numMaps)) == i) {
                this.mJobScoreTrackers.deleteAt(numMaps);
            }
        }
        for (int size = this.mJobsToCheck.size() - 1; size >= 0; size--) {
            JobStatus jobStatus = (JobStatus) this.mJobsToCheck.valueAt(size);
            if (UserHandle.getUserId(jobStatus.sourceUid) == i
                    || UserHandle.getUserId(jobStatus.callingUid) == i) {
                this.mJobsToCheck.removeAt(size);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void prepareForExecutionLocked(JobStatus jobStatus) {
        if (jobStatus.lastEvaluatedBias == 40) {
            return;
        }
        int priority = jobStatus.job.getPriority();
        int i =
                this.mFallbackFlexibilityDeadlineScores.get(
                        priority,
                        FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.get(
                                priority, priority / 100));
        SparseArrayMap sparseArrayMap = this.mJobScoreTrackers;
        int i2 = jobStatus.sourceUid;
        String str = jobStatus.sourcePackageName;
        JobScoreTracker jobScoreTracker = (JobScoreTracker) sparseArrayMap.get(i2, str);
        if (jobScoreTracker == null) {
            jobScoreTracker = new JobScoreTracker();
            this.mJobScoreTrackers.add(i2, str, jobScoreTracker);
        }
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        jobScoreTracker.addScore(i, SystemClock.elapsedRealtime());
    }

    @Override // com.android.server.job.controllers.StateController
    public final void prepareForUpdatedConstantsLocked() {
        this.mFcConfig.mShouldReevaluateConstraints = false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.server.job.controllers.StateController
    public final void processConstantLocked(DeviceConfig.Properties properties, String str) {
        char c;
        FcConfig fcConfig = this.mFcConfig;
        fcConfig.getClass();
        switch (str.hashCode()) {
            case -2004789501:
                if (str.equals("fc_min_time_between_flexibility_alarms_ms")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1573718613:
                if (str.equals("fc_max_rescheduled_deadline_ms")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -790107353:
                if (str.equals("fc_fallback_flexibility_deadline_scores")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -540379004:
                if (str.equals("fc_rescheduled_job_deadline_ms")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 100307866:
                if (str.equals("fc_fallback_flexibility_deadlines")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 581236233:
                if (str.equals("fc_flexibility_deadline_proximity_limit_ms")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 659395101:
                if (str.equals("fc_percents_to_drop_flexible_constraints")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 746985028:
                if (str.equals("fc_unseen_constraint_grace_period_ms")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1815875441:
                if (str.equals("fc_fallback_flexibility_deadline_additional_score_time_factors")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1821391602:
                if (str.equals("fc_applied_constraints")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1906562988:
                if (str.equals("fc_fallback_flexibility_deadline_ms")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                long j = properties.getLong(str, 60000L);
                fcConfig.MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS = j;
                FlexibilityController flexibilityController = FlexibilityController.this;
                if (flexibilityController.mMinTimeBetweenFlexibilityAlarmsMs != j) {
                    flexibilityController.mMinTimeBetweenFlexibilityAlarmsMs = j;
                    flexibilityController.mFlexibilityAlarmQueue.setMinTimeBetweenAlarmsMs(j);
                    fcConfig.mShouldReevaluateConstraints = true;
                    return;
                }
                return;
            case 1:
                long j2 =
                        properties.getLong(
                                str,
                                BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
                fcConfig.MAX_RESCHEDULED_DEADLINE_MS = j2;
                FlexibilityController flexibilityController2 = FlexibilityController.this;
                if (flexibilityController2.mMaxRescheduledDeadline != j2) {
                    flexibilityController2.mMaxRescheduledDeadline = j2;
                    fcConfig.mShouldReevaluateConstraints = true;
                    return;
                }
                return;
            case 2:
                String string = properties.getString(str, (String) null);
                SparseIntArray sparseIntArray = fcConfig.FALLBACK_FLEXIBILITY_DEADLINE_SCORES;
                SparseIntArray sparseIntArray2 =
                        FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES;
                KeyValueListParser keyValueListParser = new KeyValueListParser(',');
                try {
                    keyValueListParser.setString(string);
                } catch (IllegalArgumentException e) {
                    Slog.wtf("JobScheduler.Flex", "Bad string given", e);
                    keyValueListParser.setString((String) null);
                }
                int i = sparseIntArray.get(500);
                int i2 = sparseIntArray.get(400);
                int i3 = sparseIntArray.get(300);
                int i4 = sparseIntArray.get(200);
                int i5 = sparseIntArray.get(100);
                int i6 = keyValueListParser.getInt(String.valueOf(500), sparseIntArray2.get(500));
                int i7 = keyValueListParser.getInt(String.valueOf(400), sparseIntArray2.get(400));
                int i8 = keyValueListParser.getInt(String.valueOf(300), sparseIntArray2.get(300));
                int i9 = keyValueListParser.getInt(String.valueOf(200), sparseIntArray2.get(200));
                int i10 = keyValueListParser.getInt(String.valueOf(100), sparseIntArray2.get(100));
                sparseIntArray.put(500, i6);
                sparseIntArray.put(400, i7);
                sparseIntArray.put(300, i8);
                sparseIntArray.put(200, i9);
                sparseIntArray.put(100, i10);
                if (i == i6 && i2 == i7 && i3 == i8 && i4 == i9 && i5 == i10) {
                    return;
                }
                FlexibilityController.this.mFallbackFlexibilityDeadlineScores =
                        fcConfig.FALLBACK_FLEXIBILITY_DEADLINE_SCORES;
                fcConfig.mShouldReevaluateConstraints = true;
                return;
            case 3:
                long j3 =
                        properties.getLong(str, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
                fcConfig.RESCHEDULED_JOB_DEADLINE_MS = j3;
                FlexibilityController flexibilityController3 = FlexibilityController.this;
                if (flexibilityController3.mRescheduledJobDeadline != j3) {
                    flexibilityController3.mRescheduledJobDeadline = j3;
                    fcConfig.mShouldReevaluateConstraints = true;
                    return;
                }
                return;
            case 4:
                if (FcConfig.parsePriorityToLongKeyValueString(
                        properties.getString(str, (String) null),
                        fcConfig.FALLBACK_FLEXIBILITY_DEADLINES,
                        FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES)) {
                    FlexibilityController.this.mFallbackFlexibilityDeadlines =
                            fcConfig.FALLBACK_FLEXIBILITY_DEADLINES;
                    fcConfig.mShouldReevaluateConstraints = true;
                    return;
                }
                return;
            case 5:
                long j4 = properties.getLong(str, 900000L);
                fcConfig.DEADLINE_PROXIMITY_LIMIT_MS = j4;
                FlexibilityController flexibilityController4 = FlexibilityController.this;
                if (flexibilityController4.mDeadlineProximityLimitMs != j4) {
                    flexibilityController4.mDeadlineProximityLimitMs = j4;
                    fcConfig.mShouldReevaluateConstraints = true;
                    return;
                }
                return;
            case 6:
                String string2 = properties.getString(str, (String) null);
                SparseArray sparseArray = fcConfig.PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS;
                SparseArray sparseArray2 = FcConfig.DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS;
                KeyValueListParser keyValueListParser2 = new KeyValueListParser(',');
                try {
                    keyValueListParser2.setString(string2);
                } catch (IllegalArgumentException e2) {
                    Slog.wtf("JobScheduler.Flex", "Bad percent to drop key value string given", e2);
                    keyValueListParser2.setString((String) null);
                }
                int[] iArr = (int[]) sparseArray.get(500);
                int[] iArr2 = (int[]) sparseArray.get(400);
                int[] iArr3 = (int[]) sparseArray.get(300);
                int[] iArr4 = (int[]) sparseArray.get(200);
                int[] iArr5 = (int[]) sparseArray.get(100);
                int[] parsePercentToDropString =
                        FcConfig.parsePercentToDropString(
                                keyValueListParser2.getString(String.valueOf(500), (String) null));
                int[] parsePercentToDropString2 =
                        FcConfig.parsePercentToDropString(
                                keyValueListParser2.getString(String.valueOf(400), (String) null));
                int[] parsePercentToDropString3 =
                        FcConfig.parsePercentToDropString(
                                keyValueListParser2.getString(String.valueOf(300), (String) null));
                int[] parsePercentToDropString4 =
                        FcConfig.parsePercentToDropString(
                                keyValueListParser2.getString(String.valueOf(200), (String) null));
                int[] parsePercentToDropString5 =
                        FcConfig.parsePercentToDropString(
                                keyValueListParser2.getString(String.valueOf(100), (String) null));
                if (parsePercentToDropString == null) {
                    parsePercentToDropString = (int[]) sparseArray2.get(500);
                }
                sparseArray.put(500, parsePercentToDropString);
                if (parsePercentToDropString2 == null) {
                    parsePercentToDropString2 = (int[]) sparseArray2.get(400);
                }
                sparseArray.put(400, parsePercentToDropString2);
                if (parsePercentToDropString3 == null) {
                    parsePercentToDropString3 = (int[]) sparseArray2.get(300);
                }
                sparseArray.put(300, parsePercentToDropString3);
                if (parsePercentToDropString4 == null) {
                    parsePercentToDropString4 = (int[]) sparseArray2.get(200);
                }
                sparseArray.put(200, parsePercentToDropString4);
                if (parsePercentToDropString5 == null) {
                    parsePercentToDropString5 = (int[]) sparseArray2.get(100);
                }
                sparseArray.put(100, parsePercentToDropString5);
                if (Arrays.equals(iArr, (int[]) sparseArray.get(500))
                        && Arrays.equals(iArr2, (int[]) sparseArray.get(400))
                        && Arrays.equals(iArr3, (int[]) sparseArray.get(300))
                        && Arrays.equals(iArr4, (int[]) sparseArray.get(200))
                        && Arrays.equals(iArr5, (int[]) sparseArray.get(100))) {
                    return;
                }
                FlexibilityController.this.mPercentsToDropConstraints =
                        fcConfig.PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS;
                fcConfig.mShouldReevaluateConstraints = true;
                return;
            case 7:
                long j5 = properties.getLong(str, 259200000L);
                fcConfig.UNSEEN_CONSTRAINT_GRACE_PERIOD_MS = j5;
                FlexibilityController flexibilityController5 = FlexibilityController.this;
                if (flexibilityController5.mUnseenConstraintGracePeriodMs != j5) {
                    flexibilityController5.mUnseenConstraintGracePeriodMs = j5;
                    fcConfig.mShouldReevaluateConstraints = true;
                    return;
                }
                return;
            case '\b':
                if (FcConfig.parsePriorityToLongKeyValueString(
                        properties.getString(str, (String) null),
                        fcConfig.FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS,
                        FcConfig
                                .DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS)) {
                    FlexibilityController.this.mFallbackFlexibilityAdditionalScoreTimeFactors =
                            fcConfig.FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS;
                    fcConfig.mShouldReevaluateConstraints = true;
                    return;
                }
                return;
            case '\t':
                int i11 = properties.getInt(str, 0);
                FlexibilityController flexibilityController6 = FlexibilityController.this;
                int i12 = i11 & flexibilityController6.mSupportedFlexConstraints;
                fcConfig.APPLIED_CONSTRAINTS = i12;
                if (flexibilityController6.mAppliedConstraints != i12) {
                    flexibilityController6.mAppliedConstraints = i12;
                    fcConfig.mShouldReevaluateConstraints = true;
                    if (i12 != 0) {
                        flexibilityController6.mFlexibilityEnabled = true;
                        PrefetchController prefetchController =
                                flexibilityController6.mPrefetchController;
                        PrefetchController.PrefetchChangedListener prefetchChangedListener =
                                flexibilityController6.mPrefetchChangedListener;
                        synchronized (prefetchController.mLock) {
                            prefetchController.mPrefetchChangedListeners.add(
                                    prefetchChangedListener);
                        }
                        SpecialAppTracker.m625$$Nest$mstartTracking(
                                FlexibilityController.this.mSpecialAppTracker);
                        return;
                    }
                    flexibilityController6.mFlexibilityEnabled = false;
                    PrefetchController prefetchController2 =
                            flexibilityController6.mPrefetchController;
                    PrefetchController.PrefetchChangedListener prefetchChangedListener2 =
                            flexibilityController6.mPrefetchChangedListener;
                    synchronized (prefetchController2.mLock) {
                        prefetchController2.mPrefetchChangedListeners.remove(
                                prefetchChangedListener2);
                    }
                    SpecialAppTracker specialAppTracker =
                            FlexibilityController.this.mSpecialAppTracker;
                    FlexibilityController.this.mContext.unregisterReceiver(
                            specialAppTracker.mBroadcastReceiver);
                    synchronized (specialAppTracker.mSatLock) {
                        try {
                            specialAppTracker.mCarrierPrivilegedApps.clear();
                            specialAppTracker.mPowerAllowlistedApps.clear();
                            specialAppTracker.mSpecialApps.clear();
                            for (int size =
                                            specialAppTracker.mCarrierPrivilegedCallbacks.size()
                                                    - 1;
                                    size >= 0;
                                    size--) {
                                specialAppTracker.mTelephonyManager
                                        .unregisterCarrierPrivilegesCallback(
                                                (TelephonyManager.CarrierPrivilegesCallback)
                                                        specialAppTracker
                                                                .mCarrierPrivilegedCallbacks
                                                                .valueAt(size));
                            }
                            specialAppTracker.mCarrierPrivilegedCallbacks.clear();
                        } finally {
                        }
                    }
                    return;
                }
                return;
            case '\n':
                long j6 =
                        properties.getLong(
                                str,
                                BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
                fcConfig.FALLBACK_FLEXIBILITY_DEADLINE_MS = j6;
                FlexibilityController flexibilityController7 = FlexibilityController.this;
                if (flexibilityController7.mFallbackFlexibilityDeadlineMs != j6) {
                    flexibilityController7.mFallbackFlexibilityDeadlineMs = j6;
                    return;
                }
                return;
            default:
                return;
        }
    }

    public final void setConstraintSatisfied(int i, long j, boolean z) {
        synchronized (this.mLock) {
            try {
                if (((this.mSatisfiedFlexibleConstraints & i) != 0) == z) {
                    return;
                }
                if (DEBUG) {
                    Slog.d(
                            "JobScheduler.Flex",
                            "setConstraintSatisfied:  constraint: " + i + " state: " + z);
                }
                this.mLastSeenConstraintTimesElapsed.put(this.mSatisfiedFlexibleConstraints, j);
                if (!z) {
                    this.mLastSeenConstraintTimesElapsed.put(i, j);
                }
                this.mSatisfiedFlexibleConstraints =
                        (this.mSatisfiedFlexibleConstraints & (~i)) | (z ? i : 0);
                if ((i & 268435456) != 0) {
                    return;
                }
                if (this.mFlexibilityEnabled) {
                    this.mHandler.obtainMessage(0).sendToTarget();
                }
            } finally {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0016 A[Catch: all -> 0x000f, TryCatch #0 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0008, B:10:0x0012, B:12:0x0016, B:14:0x0021, B:15:0x002a, B:19:0x0019), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0021 A[Catch: all -> 0x000f, TryCatch #0 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0008, B:10:0x0012, B:12:0x0016, B:14:0x0021, B:15:0x002a, B:19:0x0019), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0019 A[Catch: all -> 0x000f, TryCatch #0 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0008, B:10:0x0012, B:12:0x0016, B:14:0x0021, B:15:0x002a, B:19:0x0019), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setLocalPolicyForTesting(int r4, boolean r5) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            boolean r1 = r3.mLocalOverride     // Catch: java.lang.Throwable -> Lf
            r2 = 0
            if (r1 != r5) goto L11
            int r1 = r3.mAppliedConstraints     // Catch: java.lang.Throwable -> Lf
            if (r1 == r4) goto Ld
            goto L11
        Ld:
            r1 = r2
            goto L12
        Lf:
            r3 = move-exception
            goto L2c
        L11:
            r1 = 1
        L12:
            r3.mLocalOverride = r5     // Catch: java.lang.Throwable -> Lf
            if (r5 == 0) goto L19
            r3.mAppliedConstraints = r4     // Catch: java.lang.Throwable -> Lf
            goto L1f
        L19:
            com.android.server.job.controllers.FlexibilityController$FcConfig r4 = r3.mFcConfig     // Catch: java.lang.Throwable -> Lf
            int r4 = r4.APPLIED_CONSTRAINTS     // Catch: java.lang.Throwable -> Lf
            r3.mAppliedConstraints = r4     // Catch: java.lang.Throwable -> Lf
        L1f:
            if (r1 == 0) goto L2a
            com.android.server.job.controllers.FlexibilityController$FcHandler r3 = r3.mHandler     // Catch: java.lang.Throwable -> Lf
            android.os.Message r3 = r3.obtainMessage(r2)     // Catch: java.lang.Throwable -> Lf
            r3.sendToTarget()     // Catch: java.lang.Throwable -> Lf
        L2a:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lf
            return
        L2c:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lf
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.job.controllers.FlexibilityController.setLocalPolicyForTesting(int,"
                    + " boolean):void");
    }

    @Override // com.android.server.job.controllers.StateController
    public final void startTrackingLocked() {
        if (this.mFlexibilityEnabled) {
            PrefetchController prefetchController = this.mPrefetchController;
            PrefetchController.PrefetchChangedListener prefetchChangedListener =
                    this.mPrefetchChangedListener;
            synchronized (prefetchController.mLock) {
                prefetchController.mPrefetchChangedListeners.add(prefetchChangedListener);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void unprepareFromExecutionLocked(JobStatus jobStatus) {
        if (jobStatus.lastEvaluatedBias == 40) {
            return;
        }
        JobScoreTracker jobScoreTracker =
                (JobScoreTracker)
                        this.mJobScoreTrackers.get(
                                jobStatus.sourceUid, jobStatus.sourcePackageName);
        if (jobScoreTracker == null) {
            Slog.e("JobScheduler.Flex", "Unprepared a job that didn't result in a score change");
            return;
        }
        int priority = jobStatus.job.getPriority();
        int i =
                -this.mFallbackFlexibilityDeadlineScores.get(
                        priority,
                        FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.get(
                                priority, priority / 100));
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        jobScoreTracker.addScore(i, SystemClock.elapsedRealtime());
    }
}
