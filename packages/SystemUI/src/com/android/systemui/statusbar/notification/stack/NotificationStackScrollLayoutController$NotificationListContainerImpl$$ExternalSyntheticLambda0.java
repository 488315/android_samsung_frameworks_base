package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.GroupHunAnimationFix;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.Iterator;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ NotificationStackScrollLayoutController.NotificationListContainerImpl f$0;
    public final /* synthetic */ ExpandableNotificationRow f$1;

    public /* synthetic */ NotificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0(NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl, ExpandableNotificationRow expandableNotificationRow) {
        this.f$0 = notificationListContainerImpl;
        this.f$1 = expandableNotificationRow;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = this.f$0;
        ExpandableNotificationRow expandableNotificationRow = this.f$1;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
        notificationStackScrollLayoutController.mHeadsUpAppearanceController.updateHeader(expandableNotificationRow);
        notificationStackScrollLayoutController.mHeadsUpAppearanceController.updateHeadsUpAndPulsingRoundness(expandableNotificationRow);
        int i = GroupHunAnimationFix.$r8$clinit;
        if (((Boolean) obj).booleanValue()) {
            return;
        }
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        Iterator it = ((HeadsUpManagerImpl) notificationStackScrollLayoutController.mHeadsUpManager).mListeners.iterator();
        while (it.hasNext()) {
            ((OnHeadsUpChangedListener) it.next()).onHeadsUpAnimatingAwayEnded(notificationEntry);
        }
    }
}
