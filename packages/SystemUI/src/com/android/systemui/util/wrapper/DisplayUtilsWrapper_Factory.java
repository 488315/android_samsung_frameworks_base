package com.android.systemui.util.wrapper;

import dagger.internal.Provider;

public final class DisplayUtilsWrapper_Factory implements Provider {

    final class InstanceHolder {
        private static final DisplayUtilsWrapper_Factory INSTANCE = new DisplayUtilsWrapper_Factory();

        private InstanceHolder() {
        }
    }

    public static DisplayUtilsWrapper_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DisplayUtilsWrapper newInstance() {
        return new DisplayUtilsWrapper();
    }

    @Override // javax.inject.Provider
    public DisplayUtilsWrapper get() {
        return newInstance();
    }
}
