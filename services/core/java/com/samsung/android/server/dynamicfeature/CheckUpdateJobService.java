package com.samsung.android.server.dynamicfeature;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Slog;

import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class CheckUpdateJobService extends JobService {
    public static boolean isStarted;
    public static Context sContext;

    public static void cancelJob() {
        if (isStarted) {
            try {
                ((JobScheduler) sContext.getSystemService("jobscheduler")).cancel(10776);
                isStarted = false;
                Slog.d("dynamicfeature_CheckUpdateJobService", "Cancel job scheduler ");
            } catch (Exception e) {
                NandswapManager$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("JobScheduler : "),
                        "dynamicfeature_CheckUpdateJobService");
            }
        }
    }

    public static void scheduleDynamicFeatureScheduler(
            Context context, DynamicFeatureService dynamicFeatureService) {
        synchronized ("dynamicfeature_CheckUpdateJobService") {
            try {
                Slog.d(
                        "dynamicfeature_CheckUpdateJobService",
                        "start job scheduler request  =======");
                if (isStarted) {
                    return;
                }
                if (dynamicFeatureService == null) {
                    Slog.e(
                            "dynamicfeature_CheckUpdateJobService",
                            "DynamicFeatureService is not started");
                    return;
                }
                Slog.d(
                        "dynamicfeature_CheckUpdateJobService",
                        "DynamicFeatureService is working : " + dynamicFeatureService);
                ComponentName componentName =
                        new ComponentName(
                                context,
                                "com.samsung.android.server.dynamicfeature.CheckUpdateJobService");
                Slog.d(
                        "dynamicfeature_CheckUpdateJobService",
                        "start job scheduler begin!  ==========  ");
                try {
                    JobScheduler jobScheduler =
                            (JobScheduler) context.getSystemService("jobscheduler");
                    sContext = context;
                    long j = 60000;
                    int schedule =
                            jobScheduler.schedule(
                                    new JobInfo.Builder(10776, componentName)
                                            .setPeriodic(
                                                    InfoBoard.basicInfo.jobIntervalMill * j,
                                                    r11.flexMill * 60000)
                                            .setRequiredNetworkType(1)
                                            .setOverrideDeadline(0L)
                                            .build());
                    if (schedule == 1) {
                        isStarted = true;
                    }
                    Slog.d(
                            "dynamicfeature_CheckUpdateJobService",
                            "next job is : "
                                    + (InfoBoard.basicInfo.jobIntervalMill * j)
                                    + " flex : "
                                    + (InfoBoard.basicInfo.flexMill * 60000));
                    StringBuilder sb = new StringBuilder("start job scheduler : ");
                    sb.append(schedule);
                    Slog.d("dynamicfeature_CheckUpdateJobService", sb.toString());
                } catch (Exception e) {
                    NandswapManager$$ExternalSyntheticOutline0.m(
                            e,
                            new StringBuilder("JobScheduler : "),
                            "dynamicfeature_CheckUpdateJobService");
                }
            } finally {
            }
        }
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        Slog.d(
                "dynamicfeature_CheckUpdateJobService",
                "onStartJob for update ==================== ");
        try {
            DynamicFeatureService dynamicFeatureService =
                    (DynamicFeatureService) LocalServices.getService(DynamicFeatureService.class);
            if (dynamicFeatureService == null) {
                Slog.e(
                        "dynamicfeature_CheckUpdateJobService",
                        "DynamicFeatureService is not started");
                return false;
            }
            dynamicFeatureService.updateFeatureViaServer();
            jobFinished(jobParameters, false);
            Slog.d(
                    "dynamicfeature_CheckUpdateJobService",
                    "onStartJob finished ==================== ");
            return true;
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("onStartJob : "), "dynamicfeature_CheckUpdateJobService");
            return true;
        }
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        Slog.d(
                "dynamicfeature_CheckUpdateJobService",
                "onStopJob for update =====================");
        return false;
    }
}
