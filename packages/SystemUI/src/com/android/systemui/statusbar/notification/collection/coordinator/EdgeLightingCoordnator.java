package com.android.systemui.statusbar.notification.collection.coordinator;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import kotlin.jvm.internal.Intrinsics;

public final class EdgeLightingCoordnator implements Coordinator {
    public static final int $stable = 8;
    private final NotifFilter secFGSFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.EdgeLightingCoordnator$secFGSFilter$1
        {
            super("SecFGSFilter");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            boolean disableDesktopLauncher;
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            if ((!"com.android.systemui".equals(statusBarNotification.getPackageName()) && !"com.samsung.android.app.cocktailbarservice".equals(statusBarNotification.getPackageName())) || notificationEntry.mRanking.getChannel() == null || !Intrinsics.areEqual(notificationEntry.mRanking.getChannel().getId(), "edge_lighting_chnnel_id") || (statusBarNotification.getNotification().flags & 64) == 0) {
                disableDesktopLauncher = EdgeLightingCoordnator.this.disableDesktopLauncher(notificationEntry);
                if (!disableDesktopLauncher) {
                    return false;
                }
            }
            return true;
        }
    };

    public final boolean disableDesktopLauncher(NotificationEntry notificationEntry) {
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        return "com.sec.android.app.desktoplauncher".equals(statusBarNotification.getPackageName()) && notificationEntry.mRanking.getChannel() != null && Intrinsics.areEqual(notificationEntry.mRanking.getChannel().getId(), "desktop_launcher_chnnel_id") && (statusBarNotification.getNotification().flags & 64) != 0;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.secFGSFilter);
    }

    private static /* synthetic */ void getSecFGSFilter$annotations() {
    }
}
