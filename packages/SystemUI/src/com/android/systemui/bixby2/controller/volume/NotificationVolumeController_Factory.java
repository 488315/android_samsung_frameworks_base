package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationVolumeController_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
