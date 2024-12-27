package com.android.server.net.watchlist;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.net.NetworkWatchlistManager;

import java.util.concurrent.TimeUnit;

public class ReportWatchlistJobService extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long REPORT_WATCHLIST_RECORDS_PERIOD_MILLIS = TimeUnit.HOURS.toMillis(12);

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        if (jobParameters.getJobId() != 882313) {
            return false;
        }
        new NetworkWatchlistManager(this).reportWatchlistIfNecessary();
        jobFinished(jobParameters, false);
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
