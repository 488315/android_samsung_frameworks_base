package com.android.systemui.util.io;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class Files_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final Files_Factory INSTANCE = new Files_Factory();

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
