package com.android.systemui.statusbar.notification.icon.domain.interactor;

import com.android.systemui.statusbar.data.repository.NotificationListenerSettingsRepository;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class StatusBarNotificationIconsInteractor {
    public final Flow statusBarNotifs;

    public StatusBarNotificationIconsInteractor(CoroutineContext coroutineContext, NotificationIconsInteractor notificationIconsInteractor, NotificationListenerSettingsRepository notificationListenerSettingsRepository) {
        this.statusBarNotifs = FlowKt.flowOn(FlowKt.transformLatest(notificationListenerSettingsRepository.showSilentStatusIcons, new StatusBarNotificationIconsInteractor$special$$inlined$flatMapLatest$1(null, notificationIconsInteractor)), coroutineContext);
    }
}
