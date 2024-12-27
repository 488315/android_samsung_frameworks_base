package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class InvalidVolumeController_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
