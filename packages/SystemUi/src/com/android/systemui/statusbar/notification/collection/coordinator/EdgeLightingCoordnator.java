package com.android.systemui.statusbar.notification.collection.coordinator;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EdgeLightingCoordnator implements Coordinator {
    public final EdgeLightingCoordnator$secFGSFilter$1 secFGSFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.EdgeLightingCoordnator$secFGSFilter$1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            return (Intrinsics.areEqual("com.android.systemui", statusBarNotification.getPackageName()) || Intrinsics.areEqual("com.samsung.android.app.cocktailbarservice", statusBarNotification.getPackageName())) && notificationEntry.getChannel() != null && Intrinsics.areEqual(notificationEntry.getChannel().getId(), "edge_lighting_chnnel_id") && (statusBarNotification.getNotification().flags & 64) != 0;
        }
    };

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.secFGSFilter);
    }
}
