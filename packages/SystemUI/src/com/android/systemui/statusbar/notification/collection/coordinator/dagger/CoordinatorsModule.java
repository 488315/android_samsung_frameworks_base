package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;

public final class CoordinatorsModule {
    public static final int $stable = 0;
    public static final CoordinatorsModule INSTANCE = new CoordinatorsModule();

    private CoordinatorsModule() {
    }

    public static final NotifCoordinators notifCoordinators(CoordinatorsSubcomponent.Factory factory) {
        return factory.create().getNotifCoordinators();
    }
}
