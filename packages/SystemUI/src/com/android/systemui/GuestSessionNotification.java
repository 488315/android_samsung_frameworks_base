package com.android.systemui;

import android.app.NotificationManager;
import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GuestSessionNotification {
    public final Context mContext;
    public final NotificationManager mNotificationManager;

    public GuestSessionNotification(Context context, NotificationManager notificationManager) {
        this.mContext = context;
        this.mNotificationManager = notificationManager;
    }
}
