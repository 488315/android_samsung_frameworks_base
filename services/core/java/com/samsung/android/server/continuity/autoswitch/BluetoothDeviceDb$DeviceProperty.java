package com.samsung.android.server.continuity.autoswitch;

public final class BluetoothDeviceDb$DeviceProperty {
    public final String mAddress;
    public final String mName;

    public BluetoothDeviceDb$DeviceProperty(String str, String str2) {
        this.mAddress = str;
        this.mName = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || BluetoothDeviceDb$DeviceProperty.class != obj.getClass()) {
            return false;
        }
        return this.mAddress.equals(((BluetoothDeviceDb$DeviceProperty) obj).mAddress);
    }

    public final int hashCode() {
        return this.mAddress.hashCode();
    }
}
