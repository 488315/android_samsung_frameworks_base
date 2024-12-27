package com.android.server.notification;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.CancellationSignal;
import android.util.Slog;

import com.android.server.LocalServices;

import java.util.concurrent.TimeUnit;

public class NotificationHistoryJobService extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long JOB_RUN_INTERVAL = TimeUnit.MINUTES.toMillis(20);
    public CancellationSignal mSignal;

    public static void scheduleJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        if (jobScheduler.getPendingJob(237039804) != null
                || jobScheduler.schedule(
                                new JobInfo.Builder(
                                                237039804,
                                                new ComponentName(
                                                        context,
                                                        (Class<?>)
                                                                NotificationHistoryJobService
                                                                        .class))
                                        .setRequiresDeviceIdle(false)
                                        .setPeriodic(JOB_RUN_INTERVAL)
                                        .build())
                        == 1) {
            return;
        }
        Slog.w("NotificationHistoryJob", "Failed to schedule history cleanup job");
    }

    @Override // android.app.Service, android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(final JobParameters jobParameters) {
        this.mSignal = new CancellationSignal();
        new Thread(
                        new Runnable() { // from class:
                                         // com.android.server.notification.NotificationHistoryJobService$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                NotificationHistoryDatabase notificationHistoryDatabase;
                                NotificationHistoryJobService notificationHistoryJobService =
                                        NotificationHistoryJobService.this;
                                JobParameters jobParameters2 = jobParameters;
                                int i = NotificationHistoryJobService.$r8$clinit;
                                notificationHistoryJobService.getClass();
                                NotificationManagerService.AnonymousClass17 anonymousClass17 =
                                        (NotificationManagerService.AnonymousClass17)
                                                ((NotificationManagerInternal)
                                                        LocalServices.getService(
                                                                NotificationManagerInternal.class));
                                anonymousClass17.this$0.getClass();
                                NotificationManagerService.checkCallerIsSystem();
                                NotificationHistoryManager notificationHistoryManager =
                                        anonymousClass17.this$0.mHistoryManager;
                                synchronized (notificationHistoryManager.mLock) {
                                    try {
                                        int size =
                                                notificationHistoryManager.mUserUnlockedStates
                                                        .size();
                                        for (int i2 = 0; i2 < size; i2++) {
                                            if (notificationHistoryManager.mUserUnlockedStates
                                                            .valueAt(i2)
                                                    && (notificationHistoryDatabase =
                                                                    (NotificationHistoryDatabase)
                                                                            notificationHistoryManager
                                                                                    .mUserState.get(
                                                                                    notificationHistoryManager
                                                                                            .mUserUnlockedStates
                                                                                            .keyAt(
                                                                                                    i2)))
                                                            != null) {
                                                notificationHistoryDatabase.prune();
                                            }
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                                notificationHistoryJobService.jobFinished(
                                        jobParameters2,
                                        notificationHistoryJobService.mSignal.isCanceled());
                            }
                        })
                .start();
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        CancellationSignal cancellationSignal = this.mSignal;
        if (cancellationSignal == null) {
            return false;
        }
        cancellationSignal.cancel();
        return false;
    }
}
