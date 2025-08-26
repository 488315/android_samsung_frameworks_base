package com.samsung.android.nexus.base.utils.random;

/* loaded from: classes4.dex */
public class NexusRandom {
    public static long seedUniquifier;
    public long seed;

    /* JADX WARN: Illegal instructions before constructor call */
    public NexusRandom() {
        long j = seedUniquifier * 181783497276652981L;
        seedUniquifier = j;
        this(j ^ System.nanoTime());
    }

    public NexusRandom(long j) {
        this.seed = (j ^ 25214903917L) & 281474976710655L;
    }
}
