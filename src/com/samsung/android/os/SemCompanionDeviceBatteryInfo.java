package com.samsung.android.os;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public final class SemCompanionDeviceBatteryInfo implements Parcelable {
    public static final Parcelable.Creator<SemCompanionDeviceBatteryInfo> CREATOR = new Parcelable.Creator<SemCompanionDeviceBatteryInfo>() { // from class: com.samsung.android.os.SemCompanionDeviceBatteryInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCompanionDeviceBatteryInfo createFromParcel(Parcel parcel) {
            return new SemCompanionDeviceBatteryInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCompanionDeviceBatteryInfo[] newArray(int i) {
            return new SemCompanionDeviceBatteryInfo[i];
        }
    };
    private String mAddress;
    private int mBatteryLevel;
    private int mBatteryStatus;
    private String mDeviceName;
    private int mDeviceType;
    private int mExtraBatteryLevelCradle;
    private int mExtraBatteryLevelLeft;
    private int mExtraBatteryLevelRight;
    private int mExtraBatteryStatusCradle;
    private int mExtraBatteryStatusLeft;
    private int mExtraBatteryStatusRight;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public void setAddress(String str) {
        this.mAddress = str;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public void setDeviceName(String str) {
        this.mDeviceName = str;
    }

    public int getDeviceType() {
        return this.mDeviceType;
    }

    public void setDeviceType(int i) {
        this.mDeviceType = i;
    }

    public int getBatteryLevel() {
        return this.mBatteryLevel;
    }

    public void setBatteryLevel(int i) {
        this.mBatteryLevel = i;
    }

    public int getBatteryStatus() {
        return this.mBatteryStatus;
    }

    public void setBatteryStatus(int i) {
        this.mBatteryStatus = i;
    }

    public int getExtraBatteryLevelLeft() {
        return this.mExtraBatteryLevelLeft;
    }

    public void setExtraBatteryLevelLeft(int i) {
        this.mExtraBatteryLevelLeft = i;
    }

    public int getExtraBatteryLevelRight() {
        return this.mExtraBatteryLevelRight;
    }

    public void setExtraBatteryLevelRight(int i) {
        this.mExtraBatteryLevelRight = i;
    }

    public int getExtraBatteryLevelCradle() {
        return this.mExtraBatteryLevelCradle;
    }

    public void setExtraBatteryLevelCradle(int i) {
        this.mExtraBatteryLevelCradle = i;
    }

    public int getExtraBatteryStatusLeft() {
        return this.mExtraBatteryStatusLeft;
    }

    public void setExtraBatteryStatusLeft(int i) {
        this.mExtraBatteryStatusLeft = i;
    }

    public int getExtraBatteryStatusRight() {
        return this.mExtraBatteryStatusRight;
    }

    public void setExtraBatteryStatusRight(int i) {
        this.mExtraBatteryStatusRight = i;
    }

    public int getExtraBatteryStatusCradle() {
        return this.mExtraBatteryStatusCradle;
    }

    public void setExtraBatteryStatusCradle(int i) {
        this.mExtraBatteryStatusCradle = i;
    }

    public SemCompanionDeviceBatteryInfo() {
        this.mBatteryLevel = -1;
        this.mExtraBatteryLevelLeft = -1;
        this.mExtraBatteryLevelRight = -1;
        this.mExtraBatteryLevelCradle = -1;
        this.mBatteryStatus = 1;
        this.mExtraBatteryStatusLeft = 1;
        this.mExtraBatteryStatusRight = 1;
        this.mExtraBatteryStatusCradle = 1;
    }

    private SemCompanionDeviceBatteryInfo(Parcel parcel) {
        this.mBatteryLevel = -1;
        this.mExtraBatteryLevelLeft = -1;
        this.mExtraBatteryLevelRight = -1;
        this.mExtraBatteryLevelCradle = -1;
        this.mBatteryStatus = 1;
        this.mExtraBatteryStatusLeft = 1;
        this.mExtraBatteryStatusRight = 1;
        this.mExtraBatteryStatusCradle = 1;
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mAddress);
        parcel.writeString(this.mDeviceName);
        parcel.writeInt(this.mDeviceType);
        parcel.writeInt(this.mBatteryLevel);
        parcel.writeInt(this.mExtraBatteryLevelLeft);
        parcel.writeInt(this.mExtraBatteryLevelRight);
        parcel.writeInt(this.mExtraBatteryLevelCradle);
        parcel.writeInt(this.mBatteryStatus);
        parcel.writeInt(this.mExtraBatteryStatusLeft);
        parcel.writeInt(this.mExtraBatteryStatusRight);
        parcel.writeInt(this.mExtraBatteryStatusCradle);
    }

    public void readFromParcel(Parcel parcel) {
        this.mAddress = parcel.readString();
        this.mDeviceName = parcel.readString();
        this.mDeviceType = parcel.readInt();
        this.mBatteryLevel = parcel.readInt();
        this.mExtraBatteryLevelLeft = parcel.readInt();
        this.mExtraBatteryLevelRight = parcel.readInt();
        this.mExtraBatteryLevelCradle = parcel.readInt();
        this.mBatteryStatus = parcel.readInt();
        this.mExtraBatteryStatusLeft = parcel.readInt();
        this.mExtraBatteryStatusRight = parcel.readInt();
        this.mExtraBatteryStatusCradle = parcel.readInt();
    }
}
