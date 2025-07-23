package android.util;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;

/* loaded from: classes4.dex */
public class SparseBooleanArray implements Cloneable {
    private int[] mKeys;
    private int mSize;
    private boolean[] mValues;

    public SparseBooleanArray() {
        this(0);
    }

    public SparseBooleanArray(int i) {
        if (i == 0) {
            this.mKeys = EmptyArray.INT;
            this.mValues = EmptyArray.BOOLEAN;
        } else {
            int[] newUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(i);
            this.mKeys = newUnpaddedIntArray;
            this.mValues = new boolean[newUnpaddedIntArray.length];
        }
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SparseBooleanArray m5530clone() {
        try {
            SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) super.clone();
            try {
                sparseBooleanArray.mKeys = (int[]) this.mKeys.clone();
                sparseBooleanArray.mValues = (boolean[]) this.mValues.clone();
                return sparseBooleanArray;
            } catch (CloneNotSupportedException unused) {
                return sparseBooleanArray;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public boolean get(int i) {
        return get(i, false);
    }

    public boolean get(int i, boolean z) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        return binarySearch < 0 ? z : this.mValues[binarySearch];
    }

    public void delete(int i) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            int[] iArr = this.mKeys;
            int i2 = binarySearch + 1;
            System.arraycopy(iArr, i2, iArr, binarySearch, this.mSize - i2);
            boolean[] zArr = this.mValues;
            System.arraycopy(zArr, i2, zArr, binarySearch, this.mSize - i2);
            this.mSize--;
        }
    }

    public void removeAt(int i) {
        int[] iArr = this.mKeys;
        int i2 = i + 1;
        System.arraycopy(iArr, i2, iArr, i, this.mSize - i2);
        boolean[] zArr = this.mValues;
        System.arraycopy(zArr, i2, zArr, i, this.mSize - i2);
        this.mSize--;
    }

    public void put(int i, boolean z) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = z;
            return;
        }
        int i2 = ~binarySearch;
        this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, i2, i);
        this.mValues = GrowingArrayUtils.insert(this.mValues, this.mSize, i2, z);
        this.mSize++;
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

    public boolean valueAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this.mValues[i];
    }

    public void setValueAt(int i, boolean z) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this.mValues[i] = z;
    }

    public void setKeyAt(int i, int i2) {
        if (i >= this.mSize) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this.mKeys[i] = i2;
    }

    public int indexOfKey(int i) {
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
    }

    public int indexOfValue(boolean z) {
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == z) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        this.mSize = 0;
    }

    public void append(int i, boolean z) {
        int i2 = this.mSize;
        if (i2 != 0 && i <= this.mKeys[i2 - 1]) {
            put(i, z);
            return;
        }
        this.mKeys = GrowingArrayUtils.append(this.mKeys, i2, i);
        this.mValues = GrowingArrayUtils.append(this.mValues, this.mSize, z);
        this.mSize++;
    }

    public int hashCode() {
        int i = this.mSize;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = ((i * 31) + this.mKeys[i2]) | (this.mValues[i2] ? 1 : 0);
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SparseBooleanArray)) {
            return false;
        }
        SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) obj;
        if (this.mSize != sparseBooleanArray.mSize) {
            return false;
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mKeys[i] != sparseBooleanArray.mKeys[i] || this.mValues[i] != sparseBooleanArray.mValues[i]) {
                return false;
            }
        }
        return true;
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
