package com.android.systemui.shade.carrier;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ServiceStateInfo {
    public final boolean connected;
    public final boolean isEmergency;
    public final boolean isRoaming;
    public final int networkType;
    public final int voiceNetworkType;

    public ServiceStateInfo(int i, int i2, boolean z, boolean z2, boolean z3) {
        this.networkType = i;
        this.voiceNetworkType = i2;
        this.isEmergency = z;
        this.isRoaming = z2;
        this.connected = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ServiceStateInfo)) {
            return false;
        }
        ServiceStateInfo serviceStateInfo = (ServiceStateInfo) obj;
        return this.networkType == serviceStateInfo.networkType && this.voiceNetworkType == serviceStateInfo.voiceNetworkType && this.isEmergency == serviceStateInfo.isEmergency && this.isRoaming == serviceStateInfo.isRoaming && this.connected == serviceStateInfo.connected;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.voiceNetworkType, Integer.hashCode(this.networkType) * 31, 31);
        boolean z = this.isEmergency;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (m42m + i) * 31;
        boolean z2 = this.isRoaming;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.connected;
        return i4 + (z3 ? 1 : z3 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ServiceStateInfo(networkType=");
        sb.append(this.networkType);
        sb.append(", voiceNetworkType=");
        sb.append(this.voiceNetworkType);
        sb.append(", isEmergency=");
        sb.append(this.isEmergency);
        sb.append(", isRoaming=");
        sb.append(this.isRoaming);
        sb.append(", connected=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.connected, ")");
    }
}
