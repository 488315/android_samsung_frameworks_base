package com.android.server.permission.access.immutable;

import android.util.ArraySet;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class IndexedSetExtensionsKt {
    public static final MutableIndexedSet indexedSetOf(Object... objArr) {
        List asList = Arrays.asList(objArr);
        Intrinsics.checkNotNullExpressionValue("asList(...)", asList);
        return new MutableIndexedSet(new ArraySet(asList));
    }
}
