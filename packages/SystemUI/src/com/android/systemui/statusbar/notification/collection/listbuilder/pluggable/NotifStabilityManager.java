package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class NotifStabilityManager extends Pluggable {
    public static final int $stable = 0;

    public NotifStabilityManager(String str) {
        super(str);
    }

    public abstract boolean isEntryReorderingAllowed(PipelineEntry pipelineEntry);

    public abstract boolean isEveryChangeAllowed();

    public abstract boolean isGroupChangeAllowed(NotificationEntry notificationEntry);

    public abstract boolean isGroupPruneAllowed(GroupEntry groupEntry);

    public abstract boolean isPipelineRunAllowed();

    public abstract boolean isSectionChangeAllowed(NotificationEntry notificationEntry);

    public abstract void onBeginRun();

    public abstract void onEntryReorderSuppressed();
}
