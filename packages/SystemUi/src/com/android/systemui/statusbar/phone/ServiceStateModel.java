package com.android.systemui.statusbar.phone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.connected;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = i * 31;
        boolean z2 = this.roaming;
        return i2 + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final String toString() {
        return "ServiceStateModel(connected=" + this.connected + ", roaming=" + this.roaming + ")";
    }
}
