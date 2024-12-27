package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BixbyVolumeController_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
