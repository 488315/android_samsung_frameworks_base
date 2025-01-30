package com.samsung.android.nexus.base.utils.random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NexusRandom {
    public static long seedUniquifier;
    public long seed;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NexusRandom() {
        this(r0 ^ System.nanoTime());
        long j = seedUniquifier * 181783497276652981L;
        seedUniquifier = j;
    }

    public NexusRandom(long j) {
        this.seed = (j ^ 25214903917L) & 281474976710655L;
    }
}
