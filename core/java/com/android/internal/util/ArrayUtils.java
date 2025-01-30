package com.android.internal.util;

import android.util.ArraySet;
import dalvik.system.VMRuntime;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import libcore.util.EmptyArray;

/* loaded from: classes5.dex */
public class ArrayUtils {
    private static final int CACHE_SIZE = 73;
    private static Object[] sCache = new Object[73];
    public static final File[] EMPTY_FILE = new File[0];

    private ArrayUtils() {
    }

    public static byte[] newUnpaddedByteArray(int minLen) {
        return (byte[]) VMRuntime.getRuntime().newUnpaddedArray(Byte.TYPE, minLen);
    }

    public static char[] newUnpaddedCharArray(int minLen) {
        return (char[]) VMRuntime.getRuntime().newUnpaddedArray(Character.TYPE, minLen);
    }

    public static int[] newUnpaddedIntArray(int minLen) {
        return (int[]) VMRuntime.getRuntime().newUnpaddedArray(Integer.TYPE, minLen);
    }

    public static boolean[] newUnpaddedBooleanArray(int minLen) {
        return (boolean[]) VMRuntime.getRuntime().newUnpaddedArray(Boolean.TYPE, minLen);
    }

    public static long[] newUnpaddedLongArray(int minLen) {
        return (long[]) VMRuntime.getRuntime().newUnpaddedArray(Long.TYPE, minLen);
    }

    public static float[] newUnpaddedFloatArray(int minLen) {
        return (float[]) VMRuntime.getRuntime().newUnpaddedArray(Float.TYPE, minLen);
    }

    public static Object[] newUnpaddedObjectArray(int minLen) {
        return (Object[]) VMRuntime.getRuntime().newUnpaddedArray(Object.class, minLen);
    }

    public static <T> T[] newUnpaddedArray(Class<T> cls, int i) {
        return (T[]) ((Object[]) VMRuntime.getRuntime().newUnpaddedArray(cls, i));
    }

