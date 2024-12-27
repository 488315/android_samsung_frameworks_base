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

    public LongArrayQueue(int initialCapacity) {
        if (initialCapacity == 0) {
            this.mValues = EmptyArray.LONG;
        } else {
            this.mValues = ArrayUtils.newUnpaddedLongArray(initialCapacity);
        }
        this.mSize = 0;
        this.mTail = 0;
        this.mHead = 0;
    }

    public LongArrayQueue() {
        this(16);
    }

    private void grow() {
        if (this.mSize < this.mValues.length) {
            throw new IllegalStateException("Queue not full yet!");
        }
        int newSize = GrowingArrayUtils.growSize(this.mSize);
        long[] newArray = ArrayUtils.newUnpaddedLongArray(newSize);
        int r = this.mValues.length - this.mHead;
        System.arraycopy(this.mValues, this.mHead, newArray, 0, r);
        System.arraycopy(this.mValues, 0, newArray, r, this.mHead);
        this.mValues = newArray;
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

    public void addLast(long value) {
        if (this.mSize == this.mValues.length) {
            grow();
        }
        this.mValues[this.mTail] = value;
        this.mTail = (this.mTail + 1) % this.mValues.length;
        this.mSize++;
    }

    public long removeFirst() {
        if (this.mSize == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
        long ret = this.mValues[this.mHead];
        this.mHead = (this.mHead + 1) % this.mValues.length;
        this.mSize--;
        return ret;
    }

    public long get(int position) {
        if (position < 0 || position >= this.mSize) {
            throw new IndexOutOfBoundsException("Index " + position + " not valid for a queue of size " + this.mSize);
        }
        int index = (this.mHead + position) % this.mValues.length;
        return this.mValues[index];
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
        int index = (this.mTail == 0 ? this.mValues.length : this.mTail) - 1;
        return this.mValues[index];
    }

    public String toString() {
        if (this.mSize <= 0) {
            return "{}";
        }
        StringBuilder buffer = new StringBuilder(this.mSize * 64);
        buffer.append('{');
        buffer.append(get(0));
        for (int i = 1; i < this.mSize; i++) {
            buffer.append(", ");
            buffer.append(get(i));
        }
        buffer.append('}');
        return buffer.toString();
    }
}
