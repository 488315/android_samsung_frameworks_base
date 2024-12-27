package com.android.systemui.statusbar.notification.icon.domain.interactor;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

public final class AlwaysOnDisplayNotificationIconsInteractor {
    public final Flow aodNotifs;

    public AlwaysOnDisplayNotificationIconsInteractor(CoroutineContext coroutineContext, DeviceEntryInteractor deviceEntryInteractor, NotificationIconsInteractor notificationIconsInteractor) {
        this.aodNotifs = FlowKt.flowOn(FlowKt.transformLatest(deviceEntryInteractor.isBypassEnabled, new AlwaysOnDisplayNotificationIconsInteractor$special$$inlined$flatMapLatest$1(null, notificationIconsInteractor)), coroutineContext);
    }
}
