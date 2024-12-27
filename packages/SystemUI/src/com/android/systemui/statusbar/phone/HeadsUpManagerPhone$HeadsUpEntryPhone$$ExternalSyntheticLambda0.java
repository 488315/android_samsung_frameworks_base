package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.util.ListenerSet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class HeadsUpManagerPhone$HeadsUpEntryPhone$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ HeadsUpManagerPhone.HeadsUpEntryPhone f$0;
    public final /* synthetic */ NotificationEntry f$1;

    public /* synthetic */ HeadsUpManagerPhone$HeadsUpEntryPhone$$ExternalSyntheticLambda0(HeadsUpManagerPhone.HeadsUpEntryPhone headsUpEntryPhone, NotificationEntry notificationEntry) {
        this.f$0 = headsUpEntryPhone;
        this.f$1 = notificationEntry;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ExpandableNotificationRow expandableNotificationRow;
        HeadsUpManagerPhone.HeadsUpEntryPhone headsUpEntryPhone = this.f$0;
        NotificationEntry notificationEntry = this.f$1;
        if (HeadsUpManagerPhone.this.mVisualStabilityProvider.isReorderingAllowed || ((expandableNotificationRow = notificationEntry.row) != null && expandableNotificationRow.showingPulsing())) {
            HeadsUpManagerPhone headsUpManagerPhone = HeadsUpManagerPhone.this;
            if (headsUpManagerPhone.mTrackingHeadsUp) {
                headsUpManagerPhone.mEntriesToRemoveAfterExpand.add(notificationEntry);
                return;
            } else {
                headsUpManagerPhone.removeEntry(notificationEntry.mKey, "createRemoveRunnable");
                return;
            }
        }
        HeadsUpManagerPhone.this.mEntriesToRemoveWhenReorderingAllowed.add(notificationEntry);
        HeadsUpManagerPhone headsUpManagerPhone2 = HeadsUpManagerPhone.this;
        VisualStabilityProvider visualStabilityProvider = headsUpManagerPhone2.mVisualStabilityProvider;
        ListenerSet listenerSet = visualStabilityProvider.allListeners;
        HeadsUpManagerPhone$$ExternalSyntheticLambda0 headsUpManagerPhone$$ExternalSyntheticLambda0 = headsUpManagerPhone2.mOnReorderingAllowedListener;
        if (listenerSet.addIfAbsent(headsUpManagerPhone$$ExternalSyntheticLambda0)) {
            visualStabilityProvider.temporaryListeners.add(headsUpManagerPhone$$ExternalSyntheticLambda0);
        }
    }
}
