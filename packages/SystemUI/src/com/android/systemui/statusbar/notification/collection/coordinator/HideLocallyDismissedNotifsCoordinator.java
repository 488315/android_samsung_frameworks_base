package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public class HideLocallyDismissedNotifsCoordinator implements Coordinator {
    private final NotifFilter mFilter = new NotifFilter("HideLocallyDismissedNotifsFilter") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HideLocallyDismissedNotifsCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return notificationEntry.mDismissState != NotificationEntry.DismissState.NOT_DISMISSED;
        }
    };

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.mFilter);
    }
}
