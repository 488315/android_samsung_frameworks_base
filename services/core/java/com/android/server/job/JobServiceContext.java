package com.android.server.job;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.Notification;
import android.app.compat.CompatChanges;
import android.app.job.IJobCallback;
import android.app.job.IJobService;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobWorkItem;
import android.app.usage.UsageStatsManagerInternal;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.PermissionChecker;
import android.content.ServiceConnection;
import android.net.Network;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import android.util.Slog;
import android.util.TimeUtils;
import com.android.internal.app.IBatteryStats;
import com.android.internal.os.TimeoutRecord;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.modules.expresslog.Counter;
import com.android.modules.expresslog.Histogram;
import com.android.server.LocalServices;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.job.controllers.JobStatus;
import com.android.server.tare.EconomyManagerInternal;
import java.util.Objects;

/* loaded from: classes2.dex */
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
    public final Handler mCallbackHandler;
    public boolean mCancelled;
    public final JobCompletedListener mCompletedListener;
    public final Context mContext;
    public String mDeathMarkDebugReason;
    public int mDeathMarkInternalStopReason;
    public final EconomyManagerInternal mEconomyManagerInternal;
    public long mEstimatedDownloadBytes;
    public long mEstimatedUploadBytes;
    public long mExecutionStartTimeElapsed;
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
    int mVerb;
    public PowerManager.WakeLock mWakeLock;
    public IJobService service;
    public int mPendingStopReason = 0;
    public int mDeathMarkStopReason = 0;

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

    public final class JobCallback extends IJobCallback.Stub {
        public String mStoppedReason;
        public long mStoppedTime;

        public JobCallback() {
        }

        public void acknowledgeGetTransferredDownloadBytesMessage(int i, int i2, long j) {
            JobServiceContext.this.doAcknowledgeGetTransferredDownloadBytesMessage(this, i, i2, j);
        }

        public void acknowledgeGetTransferredUploadBytesMessage(int i, int i2, long j) {
            JobServiceContext.this.doAcknowledgeGetTransferredUploadBytesMessage(this, i, i2, j);
        }

        public void acknowledgeStartMessage(int i, boolean z) {
            JobServiceContext.this.doAcknowledgeStartMessage(this, i, z);
        }

        public void acknowledgeStopMessage(int i, boolean z) {
            JobServiceContext.this.doAcknowledgeStopMessage(this, i, z);
        }

        public JobWorkItem dequeueWork(int i) {
            return JobServiceContext.this.doDequeueWork(this, i);
        }

        public boolean completeWork(int i, int i2) {
            return JobServiceContext.this.doCompleteWork(this, i, i2);
        }

        public void jobFinished(int i, boolean z) {
            JobServiceContext.this.doJobFinished(this, i, z);
        }

        public void updateEstimatedNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) {
            JobServiceContext.this.doUpdateEstimatedNetworkBytes(this, i, jobWorkItem, j, j2);
        }

        public void updateTransferredNetworkBytes(int i, JobWorkItem jobWorkItem, long j, long j2) {
            JobServiceContext.this.doUpdateTransferredNetworkBytes(this, i, jobWorkItem, j, j2);
        }

        public void setNotification(int i, int i2, Notification notification, int i3) {
            JobServiceContext.this.doSetNotification(this, i, i2, notification, i3);
        }
    }

    public JobServiceContext(JobSchedulerService jobSchedulerService, JobConcurrencyManager jobConcurrencyManager, JobNotificationCoordinator jobNotificationCoordinator, IBatteryStats iBatteryStats, JobPackageTracker jobPackageTracker, Looper looper) {
        Context context = jobSchedulerService.getContext();
        this.mContext = context;
        this.mLock = jobSchedulerService.getLock();
        this.mService = jobSchedulerService;
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mBatteryStats = iBatteryStats;
        this.mEconomyManagerInternal = (EconomyManagerInternal) LocalServices.getService(EconomyManagerInternal.class);
        this.mJobPackageTracker = jobPackageTracker;
        this.mCallbackHandler = new JobServiceHandler(looper);
        this.mJobConcurrencyManager = jobConcurrencyManager;
        this.mNotificationCoordinator = jobNotificationCoordinator;
        this.mCompletedListener = jobSchedulerService;
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mAvailable = true;
        this.mVerb = 4;
        this.mPreferredUid = -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x01e8 A[Catch: all -> 0x043e, TryCatch #3 {, blocks: (B:4:0x0007, B:6:0x000c, B:7:0x0013, B:10:0x0015, B:12:0x002f, B:15:0x0042, B:17:0x0046, B:18:0x0056, B:20:0x005a, B:21:0x006a, B:23:0x0074, B:24:0x007b, B:26:0x00e8, B:28:0x00f4, B:29:0x0118, B:31:0x0172, B:33:0x0178, B:35:0x017e, B:37:0x01a6, B:40:0x01e8, B:42:0x01ec, B:43:0x020a, B:44:0x021e, B:46:0x0220, B:48:0x034f, B:50:0x037a, B:51:0x038e, B:53:0x03b2, B:54:0x03c8, B:56:0x03ce, B:57:0x03e6, B:59:0x0407, B:60:0x0414, B:61:0x043b, B:67:0x01ba, B:68:0x0189, B:70:0x018f, B:73:0x0196, B:75:0x019e), top: B:3:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0220 A[Catch: all -> 0x043e, TryCatch #3 {, blocks: (B:4:0x0007, B:6:0x000c, B:7:0x0013, B:10:0x0015, B:12:0x002f, B:15:0x0042, B:17:0x0046, B:18:0x0056, B:20:0x005a, B:21:0x006a, B:23:0x0074, B:24:0x007b, B:26:0x00e8, B:28:0x00f4, B:29:0x0118, B:31:0x0172, B:33:0x0178, B:35:0x017e, B:37:0x01a6, B:40:0x01e8, B:42:0x01ec, B:43:0x020a, B:44:0x021e, B:46:0x0220, B:48:0x034f, B:50:0x037a, B:51:0x038e, B:53:0x03b2, B:54:0x03c8, B:56:0x03ce, B:57:0x03e6, B:59:0x0407, B:60:0x0414, B:61:0x043b, B:67:0x01ba, B:68:0x0189, B:70:0x018f, B:73:0x0196, B:75:0x019e), top: B:3:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean executeRunnableJob(JobStatus jobStatus, int i) {
        Uri[] uriArr;
        String[] strArr;
        boolean z;
        boolean z2;
        Context.BindServiceFlags of;
        synchronized (this.mLock) {
            if (!this.mAvailable) {
                Slog.e("JobServiceContext", "Starting new runnable but context is unavailable > Error.");
                return false;
            }
            this.mPreferredUid = -1;
            this.mRunningJob = jobStatus;
            this.mRunningJobWorkType = i;
            this.mRunningCallback = new JobCallback();
            this.mPendingNetworkChange = null;
            boolean z3 = jobStatus.hasDeadlineConstraint() && jobStatus.getLatestRunTimeElapsed() < JobSchedulerService.sElapsedRealtimeClock.millis();
            ArraySet arraySet = jobStatus.changedUris;
            if (arraySet != null) {
                Uri[] uriArr2 = new Uri[arraySet.size()];
                jobStatus.changedUris.toArray(uriArr2);
                uriArr = uriArr2;
            } else {
                uriArr = null;
            }
            ArraySet arraySet2 = jobStatus.changedAuthorities;
            if (arraySet2 != null) {
                String[] strArr2 = new String[arraySet2.size()];
                jobStatus.changedAuthorities.toArray(strArr2);
                strArr = strArr2;
            } else {
                strArr = null;
            }
            JobInfo job = jobStatus.getJob();
            this.mParams = new JobParameters(this.mRunningCallback, jobStatus.getNamespace(), jobStatus.getJobId(), job.getExtras(), job.getTransientExtras(), job.getClipData(), job.getClipGrantFlags(), z3, jobStatus.shouldTreatAsExpeditedJob(), jobStatus.shouldTreatAsUserInitiatedJob(), uriArr, strArr, canGetNetworkInformation(jobStatus) ? jobStatus.network : null);
            long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
            this.mExecutionStartTimeElapsed = millis;
            this.mLastExecutionDurationStampTimeElapsed = millis;
            this.mMinExecutionGuaranteeMillis = this.mService.getMinJobExecutionGuaranteeMs(jobStatus);
            this.mMaxExecutionTimeMillis = Math.max(this.mService.getMaxJobExecutionTimeMs(jobStatus), this.mMinExecutionGuaranteeMillis);
            this.mEstimatedDownloadBytes = jobStatus.getEstimatedNetworkDownloadBytes();
            this.mEstimatedUploadBytes = jobStatus.getEstimatedNetworkUploadBytes();
            this.mTransferredUploadBytes = 0L;
            this.mTransferredDownloadBytes = 0L;
            this.mAwaitingNotification = jobStatus.isUserVisibleJob();
            long whenStandbyDeferred = jobStatus.getWhenStandbyDeferred();
            if (whenStandbyDeferred > 0) {
                long j = this.mExecutionStartTimeElapsed - whenStandbyDeferred;
                EventLog.writeEvent(8000, j);
                if (DEBUG_STANDBY) {
                    StringBuilder sb = new StringBuilder(128);
                    sb.append("Starting job deferred for standby by ");
                    TimeUtils.formatDuration(j, sb);
                    sb.append(" ms : ");
                    sb.append(jobStatus.toShortString());
                    Slog.v("JobServiceContext", sb.toString());
                }
            }
            jobStatus.clearPersistedUtcTimes();
            PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, jobStatus.getTag());
            this.mWakeLock = newWakeLock;
            newWakeLock.setWorkSource(this.mService.deriveWorkSource(jobStatus.getSourceUid(), jobStatus.getSourcePackageName()));
            this.mWakeLock.setReferenceCounted(false);
            this.mWakeLock.acquire();
            this.mEconomyManagerInternal.noteInstantaneousEvent(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName(), getStartActionId(jobStatus), String.valueOf(jobStatus.getJobId()));
            this.mVerb = 0;
            scheduleOpTimeOutLocked();
            Intent flags = new Intent().setComponent(jobStatus.getServiceComponent()).setFlags(4);
            try {
                if (jobStatus.shouldTreatAsUserInitiatedJob() && !jobStatus.isUserBgRestricted()) {
                    of = Context.BindServiceFlags.of(4295196673L);
                    z = true;
                } else {
                    if (!jobStatus.shouldTreatAsExpeditedJob() && !jobStatus.shouldTreatAsUserInitiatedJob()) {
                        of = Context.BindServiceFlags.of(33029L);
                        z = false;
                    }
                    of = Context.BindServiceFlags.of(229381L);
                    z = false;
                }
                try {
                    z2 = this.mContext.bindServiceAsUser(flags, this, of, UserHandle.of(jobStatus.getUserId()));
                } catch (SecurityException e) {
                    e = e;
                    Slog.w("JobServiceContext", "Job service " + jobStatus.getServiceComponent().getShortClassName() + " cannot be executed: " + e.getMessage());
                    z2 = false;
                    boolean z4 = z;
                    if (z2) {
                    }
                }
            } catch (SecurityException e2) {
                e = e2;
                z = false;
            }
            boolean z42 = z;
            if (z2) {
                if (DEBUG) {
                    Slog.d("JobServiceContext", jobStatus.getServiceComponent().getShortClassName() + " unavailable.");
                }
                this.mRunningJob = null;
                this.mRunningJobWorkType = 0;
                this.mRunningCallback = null;
                this.mParams = null;
                this.mExecutionStartTimeElapsed = 0L;
                this.mWakeLock.release();
                this.mVerb = 4;
                removeOpTimeOutLocked();
                return false;
            }
            this.mJobPackageTracker.noteActive(jobStatus);
            FrameworkStatsLog.write_non_chained(8, jobStatus.getSourceUid(), null, jobStatus.getBatteryName(), 1, -1, jobStatus.getStandbyBucket(), jobStatus.getLoggingJobId(), jobStatus.hasChargingConstraint(), jobStatus.hasBatteryNotLowConstraint(), jobStatus.hasStorageNotLowConstraint(), jobStatus.hasTimingDelayConstraint(), jobStatus.hasDeadlineConstraint(), jobStatus.hasIdleConstraint(), jobStatus.hasConnectivityConstraint(), jobStatus.hasContentTriggerConstraint(), jobStatus.isRequestedExpeditedJob(), jobStatus.shouldTreatAsExpeditedJob(), 0, jobStatus.getJob().isPrefetch(), jobStatus.getJob().getPriority(), jobStatus.getEffectivePriority(), jobStatus.getNumPreviousAttempts(), jobStatus.getJob().getMaxExecutionDelayMillis(), z3, jobStatus.isConstraintSatisfied(1), jobStatus.isConstraintSatisfied(2), jobStatus.isConstraintSatisfied(8), jobStatus.isConstraintSatisfied(Integer.MIN_VALUE), jobStatus.isConstraintSatisfied(4), jobStatus.isConstraintSatisfied(268435456), jobStatus.isConstraintSatisfied(67108864), this.mExecutionStartTimeElapsed - jobStatus.enqueueTime, jobStatus.getJob().isUserInitiated(), jobStatus.shouldTreatAsUserInitiatedJob(), jobStatus.getJob().isPeriodic(), jobStatus.getJob().getMinLatencyMillis(), jobStatus.getEstimatedNetworkDownloadBytes(), jobStatus.getEstimatedNetworkUploadBytes(), jobStatus.getWorkCount(), ActivityManager.processStateAmToProto(this.mService.getUidProcState(jobStatus.getUid())), jobStatus.getNamespaceHash());
            sEnqueuedJwiAtJobStart.logSampleWithUid(jobStatus.getUid(), jobStatus.getWorkCount());
            String sourcePackageName = jobStatus.getSourcePackageName();
            if (Trace.isTagEnabled(524288L)) {
                String packageName = jobStatus.getServiceComponent().getPackageName();
                String str = "*job*<" + jobStatus.getSourceUid() + ">" + sourcePackageName;
                if (!sourcePackageName.equals(packageName)) {
                    str = str + XmlUtils.STRING_ARRAY_SEPARATOR + packageName;
                }
                String str2 = str + "/" + jobStatus.getServiceComponent().getShortClassName();
                if (!packageName.equals(jobStatus.serviceProcessName)) {
                    str2 = str2 + "$" + jobStatus.serviceProcessName;
                }
                if (jobStatus.getNamespace() != null) {
                    str2 = str2 + "@" + jobStatus.getNamespace();
                }
                Trace.asyncTraceForTrackBegin(524288L, "JobScheduler", str2 + "#" + jobStatus.getJobId(), getId());
            }
            try {
                this.mBatteryStats.noteJobStart(jobStatus.getBatteryName(), jobStatus.getSourceUid());
            } catch (RemoteException unused) {
            }
            ((UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class)).setLastJobRunTime(sourcePackageName, jobStatus.getSourceUserId(), this.mExecutionStartTimeElapsed);
            this.mAvailable = false;
            this.mStoppedReason = null;
            this.mStoppedTime = 0L;
            jobStatus.startedAsExpeditedJob = jobStatus.shouldTreatAsExpeditedJob();
            jobStatus.startedAsUserInitiatedJob = jobStatus.shouldTreatAsUserInitiatedJob();
            jobStatus.startedWithForegroundFlag = z42;
            return true;
        }
    }

    public final boolean canGetNetworkInformation(JobStatus jobStatus) {
        if (jobStatus.getJob().getRequiredNetwork() == null) {
            return false;
        }
        int uid = jobStatus.getUid();
        return !CompatChanges.isChangeEnabled(271850009L, uid) || hasPermissionForDelivery(uid, jobStatus.getServiceComponent().getPackageName(), "android.permission.ACCESS_NETWORK_STATE");
    }

    public final boolean hasPermissionForDelivery(int i, String str, String str2) {
        return PermissionChecker.checkPermissionForDataDelivery(this.mContext, str2, -1, i, str, (String) null, "network info via JS") == 0;
    }

    public static int getStartActionId(JobStatus jobStatus) {
        int effectivePriority = jobStatus.getEffectivePriority();
        if (effectivePriority == 100) {
            return 1610612744;
        }
        if (effectivePriority == 200) {
            return 1610612742;
        }
        if (effectivePriority == 300) {
            return 1610612740;
        }
        if (effectivePriority == 400) {
            return 1610612738;
        }
        if (effectivePriority == 500) {
            return 1610612736;
        }
        Slog.wtf("JobServiceContext", "Unknown priority: " + JobInfo.getPriorityString(jobStatus.getEffectivePriority()));
        return 1610612740;
    }

    public JobStatus getRunningJobLocked() {
        return this.mRunningJob;
    }

    public int getRunningJobWorkType() {
        return this.mRunningJobWorkType;
    }

    public final String getRunningJobNameLocked() {
        JobStatus jobStatus = this.mRunningJob;
        return jobStatus != null ? jobStatus.toShortString() : "<null>";
    }

    public void cancelExecutingJobLocked(int i, int i2, String str) {
        doCancelLocked(i, i2, str);
    }

    public void markForProcessDeathLocked(int i, int i2, String str) {
        if (this.mVerb == 4) {
            if (DEBUG) {
                Slog.d("JobServiceContext", "Too late to mark for death (verb=" + this.mVerb + "), ignoring.");
                return;
            }
            return;
        }
        if (DEBUG) {
            Slog.d("JobServiceContext", "Marking " + this.mRunningJob.toShortString() + " for death because " + i + XmlUtils.STRING_ARRAY_SEPARATOR + str);
        }
        this.mDeathMarkStopReason = i;
        this.mDeathMarkInternalStopReason = i2;
        this.mDeathMarkDebugReason = str;
        if (this.mParams.getStopReason() == 0) {
            this.mParams.setStopReason(i, i2, str);
        }
    }

    public int getPreferredUid() {
        return this.mPreferredUid;
    }

    public void clearPreferredUid() {
        this.mPreferredUid = -1;
    }

    public int getId() {
        return hashCode();
    }

    public long getExecutionStartTimeElapsed() {
        return this.mExecutionStartTimeElapsed;
    }

    public long getRemainingGuaranteedTimeMs(long j) {
        return Math.max(0L, (this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis) - j);
    }

    public void informOfNetworkChangeLocked(Network network) {
        JobStatus jobStatus;
        if (network != null && (jobStatus = this.mRunningJob) != null && !canGetNetworkInformation(jobStatus)) {
            if (DEBUG) {
                Slog.d("JobServiceContext", "Skipping network change call because of missing permissions");
                return;
            }
            return;
        }
        if (this.mVerb != 2) {
            Slog.w("JobServiceContext", "Sending onNetworkChanged for a job that isn't started. " + this.mRunningJob);
            int i = this.mVerb;
            if (i == 0 || i == 1) {
                this.mPendingNetworkChange = network;
                return;
            }
            return;
        }
        try {
            this.mParams.setNetwork(network);
            this.mPendingNetworkChange = null;
            this.service.onNetworkChanged(this.mParams);
        } catch (RemoteException e) {
            Slog.e("JobServiceContext", "Error sending onNetworkChanged to client.", e);
            closeAndCleanupJobLocked(true, "host crashed when trying to inform of network change");
        }
    }

    public boolean isWithinExecutionGuaranteeTime() {
        return JobSchedulerService.sElapsedRealtimeClock.millis() < this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis;
    }

    public boolean stopIfExecutingLocked(String str, int i, String str2, boolean z, int i2, int i3, int i4) {
        JobStatus runningJobLocked = getRunningJobLocked();
        if (runningJobLocked == null) {
            return false;
        }
        if (i != -1 && i != runningJobLocked.getUserId()) {
            return false;
        }
        if ((str != null && !str.equals(runningJobLocked.getSourcePackageName())) || !Objects.equals(str2, runningJobLocked.getNamespace())) {
            return false;
        }
        if ((z && i2 != runningJobLocked.getJobId()) || this.mVerb != 2) {
            return false;
        }
        this.mParams.setStopReason(i3, i4, "stop from shell");
        sendStopMessageLocked("stop from shell");
        return true;
    }

    public Pair getEstimatedNetworkBytes() {
        return Pair.create(Long.valueOf(this.mEstimatedDownloadBytes), Long.valueOf(this.mEstimatedUploadBytes));
    }

    public Pair getTransferredNetworkBytes() {
        return Pair.create(Long.valueOf(this.mTransferredDownloadBytes), Long.valueOf(this.mTransferredUploadBytes));
    }

    public void doJobFinished(JobCallback jobCallback, int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (verifyCallerLocked(jobCallback)) {
                    this.mParams.setStopReason(0, 10, "app called jobFinished");
                    doCallbackLocked(z, "app called jobFinished");
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void doAcknowledgeGetTransferredDownloadBytesMessage(JobCallback jobCallback, int i, int i2, long j) {
        synchronized (this.mLock) {
            if (verifyCallerLocked(jobCallback)) {
                this.mTransferredDownloadBytes = j;
            }
        }
    }

    public final void doAcknowledgeGetTransferredUploadBytesMessage(JobCallback jobCallback, int i, int i2, long j) {
        synchronized (this.mLock) {
            if (verifyCallerLocked(jobCallback)) {
                this.mTransferredUploadBytes = j;
            }
        }
    }

    public void doAcknowledgeStopMessage(JobCallback jobCallback, int i, boolean z) {
        doCallback(jobCallback, z, null);
    }

    public void doAcknowledgeStartMessage(JobCallback jobCallback, int i, boolean z) {
        doCallback(jobCallback, z, "finished start");
    }

    public JobWorkItem doDequeueWork(JobCallback jobCallback, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (!assertCallerLocked(jobCallback)) {
                    return null;
                }
                int i2 = this.mVerb;
                if (i2 != 3 && i2 != 4) {
                    JobWorkItem dequeueWorkLocked = this.mRunningJob.dequeueWorkLocked();
                    if (dequeueWorkLocked == null && !this.mRunningJob.hasExecutingWorkLocked()) {
                        this.mParams.setStopReason(0, 10, "last work dequeued");
                        doCallbackLocked(false, "last work dequeued");
                    } else if (dequeueWorkLocked != null) {
                        this.mService.mJobs.touchJob(this.mRunningJob);
                    }
                    return dequeueWorkLocked;
                }
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean doCompleteWork(JobCallback jobCallback, int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (!assertCallerLocked(jobCallback)) {
                    return true;
                }
                if (this.mRunningJob.completeWorkLocked(i2)) {
                    this.mService.mJobs.touchJob(this.mRunningJob);
                    return true;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void doUpdateEstimatedNetworkBytes(JobCallback jobCallback, int i, JobWorkItem jobWorkItem, long j, long j2) {
        synchronized (this.mLock) {
            if (verifyCallerLocked(jobCallback)) {
                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_bytes_updated", this.mRunningJob.getUid());
                sUpdatedEstimatedNetworkDownloadKBLogger.logSample(JobSchedulerService.safelyScaleBytesToKBForHistogram(j));
                sUpdatedEstimatedNetworkUploadKBLogger.logSample(JobSchedulerService.safelyScaleBytesToKBForHistogram(j2));
                long j3 = this.mEstimatedDownloadBytes;
                if (j3 != -1 && j != -1) {
                    if (j3 < j) {
                        Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_download_bytes_increased", this.mRunningJob.getUid());
                    } else if (j3 > j) {
                        Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_download_bytes_decreased", this.mRunningJob.getUid());
                    }
                }
                long j4 = this.mEstimatedUploadBytes;
                if (j4 != -1 && j2 != -1) {
                    if (j4 < j2) {
                        Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_upload_bytes_increased", this.mRunningJob.getUid());
                    } else if (j4 > j2) {
                        Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_estimated_network_upload_bytes_decreased", this.mRunningJob.getUid());
                    }
                }
                this.mEstimatedDownloadBytes = j;
                this.mEstimatedUploadBytes = j2;
            }
        }
    }

    public final void doUpdateTransferredNetworkBytes(JobCallback jobCallback, int i, JobWorkItem jobWorkItem, long j, long j2) {
        synchronized (this.mLock) {
            if (verifyCallerLocked(jobCallback)) {
                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_bytes_updated", this.mRunningJob.getUid());
                sTransferredNetworkDownloadKBHighWaterMarkLogger.logSample(JobSchedulerService.safelyScaleBytesToKBForHistogram(j));
                sTransferredNetworkUploadKBHighWaterMarkLogger.logSample(JobSchedulerService.safelyScaleBytesToKBForHistogram(j2));
                long j3 = this.mTransferredDownloadBytes;
                if (j3 != -1 && j != -1) {
                    if (j3 < j) {
                        Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_download_bytes_increased", this.mRunningJob.getUid());
                    } else if (j3 > j) {
                        Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_download_bytes_decreased", this.mRunningJob.getUid());
                    }
                }
                long j4 = this.mTransferredUploadBytes;
                if (j4 != -1 && j2 != -1) {
                    if (j4 < j2) {
                        Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_upload_bytes_increased", this.mRunningJob.getUid());
                    } else if (j4 > j2) {
                        Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_transferred_network_upload_bytes_decreased", this.mRunningJob.getUid());
                    }
                }
                this.mTransferredDownloadBytes = j;
                this.mTransferredUploadBytes = j2;
            }
        }
    }

    public final void doSetNotification(JobCallback jobCallback, int i, int i2, Notification notification, int i3) {
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (verifyCallerLocked(jobCallback)) {
                    if (callingUid != this.mRunningJob.getUid()) {
                        Slog.wtfStack("JobServiceContext", "Calling UID isn't the same as running job's UID...");
                        throw new SecurityException("Can't post notification on behalf of another app");
                    }
                    this.mNotificationCoordinator.enqueueNotification(this, this.mRunningJob.getServiceComponent().getPackageName(), callingPid, callingUid, i2, notification, i3);
                    if (this.mAwaitingNotification) {
                        this.mAwaitingNotification = false;
                        if (this.mVerb == 2) {
                            scheduleOpTimeOutLocked();
                        }
                    }
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.mLock) {
            JobStatus jobStatus = this.mRunningJob;
            if (jobStatus != null && componentName.equals(jobStatus.getServiceComponent())) {
                this.service = IJobService.Stub.asInterface(iBinder);
                doServiceBoundLocked();
                return;
            }
            closeAndCleanupJobLocked(true, "connected for different component");
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.mLock) {
            int i = this.mDeathMarkStopReason;
            if (i != 0) {
                this.mParams.setStopReason(i, this.mDeathMarkInternalStopReason, this.mDeathMarkDebugReason);
            } else {
                JobStatus jobStatus = this.mRunningJob;
                if (jobStatus != null) {
                    Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_unexpected_service_disconnects", jobStatus.getUid());
                }
            }
            closeAndCleanupJobLocked(true, "unexpectedly disconnected");
        }
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(ComponentName componentName) {
        synchronized (this.mLock) {
            JobStatus jobStatus = this.mRunningJob;
            if (jobStatus == null) {
                Slog.e("JobServiceContext", "Binding died for " + componentName.getPackageName() + " but no running job on this context");
            } else if (jobStatus.getServiceComponent().equals(componentName)) {
                Slog.e("JobServiceContext", "Binding died for " + this.mRunningJob.getSourceUserId() + XmlUtils.STRING_ARRAY_SEPARATOR + componentName.getPackageName());
            } else {
                Slog.e("JobServiceContext", "Binding died for " + componentName.getPackageName() + " but context is running a different job");
            }
            closeAndCleanupJobLocked(true, "binding died");
        }
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(ComponentName componentName) {
        synchronized (this.mLock) {
            JobStatus jobStatus = this.mRunningJob;
            if (jobStatus == null) {
                Slog.wtf("JobServiceContext", "Got null binding for " + componentName.getPackageName() + " but no running job on this context");
            } else if (jobStatus.getServiceComponent().equals(componentName)) {
                Slog.wtf("JobServiceContext", "Got null binding for " + this.mRunningJob.getSourceUserId() + XmlUtils.STRING_ARRAY_SEPARATOR + componentName.getPackageName());
            } else {
                Slog.wtf("JobServiceContext", "Got null binding for " + componentName.getPackageName() + " but context is running a different job");
            }
            closeAndCleanupJobLocked(false, "null binding");
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

    public final boolean assertCallerLocked(JobCallback jobCallback) {
        if (verifyCallerLocked(jobCallback)) {
            return true;
        }
        long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
        if (!this.mPreviousJobHadSuccessfulFinish && millis - this.mLastUnsuccessfulFinishElapsed < 15000) {
            return false;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append("Caller no longer running");
        if (jobCallback.mStoppedReason != null) {
            sb.append(", last stopped ");
            TimeUtils.formatDuration(millis - jobCallback.mStoppedTime, sb);
            sb.append(" because: ");
            sb.append(jobCallback.mStoppedReason);
        }
        throw new SecurityException(sb.toString());
    }

    public class JobServiceHandler extends Handler {
        public JobServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 0) {
                synchronized (JobServiceContext.this.mLock) {
                    if (message.obj == JobServiceContext.this.mRunningCallback) {
                        JobServiceContext.this.handleOpTimeoutLocked();
                    } else {
                        JobCallback jobCallback = (JobCallback) message.obj;
                        StringBuilder sb = new StringBuilder(128);
                        sb.append("Ignoring timeout of no longer active job");
                        if (jobCallback.mStoppedReason != null) {
                            sb.append(", stopped ");
                            TimeUtils.formatDuration(JobSchedulerService.sElapsedRealtimeClock.millis() - jobCallback.mStoppedTime, sb);
                            sb.append(" because: ");
                            sb.append(jobCallback.mStoppedReason);
                        }
                        Slog.w("JobServiceContext", sb.toString());
                    }
                }
                return;
            }
            Slog.e("JobServiceContext", "Unrecognised message: " + message);
        }
    }

    public void doServiceBoundLocked() {
        removeOpTimeOutLocked();
        handleServiceBoundLocked();
    }

    public void doCallback(JobCallback jobCallback, boolean z, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (verifyCallerLocked(jobCallback)) {
                    doCallbackLocked(z, str);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void doCallbackLocked(boolean z, String str) {
        boolean z2 = DEBUG;
        if (z2) {
            Slog.d("JobServiceContext", "doCallback of : " + this.mRunningJob + " v:" + VERB_STRINGS[this.mVerb]);
        }
        removeOpTimeOutLocked();
        int i = this.mVerb;
        if (i == 1) {
            handleStartedLocked(z);
            return;
        }
        if (i == 2 || i == 3) {
            handleFinishedLocked(z, str);
        } else if (z2) {
            Slog.d("JobServiceContext", "Unrecognised callback: " + this.mRunningJob);
        }
    }

    public final void doCancelLocked(int i, int i2, String str) {
        int i3 = this.mVerb;
        if (i3 == 4 || i3 == 3) {
            if (DEBUG) {
                Slog.d("JobServiceContext", "Too late to process cancel for context (verb=" + this.mVerb + "), ignoring.");
                return;
            }
            return;
        }
        if (this.mRunningJob.startedAsExpeditedJob && i == 10) {
            if (JobSchedulerService.sElapsedRealtimeClock.millis() < this.mExecutionStartTimeElapsed + this.mMinExecutionGuaranteeMillis) {
                this.mPendingStopReason = i;
                this.mPendingInternalStopReason = i2;
                this.mPendingDebugStopReason = str;
                return;
            }
        }
        this.mParams.setStopReason(i, i2, str);
        if (i == 2) {
            JobStatus jobStatus = this.mRunningJob;
            this.mPreferredUid = jobStatus != null ? jobStatus.getUid() : -1;
        }
        handleCancelLocked(str);
    }

    public final void handleServiceBoundLocked() {
        boolean z = DEBUG;
        if (z) {
            Slog.d("JobServiceContext", "handleServiceBound for " + getRunningJobNameLocked());
        }
        if (this.mVerb != 0) {
            Slog.e("JobServiceContext", "Sending onStartJob for a job that isn't pending. " + VERB_STRINGS[this.mVerb]);
            closeAndCleanupJobLocked(false, "started job not pending");
            return;
        }
        if (this.mCancelled) {
            if (z) {
                Slog.d("JobServiceContext", "Job cancelled while waiting for bind to complete. " + this.mRunningJob);
            }
            closeAndCleanupJobLocked(true, "cancelled while waiting for bind");
            return;
        }
        try {
            this.mVerb = 1;
            scheduleOpTimeOutLocked();
            this.service.startJob(this.mParams);
        } catch (Exception e) {
            Slog.e("JobServiceContext", "Error sending onStart message to '" + this.mRunningJob.getServiceComponent().getShortClassName() + "' ", e);
        }
    }

    public final void handleStartedLocked(boolean z) {
        if (this.mVerb == 1) {
            this.mVerb = 2;
            if (!z) {
                handleFinishedLocked(false, "onStartJob returned false");
                return;
            }
            if (this.mCancelled) {
                if (DEBUG) {
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
                this.mService.informObserversOfUserVisibleJobChange(this, this.mRunningJob, true);
                return;
            }
            return;
        }
        Slog.e("JobServiceContext", "Handling started job but job wasn't starting! Was " + VERB_STRINGS[this.mVerb] + ".");
    }

    public final void handleFinishedLocked(boolean z, String str) {
        int i = this.mVerb;
        if (i == 2 || i == 3) {
            closeAndCleanupJobLocked(z, str);
            return;
        }
        Slog.e("JobServiceContext", "Got an execution complete message for a job that wasn't beingexecuted. Was " + VERB_STRINGS[this.mVerb] + ".");
    }

    public final void handleCancelLocked(String str) {
        if (JobSchedulerService.DEBUG) {
            Slog.d("JobServiceContext", "Handling cancel for: " + this.mRunningJob.getJobId() + " " + VERB_STRINGS[this.mVerb]);
        }
        int i = this.mVerb;
        if (i == 0 || i == 1) {
            this.mCancelled = true;
            applyStoppedReasonLocked(str);
        } else if (i == 2) {
            sendStopMessageLocked(str);
        } else if (i != 3) {
            Slog.e("JobServiceContext", "Cancelling a job without a valid verb: " + this.mVerb);
        }
    }

    public final void handleOpTimeoutLocked() {
        int i = this.mVerb;
        if (i == 0) {
            onSlowAppResponseLocked(true, true, "job_scheduler.value_cntr_w_uid_slow_app_response_binding", "timed out while binding", "Timed out while trying to bind", false);
            return;
        }
        if (i == 1) {
            onSlowAppResponseLocked(false, true, "job_scheduler.value_cntr_w_uid_slow_app_response_on_start_job", "timed out while starting", "No response to onStartJob", CompatChanges.isChangeEnabled(258236856L, this.mRunningJob.getUid()));
            return;
        }
        if (i != 2) {
            if (i == 3) {
                onSlowAppResponseLocked(true, false, "job_scheduler.value_cntr_w_uid_slow_app_response_on_stop_job", "timed out while stopping", "No response to onStopJob", CompatChanges.isChangeEnabled(258236856L, this.mRunningJob.getUid()));
                return;
            }
            Slog.e("JobServiceContext", "Handling timeout for an invalid job state: " + getRunningJobNameLocked() + ", dropping.");
            closeAndCleanupJobLocked(false, "invalid timeout");
            return;
        }
        if (this.mPendingStopReason != 0) {
            if (this.mService.isReadyToBeExecutedLocked(this.mRunningJob, false)) {
                this.mPendingStopReason = 0;
                this.mPendingInternalStopReason = 0;
                this.mPendingDebugStopReason = null;
            } else {
                Slog.i("JobServiceContext", "JS was waiting to stop this job. Sending onStop: " + getRunningJobNameLocked());
                this.mParams.setStopReason(this.mPendingStopReason, this.mPendingInternalStopReason, this.mPendingDebugStopReason);
                sendStopMessageLocked(this.mPendingDebugStopReason);
                return;
            }
        }
        long j = this.mExecutionStartTimeElapsed;
        long j2 = this.mMaxExecutionTimeMillis + j;
        long j3 = j + this.mMinExecutionGuaranteeMillis;
        long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
        if (millis >= j2) {
            Slog.i("JobServiceContext", "Client timed out while executing (no jobFinished received). Sending onStop: " + getRunningJobNameLocked());
            this.mParams.setStopReason(3, 3, "client timed out");
            sendStopMessageLocked("timeout while executing");
            return;
        }
        if (millis >= j3) {
            String shouldStopRunningJobLocked = this.mJobConcurrencyManager.shouldStopRunningJobLocked(this);
            if (shouldStopRunningJobLocked != null) {
                Slog.i("JobServiceContext", "Stopping client after min execution time: " + getRunningJobNameLocked() + " because " + shouldStopRunningJobLocked);
                this.mParams.setStopReason(4, 3, shouldStopRunningJobLocked);
                sendStopMessageLocked(shouldStopRunningJobLocked);
                return;
            }
            Slog.i("JobServiceContext", "Letting " + getRunningJobNameLocked() + " continue to run past min execution time");
            scheduleOpTimeOutLocked();
            return;
        }
        if (this.mAwaitingNotification) {
            onSlowAppResponseLocked(true, true, "job_scheduler.value_cntr_w_uid_slow_app_response_set_notification", "timed out while stopping", "required notification not provided", true);
            return;
        }
        long j4 = millis - this.mLastExecutionDurationStampTimeElapsed;
        if (j4 < BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS) {
            Slog.e("JobServiceContext", "Unexpected op timeout while EXECUTING");
        }
        this.mRunningJob.incrementCumulativeExecutionTime(j4);
        this.mService.mJobs.touchJob(this.mRunningJob);
        this.mLastExecutionDurationStampTimeElapsed = millis;
        scheduleOpTimeOutLocked();
    }

    public final void sendStopMessageLocked(String str) {
        removeOpTimeOutLocked();
        if (this.mVerb != 2) {
            Slog.e("JobServiceContext", "Sending onStopJob for a job that isn't started. " + this.mRunningJob);
            closeAndCleanupJobLocked(false, str);
            return;
        }
        try {
            applyStoppedReasonLocked(str);
            this.mVerb = 3;
            scheduleOpTimeOutLocked();
            this.service.stopJob(this.mParams);
        } catch (RemoteException e) {
            Slog.e("JobServiceContext", "Error sending onStopJob to client.", e);
            closeAndCleanupJobLocked(true, "host crashed when trying to stop");
        }
    }

    public final void onSlowAppResponseLocked(boolean z, boolean z2, String str, String str2, String str3, boolean z3) {
        Slog.w("JobServiceContext", str3 + " for " + getRunningJobNameLocked());
        Counter.logIncrementWithUid(str, this.mRunningJob.getUid());
        if (z2) {
            this.mParams.setStopReason(0, 12, str2);
        }
        if (z3) {
            ActivityManagerInternal activityManagerInternal = this.mActivityManagerInternal;
            JobStatus jobStatus = this.mRunningJob;
            activityManagerInternal.appNotResponding(jobStatus.serviceProcessName, jobStatus.getUid(), TimeoutRecord.forJobService(str3));
        }
        closeAndCleanupJobLocked(z, str2);
    }

    public final void closeAndCleanupJobLocked(boolean z, String str) {
        int i;
        int i2;
        if (this.mVerb == 4) {
            return;
        }
        boolean z2 = DEBUG;
        if (z2) {
            Slog.d("JobServiceContext", "Cleaning up " + this.mRunningJob.toShortString() + " reschedule=" + z + " reason=" + str);
        }
        long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
        applyStoppedReasonLocked(str);
        JobStatus jobStatus = this.mRunningJob;
        jobStatus.incrementCumulativeExecutionTime(millis - this.mLastExecutionDurationStampTimeElapsed);
        int stopReason = this.mParams.getStopReason();
        int internalStopReasonCode = this.mParams.getInternalStopReasonCode();
        if (this.mDeathMarkStopReason != 0) {
            if (z2) {
                Slog.d("JobServiceContext", "Job marked for death because of " + JobParameters.getInternalReasonCodeDescription(this.mDeathMarkInternalStopReason) + ": " + this.mDeathMarkDebugReason);
            }
            i = this.mDeathMarkStopReason;
            i2 = this.mDeathMarkInternalStopReason;
        } else {
            i = stopReason;
            i2 = internalStopReasonCode;
        }
        boolean z3 = internalStopReasonCode == 10;
        this.mPreviousJobHadSuccessfulFinish = z3;
        if (!z3) {
            this.mLastUnsuccessfulFinishElapsed = millis;
        }
        this.mJobPackageTracker.noteInactive(jobStatus, internalStopReasonCode, str);
        int i3 = i2;
        FrameworkStatsLog.write_non_chained(8, jobStatus.getSourceUid(), null, jobStatus.getBatteryName(), 0, internalStopReasonCode, jobStatus.getStandbyBucket(), jobStatus.getLoggingJobId(), jobStatus.hasChargingConstraint(), jobStatus.hasBatteryNotLowConstraint(), jobStatus.hasStorageNotLowConstraint(), jobStatus.hasTimingDelayConstraint(), jobStatus.hasDeadlineConstraint(), jobStatus.hasIdleConstraint(), jobStatus.hasConnectivityConstraint(), jobStatus.hasContentTriggerConstraint(), jobStatus.isRequestedExpeditedJob(), jobStatus.startedAsExpeditedJob, stopReason, jobStatus.getJob().isPrefetch(), jobStatus.getJob().getPriority(), jobStatus.getEffectivePriority(), jobStatus.getNumPreviousAttempts(), jobStatus.getJob().getMaxExecutionDelayMillis(), this.mParams.isOverrideDeadlineExpired(), jobStatus.isConstraintSatisfied(1), jobStatus.isConstraintSatisfied(2), jobStatus.isConstraintSatisfied(8), jobStatus.isConstraintSatisfied(Integer.MIN_VALUE), jobStatus.isConstraintSatisfied(4), jobStatus.isConstraintSatisfied(268435456), jobStatus.isConstraintSatisfied(67108864), this.mExecutionStartTimeElapsed - jobStatus.enqueueTime, jobStatus.getJob().isUserInitiated(), jobStatus.startedAsUserInitiatedJob, jobStatus.getJob().isPeriodic(), jobStatus.getJob().getMinLatencyMillis(), jobStatus.getEstimatedNetworkDownloadBytes(), jobStatus.getEstimatedNetworkUploadBytes(), jobStatus.getWorkCount(), ActivityManager.processStateAmToProto(this.mService.getUidProcState(jobStatus.getUid())), jobStatus.getNamespaceHash());
        if (Trace.isTagEnabled(524288L)) {
            Trace.asyncTraceForTrackEnd(524288L, "JobScheduler", getId());
        }
        try {
            this.mBatteryStats.noteJobFinish(this.mRunningJob.getBatteryName(), this.mRunningJob.getSourceUid(), internalStopReasonCode);
        } catch (RemoteException unused) {
        }
        if (stopReason == 3) {
            this.mEconomyManagerInternal.noteInstantaneousEvent(this.mRunningJob.getSourceUserId(), this.mRunningJob.getSourcePackageName(), 1610612746, String.valueOf(this.mRunningJob.getJobId()));
        }
        this.mNotificationCoordinator.removeNotificationAssociation(this, i, jobStatus);
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            wakeLock.release();
        }
        int i4 = this.mRunningJobWorkType;
        this.mContext.unbindService(this);
        this.mWakeLock = null;
        this.mRunningJob = null;
        this.mRunningJobWorkType = 0;
        this.mRunningCallback = null;
        this.mParams = null;
        this.mVerb = 4;
        this.mCancelled = false;
        this.service = null;
        this.mAvailable = true;
        this.mDeathMarkStopReason = 0;
        this.mDeathMarkInternalStopReason = 0;
        this.mDeathMarkDebugReason = null;
        this.mLastExecutionDurationStampTimeElapsed = 0L;
        this.mPendingStopReason = 0;
        this.mPendingInternalStopReason = 0;
        this.mPendingDebugStopReason = null;
        this.mPendingNetworkChange = null;
        removeOpTimeOutLocked();
        if (jobStatus.isUserVisibleJob()) {
            this.mService.informObserversOfUserVisibleJobChange(this, jobStatus, false);
        }
        this.mCompletedListener.onJobCompletedLocked(jobStatus, i, i3, z);
        this.mJobConcurrencyManager.onJobCompletedLocked(this, jobStatus, i4);
    }

    public final void applyStoppedReasonLocked(String str) {
        if (str == null || this.mStoppedReason != null) {
            return;
        }
        this.mStoppedReason = str;
        long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
        this.mStoppedTime = millis;
        JobCallback jobCallback = this.mRunningCallback;
        if (jobCallback != null) {
            jobCallback.mStoppedReason = this.mStoppedReason;
            jobCallback.mStoppedTime = millis;
        }
    }

    public final void scheduleOpTimeOutLocked() {
        long j;
        removeOpTimeOutLocked();
        int i = this.mVerb;
        if (i == 0) {
            j = OP_BIND_TIMEOUT_MILLIS;
        } else if (i == 2) {
            long j2 = this.mExecutionStartTimeElapsed;
            long j3 = this.mMinExecutionGuaranteeMillis + j2;
            long j4 = j2 + this.mMaxExecutionTimeMillis;
            long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
            long j5 = millis < j3 ? j3 - millis : j4 - millis;
            if (this.mAwaitingNotification) {
                j5 = Math.min(j5, NOTIFICATION_TIMEOUT_MILLIS);
            }
            j = Math.min(j5, BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
        } else {
            j = OP_TIMEOUT_MILLIS;
        }
        if (DEBUG) {
            Slog.d("JobServiceContext", "Scheduling time out for '" + this.mRunningJob.getServiceComponent().getShortClassName() + "' jId: " + this.mParams.getJobId() + ", in " + (j / 1000) + " s");
        }
        this.mCallbackHandler.sendMessageDelayed(this.mCallbackHandler.obtainMessage(0, this.mRunningCallback), j);
        this.mTimeoutElapsed = JobSchedulerService.sElapsedRealtimeClock.millis() + j;
    }

    public final void removeOpTimeOutLocked() {
        this.mCallbackHandler.removeMessages(0);
    }

    public void dumpLocked(IndentingPrintWriter indentingPrintWriter, long j) {
        JobStatus jobStatus = this.mRunningJob;
        if (jobStatus == null) {
            if (this.mStoppedReason != null) {
                indentingPrintWriter.print("inactive since ");
                TimeUtils.formatDuration(this.mStoppedTime, j, indentingPrintWriter);
                indentingPrintWriter.print(", stopped because: ");
                indentingPrintWriter.println(this.mStoppedReason);
                return;
            }
            indentingPrintWriter.println("inactive");
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
}
