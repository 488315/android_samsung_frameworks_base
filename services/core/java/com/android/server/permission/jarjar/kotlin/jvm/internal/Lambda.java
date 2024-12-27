package com.android.server.permission.jarjar.kotlin.jvm.internal;

import java.io.Serializable;

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
