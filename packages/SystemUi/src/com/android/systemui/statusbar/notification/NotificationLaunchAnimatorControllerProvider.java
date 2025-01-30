package com.android.systemui.statusbar.notification;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationLaunchAnimatorControllerProvider {
    public final HeadsUpManagerPhone headsUpManager;
    public final InteractionJankMonitor jankMonitor;
    public final NotificationListContainer notificationListContainer;
    public final NotificationShadeWindowViewController notificationShadeWindowViewController;

    public NotificationLaunchAnimatorControllerProvider(NotificationShadeWindowViewController notificationShadeWindowViewController, NotificationListContainer notificationListContainer, HeadsUpManagerPhone headsUpManagerPhone, InteractionJankMonitor interactionJankMonitor) {
        this.notificationShadeWindowViewController = notificationShadeWindowViewController;
        this.notificationListContainer = notificationListContainer;
        this.headsUpManager = headsUpManagerPhone;
        this.jankMonitor = interactionJankMonitor;
    }

    public final NotificationLaunchAnimatorController getAnimatorController(ExpandableNotificationRow expandableNotificationRow, Runnable runnable) {
        return new NotificationLaunchAnimatorController(this.notificationShadeWindowViewController, this.notificationListContainer, this.headsUpManager, expandableNotificationRow, this.jankMonitor, runnable);
    }
}
