package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class PktData extends SchedClsData {
    public final String dstAddr;
    public final int dstPort;
    public final String fingerprint;
    public final String srcAddr;
    public final int srcPort;
    public final int type;

    public PktData(
            int i, long j, int i2, int i3, String str, String str2, String str3, int i4, int i5) {
        super(i, j, i2);
        this.type = i3;
        this.fingerprint = str;
        this.srcAddr = str2;
        this.dstAddr = str3;
        this.srcPort = i4;
        this.dstPort = i5;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("when", this.actualEventTime);
        bundle.putInt("what", this.event);
        bundle.putInt("uid", this.uid);
        bundle.putInt("type", this.type);
        bundle.putString("fingerprint", this.fingerprint);
        bundle.putString("saddr", this.srcAddr);
        bundle.putString("daddr", this.dstAddr);
        bundle.putInt("sport", this.srcPort);
        bundle.putInt("dport", this.dstPort);
        readExtras(bundle);
        return bundle;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("when", this.actualEventTime);
            jSONObject.put("what", this.event);
            jSONObject.put("uid", this.uid);
            jSONObject.put("type", this.type);
            jSONObject.put("fingerprint", this.fingerprint);
            jSONObject.put("saddr", this.srcAddr);
            jSONObject.put("daddr", this.dstAddr);
            jSONObject.put("sport", this.srcPort);
            jSONObject.put("dport", this.dstPort);
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
        int i2 = this.uid;
        int i3 = this.type;
        String str = this.fingerprint;
        String str2 = this.srcAddr;
        String str3 = this.dstAddr;
        int i4 = this.srcPort;
        int i5 = this.dstPort;
        String readExtras = readExtras(true);
        StringBuilder m =
                SystemServiceManager$$ExternalSyntheticOutline0.m(i, "when : ", j, " | what : ");
        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                i2, i3, " | uid : ", " | type : ", m);
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                m, " | fingerprint : ", str, " | saddr : ", str2);
        m.append(" | daddr : ");
        m.append(str3);
        m.append(" | sport : ");
        m.append(i4);
        m.append(" | dport : ");
        m.append(i5);
        m.append(readExtras);
        return m.toString();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Map toMap() {
        HashMap hashMap = new HashMap();
        hashMap.put("when", Long.toString(this.actualEventTime));
        hashMap.put("what", Integer.toString(this.event));
        hashMap.put("uid", Integer.toString(this.uid));
        hashMap.put("type", Integer.toString(this.type));
        hashMap.put("fingerprint", this.fingerprint);
        hashMap.put("saddr", this.srcAddr);
        hashMap.put("daddr", this.dstAddr);
        hashMap.put("sport", Integer.toString(this.srcPort));
        hashMap.put("dport", Integer.toString(this.dstPort));
        return hashMap;
    }
}
