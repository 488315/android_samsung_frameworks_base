package com.android.server.notification;

import android.app.Notification;
import android.service.notification.StatusBarNotification;
import android.util.Slog;

import java.util.Date;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$10$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationManagerService$10$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((NotificationManagerService.AnonymousClass5) obj)
                        .this$0.registerConversationAppPolicyScpm();
                return;
            default:
                NotificationManagerService notificationManagerService =
                        (NotificationManagerService)
                                ((NotificationManagerService.NotificationListeners.AnonymousClass1)
                                                obj)
                                        .this$1;
                synchronized (notificationManagerService.mNotificationLock) {
                    try {
                        int size = notificationManagerService.mNotificationList.size();
                        Slog.d("NotificationService", "cancelOldestNotification start N = " + size);
                        NotificationRecord notificationRecord = null;
                        for (int i2 = 0; i2 < size; i2++) {
                            NotificationRecord notificationRecord2 =
                                    (NotificationRecord)
                                            notificationManagerService.mNotificationList.get(i2);
                            StatusBarNotification statusBarNotification = notificationRecord2.sbn;
                            Notification notification = statusBarNotification.getNotification();
                            if ((!statusBarNotification.isGroup() || !notification.isGroupSummary())
                                    && (notification.flags & 8192) == 0
                                    && !notification.isFgsOrUij()
                                    && (notificationRecord == null
                                            || notificationRecord.mUpdateTimeMs
                                                    > notificationRecord2.mUpdateTimeMs)) {
                                notificationRecord = notificationRecord2;
                            }
                        }
                        if (notificationRecord == null) {
                            return;
                        }
                        StatusBarNotification statusBarNotification2 = notificationRecord.sbn;
                        int identifier = statusBarNotification2.getUser().getIdentifier();
                        Slog.d(
                                "NotificationService",
                                "cancelOldestNotification end oldest = "
                                        + notificationRecord.sbn.getKey()
                                        + ", time = "
                                        + notificationManagerService.dayTime.format(
                                                new Date(notificationRecord.mUpdateTimeMs)));
                        notificationManagerService.mHandler.postAtFrontOfQueue(
                                new NotificationManagerService$$ExternalSyntheticLambda16(
                                        identifier,
                                        0,
                                        notificationManagerService,
                                        statusBarNotification2));
                        return;
                    } finally {
                    }
                }
        }
    }
}
