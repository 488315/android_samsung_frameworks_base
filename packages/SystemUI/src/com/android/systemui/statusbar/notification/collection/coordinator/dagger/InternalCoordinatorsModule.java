package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinatorsImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class InternalCoordinatorsModule {
    public static final int $stable = 0;

    @Internal
    public abstract NotifCoordinators bindNotifCoordinators(NotifCoordinatorsImpl notifCoordinatorsImpl);
}
