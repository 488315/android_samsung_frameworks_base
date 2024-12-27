package com.android.systemui.util.concurrency;

import dagger.internal.Provider;

public final class ExecutionImpl_Factory implements Provider {

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
