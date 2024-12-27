package com.android.server.knox.zt.devicetrust.data;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
