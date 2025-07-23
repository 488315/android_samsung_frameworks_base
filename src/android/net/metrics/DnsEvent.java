package android.net.metrics;

import java.util.Arrays;
import java.util.BitSet;

/* loaded from: classes3.dex */
public final class DnsEvent {
    private static final int SIZE_LIMIT = 20000;
    public int eventCount;
    public byte[] eventTypes;
    public int[] latenciesMs;
    public final int netId;
    public byte[] returnCodes;
    public int successCount;
    public final long transports;

    public DnsEvent(int i, long j, int i2) {
        this.netId = i;
        this.transports = j;
        this.eventTypes = new byte[i2];
        this.returnCodes = new byte[i2];
        this.latenciesMs = new int[i2];
    }

    boolean addResult(byte b, byte b2, int i) {
        boolean z = b2 == 0;
        int i2 = this.eventCount;
        if (i2 < 20000) {
            if (i2 == this.eventTypes.length) {
                int i3 = (int) (i2 * 1.4d);
                if (i2 == i3) {
                    i3++;
                }
                resize(i3);
            }
            byte[] bArr = this.eventTypes;
            int i4 = this.eventCount;
            bArr[i4] = b;
            this.returnCodes[i4] = b2;
            this.latenciesMs[i4] = i;
            this.eventCount = i4 + 1;
            if (z) {
                this.successCount++;
            }
        }
        return z;
    }

    public void resize(int i) {
        this.eventTypes = Arrays.copyOf(this.eventTypes, i);
        this.returnCodes = Arrays.copyOf(this.returnCodes, i);
        this.latenciesMs = Arrays.copyOf(this.latenciesMs, i);
    }

    public String toString() {
        return "DnsEvent(netId=" + this.netId + ", transports=" + BitSet.valueOf(new long[]{this.transports}) + ", " + String.format("%d events, ", Integer.valueOf(this.eventCount)) + String.format("%d success)", Integer.valueOf(this.successCount));
    }
}
