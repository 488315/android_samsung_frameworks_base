package com.android.systemui;

import android.util.ArrayMap;
import com.android.systemui.Dependency;
import dagger.Lazy;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract /* synthetic */ class SystemUIInitializer$$ExternalSyntheticOutline0 {
    /* renamed from: m */
    public static void m95m(Lazy lazy, Lazy lazy2, int i, ArrayMap arrayMap, Class cls) {
        Objects.requireNonNull(lazy);
        arrayMap.put(cls, new Dependency$$ExternalSyntheticLambda0(lazy2, i));
    }

    public static void m$1(Lazy lazy, Lazy lazy2, int i, ArrayMap arrayMap, Class cls) {
        Objects.requireNonNull(lazy);
        arrayMap.put(cls, new Dependency$$ExternalSyntheticLambda1(lazy2, i));
    }

    public static void m$2(Lazy lazy, Lazy lazy2, int i, ArrayMap arrayMap, Class cls) {
        Objects.requireNonNull(lazy);
        arrayMap.put(cls, new Dependency$$ExternalSyntheticLambda2(lazy2, i));
    }

    public static void m$3(Lazy lazy, Lazy lazy2, int i, ArrayMap arrayMap, Class cls) {
        Objects.requireNonNull(lazy);
        arrayMap.put(cls, new Dependency$$ExternalSyntheticLambda3(lazy2, i));
    }

    public static void m$4(Lazy lazy, Lazy lazy2, int i, ArrayMap arrayMap, Class cls) {
        Objects.requireNonNull(lazy);
        arrayMap.put(cls, new Dependency$$ExternalSyntheticLambda4(lazy2, i));
    }

    public static void m$5(Lazy lazy, final Lazy lazy2, final int i, ArrayMap arrayMap, Class cls) {
        Objects.requireNonNull(lazy);
        arrayMap.put(cls, new Dependency.LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda5
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                int i2 = i;
                Lazy lazy3 = lazy2;
                switch (i2) {
                }
                return lazy3.get();
            }
        });
    }
}
