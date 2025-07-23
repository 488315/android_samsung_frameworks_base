package android.app.jank;

import java.util.Arrays;

/* loaded from: classes.dex */
public class RelativeFrameTimeHistogram {
    private static int[] sBucketEndpoints = {Integer.MIN_VALUE, -200, -150, -100, -90, -80, -70, -60, -50, -40, -30, -25, -20, -18, -16, -14, -12, -10, -8, -6, -4, -2, 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 25, 30, 40, 50, 60, 70, 80, 90, 100, 150, 200, 300, 400, 500, 600, 700, 800, 900, 1000, Integer.MAX_VALUE};
    private int[] mBucketCounts = new int[sBucketEndpoints.length - 1];

    public void addRelativeFrameTimeMillis(int i) {
        int relativeFrameTimeBucketIndex = getRelativeFrameTimeBucketIndex(i);
        int[] iArr = this.mBucketCounts;
        iArr[relativeFrameTimeBucketIndex] = iArr[relativeFrameTimeBucketIndex] + 1;
    }

    public int[] getBucketCounters() {
        int[] iArr = this.mBucketCounts;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public int[] getBucketEndpointsMillis() {
        int[] iArr = sBucketEndpoints;
        return Arrays.copyOf(iArr, iArr.length);
    }

    private int getRelativeFrameTimeBucketIndex(int i) {
        if (i < 20) {
            if (i >= -20) {
                return ((i + 20) / 2) + 12;
            }
            if (i >= -30) {
                return ((i + 30) / 5) + 10;
            }
            if (i >= -100) {
                return ((i + 100) / 10) + 3;
            }
            if (i >= -200) {
                return ((i + 200) / 50) + 1;
            }
            return 0;
        }
        if (i < 30) {
            return ((i - 20) / 5) + 32;
        }
        if (i < 100) {
            return ((i - 30) / 10) + 34;
        }
        if (i < 200) {
            return ((i - 50) / 100) + 41;
        }
        if (i < 1000) {
            return ((i - 200) / 100) + 43;
        }
        return this.mBucketCounts.length - 1;
    }
}
