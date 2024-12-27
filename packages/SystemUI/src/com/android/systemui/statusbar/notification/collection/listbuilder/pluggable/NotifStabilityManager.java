package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class NotifStabilityManager extends Pluggable {
    public static final int $stable = 0;

    public NotifStabilityManager(String str) {
        super(str);
    }

    public abstract boolean isEntryReorderingAllowed(ListEntry listEntry);

    public abstract boolean isEveryChangeAllowed();

    public abstract boolean isGroupChangeAllowed(NotificationEntry notificationEntry);

    public abstract boolean isGroupPruneAllowed(GroupEntry groupEntry);

    public abstract boolean isPipelineRunAllowed();

    public abstract boolean isSectionChangeAllowed(NotificationEntry notificationEntry);

    public abstract void onBeginRun();

    public abstract void onEntryReorderSuppressed();
}
