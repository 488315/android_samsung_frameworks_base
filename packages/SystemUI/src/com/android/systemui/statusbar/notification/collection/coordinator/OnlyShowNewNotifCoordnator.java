package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OnlyShowNewNotifCoordnator extends Invalidator implements Coordinator {
    public static final int $stable = 8;
    private final NotificationPanelViewController notificationPanelViewController;

    public OnlyShowNewNotifCoordnator(NotificationPanelViewController notificationPanelViewController) {
        super("NotilusCoordinator");
        this.notificationPanelViewController = notificationPanelViewController;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreRenderInvalidator(this);
        this.notificationPanelViewController.mNewNotifReadListener = new NotificationPanelViewController.NewNotifReadListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OnlyShowNewNotifCoordnator$attach$1
            @Override // com.android.systemui.shade.NotificationPanelViewController.NewNotifReadListener
            public final void onNewNotificationRead() {
                OnlyShowNewNotifCoordnator.this.updateNewNotifRead();
            }
        };
    }

    public final NotificationPanelViewController getNotificationPanelViewController() {
        return this.notificationPanelViewController;
    }

    public final void updateNewNotifRead() {
        invalidateList("updateNewNotifRead");
    }
}
