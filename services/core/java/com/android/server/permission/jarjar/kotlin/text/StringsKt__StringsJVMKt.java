package com.android.server.permission.jarjar.kotlin.text;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

public abstract class StringsKt__StringsJVMKt extends StringsKt__StringBuilderKt {
    public static boolean startsWith$default(String str, String str2) {
        Intrinsics.checkNotNullParameter("<this>", str);
        Intrinsics.checkNotNullParameter("prefix", str2);
        return str.startsWith(str2);
    }
}
