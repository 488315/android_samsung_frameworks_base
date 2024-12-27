package com.android.server.permission.jarjar.kotlin.collections;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class CollectionsKt extends CollectionsKt__MutableCollectionsKt {
    public static Object elementAt(Iterable iterable, int i) {
        Intrinsics.checkNotNullParameter("<this>", iterable);
        boolean z = iterable instanceof List;
        if (z) {
            return ((List) iterable).get(i);
        }
        CollectionsKt___CollectionsKt$elementAt$1 collectionsKt___CollectionsKt$elementAt$1 =
                new CollectionsKt___CollectionsKt$elementAt$1(i);
        if (z) {
            List list = (List) iterable;
            if (i >= 0 && i <= list.size() - 1) {
                return list.get(i);
            }
            collectionsKt___CollectionsKt$elementAt$1.invoke(Integer.valueOf(i));
            throw null;
        }
        if (i < 0) {
            collectionsKt___CollectionsKt$elementAt$1.invoke(Integer.valueOf(i));
            throw null;
        }
        int i2 = 0;
        for (Object obj : iterable) {
            int i3 = i2 + 1;
            if (i == i2) {
                return obj;
            }
            i2 = i3;
        }
        collectionsKt___CollectionsKt$elementAt$1.invoke(Integer.valueOf(i));
        throw null;
    }

    public static List toList(Iterable iterable) {
        Collection collection = (Collection) iterable;
        int size = collection.size();
        if (size == 0) {
            return EmptyList.INSTANCE;
        }
        if (size != 1) {
            return new ArrayList(collection);
        }
        List singletonList =
                Collections.singletonList(
                        iterable instanceof List
                                ? ((List) iterable).get(0)
                                : iterable.iterator().next());
        Intrinsics.checkNotNullExpressionValue("singletonList(...)", singletonList);
        return singletonList;
    }
}
