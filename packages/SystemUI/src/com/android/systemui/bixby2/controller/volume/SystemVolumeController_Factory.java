package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemVolumeController_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
