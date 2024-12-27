package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SkData extends TracepointData {
    public final String dstAddr;
    public final String dstAddrV6;
    public final int dstPort;
    public final int family;
    public final int newState;
    public final int oldState;
    public final int protocol;
    public final String srcAddr;
    public final String srcAddrV6;
    public final int srcPort;

    public SkData(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str, String str2, String str3, String str4, long j, long j2, long j3, String str5) {
        super(i, j, j2, j3, str5);
        this.oldState = i2;
        this.newState = i3;
        this.srcPort = i4;
        this.dstPort = i5;
        this.family = i6;
        this.protocol = i7;
        this.srcAddr = str;
        this.dstAddr = str2;
        this.srcAddrV6 = str3;
        this.dstAddrV6 = str4;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("when", this.actualEventTime);
        bundle.putInt("what", this.event);
        bundle.putInt("pid", getPid());
        bundle.putInt("uid", (int) this.uidGid);
        bundle.putString("comm", this.comm);
        bundle.putInt("oldState", this.oldState);
        bundle.putInt("newState", this.newState);
        bundle.putInt("sport", this.srcPort);
        bundle.putInt("dport", this.dstPort);
        bundle.putInt("family", this.family);
        bundle.putInt("protocol", this.protocol);
        bundle.putString("saddr", this.srcAddr);
        bundle.putString("daddr", this.dstAddr);
        bundle.putString("saddr_v6", this.srcAddrV6);
        bundle.putString("daddr_v6", this.dstAddrV6);
        readExtras(bundle);
        return bundle;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("when", this.actualEventTime);
            jSONObject.put("what", this.event);
            jSONObject.put("pid", getPid());
            jSONObject.put("uid", (int) this.uidGid);
            jSONObject.put("comm", this.comm);
            jSONObject.put("oldState", this.oldState);
            jSONObject.put("newState", this.newState);
            jSONObject.put("sport", this.srcPort);
            jSONObject.put("dport", this.dstPort);
            jSONObject.put("family", this.family);
            jSONObject.put("protocol", this.protocol);
            jSONObject.put("saddr", this.srcAddr);
            jSONObject.put("daddr", this.dstAddr);
            jSONObject.put("saddr_v6", this.srcAddrV6);
            jSONObject.put("daddr_v6", this.dstAddrV6);
            readExtras(jSONObject);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toLine() {
        Locale locale = Locale.US;
        long j = this.actualEventTime;
        int i = this.event;
        int pid = getPid();
        int i2 = (int) this.uidGid;
        String str = this.comm;
        int i3 = this.oldState;
        int i4 = this.newState;
        int i5 = this.srcPort;
        int i6 = this.dstPort;
        int i7 = this.family;
        int i8 = this.protocol;
        String str2 = this.srcAddr;
        String str3 = this.dstAddr;
        String str4 = this.srcAddrV6;
        String str5 = this.dstAddrV6;
        String readExtras = readExtras(true);
        StringBuilder m = SystemServiceManager$$ExternalSyntheticOutline0.m(i, "when : ", j, " | what : ");
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(pid, i2, " | pid : ", " | uid : ", m);
        m.append(" | comm : ");
        m.append(str);
        m.append(" | oldstate : ");
        m.append(i3);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i4, i5, " | newstate : ", " | sport : ", m);
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i6, i7, " | dport : ", " | family : ", m);
        m.append(" | protocol : ");
        m.append(i8);
        m.append(" | saddr : ");
        m.append(str2);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(m, " | daddr : ", str3, " | saddr_v6 : ", str4);
        return BootReceiver$$ExternalSyntheticOutline0.m(m, " | daddr_v6 : ", str5, readExtras);
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Map toMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("when", Long.toString(this.actualEventTime));
        hashMap.put("what", Integer.toString(this.event));
        hashMap.put("pid", Integer.toString(getPid()));
        hashMap.put("uid", Integer.toString((int) this.uidGid));
        hashMap.put("comm", this.comm);
        hashMap.put("oldState", Integer.toString(this.oldState));
        hashMap.put("newState", Integer.toString(this.newState));
        hashMap.put("sport", Integer.toString(this.srcPort));
        hashMap.put("dport", Integer.toString(this.dstPort));
        hashMap.put("family", Integer.toString(this.family));
        hashMap.put("protocol", Integer.toString(this.protocol));
        hashMap.put("saddr", this.srcAddr);
        hashMap.put("daddr", this.dstAddr);
        hashMap.put("saddr_v6", this.srcAddrV6);
        hashMap.put("daddr_v6", this.dstAddrV6);
        return hashMap;
    }
}
