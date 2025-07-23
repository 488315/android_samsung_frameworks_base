package com.samsung.android.location;

import android.hardware.scontext.SContextConstants;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemGeofence implements Parcelable {
    public static final Parcelable.Creator<SemGeofence> CREATOR = new Parcelable.Creator<SemGeofence>() { // from class: com.samsung.android.location.SemGeofence.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemGeofence createFromParcel(Parcel parcel) {
            return new SemGeofence(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemGeofence[] newArray(int i) {
            return new SemGeofence[i];
        }
    };
    private String mBssid;
    private String[] mBssidList;
    private double mLatitude;
    private double mLongitude;
    private int mRadius;
    private String mRequestId;
    private int mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemGeofence(int i, double d, double d2, int i2) {
        this.mBssidList = null;
        this.mType = i;
        this.mLatitude = d;
        this.mLongitude = d2;
        this.mRadius = i2;
        this.mBssid = null;
    }

    public SemGeofence(int i, double d, double d2, int i2, String[] strArr) {
        this.mType = i;
        this.mLatitude = d;
        this.mLongitude = d2;
        this.mRadius = i2;
        this.mBssid = null;
        this.mBssidList = strArr;
    }

    public SemGeofence(int i, String str) {
        this.mBssidList = null;
        this.mType = i;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRadius = 0;
        this.mBssid = str;
    }

    public SemGeofence(int i, String[] strArr, double d, double d2) {
        this.mType = i;
        this.mLatitude = d;
        this.mLongitude = d2;
        this.mRadius = 0;
        this.mBssidList = strArr;
    }

    public void setRequestId(String str) {
        this.mRequestId = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeDouble(this.mLatitude);
        parcel.writeDouble(this.mLongitude);
        parcel.writeInt(this.mRadius);
        parcel.writeString(this.mBssid);
        parcel.writeStringArray(this.mBssidList);
        parcel.writeString(this.mRequestId);
    }

    private SemGeofence(Parcel parcel) {
        this.mBssidList = null;
        this.mType = parcel.readInt();
        this.mLatitude = parcel.readDouble();
        this.mLongitude = parcel.readDouble();
        this.mRadius = parcel.readInt();
        this.mBssid = parcel.readString();
        this.mBssidList = parcel.readStringArray();
        this.mRequestId = parcel.readString();
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
