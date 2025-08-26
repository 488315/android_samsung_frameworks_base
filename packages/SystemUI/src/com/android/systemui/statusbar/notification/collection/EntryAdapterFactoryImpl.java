package com.android.systemui.statusbar.notification.collection;

import com.android.internal.logging.MetricsLogger;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.row.NotificationActionClickManager;
import com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider;

/* loaded from: classes3.dex */
public final class EntryAdapterFactoryImpl implements EntryAdapterFactory {
    public final HeadsUpManager headsUpManager;
    public final HighPriorityProvider highPriorityProvider;
    public final NotificationIconStyleProvider iconStyleProvider;
    public final MetricsLogger metricsLogger;
    public final NotificationActionClickManager notificationActionClickManager;
    public final NotificationActivityStarter notificationActivityStarter;
    public final PeopleNotificationIdentifier peopleNotificationIdentifier;
    public final VisualStabilityCoordinator visualStabilityCoordinator;

    public EntryAdapterFactoryImpl(NotificationActivityStarter notificationActivityStarter, MetricsLogger metricsLogger, PeopleNotificationIdentifier peopleNotificationIdentifier, NotificationIconStyleProvider notificationIconStyleProvider, VisualStabilityCoordinator visualStabilityCoordinator, NotificationActionClickManager notificationActionClickManager, HighPriorityProvider highPriorityProvider, HeadsUpManager headsUpManager) {
        this.notificationActivityStarter = notificationActivityStarter;
        this.metricsLogger = metricsLogger;
        this.peopleNotificationIdentifier = peopleNotificationIdentifier;
        this.iconStyleProvider = notificationIconStyleProvider;
        this.visualStabilityCoordinator = visualStabilityCoordinator;
        this.notificationActionClickManager = notificationActionClickManager;
        this.highPriorityProvider = highPriorityProvider;
        this.headsUpManager = headsUpManager;
    }
}
