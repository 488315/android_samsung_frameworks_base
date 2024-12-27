package com.android.systemui.util.reference;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WeakReferenceFactoryImpl_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
