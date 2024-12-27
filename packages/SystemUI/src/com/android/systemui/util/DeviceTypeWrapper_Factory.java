package com.android.systemui.util;

import dagger.internal.Provider;

public final class DeviceTypeWrapper_Factory implements Provider {

    final class InstanceHolder {
        private static final DeviceTypeWrapper_Factory INSTANCE = new DeviceTypeWrapper_Factory();

        private InstanceHolder() {
        }
    }

    public static DeviceTypeWrapper_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DeviceTypeWrapper newInstance() {
        return new DeviceTypeWrapper();
    }

    @Override // javax.inject.Provider
    public DeviceTypeWrapper get() {
        return newInstance();
    }
}
