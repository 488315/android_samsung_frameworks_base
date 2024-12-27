package com.android.server.display.utils;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AmbientFilter$WeightedMovingAverageAmbientFilter {
    public final RollingBuffer mBuffer;
    public final int mHorizon;
    public final float mIntercept;
    public final String mTag;

    public AmbientFilter$WeightedMovingAverageAmbientFilter(String str, float f, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("horizon must be positive");
        }
        this.mTag = str;
        this.mHorizon = i;
        RollingBuffer rollingBuffer = new RollingBuffer();
        rollingBuffer.mSize = 50;
        rollingBuffer.mTimes = new long[50];
        rollingBuffer.mValues = new float[50];
        rollingBuffer.mCount = 0;
        rollingBuffer.mStart = 0;
        rollingBuffer.mEnd = 0;
        this.mBuffer = rollingBuffer;
        if (Float.isNaN(f) || f < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            throw new IllegalArgumentException("intercept must be a non-negative number");
        }
        this.mIntercept = f;
    }

    public final void addValue(long j, float f) {
        if (f < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return;
        }
        truncateOldValues(j);
        RollingBuffer rollingBuffer = this.mBuffer;
        int i = rollingBuffer.mCount;
        int i2 = rollingBuffer.mSize;
        if (i >= i2) {
            int i3 = i2 * 2;
            long[] jArr = new long[i3];
            float[] fArr = new float[i3];
            long[] jArr2 = rollingBuffer.mTimes;
            int i4 = rollingBuffer.mStart;
            System.arraycopy(jArr2, i4, jArr, 0, i - i4);
            long[] jArr3 = rollingBuffer.mTimes;
            int i5 = rollingBuffer.mCount;
            int i6 = rollingBuffer.mStart;
            System.arraycopy(jArr3, 0, jArr, i5 - i6, i6);
            float[] fArr2 = rollingBuffer.mValues;
            int i7 = rollingBuffer.mStart;
            System.arraycopy(fArr2, i7, fArr, 0, rollingBuffer.mCount - i7);
            float[] fArr3 = rollingBuffer.mValues;
            int i8 = rollingBuffer.mCount;
            int i9 = rollingBuffer.mStart;
            System.arraycopy(fArr3, 0, fArr, i8 - i9, i9);
            rollingBuffer.mSize = i3;
            rollingBuffer.mStart = 0;
            rollingBuffer.mEnd = rollingBuffer.mCount;
            rollingBuffer.mTimes = jArr;
            rollingBuffer.mValues = fArr;
        }
        long[] jArr4 = rollingBuffer.mTimes;
        int i10 = rollingBuffer.mEnd;
        jArr4[i10] = j;
        rollingBuffer.mValues[i10] = f;
        rollingBuffer.mEnd = (i10 + 1) % rollingBuffer.mSize;
        rollingBuffer.mCount++;
    }

    public final float getEstimate(long j) {
        float f;
        truncateOldValues(j);
        RollingBuffer rollingBuffer = this.mBuffer;
        int i = rollingBuffer.mCount;
        if (i == 0) {
            return -1.0f;
        }
        float[] fArr = new float[i];
        long j2 = rollingBuffer.mTimes[rollingBuffer.offsetOf(0)];
        int i2 = 1;
        float f2 = 0.0f;
        while (true) {
            f = this.mIntercept;
            if (i2 >= i) {
                break;
            }
            float f3 = (rollingBuffer.mTimes[rollingBuffer.offsetOf(i2)] - j2) / 1000.0f;
            fArr[i2 - 1] = ((f * f3) + ((f3 * 0.5f) * f3)) - ((f * f2) + ((0.5f * f2) * f2));
            i2++;
            f2 = f3;
        }
        float f4 = ((j + 100) - j2) / 1000.0f;
        fArr[i - 1] = ((f4 * f) + ((f4 * 0.5f) * f4)) - ((f * f2) + ((0.5f * f2) * f2));
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (int i3 = 0; i3 < i; i3++) {
            float f7 = rollingBuffer.mValues[rollingBuffer.offsetOf(i3)];
            float f8 = fArr[i3];
            f6 += f7 * f8;
            f5 += f8;
        }
        if (f5 != FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return f6 / f5;
        }
        return rollingBuffer.mValues[rollingBuffer.offsetOf(rollingBuffer.mCount - 1)];
    }

    public final void truncateOldValues(long j) {
        int i;
        long j2 = j - this.mHorizon;
        RollingBuffer rollingBuffer = this.mBuffer;
        if (rollingBuffer.mCount != 0 && rollingBuffer.mTimes[rollingBuffer.offsetOf(0)] < j2) {
            int i2 = 1;
            while (true) {
                int i3 = rollingBuffer.mCount;
                if (i2 >= i3) {
                    i = i3 - 1;
                    break;
                } else {
                    if (rollingBuffer.mTimes[rollingBuffer.offsetOf(i2)] > j2) {
                        i = i2 - 1;
                        break;
                    }
                    i2++;
                }
            }
            int offsetOf = rollingBuffer.offsetOf(i);
            rollingBuffer.mStart = offsetOf;
            rollingBuffer.mCount -= i;
            rollingBuffer.mTimes[offsetOf] = j2;
        }
    }
}
