package com.android.systemui.statusbar.notification;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationLaunchAnimationInteractor;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.policy.HeadsUpManager;

public final class NotificationLaunchAnimatorControllerProvider {
    public final HeadsUpManager headsUpManager;
    public final InteractionJankMonitor jankMonitor;
    public final NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor;
    public final NotificationListContainer notificationListContainer;

    public NotificationLaunchAnimatorControllerProvider(NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor, NotificationListContainer notificationListContainer, HeadsUpManager headsUpManager, InteractionJankMonitor interactionJankMonitor) {
        this.notificationLaunchAnimationInteractor = notificationLaunchAnimationInteractor;
        this.notificationListContainer = notificationListContainer;
        this.headsUpManager = headsUpManager;
        this.jankMonitor = interactionJankMonitor;
    }

    public final NotificationTransitionAnimatorController getAnimatorController(ExpandableNotificationRow expandableNotificationRow, Runnable runnable) {
        return new NotificationTransitionAnimatorController(this.notificationLaunchAnimationInteractor, this.notificationListContainer, this.headsUpManager, expandableNotificationRow, this.jankMonitor, runnable);
    }
}
