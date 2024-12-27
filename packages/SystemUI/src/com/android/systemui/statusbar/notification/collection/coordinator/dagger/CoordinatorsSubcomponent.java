package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;

@CoordinatorScope
public interface CoordinatorsSubcomponent {

    public interface Factory {
        CoordinatorsSubcomponent create();
    }

    @Internal
    NotifCoordinators getNotifCoordinators();
}
