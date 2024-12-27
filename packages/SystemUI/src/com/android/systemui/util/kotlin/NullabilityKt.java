package com.android.systemui.util.kotlin;

import java.util.Optional;
import kotlin.jvm.functions.Function1;

public final class NullabilityKt {
    public static final <T> T getOrNull(Optional<T> optional) {
        return optional.orElse(null);
    }

    public static final <T, R> R transform(T t, Function1 function1) {
        if (t != null) {
            return (R) function1.invoke(t);
        }
        return null;
    }
}
