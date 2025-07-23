package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.internal.statusbar.NotificationVisibility;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DismissedByUserStats {
    public final int dismissalSentiment;
    public final int dismissalSurface;
    public final NotificationVisibility notificationVisibility;

    public DismissedByUserStats(int i, int i2, NotificationVisibility notificationVisibility) {
        this.dismissalSurface = i;
        this.dismissalSentiment = i2;
        this.notificationVisibility = notificationVisibility;
    }
}
