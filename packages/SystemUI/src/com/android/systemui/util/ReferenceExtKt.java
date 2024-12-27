package com.android.systemui.util;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ReferenceExtKt {
    public static final <T> ReadWriteProperty nullableAtomicReference(final T t) {
        return new ReadWriteProperty(t) { // from class: com.android.systemui.util.ReferenceExtKt$nullableAtomicReference$1
            private final AtomicReference<Object> t;

            {
                this.t = new AtomicReference<>(t);
            }

            public final AtomicReference<Object> getT() {
                return this.t;
            }

            @Override // kotlin.properties.ReadWriteProperty
            public Object getValue(Object obj, KProperty kProperty) {
                return this.t.get();
            }

            @Override // kotlin.properties.ReadWriteProperty
            public void setValue(Object obj, KProperty kProperty, Object obj2) {
                this.t.set(obj2);
            }
        };
    }

    public static /* synthetic */ ReadWriteProperty nullableAtomicReference$default(Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = null;
        }
        return nullableAtomicReference(obj);
    }

    public static final <T> ReadWriteProperty softReference(final T t) {
        return new ReadWriteProperty(t) { // from class: com.android.systemui.util.ReferenceExtKt$softReference$1
            private SoftReference<Object> softRef;

            {
                this.softRef = new SoftReference<>(t);
            }

            public final SoftReference<Object> getSoftRef() {
                return this.softRef;
            }

            @Override // kotlin.properties.ReadWriteProperty
            public Object getValue(Object obj, KProperty kProperty) {
                return this.softRef.get();
            }

            public final void setSoftRef(SoftReference<Object> softReference) {
                this.softRef = softReference;
            }

            @Override // kotlin.properties.ReadWriteProperty
            public void setValue(Object obj, KProperty kProperty, Object obj2) {
                this.softRef = new SoftReference<>(obj2);
            }
        };
    }

    public static /* synthetic */ ReadWriteProperty softReference$default(Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = null;
        }
        return softReference(obj);
    }

    public static final <T> ReadWriteProperty weakReference(final T t) {
        return new ReadWriteProperty(t) { // from class: com.android.systemui.util.ReferenceExtKt$weakReference$1
            private WeakReference<Object> weakRef;

            {
                this.weakRef = new WeakReference<>(t);
            }

            @Override // kotlin.properties.ReadWriteProperty
            public Object getValue(Object obj, KProperty kProperty) {
                return this.weakRef.get();
            }

            public final WeakReference<Object> getWeakRef() {
                return this.weakRef;
            }

            @Override // kotlin.properties.ReadWriteProperty
            public void setValue(Object obj, KProperty kProperty, Object obj2) {
                this.weakRef = new WeakReference<>(obj2);
            }

            public final void setWeakRef(WeakReference<Object> weakReference) {
                this.weakRef = weakReference;
            }
        };
    }

    public static /* synthetic */ ReadWriteProperty weakReference$default(Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = null;
        }
        return weakReference(obj);
    }
}
