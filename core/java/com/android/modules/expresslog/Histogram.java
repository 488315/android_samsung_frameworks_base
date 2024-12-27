package com.android.modules.expresslog;

import java.util.Arrays;

/* loaded from: classes5.dex */
public final class Histogram {
    private final BinOptions mBinOptions;
    private final String mMetricId;

    public interface BinOptions {
        int getBinForSample(float f);

        int getBinsCount();
    }

    public Histogram(String metricId, BinOptions binOptions) {
        this.mMetricId = metricId;
        this.mBinOptions = binOptions;
    }

    public void logSample(float sample) {
        long hash = MetricIds.getMetricIdHash(this.mMetricId, 2);
        int binIndex = this.mBinOptions.getBinForSample(sample);
        StatsExpressLog.write(593, hash, 1L, binIndex);
    }

    public void logSampleWithUid(int uid, float sample) {
        long hash = MetricIds.getMetricIdHash(this.mMetricId, 4);
        int binIndex = this.mBinOptions.getBinForSample(sample);
        StatsExpressLog.write(658, hash, 1L, binIndex, uid);
    }

    public static final class UniformOptions implements BinOptions {
        private final int mBinCount;
        private final float mBinSize;
        private final float mExclusiveMaxValue;
        private final float mMinValue;

        public UniformOptions(int binCount, float minValue, float exclusiveMaxValue) {
            if (binCount < 1) {
                throw new IllegalArgumentException("Bin count should be positive number");
            }
            if (exclusiveMaxValue <= minValue) {
                throw new IllegalArgumentException("Bins range invalid (maxValue < minValue)");
            }
            this.mMinValue = minValue;
            this.mExclusiveMaxValue = exclusiveMaxValue;
            this.mBinSize = (this.mExclusiveMaxValue - minValue) / binCount;
            this.mBinCount = binCount + 2;
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinsCount() {
            return this.mBinCount;
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinForSample(float sample) {
            if (sample < this.mMinValue) {
                return 0;
            }
            if (sample >= this.mExclusiveMaxValue) {
                return this.mBinCount - 1;
            }
            return (int) (((sample - this.mMinValue) / this.mBinSize) + 1.0f);
        }
    }

    public static final class ScaledRangeOptions implements BinOptions {
        final long[] mBins;

        public ScaledRangeOptions(int binCount, int minValue, float firstBinWidth, float scaleFactor) {
            if (binCount < 1) {
                throw new IllegalArgumentException("Bin count should be positive number");
            }
            if (firstBinWidth < 1.0f) {
                throw new IllegalArgumentException("First bin width invalid (should be 1.f at minimum)");
            }
            if (scaleFactor < 1.0f) {
                throw new IllegalArgumentException("Scaled factor invalid (should be 1.f at minimum)");
            }
            this.mBins = initBins(binCount + 1, minValue, firstBinWidth, scaleFactor);
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinsCount() {
            return this.mBins.length + 1;
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinForSample(float sample) {
            if (sample < this.mBins[0]) {
                return 0;
            }
            if (sample >= this.mBins[this.mBins.length - 1]) {
                return this.mBins.length;
            }
            return lower_bound(this.mBins, (long) sample) + 1;
        }

        private static int lower_bound(long[] array, long sample) {
            int index = Arrays.binarySearch(array, sample);
            if (index < 0) {
                return Math.abs(index) - 2;
            }
            return index;
        }

        private static long[] initBins(int count, int minValue, float firstBinWidth, float scaleFactor) {
            long[] bins = new long[count];
            bins[0] = minValue;
            double lastWidth = firstBinWidth;
            for (int i = 1; i < count; i++) {
                double currentBinMinValue = bins[i - 1] + lastWidth;
                if (currentBinMinValue > 2.147483647E9d) {
                    throw new IllegalArgumentException("Attempted to create a bucket larger than maxint");
                }
                bins[i] = (long) currentBinMinValue;
                lastWidth *= scaleFactor;
            }
            return bins;
        }
    }
}
