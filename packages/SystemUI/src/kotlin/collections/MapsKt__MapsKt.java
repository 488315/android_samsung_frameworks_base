package kotlin.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Pair;

/* loaded from: classes4.dex */
public class MapsKt__MapsKt extends MapsKt__MapsJVMKt {
    public static Map emptyMap() {
        return EmptyMap.INSTANCE;
    }

    public static Object getValue(Object obj, Map map) {
        if (map instanceof MapWithDefaultImpl) {
            MapWithDefaultImpl mapWithDefaultImpl = (MapWithDefaultImpl) map;
            Map map2 = mapWithDefaultImpl.map;
            Object obj2 = map2.get(obj);
            return (obj2 != null || map2.containsKey(obj)) ? obj2 : mapWithDefaultImpl.f131default.mo781invoke(obj);
        }
        Object obj3 = map.get(obj);
        if (obj3 != null || map.containsKey(obj)) {
            return obj3;
        }
        throw new NoSuchElementException("Key " + obj + " is missing in the map.");
    }

    public static HashMap hashMapOf(Pair... pairArr) {
        HashMap map = new HashMap(MapsKt__MapsJVMKt.mapCapacity(pairArr.length));
        putAll(map, pairArr);
        return map;
    }

    public static Map mapOf(Pair... pairArr) {
        if (pairArr.length <= 0) {
            return EmptyMap.INSTANCE;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(pairArr.length));
        putAll(linkedHashMap, pairArr);
        return linkedHashMap;
    }

    public static Map mutableMapOf(Pair... pairArr) {
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

    public static Map toMap(Iterable iterable) {
        if (!(iterable instanceof Collection)) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            putAll(linkedHashMap, iterable);
            return optimizeReadOnlyMap(linkedHashMap);
        }
        Collection collection = (Collection) iterable;
        int size = collection.size();
        if (size == 0) {
            return EmptyMap.INSTANCE;
        }
        if (size == 1) {
            Pair pair = (Pair) (iterable instanceof List ? ((List) iterable).get(0) : collection.iterator().next());
            return Collections.singletonMap(pair.getFirst(), pair.getSecond());
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(collection.size()));
        putAll(linkedHashMap2, iterable);
        return linkedHashMap2;
    }

    public static void putAll(Map map, Iterable iterable) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            map.put(pair.component1(), pair.component2());
        }
    }

    public static Map toMap(Pair[] pairArr) {
        int length = pairArr.length;
        if (length == 0) {
            return EmptyMap.INSTANCE;
        }
        if (length != 1) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(pairArr.length));
            putAll(linkedHashMap, pairArr);
            return linkedHashMap;
        }
        Pair pair = pairArr[0];
        return Collections.singletonMap(pair.getFirst(), pair.getSecond());
    }

    public static Map toMap(Map map) {
        int size = map.size();
        if (size == 0) {
            return EmptyMap.INSTANCE;
        }
        if (size != 1) {
            return new LinkedHashMap(map);
        }
        Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
        return Collections.singletonMap(entry.getKey(), entry.getValue());
    }
}
