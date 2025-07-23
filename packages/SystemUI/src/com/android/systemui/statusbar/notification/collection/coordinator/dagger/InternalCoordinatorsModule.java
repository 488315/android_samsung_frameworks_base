package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinatorsImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class InternalCoordinatorsModule {
    public static final int $stable = 0;

    @Internal
    public abstract NotifCoordinators bindNotifCoordinators(NotifCoordinatorsImpl notifCoordinatorsImpl);
}
