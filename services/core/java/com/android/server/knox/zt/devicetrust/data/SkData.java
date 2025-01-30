package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
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

  public SkData(
      int i,
      int i2,
      int i3,
      int i4,
      int i5,
      int i6,
      int i7,
      String str,
      String str2,
      String str3,
      String str4,
      long j,
      long j2,
      long j3,
      String str5) {
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
  public String toLine() {
    return String.format(
        Locale.US,
        "when : %d | what : %d | pid : %d | uid : %d | comm : %s | oldstate : %d | newstate : %d |"
            + " sport : %d | dport : %d | family : %d | protocol : %d | saddr : %s | daddr : %s |"
            + " saddr_v6 : %s | daddr_v6 : %s%s",
        Long.valueOf(getTime()),
        Integer.valueOf(getEvent()),
        Integer.valueOf(getPid()),
        Integer.valueOf(getUid()),
        getComm(),
        Integer.valueOf(this.oldState),
        Integer.valueOf(this.newState),
        Integer.valueOf(this.srcPort),
        Integer.valueOf(this.dstPort),
        Integer.valueOf(this.family),
        Integer.valueOf(this.protocol),
        this.srcAddr,
        this.dstAddr,
        this.srcAddrV6,
        this.dstAddrV6,
        readExtras(true));
  }

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public Map toMap() {
    HashMap hashMap = new HashMap();
    hashMap.put("when", Long.toString(getTime()));
    hashMap.put("what", Integer.toString(getEvent()));
    hashMap.put("pid", Integer.toString(getPid()));
    hashMap.put("uid", Integer.toString(getUid()));
    hashMap.put("comm", getComm());
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

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public Bundle toBundle() {
    Bundle bundle = new Bundle();
    bundle.putLong("when", getTime());
    bundle.putInt("what", getEvent());
    bundle.putInt("pid", getPid());
    bundle.putInt("uid", getUid());
    bundle.putString("comm", getComm());
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
  public String toJson() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("when", getTime());
      jSONObject.put("what", getEvent());
      jSONObject.put("pid", getPid());
      jSONObject.put("uid", getUid());
      jSONObject.put("comm", getComm());
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
}
