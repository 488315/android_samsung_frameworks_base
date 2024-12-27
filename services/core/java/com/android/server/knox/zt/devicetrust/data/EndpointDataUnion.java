package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class EndpointDataUnion extends EndpointData {
    public final EndpointData realData;

    public EndpointDataUnion(EndpointData endpointData) {
        super(0, 0L);
        if (endpointData == null) {
            throw new IllegalArgumentException("Real data cannot be null");
        }
        this.realData = endpointData;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final EndpointData adjustTime(long j) {
        EndpointData typeChecked = getTypeChecked();
        return typeChecked != null ? typeChecked.adjustTime(j) : this;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getEvent() {
        EndpointData typeChecked = getTypeChecked();
        if (typeChecked != null) {
            return typeChecked.getEvent();
        }
        return 0;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getPid() {
        EndpointData typeChecked = getTypeChecked();
        if (typeChecked != null) {
            return typeChecked.getPid();
        }
        return 0;
    }

    public final EndpointData getReal() {
        return this.realData;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final long getTime() {
        EndpointData typeChecked = getTypeChecked();
        if (typeChecked != null) {
            return typeChecked.getTime();
        }
        return 0L;
    }

    public abstract EndpointData getTypeChecked();

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getUid() {
        EndpointData typeChecked = getTypeChecked();
        if (typeChecked != null) {
            return typeChecked.getUid();
        }
        return -1;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Bundle toBundle() {
        EndpointData typeChecked = getTypeChecked();
        return typeChecked != null ? typeChecked.toBundle() : new Bundle();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toJson() {
        EndpointData typeChecked = getTypeChecked();
        return typeChecked != null ? typeChecked.toJson() : new JSONObject().toString();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toLine() {
        EndpointData typeChecked = getTypeChecked();
        return typeChecked != null ? typeChecked.toLine() : "";
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Map toMap() {
        EndpointData typeChecked = getTypeChecked();
        return typeChecked != null ? typeChecked.toMap() : new HashMap();
    }
}
