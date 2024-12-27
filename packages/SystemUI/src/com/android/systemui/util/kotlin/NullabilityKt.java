package com.android.systemui.util.kotlin;

import java.util.Optional;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
