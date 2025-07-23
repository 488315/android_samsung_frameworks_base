package com.android.systemui.util;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceTypeWrapper_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final DeviceTypeWrapper_Factory INSTANCE = new DeviceTypeWrapper_Factory();

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
