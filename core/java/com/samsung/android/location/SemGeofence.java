package com.samsung.android.location;

import android.hardware.scontext.SContextConstants;
import android.os.Parcel;
import android.os.Parcelable;

public class SemGeofence implements Parcelable {
    public static final Parcelable.Creator<SemGeofence> CREATOR =
            new Parcelable.Creator<
                    SemGeofence>() { // from class: com.samsung.android.location.SemGeofence.1
                @Override // android.os.Parcelable.Creator
                public SemGeofence createFromParcel(Parcel in) {
                    return new SemGeofence(in);
                }

                @Override // android.os.Parcelable.Creator
                public SemGeofence[] newArray(int size) {
                    return new SemGeofence[size];
                }
            };
    private String mBssid;
    private String[] mBssidList;
    private double mLatitude;
    private double mLongitude;
    private int mRadius;
    private String mRequestId;
    private int mType;

    public SemGeofence(int type, double latitude, double longitude, int radius) {
        this.mBssidList = null;
        this.mType = type;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mRadius = radius;
        this.mBssid = null;
    }

    public SemGeofence(int type, double latitude, double longitude, int radius, String[] bssid) {
        this.mBssidList = null;
        this.mType = type;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mRadius = radius;
        this.mBssid = null;
        this.mBssidList = bssid;
    }

    public SemGeofence(int type, String bssid) {
        this.mBssidList = null;
        this.mType = type;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRadius = 0;
        this.mBssid = bssid;
    }

    public SemGeofence(int type, String[] address, double latitude, double longitude) {
        this.mBssidList = null;
        this.mType = type;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mRadius = 0;
        this.mBssidList = address;
    }

    public void setRequestId(String requestId) {
        this.mRequestId = requestId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.mType);
        parcel.writeDouble(this.mLatitude);
        parcel.writeDouble(this.mLongitude);
        parcel.writeInt(this.mRadius);
        parcel.writeString(this.mBssid);
        parcel.writeStringArray(this.mBssidList);
        parcel.writeString(this.mRequestId);
    }

    private SemGeofence(Parcel in) {
        this.mBssidList = null;
        this.mType = in.readInt();
        this.mLatitude = in.readDouble();
        this.mLongitude = in.readDouble();
        this.mRadius = in.readInt();
        this.mBssid = in.readString();
        this.mBssidList = in.readStringArray();
        this.mRequestId = in.readString();
    }

    public int getType() {
        return this.mType;
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

    public String getBssid() {
        return this.mBssid;
    }

    public String getRequestId() {
        return this.mRequestId;
    }

    public String[] getBssids() {
        return this.mBssidList;
    }
}
