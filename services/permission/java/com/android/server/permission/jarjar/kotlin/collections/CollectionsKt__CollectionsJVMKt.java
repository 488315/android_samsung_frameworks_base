package com.android.server.permission.jarjar.kotlin.collections;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import java.util.Collections;
import java.util.List;

/* compiled from: CollectionsJVM.kt */
/* loaded from: classes2.dex */
public abstract class CollectionsKt__CollectionsJVMKt {
  public static final List listOf(Object obj) {
    List singletonList = Collections.singletonList(obj);
    Intrinsics.checkNotNullExpressionValue(singletonList, "singletonList(element)");
    return singletonList;
  }
}
