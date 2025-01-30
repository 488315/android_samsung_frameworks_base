package com.android.server.knox.p010zt.devicetrust.data;

/* loaded from: classes2.dex */
public abstract class SchedClsData extends EndpointData {
  public final int uid;

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public int getPid() {
    return 0;
  }

  public SchedClsData(int i, long j, int i2) {
    super(i, j);
    this.uid = i2;
  }

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public int getUid() {
    return this.uid;
  }
}
