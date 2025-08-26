package com.android.systemui.inputdevice.data.model;

/* loaded from: classes2.dex */
public final class UserDeviceConnectionStatus {
    public final boolean isConnected;
    public final int userId;

    public UserDeviceConnectionStatus(boolean z, int i) {
        this.isConnected = z;
        this.userId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserDeviceConnectionStatus)) {
            return false;
        }
        UserDeviceConnectionStatus userDeviceConnectionStatus = (UserDeviceConnectionStatus) obj;
        return this.isConnected == userDeviceConnectionStatus.isConnected && this.userId == userDeviceConnectionStatus.userId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.userId) + (Boolean.hashCode(this.isConnected) * 31);
    }

    public final String toString() {
        return "UserDeviceConnectionStatus(isConnected=" + this.isConnected + ", userId=" + this.userId + ")";
    }
}
