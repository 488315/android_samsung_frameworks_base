package com.android.server.permission.access.immutable;

import android.util.ArraySet;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;
import java.util.List;

public abstract class IndexedSetExtensionsKt {
    public static final MutableIndexedSet indexedSetOf(Object... objArr) {
        List asList = Arrays.asList(objArr);
        Intrinsics.checkNotNullExpressionValue("asList(...)", asList);
        return new MutableIndexedSet(new ArraySet(asList));
    }
}
