package com.samsung.android.server.pm.role;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import com.samsung.android.server.pm.SALoggingHelper;
import com.samsung.android.server.pm.role.RoleLogger;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class RoleLogger {
    public static final String TAG = "RoleLogger";
    public static final long LOGGING_PERIOD = TimeUnit.DAYS.toMillis(7);
    public static final ComponentName sRoleLoggingServiceName = new ComponentName("android", RoleLoggingService.class.getName());
    public static final String[] ROLES_TO_LOG = {"android.app.role.BROWSER", "android.app.role.CALL_SCREENING", "android.app.role.ASSISTANT", "android.app.role.HOME", "android.app.role.DIALER", "android.app.role.SMS", "android.app.role.CALL_REDIRECTION", "android.app.role.EMERGENCY", "android.app.role.NOTES", "android.app.role.SYSTEM_GALLERY", "android.app.role.SYSTEM_CONTACTS", "android.app.role.SYSTEM_DOCUMENT_MANAGER"};

    public void onSystemReady(Context context) {
        scheduleLoggingJob(context, 7259101);
    }

    public final void scheduleLoggingJob(Context context, int i) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        if (jobScheduler == null) {
            Log.i(TAG, "Failed to get JobScheduler");
        } else {
            jobScheduler.schedule(new JobInfo.Builder(i, sRoleLoggingServiceName).setRequiresCharging(true).setRequiresDeviceIdle(true).setPeriodic(LOGGING_PERIOD).build());
        }
    }

    public class RoleLoggingService extends JobService {
        @Override // android.app.job.JobService
        public boolean onStartJob(final JobParameters jobParameters) {
            Log.i(RoleLogger.TAG, "onStartJob: " + jobParameters.getJobId());
            new Thread(new Runnable() { // from class: com.samsung.android.server.pm.role.RoleLogger$RoleLoggingService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RoleLogger.RoleLoggingService.this.lambda$onStartJob$0(jobParameters);
                }
            }).start();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v0, types: [android.app.job.JobService, android.content.Context, com.samsung.android.server.pm.role.RoleLogger$RoleLoggingService] */
        /* JADX WARN: Type inference failed for: r5v1, types: [android.app.job.JobService] */
        /* JADX WARN: Type inference failed for: r5v3, types: [java.lang.String] */
        public /* synthetic */ void lambda$onStartJob$0(JobParameters jobParameters) {
            try {
                try {
                    Log.i(RoleLogger.TAG, "Check role holders");
                    new SALoggingHelper().sendSettingLog(RoleLogger.TAG, this, new RoleLogger().getEachRoleHolder(this, RoleLogger.ROLES_TO_LOG));
                } catch (Exception e) {
                    Log.i(RoleLogger.TAG, "Failed to log role holders", e);
                }
            } finally {
                this.jobFinished(jobParameters, false);
                Log.i(RoleLogger.TAG, "Finished");
            }
        }

        @Override // android.app.job.JobService
        public boolean onStopJob(JobParameters jobParameters) {
            Log.i(RoleLogger.TAG, "onStopJob: " + jobParameters.getJobId());
            return true;
        }
    }

    public final HashMap getEachRoleHolder(Context context, String[] strArr) {
        RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        HashMap hashMap = new HashMap();
        for (String str : strArr) {
            if (!roleManager.isRoleAvailable(str)) {
                hashMap.put(str, "Unavailable");
            } else {
                List roleHolders = roleManager.getRoleHolders(str);
                hashMap.put(str, roleHolders.isEmpty() ? "None" : (String) roleHolders.get(0));
            }
        }
        return hashMap;
    }
}
