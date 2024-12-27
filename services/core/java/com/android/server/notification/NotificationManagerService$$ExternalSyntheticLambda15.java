package com.android.server.notification;

import android.os.Binder;
import android.os.SystemClock;
import android.service.notification.NotificationRankingUpdate;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$$ExternalSyntheticLambda15
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ NotificationManagerService$$ExternalSyntheticLambda15(
            Object obj, Object obj2, Object obj3, Object obj4, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
        this.f$3 = obj4;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService notificationManagerService =
                        (NotificationManagerService) this.f$0;
                List list = (List) this.f$1;
                NotificationManagerService.PostNotificationTracker postNotificationTracker =
                        (NotificationManagerService.PostNotificationTracker) this.f$2;
                NotificationRecordLogger.NotificationReported notificationReported =
                        (NotificationRecordLogger.NotificationReported) this.f$3;
                notificationManagerService.getClass();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
                postNotificationTracker.getClass();
                long elapsedRealtime =
                        SystemClock.elapsedRealtime() - postNotificationTracker.mStartTime;
                if (postNotificationTracker.isOngoing()) {
                    postNotificationTracker.mOngoing = false;
                    if (postNotificationTracker.mWakeLock != null) {
                        Binder.withCleanCallingIdentity(
                                new NotificationManagerService$12$$ExternalSyntheticLambda3(
                                        2, postNotificationTracker));
                    }
                    if (NotificationManagerService.DBG) {
                        Slog.d(
                                "NotificationService",
                                TextUtils.formatSimple(
                                        "PostNotification: Finished in %d ms",
                                        new Object[] {Long.valueOf(elapsedRealtime)}));
                    }
                } else {
                    Log.wtfStack(
                            "NotificationService", "finish() called on already-finished tracker");
                }
                if (notificationReported != null) {
                    notificationReported.post_duration_millis = elapsedRealtime;
                    ((NotificationRecordLoggerImpl)
                                    notificationManagerService.mNotificationRecordLogger)
                            .getClass();
                    NotificationRecordLoggerImpl.writeNotificationReportedAtom(
                            notificationReported);
                    break;
                }
                break;
            case 1:
                ((NotificationManagerService.NotificationListeners) this.f$0)
                        .notifyPosted(
                                (ManagedServices.ManagedServiceInfo) this.f$1,
                                (StatusBarNotification) this.f$2,
                                (NotificationRankingUpdate) this.f$3);
                break;
            case 2:
                ((NotificationManagerService.NotificationListeners) this.f$0)
                        .notifyRemoved(
                                (ManagedServices.ManagedServiceInfo) this.f$1,
                                (StatusBarNotification) this.f$2,
                                (NotificationRankingUpdate) this.f$3,
                                null,
                                6);
                break;
            default:
                ((NotificationManagerService.NotificationListeners) this.f$0)
                        .notifyPosted(
                                (ManagedServices.ManagedServiceInfo) this.f$1,
                                (StatusBarNotification) this.f$2,
                                (NotificationRankingUpdate) this.f$3);
                break;
        }
    }
}
