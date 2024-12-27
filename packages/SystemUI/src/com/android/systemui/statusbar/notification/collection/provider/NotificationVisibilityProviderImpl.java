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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
