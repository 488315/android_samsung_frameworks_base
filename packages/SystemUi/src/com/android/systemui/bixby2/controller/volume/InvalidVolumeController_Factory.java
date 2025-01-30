package com.android.systemui.bixby2.controller.volume;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class InvalidVolumeController_Factory implements Provider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class InstanceHolder {
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
