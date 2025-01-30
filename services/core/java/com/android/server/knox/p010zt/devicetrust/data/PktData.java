package com.android.server.knox.p010zt.devicetrust.data;

import android.os.Bundle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
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

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public String toLine() {
    return String.format(
        Locale.US,
        "when : %d | what : %d | uid : %d | type : %d | fingerprint : %s | saddr : %s | daddr : %s"
            + " | sport : %d | dport : %d%s",
        Long.valueOf(getTime()),
        Integer.valueOf(getEvent()),
        Integer.valueOf(getUid()),
        Integer.valueOf(this.type),
        this.fingerprint,
        this.srcAddr,
        this.dstAddr,
        Integer.valueOf(this.srcPort),
        Integer.valueOf(this.dstPort),
        readExtras(true));
  }

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public Map toMap() {
    HashMap hashMap = new HashMap();
    hashMap.put("when", Long.toString(getTime()));
    hashMap.put("what", Integer.toString(getEvent()));
    hashMap.put("uid", Integer.toString(getUid()));
    hashMap.put("type", Integer.toString(this.type));
    hashMap.put("fingerprint", this.fingerprint);
    hashMap.put("saddr", this.srcAddr);
    hashMap.put("daddr", this.dstAddr);
    hashMap.put("sport", Integer.toString(this.srcPort));
    hashMap.put("dport", Integer.toString(this.dstPort));
    return hashMap;
  }

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public Bundle toBundle() {
    Bundle bundle = new Bundle();
    bundle.putLong("when", getTime());
    bundle.putInt("what", getEvent());
    bundle.putInt("uid", getUid());
    bundle.putInt("type", this.type);
    bundle.putString("fingerprint", this.fingerprint);
    bundle.putString("saddr", this.srcAddr);
    bundle.putString("daddr", this.dstAddr);
    bundle.putInt("sport", this.srcPort);
    bundle.putInt("dport", this.dstPort);
    readExtras(bundle);
    return bundle;
  }

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public String toJson() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("when", getTime());
      jSONObject.put("what", getEvent());
      jSONObject.put("uid", getUid());
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
}
