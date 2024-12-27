package com.android.systemui.media.controls.domain.pipeline;

import com.android.settingslib.media.MediaDevice;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class AboutToConnectDevice {
    public final MediaDeviceData backupMediaDeviceData;
    public final MediaDevice fullMediaDevice;

    public AboutToConnectDevice() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AboutToConnectDevice)) {
            return false;
        }
        AboutToConnectDevice aboutToConnectDevice = (AboutToConnectDevice) obj;
        return Intrinsics.areEqual(this.fullMediaDevice, aboutToConnectDevice.fullMediaDevice) && Intrinsics.areEqual(this.backupMediaDeviceData, aboutToConnectDevice.backupMediaDeviceData);
    }

    public final int hashCode() {
        MediaDevice mediaDevice = this.fullMediaDevice;
        int hashCode = (mediaDevice == null ? 0 : mediaDevice.hashCode()) * 31;
        MediaDeviceData mediaDeviceData = this.backupMediaDeviceData;
        return hashCode + (mediaDeviceData != null ? mediaDeviceData.hashCode() : 0);
    }

    public final String toString() {
        return "AboutToConnectDevice(fullMediaDevice=" + this.fullMediaDevice + ", backupMediaDeviceData=" + this.backupMediaDeviceData + ")";
    }

    public AboutToConnectDevice(MediaDevice mediaDevice, MediaDeviceData mediaDeviceData) {
        this.fullMediaDevice = mediaDevice;
        this.backupMediaDeviceData = mediaDeviceData;
    }

    public /* synthetic */ AboutToConnectDevice(MediaDevice mediaDevice, MediaDeviceData mediaDeviceData, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : mediaDevice, (i & 2) != 0 ? null : mediaDeviceData);
    }
}
