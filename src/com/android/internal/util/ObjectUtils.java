package com.android.internal.util;

import java.util.Objects;

/* loaded from: classes4.dex */
public class ObjectUtils {
    public static <T> T getOrElse(T t, T t2) {
        return t != null ? t : t2;
    }

    private ObjectUtils() {
    }

    public static <T> T firstNotNull(T t, T t2) {
        return t != null ? t : (T) Objects.requireNonNull(t2);
    }

    public static <T extends Comparable> int compare(T t, T t2) {
        if (t == null) {
            return t2 != null ? -1 : 0;
        }
        if (t2 != null) {
            return t.compareTo(t2);
        }
        return 1;
    }
}
