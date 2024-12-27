package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface NotifDismissInterceptor {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnEndDismissInterception {
    }

    void cancelDismissInterception(NotificationEntry notificationEntry);

    String getName();

    void setCallback(OnEndDismissInterception onEndDismissInterception);

    boolean shouldInterceptDismissal(NotificationEntry notificationEntry);
}
