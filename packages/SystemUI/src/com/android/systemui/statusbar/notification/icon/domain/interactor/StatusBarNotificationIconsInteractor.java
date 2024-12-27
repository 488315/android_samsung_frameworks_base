package com.android.systemui.statusbar.notification.icon.domain.interactor;

import com.android.systemui.statusbar.data.repository.NotificationListenerSettingsRepository;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarNotificationIconsInteractor {
    public final Flow statusBarNotifs;

    public StatusBarNotificationIconsInteractor(CoroutineContext coroutineContext, NotificationIconsInteractor notificationIconsInteractor, NotificationListenerSettingsRepository notificationListenerSettingsRepository) {
        this.statusBarNotifs = FlowKt.flowOn(FlowKt.transformLatest(notificationListenerSettingsRepository.showSilentStatusIcons, new StatusBarNotificationIconsInteractor$special$$inlined$flatMapLatest$1(null, notificationIconsInteractor)), coroutineContext);
    }
}
