package android.util;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class SparseIntArray implements Cloneable {
    private int[] mKeys;
    private int mSize;
    private int[] mValues;

    public SparseIntArray() {
        this(0);
    }

    public SparseIntArray(int i) {
        if (i == 0) {
            this.mKeys = EmptyArray.INT;
            this.mValues = EmptyArray.INT;
        } else {
            int[] iArrNewUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(i);
            this.mKeys = iArrNewUnpaddedIntArray;
            this.mValues = new int[iArrNewUnpaddedIntArray.length];
        }
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SparseIntArray m5539clone() {
        try {
            SparseIntArray sparseIntArray = (SparseIntArray) super.clone();
            try {
                sparseIntArray.mKeys = (int[]) this.mKeys.clone();
                sparseIntArray.mValues = (int[]) this.mValues.clone();
                return sparseIntArray;
            } catch (CloneNotSupportedException unused) {
                return sparseIntArray;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public int get(int i) {
        return get(i, 0);
    }

    public int get(int i, int i2) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        return iBinarySearch < 0 ? i2 : this.mValues[iBinarySearch];
    }

    public void delete(int i) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (iBinarySearch >= 0) {
            removeAt(iBinarySearch);
        }
    }

    public void removeAt(int i) {
        int[] iArr = this.mKeys;
        int i2 = i + 1;
        System.arraycopy(iArr, i2, iArr, i, this.mSize - i2);
        int[] iArr2 = this.mValues;
        System.arraycopy(iArr2, i2, iArr2, i, this.mSize - i2);
        this.mSize--;
    }

    public void put(int i, int i2) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = i2;
            return;
        }
        int i3 = ~iBinarySearch;
        this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, i3, i);
        this.mValues = GrowingArrayUtils.insert(this.mValues, this.mSize, i3, i2);
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

    public int valueAt(int i) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this.mValues[i];
    }

    public void setValueAt(int i, int i2) {
        if (i >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this.mValues[i] = i2;
    }

    public int indexOfKey(int i) {
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
    }

    public int indexOfValue(int i) {
        for (int i2 = 0; i2 < this.mSize; i2++) {
            if (this.mValues[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    public void clear() {
        this.mSize = 0;
    }

    public void append(int i, int i2) {
        int i3 = this.mSize;
        if (i3 != 0 && i <= this.mKeys[i3 - 1]) {
            put(i, i2);
            return;
        }
        this.mKeys = GrowingArrayUtils.append(this.mKeys, i3, i);
        this.mValues = GrowingArrayUtils.append(this.mValues, this.mSize, i2);
        this.mSize++;
    }

    public int[] copyKeys() {
        if (size() == 0) {
            return null;
        }
        return Arrays.copyOf(this.mKeys, size());
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
