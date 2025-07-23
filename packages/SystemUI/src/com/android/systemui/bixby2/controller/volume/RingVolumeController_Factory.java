package com.android.systemui.bixby2.controller.volume;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RingVolumeController_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final RingVolumeController_Factory INSTANCE = new RingVolumeController_Factory();

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
