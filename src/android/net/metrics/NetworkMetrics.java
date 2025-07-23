package android.net.metrics;

import android.hardware.scontext.SContextConstants;
import com.android.internal.util.TokenBucket;
import java.util.BitSet;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class NetworkMetrics {
    private static final int CONNECT_LATENCY_MAXIMUM_RECORDS = 20000;
    private static final int INITIAL_DNS_BATCH_SIZE = 100;
    public final ConnectStats connectMetrics;
    public final DnsEvent dnsMetrics;
    public final int netId;
    public Summary pendingSummary;
    public final Summary summary;
    public final long transports;

    public NetworkMetrics(int i, long j, TokenBucket tokenBucket) {
        this.netId = i;
        this.transports = j;
        this.connectMetrics = new ConnectStats(i, j, tokenBucket, 20000);
        this.dnsMetrics = new DnsEvent(i, j, 100);
        this.summary = new Summary(i, j);
    }

    public Summary getPendingStats() {
        Summary summary = this.pendingSummary;
        this.pendingSummary = null;
        if (summary != null) {
            this.summary.merge(summary);
        }
        return summary;
    }

    public void addDnsResult(int i, int i2, int i3) {
        if (this.pendingSummary == null) {
            this.pendingSummary = new Summary(this.netId, this.transports);
        }
        boolean addResult = this.dnsMetrics.addResult((byte) i, (byte) i2, i3);
        this.pendingSummary.dnsLatencies.count(i3);
        this.pendingSummary.dnsErrorRate.count(addResult ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : 1.0d);
        if (i2 == 255 || i3 >= 1000) {
            this.pendingSummary.dnsDelayedResponseCnt.count(1.0d);
        }
        this.pendingSummary.markUpdated();
    }

    public void addConnectResult(int i, int i2, String str) {
        if (this.pendingSummary == null) {
            this.pendingSummary = new Summary(this.netId, this.transports);
        }
        this.pendingSummary.connectErrorRate.count(this.connectMetrics.addEvent(i, i2, str) ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : 1.0d);
        if (ConnectStats.isNonBlocking(i)) {
            this.pendingSummary.connectLatencies.count(i2);
        }
    }

    public void addTcpStatsResult(int i, int i2, int i3, int i4) {
        if (this.pendingSummary == null) {
            this.pendingSummary = new Summary(this.netId, this.transports);
        }
        this.pendingSummary.tcpLossRate.count(i2, i);
        this.pendingSummary.roundTripTimeUs.count(i3);
        this.pendingSummary.sentAckTimeDiffenceMs.count(i4);
        this.pendingSummary.markUpdated();
    }

    public static class Summary {
        public final int netId;
        public final long transports;
        public final Metrics dnsLatencies = new Metrics();
        public final Metrics dnsErrorRate = new Metrics();
        public final Metrics connectLatencies = new Metrics();
        public final Metrics connectErrorRate = new Metrics();
        public final Metrics tcpLossRate = new Metrics();
        public final Metrics roundTripTimeUs = new Metrics();
        public final Metrics sentAckTimeDiffenceMs = new Metrics();
        public final Metrics dnsDelayedResponseCnt = new Metrics();
        private boolean isUpdated = false;

        public Summary(int i, long j) {
            this.netId = i;
            this.transports = j;
        }

        void merge(Summary summary) {
            this.dnsLatencies.merge(summary.dnsLatencies);
            this.dnsErrorRate.merge(summary.dnsErrorRate);
            this.connectLatencies.merge(summary.connectLatencies);
            this.connectErrorRate.merge(summary.connectErrorRate);
            this.tcpLossRate.merge(summary.tcpLossRate);
            this.dnsDelayedResponseCnt.merge(summary.dnsDelayedResponseCnt);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void markUpdated() {
            this.isUpdated = true;
        }

        public boolean isUpdated() {
            return this.isUpdated;
        }

        public int getDnsLatency() {
            return (int) this.dnsLatencies.average();
        }

        public int getDnsErrorRate() {
            return (int) (this.dnsErrorRate.average() * 100.0d);
        }

        public int getDnsDelayedResponse() {
            return this.dnsDelayedResponseCnt.count;
        }

        public int getTcpAverageLoss() {
            return (int) (this.tcpLossRate.average() * 100.0d);
        }

        public int getTcpRtt() {
            return (int) (this.roundTripTimeUs.average() / 1000.0d);
        }

        public int getTcpSentAckDiff() {
            return (int) this.sentAckTimeDiffenceMs.average();
        }

        public String toString() {
            StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
            stringJoiner.add("netId=" + this.netId);
            stringJoiner.add("transports=" + BitSet.valueOf(new long[]{this.transports}));
            stringJoiner.add(String.format("dns avg=%dms max=%dms err=%.1f%% tot=%d", Integer.valueOf((int) this.dnsLatencies.average()), Integer.valueOf((int) this.dnsLatencies.max), Double.valueOf(this.dnsErrorRate.average() * 100.0d), Integer.valueOf(this.dnsErrorRate.count)));
            stringJoiner.add(String.format("delayed rsp=%d", Integer.valueOf(this.dnsDelayedResponseCnt.count)));
            stringJoiner.add(String.format("connect avg=%dms max=%dms err=%.1f%% tot=%d", Integer.valueOf((int) this.connectLatencies.average()), Integer.valueOf((int) this.connectLatencies.max), Double.valueOf(this.connectErrorRate.average() * 100.0d), Integer.valueOf(this.connectErrorRate.count)));
            stringJoiner.add(String.format("tcp avg_loss=%.1f%% total_sent=%d total_lost=%d", Double.valueOf(this.tcpLossRate.average() * 100.0d), Integer.valueOf(this.tcpLossRate.count), Integer.valueOf((int) this.tcpLossRate.sum)));
            stringJoiner.add(String.format("tcp rtt=%dms", Integer.valueOf((int) (this.roundTripTimeUs.average() / 1000.0d))));
            stringJoiner.add(String.format("tcp sent-ack_diff=%dms", Integer.valueOf((int) this.sentAckTimeDiffenceMs.average())));
            return stringJoiner.toString();
        }
    }

    static class Metrics {
        public int count;
        public double max = Double.MIN_VALUE;
        public double sum;

        Metrics() {
        }

        void merge(Metrics metrics) {
            this.count += metrics.count;
            this.sum += metrics.sum;
            this.max = Math.max(this.max, metrics.max);
        }

        void count(double d) {
            count(d, 1);
        }

        void count(double d, int i) {
            this.count += i;
            this.sum += d;
            this.max = Math.max(this.max, d);
        }

        double average() {
            double d = this.sum / this.count;
            return Double.isNaN(d) ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : d;
        }
    }
}
