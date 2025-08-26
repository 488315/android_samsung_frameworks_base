package com.android.modules.expresslog;

import java.util.Arrays;

/* loaded from: classes6.dex */
public final class Histogram {
    private final BinOptions mBinOptions;
    private final String mMetricId;

    public interface BinOptions {
        int getBinForSample(float f);

        int getBinsCount();
    }

    public Histogram(String str, BinOptions binOptions) {
        this.mMetricId = str;
        this.mBinOptions = binOptions;
    }

    public void logSample(float f) {
        long metricIdHash = MetricIds.getMetricIdHash(this.mMetricId, 2);
        if (metricIdHash != 0) {
            StatsExpressLog.write(593, metricIdHash, 1L, this.mBinOptions.getBinForSample(f));
        }
    }

    public void logSampleWithUid(int i, float f) {
        long metricIdHash = MetricIds.getMetricIdHash(this.mMetricId, 4);
        if (metricIdHash != 0) {
            StatsExpressLog.write(658, metricIdHash, 1L, this.mBinOptions.getBinForSample(f), i);
        }
    }

    public static final class UniformOptions implements BinOptions {
        private final int mBinCount;
        private final float mBinSize;
        private final float mExclusiveMaxValue;
        private final float mMinValue;

        public UniformOptions(int i, float f, float f2) {
            if (i < 1) {
                throw new IllegalArgumentException("Bin count should be positive number");
            }
            if (f2 <= f) {
                throw new IllegalArgumentException("Bins range invalid (maxValue < minValue)");
            }
            this.mMinValue = f;
            this.mExclusiveMaxValue = f2;
            this.mBinSize = (f2 - f) / i;
            this.mBinCount = i + 2;
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinsCount() {
            return this.mBinCount;
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinForSample(float f) {
            float f2 = this.mMinValue;
            if (f < f2) {
                return 0;
            }
            if (f >= this.mExclusiveMaxValue) {
                return this.mBinCount - 1;
            }
            return (int) (((f - f2) / this.mBinSize) + 1.0f);
        }
    }

    public static final class ScaledRangeOptions implements BinOptions {
        final long[] mBins;

        public ScaledRangeOptions(int i, int i2, float f, float f2) {
            if (i < 1) {
                throw new IllegalArgumentException("Bin count should be positive number");
            }
            if (f < 1.0f) {
                throw new IllegalArgumentException("First bin width invalid (should be 1.f at minimum)");
            }
            if (f2 < 1.0f) {
                throw new IllegalArgumentException("Scaled factor invalid (should be 1.f at minimum)");
            }
            this.mBins = initBins(i + 1, i2, f, f2);
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinsCount() {
            return this.mBins.length + 1;
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinForSample(float f) {
            long[] jArr = this.mBins;
            if (f < jArr[0]) {
                return 0;
            }
            if (f >= jArr[jArr.length - 1]) {
                return jArr.length;
            }
            return lower_bound(jArr, (long) f) + 1;
        }

        private static int lower_bound(long[] jArr, long j) {
            int iBinarySearch = Arrays.binarySearch(jArr, j);
            return iBinarySearch < 0 ? Math.abs(iBinarySearch) - 2 : iBinarySearch;
        }

        private static long[] initBins(int i, int i2, float f, float f2) {
            long[] jArr = new long[i];
            jArr[0] = i2;
            double d = f;
            for (int i3 = 1; i3 < i; i3++) {
                double d2 = jArr[i3 - 1] + d;
                if (d2 > 2.147483647E9d) {
                    throw new IllegalArgumentException("Attempted to create a bucket larger than maxint");
                }
                jArr[i3] = (long) d2;
                d *= f2;
            }
            return jArr;
        }
    }
}
