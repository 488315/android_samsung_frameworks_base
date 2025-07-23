package android.net.metrics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.system.OsConstants;
import android.util.IntArray;
import android.util.SparseIntArray;
import com.android.internal.util.TokenBucket;
import java.util.BitSet;

/* loaded from: classes3.dex */
public class ConnectStats {
    private static final int EALREADY = OsConstants.EALREADY;
    private static final int EINPROGRESS = OsConstants.EINPROGRESS;
    public final TokenBucket mLatencyTb;
    public final int mMaxLatencyRecords;
    public final int netId;
    public final long transports;
    public final SparseIntArray errnos = new SparseIntArray();
    public final IntArray latencies = new IntArray();
    public int eventCount = 0;
    public int connectCount = 0;
    public int connectBlockingCount = 0;
    public int ipv6ConnectCount = 0;

    public ConnectStats(int i, long j, TokenBucket tokenBucket, int i2) {
        this.netId = i;
        this.transports = j;
        this.mLatencyTb = tokenBucket;
        this.mMaxLatencyRecords = i2;
    }

    boolean addEvent(int i, int i2, String str) {
        this.eventCount++;
        if (isSuccess(i)) {
            countConnect(i, str);
            countLatency(i, i2);
            return true;
        }
        countError(i);
        return false;
    }

    private void countConnect(int i, String str) {
        this.connectCount++;
        if (!isNonBlocking(i)) {
            this.connectBlockingCount++;
        }
        if (isIPv6(str)) {
            this.ipv6ConnectCount++;
        }
    }

    private void countLatency(int i, int i2) {
        if (!isNonBlocking(i) && this.mLatencyTb.get() && this.latencies.size() < this.mMaxLatencyRecords) {
            this.latencies.add(i2);
        }
    }

    private void countError(int i) {
        this.errnos.put(i, this.errnos.get(i, 0) + 1);
    }

    private static boolean isSuccess(int i) {
        return i == 0 || isNonBlocking(i);
    }

    static boolean isNonBlocking(int i) {
        return i == EINPROGRESS || i == EALREADY;
    }

    private static boolean isIPv6(String str) {
        return str.contains(":");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ConnectStats(netId=");
        sb.append(this.netId);
        sb.append(", transports=");
        sb.append(BitSet.valueOf(new long[]{this.transports}));
        sb.append(", ");
        sb.append(String.format("%d events, ", Integer.valueOf(this.eventCount)));
        sb.append(String.format("%d success, ", Integer.valueOf(this.connectCount)));
        sb.append(String.format("%d blocking, ", Integer.valueOf(this.connectBlockingCount)));
        sb.append(String.format("%d IPv6 dst", Integer.valueOf(this.ipv6ConnectCount)));
        for (int i = 0; i < this.errnos.size(); i++) {
            sb.append(String.format(", %s: %d", OsConstants.errnoName(this.errnos.keyAt(i)), Integer.valueOf(this.errnos.valueAt(i))));
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }
}
