package com.android.server.notification;

import android.app.NotificationManager;
import android.content.Intent;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class NotificationManagerService$12$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationManagerService.AnonymousClass12 f$0;
    public final /* synthetic */ NotificationManager.Policy f$1;

    public /* synthetic */ NotificationManagerService$12$$ExternalSyntheticLambda0(
            NotificationManagerService.AnonymousClass12 anonymousClass12,
            NotificationManager.Policy policy,
            int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass12;
        this.f$1 = policy;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService.AnonymousClass12 anonymousClass12 = this.f$0;
                NotificationManager.Policy policy = this.f$1;
                anonymousClass12.getClass();
                Intent intent = new Intent("android.app.action.NOTIFICATION_POLICY_CHANGED");
                if (android.app.Flags.modesApi()) {
                    intent.putExtra("android.app.extra.NOTIFICATION_POLICY", policy);
                }
                NotificationManagerService notificationManagerService = anonymousClass12.this$0;
                notificationManagerService.sendRegisteredOnlyBroadcast(intent);
                ((NotificationManagerService.RankingHandlerWorker)
                                notificationManagerService.mRankingHandler)
                        .requestSort();
                notificationManagerService.notifyZenPolicy();
                break;
            default:
                NotificationManagerService.AnonymousClass12 anonymousClass122 = this.f$0;
                NotificationManager.Policy policy2 = this.f$1;
                anonymousClass122.getClass();
                boolean modesApi = android.app.Flags.modesApi();
                NotificationManagerService notificationManagerService2 = anonymousClass122.this$0;
                if (modesApi) {
                    Intent intent2 =
                            new Intent(
                                    "android.app.action.CONSOLIDATED_NOTIFICATION_POLICY_CHANGED");
                    intent2.putExtra("android.app.extra.NOTIFICATION_POLICY", policy2);
                    notificationManagerService2.sendRegisteredOnlyBroadcast(intent2);
                }
                ((NotificationManagerService.RankingHandlerWorker)
                                notificationManagerService2.mRankingHandler)
                        .requestSort();
                break;
        }
    }
}
