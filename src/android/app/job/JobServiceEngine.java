package android.app.job;

import android.app.Notification;
import android.app.Service;
import android.app.job.IJobService;
import android.compat.Compatibility;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.os.SomeArgs;
import com.samsung.android.knox.zt.internal.KnoxZtInternalConst;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public abstract class JobServiceEngine {
    private static final int MSG_EXECUTE_JOB = 0;
    private static final int MSG_GET_TRANSFERRED_DOWNLOAD_BYTES = 3;
    private static final int MSG_GET_TRANSFERRED_UPLOAD_BYTES = 4;
    private static final int MSG_INFORM_OF_NETWORK_CHANGE = 8;
    private static final int MSG_JOB_FINISHED = 2;
    private static final int MSG_SET_NOTIFICATION = 7;
    private static final int MSG_STOP_JOB = 1;
    private static final int MSG_UPDATE_ESTIMATED_NETWORK_BYTES = 6;
    private static final int MSG_UPDATE_TRANSFERRED_NETWORK_BYTES = 5;
    private static final String TAG = "JobServiceEngine";
    private final IJobService mBinder = new JobInterface(this);
    JobHandler mHandler;

    public abstract boolean onStartJob(JobParameters jobParameters);

    public abstract boolean onStopJob(JobParameters jobParameters);

    static final class JobInterface extends IJobService.Stub {
        final WeakReference<JobServiceEngine> mService;

        JobInterface(JobServiceEngine jobServiceEngine) {
            this.mService = new WeakReference<>(jobServiceEngine);
        }

        @Override // android.app.job.IJobService
        public void getTransferredDownloadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) throws RemoteException {
            JobServiceEngine jobServiceEngine = this.mService.get();
            if (jobServiceEngine != null) {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = jobParameters;
                someArgsObtain.arg2 = jobWorkItem;
                jobServiceEngine.mHandler.obtainMessage(3, someArgsObtain).sendToTarget();
            }
        }

        @Override // android.app.job.IJobService
        public void getTransferredUploadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) throws RemoteException {
            JobServiceEngine jobServiceEngine = this.mService.get();
            if (jobServiceEngine != null) {
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.arg1 = jobParameters;
                someArgsObtain.arg2 = jobWorkItem;
                jobServiceEngine.mHandler.obtainMessage(4, someArgsObtain).sendToTarget();
            }
        }

        @Override // android.app.job.IJobService
        public void startJob(JobParameters jobParameters) throws RemoteException {
            JobServiceEngine jobServiceEngine = this.mService.get();
            if (jobServiceEngine != null) {
                Message.obtain(jobServiceEngine.mHandler, 0, jobParameters).sendToTarget();
            }
        }

        @Override // android.app.job.IJobService
        public void onNetworkChanged(JobParameters jobParameters) throws RemoteException {
            JobServiceEngine jobServiceEngine = this.mService.get();
            if (jobServiceEngine != null) {
                jobServiceEngine.mHandler.removeMessages(8);
                jobServiceEngine.mHandler.obtainMessage(8, jobParameters).sendToTarget();
            }
        }

        @Override // android.app.job.IJobService
        public void stopJob(JobParameters jobParameters) throws RemoteException {
            JobServiceEngine jobServiceEngine = this.mService.get();
            if (jobServiceEngine != null) {
                Message.obtain(jobServiceEngine.mHandler, 1, jobParameters).sendToTarget();
            }
        }
    }

    class JobHandler extends Handler {
        JobHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    JobParameters jobParameters = (JobParameters) message.obj;
                    try {
                        jobParameters.enableCleaner();
                        boolean zOnStartJob = JobServiceEngine.this.onStartJob(jobParameters);
                        if (!zOnStartJob) {
                            jobParameters.disableCleaner();
                        }
                        ackStartMessage(jobParameters, zOnStartJob);
                        return;
                    } catch (Exception e) {
                        Log.e(JobServiceEngine.TAG, "Error while executing job: " + jobParameters.getJobId());
                        throw new RuntimeException(e);
                    }
                case 1:
                    JobParameters jobParameters2 = (JobParameters) message.obj;
                    try {
                        ackStopMessage(jobParameters2, JobServiceEngine.this.onStopJob(jobParameters2));
                        return;
                    } catch (Exception e2) {
                        Log.e(JobServiceEngine.TAG, "Application unable to handle onStopJob.", e2);
                        throw new RuntimeException(e2);
                    }
                case 2:
                    JobParameters jobParameters3 = (JobParameters) message.obj;
                    boolean z = message.arg2 == 1;
                    IJobCallback callback = jobParameters3.getCallback();
                    if (callback != null) {
                        try {
                            jobParameters3.disableCleaner();
                            callback.jobFinished(jobParameters3.getJobId(), z);
                            return;
                        } catch (RemoteException unused) {
                            Log.e(JobServiceEngine.TAG, "Error reporting job finish to system: binder has goneaway.");
                            return;
                        }
                    }
                    Log.e(JobServiceEngine.TAG, "finishJob() called for a nonexistent job id.");
                    return;
                case 3:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    JobParameters jobParameters4 = (JobParameters) someArgs.arg1;
                    JobWorkItem jobWorkItem = (JobWorkItem) someArgs.arg2;
                    try {
                        ackGetTransferredDownloadBytesMessage(jobParameters4, jobWorkItem, JobServiceEngine.this.getTransferredDownloadBytes(jobParameters4, jobWorkItem));
                        someArgs.recycle();
                        return;
                    } catch (Exception e3) {
                        Log.e(JobServiceEngine.TAG, "Application unable to handle getTransferredDownloadBytes.", e3);
                        throw new RuntimeException(e3);
                    }
                case 4:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    JobParameters jobParameters5 = (JobParameters) someArgs2.arg1;
                    JobWorkItem jobWorkItem2 = (JobWorkItem) someArgs2.arg2;
                    try {
                        ackGetTransferredUploadBytesMessage(jobParameters5, jobWorkItem2, JobServiceEngine.this.getTransferredUploadBytes(jobParameters5, jobWorkItem2));
                        someArgs2.recycle();
                        return;
                    } catch (Exception e4) {
                        Log.e(JobServiceEngine.TAG, "Application unable to handle getTransferredUploadBytes.", e4);
                        throw new RuntimeException(e4);
                    }
                case 5:
                    SomeArgs someArgs3 = (SomeArgs) message.obj;
                    JobParameters jobParameters6 = (JobParameters) someArgs3.arg1;
                    IJobCallback callback2 = jobParameters6.getCallback();
                    if (callback2 != null) {
                        try {
                            callback2.updateTransferredNetworkBytes(jobParameters6.getJobId(), (JobWorkItem) someArgs3.arg2, someArgs3.argl1, someArgs3.argl2);
                        } catch (RemoteException unused2) {
                            Log.e(JobServiceEngine.TAG, "Error updating data transfer progress to system: binder has gone away.");
                        }
                    } else {
                        Log.e(JobServiceEngine.TAG, "updateDataTransferProgress() called for a nonexistent job id.");
                    }
                    someArgs3.recycle();
                    return;
                case 6:
                    SomeArgs someArgs4 = (SomeArgs) message.obj;
                    JobParameters jobParameters7 = (JobParameters) someArgs4.arg1;
                    IJobCallback callback3 = jobParameters7.getCallback();
                    if (callback3 != null) {
                        try {
                            callback3.updateEstimatedNetworkBytes(jobParameters7.getJobId(), (JobWorkItem) someArgs4.arg2, someArgs4.argl1, someArgs4.argl2);
                        } catch (RemoteException unused3) {
                            Log.e(JobServiceEngine.TAG, "Error updating estimated transfer size to system: binder has gone away.");
                        }
                    } else {
                        Log.e(JobServiceEngine.TAG, "updateEstimatedNetworkBytes() called for a nonexistent job id.");
                    }
                    someArgs4.recycle();
                    return;
                case 7:
                    SomeArgs someArgs5 = (SomeArgs) message.obj;
                    JobParameters jobParameters8 = (JobParameters) someArgs5.arg1;
                    Notification notification = (Notification) someArgs5.arg2;
                    IJobCallback callback4 = jobParameters8.getCallback();
                    if (callback4 != null) {
                        try {
                            callback4.setNotification(jobParameters8.getJobId(), someArgs5.argi1, notification, someArgs5.argi2);
                        } catch (RemoteException unused4) {
                            Log.e(JobServiceEngine.TAG, "Error providing notification: binder has gone away.");
                        }
                    } else {
                        Log.e(JobServiceEngine.TAG, "setNotification() called for a nonexistent job.");
                    }
                    someArgs5.recycle();
                    return;
                case 8:
                    JobParameters jobParameters9 = (JobParameters) message.obj;
                    try {
                        JobServiceEngine.this.onNetworkChanged(jobParameters9);
                        return;
                    } catch (Exception e5) {
                        Log.e(JobServiceEngine.TAG, "Error while executing job: " + jobParameters9.getJobId());
                        throw new RuntimeException(e5);
                    }
                default:
                    Log.e(JobServiceEngine.TAG, "Unrecognised message received.");
                    return;
            }
        }

        private void ackGetTransferredDownloadBytesMessage(JobParameters jobParameters, JobWorkItem jobWorkItem, long j) {
            IJobCallback callback = jobParameters.getCallback();
            int jobId = jobParameters.getJobId();
            int workId = jobWorkItem == null ? -1 : jobWorkItem.getWorkId();
            if (callback != null) {
                try {
                    callback.acknowledgeGetTransferredDownloadBytesMessage(jobId, workId, j);
                } catch (RemoteException unused) {
                    Log.e(JobServiceEngine.TAG, "System unreachable for returning progress.");
                }
            } else if (Log.isLoggable(JobServiceEngine.TAG, 3)) {
                Log.d(JobServiceEngine.TAG, "Attempting to ack a job that has already been processed.");
            }
        }

        private void ackGetTransferredUploadBytesMessage(JobParameters jobParameters, JobWorkItem jobWorkItem, long j) {
            IJobCallback callback = jobParameters.getCallback();
            int jobId = jobParameters.getJobId();
            int workId = jobWorkItem == null ? -1 : jobWorkItem.getWorkId();
            if (callback != null) {
                try {
                    callback.acknowledgeGetTransferredUploadBytesMessage(jobId, workId, j);
                } catch (RemoteException unused) {
                    Log.e(JobServiceEngine.TAG, "System unreachable for returning progress.");
                }
            } else if (Log.isLoggable(JobServiceEngine.TAG, 3)) {
                Log.d(JobServiceEngine.TAG, "Attempting to ack a job that has already been processed.");
            }
        }

        private void ackStartMessage(JobParameters jobParameters, boolean z) {
            IJobCallback callback = jobParameters.getCallback();
            int jobId = jobParameters.getJobId();
            if (callback != null) {
                try {
                    callback.acknowledgeStartMessage(jobId, z);
                } catch (RemoteException unused) {
                    Log.e(JobServiceEngine.TAG, "System unreachable for starting job.");
                }
            } else if (Log.isLoggable(JobServiceEngine.TAG, 3)) {
                Log.d(JobServiceEngine.TAG, "Attempting to ack a job that has already been processed.");
            }
        }

        private void ackStopMessage(JobParameters jobParameters, boolean z) {
            IJobCallback callback = jobParameters.getCallback();
            int jobId = jobParameters.getJobId();
            if (callback != null) {
                try {
                    callback.acknowledgeStopMessage(jobId, z);
                } catch (RemoteException unused) {
                    Log.e(JobServiceEngine.TAG, "System unreachable for stopping job.");
                }
            } else if (Log.isLoggable(JobServiceEngine.TAG, 3)) {
                Log.d(JobServiceEngine.TAG, "Attempting to ack a job that has already been processed.");
            }
        }
    }

    public JobServiceEngine(Service service) {
        this.mHandler = new JobHandler(service.getMainLooper());
    }

    public final IBinder getBinder() {
        return this.mBinder.asBinder();
    }

    public void jobFinished(JobParameters jobParameters, boolean z) {
        if (jobParameters == null) {
            throw new NullPointerException(KnoxZtInternalConst.Event.LogKeys.PARAMS);
        }
        Message messageObtain = Message.obtain(this.mHandler, 2, jobParameters);
        messageObtain.arg2 = z ? 1 : 0;
        messageObtain.sendToTarget();
    }

    public void onNetworkChanged(JobParameters jobParameters) {
        Log.w(TAG, "onNetworkChanged() not implemented. Must override in a subclass.");
    }

    public long getTransferredDownloadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) {
        if (Compatibility.isChangeEnabled(JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public long getTransferredUploadBytes(JobParameters jobParameters, JobWorkItem jobWorkItem) {
        if (Compatibility.isChangeEnabled(JobScheduler.THROW_ON_INVALID_DATA_TRANSFER_IMPLEMENTATION)) {
            throw new RuntimeException("Not implemented. Must override in a subclass.");
        }
        return 0L;
    }

    public void updateTransferredNetworkBytes(JobParameters jobParameters, JobWorkItem jobWorkItem, long j, long j2) {
        if (jobParameters == null) {
            throw new NullPointerException(KnoxZtInternalConst.Event.LogKeys.PARAMS);
        }
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = jobParameters;
        someArgsObtain.arg2 = jobWorkItem;
        someArgsObtain.argl1 = j;
        someArgsObtain.argl2 = j2;
        this.mHandler.obtainMessage(5, someArgsObtain).sendToTarget();
    }

    public void updateEstimatedNetworkBytes(JobParameters jobParameters, JobWorkItem jobWorkItem, long j, long j2) {
        if (jobParameters == null) {
            throw new NullPointerException(KnoxZtInternalConst.Event.LogKeys.PARAMS);
        }
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = jobParameters;
        someArgsObtain.arg2 = jobWorkItem;
        someArgsObtain.argl1 = j;
        someArgsObtain.argl2 = j2;
        this.mHandler.obtainMessage(6, someArgsObtain).sendToTarget();
    }

    public void setNotification(JobParameters jobParameters, int i, Notification notification, int i2) {
        if (jobParameters == null) {
            throw new NullPointerException(KnoxZtInternalConst.Event.LogKeys.PARAMS);
        }
        if (notification == null) {
            throw new NullPointerException("notification");
        }
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = jobParameters;
        someArgsObtain.arg2 = notification;
        someArgsObtain.argi1 = i;
        someArgsObtain.argi2 = i2;
        this.mHandler.obtainMessage(7, someArgsObtain).sendToTarget();
    }
}
