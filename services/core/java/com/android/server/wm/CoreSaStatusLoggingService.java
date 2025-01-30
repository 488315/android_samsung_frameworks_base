package com.android.server.wm;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.util.Slog;
import com.samsung.android.server.util.SafetySystemService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class CoreSaStatusLoggingService extends JobService {
    public static String TAG = CoreSaStatusLoggingService.class.getSimpleName();
    public static final long IDLE_LOGGING_PERIOD_MILLIS = TimeUnit.DAYS.toMillis(1);
    public static Object sLock = new Object();
    public static HashSet sCoreSaStatusLoggers = null;

    public interface CoreSaStatusLogger {
        String getName();

        void onStatusLogging();
    }

    public static void registerCoreSaStatusLogger(CoreSaStatusLogger coreSaStatusLogger) {
        if (coreSaStatusLogger == null) {
            return;
        }
        synchronized (sLock) {
            if (sCoreSaStatusLoggers == null) {
                sCoreSaStatusLoggers = new HashSet();
                SafetySystemService.registerForSystemReady(new SafetySystemService.Callback() { // from class: com.android.server.wm.CoreSaStatusLoggingService$$ExternalSyntheticLambda0
                    @Override // com.samsung.android.server.util.SafetySystemService.Callback
                    public final void onSystemReady(ActivityTaskManagerService activityTaskManagerService) {
                        CoreSaStatusLoggingService.schedule();
                    }
                });
            }
            sCoreSaStatusLoggers.add(coreSaStatusLogger);
            Slog.d(TAG, "registerCoreSaStatusLogger logger=" + coreSaStatusLogger.getName());
        }
    }

    public static void schedule() {
        try {
            JobScheduler jobScheduler = (JobScheduler) SafetySystemService.getSystemService(JobScheduler.class);
            if (jobScheduler == null) {
                return;
            }
            jobScheduler.schedule(new JobInfo.Builder(415377471, new ComponentName("android", CoreSaStatusLoggingService.class.getName())).setRequiresDeviceIdle(true).setRequiresCharging(true).setPeriodic(IDLE_LOGGING_PERIOD_MILLIS).build());
            Slog.d(TAG, "Jobs scheduled.");
        } catch (Throwable th) {
            Slog.w(TAG, "Failed to schedule.", th);
        }
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        try {
            int jobId = jobParameters.getJobId();
            Slog.d(TAG, "onStartJob " + jobId);
            if (jobId != 415377471) {
                return false;
            }
            synchronized (sLock) {
                if (sCoreSaStatusLoggers != null) {
                    new IdleLoggingThread(jobParameters, sCoreSaStatusLoggers).start();
                    return true;
                }
                Slog.w(TAG, "CoreSaStatusLoggers is null");
                return false;
            }
        } catch (Throwable th) {
            Slog.w(TAG, "Failed to onStartJob.", th);
            return false;
        }
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        try {
            int jobId = jobParameters.getJobId();
            Slog.d(TAG, "onStopJob " + jobId);
            return jobId == 415377471;
        } catch (Throwable th) {
            Slog.w(TAG, "Failed to onStopJob.", th);
            return false;
        }
    }

    public class IdleLoggingThread extends Thread {
        public final HashSet mCoreSaStatusLoggers;
        public final JobParameters mParams;

        public IdleLoggingThread(JobParameters jobParameters, HashSet hashSet) {
            super(CoreSaStatusLoggingService.TAG + "_IdleLoggingJob");
            this.mParams = jobParameters;
            this.mCoreSaStatusLoggers = new HashSet(hashSet);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                try {
                    Iterator it = this.mCoreSaStatusLoggers.iterator();
                    while (it.hasNext()) {
                        CoreSaStatusLogger coreSaStatusLogger = (CoreSaStatusLogger) it.next();
                        Slog.d(CoreSaStatusLoggingService.TAG, "Starting IdleLoggingJob run, logger=" + coreSaStatusLogger.getName());
                        coreSaStatusLogger.onStatusLogging();
                    }
                } catch (Throwable th) {
                    Slog.w(CoreSaStatusLoggingService.TAG, "Failed to run.", th);
                }
                CoreSaStatusLoggingService.this.jobFinished(this.mParams, false);
            } catch (Throwable th2) {
                Slog.w(CoreSaStatusLoggingService.TAG, "Failed to jobFinished.", th2);
            }
        }
    }
}
