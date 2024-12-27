package com.android.server.notification;

import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;

import com.android.server.notification.sec.runestone.CollectionContract;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$$ExternalSyntheticLambda7
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ NotificationManagerService$$ExternalSyntheticLambda7(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService notificationManagerService =
                        (NotificationManagerService) this.f$0;
                List list = (List) this.f$1;
                notificationManagerService.getClass();
                Log.d("NotificationService", "start sending log");
                try {
                    CollectionContract.API.putLogs(notificationManagerService.getContext(), list);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case 1:
                NotificationManagerService notificationManagerService2 =
                        (NotificationManagerService) this.f$0;
                NotificationRecord notificationRecord = (NotificationRecord) this.f$1;
                synchronized (notificationManagerService2.mNotificationLock) {
                    notificationManagerService2.mListeners.notifyPostedLocked(
                            notificationRecord, notificationRecord);
                }
                return;
            case 2:
                NotificationManagerService.NotificationAssistants notificationAssistants =
                        (NotificationManagerService.NotificationAssistants) this.f$0;
                ManagedServices.ManagedServiceInfo managedServiceInfo =
                        (ManagedServices.ManagedServiceInfo) this.f$1;
                notificationAssistants.getClass();
                try {
                    managedServiceInfo.service.onPanelHidden();
                    return;
                } catch (RemoteException e2) {
                    Slog.e(
                            notificationAssistants.TAG,
                            "unable to notify assistant (panel hidden): " + managedServiceInfo,
                            e2);
                    return;
                }
            case 3:
                NotificationManagerService.this.updateUriPermissions(
                        null, (NotificationRecord) this.f$1, null, 0);
                return;
            default:
                NotificationManagerService.NotificationListeners.AnonymousClass1 anonymousClass1 =
                        (NotificationManagerService.NotificationListeners.AnonymousClass1) this.f$0;
                StatusBarNotification statusBarNotification = (StatusBarNotification) this.f$1;
                synchronized (
                        ((NotificationManagerService) anonymousClass1.this$1).mNotificationLock) {
                    NotificationManagerService notificationManagerService3 =
                            (NotificationManagerService) anonymousClass1.this$1;
                    GroupHelper groupHelper = notificationManagerService3.mGroupHelper;
                    ArrayMap arrayMap =
                            (ArrayMap)
                                    notificationManagerService3.mAutobundledSummaries.get(
                                            Integer.valueOf(statusBarNotification.getUserId()));
                    groupHelper.onNotificationPosted(
                            statusBarNotification,
                            arrayMap != null
                                    && arrayMap.containsKey(
                                            statusBarNotification.getPackageName()));
                }
                return;
        }
    }
}
