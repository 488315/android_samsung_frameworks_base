package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.render.NodeController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public final class OngoingActivityCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final Context context;
    private final NodeController ongoingActivityHeaderController;
    private final NotifTimeSortCoordnator timeSortCoordnator;
    private final NotifFilter ongoingSummary = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OngoingActivityCoordinator$ongoingSummary$1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return (notificationEntry.isOngoingAcitivty() && notificationEntry.isPromotedState() && notificationEntry.mSbn.getNotification().isGroupSummary()) || notificationEntry.mSbn.getNotification().extras.getInt("android.ongoingActivityNoti.style", 0) == 2;
        }
    };
    private final OngoingActivityCoordinator$ongoingActivitySectioner$1 ongoingActivitySectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OngoingActivityCoordinator$ongoingActivitySectioner$1
        {
            super("OngoingActivity", 3);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NotifComparator getComparator() {
            return OngoingActivityCoordinator.this.getTimeSortCoordnator().getTimeComparator();
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            return OngoingActivityCoordinator.this.getOngoingActivityHeaderController();
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            NotificationEntry representativeEntry;
            NotificationEntry representativeEntry2 = listEntry.getRepresentativeEntry();
            return representativeEntry2 != null && representativeEntry2.isOngoingAcitivty() && (representativeEntry = listEntry.getRepresentativeEntry()) != null && representativeEntry.isPromotedState();
        }
    };

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.OngoingActivityCoordinator$ongoingActivitySectioner$1] */
    public OngoingActivityCoordinator(Context context, NotifTimeSortCoordnator notifTimeSortCoordnator, NodeController nodeController) {
        this.context = context;
        this.timeSortCoordnator = notifTimeSortCoordnator;
        this.ongoingActivityHeaderController = nodeController;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addFinalizeFilter(this.ongoingSummary);
    }

    public final Context getContext() {
        return this.context;
    }

    public final NodeController getOngoingActivityHeaderController() {
        return this.ongoingActivityHeaderController;
    }

    public final NotifSectioner getOngoingActivitySectioner() {
        return this.ongoingActivitySectioner;
    }

    public final NotifTimeSortCoordnator getTimeSortCoordnator() {
        return this.timeSortCoordnator;
    }
}
