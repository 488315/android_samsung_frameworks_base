package com.android.internal.util.jobs;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.util.ArraySet;
import android.util.EmptyArray;
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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class ArrayUtils {
    private static final int CACHE_SIZE = 73;
    private static Object[] sCache = new Object[73];
    public static final File[] EMPTY_FILE = new File[0];

    private ArrayUtils() {
    }

    public static ArraySet add(ArraySet arraySet, Object obj) {
        if (arraySet == null) {
            arraySet = new ArraySet();
        }
        arraySet.add(obj);
        return arraySet;
    }

    public static ArrayList add(ArrayList arrayList, int i, Object obj) {
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        arrayList.add(i, obj);
        return arrayList;
    }

    public static ArrayList add(ArrayList arrayList, Object obj) {
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        arrayList.add(obj);
        return arrayList;
    }

    public static ArraySet addAll(ArraySet arraySet, Collection collection) {
        if (arraySet == null) {
            arraySet = new ArraySet();
        }
        if (collection != null) {
            arraySet.addAll(collection);
        }
        return arraySet;
    }

    public static boolean[] appendBoolean(boolean[] zArr, boolean z) {
        if (zArr == null) {
            return new boolean[]{z};
        }
        int length = zArr.length;
        boolean[] zArr2 = new boolean[1 + length];
        System.arraycopy(zArr, 0, zArr2, 0, length);
        zArr2[length] = z;
        return zArr2;
    }

    public static Object[] appendElement(Class cls, Object[] objArr, Object obj) {
        return appendElement(cls, objArr, obj, false);
    }

    public static Object[] appendElement(Class cls, Object[] objArr, Object obj, boolean z) {
        Object[] objArr2;
        int i = 0;
        if (objArr == null) {
            objArr2 = (Object[]) Array.newInstance((Class<?>) cls, 1);
        } else {
            if (!z && contains(objArr, obj)) {
                return objArr;
            }
            int length = objArr.length;
            objArr2 = (Object[]) Array.newInstance((Class<?>) cls, length + 1);
            System.arraycopy(objArr, 0, objArr2, 0, length);
            i = length;
        }
        objArr2[i] = obj;
        return objArr2;
    }

    public static int[] appendInt(int[] iArr, int i) {
        return appendInt(iArr, i, false);
    }

    public static int[] appendInt(int[] iArr, int i, boolean z) {
        if (iArr == null) {
            return new int[]{i};
        }
        int length = iArr.length;
        if (!z) {
            for (int i2 : iArr) {
                if (i2 == i) {
                    return iArr;
                }
            }
        }
        int[] iArr2 = new int[length + 1];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        iArr2[length] = i;
        return iArr2;
    }

    public static long[] appendLong(long[] jArr, long j) {
        return appendLong(jArr, j, false);
    }

    public static long[] appendLong(long[] jArr, long j, boolean z) {
        if (jArr == null) {
            return new long[]{j};
        }
        int length = jArr.length;
        if (!z) {
            for (long j2 : jArr) {
                if (j2 == j) {
                    return jArr;
                }
            }
        }
        long[] jArr2 = new long[1 + length];
        System.arraycopy(jArr, 0, jArr2, 0, length);
        jArr2[length] = j;
        return jArr2;
    }

    public static void checkBounds(int i, int i2) {
        if (i2 < 0 || i <= i2) {
            throw new ArrayIndexOutOfBoundsException(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "length=", "; index="));
        }
    }

    public static ArraySet cloneOrNull(ArraySet arraySet) {
        if (arraySet != null) {
            return new ArraySet(arraySet);
        }
        return null;
    }

    public static long[] cloneOrNull(long[] jArr) {
        if (jArr != null) {
            return (long[]) jArr.clone();
        }
        return null;
    }

    public static Object[] cloneOrNull(Object[] objArr) {
        if (objArr != null) {
            return (Object[]) objArr.clone();
        }
        return null;
    }

    public static byte[] concat(byte[]... bArr) {
        if (bArr == null) {
            return new byte[0];
        }
        int i = 0;
        for (byte[] bArr2 : bArr) {
            if (bArr2 != null) {
                i += bArr2.length;
            }
        }
        byte[] bArr3 = new byte[i];
        int i2 = 0;
        for (byte[] bArr4 : bArr) {
            if (bArr4 != null) {
                System.arraycopy(bArr4, 0, bArr3, i2, bArr4.length);
                i2 += bArr4.length;
            }
        }
        return bArr3;
    }

    public static Object[] concat(Class cls, Object[]... objArr) {
        if (objArr == null || objArr.length == 0) {
            return createEmptyArray(cls);
        }
        int i = 0;
        for (Object[] objArr2 : objArr) {
            if (objArr2 != null) {
                i += objArr2.length;
            }
        }
        if (i == 0) {
            return createEmptyArray(cls);
        }
        Object[] objArr3 = (Object[]) Array.newInstance((Class<?>) cls, i);
        int i2 = 0;
        for (Object[] objArr4 : objArr) {
            if (objArr4 != null && objArr4.length != 0) {
                System.arraycopy(objArr4, 0, objArr3, i2, objArr4.length);
                i2 += objArr4.length;
            }
        }
        return objArr3;
    }

    public static boolean contains(Collection collection, Object obj) {
        if (collection != null) {
            return collection.contains(obj);
        }
        return false;
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

    public static boolean contains(Object[] objArr, Object obj) {
        return indexOf(objArr, obj) != -1;
    }

    public static boolean containsAll(char[] cArr, char[] cArr2) {
        if (cArr2 == null) {
            return true;
        }
        for (char c : cArr2) {
            if (!contains(cArr, c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsAll(Object[] objArr, Object[] objArr2) {
        if (objArr2 == null) {
            return true;
        }
        for (Object obj : objArr2) {
            if (!contains(objArr, obj)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsAny(Object[] objArr, Object[] objArr2) {
        if (objArr2 == null) {
            return false;
        }
        for (Object obj : objArr2) {
            if (contains(objArr, obj)) {
                return true;
            }
        }
        return false;
    }

    public static int[] convertToIntArray(ArraySet arraySet) {
        int size = arraySet.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ((Integer) arraySet.valueAt(i)).intValue();
        }
        return iArr;
    }

    @Deprecated
    public static int[] convertToIntArray(List list) {
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = ((Integer) list.get(i)).intValue();
        }
        return iArr;
    }

    public static long[] convertToLongArray(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        long[] jArr = new long[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            jArr[i] = iArr[i];
        }
        return jArr;
    }

    private static Object[] createEmptyArray(Class cls) {
        return cls == String.class ? EmptyArray.STRING : cls == Object.class ? EmptyArray.OBJECT : (Object[]) Array.newInstance((Class<?>) cls, 0);
    }

    public static String deepToString(Object obj) {
        return (obj == null || !obj.getClass().isArray()) ? String.valueOf(obj) : obj.getClass() == boolean[].class ? Arrays.toString((boolean[]) obj) : obj.getClass() == byte[].class ? Arrays.toString((byte[]) obj) : obj.getClass() == char[].class ? Arrays.toString((char[]) obj) : obj.getClass() == double[].class ? Arrays.toString((double[]) obj) : obj.getClass() == float[].class ? Arrays.toString((float[]) obj) : obj.getClass() == int[].class ? Arrays.toString((int[]) obj) : obj.getClass() == long[].class ? Arrays.toString((long[]) obj) : obj.getClass() == short[].class ? Arrays.toString((short[]) obj) : Arrays.deepToString((Object[]) obj);
    }

    public static int[] defeatNullable(int[] iArr) {
        return iArr != null ? iArr : EmptyArray.INT;
    }

    public static File[] defeatNullable(File[] fileArr) {
        return fileArr != null ? fileArr : EMPTY_FILE;
    }

    public static String[] defeatNullable(String[] strArr) {
        return strArr != null ? strArr : EmptyArray.STRING;
    }

    public static Object[] emptyArray(Class cls) {
        if (cls == Object.class) {
            return EmptyArray.OBJECT;
        }
        int hashCode = (cls.hashCode() & Integer.MAX_VALUE) % 73;
        Object obj = sCache[hashCode];
        if (obj == null || obj.getClass().getComponentType() != cls) {
            obj = Array.newInstance((Class<?>) cls, 0);
            sCache[hashCode] = obj;
        }
        return (Object[]) obj;
    }

    public static Object[] emptyIfNull(Object[] objArr, Class cls) {
        return objArr != null ? objArr : emptyArray(cls);
    }

    public static boolean equals(byte[] bArr, byte[] bArr2, int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        if (bArr == bArr2) {
            return true;
        }
        if (bArr == null || bArr2 == null || bArr.length < i || bArr2.length < i) {
            return false;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static Object[] filter(Object[] objArr, IntFunction intFunction, Predicate predicate) {
        if (isEmpty(objArr)) {
            return objArr;
        }
        int size = size(objArr);
        boolean[] zArr = new boolean[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            boolean test = predicate.test(objArr[i2]);
            zArr[i2] = test;
            if (test) {
                i++;
            }
        }
        if (i == objArr.length) {
            return objArr;
        }
        Object[] objArr2 = (Object[]) intFunction.apply(i);
        if (i == 0) {
            return objArr2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            if (zArr[i4]) {
                objArr2[i3] = objArr[i4];
                i3++;
            }
        }
        return objArr2;
    }

    public static Object[] filterNotNull(Object[] objArr, IntFunction intFunction) {
        int size = size(objArr);
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (objArr[i2] == null) {
                i++;
            }
        }
        if (i == 0) {
            return objArr;
        }
        Object[] objArr2 = (Object[]) intFunction.apply(size - i);
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            Object obj = objArr[i4];
            if (obj != null) {
                objArr2[i3] = obj;
                i3++;
            }
        }
        return objArr2;
    }

    public static Object find(Object[] objArr, Predicate predicate) {
        if (isEmpty(objArr)) {
            return null;
        }
        for (Object obj : objArr) {
            if (predicate.test(obj)) {
                return obj;
            }
        }
        return null;
    }

    public static Object firstOrNull(Object[] objArr) {
        if (objArr.length > 0) {
            return objArr[0];
        }
        return null;
    }

    public static Object getOrNull(Object[] objArr, int i) {
        if (objArr == null || objArr.length <= i) {
            return null;
        }
        return objArr[i];
    }

    public static int indexOf(Object[] objArr, Object obj) {
        if (objArr == null) {
            return -1;
        }
        for (int i = 0; i < objArr.length; i++) {
            if (Objects.equals(objArr[i], obj)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    public static boolean isEmpty(int[] iArr) {
        return iArr == null || iArr.length == 0;
    }

    public static boolean isEmpty(long[] jArr) {
        return jArr == null || jArr.length == 0;
    }

    public static boolean isEmpty(Object[] objArr) {
        return objArr == null || objArr.length == 0;
    }

    public static boolean isEmpty(boolean[] zArr) {
        return zArr == null || zArr.length == 0;
    }

    public static Object[] newUnpaddedArray(Class cls, int i) {
        return (Object[]) VMRuntime.getRuntime().newUnpaddedArray(cls, i);
    }

    public static Object[] newUnpaddedArray$ravenwood(Class cls, int i) {
        return (Object[]) Array.newInstance((Class<?>) cls, i);
    }

    public static boolean[] newUnpaddedBooleanArray(int i) {
        return (boolean[]) VMRuntime.getRuntime().newUnpaddedArray(Boolean.TYPE, i);
    }

    public static boolean[] newUnpaddedBooleanArray$ravenwood(int i) {
        return new boolean[i];
    }

    public static byte[] newUnpaddedByteArray(int i) {
        return (byte[]) VMRuntime.getRuntime().newUnpaddedArray(Byte.TYPE, i);
    }

    public static byte[] newUnpaddedByteArray$ravenwood(int i) {
        return new byte[i];
    }

    public static char[] newUnpaddedCharArray(int i) {
        return (char[]) VMRuntime.getRuntime().newUnpaddedArray(Character.TYPE, i);
    }

    public static char[] newUnpaddedCharArray$ravenwood(int i) {
        return new char[i];
    }

    public static float[] newUnpaddedFloatArray(int i) {
        return (float[]) VMRuntime.getRuntime().newUnpaddedArray(Float.TYPE, i);
    }

    public static float[] newUnpaddedFloatArray$ravenwood(int i) {
        return new float[i];
    }

    public static int[] newUnpaddedIntArray(int i) {
        return (int[]) VMRuntime.getRuntime().newUnpaddedArray(Integer.TYPE, i);
    }

    public static int[] newUnpaddedIntArray$ravenwood(int i) {
        return new int[i];
    }

    public static long[] newUnpaddedLongArray(int i) {
        return (long[]) VMRuntime.getRuntime().newUnpaddedArray(Long.TYPE, i);
    }

    public static long[] newUnpaddedLongArray$ravenwood(int i) {
        return new long[i];
    }

    public static Object[] newUnpaddedObjectArray(int i) {
        return (Object[]) VMRuntime.getRuntime().newUnpaddedArray(Object.class, i);
    }

    public static Object[] newUnpaddedObjectArray$ravenwood(int i) {
        return new Object[i];
    }

    public static boolean referenceEquals(ArrayList arrayList, ArrayList arrayList2) {
        if (arrayList == arrayList2) {
            return true;
        }
        int size = arrayList.size();
        if (size != arrayList2.size()) {
            return false;
        }
        boolean z = false;
        for (int i = 0; i < size && !z; i++) {
            z |= arrayList.get(i) != arrayList2.get(i);
        }
        return !z;
    }

    public static ArraySet remove(ArraySet arraySet, Object obj) {
        if (arraySet == null) {
            return null;
        }
        arraySet.remove(obj);
        if (arraySet.isEmpty()) {
            return null;
        }
        return arraySet;
    }

    public static ArrayList remove(ArrayList arrayList, Object obj) {
        if (arrayList == null) {
            return null;
        }
        arrayList.remove(obj);
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    public static Object[] removeElement(Class cls, Object[] objArr, Object obj) {
        if (objArr == null || !contains(objArr, obj)) {
            return objArr;
        }
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            if (Objects.equals(objArr[i], obj)) {
                if (length == 1) {
                    return null;
                }
                Object[] objArr2 = (Object[]) Array.newInstance((Class<?>) cls, length - 1);
                System.arraycopy(objArr, 0, objArr2, 0, i);
                System.arraycopy(objArr, i + 1, objArr2, i, (length - i) - 1);
                return objArr2;
            }
        }
        return objArr;
    }

    public static int[] removeInt(int[] iArr, int i) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (iArr[i2] == i) {
                int i3 = length - 1;
                int[] iArr2 = new int[i3];
                if (i2 > 0) {
                    System.arraycopy(iArr, 0, iArr2, 0, i2);
                }
                if (i2 < i3) {
                    System.arraycopy(iArr, i2 + 1, iArr2, i2, (length - i2) - 1);
                }
                return iArr2;
            }
        }
        return iArr;
    }

    public static long[] removeLong(long[] jArr, long j) {
        if (jArr == null) {
            return null;
        }
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            if (jArr[i] == j) {
                int i2 = length - 1;
                long[] jArr2 = new long[i2];
                if (i > 0) {
                    System.arraycopy(jArr, 0, jArr2, 0, i);
                }
                if (i < i2) {
                    System.arraycopy(jArr, i + 1, jArr2, i, (length - i) - 1);
                }
                return jArr2;
            }
        }
        return jArr;
    }

    public static String[] removeString(String[] strArr, String str) {
        if (strArr == null) {
            return null;
        }
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (Objects.equals(strArr[i], str)) {
                int i2 = length - 1;
                String[] strArr2 = new String[i2];
                if (i > 0) {
                    System.arraycopy(strArr, 0, strArr2, 0, i);
                }
                if (i < i2) {
                    System.arraycopy(strArr, i + 1, strArr2, i, (length - i) - 1);
                }
                return strArr2;
            }
        }
        return strArr;
    }

    public static int size(Collection collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static int size(Map map) {
        if (map == null) {
            return 0;
        }
        return map.size();
    }

    public static int size(Object[] objArr) {
        if (objArr == null) {
            return 0;
        }
        return objArr.length;
    }

    public static boolean startsWith(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null || bArr.length < bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr2.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void throwsIfOutOfBounds(int i, int i2, int i3) {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Negative length: "));
        }
        if ((i2 | i3) < 0 || i2 > i - i3) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "length=", "; regionStart=", "; regionLength=");
            m.append(i3);
            throw new ArrayIndexOutOfBoundsException(m.toString());
        }
    }

    public static List toList(Object[] objArr) {
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            arrayList.add(obj);
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

    public static Object[] trimToSize(Object[] objArr, int i) {
        if (objArr == null || i == 0) {
            return null;
        }
        return objArr.length == i ? objArr : Arrays.copyOf(objArr, i);
    }

    public static int unstableRemoveIf(ArrayList arrayList, Predicate predicate) {
        int i = 0;
        if (arrayList == null) {
            return 0;
        }
        int size = arrayList.size();
        int i2 = size - 1;
        int i3 = i2;
        while (i <= i3) {
            while (i < size && !predicate.test(arrayList.get(i))) {
                i++;
            }
            while (i3 > i && predicate.test(arrayList.get(i3))) {
                i3--;
            }
            if (i >= i3) {
                break;
            }
            Collections.swap(arrayList, i, i3);
            i++;
            i3--;
        }
        while (i2 >= i) {
            arrayList.remove(i2);
            i2--;
        }
        return size - i;
    }
}
