package com.android.systemui.statusbar.notification.collection.provider;

import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.Flags;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;

public final class NotificationVisibilityProviderImpl implements NotificationVisibilityProvider {
    public final CommonNotifCollection notifCollection;
    public final NotifLiveDataStore notifDataStore;

    public NotificationVisibilityProviderImpl(ActiveNotificationsInteractor activeNotificationsInteractor, NotifLiveDataStore notifLiveDataStore, CommonNotifCollection commonNotifCollection) {
        this.notifDataStore = notifLiveDataStore;
        this.notifCollection = commonNotifCollection;
    }

    public final NotificationVisibility obtain(NotificationEntry notificationEntry) {
        Flags.notificationsLiveDataStoreRefactor();
        return NotificationVisibility.obtain(notificationEntry.mKey, notificationEntry.mRanking.getRank(), ((Number) ((NotifLiveDataStoreImpl) this.notifDataStore).activeNotifCount.atomicValue.get()).intValue(), notificationEntry.row != null, NotificationLogger.getNotificationLocation(notificationEntry));
    }
}
