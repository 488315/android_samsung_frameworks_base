package com.android.internal.telephony.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static <T> T[] appendElement(Class<T> cls, T[] tArr, T t) {
        return (T[]) appendElement(cls, tArr, t, false);
    }

    public static <T> T[] appendElement(Class<T> cls, T[] tArr, T t, boolean z) {
        T[] tArr2;
        int i = 0;
        if (tArr != null) {
            if (!z && contains(tArr, t)) {
                return tArr;
            }
            int length = tArr.length;
            tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, length + 1));
            System.arraycopy(tArr, 0, tArr2, 0, length);
            i = length;
        } else {
            tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, 1));
        }
        tArr2[i] = t;
        return tArr2;
    }

    public static <T> T[] concatElements(Class<T> cls, T[]... tArr) {
        if (tArr == null || tArr.length == 0) {
            return (T[]) createEmptyArray(cls);
        }
        int length = 0;
        for (T[] tArr2 : tArr) {
            if (tArr2 != null) {
                length += tArr2.length;
            }
        }
        if (length == 0) {
            return (T[]) createEmptyArray(cls);
        }
        T[] tArr3 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, length));
        int length2 = 0;
        for (T[] tArr4 : tArr) {
            if (tArr4 != null && tArr4.length != 0) {
                System.arraycopy(tArr4, 0, tArr3, length2, tArr4.length);
                length2 += tArr4.length;
            }
        }
        return tArr3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> T[] createEmptyArray(Class<T> cls) {
        if (cls == String.class) {
            return (T[]) EmptyArray.STRING;
        }
        if (cls == Object.class) {
            return (T[]) EmptyArray.OBJECT;
        }
        return (T[]) ((Object[]) Array.newInstance((Class<?>) cls, 0));
    }

    private static final class EmptyArray {
        public static final Object[] OBJECT = new Object[0];
        public static final String[] STRING = new String[0];

        private EmptyArray() {
        }
    }

    public static boolean contains(char[] cArr, char c) {
        if (cArr == null) {
            return false;
        }
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean contains(Collection<T> collection, T t) {
        if (collection != null) {
            return collection.contains(t);
        }
        return false;
    }

    public static boolean contains(int[] iArr, int i) {
        if (iArr == null) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(long[] jArr, long j) {
        if (jArr == null) {
            return false;
        }
        for (long j2 : jArr) {
            if (j2 == j) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean contains(T[] tArr, T t) {
        return indexOf(tArr, t) != -1;
    }

    public static <T> int indexOf(T[] tArr, T t) {
        if (tArr == null) {
            return -1;
        }
        for (int i = 0; i < tArr.length; i++) {
            if (Objects.equals(tArr[i], t)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean isEmpty(T[] tArr) {
        return tArr == null || tArr.length == 0;
    }

    public static boolean isEmpty(int[] iArr) {
        return iArr == null || iArr.length == 0;
    }

    public static boolean isEmpty(long[] jArr) {
        return jArr == null || jArr.length == 0;
    }

    public static boolean isEmpty(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    public static boolean isEmpty(boolean[] zArr) {
        return zArr == null || zArr.length == 0;
    }
}
