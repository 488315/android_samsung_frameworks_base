package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NotifStabilityManager extends Pluggable {
    public NotifStabilityManager(String str) {
        super(str);
    }

    public abstract boolean isEntryReorderingAllowed(ListEntry listEntry);

    public abstract boolean isEveryChangeAllowed();

    public abstract void isGroupChangeAllowed();

    public abstract boolean isGroupPruneAllowed();

    public abstract boolean isPipelineRunAllowed();

    public abstract boolean isSectionChangeAllowed(NotificationEntry notificationEntry);

    public abstract void onBeginRun();

    public abstract void onEntryReorderSuppressed();
}
