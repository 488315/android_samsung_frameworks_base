package kotlin.collections;

import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: classes4.dex */
public class SetsKt__SetsKt extends SetsKt__SetsJVMKt {
    public static Set mutableSetOf(Object... objArr) {
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(objArr.length));
        ArraysKt___ArraysKt.toCollection(linkedHashSet, objArr);
        return linkedHashSet;
    }
}
