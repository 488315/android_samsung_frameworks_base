package com.android.systemui.statusbar.notification.icon.domain.interactor;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AlwaysOnDisplayNotificationIconsInteractor {
    public final Flow aodNotifs;

    public AlwaysOnDisplayNotificationIconsInteractor(CoroutineContext coroutineContext, DeviceEntryInteractor deviceEntryInteractor, NotificationIconsInteractor notificationIconsInteractor) {
        this.aodNotifs = FlowKt.flowOn(FlowKt.transformLatest(deviceEntryInteractor.isBypassEnabled, new AlwaysOnDisplayNotificationIconsInteractor$special$$inlined$flatMapLatest$1(null, notificationIconsInteractor)), coroutineContext);
    }
}
