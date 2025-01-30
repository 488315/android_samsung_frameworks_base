package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.internal.statusbar.NotificationVisibility;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
