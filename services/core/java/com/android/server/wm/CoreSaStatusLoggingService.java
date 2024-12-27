package com.android.server.wm;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.util.Slog;

import com.samsung.android.server.util.SafetySystemService;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class CoreSaStatusLoggingService extends JobService {
    public static final long IDLE_LOGGING_PERIOD_MILLIS = TimeUnit.DAYS.toMillis(1);
    public static final HashSet sCoreSaStatusLoggers = new HashSet();

    public static void schedule() {
        try {
            JobScheduler jobScheduler =
                    (JobScheduler) SafetySystemService.getSystemService(JobScheduler.class);
            if (jobScheduler == null) {
                return;
            }
            jobScheduler.schedule(
                    new JobInfo.Builder(
                                    415377471,
                                    new ComponentName(
                                            "android", CoreSaStatusLoggingService.class.getName()))
                            .setRequiresDeviceIdle(true)
                            .setRequiresCharging(true)
                            .setPeriodic(IDLE_LOGGING_PERIOD_MILLIS)
                            .build());
        } catch (Throwable th) {
            Slog.w("CoreSaStatusLoggingService", "Failed to schedule.", th);
        }
    }
}
