package com.android.systemui.util;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceConfigProxy_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final DeviceConfigProxy_Factory INSTANCE = new DeviceConfigProxy_Factory();

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
