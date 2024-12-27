package com.android.server.companion.association;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Slog;

import com.android.server.companion.CompanionDeviceManagerService;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class InactiveAssociationsRemovalService extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long ONE_DAY_INTERVAL = TimeUnit.DAYS.toMillis(1);

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        Slog.i("CDM_InactiveAssociationsRemovalService", "Execute the Association Removal job");
        CompanionDeviceManagerService.this.mDisassociationProcessor
                .removeIdleSelfManagedAssociations();
        jobFinished(jobParameters, false);
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        Slog.i(
                "CDM_InactiveAssociationsRemovalService",
                "Association removal job stopped; id="
                        + jobParameters.getJobId()
                        + ", reason="
                        + JobParameters.getInternalReasonCodeDescription(
                                jobParameters.getInternalStopReasonCode()));
        return false;
    }
}
