package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.SparseArray;
import android.widget.RemoteViews;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.Map;

/* loaded from: classes3.dex */
public class NotifRemoteViewCacheImpl implements NotifRemoteViewCache {
    public final AnonymousClass1 mCollectionListener;
    public final Map mNotifCachedContentViews = new ArrayMap();

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener, com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl$1] */
    public NotifRemoteViewCacheImpl(CommonNotifCollection commonNotifCollection) {
        ?? r0 = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryCleanUp(NotificationEntry notificationEntry) {
                ((ArrayMap) NotifRemoteViewCacheImpl.this.mNotifCachedContentViews).remove(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryInit(NotificationEntry notificationEntry) {
                ((ArrayMap) NotifRemoteViewCacheImpl.this.mNotifCachedContentViews).put(notificationEntry, new SparseArray());
            }
        };
        this.mCollectionListener = r0;
        ((NotifPipeline) commonNotifCollection).addCollectionListener(r0);
    }

    public final RemoteViews getCachedView(NotificationEntry notificationEntry, int i) {
        SparseArray sparseArray = (SparseArray) ((ArrayMap) this.mNotifCachedContentViews).get(notificationEntry);
        if (sparseArray == null) {
            return null;
        }
        return (RemoteViews) sparseArray.get(i);
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
