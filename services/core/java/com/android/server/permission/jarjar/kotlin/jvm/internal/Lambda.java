package com.android.server.permission.jarjar.kotlin.jvm.internal;

import java.io.Serializable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class Lambda implements Serializable {
    private final int arity = 1;

    public final String toString() {
        Reflection.factory.getClass();
        String obj = getClass().getGenericInterfaces()[0].toString();
        if (obj.startsWith("com.android.server.permission.jarjar.kotlin.jvm.functions.")) {
            obj = obj.substring(58);
        }
        Intrinsics.checkNotNullExpressionValue("renderLambdaToString(...)", obj);
        return obj;
    }
}
