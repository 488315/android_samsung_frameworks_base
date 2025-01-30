package com.android.systemui.bixby2.controller.volume;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BixbyVolumeController_Factory implements Provider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class InstanceHolder {
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