    public static boolean equals(byte[] array1, byte[] array2, int length) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }
        if (array1 == array2) {
            return true;
        }
        if (array1 == null || array2 == null || array1.length < length || array2.length < length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    public static <T> T[] emptyArray(Class<T> cls) {
        if (cls == Object.class) {
            return (T[]) EmptyArray.OBJECT;
        }
        int hashCode = (cls.hashCode() & Integer.MAX_VALUE) % 73;
        Object obj = sCache[hashCode];
        if (obj == null || obj.getClass().getComponentType() != cls) {
            obj = Array.newInstance((Class<?>) cls, 0);
            sCache[hashCode] = obj;
        }
        return (T[]) ((Object[]) obj);
    }

    public static <T> T[] emptyIfNull(T[] tArr, Class<T> cls) {
        return tArr != null ? tArr : (T[]) emptyArray(cls);
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

    public static int size(Object[] array) {
        if (array == null) {
            return 0;
        }
        return array.length;
    }

    public static int size(Collection<?> collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static int size(Map<?, ?> map) {
        if (map == null) {
            return 0;
        }
        return map.size();
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

    public static <T> boolean containsAll(T[] array, T[] check) {
        if (check == null) {
            return true;
        }
        for (T checkItem : check) {
            if (!contains(array, checkItem)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean containsAny(T[] array, T[] check) {
        if (check == null) {
            return false;
        }
        for (T checkItem : check) {
            if (contains(array, checkItem)) {
                return true;
            }
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

    public static <T> boolean containsAll(char[] array, char[] check) {
        if (check == null) {
            return true;
        }
        for (char checkItem : check) {
            if (!contains(array, checkItem)) {
                return false;
            }
        }
        return true;
    }

    public static long total(long[] array) {
        long total = 0;
        if (array != null) {
            for (long value : array) {
                total += value;
            }
        }
        return total;
    }

    @Deprecated
    public static int[] convertToIntArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).intValue();
        }
        return array;
    }

    public static int[] convertToIntArray(ArraySet<Integer> set) {
        int size = set.size();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = set.valueAt(i).intValue();
        }
        return array;
    }

    public static long[] convertToLongArray(int[] intArray) {
        if (intArray == null) {
            return null;
        }
        long[] array = new long[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            array[i] = intArray[i];
        }
        return array;
    }

    public static <T> T[] concat(Class<T> cls, T[]... tArr) {
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

    public static byte[] concat(byte[]... arrays) {
        if (arrays == null) {
            return new byte[0];
        }
        int totalLength = 0;
        for (byte[] a : arrays) {
            if (a != null) {
                totalLength += a.length;
            }
        }
        byte[] result = new byte[totalLength];
        int pos = 0;
        for (byte[] a2 : arrays) {
            if (a2 != null) {
                System.arraycopy(a2, 0, result, pos, a2.length);
                pos += a2.length;
            }
        }
        return result;
    }

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

    public static <T> T[] removeElement(Class<T> cls, T[] tArr, T t) {
        if (tArr != null) {
            if (!contains(tArr, t)) {
                return tArr;
            }
            int length = tArr.length;
            for (int i = 0; i < length; i++) {
                if (Objects.equals(tArr[i], t)) {
                    if (length == 1) {
                        return null;
                    }
                    T[] tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, length - 1));
                    System.arraycopy(tArr, 0, tArr2, 0, i);
                    System.arraycopy(tArr, i + 1, tArr2, i, (length - i) - 1);
                    return tArr2;
                }
            }
        }
        return tArr;
    }

    public static int[] appendInt(int[] cur, int val, boolean allowDuplicates) {
        if (cur == null) {
            return new int[]{val};
        }
        int N = cur.length;
        if (!allowDuplicates) {
            for (int i : cur) {
                if (i == val) {
                    return cur;
                }
            }
        }
        int i2 = N + 1;
        int[] ret = new int[i2];
        System.arraycopy(cur, 0, ret, 0, N);
        ret[N] = val;
        return ret;
    }

    public static int[] appendInt(int[] cur, int val) {
        return appendInt(cur, val, false);
    }

    public static int[] removeInt(int[] cur, int val) {
        if (cur == null) {
            return null;
        }
        int N = cur.length;
        for (int i = 0; i < N; i++) {
            if (cur[i] == val) {
                int[] ret = new int[N - 1];
                if (i > 0) {
                    System.arraycopy(cur, 0, ret, 0, i);
                }
                if (i < N - 1) {
                    System.arraycopy(cur, i + 1, ret, i, (N - i) - 1);
                }
                return ret;
            }
        }
        return cur;
    }

    public static String[] removeString(String[] cur, String val) {
        if (cur == null) {
            return null;
        }
        int N = cur.length;
        for (int i = 0; i < N; i++) {
            if (Objects.equals(cur[i], val)) {
                String[] ret = new String[N - 1];
                if (i > 0) {
                    System.arraycopy(cur, 0, ret, 0, i);
                }
                if (i < N - 1) {
                    System.arraycopy(cur, i + 1, ret, i, (N - i) - 1);
                }
                return ret;
            }
        }
        return cur;
    }

    public static long[] appendLong(long[] cur, long val, boolean allowDuplicates) {
        if (cur == null) {
            return new long[]{val};
        }
        int N = cur.length;
        if (!allowDuplicates) {
            for (long j : cur) {
                if (j == val) {
                    return cur;
                }
            }
        }
        int i = N + 1;
        long[] ret = new long[i];
        System.arraycopy(cur, 0, ret, 0, N);
        ret[N] = val;
        return ret;
    }

    public static long[] appendLong(long[] cur, long val) {
        return appendLong(cur, val, false);
    }

    public static long[] removeLong(long[] cur, long val) {
        if (cur == null) {
            return null;
        }
        int N = cur.length;
        for (int i = 0; i < N; i++) {
            if (cur[i] == val) {
                long[] ret = new long[N - 1];
                if (i > 0) {
                    System.arraycopy(cur, 0, ret, 0, i);
                }
                if (i < N - 1) {
                    System.arraycopy(cur, i + 1, ret, i, (N - i) - 1);
                }
                return ret;
            }
        }
        return cur;
    }

    public static long[] cloneOrNull(long[] array) {
        if (array != null) {
            return (long[]) array.clone();
        }
        return null;
    }

    public static <T> T[] cloneOrNull(T[] tArr) {
        if (tArr != null) {
            return (T[]) ((Object[]) tArr.clone());
        }
        return null;
    }

    public static <T> ArraySet<T> cloneOrNull(ArraySet<T> array) {
        if (array != null) {
            return new ArraySet<>((ArraySet) array);
        }
        return null;
    }

    public static <T> ArraySet<T> add(ArraySet<T> cur, T val) {
        if (cur == null) {
            cur = new ArraySet<>();
        }
        cur.add(val);
        return cur;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> ArraySet<T> addAll(ArraySet<T> cur, Collection<T> collection) {
        if (cur == null) {
            cur = new ArraySet<>();
        }
        if (collection != 0) {
            cur.addAll((Collection<? extends T>) collection);
        }
        return cur;
    }

    public static <T> ArraySet<T> remove(ArraySet<T> cur, T val) {
        if (cur == null) {
            return null;
        }
        cur.remove(val);
        if (cur.isEmpty()) {
            return null;
        }
        return cur;
    }

    public static <T> ArrayList<T> add(ArrayList<T> cur, T val) {
        if (cur == null) {
            cur = new ArrayList<>();
        }
        cur.add(val);
        return cur;
    }

    public static <T> ArrayList<T> add(ArrayList<T> cur, int index, T val) {
        if (cur == null) {
            cur = new ArrayList<>();
        }
        cur.add(index, val);
        return cur;
    }

    public static <T> ArrayList<T> remove(ArrayList<T> cur, T val) {
        if (cur == null) {
            return null;
        }
        cur.remove(val);
        if (cur.isEmpty()) {
            return null;
        }
        return cur;
    }

    public static <T> boolean contains(Collection<T> cur, T val) {
        if (cur != null) {
            return cur.contains(val);
        }
        return false;
    }

    public static <T> T[] trimToSize(T[] tArr, int i) {
        if (tArr == null || i == 0) {
            return null;
        }
        if (tArr.length == i) {
            return tArr;
        }
        return (T[]) Arrays.copyOf(tArr, i);
    }

    public static <T> boolean referenceEquals(ArrayList<T> a, ArrayList<T> b) {
        boolean z;
        if (a == b) {
            return true;
        }
        int sizeA = a.size();
        int sizeB = b.size();
        if (a == null || b == null || sizeA != sizeB) {
            return false;
        }
        boolean diff = false;
        for (int i = 0; i < sizeA && !diff; i++) {
            if (a.get(i) != b.get(i)) {
                z = true;
            } else {
                z = false;
            }
            diff |= z;
        }
        return !diff;
    }

    public static <T> int unstableRemoveIf(ArrayList<T> collection, Predicate<T> predicate) {
        if (collection == null) {
            return 0;
        }
        int size = collection.size();
        int leftIdx = 0;
        int rightIdx = size - 1;
        while (leftIdx <= rightIdx) {
            while (leftIdx < size && !predicate.test(collection.get(leftIdx))) {
                leftIdx++;
            }
            while (rightIdx > leftIdx && predicate.test(collection.get(rightIdx))) {
                rightIdx--;
            }
            if (leftIdx >= rightIdx) {
                break;
            }
            Collections.swap(collection, leftIdx, rightIdx);
            leftIdx++;
            rightIdx--;
        }
        for (int i = size - 1; i >= leftIdx; i--) {
            collection.remove(i);
        }
        int i2 = size - leftIdx;
        return i2;
    }

    public static int[] defeatNullable(int[] val) {
        return val != null ? val : EmptyArray.INT;
    }

    public static String[] defeatNullable(String[] val) {
        return val != null ? val : EmptyArray.STRING;
    }

    public static File[] defeatNullable(File[] val) {
        return val != null ? val : EMPTY_FILE;
    }

    public static void checkBounds(int len, int index) {
        if (index < 0 || len <= index) {
            throw new ArrayIndexOutOfBoundsException("length=" + len + "; index=" + index);
        }
    }

    public static void throwsIfOutOfBounds(int len, int offset, int count) {
        if (len < 0) {
            throw new ArrayIndexOutOfBoundsException("Negative length: " + len);
        }
        if ((offset | count) < 0 || offset > len - count) {
            throw new ArrayIndexOutOfBoundsException("length=" + len + "; regionStart=" + offset + "; regionLength=" + count);
        }
    }

    public static <T> T[] filterNotNull(T[] val, IntFunction<T[]> arrayConstructor) {
        int nullCount = 0;
        int size = size(val);
        for (int i = 0; i < size; i++) {
            if (val[i] == null) {
                nullCount++;
            }
        }
        if (nullCount == 0) {
            return val;
        }
        T[] result = arrayConstructor.apply(size - nullCount);
        int outIdx = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (val[i2] != null) {
                result[outIdx] = val[i2];
                outIdx++;
            }
        }
        return result;
    }

    public static <T> T[] filter(T[] items, IntFunction<T[]> arrayConstructor, Predicate<T> predicate) {
        if (isEmpty(items)) {
            return items;
        }
        int matchesCount = 0;
        int size = size(items);
        boolean[] tests = new boolean[size];
        for (int i = 0; i < size; i++) {
            tests[i] = predicate.test(items[i]);
            if (tests[i]) {
                matchesCount++;
            }
        }
        int i2 = items.length;
        if (matchesCount == i2) {
            return items;
        }
        T[] result = arrayConstructor.apply(matchesCount);
        if (matchesCount == 0) {
            return result;
        }
        int outIdx = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (tests[i3]) {
                result[outIdx] = items[i3];
                outIdx++;
            }
        }
        return result;
    }

    public static boolean startsWith(byte[] cur, byte[] val) {
        if (cur == null || val == null || cur.length < val.length) {
            return false;
        }
        for (int i = 0; i < val.length; i++) {
            if (cur[i] != val[i]) {
                return false;
            }
        }
        return true;
    }

    public static <T> T find(T[] items, Predicate<T> predicate) {
        if (isEmpty(items)) {
            return null;
        }
        for (T item : items) {
            if (predicate.test(item)) {
                return item;
            }
        }
        return null;
    }

    public static String deepToString(Object value) {
        if (value != null && value.getClass().isArray()) {
            if (value.getClass() == boolean[].class) {
                return Arrays.toString((boolean[]) value);
            }
            if (value.getClass() == byte[].class) {
                return Arrays.toString((byte[]) value);
            }
            if (value.getClass() == char[].class) {
                return Arrays.toString((char[]) value);
            }
            if (value.getClass() == double[].class) {
                return Arrays.toString((double[]) value);
            }
            if (value.getClass() == float[].class) {
                return Arrays.toString((float[]) value);
            }
            if (value.getClass() == int[].class) {
                return Arrays.toString((int[]) value);
            }
            if (value.getClass() == long[].class) {
                return Arrays.toString((long[]) value);
            }
            if (value.getClass() == short[].class) {
                return Arrays.toString((short[]) value);
            }
            return Arrays.deepToString((Object[]) value);
        }
        return String.valueOf(value);
    }

    public static <T> T getOrNull(T[] items, int i) {
        if (items == null || items.length <= i) {
            return null;
        }
        return items[i];
    }

    public static <T> T firstOrNull(T[] items) {
        if (items.length > 0) {
            return items[0];
        }
        return null;
    }

    public static <T> List<T> toList(T[] array) {
        List<T> list = new ArrayList<>(array.length);
        for (T item : array) {
            list.add(item);
        }
        return list;
    }
}
