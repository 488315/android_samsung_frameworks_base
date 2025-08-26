package com.android.systemui.util.concurrency;

import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ThreadFactoryImpl_Factory implements Provider {

    final class InstanceHolder {
        static final ThreadFactoryImpl_Factory INSTANCE = new ThreadFactoryImpl_Factory();

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
