package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda8;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface NotifLifetimeExtender {
    void cancelLifetimeExtension(NotificationEntry notificationEntry);

    String getName();

    boolean maybeExtendLifetime(NotificationEntry notificationEntry, int i);

    void setCallback(NotifCollection$$ExternalSyntheticLambda8 notifCollection$$ExternalSyntheticLambda8);
}
