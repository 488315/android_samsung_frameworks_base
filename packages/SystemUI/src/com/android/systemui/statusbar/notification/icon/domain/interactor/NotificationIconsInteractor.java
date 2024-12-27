package com.android.systemui.statusbar.notification.icon.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.NotificationsKeyguardViewStateRepository;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationIconInteractor;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
