package android.text;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import libcore.util.EmptyArray;

/* loaded from: classes4.dex */
class PackedObjectVector<E> {
    private int mColumns;
    private Object[] mValues = EmptyArray.OBJECT;
    private int mRows = 0;
    private int mRowGapStart = 0;
    private int mRowGapLength = 0;

    public PackedObjectVector(int i) {
        this.mColumns = i;
    }

    public E getValue(int i, int i2) {
        if (i >= this.mRowGapStart) {
            i += this.mRowGapLength;
        }
        return (E) this.mValues[(i * this.mColumns) + i2];
    }

    public void setValue(int i, int i2, E e) {
        if (i >= this.mRowGapStart) {
            i += this.mRowGapLength;
        }
        this.mValues[(i * this.mColumns) + i2] = e;
    }

    public void insertAt(int i, E[] eArr) {
        moveRowGapTo(i);
        if (this.mRowGapLength == 0) {
            growBuffer();
        }
        this.mRowGapStart++;
        this.mRowGapLength--;
        int i2 = 0;
        if (eArr == null) {
            while (i2 < this.mColumns) {
                setValue(i, i2, null);
                i2++;
            }
        } else {
            while (i2 < this.mColumns) {
                setValue(i, i2, eArr[i2]);
                i2++;
            }
        }
    }

    public void deleteAt(int i, int i2) {
        moveRowGapTo(i + i2);
        this.mRowGapStart -= i2;
        this.mRowGapLength += i2;
        size();
    }

    public int size() {
        return this.mRows - this.mRowGapLength;
    }

    public int width() {
        return this.mColumns;
    }

    private void growBuffer() {
        Object[] objArrNewUnpaddedObjectArray = ArrayUtils.newUnpaddedObjectArray(GrowingArrayUtils.growSize(size()) * this.mColumns);
        int length = objArrNewUnpaddedObjectArray.length;
        int i = this.mColumns;
        int i2 = length / i;
        int i3 = this.mRows;
        int i4 = this.mRowGapStart;
        int i5 = i3 - (this.mRowGapLength + i4);
        System.arraycopy(this.mValues, 0, objArrNewUnpaddedObjectArray, 0, i * i4);
        Object[] objArr = this.mValues;
        int i6 = this.mRows - i5;
        int i7 = this.mColumns;
        System.arraycopy(objArr, i6 * i7, objArrNewUnpaddedObjectArray, (i2 - i5) * i7, i5 * i7);
        this.mRowGapLength += i2 - this.mRows;
        this.mRows = i2;
        this.mValues = objArrNewUnpaddedObjectArray;
    }

    private void moveRowGapTo(int i) {
        int i2 = this.mRowGapStart;
        if (i == i2) {
            return;
        }
        if (i > i2) {
            int i3 = this.mRowGapLength;
            int i4 = (i + i3) - (i2 + i3);
            int i5 = i2 + i3;
            while (true) {
                int i6 = this.mRowGapStart;
                int i7 = this.mRowGapLength;
                if (i5 >= i6 + i7 + i4) {
                    break;
                }
                int i8 = (i5 - (i7 + i6)) + i6;
                int i9 = 0;
                while (true) {
                    int i10 = this.mColumns;
                    if (i9 < i10) {
                        Object[] objArr = this.mValues;
                        objArr[(i10 * i8) + i9] = objArr[(i5 * i10) + i9];
                        i9++;
                    }
                }
                i5++;
            }
        } else {
            int i11 = i2 - i;
            for (int i12 = (i + i11) - 1; i12 >= i; i12--) {
                int i13 = (((i12 - i) + this.mRowGapStart) + this.mRowGapLength) - i11;
                int i14 = 0;
                while (true) {
                    int i15 = this.mColumns;
                    if (i14 < i15) {
                        Object[] objArr2 = this.mValues;
                        objArr2[(i15 * i13) + i14] = objArr2[(i12 * i15) + i14];
                        i14++;
                    }
                }
            }
        }
        this.mRowGapStart = i;
    }

    public void dump() {
        for (int i = 0; i < this.mRows; i++) {
            int i2 = 0;
            while (true) {
                int i3 = this.mColumns;
                if (i2 < i3) {
                    Object obj = this.mValues[(i3 * i) + i2];
                    int i4 = this.mRowGapStart;
                    if (i < i4 || i >= i4 + this.mRowGapLength) {
                        System.out.print(obj + " ");
                    } else {
                        System.out.print(NavigationBarInflaterView.KEY_CODE_START + obj + ") ");
                    }
                    i2++;
                }
            }
            System.out.print(" << \n");
        }
        System.out.print("-----\n\n");
    }
}
