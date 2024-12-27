package com.android.systemui.util.concurrency;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ExecutionImpl_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final ExecutionImpl_Factory INSTANCE = new ExecutionImpl_Factory();

        private InstanceHolder() {
        }
    }

    public static ExecutionImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ExecutionImpl newInstance() {
        return new ExecutionImpl();
    }

    @Override // javax.inject.Provider
    public ExecutionImpl get() {
        return newInstance();
    }
}
