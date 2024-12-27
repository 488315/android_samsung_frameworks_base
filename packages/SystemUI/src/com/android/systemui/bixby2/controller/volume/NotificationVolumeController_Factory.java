package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

public final class NotificationVolumeController_Factory implements Provider {

    final class InstanceHolder {
        private static final NotificationVolumeController_Factory INSTANCE = new NotificationVolumeController_Factory();

        private InstanceHolder() {
        }
    }

    public static NotificationVolumeController_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static NotificationVolumeController newInstance() {
        return new NotificationVolumeController();
    }

    @Override // javax.inject.Provider
    public NotificationVolumeController get() {
        return newInstance();
    }
}
