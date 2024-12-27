package com.android.systemui.util.reference;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WeakReferenceFactoryImpl implements WeakReferenceFactory {
    public static final int $stable = 0;

    @Override // com.android.systemui.util.reference.WeakReferenceFactory
    public <T> WeakReference<T> create(T t) {
        return new WeakReference<>(t);
    }
}
