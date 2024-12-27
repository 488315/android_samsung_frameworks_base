package com.android.internal.util.jobs;

import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.ExceptionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class CollectionUtils {
    private CollectionUtils() {}

    public static List add(List list, int i, Object obj) {
        if (list == null || list == Collections.emptyList()) {
            list = new ArrayList();
        }
        list.add(i, obj);
        return list;
    }

    public static List add(List list, Object obj) {
        if (list == null || list == Collections.emptyList()) {
            list = new ArrayList();
        }
        list.add(obj);
        return list;
    }

    public static Map add(Map map, Object obj, Object obj2) {
        if (map == null || map == Collections.emptyMap()) {
            map = new ArrayMap();
        }
        map.put(obj, obj2);
        return map;
    }

    public static Set add(Set set, Object obj) {
        if (set == null || set == Collections.emptySet()) {
            set = new ArraySet();
        }
        set.add(obj);
        return set;
    }

    public static Set addAll(Set set, Collection collection) {
        if (isEmpty(collection)) {
            return set != null ? set : Collections.emptySet();
        }
        if (set == null || set == Collections.emptySet()) {
            set = new ArraySet();
        }
        set.addAll(collection);
        return set;
    }

    public static void addIf(List list, Collection collection, Predicate predicate) {
        for (int i = 0; i < size(list); i++) {
            Object obj = list.get(i);
            if (predicate.test(obj)) {
                collection.add(obj);
            }
        }
    }

    public static boolean any(List list, Predicate predicate) {
        return find(list, predicate) != null;
    }

    public static boolean any(Set set, Predicate predicate) {
        return find(set, predicate) != null;
    }

    public static boolean contains(Collection collection, Object obj) {
        return collection != null && collection.contains(obj);
    }

    public static List copyOf(List list) {
        return isEmpty(list) ? Collections.emptyList() : new ArrayList(list);
    }

    public static Set copyOf(Set set) {
        return isEmpty(set) ? Collections.emptySet() : new ArraySet(set);
    }

    public static List emptyIfNull(List list) {
        return list == null ? Collections.emptyList() : list;
    }

    public static Map emptyIfNull(Map map) {
        return map == null ? Collections.emptyMap() : map;
    }

    public static Set emptyIfNull(Set set) {
        return set == null ? Collections.emptySet() : set;
    }

    public static List filter(List list, Class cls) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        int size = list.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            Object obj = list.get(i);
            if (cls.isInstance(obj)) {
                arrayList = ArrayUtils.add(arrayList, obj);
            }
        }
        return emptyIfNull(arrayList);
    }

    public static List filter(List list, Predicate predicate) {
        ArrayList arrayList = null;
        for (int i = 0; i < size(list); i++) {
            Object obj = list.get(i);
            if (predicate.test(obj)) {
                arrayList = ArrayUtils.add(arrayList, obj);
            }
        }
        return emptyIfNull(arrayList);
    }

    public static Set filter(Set set, Predicate predicate) {
        if (set == null || set.size() == 0) {
            return Collections.emptySet();
        }
        ArraySet arraySet = null;
        if (set instanceof ArraySet) {
            ArraySet arraySet2 = (ArraySet) set;
            int size = arraySet2.size();
            for (int i = 0; i < size; i++) {
                Object valueAt = arraySet2.valueAt(i);
                if (predicate.test(valueAt)) {
                    arraySet = ArrayUtils.add(arraySet, valueAt);
                }
            }
        } else {
            for (Object obj : set) {
                if (predicate.test(obj)) {
                    arraySet = ArrayUtils.add(arraySet, obj);
                }
            }
        }
        return emptyIfNull(arraySet);
    }

    public static Object find(List list, Predicate predicate) {
        if (isEmpty(list)) {
            return null;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Object obj = list.get(i);
            if (predicate.test(obj)) {
                return obj;
            }
        }
        return null;
    }

    public static Object find(Set set, Predicate predicate) {
        int size;
        if (set == null || predicate == null || (size = set.size()) == 0) {
            return null;
        }
        try {
            if (set instanceof ArraySet) {
                ArraySet arraySet = (ArraySet) set;
                for (int i = 0; i < size; i++) {
                    Object valueAt = arraySet.valueAt(i);
                    if (predicate.test(valueAt)) {
                        return valueAt;
                    }
                }
            } else {
                for (Object obj : set) {
                    if (predicate.test(obj)) {
                        return obj;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw ExceptionUtils.propagate(e);
        }
    }

    public static Object firstOrNull(Collection collection) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.iterator().next();
    }

    public static Object firstOrNull(List list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public static void forEach(Map map, BiConsumer biConsumer) {
        int size;
        if (map == null || biConsumer == null || (size = map.size()) == 0) {
            return;
        }
        if (!(map instanceof ArrayMap)) {
            for (Object obj : map.keySet()) {
                biConsumer.accept(obj, map.get(obj));
            }
            return;
        }
        ArrayMap arrayMap = (ArrayMap) map;
        for (int i = 0; i < size; i++) {
            biConsumer.accept(arrayMap.keyAt(i), arrayMap.valueAt(i));
        }
    }

    public static void forEach(Set set, FunctionalUtils.ThrowingConsumer throwingConsumer) {
        int size;
        if (set == null || throwingConsumer == null || (size = set.size()) == 0) {
            return;
        }
        try {
            if (!(set instanceof ArraySet)) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    throwingConsumer.acceptOrThrow(it.next());
                }
            } else {
                ArraySet arraySet = (ArraySet) set;
                for (int i = 0; i < size; i++) {
                    throwingConsumer.acceptOrThrow(arraySet.valueAt(i));
                }
            }
        } catch (Exception e) {
            throw ExceptionUtils.propagate(e);
        }
    }

    public static boolean isEmpty(Collection collection) {
        return size(collection) == 0;
    }

    public static boolean isEmpty(Map map) {
        return size(map) == 0;
    }

    public static List map(List list, Function function) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(function.apply(list.get(i)));
        }
        return arrayList;
    }

    public static Set map(Set set, Function function) {
        if (isEmpty(set)) {
            return Collections.emptySet();
        }
        ArraySet arraySet = new ArraySet(set.size());
        if (set instanceof ArraySet) {
            ArraySet arraySet2 = (ArraySet) set;
            int size = arraySet2.size();
            for (int i = 0; i < size; i++) {
                arraySet.add(function.apply(arraySet2.valueAt(i)));
            }
        } else {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                arraySet.add(function.apply(it.next()));
            }
        }
        return arraySet;
    }

    public static List mapNotNull(List list, Function function) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        int size = list.size();
        List list2 = null;
        for (int i = 0; i < size; i++) {
            Object apply = function.apply(list.get(i));
            if (apply != null) {
                list2 = add(list2, apply);
            }
        }
        return emptyIfNull(list2);
    }

    public static List remove(List list, Object obj) {
        if (isEmpty(list)) {
            return emptyIfNull(list);
        }
        list.remove(obj);
        return list;
    }

    public static Set remove(Set set, Object obj) {
        if (isEmpty(set)) {
            return emptyIfNull(set);
        }
        set.remove(obj);
        return set;
    }

    public static List singletonOrEmpty(Object obj) {
        return obj == null ? Collections.emptyList() : Collections.singletonList(obj);
    }

    public static int size(Collection collection) {
        if (collection != null) {
            return collection.size();
        }
        return 0;
    }

    public static int size(Map map) {
        if (map != null) {
            return map.size();
        }
        return 0;
    }

    public static Set toSet(Collection collection) {
        return isEmpty(collection) ? Collections.emptySet() : new ArraySet(collection);
    }
}
