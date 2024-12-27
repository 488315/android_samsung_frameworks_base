package com.android.server.permission.jarjar.kotlin.collections;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class MapsKt__MapsKt {
    public static Map emptyMap() {
        EmptyMap emptyMap = EmptyMap.INSTANCE;
        Intrinsics.checkNotNull(
                "null cannot be cast to non-null type kotlin.collections.Map<K of"
                    + " kotlin.collections.MapsKt__MapsKt.emptyMap, V of"
                    + " kotlin.collections.MapsKt__MapsKt.emptyMap>",
                emptyMap);
        return emptyMap;
    }
}
