package com.android.systemui.statusbar.notification.collection;

import com.android.internal.logging.MetricsLogger;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.row.NotificationActionClickManager;
import com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider;

/* loaded from: classes3.dex */
public final class NotificationEntryAdapter implements EntryAdapter {
    public final NotificationEntry entry;
    public final NotificationActionClickManager notificationActionClickManager;
    public final NotificationActivityStarter notificationActivityStarter;
    public final VisualStabilityCoordinator visualStabilityCoordinator;

    public NotificationEntryAdapter(NotificationActivityStarter notificationActivityStarter, MetricsLogger metricsLogger, PeopleNotificationIdentifier peopleNotificationIdentifier, NotificationIconStyleProvider notificationIconStyleProvider, VisualStabilityCoordinator visualStabilityCoordinator, NotificationActionClickManager notificationActionClickManager, HighPriorityProvider highPriorityProvider, HeadsUpManager headsUpManager, NotificationEntry notificationEntry) {
        this.notificationActivityStarter = notificationActivityStarter;
        this.visualStabilityCoordinator = visualStabilityCoordinator;
        this.notificationActionClickManager = notificationActionClickManager;
        this.entry = notificationEntry;
    }

    @Override // com.android.systemui.statusbar.notification.collection.EntryAdapter
    public final void endLifetimeExtension(NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback, NotifLifetimeExtender notifLifetimeExtender) {
        if (onEndLifetimeExtensionCallback != null) {
            ((NotifCollection$$ExternalSyntheticLambda4) onEndLifetimeExtensionCallback).onEndLifetimeExtension(notifLifetimeExtender, this.entry);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.EntryAdapter
    public final String getKey() {
        return this.entry.mKey;
    }
}
