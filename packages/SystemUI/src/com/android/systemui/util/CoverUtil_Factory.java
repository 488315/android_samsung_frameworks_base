package com.android.systemui.util;

import dagger.internal.Provider;

public final class CoverUtil_Factory implements Provider {

    final class InstanceHolder {
        private static final CoverUtil_Factory INSTANCE = new CoverUtil_Factory();

        private InstanceHolder() {
        }
    }

    public static CoverUtil_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static CoverUtil newInstance() {
        return new CoverUtil();
    }

    @Override // javax.inject.Provider
    public CoverUtil get() {
        return newInstance();
    }
}
