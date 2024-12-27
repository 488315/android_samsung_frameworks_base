package com.android.systemui;

import android.app.NotificationManager;
import android.content.Context;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GuestSessionNotification {
    public final Context mContext;
    public final NotificationManager mNotificationManager;

    public GuestSessionNotification(Context context, NotificationManager notificationManager) {
        this.mContext = context;
        this.mNotificationManager = notificationManager;
    }
}
