package com.android.server.permission.jarjar.kotlin.collections;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: MutableCollections.kt */
/* loaded from: classes2.dex */
public abstract class CollectionsKt__MutableCollectionsKt
    extends CollectionsKt__MutableCollectionsJVMKt {
  public static final boolean addAll(Collection collection, Iterable iterable) {
    Intrinsics.checkNotNullParameter(collection, "<this>");
    Intrinsics.checkNotNullParameter(iterable, "elements");
    if (iterable instanceof Collection) {
      return collection.addAll((Collection) iterable);
    }
    Iterator it = iterable.iterator();
    boolean z = false;
    while (it.hasNext()) {
      if (collection.add(it.next())) {
        z = true;
      }
    }
    return z;
  }

  public static final boolean addAll(Collection collection, Object[] objArr) {
    Intrinsics.checkNotNullParameter(collection, "<this>");
    Intrinsics.checkNotNullParameter(objArr, "elements");
    return collection.addAll(ArraysKt___ArraysJvmKt.asList(objArr));
  }
}
