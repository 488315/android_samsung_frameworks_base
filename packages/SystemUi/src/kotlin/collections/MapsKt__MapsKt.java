package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Pair;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class MapsKt__MapsKt extends MapsKt__MapsJVMKt {
    public static final Map emptyMap() {
        return EmptyMap.INSTANCE;
    }

    public static final Object getValue(Map map, Object obj) {
        if (map instanceof MapWithDefaultImpl) {
            MapWithDefaultImpl mapWithDefaultImpl = (MapWithDefaultImpl) map;
            Map map2 = mapWithDefaultImpl.map;
            Object obj2 = map2.get(obj);
            return (obj2 != null || map2.containsKey(obj)) ? obj2 : mapWithDefaultImpl.f837default.invoke(obj);
        }
        Object obj3 = map.get(obj);
        if (obj3 != null || map.containsKey(obj)) {
            return obj3;
        }
        throw new NoSuchElementException("Key " + obj + " is missing in the map.");
    }

    public static final HashMap hashMapOf(Pair... pairArr) {
        HashMap hashMap = new HashMap(MapsKt__MapsJVMKt.mapCapacity(pairArr.length));
        putAll(hashMap, pairArr);
        return hashMap;
    }

    public static final Map mapOf(Pair... pairArr) {
        if (pairArr.length <= 0) {
            return EmptyMap.INSTANCE;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(pairArr.length));
        putAll(linkedHashMap, pairArr);
        return linkedHashMap;
    }

    public static final Map optimizeReadOnlyMap(Map map) {
        int size = map.size();
        if (size == 0) {
            return EmptyMap.INSTANCE;
        }
        if (size != 1) {
            return map;
        }
        Map.Entry entry = (Map.Entry) ((LinkedHashMap) map).entrySet().iterator().next();
        return Collections.singletonMap(entry.getKey(), entry.getValue());
    }

    public static final void putAll(Map map, Pair[] pairArr) {
        for (Pair pair : pairArr) {
            map.put(pair.component1(), pair.component2());
        }
    }

    public static final void toMap(Iterable iterable, Map map) {
        Iterator it = ((ArrayList) iterable).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            map.put(pair.component1(), pair.component2());
        }
    }

    public static final Map toMap(Iterable iterable) {
        Collection collection = (Collection) iterable;
        int size = collection.size();
        if (size == 0) {
            return EmptyMap.INSTANCE;
        }
        if (size != 1) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(collection.size()));
            toMap(iterable, linkedHashMap);
            return linkedHashMap;
        }
        return MapsKt__MapsJVMKt.mapOf((Pair) ((List) iterable).get(0));
    }

    public static final Map toMap(Map map) {
        int size = map.size();
        if (size == 0) {
            return EmptyMap.INSTANCE;
        }
        if (size != 1) {
            return new LinkedHashMap(map);
        }
        Map.Entry entry = (Map.Entry) ((LinkedHashMap) map).entrySet().iterator().next();
        return Collections.singletonMap(entry.getKey(), entry.getValue());
    }
}
