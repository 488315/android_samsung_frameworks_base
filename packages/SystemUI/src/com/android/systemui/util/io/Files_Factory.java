package com.android.systemui.util.io;

import dagger.internal.Provider;

public final class Files_Factory implements Provider {

    final class InstanceHolder {
        private static final Files_Factory INSTANCE = new Files_Factory();

        private InstanceHolder() {
        }
    }

    public static Files_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Files newInstance() {
        return new Files();
    }

    @Override // javax.inject.Provider
    public Files get() {
        return newInstance();
    }
}
