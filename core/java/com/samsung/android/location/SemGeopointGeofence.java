package com.samsung.android.location;

public class SemGeopointGeofence {
    private final double mLatitude;
    private final double mLongitude;
    private final int mRadius;
    private final String mRequestId;
    private String[] mWifiBssids;

    public SemGeopointGeofence(double latitude, double longitude, int radius, String requestId) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mRadius = radius;
        this.mRequestId = requestId;
    }

    public void setWifiBssids(String[] wifiBssids) {
        this.mWifiBssids = wifiBssids;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public int getRadius() {
        return this.mRadius;
    }

    public String[] getWifiBssidList() {
        return this.mWifiBssids;
    }

    public String getRequestId() {
        return this.mRequestId;
    }
}
