package com.android.server.notification;

import android.os.Trace;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.SystemService;
import com.android.server.notification.edgelighting.EdgeLightingClientManager;
import com.android.server.notification.edgelighting.EdgeLightingManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationManagerService f$0;
    public final /* synthetic */ SystemService.TargetUser f$1;

    public /* synthetic */ NotificationManagerService$$ExternalSyntheticLambda3(
            NotificationManagerService notificationManagerService,
            SystemService.TargetUser targetUser,
            int i) {
        this.$r8$classId = i;
        this.f$0 = notificationManagerService;
        this.f$1 = targetUser;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService notificationManagerService = this.f$0;
                SystemService.TargetUser targetUser = this.f$1;
                notificationManagerService.getClass();
                Trace.traceBegin(524288L, "notifHistoryUnlockUser");
                try {
                    notificationManagerService.mHistoryManager.onUserUnlocked(
                            targetUser.getUserIdentifier());
                    EdgeLightingManager edgeLightingManager =
                            notificationManagerService.mEdgeLightingManager;
                    int userIdentifier = targetUser.getUserIdentifier();
                    EdgeLightingClientManager edgeLightingClientManager =
                            edgeLightingManager.mEdgeLightingClientManager;
                    edgeLightingClientManager.getClass();
                    Slog.d("EdgeLightingClientManager", "onUnlockUser : " + userIdentifier);
                    edgeLightingClientManager.createEdgeLightingService(UserHandle.SEM_OWNER);
                    return;
                } finally {
                    Trace.traceEnd(524288L);
                }
            default:
                NotificationManagerService notificationManagerService2 = this.f$0;
                SystemService.TargetUser targetUser2 = this.f$1;
                notificationManagerService2.getClass();
                Trace.traceBegin(524288L, "notifHistoryStopUser");
                try {
                    notificationManagerService2.mHistoryManager.onUserStopped(
                            targetUser2.getUserIdentifier());
                    return;
                } finally {
                    Trace.traceEnd(524288L);
                }
        }
    }
}
