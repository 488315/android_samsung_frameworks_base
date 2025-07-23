package com.android.systemui.statusbar.notification;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationLaunchAnimationInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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

    public final NotificationTransitionAnimatorController getAnimatorController(ExpandableNotificationRow expandableNotificationRow) {
        return new NotificationTransitionAnimatorController(this.notificationLaunchAnimationInteractor, this.notificationListContainer, this.headsUpManager, expandableNotificationRow, this.jankMonitor, null);
    }
}
