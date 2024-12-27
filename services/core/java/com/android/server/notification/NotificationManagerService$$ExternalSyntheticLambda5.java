package com.android.server.notification;

import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$$ExternalSyntheticLambda5
        implements NotificationManagerService.FlagChecker {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationManagerService$$ExternalSyntheticLambda5(Object obj) {
        this.f$0 = obj;
    }

    @Override // com.android.server.notification.NotificationManagerService.FlagChecker
    public boolean apply(int i) {
        NotificationManagerService.CancelNotificationRunnable cancelNotificationRunnable =
                (NotificationManagerService.CancelNotificationRunnable) this.f$0;
        int i2 = cancelNotificationRunnable.mReason;
        if (i2 == 2 || i2 == 1 || i2 == 3
                ? (i & 4096) == 0
                : i2 != 8 || ((i & 64) == 0 && (32768 & i) == 0)) {
            if ((cancelNotificationRunnable.mMustNotHaveFlags & i) == 0) {
                return true;
            }
        }
        return false;
    }

    public void repost(int i, NotificationRecord notificationRecord, boolean z) {
        NotificationManagerService notificationManagerService =
                (NotificationManagerService) this.f$0;
        notificationManagerService.getClass();
        try {
            if (NotificationManagerService.DBG) {
                Slog.d(
                        "NotificationService",
                        "Reposting " + notificationRecord.sbn.getKey() + " " + z);
            }
            notificationRecord.sbn.getNotification().semFlags &= -129;
            notificationManagerService.enqueueNotificationInternal(
                    notificationRecord.sbn.getPackageName(),
                    notificationRecord.sbn.getOpPkg(),
                    notificationRecord.sbn.getUid(),
                    notificationRecord.sbn.getInitialPid(),
                    notificationRecord.sbn.getTag(),
                    notificationRecord.sbn.getId(),
                    notificationRecord.sbn.getNotification(),
                    i,
                    z,
                    false);
        } catch (Exception e) {
            Slog.e("NotificationService", "Cannot un-snooze notification", e);
        }
    }
}
