package android.app.job;

import android.app.Notification;
import android.app.Service;
import android.compat.Compatibility;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public abstract class JobService extends Service {
    public static final int JOB_END_NOTIFICATION_POLICY_DETACH = 0;
    public static final int JOB_END_NOTIFICATION_POLICY_REMOVE = 1;
    public static final String PERMISSION_BIND = "android.permission.BIND_JOB_SERVICE";
    private static final String TAG = "JobService";
    private JobServiceEngine mEngine;

    @Retention(RetentionPolicy.SOURCE)
    public @interface JobEndNotificationPolicy {
    }

    public abstract boolean onStartJob(JobParameters jobParameters);

    public abstract boolean onStopJob(JobParameters jobParameters);

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (this.mEngine == null) {
            this.mEngine = new JobServiceEngine(this) { // from class: android.app.job.JobService.1
                @Override // android.app.job.JobServiceEngine
                public boolean onStartJob(JobParameters jobParameters) {
                    return JobService.this.onStartJob(jobParameters);
                }

                @Override // android.app.job.JobServiceEngine
                public boolean onStopJob(JobParameters jobParameters) {
                    return JobService.this.onStopJob(jobParameters);
                }

                @Override // android.app.job.JobServiceEngine
                public long getTransferredDownloadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) {
                    if (jobWorkItem == null) {
                        return JobService.this.getTransferredDownloadBytes(jobParameters);
                    }
                    return JobService.this.getTransferredDownloadBytes(jobParameters, jobWorkItem);
                }

                @Override // android.app.job.JobServiceEngine
                public long getTransferredUploadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) {
                    if (jobWorkItem == null) {
                        return JobService.this.getTransferredUploadBytes(jobParameters);
                    }
                    return JobService.this.getTransferredUploadBytes(jobParameters, jobWorkItem);
                }

                @Override // android.app.job.JobServiceEngine
                public void onNetworkChanged(JobParameters jobParameters) {
                    JobService.this.onNetworkChanged(jobParameters);
                }
            };
        }
        return this.mEngine.getBinder();
    }

    public final void jobFinished(JobParameters jobParameters, boolean z) {
        this.mEngine.jobFinished(jobParameters, z);
    }

    public void onNetworkChanged(JobParameters jobParameters) {
        Log.w(TAG, "onNetworkChanged() not implemented in " + getClass().getName() + ". Must override in a subclass.");
    }

    public final void updateEstimatedNetworkBytes(JobParameters jobParameters, long j, long j2) {
        this.mEngine.updateEstimatedNetworkBytes(jobParameters, null, j, j2);
    }

    public final void updateEstimatedNetworkBytes(JobParameters jobParameters, JobWorkItem jobWorkItem, long j, long j2) {
        this.mEngine.updateEstimatedNetworkBytes(jobParameters, jobWorkItem, j, j2);
    }

    public final void updateTransferredNetworkBytes(JobParameters jobParameters, long j, long j2) {
        this.mEngine.updateTransferredNetworkBytes(jobParameters, null, j, j2);
    }

    public final void updateTransferredNetworkBytes(JobParameters jobParameters, JobWorkItem jobWorkItem, long j, long j2) {
        this.mEngine.updateTransferredNetworkBytes(jobParameters, jobWorkItem, j, j2);
    }

    public long getTransferredDownloadBytes(JobParameters jobParameters) {
        if (Compatibility.isChangeEnabled(JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public long getTransferredUploadBytes(JobParameters jobParameters) {
        if (Compatibility.isChangeEnabled(JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public long getTransferredDownloadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) {
        if (jobWorkItem == null) {
            return getTransferredDownloadBytes(jobParameters);
        }
        if (Compatibility.isChangeEnabled(JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public long getTransferredUploadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) {
        if (jobWorkItem == null) {
            return getTransferredUploadBytes(jobParameters);
        }
        if (Compatibility.isChangeEnabled(JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public final void setNotification(JobParameters jobParameters, int i, Notification notification, int i2) {
        this.mEngine.setNotification(jobParameters, i, notification, i2);
    }
}
