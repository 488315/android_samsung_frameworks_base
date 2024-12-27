package com.android.server.notification;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.util.Slog;

import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$$ExternalSyntheticLambda16
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ NotificationManagerService$$ExternalSyntheticLambda16(
            int i, int i2, Object obj, Object obj2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = i;
    }

    public /* synthetic */ NotificationManagerService$$ExternalSyntheticLambda16(
            NotificationManagerService.AnonymousClass16 anonymousClass16, int i, String str) {
        this.$r8$classId = 2;
        this.f$0 = anonymousClass16;
        this.f$2 = i;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService notificationManagerService =
                        (NotificationManagerService) this.f$0;
                StatusBarNotification statusBarNotification = (StatusBarNotification) this.f$1;
                int i = this.f$2;
                notificationManagerService.getClass();
                notificationManagerService.cancelNotification(
                        NotificationManagerService.MY_UID,
                        NotificationManagerService.MY_PID,
                        statusBarNotification.getPackageName(),
                        statusBarNotification.getTag(),
                        statusBarNotification.getId(),
                        0,
                        false,
                        i,
                        25,
                        null);
                break;
            case 1:
                NotificationManagerService.AnonymousClass13 anonymousClass13 =
                        (NotificationManagerService.AnonymousClass13) this.f$0;
                String str = (String) this.f$1;
                int i2 = this.f$2;
                NotificationManagerService notificationManagerService2 =
                        NotificationManagerService.this;
                if (notificationManagerService2.mUmInternal.isUserInitialized(i2)) {
                    int packageUid =
                            notificationManagerService2.mPackageManagerInternal.getPackageUid(
                                    str, 0L, i2);
                    if (packageUid != -1) {
                        if (!notificationManagerService2.mPermissionHelper.hasPermission(
                                packageUid)) {
                            notificationManagerService2.cancelAllNotificationsInt(
                                    NotificationManagerService.MY_UID,
                                    NotificationManagerService.MY_PID,
                                    0,
                                    str,
                                    null,
                                    i2,
                                    7);
                            break;
                        }
                    } else {
                        Log.e(
                                "NotificationService",
                                AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                                        i2, "No uid found for ", str, ", ", "!"));
                        break;
                    }
                }
                break;
            case 2:
                ((PowerManager)
                                NotificationManagerService.this
                                        .getContext()
                                        .getSystemService(PowerManager.class))
                        .wakeUp(
                                SystemClock.uptimeMillis(),
                                this.f$2,
                                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                        "EDGELIGHTING:", (String) this.f$1));
                break;
            default:
                NotificationManagerService.NotificationAssistants notificationAssistants =
                        (NotificationManagerService.NotificationAssistants) this.f$0;
                ManagedServices.ManagedServiceInfo managedServiceInfo =
                        (ManagedServices.ManagedServiceInfo) this.f$1;
                int i3 = this.f$2;
                notificationAssistants.getClass();
                try {
                    managedServiceInfo.service.onPanelRevealed(i3);
                    break;
                } catch (RemoteException e) {
                    Slog.e(
                            notificationAssistants.TAG,
                            "unable to notify assistant (panel revealed): " + managedServiceInfo,
                            e);
                }
        }
    }
}
