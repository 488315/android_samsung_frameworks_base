package com.android.systemui.statusbar.notification.icon.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.NotificationsKeyguardViewStateRepository;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationIconInteractor;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;

public final class NotificationIconsInteractor {
    public final ActiveNotificationsInteractor activeNotificationsInteractor;
    public final Optional bubbles;
    public final HeadsUpNotificationIconInteractor headsUpNotificationIconInteractor;
    public final NotificationsKeyguardViewStateRepository keyguardViewStateRepository;

    public NotificationIconsInteractor(ActiveNotificationsInteractor activeNotificationsInteractor, Optional<Bubbles> optional, HeadsUpNotificationIconInteractor headsUpNotificationIconInteractor, NotificationsKeyguardViewStateRepository notificationsKeyguardViewStateRepository, OngoingCallController ongoingCallController) {
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        this.bubbles = optional;
        this.headsUpNotificationIconInteractor = headsUpNotificationIconInteractor;
        this.keyguardViewStateRepository = notificationsKeyguardViewStateRepository;
    }
}
