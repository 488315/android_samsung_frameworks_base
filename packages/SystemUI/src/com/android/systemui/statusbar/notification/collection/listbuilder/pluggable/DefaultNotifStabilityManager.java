package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

public final class DefaultNotifStabilityManager extends NotifStabilityManager {
    public static final DefaultNotifStabilityManager INSTANCE = new DefaultNotifStabilityManager();

    private DefaultNotifStabilityManager() {
        super("DefaultNotifStabilityManager");
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public final boolean isEntryReorderingAllowed(ListEntry listEntry) {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public final boolean isEveryChangeAllowed() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public final boolean isGroupChangeAllowed(NotificationEntry notificationEntry) {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public final boolean isGroupPruneAllowed(GroupEntry groupEntry) {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public final boolean isPipelineRunAllowed() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public final boolean isSectionChangeAllowed(NotificationEntry notificationEntry) {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public final void onBeginRun() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public final void onEntryReorderSuppressed() {
    }
}
