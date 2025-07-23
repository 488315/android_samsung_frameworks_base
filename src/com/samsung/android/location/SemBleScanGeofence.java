package com.samsung.android.location;

/* loaded from: classes6.dex */
public class SemBleScanGeofence {
    private final String[] mAddress;
    private double mLatitude = 200.0d;
    private double mLongitude = 200.0d;
    private final String mRequestId;

    public SemBleScanGeofence(String[] strArr, String str) {
        this.mAddress = strArr;
        this.mRequestId = str;
    }

    public boolean setGeopoint(double d, double d2) {
        if (!isLatLonValid(d, d2)) {
            return false;
        }
        this.mLatitude = d;
        this.mLongitude = d2;
        return true;
    }

    private boolean isLatLonValid(double d, double d2) {
        double abs = Math.abs(d);
        double abs2 = Math.abs(d2);
        if (90.0d >= abs && 180.0d >= abs2) {
            return abs >= 1.0E-6d || abs2 >= 1.0E-6d;
        }
        return false;
    }

    public String[] getAddress() {
        return this.mAddress;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public String getRequestId() {
        return this.mRequestId;
    }
}
