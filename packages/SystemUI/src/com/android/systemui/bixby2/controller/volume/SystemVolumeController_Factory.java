package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

public final class SystemVolumeController_Factory implements Provider {

    final class InstanceHolder {
        private static final SystemVolumeController_Factory INSTANCE = new SystemVolumeController_Factory();

        private InstanceHolder() {
        }
    }

    public static SystemVolumeController_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static SystemVolumeController newInstance() {
        return new SystemVolumeController();
    }

    @Override // javax.inject.Provider
    public SystemVolumeController get() {
        return newInstance();
    }
}
