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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class MapPatchKt {
    public static final Map mapPatchFromFullDiff(Map map, Map map2) {
        Object m2573boximpl;
        Set minus = SetsKt___SetsKt.minus(map.keySet(), (Iterable) map2.keySet());
        MapBuilder mapBuilder = new MapBuilder();
        for (Map.Entry entry : map2.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (map.containsKey(key) && Intrinsics.areEqual(value, map.get(key))) {
                Maybe.Companion.getClass();
                m2573boximpl = Maybe.Companion.absent;
            } else {
                Maybe.Companion.getClass();
                m2573boximpl = Maybe.Present.m2573boximpl(value);
            }
            if (m2573boximpl instanceof Maybe.Present) {
                mapBuilder.put(entry.getKey(), ((Maybe.Present) m2573boximpl).value);
            }
        }
        MapBuilder build = mapBuilder.build();
        HashMap hashMap = new HashMap();
        for (Object obj : minus) {
            Maybe.Companion.getClass();
            hashMap.put(obj, Maybe.Companion.absent);
        }
        Iterator it = ((MapBuilderEntries) build.entrySet()).iterator();
        while (it.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it.next();
            Object key2 = entry2.getKey();
            Object value2 = entry2.getValue();
            Maybe.Companion.getClass();
            hashMap.put(key2, Maybe.Present.m2573boximpl(value2));
        }
        return hashMap;
    }
}
