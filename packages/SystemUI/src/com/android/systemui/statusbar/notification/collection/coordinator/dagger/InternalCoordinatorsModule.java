package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinatorsImpl;

/* loaded from: classes3.dex */
public abstract class InternalCoordinatorsModule {
    public static final int $stable = 0;

    @Internal
    public abstract NotifCoordinators bindNotifCoordinators(NotifCoordinatorsImpl notifCoordinatorsImpl);
}
