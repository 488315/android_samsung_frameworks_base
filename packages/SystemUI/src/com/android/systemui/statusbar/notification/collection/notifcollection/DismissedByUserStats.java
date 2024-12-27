package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.internal.statusbar.NotificationVisibility;

public final class DismissedByUserStats {
    public final int dismissalSentiment;
    public final int dismissalSurface;
    public final NotificationVisibility notificationVisibility;

    public DismissedByUserStats(int i, int i2, NotificationVisibility notificationVisibility) {
        this.dismissalSurface = i;
        this.dismissalSentiment = i2;
        this.notificationVisibility = notificationVisibility;
    }
}
