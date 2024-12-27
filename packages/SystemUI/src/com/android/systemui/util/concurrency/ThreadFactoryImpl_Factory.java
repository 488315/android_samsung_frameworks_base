package com.android.systemui.util.concurrency;

import dagger.internal.Provider;

public final class ThreadFactoryImpl_Factory implements Provider {

    final class InstanceHolder {
        private static final ThreadFactoryImpl_Factory INSTANCE = new ThreadFactoryImpl_Factory();

        private InstanceHolder() {
        }
    }

    public static ThreadFactoryImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ThreadFactoryImpl newInstance() {
        return new ThreadFactoryImpl();
    }

    @Override // javax.inject.Provider
    public ThreadFactoryImpl get() {
        return newInstance();
    }
}
