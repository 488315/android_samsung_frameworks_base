package androidx.slice;

import java.util.Objects;

/* loaded from: classes.dex */
public class ArrayUtils {
    private ArrayUtils() {
    }

    public static boolean contains(Object[] objArr, Object obj) {
        for (Object obj2 : objArr) {
            if (Objects.equals(obj2, obj)) {
                return true;
            }
        }
        return false;
    }
}
