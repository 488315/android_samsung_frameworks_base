package com.android.internal.util;

import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public final class QuickSelect {
    private static <T> int selectImpl(List<T> list, int i, int i2, int i3, Comparator<? super T> comparator) {
        while (i != i2) {
            int iPartition = partition(list, i, i2, (i + i2) >> 1, comparator);
            if (i3 == iPartition) {
                return i3;
            }
            if (i3 < iPartition) {
                i2 = iPartition - 1;
            } else {
                i = iPartition + 1;
            }
        }
        return i;
    }

    private static int selectImpl(int[] iArr, int i, int i2, int i3) {
        while (i != i2) {
            int iPartition = partition(iArr, i, i2, (i + i2) >> 1);
            if (i3 == iPartition) {
                return i3;
            }
            if (i3 < iPartition) {
                i2 = iPartition - 1;
            } else {
                i = iPartition + 1;
            }
        }
        return i;
    }

    private static int selectImpl(long[] jArr, int i, int i2, int i3) {
        while (i != i2) {
            int iPartition = partition(jArr, i, i2, (i + i2) >> 1);
            if (i3 == iPartition) {
                return i3;
            }
            if (i3 < iPartition) {
                i2 = iPartition - 1;
            } else {
                i = iPartition + 1;
            }
        }
        return i;
    }

    private static <T> int selectImpl(T[] tArr, int i, int i2, int i3, Comparator<? super T> comparator) {
        while (i != i2) {
            int iPartition = partition(tArr, i, i2, (i + i2) >> 1, comparator);
            if (i3 == iPartition) {
                return i3;
            }
            if (i3 < iPartition) {
                i2 = iPartition - 1;
            } else {
                i = iPartition + 1;
            }
        }
        return i;
    }

    private static <T> int partition(List<T> list, int i, int i2, int i3, Comparator<? super T> comparator) {
        T t = list.get(i3);
        swap(list, i2, i3);
        int i4 = i;
        while (i < i2) {
            if (comparator.compare(list.get(i), t) < 0) {
                swap(list, i4, i);
                i4++;
            }
            i++;
        }
        swap(list, i2, i4);
        return i4;
    }

    private static int partition(int[] iArr, int i, int i2, int i3) {
        int i4 = iArr[i3];
        swap(iArr, i2, i3);
        int i5 = i;
        while (i < i2) {
            if (iArr[i] < i4) {
                swap(iArr, i5, i);
                i5++;
            }
            i++;
        }
        swap(iArr, i2, i5);
        return i5;
    }

    private static int partition(long[] jArr, int i, int i2, int i3) {
        long j = jArr[i3];
        swap(jArr, i2, i3);
        int i4 = i;
        while (i < i2) {
            if (jArr[i] < j) {
                swap(jArr, i4, i);
                i4++;
            }
            i++;
        }
        swap(jArr, i2, i4);
        return i4;
    }

    private static <T> int partition(T[] tArr, int i, int i2, int i3, Comparator<? super T> comparator) {
        T t = tArr[i3];
        swap(tArr, i2, i3);
        int i4 = i;
        while (i < i2) {
            if (comparator.compare(tArr[i], t) < 0) {
                swap(tArr, i4, i);
                i4++;
            }
            i++;
        }
        swap(tArr, i2, i4);
        return i4;
    }

    private static <T> void swap(List<T> list, int i, int i2) {
        T t = list.get(i);
        list.set(i, list.get(i2));
        list.set(i2, t);
    }

    private static void swap(int[] iArr, int i, int i2) {
        int i3 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = i3;
    }

    private static void swap(long[] jArr, int i, int i2) {
        long j = jArr[i];
        jArr[i] = jArr[i2];
        jArr[i2] = j;
    }

    private static <T> void swap(T[] tArr, int i, int i2) {
        T t = tArr[i];
        tArr[i] = tArr[i2];
        tArr[i2] = t;
    }

    public static <T> T select(List<T> list, int i, int i2, int i3, Comparator<? super T> comparator) {
        int i4;
        if (list == null || i < 0 || i2 <= 0 || list.size() < (i4 = i + i2) || i3 < 0 || i2 <= i3) {
            throw new IllegalArgumentException();
        }
        return list.get(selectImpl(list, i, i4 - 1, i3 + i, comparator));
    }

    public static int select(int[] iArr, int i, int i2, int i3) {
        int i4;
        if (iArr == null || i < 0 || i2 <= 0 || iArr.length < (i4 = i + i2) || i3 < 0 || i2 <= i3) {
            throw new IllegalArgumentException();
        }
        return iArr[selectImpl(iArr, i, i4 - 1, i3 + i)];
    }

    public static long select(long[] jArr, int i, int i2, int i3) {
        int i4;
        if (jArr == null || i < 0 || i2 <= 0 || jArr.length < (i4 = i + i2) || i3 < 0 || i2 <= i3) {
            throw new IllegalArgumentException();
        }
        return jArr[selectImpl(jArr, i, i4 - 1, i3 + i)];
    }

    public static <T> T select(T[] tArr, int i, int i2, int i3, Comparator<? super T> comparator) {
        int i4;
        if (tArr == null || i < 0 || i2 <= 0 || tArr.length < (i4 = i + i2) || i3 < 0 || i2 <= i3) {
            throw new IllegalArgumentException();
        }
        return tArr[selectImpl(tArr, i, i4 - 1, i3 + i, comparator)];
    }
}
