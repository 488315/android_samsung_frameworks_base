package com.android.systemui.util;

import dagger.internal.Provider;

public final class DeviceConfigProxy_Factory implements Provider {

    final class InstanceHolder {
        private static final DeviceConfigProxy_Factory INSTANCE = new DeviceConfigProxy_Factory();

        private InstanceHolder() {
        }
    }

    public static DeviceConfigProxy_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DeviceConfigProxy newInstance() {
        return new DeviceConfigProxy();
    }

    @Override // javax.inject.Provider
    public DeviceConfigProxy get() {
        return newInstance();
    }
}
