package com.android.server.notification;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Slog;

import com.android.server.LocalServices;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class NotificationBitmapJobService extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;

    public static long getTimeUntilRemoval(
            ZonedDateTime zonedDateTime,
            ZonedDateTime zonedDateTime2,
            ZonedDateTime zonedDateTime3) {
        return Duration.between(zonedDateTime, zonedDateTime2).isNegative()
                ? Duration.between(zonedDateTime, zonedDateTime3).toMillis()
                : Duration.between(zonedDateTime, zonedDateTime2).toMillis();
    }

    public static void scheduleJob(Context context) {
        if (context == null) {
            return;
        }
        try {
            JobScheduler forNamespace =
                    ((JobScheduler) context.getSystemService(JobScheduler.class))
                            .forNamespace("NotificationBitmapJob");
            JobInfo.Builder requiresDeviceIdle =
                    new JobInfo.Builder(
                                    290381858,
                                    new ComponentName(
                                            context, (Class<?>) NotificationBitmapJobService.class))
                            .setRequiresDeviceIdle(true);
            ZoneId systemDefault = ZoneId.systemDefault();
            ZonedDateTime atZone = Instant.now().atZone(systemDefault);
            ZonedDateTime of =
                    ZonedDateTime.of(atZone.toLocalDate(), LocalTime.of(2, 0), systemDefault);
            if (forNamespace.schedule(
                            requiresDeviceIdle
                                    .setMinimumLatency(
                                            getTimeUntilRemoval(atZone, of, of.plusDays(1L)))
                                    .build())
                    != 1) {
                Slog.e("NotificationBitmapJob", "Failed to schedule bitmap removal job");
            }
        } catch (Throwable th) {
            Slog.wtf("NotificationBitmapJob", "Failed bitmap removal job", th);
        }
    }

    @Override // android.app.Service, android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(final JobParameters jobParameters) {
        new Thread(
                        new Runnable() { // from class:
                                         // com.android.server.notification.NotificationBitmapJobService$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                NotificationBitmapJobService notificationBitmapJobService =
                                        NotificationBitmapJobService.this;
                                JobParameters jobParameters2 = jobParameters;
                                int i = NotificationBitmapJobService.$r8$clinit;
                                notificationBitmapJobService.getClass();
                                ((NotificationManagerService.AnonymousClass17)
                                                ((NotificationManagerInternal)
                                                        LocalServices.getService(
                                                                NotificationManagerInternal.class)))
                                        .removeBitmaps();
                                NotificationBitmapJobService.scheduleJob(
                                        notificationBitmapJobService);
                                notificationBitmapJobService.jobFinished(jobParameters2, false);
                            }
                        })
                .start();
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
