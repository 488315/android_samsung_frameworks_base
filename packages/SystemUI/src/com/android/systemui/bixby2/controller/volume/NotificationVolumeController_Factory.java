package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NotificationVolumeController_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final NotificationVolumeController_Factory INSTANCE = new NotificationVolumeController_Factory();

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
