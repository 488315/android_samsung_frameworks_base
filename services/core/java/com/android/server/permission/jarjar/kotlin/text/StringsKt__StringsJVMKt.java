package com.android.server.permission.jarjar.kotlin.text;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class StringsKt__StringsJVMKt extends StringsKt__StringBuilderKt {
    public static boolean startsWith$default(String str, String str2) {
        Intrinsics.checkNotNullParameter("<this>", str);
        Intrinsics.checkNotNullParameter("prefix", str2);
        return str.startsWith(str2);
    }
}
