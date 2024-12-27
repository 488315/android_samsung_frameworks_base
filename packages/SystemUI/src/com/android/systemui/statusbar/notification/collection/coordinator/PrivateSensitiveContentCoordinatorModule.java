package com.android.systemui.statusbar.notification.collection.coordinator;

public interface PrivateSensitiveContentCoordinatorModule {
    SensitiveContentCoordinator bindCoordinator(SensitiveContentCoordinatorImpl sensitiveContentCoordinatorImpl);
}
