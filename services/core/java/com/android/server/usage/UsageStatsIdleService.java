package com.android.server.usage;

import android.app.admin.DevicePolicyManagerInternal;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.app.usage.UsageStatsManagerInternal;
import android.os.AsyncTask;
import android.os.UserHandle;

import com.android.server.LocalServices;

import java.util.HashMap;

public class UsageStatsIdleService extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.app.job.JobService
    public final boolean onStartJob(final JobParameters jobParameters) {
        final int i = jobParameters.getExtras().getInt("user_id", -1);
        if (i == -1) {
            return false;
        }
        AsyncTask.execute(
                new Runnable() { // from class:
                                 // com.android.server.usage.UsageStatsIdleService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UsageStatsIdleService usageStatsIdleService = UsageStatsIdleService.this;
                        JobParameters jobParameters2 = jobParameters;
                        int i2 = i;
                        int i3 = UsageStatsIdleService.$r8$clinit;
                        usageStatsIdleService.getClass();
                        UsageStatsManagerInternal usageStatsManagerInternal =
                                (UsageStatsManagerInternal)
                                        LocalServices.getService(UsageStatsManagerInternal.class);
                        boolean z = false;
                        if (!"usagestats_mapping".equals(jobParameters2.getJobNamespace())) {
                            UsageStatsService usageStatsService =
                                    ((UsageStatsService.LocalService) usageStatsManagerInternal)
                                            .this$0;
                            synchronized (usageStatsService.mLock) {
                                try {
                                    if (usageStatsService.mUserUnlockedStates.contains(
                                            Integer.valueOf(i2))) {
                                        UserUsageStatsService userUsageStatsService =
                                                (UserUsageStatsService)
                                                        usageStatsService.mUserState.get(i2);
                                        if (userUsageStatsService != null) {
                                            z =
                                                    userUsageStatsService
                                                            .pruneUninstalledPackagesData();
                                        }
                                    }
                                } finally {
                                }
                            }
                            usageStatsIdleService.jobFinished(jobParameters2, !z);
                            return;
                        }
                        UsageStatsService usageStatsService2 =
                                ((UsageStatsService.LocalService) usageStatsManagerInternal).this$0;
                        usageStatsService2.getClass();
                        UserHandle of = UserHandle.of(i2);
                        DevicePolicyManagerInternal dpmInternal =
                                usageStatsService2.getDpmInternal();
                        if (dpmInternal == null
                                || dpmInternal.getProfileOwnerOrDeviceOwnerSupervisionComponent(of)
                                        == null) {
                            HashMap installedPackages = usageStatsService2.getInstalledPackages(i2);
                            synchronized (usageStatsService2.mLock) {
                                try {
                                    if (usageStatsService2.mUserUnlockedStates.contains(
                                            Integer.valueOf(i2))) {
                                        UserUsageStatsService userUsageStatsService2 =
                                                (UserUsageStatsService)
                                                        usageStatsService2.mUserState.get(i2);
                                        if (userUsageStatsService2 != null) {
                                            z =
                                                    userUsageStatsService2
                                                            .updatePackageMappingsLocked(
                                                                    installedPackages);
                                        }
                                    }
                                } finally {
                                }
                            }
                        } else {
                            z = true;
                        }
                        usageStatsIdleService.jobFinished(jobParameters2, !z);
                    }
                });
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
