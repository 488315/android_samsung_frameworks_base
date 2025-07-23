package com.samsung.android.location;

/* loaded from: classes6.dex */
public class SemGeopointGeofence {
    private final double mLatitude;
    private final double mLongitude;
    private final int mRadius;
    private final String mRequestId;
    private String[] mWifiBssids;

    public SemGeopointGeofence(double d, double d2, int i, String str) {
        this.mLatitude = d;
        this.mLongitude = d2;
        this.mRadius = i;
        this.mRequestId = str;
    }

    public void setWifiBssids(String[] strArr) {
        this.mWifiBssids = strArr;
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
