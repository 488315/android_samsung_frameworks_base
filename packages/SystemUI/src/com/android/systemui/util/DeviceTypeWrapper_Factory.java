package com.android.systemui.util;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DeviceTypeWrapper_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
