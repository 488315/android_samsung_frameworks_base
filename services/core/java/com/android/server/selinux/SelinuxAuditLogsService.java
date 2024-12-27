package com.android.server.selinux;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.provider.DeviceConfig;
import android.util.EventLog;
import android.util.Slog;
import com.android.internal.os.Clock;
import com.android.sdksandbox.flags.Flags;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class SelinuxAuditLogsService extends JobService {
    public static final SelinuxAuditLogsJob LOGS_COLLECTOR_JOB;
    public static final QuotaLimiter QUOTA_LIMITER;
    public static final int AUDITD_TAG_CODE = EventLog.getTagCode("auditd");
    public static final ComponentName SELINUX_AUDIT_JOB_COMPONENT = new ComponentName("android", SelinuxAuditLogsService.class.getName());
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LogsCollectorJobScheduler implements DeviceConfig.OnPropertiesChangedListener {
        public final JobScheduler mJobScheduler;

        public LogsCollectorJobScheduler(JobScheduler jobScheduler) {
            this.mJobScheduler = jobScheduler;
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            Set keyset = properties.getKeyset();
            if (keyset.contains("selinux_audit_cap")) {
                SelinuxAuditLogsService.QUOTA_LIMITER.mMaxPermits = properties.getInt("selinux_audit_cap", 50000);
            }
            if (!keyset.contains("selinux_enable_audit_job")) {
                if (keyset.contains("selinux_audit_job_frequency_hours")) {
                    schedule();
                }
            } else if (properties.getBoolean("selinux_enable_audit_job", false)) {
                schedule();
            } else {
                this.mJobScheduler.cancel(25327386);
            }
        }

        public final void schedule() {
            if (this.mJobScheduler.schedule(new JobInfo.Builder(25327386, SelinuxAuditLogsService.SELINUX_AUDIT_JOB_COMPONENT).setPeriodic(TimeUnit.HOURS.toMillis(DeviceConfig.getInt("adservices", "selinux_audit_job_frequency_hours", 24))).setRequiresDeviceIdle(true).setRequiresBatteryNotLow(true).build()) == 0) {
                Slog.e("SelinuxAuditLogs", "SelinuxAuditLogsService could not be scheduled.");
            } else {
                Slog.d("SelinuxAuditLogs", "SelinuxAuditLogsService scheduled successfully.");
            }
        }
    }

    static {
        Duration ofMillis = Duration.ofMillis(10L);
        QuotaLimiter quotaLimiter = new QuotaLimiter(Clock.SYSTEM_CLOCK, Duration.ofDays(1L), DeviceConfig.getInt("adservices", "selinux_audit_cap", 50000));
        QUOTA_LIMITER = quotaLimiter;
        LOGS_COLLECTOR_JOB = new SelinuxAuditLogsJob(new SelinuxAuditLogsCollector(new RateLimiter(ofMillis), quotaLimiter));
    }

    public static void schedule(Context context) {
        if (!Flags.selinuxSdkSandboxAudit()) {
            Slog.d("SelinuxAuditLogs", "SelinuxAuditLogsService not enabled");
        } else {
            if (AUDITD_TAG_CODE == -1) {
                Slog.e("SelinuxAuditLogs", "auditd is not a registered tag on this system");
                return;
            }
            LogsCollectorJobScheduler logsCollectorJobScheduler = new LogsCollectorJobScheduler(((JobScheduler) context.getSystemService(JobScheduler.class)).forNamespace("SelinuxAuditLogsNamespace"));
            logsCollectorJobScheduler.schedule();
            DeviceConfig.addOnPropertiesChangedListener("adservices", context.getMainExecutor(), logsCollectorJobScheduler);
        }
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(final JobParameters jobParameters) {
        if (jobParameters.getJobId() != 25327386) {
            Slog.e("SelinuxAuditLogs", "The job id does not match the expected selinux job id.");
            return false;
        }
        if (Flags.selinuxSdkSandboxAudit()) {
            EXECUTOR_SERVICE.execute(new Runnable() { // from class: com.android.server.selinux.SelinuxAuditLogsService$$ExternalSyntheticLambda0
                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Code restructure failed: missing block: B:155:0x0361, code lost:
                
                    r14 = r4;
                    r18 = r5;
                    r29 = r6;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:156:0x0366, code lost:
                
                    if (r15 == false) goto L140;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:157:0x0368, code lost:
                
                    com.android.server.utils.Slogf.d("SelinuxAuditLogs", "Written %d logs", java.lang.Integer.valueOf(r11));
                 */
                /* JADX WARN: Failed to find 'out' block for switch in B:68:0x017b. Please report as an issue. */
                /* JADX WARN: Removed duplicated region for block: B:83:0x029a  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void run() {
                    /*
                        Method dump skipped, instructions count: 962
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.selinux.SelinuxAuditLogsService$$ExternalSyntheticLambda0.run():void");
                }
            });
            return true;
        }
        Slog.i("SelinuxAuditLogs", "Selinux audit job disabled.");
        return false;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        if (jobParameters.getJobId() != 25327386) {
            return false;
        }
        SelinuxAuditLogsJob selinuxAuditLogsJob = LOGS_COLLECTOR_JOB;
        if (!selinuxAuditLogsJob.mIsRunning.get()) {
            return false;
        }
        selinuxAuditLogsJob.mAuditLogsCollector.mStopRequested.set(true);
        return true;
    }
}
