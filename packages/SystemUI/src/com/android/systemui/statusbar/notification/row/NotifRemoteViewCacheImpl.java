package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.SparseArray;
import android.widget.RemoteViews;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifRemoteViewCacheImpl implements NotifRemoteViewCache {
    public final AnonymousClass1 mCollectionListener;
    public final Map mNotifCachedContentViews = new ArrayMap();

    public NotifRemoteViewCacheImpl(CommonNotifCollection commonNotifCollection) {
        ((NotifPipeline) commonNotifCollection).addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryCleanUp(NotificationEntry notificationEntry) {
                ((ArrayMap) NotifRemoteViewCacheImpl.this.mNotifCachedContentViews).remove(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryInit(NotificationEntry notificationEntry) {
                ((ArrayMap) NotifRemoteViewCacheImpl.this.mNotifCachedContentViews).put(notificationEntry, new SparseArray());
            }
        });
    }

    public final RemoteViews getCachedView(NotificationEntry notificationEntry, int i) {
        SparseArray sparseArray = (SparseArray) ((ArrayMap) this.mNotifCachedContentViews).get(notificationEntry);
        if (sparseArray == null) {
            return null;
        }
        return (RemoteViews) sparseArray.get(i);
    }

    public final boolean hasCachedView(NotificationEntry notificationEntry, int i) {
        return getCachedView(notificationEntry, i) != null;
    }

    public final void putCachedView(NotificationEntry notificationEntry, int i, RemoteViews remoteViews) {
        SparseArray sparseArray = (SparseArray) ((ArrayMap) this.mNotifCachedContentViews).get(notificationEntry);
        if (sparseArray == null) {
            return;
        }
        sparseArray.put(i, remoteViews);
    }

    public final void removeCachedView(NotificationEntry notificationEntry, int i) {
        SparseArray sparseArray = (SparseArray) ((ArrayMap) this.mNotifCachedContentViews).get(notificationEntry);
        if (sparseArray == null) {
            return;
        }
        sparseArray.remove(i);
    }
}
