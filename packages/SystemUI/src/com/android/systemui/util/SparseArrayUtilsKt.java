package com.android.systemui.util;

import android.util.SparseArray;
import java.util.Iterator;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.Grouping;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SparseArrayUtilsKt {
    public static final <T> Map<Integer, T> asMap(SparseArray<T> sparseArray) {
        return new SparseArrayMapWrapper(sparseArray);
    }

    public static final <T> SparseArray<T> associateByToSparseArray(T[] tArr, Function1 function1) {
        SparseArray<T> sparseArray = new SparseArray<>(tArr.length);
        for (T t : tArr) {
            sparseArray.put(((Number) function1.invoke(t)).intValue(), t);
        }
        return sparseArray;
    }

    public static final <T, R> SparseArray<R> foldToSparseArray(Grouping grouping, R r, int i, Function2 function2) {
        SparseArray sparseArray = i < 0 ? new SparseArray() : new SparseArray(i);
        Iterator sourceIterator = grouping.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object next = sourceIterator.next();
            int intValue = ((Number) grouping.keyOf(next)).intValue();
            Object obj = sparseArray.get(intValue);
            if (obj == null) {
                obj = r;
            }
            sparseArray.put(intValue, function2.invoke(obj, next));
        }
        return sparseArray;
    }

    public static /* synthetic */ SparseArray foldToSparseArray$default(Grouping grouping, Object obj, int i, Function2 function2, int i2, Object obj2) {
        if ((i2 & 2) != 0) {
            i = -1;
        }
        SparseArray sparseArray = i < 0 ? new SparseArray() : new SparseArray(i);
        Iterator sourceIterator = grouping.sourceIterator();
        while (sourceIterator.hasNext()) {
            Object next = sourceIterator.next();
            int intValue = ((Number) grouping.keyOf(next)).intValue();
            Object obj3 = sparseArray.get(intValue);
            if (obj3 == null) {
                obj3 = obj;
            }
            sparseArray.put(intValue, function2.invoke(obj3, next));
        }
        return sparseArray;
    }

    public static final <T> SparseArray<T> toSparseArray(Sequence sequence, int i) {
        SparseArray sparseArray = i < 0 ? new SparseArray() : new SparseArray(i);
        Iterator it = sequence.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            sparseArray.put(((Number) pair.component1()).intValue(), pair.component2());
        }
        return sparseArray;
    }

    public static /* synthetic */ SparseArray toSparseArray$default(Sequence sequence, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        return toSparseArray(sequence, i);
    }
}
