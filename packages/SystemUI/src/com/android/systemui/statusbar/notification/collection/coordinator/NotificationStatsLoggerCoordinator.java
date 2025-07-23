package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
public final class NotificationStatsLoggerCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final NotificationStatsLoggerCoordinator$collectionListener$1 collectionListener = new NotificationStatsLoggerCoordinator$collectionListener$1(this);
    private final Optional<NotificationStatsLogger> loggerOptional;

    public NotificationStatsLoggerCoordinator(Optional<NotificationStatsLogger> optional) {
        this.loggerOptional = optional;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationsLiveDataStoreRefactor.$r8$clinit;
        notifPipeline.addCollectionListener(this.collectionListener);
    }
}
