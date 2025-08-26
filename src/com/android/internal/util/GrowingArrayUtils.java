package com.android.internal.util;

/* loaded from: classes4.dex */
public final class GrowingArrayUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public static int growSize(int i) {
        if (i <= 4) {
            return 8;
        }
        return i * 2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Object, java.lang.Object[]] */
    public static <T> T[] append(T[] tArr, int i, T t) {
        if (i + 1 > tArr.length) {
            ?? NewUnpaddedArray = ArrayUtils.newUnpaddedArray(tArr.getClass().getComponentType(), growSize(i));
            System.arraycopy(tArr, 0, NewUnpaddedArray, 0, i);
            tArr = NewUnpaddedArray;
        }
        tArr[i] = t;
        return tArr;
    }

    public static int[] append(int[] iArr, int i, int i2) {
        if (i + 1 > iArr.length) {
            int[] iArrNewUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(growSize(i));
            System.arraycopy(iArr, 0, iArrNewUnpaddedIntArray, 0, i);
            iArr = iArrNewUnpaddedIntArray;
        }
        iArr[i] = i2;
        return iArr;
    }

    public static long[] append(long[] jArr, int i, long j) {
        if (i + 1 > jArr.length) {
            long[] jArrNewUnpaddedLongArray = ArrayUtils.newUnpaddedLongArray(growSize(i));
            System.arraycopy(jArr, 0, jArrNewUnpaddedLongArray, 0, i);
            jArr = jArrNewUnpaddedLongArray;
        }
        jArr[i] = j;
        return jArr;
    }

    public static boolean[] append(boolean[] zArr, int i, boolean z) {
        if (i + 1 > zArr.length) {
            boolean[] zArrNewUnpaddedBooleanArray = ArrayUtils.newUnpaddedBooleanArray(growSize(i));
            System.arraycopy(zArr, 0, zArrNewUnpaddedBooleanArray, 0, i);
            zArr = zArrNewUnpaddedBooleanArray;
        }
        zArr[i] = z;
        return zArr;
    }

    public static float[] append(float[] fArr, int i, float f) {
        if (i + 1 > fArr.length) {
            float[] fArrNewUnpaddedFloatArray = ArrayUtils.newUnpaddedFloatArray(growSize(i));
            System.arraycopy(fArr, 0, fArrNewUnpaddedFloatArray, 0, i);
            fArr = fArrNewUnpaddedFloatArray;
        }
        fArr[i] = f;
        return fArr;
    }

    public static <T> T[] insert(T[] tArr, int i, int i2, T t) {
        if (i + 1 <= tArr.length) {
            System.arraycopy(tArr, i2, tArr, i2 + 1, i - i2);
            tArr[i2] = t;
            return tArr;
        }
        T[] tArr2 = (T[]) ArrayUtils.newUnpaddedArray(tArr.getClass().getComponentType(), growSize(i));
        System.arraycopy(tArr, 0, tArr2, 0, i2);
        tArr2[i2] = t;
        System.arraycopy(tArr, i2, tArr2, i2 + 1, tArr.length - i2);
        return tArr2;
    }

    public static int[] insert(int[] iArr, int i, int i2, int i3) {
        if (i + 1 <= iArr.length) {
            System.arraycopy(iArr, i2, iArr, i2 + 1, i - i2);
            iArr[i2] = i3;
            return iArr;
        }
        int[] iArrNewUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(growSize(i));
        System.arraycopy(iArr, 0, iArrNewUnpaddedIntArray, 0, i2);
        iArrNewUnpaddedIntArray[i2] = i3;
        System.arraycopy(iArr, i2, iArrNewUnpaddedIntArray, i2 + 1, iArr.length - i2);
        return iArrNewUnpaddedIntArray;
    }

    public static long[] insert(long[] jArr, int i, int i2, long j) {
        if (i + 1 <= jArr.length) {
            System.arraycopy(jArr, i2, jArr, i2 + 1, i - i2);
            jArr[i2] = j;
            return jArr;
        }
        long[] jArrNewUnpaddedLongArray = ArrayUtils.newUnpaddedLongArray(growSize(i));
        System.arraycopy(jArr, 0, jArrNewUnpaddedLongArray, 0, i2);
        jArrNewUnpaddedLongArray[i2] = j;
        System.arraycopy(jArr, i2, jArrNewUnpaddedLongArray, i2 + 1, jArr.length - i2);
        return jArrNewUnpaddedLongArray;
    }

    public static boolean[] insert(boolean[] zArr, int i, int i2, boolean z) {
        if (i + 1 <= zArr.length) {
            System.arraycopy(zArr, i2, zArr, i2 + 1, i - i2);
            zArr[i2] = z;
            return zArr;
        }
        boolean[] zArrNewUnpaddedBooleanArray = ArrayUtils.newUnpaddedBooleanArray(growSize(i));
        System.arraycopy(zArr, 0, zArrNewUnpaddedBooleanArray, 0, i2);
        zArrNewUnpaddedBooleanArray[i2] = z;
        System.arraycopy(zArr, i2, zArrNewUnpaddedBooleanArray, i2 + 1, zArr.length - i2);
        return zArrNewUnpaddedBooleanArray;
    }

    private GrowingArrayUtils() {
    }
}
