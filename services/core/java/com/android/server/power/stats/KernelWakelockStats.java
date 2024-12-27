package com.android.server.power.stats;

import java.util.HashMap;

public class KernelWakelockStats extends HashMap {
    int kernelWakelockVersion;

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
