package com.android.systemui.util.concurrency;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ThreadFactoryImpl_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
