package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

public abstract class NotifFilter extends Pluggable {
    public NotifFilter(String str) {
        super(str);
    }

    public abstract boolean shouldFilterOut(NotificationEntry notificationEntry, long j);
}
