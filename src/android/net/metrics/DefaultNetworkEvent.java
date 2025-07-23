package android.net.metrics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.security.keystore.KeyProperties;
import java.util.BitSet;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class DefaultNetworkEvent {
    public final long creationTimeMs;
    public long durationMs;
    public int finalScore;
    public int initialScore;
    public boolean ipv4;
    public boolean ipv6;
    public int netId = 0;
    public int previousTransports;
    public int transports;
    public long validatedMs;

    public DefaultNetworkEvent(long j) {
        this.creationTimeMs = j;
    }

    public void updateDuration(long j) {
        this.durationMs = j - this.creationTimeMs;
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "DefaultNetworkEvent(", NavigationBarInflaterView.KEY_CODE_END);
        stringJoiner.add("netId=" + this.netId);
        stringJoiner.add("transports=" + BitSet.valueOf(new long[]{(long) this.transports}));
        stringJoiner.add("ip=" + ipSupport());
        if (this.initialScore > 0) {
            stringJoiner.add("initial_score=" + this.initialScore);
        }
        if (this.finalScore > 0) {
            stringJoiner.add("final_score=" + this.finalScore);
        }
        stringJoiner.add(String.format("duration=%.0fs", Double.valueOf(this.durationMs / 1000.0d)));
        stringJoiner.add(String.format("validation=%04.1f%%", Double.valueOf((this.validatedMs * 100.0d) / this.durationMs)));
        return stringJoiner.toString();
    }

    private String ipSupport() {
        boolean z = this.ipv4;
        if (z && this.ipv6) {
            return "IPv4v6";
        }
        if (this.ipv6) {
            return "IPv6";
        }
        if (z) {
            return "IPv4";
        }
        return KeyProperties.DIGEST_NONE;
    }
}
