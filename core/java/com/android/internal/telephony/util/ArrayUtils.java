package com.android.internal.telephony.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class ArrayUtils {
  private ArrayUtils() {}

  public static <T> T[] appendElement(Class<T> cls, T[] tArr, T t) {
    return (T[]) appendElement(cls, tArr, t, false);
  }

  public static <T> T[] appendElement(Class<T> cls, T[] tArr, T t, boolean z) {
    int i;
    T[] tArr2;
    if (tArr != null) {
      if (!z && contains(tArr, t)) {
        return tArr;
      }
      i = tArr.length;
      tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i + 1));
      System.arraycopy(tArr, 0, tArr2, 0, i);
    } else {
      i = 0;
      tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, 1));
    }
    tArr2[i] = t;
    return tArr2;
  }

  public static <T> T[] concatElements(Class<T> cls, T[]... tArr) {
    if (tArr == null || tArr.length == 0) {
      return (T[]) createEmptyArray(cls);
    }
    int i = 0;
    for (T[] tArr2 : tArr) {
      if (tArr2 != null) {
        i += tArr2.length;
      }
    }
    if (i == 0) {
      return (T[]) createEmptyArray(cls);
    }
    T[] tArr3 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i));
    int i2 = 0;
    for (T[] tArr4 : tArr) {
      if (tArr4 != null && tArr4.length != 0) {
        System.arraycopy(tArr4, 0, tArr3, i2, tArr4.length);
        i2 += tArr4.length;
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

    private EmptyArray() {}
  }

  public static boolean contains(char[] array, char value) {
    if (array == null) {
      return false;
    }
    for (char element : array) {
      if (element == value) {
        return true;
      }
    }
    return false;
  }

  public static <T> boolean contains(Collection<T> cur, T val) {
    if (cur != null) {
      return cur.contains(val);
    }
    return false;
  }

  public static boolean contains(int[] array, int value) {
    if (array == null) {
      return false;
    }
    for (int element : array) {
      if (element == value) {
        return true;
      }
    }
    return false;
  }

  public static boolean contains(long[] array, long value) {
    if (array == null) {
      return false;
    }
    for (long element : array) {
      if (element == value) {
        return true;
      }
    }
    return false;
  }

  public static <T> boolean contains(T[] array, T value) {
    return indexOf(array, value) != -1;
  }

  public static <T> int indexOf(T[] array, T value) {
    if (array == null) {
      return -1;
    }
    for (int i = 0; i < array.length; i++) {
      if (Objects.equals(array[i], value)) {
        return i;
      }
    }
    return -1;
  }

  public static boolean isEmpty(Collection<?> array) {
    return array == null || array.isEmpty();
  }

  public static boolean isEmpty(Map<?, ?> map) {
    return map == null || map.isEmpty();
  }

  public static <T> boolean isEmpty(T[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(int[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(long[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(byte[] array) {
    return array == null || array.length == 0;
  }

  public static boolean isEmpty(boolean[] array) {
    return array == null || array.length == 0;
  }
}
