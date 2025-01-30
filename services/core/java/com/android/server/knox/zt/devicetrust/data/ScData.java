package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class ScData extends EndpointData {
  public static final String TAG = "ScData";
  public final EndpointData data;

  /* renamed from: nr */
  public final int f1710nr;

  public ScData(int i, EndpointData endpointData) {
    super(0, 0L);
    this.f1710nr = i;
    this.data = endpointData;
  }

  public ScOpenData getOpenData() {
    try {
      return (ScOpenData) this.data;
    } catch (ClassCastException unused) {
      printCastError();
      return null;
    }
  }

  public ScCloseData getCloseData() {
    try {
      return (ScCloseData) this.data;
    } catch (ClassCastException unused) {
      printCastError();
      return null;
    }
  }

  public ScMountData getMountData() {
    try {
      return (ScMountData) this.data;
    } catch (ClassCastException unused) {
      printCastError();
      return null;
    }
  }

  public ScExecveData getExecveData() {
    try {
      return (ScExecveData) this.data;
    } catch (ClassCastException unused) {
      printCastError();
      return null;
    }
  }

  public final void printCastError() {
    Log.e(TAG, "Not castable(NR : " + this.f1710nr + ")");
  }

  public final EndpointData self() {
    int i = this.f1710nr;
    if (i == 40) {
      return getMountData();
    }
    if (i == 221) {
      return getExecveData();
    }
    if (i == 56) {
      return getOpenData();
    }
    if (i != 57) {
      return null;
    }
    return getCloseData();
  }

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public int getEvent() {
    EndpointData self = self();
    if (self != null) {
      return self.getEvent();
    }
    return 0;
  }

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public long getTime() {
    EndpointData self = self();
    if (self != null) {
      return self.getTime();
    }
    return 0L;
  }

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public EndpointData adjustTime(long j) {
    EndpointData self = self();
    return self != null ? self.adjustTime(j) : this;
  }

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public String toLine() {
    EndpointData self = self();
    return self != null ? self.toLine() : "";
  }

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public Map toMap() {
    EndpointData self = self();
    if (self != null) {
      return self.toMap();
    }
    return new HashMap();
  }

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public Bundle toBundle() {
    EndpointData self = self();
    if (self != null) {
      return self.toBundle();
    }
    return new Bundle();
  }

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public String toJson() {
    EndpointData self = self();
    if (self != null) {
      return self.toJson();
    }
    return new JSONObject().toString();
  }

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public int getPid() {
    EndpointData self = self();
    if (self != null) {
      return self.getPid();
    }
    return 0;
  }

  @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
  public int getUid() {
    EndpointData self = self();
    if (self != null) {
      return self.getUid();
    }
    return -1;
  }
}
