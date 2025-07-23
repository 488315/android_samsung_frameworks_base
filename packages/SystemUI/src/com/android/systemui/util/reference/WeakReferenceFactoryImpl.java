package com.android.systemui.util.reference;

import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WeakReferenceFactoryImpl implements WeakReferenceFactory {
    public static final int $stable = 0;

    @Override // com.android.systemui.util.reference.WeakReferenceFactory
    public <T> WeakReference<T> create(T t) {
        return new WeakReference<>(t);
    }
}
