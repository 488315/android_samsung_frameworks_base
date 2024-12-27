package com.android.server.notification;

import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.provider.Settings;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class ReviewNotificationPermissionsJobService extends JobService {
    protected static final int JOB_ID = 225373531;

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        NotificationManagerService notificationManagerService = NotificationManagerService.this;
        if (!notificationManagerService.mShowReviewPermissionsNotification) {
            return false;
        }
        NotificationManagerService.checkCallerIsSystem();
        ((NotificationManager)
                        notificationManagerService
                                .getContext()
                                .getSystemService(NotificationManager.class))
                .notify(
                        "NotificationService",
                        71,
                        notificationManagerService.createReviewPermissionsNotification());
        Settings.Global.putInt(
                notificationManagerService.getContext().getContentResolver(),
                "review_permissions_notification_state",
                3);
        return false;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
