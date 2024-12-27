package com.android.server.notification;

import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */
class NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda9
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationManagerService.NotificationListeners f$0;
    public final /* synthetic */ ManagedServices.ManagedServiceInfo f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */
    NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda9(
            NotificationManagerService.NotificationListeners notificationListeners,
            ManagedServices.ManagedServiceInfo managedServiceInfo,
            int i,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = notificationListeners;
        this.f$1 = managedServiceInfo;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService.NotificationListeners notificationListeners = this.f$0;
                ManagedServices.ManagedServiceInfo managedServiceInfo = this.f$1;
                int i = this.f$2;
                notificationListeners.getClass();
                try {
                    managedServiceInfo.service.onListenerHintsChanged(i);
                    break;
                } catch (RemoteException e) {
                    Slog.e(
                            notificationListeners.TAG,
                            "unable to notify listener (listener hints): " + managedServiceInfo,
                            e);
                    return;
                }
            default:
                NotificationManagerService.NotificationListeners notificationListeners2 = this.f$0;
                ManagedServices.ManagedServiceInfo managedServiceInfo2 = this.f$1;
                int i2 = this.f$2;
                notificationListeners2.getClass();
                try {
                    managedServiceInfo2.service.onInterruptionFilterChanged(i2);
                    break;
                } catch (RemoteException e2) {
                    Slog.e(
                            notificationListeners2.TAG,
                            "unable to notify listener (interruption filter): "
                                    + managedServiceInfo2,
                            e2);
                }
        }
    }
}
