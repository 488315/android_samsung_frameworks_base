package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RingVolumeController_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
