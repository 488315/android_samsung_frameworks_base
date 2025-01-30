package com.android.systemui.statusbar.notification.collection.provider;

import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationVisibilityProviderImpl implements NotificationVisibilityProvider {
    public final CommonNotifCollection notifCollection;
    public final NotifLiveDataStore notifDataStore;

    public NotificationVisibilityProviderImpl(NotifLiveDataStore notifLiveDataStore, CommonNotifCollection commonNotifCollection) {
        this.notifDataStore = notifLiveDataStore;
        this.notifCollection = commonNotifCollection;
    }

    public final NotificationVisibility obtain(NotificationEntry notificationEntry) {
        int intValue = ((Number) ((NotifLiveDataStoreImpl) this.notifDataStore).activeNotifCount.getValue()).intValue();
        int rank = notificationEntry.mRanking.getRank();
        boolean z = notificationEntry.row != null;
        return NotificationVisibility.obtain(notificationEntry.mKey, rank, intValue, z, NotificationLogger.getNotificationLocation(notificationEntry));
    }
}
