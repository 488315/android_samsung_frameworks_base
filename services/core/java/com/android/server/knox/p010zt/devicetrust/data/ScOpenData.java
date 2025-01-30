package com.android.server.knox.p010zt.devicetrust.data;

import android.os.Bundle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class ScOpenData extends TracepointData {
  public final String filename;
  public final int flags;
  public final int mode;
  public final long ret;

  public ScOpenData(
      int i, String str, int i2, int i3, long j, long j2, long j3, long j4, String str2) {
    super(i, j2, j3, j4, str2);
    this.filename = str;
    this.flags = i2;
    this.mode = i3;
    this.ret = j;
  }

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public String toLine() {
    return String.format(
        Locale.US,
        "when : %d | what : %d | pid : %d | uid : %d | comm : %s | filename : %s | flags : %d |"
            + " mode : %d | ret : %d%s",
        Long.valueOf(getTime()),
        Integer.valueOf(getEvent()),
        Integer.valueOf(getPid()),
        Integer.valueOf(getUid()),
        getComm(),
        this.filename,
        Integer.valueOf(this.flags),
        Integer.valueOf(this.mode),
        Long.valueOf(this.ret),
        readExtras(true));
  }

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public Map toMap() {
    HashMap hashMap = new HashMap();
    hashMap.put("when", Long.toString(getTime()));
    hashMap.put("what", Integer.toString(getEvent()));
    hashMap.put("pid", Integer.toString(getPid()));
    hashMap.put("uid", Integer.toString(getUid()));
    hashMap.put("comm", getComm());
    hashMap.put("filename", this.filename);
    hashMap.put("flags", Integer.toString(this.flags));
    hashMap.put("mode", Integer.toString(this.mode));
    hashMap.put("ret", Long.toString(this.ret));
    return hashMap;
  }

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public Bundle toBundle() {
    Bundle bundle = new Bundle();
    bundle.putLong("when", getTime());
    bundle.putInt("what", getEvent());
    bundle.putInt("pid", getPid());
    bundle.putInt("uid", getUid());
    bundle.putString("comm", getComm());
    bundle.putString("filename", this.filename);
    bundle.putInt("flags", this.flags);
    bundle.putInt("mode", this.mode);
    bundle.putLong("ret", this.ret);
    readExtras(bundle);
    return bundle;
  }

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public String toJson() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("when", getTime());
      jSONObject.put("what", getEvent());
      jSONObject.put("pid", getPid());
      jSONObject.put("uid", getUid());
      jSONObject.put("comm", getComm());
      jSONObject.put("filename", this.filename);
      jSONObject.put("flags", this.flags);
      jSONObject.put("mode", this.mode);
      jSONObject.put("ret", this.ret);
      readExtras(jSONObject);
    } catch (JSONException unused) {
    }
    return jSONObject.toString();
  }
}
