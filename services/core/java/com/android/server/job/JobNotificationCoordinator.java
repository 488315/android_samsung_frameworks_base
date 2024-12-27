package com.android.server.job;

import android.app.Notification;
import android.content.pm.UserPackage;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArrayMap;
import android.util.SparseSetArray;

import com.android.modules.expresslog.Counter;
import com.android.server.LocalServices;
import com.android.server.job.controllers.JobStatus;
import com.android.server.notification.NotificationManagerInternal;
import com.android.server.notification.NotificationManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class JobNotificationCoordinator {
    public final Object mUijLock = new Object();
    public final ArrayMap mCurrentAssociations = new ArrayMap();
    public final ArrayMap mNotificationDetails = new ArrayMap();
    public final SparseArrayMap mUijNotifications = new SparseArrayMap();
    public final SparseArrayMap mUijNotificationChannels = new SparseArrayMap();
    public final NotificationManagerInternal mNotificationManagerInternal =
            (NotificationManagerInternal)
                    LocalServices.getService(NotificationManagerInternal.class);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationDetails {
        public final int appPid;
        public final int appUid;
        public final int jobEndNotificationPolicy;
        public final String notificationChannel;
        public final int notificationId;
        public final UserPackage userPackage;

        public NotificationDetails(
                UserPackage userPackage, int i, int i2, int i3, String str, int i4) {
            this.userPackage = userPackage;
            this.notificationId = i3;
            this.notificationChannel = str;
            this.appPid = i;
            this.appUid = i2;
            this.jobEndNotificationPolicy = i4;
        }
    }

    public final void enqueueNotification(
            JobServiceContext jobServiceContext,
            String str,
            int i,
            int i2,
            int i3,
            Notification notification,
            int i4) {
        if (notification == null) {
            throw new NullPointerException("notification");
        }
        if (notification.getSmallIcon() == null) {
            throw new IllegalArgumentException("small icon required");
        }
        if (((NotificationManagerService.AnonymousClass17) this.mNotificationManagerInternal)
                        .getNotificationChannel(i2, str, notification.getChannelId())
                == null) {
            throw new IllegalArgumentException("invalid notification channel");
        }
        if (i4 != 0 && i4 != 1) {
            throw new IllegalArgumentException("invalid job end notification policy");
        }
        JobStatus jobStatus = jobServiceContext.mRunningJob;
        if (jobStatus == null) {
            Slog.wtfStack(
                    "JobNotificationCoordinator", "enqueueNotification called with no running job");
            return;
        }
        NotificationDetails notificationDetails =
                (NotificationDetails) this.mNotificationDetails.get(jobServiceContext);
        if (notificationDetails != null) {
            if (jobStatus.startedAsUserInitiatedJob) {
                Counter.logIncrementWithUid(
                        "job_scheduler.value_cntr_w_uid_subsequent_set_notification_call_required",
                        jobStatus.callingUid);
            } else {
                Counter.logIncrementWithUid(
                        "job_scheduler.value_cntr_w_uid_subsequent_set_notification_call_optional",
                        jobStatus.callingUid);
            }
            if (notificationDetails.notificationId != i3) {
                removeNotificationAssociation(0, jobServiceContext, jobStatus);
                Counter.logIncrementWithUid(
                        "job_scheduler.value_cntr_w_uid_set_notification_changed_notification_ids",
                        jobStatus.callingUid);
            }
        } else if (jobStatus.startedAsUserInitiatedJob) {
            Counter.logIncrementWithUid(
                    "job_scheduler.value_cntr_w_uid_initial_set_notification_call_required",
                    jobStatus.callingUid);
        } else {
            Counter.logIncrementWithUid(
                    "job_scheduler.value_cntr_w_uid_initial_set_notification_call_optional",
                    jobStatus.callingUid);
        }
        int userId = UserHandle.getUserId(i2);
        if (jobStatus.startedAsUserInitiatedJob) {
            notification.flags |= 32768;
            synchronized (this.mUijLock) {
                try {
                    if (this.mUijNotifications.get(userId, str) == null) {
                        this.mUijNotifications.add(userId, str, new IntArray());
                    }
                    if (this.mUijNotificationChannels.get(userId, str) == null) {
                        this.mUijNotificationChannels.add(userId, str, new ArraySet());
                    }
                    IntArray intArray = (IntArray) this.mUijNotifications.get(userId, str);
                    if (intArray.indexOf(i3) == -1) {
                        intArray.add(i3);
                    }
                    ((ArraySet) this.mUijNotificationChannels.get(userId, str))
                            .add(notification.getChannelId());
                } finally {
                }
            }
        }
        UserPackage of = UserPackage.of(userId, str);
        NotificationDetails notificationDetails2 =
                new NotificationDetails(of, i, i2, i3, notification.getChannelId(), i4);
        SparseSetArray sparseSetArray = (SparseSetArray) this.mCurrentAssociations.get(of);
        if (sparseSetArray == null) {
            sparseSetArray = new SparseSetArray();
            this.mCurrentAssociations.put(of, sparseSetArray);
        }
        sparseSetArray.add(i3, jobServiceContext);
        this.mNotificationDetails.put(jobServiceContext, notificationDetails2);
        NotificationManagerService.this.enqueueNotificationInternal(
                str, str, i2, i, null, i3, notification, userId, false, false);
    }

    public final boolean isNotificationUsedForAnyUij(int i, int i2, String str) {
        ArraySet arraySet;
        SparseSetArray sparseSetArray =
                (SparseSetArray) this.mCurrentAssociations.get(UserPackage.of(i, str));
        if (sparseSetArray == null || (arraySet = sparseSetArray.get(i2)) == null) {
            return false;
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            JobStatus jobStatus = ((JobServiceContext) arraySet.valueAt(size)).mRunningJob;
            if (jobStatus != null && jobStatus.startedAsUserInitiatedJob) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void removeNotificationAssociation(
            int r18,
            com.android.server.job.JobServiceContext r19,
            com.android.server.job.controllers.JobStatus r20) {
        /*
            Method dump skipped, instructions count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.job.JobNotificationCoordinator.removeNotificationAssociation(int,"
                    + " com.android.server.job.JobServiceContext,"
                    + " com.android.server.job.controllers.JobStatus):void");
    }
}
