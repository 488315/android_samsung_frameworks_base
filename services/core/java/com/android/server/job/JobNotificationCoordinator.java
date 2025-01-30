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

/* loaded from: classes2.dex */
public class JobNotificationCoordinator {
    public final Object mUijLock = new Object();
    public final ArrayMap mCurrentAssociations = new ArrayMap();
    public final ArrayMap mNotificationDetails = new ArrayMap();
    public final SparseArrayMap mUijNotifications = new SparseArrayMap();
    public final SparseArrayMap mUijNotificationChannels = new SparseArrayMap();
    public final NotificationManagerInternal mNotificationManagerInternal = (NotificationManagerInternal) LocalServices.getService(NotificationManagerInternal.class);

    public final class NotificationDetails {
        public final int appPid;
        public final int appUid;
        public final int jobEndNotificationPolicy;
        public final String notificationChannel;
        public final int notificationId;
        public final UserPackage userPackage;

        public NotificationDetails(UserPackage userPackage, int i, int i2, int i3, String str, int i4) {
            this.userPackage = userPackage;
            this.notificationId = i3;
            this.notificationChannel = str;
            this.appPid = i;
            this.appUid = i2;
            this.jobEndNotificationPolicy = i4;
        }
    }

    public void enqueueNotification(JobServiceContext jobServiceContext, String str, int i, int i2, int i3, Notification notification, int i4) {
        validateNotification(str, i2, notification, i4);
        JobStatus runningJobLocked = jobServiceContext.getRunningJobLocked();
        if (runningJobLocked == null) {
            Slog.wtfStack("JobNotificationCoordinator", "enqueueNotification called with no running job");
            return;
        }
        NotificationDetails notificationDetails = (NotificationDetails) this.mNotificationDetails.get(jobServiceContext);
        if (notificationDetails == null) {
            if (runningJobLocked.startedAsUserInitiatedJob) {
                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_initial_set_notification_call_required", runningJobLocked.getUid());
            } else {
                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_initial_set_notification_call_optional", runningJobLocked.getUid());
            }
        } else {
            if (runningJobLocked.startedAsUserInitiatedJob) {
                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_subsequent_set_notification_call_required", runningJobLocked.getUid());
            } else {
                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_subsequent_set_notification_call_optional", runningJobLocked.getUid());
            }
            if (notificationDetails.notificationId != i3) {
                removeNotificationAssociation(jobServiceContext, 0, runningJobLocked);
                Counter.logIncrementWithUid("job_scheduler.value_cntr_w_uid_set_notification_changed_notification_ids", runningJobLocked.getUid());
            }
        }
        int userId = UserHandle.getUserId(i2);
        if (runningJobLocked.startedAsUserInitiatedJob) {
            notification.flags |= 32768;
            synchronized (this.mUijLock) {
                maybeCreateUijNotificationSetsLocked(userId, str);
                IntArray intArray = (IntArray) this.mUijNotifications.get(userId, str);
                if (intArray.indexOf(i3) == -1) {
                    intArray.add(i3);
                }
                ((ArraySet) this.mUijNotificationChannels.get(userId, str)).add(notification.getChannelId());
            }
        }
        UserPackage of = UserPackage.of(userId, str);
        NotificationDetails notificationDetails2 = new NotificationDetails(of, i, i2, i3, notification.getChannelId(), i4);
        SparseSetArray sparseSetArray = (SparseSetArray) this.mCurrentAssociations.get(of);
        if (sparseSetArray == null) {
            sparseSetArray = new SparseSetArray();
            this.mCurrentAssociations.put(of, sparseSetArray);
        }
        sparseSetArray.add(i3, jobServiceContext);
        this.mNotificationDetails.put(jobServiceContext, notificationDetails2);
        this.mNotificationManagerInternal.enqueueNotification(str, str, i2, i, null, i3, notification, userId);
    }

    public void removeNotificationAssociation(JobServiceContext jobServiceContext, int i, JobStatus jobStatus) {
        NotificationDetails notificationDetails = (NotificationDetails) this.mNotificationDetails.remove(jobServiceContext);
        if (notificationDetails == null) {
            return;
        }
        SparseSetArray sparseSetArray = (SparseSetArray) this.mCurrentAssociations.get(notificationDetails.userPackage);
        if (sparseSetArray == null || !sparseSetArray.remove(notificationDetails.notificationId, jobServiceContext)) {
            Slog.wtf("JobNotificationCoordinator", "Association data structures not in sync");
            return;
        }
        int userId = UserHandle.getUserId(notificationDetails.appUid);
        String str = notificationDetails.userPackage.packageName;
        int i2 = notificationDetails.notificationId;
        ArraySet arraySet = sparseSetArray.get(i2);
        boolean z = true;
        if (arraySet == null || arraySet.isEmpty()) {
            if (notificationDetails.jobEndNotificationPolicy == 1 || i == 11 || i == 13) {
                this.mNotificationManagerInternal.cancelNotification(str, str, notificationDetails.appUid, notificationDetails.appPid, null, i2, userId);
                z = false;
            }
        } else {
            z = true ^ isNotificationUsedForAnyUij(userId, str, i2);
        }
        if (z) {
            this.mNotificationManagerInternal.removeUserInitiatedJobFlagFromNotification(str, i2, userId);
        }
        if (jobStatus == null || !jobStatus.startedAsUserInitiatedJob) {
            return;
        }
        maybeDeleteNotificationIdAssociation(userId, str, i2);
        maybeDeleteNotificationChannelAssociation(userId, str, notificationDetails.notificationChannel);
    }

