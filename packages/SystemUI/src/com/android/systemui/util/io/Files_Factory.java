package com.android.systemui.util.io;

import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class Files_Factory implements Provider {

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
