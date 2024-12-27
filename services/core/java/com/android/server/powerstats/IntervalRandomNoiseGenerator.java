package com.android.server.powerstats;

import org.apache.commons.math.distribution.BetaDistributionImpl;

import java.util.Arrays;

public final class IntervalRandomNoiseGenerator {
    public final BetaDistributionImpl mDistribution = new BetaDistributionImpl();
    public final double[] mSamples;

    public IntervalRandomNoiseGenerator() {
        double[] dArr = new double[17];
        this.mSamples = dArr;
        Arrays.fill(dArr, -1.0d);
    }

    public void reseed(long j) {
        this.mDistribution.reseedRandomGenerator(j);
    }
}
