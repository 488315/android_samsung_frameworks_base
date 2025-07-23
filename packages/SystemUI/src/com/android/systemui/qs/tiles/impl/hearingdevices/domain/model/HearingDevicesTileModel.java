package com.android.systemui.qs.tiles.impl.hearingdevices.domain.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HearingDevicesTileModel {
    public final boolean isAnyActiveHearingDevice;
    public final boolean isAnyPairedHearingDevice;

    public HearingDevicesTileModel(boolean z, boolean z2) {
        this.isAnyActiveHearingDevice = z;
        this.isAnyPairedHearingDevice = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HearingDevicesTileModel)) {
            return false;
        }
        HearingDevicesTileModel hearingDevicesTileModel = (HearingDevicesTileModel) obj;
        return this.isAnyActiveHearingDevice == hearingDevicesTileModel.isAnyActiveHearingDevice && this.isAnyPairedHearingDevice == hearingDevicesTileModel.isAnyPairedHearingDevice;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isAnyPairedHearingDevice) + (Boolean.hashCode(this.isAnyActiveHearingDevice) * 31);
    }

    public final String toString() {
        return "HearingDevicesTileModel(isAnyActiveHearingDevice=" + this.isAnyActiveHearingDevice + ", isAnyPairedHearingDevice=" + this.isAnyPairedHearingDevice + ")";
    }
}
