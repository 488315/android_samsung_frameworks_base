package com.android.systemui.statusbar.notification.icon.domain.interactor;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AlwaysOnDisplayNotificationIconsInteractor {
    public final Flow aodNotifs;

    public AlwaysOnDisplayNotificationIconsInteractor(CoroutineContext coroutineContext, DeviceEntryInteractor deviceEntryInteractor, NotificationIconsInteractor notificationIconsInteractor) {
        this.aodNotifs = FlowKt.flowOn(FlowKt.transformLatest(deviceEntryInteractor.isBypassEnabled, new AlwaysOnDisplayNotificationIconsInteractor$special$$inlined$flatMapLatest$1(null, notificationIconsInteractor)), coroutineContext);
    }
}
