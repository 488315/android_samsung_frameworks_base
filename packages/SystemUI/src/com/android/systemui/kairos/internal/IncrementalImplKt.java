package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.util.Maybe;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class IncrementalImplKt {
    public static final Pair access$applyPatchCalm(Map map, Map map2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(map);
        for (Map.Entry entry : map2.entrySet()) {
            Object key = entry.getKey();
            Maybe maybe = (Maybe) entry.getValue();
            if (!(maybe instanceof Maybe.Present)) {
                if (!Intrinsics.areEqual(maybe, Maybe.Absent.INSTANCE)) {
                    throw new NoWhenBranchMatchedException();
                }
                if (map.containsKey(key)) {
                    linkedHashMap.put(key, maybe);
                    linkedHashMap2.remove(key);
                }
            } else if (!map.containsKey(key) || !Intrinsics.areEqual(MapsKt__MapsKt.getValue(key, map), ((Maybe.Present) maybe).value)) {
                linkedHashMap.put(key, maybe);
                linkedHashMap2.put(key, ((Maybe.Present) maybe).value);
            }
        }
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        return new Pair(linkedHashMap, linkedHashMap2);
    }
}
