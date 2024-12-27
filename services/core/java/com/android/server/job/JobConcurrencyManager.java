package com.android.server.job;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.BackgroundStartPrivileges;
import android.app.UserSwitchObserver;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.DataUnit;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import android.util.Pools;
import android.util.Slog;
import android.util.SparseArrayMap;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.internal.app.IBatteryStats;
import com.android.internal.util.MemInfoReader;
import com.android.internal.util.jobs.StatLogger;
import com.android.modules.expresslog.Histogram;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.mars.filter.filter.JobSchedulerPackageFilter;
import com.android.server.job.controllers.JobStatus;
import com.android.server.job.controllers.StateController;
import com.android.server.job.restrictions.JobRestriction;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.UserManagerInternal;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class JobConcurrencyManager {
    public static final WorkConfigLimitsPerMemoryTrimLevel CONFIG_LIMITS_SCREEN_OFF;
    public static final WorkConfigLimitsPerMemoryTrimLevel CONFIG_LIMITS_SCREEN_ON;
    public static final boolean DEBUG = JobSchedulerService.DEBUG;
    public static final int DEFAULT_CONCURRENCY_LIMIT;
    static final long DEFAULT_MAX_WAIT_EJ_MS = 300000;
    static final long DEFAULT_MAX_WAIT_REGULAR_MS = 1800000;
    static final long DEFAULT_MAX_WAIT_UI_MS = 300000;
    public static final int DEFAULT_PKG_CONCURRENCY_LIMIT_REGULAR;
    static final String KEY_ENABLE_MAX_WAIT_TIME_BYPASS = "concurrency_enable_max_wait_time_bypass";
    static final String KEY_MAX_WAIT_UI_MS = "concurrency_max_wait_ui_ms";
    static final String KEY_PKG_CONCURRENCY_LIMIT_EJ = "concurrency_pkg_concurrency_limit_ej";
    static final String KEY_PKG_CONCURRENCY_LIMIT_REGULAR =
            "concurrency_pkg_concurrency_limit_regular";
    static final int MAX_CONCURRENCY_LIMIT = 64;
    static final int NUM_WORK_TYPES = 7;
    public static final Histogram sConcurrencyHistogramLogger;
    public static final JobConcurrencyManager$$ExternalSyntheticLambda2 sDeterminationComparator;
    public final ActivityManagerInternal mActivityManagerInternal;
    public final Context mContext;
    public boolean mCurrentInteractiveState;
    public boolean mEffectiveInteractiveState;
    GracePeriodObserver mGracePeriodObserver;
    public final Handler mHandler;
    public final Injector mInjector;
    public int mLastMemoryTrimLevel;
    public long mLastScreenOffRealtime;
    public long mLastScreenOnRealtime;
    public final Object mLock;
    public long mNextSystemStateRefreshTime;
    public final JobNotificationCoordinator mNotificationCoordinator;
    public PowerManager mPowerManager;
    public final JobSchedulerService mService;
    boolean mShouldRestrictBgUser;
    public final UserManagerInternal mUserManagerInternal;
    public final ArraySet mRecycledChanged = new ArraySet();
    public final ArraySet mRecycledIdle = new ArraySet();
    public final ArrayList mRecycledPreferredUidOnly = new ArrayList();
    public final ArrayList mRecycledStoppable = new ArrayList();
    public final AssignmentInfo mRecycledAssignmentInfo = new AssignmentInfo();
    public final SparseIntArray mRecycledPrivilegedState = new SparseIntArray();
    public final Pools.Pool mContextAssignmentPool = new Pools.SimplePool(96);
    public final List mActiveServices = new ArrayList();
    public final ArraySet mIdleContexts = new ArraySet();
    public int mNumDroppedContexts = 0;
    public final ArraySet mRunningJobs = new ArraySet();
    public final WorkCountTracker mWorkCountTracker = new WorkCountTracker();
    public final Pools.Pool mPkgStatsPool = new Pools.SimplePool(96);
    public final SparseArrayMap mActivePkgStats = new SparseArrayMap();
    public WorkTypeConfig mWorkTypeConfig = CONFIG_LIMITS_SCREEN_OFF.normal;
    public long mScreenOffAdjustmentDelayMs = 30000;
    public int mSteadyStateConcurrencyLimit = DEFAULT_CONCURRENCY_LIMIT;
    public int mPkgConcurrencyLimitEj = 3;
    public int mPkgConcurrencyLimitRegular = DEFAULT_PKG_CONCURRENCY_LIMIT_REGULAR;
    public boolean mMaxWaitTimeBypassEnabled = true;
    public long mMaxWaitUIMs = 300000;
    public long mMaxWaitEjMs = 300000;
    public long mMaxWaitRegularMs = 1800000;
    public final JobConcurrencyManager$$ExternalSyntheticLambda0 mPackageStatsStagingCountClearer =
            new JobConcurrencyManager$$ExternalSyntheticLambda0();
    public final StatLogger mStatLogger =
            new StatLogger(new String[] {"assignJobsToContexts", "refreshSystemState"});
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class: com.android.server.job.JobConcurrencyManager.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    int i;
                    String action = intent.getAction();
                    action.getClass();
                    switch (action) {
                        case "android.intent.action.SCREEN_OFF":
                            JobConcurrencyManager.this.onInteractiveStateChanged(false);
                            return;
                        case "android.intent.action.SCREEN_ON":
                            JobConcurrencyManager.this.onInteractiveStateChanged(true);
                            return;
                        case "android.os.action.DEVICE_IDLE_MODE_CHANGED":
                            PowerManager powerManager = JobConcurrencyManager.this.mPowerManager;
                            if (powerManager == null || !powerManager.isDeviceIdleMode()) {
                                return;
                            }
                            synchronized (JobConcurrencyManager.this.mLock) {
                                JobConcurrencyManager jobConcurrencyManager =
                                        JobConcurrencyManager.this;
                                for (i = 0;
                                        i
                                                < ((ArrayList)
                                                                jobConcurrencyManager
                                                                        .mActiveServices)
                                                        .size();
                                        i++) {
                                    JobServiceContext jobServiceContext =
                                            (JobServiceContext)
                                                    ((ArrayList)
                                                                    jobConcurrencyManager
                                                                            .mActiveServices)
                                                            .get(i);
                                    JobStatus jobStatus = jobServiceContext.mRunningJob;
                                    if (jobStatus != null && !jobStatus.canRunInDoze()) {
                                        jobServiceContext.cancelExecutingJobLocked(
                                                4, 4, "cancelled due to doze");
                                    }
                                }
                                JobConcurrencyManager.m605$$Nest$mstopOvertimeJobsLocked(
                                        JobConcurrencyManager.this, "deep doze");
                            }
                            return;
                        case "android.os.action.POWER_SAVE_MODE_CHANGED":
                            PowerManager powerManager2 = JobConcurrencyManager.this.mPowerManager;
                            if (powerManager2 == null || !powerManager2.isPowerSaveMode()) {
                                return;
                            }
                            synchronized (JobConcurrencyManager.this.mLock) {
                                JobConcurrencyManager.m605$$Nest$mstopOvertimeJobsLocked(
                                        JobConcurrencyManager.this, "battery saver");
                            }
                            return;
                        default:
                            return;
                    }
                }
            };
    public final JobConcurrencyManager$$ExternalSyntheticLambda1 mRampUpForScreenOff =
            new Runnable() { // from class:
                             // com.android.server.job.JobConcurrencyManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    JobConcurrencyManager jobConcurrencyManager = JobConcurrencyManager.this;
                    synchronized (jobConcurrencyManager.mLock) {
                        try {
                            if (jobConcurrencyManager.mEffectiveInteractiveState) {
                                if (jobConcurrencyManager.mLastScreenOnRealtime
                                        > jobConcurrencyManager.mLastScreenOffRealtime) {
                                    return;
                                }
                                JobSchedulerService.sElapsedRealtimeClock.getClass();
                                if (jobConcurrencyManager.mLastScreenOffRealtime
                                                + jobConcurrencyManager.mScreenOffAdjustmentDelayMs
                                        > SystemClock.elapsedRealtime()) {
                                    return;
                                }
                                jobConcurrencyManager.mEffectiveInteractiveState = false;
                                if (JobConcurrencyManager.DEBUG) {
                                    Slog.d("JobScheduler.Concurrency", "Ramping up concurrency");
                                }
                                jobConcurrencyManager.mService.maybeRunPendingJobsLocked();
                            }
                        } finally {
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class AssignmentInfo {
        public long minPreferredUidOnlyWaitingTimeMs;
        public int numRunningEj;
        public int numRunningImmediacyPrivileged;
        public int numRunningReg;
        public int numRunningUi;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class ContextAssignment {
        public JobServiceContext context;
        public JobStatus newJob;
        public String preemptReason;
        public String shouldStopJobReason;
        public long timeUntilStoppableMs;
        public int preferredUid = -1;
        public int preemptReasonCode = 0;
        public int newWorkType = 0;

        public final void clear() {
            this.context = null;
            this.preferredUid = -1;
            this.preemptReason = null;
            this.preemptReasonCode = 0;
            this.timeUntilStoppableMs = 0L;
            this.shouldStopJobReason = null;
            this.newJob = null;
            this.newWorkType = 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class GracePeriodObserver extends UserSwitchObserver {
        int mGracePeriod;
        final SparseLongArray mGracePeriodExpiration = new SparseLongArray();
        public final Object mLock = new Object();
        public int mCurrentUserId =
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class))
                        .getCurrentUserId();
        public final UserManagerInternal mUserManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);

        public GracePeriodObserver(Context context) {
            this.mGracePeriod =
                    Math.max(
                            0,
                            context.getResources()
                                    .getInteger(R.integer.config_metrics_pull_cooldown_millis));
        }

        public boolean isWithinGracePeriodForUser(int i) {
            boolean z;
            synchronized (this.mLock) {
                try {
                    if (i != this.mCurrentUserId) {
                        JobSchedulerService.sElapsedRealtimeClock.getClass();
                        if (SystemClock.elapsedRealtime()
                                >= this.mGracePeriodExpiration.get(i, Long.MAX_VALUE)) {
                            z = false;
                        }
                    }
                    z = true;
                } finally {
                }
            }
            return z;
        }

        public final void onUserSwitchComplete(int i) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime() + this.mGracePeriod;
            synchronized (this.mLock) {
                try {
                    int i2 = this.mCurrentUserId;
                    if (i2 != -10000 && this.mUserManagerInternal.exists(i2)) {
                        this.mGracePeriodExpiration.append(this.mCurrentUserId, elapsedRealtime);
                    }
                    this.mGracePeriodExpiration.delete(i);
                    this.mCurrentUserId = i;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class PackageStats {
        public int numRunningEj;
        public int numRunningRegular;
        public int numStagedEj;
        public int numStagedRegular;
        public String packageName;
        public int userId;

        /* renamed from: -$$Nest$madjustRunningCount, reason: not valid java name */
        public static void m606$$Nest$madjustRunningCount(
                PackageStats packageStats, boolean z, boolean z2) {
            if (z2) {
                packageStats.numRunningEj = Math.max(0, packageStats.numRunningEj + (z ? 1 : -1));
            } else {
                packageStats.numRunningRegular =
                        Math.max(0, packageStats.numRunningRegular + (z ? 1 : -1));
            }
        }

        /* renamed from: -$$Nest$madjustStagedCount, reason: not valid java name */
        public static void m607$$Nest$madjustStagedCount(
                PackageStats packageStats, boolean z, boolean z2) {
            if (z2) {
                packageStats.numStagedEj = Math.max(0, packageStats.numStagedEj + (z ? 1 : -1));
            } else {
                packageStats.numStagedRegular =
                        Math.max(0, packageStats.numStagedRegular + (z ? 1 : -1));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WorkConfigLimitsPerMemoryTrimLevel {
        public final WorkTypeConfig critical;
        public final WorkTypeConfig low;
        public final WorkTypeConfig moderate;
        public final WorkTypeConfig normal;

        public WorkConfigLimitsPerMemoryTrimLevel(
                WorkTypeConfig workTypeConfig,
                WorkTypeConfig workTypeConfig2,
                WorkTypeConfig workTypeConfig3,
                WorkTypeConfig workTypeConfig4) {
            this.normal = workTypeConfig;
            this.moderate = workTypeConfig2;
            this.low = workTypeConfig3;
            this.critical = workTypeConfig4;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class WorkCountTracker {
        public int mConfigMaxTotal;
        public final SparseIntArray mConfigNumReservedSlots = new SparseIntArray(7);
        public final SparseIntArray mConfigAbsoluteMaxSlots = new SparseIntArray(7);
        public final SparseIntArray mRecycledReserved = new SparseIntArray(7);
        public final SparseIntArray mNumActuallyReservedSlots = new SparseIntArray(7);
        public final SparseIntArray mNumPendingJobs = new SparseIntArray(7);
        public final SparseIntArray mNumRunningJobs = new SparseIntArray(7);
        public final SparseIntArray mNumStartingJobs = new SparseIntArray(7);
        public int mNumUnspecializedRemaining = 0;

        public final int adjustPendingJobCount(int i, boolean z) {
            int i2 = z ? 1 : -1;
            int i3 = 0;
            for (int i4 = 1; i4 <= i; i4 <<= 1) {
                if ((i & i4) == i4) {
                    SparseIntArray sparseIntArray = this.mNumPendingJobs;
                    sparseIntArray.put(i4, sparseIntArray.get(i4) + i2);
                    i3++;
                }
            }
            return i3;
        }

        public final int canJobStart(int i) {
            for (int i2 = 1; i2 <= i; i2 <<= 1) {
                if ((i & i2) == i2) {
                    if (this.mNumStartingJobs.get(i2) + this.mNumRunningJobs.get(i2)
                            < Math.min(
                                    this.mConfigAbsoluteMaxSlots.get(i2),
                                    this.mNumActuallyReservedSlots.get(i2)
                                            + this.mNumUnspecializedRemaining)) {
                        return i2;
                    }
                }
            }
            return 0;
        }

        public final int canJobStart(int i, int i2) {
            boolean z;
            int i3 = this.mNumRunningJobs.get(i2);
            if (i2 == 0 || i3 <= 0) {
                z = false;
            } else {
                this.mNumRunningJobs.put(i2, i3 - 1);
                this.mNumUnspecializedRemaining++;
                z = true;
            }
            int canJobStart = canJobStart(i);
            if (z) {
                this.mNumRunningJobs.put(i2, i3);
                this.mNumUnspecializedRemaining--;
            }
            return canJobStart;
        }

        public final int getPendingJobCount(int i) {
            return this.mNumPendingJobs.get(i, 0);
        }

        public final void maybeAdjustReservations(int i) {
            int max =
                    Math.max(
                            this.mConfigNumReservedSlots.get(i),
                            this.mNumPendingJobs.get(i)
                                    + this.mNumStartingJobs.get(i)
                                    + this.mNumRunningJobs.get(i));
            if (max < this.mNumActuallyReservedSlots.get(i)) {
                this.mNumActuallyReservedSlots.put(i, max);
                int i2 = 0;
                for (int i3 = 0; i3 < this.mNumActuallyReservedSlots.size(); i3++) {
                    int keyAt = this.mNumActuallyReservedSlots.keyAt(i3);
                    if (i2 == 0 || keyAt < i2) {
                        int i4 =
                                this.mNumPendingJobs.get(keyAt)
                                        + this.mNumStartingJobs.get(keyAt)
                                        + this.mNumRunningJobs.get(keyAt);
                        if (this.mNumActuallyReservedSlots.valueAt(i3)
                                        < this.mConfigAbsoluteMaxSlots.get(keyAt)
                                && i4 > this.mNumActuallyReservedSlots.valueAt(i3)) {
                            i2 = keyAt;
                        }
                    }
                }
                if (i2 == 0) {
                    this.mNumUnspecializedRemaining++;
                } else {
                    SparseIntArray sparseIntArray = this.mNumActuallyReservedSlots;
                    sparseIntArray.put(i2, sparseIntArray.get(i2) + 1);
                }
            }
        }

        public final void stageJob(int i, int i2) {
            int i3 = this.mNumStartingJobs.get(i) + 1;
            this.mNumStartingJobs.put(i, i3);
            if (adjustPendingJobCount(i2, false) > 1) {
                for (int i4 = 1; i4 <= i2; i4 <<= 1) {
                    if ((i4 & i2) == i4) {
                        maybeAdjustReservations(i4);
                    }
                }
            }
            if (this.mNumRunningJobs.get(i) + i3 > this.mNumActuallyReservedSlots.get(i)) {
                this.mNumUnspecializedRemaining--;
            }
        }

        public final String toString() {
            return "Config={tot="
                    + this.mConfigMaxTotal
                    + " mins="
                    + this.mConfigNumReservedSlots
                    + " maxs="
                    + this.mConfigAbsoluteMaxSlots
                    + "}, act res="
                    + this.mNumActuallyReservedSlots
                    + ", Pending="
                    + this.mNumPendingJobs
                    + ", Running="
                    + this.mNumRunningJobs
                    + ", Staged="
                    + this.mNumStartingJobs
                    + ", # unspecialized remaining="
                    + this.mNumUnspecializedRemaining;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class WorkTypeConfig {
        static final String KEY_PREFIX_MAX_RATIO = "concurrency_max_ratio_";
        static final String KEY_PREFIX_MAX_TOTAL = "concurrency_max_total_";
        static final String KEY_PREFIX_MIN_RATIO = "concurrency_min_ratio_";
        public final String mConfigIdentifier;
        public final int mDefaultMaxTotal;
        public int mMaxTotal;
        public final SparseIntArray mMinReservedSlots = new SparseIntArray(7);
        public final SparseIntArray mMaxAllowedSlots = new SparseIntArray(7);
        public final SparseIntArray mDefaultMinReservedSlotsRatio = new SparseIntArray(7);
        public final SparseIntArray mDefaultMaxAllowedSlotsRatio = new SparseIntArray(7);

        public WorkTypeConfig(String str, int i, int i2, List list, List list2) {
            this.mConfigIdentifier = str;
            int min = Math.min(i2, i);
            this.mMaxTotal = min;
            this.mDefaultMaxTotal = min;
            int i3 = 0;
            for (int size = list.size() - 1; size >= 0; size--) {
                float floatValue = ((Float) ((Pair) list.get(size)).second).floatValue();
                int intValue = ((Integer) ((Pair) list.get(size)).first).intValue();
                if (floatValue < FullScreenMagnificationGestureHandler.MAX_SCALE
                        || 1.0f <= floatValue) {
                    throw new IllegalArgumentException(
                            "Invalid default min ratio: wt="
                                    + intValue
                                    + " minRatio="
                                    + floatValue);
                }
                this.mDefaultMinReservedSlotsRatio.put(
                        intValue, Float.floatToRawIntBits(floatValue));
                i3 = (int) ((this.mMaxTotal * floatValue) + i3);
            }
            int i4 = this.mDefaultMaxTotal;
            if (i4 < 0 || i3 > i4) {
                throw new IllegalArgumentException(
                        "Invalid default config: t=" + i2 + " min=" + list + " max=" + list2);
            }
            for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                float floatValue2 = ((Float) ((Pair) list2.get(size2)).second).floatValue();
                int intValue2 = ((Integer) ((Pair) list2.get(size2)).first).intValue();
                if (floatValue2
                                < Float.intBitsToFloat(
                                        this.mDefaultMinReservedSlotsRatio.get(intValue2, 0))
                        || floatValue2 <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    throw new IllegalArgumentException(
                            "Invalid default config: t=" + i2 + " min=" + list + " max=" + list2);
                }
                this.mDefaultMaxAllowedSlotsRatio.put(
                        intValue2, Float.floatToRawIntBits(floatValue2));
            }
            update(new DeviceConfig.Properties.Builder("jobscheduler").build(), i);
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            StringBuilder sb = new StringBuilder(KEY_PREFIX_MAX_TOTAL);
            String str = this.mConfigIdentifier;
            sb.append(str);
            indentingPrintWriter.print(sb.toString(), Integer.valueOf(this.mMaxTotal)).println();
            indentingPrintWriter
                    .print(
                            "concurrency_min_ratio_top_" + str,
                            Integer.valueOf(this.mMinReservedSlots.get(1)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_max_ratio_top_" + str,
                            Integer.valueOf(this.mMaxAllowedSlots.get(1)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_min_ratio_fgs_" + str,
                            Integer.valueOf(this.mMinReservedSlots.get(2)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_max_ratio_fgs_" + str,
                            Integer.valueOf(this.mMaxAllowedSlots.get(2)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_min_ratio_ui_" + str,
                            Integer.valueOf(this.mMinReservedSlots.get(4)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_max_ratio_ui_" + str,
                            Integer.valueOf(this.mMaxAllowedSlots.get(4)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_min_ratio_ej_" + str,
                            Integer.valueOf(this.mMinReservedSlots.get(8)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_max_ratio_ej_" + str,
                            Integer.valueOf(this.mMaxAllowedSlots.get(8)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_min_ratio_bg_" + str,
                            Integer.valueOf(this.mMinReservedSlots.get(16)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_max_ratio_bg_" + str,
                            Integer.valueOf(this.mMaxAllowedSlots.get(16)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_min_ratio_bguser_" + str,
                            Integer.valueOf(this.mMinReservedSlots.get(32)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_max_ratio_bguser_" + str,
                            Integer.valueOf(this.mMaxAllowedSlots.get(32)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_min_ratio_bguser_" + str,
                            Integer.valueOf(this.mMinReservedSlots.get(64)))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_max_ratio_bguser_" + str,
                            Integer.valueOf(this.mMaxAllowedSlots.get(64)))
                    .println();
        }

        public final int getMaxValue(
                DeviceConfig.Properties properties, String str, int i, int i2) {
            return Math.max(
                    1,
                    (int)
                            (this.mMaxTotal
                                    * Math.min(
                                            1.0f,
                                            properties.getFloat(
                                                    str,
                                                    Float.intBitsToFloat(
                                                            this.mDefaultMaxAllowedSlotsRatio.get(
                                                                    i, i2))))));
        }

        public final int getMinValue(
                DeviceConfig.Properties properties, String str, int i, int i2, int i3) {
            return Math.max(
                    i2,
                    Math.min(
                            i3,
                            (int)
                                    (this.mMaxTotal
                                            * Math.min(
                                                    1.0f,
                                                    properties.getFloat(
                                                            str,
                                                            Float.intBitsToFloat(
                                                                    this
                                                                            .mDefaultMinReservedSlotsRatio
                                                                            .get(i)))))));
        }

        public final void update(DeviceConfig.Properties properties, int i) {
            StringBuilder sb = new StringBuilder(KEY_PREFIX_MAX_TOTAL);
            String str = this.mConfigIdentifier;
            sb.append(str);
            this.mMaxTotal =
                    Math.max(
                            1,
                            Math.min(i, properties.getInt(sb.toString(), this.mDefaultMaxTotal)));
            int floatToIntBits = Float.floatToIntBits(1.0f);
            this.mMaxAllowedSlots.clear();
            int maxValue =
                    getMaxValue(properties, "concurrency_max_ratio_top_" + str, 1, floatToIntBits);
            this.mMaxAllowedSlots.put(1, maxValue);
            int maxValue2 =
                    getMaxValue(properties, "concurrency_max_ratio_fgs_" + str, 2, floatToIntBits);
            this.mMaxAllowedSlots.put(2, maxValue2);
            int maxValue3 =
                    getMaxValue(properties, "concurrency_max_ratio_ui_" + str, 4, floatToIntBits);
            this.mMaxAllowedSlots.put(4, maxValue3);
            int maxValue4 =
                    getMaxValue(properties, "concurrency_max_ratio_ej_" + str, 8, floatToIntBits);
            this.mMaxAllowedSlots.put(8, maxValue4);
            int maxValue5 =
                    getMaxValue(properties, "concurrency_max_ratio_bg_" + str, 16, floatToIntBits);
            this.mMaxAllowedSlots.put(16, maxValue5);
            int maxValue6 =
                    getMaxValue(
                            properties,
                            "concurrency_max_ratio_bguser_important_" + str,
                            32,
                            floatToIntBits);
            this.mMaxAllowedSlots.put(32, maxValue6);
            int maxValue7 =
                    getMaxValue(
                            properties, "concurrency_max_ratio_bguser_" + str, 64, floatToIntBits);
            this.mMaxAllowedSlots.put(64, maxValue7);
            int i2 = this.mMaxTotal;
            this.mMinReservedSlots.clear();
            int minValue =
                    getMinValue(
                            properties,
                            "concurrency_min_ratio_top_" + str,
                            1,
                            1,
                            Math.min(maxValue, this.mMaxTotal));
            this.mMinReservedSlots.put(1, minValue);
            int i3 = i2 - minValue;
            int minValue2 =
                    getMinValue(
                            properties,
                            "concurrency_min_ratio_fgs_" + str,
                            2,
                            0,
                            Math.min(maxValue2, i3));
            this.mMinReservedSlots.put(2, minValue2);
            int i4 = i3 - minValue2;
            int minValue3 =
                    getMinValue(
                            properties,
                            "concurrency_min_ratio_ui_" + str,
                            4,
                            0,
                            Math.min(maxValue3, i4));
            this.mMinReservedSlots.put(4, minValue3);
            int i5 = i4 - minValue3;
            int minValue4 =
                    getMinValue(
                            properties,
                            "concurrency_min_ratio_ej_" + str,
                            8,
                            0,
                            Math.min(maxValue4, i5));
            this.mMinReservedSlots.put(8, minValue4);
            int i6 = i5 - minValue4;
            int minValue5 =
                    getMinValue(
                            properties,
                            "concurrency_min_ratio_bg_" + str,
                            16,
                            0,
                            Math.min(maxValue5, i6));
            this.mMinReservedSlots.put(16, minValue5);
            int i7 = i6 - minValue5;
            int minValue6 =
                    getMinValue(
                            properties,
                            "concurrency_min_ratio_bguser_important_" + str,
                            32,
                            0,
                            Math.min(maxValue6, i7));
            this.mMinReservedSlots.put(32, minValue6);
            this.mMinReservedSlots.put(
                    64,
                    getMinValue(
                            properties,
                            "concurrency_min_ratio_bguser_" + str,
                            64,
                            0,
                            Math.min(maxValue7, i7 - minValue6)));
        }
    }

    /* renamed from: -$$Nest$mstopOvertimeJobsLocked, reason: not valid java name */
    public static void m605$$Nest$mstopOvertimeJobsLocked(
            JobConcurrencyManager jobConcurrencyManager, String str) {
        for (int i = 0; i < ((ArrayList) jobConcurrencyManager.mActiveServices).size(); i++) {
            JobServiceContext jobServiceContext =
                    (JobServiceContext) ((ArrayList) jobConcurrencyManager.mActiveServices).get(i);
            if (jobServiceContext.mRunningJob != null
                    && !jobServiceContext.isWithinExecutionGuaranteeTime()) {
                jobServiceContext.cancelExecutingJobLocked(4, 3, str);
            }
        }
    }

    static {
        if (ActivityManager.isLowRamDeviceStatic()) {
            DEFAULT_CONCURRENCY_LIMIT = 8;
        } else {
            long totalSize = new MemInfoReader().getTotalSize();
            DataUnit dataUnit = DataUnit.GIGABYTES;
            if (totalSize <= dataUnit.toBytes(6L)) {
                DEFAULT_CONCURRENCY_LIMIT = 16;
            } else if (totalSize <= dataUnit.toBytes(8L)) {
                DEFAULT_CONCURRENCY_LIMIT = 20;
            } else if (totalSize <= dataUnit.toBytes(12L)) {
                DEFAULT_CONCURRENCY_LIMIT = 32;
            } else {
                DEFAULT_CONCURRENCY_LIMIT = 40;
            }
        }
        int i = DEFAULT_CONCURRENCY_LIMIT;
        int i2 = i / 2;
        DEFAULT_PKG_CONCURRENCY_LIMIT_REGULAR = i2;
        int i3 = (i * 4) / 10;
        CONFIG_LIMITS_SCREEN_ON =
                new WorkConfigLimitsPerMemoryTrimLevel(
                        new WorkTypeConfig(
                                "screen_on_normal",
                                i,
                                (i * 3) / 4,
                                List.of(
                                        Pair.create(1, Float.valueOf(0.4f)),
                                        Pair.create(2, Float.valueOf(0.2f)),
                                        Pair.create(4, Float.valueOf(0.1f)),
                                        Pair.create(8, Float.valueOf(0.1f)),
                                        Pair.create(16, Float.valueOf(0.05f)),
                                        Pair.create(32, Float.valueOf(0.05f))),
                                List.of(
                                        Pair.create(16, Float.valueOf(0.5f)),
                                        Pair.create(32, Float.valueOf(0.25f)),
                                        Pair.create(64, Float.valueOf(0.2f)))),
                        new WorkTypeConfig(
                                "screen_on_moderate",
                                i,
                                i2,
                                List.of(
                                        Pair.create(1, Float.valueOf(0.4f)),
                                        Pair.create(2, Float.valueOf(0.1f)),
                                        Pair.create(4, Float.valueOf(0.1f)),
                                        Pair.create(8, Float.valueOf(0.1f)),
                                        Pair.create(16, Float.valueOf(0.1f)),
                                        Pair.create(32, Float.valueOf(0.1f))),
                                List.of(
                                        Pair.create(16, Float.valueOf(0.4f)),
                                        Pair.create(32, Float.valueOf(0.1f)),
                                        Pair.create(64, Float.valueOf(0.1f)))),
                        new WorkTypeConfig(
                                "screen_on_low",
                                i,
                                i3,
                                List.of(
                                        Pair.create(1, Float.valueOf(0.6f)),
                                        Pair.create(2, Float.valueOf(0.1f)),
                                        Pair.create(4, Float.valueOf(0.1f)),
                                        Pair.create(8, Float.valueOf(0.1f))),
                                List.of(
                                        Pair.create(16, Float.valueOf(0.33333334f)),
                                        Pair.create(32, Float.valueOf(0.16666667f)),
                                        Pair.create(64, Float.valueOf(0.16666667f)))),
                        new WorkTypeConfig(
                                "screen_on_critical",
                                i,
                                i3,
                                List.of(
                                        Pair.create(1, Float.valueOf(0.7f)),
                                        Pair.create(2, Float.valueOf(0.1f)),
                                        Pair.create(4, Float.valueOf(0.1f)),
                                        Pair.create(8, Float.valueOf(0.05f))),
                                List.of(
                                        Pair.create(16, Float.valueOf(0.16666667f)),
                                        Pair.create(32, Float.valueOf(0.16666667f)),
                                        Pair.create(64, Float.valueOf(0.16666667f)))));
        CONFIG_LIMITS_SCREEN_OFF =
                new WorkConfigLimitsPerMemoryTrimLevel(
                        new WorkTypeConfig(
                                "screen_off_normal",
                                i,
                                i,
                                List.of(
                                        Pair.create(1, Float.valueOf(0.3f)),
                                        Pair.create(2, Float.valueOf(0.2f)),
                                        Pair.create(4, Float.valueOf(0.2f)),
                                        Pair.create(8, Float.valueOf(0.15f)),
                                        Pair.create(16, Float.valueOf(0.1f)),
                                        Pair.create(32, Float.valueOf(0.05f))),
                                List.of(
                                        Pair.create(16, Float.valueOf(0.6f)),
                                        Pair.create(32, Float.valueOf(0.2f)),
                                        Pair.create(64, Float.valueOf(0.2f)))),
                        new WorkTypeConfig(
                                "screen_off_moderate",
                                i,
                                (i * 9) / 10,
                                List.of(
                                        Pair.create(1, Float.valueOf(0.3f)),
                                        Pair.create(2, Float.valueOf(0.2f)),
                                        Pair.create(4, Float.valueOf(0.2f)),
                                        Pair.create(8, Float.valueOf(0.15f)),
                                        Pair.create(16, Float.valueOf(0.1f)),
                                        Pair.create(32, Float.valueOf(0.05f))),
                                List.of(
                                        Pair.create(16, Float.valueOf(0.5f)),
                                        Pair.create(32, Float.valueOf(0.1f)),
                                        Pair.create(64, Float.valueOf(0.1f)))),
                        new WorkTypeConfig(
                                "screen_off_low",
                                i,
                                (i * 6) / 10,
                                List.of(
                                        Pair.create(1, Float.valueOf(0.3f)),
                                        Pair.create(2, Float.valueOf(0.15f)),
                                        Pair.create(4, Float.valueOf(0.15f)),
                                        Pair.create(8, Float.valueOf(0.1f)),
                                        Pair.create(16, Float.valueOf(0.05f)),
                                        Pair.create(32, Float.valueOf(0.05f))),
                                List.of(
                                        Pair.create(16, Float.valueOf(0.25f)),
                                        Pair.create(32, Float.valueOf(0.1f)),
                                        Pair.create(64, Float.valueOf(0.1f)))),
                        new WorkTypeConfig(
                                "screen_off_critical",
                                i,
                                i3,
                                List.of(
                                        Pair.create(1, Float.valueOf(0.3f)),
                                        Pair.create(2, Float.valueOf(0.1f)),
                                        Pair.create(4, Float.valueOf(0.1f)),
                                        Pair.create(8, Float.valueOf(0.05f))),
                                List.of(
                                        Pair.create(16, Float.valueOf(0.1f)),
                                        Pair.create(32, Float.valueOf(0.1f)),
                                        Pair.create(64, Float.valueOf(0.1f)))));
        sDeterminationComparator = new JobConcurrencyManager$$ExternalSyntheticLambda2();
        sConcurrencyHistogramLogger =
                new Histogram(
                        "job_scheduler.value_hist_job_concurrency",
                        new Histogram.UniformOptions(
                                100, FullScreenMagnificationGestureHandler.MAX_SCALE, 99.0f));
    }

    /* JADX WARN: Type inference failed for: r0v25, types: [com.android.server.job.JobConcurrencyManager$1] */
    /* JADX WARN: Type inference failed for: r0v26, types: [com.android.server.job.JobConcurrencyManager$$ExternalSyntheticLambda1] */
    public JobConcurrencyManager(JobSchedulerService jobSchedulerService, Injector injector) {
        this.mService = jobSchedulerService;
        this.mLock = jobSchedulerService.mLock;
        Context context = jobSchedulerService.getContext();
        this.mContext = context;
        this.mInjector = injector;
        this.mNotificationCoordinator = new JobNotificationCoordinator();
        this.mActivityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mUserManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        this.mHandler = AppSchedulingModuleThread.getHandler();
        this.mGracePeriodObserver = new GracePeriodObserver(context);
        this.mShouldRestrictBgUser =
                context.getResources()
                        .getBoolean(
                                R.bool
                                        .config_letterboxIsPolicyForIgnoringRequestedOrientationEnabled);
    }

    public static String printAssignments(String str, Collection... collectionArr) {
        StringBuilder sb = new StringBuilder(str.concat(": "));
        for (int i = 0; i < collectionArr.length; i++) {
            int i2 = 0;
            for (ContextAssignment contextAssignment : collectionArr[i]) {
                JobStatus jobStatus = contextAssignment.newJob;
                if (jobStatus == null) {
                    jobStatus = contextAssignment.context.mRunningJob;
                }
                if (i > 0 || i2 > 0) {
                    sb.append(" ");
                }
                sb.append("(");
                sb.append(contextAssignment.context.hashCode());
                sb.append("=");
                if (jobStatus == null) {
                    sb.append("nothing");
                } else {
                    String str2 = jobStatus.mNamespace;
                    if (str2 != null) {
                        sb.append(str2);
                        sb.append(":");
                    }
                    sb.append(jobStatus.job.getId());
                    sb.append("/");
                    sb.append(jobStatus.callingUid);
                }
                sb.append(")");
                i2++;
            }
        }
        return sb.toString();
    }

    public static String workTypeToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 4
                                        ? i != 8
                                                ? i != 16
                                                        ? i != 32
                                                                ? i != 64
                                                                        ? BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                .m(i, "WORK(", ")")
                                                                        : "BGUSER"
                                                                : "BGUSER_IMPORTANT"
                                                        : "BG"
                                                : "EJ"
                                        : "UI"
                                : "FGS"
                        : "TOP"
                : "NONE";
    }

    public void addRunningJobForTesting(JobStatus jobStatus) {
        JobServiceContext createNewJobServiceContext;
        this.mRunningJobs.add(jobStatus);
        PackageStats.m606$$Nest$madjustRunningCount(
                getPackageStatsForTesting(jobStatus.sourceUserId, jobStatus.sourcePackageName),
                true,
                jobStatus.shouldTreatAsExpeditedJob());
        if (this.mIdleContexts.size() > 0) {
            ArraySet arraySet = this.mIdleContexts;
            createNewJobServiceContext = (JobServiceContext) arraySet.removeAt(arraySet.size() - 1);
        } else {
            createNewJobServiceContext = createNewJobServiceContext();
        }
        createNewJobServiceContext.executeRunnableJob(
                jobStatus, this.mWorkCountTracker.canJobStart(getJobWorkTypes(jobStatus)));
        ((ArrayList) this.mActiveServices).add(createNewJobServiceContext);
    }

    public final void assignJobsToContextsLocked() {
        int i;
        StatLogger statLogger = this.mStatLogger;
        long time = statLogger.getTime();
        JobSchedulerService jobSchedulerService = this.mService;
        boolean z = DEBUG;
        if (z) {
            StringBuilder sb = new StringBuilder("Pending queue: ");
            PendingJobQueue pendingJobQueue = jobSchedulerService.mPendingJobQueue;
            pendingJobQueue.mNeedToResetIterators = true;
            while (true) {
                JobStatus next = pendingJobQueue.next();
                if (next == null) {
                    break;
                }
                sb.append("({");
                sb.append(next.mNamespace);
                sb.append("} ");
                sb.append(next.job.getId());
                sb.append(", ");
                sb.append(next.callingUid);
                sb.append(") ");
            }
            Slog.d("JobScheduler.Concurrency", sb.toString());
        }
        if (jobSchedulerService.mPendingJobQueue.mSize == 0) {
            i = 0;
        } else {
            ArraySet arraySet = this.mRecycledIdle;
            ArrayList arrayList = this.mRecycledPreferredUidOnly;
            ArrayList arrayList2 = this.mRecycledStoppable;
            AssignmentInfo assignmentInfo = this.mRecycledAssignmentInfo;
            prepareForAssignmentDeterminationLocked(
                    arraySet, arrayList, arrayList2, assignmentInfo);
            if (z) {
                Slog.d(
                        "JobScheduler.Concurrency",
                        printAssignments(
                                "running jobs initial",
                                this.mRecycledStoppable,
                                this.mRecycledPreferredUidOnly));
            }
            determineAssignmentsLocked(
                    this.mRecycledChanged,
                    this.mRecycledIdle,
                    this.mRecycledPreferredUidOnly,
                    this.mRecycledStoppable,
                    this.mRecycledAssignmentInfo);
            WorkCountTracker workCountTracker = this.mWorkCountTracker;
            if (z) {
                Slog.d(
                        "JobScheduler.Concurrency",
                        printAssignments(
                                "running jobs final",
                                this.mRecycledStoppable,
                                this.mRecycledPreferredUidOnly,
                                this.mRecycledChanged));
                Slog.d("JobScheduler.Concurrency", "work count results: " + workCountTracker);
            }
            ArraySet arraySet2 = this.mRecycledChanged;
            for (int size = arraySet2.size() - 1; size >= 0; size--) {
                ContextAssignment contextAssignment = (ContextAssignment) arraySet2.valueAt(size);
                JobStatus jobStatus = contextAssignment.context.mRunningJob;
                if (jobStatus != null) {
                    if (z) {
                        Slog.d("JobScheduler.Concurrency", "preempting job: " + jobStatus);
                    }
                    contextAssignment.context.cancelExecutingJobLocked(
                            contextAssignment.preemptReasonCode,
                            2,
                            contextAssignment.preemptReason);
                } else {
                    JobStatus jobStatus2 = contextAssignment.newJob;
                    if (z) {
                        Slog.d(
                                "JobScheduler.Concurrency",
                                "About to run job on context "
                                        + contextAssignment.context.hashCode()
                                        + ", job: "
                                        + jobStatus2);
                    }
                    startJobLocked(
                            contextAssignment.newWorkType, contextAssignment.context, jobStatus2);
                }
                contextAssignment.clear();
                this.mContextAssignmentPool.release(contextAssignment);
            }
            ArraySet arraySet3 = this.mRecycledChanged;
            ArraySet arraySet4 = this.mRecycledIdle;
            ArrayList arrayList3 = this.mRecycledPreferredUidOnly;
            ArrayList arrayList4 = this.mRecycledStoppable;
            SparseIntArray sparseIntArray = this.mRecycledPrivilegedState;
            for (int size2 = arrayList4.size() - 1; size2 >= 0; size2--) {
                ContextAssignment contextAssignment2 = (ContextAssignment) arrayList4.get(size2);
                contextAssignment2.clear();
                this.mContextAssignmentPool.release(contextAssignment2);
            }
            for (int size3 = arrayList3.size() - 1; size3 >= 0; size3--) {
                ContextAssignment contextAssignment3 = (ContextAssignment) arrayList3.get(size3);
                contextAssignment3.clear();
                this.mContextAssignmentPool.release(contextAssignment3);
            }
            for (int size4 = arraySet4.size() - 1; size4 >= 0; size4--) {
                ContextAssignment contextAssignment4 = (ContextAssignment) arraySet4.valueAt(size4);
                this.mIdleContexts.add(contextAssignment4.context);
                contextAssignment4.clear();
                this.mContextAssignmentPool.release(contextAssignment4);
            }
            arraySet3.clear();
            arraySet4.clear();
            arrayList4.clear();
            arrayList3.clear();
            assignmentInfo.minPreferredUidOnlyWaitingTimeMs = 0L;
            assignmentInfo.numRunningImmediacyPrivileged = 0;
            assignmentInfo.numRunningUi = 0;
            assignmentInfo.numRunningEj = 0;
            assignmentInfo.numRunningReg = 0;
            sparseIntArray.clear();
            workCountTracker.mNumStartingJobs.clear();
            this.mActivePkgStats.forEach(this.mPackageStatsStagingCountClearer);
            noteConcurrency(true);
            i = 0;
        }
        statLogger.logDurationStat(i, time);
    }

    public final JobServiceContext createNewJobServiceContext() {
        IBatteryStats asInterface =
                IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
        JobPackageTracker jobPackageTracker = this.mService.mJobPackageTracker;
        Looper looper = AppSchedulingModuleThread.get().getLooper();
        this.mInjector.getClass();
        return new JobServiceContext(
                this.mService,
                this,
                this.mNotificationCoordinator,
                asInterface,
                jobPackageTracker,
                looper);
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x0145, code lost:

       r2 = true;
    */
    /* JADX WARN: Removed duplicated region for block: B:117:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0369  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0361  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0204  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void determineAssignmentsLocked(
            android.util.ArraySet r34,
            android.util.ArraySet r35,
            java.util.List r36,
            java.util.List r37,
            com.android.server.job.JobConcurrencyManager.AssignmentInfo r38) {
        /*
            Method dump skipped, instructions count: 902
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.job.JobConcurrencyManager.determineAssignmentsLocked(android.util.ArraySet,"
                    + " android.util.ArraySet, java.util.List, java.util.List,"
                    + " com.android.server.job.JobConcurrencyManager$AssignmentInfo):void");
    }

    public final void dumpContextInfoLocked(
            IndentingPrintWriter indentingPrintWriter,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5,
            long j,
            long j2) {
        indentingPrintWriter.println("Active jobs:");
        indentingPrintWriter.increaseIndent();
        if (((ArrayList) this.mActiveServices).size() == 0) {
            indentingPrintWriter.println("N/A");
        }
        for (int i = 0; i < ((ArrayList) this.mActiveServices).size(); i++) {
            JobServiceContext jobServiceContext =
                    (JobServiceContext) ((ArrayList) this.mActiveServices).get(i);
            JobStatus jobStatus = jobServiceContext.mRunningJob;
            if (jobStatus == null
                    || jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                indentingPrintWriter.print("Slot #");
                indentingPrintWriter.print(i);
                indentingPrintWriter.print("(ID=");
                indentingPrintWriter.print(jobServiceContext.hashCode());
                indentingPrintWriter.print("): ");
                jobServiceContext.dumpLocked(indentingPrintWriter, j);
                if (jobStatus != null) {
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.increaseIndent();
                    jobStatus.dump(indentingPrintWriter, false, j);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.print("Evaluated bias: ");
                    indentingPrintWriter.println(
                            JobInfo.getBiasString(jobStatus.lastEvaluatedBias));
                    indentingPrintWriter.print("Active at ");
                    TimeUtils.formatDuration(jobStatus.madeActive - j2, indentingPrintWriter);
                    indentingPrintWriter.print(", pending for ");
                    TimeUtils.formatDuration(
                            jobStatus.madeActive - jobStatus.madePending, indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.print("Idle contexts (");
        indentingPrintWriter.print(this.mIdleContexts.size());
        indentingPrintWriter.println("):");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < this.mIdleContexts.size(); i2++) {
            JobServiceContext jobServiceContext2 =
                    (JobServiceContext) this.mIdleContexts.valueAt(i2);
            indentingPrintWriter.print("ID=");
            indentingPrintWriter.print(jobServiceContext2.hashCode());
            indentingPrintWriter.print(": ");
            jobServiceContext2.dumpLocked(indentingPrintWriter, j);
        }
        indentingPrintWriter.decreaseIndent();
        if (this.mNumDroppedContexts > 0) {
            indentingPrintWriter.println();
            indentingPrintWriter.print("Dropped ");
            indentingPrintWriter.print(this.mNumDroppedContexts);
            indentingPrintWriter.println(" contexts");
        }
    }

    public final void dumpLocked(final IndentingPrintWriter indentingPrintWriter, long j, long j2) {
        indentingPrintWriter.println("Concurrency:");
        indentingPrintWriter.increaseIndent();
        try {
            indentingPrintWriter.println("Configuration:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter
                    .print("concurrency_limit", Integer.valueOf(this.mSteadyStateConcurrencyLimit))
                    .println();
            indentingPrintWriter
                    .print(
                            "concurrency_screen_off_adjustment_delay_ms",
                            Long.valueOf(this.mScreenOffAdjustmentDelayMs))
                    .println();
            indentingPrintWriter
                    .print(
                            KEY_PKG_CONCURRENCY_LIMIT_EJ,
                            Integer.valueOf(this.mPkgConcurrencyLimitEj))
                    .println();
            indentingPrintWriter
                    .print(
                            KEY_PKG_CONCURRENCY_LIMIT_REGULAR,
                            Integer.valueOf(this.mPkgConcurrencyLimitRegular))
                    .println();
            indentingPrintWriter
                    .print(
                            KEY_ENABLE_MAX_WAIT_TIME_BYPASS,
                            Boolean.valueOf(this.mMaxWaitTimeBypassEnabled))
                    .println();
            indentingPrintWriter
                    .print(KEY_MAX_WAIT_UI_MS, Long.valueOf(this.mMaxWaitUIMs))
                    .println();
            indentingPrintWriter
                    .print("concurrency_max_wait_ej_ms", Long.valueOf(this.mMaxWaitEjMs))
                    .println();
            indentingPrintWriter
                    .print("concurrency_max_wait_regular_ms", Long.valueOf(this.mMaxWaitRegularMs))
                    .println();
            indentingPrintWriter.println();
            WorkConfigLimitsPerMemoryTrimLevel workConfigLimitsPerMemoryTrimLevel =
                    CONFIG_LIMITS_SCREEN_ON;
            workConfigLimitsPerMemoryTrimLevel.normal.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            workConfigLimitsPerMemoryTrimLevel.moderate.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            workConfigLimitsPerMemoryTrimLevel.low.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            workConfigLimitsPerMemoryTrimLevel.critical.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            WorkConfigLimitsPerMemoryTrimLevel workConfigLimitsPerMemoryTrimLevel2 =
                    CONFIG_LIMITS_SCREEN_OFF;
            workConfigLimitsPerMemoryTrimLevel2.normal.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            workConfigLimitsPerMemoryTrimLevel2.moderate.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            workConfigLimitsPerMemoryTrimLevel2.low.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            workConfigLimitsPerMemoryTrimLevel2.critical.dump(indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.print("Screen state: current ");
            indentingPrintWriter.print(this.mCurrentInteractiveState ? "ON" : "OFF");
            indentingPrintWriter.print("  effective ");
            indentingPrintWriter.print(this.mEffectiveInteractiveState ? "ON" : "OFF");
            indentingPrintWriter.println();
            indentingPrintWriter.print("Last screen ON: ");
            long j3 = j - j2;
            TimeUtils.dumpTimeWithDelta(indentingPrintWriter, this.mLastScreenOnRealtime + j3, j);
            indentingPrintWriter.println();
            indentingPrintWriter.print("Last screen OFF: ");
            TimeUtils.dumpTimeWithDelta(indentingPrintWriter, j3 + this.mLastScreenOffRealtime, j);
            indentingPrintWriter.println();
            indentingPrintWriter.println();
            indentingPrintWriter.print("Current work counts: ");
            indentingPrintWriter.println(this.mWorkCountTracker);
            indentingPrintWriter.println();
            indentingPrintWriter.print("mLastMemoryTrimLevel: ");
            indentingPrintWriter.println(this.mLastMemoryTrimLevel);
            indentingPrintWriter.println();
            indentingPrintWriter.println("Active Package stats:");
            indentingPrintWriter.increaseIndent();
            this.mActivePkgStats.forEach(
                    new Consumer() { // from class:
                                     // com.android.server.job.JobConcurrencyManager$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                            JobConcurrencyManager.PackageStats packageStats =
                                    (JobConcurrencyManager.PackageStats) obj;
                            packageStats.getClass();
                            indentingPrintWriter2.print("PackageStats{");
                            indentingPrintWriter2.print(packageStats.userId);
                            indentingPrintWriter2.print(
                                    PackageManagerShellCommandDataLoader.STDIN_PATH);
                            indentingPrintWriter2.print(packageStats.packageName);
                            indentingPrintWriter2.print(
                                    "#runEJ", Integer.valueOf(packageStats.numRunningEj));
                            indentingPrintWriter2.print(
                                    "#runReg", Integer.valueOf(packageStats.numRunningRegular));
                            indentingPrintWriter2.print(
                                    "#stagedEJ", Integer.valueOf(packageStats.numStagedEj));
                            indentingPrintWriter2.print(
                                    "#stagedReg", Integer.valueOf(packageStats.numStagedRegular));
                            indentingPrintWriter2.println("}");
                        }
                    });
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.print("User Grace Period: ");
            indentingPrintWriter.println(this.mGracePeriodObserver.mGracePeriodExpiration);
            indentingPrintWriter.println();
            this.mStatLogger.dump(indentingPrintWriter);
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }

    public final void dumpProtoLocked(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(1146756268052L);
        protoOutputStream.write(1133871366145L, this.mCurrentInteractiveState);
        protoOutputStream.write(1133871366146L, this.mEffectiveInteractiveState);
        protoOutputStream.write(1112396529667L, j - this.mLastScreenOnRealtime);
        protoOutputStream.write(1112396529668L, j - this.mLastScreenOffRealtime);
        protoOutputStream.write(1120986464262L, this.mLastMemoryTrimLevel);
        this.mStatLogger.dumpProto(protoOutputStream, 1146756268039L);
        protoOutputStream.end(start);
    }

    public final boolean executeStopCommandLocked(
            PrintWriter printWriter,
            String str,
            int i,
            String str2,
            boolean z,
            int i2,
            int i3,
            int i4) {
        boolean z2 = false;
        for (int i5 = 0; i5 < ((ArrayList) this.mActiveServices).size(); i5++) {
            JobServiceContext jobServiceContext =
                    (JobServiceContext) ((ArrayList) this.mActiveServices).get(i5);
            JobStatus jobStatus = jobServiceContext.mRunningJob;
            if (jobStatus != null
                    && ((i == -1 || i == UserHandle.getUserId(jobStatus.callingUid))
                            && ((str == null || str.equals(jobStatus.sourcePackageName))
                                    && Objects.equals(str2, jobStatus.mNamespace)
                                    && ((!z || i2 == jobStatus.job.getId())
                                            && jobServiceContext.mVerb == 2)))) {
                jobServiceContext.mParams.setStopReason(i3, i4, "stop from shell");
                jobServiceContext.sendStopMessageLocked("stop from shell");
                printWriter.print("Stopping job: ");
                jobStatus.printUniqueId(printWriter);
                printWriter.print(" ");
                printWriter.println(jobStatus.job.getService().flattenToShortString());
                z2 = true;
            }
        }
        return z2;
    }

    public final Pair getEstimatedNetworkBytesLocked(int i, int i2, String str, String str2) {
        for (int i3 = 0; i3 < ((ArrayList) this.mActiveServices).size(); i3++) {
            JobServiceContext jobServiceContext =
                    (JobServiceContext) ((ArrayList) this.mActiveServices).get(i3);
            JobStatus jobStatus = jobServiceContext.mRunningJob;
            if (jobStatus != null
                    && jobStatus.matches(i, i2, str2)
                    && jobStatus.sourcePackageName.equals(str)) {
                return Pair.create(
                        Long.valueOf(jobServiceContext.mEstimatedDownloadBytes),
                        Long.valueOf(jobServiceContext.mEstimatedUploadBytes));
            }
        }
        return null;
    }

    public final int getJobWorkTypes(JobStatus jobStatus) {
        if (!shouldRunAsFgUserJob(jobStatus)) {
            return ((jobStatus.lastEvaluatedBias >= 35
                                    || jobStatus.shouldTreatAsExpeditedJob()
                                    || jobStatus.shouldTreatAsUserInitiatedJob())
                            ? 32
                            : 0)
                    | 64;
        }
        int i = jobStatus.lastEvaluatedBias;
        int i2 = i >= 40 ? 1 : i >= 35 ? 2 : 16;
        return jobStatus.shouldTreatAsExpeditedJob()
                ? i2 | 8
                : jobStatus.shouldTreatAsUserInitiatedJob() ? i2 | 4 : i2;
    }

    public int getPackageConcurrencyLimitEj() {
        return this.mPkgConcurrencyLimitEj;
    }

    public PackageStats getPackageStatsForTesting(int i, String str) {
        PackageStats pkgStatsLocked = getPkgStatsLocked(i, str);
        this.mActivePkgStats.add(i, str, pkgStatsLocked);
        return pkgStatsLocked;
    }

    public final PackageStats getPkgStatsLocked(int i, String str) {
        PackageStats packageStats = (PackageStats) this.mActivePkgStats.get(i, str);
        if (packageStats != null) {
            return packageStats;
        }
        PackageStats packageStats2 = (PackageStats) this.mPkgStatsPool.acquire();
        if (packageStats2 == null) {
            packageStats2 = new PackageStats();
        }
        PackageStats packageStats3 = packageStats2;
        packageStats3.userId = i;
        packageStats3.packageName = str;
        packageStats3.numRunningRegular = 0;
        packageStats3.numRunningEj = 0;
        packageStats3.numStagedRegular = 0;
        packageStats3.numStagedEj = 0;
        return packageStats3;
    }

    public final JobServiceContext getRunningJobServiceContextLocked(JobStatus jobStatus) {
        if (!this.mRunningJobs.contains(jobStatus)) {
            return null;
        }
        for (int i = 0; i < ((ArrayList) this.mActiveServices).size(); i++) {
            JobServiceContext jobServiceContext =
                    (JobServiceContext) ((ArrayList) this.mActiveServices).get(i);
            if (jobServiceContext.mRunningJob == jobStatus) {
                return jobServiceContext;
            }
        }
        Slog.wtf("JobScheduler.Concurrency", "Couldn't find running job on a context");
        this.mRunningJobs.remove(jobStatus);
        if (jobStatus != null) {
            JobSchedulerPackageFilter jobSchedulerPackageFilter =
                    JobSchedulerPackageFilter.JobSchedulerPackageFilterHolder.INSTANCE;
            JobInfo jobInfo = jobStatus.job;
            if (jobInfo != null) {
                ((HashSet) jobSchedulerPackageFilter.mRunningJobSet).remove(jobInfo);
            } else {
                jobSchedulerPackageFilter.getClass();
            }
        }
        return null;
    }

    public final Pair getTransferredNetworkBytesLocked(int i, int i2, String str, String str2) {
        for (int i3 = 0; i3 < ((ArrayList) this.mActiveServices).size(); i3++) {
            JobServiceContext jobServiceContext =
                    (JobServiceContext) ((ArrayList) this.mActiveServices).get(i3);
            JobStatus jobStatus = jobServiceContext.mRunningJob;
            if (jobStatus != null
                    && jobStatus.matches(i, i2, str2)
                    && jobStatus.sourcePackageName.equals(str)) {
                return Pair.create(
                        Long.valueOf(jobServiceContext.mTransferredDownloadBytes),
                        Long.valueOf(jobServiceContext.mTransferredUploadBytes));
            }
        }
        return null;
    }

    public boolean hasImmediacyPrivilegeLocked(JobStatus jobStatus, SparseIntArray sparseIntArray) {
        if (!jobStatus.shouldTreatAsExpeditedJob() && !jobStatus.shouldTreatAsUserInitiatedJob()) {
            return false;
        }
        if (jobStatus.lastEvaluatedBias == 40) {
            return true;
        }
        int i = jobStatus.sourceUid;
        int i2 = sparseIntArray.get(i, 0);
        if (i2 == 1) {
            return false;
        }
        if (i2 == 2) {
            return jobStatus.shouldTreatAsUserInitiatedJob();
        }
        if (i2 == 3) {
            return true;
        }
        if (this.mActivityManagerInternal.getUidProcessState(i) == 2) {
            sparseIntArray.put(i, 3);
            return true;
        }
        if (jobStatus.shouldTreatAsExpeditedJob()) {
            return false;
        }
        BackgroundStartPrivileges backgroundStartPrivileges =
                this.mActivityManagerInternal.getBackgroundStartPrivileges(i);
        if (DEBUG) {
            Slog.d(
                    "JobScheduler.Concurrency",
                    "Job "
                            + jobStatus.toShortString()
                            + " bsp state: "
                            + backgroundStartPrivileges);
        }
        boolean allowsBackgroundActivityStarts =
                backgroundStartPrivileges.allowsBackgroundActivityStarts();
        sparseIntArray.put(i, allowsBackgroundActivityStarts ? 2 : 1);
        return allowsBackgroundActivityStarts;
    }

    public boolean isPkgConcurrencyLimitedLocked(JobStatus jobStatus) {
        PackageStats packageStats;
        if (jobStatus.lastEvaluatedBias >= 40) {
            return false;
        }
        if (this.mRunningJobs.size() + this.mService.mPendingJobQueue.mSize
                        >= this.mWorkTypeConfig.mMaxTotal
                && (packageStats =
                                (PackageStats)
                                        this.mActivePkgStats.get(
                                                jobStatus.sourceUserId,
                                                jobStatus.sourcePackageName))
                        != null) {
            return jobStatus.shouldTreatAsExpeditedJob()
                    ? packageStats.numRunningEj + packageStats.numStagedEj
                            >= this.mPkgConcurrencyLimitEj
                    : packageStats.numRunningRegular + packageStats.numStagedRegular
                            >= this.mPkgConcurrencyLimitRegular;
        }
        return false;
    }

    public final boolean isSimilarJobRunningLocked(JobStatus jobStatus) {
        for (int size = this.mRunningJobs.size() - 1; size >= 0; size--) {
            JobStatus jobStatus2 = (JobStatus) this.mRunningJobs.valueAt(size);
            if (jobStatus.matches(
                    jobStatus2.callingUid, jobStatus2.job.getId(), jobStatus2.mNamespace)) {
                return true;
            }
        }
        return false;
    }

    public final void markJobsForUserStopLocked(int i, String str, String str2) {
        for (int size = ((ArrayList) this.mActiveServices).size() - 1; size >= 0; size--) {
            JobServiceContext jobServiceContext =
                    (JobServiceContext) ((ArrayList) this.mActiveServices).get(size);
            JobStatus jobStatus = jobServiceContext.mRunningJob;
            if (jobStatus != null
                    && i == UserHandle.getUserId(jobStatus.callingUid)
                    && jobStatus.job.getService().getPackageName().equals(str)) {
                if (jobServiceContext.mVerb != 4) {
                    if (JobServiceContext.DEBUG) {
                        Slog.d(
                                "JobServiceContext",
                                "Marking "
                                        + jobServiceContext.mRunningJob.toShortString()
                                        + " for death because 13:"
                                        + str2);
                    }
                    jobServiceContext.mDeathMarkStopReason = 13;
                    jobServiceContext.mDeathMarkInternalStopReason = 11;
                    jobServiceContext.mDeathMarkDebugReason = str2;
                    if (jobServiceContext.mParams.getStopReason() == 0) {
                        jobServiceContext.mParams.setStopReason(13, 11, str2);
                    }
                } else if (JobServiceContext.DEBUG) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Too late to mark for death (verb="),
                            jobServiceContext.mVerb,
                            "), ignoring.",
                            "JobServiceContext");
                }
            }
        }
    }

    public final void maybeStopOvertimeJobsLocked(JobRestriction jobRestriction) {
        for (int size = ((ArrayList) this.mActiveServices).size() - 1; size >= 0; size--) {
            JobServiceContext jobServiceContext =
                    (JobServiceContext) ((ArrayList) this.mActiveServices).get(size);
            JobStatus jobStatus = jobServiceContext.mRunningJob;
            if (jobStatus != null
                    && !jobServiceContext.isWithinExecutionGuaranteeTime()
                    && jobRestriction.isJobRestricted(
                            jobStatus, this.mService.evaluateJobBiasLocked(jobStatus))) {
                int i = jobRestriction.mInternalReason;
                jobServiceContext.cancelExecutingJobLocked(
                        jobRestriction.mStopReason,
                        i,
                        JobParameters.getInternalReasonCodeDescription(i));
            }
        }
    }

    public final void noteConcurrency(boolean z) {
        JobPackageTracker jobPackageTracker = this.mService.mJobPackageTracker;
        int size = this.mRunningJobs.size();
        int i = this.mWorkCountTracker.mNumRunningJobs.get(1, 0);
        JobPackageTracker.DataSet dataSet = jobPackageTracker.mCurDataSet;
        if (size > dataSet.mMaxTotalActive) {
            dataSet.mMaxTotalActive = size;
        }
        if (i > dataSet.mMaxFgActive) {
            dataSet.mMaxFgActive = i;
        }
        if (z) {
            sConcurrencyHistogramLogger.logSample(((ArrayList) this.mActiveServices).size());
        }
    }

    public final void onAppRemovedLocked(int i, String str) {
        PackageStats packageStats =
                (PackageStats) this.mActivePkgStats.get(UserHandle.getUserId(i), str);
        if (packageStats != null) {
            if (packageStats.numRunningEj <= 0 && packageStats.numRunningRegular <= 0) {
                this.mActivePkgStats.delete(UserHandle.getUserId(i), str);
                return;
            }
            Slog.w(
                    "JobScheduler.Concurrency",
                    str + "(" + i + ") marked as removed before jobs stopped running");
        }
    }

    public final void onInteractiveStateChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentInteractiveState == z) {
                    return;
                }
                this.mCurrentInteractiveState = z;
                if (DEBUG) {
                    Slog.d("JobScheduler.Concurrency", "Interactive: " + z);
                }
                JobSchedulerService.sElapsedRealtimeClock.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (z) {
                    this.mLastScreenOnRealtime = elapsedRealtime;
                    this.mEffectiveInteractiveState = true;
                    this.mHandler.removeCallbacks(this.mRampUpForScreenOff);
                } else {
                    this.mLastScreenOffRealtime = elapsedRealtime;
                    this.mHandler.postDelayed(
                            this.mRampUpForScreenOff, this.mScreenOffAdjustmentDelayMs);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onThirdPartyAppsCanStart() {
        IBatteryStats asInterface =
                IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
        for (int i = 0; i < this.mSteadyStateConcurrencyLimit; i++) {
            ArraySet arraySet = this.mIdleContexts;
            JobSchedulerService jobSchedulerService = this.mService;
            JobPackageTracker jobPackageTracker = jobSchedulerService.mJobPackageTracker;
            Looper looper = AppSchedulingModuleThread.get().getLooper();
            this.mInjector.getClass();
            arraySet.add(
                    new JobServiceContext(
                            jobSchedulerService,
                            this,
                            this.mNotificationCoordinator,
                            asInterface,
                            jobPackageTracker,
                            looper));
        }
    }

    public final void onUserRemoved(int i) {
        GracePeriodObserver gracePeriodObserver = this.mGracePeriodObserver;
        synchronized (gracePeriodObserver.mLock) {
            gracePeriodObserver.mGracePeriodExpiration.delete(i);
        }
    }

    public void prepareForAssignmentDeterminationLocked(
            ArraySet arraySet, List list, List list2, AssignmentInfo assignmentInfo) {
        JobServiceContext createNewJobServiceContext;
        int i;
        ArrayList arrayList;
        int i2;
        PendingJobQueue pendingJobQueue = this.mService.mPendingJobQueue;
        List list3 = this.mActiveServices;
        updateCounterConfigLocked();
        WorkCountTracker workCountTracker = this.mWorkCountTracker;
        workCountTracker.mNumActuallyReservedSlots.clear();
        workCountTracker.mNumPendingJobs.clear();
        workCountTracker.mNumRunningJobs.clear();
        workCountTracker.mNumStartingJobs.clear();
        int i3 = 1;
        updateNonRunningPrioritiesLocked(pendingJobQueue, true);
        ArrayList arrayList2 = (ArrayList) list3;
        int size = arrayList2.size();
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i4 = 0;
        long j = Long.MAX_VALUE;
        while (i4 < size) {
            JobServiceContext jobServiceContext = (JobServiceContext) arrayList2.get(i4);
            JobStatus jobStatus = jobServiceContext.mRunningJob;
            ContextAssignment contextAssignment =
                    (ContextAssignment) this.mContextAssignmentPool.acquire();
            if (contextAssignment == null) {
                contextAssignment = new ContextAssignment();
            }
            contextAssignment.context = jobServiceContext;
            if (jobStatus != null) {
                int i5 = jobServiceContext.mRunningJobWorkType;
                i = size;
                SparseIntArray sparseIntArray = workCountTracker.mNumRunningJobs;
                arrayList = arrayList2;
                i2 = 1;
                sparseIntArray.put(i5, sparseIntArray.get(i5) + 1);
                if (jobStatus.startedWithImmediacyPrivilege) {
                    assignmentInfo.numRunningImmediacyPrivileged++;
                }
                if (jobStatus.shouldTreatAsUserInitiatedJob()) {
                    assignmentInfo.numRunningUi++;
                } else if (jobStatus.startedAsExpeditedJob) {
                    assignmentInfo.numRunningEj++;
                } else {
                    assignmentInfo.numRunningReg++;
                }
            } else {
                i = size;
                arrayList = arrayList2;
                i2 = i3;
            }
            contextAssignment.preferredUid = jobServiceContext.mPreferredUid;
            String shouldStopRunningJobLocked = shouldStopRunningJobLocked(jobServiceContext);
            contextAssignment.shouldStopJobReason = shouldStopRunningJobLocked;
            if (shouldStopRunningJobLocked != null) {
                list2.add(contextAssignment);
            } else {
                long max =
                        Math.max(
                                0L,
                                (jobServiceContext.mExecutionStartTimeElapsed
                                                + jobServiceContext.mMinExecutionGuaranteeMillis)
                                        - elapsedRealtime);
                contextAssignment.timeUntilStoppableMs = max;
                j = Math.min(j, max);
                list.add(contextAssignment);
            }
            i4++;
            i3 = i2;
            size = i;
            arrayList2 = arrayList;
        }
        int i6 = size;
        int i7 = i3;
        JobConcurrencyManager$$ExternalSyntheticLambda2
                jobConcurrencyManager$$ExternalSyntheticLambda2 = sDeterminationComparator;
        list.sort(jobConcurrencyManager$$ExternalSyntheticLambda2);
        list2.sort(jobConcurrencyManager$$ExternalSyntheticLambda2);
        for (int i8 = i6; i8 < this.mSteadyStateConcurrencyLimit; i8++) {
            int size2 = this.mIdleContexts.size();
            if (size2 > 0) {
                createNewJobServiceContext =
                        (JobServiceContext) this.mIdleContexts.removeAt(size2 - 1);
            } else {
                Slog.w(
                        "JobScheduler.Concurrency",
                        "Had fewer than " + this.mSteadyStateConcurrencyLimit + " in existence");
                createNewJobServiceContext = createNewJobServiceContext();
            }
            ContextAssignment contextAssignment2 =
                    (ContextAssignment) this.mContextAssignmentPool.acquire();
            if (contextAssignment2 == null) {
                contextAssignment2 = new ContextAssignment();
            }
            contextAssignment2.context = createNewJobServiceContext;
            arraySet.add(contextAssignment2);
        }
        workCountTracker.mNumUnspecializedRemaining = workCountTracker.mConfigMaxTotal;
        for (int i9 = i7; i9 < 127; i9 <<= 1) {
            int i10 = workCountTracker.mNumRunningJobs.get(i9);
            workCountTracker.mRecycledReserved.put(i9, i10);
            workCountTracker.mNumUnspecializedRemaining -= i10;
        }
        for (int i11 = i7; i11 < 127; i11 <<= 1) {
            int i12 =
                    workCountTracker.mNumPendingJobs.get(i11)
                            + workCountTracker.mNumRunningJobs.get(i11);
            int i13 = workCountTracker.mRecycledReserved.get(i11);
            int max2 =
                    Math.max(
                            0,
                            Math.min(
                                    workCountTracker.mNumUnspecializedRemaining,
                                    Math.min(
                                            i12,
                                            workCountTracker.mConfigNumReservedSlots.get(i11)
                                                    - i13)));
            workCountTracker.mRecycledReserved.put(i11, i13 + max2);
            workCountTracker.mNumUnspecializedRemaining -= max2;
        }
        for (int i14 = i7; i14 < 127; i14 <<= 1) {
            int i15 =
                    workCountTracker.mNumPendingJobs.get(i14)
                            + workCountTracker.mNumRunningJobs.get(i14);
            int i16 = workCountTracker.mRecycledReserved.get(i14);
            int max3 =
                    Math.max(
                            0,
                            Math.min(
                                    workCountTracker.mNumUnspecializedRemaining,
                                    Math.min(workCountTracker.mConfigAbsoluteMaxSlots.get(i14), i15)
                                            - i16));
            workCountTracker.mNumActuallyReservedSlots.put(i14, i16 + max3);
            workCountTracker.mNumUnspecializedRemaining -= max3;
        }
        if (j == Long.MAX_VALUE) {
            j = 0;
        }
        assignmentInfo.minPreferredUidOnlyWaitingTimeMs = j;
    }

    public boolean shouldRunAsFgUserJob(JobStatus jobStatus) {
        if (!this.mShouldRestrictBgUser) {
            return true;
        }
        int i = jobStatus.sourceUserId;
        UserManagerInternal userManagerInternal = this.mUserManagerInternal;
        UserInfo userInfo = userManagerInternal.getUserInfo(i);
        int i2 = userInfo.profileGroupId;
        if (i2 != -10000 && i2 != i) {
            userInfo = userManagerInternal.getUserInfo(i2);
            i = i2;
        }
        return this.mActivityManagerInternal.getCurrentUserId() == i
                || userInfo.isPrimary()
                || this.mGracePeriodObserver.isWithinGracePeriodForUser(i);
    }

    public final String shouldStopRunningJobLocked(JobServiceContext jobServiceContext) {
        JobStatus jobStatus = jobServiceContext.mRunningJob;
        if (jobStatus == null || jobServiceContext.isWithinExecutionGuaranteeTime()) {
            return null;
        }
        if (this.mPowerManager.isPowerSaveMode()) {
            return "battery saver";
        }
        if (this.mPowerManager.isDeviceIdleMode()) {
            return "deep doze";
        }
        JobSchedulerService jobSchedulerService = this.mService;
        JobRestriction checkIfRestricted = jobSchedulerService.checkIfRestricted(jobStatus);
        if (checkIfRestricted != null) {
            return "restriction:"
                    + JobParameters.getInternalReasonCodeDescription(
                            checkIfRestricted.mInternalReason);
        }
        updateCounterConfigLocked();
        int i = jobServiceContext.mRunningJobWorkType;
        if (this.mRunningJobs.size() > this.mWorkTypeConfig.mMaxTotal) {
            return "too many jobs running";
        }
        WorkCountTracker workCountTracker = this.mWorkCountTracker;
        int i2 = 0;
        if (workCountTracker.mNumRunningJobs.get(i, 0)
                > workCountTracker.mConfigAbsoluteMaxSlots.get(i)) {
            return "too many jobs running";
        }
        PendingJobQueue pendingJobQueue = jobSchedulerService.mPendingJobQueue;
        if (pendingJobQueue.mSize == 0) {
            return null;
        }
        if (!jobStatus.shouldTreatAsExpeditedJob() && !jobStatus.startedAsExpeditedJob) {
            if (workCountTracker.getPendingJobCount(i) > 0) {
                return "blocking " + workTypeToString(i) + " queue";
            }
            pendingJobQueue.mNeedToResetIterators = true;
            int i3 = 127;
            do {
                JobStatus next = pendingJobQueue.next();
                if (next == null) {
                    break;
                }
                int jobWorkTypes = getJobWorkTypes(next);
                if ((jobWorkTypes & i3) > 0 && workCountTracker.canJobStart(jobWorkTypes, i) != 0) {
                    return "blocking other pending jobs";
                }
                i3 &= ~jobWorkTypes;
            } while (i3 != 0);
            return null;
        }
        if (i == 32 || i == 64) {
            if (workCountTracker.getPendingJobCount(32) > 0) {
                return "blocking " + workTypeToString(32) + " queue";
            }
            if (workCountTracker.getPendingJobCount(8) > 0
                    && workCountTracker.canJobStart(8, i) != 0) {
                return "blocking " + workTypeToString(8) + " queue";
            }
        } else {
            if (workCountTracker.getPendingJobCount(8) > 0) {
                return "blocking " + workTypeToString(8) + " queue";
            }
            if (jobStatus.startedWithImmediacyPrivilege) {
                for (int size = this.mRunningJobs.size() - 1; size >= 0; size--) {
                    if (((JobStatus) this.mRunningJobs.valueAt(size))
                            .startedWithImmediacyPrivilege) {
                        i2++;
                    }
                }
                if (i2 > this.mWorkTypeConfig.mMaxTotal / 2) {
                    return "prevent immediacy privilege dominance";
                }
            }
        }
        return null;
    }

    public final void startJobLocked(
            int i, JobServiceContext jobServiceContext, JobStatus jobStatus) {
        JobSchedulerService jobSchedulerService = this.mService;
        ArrayList arrayList = (ArrayList) jobSchedulerService.mControllers;
        int size = arrayList.size();
        PowerManager powerManager = this.mPowerManager;
        String wakelockTag = jobStatus.getWakelockTag();
        int i2 = jobStatus.sourceUserId;
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, wakelockTag);
        int i3 = jobStatus.sourceUid;
        String str = jobStatus.sourcePackageName;
        newWakeLock.setWorkSource(jobSchedulerService.deriveWorkSource(i3, str));
        newWakeLock.setReferenceCounted(false);
        newWakeLock.acquire();
        for (int i4 = 0; i4 < size; i4++) {
            try {
                ((StateController) arrayList.get(i4)).prepareForExecutionLocked(jobStatus);
            } catch (Throwable th) {
                newWakeLock.release();
                throw th;
            }
        }
        PackageStats pkgStatsLocked = getPkgStatsLocked(i2, str);
        PackageStats.m607$$Nest$madjustStagedCount(
                pkgStatsLocked, false, jobStatus.shouldTreatAsExpeditedJob());
        boolean executeRunnableJob = jobServiceContext.executeRunnableJob(jobStatus, i);
        WorkCountTracker workCountTracker = this.mWorkCountTracker;
        if (executeRunnableJob) {
            this.mRunningJobs.add(jobStatus);
            JobSchedulerPackageFilter.JobSchedulerPackageFilterHolder.INSTANCE.addRunningJobs(
                    jobStatus.job, jobStatus.callingUid);
            ((ArrayList) this.mActiveServices).add(jobServiceContext);
            this.mIdleContexts.remove(jobServiceContext);
            SparseIntArray sparseIntArray = workCountTracker.mNumRunningJobs;
            sparseIntArray.put(i, sparseIntArray.get(i) + 1);
            int i5 = workCountTracker.mNumStartingJobs.get(i);
            if (i5 == 0) {
                FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(
                        i, "# stated jobs for ", " went negative.", "JobScheduler.Concurrency");
            } else {
                workCountTracker.mNumStartingJobs.put(i, i5 - 1);
            }
            PackageStats.m606$$Nest$madjustRunningCount(
                    pkgStatsLocked, true, jobStatus.shouldTreatAsExpeditedJob());
            this.mActivePkgStats.add(i2, str, pkgStatsLocked);
            jobSchedulerService.resetPendingJobReasonCache(jobStatus);
        } else {
            Slog.e("JobScheduler.Concurrency", "Error executing " + jobStatus);
            int i6 = workCountTracker.mNumStartingJobs.get(i);
            if (i6 == 0) {
                Slog.e("JobScheduler.Concurrency", "# staged jobs for " + i + " went negative.");
            } else {
                workCountTracker.mNumStartingJobs.put(i, i6 - 1);
                workCountTracker.maybeAdjustReservations(i);
            }
            for (int i7 = 0; i7 < size; i7++) {
                ((StateController) arrayList.get(i7)).unprepareFromExecutionLocked(jobStatus);
            }
        }
        if (jobSchedulerService.mPendingJobQueue.remove(jobStatus)) {
            jobSchedulerService.mJobPackageTracker.noteNonpending(jobStatus);
        }
        newWakeLock.release();
    }

    public final boolean stopJobOnServiceContextLocked(
            JobStatus jobStatus, int i, int i2, String str) {
        if (!this.mRunningJobs.contains(jobStatus)) {
            return false;
        }
        for (int i3 = 0; i3 < ((ArrayList) this.mActiveServices).size(); i3++) {
            JobServiceContext jobServiceContext =
                    (JobServiceContext) ((ArrayList) this.mActiveServices).get(i3);
            if (jobServiceContext.mRunningJob == jobStatus) {
                jobServiceContext.cancelExecutingJobLocked(i, i2, str);
                return true;
            }
        }
        Slog.wtf("JobScheduler.Concurrency", "Couldn't find running job on a context");
        this.mRunningJobs.remove(jobStatus);
        if (jobStatus != null) {
            JobSchedulerPackageFilter jobSchedulerPackageFilter =
                    JobSchedulerPackageFilter.JobSchedulerPackageFilterHolder.INSTANCE;
            JobInfo jobInfo = jobStatus.job;
            if (jobInfo != null) {
                ((HashSet) jobSchedulerPackageFilter.mRunningJobSet).remove(jobInfo);
            } else {
                jobSchedulerPackageFilter.getClass();
            }
        }
        return false;
    }

    public final void updateConfigLocked() {
        DeviceConfig.Properties properties =
                DeviceConfig.getProperties("jobscheduler", new String[0]);
        this.mSteadyStateConcurrencyLimit =
                Math.max(
                        8,
                        Math.min(
                                64,
                                properties.getInt("concurrency_limit", DEFAULT_CONCURRENCY_LIMIT)));
        this.mScreenOffAdjustmentDelayMs =
                properties.getLong("concurrency_screen_off_adjustment_delay_ms", 30000L);
        WorkConfigLimitsPerMemoryTrimLevel workConfigLimitsPerMemoryTrimLevel =
                CONFIG_LIMITS_SCREEN_ON;
        workConfigLimitsPerMemoryTrimLevel.normal.update(
                properties, this.mSteadyStateConcurrencyLimit);
        workConfigLimitsPerMemoryTrimLevel.moderate.update(
                properties, this.mSteadyStateConcurrencyLimit);
        workConfigLimitsPerMemoryTrimLevel.low.update(
                properties, this.mSteadyStateConcurrencyLimit);
        workConfigLimitsPerMemoryTrimLevel.critical.update(
                properties, this.mSteadyStateConcurrencyLimit);
        WorkConfigLimitsPerMemoryTrimLevel workConfigLimitsPerMemoryTrimLevel2 =
                CONFIG_LIMITS_SCREEN_OFF;
        workConfigLimitsPerMemoryTrimLevel2.normal.update(
                properties, this.mSteadyStateConcurrencyLimit);
        workConfigLimitsPerMemoryTrimLevel2.moderate.update(
                properties, this.mSteadyStateConcurrencyLimit);
        workConfigLimitsPerMemoryTrimLevel2.low.update(
                properties, this.mSteadyStateConcurrencyLimit);
        workConfigLimitsPerMemoryTrimLevel2.critical.update(
                properties, this.mSteadyStateConcurrencyLimit);
        this.mPkgConcurrencyLimitEj =
                Math.max(
                        1,
                        Math.min(
                                this.mSteadyStateConcurrencyLimit,
                                properties.getInt(KEY_PKG_CONCURRENCY_LIMIT_EJ, 3)));
        this.mPkgConcurrencyLimitRegular =
                Math.max(
                        1,
                        Math.min(
                                this.mSteadyStateConcurrencyLimit,
                                properties.getInt(
                                        KEY_PKG_CONCURRENCY_LIMIT_REGULAR,
                                        DEFAULT_PKG_CONCURRENCY_LIMIT_REGULAR)));
        this.mMaxWaitTimeBypassEnabled =
                properties.getBoolean(KEY_ENABLE_MAX_WAIT_TIME_BYPASS, true);
        long max = Math.max(0L, properties.getLong(KEY_MAX_WAIT_UI_MS, 300000L));
        this.mMaxWaitUIMs = max;
        long max2 = Math.max(max, properties.getLong("concurrency_max_wait_ej_ms", 300000L));
        this.mMaxWaitEjMs = max2;
        this.mMaxWaitRegularMs =
                Math.max(max2, properties.getLong("concurrency_max_wait_regular_ms", 1800000L));
    }

    public final void updateCounterConfigLocked() {
        long millis = JobSchedulerService.sUptimeMillisClock.millis();
        if (millis < this.mNextSystemStateRefreshTime) {
            return;
        }
        StatLogger statLogger = this.mStatLogger;
        long time = statLogger.getTime();
        this.mNextSystemStateRefreshTime = millis + 1000;
        this.mLastMemoryTrimLevel = 0;
        try {
            this.mLastMemoryTrimLevel = ActivityManager.getService().getMemoryTrimLevel();
        } catch (RemoteException unused) {
        }
        statLogger.logDurationStat(1, time);
        WorkConfigLimitsPerMemoryTrimLevel workConfigLimitsPerMemoryTrimLevel =
                this.mEffectiveInteractiveState
                        ? CONFIG_LIMITS_SCREEN_ON
                        : CONFIG_LIMITS_SCREEN_OFF;
        int i = this.mLastMemoryTrimLevel;
        if (i == 1) {
            this.mWorkTypeConfig = workConfigLimitsPerMemoryTrimLevel.moderate;
        } else if (i == 2) {
            this.mWorkTypeConfig = workConfigLimitsPerMemoryTrimLevel.low;
        } else if (i != 3) {
            this.mWorkTypeConfig = workConfigLimitsPerMemoryTrimLevel.normal;
        } else {
            this.mWorkTypeConfig = workConfigLimitsPerMemoryTrimLevel.critical;
        }
        WorkTypeConfig workTypeConfig = this.mWorkTypeConfig;
        WorkCountTracker workCountTracker = this.mWorkCountTracker;
        workCountTracker.getClass();
        workCountTracker.mConfigMaxTotal = workTypeConfig.mMaxTotal;
        for (int i2 = 1; i2 < 127; i2 <<= 1) {
            workCountTracker.mConfigNumReservedSlots.put(
                    i2, workTypeConfig.mMinReservedSlots.get(i2));
            workCountTracker.mConfigAbsoluteMaxSlots.put(
                    i2, workTypeConfig.mMaxAllowedSlots.get(i2, workTypeConfig.mMaxTotal));
        }
        workCountTracker.mNumUnspecializedRemaining = workCountTracker.mConfigMaxTotal;
        for (int size = workCountTracker.mNumRunningJobs.size() - 1; size >= 0; size--) {
            workCountTracker.mNumUnspecializedRemaining -=
                    Math.max(
                            workCountTracker.mNumRunningJobs.valueAt(size),
                            workCountTracker.mConfigNumReservedSlots.get(
                                    workCountTracker.mNumRunningJobs.keyAt(size)));
        }
    }

    public final void updateNonRunningPrioritiesLocked(PendingJobQueue pendingJobQueue, boolean z) {
        pendingJobQueue.mNeedToResetIterators = true;
        while (true) {
            JobStatus next = pendingJobQueue.next();
            if (next == null) {
                return;
            }
            if (!this.mRunningJobs.contains(next)) {
                next.lastEvaluatedBias = this.mService.evaluateJobBiasLocked(next);
                if (z) {
                    this.mWorkCountTracker.adjustPendingJobCount(getJobWorkTypes(next), true);
                }
            }
        }
    }
}
