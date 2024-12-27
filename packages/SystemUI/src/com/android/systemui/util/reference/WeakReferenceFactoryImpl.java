package com.android.systemui.util.reference;

import java.lang.ref.WeakReference;

public final class WeakReferenceFactoryImpl implements WeakReferenceFactory {
    public static final int $stable = 0;

    @Override // com.android.systemui.util.reference.WeakReferenceFactory
    public <T> WeakReference<T> create(T t) {
        return new WeakReference<>(t);
    }
}
