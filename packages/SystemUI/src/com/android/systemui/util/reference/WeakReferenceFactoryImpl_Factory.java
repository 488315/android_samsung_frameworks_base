package com.android.systemui.util.reference;

import dagger.internal.Provider;

public final class WeakReferenceFactoryImpl_Factory implements Provider {

    final class InstanceHolder {
        private static final WeakReferenceFactoryImpl_Factory INSTANCE = new WeakReferenceFactoryImpl_Factory();

        private InstanceHolder() {
        }
    }

    public static WeakReferenceFactoryImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static WeakReferenceFactoryImpl newInstance() {
        return new WeakReferenceFactoryImpl();
    }

    @Override // javax.inject.Provider
    public WeakReferenceFactoryImpl get() {
        return newInstance();
    }
}
