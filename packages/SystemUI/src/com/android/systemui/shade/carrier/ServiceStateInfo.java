package com.android.systemui.shade.carrier;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public final int hashCode() {
        return Boolean.hashCode(this.connected) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.voiceNetworkType, Integer.hashCode(this.networkType) * 31, 31), 31, this.isEmergency), 31, this.isRoaming);
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
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.connected, ")");
    }
}
