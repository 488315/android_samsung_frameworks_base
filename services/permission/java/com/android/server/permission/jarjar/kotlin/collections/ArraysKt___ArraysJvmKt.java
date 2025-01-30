package com.android.server.permission.jarjar.kotlin.collections;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import java.util.List;

/* compiled from: _ArraysJvm.kt */
/* loaded from: classes2.dex */
public abstract class ArraysKt___ArraysJvmKt extends ArraysKt__ArraysKt {
    public static final List asList(Object[] objArr) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        List asList = ArraysUtilJVM.asList(objArr);
        Intrinsics.checkNotNullExpressionValue(asList, "asList(this)");
        return asList;
    }
}
