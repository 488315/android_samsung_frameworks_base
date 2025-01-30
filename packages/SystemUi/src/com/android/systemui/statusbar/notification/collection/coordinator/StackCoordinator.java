package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.provider.SeenNotificationsProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StackCoordinator implements Coordinator {
    public final NotificationIconAreaController notificationIconAreaController;

    public StackCoordinator(GroupExpansionManagerImpl groupExpansionManagerImpl, NotificationIconAreaController notificationIconAreaController) {
        this.notificationIconAreaController = notificationIconAreaController;
    }

    public static NotifStats calculateNotifStats(List list) {
        Iterator it = list.iterator();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            NotifSection section = listEntry.getSection();
            if (section == null) {
                throw new IllegalStateException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Null section for ", listEntry.getKey()).toString());
            }
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry == null) {
                throw new IllegalStateException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Null notif entry for ", listEntry.getKey()).toString());
            }
            boolean z5 = section.bucket == 9;
            boolean isClearable = representativeEntry.isClearable();
            if (z5 && isClearable) {
                z4 = true;
            } else if (z5 && !isClearable) {
                z3 = true;
            } else if (!z5 && isClearable) {
                z2 = true;
            } else if (!z5 && !isClearable) {
                z = true;
            }
        }
        return new NotifStats(list.size(), z, z2, z3, z4);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderListListeners).add(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.StackCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List list, NotifStackController notifStackController) {
                StackCoordinator stackCoordinator = StackCoordinator.this;
                stackCoordinator.getClass();
                boolean isTagEnabled = Trace.isTagEnabled(4096L);
                NotificationIconAreaController notificationIconAreaController = stackCoordinator.notificationIconAreaController;
                if (!isTagEnabled) {
                    NotifStats calculateNotifStats = StackCoordinator.calculateNotifStats(list);
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                    notificationStackScrollLayoutController.mNotifStats = calculateNotifStats;
                    notificationStackScrollLayoutController.mView.mHasFilteredOutSeenNotifications = ((SeenNotificationsProviderImpl) notificationStackScrollLayoutController.mSeenNotificationsProvider).hasFilteredOutSeenNotifications;
                    notificationStackScrollLayoutController.updateFooter();
                    notificationStackScrollLayoutController.updateShowEmptyShadeView();
                    notificationStackScrollLayoutController.mShelfManager.updateClearAllOnShelf();
                    notificationIconAreaController.updateNotificationIcons(list);
                    return;
                }
                Trace.traceBegin(4096L, "StackCoordinator.onAfterRenderList");
                try {
                    NotifStats calculateNotifStats2 = StackCoordinator.calculateNotifStats(list);
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
                    notificationStackScrollLayoutController2.mNotifStats = calculateNotifStats2;
                    notificationStackScrollLayoutController2.mView.mHasFilteredOutSeenNotifications = ((SeenNotificationsProviderImpl) notificationStackScrollLayoutController2.mSeenNotificationsProvider).hasFilteredOutSeenNotifications;
                    notificationStackScrollLayoutController2.updateFooter();
                    notificationStackScrollLayoutController2.updateShowEmptyShadeView();
                    notificationStackScrollLayoutController2.mShelfManager.updateClearAllOnShelf();
                    notificationIconAreaController.updateNotificationIcons(list);
                    Unit unit = Unit.INSTANCE;
                } finally {
                    Trace.traceEnd(4096L);
                }
            }
        });
    }
}
