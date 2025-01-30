package com.android.systemui.statusbar.tv.notifications;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.util.SparseArray;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.NotificationListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvNotificationHandler implements CoreStartable, NotificationListener.NotificationHandler {
    public final NotificationListener mNotificationListener;
    public final SparseArray mNotifications = new SparseArray();
    public Listener mUpdateListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Listener {
    }

    public TvNotificationHandler(NotificationListener notificationListener) {
        this.mNotificationListener = notificationListener;
    }

    @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
    public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        if (new Notification.TvExtender(statusBarNotification.getNotification()).isAvailableOnTv()) {
            SparseArray sparseArray = this.mNotifications;
            sparseArray.put(statusBarNotification.getId(), statusBarNotification);
            Listener listener = this.mUpdateListener;
            if (listener != null) {
                ((TvNotificationPanelActivity) listener).notificationsUpdated(sparseArray);
            }
            Log.d("TvNotificationHandler", "Notification added");
        }
    }

    @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
    public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
        SparseArray sparseArray = this.mNotifications;
        if (sparseArray.contains(statusBarNotification.getId())) {
            sparseArray.remove(statusBarNotification.getId());
            Log.d("TvNotificationHandler", "Notification removed");
            Listener listener = this.mUpdateListener;
            if (listener != null) {
                ((TvNotificationPanelActivity) listener).notificationsUpdated(sparseArray);
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        NotificationListener notificationListener = this.mNotificationListener;
        notificationListener.addNotificationHandler(this);
        notificationListener.registerAsSystemService();
    }

    @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
    public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
    }

    @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
    public final void onNotificationsInitialized() {
    }
}
