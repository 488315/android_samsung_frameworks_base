package com.android.server.people.data;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.CancellationSignal;
import com.android.server.LocalServices;
import com.android.server.people.PeopleServiceInternal;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class DataMaintenanceService extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long JOB_RUN_INTERVAL = TimeUnit.HOURS.toMillis(24);
    public CancellationSignal mSignal;

    public static void scheduleJob(Context context, int i) {
        int i2 = i + 204561367;
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        if (jobScheduler.getPendingJob(i2) == null) {
            jobScheduler.schedule(new JobInfo.Builder(i2, new ComponentName(context, (Class<?>) DataMaintenanceService.class)).setRequiresDeviceIdle(true).setPeriodic(JOB_RUN_INTERVAL).build());
        }
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(final JobParameters jobParameters) {
        final int jobId = jobParameters.getJobId() - 204561367;
        this.mSignal = new CancellationSignal();
        new Thread(new Runnable() { // from class: com.android.server.people.data.DataMaintenanceService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DataMaintenanceService dataMaintenanceService = DataMaintenanceService.this;
                int i = jobId;
                JobParameters jobParameters2 = jobParameters;
                int i2 = DataMaintenanceService.$r8$clinit;
                dataMaintenanceService.getClass();
                ((PeopleServiceInternal) LocalServices.getService(PeopleServiceInternal.class)).pruneDataForUser(i, dataMaintenanceService.mSignal);
                dataMaintenanceService.jobFinished(jobParameters2, dataMaintenanceService.mSignal.isCanceled());
            }
        }).start();
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        CancellationSignal cancellationSignal = this.mSignal;
        if (cancellationSignal == null) {
            return false;
        }
        cancellationSignal.cancel();
        return false;
    }
}
