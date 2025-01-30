package com.samsung.android.p042os;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public final class SemCompanionDeviceBatteryInfo implements Parcelable {
    public static final Parcelable.Creator<SemCompanionDeviceBatteryInfo> CREATOR = new Parcelable.Creator<SemCompanionDeviceBatteryInfo>() { // from class: com.samsung.android.os.SemCompanionDeviceBatteryInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCompanionDeviceBatteryInfo createFromParcel(Parcel in) {
            return new SemCompanionDeviceBatteryInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCompanionDeviceBatteryInfo[] newArray(int size) {
            return new SemCompanionDeviceBatteryInfo[size];
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

    public String getAddress() {
        return this.mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public void setDeviceName(String deviceName) {
        this.mDeviceName = deviceName;
    }

    public int getDeviceType() {
        return this.mDeviceType;
    }

    public void setDeviceType(int deviceType) {
        this.mDeviceType = deviceType;
    }

    public int getBatteryLevel() {
        return this.mBatteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.mBatteryLevel = batteryLevel;
    }

    public int getBatteryStatus() {
        return this.mBatteryStatus;
    }

    public void setBatteryStatus(int batteryStatus) {
        this.mBatteryStatus = batteryStatus;
    }

    public int getExtraBatteryLevelLeft() {
        return this.mExtraBatteryLevelLeft;
    }

    public void setExtraBatteryLevelLeft(int batteryLevel) {
        this.mExtraBatteryLevelLeft = batteryLevel;
    }

    public int getExtraBatteryLevelRight() {
        return this.mExtraBatteryLevelRight;
    }

    public void setExtraBatteryLevelRight(int batteryLevel) {
        this.mExtraBatteryLevelRight = batteryLevel;
    }

    public int getExtraBatteryLevelCradle() {
        return this.mExtraBatteryLevelCradle;
    }

    public void setExtraBatteryLevelCradle(int batteryLevel) {
        this.mExtraBatteryLevelCradle = batteryLevel;
    }

    public int getExtraBatteryStatusLeft() {
        return this.mExtraBatteryStatusLeft;
    }

    public void setExtraBatteryStatusLeft(int batteryStatus) {
        this.mExtraBatteryStatusLeft = batteryStatus;
    }

    public int getExtraBatteryStatusRight() {
        return this.mExtraBatteryStatusRight;
    }

    public void setExtraBatteryStatusRight(int batteryStatus) {
        this.mExtraBatteryStatusRight = batteryStatus;
    }

    public int getExtraBatteryStatusCradle() {
        return this.mExtraBatteryStatusCradle;
    }

    public void setExtraBatteryStatusCradle(int batteryStatus) {
        this.mExtraBatteryStatusCradle = batteryStatus;
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

    private SemCompanionDeviceBatteryInfo(Parcel in) {
        this.mBatteryLevel = -1;
        this.mExtraBatteryLevelLeft = -1;
        this.mExtraBatteryLevelRight = -1;
        this.mExtraBatteryLevelCradle = -1;
        this.mBatteryStatus = 1;
        this.mExtraBatteryStatusLeft = 1;
        this.mExtraBatteryStatusRight = 1;
        this.mExtraBatteryStatusCradle = 1;
        readFromParcel(in);
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mAddress);
        out.writeString(this.mDeviceName);
        out.writeInt(this.mDeviceType);
        out.writeInt(this.mBatteryLevel);
        out.writeInt(this.mExtraBatteryLevelLeft);
        out.writeInt(this.mExtraBatteryLevelRight);
        out.writeInt(this.mExtraBatteryLevelCradle);
        out.writeInt(this.mBatteryStatus);
        out.writeInt(this.mExtraBatteryStatusLeft);
        out.writeInt(this.mExtraBatteryStatusRight);
        out.writeInt(this.mExtraBatteryStatusCradle);
    }

    public void readFromParcel(Parcel in) {
        this.mAddress = in.readString();
        this.mDeviceName = in.readString();
        this.mDeviceType = in.readInt();
        this.mBatteryLevel = in.readInt();
        this.mExtraBatteryLevelLeft = in.readInt();
        this.mExtraBatteryLevelRight = in.readInt();
        this.mExtraBatteryLevelCradle = in.readInt();
        this.mBatteryStatus = in.readInt();
        this.mExtraBatteryStatusLeft = in.readInt();
        this.mExtraBatteryStatusRight = in.readInt();
        this.mExtraBatteryStatusCradle = in.readInt();
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }
}
