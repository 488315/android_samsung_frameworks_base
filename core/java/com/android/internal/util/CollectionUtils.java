package com.android.internal.util;

import android.C0000R;
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

/* loaded from: classes5.dex */
public class CollectionUtils {
  private CollectionUtils() {}

  public static <T> boolean contains(Collection<T> collection, T element) {
    return collection != null && collection.contains(element);
  }

  public static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
    ArrayList<T> result = null;
    for (int i = 0; i < size(list); i++) {
      T item = list.get(i);
      if (predicate.test(item)) {
        result = ArrayUtils.add(result, item);
      }
    }
    return emptyIfNull(result);
  }

  public static <T> Set<T> filter(Set<T> set, Predicate<? super T> predicate) {
    if (set == null || set.size() == 0) {
      return Collections.emptySet();
    }
    ArraySet<T> arraySet = null;
    if (set instanceof ArraySet) {
      ArraySet arraySet2 = (ArraySet) set;
      int size = arraySet2.size();
      for (int i = 0; i < size; i++) {
        C0000R.bool boolVar = (T) arraySet2.valueAt(i);
        if (predicate.test(boolVar)) {
          arraySet = ArrayUtils.add((ArraySet<C0000R.bool>) arraySet, boolVar);
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

  public static <T> void addIf(
      List<T> source, Collection<? super T> dest, Predicate<? super T> predicate) {
    for (int i = 0; i < size(source); i++) {
      T item = source.get(i);
      if (predicate.test(item)) {
        dest.add(item);
      }
    }
  }

  public static <I, O> List<O> map(List<I> cur, Function<? super I, ? extends O> f) {
    if (isEmpty(cur)) {
      return Collections.emptyList();
    }
    ArrayList<O> result = new ArrayList<>();
    for (int i = 0; i < cur.size(); i++) {
      result.add(f.apply(cur.get(i)));
    }
    return result;
  }

  public static <I, O> Set<O> map(Set<I> set, Function<? super I, ? extends O> function) {
    if (isEmpty(set)) {
      return Collections.emptySet();
    }
    ArraySet arraySet = new ArraySet();
    if (set instanceof ArraySet) {
      ArraySet arraySet2 = (ArraySet) set;
      int size = arraySet2.size();
      for (int i = 0; i < size; i++) {
        arraySet.add(function.apply((I) arraySet2.valueAt(i)));
      }
    } else {
      Iterator<I> it = set.iterator();
      while (it.hasNext()) {
        arraySet.add(function.apply(it.next()));
      }
    }
    return arraySet;
  }

  public static <I, O> List<O> mapNotNull(List<I> cur, Function<? super I, ? extends O> f) {
    if (isEmpty(cur)) {
      return Collections.emptyList();
    }
    List<O> result = null;
    for (int i = 0; i < cur.size(); i++) {
      O transformed = f.apply(cur.get(i));
      if (transformed != null) {
        result = add(result, transformed);
      }
    }
    return emptyIfNull(result);
  }

  public static <T> List<T> emptyIfNull(List<T> cur) {
    return cur == null ? Collections.emptyList() : cur;
  }

  public static <T> Set<T> emptyIfNull(Set<T> cur) {
    return cur == null ? Collections.emptySet() : cur;
  }

  public static <K, V> Map<K, V> emptyIfNull(Map<K, V> cur) {
    return cur == null ? Collections.emptyMap() : cur;
  }

  public static int size(Collection<?> cur) {
    if (cur != null) {
      return cur.size();
    }
    return 0;
  }

  public static int size(Map<?, ?> cur) {
    if (cur != null) {
      return cur.size();
    }
    return 0;
  }

  public static boolean isEmpty(Collection<?> cur) {
    return size(cur) == 0;
  }

  public static boolean isEmpty(Map<?, ?> cur) {
    return size(cur) == 0;
  }

  public static <T> List<T> filter(List<?> list, Class<T> c) {
    if (isEmpty(list)) {
      return Collections.emptyList();
    }
    ArrayList<T> result = null;
    for (int i = 0; i < list.size(); i++) {
      Object item = list.get(i);
      if (c.isInstance(item)) {
        result = ArrayUtils.add(result, item);
      }
    }
    return emptyIfNull(result);
  }

  public static <T> boolean any(List<T> items, Predicate<T> predicate) {
    return find(items, predicate) != null;
  }

  public static <T> boolean any(Set<T> items, Predicate<T> predicate) {
    return find(items, predicate) != null;
  }

  public static <T> T find(List<T> items, Predicate<T> predicate) {
    if (isEmpty(items)) {
      return null;
    }
    for (int i = 0; i < items.size(); i++) {
      T item = items.get(i);
      if (predicate.test(item)) {
        return item;
      }
    }
    return null;
  }

  public static <T> T find(Set<T> cur, Predicate<T> predicate) {
    int size;
    if (cur == null || predicate == null || (size = cur.size()) == 0) {
      return null;
    }
    try {
      if (cur instanceof ArraySet) {
        ArraySet<T> arraySet = (ArraySet) cur;
        for (int i = 0; i < size; i++) {
          T item = arraySet.valueAt(i);
          if (predicate.test(item)) {
            return item;
          }
        }
      } else {
        for (T t : cur) {
          if (predicate.test(t)) {
            return t;
          }
        }
      }
      return null;
    } catch (Exception e) {
      throw ExceptionUtils.propagate(e);
    }
  }

  public static <T> List<T> add(List<T> cur, T val) {
    if (cur == null || cur == Collections.emptyList()) {
      cur = new ArrayList();
    }
    cur.add(val);
    return cur;
  }

  public static <T> List<T> add(List<T> cur, int index, T val) {
    if (cur == null || cur == Collections.emptyList()) {
      cur = new ArrayList();
    }
    cur.add(index, val);
    return cur;
  }

  /* JADX WARN: Multi-variable type inference failed */
  public static <T> Set<T> addAll(Set<T> cur, Collection<T> collection) {
    if (isEmpty((Collection<?>) collection)) {
      return cur != null ? cur : Collections.emptySet();
    }
    if (cur == null || cur == Collections.emptySet()) {
      cur = new ArraySet();
    }
    cur.addAll(collection);
    return cur;
  }

  public static <T> Set<T> add(Set<T> cur, T val) {
    if (cur == null || cur == Collections.emptySet()) {
      cur = new ArraySet();
    }
    cur.add(val);
    return cur;
  }

  public static <K, V> Map<K, V> add(Map<K, V> map, K key, V value) {
    if (map == null || map == Collections.emptyMap()) {
      map = new ArrayMap();
    }
    map.put(key, value);
    return map;
  }

  public static <T> List<T> remove(List<T> cur, T val) {
    if (isEmpty(cur)) {
      return emptyIfNull(cur);
    }
    cur.remove(val);
    return cur;
  }

  public static <T> Set<T> remove(Set<T> cur, T val) {
    if (isEmpty(cur)) {
      return emptyIfNull(cur);
    }
    cur.remove(val);
    return cur;
  }

  public static <T> List<T> copyOf(List<T> cur) {
    return isEmpty(cur) ? Collections.emptyList() : new ArrayList(cur);
  }

  public static <T> Set<T> copyOf(Set<T> cur) {
    return isEmpty(cur) ? Collections.emptySet() : new ArraySet(cur);
  }

  public static <T> Set<T> toSet(Collection<T> cur) {
    return isEmpty((Collection<?>) cur) ? Collections.emptySet() : new ArraySet(cur);
  }

  public static <T> void forEach(Set<T> cur, FunctionalUtils.ThrowingConsumer<T> throwingConsumer) {
    int size;
    if (cur == null || throwingConsumer == null || (size = cur.size()) == 0) {
      return;
    }
    try {
      if (cur instanceof ArraySet) {
        ArraySet<T> arraySet = (ArraySet) cur;
        for (int i = 0; i < size; i++) {
          throwingConsumer.acceptOrThrow(arraySet.valueAt(i));
        }
        return;
      }
      for (T t : cur) {
        throwingConsumer.acceptOrThrow(t);
      }
    } catch (Exception e) {
      throw ExceptionUtils.propagate(e);
    }
  }

  public static <K, V> void forEach(Map<K, V> cur, BiConsumer<K, V> biConsumer) {
    int size;
    if (cur == null || biConsumer == null || (size = cur.size()) == 0) {
      return;
    }
    if (cur instanceof ArrayMap) {
      ArrayMap<K, V> arrayMap = (ArrayMap) cur;
      for (int i = 0; i < size; i++) {
        biConsumer.accept(arrayMap.keyAt(i), arrayMap.valueAt(i));
      }
      return;
    }
    for (K key : cur.keySet()) {
      biConsumer.accept(key, cur.get(key));
    }
  }

  public static <T> T firstOrNull(List<T> cur) {
    if (isEmpty(cur)) {
      return null;
    }
    return cur.get(0);
  }

  public static <T> T firstOrNull(Collection<T> cur) {
    if (isEmpty((Collection<?>) cur)) {
      return null;
    }
    return cur.iterator().next();
  }

  public static <T> List<T> singletonOrEmpty(T item) {
    return item == null ? Collections.emptyList() : Collections.singletonList(item);
  }
}
