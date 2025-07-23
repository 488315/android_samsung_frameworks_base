package com.samsung.android.location;

/* loaded from: classes6.dex */
public class SemWifiGeofence {
    private final String mBssid;
    private final String mRequestId;

    public SemWifiGeofence(String str, String str2) {
        this.mBssid = str;
        this.mRequestId = str2;
    }

    public String getBssid() {
        return this.mBssid;
    }

    public String getRequestId() {
        return this.mRequestId;
    }
}
