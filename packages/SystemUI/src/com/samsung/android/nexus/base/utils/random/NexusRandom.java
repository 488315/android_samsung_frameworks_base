package com.samsung.android.nexus.base.utils.random;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class NexusRandom {
    public static long seedUniquifier;
    public long seed;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NexusRandom() {
        /*
            r4 = this;
            long r0 = com.samsung.android.nexus.base.utils.random.NexusRandom.seedUniquifier
            r2 = 181783497276652981(0x285d320ad33fdb5, double:1.6685641475275746E-296)
            long r0 = r0 * r2
            com.samsung.android.nexus.base.utils.random.NexusRandom.seedUniquifier = r0
            long r2 = java.lang.System.nanoTime()
            long r0 = r0 ^ r2
            r4.<init>(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.nexus.base.utils.random.NexusRandom.<init>():void");
    }

    public NexusRandom(long j) {
        this.seed = (j ^ 25214903917L) & 281474976710655L;
    }
}
