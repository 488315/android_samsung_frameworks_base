package com.android.server.knox.zt.devicetrust.data;

public abstract class SchedClsData extends EndpointData {
    public final int uid;

    public SchedClsData(int i, long j, int i2) {
        super(i, j);
        this.uid = i2;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getPid() {
        return 0;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getUid() {
        return this.uid;
    }
}
