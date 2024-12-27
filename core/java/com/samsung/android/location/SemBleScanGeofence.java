package com.samsung.android.location;

public class SemBleScanGeofence {
    private final String[] mAddress;
    private double mLatitude = 200.0d;
    private double mLongitude = 200.0d;
    private final String mRequestId;

    public SemBleScanGeofence(String[] address, String requestId) {
        this.mAddress = address;
        this.mRequestId = requestId;
    }

    public boolean setGeopoint(double latitude, double longitude) {
        if (!isLatLonValid(latitude, longitude)) {
            return false;
        }
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        return true;
    }

    private boolean isLatLonValid(double lat, double lon) {
        double lat2 = Math.abs(lat);
        double lon2 = Math.abs(lon);
        if (90.0d >= lat2 && 180.0d >= lon2) {
            return lat2 >= 1.0E-6d || lon2 >= 1.0E-6d;
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
