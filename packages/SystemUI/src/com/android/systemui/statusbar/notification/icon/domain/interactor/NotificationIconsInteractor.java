package com.android.systemui.statusbar.notification.icon.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.NotificationsKeyguardViewStateRepository;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationIconInteractor;
import com.android.systemui.statusbar.notification.promoted.domain.interactor.AODPromotedNotificationInteractor;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class NotificationIconsInteractor {
    public final ActiveNotificationsInteractor activeNotificationsInteractor;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 aodPromotedKeyToHide;
    public final Optional bubbles;
    public final HeadsUpNotificationIconInteractor headsUpNotificationIconInteractor;
    public final NotificationsKeyguardViewStateRepository keyguardViewStateRepository;

    public NotificationIconsInteractor(ActiveNotificationsInteractor activeNotificationsInteractor, Optional<Bubbles> optional, HeadsUpNotificationIconInteractor headsUpNotificationIconInteractor, AODPromotedNotificationInteractor aODPromotedNotificationInteractor, NotificationsKeyguardViewStateRepository notificationsKeyguardViewStateRepository, OngoingCallController ongoingCallController) {
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        this.bubbles = optional;
        this.headsUpNotificationIconInteractor = headsUpNotificationIconInteractor;
        this.keyguardViewStateRepository = notificationsKeyguardViewStateRepository;
        this.aodPromotedKeyToHide = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(aODPromotedNotificationInteractor.content, aODPromotedNotificationInteractor.isPresent, new NotificationIconsInteractor$aodPromotedKeyToHide$1(null));
    }

    public static FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 filteredNotifSet$default(NotificationIconsInteractor notificationIconsInteractor, boolean z, boolean z2, int i) {
        boolean z3 = (i & 1) == 0;
        boolean z4 = (i & 2) != 0;
        boolean z5 = (i & 4) != 0 ? true : z;
        boolean z6 = (i & 8) != 0;
        boolean z7 = (i & 16) != 0;
        boolean z8 = (i & 32) != 0 ? true : z2;
        return FlowKt.combine(notificationIconsInteractor.activeNotificationsInteractor.topLevelRepresentativeNotifications, notificationIconsInteractor.headsUpNotificationIconInteractor.isolatedNotification, (i & 64) != 0 ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null) : notificationIconsInteractor.aodPromotedKeyToHide, notificationIconsInteractor.keyguardViewStateRepository.areNotificationsFullyHidden, new NotificationIconsInteractor$filteredNotifSet$1(notificationIconsInteractor, z3, z4, z5, z6, z7, z8, null));
    }
}
