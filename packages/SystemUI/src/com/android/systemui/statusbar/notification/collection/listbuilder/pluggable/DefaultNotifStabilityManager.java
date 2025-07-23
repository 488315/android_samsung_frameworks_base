package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DefaultNotifStabilityManager extends NotifStabilityManager {
    public static final DefaultNotifStabilityManager INSTANCE = new DefaultNotifStabilityManager();

    private DefaultNotifStabilityManager() {
        super("DefaultNotifStabilityManager");
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public final boolean isEntryReorderingAllowed(PipelineEntry pipelineEntry) {
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
