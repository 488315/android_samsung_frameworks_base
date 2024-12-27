package com.android.server.display;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.hardware.display.DisplayManagerInternal;
import android.util.Slog;

import com.android.server.LocalServices;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class BrightnessIdleJob extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;

    public static void scheduleJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        JobInfo pendingJob = jobScheduler.getPendingJob(3923512);
        JobInfo build =
                new JobInfo.Builder(
                                3923512,
                                new ComponentName(context, (Class<?>) BrightnessIdleJob.class))
                        .setRequiresDeviceIdle(true)
                        .setRequiresCharging(true)
                        .setPeriodic(TimeUnit.HOURS.toMillis(24L))
                        .build();
        if (pendingJob != null && !pendingJob.equals(build)) {
            jobScheduler.cancel(3923512);
            pendingJob = null;
        }
        if (pendingJob == null) {
            jobScheduler.schedule(build);
        }
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        if (BrightnessTracker.DEBUG) {
            Slog.d("BrightnessTracker", "Scheduled write of brightness events");
        }
        ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class))
                .persistBrightnessTrackerState();
        return false;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
