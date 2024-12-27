package com.android.server.knox.zt.devicetrust.data;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public final class ScData extends EndpointData {
    public static final String TAG = "ScData";
    public final EndpointData data;
    public final int nr;

    public ScData(int i, EndpointData endpointData) {
        super(0, 0L);
        this.nr = i;
        this.data = endpointData;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final EndpointData adjustTime(long j) {
        EndpointData self = self();
        return self != null ? self.adjustTime(j) : this;
    }

    public final ScCloseData getCloseData() {
        try {
            return (ScCloseData) this.data;
        } catch (ClassCastException unused) {
            printCastError();
            return null;
        }
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getEvent() {
        EndpointData self = self();
        if (self != null) {
            return self.getEvent();
        }
        return 0;
    }

    public final ScExecveData getExecveData() {
        try {
            return (ScExecveData) this.data;
        } catch (ClassCastException unused) {
            printCastError();
            return null;
        }
    }

    public final ScFchmodData getFchmodData() {
        try {
            return (ScFchmodData) this.data;
        } catch (ClassCastException unused) {
            printCastError();
            return null;
        }
    }

    public final ScFchmodatData getFchmodatData() {
        try {
            return (ScFchmodatData) this.data;
        } catch (ClassCastException unused) {
            printCastError();
            return null;
        }
    }

    public final ScFchownData getFchownData() {
        try {
            return (ScFchownData) this.data;
        } catch (ClassCastException unused) {
            printCastError();
            return null;
        }
    }

    public final ScFchownatData getFchownatData() {
        try {
            return (ScFchownatData) this.data;
        } catch (ClassCastException unused) {
            printCastError();
            return null;
        }
    }

    public final ScMemfdCreateData getMemfdCreateData() {
        try {
            return (ScMemfdCreateData) this.data;
        } catch (ClassCastException unused) {
            printCastError();
            return null;
        }
    }

    public final ScMountData getMountData() {
        try {
            return (ScMountData) this.data;
        } catch (ClassCastException unused) {
            printCastError();
            return null;
        }
    }

    public final ScOpenData getOpenData() {
        try {
            return (ScOpenData) this.data;
        } catch (ClassCastException unused) {
            printCastError();
            return null;
        }
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getPid() {
        EndpointData self = self();
        if (self != null) {
            return self.getPid();
        }
        return 0;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final long getTime() {
        EndpointData self = self();
        if (self != null) {
            return self.getTime();
        }
        return 0L;
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final int getUid() {
        EndpointData self = self();
        if (self != null) {
            return self.getUid();
        }
        return -1;
    }

    public final void printCastError() {
        Log.e(TAG, "Not castable(NR : " + this.nr + ")");
    }

    public final EndpointData self() {
        int i = this.nr;
        if (i == 40) {
            return getMountData();
        }
        if (i == 221) {
            return getExecveData();
        }
        if (i == 279) {
            return getMemfdCreateData();
        }
        switch (i) {
            case 52:
                return getFchmodData();
            case 53:
                return getFchmodatData();
            case 54:
                return getFchownatData();
            case 55:
                return getFchownData();
            case 56:
                return getOpenData();
            case 57:
                return getCloseData();
            default:
                return null;
        }
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Bundle toBundle() {
        EndpointData self = self();
        return self != null ? self.toBundle() : new Bundle();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toJson() {
        EndpointData self = self();
        return self != null ? self.toJson() : new JSONObject().toString();
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final String toLine() {
        EndpointData self = self();
        return self != null ? self.toLine() : "";
    }

    @Override // com.android.server.knox.zt.devicetrust.data.EndpointData
    public final Map toMap() {
        EndpointData self = self();
        return self != null ? self.toMap() : new HashMap();
    }
}
