package com.android.systemui.util.reference;

public abstract class ReferenceModule {
    public static final int $stable = 0;

    public abstract WeakReferenceFactory bindWeakReferenceFactory(WeakReferenceFactoryImpl weakReferenceFactoryImpl);
}
