package com.android.systemui.bixby2.controller;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MWBixbyController_Factory implements Provider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class InstanceHolder {
        private static final MWBixbyController_Factory INSTANCE = new MWBixbyController_Factory();

        private InstanceHolder() {
        }
    }

    public static MWBixbyController_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MWBixbyController newInstance() {
        return new MWBixbyController();
    }

    @Override // javax.inject.Provider
    public MWBixbyController get() {
        return newInstance();
    }
}
