package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;

/* loaded from: classes3.dex */
public final class ReferenceCoordinatorsModule {
    public static final int $stable = 0;
    public static final ReferenceCoordinatorsModule INSTANCE = new ReferenceCoordinatorsModule();

    private ReferenceCoordinatorsModule() {
    }

    public static final NotifCoordinators notifCoordinators(CoordinatorsSubcomponent.Factory factory) {
        return factory.create().getNotifCoordinators();
    }
}
