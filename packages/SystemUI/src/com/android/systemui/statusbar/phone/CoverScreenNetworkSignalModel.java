package com.android.systemui.statusbar.phone;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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

    public final int hashCode() {
        return Integer.hashCode(this.noServiceType) + (Boolean.hashCode(this.isAirplaneMode) * 31);
    }

    public final String toString() {
        return "CoverScreenNetworkSignalModel(isAirplaneMode=" + this.isAirplaneMode + ", noServiceType=" + this.noServiceType + ")";
    }
}
