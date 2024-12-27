package com.samsung.android.sume.core.functional;

/* loaded from: classes6.dex */
public interface PlaceHolder<T> {
    boolean isEmpty();

    boolean isNotEmpty();

    void put(T t);

    T reset();

    default PlaceHolder<T> setParameters(Object... args) {
        throw new UnsupportedOperationException();
    }

    default Object[] getParameters() {
        throw new UnsupportedOperationException();
    }
}
