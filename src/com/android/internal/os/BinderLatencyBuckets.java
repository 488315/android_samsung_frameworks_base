package com.android.internal.os;

import android.util.Slog;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class BinderLatencyBuckets {
    private static final String TAG = "BinderLatencyBuckets";
    private final int[] mBuckets;

    public BinderLatencyBuckets(int i, int i2, float f) {
        int i3 = i - 1;
        int[] iArr = new int[i3];
        iArr[0] = i2;
        double d = i2;
        for (int i4 = 1; i4 < i3; i4++) {
            d *= f;
            if (d > 2.147483647E9d) {
                Slog.w(TAG, "Attempted to create a bucket larger than maxint");
                this.mBuckets = Arrays.copyOfRange(iArr, 0, i4);
                return;
            }
            int i5 = (int) d;
            int i6 = iArr[i4 - 1];
            if (i5 > i6) {
                iArr[i4] = i5;
            } else {
                iArr[i4] = i6 + 1;
            }
        }
        this.mBuckets = iArr;
    }

    public int sampleToBucket(int i) {
        int[] iArr = this.mBuckets;
        if (i >= iArr[iArr.length - 1]) {
            return iArr.length;
        }
        int binarySearch = Arrays.binarySearch(iArr, i);
        return binarySearch < 0 ? -(binarySearch + 1) : binarySearch + 1;
    }

    public int[] getBuckets() {
        return this.mBuckets;
    }
}
