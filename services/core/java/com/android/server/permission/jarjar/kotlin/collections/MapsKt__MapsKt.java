package com.android.server.permission.jarjar.kotlin.collections;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

import java.util.Map;

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
