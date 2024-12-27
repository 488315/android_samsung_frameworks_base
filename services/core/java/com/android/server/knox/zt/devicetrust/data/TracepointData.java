package com.android.server.knox.zt.devicetrust.data;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class TracepointData extends EndpointData {
    public final String comm;
    public final long pidTgid;
    public final long uidGid;

    public TracepointData(int i, long j, long j2, long j3, String str) {
        super(i, j);
        this.pidTgid = j2;
        this.uidGid = j3;
        this.comm = str == null ? "" : str;
    }

    public final String getComm() {
        return this.comm;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getPid() {
        return (int) (this.pidTgid >> 32);
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getUid() {
        return (int) this.uidGid;
    }
}
