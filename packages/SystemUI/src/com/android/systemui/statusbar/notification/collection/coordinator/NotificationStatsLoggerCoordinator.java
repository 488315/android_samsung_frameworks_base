package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public final class NotificationStatsLoggerCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final NotificationStatsLoggerCoordinator$collectionListener$1 collectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotificationStatsLoggerCoordinator$collectionListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryRemoved(final NotificationEntry notificationEntry, int i) {
            Optional optional;
            optional = NotificationStatsLoggerCoordinator.this.loggerOptional;
            optional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotificationStatsLoggerCoordinator$collectionListener$1$onEntryRemoved$1
                @Override // java.util.function.Consumer
                public final void accept(NotificationStatsLogger notificationStatsLogger) {
                    String str = NotificationEntry.this.mKey;
                    NotificationStatsLoggerImpl notificationStatsLoggerImpl = (NotificationStatsLoggerImpl) notificationStatsLogger;
                    ((ConcurrentHashMap) notificationStatsLoggerImpl.expansionStates).remove(str);
                    ((ConcurrentHashMap) notificationStatsLoggerImpl.lastReportedExpansionValues).remove(str);
                }
            });
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(final NotificationEntry notificationEntry) {
            Optional optional;
            optional = NotificationStatsLoggerCoordinator.this.loggerOptional;
            optional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotificationStatsLoggerCoordinator$collectionListener$1$onEntryUpdated$1
                @Override // java.util.function.Consumer
                public final void accept(NotificationStatsLogger notificationStatsLogger) {
                    ((ConcurrentHashMap) ((NotificationStatsLoggerImpl) notificationStatsLogger).lastReportedExpansionValues).remove(NotificationEntry.this.mKey);
                }
            });
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
            onEntryUpdated(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onRankingApplied() {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryAdded(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryCleanUp(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryInit(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        @Deprecated
        public /* bridge */ /* synthetic */ void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }
    };
    private final Optional<NotificationStatsLogger> loggerOptional;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.NotificationStatsLoggerCoordinator$collectionListener$1] */
    public NotificationStatsLoggerCoordinator(Optional<NotificationStatsLogger> optional) {
        this.loggerOptional = optional;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationsLiveDataStoreRefactor.$r8$clinit;
        Flags.notificationsLiveDataStoreRefactor();
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_live_data_store_refactor to be enabled.");
    }
}
