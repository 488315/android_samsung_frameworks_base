package com.android.systemui.util.kotlin;

import dagger.Lazy;
import java.util.Optional;
import kotlin.reflect.KProperty;

public final class DaggerKt {
    public static final <T> T getValue(Lazy lazy, Object obj, KProperty kProperty) {
        return (T) lazy.get();
    }

    public static final <T> T getValue(Optional<T> optional, Object obj, KProperty kProperty) {
        return optional.orElse(null);
    }
}
