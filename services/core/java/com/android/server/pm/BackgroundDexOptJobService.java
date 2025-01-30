package com.android.server.pm;

import android.app.job.JobParameters;
import android.app.job.JobService;

/* loaded from: classes3.dex */
public final class BackgroundDexOptJobService extends JobService {
    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        return BackgroundDexOptService.getService().onStartJob(this, jobParameters);
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        return BackgroundDexOptService.getService().onStopJob(this, jobParameters);
    }
}
