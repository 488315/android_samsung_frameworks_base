package com.android.server;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.pm.InstantAppRegistry;
import com.android.server.pm.PackageManagerService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class PruneInstantAppsJobService extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long PRUNE_INSTANT_APPS_PERIOD_MILLIS = TimeUnit.DAYS.toMillis(1);

    public static void schedule(Context context) {
        ((JobScheduler) context.getSystemService(JobScheduler.class))
                .schedule(
                        new JobInfo.Builder(
                                        765123,
                                        new ComponentName(
                                                context.getPackageName(),
                                                PruneInstantAppsJobService.class.getName()))
                                .setRequiresDeviceIdle(true)
                                .setPeriodic(PRUNE_INSTANT_APPS_PERIOD_MILLIS)
                                .build());
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(final JobParameters jobParameters) {
        AsyncTask.execute(
                new Runnable() { // from class:
                                 // com.android.server.PruneInstantAppsJobService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PruneInstantAppsJobService pruneInstantAppsJobService =
                                PruneInstantAppsJobService.this;
                        JobParameters jobParameters2 = jobParameters;
                        int i = PruneInstantAppsJobService.$r8$clinit;
                        pruneInstantAppsJobService.getClass();
                        PackageManagerService.PackageManagerInternalImpl
                                packageManagerInternalImpl =
                                        (PackageManagerService.PackageManagerInternalImpl)
                                                ((PackageManagerInternal)
                                                        LocalServices.getService(
                                                                PackageManagerInternal.class));
                        InstantAppRegistry instantAppRegistry =
                                packageManagerInternalImpl.this$0.mInstantAppRegistry;
                        try {
                            instantAppRegistry.pruneInstantApps(
                                    packageManagerInternalImpl.mService.snapshotComputer(),
                                    Long.MAX_VALUE,
                                    Settings.Global.getLong(
                                            instantAppRegistry.mContext.getContentResolver(),
                                            "installed_instant_app_max_cache_period",
                                            15552000000L),
                                    Settings.Global.getLong(
                                            instantAppRegistry.mContext.getContentResolver(),
                                            "uninstalled_instant_app_max_cache_period",
                                            15552000000L));
                        } catch (IOException e) {
                            Slog.e(
                                    "InstantAppRegistry",
                                    "Error pruning installed and uninstalled instant apps",
                                    e);
                        }
                        pruneInstantAppsJobService.jobFinished(jobParameters2, false);
                    }
                });
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
