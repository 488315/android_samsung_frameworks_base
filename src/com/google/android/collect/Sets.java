package com.google.android.collect;

import android.util.ArraySet;
import java.util.Collections;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

/* loaded from: classes6.dex */
public class Sets {
    public static <K> HashSet<K> newHashSet() {
        return new HashSet<>();
    }

    public static <E> HashSet<E> newHashSet(E... eArr) {
        HashSet<E> hashSet = new HashSet<>(((eArr.length * 4) / 3) + 1);
        Collections.addAll(hashSet, eArr);
        return hashSet;
    }

    public static <E> SortedSet<E> newSortedSet() {
        return new TreeSet();
    }

    public static <E> SortedSet<E> newSortedSet(E... eArr) {
        TreeSet treeSet = new TreeSet();
        Collections.addAll(treeSet, eArr);
        return treeSet;
    }

    public static <E> ArraySet<E> newArraySet() {
        return new ArraySet<>();
    }

    public static <E> ArraySet<E> newArraySet(E... eArr) {
        ArraySet<E> arraySet = new ArraySet<>(((eArr.length * 4) / 3) + 1);
        Collections.addAll(arraySet, eArr);
        return arraySet;
    }
}
