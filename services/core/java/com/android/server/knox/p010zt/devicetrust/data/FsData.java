package com.android.server.knox.p010zt.devicetrust.data;

import android.os.Bundle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class FsData extends TracepointData {
  public final long ino;
  public final long pino;

  public FsData(int i, long j, long j2, long j3, long j4, long j5, String str) {
    super(i, j3, j4, j5, str);
    this.ino = j;
    this.pino = j2;
  }

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public String toLine() {
    return String.format(
        Locale.US,
        "when : %d | what : %d | pid : %d | uid : %d | comm : %s | ino : %d | pino : %d%s",
        Long.valueOf(getTime()),
        Integer.valueOf(getEvent()),
        Integer.valueOf(getPid()),
        Integer.valueOf(getUid()),
        getComm(),
        Long.valueOf(this.ino),
        Long.valueOf(this.pino),
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
    hashMap.put("ino", Long.toString(this.ino));
    hashMap.put("pino", Long.toString(this.pino));
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
    bundle.putLong("ino", this.ino);
    bundle.putLong("pino", this.pino);
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
      jSONObject.put("ino", this.ino);
      jSONObject.put("pino", this.pino);
      readExtras(jSONObject);
    } catch (JSONException unused) {
    }
    return jSONObject.toString();
  }
}
