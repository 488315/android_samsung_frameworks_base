package android.util;

/* loaded from: classes4.dex */
class ContainerHelpers {
  ContainerHelpers() {}

  static int binarySearch(int[] array, int size, int value) {
    int lo = 0;
    int hi = size - 1;
    while (lo <= hi) {
      int mid = (lo + hi) >>> 1;
      int midVal = array[mid];
      if (midVal < value) {
        lo = mid + 1;
      } else if (midVal > value) {
        hi = mid - 1;
      } else {
        return mid;
      }
    }
    return ~lo;
  }

  static int binarySearch(long[] array, int size, long value) {
    int lo = 0;
    int hi = size - 1;
    while (lo <= hi) {
      int mid = (lo + hi) >>> 1;
      long midVal = array[mid];
      if (midVal < value) {
        lo = mid + 1;
      } else if (midVal > value) {
        hi = mid - 1;
      } else {
        return mid;
      }
    }
    return ~lo;
  }
}
