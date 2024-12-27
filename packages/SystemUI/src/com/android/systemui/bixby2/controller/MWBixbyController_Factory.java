package com.android.systemui.bixby2.controller;

import dagger.internal.Provider;

public final class MWBixbyController_Factory implements Provider {

    final class InstanceHolder {
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
