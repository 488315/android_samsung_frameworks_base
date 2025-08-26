package com.android.systemui.util.reference;

import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public interface WeakReferenceFactory {
    <T> WeakReference<T> create(T t);
}
