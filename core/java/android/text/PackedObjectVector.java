package android.text;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;

import libcore.util.EmptyArray;

class PackedObjectVector<E> {
    private int mColumns;
    private Object[] mValues = EmptyArray.OBJECT;
    private int mRows = 0;
    private int mRowGapStart = 0;
    private int mRowGapLength = this.mRows;

    public PackedObjectVector(int columns) {
        this.mColumns = columns;
    }

    public E getValue(int i, int i2) {
        if (i >= this.mRowGapStart) {
            i += this.mRowGapLength;
        }
        return (E) this.mValues[(this.mColumns * i) + i2];
    }

    public void setValue(int row, int column, E value) {
        if (row >= this.mRowGapStart) {
            row += this.mRowGapLength;
        }
        this.mValues[(this.mColumns * row) + column] = value;
    }

    public void insertAt(int row, E[] values) {
        moveRowGapTo(row);
        if (this.mRowGapLength == 0) {
            growBuffer();
        }
        this.mRowGapStart++;
        this.mRowGapLength--;
        if (values == null) {
            for (int i = 0; i < this.mColumns; i++) {
                setValue(row, i, null);
            }
            return;
        }
        for (int i2 = 0; i2 < this.mColumns; i2++) {
            setValue(row, i2, values[i2]);
        }
    }

    public void deleteAt(int row, int count) {
        moveRowGapTo(row + count);
        this.mRowGapStart -= count;
        this.mRowGapLength += count;
        size();
    }

    public int size() {
        return this.mRows - this.mRowGapLength;
    }

    public int width() {
        return this.mColumns;
    }

    private void growBuffer() {
        Object[] newvalues =
                ArrayUtils.newUnpaddedObjectArray(
                        GrowingArrayUtils.growSize(size()) * this.mColumns);
        int newsize = newvalues.length / this.mColumns;
        int after = this.mRows - (this.mRowGapStart + this.mRowGapLength);
        System.arraycopy(this.mValues, 0, newvalues, 0, this.mColumns * this.mRowGapStart);
        System.arraycopy(
                this.mValues,
                (this.mRows - after) * this.mColumns,
                newvalues,
                (newsize - after) * this.mColumns,
                this.mColumns * after);
        this.mRowGapLength += newsize - this.mRows;
        this.mRows = newsize;
        this.mValues = newvalues;
    }

    private void moveRowGapTo(int where) {
        if (where == this.mRowGapStart) {
            return;
        }
        if (where > this.mRowGapStart) {
            int moving = (this.mRowGapLength + where) - (this.mRowGapStart + this.mRowGapLength);
            for (int i = this.mRowGapStart + this.mRowGapLength;
                    i < this.mRowGapStart + this.mRowGapLength + moving;
                    i++) {
                int destrow = (i - (this.mRowGapStart + this.mRowGapLength)) + this.mRowGapStart;
                for (int j = 0; j < this.mColumns; j++) {
                    Object val = this.mValues[(this.mColumns * i) + j];
                    this.mValues[(this.mColumns * destrow) + j] = val;
                }
            }
        } else {
            int moving2 = this.mRowGapStart - where;
            for (int i2 = (where + moving2) - 1; i2 >= where; i2--) {
                int destrow2 = (((i2 - where) + this.mRowGapStart) + this.mRowGapLength) - moving2;
                for (int j2 = 0; j2 < this.mColumns; j2++) {
                    Object val2 = this.mValues[(this.mColumns * i2) + j2];
                    this.mValues[(this.mColumns * destrow2) + j2] = val2;
                }
            }
        }
        this.mRowGapStart = where;
    }

    public void dump() {
        for (int i = 0; i < this.mRows; i++) {
            for (int j = 0; j < this.mColumns; j++) {
                Object val = this.mValues[(this.mColumns * i) + j];
                if (i < this.mRowGapStart || i >= this.mRowGapStart + this.mRowGapLength) {
                    System.out.print(val + " ");
                } else {
                    System.out.print(NavigationBarInflaterView.KEY_CODE_START + val + ") ");
                }
            }
            System.out.print(" << \n");
        }
        System.out.print("-----\n\n");
    }
}
