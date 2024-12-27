package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.internal.statusbar.NotificationVisibility;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
