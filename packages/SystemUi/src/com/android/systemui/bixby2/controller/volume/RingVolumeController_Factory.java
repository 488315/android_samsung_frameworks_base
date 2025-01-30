package com.android.systemui.bixby2.controller.volume;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RingVolumeController_Factory implements Provider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class InstanceHolder {
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
