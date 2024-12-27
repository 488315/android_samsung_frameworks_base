package com.android.server.display.utils;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RollingBuffer {
    public int mCount;
    public int mEnd;
    public int mSize;
    public int mStart;
    public long[] mTimes;
    public float[] mValues;

    public final int offsetOf(int i) {
        if (i >= 0 && i < this.mCount) {
            return (this.mStart + i) % this.mSize;
        }
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "invalid index: ", ", mCount= ");
        m.append(this.mCount);
        throw new ArrayIndexOutOfBoundsException(m.toString());
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        while (i < this.mCount) {
            int offsetOf = offsetOf(i);
            sb.append(this.mValues[offsetOf] + " @ " + this.mTimes[offsetOf]);
            i++;
            if (i != this.mCount) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
