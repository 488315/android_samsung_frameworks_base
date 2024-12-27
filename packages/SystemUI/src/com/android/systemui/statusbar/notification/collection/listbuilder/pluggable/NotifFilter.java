package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class NotifFilter extends Pluggable {
    public NotifFilter(String str) {
        super(str);
    }

    public abstract boolean shouldFilterOut(NotificationEntry notificationEntry, long j);
}
