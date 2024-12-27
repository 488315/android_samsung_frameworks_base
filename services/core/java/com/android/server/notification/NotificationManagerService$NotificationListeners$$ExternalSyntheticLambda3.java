package com.android.server.notification;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.NotificationRankingUpdate;
import android.service.notification.NotificationStats;
import android.service.notification.StatusBarNotification;
import android.util.Slog;
import com.android.server.notification.ManagedServices;
import com.android.server.notification.NotificationManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId = 2;
    public final /* synthetic */ NotificationManagerService.NotificationListeners f$0;
    public final /* synthetic */ ManagedServices.ManagedServiceInfo f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Object f$3;
    public final /* synthetic */ Object f$4;
    public final /* synthetic */ int f$5;

    public /* synthetic */ NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda3(NotificationManagerService.NotificationListeners notificationListeners, ManagedServices.ManagedServiceInfo managedServiceInfo, StatusBarNotification statusBarNotification, NotificationRankingUpdate notificationRankingUpdate, NotificationStats notificationStats, int i) {
        this.f$0 = notificationListeners;
        this.f$1 = managedServiceInfo;
        this.f$2 = statusBarNotification;
        this.f$3 = notificationRankingUpdate;
        this.f$4 = notificationStats;
        this.f$5 = i;
    }

    public /* synthetic */ NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda3(NotificationManagerService.NotificationListeners notificationListeners, ManagedServices.ManagedServiceInfo managedServiceInfo, String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        this.f$0 = notificationListeners;
        this.f$1 = managedServiceInfo;
        this.f$2 = str;
        this.f$3 = userHandle;
        this.f$4 = notificationChannel;
        this.f$5 = i;
    }

    public /* synthetic */ NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda3(NotificationManagerService.NotificationListeners notificationListeners, ManagedServices.ManagedServiceInfo managedServiceInfo, String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup, int i) {
        this.f$0 = notificationListeners;
        this.f$1 = managedServiceInfo;
        this.f$2 = str;
        this.f$3 = userHandle;
        this.f$4 = notificationChannelGroup;
        this.f$5 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService.NotificationListeners notificationListeners = this.f$0;
                ManagedServices.ManagedServiceInfo managedServiceInfo = this.f$1;
                String str = (String) this.f$2;
                UserHandle userHandle = (UserHandle) this.f$3;
                NotificationChannel notificationChannel = (NotificationChannel) this.f$4;
                int i = this.f$5;
                notificationListeners.getClass();
                if (!managedServiceInfo.isSystem) {
                    NotificationManagerService notificationManagerService = NotificationManagerService.this;
                    notificationManagerService.getClass();
                    if (!notificationManagerService.hasCompanionDevice(managedServiceInfo.userid, managedServiceInfo.component.getPackageName(), null)) {
                        IInterface iInterface = managedServiceInfo.service;
                        synchronized (notificationManagerService.mNotificationLock) {
                            NotificationManagerService.NotificationAssistants notificationAssistants = notificationManagerService.mAssistants;
                            z = false;
                            if (iInterface == null) {
                                notificationAssistants.getClass();
                            } else if (notificationAssistants.getServiceFromTokenLocked(iInterface) != null) {
                                z = true;
                            }
                        }
                        if (!z) {
                            return;
                        }
                    }
                }
                try {
                    managedServiceInfo.service.onNotificationChannelModification(str, userHandle, notificationChannel, i);
                    return;
                } catch (RemoteException e) {
                    Slog.e(notificationListeners.TAG, "unable to notify listener (channel changed): " + managedServiceInfo, e);
                    return;
                }
            case 1:
                NotificationManagerService.NotificationListeners notificationListeners2 = this.f$0;
                ManagedServices.ManagedServiceInfo managedServiceInfo2 = this.f$1;
                String str2 = (String) this.f$2;
                UserHandle userHandle2 = (UserHandle) this.f$3;
                NotificationChannelGroup notificationChannelGroup = (NotificationChannelGroup) this.f$4;
                int i2 = this.f$5;
                notificationListeners2.getClass();
                if (!managedServiceInfo2.isSystem) {
                    NotificationManagerService notificationManagerService2 = NotificationManagerService.this;
                    notificationManagerService2.getClass();
                    if (!notificationManagerService2.hasCompanionDevice(managedServiceInfo2.userid, managedServiceInfo2.component.getPackageName(), null)) {
                        return;
                    }
                }
                try {
                    managedServiceInfo2.service.onNotificationChannelGroupModification(str2, userHandle2, notificationChannelGroup, i2);
                    return;
                } catch (RemoteException e2) {
                    Slog.e(notificationListeners2.TAG, "unable to notify listener (channel group changed): " + managedServiceInfo2, e2);
                    return;
                }
            default:
                this.f$0.notifyRemoved(this.f$1, (StatusBarNotification) this.f$2, (NotificationRankingUpdate) this.f$3, (NotificationStats) this.f$4, this.f$5);
                return;
        }
    }
}
