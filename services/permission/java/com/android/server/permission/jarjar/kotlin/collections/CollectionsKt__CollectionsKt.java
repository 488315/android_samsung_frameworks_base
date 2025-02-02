package com.android.server.permission.jarjar.kotlin.collections;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import java.util.List;

/* compiled from: Collections.kt */
/* loaded from: classes2.dex */
public abstract class CollectionsKt__CollectionsKt extends CollectionsKt__CollectionsJVMKt {
  public static final List emptyList() {
    return EmptyList.INSTANCE;
  }

  public static final List optimizeReadOnlyList(List list) {
    Intrinsics.checkNotNullParameter(list, "<this>");
    int size = list.size();
    if (size != 0) {
      return size != 1 ? list : CollectionsKt__CollectionsJVMKt.listOf(list.get(0));
    }
    return emptyList();
  }
}
