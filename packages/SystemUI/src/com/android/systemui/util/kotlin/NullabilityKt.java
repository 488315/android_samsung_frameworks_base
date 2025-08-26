package com.android.systemui.util.kotlin;

import android.util.Log;
import java.util.Optional;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final class NullabilityKt {
    public static final <T> void expectNotNull(String str, String str2, T t) {
        if (t == null) {
            Log.wtf(str, "Expected value of " + str2 + " to not be null.");
        }
    }

    public static final <T> T getOrNull(Optional<T> optional) {
        return optional.orElse(null);
    }

    public static final <T, R> R transform(T t, Function1 function1) {
        if (t != null) {
            return (R) function1.mo781invoke(t);
        }
        return null;
    }
}
