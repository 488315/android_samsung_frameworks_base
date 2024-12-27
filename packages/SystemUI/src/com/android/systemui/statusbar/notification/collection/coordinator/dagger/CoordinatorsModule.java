package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CoordinatorsModule {
    public static final int $stable = 0;
    public static final CoordinatorsModule INSTANCE = new CoordinatorsModule();

    private CoordinatorsModule() {
    }

    public static final NotifCoordinators notifCoordinators(CoordinatorsSubcomponent.Factory factory) {
        return factory.create().getNotifCoordinators();
    }
}
