package kotlin.collections;

import java.util.Collections;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MapsJVM.kt */
/* loaded from: classes.dex */
class MapsKt__MapsJVMKt {
    public static final <K, V> Map<K, V> toSingletonMap(Map<? extends K, ? extends V> map) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Map.Entry<? extends K, ? extends V> next = map.entrySet().iterator().next();
        Map<K, V> singletonMap = Collections.singletonMap(next.getKey(), next.getValue());
        Intrinsics.checkNotNullExpressionValue(singletonMap, "with(entries.iterator().…ingletonMap(key, value) }");
        return singletonMap;
    }
}
