package com.android.server.notification;

import android.content.Intent;
import android.os.Binder;
import android.os.UserHandle;
import android.util.Slog;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class NotificationManagerService$12$$ExternalSyntheticLambda3
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationManagerService$12$$ExternalSyntheticLambda3(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void runOrThrow() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                NotificationManagerService.AnonymousClass12 anonymousClass12 =
                        (NotificationManagerService.AnonymousClass12) obj;
                NotificationManagerService notificationManagerService =
                        NotificationManagerService.this;
                notificationManagerService.getClass();
                notificationManagerService.sendRegisteredOnlyBroadcast(
                        new Intent("android.app.action.INTERRUPTION_FILTER_CHANGED"));
                NotificationManagerService.this
                        .getContext()
                        .sendBroadcastAsUser(
                                new Intent(
                                                "android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL")
                                        .addFlags(67108864),
                                UserHandle.ALL,
                                "android.permission.MANAGE_NOTIFICATIONS");
                synchronized (NotificationManagerService.this.mNotificationLock) {
                    NotificationManagerService.m722$$Nest$mupdateInterruptionFilterLocked(
                            NotificationManagerService.this);
                }
                int callingUid = Binder.getCallingUid();
                if (NotificationManagerService.this.mZenModeHelper
                                .getAppsToBypassDndForEnabledForMode()
                        != null) {
                    if (NotificationManagerService.DBG) {
                        Slog.d(
                                "NotificationService",
                                "BixbyRoutine Mode rule is added when DND is on.");
                    }
                    NotificationManagerService notificationManagerService2 =
                            NotificationManagerService.this;
                    if (notificationManagerService2.mZenModeHelper.mZenMode != 0) {
                        notificationManagerService2.mPreferencesHelper
                                .setChannelsBypassingDndForMode(
                                        callingUid,
                                        true,
                                        notificationManagerService2.isCallerSystemOrSystemUi());
                    } else {
                        notificationManagerService2.mPreferencesHelper
                                .setChannelsBypassingDndForMode(
                                        callingUid,
                                        false,
                                        notificationManagerService2.isCallerSystemOrSystemUi());
                    }
                } else {
                    if (NotificationManagerService.DBG) {
                        Slog.d("NotificationService", "There is not a BixbyRoutine Mode rule.");
                    }
                    NotificationManagerService notificationManagerService3 =
                            NotificationManagerService.this;
                    notificationManagerService3.mPreferencesHelper.setChannelsBypassingDndForMode(
                            callingUid,
                            false,
                            notificationManagerService3.isCallerSystemOrSystemUi());
                }
                ((NotificationManagerService.RankingHandlerWorker)
                                NotificationManagerService.this.mRankingHandler)
                        .requestSort();
                NotificationManagerService.this.notifyZenPolicy();
                return;
            case 1:
                ((NotificationManagerService.PostNotificationTracker) obj).mWakeLock.release();
                return;
            default:
                ((NotificationManagerService.PostNotificationTracker) obj).mWakeLock.release();
                return;
        }
    }
}
