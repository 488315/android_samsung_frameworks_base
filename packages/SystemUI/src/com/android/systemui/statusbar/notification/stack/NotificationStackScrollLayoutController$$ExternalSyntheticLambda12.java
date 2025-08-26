package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationStackScrollLayoutController$$ExternalSyntheticLambda12 implements VisibilityLocationProvider {
    public final /* synthetic */ NotificationStackScrollLayoutController f$0;

    public /* synthetic */ NotificationStackScrollLayoutController$$ExternalSyntheticLambda12(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.f$0 = notificationStackScrollLayoutController;
    }

    @Override // com.android.systemui.statusbar.notification.VisibilityLocationProvider
    public final boolean isInVisibleLocation(NotificationEntry notificationEntry) {
        NotificationStackScrollLayoutController.AnonymousClass4 anonymousClass4 = NotificationStackScrollLayoutController.HIDE_ALPHA_PROPERTY;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        return (expandableNotificationRow == null || (expandableNotificationRow.mViewState.location & 5) == 0 || expandableNotificationRow.getVisibility() != 0) ? false : true;
    }
}
