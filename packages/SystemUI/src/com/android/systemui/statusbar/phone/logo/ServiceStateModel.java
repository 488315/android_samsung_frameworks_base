package com.android.systemui.statusbar.phone.logo;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ServiceStateModel {
    public final boolean connected;
    public final boolean roaming;

    public ServiceStateModel(boolean z, boolean z2) {
        this.connected = z;
        this.roaming = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ServiceStateModel)) {
            return false;
        }
        ServiceStateModel serviceStateModel = (ServiceStateModel) obj;
        return this.connected == serviceStateModel.connected && this.roaming == serviceStateModel.roaming;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.roaming) + (Boolean.hashCode(this.connected) * 31);
    }

    public final String toString() {
        return "ServiceStateModel(connected=" + this.connected + ", roaming=" + this.roaming + ")";
    }
}
