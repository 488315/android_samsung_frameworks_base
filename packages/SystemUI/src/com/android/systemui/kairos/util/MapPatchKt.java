package com.android.systemui.kairos.util;

import com.android.systemui.kairos.util.Maybe;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.collections.builders.MapBuilder;
import kotlin.collections.builders.MapBuilderEntries;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class MapPatchKt {
    public static final Map mapPatchFromFullDiff(Map map, Map map2) {
        Object objM2590boximpl;
        Set setMinus = SetsKt___SetsKt.minus(map.keySet(), (Iterable) map2.keySet());
        MapBuilder mapBuilder = new MapBuilder();
        for (Map.Entry entry : map2.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (map.containsKey(key) && Intrinsics.areEqual(value, map.get(key))) {
                Maybe.Companion.getClass();
                objM2590boximpl = Maybe.Companion.absent;
            } else {
                Maybe.Companion.getClass();
                objM2590boximpl = Maybe.Present.m2590boximpl(value);
            }
            if (objM2590boximpl instanceof Maybe.Present) {
                mapBuilder.put(entry.getKey(), ((Maybe.Present) objM2590boximpl).value);
            }
        }
        MapBuilder mapBuilderBuild = mapBuilder.build();
        HashMap map3 = new HashMap();
        for (Object obj : setMinus) {
            Maybe.Companion.getClass();
            map3.put(obj, Maybe.Companion.absent);
        }
        Iterator it = ((MapBuilderEntries) mapBuilderBuild.entrySet()).iterator();
        while (it.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it.next();
            Object key2 = entry2.getKey();
            Object value2 = entry2.getValue();
            Maybe.Companion.getClass();
            map3.put(key2, Maybe.Present.m2590boximpl(value2));
        }
        return map3;
    }
}
