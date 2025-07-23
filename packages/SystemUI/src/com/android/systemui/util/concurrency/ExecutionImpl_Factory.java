package com.android.systemui.util.concurrency;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ExecutionImpl_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final ExecutionImpl_Factory INSTANCE = new ExecutionImpl_Factory();

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
