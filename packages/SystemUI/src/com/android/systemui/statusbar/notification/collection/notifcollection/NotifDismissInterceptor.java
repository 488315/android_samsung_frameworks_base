package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface NotifDismissInterceptor {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnEndDismissInterception {
    }

    void cancelDismissInterception(NotificationEntry notificationEntry);

    String getName();

    void setCallback(OnEndDismissInterception onEndDismissInterception);

    boolean shouldInterceptDismissal(NotificationEntry notificationEntry);
}
