package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class ScMountData extends TracepointData {
  public final String data;
  public final String devName;
  public final String dirName;
  public final long flags;
  public final long ret;
  public final String type;

  public ScMountData(
      int i,
      String str,
      String str2,
      String str3,
      long j,
      String str4,
      long j2,
      long j3,
      long j4,
      long j5,
      String str5) {
    super(i, j3, j4, j5, str5);
    this.devName = str;
    this.dirName = str2;
    this.type = str3;
    this.flags = j;
    this.data = str4;
    this.ret = j2;
  }

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public String toLine() {
    return String.format(
        Locale.US,
        "when : %d | what : %d | pid : %d | uid : %d | comm : %s | dev_name : %s | dir_name : %s |"
            + " type : %s | flags : %d | data : %s | ret : %d%s",
        Long.valueOf(getTime()),
        Integer.valueOf(getEvent()),
        Integer.valueOf(getPid()),
        Integer.valueOf(getUid()),
        getComm(),
        this.devName,
        this.dirName,
        this.type,
        Long.valueOf(this.flags),
        this.data,
        Long.valueOf(this.ret),
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
    hashMap.put("dev_name", this.devName);
    hashMap.put("dir_name", this.dirName);
    hashMap.put("type", this.type);
    hashMap.put("flags", Long.toString(this.flags));
    hashMap.put("data", this.data);
    hashMap.put("ret", Long.toString(this.ret));
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
    bundle.putString("dev_name", this.devName);
    bundle.putString("dir_name", this.dirName);
    bundle.putString("type", this.type);
    bundle.putLong("flags", this.flags);
    bundle.putString("data", this.data);
    bundle.putLong("ret", this.ret);
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
      jSONObject.put("dev_name", this.devName);
      jSONObject.put("dir_name", this.dirName);
      jSONObject.put("type", this.type);
      jSONObject.put("flags", this.flags);
      jSONObject.put("data", this.data);
      jSONObject.put("ret", this.ret);
      readExtras(jSONObject);
    } catch (JSONException unused) {
    }
    return jSONObject.toString();
  }
}
