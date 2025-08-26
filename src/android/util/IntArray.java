package android.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.samsung.android.knox.analytics.database.Contract;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class IntArray implements Cloneable {
    private static final int MIN_CAPACITY_INCREMENT = 12;
    private int mSize;
    private int[] mValues;

    private IntArray(int[] iArr, int i) {
        this.mValues = iArr;
        this.mSize = Preconditions.checkArgumentInRange(i, 0, iArr.length, Contract.DatabaseSize.PATH);
    }

    public IntArray() {
        this(0);
    }

    public IntArray(int i) {
        if (i == 0) {
            this.mValues = EmptyArray.INT;
        } else {
            this.mValues = ArrayUtils.newUnpaddedIntArray(i);
        }
        this.mSize = 0;
    }

    public static IntArray wrap(int[] iArr) {
        return new IntArray(iArr, iArr.length);
    }

    public static IntArray fromArray(int[] iArr, int i) {
        return wrap(Arrays.copyOf(iArr, i));
    }

    public void resize(int i) {
        Preconditions.checkArgumentNonnegative(i);
        int[] iArr = this.mValues;
        if (i <= iArr.length) {
            Arrays.fill(iArr, i, iArr.length, 0);
        } else {
            ensureCapacity(i - this.mSize);
        }
        this.mSize = i;
    }

    public void add(int i) {
        add(this.mSize, i);
    }

    public void add(int i, int i2) {
        ensureCapacity(1);
        int i3 = this.mSize;
        int i4 = i3 - i;
        int i5 = i3 + 1;
        this.mSize = i5;
        ArrayUtils.checkBounds(i5, i);
        if (i4 != 0) {
            int[] iArr = this.mValues;
            System.arraycopy(iArr, i, iArr, i + 1, i4);
        }
        this.mValues[i] = i2;
    }

    public int binarySearch(int i) {
        return ContainerHelpers.binarySearch(this.mValues, this.mSize, i);
    }

    public void addAll(IntArray intArray) {
        int i = intArray.mSize;
        ensureCapacity(i);
        System.arraycopy(intArray.mValues, 0, this.mValues, this.mSize, i);
        this.mSize += i;
    }

    public void addAll(int[] iArr) {
        int length = iArr.length;
        ensureCapacity(length);
        System.arraycopy(iArr, 0, this.mValues, this.mSize, length);
        this.mSize += length;
    }

    private void ensureCapacity(int i) {
        int i2 = this.mSize;
        int i3 = i + i2;
        if (i3 >= this.mValues.length) {
            int i4 = (i2 < 6 ? 12 : i2 >> 1) + i2;
            if (i4 > i3) {
                i3 = i4;
            }
            int[] iArrNewUnpaddedIntArray = ArrayUtils.newUnpaddedIntArray(i3);
            System.arraycopy(this.mValues, 0, iArrNewUnpaddedIntArray, 0, i2);
            this.mValues = iArrNewUnpaddedIntArray;
        }
    }

    public void clear() {
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public IntArray m5509clone() {
        return new IntArray((int[]) this.mValues.clone(), this.mSize);
    }

    public int get(int i) {
        ArrayUtils.checkBounds(this.mSize, i);
        return this.mValues[i];
    }

    public void set(int i, int i2) {
        ArrayUtils.checkBounds(this.mSize, i);
        this.mValues[i] = i2;
    }

    public int indexOf(int i) {
        int i2 = this.mSize;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.mValues[i3] == i) {
                return i3;
            }
        }
        return -1;
    }

    public boolean contains(int i) {
        return indexOf(i) != -1;
    }

    public void remove(int i) {
        ArrayUtils.checkBounds(this.mSize, i);
        int[] iArr = this.mValues;
        System.arraycopy(iArr, i + 1, iArr, i, (this.mSize - i) - 1);
        this.mSize--;
    }

    public int size() {
        return this.mSize;
    }

    public int[] toArray() {
        return Arrays.copyOf(this.mValues, this.mSize);
    }

    public String toString() {
        int i = this.mSize - 1;
        if (i == -1) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        int i2 = 0;
        while (true) {
            sb.append(this.mValues[i2]);
            if (i2 == i) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(", ");
            i2++;
        }
    }
}
