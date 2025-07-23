package android.net.metrics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.MacAddress;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class WakeupEvent {
    public MacAddress dstHwAddr;
    public String dstIp;
    public int dstPort;
    public int ethertype;
    public String iface;
    public int ipNextHeader;
    public String srcIp;
    public int srcPort;
    public long timestampMs;
    public int uid;

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "WakeupEvent(", NavigationBarInflaterView.KEY_CODE_END);
        stringJoiner.add(String.format("%tT.%tL", Long.valueOf(this.timestampMs), Long.valueOf(this.timestampMs)));
        stringJoiner.add(this.iface);
        stringJoiner.add("uid: " + Integer.toString(this.uid));
        stringJoiner.add("eth=0x" + Integer.toHexString(this.ethertype));
        stringJoiner.add("srcMac=" + this.dstHwAddr);
        if (this.ipNextHeader > 0) {
            stringJoiner.add("ipNxtHdr=" + this.ipNextHeader);
            stringJoiner.add("srcIp=" + this.srcIp);
            stringJoiner.add("dstIp=" + this.dstIp);
            if (this.srcPort > -1) {
                stringJoiner.add("srcPort=" + this.srcPort);
            }
            if (this.dstPort > -1) {
                stringJoiner.add("dstPort=" + this.dstPort);
            }
        }
        return stringJoiner.toString();
    }
}
