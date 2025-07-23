package android.net.metrics;

import android.app.blob.XmlTags;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.SystemClock;
import android.util.SparseIntArray;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class WakeupStats {
    private static final int NO_UID = -1;
    public final String iface;
    public final long creationTimeMs = SystemClock.elapsedRealtime();
    public long totalWakeups = 0;
    public long rootWakeups = 0;
    public long systemWakeups = 0;
    public long nonApplicationWakeups = 0;
    public long applicationWakeups = 0;
    public long noUidWakeups = 0;
    public long durationSec = 0;
    public long l2UnicastCount = 0;
    public long l2MulticastCount = 0;
    public long l2BroadcastCount = 0;
    public final SparseIntArray ethertypes = new SparseIntArray();
    public final SparseIntArray ipNextHeaders = new SparseIntArray();

    public WakeupStats(String str) {
        this.iface = str;
    }

    public void updateDuration() {
        this.durationSec = (SystemClock.elapsedRealtime() - this.creationTimeMs) / 1000;
    }

    public void countEvent(WakeupEvent wakeupEvent) {
        this.totalWakeups++;
        int i = wakeupEvent.uid;
        if (i == -1) {
            this.noUidWakeups++;
        } else if (i == 0) {
            this.rootWakeups++;
        } else if (i == 1000) {
            this.systemWakeups++;
        } else if (wakeupEvent.uid >= 10000) {
            this.applicationWakeups++;
        } else {
            this.nonApplicationWakeups++;
        }
        if (wakeupEvent.dstHwAddr != null) {
            int addressType = wakeupEvent.dstHwAddr.getAddressType();
            if (addressType == 1) {
                this.l2UnicastCount++;
            } else if (addressType == 2) {
                this.l2MulticastCount++;
            } else if (addressType == 3) {
                this.l2BroadcastCount++;
            }
        }
        increment(this.ethertypes, wakeupEvent.ethertype);
        if (wakeupEvent.ipNextHeader >= 0) {
            increment(this.ipNextHeaders, wakeupEvent.ipNextHeader);
        }
    }

    public String toString() {
        updateDuration();
        StringJoiner stringJoiner = new StringJoiner(", ", "WakeupStats(", NavigationBarInflaterView.KEY_CODE_END);
        stringJoiner.add(this.iface);
        stringJoiner.add("" + this.durationSec + XmlTags.TAG_SESSION);
        StringBuilder sb = new StringBuilder("total: ");
        sb.append(this.totalWakeups);
        stringJoiner.add(sb.toString());
        stringJoiner.add("root: " + this.rootWakeups);
        stringJoiner.add("system: " + this.systemWakeups);
        stringJoiner.add("apps: " + this.applicationWakeups);
        stringJoiner.add("non-apps: " + this.nonApplicationWakeups);
        stringJoiner.add("no uid: " + this.noUidWakeups);
        stringJoiner.add(String.format("l2 unicast/multicast/broadcast: %d/%d/%d", Long.valueOf(this.l2UnicastCount), Long.valueOf(this.l2MulticastCount), Long.valueOf(this.l2BroadcastCount)));
        for (int i = 0; i < this.ethertypes.size(); i++) {
            stringJoiner.add(String.format("ethertype 0x%x: %d", Integer.valueOf(this.ethertypes.keyAt(i)), Integer.valueOf(this.ethertypes.valueAt(i))));
        }
        for (int i2 = 0; i2 < this.ipNextHeaders.size(); i2++) {
            stringJoiner.add(String.format("ipNxtHdr %d: %d", Integer.valueOf(this.ipNextHeaders.keyAt(i2)), Integer.valueOf(this.ipNextHeaders.valueAt(i2))));
        }
        return stringJoiner.toString();
    }

    private static void increment(SparseIntArray sparseIntArray, int i) {
        sparseIntArray.put(i, sparseIntArray.get(i, 0) + 1);
    }
}
