package android.util;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;

/* loaded from: classes4.dex */
public class SparseLongArray implements Cloneable {
    private int[] mKeys;
    private int mSize;
    private long[] mValues;

    public SparseLongArray() {
        this(0);
    }

    public SparseLongArray(int i) {
        if (i == 0) {
            this.mKeys = EmptyArray.INT;
            this.mValues = EmptyArray.LONG;
        } else {
            long[] newUnpaddedLongArray = ArrayUtils.newUnpaddedLongArray(i);
            this.mValues = newUnpaddedLongArray;
            this.mKeys = new int[newUnpaddedLongArray.length];
        }
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SparseLongArray m5533clone() {
        try {
            SparseLongArray sparseLongArray = (SparseLongArray) super.clone();
            try {
                sparseLongArray.mKeys = (int[]) this.mKeys.clone();
                sparseLongArray.mValues = (long[]) this.mValues.clone();
                return sparseLongArray;
            } catch (CloneNotSupportedException unused) {
                return sparseLongArray;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public long get(int i) {
        return get(i, 0L);
    }

    public long get(int i, long j) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        return binarySearch < 0 ? j : this.mValues[binarySearch];
    }

    public void delete(int i) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            removeAt(binarySearch);
        }
    }

    public void removeAtRange(int i, int i2) {
        int min = Math.min(i2, this.mSize - i);
        int[] iArr = this.mKeys;
        int i3 = i + min;
        System.arraycopy(iArr, i3, iArr, i, this.mSize - i3);
        long[] jArr = this.mValues;
        System.arraycopy(jArr, i3, jArr, i, this.mSize - i3);
        this.mSize -= min;
    }

    public void removeAt(int i) {
        int[] iArr = this.mKeys;
        int i2 = i + 1;
        System.arraycopy(iArr, i2, iArr, i, this.mSize - i2);
        long[] jArr = this.mValues;
        System.arraycopy(jArr, i2, jArr, i, this.mSize - i2);
        this.mSize--;
    }

    public void put(int i, long j) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = j;
            return;
        }
        int i2 = ~binarySearch;
        this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, i2, i);
        this.mValues = GrowingArrayUtils.insert(this.mValues, this.mSize, i2, j);
        this.mSize++;
    }

    public void incrementValue(int i, long j) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            long[] jArr = this.mValues;
            jArr[binarySearch] = jArr[binarySearch] + j;
        } else {
            int i2 = ~binarySearch;
            this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, i2, i);
            this.mValues = GrowingArrayUtils.insert(this.mValues, this.mSize, i2, j);
            this.mSize++;
        }
    }

    public int size() {
        return this.mSize;
    }

    public int keyAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this.mKeys[i];
    }

    public long valueAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this.mValues[i];
    }

    public void setValueAt(int i, long j) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this.mValues[i] = j;
    }

    public int indexOfKey(int i) {
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
    }

    public int indexOfValue(long j) {
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == j) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        this.mSize = 0;
    }

    public void append(int i, long j) {
        int i2 = this.mSize;
        if (i2 != 0 && i <= this.mKeys[i2 - 1]) {
            put(i, j);
            return;
        }
        this.mKeys = GrowingArrayUtils.append(this.mKeys, i2, i);
        this.mValues = GrowingArrayUtils.append(this.mValues, this.mSize, j);
        this.mSize++;
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            sb.append(valueAt(i));
        }
        sb.append('}');
        return sb.toString();
    }
}