    public boolean isNotificationAssociatedWithAnyUserInitiatedJobs(int i, int i2, String str) {
        synchronized (this.mUijLock) {
            IntArray intArray = (IntArray) this.mUijNotifications.get(i2, str);
            if (intArray != null) {
                return intArray.indexOf(i) != -1;
            }
            return false;
        }
    }

    public boolean isNotificationChannelAssociatedWithAnyUserInitiatedJobs(String str, int i, String str2) {
        synchronized (this.mUijLock) {
            ArraySet arraySet = (ArraySet) this.mUijNotificationChannels.get(i, str2);
            if (arraySet == null) {
                return false;
            }
            return arraySet.contains(str);
        }
    }

    public final boolean isNotificationUsedForAnyUij(int i, String str, int i2) {
        ArraySet arraySet;
        SparseSetArray sparseSetArray = (SparseSetArray) this.mCurrentAssociations.get(UserPackage.of(i, str));
        if (sparseSetArray == null || (arraySet = sparseSetArray.get(i2)) == null) {
            return false;
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            JobStatus runningJobLocked = ((JobServiceContext) arraySet.valueAt(size)).getRunningJobLocked();
            if (runningJobLocked != null && runningJobLocked.startedAsUserInitiatedJob) {
                return true;
            }
        }
        return false;
    }

    public final void maybeDeleteNotificationIdAssociation(int i, String str, int i2) {
        if (isNotificationUsedForAnyUij(i, str, i2)) {
            return;
        }
        synchronized (this.mUijLock) {
            IntArray intArray = (IntArray) this.mUijNotifications.get(i, str);
            if (intArray != null) {
                intArray.remove(intArray.indexOf(i2));
                if (intArray.size() == 0) {
                    this.mUijNotifications.delete(i, str);
                }
            }
        }
    }

    public final void maybeDeleteNotificationChannelAssociation(int i, String str, String str2) {
        JobStatus runningJobLocked;
        for (int size = this.mNotificationDetails.size() - 1; size >= 0; size--) {
            JobServiceContext jobServiceContext = (JobServiceContext) this.mNotificationDetails.keyAt(size);
            NotificationDetails notificationDetails = (NotificationDetails) this.mNotificationDetails.get(jobServiceContext);
            if (notificationDetails != null && UserHandle.getUserId(notificationDetails.appUid) == i && notificationDetails.userPackage.packageName.equals(str) && notificationDetails.notificationChannel.equals(str2) && (runningJobLocked = jobServiceContext.getRunningJobLocked()) != null && runningJobLocked.startedAsUserInitiatedJob) {
                return;
            }
        }
        synchronized (this.mUijLock) {
            ArraySet arraySet = (ArraySet) this.mUijNotificationChannels.get(i, str);
            if (arraySet != null) {
                arraySet.remove(str2);
                if (arraySet.isEmpty()) {
                    this.mUijNotificationChannels.delete(i, str);
                }
            }
        }
    }

    public final void maybeCreateUijNotificationSetsLocked(int i, String str) {
        if (this.mUijNotifications.get(i, str) == null) {
            this.mUijNotifications.add(i, str, new IntArray());
        }
        if (this.mUijNotificationChannels.get(i, str) == null) {
            this.mUijNotificationChannels.add(i, str, new ArraySet());
        }
    }

    public final void validateNotification(String str, int i, Notification notification, int i2) {
        if (notification == null) {
            throw new NullPointerException("notification");
        }
        if (notification.getSmallIcon() == null) {
            throw new IllegalArgumentException("small icon required");
        }
        if (this.mNotificationManagerInternal.getNotificationChannel(str, i, notification.getChannelId()) == null) {
            throw new IllegalArgumentException("invalid notification channel");
        }
        if (i2 != 0 && i2 != 1) {
            throw new IllegalArgumentException("invalid job end notification policy");
        }
    }
}
