package com.android.systemui.statusbar.phone;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
