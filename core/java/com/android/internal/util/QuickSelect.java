package com.android.internal.util;

import java.util.Comparator;
import java.util.List;

/* loaded from: classes5.dex */
public final class QuickSelect {
  private static <T> int selectImpl(
      List<T> list, int left, int right, int k, Comparator<? super T> comparator) {
    while (left != right) {
      int pivotIndex = partition(list, left, right, (left + right) >> 1, comparator);
      if (k == pivotIndex) {
        return k;
      }
      if (k < pivotIndex) {
        right = pivotIndex - 1;
      } else {
        left = pivotIndex + 1;
      }
    }
    return left;
  }

  private static int selectImpl(int[] array, int left, int right, int k) {
    while (left != right) {
      int pivotIndex = partition(array, left, right, (left + right) >> 1);
      if (k == pivotIndex) {
        return k;
      }
      if (k < pivotIndex) {
        right = pivotIndex - 1;
      } else {
        left = pivotIndex + 1;
      }
    }
    return left;
  }

  private static int selectImpl(long[] array, int left, int right, int k) {
    while (left != right) {
      int pivotIndex = partition(array, left, right, (left + right) >> 1);
      if (k == pivotIndex) {
        return k;
      }
      if (k < pivotIndex) {
        right = pivotIndex - 1;
      } else {
        left = pivotIndex + 1;
      }
    }
    return left;
  }

  private static <T> int selectImpl(
      T[] array, int left, int right, int k, Comparator<? super T> comparator) {
    while (left != right) {
      int pivotIndex = partition(array, left, right, (left + right) >> 1, comparator);
      if (k == pivotIndex) {
        return k;
      }
      if (k < pivotIndex) {
        right = pivotIndex - 1;
      } else {
        left = pivotIndex + 1;
      }
    }
    return left;
  }

  private static <T> int partition(
      List<T> list, int left, int right, int pivotIndex, Comparator<? super T> comparator) {
    T pivotValue = list.get(pivotIndex);
    swap(list, right, pivotIndex);
    int storeIndex = left;
    for (int i = left; i < right; i++) {
      if (comparator.compare(list.get(i), pivotValue) < 0) {
        swap(list, storeIndex, i);
        storeIndex++;
      }
    }
    swap(list, right, storeIndex);
    return storeIndex;
  }

  private static int partition(int[] array, int left, int right, int pivotIndex) {
    int pivotValue = array[pivotIndex];
    swap(array, right, pivotIndex);
    int storeIndex = left;
    for (int i = left; i < right; i++) {
      if (array[i] < pivotValue) {
        swap(array, storeIndex, i);
        storeIndex++;
      }
    }
    swap(array, right, storeIndex);
    return storeIndex;
  }

  private static int partition(long[] array, int left, int right, int pivotIndex) {
    long pivotValue = array[pivotIndex];
    swap(array, right, pivotIndex);
    int storeIndex = left;
    for (int i = left; i < right; i++) {
      if (array[i] < pivotValue) {
        swap(array, storeIndex, i);
        storeIndex++;
      }
    }
    swap(array, right, storeIndex);
    return storeIndex;
  }

  private static <T> int partition(
      T[] array, int left, int right, int pivotIndex, Comparator<? super T> comparator) {
    T pivotValue = array[pivotIndex];
    swap(array, right, pivotIndex);
    int storeIndex = left;
    for (int i = left; i < right; i++) {
      if (comparator.compare(array[i], pivotValue) < 0) {
        swap(array, storeIndex, i);
        storeIndex++;
      }
    }
    swap(array, right, storeIndex);
    return storeIndex;
  }

  private static <T> void swap(List<T> list, int left, int right) {
    T tmp = list.get(left);
    list.set(left, list.get(right));
    list.set(right, tmp);
  }

  private static void swap(int[] array, int left, int right) {
    int tmp = array[left];
    array[left] = array[right];
    array[right] = tmp;
  }

  private static void swap(long[] array, int left, int right) {
    long tmp = array[left];
    array[left] = array[right];
    array[right] = tmp;
  }

  private static <T> void swap(T[] array, int left, int right) {
    T tmp = array[left];
    array[left] = array[right];
    array[right] = tmp;
  }

  public static <T> T select(
      List<T> list, int start, int length, int k, Comparator<? super T> comparator) {
    if (list == null
        || start < 0
        || length <= 0
        || list.size() < start + length
        || k < 0
        || length <= k) {
      throw new IllegalArgumentException();
    }
    return list.get(selectImpl(list, start, (start + length) - 1, k + start, comparator));
  }

  public static int select(int[] array, int start, int length, int k) {
    if (array == null
        || start < 0
        || length <= 0
        || array.length < start + length
        || k < 0
        || length <= k) {
      throw new IllegalArgumentException();
    }
    return array[selectImpl(array, start, (start + length) - 1, k + start)];
  }

  public static long select(long[] array, int start, int length, int k) {
    if (array == null
        || start < 0
        || length <= 0
        || array.length < start + length
        || k < 0
        || length <= k) {
      throw new IllegalArgumentException();
    }
    return array[selectImpl(array, start, (start + length) - 1, k + start)];
  }

  public static <T> T select(
      T[] array, int start, int length, int k, Comparator<? super T> comparator) {
    if (array == null
        || start < 0
        || length <= 0
        || array.length < start + length
        || k < 0
        || length <= k) {
      throw new IllegalArgumentException();
    }
    return array[selectImpl(array, start, (start + length) - 1, k + start, comparator)];
  }
}
