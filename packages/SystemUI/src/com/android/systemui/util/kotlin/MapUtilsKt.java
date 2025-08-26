package com.android.systemui.util.kotlin;

import java.util.Map;
import kotlin.collections.builders.MapBuilder;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final class MapUtilsKt {
    public static final <K, V> Map<K, V> filterValuesNotNull(Map<K, ? extends V> map) {
        return mapValuesNotNull(map, new MapUtilsKt$$ExternalSyntheticLambda0());
    }

    public static final <K, V, R> Map<K, R> mapValuesNotNull(Map<K, ? extends V> map, Function1 function1) {
        MapBuilder mapBuilder = new MapBuilder();
        mapValuesNotNullTo(map, mapBuilder, function1);
        return mapBuilder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <K, V, R, M extends Map<? super K, ? super R>> M mapValuesNotNullTo(Map<? extends K, ? extends V> map, M m, Function1 function1) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            Object objMo781invoke = function1.mo781invoke(entry);
            if (objMo781invoke != null) {
                m.put(entry.getKey(), objMo781invoke);
            }
        }
        return m;
    }
}
