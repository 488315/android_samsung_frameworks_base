package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class InvalidVolumeController_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final InvalidVolumeController_Factory INSTANCE = new InvalidVolumeController_Factory();

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
