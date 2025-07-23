package kotlin.collections;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;
import kotlin.collections.AbstractList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class RingBuffer extends AbstractList implements RandomAccess {
    public final Object[] buffer;
    public final int capacity;
    public int size;
    public int startIndex;

    public RingBuffer(Object[] objArr, int i) {
        this.buffer = objArr;
        if (i < 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "ring buffer filled size should not be negative but it is ").toString());
        }
        if (i <= objArr.length) {
            this.capacity = objArr.length;
            this.size = i;
        } else {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "ring buffer filled size: ", " cannot be larger than the buffer size: ");
            m.append(objArr.length);
            throw new IllegalArgumentException(m.toString().toString());
        }
    }

    @Override // java.util.List
    public final Object get(int i) {
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.size;
        companion.getClass();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
        return this.buffer[(this.startIndex + i) % this.capacity];
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.size;
    }

    @Override // kotlin.collections.AbstractList, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return new AbstractIterator() { // from class: kotlin.collections.RingBuffer$iterator$1
            public int count;
            public int index;

            {
                this.count = RingBuffer.this.getSize();
                this.index = RingBuffer.this.startIndex;
            }

            @Override // kotlin.collections.AbstractIterator
            public final void computeNext() {
                int i = this.count;
                if (i == 0) {
                    this.state = 2;
                    return;
                }
                RingBuffer ringBuffer = RingBuffer.this;
                Object[] objArr = ringBuffer.buffer;
                int i2 = this.index;
                this.nextValue = objArr[i2];
                this.state = 1;
                this.index = (i2 + 1) % ringBuffer.capacity;
                this.count = i - 1;
            }
        };
    }

    public final void removeFirst(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "n shouldn't be negative but it is ").toString());
        }
        if (i > this.size) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "n shouldn't be greater than the buffer size: n = ", ", size = ");
            m.append(this.size);
            throw new IllegalArgumentException(m.toString().toString());
        }
        if (i > 0) {
            int i2 = this.startIndex;
            int i3 = this.capacity;
            int i4 = (i2 + i) % i3;
            if (i2 > i4) {
                Arrays.fill(this.buffer, i2, i3, (Object) null);
                Arrays.fill(this.buffer, 0, i4, (Object) null);
            } else {
                Arrays.fill(this.buffer, i2, i4, (Object) null);
            }
            this.startIndex = i4;
            this.size -= i;
        }
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final Object[] toArray() {
        return toArray(new Object[getSize()]);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        int length = objArr.length;
        int i = this.size;
        if (length < i) {
            objArr = Arrays.copyOf(objArr, i);
        }
        int i2 = this.size;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = this.startIndex; i4 < i2 && i5 < this.capacity; i5++) {
            objArr[i4] = this.buffer[i5];
            i4++;
        }
        while (i4 < i2) {
            objArr[i4] = this.buffer[i3];
            i4++;
            i3++;
        }
        if (i2 < objArr.length) {
            objArr[i2] = null;
        }
        return objArr;
    }

    public RingBuffer(int i) {
        this(new Object[i], 0);
    }
}
