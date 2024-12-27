package com.android.server.notification;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.service.notification.INotificationListener;
import android.service.notification.NotificationRankingUpdate;
import android.util.Slog;

import java.util.ArrayList;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */
class NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda8
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */
    NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda8(
            Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService.NotificationListeners notificationListeners =
                        (NotificationManagerService.NotificationListeners) this.f$0;
                ManagedServices.ManagedServiceInfo managedServiceInfo =
                        (ManagedServices.ManagedServiceInfo) this.f$1;
                NotificationRankingUpdate notificationRankingUpdate =
                        (NotificationRankingUpdate) this.f$2;
                String str = notificationListeners.TAG;
                try {
                    managedServiceInfo.service.onNotificationRankingUpdate(
                            notificationRankingUpdate);
                    break;
                } catch (DeadObjectException e) {
                    Slog.wtf(
                            str,
                            "unable to notify listener (ranking update): " + managedServiceInfo,
                            e);
                    return;
                } catch (RemoteException e2) {
                    Slog.e(
                            str,
                            "unable to notify listener (ranking update): " + managedServiceInfo,
                            e2);
                    return;
                }
            case 1:
                NotificationManagerService.NotificationAssistants notificationAssistants =
                        (NotificationManagerService.NotificationAssistants) this.f$0;
                ManagedServices.ManagedServiceInfo managedServiceInfo2 =
                        (ManagedServices.ManagedServiceInfo) this.f$1;
                ArrayList arrayList = (ArrayList) this.f$2;
                notificationAssistants.getClass();
                try {
                    managedServiceInfo2.service.onNotificationsSeen(arrayList);
                    break;
                } catch (RemoteException e3) {
                    Slog.e(
                            notificationAssistants.TAG,
                            "unable to notify assistant (seen): " + managedServiceInfo2,
                            e3);
                    return;
                }
            default:
                ((BiConsumer) this.f$0)
                        .accept(
                                (INotificationListener) this.f$1,
                                (NotificationManagerService.StatusBarNotificationHolder) this.f$2);
                break;
        }
    }
}
