package com.samsung.android.wifi;

import android.os.Parcel;
import android.os.Parcelable;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/* loaded from: classes6.dex */
public class SemWifiApBleScanResult implements Parcelable {
    public static final int AH_SOURCE = 1;
    public static final Parcelable.Creator<SemWifiApBleScanResult> CREATOR = new Parcelable.Creator<SemWifiApBleScanResult>() { // from class: com.samsung.android.wifi.SemWifiApBleScanResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiApBleScanResult createFromParcel(Parcel parcel) {
            return new SemWifiApBleScanResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiApBleScanResult[] newArray(int i) {
            return new SemWifiApBleScanResult[i];
        }
    };
    public static final int IH_SOURCE = 3;
    public static final int MCF_SOURCE = 2;
    public static int MHS_WIFI_6E_NETWORK = 32;
    public static int MHS_WIFI_6_NETWORK = 16;
    public boolean isDataSaverEnabled;
    public boolean isMobileDataLimitReached;
    public boolean isNotValidNetwork;
    public boolean isWifiProfileShareEnabled;
    public int mBLERssi;
    public int mBattery;
    public String mDevice;
    public int mMHSdeviceType;
    public int mNetworkSignalStrength;
    public int mNetworkType;
    public int mProtocol;
    public String mSSID;
    public int mSecurity;
    public long mTimeStamp;
    public String mUserName;
    public String mWifiMac;
    public int mhidden;
    public int version;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemWifiApBleScanResult(String str, int i, int i2, int i3, int i4, String str2, String str3, String str4, int i5, int i6, long j, int i7, int i8, boolean z, boolean z2, boolean z3, int i9) {
        this.mDevice = str;
        this.mBattery = i2;
        this.mMHSdeviceType = i;
        this.mNetworkType = i3;
        this.mNetworkSignalStrength = i4;
        this.mWifiMac = str2;
        this.mUserName = str3;
        this.mSSID = str4;
        this.mhidden = i5;
        this.mSecurity = i6;
        this.mTimeStamp = j;
        this.mBLERssi = i7;
        this.version = i8;
        this.isDataSaverEnabled = z;
        this.isWifiProfileShareEnabled = z2;
        this.isMobileDataLimitReached = z3;
        this.mProtocol = i9;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mDevice != null) {
            parcel.writeInt(1);
            parcel.writeByteArray(this.mDevice.getBytes());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mBattery);
        parcel.writeInt(this.mMHSdeviceType);
        parcel.writeInt(this.mNetworkType);
        parcel.writeInt(this.mNetworkSignalStrength);
        if (this.mWifiMac != null) {
            parcel.writeInt(1);
            parcel.writeByteArray(this.mWifiMac.getBytes());
        } else {
            parcel.writeInt(0);
        }
        if (this.mUserName != null) {
            parcel.writeInt(1);
            parcel.writeByteArray(this.mUserName.getBytes());
        } else {
            parcel.writeInt(0);
        }
        if (this.mSSID != null) {
            parcel.writeInt(1);
            parcel.writeByteArray(this.mSSID.getBytes());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mhidden);
        parcel.writeInt(this.mSecurity);
        parcel.writeLong(this.mTimeStamp);
        parcel.writeInt(this.mBLERssi);
        parcel.writeInt(this.version);
        parcel.writeBoolean(this.isDataSaverEnabled);
        parcel.writeBoolean(this.isWifiProfileShareEnabled);
        parcel.writeBoolean(this.isMobileDataLimitReached);
        parcel.writeBoolean(this.isNotValidNetwork);
        parcel.writeInt(this.mProtocol);
    }

    private SemWifiApBleScanResult(Parcel parcel) {
        readFromParcel(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        if (parcel.readInt() == 1) {
            this.mDevice = new String(parcel.createByteArray(), StandardCharsets.UTF_8);
        }
        this.mBattery = parcel.readInt();
        this.mMHSdeviceType = parcel.readInt();
        this.mNetworkType = parcel.readInt();
        this.mNetworkSignalStrength = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.mWifiMac = new String(parcel.createByteArray(), StandardCharsets.UTF_8);
        }
        if (parcel.readInt() == 1) {
            this.mUserName = new String(parcel.createByteArray(), StandardCharsets.UTF_8);
        }
        if (parcel.readInt() == 1) {
            this.mSSID = new String(parcel.createByteArray(), StandardCharsets.UTF_8);
        }
        this.mhidden = parcel.readInt();
        this.mSecurity = parcel.readInt();
        this.mTimeStamp = parcel.readLong();
        this.mBLERssi = parcel.readInt();
        this.version = parcel.readInt();
        this.isDataSaverEnabled = parcel.readBoolean();
        this.isWifiProfileShareEnabled = parcel.readBoolean();
        this.isMobileDataLimitReached = parcel.readBoolean();
        this.isNotValidNetwork = parcel.readBoolean();
        this.mProtocol = parcel.readInt();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mWifiMac, ((SemWifiApBleScanResult) obj).mWifiMac);
    }

    public String toString() {
        return "SemWifiApBleScanResult{mMHSdeviceType=" + this.mMHSdeviceType + ", mDevice='" + hideString(this.mDevice) + "', mBattery=" + this.mBattery + ", mNetworkType=" + this.mNetworkType + ", mNetworkSignalStrength=" + this.mNetworkSignalStrength + ", mWifiMac='" + this.mWifiMac + "', mUserName='" + hideString(this.mUserName) + "', mSSID='" + this.mSSID + "', mhidden=" + this.mhidden + ", mSecurity=" + this.mSecurity + ", mTimeStamp=" + this.mTimeStamp + ", mBLERssi=" + this.mBLERssi + ", version=" + this.version + ", isDataSaverEnabled=" + this.isDataSaverEnabled + ", isWifiProfileShareEnabled=" + this.isWifiProfileShareEnabled + ", isMobileDataLimitReached=" + this.isMobileDataLimitReached + ", isNotValidNetwork=" + this.isNotValidNetwork + ", mProtocol=" + this.mProtocol + '}';
    }

    String hideString(String str) {
        if (SemWifiManager.MHSDBG || str == null || str.isEmpty()) {
            return str;
        }
        int length = str.length() / 2;
        StringBuffer stringBuffer = new StringBuffer(str.substring(0, length));
        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }
}
