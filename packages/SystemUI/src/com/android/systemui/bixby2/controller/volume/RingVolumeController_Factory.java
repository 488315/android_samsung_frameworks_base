package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

public final class RingVolumeController_Factory implements Provider {

    final class InstanceHolder {
        private static final RingVolumeController_Factory INSTANCE = new RingVolumeController_Factory();

        private InstanceHolder() {
        }
    }

    public static RingVolumeController_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static RingVolumeController newInstance() {
        return new RingVolumeController();
    }

    @Override // javax.inject.Provider
    public RingVolumeController get() {
        return newInstance();
    }
}
