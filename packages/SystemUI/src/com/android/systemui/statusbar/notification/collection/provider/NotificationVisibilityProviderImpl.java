package com.android.systemui.statusbar.notification.collection.provider;

import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;

/* loaded from: classes3.dex */
public final class NotificationVisibilityProviderImpl implements NotificationVisibilityProvider {
    public final ActiveNotificationsInteractor activeNotificationsInteractor;
    public final CommonNotifCollection notifCollection;

    public NotificationVisibilityProviderImpl(ActiveNotificationsInteractor activeNotificationsInteractor, NotifLiveDataStore notifLiveDataStore, CommonNotifCollection commonNotifCollection) {
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        this.notifCollection = commonNotifCollection;
    }

    public final NotificationVisibility obtain(String str) {
        NotificationEntry entry = ((NotifPipeline) this.notifCollection).mNotifCollection.getEntry(str);
        return entry != null ? obtain(entry) : NotificationVisibility.obtain(str, -1, this.activeNotificationsInteractor.getAllNotificationsCountValue(), false);
    }

    public final NotificationVisibility obtain(NotificationEntry notificationEntry) {
        return NotificationVisibility.obtain(notificationEntry.mKey, notificationEntry.mRanking.getRank(), this.activeNotificationsInteractor.getAllNotificationsCountValue(), notificationEntry.row != null, NotificationLogger.getNotificationLocation(notificationEntry));
    }
}
