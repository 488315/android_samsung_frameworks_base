package com.android.server.blob;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Slog;

import com.android.server.LocalServices;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class BlobStoreIdleJobService extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.app.job.JobService
    public final boolean onStartJob(final JobParameters jobParameters) {
        AsyncTask.execute(
                new Runnable() { // from class:
                                 // com.android.server.blob.BlobStoreIdleJobService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BlobStoreIdleJobService blobStoreIdleJobService =
                                BlobStoreIdleJobService.this;
                        JobParameters jobParameters2 = jobParameters;
                        int i = BlobStoreIdleJobService.$r8$clinit;
                        blobStoreIdleJobService.getClass();
                        BlobStoreManagerService blobStoreManagerService =
                                ((BlobStoreManagerService.LocalService)
                                                LocalServices.getService(
                                                        BlobStoreManagerService.LocalService.class))
                                        .this$0;
                        synchronized (blobStoreManagerService.mBlobsLock) {
                            blobStoreManagerService.handleIdleMaintenanceLocked();
                        }
                        blobStoreIdleJobService.jobFinished(jobParameters2, false);
                    }
                });
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        Slog.d(
                "BlobStore",
                "Idle maintenance job is stopped; id="
                        + jobParameters.getJobId()
                        + ", reason="
                        + JobParameters.getInternalReasonCodeDescription(
                                jobParameters.getInternalStopReasonCode()));
        return false;
    }
}
