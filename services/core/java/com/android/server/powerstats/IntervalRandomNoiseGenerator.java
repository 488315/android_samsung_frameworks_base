package com.android.server.powerstats;

import java.util.Arrays;
import org.apache.commons.math.distribution.BetaDistributionImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
