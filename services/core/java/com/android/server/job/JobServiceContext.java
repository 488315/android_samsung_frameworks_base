package com.android.server.job;

import android.app.ActivityManagerInternal;
import android.app.Notification;
import android.app.compat.CompatChanges;
import android.app.job.IJobCallback;
import android.app.job.IJobService;
import android.app.job.JobParameters;
import android.app.job.JobWorkItem;
import android.app.usage.UsageStatsManagerInternal;
import android.content.ComponentName;
import android.content.Context;
import android.content.PermissionChecker;
import android.content.ServiceConnection;
import android.net.Network;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.TimeUtils;
import com.android.internal.app.IBatteryStats;
import com.android.internal.os.SomeArgs;
import com.android.internal.os.TimeoutRecord;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.modules.expresslog.Counter;
import com.android.modules.expresslog.Histogram;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.job.controllers.JobStatus;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class JobServiceContext implements ServiceConnection {
    public static final boolean DEBUG = JobSchedulerService.DEBUG;
    public static final boolean DEBUG_STANDBY = JobSchedulerService.DEBUG_STANDBY;
    public static final long NOTIFICATION_TIMEOUT_MILLIS;
    public static final long OP_BIND_TIMEOUT_MILLIS;
    public static final long OP_TIMEOUT_MILLIS;
    public static final String[] VERB_STRINGS;
    public static final Histogram sEnqueuedJwiAtJobStart;
    public static final Histogram sTransferredNetworkDownloadKBHighWaterMarkLogger;
    public static final Histogram sTransferredNetworkUploadKBHighWaterMarkLogger;
    public static final Histogram sUpdatedEstimatedNetworkDownloadKBLogger;
    public static final Histogram sUpdatedEstimatedNetworkUploadKBLogger;
    public final ActivityManagerInternal mActivityManagerInternal;
    public boolean mAvailable;
    public boolean mAwaitingNotification;
    public final IBatteryStats mBatteryStats;
    public final JobServiceHandler mCallbackHandler;
    public boolean mCancelled;
    public final JobSchedulerService mCompletedListener;
    public final Context mContext;
    public String mDeathMarkDebugReason;
    public int mDeathMarkInternalStopReason;
    public long mEstimatedDownloadBytes;
    public long mEstimatedUploadBytes;
    public long mExecutionStartTimeElapsed;
    public long mInitialDownloadedBytesFromCalling;
    public long mInitialDownloadedBytesFromSource;
    public long mInitialUploadedBytesFromCalling;
    public long mInitialUploadedBytesFromSource;
    public final JobConcurrencyManager mJobConcurrencyManager;
    public final JobPackageTracker mJobPackageTracker;
    public long mLastExecutionDurationStampTimeElapsed;
    public long mLastUnsuccessfulFinishElapsed;
    public final Object mLock;
    public long mMaxExecutionTimeMillis;
    public long mMinExecutionGuaranteeMillis;
    public final JobNotificationCoordinator mNotificationCoordinator;
    public JobParameters mParams;
    public String mPendingDebugStopReason;
    public int mPendingInternalStopReason;
    public Network mPendingNetworkChange;
    public final PowerManager mPowerManager;
    public int mPreferredUid;
    public boolean mPreviousJobHadSuccessfulFinish;
    public JobCallback mRunningCallback;
    public JobStatus mRunningJob;
    public int mRunningJobWorkType;
    public final JobSchedulerService mService;
    public String mStoppedReason;
    public long mStoppedTime;
    public long mTimeoutElapsed;
    public long mTransferredDownloadBytes;
    public long mTransferredUploadBytes;
    public final UsageStatsManagerInternal mUsageStatsManagerInternal;
    int mVerb;
    public PowerManager.WakeLock mWakeLock;
    public IJobService service;
    public int mPendingStopReason = 0;
    public int mDeathMarkStopReason = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class JobCallback extends IJobCallback.Stub {
        public String mStoppedReason;
        public long mStoppedTime;

        public JobCallback() {
        }

        public final void acknowledgeGetTransferredDownloadBytesMessage(int i, int i2, long j) {
            JobServiceContext jobServiceContext = JobServiceContext.this;
            synchronized (jobServiceContext.mLock) {
                try {
                    if (jobServiceContext.verifyCallerLocked(this)) {
                        jobServiceContext.mTransferredDownloadBytes = j;
                    }
                } finally {
                }
            }
        }

        public final void acknowledgeGetTransferredUploadBytesMessage(int i, int i2, long j) {
            JobServiceContext jobServiceContext = JobServiceContext.this;
            synchronized (jobServiceContext.mLock) {
                try {
                    if (jobServiceContext.verifyCallerLocked(this)) {
                        jobServiceContext.mTransferredUploadBytes = j;
                    }
                } finally {
                }
            }
        }

        public final void acknowledgeStartMessage(int i, boolean z) {
            JobServiceContext.this.doCallback(this, z, "finished start");
        }

        public final void acknowledgeStopMessage(int i, boolean z) {
            JobServiceContext.this.doCallback(this, z, null);
        }

        public final boolean completeWork(int i, int i2) {
            JobServiceContext jobServiceContext = JobServiceContext.this;
            jobServiceContext.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (jobServiceContext.mLock) {
                    if (jobServiceContext.assertCallerLocked(this)) {
                        JobStatus jobStatus = jobServiceContext.mRunningJob;
                        ArrayList arrayList = jobStatus.executingWork;
                        if (arrayList != null) {
                            int size = arrayList.size();
                            for (int i3 = 0; i3 < size; i3++) {
                                JobWorkItem jobWorkItem = (JobWorkItem) jobStatus.executingWork.get(i3);
                                if (jobWorkItem.getWorkId() == i2) {
                                    jobStatus.executingWork.remove(i3);
                                    if (jobWorkItem.getGrants() != null) {
                                        ((GrantedUriPermissions) jobWorkItem.getGrants()).revoke();
                                    }
                                    jobStatus.updateNetworkBytesLocked();
                                    jobServiceContext.mService.mJobs.touchJob(jobServiceContext.mRunningJob);
                                }
                            }
                        }
                        return false;
                    }
                    return true;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x006f, code lost:
        
            r8.mService.mJobs.touchJob(r8.mRunningJob);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.app.job.JobWorkItem dequeueWork(int r8) {
            /*
                r7 = this;
                com.android.server.job.JobServiceContext r8 = com.android.server.job.JobServiceContext.this
                r8.getClass()
                long r0 = android.os.Binder.clearCallingIdentity()
                java.lang.Object r2 = r8.mLock     // Catch: java.lang.Throwable -> L7f
                monitor-enter(r2)     // Catch: java.lang.Throwable -> L7f
                boolean r7 = r8.assertCallerLocked(r7)     // Catch: java.lang.Throwable -> L18
                r3 = 0
                if (r7 != 0) goto L1a
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L18
            L14:
                android.os.Binder.restoreCallingIdentity(r0)
                goto L7c
            L18:
                r7 = move-exception
                goto L7d
            L1a:
                int r7 = r8.mVerb     // Catch: java.lang.Throwable -> L18
                r4 = 3
                if (r7 == r4) goto L7a
                r4 = 4
                if (r7 != r4) goto L23
                goto L7a
            L23:
                com.android.server.job.controllers.JobStatus r7 = r8.mRunningJob     // Catch: java.lang.Throwable -> L18
                java.util.ArrayList r4 = r7.pendingWork     // Catch: java.lang.Throwable -> L18
                r5 = 0
                if (r4 == 0) goto L4d
                int r4 = r4.size()     // Catch: java.lang.Throwable -> L18
                if (r4 <= 0) goto L4d
                java.util.ArrayList r3 = r7.pendingWork     // Catch: java.lang.Throwable -> L18
                java.lang.Object r3 = r3.remove(r5)     // Catch: java.lang.Throwable -> L18
                android.app.job.JobWorkItem r3 = (android.app.job.JobWorkItem) r3     // Catch: java.lang.Throwable -> L18
                if (r3 == 0) goto L4d
                java.util.ArrayList r4 = r7.executingWork     // Catch: java.lang.Throwable -> L18
                if (r4 != 0) goto L45
                java.util.ArrayList r4 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L18
                r4.<init>()     // Catch: java.lang.Throwable -> L18
                r7.executingWork = r4     // Catch: java.lang.Throwable -> L18
            L45:
                java.util.ArrayList r7 = r7.executingWork     // Catch: java.lang.Throwable -> L18
                r7.add(r3)     // Catch: java.lang.Throwable -> L18
                r3.bumpDeliveryCount()     // Catch: java.lang.Throwable -> L18
            L4d:
                if (r3 != 0) goto L6d
                com.android.server.job.controllers.JobStatus r7 = r8.mRunningJob     // Catch: java.lang.Throwable -> L18
                java.util.ArrayList r7 = r7.executingWork     // Catch: java.lang.Throwable -> L18
                if (r7 == 0) goto L5c
                int r7 = r7.size()     // Catch: java.lang.Throwable -> L18
                if (r7 <= 0) goto L5c
                goto L6d
            L5c:
                android.app.job.JobParameters r7 = r8.mParams     // Catch: java.lang.Throwable -> L18
                java.lang.String r4 = "last work dequeued"
                r6 = 10
                r7.setStopReason(r5, r6, r4)     // Catch: java.lang.Throwable -> L18
                java.lang.String r7 = "last work dequeued"
                r8.doCallbackLocked(r7, r5)     // Catch: java.lang.Throwable -> L18
                goto L78
            L6d:
                if (r3 == 0) goto L78
                com.android.server.job.JobSchedulerService r7 = r8.mService     // Catch: java.lang.Throwable -> L18
                com.android.server.job.JobStore r7 = r7.mJobs     // Catch: java.lang.Throwable -> L18
                com.android.server.job.controllers.JobStatus r8 = r8.mRunningJob     // Catch: java.lang.Throwable -> L18
                r7.touchJob(r8)     // Catch: java.lang.Throwable -> L18
            L78:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L18
                goto L14
            L7a:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L18
                goto L14
            L7c:
                return r3
            L7d:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L18
                throw r7     // Catch: java.lang.Throwable -> L7f
            L7f:
                r7 = move-exception
                android.os.Binder.restoreCallingIdentity(r0)
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobServiceContext.JobCallback.dequeueWork(int):android.app.job.JobWorkItem");
        }

        public final void jobFinished(int i, boolean z) {
            JobServiceContext jobServiceContext = JobServiceContext.this;
            jobServiceContext.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (jobServiceContext.mLock) {
                    if (jobServiceContext.verifyCallerLocked(this)) {
                        jobServiceContext.mParams.setStopReason(0, 10, "app called jobFinished");
                        jobServiceContext.doCallbackLocked("app called jobFinished", z);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setNotification(int i, int i2, Notification notification, int i3) {
            JobServiceContext jobServiceContext = JobServiceContext.this;
            jobServiceContext.getClass();
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (jobServiceContext.mLock) {
                    if (jobServiceContext.verifyCallerLocked(this)) {
                        JobStatus jobStatus = jobServiceContext.mRunningJob;
                        if (callingUid != jobStatus.callingUid) {
                            Slog.wtfStack("JobServiceContext", "Calling UID isn't the same as running job's UID...");
                            throw new SecurityException("Can't post notification on behalf of another app");
                        }
                        jobServiceContext.mNotificationCoordinator.enqueueNotification(jobServiceContext, jobStatus.job.getService().getPackageName(), callingPid, callingUid, i2, notification, i3);
                        if (jobServiceContext.mAwaitingNotification) {
                            jobServiceContext.mAwaitingNotification = false;
                            if (jobServiceContext.mVerb == 2) {
                                jobServiceContext.scheduleOpTimeOutLocked();
                            }
                        }
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void updateEstimatedNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) {
            JobServiceContext jobServiceContext = JobServiceContext.this;
            synchronized (jobServiceContext.mLock) {
                try {
                    if (jobServiceContext.verifyCallerLocked(this)) {
                        Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_bytes_updated", jobServiceContext.mRunningJob.callingUid);
                        JobServiceContext.sUpdatedEstimatedNetworkDownloadKBLogger.logSample(JobSchedulerService.safelyScaleBytesToKBForHistogram(j));
                        JobServiceContext.sUpdatedEstimatedNetworkUploadKBLogger.logSample(JobSchedulerService.safelyScaleBytesToKBForHistogram(j2));
                        long j3 = jobServiceContext.mEstimatedDownloadBytes;
                        if (j3 != -1 && j != -1) {
                            if (j3 < j) {
                                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_download_bytes_increased", jobServiceContext.mRunningJob.callingUid);
                            } else if (j3 > j) {
                                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_download_bytes_decreased", jobServiceContext.mRunningJob.callingUid);
                            }
                        }
                        long j4 = jobServiceContext.mEstimatedUploadBytes;
                        if (j4 != -1 && j2 != -1) {
                            if (j4 < j2) {
                                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_upload_bytes_increased", jobServiceContext.mRunningJob.callingUid);
                            } else if (j4 > j2) {
                                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_upload_bytes_decreased", jobServiceContext.mRunningJob.callingUid);
                            }
                        }
                        jobServiceContext.mEstimatedDownloadBytes = j;
                        jobServiceContext.mEstimatedUploadBytes = j2;
                    }
                } finally {
                }
            }
        }

        public final void updateTransferredNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) {
            JobServiceContext jobServiceContext = JobServiceContext.this;
            synchronized (jobServiceContext.mLock) {
                try {
                    if (jobServiceContext.verifyCallerLocked(this)) {
                        Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_bytes_updated", jobServiceContext.mRunningJob.callingUid);
                        JobServiceContext.sTransferredNetworkDownloadKBHighWaterMarkLogger.logSample(JobSchedulerService.safelyScaleBytesToKBForHistogram(j));
                        JobServiceContext.sTransferredNetworkUploadKBHighWaterMarkLogger.logSample(JobSchedulerService.safelyScaleBytesToKBForHistogram(j2));
                        long j3 = jobServiceContext.mTransferredDownloadBytes;
                        if (j3 != -1 && j != -1) {
                            if (j3 < j) {
                                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_download_bytes_increased", jobServiceContext.mRunningJob.callingUid);
                            } else if (j3 > j) {
                                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_download_bytes_decreased", jobServiceContext.mRunningJob.callingUid);
                            }
                        }
                        long j4 = jobServiceContext.mTransferredUploadBytes;
                        if (j4 != -1 && j2 != -1) {
                            if (j4 < j2) {
                                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_upload_bytes_increased", jobServiceContext.mRunningJob.callingUid);
                            } else if (j4 > j2) {
                                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_upload_bytes_decreased", jobServiceContext.mRunningJob.callingUid);
                            }
                        }
                        jobServiceContext.mTransferredDownloadBytes = j;
                        jobServiceContext.mTransferredUploadBytes = j2;
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class JobServiceHandler extends Handler {
        public JobServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 0) {
                Slog.e("JobServiceContext", "Unrecognised message: " + message);
                return;
            }
            synchronized (JobServiceContext.this.mLock) {
                try {
                    Object obj = message.obj;
                    JobServiceContext jobServiceContext = JobServiceContext.this;
                    if (obj == jobServiceContext.mRunningCallback) {
                        JobServiceContext.m621$$Nest$mhandleOpTimeoutLocked(jobServiceContext);
                    } else {
                        JobCallback jobCallback = (JobCallback) obj;
                        StringBuilder sb = new StringBuilder(128);
                        sb.append("Ignoring timeout of no longer active job");
                        if (jobCallback.mStoppedReason != null) {
                            sb.append(", stopped ");
                            JobSchedulerService.sElapsedRealtimeClock.getClass();
                            TimeUtils.formatDuration(SystemClock.elapsedRealtime() - jobCallback.mStoppedTime, sb);
                            sb.append(" because: ");
                            sb.append(jobCallback.mStoppedReason);
                        }
                        Slog.w("JobServiceContext", sb.toString());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mhandleOpTimeoutLocked, reason: not valid java name */
    public static void m621$$Nest$mhandleOpTimeoutLocked(JobServiceContext jobServiceContext) {
        int i = jobServiceContext.mVerb;
        if (i == 0) {
            jobServiceContext.onSlowAppResponseLocked("job_scheduler.value_cntr_w_uid_slow_app_response_binding", true, "timed out while binding", true, false, "Timed out while trying to bind");
            return;
        }
        if (i == 1) {
            jobServiceContext.onSlowAppResponseLocked("job_scheduler.value_cntr_w_uid_slow_app_response_on_start_job", false, "timed out while starting", true, CompatChanges.isChangeEnabled(258236856L, jobServiceContext.mRunningJob.callingUid), "No response to onStartJob");
            return;
        }
        if (i != 2) {
            if (i == 3) {
                jobServiceContext.onSlowAppResponseLocked("job_scheduler.value_cntr_w_uid_slow_app_response_on_stop_job", true, "timed out while stopping", false, CompatChanges.isChangeEnabled(258236856L, jobServiceContext.mRunningJob.callingUid), "No response to onStopJob");
                return;
            }
            Slog.e("JobServiceContext", "Handling timeout for an invalid job state: " + jobServiceContext.getRunningJobNameLocked() + ", dropping.");
            jobServiceContext.closeAndCleanupJobLocked("invalid timeout", false);
            return;
        }
        if (jobServiceContext.mPendingStopReason != 0) {
            if (!jobServiceContext.mService.isReadyToBeExecutedLocked(jobServiceContext.mRunningJob, false)) {
                Slog.i("JobServiceContext", "JS was waiting to stop this job. Sending onStop: " + jobServiceContext.getRunningJobNameLocked());
                jobServiceContext.mParams.setStopReason(jobServiceContext.mPendingStopReason, jobServiceContext.mPendingInternalStopReason, jobServiceContext.mPendingDebugStopReason);
                jobServiceContext.sendStopMessageLocked(jobServiceContext.mPendingDebugStopReason);
                return;
            }
            jobServiceContext.mPendingStopReason = 0;
            jobServiceContext.mPendingInternalStopReason = 0;
            jobServiceContext.mPendingDebugStopReason = null;
        }
        long j = jobServiceContext.mExecutionStartTimeElapsed;
        long j2 = jobServiceContext.mMaxExecutionTimeMillis + j;
        long j3 = j + jobServiceContext.mMinExecutionGuaranteeMillis;
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime >= j2) {
            Slog.i("JobServiceContext", "Client timed out while executing (no jobFinished received). Sending onStop: " + jobServiceContext.getRunningJobNameLocked());
            jobServiceContext.mParams.setStopReason(3, 3, "client timed out");
            jobServiceContext.sendStopMessageLocked("timeout while executing");
            return;
        }
        if (elapsedRealtime < j3) {
            if (jobServiceContext.mAwaitingNotification) {
                jobServiceContext.onSlowAppResponseLocked("job_scheduler.value_cntr_w_uid_slow_app_response_set_notification", true, "timed out while stopping", true, true, "required notification not provided");
                return;
            }
            long j4 = elapsedRealtime - jobServiceContext.mLastExecutionDurationStampTimeElapsed;
            if (j4 < 300000) {
                Slog.e("JobServiceContext", "Unexpected op timeout while EXECUTING");
            }
            JobStatus jobStatus = jobServiceContext.mRunningJob;
            jobStatus.mCumulativeExecutionTimeMs += j4;
            jobServiceContext.mService.mJobs.touchJob(jobStatus);
            jobServiceContext.mLastExecutionDurationStampTimeElapsed = elapsedRealtime;
            jobServiceContext.scheduleOpTimeOutLocked();
            return;
        }
        String shouldStopRunningJobLocked = jobServiceContext.mJobConcurrencyManager.shouldStopRunningJobLocked(jobServiceContext);
        if (shouldStopRunningJobLocked == null) {
            Slog.i("JobServiceContext", "Letting " + jobServiceContext.getRunningJobNameLocked() + " continue to run past min execution time");
            jobServiceContext.scheduleOpTimeOutLocked();
            return;
        }
        Slog.i("JobServiceContext", "Stopping client after min execution time: " + jobServiceContext.getRunningJobNameLocked() + " because " + shouldStopRunningJobLocked);
        jobServiceContext.mParams.setStopReason(4, 3, shouldStopRunningJobLocked);
        jobServiceContext.sendStopMessageLocked(shouldStopRunningJobLocked);
    }

    static {
        int i = Build.HW_TIMEOUT_MULTIPLIER;
        OP_BIND_TIMEOUT_MILLIS = i * 18000;
        OP_TIMEOUT_MILLIS = i * 8000;
        NOTIFICATION_TIMEOUT_MILLIS = i * 10000;
        sEnqueuedJwiAtJobStart = new Histogram("job_scheduler.value_hist_w_uid_enqueued_work_items_at_job_start", new Histogram.ScaledRangeOptions(20, 1, 3.0f, 1.4f));
        sTransferredNetworkDownloadKBHighWaterMarkLogger = new Histogram("job_scheduler.value_hist_transferred_network_download_kilobytes_high_water_mark", new Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        sTransferredNetworkUploadKBHighWaterMarkLogger = new Histogram("job_scheduler.value_hist_transferred_network_upload_kilobytes_high_water_mark", new Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        sUpdatedEstimatedNetworkDownloadKBLogger = new Histogram("job_scheduler.value_hist_updated_estimated_network_download_kilobytes", new Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        sUpdatedEstimatedNetworkUploadKBLogger = new Histogram("job_scheduler.value_hist_updated_estimated_network_upload_kilobytes", new Histogram.ScaledRangeOptions(50, 0, 32.0f, 1.31f));
        VERB_STRINGS = new String[]{"VERB_BINDING", "VERB_STARTING", "VERB_EXECUTING", "VERB_STOPPING", "VERB_FINISHED"};
    }

    public JobServiceContext(JobSchedulerService jobSchedulerService, JobConcurrencyManager jobConcurrencyManager, JobNotificationCoordinator jobNotificationCoordinator, IBatteryStats iBatteryStats, JobPackageTracker jobPackageTracker, Looper looper) {
        Context context = jobSchedulerService.getContext();
        this.mContext = context;
        this.mLock = jobSchedulerService.mLock;
        this.mService = jobSchedulerService;
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mBatteryStats = iBatteryStats;
        this.mJobPackageTracker = jobPackageTracker;
        this.mCallbackHandler = new JobServiceHandler(looper);
        this.mJobConcurrencyManager = jobConcurrencyManager;
        this.mNotificationCoordinator = jobNotificationCoordinator;
        this.mCompletedListener = jobSchedulerService;
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mUsageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
        this.mAvailable = true;
        this.mVerb = 4;
        this.mPreferredUid = -1;
    }

    public final void applyStoppedReasonLocked(String str) {
        if (str == null || this.mStoppedReason != null) {
            return;
        }
        this.mStoppedReason = str;
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mStoppedTime = elapsedRealtime;
        JobCallback jobCallback = this.mRunningCallback;
        if (jobCallback != null) {
            jobCallback.mStoppedReason = this.mStoppedReason;
            jobCallback.mStoppedTime = elapsedRealtime;
        }
    }

    public final boolean assertCallerLocked(JobCallback jobCallback) {
        if (verifyCallerLocked(jobCallback)) {
            return true;
        }
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (!this.mPreviousJobHadSuccessfulFinish && elapsedRealtime - this.mLastUnsuccessfulFinishElapsed < 15000) {
            return false;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "Caller no longer running");
        if (jobCallback.mStoppedReason != null) {
            m.append(", last stopped ");
            TimeUtils.formatDuration(elapsedRealtime - jobCallback.mStoppedTime, m);
            m.append(" because: ");
            m.append(jobCallback.mStoppedReason);
        }
        throw new SecurityException(m.toString());
    }

    public final boolean canGetNetworkInformation(JobStatus jobStatus) {
        if (jobStatus.job.getRequiredNetwork() == null) {
            return false;
        }
        int i = jobStatus.callingUid;
        if (CompatChanges.isChangeEnabled(271850009L, i)) {
            return PermissionChecker.checkPermissionForDataDelivery(this.mContext, "android.permission.ACCESS_NETWORK_STATE", -1, i, jobStatus.job.getService().getPackageName(), (String) null, "network info via JS") == 0;
        }
        return true;
    }

    public final void cancelExecutingJobLocked(int i, int i2, String str) {
        int i3 = this.mVerb;
        if (i3 == 4 || i3 == 3) {
            if (DEBUG) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Too late to process cancel for context (verb="), this.mVerb, "), ignoring.", "JobServiceContext");
                return;
            }
            return;
        }
        if (this.mRunningJob.startedAsExpeditedJob && i == 10) {
            long j = this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis;
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            if (SystemClock.elapsedRealtime() < j) {
                this.mPendingStopReason = i;
                this.mPendingInternalStopReason = i2;
                this.mPendingDebugStopReason = str;
                return;
            }
        }
        this.mParams.setStopReason(i, i2, str);
        if (i == 2) {
            JobStatus jobStatus = this.mRunningJob;
            this.mPreferredUid = jobStatus != null ? jobStatus.callingUid : -1;
        }
        handleCancelLocked(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:204:0x0703, code lost:
    
        if (r5.lastEvaluatedBias < r6.lastEvaluatedBias) goto L254;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void closeAndCleanupJobLocked(java.lang.String r83, boolean r84) {
        /*
            Method dump skipped, instructions count: 1839
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobServiceContext.closeAndCleanupJobLocked(java.lang.String, boolean):void");
    }

    public final void doCallback(JobCallback jobCallback, boolean z, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (verifyCallerLocked(jobCallback)) {
                    doCallbackLocked(str, z);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void doCallbackLocked(String str, boolean z) {
        boolean z2 = DEBUG;
        if (z2) {
            StringBuilder sb = new StringBuilder("doCallback of : ");
            sb.append(this.mRunningJob);
            sb.append(" v:");
            BootReceiver$$ExternalSyntheticOutline0.m(sb, VERB_STRINGS[this.mVerb], "JobServiceContext");
        }
        removeOpTimeOutLocked();
        int i = this.mVerb;
        if (i != 1) {
            if (i == 2 || i == 3) {
                handleFinishedLocked(str, z);
                return;
            } else {
                if (z2) {
                    Slog.d("JobServiceContext", "Unrecognised callback: " + this.mRunningJob);
                    return;
                }
                return;
            }
        }
        if (i != 1) {
            Slog.e("JobServiceContext", "Handling started job but job wasn't starting! Was " + VERB_STRINGS[this.mVerb] + ".");
            return;
        }
        this.mVerb = 2;
        if (!z) {
            handleFinishedLocked("onStartJob returned false", false);
            return;
        }
        if (this.mCancelled) {
            if (z2) {
                Slog.d("JobServiceContext", "Job cancelled while waiting for onStartJob to complete.");
            }
            handleCancelLocked(null);
            return;
        }
        scheduleOpTimeOutLocked();
        if (this.mPendingNetworkChange != null && !Objects.equals(this.mParams.getNetwork(), this.mPendingNetworkChange)) {
            informOfNetworkChangeLocked(this.mPendingNetworkChange);
        }
        if (this.mRunningJob.isUserVisibleJob()) {
            JobSchedulerService jobSchedulerService = this.mService;
            JobStatus jobStatus = this.mRunningJob;
            jobSchedulerService.getClass();
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = this;
            obtain.arg2 = jobStatus;
            obtain.argi1 = 1;
            jobSchedulerService.mHandler.obtainMessage(11, obtain).sendToTarget();
        }
    }

    public final void doServiceBoundLocked() {
        removeOpTimeOutLocked();
        boolean z = DEBUG;
        if (z) {
            Slog.d("JobServiceContext", "handleServiceBound for " + getRunningJobNameLocked());
        }
        if (this.mVerb != 0) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(new StringBuilder("Sending onStartJob for a job that isn't pending. "), VERB_STRINGS[this.mVerb], "JobServiceContext");
            closeAndCleanupJobLocked("started job not pending", false);
            return;
        }
        if (this.mCancelled) {
            if (z) {
                Slog.d("JobServiceContext", "Job cancelled while waiting for bind to complete. " + this.mRunningJob);
            }
            closeAndCleanupJobLocked("cancelled while waiting for bind", true);
            return;
        }
        try {
            this.mVerb = 1;
            scheduleOpTimeOutLocked();
            this.service.startJob(this.mParams);
        } catch (Exception e) {
            Slog.e("JobServiceContext", "Error sending onStart message to '" + this.mRunningJob.job.getService().getShortClassName() + "' ", e);
        }
    }

    public final void dumpLocked(IndentingPrintWriter indentingPrintWriter, long j) {
        JobStatus jobStatus = this.mRunningJob;
        if (jobStatus == null) {
            if (this.mStoppedReason == null) {
                indentingPrintWriter.println("inactive");
                return;
            }
            indentingPrintWriter.print("inactive since ");
            TimeUtils.formatDuration(this.mStoppedTime, j, indentingPrintWriter);
            indentingPrintWriter.print(", stopped because: ");
            indentingPrintWriter.println(this.mStoppedReason);
            return;
        }
        indentingPrintWriter.println(jobStatus.toShortString());
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("Running for: ");
        TimeUtils.formatDuration(j - this.mExecutionStartTimeElapsed, indentingPrintWriter);
        indentingPrintWriter.print(", timeout at: ");
        TimeUtils.formatDuration(this.mTimeoutElapsed - j, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.print("Remaining execution limits: [");
        TimeUtils.formatDuration((this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis) - j, indentingPrintWriter);
        indentingPrintWriter.print(", ");
        TimeUtils.formatDuration((this.mExecutionStartTimeElapsed + this.mMaxExecutionTimeMillis) - j, indentingPrintWriter);
        indentingPrintWriter.print("]");
        if (this.mPendingStopReason != 0) {
            indentingPrintWriter.print(" Pending stop because ");
            indentingPrintWriter.print(this.mPendingStopReason);
            indentingPrintWriter.print("/");
            indentingPrintWriter.print(this.mPendingInternalStopReason);
            indentingPrintWriter.print("/");
            indentingPrintWriter.print(this.mPendingDebugStopReason);
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(24:10|(2:12|(17:14|15|(1:17)(1:89)|18|(1:20)(1:88)|21|(1:23)(1:87)|24|(2:26|(1:28))|29|30|31|(5:74|(2:79|80)|81|(1:83)(1:84)|80)(3:35|(1:37)(1:73)|38)|39|40|41|(5:43|(1:45)|46|47|48)(13:49|(1:51)(1:69)|52|(1:54)(1:68)|55|(1:57)|58|(1:60)|61|62|63|64|65)))|90|15|(0)(0)|18|(0)(0)|21|(0)(0)|24|(0)|29|30|31|(1:33)|74|(7:76|79|80|39|40|41|(0)(0))|81|(0)(0)|80|39|40|41|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01b1, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01b2, code lost:
    
        android.util.Slog.w("JobServiceContext", "Job service " + r77.job.getService().getShortClassName() + " cannot be executed: " + r0.getMessage());
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0178, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0179, code lost:
    
        r13 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004e A[Catch: all -> 0x0017, TryCatch #3 {all -> 0x0017, blocks: (B:4:0x0009, B:6:0x000e, B:7:0x0015, B:10:0x001a, B:12:0x0036, B:15:0x004a, B:17:0x004e, B:18:0x005e, B:20:0x0062, B:21:0x0072, B:23:0x007a, B:24:0x0081, B:26:0x00ef, B:28:0x00fb, B:29:0x011f, B:31:0x015d, B:33:0x0163, B:35:0x0167, B:40:0x019b, B:43:0x01dd, B:45:0x01e1, B:46:0x0201, B:47:0x021a, B:49:0x021c, B:51:0x0243, B:54:0x0252, B:55:0x025f, B:57:0x036c, B:58:0x0379, B:60:0x0381, B:62:0x0394, B:63:0x039d, B:64:0x03c4, B:68:0x0259, B:69:0x024b, B:72:0x01b2, B:74:0x017b, B:76:0x0181, B:81:0x018d), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0062 A[Catch: all -> 0x0017, TryCatch #3 {all -> 0x0017, blocks: (B:4:0x0009, B:6:0x000e, B:7:0x0015, B:10:0x001a, B:12:0x0036, B:15:0x004a, B:17:0x004e, B:18:0x005e, B:20:0x0062, B:21:0x0072, B:23:0x007a, B:24:0x0081, B:26:0x00ef, B:28:0x00fb, B:29:0x011f, B:31:0x015d, B:33:0x0163, B:35:0x0167, B:40:0x019b, B:43:0x01dd, B:45:0x01e1, B:46:0x0201, B:47:0x021a, B:49:0x021c, B:51:0x0243, B:54:0x0252, B:55:0x025f, B:57:0x036c, B:58:0x0379, B:60:0x0381, B:62:0x0394, B:63:0x039d, B:64:0x03c4, B:68:0x0259, B:69:0x024b, B:72:0x01b2, B:74:0x017b, B:76:0x0181, B:81:0x018d), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007a A[Catch: all -> 0x0017, TryCatch #3 {all -> 0x0017, blocks: (B:4:0x0009, B:6:0x000e, B:7:0x0015, B:10:0x001a, B:12:0x0036, B:15:0x004a, B:17:0x004e, B:18:0x005e, B:20:0x0062, B:21:0x0072, B:23:0x007a, B:24:0x0081, B:26:0x00ef, B:28:0x00fb, B:29:0x011f, B:31:0x015d, B:33:0x0163, B:35:0x0167, B:40:0x019b, B:43:0x01dd, B:45:0x01e1, B:46:0x0201, B:47:0x021a, B:49:0x021c, B:51:0x0243, B:54:0x0252, B:55:0x025f, B:57:0x036c, B:58:0x0379, B:60:0x0381, B:62:0x0394, B:63:0x039d, B:64:0x03c4, B:68:0x0259, B:69:0x024b, B:72:0x01b2, B:74:0x017b, B:76:0x0181, B:81:0x018d), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00ef A[Catch: all -> 0x0017, TryCatch #3 {all -> 0x0017, blocks: (B:4:0x0009, B:6:0x000e, B:7:0x0015, B:10:0x001a, B:12:0x0036, B:15:0x004a, B:17:0x004e, B:18:0x005e, B:20:0x0062, B:21:0x0072, B:23:0x007a, B:24:0x0081, B:26:0x00ef, B:28:0x00fb, B:29:0x011f, B:31:0x015d, B:33:0x0163, B:35:0x0167, B:40:0x019b, B:43:0x01dd, B:45:0x01e1, B:46:0x0201, B:47:0x021a, B:49:0x021c, B:51:0x0243, B:54:0x0252, B:55:0x025f, B:57:0x036c, B:58:0x0379, B:60:0x0381, B:62:0x0394, B:63:0x039d, B:64:0x03c4, B:68:0x0259, B:69:0x024b, B:72:0x01b2, B:74:0x017b, B:76:0x0181, B:81:0x018d), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01dd A[Catch: all -> 0x0017, TryCatch #3 {all -> 0x0017, blocks: (B:4:0x0009, B:6:0x000e, B:7:0x0015, B:10:0x001a, B:12:0x0036, B:15:0x004a, B:17:0x004e, B:18:0x005e, B:20:0x0062, B:21:0x0072, B:23:0x007a, B:24:0x0081, B:26:0x00ef, B:28:0x00fb, B:29:0x011f, B:31:0x015d, B:33:0x0163, B:35:0x0167, B:40:0x019b, B:43:0x01dd, B:45:0x01e1, B:46:0x0201, B:47:0x021a, B:49:0x021c, B:51:0x0243, B:54:0x0252, B:55:0x025f, B:57:0x036c, B:58:0x0379, B:60:0x0381, B:62:0x0394, B:63:0x039d, B:64:0x03c4, B:68:0x0259, B:69:0x024b, B:72:0x01b2, B:74:0x017b, B:76:0x0181, B:81:0x018d), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x021c A[Catch: all -> 0x0017, TryCatch #3 {all -> 0x0017, blocks: (B:4:0x0009, B:6:0x000e, B:7:0x0015, B:10:0x001a, B:12:0x0036, B:15:0x004a, B:17:0x004e, B:18:0x005e, B:20:0x0062, B:21:0x0072, B:23:0x007a, B:24:0x0081, B:26:0x00ef, B:28:0x00fb, B:29:0x011f, B:31:0x015d, B:33:0x0163, B:35:0x0167, B:40:0x019b, B:43:0x01dd, B:45:0x01e1, B:46:0x0201, B:47:0x021a, B:49:0x021c, B:51:0x0243, B:54:0x0252, B:55:0x025f, B:57:0x036c, B:58:0x0379, B:60:0x0381, B:62:0x0394, B:63:0x039d, B:64:0x03c4, B:68:0x0259, B:69:0x024b, B:72:0x01b2, B:74:0x017b, B:76:0x0181, B:81:0x018d), top: B:3:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean executeRunnableJob(com.android.server.job.controllers.JobStatus r77, int r78) {
        /*
            Method dump skipped, instructions count: 969
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.JobServiceContext.executeRunnableJob(com.android.server.job.controllers.JobStatus, int):boolean");
    }

    public final String getRunningJobNameLocked() {
        JobStatus jobStatus = this.mRunningJob;
        return jobStatus != null ? jobStatus.toShortString() : "<null>";
    }

    public final void handleCancelLocked(String str) {
        if (JobSchedulerService.DEBUG) {
            StringBuilder sb = new StringBuilder("Handling cancel for: ");
            sb.append(this.mRunningJob.job.getId());
            sb.append(" ");
            BootReceiver$$ExternalSyntheticOutline0.m(sb, VERB_STRINGS[this.mVerb], "JobServiceContext");
        }
        int i = this.mVerb;
        if (i == 0 || i == 1) {
            this.mCancelled = true;
            applyStoppedReasonLocked(str);
        } else if (i == 2) {
            sendStopMessageLocked(str);
        } else if (i != 3) {
            VaultKeeperService$$ExternalSyntheticOutline0.m(new StringBuilder("Cancelling a job without a valid verb: "), this.mVerb, "JobServiceContext");
        }
    }

    public final void handleFinishedLocked(String str, boolean z) {
        int i = this.mVerb;
        if (i == 2 || i == 3) {
            closeAndCleanupJobLocked(str, z);
            return;
        }
        Slog.e("JobServiceContext", "Got an execution complete message for a job that wasn't beingexecuted. Was " + VERB_STRINGS[this.mVerb] + ".");
    }

    public final void informOfNetworkChangeLocked(Network network) {
        JobStatus jobStatus;
        if (network != null && (jobStatus = this.mRunningJob) != null && !canGetNetworkInformation(jobStatus)) {
            if (DEBUG) {
                Slog.d("JobServiceContext", "Skipping network change call because of missing permissions");
                return;
            }
            return;
        }
        if (this.mVerb == 2) {
            try {
                this.mParams.setNetwork(network);
                this.mPendingNetworkChange = null;
                this.service.onNetworkChanged(this.mParams);
                return;
            } catch (RemoteException e) {
                Slog.e("JobServiceContext", "Error sending onNetworkChanged to client.", e);
                closeAndCleanupJobLocked("host crashed when trying to inform of network change", true);
                return;
            }
        }
        Slog.w("JobServiceContext", "Sending onNetworkChanged for a job that isn't started. " + this.mRunningJob);
        int i = this.mVerb;
        if (i == 0 || i == 1) {
            this.mPendingNetworkChange = network;
        }
    }

    public final boolean isWithinExecutionGuaranteeTime() {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        return SystemClock.elapsedRealtime() < this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis;
    }

    @Override // android.content.ServiceConnection
    public final void onBindingDied(ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                JobStatus jobStatus = this.mRunningJob;
                if (jobStatus == null) {
                    Slog.e("JobServiceContext", "Binding died for " + componentName.getPackageName() + " but no running job on this context");
                } else if (jobStatus.job.getService().equals(componentName)) {
                    Slog.e("JobServiceContext", "Binding died for " + this.mRunningJob.sourceUserId + ":" + componentName.getPackageName());
                } else {
                    Slog.e("JobServiceContext", "Binding died for " + componentName.getPackageName() + " but context is running a different job");
                }
                closeAndCleanupJobLocked("binding died", true);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onNullBinding(ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                JobStatus jobStatus = this.mRunningJob;
                if (jobStatus == null) {
                    Slog.wtf("JobServiceContext", "Got null binding for " + componentName.getPackageName() + " but no running job on this context");
                } else if (jobStatus.job.getService().equals(componentName)) {
                    Slog.wtf("JobServiceContext", "Got null binding for " + this.mRunningJob.sourceUserId + ":" + componentName.getPackageName());
                } else {
                    Slog.wtf("JobServiceContext", "Got null binding for " + componentName.getPackageName() + " but context is running a different job");
                }
                closeAndCleanupJobLocked("null binding", false);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                JobStatus jobStatus = this.mRunningJob;
                if (jobStatus != null && componentName.equals(jobStatus.job.getService())) {
                    this.service = IJobService.Stub.asInterface(iBinder);
                    doServiceBoundLocked();
                    return;
                }
                closeAndCleanupJobLocked("connected for different component", true);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                int i = this.mDeathMarkStopReason;
                if (i != 0) {
                    this.mParams.setStopReason(i, this.mDeathMarkInternalStopReason, this.mDeathMarkDebugReason);
                } else {
                    JobStatus jobStatus = this.mRunningJob;
                    if (jobStatus != null) {
                        Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_unexpected_service_disconnects", jobStatus.callingUid);
                    }
                }
                closeAndCleanupJobLocked("unexpectedly disconnected", true);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSlowAppResponseLocked(String str, boolean z, String str2, boolean z2, boolean z3, String str3) {
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str3, " for ");
        m.append(getRunningJobNameLocked());
        Slog.w("JobServiceContext", m.toString());
        Counter.logIncrementWithUid(str, this.mRunningJob.callingUid);
        if (z2) {
            this.mParams.setStopReason(0, 12, str2);
        }
        if (z3) {
            ActivityManagerInternal activityManagerInternal = this.mActivityManagerInternal;
            JobStatus jobStatus = this.mRunningJob;
            activityManagerInternal.appNotResponding(jobStatus.serviceProcessName, jobStatus.callingUid, TimeoutRecord.forJobService(str3));
        }
        closeAndCleanupJobLocked(str2, z);
    }

    public final void removeOpTimeOutLocked() {
        this.mCallbackHandler.removeMessages(0);
    }

    public final void scheduleOpTimeOutLocked() {
        long j;
        removeOpTimeOutLocked();
        int i = this.mVerb;
        if (i == 0) {
            j = OP_BIND_TIMEOUT_MILLIS;
        } else if (i != 2) {
            j = OP_TIMEOUT_MILLIS;
        } else {
            long j2 = this.mExecutionStartTimeElapsed;
            long j3 = this.mMinExecutionGuaranteeMillis + j2;
            long j4 = j2 + this.mMaxExecutionTimeMillis;
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j5 = elapsedRealtime < j3 ? j3 - elapsedRealtime : j4 - elapsedRealtime;
            if (this.mAwaitingNotification) {
                j5 = Math.min(j5, NOTIFICATION_TIMEOUT_MILLIS);
            }
            j = Math.min(j5, 300000L);
        }
        if (DEBUG) {
            Slog.d("JobServiceContext", "Scheduling time out for '" + this.mRunningJob.job.getService().getShortClassName() + "' jId: " + this.mParams.getJobId() + ", in " + (j / 1000) + " s");
        }
        this.mCallbackHandler.sendMessageDelayed(this.mCallbackHandler.obtainMessage(0, this.mRunningCallback), j);
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        this.mTimeoutElapsed = SystemClock.elapsedRealtime() + j;
    }

    public final void sendStopMessageLocked(String str) {
        removeOpTimeOutLocked();
        if (this.mVerb != 2) {
            Slog.e("JobServiceContext", "Sending onStopJob for a job that isn't started. " + this.mRunningJob);
            closeAndCleanupJobLocked(str, false);
            return;
        }
        try {
            applyStoppedReasonLocked(str);
            this.mVerb = 3;
            scheduleOpTimeOutLocked();
            this.service.stopJob(this.mParams);
        } catch (RemoteException e) {
            Slog.e("JobServiceContext", "Error sending onStopJob to client.", e);
            closeAndCleanupJobLocked("host crashed when trying to stop", true);
        }
    }

    public final boolean verifyCallerLocked(JobCallback jobCallback) {
        if (this.mRunningCallback == jobCallback) {
            return true;
        }
        if (!DEBUG) {
            return false;
        }
        Slog.d("JobServiceContext", "Stale callback received, ignoring.");
        return false;
    }
}
