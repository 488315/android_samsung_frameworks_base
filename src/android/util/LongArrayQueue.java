package android.util;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
public class LongArrayQueue {
    private int mHead;
    private int mSize;
    private int mTail;
    private long[] mValues;

    public LongArrayQueue(int i) {
        if (i == 0) {
            this.mValues = EmptyArray.LONG;
        } else {
            this.mValues = ArrayUtils.newUnpaddedLongArray(i);
        }
        this.mSize = 0;
        this.mTail = 0;
        this.mHead = 0;
    }

    public LongArrayQueue() {
        this(16);
    }

    private void grow() {
        int i = this.mSize;
        if (i < this.mValues.length) {
            throw new IllegalStateException("Queue not full yet!");
        }
        long[] jArrNewUnpaddedLongArray = ArrayUtils.newUnpaddedLongArray(GrowingArrayUtils.growSize(i));
        long[] jArr = this.mValues;
        int length = jArr.length;
        int i2 = this.mHead;
        int i3 = length - i2;
        System.arraycopy(jArr, i2, jArrNewUnpaddedLongArray, 0, i3);
        System.arraycopy(this.mValues, 0, jArrNewUnpaddedLongArray, i3, this.mHead);
        this.mValues = jArrNewUnpaddedLongArray;
        this.mHead = 0;
        this.mTail = this.mSize;
    }

    public int size() {
        return this.mSize;
    }

    public void clear() {
        this.mSize = 0;
        this.mTail = 0;
        this.mHead = 0;
    }

    public void addLast(long j) {
        if (this.mSize == this.mValues.length) {
            grow();
        }
        long[] jArr = this.mValues;
        int i = this.mTail;
        jArr[i] = j;
        this.mTail = (i + 1) % jArr.length;
        this.mSize++;
    }

    public long removeFirst() {
        int i = this.mSize;
        if (i == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
        long[] jArr = this.mValues;
        int i2 = this.mHead;
        long j = jArr[i2];
        this.mHead = (i2 + 1) % jArr.length;
        this.mSize = i - 1;
        return j;
    }

    public long get(int i) {
        if (i < 0 || i >= this.mSize) {
            throw new IndexOutOfBoundsException("Index " + i + " not valid for a queue of size " + this.mSize);
        }
        int i2 = this.mHead + i;
        long[] jArr = this.mValues;
        return jArr[i2 % jArr.length];
    }

    public long peekFirst() {
        if (this.mSize == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return this.mValues[this.mHead];
    }

    public long peekLast() {
        if (this.mSize == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
        int length = this.mTail;
        if (length == 0) {
            length = this.mValues.length;
        }
        return this.mValues[length - 1];
    }

    public String toString() {
        if (this.mSize <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 64);
        sb.append('{');
        sb.append(get(0));
        for (int i = 1; i < this.mSize; i++) {
            sb.append(", ");
            sb.append(get(i));
        }
        sb.append('}');
        return sb.toString();
    }
}
