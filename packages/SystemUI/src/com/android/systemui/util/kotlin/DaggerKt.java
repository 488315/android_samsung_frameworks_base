package com.android.systemui.util.kotlin;

import dagger.Lazy;
import java.util.Optional;
import kotlin.reflect.KProperty;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DaggerKt {
    public static final <T> T getValue(Lazy lazy, Object obj, KProperty kProperty) {
        return (T) lazy.get();
    }

    public static final <T> T getValue(Optional<T> optional, Object obj, KProperty kProperty) {
        return optional.orElse(null);
    }
}
