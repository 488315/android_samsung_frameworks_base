package com.android.systemui.util.reference;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WeakReferenceFactoryImpl_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final WeakReferenceFactoryImpl_Factory INSTANCE = new WeakReferenceFactoryImpl_Factory();

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
