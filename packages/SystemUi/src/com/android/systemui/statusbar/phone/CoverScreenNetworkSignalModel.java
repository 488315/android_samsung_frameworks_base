package com.android.systemui.statusbar.phone;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CoverScreenNetworkSignalModel {
    public final boolean isAirplaneMode;
    public final int noServiceType;

    public CoverScreenNetworkSignalModel(boolean z, int i) {
        this.isAirplaneMode = z;
        this.noServiceType = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CoverScreenNetworkSignalModel)) {
            return false;
        }
        CoverScreenNetworkSignalModel coverScreenNetworkSignalModel = (CoverScreenNetworkSignalModel) obj;
        return this.isAirplaneMode == coverScreenNetworkSignalModel.isAirplaneMode && this.noServiceType == coverScreenNetworkSignalModel.noServiceType;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    public final int hashCode() {
        boolean z = this.isAirplaneMode;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return Integer.hashCode(this.noServiceType) + (r0 * 31);
    }

    public final String toString() {
        return "CoverScreenNetworkSignalModel(isAirplaneMode=" + this.isAirplaneMode + ", noServiceType=" + this.noServiceType + ")";
    }
}
