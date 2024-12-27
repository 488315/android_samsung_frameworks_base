package com.samsung.android.location;

public class SemBluetoothGeofence {
    private final String mBssid;
    private final String mRequestId;

    public SemBluetoothGeofence(String bssid, String requestId) {
        this.mBssid = bssid;
        this.mRequestId = requestId;
    }

    public String getBssid() {
        return this.mBssid;
    }

    public String getRequestId() {
        return this.mRequestId;
    }
}
