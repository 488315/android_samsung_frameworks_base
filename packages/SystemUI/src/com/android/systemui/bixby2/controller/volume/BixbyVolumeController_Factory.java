package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

public final class BixbyVolumeController_Factory implements Provider {

    final class InstanceHolder {
        private static final BixbyVolumeController_Factory INSTANCE = new BixbyVolumeController_Factory();

        private InstanceHolder() {
        }
    }

    public static BixbyVolumeController_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static BixbyVolumeController newInstance() {
        return new BixbyVolumeController();
    }

    @Override // javax.inject.Provider
    public BixbyVolumeController get() {
        return newInstance();
    }
}
