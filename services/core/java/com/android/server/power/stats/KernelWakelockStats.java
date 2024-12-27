package com.android.server.power.stats;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class KernelWakelockStats extends HashMap {
    int kernelWakelockVersion;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Entry {
        public long activeTimeUs;
        public int count;
        public long totalTimeUs;
        public int version;

        public Entry(int i, int i2, long j, long j2) {
            this.count = i;
            this.totalTimeUs = j;
            this.activeTimeUs = j2;
            this.version = i2;
        }
    }
}
