package com.android.systemui.util.kotlin;

import java.util.Map;
import kotlin.collections.builders.MapBuilder;
import kotlin.jvm.functions.Function1;

public final class MapUtilsKt {
    public static final <K, V> Map<K, V> filterValuesNotNull(Map<K, ? extends V> map) {
        return mapValuesNotNull(map, new Function1() { // from class: com.android.systemui.util.kotlin.MapUtilsKt$filterValuesNotNull$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Map.Entry<Object, Object> entry) {
                return entry.getValue();
            }
        });
    }

    public static final <K, V, R> Map<K, R> mapValuesNotNull(Map<K, ? extends V> map, Function1 function1) {
        MapBuilder mapBuilder = new MapBuilder();
        mapValuesNotNullTo(map, mapBuilder, function1);
        return mapBuilder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <K, V, R, M extends Map<? super K, ? super R>> M mapValuesNotNullTo(Map<? extends K, ? extends V> map, M m, Function1 function1) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            Object invoke = function1.invoke(entry);
            if (invoke != null) {
                m.put(entry.getKey(), invoke);
            }
        }
        return m;
    }
}
