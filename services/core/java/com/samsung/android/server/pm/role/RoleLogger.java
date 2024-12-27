package com.samsung.android.server.pm.role;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import com.android.server.input.KeyboardMetricsCollector;
import com.samsung.android.server.pm.SALoggingHelper;
import com.samsung.android.server.pm.role.RoleLogger;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RoleLogger {
    public static final long LOGGING_PERIOD = TimeUnit.DAYS.toMillis(7);
    public static final ComponentName sRoleLoggingServiceName = new ComponentName("android", RoleLoggingService.class.getName());
    public static final String[] ROLES_TO_LOG = {"android.app.role.BROWSER", "android.app.role.CALL_SCREENING", "android.app.role.ASSISTANT", "android.app.role.HOME", "android.app.role.DIALER", "android.app.role.SMS", "android.app.role.CALL_REDIRECTION", "android.app.role.EMERGENCY", "android.app.role.NOTES", "android.app.role.SYSTEM_GALLERY", "android.app.role.SYSTEM_CONTACTS", "android.app.role.SYSTEM_DOCUMENT_MANAGER"};

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class RoleLoggingService extends JobService {
        public static final /* synthetic */ int $r8$clinit = 0;

        @Override // android.app.job.JobService
        public final boolean onStartJob(final JobParameters jobParameters) {
            ComponentName componentName = RoleLogger.sRoleLoggingServiceName;
            Log.i("RoleLogger", "onStartJob: " + jobParameters.getJobId());
            new Thread(new Runnable() { // from class: com.samsung.android.server.pm.role.RoleLogger$RoleLoggingService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RoleLogger.RoleLoggingService roleLoggingService = RoleLogger.RoleLoggingService.this;
                    JobParameters jobParameters2 = jobParameters;
                    int i = RoleLogger.RoleLoggingService.$r8$clinit;
                    roleLoggingService.getClass();
                    try {
                        try {
                            ComponentName componentName2 = RoleLogger.sRoleLoggingServiceName;
                            Log.i("RoleLogger", "Check role holders");
                            SALoggingHelper.sendSettingLog(roleLoggingService, RoleLogger.m1232$$Nest$mgetEachRoleHolder(new RoleLogger(), roleLoggingService, RoleLogger.ROLES_TO_LOG));
                        } catch (Exception e) {
                            ComponentName componentName3 = RoleLogger.sRoleLoggingServiceName;
                            Log.i("RoleLogger", "Failed to log role holders", e);
                        }
                        roleLoggingService.jobFinished(jobParameters2, false);
                        Log.i("RoleLogger", "Finished");
                    } catch (Throwable th) {
                        roleLoggingService.jobFinished(jobParameters2, false);
                        ComponentName componentName4 = RoleLogger.sRoleLoggingServiceName;
                        Log.i("RoleLogger", "Finished");
                        throw th;
                    }
                }
            }).start();
            return true;
        }

        @Override // android.app.job.JobService
        public final boolean onStopJob(JobParameters jobParameters) {
            ComponentName componentName = RoleLogger.sRoleLoggingServiceName;
            Log.i("RoleLogger", "onStopJob: " + jobParameters.getJobId());
            return true;
        }
    }

    /* renamed from: -$$Nest$mgetEachRoleHolder, reason: not valid java name */
    public static HashMap m1232$$Nest$mgetEachRoleHolder(RoleLogger roleLogger, Context context, String[] strArr) {
        RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        HashMap hashMap = new HashMap();
        for (String str : strArr) {
            if (roleManager.isRoleAvailable(str)) {
                List roleHolders = roleManager.getRoleHolders(str);
                hashMap.put(str, roleHolders.isEmpty() ? KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : (String) roleHolders.get(0));
            } else {
                hashMap.put(str, "Unavailable");
            }
        }
        return hashMap;
    }
}
