package com.android.net.module.util;

import android.util.ArrayMap;
import android.util.Pair;
import android.util.SparseArray;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> boolean isEmpty(T[] tArr) {
        return tArr == null || tArr.length == 0;
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static int[] toIntArray(Collection<Integer> collection) {
        int[] iArr = new int[collection.size()];
        Iterator<Integer> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        return iArr;
    }

    public static long[] toLongArray(Collection<Long> collection) {
        long[] jArr = new long[collection.size()];
        Iterator<Long> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            jArr[i] = it.next().longValue();
            i++;
        }
        return jArr;
    }

    public static <T> boolean all(Collection<T> collection, Predicate<T> predicate) {
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (!predicate.test(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean any(Collection<T> collection, Predicate<T> predicate) {
        return indexOf(collection, predicate) >= 0;
    }

    public static <T> int indexOf(Collection<T> collection, Predicate<? super T> predicate) {
        Iterator<T> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static <T> boolean any(SparseArray<T> sparseArray, Predicate<T> predicate) {
        for (int i = 0; i < sparseArray.size(); i++) {
            if (predicate.test(sparseArray.valueAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(short[] sArr, short s) {
        if (sArr == null) {
            return false;
        }
        for (short s2 : sArr) {
            if (s2 == s) {
                return true;
            }
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

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0015, code lost:
    
        r1 = r1 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int indexOfSubArray(byte[] r5, byte[] r6) {
        /*
            r0 = 0
            r1 = r0
        L2:
            int r2 = r5.length
            int r3 = r6.length
            int r2 = r2 - r3
            int r2 = r2 + 1
            if (r1 >= r2) goto L1c
            r2 = r0
        La:
            int r3 = r6.length
            if (r2 >= r3) goto L1b
            int r3 = r1 + r2
            r3 = r5[r3]
            r4 = r6[r2]
            if (r3 == r4) goto L18
            int r1 = r1 + 1
            goto L2
        L18:
            int r2 = r2 + 1
            goto La
        L1b:
            return r1
        L1c:
            r5 = -1
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.net.module.util.CollectionUtils.indexOfSubArray(byte[], byte[]):int");
    }

    public static <T> ArrayList<T> filter(Collection<T> collection, Predicate<T> predicate) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (T t : collection) {
            if (predicate.test(t)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static long total(long[] jArr) {
        long j = 0;
        if (jArr != null) {
            for (long j2 : jArr) {
                j += j2;
            }
        }
        return j;
    }

    public static <T> boolean containsAny(Collection<T> collection, Collection<? extends T> collection2) {
        Iterator<? extends T> it = collection2.iterator();
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean containsAll(Collection<T> collection, Collection<? extends T> collection2) {
        return collection.containsAll(collection2);
    }

    public static <T> T findFirst(Collection<T> collection, Predicate<? super T> predicate) {
        for (T t : collection) {
            if (predicate.test(t)) {
                return t;
            }
        }
        return null;
    }

    public static <T> T findLast(List<T> list, Predicate<? super T> predicate) {
        for (int size = list.size() - 1; size >= 0; size--) {
            T t = list.get(size);
            if (predicate.test(t)) {
                return t;
            }
        }
        return null;
    }

    public static <T> boolean contains(Collection<T> collection, Predicate<? super T> predicate) {
        return -1 != indexOf(collection, predicate);
    }

    public static <T, R> ArrayList<R> map(Collection<T> collection, Function<? super T, ? extends R> function) {
        ArrayList<R> arrayList = new ArrayList<>(collection.size());
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(function.apply(it.next()));
        }
        return arrayList;
    }

    public static <T, R> ArrayList<Pair<T, R>> zip(List<T> list, List<R> list2) {
        int size = list.size();
        if (size != list2.size()) {
            throw new IllegalArgumentException("zip : collections must be the same size");
        }
        ArrayList<Pair<T, R>> arrayList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(new Pair<>(list.get(i), list2.get(i)));
        }
        return arrayList;
    }

    public static <T, R> ArrayMap<T, R> assoc(List<T> list, List<R> list2) {
        int size = list.size();
        if (size != list2.size()) {
            throw new IllegalArgumentException("assoc : collections must be the same size");
        }
        ArrayMap<T, R> arrayMap = new ArrayMap<>(size);
        for (int i = 0; i < size; i++) {
            T t = list.get(i);
            if (arrayMap.containsKey(t)) {
                throw new IllegalArgumentException("assoc : keys may not contain the same value twice");
            }
            arrayMap.put(t, list2.get(i));
        }
        return arrayMap;
    }

    public static <T> int getIndexForValue(SparseArray<T> sparseArray, T t) {
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            T valueAt = sparseArray.valueAt(i);
            if (valueAt == null) {
                if (t == null) {
                    return i;
                }
            } else {
                if (valueAt.equals(t)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static byte[] concatArrays(byte[]... bArr) {
        int i = 0;
        for (byte[] bArr2 : bArr) {
            i += bArr2.length;
        }
        byte[] bArr3 = new byte[i];
        int i2 = 0;
        for (byte[] bArr4 : bArr) {
            System.arraycopy(bArr4, 0, bArr3, i2, bArr4.length);
            i2 += bArr4.length;
        }
        return bArr3;
    }

    public static <T> T[] concatArrays(Class<T> cls, T[]... tArr) {
        int i = 0;
        for (T[] tArr2 : tArr) {
            i += tArr2.length;
        }
        T[] tArr3 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i));
        int i2 = 0;
        for (T[] tArr4 : tArr) {
            System.arraycopy(tArr4, 0, tArr3, i2, tArr4.length);
            i2 += tArr4.length;
        }
        return tArr3;
    }

    public static byte[] prependArray(byte[] bArr, byte... bArr2) {
        return concatArrays(bArr2, bArr);
    }

    public static <T> T[] prependArray(Class<T> cls, T[] tArr, T... tArr2) {
        return (T[]) concatArrays(cls, tArr2, tArr);
    }

    public static byte[] appendArray(byte[] bArr, byte... bArr2) {
        return concatArrays(bArr, bArr2);
    }

    public static <T> T[] appendArray(Class<T> cls, T[] tArr, T... tArr2) {
        return (T[]) concatArrays(cls, tArr, tArr2);
    }
}
