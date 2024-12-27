package com.android.server.content;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseLongArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class SyncJobService extends JobService {
    public static SyncJobService sInstance;
    public static final Object sLock = new Object();
    public static final SparseArray sJobParamsMap = new SparseArray();
    public static final SparseBooleanArray sStartedSyncs = new SparseBooleanArray();
    public static final SparseLongArray sJobStartUptimes = new SparseLongArray();
    public static final SyncLogger sLogger = SyncLogger.getInstance();

    public static void callJobFinished(int i, String str) {
        SyncJobService syncJobService;
        Object obj = sLock;
        synchronized (obj) {
            try {
                if (sInstance == null) {
                    Slog.wtf("SyncManager", "sInstance == null");
                }
                syncJobService = sInstance;
            } finally {
            }
        }
        if (syncJobService != null) {
            synchronized (obj) {
                try {
                    SparseArray sparseArray = sJobParamsMap;
                    JobParameters jobParameters = (JobParameters) sparseArray.get(i);
                    SyncLogger syncLogger = sLogger;
                    syncLogger.log(
                            "callJobFinished()",
                            " jobid=",
                            Integer.valueOf(i),
                            " needsReschedule=",
                            Boolean.FALSE,
                            " ",
                            syncLogger.jobParametersToString(jobParameters),
                            " why=",
                            str);
                    if (jobParameters != null) {
                        syncJobService.jobFinished(jobParameters, false);
                        sparseArray.remove(i);
                    } else {
                        Slog.e("SyncManager", "Job params not found for " + String.valueOf(i));
                    }
                } finally {
                }
            }
        }
    }

    public static String jobParametersToString(JobParameters jobParameters) {
        if (jobParameters == null) {
            return "job:null";
        }
        return "job:#"
                + jobParameters.getJobId()
                + ":sr=["
                + jobParameters.getInternalStopReasonCode()
                + "/"
                + jobParameters.getDebugStopReason()
                + "]:"
                + SyncOperation.maybeCreateFromJobExtras(jobParameters.getExtras());
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        SyncManager.SyncHandler syncHandler;
        synchronized (SyncJobService.class) {
            sInstance = this;
        }
        SyncLogger syncLogger = sLogger;
        syncLogger.purgeOldLogs();
        SyncOperation maybeCreateFromJobExtras =
                SyncOperation.maybeCreateFromJobExtras(jobParameters.getExtras());
        if (maybeCreateFromJobExtras == null) {
            Slog.wtf("SyncManager", "Got invalid job " + jobParameters.getJobId());
            return false;
        }
        boolean readyToSync = SyncManager.readyToSync(maybeCreateFromJobExtras.target.userId);
        syncLogger.log(
                "onStartJob() jobid=",
                Integer.valueOf(jobParameters.getJobId()),
                " op=",
                maybeCreateFromJobExtras,
                " readyToSync",
                Boolean.valueOf(readyToSync));
        if (!readyToSync) {
            jobFinished(jobParameters, !maybeCreateFromJobExtras.isPeriodic);
            return true;
        }
        boolean isLoggable = Log.isLoggable("SyncManager", 2);
        synchronized (sLock) {
            int jobId = jobParameters.getJobId();
            sJobParamsMap.put(jobId, jobParameters);
            sStartedSyncs.delete(jobId);
            sJobStartUptimes.put(jobId, SystemClock.uptimeMillis());
        }
        Message obtain = Message.obtain();
        obtain.what = 10;
        if (isLoggable) {
            Slog.v("SyncManager", "Got start job message " + maybeCreateFromJobExtras.target);
        }
        obtain.obj = maybeCreateFromJobExtras;
        SyncManager syncManager = SyncManager.getInstance();
        if (syncManager != null && (syncHandler = syncManager.mSyncHandler) != null) {
            syncHandler.sendMessage(obtain);
        }
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        SyncManager.SyncHandler syncHandler;
        if (Log.isLoggable("SyncManager", 2)) {
            Slog.v(
                    "SyncManager",
                    "onStopJob called "
                            + jobParameters.getJobId()
                            + ", reason: "
                            + jobParameters.getInternalStopReasonCode());
        }
        SyncOperation maybeCreateFromJobExtras =
                SyncOperation.maybeCreateFromJobExtras(jobParameters.getExtras());
        if (maybeCreateFromJobExtras == null) {
            Slog.wtf("SyncManager", "Got invalid job " + jobParameters.getJobId());
            return false;
        }
        boolean readyToSync = SyncManager.readyToSync(maybeCreateFromJobExtras.target.userId);
        SyncLogger syncLogger = sLogger;
        syncLogger.log(
                "onStopJob() ",
                syncLogger.jobParametersToString(jobParameters),
                " readyToSync=",
                Boolean.valueOf(readyToSync));
        synchronized (sLock) {
            try {
                int jobId = jobParameters.getJobId();
                sJobParamsMap.remove(jobId);
                SparseLongArray sparseLongArray = sJobStartUptimes;
                long j = sparseLongArray.get(jobId);
                long uptimeMillis = SystemClock.uptimeMillis();
                if (uptimeMillis - j > 60000 && readyToSync && !sStartedSyncs.get(jobId)) {
                    String str =
                            "Job "
                                    + jobId
                                    + " didn't start:  startUptime="
                                    + j
                                    + " nowUptime="
                                    + uptimeMillis
                                    + " params="
                                    + jobParametersToString(jobParameters);
                    syncLogger.log(str);
                    Slog.wtf("SyncManager", str);
                }
                sStartedSyncs.delete(jobId);
                sparseLongArray.delete(jobId);
            } catch (Throwable th) {
                throw th;
            }
        }
        Message obtain = Message.obtain();
        obtain.what = 11;
        obtain.obj = maybeCreateFromJobExtras;
        obtain.arg1 = jobParameters.getInternalStopReasonCode() != 0 ? 1 : 0;
        obtain.arg2 = jobParameters.getInternalStopReasonCode() != 3 ? 0 : 1;
        SyncManager syncManager = SyncManager.getInstance();
        if (syncManager != null && (syncHandler = syncManager.mSyncHandler) != null) {
            syncHandler.sendMessage(obtain);
        }
        return false;
    }
}
