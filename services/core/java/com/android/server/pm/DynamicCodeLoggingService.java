package com.android.server.pm;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class DynamicCodeLoggingService extends JobService {
    public static final String TAG = DynamicCodeLoggingService.class.getName();
    public static final long IDLE_LOGGING_PERIOD_MILLIS = TimeUnit.DAYS.toMillis(1);
    public static final long AUDIT_WATCHING_PERIOD_MILLIS = TimeUnit.HOURS.toMillis(2);
    public static final Pattern EXECUTE_NATIVE_AUDIT_PATTERN =
            Pattern.compile(
                    ".*\\bavc: +granted +\\{ execute(?:_no_trans|) \\} .*\\bpath=(?:\"([^\""
                        + " ]*)\"|([0-9A-F]+))"
                        + " .*\\bscontext=u:r:untrusted_app(?:_25|_27)?:.*\\btcontext=u:object_r:app_data_file:.*\\btclass=file\\b.*");
    public volatile boolean mIdleLoggingStopRequested = false;
    public volatile boolean mAuditWatchingStopRequested = false;

    public final class IdleLoggingThread extends Thread {
        public final /* synthetic */ int $r8$classId;
        public final JobParameters mParams;
        public final /* synthetic */ DynamicCodeLoggingService this$0;

        public IdleLoggingThread(
                DynamicCodeLoggingService dynamicCodeLoggingService,
                JobParameters jobParameters,
                int i) {
            super("DynamicCodeLoggingService_IdleLoggingJob");
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = dynamicCodeLoggingService;
                    super("DynamicCodeLoggingService_AuditWatchingJob");
                    this.mParams = jobParameters;
                    break;
                default:
                    this.this$0 = dynamicCodeLoggingService;
                    this.mParams = jobParameters;
                    break;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:189:0x0284, code lost:

           r0 = e;
        */
        /* JADX WARN: Code restructure failed: missing block: B:190:0x0285, code lost:

           r21 = r8;
           r5 = "DynamicCodeLogger";
           r8 = r11;
           r1 = r12;
           r6 = r13;
           r2 = r14;
        */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 892
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.pm.DynamicCodeLoggingService.IdleLoggingThread.run():void");
        }
    }

    public static void schedule(Context context) {
        ComponentName componentName =
                new ComponentName("android", DynamicCodeLoggingService.class.getName());
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        jobScheduler.schedule(
                new JobInfo.Builder(2030028, componentName)
                        .setRequiresDeviceIdle(true)
                        .setRequiresCharging(true)
                        .setPeriodic(IDLE_LOGGING_PERIOD_MILLIS)
                        .build());
        jobScheduler.schedule(
                new JobInfo.Builder(203142925, componentName)
                        .setRequiresDeviceIdle(true)
                        .setRequiresBatteryNotLow(true)
                        .setPeriodic(AUDIT_WATCHING_PERIOD_MILLIS)
                        .build());
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        int jobId = jobParameters.getJobId();
        if (jobId == 2030028) {
            this.mIdleLoggingStopRequested = false;
            new IdleLoggingThread(this, jobParameters, 0).start();
            return true;
        }
        if (jobId != 203142925) {
            return false;
        }
        this.mAuditWatchingStopRequested = false;
        new IdleLoggingThread(this, jobParameters, 1).start();
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        int jobId = jobParameters.getJobId();
        if (jobId == 2030028) {
            this.mIdleLoggingStopRequested = true;
            return true;
        }
        if (jobId != 203142925) {
            return false;
        }
        this.mAuditWatchingStopRequested = true;
        return true;
    }
}
