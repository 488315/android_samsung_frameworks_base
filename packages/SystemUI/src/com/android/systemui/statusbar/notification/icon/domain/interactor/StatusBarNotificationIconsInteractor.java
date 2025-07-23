package com.android.systemui.statusbar.notification.icon.domain.interactor;

import com.android.systemui.statusbar.data.repository.NotificationListenerSettingsRepository;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarNotificationIconsInteractor {
    public final Flow statusBarNotifs;

    public StatusBarNotificationIconsInteractor(CoroutineContext coroutineContext, NotificationIconsInteractor notificationIconsInteractor, NotificationListenerSettingsRepository notificationListenerSettingsRepository) {
        this.statusBarNotifs = FlowKt.flowOn(FlowKt.transformLatest(notificationListenerSettingsRepository.showSilentStatusIcons, new StatusBarNotificationIconsInteractor$special$$inlined$flatMapLatest$1(null, notificationIconsInteractor)), coroutineContext);
    }
}
