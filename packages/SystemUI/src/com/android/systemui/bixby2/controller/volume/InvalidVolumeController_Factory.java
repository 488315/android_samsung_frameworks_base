package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

public final class InvalidVolumeController_Factory implements Provider {

    final class InstanceHolder {
        private static final InvalidVolumeController_Factory INSTANCE = new InvalidVolumeController_Factory();

        private InstanceHolder() {
        }
    }

    public static InvalidVolumeController_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static InvalidVolumeController newInstance() {
        return new InvalidVolumeController();
    }

    @Override // javax.inject.Provider
    public InvalidVolumeController get() {
        return newInstance();
    }
}
