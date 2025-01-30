package com.android.server.knox.p010zt.devicetrust.data;

/* loaded from: classes2.dex */
public abstract class TracepointData extends EndpointData {
  public final String comm;
  public final long pidTgid;
  public final long uidGid;

  public TracepointData(int i, long j, long j2, long j3, String str) {
    super(i, j);
    this.pidTgid = j2;
    this.uidGid = j3;
    this.comm = Utils.nullSafe(str);
  }

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public int getPid() {
    return (int) (this.pidTgid >> 32);
  }

  @Override // com.android.server.knox.p010zt.devicetrust.data.EndpointData
  public int getUid() {
    return (int) this.uidGid;
  }

  public String getComm() {
    return this.comm;
  }
}
