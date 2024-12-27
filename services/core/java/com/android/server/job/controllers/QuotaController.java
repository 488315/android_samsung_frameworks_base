package com.android.server.job.controllers;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.UidObserver;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManagerInternal;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.UserPackage;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseArrayMap;
import android.util.SparseBooleanArray;
import android.util.SparseLongArray;
import android.util.SparseSetArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.jobs.ArrayUtils;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PowerAllowlistInternal;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import com.android.server.job.Flags;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;
import com.android.server.job.JobSchedulerService$Constants$$ExternalSyntheticOutline0;
import com.android.server.job.controllers.QuotaController;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.usage.AppStandbyInternal;
import com.android.server.utils.AlarmQueue;
import dalvik.annotation.optimization.NeverCompile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class QuotaController extends StateController {
    public static final boolean DEBUG;
    static final int MSG_END_GRACE_PERIOD = 6;
    static final int MSG_REACHED_EJ_TIME_QUOTA = 4;
    static final int MSG_REACHED_TIME_QUOTA = 0;
    public final AlarmManager mAlarmManager;
    public final long[] mAllowedTimePerPeriodMs;
    public final BackgroundJobsController mBackgroundJobsController;
    public final long[] mBucketPeriodsMs;
    public final ConnectivityController mConnectivityController;
    public final QuotaController$$ExternalSyntheticLambda0 mDeleteOldEventsFunctor;
    public long mEJGracePeriodTempAllowlistMs;
    public long mEJGracePeriodTopAppMs;
    public long mEJLimitWindowSizeMs;
    public final long[] mEJLimitsMs;
    public final SparseArrayMap mEJPkgTimers;
    public long mEJRewardInteractionMs;
    public long mEJRewardNotificationSeenMs;
    public long mEJRewardTopAppMs;
    public final SparseArrayMap mEJStats;
    public final SparseArrayMap mEJTimingSessions;
    public long mEJTopAppTimeChunkSizeMs;
    public final EarliestEndTimeFunctor mEarliestEndTimeFunctor;
    public long mEjLimitAdditionInstallerMs;
    public long mEjLimitAdditionSpecialMs;
    public final SparseArrayMap mExecutionStatsCache;
    public final SparseBooleanArray mForegroundUids;
    public final QcHandler mHandler;
    public final InQuotaAlarmQueue mInQuotaAlarmQueue;
    public final int[] mMaxBucketJobCounts;
    public final int[] mMaxBucketSessionCounts;
    public long mMaxExecutionTimeIntoQuotaMs;
    public long mMaxExecutionTimeMs;
    public int mMaxJobCountPerRateLimitingWindow;
    public int mMaxSessionCountPerRateLimitingWindow;
    public long mNextCleanupTimeElapsed;
    public final SparseArrayMap mPkgTimers;
    public final QcConstants mQcConstants;
    public long mQuotaBufferMs;
    public long mQuotaBumpAdditionalDurationMs;
    public int mQuotaBumpAdditionalJobCount;
    public int mQuotaBumpAdditionalSessionCount;
    public int mQuotaBumpLimit;
    public long mQuotaBumpWindowSizeMs;
    public long mRateLimitingWindowMs;
    public final AnonymousClass1 mSessionCleanupAlarmListener;
    public final SparseSetArray mSystemInstallers;
    public final SparseBooleanArray mTempAllowlistCache;
    public final SparseLongArray mTempAllowlistGraceCache;
    public final TimedEventTooOldPredicate mTimedEventTooOld;
    public final TimerChargingUpdateFunctor mTimerChargingUpdateFunctor;
    public final SparseArrayMap mTimingEvents;
    public long mTimingSessionCoalescingDurationMs;
    public final SparseBooleanArray mTopAppCache;
    public final SparseLongArray mTopAppGraceCache;
    public final SparseArrayMap mTopAppTrackers;
    public final ArraySet mTopStartedJobs;
    public final SparseArrayMap mTrackedJobs;
    public final UidConstraintUpdater mUpdateUidConstraints;

    public final class EarliestEndTimeFunctor implements Consumer {
        public long earliestEndElapsed;

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            List list = (List) obj;
            if (list == null || list.size() <= 0) {
                return;
            }
            this.earliestEndElapsed = Math.min(this.earliestEndElapsed, ((TimedEvent) list.get(0)).getEndTimeElapsed());
        }
    }

    class ExecutionStats {
        public long allowedTimePerPeriodMs;
        public int bgJobCountInMaxPeriod;
        public int bgJobCountInWindow;
        public long executionTimeInMaxPeriodMs;
        public long executionTimeInWindowMs;
        public long expirationTimeElapsed;
        public long inQuotaTimeElapsed;
        public int jobCountInRateLimitingWindow;
        public int jobCountLimit;
        public long jobRateLimitExpirationTimeElapsed;
        public int sessionCountInRateLimitingWindow;
        public int sessionCountInWindow;
        public int sessionCountLimit;
        public long sessionRateLimitExpirationTimeElapsed;
        public long windowSizeMs;

        public final boolean equals(Object obj) {
            if (!(obj instanceof ExecutionStats)) {
                return false;
            }
            ExecutionStats executionStats = (ExecutionStats) obj;
            return this.expirationTimeElapsed == executionStats.expirationTimeElapsed && this.allowedTimePerPeriodMs == executionStats.allowedTimePerPeriodMs && this.windowSizeMs == executionStats.windowSizeMs && this.jobCountLimit == executionStats.jobCountLimit && this.sessionCountLimit == executionStats.sessionCountLimit && this.executionTimeInWindowMs == executionStats.executionTimeInWindowMs && this.bgJobCountInWindow == executionStats.bgJobCountInWindow && this.executionTimeInMaxPeriodMs == executionStats.executionTimeInMaxPeriodMs && this.sessionCountInWindow == executionStats.sessionCountInWindow && this.bgJobCountInMaxPeriod == executionStats.bgJobCountInMaxPeriod && this.inQuotaTimeElapsed == executionStats.inQuotaTimeElapsed && this.jobRateLimitExpirationTimeElapsed == executionStats.jobRateLimitExpirationTimeElapsed && this.jobCountInRateLimitingWindow == executionStats.jobCountInRateLimitingWindow && this.sessionRateLimitExpirationTimeElapsed == executionStats.sessionRateLimitExpirationTimeElapsed && this.sessionCountInRateLimitingWindow == executionStats.sessionCountInRateLimitingWindow;
        }

        public final int hashCode() {
            return ((QuotaController.m629$$Nest$smhashLong(this.sessionRateLimitExpirationTimeElapsed) + ((((QuotaController.m629$$Nest$smhashLong(this.jobRateLimitExpirationTimeElapsed) + ((QuotaController.m629$$Nest$smhashLong(this.inQuotaTimeElapsed) + ((((((QuotaController.m629$$Nest$smhashLong(this.executionTimeInMaxPeriodMs) + ((((QuotaController.m629$$Nest$smhashLong(this.executionTimeInWindowMs) + ((QuotaController.m629$$Nest$smhashLong(this.sessionCountLimit) + ((QuotaController.m629$$Nest$smhashLong(this.jobCountLimit) + ((QuotaController.m629$$Nest$smhashLong(this.windowSizeMs) + ((QuotaController.m629$$Nest$smhashLong(this.allowedTimePerPeriodMs) + (QuotaController.m629$$Nest$smhashLong(this.expirationTimeElapsed) * 31)) * 31)) * 31)) * 31)) * 31)) * 31) + this.bgJobCountInWindow) * 31)) * 31) + this.bgJobCountInMaxPeriod) * 31) + this.sessionCountInWindow) * 31)) * 31)) * 31) + this.jobCountInRateLimitingWindow) * 31)) * 31) + this.sessionCountInRateLimitingWindow;
        }

        public final String toString() {
            return "expirationTime=" + this.expirationTimeElapsed + ", allowedTimePerPeriodMs=" + this.allowedTimePerPeriodMs + ", windowSizeMs=" + this.windowSizeMs + ", jobCountLimit=" + this.jobCountLimit + ", sessionCountLimit=" + this.sessionCountLimit + ", executionTimeInWindow=" + this.executionTimeInWindowMs + ", bgJobCountInWindow=" + this.bgJobCountInWindow + ", executionTimeInMaxPeriod=" + this.executionTimeInMaxPeriodMs + ", bgJobCountInMaxPeriod=" + this.bgJobCountInMaxPeriod + ", sessionCountInWindow=" + this.sessionCountInWindow + ", inQuotaTime=" + this.inQuotaTimeElapsed + ", rateLimitJobCountExpirationTime=" + this.jobRateLimitExpirationTimeElapsed + ", rateLimitJobCountWindow=" + this.jobCountInRateLimitingWindow + ", rateLimitSessionCountExpirationTime=" + this.sessionRateLimitExpirationTimeElapsed + ", rateLimitSessionCountWindow=" + this.sessionCountInRateLimitingWindow;
        }
    }

    public final class InQuotaAlarmQueue extends AlarmQueue {
        public InQuotaAlarmQueue(Context context, Looper looper) {
            super(context, looper, "*job.quota_check*", "In quota", false, 60000L);
        }

        @Override // com.android.server.utils.AlarmQueue
        public final boolean isForUser(int i, Object obj) {
            return ((UserPackage) obj).userId == i;
        }

        @Override // com.android.server.utils.AlarmQueue
        public final void processExpiredAlarms(ArraySet arraySet) {
            for (int i = 0; i < arraySet.size(); i++) {
                UserPackage userPackage = (UserPackage) arraySet.valueAt(i);
                QuotaController.this.mHandler.obtainMessage(2, userPackage.userId, 0, userPackage.packageName).sendToTarget();
            }
        }
    }

    class QcConstants {
        static final String KEY_ALLOWED_TIME_PER_PERIOD_ACTIVE_MS = "qc_allowed_time_per_period_active_ms";
        static final String KEY_ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS = "qc_allowed_time_per_period_exempted_ms";
        static final String KEY_ALLOWED_TIME_PER_PERIOD_FREQUENT_MS = "qc_allowed_time_per_period_frequent_ms";
        static final String KEY_ALLOWED_TIME_PER_PERIOD_RARE_MS = "qc_allowed_time_per_period_rare_ms";
        static final String KEY_ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS = "qc_allowed_time_per_period_restricted_ms";
        static final String KEY_ALLOWED_TIME_PER_PERIOD_WORKING_MS = "qc_allowed_time_per_period_working_ms";
        static final String KEY_EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS = "qc_ej_grace_period_temp_allowlist_ms";
        static final String KEY_EJ_GRACE_PERIOD_TOP_APP_MS = "qc_ej_grace_period_top_app_ms";
        static final String KEY_EJ_LIMIT_ACTIVE_MS = "qc_ej_limit_active_ms";
        static final String KEY_EJ_LIMIT_ADDITION_INSTALLER_MS = "qc_ej_limit_addition_installer_ms";
        static final String KEY_EJ_LIMIT_ADDITION_SPECIAL_MS = "qc_ej_limit_addition_special_ms";
        static final String KEY_EJ_LIMIT_EXEMPTED_MS = "qc_ej_limit_exempted_ms";
        static final String KEY_EJ_LIMIT_FREQUENT_MS = "qc_ej_limit_frequent_ms";
        static final String KEY_EJ_LIMIT_RARE_MS = "qc_ej_limit_rare_ms";
        static final String KEY_EJ_LIMIT_RESTRICTED_MS = "qc_ej_limit_restricted_ms";
        static final String KEY_EJ_LIMIT_WORKING_MS = "qc_ej_limit_working_ms";
        static final String KEY_EJ_REWARD_INTERACTION_MS = "qc_ej_reward_interaction_ms";
        static final String KEY_EJ_REWARD_NOTIFICATION_SEEN_MS = "qc_ej_reward_notification_seen_ms";
        static final String KEY_EJ_REWARD_TOP_APP_MS = "qc_ej_reward_top_app_ms";
        static final String KEY_EJ_TOP_APP_TIME_CHUNK_SIZE_MS = "qc_ej_top_app_time_chunk_size_ms";
        static final String KEY_EJ_WINDOW_SIZE_MS = "qc_ej_window_size_ms";
        static final String KEY_IN_QUOTA_BUFFER_MS = "qc_in_quota_buffer_ms";
        static final String KEY_MAX_EXECUTION_TIME_MS = "qc_max_execution_time_ms";
        static final String KEY_MAX_JOB_COUNT_ACTIVE = "qc_max_job_count_active";
        static final String KEY_MAX_JOB_COUNT_EXEMPTED = "qc_max_job_count_exempted";
        static final String KEY_MAX_JOB_COUNT_FREQUENT = "qc_max_job_count_frequent";
        static final String KEY_MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW = "qc_max_job_count_per_rate_limiting_window";
        static final String KEY_MAX_JOB_COUNT_RARE = "qc_max_job_count_rare";
        static final String KEY_MAX_JOB_COUNT_RESTRICTED = "qc_max_job_count_restricted";
        static final String KEY_MAX_JOB_COUNT_WORKING = "qc_max_job_count_working";
        static final String KEY_MAX_SESSION_COUNT_ACTIVE = "qc_max_session_count_active";
        static final String KEY_MAX_SESSION_COUNT_EXEMPTED = "qc_max_session_count_exempted";
        static final String KEY_MAX_SESSION_COUNT_FREQUENT = "qc_max_session_count_frequent";
        static final String KEY_MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW = "qc_max_session_count_per_rate_limiting_window";
        static final String KEY_MAX_SESSION_COUNT_RARE = "qc_max_session_count_rare";
        static final String KEY_MAX_SESSION_COUNT_RESTRICTED = "qc_max_session_count_restricted";
        static final String KEY_MAX_SESSION_COUNT_WORKING = "qc_max_session_count_working";
        static final String KEY_MIN_QUOTA_CHECK_DELAY_MS = "qc_min_quota_check_delay_ms";
        static final String KEY_QUOTA_BUMP_ADDITIONAL_DURATION_MS = "qc_quota_bump_additional_duration_ms";
        static final String KEY_QUOTA_BUMP_ADDITIONAL_JOB_COUNT = "qc_quota_bump_additional_job_count";
        static final String KEY_QUOTA_BUMP_ADDITIONAL_SESSION_COUNT = "qc_quota_bump_additional_session_count";
        static final String KEY_QUOTA_BUMP_LIMIT = "qc_quota_bump_limit";
        static final String KEY_QUOTA_BUMP_WINDOW_SIZE_MS = "qc_quota_bump_window_size_ms";
        static final String KEY_RATE_LIMITING_WINDOW_MS = "qc_rate_limiting_window_ms";
        static final String KEY_TIMING_SESSION_COALESCING_DURATION_MS = "qc_timing_session_coalescing_duration_ms";
        static final String KEY_WINDOW_SIZE_ACTIVE_MS = "qc_window_size_active_ms";
        static final String KEY_WINDOW_SIZE_EXEMPTED_MS = "qc_window_size_exempted_ms";
        static final String KEY_WINDOW_SIZE_FREQUENT_MS = "qc_window_size_frequent_ms";
        static final String KEY_WINDOW_SIZE_RARE_MS = "qc_window_size_rare_ms";
        static final String KEY_WINDOW_SIZE_RESTRICTED_MS = "qc_window_size_restricted_ms";
        static final String KEY_WINDOW_SIZE_WORKING_MS = "qc_window_size_working_ms";
        public boolean mShouldReevaluateConstraints = false;
        public boolean mRateLimitingConstantsUpdated = false;
        public boolean mExecutionPeriodConstantsUpdated = false;
        public boolean mEJLimitConstantsUpdated = false;
        public boolean mQuotaBumpConstantsUpdated = false;
        public long ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS = 600000;
        public long ALLOWED_TIME_PER_PERIOD_ACTIVE_MS = 600000;
        public long ALLOWED_TIME_PER_PERIOD_WORKING_MS = 600000;
        public long ALLOWED_TIME_PER_PERIOD_FREQUENT_MS = 600000;
        public long ALLOWED_TIME_PER_PERIOD_RARE_MS = 600000;
        public long ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS = 600000;
        public long IN_QUOTA_BUFFER_MS = 30000;
        public long WINDOW_SIZE_EXEMPTED_MS = 600000;
        public long WINDOW_SIZE_ACTIVE_MS = 600000;
        public long WINDOW_SIZE_WORKING_MS = 7200000;
        public long WINDOW_SIZE_FREQUENT_MS = 28800000;
        public long WINDOW_SIZE_RARE_MS = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        public long WINDOW_SIZE_RESTRICTED_MS = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        public long MAX_EXECUTION_TIME_MS = BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS;
        public int MAX_JOB_COUNT_EXEMPTED = 75;
        public int MAX_JOB_COUNT_ACTIVE = 75;
        public int MAX_JOB_COUNT_WORKING = 120;
        public int MAX_JOB_COUNT_FREQUENT = 200;
        public int MAX_JOB_COUNT_RARE = 48;
        public int MAX_JOB_COUNT_RESTRICTED = 10;
        public long RATE_LIMITING_WINDOW_MS = 60000;
        public int MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW = 20;
        public int MAX_SESSION_COUNT_EXEMPTED = 75;
        public int MAX_SESSION_COUNT_ACTIVE = 75;
        public int MAX_SESSION_COUNT_WORKING = 10;
        public int MAX_SESSION_COUNT_FREQUENT = 8;
        public int MAX_SESSION_COUNT_RARE = 3;
        public int MAX_SESSION_COUNT_RESTRICTED = 1;
        public int MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW = 20;
        public long TIMING_SESSION_COALESCING_DURATION_MS = 5000;
        public long MIN_QUOTA_CHECK_DELAY_MS = 60000;
        public long EJ_LIMIT_EXEMPTED_MS = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        public long EJ_LIMIT_ACTIVE_MS = 1800000;
        public long EJ_LIMIT_WORKING_MS = 1800000;
        public long EJ_LIMIT_FREQUENT_MS = 600000;
        public long EJ_LIMIT_RARE_MS = 600000;
        public long EJ_LIMIT_RESTRICTED_MS = 300000;
        public long EJ_LIMIT_ADDITION_SPECIAL_MS = 900000;
        public long EJ_LIMIT_ADDITION_INSTALLER_MS = 1800000;
        public long EJ_WINDOW_SIZE_MS = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        public long EJ_TOP_APP_TIME_CHUNK_SIZE_MS = 30000;
        public long EJ_REWARD_TOP_APP_MS = 10000;
        public long EJ_REWARD_INTERACTION_MS = 15000;
        public long EJ_REWARD_NOTIFICATION_SEEN_MS = 0;
        public long EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS = 180000;
        public long EJ_GRACE_PERIOD_TOP_APP_MS = 60000;
        public long QUOTA_BUMP_ADDITIONAL_DURATION_MS = 60000;
        public int QUOTA_BUMP_ADDITIONAL_JOB_COUNT = 2;
        public int QUOTA_BUMP_ADDITIONAL_SESSION_COUNT = 1;
        public long QUOTA_BUMP_WINDOW_SIZE_MS = 28800000;
        public int QUOTA_BUMP_LIMIT = 8;

        public QcConstants() {
        }
    }

    public final class QcHandler extends Handler {
        public QcHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Code restructure failed: missing block: B:160:0x02f2, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:163:0x03b2, code lost:
        
            throw r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:187:0x0045, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:190:0x04ae, code lost:
        
            throw r0;
         */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r17) {
            /*
                Method dump skipped, instructions count: 1220
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.controllers.QuotaController.QcHandler.handleMessage(android.os.Message):void");
        }
    }

    public final class QcUidObserver extends UidObserver {
        public QcUidObserver() {
        }

        public final void onUidStateChanged(int i, int i2, long j, int i3) {
            QuotaController.this.mHandler.obtainMessage(3, i, i2).sendToTarget();
        }
    }

    final class QuotaBump implements TimedEvent {
        public final long eventTimeElapsed;

        public QuotaBump(long j) {
            this.eventTimeElapsed = j;
        }

        @Override // com.android.server.job.controllers.QuotaController.TimedEvent
        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.print("Quota bump @ ");
            indentingPrintWriter.print(this.eventTimeElapsed);
            indentingPrintWriter.println();
        }

        @Override // com.android.server.job.controllers.QuotaController.TimedEvent
        public final long getEndTimeElapsed() {
            return this.eventTimeElapsed;
        }
    }

    final class ShrinkableDebits {
        public long mDebitTally;
        public int mStandbyBucket;

        public final String toString() {
            StringBuilder sb = new StringBuilder("ShrinkableDebits { debit tally: ");
            sb.append(this.mDebitTally);
            sb.append(", bucket: ");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mStandbyBucket, sb, " }");
        }

        public final long transactLocked(long j) {
            long j2;
            if (j < 0) {
                long abs = Math.abs(j);
                long j3 = this.mDebitTally;
                if (abs > j3) {
                    j2 = j3 + j;
                    this.mDebitTally = Math.max(0L, this.mDebitTally + j);
                    return j2;
                }
            }
            j2 = 0;
            this.mDebitTally = Math.max(0L, this.mDebitTally + j);
            return j2;
        }
    }

    public final class StandbyTracker extends AppStandbyInternal.AppIdleStateChangeListener {
        public StandbyTracker() {
        }

        public final void onAppIdleStateChanged(final String str, final int i, boolean z, final int i2, int i3) {
            AppSchedulingModuleThread.getHandler().post(new Runnable() { // from class: com.android.server.job.controllers.QuotaController$StandbyTracker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    QuotaController.StandbyTracker standbyTracker = QuotaController.StandbyTracker.this;
                    int i4 = i2;
                    int i5 = i;
                    String str2 = str;
                    standbyTracker.getClass();
                    QuotaController.this.updateStandbyBucket(i5, str2, JobSchedulerService.standbyBucketToBucketIndex(i4));
                }
            });
        }

        public final void triggerTemporaryQuotaBump(String str, int i) {
            synchronized (QuotaController.this.mLock) {
                List list = (List) QuotaController.this.mTimingEvents.get(i, str);
                if (list != null && list.size() != 0) {
                    JobSchedulerService.sElapsedRealtimeClock.getClass();
                    list.add(new QuotaBump(SystemClock.elapsedRealtime()));
                    QuotaController.this.invalidateAllExecutionStatsLocked(i, str);
                    QuotaController.this.mHandler.obtainMessage(2, i, 0, str).sendToTarget();
                }
            }
        }
    }

    public final class TempAllowlistTracker implements PowerAllowlistInternal.TempAllowlistChangeListener {
        public TempAllowlistTracker() {
        }

        public final void onAppAdded(int i) {
            synchronized (QuotaController.this.mLock) {
                try {
                    JobSchedulerService.sElapsedRealtimeClock.getClass();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    QuotaController.this.mTempAllowlistCache.put(i, true);
                    ArraySet packagesForUidLocked = QuotaController.this.mService.getPackagesForUidLocked(i);
                    if (packagesForUidLocked != null) {
                        int userId = UserHandle.getUserId(i);
                        for (int size = packagesForUidLocked.size() - 1; size >= 0; size--) {
                            Timer timer = (Timer) QuotaController.this.mEJPkgTimers.get(userId, (String) packagesForUidLocked.valueAt(size));
                            if (timer != null) {
                                timer.onStateChangedLocked(elapsedRealtime, true);
                            }
                        }
                        ArraySet m628$$Nest$mmaybeUpdateConstraintForUidLocked = QuotaController.m628$$Nest$mmaybeUpdateConstraintForUidLocked(QuotaController.this, i);
                        if (m628$$Nest$mmaybeUpdateConstraintForUidLocked.size() > 0) {
                            QuotaController.this.mStateChangedListener.onControllerStateChanged(m628$$Nest$mmaybeUpdateConstraintForUidLocked);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onAppRemoved(int i) {
            synchronized (QuotaController.this.mLock) {
                JobSchedulerService.sElapsedRealtimeClock.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                QuotaController quotaController = QuotaController.this;
                long j = elapsedRealtime + quotaController.mEJGracePeriodTempAllowlistMs;
                quotaController.mTempAllowlistCache.delete(i);
                QuotaController.this.mTempAllowlistGraceCache.put(i, j);
                Message obtainMessage = QuotaController.this.mHandler.obtainMessage(6, i, 0);
                QuotaController quotaController2 = QuotaController.this;
                quotaController2.mHandler.sendMessageDelayed(obtainMessage, quotaController2.mEJGracePeriodTempAllowlistMs);
            }
        }
    }

    interface TimedEvent {
        void dump(IndentingPrintWriter indentingPrintWriter);

        long getEndTimeElapsed();
    }

    public final class TimedEventTooOldPredicate implements Predicate {
        public long mNowElapsed;

        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            return ((TimedEvent) obj).getEndTimeElapsed() <= this.mNowElapsed - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        }
    }

    public final class Timer {
        public int mBgJobCount;
        public long mDebitAdjustment;
        public final UserPackage mPkg;
        public final boolean mRegularJobTimer;
        public final ArraySet mRunningBgJobs = new ArraySet();
        public long mStartTimeElapsed;
        public final int mUid;

        public Timer(int i, int i2, String str, boolean z) {
            this.mPkg = UserPackage.of(i2, str);
            this.mUid = i;
            this.mRegularJobTimer = z;
        }

        public final void cancelCutoff() {
            QuotaController.this.mHandler.removeMessages(this.mRegularJobTimer ? 0 : 4, this.mPkg);
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter, JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda5) {
            indentingPrintWriter.print("Timer<");
            boolean z = this.mRegularJobTimer;
            indentingPrintWriter.print(z ? "REG" : "EJ");
            indentingPrintWriter.print(">{");
            indentingPrintWriter.print(this.mPkg);
            indentingPrintWriter.print("} ");
            if (isActive()) {
                indentingPrintWriter.print("started at ");
                indentingPrintWriter.print(this.mStartTimeElapsed);
                indentingPrintWriter.print(" (");
                JobSchedulerService.sElapsedRealtimeClock.getClass();
                indentingPrintWriter.print(SystemClock.elapsedRealtime() - this.mStartTimeElapsed);
                indentingPrintWriter.print("ms ago)");
            } else {
                indentingPrintWriter.print("NOT active");
            }
            indentingPrintWriter.print(", ");
            indentingPrintWriter.print(this.mBgJobCount);
            indentingPrintWriter.print(" running bg jobs");
            if (!z) {
                indentingPrintWriter.print(" (debit adj=");
                indentingPrintWriter.print(this.mDebitAdjustment);
                indentingPrintWriter.print(")");
            }
            indentingPrintWriter.println();
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mRunningBgJobs.size(); i++) {
                JobStatus jobStatus = (JobStatus) this.mRunningBgJobs.valueAt(i);
                if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                    indentingPrintWriter.println(jobStatus.toShortString());
                }
            }
            indentingPrintWriter.decreaseIndent();
        }

        public final void dump(ProtoOutputStream protoOutputStream, long j, JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda5) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1133871366146L, isActive());
            protoOutputStream.write(1112396529667L, this.mStartTimeElapsed);
            protoOutputStream.write(1120986464260L, this.mBgJobCount);
            for (int i = 0; i < this.mRunningBgJobs.size(); i++) {
                JobStatus jobStatus = (JobStatus) this.mRunningBgJobs.valueAt(i);
                if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                    jobStatus.writeToShortProto(protoOutputStream, 2246267895813L);
                }
            }
            protoOutputStream.end(start);
        }

        public final void emitSessionLocked(long j) {
            int i = this.mBgJobCount;
            if (i <= 0) {
                return;
            }
            TimingSession timingSession = new TimingSession(i, this.mStartTimeElapsed, j);
            UserPackage userPackage = this.mPkg;
            int i2 = userPackage.userId;
            String str = userPackage.packageName;
            boolean z = this.mRegularJobTimer;
            QuotaController.this.saveTimingSession(i2, str, timingSession, !z, this.mDebitAdjustment);
            this.mBgJobCount = 0;
            cancelCutoff();
            if (z) {
                UserPackage userPackage2 = this.mPkg;
                int i3 = userPackage2.userId;
                String str2 = userPackage2.packageName;
                QuotaController quotaController = QuotaController.this;
                quotaController.getClass();
                JobSchedulerService.sElapsedRealtimeClock.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                ExecutionStats[] executionStatsArr = (ExecutionStats[]) quotaController.mExecutionStatsCache.get(i3, str2);
                if (executionStatsArr == null) {
                    executionStatsArr = new ExecutionStats[quotaController.mBucketPeriodsMs.length];
                    quotaController.mExecutionStatsCache.add(i3, str2, executionStatsArr);
                }
                for (int i4 = 0; i4 < executionStatsArr.length; i4++) {
                    ExecutionStats executionStats = executionStatsArr[i4];
                    if (executionStats == null) {
                        executionStats = new ExecutionStats();
                        executionStatsArr[i4] = executionStats;
                    }
                    if (executionStats.sessionRateLimitExpirationTimeElapsed <= elapsedRealtime) {
                        executionStats.sessionRateLimitExpirationTimeElapsed = quotaController.mRateLimitingWindowMs + elapsedRealtime;
                        executionStats.sessionCountInRateLimitingWindow = 0;
                    }
                    executionStats.sessionCountInRateLimitingWindow++;
                }
            }
        }

        public final long getCurrentDuration(long j) {
            long j2;
            synchronized (QuotaController.this.mLock) {
                j2 = !isActive() ? 0L : (j - this.mStartTimeElapsed) + this.mDebitAdjustment;
            }
            return j2;
        }

        public final boolean isActive() {
            boolean z;
            synchronized (QuotaController.this.mLock) {
                z = this.mBgJobCount > 0;
            }
            return z;
        }

        public final void onStateChangedLocked(long j, boolean z) {
            if (z) {
                emitSessionLocked(j);
                return;
            }
            if (isActive() || !shouldTrackLocked() || this.mRunningBgJobs.size() <= 0) {
                return;
            }
            this.mStartTimeElapsed = j;
            this.mDebitAdjustment = 0L;
            int size = this.mRunningBgJobs.size();
            this.mBgJobCount = size;
            if (this.mRegularJobTimer) {
                UserPackage userPackage = this.mPkg;
                int i = userPackage.userId;
                String str = userPackage.packageName;
                QuotaController quotaController = QuotaController.this;
                quotaController.incrementJobCountLocked(i, str, size);
                UserPackage userPackage2 = this.mPkg;
                quotaController.invalidateAllExecutionStatsLocked(userPackage2.userId, userPackage2.packageName);
            }
            scheduleCutoff();
        }

        public final void scheduleCutoff() {
            long timeUntilEJQuotaConsumedLocked;
            synchronized (QuotaController.this.mLock) {
                try {
                    if (isActive()) {
                        Message obtainMessage = QuotaController.this.mHandler.obtainMessage(this.mRegularJobTimer ? 0 : 4, this.mPkg);
                        if (this.mRegularJobTimer) {
                            QuotaController quotaController = QuotaController.this;
                            UserPackage userPackage = this.mPkg;
                            timeUntilEJQuotaConsumedLocked = quotaController.getTimeUntilQuotaConsumedLocked(userPackage.userId, userPackage.packageName);
                        } else {
                            QuotaController quotaController2 = QuotaController.this;
                            UserPackage userPackage2 = this.mPkg;
                            timeUntilEJQuotaConsumedLocked = quotaController2.getTimeUntilEJQuotaConsumedLocked(userPackage2.userId, userPackage2.packageName);
                        }
                        if (QuotaController.DEBUG) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(this.mRegularJobTimer ? "Regular job" : "EJ");
                            sb.append(" for ");
                            sb.append(this.mPkg);
                            sb.append(" has ");
                            sb.append(timeUntilEJQuotaConsumedLocked);
                            sb.append("ms left.");
                            Slog.i("JobScheduler.Quota", sb.toString());
                        }
                        QuotaController.this.mHandler.sendMessageDelayed(obtainMessage, timeUntilEJQuotaConsumedLocked);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean shouldTrackLocked() {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            UserPackage userPackage = this.mPkg;
            int standbyBucketForPackage = JobSchedulerService.standbyBucketForPackage(userPackage.userId, userPackage.packageName, elapsedRealtime);
            boolean z = this.mRegularJobTimer;
            int i = this.mUid;
            QuotaController quotaController = QuotaController.this;
            boolean z2 = !z && quotaController.hasTempAllowlistExemptionLocked(i, standbyBucketForPackage, elapsedRealtime);
            boolean z3 = !z && (quotaController.mTopAppCache.get(i) || elapsedRealtime < quotaController.mTopAppGraceCache.get(i));
            if (QuotaController.DEBUG) {
                Slog.d("JobScheduler.Quota", "quotaFree=" + quotaController.isQuotaFreeLocked(standbyBucketForPackage) + " isFG=" + quotaController.mForegroundUids.get(i) + " tempEx=" + z2 + " topEx=" + z3);
            }
            return (quotaController.isQuotaFreeLocked(standbyBucketForPackage) || quotaController.mForegroundUids.get(i) || z2 || z3) ? false : true;
        }

        public final void stopTrackingJob(JobStatus jobStatus) {
            boolean z = QuotaController.DEBUG;
            if (z) {
                Slog.v("JobScheduler.Quota", "Stopping tracking of " + jobStatus.toShortString());
            }
            synchronized (QuotaController.this.mLock) {
                try {
                    if (this.mRunningBgJobs.size() == 0) {
                        if (z) {
                            Slog.d("JobScheduler.Quota", "Timer isn't tracking any jobs but still told to stop");
                        }
                        return;
                    }
                    JobSchedulerService.sElapsedRealtimeClock.getClass();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    UserPackage userPackage = this.mPkg;
                    int standbyBucketForPackage = JobSchedulerService.standbyBucketForPackage(userPackage.userId, userPackage.packageName, elapsedRealtime);
                    if (this.mRunningBgJobs.remove(jobStatus) && this.mRunningBgJobs.size() == 0 && !QuotaController.this.isQuotaFreeLocked(standbyBucketForPackage)) {
                        emitSessionLocked(elapsedRealtime);
                        cancelCutoff();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class TimerChargingUpdateFunctor implements Consumer {
        public boolean mIsCharging;
        public long mNowElapsed;

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            Timer timer = (Timer) obj;
            UserPackage userPackage = timer.mPkg;
            if (JobSchedulerService.standbyBucketForPackage(userPackage.userId, userPackage.packageName, this.mNowElapsed) != 5) {
                timer.onStateChangedLocked(this.mNowElapsed, this.mIsCharging);
            }
        }
    }

    final class TimingSession implements TimedEvent {
        public final int bgJobCount;
        public final long endTimeElapsed;
        public final int mHashCode;
        public final long startTimeElapsed;

        public TimingSession(int i, long j, long j2) {
            this.startTimeElapsed = j;
            this.endTimeElapsed = j2;
            this.bgJobCount = i;
            this.mHashCode = ((QuotaController.m629$$Nest$smhashLong(j2) + (QuotaController.m629$$Nest$smhashLong(j) * 31)) * 31) + i;
        }

        @Override // com.android.server.job.controllers.QuotaController.TimedEvent
        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            long j = this.startTimeElapsed;
            indentingPrintWriter.print(j);
            indentingPrintWriter.print(" -> ");
            long j2 = this.endTimeElapsed;
            indentingPrintWriter.print(j2);
            indentingPrintWriter.print(" (");
            indentingPrintWriter.print(j2 - j);
            indentingPrintWriter.print("), ");
            indentingPrintWriter.print(this.bgJobCount);
            indentingPrintWriter.print(" bg jobs.");
            indentingPrintWriter.println();
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof TimingSession)) {
                return false;
            }
            TimingSession timingSession = (TimingSession) obj;
            return this.startTimeElapsed == timingSession.startTimeElapsed && this.endTimeElapsed == timingSession.endTimeElapsed && this.bgJobCount == timingSession.bgJobCount;
        }

        @Override // com.android.server.job.controllers.QuotaController.TimedEvent
        public final long getEndTimeElapsed() {
            return this.endTimeElapsed;
        }

        public final int hashCode() {
            return this.mHashCode;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("TimingSession{");
            sb.append(this.startTimeElapsed);
            sb.append("->");
            sb.append(this.endTimeElapsed);
            sb.append(", ");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.bgJobCount, sb, "}");
        }
    }

    public final class TopAppTimer {
        public final SparseArray mActivities = new SparseArray();
        public final UserPackage mPkg;
        public long mStartTimeElapsed;

        public TopAppTimer(int i, String str) {
            this.mPkg = UserPackage.of(i, str);
        }

        public final void processEventLocked(UsageEvents.Event event) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            int eventType = event.getEventType();
            if (eventType == 1) {
                if (this.mActivities.size() == 0) {
                    this.mStartTimeElapsed = elapsedRealtime;
                }
                this.mActivities.put(event.mInstanceId, event);
                return;
            }
            if ((eventType == 2 || eventType == 23 || eventType == 24) && ((UsageEvents.Event) this.mActivities.removeReturnOld(event.mInstanceId)) != null && this.mActivities.size() == 0) {
                QuotaController quotaController = QuotaController.this;
                long j = quotaController.mEJRewardTopAppMs;
                long j2 = elapsedRealtime - this.mStartTimeElapsed;
                long j3 = quotaController.mEJTopAppTimeChunkSizeMs;
                int i = (int) (j2 / j3);
                if (j2 % j3 >= 1000) {
                    i++;
                }
                long j4 = j * i;
                if (QuotaController.DEBUG) {
                    StringBuilder sb = new StringBuilder("Crediting ");
                    sb.append(this.mPkg);
                    sb.append(" ");
                    sb.append(j4);
                    sb.append("ms for ");
                    long j5 = elapsedRealtime - this.mStartTimeElapsed;
                    long j6 = quotaController.mEJTopAppTimeChunkSizeMs;
                    int i2 = (int) (j5 / j6);
                    if (j5 % j6 >= 1000) {
                        i2++;
                    }
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, i2, " time chunks", "JobScheduler.Quota");
                }
                UserPackage userPackage = this.mPkg;
                ShrinkableDebits eJDebitsLocked = quotaController.getEJDebitsLocked(userPackage.userId, userPackage.packageName);
                UserPackage userPackage2 = this.mPkg;
                if (QuotaController.this.transactQuotaLocked(userPackage2.userId, userPackage2.packageName, elapsedRealtime, eJDebitsLocked, j4)) {
                    UserPackage userPackage3 = this.mPkg;
                    quotaController.mStateChangedListener.onControllerStateChanged(quotaController.maybeUpdateConstraintForPkgLocked(userPackage3.userId, userPackage3.packageName, elapsedRealtime));
                }
            }
        }
    }

    public final class UidConstraintUpdater implements Consumer {
        public final SparseArrayMap mToScheduleStartAlarms = new SparseArrayMap();
        public final ArraySet changedJobs = new ArraySet();
        public long mUpdateTimeElapsed = 0;

        public UidConstraintUpdater() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            JobStatus jobStatus = (JobStatus) obj;
            boolean isWithinEJQuotaLocked = jobStatus.isRequestedExpeditedJob() ? QuotaController.this.isWithinEJQuotaLocked(jobStatus) : false;
            QuotaController quotaController = QuotaController.this;
            if (quotaController.setConstraintSatisfied(jobStatus, this.mUpdateTimeElapsed, quotaController.isWithinQuotaLocked(jobStatus), isWithinEJQuotaLocked)) {
                this.changedJobs.add(jobStatus);
            }
            if (QuotaController.this.setExpeditedQuotaApproved(jobStatus, isWithinEJQuotaLocked)) {
                this.changedJobs.add(jobStatus);
            }
            int i = jobStatus.standbyBucket;
            int i2 = jobStatus.sourceUserId;
            String str = jobStatus.sourcePackageName;
            if (isWithinEJQuotaLocked && QuotaController.this.isWithinQuotaLocked(i2, str, i)) {
                QuotaController.this.mInQuotaAlarmQueue.removeAlarmForKey(UserPackage.of(i2, str));
            } else {
                this.mToScheduleStartAlarms.add(i2, str, Integer.valueOf(i));
            }
        }
    }

    public final class UsageEventTracker implements UsageStatsManagerInternal.UsageEventListener {
        public UsageEventTracker() {
        }

        @Override // android.app.usage.UsageStatsManagerInternal.UsageEventListener
        public final void onUsageEvent(int i, UsageEvents.Event event) {
            int eventType = event.getEventType();
            if (eventType == 1 || eventType == 2 || eventType == 7 || eventType == 12 || eventType == 9 || eventType == 10 || eventType == 23 || eventType == 24) {
                QuotaController.this.mHandler.obtainMessage(5, i, 0, event).sendToTarget();
            } else if (QuotaController.DEBUG) {
                Slog.d("JobScheduler.Quota", "Dropping usage event " + event.getEventType());
            }
        }
    }

    /* renamed from: -$$Nest$mgrantRewardForInstantEvent, reason: not valid java name */
    public static void m627$$Nest$mgrantRewardForInstantEvent(QuotaController quotaController, int i, String str, long j) {
        if (j == 0) {
            quotaController.getClass();
            return;
        }
        synchronized (quotaController.mLock) {
            try {
                JobSchedulerService.sElapsedRealtimeClock.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (quotaController.transactQuotaLocked(i, str, elapsedRealtime, quotaController.getEJDebitsLocked(i, str), j)) {
                    quotaController.mStateChangedListener.onControllerStateChanged(quotaController.maybeUpdateConstraintForPkgLocked(i, str, elapsedRealtime));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mmaybeUpdateConstraintForUidLocked, reason: not valid java name */
    public static ArraySet m628$$Nest$mmaybeUpdateConstraintForUidLocked(QuotaController quotaController, int i) {
        UidConstraintUpdater uidConstraintUpdater = quotaController.mUpdateUidConstraints;
        uidConstraintUpdater.getClass();
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        uidConstraintUpdater.mUpdateTimeElapsed = SystemClock.elapsedRealtime();
        uidConstraintUpdater.changedJobs.clear();
        quotaController.mService.mJobs.forEachJobForSourceUid(i, uidConstraintUpdater);
        for (int i2 = 0; i2 < uidConstraintUpdater.mToScheduleStartAlarms.numMaps(); i2++) {
            int keyAt = uidConstraintUpdater.mToScheduleStartAlarms.keyAt(i2);
            for (int i3 = 0; i3 < uidConstraintUpdater.mToScheduleStartAlarms.numElementsForKey(keyAt); i3++) {
                String str = (String) uidConstraintUpdater.mToScheduleStartAlarms.keyAt(i2, i3);
                QuotaController.this.maybeScheduleStartAlarmLocked(keyAt, str, ((Integer) uidConstraintUpdater.mToScheduleStartAlarms.get(keyAt, str)).intValue());
            }
        }
        uidConstraintUpdater.mToScheduleStartAlarms.clear();
        return uidConstraintUpdater.changedJobs;
    }

    /* renamed from: -$$Nest$smhashLong, reason: not valid java name */
    public static int m629$$Nest$smhashLong(long j) {
        return (int) (j ^ (j >>> 32));
    }

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.Quota", 3);
    }

    public QuotaController(JobSchedulerService jobSchedulerService, BackgroundJobsController backgroundJobsController, ConnectivityController connectivityController) {
        super(jobSchedulerService);
        this.mTrackedJobs = new SparseArrayMap();
        this.mPkgTimers = new SparseArrayMap();
        this.mEJPkgTimers = new SparseArrayMap();
        this.mTimingEvents = new SparseArrayMap();
        this.mEJTimingSessions = new SparseArrayMap();
        this.mExecutionStatsCache = new SparseArrayMap();
        this.mEJStats = new SparseArrayMap();
        this.mTopAppTrackers = new SparseArrayMap();
        this.mForegroundUids = new SparseBooleanArray();
        this.mTopStartedJobs = new ArraySet();
        this.mTempAllowlistCache = new SparseBooleanArray();
        this.mTempAllowlistGraceCache = new SparseLongArray();
        this.mTopAppCache = new SparseBooleanArray();
        this.mTopAppGraceCache = new SparseLongArray();
        this.mAllowedTimePerPeriodMs = new long[]{600000, 600000, 600000, 600000, 0, 600000, 600000};
        this.mMaxExecutionTimeMs = BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS;
        this.mQuotaBufferMs = 30000L;
        this.mMaxExecutionTimeIntoQuotaMs = 14370000L;
        this.mRateLimitingWindowMs = 60000L;
        this.mMaxJobCountPerRateLimitingWindow = 20;
        this.mMaxSessionCountPerRateLimitingWindow = 20;
        this.mNextCleanupTimeElapsed = 0L;
        this.mSessionCleanupAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.android.server.job.controllers.QuotaController.1
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                QuotaController.this.mHandler.obtainMessage(1).sendToTarget();
            }
        };
        this.mBucketPeriodsMs = new long[]{600000, 7200000, 28800000, BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, 0, BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, 600000};
        this.mMaxBucketJobCounts = new int[]{75, 120, 200, 48, 0, 10, 75};
        this.mMaxBucketSessionCounts = new int[]{75, 10, 8, 3, 0, 1, 75};
        this.mTimingSessionCoalescingDurationMs = 5000L;
        this.mEJLimitsMs = new long[]{1800000, 1800000, 600000, 600000, 0, 300000, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS};
        this.mEjLimitAdditionInstallerMs = 1800000L;
        this.mEjLimitAdditionSpecialMs = 900000L;
        this.mEJLimitWindowSizeMs = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        this.mEJTopAppTimeChunkSizeMs = 30000L;
        this.mEJRewardTopAppMs = 10000L;
        this.mEJRewardInteractionMs = 15000L;
        this.mEJRewardNotificationSeenMs = 0L;
        this.mEJGracePeriodTempAllowlistMs = 180000L;
        this.mEJGracePeriodTopAppMs = 60000L;
        this.mQuotaBumpAdditionalDurationMs = 60000L;
        this.mQuotaBumpAdditionalJobCount = 2;
        this.mQuotaBumpAdditionalSessionCount = 1;
        this.mQuotaBumpWindowSizeMs = 28800000L;
        this.mQuotaBumpLimit = 8;
        this.mSystemInstallers = new SparseSetArray();
        EarliestEndTimeFunctor earliestEndTimeFunctor = new EarliestEndTimeFunctor();
        earliestEndTimeFunctor.earliestEndElapsed = Long.MAX_VALUE;
        this.mEarliestEndTimeFunctor = earliestEndTimeFunctor;
        this.mTimerChargingUpdateFunctor = new TimerChargingUpdateFunctor();
        this.mUpdateUidConstraints = new UidConstraintUpdater();
        this.mTimedEventTooOld = new TimedEventTooOldPredicate();
        this.mDeleteOldEventsFunctor = new QuotaController$$ExternalSyntheticLambda0(0, this);
        this.mHandler = new QcHandler(AppSchedulingModuleThread.get().getLooper());
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        this.mQcConstants = new QcConstants();
        this.mBackgroundJobsController = backgroundJobsController;
        this.mConnectivityController = connectivityController;
        this.mInQuotaAlarmQueue = new InQuotaAlarmQueue(this.mContext, AppSchedulingModuleThread.get().getLooper());
        ((AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class)).addListener(new StandbyTracker());
        ((UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class)).registerListener(new UsageEventTracker());
        ((PowerAllowlistInternal) LocalServices.getService(PowerAllowlistInternal.class)).registerTempAllowlistChangeListener(new TempAllowlistTracker());
        try {
            ActivityManager.getService().registerUidObserver(new QcUidObserver(), 1, 4, (String) null);
            ActivityManager.getService().registerUidObserver(new QcUidObserver(), 1, 2, (String) null);
        } catch (RemoteException unused) {
        }
    }

    public final void cacheInstallerPackagesLocked(int i) {
        List installedPackagesAsUser = this.mContext.getPackageManager().getInstalledPackagesAsUser(4993024, i);
        for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
            PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(size);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (ArrayUtils.indexOf(packageInfo.requestedPermissions, "android.permission.INSTALL_PACKAGES") >= 0 && applicationInfo != null && this.mContext.checkPermission("android.permission.INSTALL_PACKAGES", -1, applicationInfo.uid) == 0) {
                this.mSystemInstallers.add(UserHandle.getUserId(applicationInfo.uid), packageInfo.packageName);
            }
        }
    }

    public final long calculateTimeUntilQuotaConsumedLocked(List list, long j, long j2, boolean z) {
        long j3;
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mQuotaBumpWindowSizeMs;
        int size = list.size();
        if (z) {
            int i = 0;
            j3 = j2;
            for (int i2 = size - 1; i2 >= 0; i2--) {
                TimedEvent timedEvent = (TimedEvent) list.get(i2);
                if (timedEvent instanceof QuotaBump) {
                    if (((QuotaBump) timedEvent).eventTimeElapsed < elapsedRealtime) {
                        break;
                    }
                    int i3 = i + 1;
                    if (i >= this.mQuotaBumpLimit) {
                        break;
                    }
                    j3 += this.mQuotaBumpAdditionalDurationMs;
                    i = i3;
                }
            }
        } else {
            j3 = j2;
        }
        long j4 = 0;
        long j5 = j;
        for (int i4 = 0; i4 < size; i4++) {
            TimedEvent timedEvent2 = (TimedEvent) list.get(i4);
            if (!(timedEvent2 instanceof QuotaBump)) {
                TimingSession timingSession = (TimingSession) timedEvent2;
                long j6 = timingSession.endTimeElapsed;
                if (j6 < j) {
                    continue;
                } else {
                    long j7 = timingSession.startTimeElapsed;
                    if (j7 <= j) {
                        j4 = (j6 - j) + j4;
                        j5 = j6;
                    } else {
                        long j8 = j7 - j5;
                        if (j8 > j3) {
                            break;
                        }
                        long j9 = (j6 - j7) + j8 + j4;
                        j3 -= j8;
                        j5 = j6;
                        j4 = j9;
                    }
                }
            }
        }
        long j10 = j4 + j3;
        if (j10 > this.mMaxExecutionTimeMs) {
            Slog.wtf("JobScheduler.Quota", "Calculated quota consumed time too high: " + j10);
        }
        return j10;
    }

    public final void clearAppStatsLocked(int i, String str) {
        this.mTrackedJobs.delete(i, str);
        Timer timer = (Timer) this.mPkgTimers.delete(i, str);
        if (timer != null && timer.isActive()) {
            Slog.e("JobScheduler.Quota", "clearAppStats called before Timer turned off.");
            timer.mRunningBgJobs.clear();
            timer.cancelCutoff();
        }
        Timer timer2 = (Timer) this.mEJPkgTimers.delete(i, str);
        if (timer2 != null && timer2.isActive()) {
            Slog.e("JobScheduler.Quota", "clearAppStats called before EJ Timer turned off.");
            timer2.mRunningBgJobs.clear();
            timer2.cancelCutoff();
        }
        this.mTimingEvents.delete(i, str);
        this.mEJTimingSessions.delete(i, str);
        this.mInQuotaAlarmQueue.removeAlarmForKey(UserPackage.of(i, str));
        this.mExecutionStatsCache.delete(i, str);
        this.mEJStats.delete(i, str);
        this.mTopAppTrackers.delete(i, str);
    }

    public void deleteObsoleteSessionsLocked() {
        TimedEventTooOldPredicate timedEventTooOldPredicate = this.mTimedEventTooOld;
        timedEventTooOldPredicate.getClass();
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        timedEventTooOldPredicate.mNowElapsed = SystemClock.elapsedRealtime();
        this.mTimingEvents.forEach(this.mDeleteOldEventsFunctor);
        for (int i = 0; i < this.mEJTimingSessions.numMaps(); i++) {
            int keyAt = this.mEJTimingSessions.keyAt(i);
            for (int i2 = 0; i2 < this.mEJTimingSessions.numElementsForKey(keyAt); i2++) {
                String str = (String) this.mEJTimingSessions.keyAt(i, i2);
                ShrinkableDebits eJDebitsLocked = getEJDebitsLocked(keyAt, str);
                List list = (List) this.mEJTimingSessions.get(keyAt, str);
                if (list != null) {
                    while (list.size() > 0) {
                        TimingSession timingSession = (TimingSession) list.get(0);
                        if (timingSession.getEndTimeElapsed() <= timedEventTooOldPredicate.mNowElapsed - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) {
                            eJDebitsLocked.transactLocked(-(timingSession.endTimeElapsed - timingSession.startTimeElapsed));
                            list.remove(0);
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpConstants(IndentingPrintWriter indentingPrintWriter) {
        QcConstants qcConstants = this.mQcConstants;
        qcConstants.getClass();
        indentingPrintWriter.println();
        indentingPrintWriter.println("QuotaController:");
        indentingPrintWriter.increaseIndent();
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS, indentingPrintWriter, "qc_allowed_time_per_period_exempted_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.ALLOWED_TIME_PER_PERIOD_ACTIVE_MS, indentingPrintWriter, "qc_allowed_time_per_period_active_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.ALLOWED_TIME_PER_PERIOD_WORKING_MS, indentingPrintWriter, "qc_allowed_time_per_period_working_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.ALLOWED_TIME_PER_PERIOD_FREQUENT_MS, indentingPrintWriter, "qc_allowed_time_per_period_frequent_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.ALLOWED_TIME_PER_PERIOD_RARE_MS, indentingPrintWriter, "qc_allowed_time_per_period_rare_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS, indentingPrintWriter, "qc_allowed_time_per_period_restricted_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.IN_QUOTA_BUFFER_MS, indentingPrintWriter, "qc_in_quota_buffer_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.WINDOW_SIZE_EXEMPTED_MS, indentingPrintWriter, "qc_window_size_exempted_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.WINDOW_SIZE_ACTIVE_MS, indentingPrintWriter, "qc_window_size_active_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.WINDOW_SIZE_WORKING_MS, indentingPrintWriter, "qc_window_size_working_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.WINDOW_SIZE_FREQUENT_MS, indentingPrintWriter, "qc_window_size_frequent_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.WINDOW_SIZE_RARE_MS, indentingPrintWriter, "qc_window_size_rare_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.WINDOW_SIZE_RESTRICTED_MS, indentingPrintWriter, "qc_window_size_restricted_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_EXECUTION_TIME_MS, indentingPrintWriter, "qc_max_execution_time_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_JOB_COUNT_EXEMPTED, indentingPrintWriter, "qc_max_job_count_exempted");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_JOB_COUNT_ACTIVE, indentingPrintWriter, "qc_max_job_count_active");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_JOB_COUNT_WORKING, indentingPrintWriter, "qc_max_job_count_working");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_JOB_COUNT_FREQUENT, indentingPrintWriter, "qc_max_job_count_frequent");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_JOB_COUNT_RARE, indentingPrintWriter, "qc_max_job_count_rare");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_JOB_COUNT_RESTRICTED, indentingPrintWriter, "qc_max_job_count_restricted");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.RATE_LIMITING_WINDOW_MS, indentingPrintWriter, "qc_rate_limiting_window_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW, indentingPrintWriter, "qc_max_job_count_per_rate_limiting_window");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_SESSION_COUNT_EXEMPTED, indentingPrintWriter, "qc_max_session_count_exempted");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_SESSION_COUNT_ACTIVE, indentingPrintWriter, "qc_max_session_count_active");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_SESSION_COUNT_WORKING, indentingPrintWriter, "qc_max_session_count_working");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_SESSION_COUNT_FREQUENT, indentingPrintWriter, "qc_max_session_count_frequent");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_SESSION_COUNT_RARE, indentingPrintWriter, "qc_max_session_count_rare");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_SESSION_COUNT_RESTRICTED, indentingPrintWriter, "qc_max_session_count_restricted");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW, indentingPrintWriter, "qc_max_session_count_per_rate_limiting_window");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.TIMING_SESSION_COALESCING_DURATION_MS, indentingPrintWriter, "qc_timing_session_coalescing_duration_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.MIN_QUOTA_CHECK_DELAY_MS, indentingPrintWriter, "qc_min_quota_check_delay_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_LIMIT_EXEMPTED_MS, indentingPrintWriter, "qc_ej_limit_exempted_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_LIMIT_ACTIVE_MS, indentingPrintWriter, "qc_ej_limit_active_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_LIMIT_WORKING_MS, indentingPrintWriter, "qc_ej_limit_working_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_LIMIT_FREQUENT_MS, indentingPrintWriter, "qc_ej_limit_frequent_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_LIMIT_RARE_MS, indentingPrintWriter, "qc_ej_limit_rare_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_LIMIT_RESTRICTED_MS, indentingPrintWriter, "qc_ej_limit_restricted_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_LIMIT_ADDITION_INSTALLER_MS, indentingPrintWriter, "qc_ej_limit_addition_installer_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_LIMIT_ADDITION_SPECIAL_MS, indentingPrintWriter, "qc_ej_limit_addition_special_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_WINDOW_SIZE_MS, indentingPrintWriter, "qc_ej_window_size_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_TOP_APP_TIME_CHUNK_SIZE_MS, indentingPrintWriter, "qc_ej_top_app_time_chunk_size_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_REWARD_TOP_APP_MS, indentingPrintWriter, "qc_ej_reward_top_app_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_REWARD_INTERACTION_MS, indentingPrintWriter, "qc_ej_reward_interaction_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_REWARD_NOTIFICATION_SEEN_MS, indentingPrintWriter, "qc_ej_reward_notification_seen_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS, indentingPrintWriter, "qc_ej_grace_period_temp_allowlist_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.EJ_GRACE_PERIOD_TOP_APP_MS, indentingPrintWriter, "qc_ej_grace_period_top_app_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.QUOTA_BUMP_ADDITIONAL_DURATION_MS, indentingPrintWriter, "qc_quota_bump_additional_duration_ms");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.QUOTA_BUMP_ADDITIONAL_JOB_COUNT, indentingPrintWriter, "qc_quota_bump_additional_job_count");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.QUOTA_BUMP_ADDITIONAL_SESSION_COUNT, indentingPrintWriter, "qc_quota_bump_additional_session_count");
        JobSchedulerService$Constants$$ExternalSyntheticOutline0.m(qcConstants.QUOTA_BUMP_WINDOW_SIZE_MS, indentingPrintWriter, "qc_quota_bump_window_size_ms");
        indentingPrintWriter.print("qc_quota_bump_limit", Integer.valueOf(qcConstants.QUOTA_BUMP_LIMIT)).println();
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpConstants(ProtoOutputStream protoOutputStream) {
        QcConstants qcConstants = this.mQcConstants;
        qcConstants.getClass();
        long start = protoOutputStream.start(1146756268056L);
        protoOutputStream.write(1112396529666L, qcConstants.IN_QUOTA_BUFFER_MS);
        protoOutputStream.write(1112396529667L, qcConstants.WINDOW_SIZE_ACTIVE_MS);
        protoOutputStream.write(1112396529668L, qcConstants.WINDOW_SIZE_WORKING_MS);
        protoOutputStream.write(1112396529669L, qcConstants.WINDOW_SIZE_FREQUENT_MS);
        protoOutputStream.write(1112396529670L, qcConstants.WINDOW_SIZE_RARE_MS);
        protoOutputStream.write(1112396529684L, qcConstants.WINDOW_SIZE_RESTRICTED_MS);
        protoOutputStream.write(1112396529671L, qcConstants.MAX_EXECUTION_TIME_MS);
        protoOutputStream.write(1120986464264L, qcConstants.MAX_JOB_COUNT_ACTIVE);
        protoOutputStream.write(1120986464265L, qcConstants.MAX_JOB_COUNT_WORKING);
        protoOutputStream.write(1120986464266L, qcConstants.MAX_JOB_COUNT_FREQUENT);
        protoOutputStream.write(1120986464267L, qcConstants.MAX_JOB_COUNT_RARE);
        protoOutputStream.write(1120986464277L, qcConstants.MAX_JOB_COUNT_RESTRICTED);
        protoOutputStream.write(1120986464275L, qcConstants.RATE_LIMITING_WINDOW_MS);
        protoOutputStream.write(1120986464268L, qcConstants.MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW);
        protoOutputStream.write(1120986464269L, qcConstants.MAX_SESSION_COUNT_ACTIVE);
        protoOutputStream.write(1120986464270L, qcConstants.MAX_SESSION_COUNT_WORKING);
        protoOutputStream.write(1120986464271L, qcConstants.MAX_SESSION_COUNT_FREQUENT);
        protoOutputStream.write(1120986464272L, qcConstants.MAX_SESSION_COUNT_RARE);
        protoOutputStream.write(1120986464278L, qcConstants.MAX_SESSION_COUNT_RESTRICTED);
        protoOutputStream.write(1120986464273L, qcConstants.MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW);
        protoOutputStream.write(1112396529682L, qcConstants.TIMING_SESSION_COALESCING_DURATION_MS);
        protoOutputStream.write(1112396529687L, qcConstants.MIN_QUOTA_CHECK_DELAY_MS);
        protoOutputStream.write(1112396529688L, qcConstants.EJ_LIMIT_ACTIVE_MS);
        protoOutputStream.write(1112396529689L, qcConstants.EJ_LIMIT_WORKING_MS);
        protoOutputStream.write(1112396529690L, qcConstants.EJ_LIMIT_FREQUENT_MS);
        protoOutputStream.write(1112396529691L, qcConstants.EJ_LIMIT_RARE_MS);
        protoOutputStream.write(1112396529692L, qcConstants.EJ_LIMIT_RESTRICTED_MS);
        protoOutputStream.write(1112396529693L, qcConstants.EJ_WINDOW_SIZE_MS);
        protoOutputStream.write(1112396529694L, qcConstants.EJ_TOP_APP_TIME_CHUNK_SIZE_MS);
        protoOutputStream.write(1112396529695L, qcConstants.EJ_REWARD_TOP_APP_MS);
        protoOutputStream.write(1112396529696L, qcConstants.EJ_REWARD_INTERACTION_MS);
        protoOutputStream.write(1112396529697L, qcConstants.EJ_REWARD_NOTIFICATION_SEEN_MS);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.StateController
    @NeverCompile
    public final void dumpControllerStateLocked(IndentingPrintWriter indentingPrintWriter, JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda5) {
        StringBuilder sb = new StringBuilder("Current elapsed time: ");
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        sb.append(SystemClock.elapsedRealtime());
        indentingPrintWriter.println(sb.toString());
        indentingPrintWriter.println();
        indentingPrintWriter.print("Foreground UIDs: ");
        indentingPrintWriter.println(this.mForegroundUids.toString());
        indentingPrintWriter.println();
        indentingPrintWriter.print("Cached top apps: ");
        indentingPrintWriter.println(this.mTopAppCache.toString());
        indentingPrintWriter.print("Cached top app grace period: ");
        indentingPrintWriter.println(this.mTopAppGraceCache.toString());
        indentingPrintWriter.print("Cached temp allowlist: ");
        indentingPrintWriter.println(this.mTempAllowlistCache.toString());
        indentingPrintWriter.print("Cached temp allowlist grace period: ");
        indentingPrintWriter.println(this.mTempAllowlistGraceCache.toString());
        indentingPrintWriter.println();
        indentingPrintWriter.println("Special apps:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("System installers={");
        for (int i = 0; i < this.mSystemInstallers.size(); i++) {
            if (i > 0) {
                indentingPrintWriter.print(", ");
            }
            indentingPrintWriter.print(this.mSystemInstallers.keyAt(i));
            indentingPrintWriter.print("->");
            indentingPrintWriter.print(this.mSystemInstallers.get(i));
        }
        indentingPrintWriter.println("}");
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mTrackedJobs.forEach(new QuotaController$$ExternalSyntheticLambda4(this, jobSchedulerService$$ExternalSyntheticLambda5, indentingPrintWriter));
        indentingPrintWriter.println();
        for (int i2 = 0; i2 < this.mPkgTimers.numMaps(); i2++) {
            int keyAt = this.mPkgTimers.keyAt(i2);
            for (int i3 = 0; i3 < this.mPkgTimers.numElementsForKey(keyAt); i3++) {
                String str = (String) this.mPkgTimers.keyAt(i2, i3);
                ((Timer) this.mPkgTimers.valueAt(i2, i3)).dump(indentingPrintWriter, jobSchedulerService$$ExternalSyntheticLambda5);
                indentingPrintWriter.println();
                List list = (List) this.mTimingEvents.get(keyAt, str);
                if (list != null) {
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("Saved events:");
                    indentingPrintWriter.increaseIndent();
                    for (int size = list.size() - 1; size >= 0; size--) {
                        ((TimedEvent) list.get(size)).dump(indentingPrintWriter);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
            }
        }
        indentingPrintWriter.println();
        for (int i4 = 0; i4 < this.mEJPkgTimers.numMaps(); i4++) {
            int keyAt2 = this.mEJPkgTimers.keyAt(i4);
            for (int i5 = 0; i5 < this.mEJPkgTimers.numElementsForKey(keyAt2); i5++) {
                String str2 = (String) this.mEJPkgTimers.keyAt(i4, i5);
                ((Timer) this.mEJPkgTimers.valueAt(i4, i5)).dump(indentingPrintWriter, jobSchedulerService$$ExternalSyntheticLambda5);
                indentingPrintWriter.println();
                List list2 = (List) this.mEJTimingSessions.get(keyAt2, str2);
                if (list2 != null) {
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("Saved sessions:");
                    indentingPrintWriter.increaseIndent();
                    for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                        ((TimedEvent) list2.get(size2)).dump(indentingPrintWriter);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
            }
        }
        indentingPrintWriter.println();
        this.mTopAppTrackers.forEach(new QuotaController$$ExternalSyntheticLambda0(1, indentingPrintWriter));
        indentingPrintWriter.println();
        indentingPrintWriter.println("Cached execution stats:");
        indentingPrintWriter.increaseIndent();
        for (int i6 = 0; i6 < this.mExecutionStatsCache.numMaps(); i6++) {
            int keyAt3 = this.mExecutionStatsCache.keyAt(i6);
            for (int i7 = 0; i7 < this.mExecutionStatsCache.numElementsForKey(keyAt3); i7++) {
                String str3 = (String) this.mExecutionStatsCache.keyAt(i6, i7);
                ExecutionStats[] executionStatsArr = (ExecutionStats[]) this.mExecutionStatsCache.valueAt(i6, i7);
                indentingPrintWriter.println(StateController.packageToString(keyAt3, str3));
                indentingPrintWriter.increaseIndent();
                for (int i8 = 0; i8 < executionStatsArr.length; i8++) {
                    ExecutionStats executionStats = executionStatsArr[i8];
                    if (executionStats != null) {
                        indentingPrintWriter.print(JobStatus.bucketName(i8));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(executionStats);
                    }
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("EJ debits:");
        indentingPrintWriter.increaseIndent();
        for (int i9 = 0; i9 < this.mEJStats.numMaps(); i9++) {
            int keyAt4 = this.mEJStats.keyAt(i9);
            for (int i10 = 0; i10 < this.mEJStats.numElementsForKey(keyAt4); i10++) {
                String str4 = (String) this.mEJStats.keyAt(i9, i10);
                ShrinkableDebits shrinkableDebits = (ShrinkableDebits) this.mEJStats.valueAt(i9, i10);
                indentingPrintWriter.print(StateController.packageToString(keyAt4, str4));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.println(shrinkableDebits.toString());
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mInQuotaAlarmQueue.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(ProtoOutputStream protoOutputStream, JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda5) {
        int i;
        List list;
        long j;
        JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda52 = jobSchedulerService$$ExternalSyntheticLambda5;
        long start = protoOutputStream.start(2246267895812L);
        long start2 = protoOutputStream.start(1146756268041L);
        protoOutputStream.write(1133871366145L, this.mService.isBatteryCharging());
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        protoOutputStream.write(1112396529670L, SystemClock.elapsedRealtime());
        for (int i2 = 0; i2 < this.mForegroundUids.size(); i2++) {
            protoOutputStream.write(2220498092035L, this.mForegroundUids.keyAt(i2));
        }
        this.mTrackedJobs.forEach(new QuotaController$$ExternalSyntheticLambda4(this, jobSchedulerService$$ExternalSyntheticLambda52, protoOutputStream));
        int i3 = 0;
        while (i3 < this.mPkgTimers.numMaps()) {
            int keyAt = this.mPkgTimers.keyAt(i3);
            int i4 = 0;
            while (i4 < this.mPkgTimers.numElementsForKey(keyAt)) {
                String str = (String) this.mPkgTimers.keyAt(i3, i4);
                long start3 = protoOutputStream.start(2246267895813L);
                ((Timer) this.mPkgTimers.valueAt(i3, i4)).dump(protoOutputStream, 1146756268034L, jobSchedulerService$$ExternalSyntheticLambda52);
                Timer timer = (Timer) this.mEJPkgTimers.get(keyAt, str);
                long j2 = start;
                if (timer != null) {
                    timer.dump(protoOutputStream, 1146756268038L, jobSchedulerService$$ExternalSyntheticLambda52);
                }
                List list2 = (List) this.mTimingEvents.get(keyAt, str);
                if (list2 != null) {
                    int size = list2.size() - 1;
                    while (size >= 0) {
                        TimedEvent timedEvent = (TimedEvent) list2.get(size);
                        if (timedEvent instanceof TimingSession) {
                            TimingSession timingSession = (TimingSession) timedEvent;
                            timingSession.getClass();
                            long start4 = protoOutputStream.start(2246267895811L);
                            list = list2;
                            j = start2;
                            protoOutputStream.write(1112396529665L, timingSession.startTimeElapsed);
                            protoOutputStream.write(1112396529666L, timingSession.endTimeElapsed);
                            protoOutputStream.write(1120986464259L, timingSession.bgJobCount);
                            protoOutputStream.end(start4);
                        } else {
                            list = list2;
                            j = start2;
                        }
                        size--;
                        list2 = list;
                        start2 = j;
                    }
                }
                long j3 = start2;
                ExecutionStats[] executionStatsArr = (ExecutionStats[]) this.mExecutionStatsCache.get(keyAt, str);
                if (executionStatsArr != null) {
                    int i5 = 0;
                    while (i5 < executionStatsArr.length) {
                        ExecutionStats executionStats = executionStatsArr[i5];
                        if (executionStats == null) {
                            i = i3;
                        } else {
                            long start5 = protoOutputStream.start(2246267895812L);
                            protoOutputStream.write(1159641169921L, i5);
                            i = i3;
                            protoOutputStream.write(1112396529666L, executionStats.expirationTimeElapsed);
                            protoOutputStream.write(1112396529667L, executionStats.windowSizeMs);
                            protoOutputStream.write(1120986464270L, executionStats.jobCountLimit);
                            protoOutputStream.write(1120986464271L, executionStats.sessionCountLimit);
                            protoOutputStream.write(1112396529668L, executionStats.executionTimeInWindowMs);
                            protoOutputStream.write(1120986464261L, executionStats.bgJobCountInWindow);
                            protoOutputStream.write(1112396529670L, executionStats.executionTimeInMaxPeriodMs);
                            protoOutputStream.write(1120986464263L, executionStats.bgJobCountInMaxPeriod);
                            protoOutputStream.write(1120986464267L, executionStats.sessionCountInWindow);
                            protoOutputStream.write(1112396529672L, executionStats.inQuotaTimeElapsed);
                            protoOutputStream.write(1112396529673L, executionStats.jobRateLimitExpirationTimeElapsed);
                            protoOutputStream.write(1120986464266L, executionStats.jobCountInRateLimitingWindow);
                            protoOutputStream.write(1112396529676L, executionStats.sessionRateLimitExpirationTimeElapsed);
                            protoOutputStream.write(1120986464269L, executionStats.sessionCountInRateLimitingWindow);
                            protoOutputStream.end(start5);
                        }
                        i5++;
                        i3 = i;
                    }
                }
                protoOutputStream.end(start3);
                i4++;
                jobSchedulerService$$ExternalSyntheticLambda52 = jobSchedulerService$$ExternalSyntheticLambda5;
                i3 = i3;
                start = j2;
                start2 = j3;
            }
            i3++;
            jobSchedulerService$$ExternalSyntheticLambda52 = jobSchedulerService$$ExternalSyntheticLambda5;
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    public long[] getAllowedTimePerPeriodMs() {
        return this.mAllowedTimePerPeriodMs;
    }

    public int[] getBucketMaxJobCounts() {
        return this.mMaxBucketJobCounts;
    }

    public int[] getBucketMaxSessionCounts() {
        return this.mMaxBucketSessionCounts;
    }

    public long[] getBucketWindowSizes() {
        return this.mBucketPeriodsMs;
    }

    public ShrinkableDebits getEJDebitsLocked(int i, String str) {
        ShrinkableDebits shrinkableDebits = (ShrinkableDebits) this.mEJStats.get(i, str);
        if (shrinkableDebits != null) {
            return shrinkableDebits;
        }
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        int standbyBucketForPackage = JobSchedulerService.standbyBucketForPackage(i, str, SystemClock.elapsedRealtime());
        ShrinkableDebits shrinkableDebits2 = new ShrinkableDebits();
        shrinkableDebits2.mDebitTally = 0L;
        shrinkableDebits2.mStandbyBucket = standbyBucketForPackage;
        this.mEJStats.add(i, str, shrinkableDebits2);
        return shrinkableDebits2;
    }

    public long getEJGracePeriodTempAllowlistMs() {
        return this.mEJGracePeriodTempAllowlistMs;
    }

    public long getEJGracePeriodTopAppMs() {
        return this.mEJGracePeriodTopAppMs;
    }

    public final long getEJLimitMsLocked(int i, int i2, String str) {
        long j = this.mEJLimitsMs[i2];
        return this.mSystemInstallers.contains(i, str) ? j + this.mEjLimitAdditionInstallerMs : j;
    }

    public long getEJLimitWindowSizeMs() {
        return this.mEJLimitWindowSizeMs;
    }

    public long[] getEJLimitsMs() {
        return this.mEJLimitsMs;
    }

    public long getEJRewardInteractionMs() {
        return this.mEJRewardInteractionMs;
    }

    public long getEJRewardNotificationSeenMs() {
        return this.mEJRewardNotificationSeenMs;
    }

    public long getEJRewardTopAppMs() {
        return this.mEJRewardTopAppMs;
    }

    public List getEJTimingSessions(int i, String str) {
        return (List) this.mEJTimingSessions.get(i, str);
    }

    public long getEJTopAppTimeChunkSizeMs() {
        return this.mEJTopAppTimeChunkSizeMs;
    }

    public long getEjLimitAdditionInstallerMs() {
        return this.mEjLimitAdditionInstallerMs;
    }

    public long getEjLimitAdditionSpecialMs() {
        return this.mEjLimitAdditionSpecialMs;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0071, code lost:
    
        if (r2.sessionCountLimit == r11) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.job.controllers.QuotaController.ExecutionStats getExecutionStatsLocked(int r10, int r11, java.lang.String r12, boolean r13) {
        /*
            r9 = this;
            r0 = 4
            if (r11 != r0) goto L11
            java.lang.String r9 = "JobScheduler.Quota"
            java.lang.String r10 = "getExecutionStatsLocked called for a NEVER app."
            android.util.Slog.wtf(r9, r10)
            com.android.server.job.controllers.QuotaController$ExecutionStats r9 = new com.android.server.job.controllers.QuotaController$ExecutionStats
            r9.<init>()
            return r9
        L11:
            android.util.SparseArrayMap r0 = r9.mExecutionStatsCache
            java.lang.Object r0 = r0.get(r10, r12)
            com.android.server.job.controllers.QuotaController$ExecutionStats[] r0 = (com.android.server.job.controllers.QuotaController.ExecutionStats[]) r0
            long[] r1 = r9.mBucketPeriodsMs
            if (r0 != 0) goto L25
            int r0 = r1.length
            com.android.server.job.controllers.QuotaController$ExecutionStats[] r0 = new com.android.server.job.controllers.QuotaController.ExecutionStats[r0]
            android.util.SparseArrayMap r2 = r9.mExecutionStatsCache
            r2.add(r10, r12, r0)
        L25:
            r2 = r0[r11]
            if (r2 != 0) goto L30
            com.android.server.job.controllers.QuotaController$ExecutionStats r2 = new com.android.server.job.controllers.QuotaController$ExecutionStats
            r2.<init>()
            r0[r11] = r2
        L30:
            if (r13 == 0) goto L7e
            long[] r13 = r9.mAllowedTimePerPeriodMs
            r3 = r13[r11]
            r0 = r1[r11]
            int[] r13 = r9.mMaxBucketJobCounts
            r13 = r13[r11]
            int[] r5 = r9.mMaxBucketSessionCounts
            r11 = r5[r11]
            android.util.SparseArrayMap r5 = r9.mPkgTimers
            java.lang.Object r5 = r5.get(r10, r12)
            com.android.server.job.controllers.QuotaController$Timer r5 = (com.android.server.job.controllers.QuotaController.Timer) r5
            if (r5 == 0) goto L50
            boolean r5 = r5.isActive()
            if (r5 != 0) goto L73
        L50:
            long r5 = r2.expirationTimeElapsed
            com.android.server.job.JobSchedulerService$1 r7 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock
            r7.getClass()
            long r7 = android.os.SystemClock.elapsedRealtime()
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 <= 0) goto L73
            long r5 = r2.allowedTimePerPeriodMs
            int r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r5 != 0) goto L73
            long r5 = r2.windowSizeMs
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 != 0) goto L73
            int r5 = r2.jobCountLimit
            if (r5 != r13) goto L73
            int r5 = r2.sessionCountLimit
            if (r5 == r11) goto L7e
        L73:
            r2.allowedTimePerPeriodMs = r3
            r2.windowSizeMs = r0
            r2.jobCountLimit = r13
            r2.sessionCountLimit = r11
            r9.updateExecutionStatsLocked(r10, r12, r2)
        L7e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.controllers.QuotaController.getExecutionStatsLocked(int, int, java.lang.String, boolean):com.android.server.job.controllers.QuotaController$ExecutionStats");
    }

    public ExecutionStats getExecutionStatsLocked(int i, String str, int i2) {
        return getExecutionStatsLocked(i, i2, str, true);
    }

    public SparseBooleanArray getForegroundUids() {
        return this.mForegroundUids;
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public long getInQuotaBufferMs() {
        return this.mQuotaBufferMs;
    }

    public long getMaxExecutionTimeMs() {
        return this.mMaxExecutionTimeMs;
    }

    public int getMaxJobCountPerRateLimitingWindow() {
        return this.mMaxJobCountPerRateLimitingWindow;
    }

    public final long getMaxJobExecutionTimeMsLocked(JobStatus jobStatus) {
        boolean shouldTreatAsExpeditedJob = jobStatus.shouldTreatAsExpeditedJob();
        JobSchedulerService jobSchedulerService = this.mService;
        boolean z = true;
        JobSchedulerService.Constants constants = this.mConstants;
        int i = jobStatus.sourceUid;
        String str = jobStatus.sourcePackageName;
        int i2 = jobStatus.sourceUserId;
        if (shouldTreatAsExpeditedJob) {
            if (jobSchedulerService.isBatteryCharging()) {
                return constants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
            }
            int effectiveStandbyBucket = jobStatus.getEffectiveStandbyBucket();
            long[] jArr = this.mEJLimitsMs;
            return effectiveStandbyBucket == 6 ? Math.max(jArr[6] / 2, getTimeUntilEJQuotaConsumedLocked(i2, str)) : (this.mTopAppCache.get(i) || this.mTopStartedJobs.contains(jobStatus)) ? Math.max(jArr[0] / 2, getTimeUntilEJQuotaConsumedLocked(i2, str)) : isUidInForeground(i) ? Math.max(jArr[1] / 2, getTimeUntilEJQuotaConsumedLocked(i2, str)) : getTimeUntilEJQuotaConsumedLocked(i2, str);
        }
        if (jobSchedulerService.isBatteryCharging()) {
            return constants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS;
        }
        boolean z2 = this.mTopAppCache.get(i) || this.mTopStartedJobs.contains(jobStatus) || isUidInForeground(i);
        if (jobStatus.getEffectivePriority() < 400 && (jobStatus.job.getFlags() & 2) == 0) {
            z = false;
        }
        return (z2 && z) ? constants.RUNTIME_FREE_QUOTA_MAX_LIMIT_MS : getTimeUntilQuotaConsumedLocked(i2, str);
    }

    public int getMaxSessionCountPerRateLimitingWindow() {
        return this.mMaxSessionCountPerRateLimitingWindow;
    }

    public long getMinQuotaCheckDelayMs() {
        long j;
        InQuotaAlarmQueue inQuotaAlarmQueue = this.mInQuotaAlarmQueue;
        synchronized (inQuotaAlarmQueue.mLock) {
            j = inQuotaAlarmQueue.mMinTimeBetweenAlarmsMs;
        }
        return j;
    }

    public QcConstants getQcConstants() {
        return this.mQcConstants;
    }

    public long getQuotaBumpAdditionDurationMs() {
        return this.mQuotaBumpAdditionalDurationMs;
    }

    public int getQuotaBumpAdditionJobCount() {
        return this.mQuotaBumpAdditionalJobCount;
    }

    public int getQuotaBumpAdditionSessionCount() {
        return this.mQuotaBumpAdditionalSessionCount;
    }

    public int getQuotaBumpLimit() {
        return this.mQuotaBumpLimit;
    }

    public long getQuotaBumpWindowSizeMs() {
        return this.mQuotaBumpWindowSizeMs;
    }

    public long getRateLimitingWindowMs() {
        return this.mRateLimitingWindowMs;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long getRemainingEJExecutionTimeLocked(int r17, java.lang.String r18) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            com.android.server.job.controllers.QuotaController$ShrinkableDebits r3 = r16.getEJDebitsLocked(r17, r18)
            int r4 = r3.mStandbyBucket
            r5 = 4
            if (r4 != r5) goto L12
            r0 = 0
            return r0
        L12:
            long r4 = r0.getEJLimitMsLocked(r1, r4, r2)
            long r6 = r3.mDebitTally
            long r4 = r4 - r6
            android.util.SparseArrayMap r6 = r0.mEJTimingSessions
            java.lang.Object r6 = r6.get(r1, r2)
            java.util.List r6 = (java.util.List) r6
            com.android.server.job.JobSchedulerService$1 r7 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock
            r7.getClass()
            long r7 = android.os.SystemClock.elapsedRealtime()
            long r9 = r0.mEJLimitWindowSizeMs
            long r9 = r7 - r9
            r11 = 0
            if (r6 == 0) goto L59
        L31:
            int r12 = r6.size()
            if (r12 <= 0) goto L59
            java.lang.Object r12 = r6.get(r11)
            com.android.server.job.controllers.QuotaController$TimingSession r12 = (com.android.server.job.controllers.QuotaController.TimingSession) r12
            long r13 = r12.endTimeElapsed
            int r15 = (r13 > r9 ? 1 : (r13 == r9 ? 0 : -1))
            long r11 = r12.startTimeElapsed
            if (r15 >= 0) goto L51
            long r13 = r13 - r11
            long r4 = r4 + r13
            long r11 = -r13
            r3.transactLocked(r11)
            r13 = 0
            r6.remove(r13)
            r11 = r13
            goto L31
        L51:
            r13 = 0
            int r3 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r3 >= 0) goto L5a
            long r9 = r9 - r11
            long r4 = r4 + r9
            goto L5a
        L59:
            r13 = r11
        L5a:
            android.util.SparseArrayMap r3 = r0.mTopAppTrackers
            java.lang.Object r3 = r3.get(r1, r2)
            com.android.server.job.controllers.QuotaController$TopAppTimer r3 = (com.android.server.job.controllers.QuotaController.TopAppTimer) r3
            if (r3 == 0) goto L93
            com.android.server.job.controllers.QuotaController r6 = com.android.server.job.controllers.QuotaController.this
            java.lang.Object r6 = r6.mLock
            monitor-enter(r6)
            android.util.SparseArray r9 = r3.mActivities     // Catch: java.lang.Throwable -> L90
            int r9 = r9.size()     // Catch: java.lang.Throwable -> L90
            if (r9 <= 0) goto L73
            r11 = 1
            goto L74
        L73:
            r11 = r13
        L74:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L90
            if (r11 == 0) goto L93
            com.android.server.job.controllers.QuotaController r6 = com.android.server.job.controllers.QuotaController.this
            long r9 = r6.mEJRewardTopAppMs
            long r11 = r3.mStartTimeElapsed
            long r7 = r7 - r11
            long r11 = r6.mEJTopAppTimeChunkSizeMs
            long r13 = r7 / r11
            int r3 = (int) r13
            long r7 = r7 % r11
            r11 = 1000(0x3e8, double:4.94E-321)
            int r6 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r6 < 0) goto L8c
            int r3 = r3 + 1
        L8c:
            long r6 = (long) r3
            long r9 = r9 * r6
            long r4 = r4 + r9
            goto L93
        L90:
            r0 = move-exception
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L90
            throw r0
        L93:
            android.util.SparseArrayMap r0 = r0.mEJPkgTimers
            java.lang.Object r0 = r0.get(r1, r2)
            com.android.server.job.controllers.QuotaController$Timer r0 = (com.android.server.job.controllers.QuotaController.Timer) r0
            if (r0 != 0) goto L9e
            return r4
        L9e:
            com.android.server.job.JobSchedulerService$1 r1 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock
            r1.getClass()
            long r1 = android.os.SystemClock.elapsedRealtime()
            long r0 = r0.getCurrentDuration(r1)
            long r4 = r4 - r0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.controllers.QuotaController.getRemainingEJExecutionTimeLocked(int, java.lang.String):long");
    }

    public long getRemainingExecutionTimeLocked(int i, String str) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        int standbyBucketForPackage = JobSchedulerService.standbyBucketForPackage(i, str, SystemClock.elapsedRealtime());
        if (standbyBucketForPackage == 4) {
            return 0L;
        }
        ExecutionStats executionStatsLocked = getExecutionStatsLocked(i, str, standbyBucketForPackage);
        return Math.min(executionStatsLocked.allowedTimePerPeriodMs - executionStatsLocked.executionTimeInWindowMs, this.mMaxExecutionTimeMs - executionStatsLocked.executionTimeInMaxPeriodMs);
    }

    public long getRemainingExecutionTimeLocked(JobStatus jobStatus) {
        int i = jobStatus.sourceUserId;
        int effectiveStandbyBucket = jobStatus.getEffectiveStandbyBucket();
        if (effectiveStandbyBucket == 4) {
            return 0L;
        }
        ExecutionStats executionStatsLocked = getExecutionStatsLocked(i, jobStatus.sourcePackageName, effectiveStandbyBucket);
        return Math.min(executionStatsLocked.allowedTimePerPeriodMs - executionStatsLocked.executionTimeInWindowMs, this.mMaxExecutionTimeMs - executionStatsLocked.executionTimeInMaxPeriodMs);
    }

    public long getTimeUntilEJQuotaConsumedLocked(int i, String str) {
        long j;
        long j2;
        long remainingEJExecutionTimeLocked = getRemainingEJExecutionTimeLocked(i, str);
        List list = (List) this.mEJTimingSessions.get(i, str);
        if (list == null || list.size() == 0) {
            return remainingEJExecutionTimeLocked;
        }
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long eJLimitMsLocked = getEJLimitMsLocked(i, getEJDebitsLocked(i, str).mStandbyBucket, str);
        long max = Math.max(0L, elapsedRealtime - this.mEJLimitWindowSizeMs);
        int i2 = 0;
        long j3 = 0;
        long j4 = 0;
        while (true) {
            if (i2 >= list.size()) {
                j = eJLimitMsLocked;
                break;
            }
            TimingSession timingSession = (TimingSession) list.get(i2);
            long j5 = timingSession.endTimeElapsed;
            j = eJLimitMsLocked;
            long j6 = timingSession.startTimeElapsed;
            if (j5 < max) {
                list.remove(i2);
                i2--;
                remainingEJExecutionTimeLocked = (j5 - j6) + remainingEJExecutionTimeLocked;
            } else if (j6 < max) {
                j4 = j5 - max;
            } else {
                long endTimeElapsed = j6 - (i2 == 0 ? max : ((TimedEvent) list.get(i2 - 1)).getEndTimeElapsed());
                long min = Math.min(remainingEJExecutionTimeLocked, endTimeElapsed);
                j3 += min;
                if (min == endTimeElapsed) {
                    j4 += timingSession.endTimeElapsed - j6;
                }
                remainingEJExecutionTimeLocked -= min;
                j2 = 0;
                if (remainingEJExecutionTimeLocked <= 0) {
                    break;
                }
                i2++;
                eJLimitMsLocked = j;
            }
            j2 = 0;
            i2++;
            eJLimitMsLocked = j;
        }
        return Math.min(j, j3 + j4 + remainingEJExecutionTimeLocked);
    }

    public long getTimeUntilQuotaConsumedLocked(int i, String str) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int standbyBucketForPackage = JobSchedulerService.standbyBucketForPackage(i, str, elapsedRealtime);
        if (standbyBucketForPackage == 4) {
            return 0L;
        }
        List list = (List) this.mTimingEvents.get(i, str);
        ExecutionStats executionStatsLocked = getExecutionStatsLocked(i, str, standbyBucketForPackage);
        long[] jArr = this.mAllowedTimePerPeriodMs;
        if (list == null || list.size() == 0) {
            long j = executionStatsLocked.windowSizeMs;
            long j2 = jArr[standbyBucketForPackage];
            return j == j2 ? this.mMaxExecutionTimeMs : j2;
        }
        long j3 = executionStatsLocked.windowSizeMs;
        long j4 = elapsedRealtime - j3;
        long j5 = elapsedRealtime - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        long j6 = jArr[standbyBucketForPackage];
        long j7 = j6 - executionStatsLocked.executionTimeInWindowMs;
        long j8 = this.mMaxExecutionTimeMs - executionStatsLocked.executionTimeInMaxPeriodMs;
        return j3 == j6 ? calculateTimeUntilQuotaConsumedLocked(list, j5, j8, false) : Math.min(calculateTimeUntilQuotaConsumedLocked(list, j5, j8, false), calculateTimeUntilQuotaConsumedLocked(list, j4, j7, true));
    }

    public long getTimingSessionCoalescingDurationMs() {
        return this.mTimingSessionCoalescingDurationMs;
    }

    public List getTimingSessions(int i, String str) {
        return (List) this.mTimingEvents.get(i, str);
    }

    public final boolean hasTempAllowlistExemptionLocked(int i, int i2, long j) {
        if (i2 == 5 || i2 == 4) {
            return false;
        }
        return this.mTempAllowlistCache.get(i) || j < this.mTempAllowlistGraceCache.get(i);
    }

    public void incrementJobCountLocked(int i, String str, int i2) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        ExecutionStats[] executionStatsArr = (ExecutionStats[]) this.mExecutionStatsCache.get(i, str);
        if (executionStatsArr == null) {
            executionStatsArr = new ExecutionStats[this.mBucketPeriodsMs.length];
            this.mExecutionStatsCache.add(i, str, executionStatsArr);
        }
        for (int i3 = 0; i3 < executionStatsArr.length; i3++) {
            ExecutionStats executionStats = executionStatsArr[i3];
            if (executionStats == null) {
                executionStats = new ExecutionStats();
                executionStatsArr[i3] = executionStats;
            }
            if (executionStats.jobRateLimitExpirationTimeElapsed <= elapsedRealtime) {
                executionStats.jobRateLimitExpirationTimeElapsed = this.mRateLimitingWindowMs + elapsedRealtime;
                executionStats.jobCountInRateLimitingWindow = 0;
            }
            executionStats.jobCountInRateLimitingWindow += i2;
            Flags.countQuotaFix();
            executionStats.bgJobCountInWindow += i2;
        }
    }

    public void invalidateAllExecutionStatsLocked() {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mExecutionStatsCache.forEach(new Consumer() { // from class: com.android.server.job.controllers.QuotaController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                long j = elapsedRealtime;
                QuotaController.ExecutionStats[] executionStatsArr = (QuotaController.ExecutionStats[]) obj;
                if (executionStatsArr != null) {
                    for (QuotaController.ExecutionStats executionStats : executionStatsArr) {
                        if (executionStats != null) {
                            executionStats.expirationTimeElapsed = j;
                        }
                    }
                }
            }
        });
    }

    public void invalidateAllExecutionStatsLocked(int i, String str) {
        ExecutionStats[] executionStatsArr = (ExecutionStats[]) this.mExecutionStatsCache.get(i, str);
        if (executionStatsArr != null) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            for (ExecutionStats executionStats : executionStatsArr) {
                if (executionStats != null) {
                    executionStats.expirationTimeElapsed = elapsedRealtime;
                }
            }
        }
    }

    public final boolean isQuotaFreeLocked(int i) {
        return this.mService.isBatteryCharging() && i != 5;
    }

    public final boolean isUidInForeground(int i) {
        boolean z;
        if (UserHandle.isCore(i)) {
            return true;
        }
        synchronized (this.mLock) {
            z = this.mForegroundUids.get(i);
        }
        return z;
    }

    public final boolean isUnderJobCountQuotaLocked(ExecutionStats executionStats) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        return (executionStats.jobRateLimitExpirationTimeElapsed <= SystemClock.elapsedRealtime() || executionStats.jobCountInRateLimitingWindow < this.mMaxJobCountPerRateLimitingWindow) && executionStats.bgJobCountInWindow < executionStats.jobCountLimit;
    }

    public final boolean isUnderSessionCountQuotaLocked(ExecutionStats executionStats) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        return (executionStats.sessionRateLimitExpirationTimeElapsed <= SystemClock.elapsedRealtime() || executionStats.sessionCountInRateLimitingWindow < this.mMaxSessionCountPerRateLimitingWindow) && executionStats.sessionCountInWindow < executionStats.sessionCountLimit;
    }

    public final boolean isWithinEJQuotaLocked(JobStatus jobStatus) {
        if (isQuotaFreeLocked(jobStatus.getEffectiveStandbyBucket()) || this.mTopStartedJobs.contains(jobStatus)) {
            return true;
        }
        int i = jobStatus.sourceUid;
        if (isUidInForeground(i)) {
            return true;
        }
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (hasTempAllowlistExemptionLocked(i, jobStatus.getEffectiveStandbyBucket(), elapsedRealtime)) {
            return true;
        }
        return this.mTopAppCache.get(i) || elapsedRealtime < this.mTopAppGraceCache.get(i) || 0 < getRemainingEJExecutionTimeLocked(jobStatus.sourceUserId, jobStatus.sourcePackageName);
    }

    public boolean isWithinQuotaLocked(int i, String str, int i2) {
        if (i2 == 4) {
            return false;
        }
        if (isQuotaFreeLocked(i2)) {
            return true;
        }
        ExecutionStats executionStatsLocked = getExecutionStatsLocked(i, str, i2);
        return Math.min(executionStatsLocked.allowedTimePerPeriodMs - executionStatsLocked.executionTimeInWindowMs, this.mMaxExecutionTimeMs - executionStatsLocked.executionTimeInMaxPeriodMs) > 0 && isUnderJobCountQuotaLocked(executionStatsLocked) && isUnderSessionCountQuotaLocked(executionStatsLocked);
    }

    public boolean isWithinQuotaLocked(JobStatus jobStatus) {
        int effectiveStandbyBucket = jobStatus.getEffectiveStandbyBucket();
        Flags.countQuotaFix();
        if (jobStatus.shouldTreatAsUserInitiatedJob() || this.mTopStartedJobs.contains(jobStatus) || isUidInForeground(jobStatus.sourceUid)) {
            return true;
        }
        if (effectiveStandbyBucket == 4) {
            return false;
        }
        if (isQuotaFreeLocked(effectiveStandbyBucket)) {
            return true;
        }
        ExecutionStats executionStatsLocked = getExecutionStatsLocked(jobStatus.sourceUserId, jobStatus.sourcePackageName, effectiveStandbyBucket);
        if (Math.min(executionStatsLocked.allowedTimePerPeriodMs - executionStatsLocked.executionTimeInWindowMs, this.mMaxExecutionTimeMs - executionStatsLocked.executionTimeInMaxPeriodMs) <= 0) {
            return false;
        }
        if (effectiveStandbyBucket == 5 || !this.mService.isCurrentlyRunningLocked(jobStatus)) {
            return isUnderJobCountQuotaLocked(executionStatsLocked) && isUnderSessionCountQuotaLocked(executionStatsLocked);
        }
        return true;
    }

    public void maybeScheduleCleanupAlarmLocked() {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.mNextCleanupTimeElapsed;
        boolean z = DEBUG;
        if (j > elapsedRealtime) {
            if (z) {
                Slog.v("JobScheduler.Quota", "Not scheduling cleanup since there's already one at " + this.mNextCleanupTimeElapsed + " (in " + (this.mNextCleanupTimeElapsed - elapsedRealtime) + "ms)");
                return;
            }
            return;
        }
        EarliestEndTimeFunctor earliestEndTimeFunctor = this.mEarliestEndTimeFunctor;
        earliestEndTimeFunctor.earliestEndElapsed = Long.MAX_VALUE;
        this.mTimingEvents.forEach(earliestEndTimeFunctor);
        this.mEJTimingSessions.forEach(earliestEndTimeFunctor);
        long j2 = earliestEndTimeFunctor.earliestEndElapsed;
        if (j2 == Long.MAX_VALUE) {
            if (z) {
                Slog.d("JobScheduler.Quota", "Didn't find a time to schedule cleanup");
                return;
            }
            return;
        }
        long j3 = j2 + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        long j4 = this.mNextCleanupTimeElapsed;
        if (j3 - j4 <= 600000) {
            j3 = j4 + 600000;
        }
        long j5 = j3;
        this.mNextCleanupTimeElapsed = j5;
        this.mAlarmManager.set(3, j5, "*job.cleanup*", this.mSessionCleanupAlarmListener, this.mHandler);
        if (z) {
            BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("Scheduled next cleanup for "), this.mNextCleanupTimeElapsed, "JobScheduler.Quota");
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void maybeScheduleStartAlarmLocked(int r22, java.lang.String r23, int r24) {
        /*
            Method dump skipped, instructions count: 476
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.controllers.QuotaController.maybeScheduleStartAlarmLocked(int, java.lang.String, int):void");
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i = jobStatus.sourceUserId;
        SparseArrayMap sparseArrayMap = this.mTrackedJobs;
        String str = jobStatus.sourcePackageName;
        ArraySet arraySet = (ArraySet) sparseArrayMap.get(i, str);
        if (arraySet == null) {
            arraySet = new ArraySet();
            this.mTrackedJobs.add(i, str, arraySet);
        }
        arraySet.add(jobStatus);
        jobStatus.setTrackingController(64);
        boolean isWithinQuotaLocked = isWithinQuotaLocked(jobStatus);
        boolean z = false;
        boolean z2 = jobStatus.isRequestedExpeditedJob() && isWithinEJQuotaLocked(jobStatus);
        setConstraintSatisfied(jobStatus, elapsedRealtime, isWithinQuotaLocked, z2);
        if (jobStatus.isRequestedExpeditedJob()) {
            setExpeditedQuotaApproved(jobStatus, z2);
            z = !z2;
        }
        if (!isWithinQuotaLocked || z) {
            maybeScheduleStartAlarmLocked(i, str, jobStatus.getEffectiveStandbyBucket());
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(64)) {
            unprepareFromExecutionLocked(jobStatus);
            SparseArrayMap sparseArrayMap = this.mTrackedJobs;
            int i = jobStatus.sourceUserId;
            String str = jobStatus.sourcePackageName;
            ArraySet arraySet = (ArraySet) sparseArrayMap.get(i, str);
            if (arraySet != null && arraySet.remove(jobStatus) && arraySet.size() == 0) {
                this.mInQuotaAlarmQueue.removeAlarmForKey(UserPackage.of(i, str));
            }
        }
    }

    public final void maybeUpdateAllConstraintsLocked() {
        ArraySet arraySet = new ArraySet();
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        for (int i = 0; i < this.mTrackedJobs.numMaps(); i++) {
            int keyAt = this.mTrackedJobs.keyAt(i);
            for (int i2 = 0; i2 < this.mTrackedJobs.numElementsForKey(keyAt); i2++) {
                arraySet.addAll(maybeUpdateConstraintForPkgLocked(keyAt, (String) this.mTrackedJobs.keyAt(i, i2), elapsedRealtime));
            }
        }
        if (arraySet.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(arraySet);
        }
    }

    public final ArraySet maybeUpdateConstraintForPkgLocked(int i, String str, long j) {
        boolean z;
        JobStatus jobStatus;
        ArraySet arraySet = (ArraySet) this.mTrackedJobs.get(i, str);
        ArraySet arraySet2 = new ArraySet();
        if (arraySet != null && arraySet.size() != 0) {
            boolean z2 = false;
            int i2 = ((JobStatus) arraySet.valueAt(0)).standbyBucket;
            boolean isWithinQuotaLocked = isWithinQuotaLocked(i, str, i2);
            int size = arraySet.size() - 1;
            boolean z3 = false;
            while (size >= 0) {
                JobStatus jobStatus2 = (JobStatus) arraySet.valueAt(size);
                boolean z4 = (jobStatus2.isRequestedExpeditedJob() && isWithinEJQuotaLocked(jobStatus2)) ? true : z2;
                if (this.mTopStartedJobs.contains(jobStatus2)) {
                    if (jobStatus2.setConstraintSatisfied(16777216, j, true)) {
                        jobStatus2.mReadyWithinQuota = true;
                        arraySet2.add(jobStatus2);
                    }
                    z = z4;
                    jobStatus = jobStatus2;
                } else {
                    if (i2 != 6 && i2 != 0 && i2 == jobStatus2.getEffectiveStandbyBucket()) {
                        Flags.countQuotaFix();
                        if (!this.mService.isCurrentlyRunningLocked(jobStatus2)) {
                            z = z4;
                            jobStatus = jobStatus2;
                            if (setConstraintSatisfied(jobStatus2, j, isWithinQuotaLocked, z)) {
                                arraySet2.add(jobStatus);
                            }
                        }
                    }
                    z = z4;
                    jobStatus = jobStatus2;
                    if (setConstraintSatisfied(jobStatus, j, isWithinQuotaLocked(jobStatus), z)) {
                        arraySet2.add(jobStatus);
                    }
                }
                if (jobStatus.isRequestedExpeditedJob()) {
                    boolean z5 = z;
                    if (setExpeditedQuotaApproved(jobStatus, z5)) {
                        arraySet2.add(jobStatus);
                    }
                    z3 |= !z5;
                }
                size--;
                z2 = false;
            }
            if (!isWithinQuotaLocked || z3) {
                maybeScheduleStartAlarmLocked(i, str, i2);
            } else {
                this.mInQuotaAlarmQueue.removeAlarmForKey(UserPackage.of(i, str));
            }
        }
        return arraySet2;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onAppRemovedLocked(int i, String str) {
        if (str == null) {
            Slog.wtf("JobScheduler.Quota", "Told app removed but given null package name.");
            return;
        }
        clearAppStatsLocked(UserHandle.getUserId(i), str);
        if (this.mService.getPackagesForUidLocked(i) == null) {
            this.mForegroundUids.delete(i);
            this.mTempAllowlistCache.delete(i);
            this.mTempAllowlistGraceCache.delete(i);
            this.mTopAppCache.delete(i);
            this.mTopAppGraceCache.delete(i);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onBatteryStateChangedLocked() {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        JobSchedulerService jobSchedulerService = this.mService;
        boolean isBatteryCharging = jobSchedulerService.isBatteryCharging();
        TimerChargingUpdateFunctor timerChargingUpdateFunctor = this.mTimerChargingUpdateFunctor;
        timerChargingUpdateFunctor.mNowElapsed = elapsedRealtime;
        timerChargingUpdateFunctor.mIsCharging = isBatteryCharging;
        if (DEBUG) {
            Slog.d("JobScheduler.Quota", "handleNewChargingStateLocked: " + jobSchedulerService.isBatteryCharging());
        }
        this.mEJPkgTimers.forEach(timerChargingUpdateFunctor);
        this.mPkgTimers.forEach(timerChargingUpdateFunctor);
        AppSchedulingModuleThread.getHandler().post(new QuotaController$$ExternalSyntheticLambda2(this, 0));
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onConstantsUpdatedLocked() {
        if (this.mQcConstants.mShouldReevaluateConstraints) {
            AppSchedulingModuleThread.getHandler().post(new QuotaController$$ExternalSyntheticLambda2(this, 1));
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onSystemServicesReady() {
        synchronized (this.mLock) {
            cacheInstallerPackagesLocked(0);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onUserAddedLocked(int i) {
        cacheInstallerPackagesLocked(i);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onUserRemovedLocked(int i) {
        this.mTrackedJobs.delete(i);
        this.mPkgTimers.delete(i);
        this.mEJPkgTimers.delete(i);
        this.mTimingEvents.delete(i);
        this.mEJTimingSessions.delete(i);
        this.mInQuotaAlarmQueue.removeAlarmsForUserId(i);
        this.mExecutionStatsCache.delete(i);
        this.mEJStats.delete(i);
        this.mSystemInstallers.remove(i);
        this.mTopAppTrackers.delete(i);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r12v0 ??, still in use, count: 1, list:
          (r12v0 ?? I:java.lang.Object) from 0x0073: INVOKE (r2v5 ?? I:android.util.SparseArrayMap), (r9v0 ?? I:int), (r10v0 ?? I:java.lang.Object), (r12v0 ?? I:java.lang.Object) VIRTUAL call: android.util.SparseArrayMap.add(int, java.lang.Object, java.lang.Object):java.lang.Object
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    @Override // com.android.server.job.controllers.StateController
    public final void prepareForExecutionLocked(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r12v0 ??, still in use, count: 1, list:
          (r12v0 ?? I:java.lang.Object) from 0x0073: INVOKE (r2v5 ?? I:android.util.SparseArrayMap), (r9v0 ?? I:int), (r10v0 ?? I:java.lang.Object), (r12v0 ?? I:java.lang.Object) VIRTUAL call: android.util.SparseArrayMap.add(int, java.lang.Object, java.lang.Object):java.lang.Object
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r14v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:238)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */

    @Override // com.android.server.job.controllers.StateController
    public final void prepareForUpdatedConstantsLocked() {
        QcConstants qcConstants = this.mQcConstants;
        qcConstants.mShouldReevaluateConstraints = false;
        qcConstants.mRateLimitingConstantsUpdated = false;
        qcConstants.mExecutionPeriodConstantsUpdated = false;
        qcConstants.mEJLimitConstantsUpdated = false;
        qcConstants.mQuotaBumpConstantsUpdated = false;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void processConstantLocked(DeviceConfig.Properties properties, String str) {
        String str2;
        String str3;
        String str4;
        char c;
        char c2;
        ?? r6;
        ?? r0;
        ?? r02;
        boolean z;
        boolean z2;
        QcConstants qcConstants = this.mQcConstants;
        qcConstants.getClass();
        QuotaController quotaController = QuotaController.this;
        switch (str.hashCode()) {
            case -1952749138:
                str2 = "qc_allowed_time_per_period_active_ms";
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
                if (str.equals(str4)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1719823663:
                str2 = "qc_allowed_time_per_period_active_ms";
                if (!str.equals(str2)) {
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    c = 65535;
                    break;
                } else {
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    c = 1;
                    break;
                }
            case -1683576133:
                if (str.equals("qc_window_size_frequent_ms")) {
                    str2 = "qc_allowed_time_per_period_active_ms";
                    c = 2;
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -1525098678:
                if (str.equals("qc_quota_bump_window_size_ms")) {
                    str2 = "qc_allowed_time_per_period_active_ms";
                    c = 3;
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -1515776932:
                if (str.equals("qc_allowed_time_per_period_restricted_ms")) {
                    c2 = 4;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -1507602138:
                if (str.equals("qc_ej_limit_frequent_ms")) {
                    c = 5;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -1495638658:
                if (str.equals("qc_ej_limit_addition_special_ms")) {
                    c = 6;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -1436524327:
                if (str.equals("qc_ej_reward_notification_seen_ms")) {
                    c2 = 7;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -1412574464:
                if (str.equals("qc_max_job_count_active")) {
                    str2 = "qc_allowed_time_per_period_active_ms";
                    c = '\b';
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -1409079211:
                if (str.equals("qc_ej_top_app_time_chunk_size_ms")) {
                    c2 = '\t';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -1301522660:
                if (str.equals("qc_max_job_count_rare")) {
                    str2 = "qc_allowed_time_per_period_active_ms";
                    c = '\n';
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -1253638898:
                if (str.equals("qc_window_size_restricted_ms")) {
                    c2 = 11;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -1004520055:
                if (str.equals("qc_allowed_time_per_period_frequent_ms")) {
                    c2 = '\f';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -947372170:
                if (str.equals("qc_ej_reward_interaction_ms")) {
                    c2 = '\r';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -947005713:
                if (str.equals("qc_rate_limiting_window_ms")) {
                    c2 = 14;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -911626004:
                if (str.equals("qc_max_session_count_per_rate_limiting_window")) {
                    c2 = 15;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -861283784:
                if (str.equals("qc_max_job_count_exempted")) {
                    c2 = 16;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -743649451:
                if (str.equals("qc_max_job_count_restricted")) {
                    c2 = 17;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -615999962:
                if (str.equals("qc_quota_bump_limit")) {
                    c2 = 18;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -473591193:
                if (str.equals("qc_window_size_rare_ms")) {
                    c2 = 19;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case -144699320:
                if (str.equals("qc_max_job_count_frequent")) {
                    str2 = "qc_allowed_time_per_period_active_ms";
                    c = 20;
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 202838626:
                if (str.equals("qc_allowed_time_per_period_working_ms")) {
                    c2 = 21;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 224532750:
                if (str.equals("qc_quota_bump_additional_duration_ms")) {
                    c2 = 22;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 319829733:
                if (str.equals("qc_max_job_count_per_rate_limiting_window")) {
                    c2 = 23;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 353645753:
                if (str.equals("qc_ej_limit_restricted_ms")) {
                    c2 = 24;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 353674834:
                if (str.equals("qc_ej_limit_rare_ms")) {
                    c2 = 25;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 515924943:
                if (str.equals("qc_ej_limit_addition_installer_ms")) {
                    c2 = 26;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 542719401:
                if (str.equals("qc_max_execution_time_ms")) {
                    c2 = 27;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 659682264:
                if (str.equals("qc_ej_grace_period_top_app_ms")) {
                    c2 = 28;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1012217584:
                if (str.equals("qc_window_size_working_ms")) {
                    c2 = 29;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1029123626:
                if (str.equals("qc_quota_bump_additional_job_count")) {
                    c2 = 30;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1070239943:
                if (str.equals("qc_max_session_count_active")) {
                    c2 = 31;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1072854979:
                if (str.equals("qc_quota_bump_additional_session_count")) {
                    c2 = ' ';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1185201205:
                if (str.equals("qc_allowed_time_per_period_rare_ms")) {
                    c2 = '!';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1211719583:
                if (str.equals("qc_ej_grace_period_temp_allowlist_ms")) {
                    c2 = '\"';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1232643386:
                if (str.equals("qc_min_quota_check_delay_ms")) {
                    c2 = '#';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1415707953:
                if (str.equals("qc_in_quota_buffer_ms")) {
                    c2 = '$';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1416512063:
                if (str.equals("qc_max_session_count_exempted")) {
                    c2 = '%';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1504661904:
                if (str.equals("qc_max_session_count_working")) {
                    c2 = PackageManagerShellCommandDataLoader.ARGS_DELIM;
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1510141337:
                if (str.equals("qc_allowed_time_per_period_exempted_ms")) {
                    c2 = '\'';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1572083493:
                if (str.equals("qc_ej_limit_working_ms")) {
                    c2 = '(';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1737007281:
                if (str.equals("qc_ej_reward_top_app_ms")) {
                    c2 = ')';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1846826615:
                if (str.equals("qc_max_job_count_working")) {
                    c2 = '*';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1908515971:
                if (str.equals("qc_window_size_active_ms")) {
                    c2 = '+';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1921715463:
                if (str.equals("qc_timing_session_coalescing_duration_ms")) {
                    c2 = ',';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 1988481858:
                if (str.equals("qc_ej_window_size_ms")) {
                    c2 = '-';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 2079805852:
                if (str.equals("qc_max_session_count_restricted")) {
                    c2 = '.';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 2084297379:
                if (str.equals("qc_max_session_count_rare")) {
                    c2 = '/';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            case 2133096527:
                if (str.equals("qc_max_session_count_frequent")) {
                    c2 = '0';
                    c = c2;
                    str2 = "qc_allowed_time_per_period_active_ms";
                    str3 = "qc_ej_limit_addition_special_ms";
                    str4 = "qc_ej_limit_active_ms";
                    break;
                }
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
            default:
                str2 = "qc_allowed_time_per_period_active_ms";
                c = 65535;
                str3 = "qc_ej_limit_addition_special_ms";
                str4 = "qc_ej_limit_active_ms";
                break;
        }
        switch (c) {
            case 0:
            case 5:
            case 6:
            case 24:
            case 25:
            case 26:
            case '(':
            case '-':
                if (!qcConstants.mEJLimitConstantsUpdated) {
                    qcConstants.mEJLimitConstantsUpdated = true;
                    DeviceConfig.Properties properties2 = DeviceConfig.getProperties("jobscheduler", new String[]{"qc_ej_limit_exempted_ms", "qc_ej_limit_active_ms", "qc_ej_limit_working_ms", "qc_ej_limit_frequent_ms", "qc_ej_limit_rare_ms", "qc_ej_limit_restricted_ms", "qc_ej_limit_addition_special_ms", "qc_ej_limit_addition_installer_ms", "qc_ej_window_size_ms"});
                    qcConstants.EJ_LIMIT_EXEMPTED_MS = properties2.getLong("qc_ej_limit_exempted_ms", ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
                    qcConstants.EJ_LIMIT_ACTIVE_MS = properties2.getLong(str4, 1800000L);
                    qcConstants.EJ_LIMIT_WORKING_MS = properties2.getLong("qc_ej_limit_working_ms", 1800000L);
                    qcConstants.EJ_LIMIT_FREQUENT_MS = properties2.getLong("qc_ej_limit_frequent_ms", 600000L);
                    qcConstants.EJ_LIMIT_RARE_MS = properties2.getLong("qc_ej_limit_rare_ms", 600000L);
                    qcConstants.EJ_LIMIT_RESTRICTED_MS = properties2.getLong("qc_ej_limit_restricted_ms", 300000L);
                    qcConstants.EJ_LIMIT_ADDITION_INSTALLER_MS = properties2.getLong("qc_ej_limit_addition_installer_ms", 1800000L);
                    qcConstants.EJ_LIMIT_ADDITION_SPECIAL_MS = properties2.getLong(str3, 900000L);
                    long j = properties2.getLong("qc_ej_window_size_ms", BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
                    qcConstants.EJ_WINDOW_SIZE_MS = j;
                    long max = Math.max(ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, Math.min(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, j));
                    if (quotaController.mEJLimitWindowSizeMs != max) {
                        quotaController.mEJLimitWindowSizeMs = max;
                        qcConstants.mShouldReevaluateConstraints = true;
                    }
                    long max2 = Math.max(900000L, Math.min(max, qcConstants.EJ_LIMIT_EXEMPTED_MS));
                    long[] jArr = quotaController.mEJLimitsMs;
                    if (jArr[6] != max2) {
                        jArr[6] = max2;
                        r6 = 1;
                        qcConstants.mShouldReevaluateConstraints = true;
                    } else {
                        r6 = 1;
                    }
                    long max3 = Math.max(900000L, Math.min(max2, qcConstants.EJ_LIMIT_ACTIVE_MS));
                    if (jArr[0] != max3) {
                        jArr[0] = max3;
                        qcConstants.mShouldReevaluateConstraints = r6;
                    }
                    long max4 = Math.max(900000L, Math.min(max3, qcConstants.EJ_LIMIT_WORKING_MS));
                    if (jArr[r6] != max4) {
                        jArr[r6] = max4;
                        qcConstants.mShouldReevaluateConstraints = r6;
                    }
                    long max5 = Math.max(600000L, Math.min(max4, qcConstants.EJ_LIMIT_FREQUENT_MS));
                    if (jArr[2] != max5) {
                        jArr[2] = max5;
                        qcConstants.mShouldReevaluateConstraints = r6;
                    }
                    long max6 = Math.max(600000L, Math.min(max5, qcConstants.EJ_LIMIT_RARE_MS));
                    if (jArr[3] != max6) {
                        jArr[3] = max6;
                        qcConstants.mShouldReevaluateConstraints = r6;
                    }
                    long max7 = Math.max(300000L, Math.min(max6, qcConstants.EJ_LIMIT_RESTRICTED_MS));
                    if (jArr[5] != max7) {
                        jArr[5] = max7;
                        qcConstants.mShouldReevaluateConstraints = r6;
                    }
                    long j2 = max - max3;
                    long max8 = Math.max(0L, Math.min(j2, qcConstants.EJ_LIMIT_ADDITION_INSTALLER_MS));
                    if (quotaController.mEjLimitAdditionInstallerMs != max8) {
                        quotaController.mEjLimitAdditionInstallerMs = max8;
                        qcConstants.mShouldReevaluateConstraints = true;
                    }
                    long max9 = Math.max(0L, Math.min(j2, qcConstants.EJ_LIMIT_ADDITION_SPECIAL_MS));
                    if (quotaController.mEjLimitAdditionSpecialMs != max9) {
                        quotaController.mEjLimitAdditionSpecialMs = max9;
                        qcConstants.mShouldReevaluateConstraints = true;
                        break;
                    }
                }
                break;
            case 1:
            case 2:
            case 4:
            case 11:
            case '\f':
            case 19:
            case 21:
            case 27:
            case 29:
            case '!':
            case '$':
            case '\'':
            case '+':
                if (!qcConstants.mExecutionPeriodConstantsUpdated) {
                    qcConstants.mExecutionPeriodConstantsUpdated = true;
                    DeviceConfig.Properties properties3 = DeviceConfig.getProperties("jobscheduler", new String[]{"qc_allowed_time_per_period_exempted_ms", "qc_allowed_time_per_period_active_ms", "qc_allowed_time_per_period_working_ms", "qc_allowed_time_per_period_frequent_ms", "qc_allowed_time_per_period_rare_ms", "qc_allowed_time_per_period_restricted_ms", "qc_in_quota_buffer_ms", "qc_max_execution_time_ms", "qc_window_size_exempted_ms", "qc_window_size_active_ms", "qc_window_size_working_ms", "qc_window_size_frequent_ms", "qc_window_size_rare_ms", "qc_window_size_restricted_ms"});
                    qcConstants.ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS = properties3.getLong("qc_allowed_time_per_period_exempted_ms", 600000L);
                    qcConstants.ALLOWED_TIME_PER_PERIOD_ACTIVE_MS = properties3.getLong(str2, 600000L);
                    qcConstants.ALLOWED_TIME_PER_PERIOD_WORKING_MS = properties3.getLong("qc_allowed_time_per_period_working_ms", 600000L);
                    qcConstants.ALLOWED_TIME_PER_PERIOD_FREQUENT_MS = properties3.getLong("qc_allowed_time_per_period_frequent_ms", 600000L);
                    qcConstants.ALLOWED_TIME_PER_PERIOD_RARE_MS = properties3.getLong("qc_allowed_time_per_period_rare_ms", 600000L);
                    qcConstants.ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS = properties3.getLong("qc_allowed_time_per_period_restricted_ms", 600000L);
                    qcConstants.IN_QUOTA_BUFFER_MS = properties3.getLong("qc_in_quota_buffer_ms", 30000L);
                    qcConstants.MAX_EXECUTION_TIME_MS = properties3.getLong("qc_max_execution_time_ms", BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
                    qcConstants.WINDOW_SIZE_EXEMPTED_MS = properties3.getLong("qc_window_size_exempted_ms", 600000L);
                    qcConstants.WINDOW_SIZE_ACTIVE_MS = properties3.getLong("qc_window_size_active_ms", 600000L);
                    qcConstants.WINDOW_SIZE_WORKING_MS = properties3.getLong("qc_window_size_working_ms", 7200000L);
                    qcConstants.WINDOW_SIZE_FREQUENT_MS = properties3.getLong("qc_window_size_frequent_ms", 28800000L);
                    qcConstants.WINDOW_SIZE_RARE_MS = properties3.getLong("qc_window_size_rare_ms", BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
                    qcConstants.WINDOW_SIZE_RESTRICTED_MS = properties3.getLong("qc_window_size_restricted_ms", BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
                    long max10 = Math.max(ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, Math.min(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, qcConstants.MAX_EXECUTION_TIME_MS));
                    if (quotaController.mMaxExecutionTimeMs != max10) {
                        quotaController.mMaxExecutionTimeMs = max10;
                        quotaController.mMaxExecutionTimeIntoQuotaMs = max10 - quotaController.mQuotaBufferMs;
                        qcConstants.mShouldReevaluateConstraints = true;
                    }
                    long min = Math.min(quotaController.mMaxExecutionTimeMs, Math.max(60000L, qcConstants.ALLOWED_TIME_PER_PERIOD_EXEMPTED_MS));
                    long min2 = Math.min(Long.MAX_VALUE, min);
                    long[] jArr2 = quotaController.mAllowedTimePerPeriodMs;
                    if (jArr2[6] != min) {
                        jArr2[6] = min;
                        qcConstants.mShouldReevaluateConstraints = true;
                    }
                    long min3 = Math.min(quotaController.mMaxExecutionTimeMs, Math.max(60000L, qcConstants.ALLOWED_TIME_PER_PERIOD_ACTIVE_MS));
                    long min4 = Math.min(min2, min3);
                    if (jArr2[0] != min3) {
                        jArr2[0] = min3;
                        r0 = 1;
                        qcConstants.mShouldReevaluateConstraints = true;
                    } else {
                        r0 = 1;
                    }
                    long min5 = Math.min(quotaController.mMaxExecutionTimeMs, Math.max(60000L, qcConstants.ALLOWED_TIME_PER_PERIOD_WORKING_MS));
                    long min6 = Math.min(min4, min5);
                    if (jArr2[r0] != min5) {
                        jArr2[r0] = min5;
                        qcConstants.mShouldReevaluateConstraints = r0;
                    }
                    long min7 = Math.min(quotaController.mMaxExecutionTimeMs, Math.max(60000L, qcConstants.ALLOWED_TIME_PER_PERIOD_FREQUENT_MS));
                    long min8 = Math.min(min6, min7);
                    if (jArr2[2] != min7) {
                        jArr2[2] = min7;
                        qcConstants.mShouldReevaluateConstraints = true;
                    }
                    long min9 = Math.min(quotaController.mMaxExecutionTimeMs, Math.max(60000L, qcConstants.ALLOWED_TIME_PER_PERIOD_RARE_MS));
                    long min10 = Math.min(min8, min9);
                    if (jArr2[3] != min9) {
                        jArr2[3] = min9;
                        qcConstants.mShouldReevaluateConstraints = true;
                    }
                    long min11 = Math.min(quotaController.mMaxExecutionTimeMs, Math.max(60000L, qcConstants.ALLOWED_TIME_PER_PERIOD_RESTRICTED_MS));
                    long min12 = Math.min(min10, min11);
                    if (jArr2[5] != min11) {
                        jArr2[5] = min11;
                        qcConstants.mShouldReevaluateConstraints = true;
                    }
                    long max11 = Math.max(0L, Math.min(min12, Math.min(300000L, qcConstants.IN_QUOTA_BUFFER_MS)));
                    if (quotaController.mQuotaBufferMs != max11) {
                        quotaController.mQuotaBufferMs = max11;
                        quotaController.mMaxExecutionTimeIntoQuotaMs = quotaController.mMaxExecutionTimeMs - max11;
                        qcConstants.mShouldReevaluateConstraints = true;
                    }
                    long max12 = Math.max(jArr2[6], Math.min(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, qcConstants.WINDOW_SIZE_EXEMPTED_MS));
                    long[] jArr3 = quotaController.mBucketPeriodsMs;
                    if (jArr3[6] != max12) {
                        jArr3[6] = max12;
                        qcConstants.mShouldReevaluateConstraints = true;
                    }
                    long max13 = Math.max(jArr2[0], Math.min(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, qcConstants.WINDOW_SIZE_ACTIVE_MS));
                    if (jArr3[0] != max13) {
                        jArr3[0] = max13;
                        r02 = 1;
                        qcConstants.mShouldReevaluateConstraints = true;
                    } else {
                        r02 = 1;
                    }
                    long max14 = Math.max(jArr2[r02], Math.min(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, qcConstants.WINDOW_SIZE_WORKING_MS));
                    if (jArr3[r02] != max14) {
                        jArr3[r02] = max14;
                        qcConstants.mShouldReevaluateConstraints = r02;
                    }
                    long max15 = Math.max(jArr2[2], Math.min(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, qcConstants.WINDOW_SIZE_FREQUENT_MS));
                    if (jArr3[2] != max15) {
                        jArr3[2] = max15;
                        qcConstants.mShouldReevaluateConstraints = true;
                    }
                    long max16 = Math.max(jArr2[3], Math.min(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, qcConstants.WINDOW_SIZE_RARE_MS));
                    if (jArr3[3] != max16) {
                        jArr3[3] = max16;
                        qcConstants.mShouldReevaluateConstraints = true;
                    }
                    long max17 = Math.max(jArr2[5], Math.min(604800000L, qcConstants.WINDOW_SIZE_RESTRICTED_MS));
                    if (jArr3[5] != max17) {
                        jArr3[5] = max17;
                        qcConstants.mShouldReevaluateConstraints = true;
                        break;
                    }
                }
                break;
            case 3:
            case 18:
            case 22:
            case 30:
            case ' ':
                if (!qcConstants.mQuotaBumpConstantsUpdated) {
                    qcConstants.mQuotaBumpConstantsUpdated = true;
                    DeviceConfig.Properties properties4 = DeviceConfig.getProperties("jobscheduler", new String[]{"qc_quota_bump_additional_duration_ms", "qc_quota_bump_additional_job_count", "qc_quota_bump_additional_session_count", "qc_quota_bump_window_size_ms", "qc_quota_bump_limit"});
                    qcConstants.QUOTA_BUMP_ADDITIONAL_DURATION_MS = properties4.getLong("qc_quota_bump_additional_duration_ms", 60000L);
                    qcConstants.QUOTA_BUMP_ADDITIONAL_JOB_COUNT = properties4.getInt("qc_quota_bump_additional_job_count", 2);
                    qcConstants.QUOTA_BUMP_ADDITIONAL_SESSION_COUNT = properties4.getInt("qc_quota_bump_additional_session_count", 1);
                    qcConstants.QUOTA_BUMP_WINDOW_SIZE_MS = properties4.getLong("qc_quota_bump_window_size_ms", 28800000L);
                    qcConstants.QUOTA_BUMP_LIMIT = properties4.getInt("qc_quota_bump_limit", 8);
                    long max18 = Math.max(ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, Math.min(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, qcConstants.QUOTA_BUMP_WINDOW_SIZE_MS));
                    if (quotaController.mQuotaBumpWindowSizeMs != max18) {
                        quotaController.mQuotaBumpWindowSizeMs = max18;
                        z = true;
                        qcConstants.mShouldReevaluateConstraints = true;
                    } else {
                        z = true;
                    }
                    int max19 = Math.max(0, qcConstants.QUOTA_BUMP_LIMIT);
                    if (quotaController.mQuotaBumpLimit != max19) {
                        quotaController.mQuotaBumpLimit = max19;
                        qcConstants.mShouldReevaluateConstraints = z;
                    }
                    int max20 = Math.max(0, qcConstants.QUOTA_BUMP_ADDITIONAL_JOB_COUNT);
                    if (quotaController.mQuotaBumpAdditionalJobCount != max20) {
                        quotaController.mQuotaBumpAdditionalJobCount = max20;
                        qcConstants.mShouldReevaluateConstraints = z;
                    }
                    int max21 = Math.max(0, qcConstants.QUOTA_BUMP_ADDITIONAL_SESSION_COUNT);
                    if (quotaController.mQuotaBumpAdditionalSessionCount != max21) {
                        quotaController.mQuotaBumpAdditionalSessionCount = max21;
                        qcConstants.mShouldReevaluateConstraints = z;
                    }
                    long max22 = Math.max(0L, Math.min(600000L, qcConstants.QUOTA_BUMP_ADDITIONAL_DURATION_MS));
                    if (quotaController.mQuotaBumpAdditionalDurationMs != max22) {
                        quotaController.mQuotaBumpAdditionalDurationMs = max22;
                        qcConstants.mShouldReevaluateConstraints = true;
                        break;
                    }
                }
                break;
            case 7:
                long j3 = properties.getLong(str, 0L);
                qcConstants.EJ_REWARD_NOTIFICATION_SEEN_MS = j3;
                quotaController.mEJRewardNotificationSeenMs = Math.min(300000L, Math.max(0L, j3));
                break;
            case '\b':
                int i = properties.getInt(str, 75);
                qcConstants.MAX_JOB_COUNT_ACTIVE = i;
                int max23 = Math.max(10, i);
                int[] iArr = quotaController.mMaxBucketJobCounts;
                if (iArr[0] != max23) {
                    iArr[0] = max23;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case '\t':
                long j4 = properties.getLong(str, 30000L);
                qcConstants.EJ_TOP_APP_TIME_CHUNK_SIZE_MS = j4;
                long min13 = Math.min(900000L, Math.max(1L, j4));
                if (quotaController.mEJTopAppTimeChunkSizeMs != min13) {
                    quotaController.mEJTopAppTimeChunkSizeMs = min13;
                    if (min13 < quotaController.mEJRewardTopAppMs) {
                        Slog.w("JobScheduler.Quota", "EJ top app time chunk less than reward: " + quotaController.mEJTopAppTimeChunkSizeMs + " vs " + quotaController.mEJRewardTopAppMs);
                        break;
                    }
                }
                break;
            case '\n':
                int i2 = properties.getInt(str, 48);
                qcConstants.MAX_JOB_COUNT_RARE = i2;
                int max24 = Math.max(10, i2);
                int[] iArr2 = quotaController.mMaxBucketJobCounts;
                if (iArr2[3] != max24) {
                    iArr2[3] = max24;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case '\r':
                long j5 = properties.getLong(str, 15000L);
                qcConstants.EJ_REWARD_INTERACTION_MS = j5;
                quotaController.mEJRewardInteractionMs = Math.min(900000L, Math.max(5000L, j5));
                break;
            case 14:
            case 15:
            case 23:
                if (!qcConstants.mRateLimitingConstantsUpdated) {
                    qcConstants.mRateLimitingConstantsUpdated = true;
                    DeviceConfig.Properties properties5 = DeviceConfig.getProperties("jobscheduler", new String[]{"qc_rate_limiting_window_ms", "qc_max_job_count_per_rate_limiting_window", "qc_max_session_count_per_rate_limiting_window"});
                    qcConstants.RATE_LIMITING_WINDOW_MS = properties5.getLong("qc_rate_limiting_window_ms", 60000L);
                    qcConstants.MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW = properties5.getInt("qc_max_job_count_per_rate_limiting_window", 20);
                    qcConstants.MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW = properties5.getInt("qc_max_session_count_per_rate_limiting_window", 20);
                    long min14 = Math.min(BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, Math.max(30000L, qcConstants.RATE_LIMITING_WINDOW_MS));
                    if (quotaController.mRateLimitingWindowMs != min14) {
                        quotaController.mRateLimitingWindowMs = min14;
                        z2 = true;
                        qcConstants.mShouldReevaluateConstraints = true;
                    } else {
                        z2 = true;
                    }
                    int max25 = Math.max(10, qcConstants.MAX_JOB_COUNT_PER_RATE_LIMITING_WINDOW);
                    if (quotaController.mMaxJobCountPerRateLimitingWindow != max25) {
                        quotaController.mMaxJobCountPerRateLimitingWindow = max25;
                        qcConstants.mShouldReevaluateConstraints = z2;
                    }
                    int max26 = Math.max(10, qcConstants.MAX_SESSION_COUNT_PER_RATE_LIMITING_WINDOW);
                    if (quotaController.mMaxSessionCountPerRateLimitingWindow != max26) {
                        quotaController.mMaxSessionCountPerRateLimitingWindow = max26;
                        qcConstants.mShouldReevaluateConstraints = z2;
                        break;
                    }
                }
                break;
            case 16:
                int i3 = properties.getInt(str, 75);
                qcConstants.MAX_JOB_COUNT_EXEMPTED = i3;
                int max27 = Math.max(10, i3);
                int[] iArr3 = quotaController.mMaxBucketJobCounts;
                if (iArr3[6] != max27) {
                    iArr3[6] = max27;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case 17:
                int i4 = properties.getInt(str, 10);
                qcConstants.MAX_JOB_COUNT_RESTRICTED = i4;
                int max28 = Math.max(10, i4);
                int[] iArr4 = quotaController.mMaxBucketJobCounts;
                if (iArr4[5] != max28) {
                    iArr4[5] = max28;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case 20:
                int i5 = properties.getInt(str, 200);
                qcConstants.MAX_JOB_COUNT_FREQUENT = i5;
                int max29 = Math.max(10, i5);
                int[] iArr5 = quotaController.mMaxBucketJobCounts;
                if (iArr5[2] != max29) {
                    iArr5[2] = max29;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case 28:
                long j6 = properties.getLong(str, 60000L);
                qcConstants.EJ_GRACE_PERIOD_TOP_APP_MS = j6;
                quotaController.mEJGracePeriodTopAppMs = Math.min(ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, Math.max(0L, j6));
                break;
            case 31:
                int i6 = properties.getInt(str, 75);
                qcConstants.MAX_SESSION_COUNT_ACTIVE = i6;
                int max30 = Math.max(1, i6);
                int[] iArr6 = quotaController.mMaxBucketSessionCounts;
                if (iArr6[0] != max30) {
                    iArr6[0] = max30;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case '\"':
                long j7 = properties.getLong(str, 180000L);
                qcConstants.EJ_GRACE_PERIOD_TEMP_ALLOWLIST_MS = j7;
                quotaController.mEJGracePeriodTempAllowlistMs = Math.min(ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, Math.max(0L, j7));
                break;
            case '#':
                long j8 = properties.getLong(str, 60000L);
                qcConstants.MIN_QUOTA_CHECK_DELAY_MS = j8;
                quotaController.mInQuotaAlarmQueue.setMinTimeBetweenAlarmsMs(Math.min(900000L, Math.max(0L, j8)));
                break;
            case '%':
                int i7 = properties.getInt(str, 75);
                qcConstants.MAX_SESSION_COUNT_EXEMPTED = i7;
                int max31 = Math.max(1, i7);
                int[] iArr7 = quotaController.mMaxBucketSessionCounts;
                if (iArr7[6] != max31) {
                    iArr7[6] = max31;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case '&':
                int i8 = properties.getInt(str, 10);
                qcConstants.MAX_SESSION_COUNT_WORKING = i8;
                int max32 = Math.max(1, i8);
                int[] iArr8 = quotaController.mMaxBucketSessionCounts;
                if (iArr8[1] != max32) {
                    iArr8[1] = max32;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case ')':
                long j9 = properties.getLong(str, 10000L);
                qcConstants.EJ_REWARD_TOP_APP_MS = j9;
                long min15 = Math.min(900000L, Math.max(10000L, j9));
                if (quotaController.mEJRewardTopAppMs != min15) {
                    quotaController.mEJRewardTopAppMs = min15;
                    if (quotaController.mEJTopAppTimeChunkSizeMs < min15) {
                        Slog.w("JobScheduler.Quota", "EJ top app time chunk less than reward: " + quotaController.mEJTopAppTimeChunkSizeMs + " vs " + quotaController.mEJRewardTopAppMs);
                        break;
                    }
                }
                break;
            case '*':
                int i9 = properties.getInt(str, 120);
                qcConstants.MAX_JOB_COUNT_WORKING = i9;
                int max33 = Math.max(10, i9);
                int[] iArr9 = quotaController.mMaxBucketJobCounts;
                if (iArr9[1] != max33) {
                    iArr9[1] = max33;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case ',':
                long j10 = properties.getLong(str, 5000L);
                qcConstants.TIMING_SESSION_COALESCING_DURATION_MS = j10;
                long min16 = Math.min(900000L, Math.max(0L, j10));
                if (quotaController.mTimingSessionCoalescingDurationMs != min16) {
                    quotaController.mTimingSessionCoalescingDurationMs = min16;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case '.':
                int i10 = properties.getInt(str, 1);
                qcConstants.MAX_SESSION_COUNT_RESTRICTED = i10;
                int max34 = Math.max(0, i10);
                int[] iArr10 = quotaController.mMaxBucketSessionCounts;
                if (iArr10[5] != max34) {
                    iArr10[5] = max34;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case '/':
                int i11 = properties.getInt(str, 3);
                qcConstants.MAX_SESSION_COUNT_RARE = i11;
                int max35 = Math.max(1, i11);
                int[] iArr11 = quotaController.mMaxBucketSessionCounts;
                if (iArr11[3] != max35) {
                    iArr11[3] = max35;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
            case '0':
                int i12 = properties.getInt(str, 8);
                qcConstants.MAX_SESSION_COUNT_FREQUENT = i12;
                int max36 = Math.max(1, i12);
                int[] iArr12 = quotaController.mMaxBucketSessionCounts;
                if (iArr12[2] != max36) {
                    iArr12[2] = max36;
                    qcConstants.mShouldReevaluateConstraints = true;
                    break;
                }
                break;
        }
    }

    public void saveTimingSession(int i, String str, TimingSession timingSession, boolean z) {
        saveTimingSession(i, str, timingSession, z, 0L);
    }

    public final void saveTimingSession(int i, String str, TimingSession timingSession, boolean z, long j) {
        synchronized (this.mLock) {
            try {
                SparseArrayMap sparseArrayMap = z ? this.mEJTimingSessions : this.mTimingEvents;
                List list = (List) sparseArrayMap.get(i, str);
                if (list == null) {
                    list = new ArrayList();
                    sparseArrayMap.add(i, str, list);
                }
                list.add(timingSession);
                if (z) {
                    getEJDebitsLocked(i, str).transactLocked((timingSession.endTimeElapsed - timingSession.startTimeElapsed) + j);
                } else {
                    invalidateAllExecutionStatsLocked(i, str);
                    maybeScheduleCleanupAlarmLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setConstraintSatisfied(JobStatus jobStatus, long j, boolean z, boolean z2) {
        if (jobStatus.startedAsExpeditedJob) {
            z = z2;
        } else if (!this.mService.isCurrentlyRunningLocked(jobStatus)) {
            z = z2 || z;
        }
        if (!z && jobStatus.whenStandbyDeferred == 0) {
            jobStatus.whenStandbyDeferred = j;
        }
        if (!jobStatus.setConstraintSatisfied(16777216, j, z)) {
            return false;
        }
        jobStatus.mReadyWithinQuota = z;
        return true;
    }

    public final boolean setExpeditedQuotaApproved(JobStatus jobStatus, boolean z) {
        if (jobStatus.mExpeditedQuotaApproved == z) {
            return false;
        }
        boolean z2 = !z && jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest);
        jobStatus.mExpeditedQuotaApproved = z;
        jobStatus.mReadyNotDozing = jobStatus.isConstraintSatisfied(33554432) || jobStatus.canRunInDoze();
        boolean isReady = jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest);
        if (z2 && !isReady) {
            jobStatus.mReasonReadyToUnready = 10;
        } else if (!z2 && isReady) {
            jobStatus.mReasonReadyToUnready = 0;
        }
        this.mBackgroundJobsController.evaluateStateLocked(jobStatus);
        this.mConnectivityController.evaluateStateLocked(jobStatus);
        if (z && jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest)) {
            this.mStateChangedListener.onRunJobNow(jobStatus);
        }
        return true;
    }

    public final boolean transactQuotaLocked(int i, String str, long j, ShrinkableDebits shrinkableDebits, long j2) {
        Timer timer;
        long j3 = shrinkableDebits.mDebitTally;
        long transactLocked = shrinkableDebits.transactLocked(-j2);
        if (DEBUG) {
            Slog.d("JobScheduler.Quota", "debits overflowed by " + transactLocked);
        }
        boolean z = j3 != shrinkableDebits.mDebitTally;
        if (transactLocked == 0 || (timer = (Timer) this.mEJPkgTimers.get(i, str)) == null || !timer.isActive()) {
            return z;
        }
        timer.mDebitAdjustment = Math.max(timer.mDebitAdjustment + transactLocked, timer.mStartTimeElapsed - j);
        return true;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void unprepareFromExecutionLocked(JobStatus jobStatus) {
        Timer timer;
        SparseArrayMap sparseArrayMap = this.mPkgTimers;
        int i = jobStatus.sourceUserId;
        String str = jobStatus.sourcePackageName;
        Timer timer2 = (Timer) sparseArrayMap.get(i, str);
        if (timer2 != null) {
            timer2.stopTrackingJob(jobStatus);
        }
        if (jobStatus.isRequestedExpeditedJob() && (timer = (Timer) this.mEJPkgTimers.get(jobStatus.sourceUserId, str)) != null) {
            timer.stopTrackingJob(jobStatus);
        }
        this.mTopStartedJobs.remove(jobStatus);
    }

    public void updateExecutionStatsLocked(int i, String str, ExecutionStats executionStats) {
        long j;
        long j2;
        long j3;
        long j4;
        int i2;
        executionStats.executionTimeInWindowMs = 0L;
        executionStats.bgJobCountInWindow = 0;
        executionStats.executionTimeInMaxPeriodMs = 0L;
        executionStats.bgJobCountInMaxPeriod = 0;
        executionStats.sessionCountInWindow = 0;
        if (executionStats.jobCountLimit == 0 || executionStats.sessionCountLimit == 0) {
            executionStats.inQuotaTimeElapsed = Long.MAX_VALUE;
        } else {
            executionStats.inQuotaTimeElapsed = 0L;
        }
        long j5 = executionStats.allowedTimePerPeriodMs - this.mQuotaBufferMs;
        Timer timer = (Timer) this.mPkgTimers.get(i, str);
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        executionStats.expirationTimeElapsed = elapsedRealtime + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        if (timer != null && timer.isActive()) {
            long currentDuration = timer.getCurrentDuration(elapsedRealtime);
            executionStats.executionTimeInMaxPeriodMs = currentDuration;
            executionStats.executionTimeInWindowMs = currentDuration;
            synchronized (QuotaController.this.mLock) {
                i2 = timer.mBgJobCount;
            }
            executionStats.bgJobCountInMaxPeriod = i2;
            executionStats.bgJobCountInWindow = i2;
            executionStats.expirationTimeElapsed = elapsedRealtime;
            if (executionStats.executionTimeInWindowMs >= j5) {
                executionStats.inQuotaTimeElapsed = Math.max(executionStats.inQuotaTimeElapsed, (elapsedRealtime - j5) + executionStats.windowSizeMs);
            }
            long j6 = executionStats.executionTimeInMaxPeriodMs;
            long j7 = this.mMaxExecutionTimeIntoQuotaMs;
            if (j6 >= j7) {
                executionStats.inQuotaTimeElapsed = Math.max(executionStats.inQuotaTimeElapsed, (elapsedRealtime - j7) + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
            }
            if (executionStats.bgJobCountInWindow >= executionStats.jobCountLimit) {
                executionStats.inQuotaTimeElapsed = Math.max(executionStats.inQuotaTimeElapsed, executionStats.windowSizeMs + elapsedRealtime);
            }
        }
        List list = (List) this.mTimingEvents.get(i, str);
        if (list == null || list.size() == 0) {
            return;
        }
        long j8 = elapsedRealtime - executionStats.windowSizeMs;
        long j9 = elapsedRealtime - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        long j10 = elapsedRealtime - this.mQuotaBumpWindowSizeMs;
        int size = list.size() - 1;
        int i3 = 0;
        long j11 = Long.MAX_VALUE;
        while (size >= 0) {
            TimedEvent timedEvent = (TimedEvent) list.get(size);
            if (timedEvent.getEndTimeElapsed() < j10) {
                break;
            }
            j = j9;
            if (i3 >= this.mQuotaBumpLimit) {
                break;
            }
            if (timedEvent instanceof QuotaBump) {
                j4 = j5;
                executionStats.allowedTimePerPeriodMs += this.mQuotaBumpAdditionalDurationMs;
                executionStats.jobCountLimit += this.mQuotaBumpAdditionalJobCount;
                executionStats.sessionCountLimit += this.mQuotaBumpAdditionalSessionCount;
                i3++;
                j11 = Math.min(j11, timedEvent.getEndTimeElapsed() - j10);
            } else {
                j4 = j5;
            }
            size--;
            j9 = j;
            j5 = j4;
        }
        j = j9;
        long j12 = j5;
        TimingSession timingSession = null;
        int i4 = 0;
        int i5 = size;
        while (i5 >= 0) {
            TimedEvent timedEvent2 = (TimedEvent) list.get(i5);
            if (timedEvent2 instanceof QuotaBump) {
                j2 = j8;
            } else {
                TimingSession timingSession2 = (TimingSession) timedEvent2;
                if (j8 < timingSession2.endTimeElapsed) {
                    long j13 = timingSession2.startTimeElapsed;
                    if (j8 < j13) {
                        j11 = Math.min(j11, j13 - j8);
                    } else {
                        j13 = j8;
                        j11 = 0;
                    }
                    long j14 = (timingSession2.endTimeElapsed - j13) + executionStats.executionTimeInWindowMs;
                    executionStats.executionTimeInWindowMs = j14;
                    executionStats.bgJobCountInWindow += timingSession2.bgJobCount;
                    if (j14 >= j12) {
                        executionStats.inQuotaTimeElapsed = Math.max(executionStats.inQuotaTimeElapsed, ((j13 + j14) - j12) + executionStats.windowSizeMs);
                    }
                    if (executionStats.bgJobCountInWindow >= executionStats.jobCountLimit) {
                        executionStats.inQuotaTimeElapsed = Math.max(executionStats.inQuotaTimeElapsed, timingSession2.endTimeElapsed + executionStats.windowSizeMs);
                    }
                    if (timingSession == null || timingSession.startTimeElapsed - timingSession2.endTimeElapsed > this.mTimingSessionCoalescingDurationMs) {
                        int i6 = i4 + 1;
                        if (i6 >= executionStats.sessionCountLimit) {
                            executionStats.inQuotaTimeElapsed = Math.max(executionStats.inQuotaTimeElapsed, timingSession2.endTimeElapsed + executionStats.windowSizeMs);
                        }
                        i4 = i6;
                    }
                }
                long j15 = timingSession2.startTimeElapsed;
                if (j >= j15) {
                    j2 = j8;
                    long j16 = timingSession2.endTimeElapsed;
                    if (j >= j16) {
                        break;
                    }
                    long j17 = (j16 - j) + executionStats.executionTimeInMaxPeriodMs;
                    executionStats.executionTimeInMaxPeriodMs = j17;
                    executionStats.bgJobCountInMaxPeriod += timingSession2.bgJobCount;
                    long j18 = this.mMaxExecutionTimeIntoQuotaMs;
                    if (j17 >= j18) {
                        executionStats.inQuotaTimeElapsed = Math.max(executionStats.inQuotaTimeElapsed, ((j + j17) - j18) + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
                    }
                    j3 = 0;
                } else {
                    executionStats.executionTimeInMaxPeriodMs = (timingSession2.endTimeElapsed - j15) + executionStats.executionTimeInMaxPeriodMs;
                    executionStats.bgJobCountInMaxPeriod += timingSession2.bgJobCount;
                    long min = Math.min(j11, j15 - j);
                    long j19 = executionStats.executionTimeInMaxPeriodMs;
                    long j20 = this.mMaxExecutionTimeIntoQuotaMs;
                    if (j19 >= j20) {
                        j2 = j8;
                        executionStats.inQuotaTimeElapsed = Math.max(executionStats.inQuotaTimeElapsed, ((timingSession2.startTimeElapsed + j19) - j20) + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
                    } else {
                        j2 = j8;
                    }
                    j3 = min;
                }
                j11 = j3;
                timingSession = timingSession2;
            }
            i5--;
            j8 = j2;
        }
        executionStats.expirationTimeElapsed = elapsedRealtime + j11;
        executionStats.sessionCountInWindow = i4;
    }

    public void updateStandbyBucket(int i, String str, int i2) {
        int i3;
        if (DEBUG) {
            Slog.i("JobScheduler.Quota", "Moving pkg " + StateController.packageToString(i, str) + " to bucketIndex " + i2);
        }
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                ShrinkableDebits shrinkableDebits = (ShrinkableDebits) this.mEJStats.get(i, str);
                if (shrinkableDebits != null) {
                    shrinkableDebits.mStandbyBucket = i2;
                }
                ArraySet arraySet = (ArraySet) this.mTrackedJobs.get(i, str);
                if (arraySet != null && arraySet.size() != 0) {
                    int size = arraySet.size() - 1;
                    while (true) {
                        if (size < 0) {
                            break;
                        }
                        JobStatus jobStatus = (JobStatus) arraySet.valueAt(size);
                        if ((i2 == 5 || jobStatus.standbyBucket == 5) && i2 != jobStatus.standbyBucket) {
                            arrayList.add(jobStatus);
                        }
                        if (i2 == 5) {
                            jobStatus.addDynamicConstraints(268435463);
                        } else if (jobStatus.standbyBucket == 5) {
                            int i4 = jobStatus.mDynamicConstraints & (-268435464);
                            jobStatus.mDynamicConstraints = i4;
                            jobStatus.mReadyDynamicSatisfied = i4 != 0 && i4 == (jobStatus.satisfiedConstraints & i4);
                        }
                        jobStatus.standbyBucket = i2;
                        jobStatus.mLoggedBucketMismatch = false;
                        size--;
                    }
                    Timer timer = (Timer) this.mPkgTimers.get(i, str);
                    if (timer != null && timer.isActive()) {
                        timer.cancelCutoff();
                        timer.scheduleCutoff();
                    }
                    Timer timer2 = (Timer) this.mEJPkgTimers.get(i, str);
                    if (timer2 != null && timer2.isActive()) {
                        timer2.cancelCutoff();
                        timer2.scheduleCutoff();
                    }
                    JobSchedulerService jobSchedulerService = this.mStateChangedListener;
                    JobSchedulerService.sElapsedRealtimeClock.getClass();
                    jobSchedulerService.onControllerStateChanged(maybeUpdateConstraintForPkgLocked(i, str, SystemClock.elapsedRealtime()));
                    if (arrayList.size() > 0) {
                        JobSchedulerService jobSchedulerService2 = this.mStateChangedListener;
                        jobSchedulerService2.getClass();
                        int size2 = arrayList.size();
                        if (size2 == 0) {
                            Slog.wtf("JobScheduler", "onRestrictedBucketChanged called with no jobs");
                            return;
                        }
                        synchronized (jobSchedulerService2.mLock) {
                            for (i3 = 0; i3 < size2; i3++) {
                                try {
                                    JobStatus jobStatus2 = (JobStatus) arrayList.get(i3);
                                    for (int size3 = ((ArrayList) jobSchedulerService2.mRestrictiveControllers).size() - 1; size3 >= 0; size3--) {
                                        if (jobStatus2.standbyBucket == 5) {
                                            ((RestrictingController) ((ArrayList) jobSchedulerService2.mRestrictiveControllers).get(size3)).startTrackingRestrictedJobLocked(jobStatus2);
                                        } else {
                                            ((RestrictingController) ((ArrayList) jobSchedulerService2.mRestrictiveControllers).get(size3)).stopTrackingRestrictedJobLocked(jobStatus2);
                                        }
                                    }
                                } finally {
                                }
                            }
                        }
                        jobSchedulerService2.mHandler.obtainMessage(1).sendToTarget();
                    }
                }
            } finally {
            }
        }
    }
}
