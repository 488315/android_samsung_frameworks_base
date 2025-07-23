package kotlin.collections;

import java.util.LinkedHashSet;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SetsKt__SetsKt extends SetsKt__SetsJVMKt {
    public static Set mutableSetOf(Object... objArr) {
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(objArr.length));
        ArraysKt___ArraysKt.toCollection(linkedHashSet, objArr);
        return linkedHashSet;
    }
}
