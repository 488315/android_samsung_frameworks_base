package com.android.systemui.util.reference;

import java.lang.ref.WeakReference;

public interface WeakReferenceFactory {
    <T> WeakReference<T> create(T t);
}
