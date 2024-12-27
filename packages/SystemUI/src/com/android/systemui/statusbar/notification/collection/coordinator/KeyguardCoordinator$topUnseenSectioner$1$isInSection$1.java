package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.shared.NotificationMinimalismPrototype$V2;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

final class KeyguardCoordinator$topUnseenSectioner$1$isInSection$1 extends Lambda implements Function1 {
    final /* synthetic */ KeyguardCoordinator this$0;

    public KeyguardCoordinator$topUnseenSectioner$1$isInSection$1(KeyguardCoordinator keyguardCoordinator) {
        super(1);
        this.this$0 = keyguardCoordinator;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(NotificationEntry notificationEntry) {
        SeenNotificationsInteractor seenNotificationsInteractor;
        seenNotificationsInteractor = this.this$0.seenNotificationsInteractor;
        seenNotificationsInteractor.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationMinimalismPrototype$V2.$r8$clinit;
        Flags.notificationMinimalismPrototype();
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notification_minimalism_prototype to be enabled.");
        return Boolean.FALSE;
    }
}
