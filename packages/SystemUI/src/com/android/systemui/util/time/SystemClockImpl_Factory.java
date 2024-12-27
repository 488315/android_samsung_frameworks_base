package com.android.systemui.util.time;

import dagger.internal.Provider;

public final class SystemClockImpl_Factory implements Provider {

    final class InstanceHolder {
        private static final SystemClockImpl_Factory INSTANCE = new SystemClockImpl_Factory();

        private InstanceHolder() {
        }
    }

    public static SystemClockImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static SystemClockImpl newInstance() {
        return new SystemClockImpl();
    }

    @Override // javax.inject.Provider
    public SystemClockImpl get() {
        return newInstance();
    }
}
