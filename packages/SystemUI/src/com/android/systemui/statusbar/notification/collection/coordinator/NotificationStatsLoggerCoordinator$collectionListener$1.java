package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationStatsLoggerCoordinator$collectionListener$1 implements NotifCollectionListener {
    final /* synthetic */ NotificationStatsLoggerCoordinator this$0;

    public NotificationStatsLoggerCoordinator$collectionListener$1(NotificationStatsLoggerCoordinator notificationStatsLoggerCoordinator) {
        this.this$0 = notificationStatsLoggerCoordinator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onEntryRemoved$lambda$1(NotificationEntry notificationEntry, NotificationStatsLogger notificationStatsLogger) {
        String str = notificationEntry.mKey;
        NotificationStatsLoggerImpl notificationStatsLoggerImpl = (NotificationStatsLoggerImpl) notificationStatsLogger;
        ((ConcurrentHashMap) notificationStatsLoggerImpl.expansionStates).remove(str);
        ((ConcurrentHashMap) notificationStatsLoggerImpl.lastReportedExpansionValues).remove(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onEntryUpdated$lambda$0(NotificationEntry notificationEntry, NotificationStatsLogger notificationStatsLogger) {
        ((ConcurrentHashMap) ((NotificationStatsLoggerImpl) notificationStatsLogger).lastReportedExpansionValues).remove(notificationEntry.mKey);
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        Optional optional;
        optional = this.this$0.loggerOptional;
        optional.ifPresent(new NotificationStatsLoggerCoordinator$sam$java_util_function_Consumer$0(new NotificationStatsLoggerCoordinator$collectionListener$1$$ExternalSyntheticLambda0(notificationEntry, 1)));
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryUpdated(NotificationEntry notificationEntry) {
        Optional optional;
        optional = this.this$0.loggerOptional;
        optional.ifPresent(new NotificationStatsLoggerCoordinator$sam$java_util_function_Consumer$0(new NotificationStatsLoggerCoordinator$collectionListener$1$$ExternalSyntheticLambda0(notificationEntry, 0)));
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
        onEntryUpdated(notificationEntry);
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
    public /* bridge */ /* synthetic */ void onRankingApplied() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
    }
}
