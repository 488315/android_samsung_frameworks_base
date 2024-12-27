package com.android.internal.util.jobs;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class RingBufferIndices {
    private final int mCapacity;
    private int mSize;
    private int mStart;

    public RingBufferIndices(int i) {
        this.mCapacity = i;
    }

    public int add() {
        int i = this.mSize;
        int i2 = this.mCapacity;
        if (i < i2) {
            this.mSize = i + 1;
            return i;
        }
        int i3 = this.mStart;
        int i4 = i3 + 1;
        this.mStart = i4;
        if (i4 == i2) {
            this.mStart = 0;
        }
        return i3;
    }

    public void clear() {
        this.mStart = 0;
        this.mSize = 0;
    }

    public int indexOf(int i) {
        int i2 = this.mStart + i;
        int i3 = this.mCapacity;
        return i2 >= i3 ? i2 - i3 : i2;
    }

    public int size() {
        return this.mSize;
    }
}
