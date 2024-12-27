package com.android.server.stats.pull.netstats;

import android.net.NetworkStats;

import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NetworkStatsExt {
    public final boolean isTypeProxy;
    public final int oemManaged;
    public final int ratType;
    public final boolean slicedByFgbg;
    public final boolean slicedByMetered;
    public final boolean slicedByTag;
    public final NetworkStats stats;
    public final SubInfo subInfo;
    public final int[] transports;

    public NetworkStatsExt(NetworkStats networkStats, int[] iArr, boolean z) {
        this(networkStats, iArr, z, false, false, 0, null, -1, false);
    }

    public NetworkStatsExt(
            NetworkStats networkStats,
            int[] iArr,
            boolean z,
            boolean z2,
            boolean z3,
            int i,
            SubInfo subInfo,
            int i2,
            boolean z4) {
        this.stats = networkStats;
        int[] copyOf = Arrays.copyOf(iArr, iArr.length);
        this.transports = copyOf;
        Arrays.sort(copyOf);
        this.slicedByFgbg = z;
        this.slicedByTag = z2;
        this.slicedByMetered = z3;
        this.ratType = i;
        this.subInfo = subInfo;
        this.oemManaged = i2;
        this.isTypeProxy = z4;
    }
}
