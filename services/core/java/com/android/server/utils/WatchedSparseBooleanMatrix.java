package com.android.server.utils;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WatchedSparseBooleanMatrix extends WatchableImpl implements Snappable {
    static final int STEP = 64;
    public boolean[] mInUse;
    public int[] mKeys;
    public int[] mMap;
    public int mOrder;
    public int mSize;
    public int[] mValues;

    public WatchedSparseBooleanMatrix() {
        this.mOrder = 64;
        if (64 % 64 != 0) {
            this.mOrder = 128;
        }
        int i = this.mOrder;
        if (i < 64 || i % 64 != 0) {
            throw new RuntimeException(AmFmBandRange$$ExternalSyntheticOutline0.m(this.mOrder, new StringBuilder("mOrder is "), " initCap is 64"));
        }
        this.mInUse = ArrayUtils.newUnpaddedBooleanArray(i);
        this.mKeys = ArrayUtils.newUnpaddedIntArray(this.mOrder);
        this.mMap = ArrayUtils.newUnpaddedIntArray(this.mOrder);
        int i2 = this.mOrder;
        this.mValues = ArrayUtils.newUnpaddedIntArray((i2 * i2) / 32);
        this.mSize = 0;
    }

    public static int binarySearch(int[] iArr, int i, int i2) {
        int i3 = i - 1;
        int i4 = 0;
        while (i4 <= i3) {
            int i5 = (i4 + i3) >>> 1;
            int i6 = iArr[i5];
            if (i6 < i2) {
                i4 = i5 + 1;
            } else {
                if (i6 <= i2) {
                    return i5;
                }
                i3 = i5 - 1;
            }
        }
        return ~i4;
    }

    public final void compact() {
        int i;
        int i2 = this.mSize;
        if (i2 != 0 && i2 != this.mOrder) {
            while (true) {
                int nextFree = nextFree(false);
                if (nextFree >= this.mSize) {
                    break;
                }
                this.mInUse[nextFree] = true;
                int i3 = this.mOrder - 1;
                while (true) {
                    if (i3 < 0) {
                        i = -1;
                        break;
                    } else {
                        if (this.mInUse[i3]) {
                            i = 0;
                            while (i < this.mSize) {
                                if (this.mMap[i] != i3) {
                                    i++;
                                }
                            }
                            throw new IndexOutOfBoundsException();
                        }
                        i3--;
                    }
                }
                int[] iArr = this.mMap;
                int i4 = iArr[i];
                this.mInUse[i4] = false;
                iArr[i] = nextFree;
                int[] iArr2 = this.mValues;
                int i5 = this.mOrder;
                System.arraycopy(iArr2, (i4 * i5) / 32, iArr2, (nextFree * i5) / 32, i5 / 32);
                int i6 = i4 / 32;
                int i7 = 1 << (i4 % 32);
                int i8 = nextFree / 32;
                int i9 = 1 << (nextFree % 32);
                int i10 = 0;
                while (true) {
                    int i11 = this.mOrder;
                    if (i10 < i11) {
                        int[] iArr3 = this.mValues;
                        if ((iArr3[i6] & i7) == 0) {
                            iArr3[i8] = iArr3[i8] & (~i9);
                        } else {
                            iArr3[i8] = iArr3[i8] | i9;
                        }
                        int i12 = i11 / 32;
                        i6 += i12;
                        i8 += i12;
                        i10++;
                    }
                }
            }
        }
        int i13 = this.mOrder;
        int i14 = (i13 - this.mSize) / 64;
        if (i14 > 0) {
            resizeMatrix(i13 - (i14 * 64));
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WatchedSparseBooleanMatrix)) {
            return false;
        }
        WatchedSparseBooleanMatrix watchedSparseBooleanMatrix = (WatchedSparseBooleanMatrix) obj;
        if (this.mSize != watchedSparseBooleanMatrix.mSize || !Arrays.equals(this.mKeys, watchedSparseBooleanMatrix.mKeys)) {
            return false;
        }
        for (int i = 0; i < this.mSize; i++) {
            int i2 = this.mMap[i];
            for (int i3 = 0; i3 < this.mSize; i3++) {
                int i4 = this.mMap[i3];
                if (valueAtInternal(i2, i4) != watchedSparseBooleanMatrix.valueAtInternal(i2, i4)) {
                    return false;
                }
            }
        }
        return true;
    }

    public final int hashCode() {
        int hashCode = Arrays.hashCode(this.mMap) + ((Arrays.hashCode(this.mKeys) + (this.mSize * 31)) * 31);
        for (int i = 0; i < this.mSize; i++) {
            int i2 = this.mMap[i];
            for (int i3 = 0; i3 < this.mSize; i3++) {
                hashCode = (hashCode * 31) + (valueAtInternal(i2, this.mMap[i3]) ? 1 : 0);
            }
        }
        return hashCode;
    }

    public final int indexOfKey(int i) {
        return binarySearch(this.mKeys, this.mSize, i);
    }

    /* renamed from: indexOfKey, reason: collision with other method in class */
    public final void m1027indexOfKey(int i) {
        int binarySearch = binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch < 0) {
            int i2 = ~binarySearch;
            int i3 = this.mSize;
            int i4 = this.mOrder;
            if (i3 >= i4) {
                resizeMatrix(i4 + 64);
            }
            int nextFree = nextFree(true);
            this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, i2, i);
            this.mMap = GrowingArrayUtils.insert(this.mMap, this.mSize, i2, nextFree);
            this.mSize++;
            int i5 = this.mOrder / 32;
            int i6 = nextFree / 32;
            int i7 = ~(1 << (nextFree % 32));
            Arrays.fill(this.mValues, nextFree * i5, (nextFree + 1) * i5, 0);
            for (int i8 = 0; i8 < this.mSize; i8++) {
                int[] iArr = this.mValues;
                int i9 = (i8 * i5) + i6;
                iArr[i9] = iArr[i9] & i7;
            }
        }
    }

    public String[] matrixToStringCooked() {
        String[] strArr = new String[this.mSize];
        int i = 0;
        while (true) {
            int i2 = this.mSize;
            if (i >= i2) {
                return strArr;
            }
            int i3 = this.mMap[i];
            StringBuilder sb = new StringBuilder(i2);
            for (int i4 = 0; i4 < this.mSize; i4++) {
                sb.append(valueAtInternal(i3, this.mMap[i4]) ? "1" : "0");
            }
            strArr[i] = sb.substring(0);
            i++;
        }
    }

    public String[] matrixToStringMeta() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mSize; i++) {
            sb.append(this.mKeys[i]);
            if (i < this.mSize - 1) {
                sb.append(" ");
            }
        }
        String substring = sb.substring(0);
        StringBuilder sb2 = new StringBuilder();
        for (int i2 = 0; i2 < this.mSize; i2++) {
            sb2.append(this.mMap[i2]);
            if (i2 < this.mSize - 1) {
                sb2.append(" ");
            }
        }
        String substring2 = sb2.substring(0);
        StringBuilder sb3 = new StringBuilder();
        for (int i3 = 0; i3 < this.mOrder; i3++) {
            sb3.append(this.mInUse[i3] ? "1" : "0");
        }
        return new String[]{substring, substring2, sb3.substring(0)};
    }

    public String[] matrixToStringRaw() {
        String[] strArr = new String[this.mOrder];
        int i = 0;
        while (true) {
            int i2 = this.mOrder;
            if (i >= i2) {
                return strArr;
            }
            StringBuilder sb = new StringBuilder(i2);
            for (int i3 = 0; i3 < this.mOrder; i3++) {
                sb.append(valueAtInternal(i, i3) ? "1" : "0");
            }
            strArr[i] = sb.substring(0);
            i++;
        }
    }

    public final int nextFree(boolean z) {
        int i = 0;
        while (true) {
            boolean[] zArr = this.mInUse;
            if (i >= zArr.length) {
                throw new RuntimeException();
            }
            if (!zArr[i]) {
                zArr[i] = z;
                return i;
            }
            i++;
        }
    }

    public final void put(int i, int i2, boolean z) {
        int indexOfKey = indexOfKey(i);
        int indexOfKey2 = indexOfKey(i2);
        if (indexOfKey < 0 || indexOfKey2 < 0) {
            if (indexOfKey < 0) {
                m1027indexOfKey(i);
            }
            if (indexOfKey2 < 0) {
                m1027indexOfKey(i2);
            }
            indexOfKey = indexOfKey(i);
            indexOfKey2 = indexOfKey(i2);
        }
        if (indexOfKey < 0 || indexOfKey2 < 0) {
            throw new RuntimeException("matrix overflow");
        }
        validateIndex(indexOfKey);
        validateIndex(indexOfKey2);
        int[] iArr = this.mMap;
        int i3 = iArr[indexOfKey];
        int i4 = (i3 * this.mOrder) + iArr[indexOfKey2];
        int i5 = i4 / 32;
        int i6 = 1 << (i4 % 32);
        if (z) {
            int[] iArr2 = this.mValues;
            iArr2[i5] = i6 | iArr2[i5];
        } else {
            int[] iArr3 = this.mValues;
            iArr3[i5] = (~i6) & iArr3[i5];
        }
        dispatchChange(this);
    }

    public final void removeAt(int i) {
        validateIndex(i);
        this.mInUse[this.mMap[i]] = false;
        int[] iArr = this.mKeys;
        int i2 = i + 1;
        System.arraycopy(iArr, i2, iArr, i, this.mSize - i2);
        int[] iArr2 = this.mKeys;
        int i3 = this.mSize;
        iArr2[i3 - 1] = 0;
        int[] iArr3 = this.mMap;
        System.arraycopy(iArr3, i2, iArr3, i, i3 - i2);
        int[] iArr4 = this.mMap;
        int i4 = this.mSize - 1;
        iArr4[i4] = 0;
        this.mSize = i4;
        dispatchChange(this);
    }

    public final void removeRange(int i, int i2) {
        if (i2 < i) {
            throw new ArrayIndexOutOfBoundsException("toIndex < fromIndex");
        }
        int i3 = i2 - i;
        if (i3 == 0) {
            return;
        }
        validateIndex(i);
        validateIndex(i2 - 1);
        for (int i4 = i; i4 < i2; i4++) {
            this.mInUse[this.mMap[i4]] = false;
        }
        int[] iArr = this.mKeys;
        System.arraycopy(iArr, i2, iArr, i, this.mSize - i2);
        int[] iArr2 = this.mMap;
        System.arraycopy(iArr2, i2, iArr2, i, this.mSize - i2);
        int i5 = this.mSize - i3;
        while (true) {
            int i6 = this.mSize;
            if (i5 >= i6) {
                this.mSize = i6 - i3;
                dispatchChange(this);
                return;
            } else {
                this.mKeys[i5] = 0;
                this.mMap[i5] = 0;
                i5++;
            }
        }
    }

    public final void resizeMatrix(int i) {
        if (i % 64 != 0) {
            throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "matrix order ", " is not a multiple of 64"));
        }
        int min = Math.min(this.mOrder, i);
        boolean[] newUnpaddedBooleanArray = ArrayUtils.newUnpaddedBooleanArray(i);
        System.arraycopy(this.mInUse, 0, newUnpaddedBooleanArray, 0, min);
        int[] newUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(i);
        System.arraycopy(this.mMap, 0, newUnpaddedIntArray, 0, min);
        int[] newUnpaddedIntArray2 = ArrayUtils.newUnpaddedIntArray(i);
        System.arraycopy(this.mKeys, 0, newUnpaddedIntArray2, 0, min);
        int[] newUnpaddedIntArray3 = ArrayUtils.newUnpaddedIntArray((i * i) / 32);
        for (int i2 = 0; i2 < min; i2++) {
            System.arraycopy(this.mValues, (this.mOrder * i2) / 32, newUnpaddedIntArray3, (i * i2) / 32, min / 32);
        }
        this.mInUse = newUnpaddedBooleanArray;
        this.mMap = newUnpaddedIntArray;
        this.mKeys = newUnpaddedIntArray2;
        this.mValues = newUnpaddedIntArray3;
        this.mOrder = i;
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        WatchedSparseBooleanMatrix watchedSparseBooleanMatrix = new WatchedSparseBooleanMatrix();
        watchedSparseBooleanMatrix.mOrder = this.mOrder;
        watchedSparseBooleanMatrix.mSize = this.mSize;
        watchedSparseBooleanMatrix.mKeys = (int[]) this.mKeys.clone();
        watchedSparseBooleanMatrix.mMap = (int[]) this.mMap.clone();
        watchedSparseBooleanMatrix.mInUse = (boolean[]) this.mInUse.clone();
        watchedSparseBooleanMatrix.mValues = (int[]) this.mValues.clone();
        return watchedSparseBooleanMatrix;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append(this.mSize);
        sb.append("x");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mSize, sb, "}");
    }

    public final void validateIndex(int i) {
        if (i >= this.mSize) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
    }

    public final boolean valueAtInternal(int i, int i2) {
        int i3 = (i * this.mOrder) + i2;
        return (this.mValues[i3 / 32] & (1 << (i3 % 32))) != 0;
    }
}
