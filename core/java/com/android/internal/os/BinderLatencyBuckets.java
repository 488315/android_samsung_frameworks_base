package com.android.internal.os;

import android.util.Slog;

import java.util.Arrays;

/* loaded from: classes5.dex */
public class BinderLatencyBuckets {
    private static final String TAG = "BinderLatencyBuckets";
    private final int[] mBuckets;

    public BinderLatencyBuckets(int bucketCount, int firstBucketSize, float scaleFactor) {
        int[] buffer = new int[bucketCount - 1];
        buffer[0] = firstBucketSize;
        double lastTarget = firstBucketSize;
        for (int i = 1; i < bucketCount - 1; i++) {
            double nextTarget = scaleFactor * lastTarget;
            if (nextTarget > 2.147483647E9d) {
                Slog.w(TAG, "Attempted to create a bucket larger than maxint");
                this.mBuckets = Arrays.copyOfRange(buffer, 0, i);
                return;
            } else {
                if (((int) nextTarget) > buffer[i - 1]) {
                    buffer[i] = (int) nextTarget;
                } else {
                    buffer[i] = buffer[i - 1] + 1;
                }
                lastTarget = nextTarget;
            }
        }
        this.mBuckets = buffer;
    }

    public int sampleToBucket(int sample) {
        if (sample >= this.mBuckets[this.mBuckets.length - 1]) {
            return this.mBuckets.length;
        }
        int searchResult = Arrays.binarySearch(this.mBuckets, sample);
        int i = searchResult + 1;
        return searchResult < 0 ? -i : i;
    }

    public int[] getBuckets() {
        return this.mBuckets;
    }
}
