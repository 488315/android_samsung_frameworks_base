package com.android.systemui.statusbar.notification.row.dagger;

import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ExpandableNotificationRowComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Builder {
        ExpandableNotificationRowComponent build();

        Builder expandableNotificationRow(ExpandableNotificationRow expandableNotificationRow);

        Builder listContainer(NotificationListContainer notificationListContainer);

        Builder notificationEntry(NotificationEntry notificationEntry);

        Builder onExpandClickListener(NotificationPresenter notificationPresenter);
    }

    ExpandableNotificationRowController getExpandableNotificationRowController();
}
