package com.android.systemui.bixby2.controller.volume;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemVolumeController_Factory implements Provider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class InstanceHolder {
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
