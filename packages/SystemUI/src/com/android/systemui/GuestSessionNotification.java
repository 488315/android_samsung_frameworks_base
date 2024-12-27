package com.android.systemui;

import android.app.NotificationManager;
import android.content.Context;

public final class GuestSessionNotification {
    public final Context mContext;
    public final NotificationManager mNotificationManager;

    public GuestSessionNotification(Context context, NotificationManager notificationManager) {
        this.mContext = context;
        this.mNotificationManager = notificationManager;
    }
}
