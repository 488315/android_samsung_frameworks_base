package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class NotifFilter extends Pluggable {
    public NotifFilter(String str) {
        super(str);
    }

    public abstract boolean shouldFilterOut(NotificationEntry notificationEntry, long j);
}
